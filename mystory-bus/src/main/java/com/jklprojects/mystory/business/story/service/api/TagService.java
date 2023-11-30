/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.api;

import com.jklprojects.mystory.business.story.entity.Tag;
import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.common.story.TagStatus;
import java.util.Set;
import javax.ejb.Local;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 16, 2015
 */
@Local
public interface TagService {
	TagView createTag(TagView tagView);

	Tag find(long id);

	Set<Tag> findTagsEntitiesWithNames(String... tags);

	Set<TagView> findTagsWithNames(String... tags);

	Set<TagView> findTagsWithStatuses(TagStatus... statuses);

	TagView findTagWithName(String tagName);
}
