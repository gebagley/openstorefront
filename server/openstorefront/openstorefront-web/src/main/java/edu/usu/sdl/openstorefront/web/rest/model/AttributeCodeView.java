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
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dshurtleff
 */
public class AttributeCodeView
{

	private String code;
	private String label;
	private String description;
	private String fullTextLink;

	public AttributeCodeView()
	{
	}

	public static AttributeCodeView toView(AttributeCode attributeCode)
	{
		AttributeCodeView attributeCodeView = new AttributeCodeView();
		attributeCodeView.setCode(attributeCode.getAttributeCodePk().getAttributeCode());
		attributeCodeView.setLabel(attributeCode.getLabel());
		attributeCodeView.setDescription(attributeCode.getDescription());

		if (StringUtils.isNotBlank(attributeCode.getArticleFilename())) {
			attributeCodeView.setFullTextLink("api/v1/resource/attributes/" + attributeCode.getAttributeCodePk().getAttributeType() + "/" + attributeCode.getAttributeCodePk().getAttributeCode() + "/article");
		} else {
			attributeCodeView.setFullTextLink(attributeCode.getDetailUrl());
		}

		return attributeCodeView;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getFullTextLink()
	{
		return fullTextLink;
	}

	public void setFullTextLink(String fullTextLink)
	{
		this.fullTextLink = fullTextLink;
	}

}
