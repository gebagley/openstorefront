/*
 * Copyright 2014 Space Dynamics Laboratory - Utah State University Research Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usu.sdl.openstorefront.web.rest.model;

import edu.usu.sdl.openstorefront.doc.ConsumeField;
import edu.usu.sdl.openstorefront.doc.DataType;
import edu.usu.sdl.openstorefront.service.ServiceProxy;
import edu.usu.sdl.openstorefront.storage.model.Component;
import edu.usu.sdl.openstorefront.storage.model.ComponentReview;
import edu.usu.sdl.openstorefront.storage.model.ExperienceTimeType;
import edu.usu.sdl.openstorefront.storage.model.UserTypeCode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * Composite View of a Component Review
 *
 * @author dshurtleff
 */
public class ComponentReviewView
{

	private String username;

	@NotNull
	@ConsumeField
	private String userType;

	@NotNull
	@ConsumeField
	private String comment;

	@NotNull
	@ConsumeField
	private int rating;

	@NotNull
	@ConsumeField
	private String title;

	@NotNull
	@ConsumeField
	private String usedTimeCode;

	@NotNull
	@ConsumeField
	private Date lastUsed;
	private Date updateDate;

	@NotNull
	@ConsumeField
	private String organization;

	@ConsumeField
	private boolean recommend;
	private String componentId;
	private String reviewId;

	@NotNull
	@ConsumeField
	private String name;
	private Component component;

	@ConsumeField
	@DataType(ComponentReviewProCon.class)
	private List<ComponentReviewProCon> pros = new ArrayList<>();

	@ConsumeField
	@DataType(ComponentReviewProCon.class)
	private List<ComponentReviewProCon> cons = new ArrayList<>();

	public ComponentReviewView()
	{
	}

	public static ComponentReviewView toView(ComponentReview review)
	{
		ServiceProxy service = new ServiceProxy();
		ComponentReviewView view = new ComponentReviewView();
		view.setUsername(review.getCreateUser());
		UserTypeCode typeCode = service.getLookupService().getLookupEnity(UserTypeCode.class, review.getUserTypeCode());
		if (typeCode == null) {
			view.setUserType(null);
		} else {
			view.setUserType(typeCode.getDescription());
		}
		view.setComment(review.getComment());
		view.setRating(review.getRating());
		view.setTitle(review.getTitle());
		view.setComponentId(review.getComponentId());
		view.setReviewId(review.getComponentReviewId());
		view.setName(service.getPersistenceService().findById(Component.class, review.getComponentId()).getName());
		ExperienceTimeType timeCode = service.getLookupService().getLookupEnity(ExperienceTimeType.class, review.getUserTimeCode());
		if (timeCode == null) {
			view.setUsedTimeCode(null);
		} else {
			view.setUsedTimeCode(timeCode.getDescription());
		}
		view.setLastUsed(review.getLastUsed());
		view.setUpdateDate(review.getUpdateDts());
		view.setOrganization(review.getOrganization());
		view.setRecommend(review.getRecommend());
		return view;
	}

	public List<ComponentReviewProCon> getPros()
	{
		return pros;
	}

	public void setPros(List<ComponentReviewProCon> pros)
	{
		this.pros = pros;
	}

	public List<ComponentReviewProCon> getCons()
	{
		return cons;
	}

	public void setCons(List<ComponentReviewProCon> cons)
	{
		this.cons = cons;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public int getRating()
	{
		return rating;
	}

	public void setRating(int rating)
	{
		this.rating = rating;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUsedTimeCode()
	{
		return usedTimeCode;
	}

	public void setUsedTimeCode(String usedTimeCode)
	{
		this.usedTimeCode = usedTimeCode;
	}

	public Date getLastUsed()
	{
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed)
	{
		this.lastUsed = lastUsed;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public String getOrganization()
	{
		return organization;
	}

	public void setOrganization(String organization)
	{
		this.organization = organization;
	}

	public boolean isRecommend()
	{
		return recommend;
	}

	public void setRecommend(boolean recommend)
	{
		this.recommend = recommend;
	}

	public Component getComponent()
	{
		return component;
	}

	public void setComponent(Component component)
	{
		this.component = component;
	}

	public String getComponentId()
	{
		return componentId;
	}

	public void setComponentId(String componentId)
	{
		this.componentId = componentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getReviewId()
	{
		return reviewId;
	}

	public void setReviewId(String reviewId)
	{
		this.reviewId = reviewId;
	}

}
