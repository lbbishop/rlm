/*
 * South Face Software
 * Copyright 2012, South Face Software, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.rhinocorps.rlm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.rhinocorps.rlm.data.Constants;
import com.rhinocorps.rlm.data.Literal;
import com.rhinocorps.rlm.data.RiskLevelType;
import com.rhinocorps.rlm.util.ModelUtils;

/**
 * Risk
 * 
 * @author lbbishop
 * 
 */
@Entity
@Indexed
@Audited
@Table(name = "risk")
public class Risk extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	@NotNull(message = "Name is required")
	@Size(max = 100)
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	@NotNull(message = "Description is required")
	@Lob
	@Column(name = "description", columnDefinition = "CLOB", nullable = false)
	private String description;

	@Lob
	@Column(name = "mitigation", columnDefinition = "CLOB", nullable = true)
	private String mitigation;

	@NotNull(message = "Level is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "risk_level_type", nullable = false)
	private RiskLevelType riskLevelType;

	@ManyToOne
	private Project project;

	/**
	 * Default constructor
	 */
	public Risk() {
		super();
	}

	/**
	 * Constructor
	 */
	public Risk(int identifier) {
		super();
		this.identifier = Integer.valueOf(identifier);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		if (name != null) {
			this.name = name.trim();
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the description abbreviated
	 */
	public String getDescriptionAbbrv() {
		return StringUtils.abbreviate(this.description, Constants.ABBRV_DESC_LEN);
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mitigation
	 */
	public String getMitigation() {
		return mitigation;
	}

	/**
	 * @return the mitigation
	 */
	public String getMitigationAbbrv() {
		return StringUtils.abbreviate(this.mitigation, Constants.ABBRV_DESC_LEN);
	}

	/**
	 * @param mitigation
	 *            the mitigation to set
	 */
	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	/**
	 * @return the riskLevelType
	 */
	public RiskLevelType getRiskLevelType() {
		return riskLevelType;
	}

	/**
	 * @param riskLevelType
	 *            the riskLevelType to set
	 */
	public void setRiskLevelType(RiskLevelType riskLevelType) {
		this.riskLevelType = riskLevelType;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the identifier string (PREFIX concatenated with identifier)
	 */
	public String getArtifact() {
		return ModelUtils.buildArtifactIdentifier(Literal.PREFIX_RISK.toString(), this.identifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Risk [id=");
		builder.append(id);
		builder.append(", identifier=");
		builder.append(identifier);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", mitigation=");
		builder.append(mitigation);
		builder.append(", riskLevelType=");
		builder.append(riskLevelType);
		builder.append(", project=");
		builder.append(project);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Risk other = (Risk) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
