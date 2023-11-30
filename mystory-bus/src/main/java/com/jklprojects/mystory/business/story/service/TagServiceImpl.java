/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.story.entity.Tag;
import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.business.story.service.api.TagService;
import com.jklprojects.mystory.common.story.TagStatus;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Story Tag services.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, May 16, 2015
 * @version 2, 2017-Apr-23
 */
@Stateless
public class TagServiceImpl extends AppEntityDAO<Tag> implements TagService {

	private static final XLogger logger = XLoggerFactory.getXLogger(TagServiceImpl.class);

	public TagServiceImpl() {
		super(Tag.class);
	}

	@Override
	public TagView createTag(TagView tagView) {
		Tag entity = create(tagView.setEntityFromView(new Tag(), false));
		return new TagView().setViewFromEntity(entity, false);
	}

	@Override
	public Set<Tag> findTagsEntitiesWithNames(String... tags) {
		List<Tag> entities = getEM().createNamedQuery("findTagsWithNames", Tag.class).setParameter("tags", tags)
				.getResultList();

		return new HashSet<Tag>(entities);
	}

	@Override
	public Set<TagView> findTagsWithNames(String... tags) {
		Set<Tag> entities = findTagsEntitiesWithNames(tags);
		return TagView.toViews(entities);
	}

	@Override
	public Set<TagView> findTagsWithStatuses(TagStatus... statuses) {
		Set<Tag> entities = findTagsWithStatuses_(statuses);
		return TagView.toViews(entities);
	}

	private Set<Tag> findTagsWithStatuses_(TagStatus... statuses) {
		List<Tag> entities = getEM().createNamedQuery("findTagsWithStatuses", Tag.class)
				.setParameter("status", Arrays.asList(statuses)).getResultList();

		return new HashSet<Tag>(entities);
	}

	@Override
	public TagView findTagWithName(String tagName) {
		Tag entity = getEM().createNamedQuery("findTagWithName", Tag.class).setParameter("tagName", tagName)
				.getSingleResult();

		return entity != null ? new TagView().setViewFromEntity(entity, false) : null;
	}
}
