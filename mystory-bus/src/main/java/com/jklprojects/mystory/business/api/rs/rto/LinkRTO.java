/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.rs.rto;

/**
 * The link object - Hypermedia As The Engine Of Application State (HATEOAS).
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 2, 2018-04-23
 */
public class LinkRTO implements RTO, Comparable<LinkRTO> {

	private static final long serialVersionUID = -741329871630701241L;

	private String id;
	private String href;
	private String rel;
	private String method;

	public LinkRTO() {
		super();
	}

	public LinkRTO(String id, String href, String rel, String method) {
		this();

		this.id = id;
		this.href = href;
		this.rel = rel;
		this.method = method;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LinkRTO))
			return false;

		LinkRTO linkRTO = (LinkRTO) o;

		if (getId() != null ? !getId().equals(linkRTO.getId()) : linkRTO.getId() != null)
			return false;
		if (getHref() != null ? !getHref().equals(linkRTO.getHref()) : linkRTO.getHref() != null)
			return false;
		if (getRel() != null ? !getRel().equals(linkRTO.getRel()) : linkRTO.getRel() != null)
			return false;
		return getMethod() != null ? getMethod().equals(linkRTO.getMethod()) : linkRTO.getMethod() == null;
	}

	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result + (getHref() != null ? getHref().hashCode() : 0);
		result = 31 * result + (getRel() != null ? getRel().hashCode() : 0);
		result = 31 * result + (getMethod() != null ? getMethod().hashCode() : 0);
		return result;
	}

	/** @return the href */
	public String getHref() {
		return href;
	}

	public String getId() {
		return id;
	}

	/** @return the method */
	public String getMethod() {
		return method;
	}

	/** @return the rel */
	public String getRel() {
		return rel;
	}

	@Override
	public String toString() {
		return "LinkRTO [id=" + id + ", href=" + href + ", rel=" + rel + ", method=" + method + ", getHref()="
				+ getHref() + ", getId()=" + getId() + ", getMethod()=" + getMethod() + ", getRel()=" + getRel() + "]";
	}

	@Override
	public int compareTo(LinkRTO o) {
		return getId().compareTo(o.getId());
	}
}
