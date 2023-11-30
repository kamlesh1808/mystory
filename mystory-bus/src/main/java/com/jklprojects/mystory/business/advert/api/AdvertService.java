/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.api;

import com.jklprojects.mystory.business.advert.entity.Advert;
import com.jklprojects.mystory.business.advert.entity.view.AdvertView;
import com.jklprojects.mystory.common.advert.AdvertType;
import com.jklprojects.mystory.common.advert.AdvertWatchType;
import com.jklprojects.mystory.common.advert.dto.AdvertDTO;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.util.List;
import javax.ejb.Local;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-01-23
 */
@Local
public interface AdvertService {

	void createAdvertWatchOnStory(List<Long> advertIds, long storyId, UserRoleType userWatcherRoleType,
			Long watcherUserId, AdvertWatchType advertWatchType, String watcherIPAddress);

	Advert findAdvert(long id) throws AppCodeException;

	List<Advert> findAdvertsAll();

	List<Advert> findAdvertsWithAdvertTypes(AdvertType... advertTypes);

	List<Advert> findAdvertsWithStoryName(StoryName storyName);

	List<AdvertView> findAdvertViewsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes);

	List<Advert> findAdvertsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes);

	List<AdvertDTO> findAdvertDTOsWithStoryNameAdvertType(StoryName storyName, AdvertType... advertTypes);
}
