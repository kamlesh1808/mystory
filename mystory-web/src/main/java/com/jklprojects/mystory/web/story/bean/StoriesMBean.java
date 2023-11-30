/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.advert.AdvertType;
import com.jklprojects.mystory.common.advert.AdvertWatchType;
import com.jklprojects.mystory.common.advert.dto.AdvertDTO;
import com.jklprojects.mystory.common.blacklist.BlackListType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.UserProfileDTO;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import com.jklprojects.mystory.web.api.beans.AppMBean;
import com.jklprojects.mystory.web.user.bean.SignInMBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-23
 */
@Named("storiesMBean")
@SessionScoped
public class StoriesMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoriesMBean.class);
	private static final long serialVersionUID = 5109048508547793683L;

	@Inject
	private SignInMBean signInMBean;

	@Inject
	private AppMBean appMBean;

	private StoryView story;

	private boolean storyEditEnabled;
	private boolean storyEditReviewEnabled;
	private UserProfileDTO userProfileOfStory;
	private List<StoryLinkDTO> storiesRelated;

	private List<AdvertDTO> advertBooks = new ArrayList<>();
	private List<AdvertDTO> advertProducts = new ArrayList<>();
	private List<AdvertDTO> advertWebsites = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();
	}

	/** Find the story based on request parameter, story id and prepare for edit. */
	public void doStoryEdit() {
		if (findStory()) {
			setStoryEditEnabled();
		}
	}

	/**
	 * Find the story based on request parameter, story id, and prepare for review
	 * edit.
	 */
	public void doStoryEditReview() {
		if (findStory()) {
			setStoryEditReviewEnabled();
		}
	}

	/** Persist the story reviewed changes. */
	public String doStoryReview() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();
		if (story != null) {
			try {
				story = getStoryService().reviewStory(story);
				addMsg(I18N.APP.getI18N("story_storyReviewedSuccessMsg"), "", FacesMessage.SEVERITY_INFO, true);

				navOutcome = JSFNavigation.SUCCESS.getNavigation();
			} catch (AppCodeException e) {
				processAppCodeException(e);
			}
		}
		return navOutcome;
	}

	/** Persist the user updated story. */
	public void doStoryUpdate() {
		if (story != null) {
			try {
				story = getStoryService().updateStory(story);
				addMsg(I18N.APP.getI18N("story_storyUpdatedSuccessMsg"), "", FacesMessage.SEVERITY_INFO, true);
			} catch (AppCodeException e) {
				processAppCodeException(e);
			}
		}
	}

	public String doStoryUpdateStatusActive(long storyId) {
		return doStoryUpdateStatus(storyId, StoryStatus.ACTIVE);
	}

	public String doStoryUpdateStatusInactive(long storyId) {
		return doStoryUpdateStatus(storyId, StoryStatus.INACTIVE);
	}

	public UserProfileDTO getUserProfileOfStory() {
		return userProfileOfStory;
	}

	public List<StoryLinkDTO> getStoriesRelated() {
		return storiesRelated;
	}

	public boolean renderStoriesRelated() {
		return CollUtil.isNotEmpty(storiesRelated);
	}

	/** Find the story based on request parameter, story id and display it. */
	public void doStoryView() {
		logger.entry();

		if (findStory() && story != null) {

			if (isStoryViewable(story)) {
				findAdverts();

				try {
					logger.debug("findUserProfileOfStory storyId {} ", story.getId());
					userProfileOfStory = getUserProfileService().findUserProfileOfStory(story.getId());

					storiesRelated = getStoryService().findStoriesRelated(userProfileOfStory.getUserUID(),
							story.getId(), userProfileOfStory.getUserRoleType());
				} catch (AppCodeException e) {
					logger.warn(e.getI18NMessage(), e);
					processAppCodeException(e);
				}

				String watcherIPAddress = getClientIPAddress();
				long storyAuthorId = getStoryAuthorId(story);
				long watcherUserId = getWatcherUserId();
				UserRoleType watcherRoleType = getWatcherRoleType();

				if (watcherUserId != storyAuthorId) {
					if (!getBlackListService().isBlackListed(watcherIPAddress, BlackListType.STORY_WATCH_IP_ADDRESS)) {
						getStoryWatchService().createStoryWatch(story.getId(), watcherIPAddress, watcherUserId,
								watcherRoleType, storyAuthorId);
					}

					if (!getBlackListService().isBlackListed(watcherIPAddress, BlackListType.ADVERT_WATCH_IP_ADDRESS)) {
						getAdvertService().createAdvertWatchOnStory(getAdvertIds(), story.getId(), watcherRoleType,
								watcherUserId, AdvertWatchType.VIEW, watcherIPAddress);
					}
				} else {
					logger.debug("Watch not created. watcher user id {} is the story author id {}", watcherUserId,
							storyAuthorId);
				}

			} else { // handle story not viewable error conditions
				String storyTitle = getStoryTitle(story);
				String storyName = getStoryName(story);

				setStory(null); // reset the found story

				if (getSignInMBean() != null && signInMBean.isUserSignedIn()) {
					setStory(null);
					addMsg(I18N.APP.getI18N("stories_sharedStoryMsg", storyTitle, storyName), "",
							FacesMessage.SEVERITY_INFO, true);
				} else {
					addMsg(I18N.APP.getI18N("stories_signinToAccessStoryMsg", storyTitle, storyName), "",
							FacesMessage.SEVERITY_INFO, true);
				}
			}
		}
	}

	public List<AdvertDTO> getAdvertBooks() {
		return advertBooks;
	}

	public void setAdvertBooks(List<AdvertDTO> advertBooks) {
		this.advertBooks = advertBooks;
	}

	public List<AdvertDTO> getAdvertBooksForDisplay() {
		return getAdvertBooksForDisplay(false);
	}

	public List<AdvertDTO> getAdvertBooksForDisplay(boolean sort) {
		if (CollUtil.isNotEmpty(advertBooks)) {
			if (advertBooks.size() > appMBean.getStoryNumberOfBookAdvertsToDisplay()) {
				if (sort) {
					advertBooks.sort(Comparator.comparing(AdvertDTO::getAdvertPriority));
				} else {
					Collections.shuffle(advertBooks);
				}
				return advertBooks.subList(0, appMBean.getStoryNumberOfBookAdvertsToDisplay());
			}
		}
		return advertBooks;
	}

	public List<Long> getAdvertIds() {
		List<Long> advertIds = new ArrayList<>();

		if (CollUtil.isNotEmpty(getAdvertBooks())) {
			advertIds
					.addAll(getAdvertBooks().parallelStream().map(AdvertDTO::getAdvertId).collect(Collectors.toList()));
		}

		if (CollUtil.isNotEmpty(getAdvertProducts())) {
			advertIds.addAll(
					getAdvertProducts().parallelStream().map(AdvertDTO::getAdvertId).collect(Collectors.toList()));
		}

		if (CollUtil.isNotEmpty(getAdvertWebsites())) {
			advertIds.addAll(
					getAdvertWebsites().parallelStream().map(AdvertDTO::getAdvertId).collect(Collectors.toList()));
		}

		return advertIds;
	}

	public List<AdvertDTO> getAdvertProducts() {
		return advertProducts;
	}

	public void setAdvertProducts(List<AdvertDTO> advertProducts) {
		this.advertProducts = advertProducts;
	}

	public List<AdvertDTO> getAdvertProductsForDisplay() {
		return getAdvertProductsForDisplay(false);
	}

	public List<AdvertDTO> getAdvertProductsForDisplay(boolean sort) {
		if (CollUtil.isNotEmpty(advertProducts)) {
			if (sort) {
				advertProducts.sort(Comparator.comparing(AdvertDTO::getAdvertPriority));
			} else {
				Collections.shuffle(advertProducts);
			}

			if (advertProducts.size() > appMBean.getStoryNumberOfProductAdvertsToDisplay()) {
				return advertProducts.subList(0, appMBean.getStoryNumberOfProductAdvertsToDisplay());
			}
		}
		return advertProducts;
	}

	public AdvertDTO getAdvertWebsite() {
		if (CollUtil.isNotEmpty(getAdvertWebsites())) {
			int randomInt = new Random().nextInt(getAdvertWebsites().size());
			return getAdvertWebsites().get(randomInt);
		}
		return null;
	}

	public List<AdvertDTO> getAdvertWebsites() {
		return advertWebsites;
	}

	public void setAdvertWebsites(List<AdvertDTO> advertWebsites) {
		this.advertWebsites = advertWebsites;
	}

	public SignInMBean getSignInMBean() {
		return signInMBean;
	}

	/**
	 * Required setter method for injection.
	 *
	 * @param signInMBean
	 */
	public void setSignInMBean(SignInMBean signInMBean) {
		this.signInMBean = signInMBean;
	}

	public StoryView getStory() {
		return story;
	}

	public void setStory(StoryView story) {
		this.story = story;
	}

	public boolean isAdvertBooksAvailable() {
		return CollUtil.isNotEmpty(getAdvertBooks());
	}

	public boolean isAdvertProductsAvailable() {
		return CollUtil.isNotEmpty(getAdvertProducts());
	}

	public boolean isAdvertWebsitesAvailable() {
		return CollUtil.isNotEmpty(getAdvertWebsites());
	}

	public boolean isStoryEditEnabled() {
		return storyEditEnabled;
	}

	public boolean isStoryEditReviewEnabled() {
		return storyEditReviewEnabled;
	}

	public boolean isStoryViewable(final StoryView story) {
		boolean storyViewable = false;
		if (story != null) {
			if (story.getAccessType().isAccessTypePublic()) {
				storyViewable = true;
			} else if (story.getAccessType().isStoryAccessTypeSharedWithStory()) {
				if (isUserSignedIn()) {
					storyViewable = getUserProfileService().isStoryViewableByUser(story, getSignedInUserUID(),
							getSignedInUserRoleType());
				}
			}
		}

		logger.debug("storyViewable {}", storyViewable);
		return storyViewable;
	}

	private String doStoryUpdateStatus(long storyId, StoryStatus status) {
		logger.info("storyId: {}, status: {}", storyId, status);
		String navOutcome = JSFNavigation.FAILURE.getNavigation();
		story = getStoryService().updateStoryStatus(storyId, status);

		logger.info("story after status update, id: {} status: {} ", story.getId(), story.getStatus());

		addMsg(I18N.APP.getI18N("story_storyStatusUpdatedSuccessMsg"), "", FacesMessage.SEVERITY_INFO, true);

		navOutcome = story != null ? JSFNavigation.SUCCESS.getNavigation() : JSFNavigation.FAILURE.getNavigation();

		return navOutcome;
	}

	private void findAdverts() {
		logger.entry();

		if (story != null) {
			resetAdverts();

			List<AdvertDTO> advertDTOs = getAdvertService().findAdvertDTOsWithStoryNameAdvertType(story.getStoryName(),
					AdvertType.BOOK, AdvertType.PRODUCT, AdvertType.WEBSITE);

			setAdvertBooks(
					advertDTOs.stream().filter(v -> v.getAdvertType() == AdvertType.BOOK).collect(Collectors.toList()));

			setAdvertProducts(advertDTOs.stream().filter(v -> v.getAdvertType() == AdvertType.PRODUCT)
					.collect(Collectors.toList()));

			setAdvertWebsites(advertDTOs.stream().filter(v -> v.getAdvertType() == AdvertType.WEBSITE)
					.collect(Collectors.toList()));
		}
	}

	private boolean findStory() {
		boolean found = false;
		// get storyId from request parameter
		String requestParamStoryId = getRequestParamStoryId();
		logger.debug("request parameter storyId {} ", requestParamStoryId);

		try {
			long storyId = Long.parseLong(requestParamStoryId);
			if (storyId > 0) {
				StoryView storyFound = getStoryService().findStory(storyId);
				if (storyFound != null && storyFound.isIdSet()) {
					story = storyFound;
					found = true;
					logger.debug("story found {} ", story.getId());
				}
			}
		} catch (NumberFormatException e) {
			setStory(null); // reset the found story

			processAppException(new AppException(I18N.APP.getI18N("story_storyIdInvalid", requestParamStoryId)));

		} catch (AppCodeException e) {
			setStory(null); // reset the found story

			processAppCodeException(e);
		}
		return found;
	}

	private void resetAdverts() {
		this.advertBooks = new ArrayList<AdvertDTO>();
		this.advertProducts = new ArrayList<AdvertDTO>();
		this.advertWebsites = new ArrayList<AdvertDTO>();
	}

	/** Determine and set if the story is editable. */
	private void setStoryEditEnabled() {
		if (story != null && getSignInMBean().isSignedInUserStoryAuthor(story)) {
			if (!story.getStoryType().isIntro()) {
				storyEditEnabled = false; // reset

				int expiryHours = getAppConfigService().getStoryGeneralPostingEditExpiryInHours();
				logger.info(AppConfigConsts.STORY_GENERAL_POST_EDIT_EXPIRY_IN_HRS + " {} ", expiryHours);

				storyEditEnabled = TimeUtil.isBefore(story.getCreatedTimestamp().toLocalDateTime(), expiryHours,
						TimeUnit.HOURS);

			} else if (story.getStoryType().isIntro()) {
				storyEditEnabled = true;
			}
		}
	}

	/** Determine and set if the story is editable for review. */
	private void setStoryEditReviewEnabled() {
		storyEditReviewEnabled = story != null && getSignInMBean().isSignedInUserRoleTypeAppAdmin();
	}
}
