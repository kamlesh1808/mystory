/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.service;

import com.jklprojects.mystory.business.advert.api.AdvertService;
import com.jklprojects.mystory.business.advert.entity.Advert;
import com.jklprojects.mystory.business.advert.entity.AdvertStoryName;
import com.jklprojects.mystory.business.advert.entity.AdvertWatch;
import com.jklprojects.mystory.business.advert.entity.view.AdvertView;
import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.common.advert.AdvertAttrType;
import com.jklprojects.mystory.common.advert.AdvertExceptions;
import com.jklprojects.mystory.common.advert.AdvertStatus;
import com.jklprojects.mystory.common.advert.AdvertType;
import com.jklprojects.mystory.common.advert.AdvertWatchType;
import com.jklprojects.mystory.common.advert.dto.AdvertDTO;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.util.CollUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Advert services API for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 0.31.0-2017-01-23
 */
@Stateless
public class AdvertServiceImpl extends AppEntityDAO<Advert> implements AdvertService {

	private static final XLogger logger = XLoggerFactory.getXLogger(AdvertServiceImpl.class);

	public AdvertServiceImpl() {
		super(Advert.class);
	}

	@Override
	public void createAdvertWatchOnStory(List<Long> advertIds, long storyId, UserRoleType watcherRoleType,
			Long watcherUserId, AdvertWatchType advertWatchType, String watcherIPAddress) {

		logger.entry(advertIds, storyId, watcherRoleType, watcherUserId, advertWatchType, watcherIPAddress);

		if (CollUtil.isNotEmpty(advertIds)) {
			for (long advertId : advertIds) {
				AdvertWatch advertWatch = new AdvertWatch(advertId, advertWatchType, storyId, watcherUserId,
						watcherRoleType, watcherIPAddress);

				createEntity(advertWatch);
			}
		}
	}

	@Override
	public Advert findAdvert(long id) throws AppCodeException {
		logger.entry(id);

		Advert advert = find(id);
		if (advert == null) {
			throw AdvertExceptions.ADVERT_002.newAppCodeExcepiton(String.valueOf(id));
		}

		return advert;
	}

	@Override
	public List<Advert> findAdvertsAll() {
		logger.entry();

		return getEM().createNamedQuery("findAdvertAll", Advert.class).getResultList();
	}

	@Override
	public List<Advert> findAdvertsWithAdvertTypes(AdvertType... advertTypes) {
		logger.entry(Arrays.toString(advertTypes));

		return getEM().createNamedQuery("findAdvertsWithAdvertTypes", Advert.class)
				.setParameter("status", Arrays.asList(AdvertStatus.ACTIVE))
				.setParameter("advertType", Arrays.asList(advertTypes)).getResultList();
	}

	@Override
	public List<Advert> findAdvertsWithStoryName(StoryName storyName) {
		logger.entry(storyName);

		List<Advert> adverts = new ArrayList<>();
		List<Advert> ads = findAdvertsWithAdvertTypes(AdvertType.values());
		for (Advert advert : ads) {
			for (AdvertStoryName asnv : advert.getAdvertStoryNames()) {
				if (asnv.getStoryName() == storyName && asnv.getStatus().isActive() && !adverts.contains(advert)) {
					adverts.add(advert);
				}
			}
		}
		return adverts;
	}

	@Override
	public List<AdvertView> findAdvertViewsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes) {
		logger.entry(storyName, advertTypes);

		List<Advert> adverts = findAdvertsWithStoryNameAdvertType(storyName, advertTypes);
		return AdvertView.toViews(adverts);
	}

	@Override
	public List<Advert> findAdvertsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes) {
		logger.entry(storyName, advertTypes);

		return getEM().createNamedQuery("findAdvertsWithStoryNameAdvertType", Advert.class)
				.setParameter("status", Arrays.asList(AdvertStatus.ACTIVE))
				.setParameter("advertStoryNameStatus", Arrays.asList(AdvertStatus.ACTIVE))
				.setParameter("storyName", Arrays.asList(storyName))
				.setParameter("advertType", Arrays.asList(advertTypes)).getResultList();
	}

	@Override
	public List<AdvertDTO> findAdvertDTOsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes) {
		logger.entry(storyName, advertTypes);

		List<Advert> adverts = findAdvertsWithStoryNameAdvertType(storyName, advertTypes);
		return toAdvertDTOs(adverts);
	}

	public List<AdvertDTO> toAdvertDTOs(List<Advert> adverts) {
		List<AdvertDTO> advertDTOs = new ArrayList<>();

		for (Advert advert : adverts) {
			String header = advert.getAttrValue(AdvertAttrType.HEADER);
			String title = advert.getAttrValue(AdvertAttrType.TITLE);
			String subTitle = advert.getAttrValue(AdvertAttrType.SUB_TITLE);
			String link = advert.getAttrValue(AdvertAttrType.LINK);
			String imageSrc = advert.getAttrValue(AdvertAttrType.IMAGE_SRC);

			advertDTOs.add(new AdvertDTO(advert.getId(), advert.getName(), advert.getAdvertType(),
					advert.getAdvertPriorityType(), header, title, subTitle, link, imageSrc));
		}

		return advertDTOs;
	}
}
