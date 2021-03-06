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

import edu.usu.sdl.openstorefront.storage.model.AttributeCode;
import java.util.Date;

/**
 * Topic landing page/article
 *
 * @author dshurtleff
 */
public class Article
{

	private String attributeCode;
	private String attributeType;
	private String attributeCodeLabel;
	private String attributeCodeDescription;
	private String html;
	private Date updateDts;
	private String organization;

	public Article()
	{
	}

	public static Article toView(AttributeCode code)
	{
		Article article = new Article();
		article.setAttributeCode(code.getAttributeCodePk().getAttributeCode());
		article.setAttributeType(code.getAttributeCodePk().getAttributeType());
		article.setAttributeCodeLabel(code.getLabel());
		article.setAttributeCodeDescription(code.getDescription());
		article.setUpdateDts(code.getUpdateDts());
		return article;
	}

	public static Article toViewHtml(AttributeCode code, String html)
	{
		Article article = new Article();
		article.setAttributeCode(code.getAttributeCodePk().getAttributeCode());
		article.setAttributeType(code.getAttributeCodePk().getAttributeType());
		article.setAttributeCodeLabel(code.getLabel());
		article.setAttributeCodeDescription(code.getDescription());
		article.setUpdateDts(code.getUpdateDts());
		article.setHtml(html);
		return article;
	}

	public String getAttributeCode()
	{
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode)
	{
		this.attributeCode = attributeCode;
	}

	public String getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(String attributeType)
	{
		this.attributeType = attributeType;
	}

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public Date getUpdateDts()
	{
		return updateDts;
	}

	public void setUpdateDts(Date updateDts)
	{
		this.updateDts = updateDts;
	}

	public String getOrganization()
	{
		return organization;
	}

	public void setOrganization(String organization)
	{
		this.organization = organization;
	}

	public String getAttributeCodeDescription()
	{
		return attributeCodeDescription;
	}

	public void setAttributeCodeDescription(String attributeCodeDescription)
	{
		this.attributeCodeDescription = attributeCodeDescription;
	}

	public String getAttributeCodeLabel()
	{
		return attributeCodeLabel;
	}

	public void setAttributeCodeLabel(String attributeCodeLabel)
	{
		this.attributeCodeLabel = attributeCodeLabel;
	}

}
