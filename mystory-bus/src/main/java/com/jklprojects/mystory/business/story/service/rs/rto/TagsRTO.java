/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.rs.rto;

import com.jklprojects.mystory.business.api.rs.rto.LinkRTO;
import com.jklprojects.mystory.business.common.rs.AbstractRTO;
import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.common.story.TagStatus;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Story immutable RTO.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 2, 2018-04-23
 * @version 3, 2018-10-23
 */
@XmlRootElement(name = "Tag")
public class TagsRTO extends AbstractRTO {

	private static final long serialVersionUID = 7990963831405161113L;

	public static final String RELATIONSHIP_TYPE = "tags";

	private long id;
	private String name;
	private String desc;
	private TagStatus status;
	private String createdTimestamp;
	private String updatedTimestamp;

	private final List<LinkRTO> links = new ArrayList<LinkRTO>();

	/** public no-arg constructor */
	public TagsRTO() {
		super();
	}

	public TagsRTO(TagView view) {
		this();

		if (view != null) {
			id = view.getId();
			name = view.getName();
			desc = view.getDesc();
			status = view.getStatus();

			createdTimestamp = view.getCreatedTimestampFormattedISOLocalDateTime();
			updatedTimestamp = view.getUpdatedTimestampFormattedISOLocalDateTime();
		}
	}

	/** @return the createdTimestamp */
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public long getId() {
		return id;
	}

	public List<LinkRTO> getLinks() {
		return links;
	}

	/** @return the updatedTimestamp */
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public TagStatus getStatus() {
		return status;
	}
}
