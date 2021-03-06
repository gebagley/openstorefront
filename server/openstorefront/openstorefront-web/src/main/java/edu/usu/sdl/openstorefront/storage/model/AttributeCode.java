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
package edu.usu.sdl.openstorefront.storage.model;

import edu.usu.sdl.openstorefront.doc.ConsumeField;
import edu.usu.sdl.openstorefront.util.OpenStorefrontConstant;
import edu.usu.sdl.openstorefront.util.PK;
import edu.usu.sdl.openstorefront.validation.BasicHTMLSanitizer;
import edu.usu.sdl.openstorefront.validation.LinkSanitizer;
import edu.usu.sdl.openstorefront.validation.Sanitize;
import edu.usu.sdl.openstorefront.validation.TextSanitizer;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jlaw
 */
public class AttributeCode
		extends BaseEntity
{

	@PK
	@NotNull
	@ConsumeField
	private AttributeCodePk attributeCodePk;

	@NotNull
	@Size(min = 1, max = OpenStorefrontConstant.FIELD_SIZE_GENERAL_TEXT)
	@Sanitize(TextSanitizer.class)
	@ConsumeField
	private String label;

	@Size(min = 0, max = OpenStorefrontConstant.FIELD_SIZE_DETAILED_DESCRIPTION)
	@Sanitize(BasicHTMLSanitizer.class)
	@ConsumeField
	private String description;

	@Size(min = 0, max = OpenStorefrontConstant.FIELD_SIZE_GENERAL_TEXT)
	private String articleFilename;

	@Size(min = 0, max = OpenStorefrontConstant.FIELD_SIZE_URL)
	@Sanitize(LinkSanitizer.class)
	@ConsumeField
	private String detailUrl;

	public static final String DI2ELEVEL_NA = "NA";
	public static final String DI2ELEVEL_LEVEL0 = "LEVEL0";
	public static final String DI2ELEVEL_LEVEL1 = "LEVEL1";
	public static final String DI2ELEVEL_LEVEL2 = "LEVEL2";
	public static final String DI2ELEVEL_LEVEL3 = "LEVEL3";

	public AttributeCode()
	{
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDetailUrl()
	{
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl)
	{
		this.detailUrl = detailUrl;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public AttributeCodePk getAttributeCodePk()
	{
		return attributeCodePk;
	}

	public void setAttributeCodePk(AttributeCodePk attributeCodePk)
	{
		this.attributeCodePk = attributeCodePk;
	}

	public String getArticleFilename()
	{
		return articleFilename;
	}

	public void setArticleFilename(String articleFilename)
	{
		this.articleFilename = articleFilename;
	}

}
