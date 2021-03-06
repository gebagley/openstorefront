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
import edu.usu.sdl.openstorefront.util.ServiceUtil;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dshurtleff
 */
public class AttributeCodePk
		extends BasePK
{

	@NotNull
	@Size(min = 1, max = OpenStorefrontConstant.FIELD_SIZE_CODE)
	@ConsumeField
	private String attributeType;

	@NotNull
	@Size(min = 1, max = OpenStorefrontConstant.FIELD_SIZE_CODE)
	@ConsumeField
	private String attributeCode;

	public AttributeCodePk()
	{
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof AttributeCodePk)) {
			return false;
		}
		return toKey().equals(((AttributeCodePk)obj).toKey());		
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 89 * hash + Objects.hashCode(getAttributeType());
		hash = 89 * hash + Objects.hashCode(getAttributeCode());
		return hash;
	}

	@Override
	public String toString()
	{
		return toKey();
	}
	
	public String toKey()
	{
		return getAttributeType() + ServiceUtil.COMPOSITE_KEY_SEPERATOR + getAttributeCode();
	}

	public static AttributeCodePk fromKey(String key)
	{
		AttributeCodePk attributeCodePk = new AttributeCodePk();
		String keySplit[] = key.split(ServiceUtil.COMPOSITE_KEY_SEPERATOR);
		attributeCodePk.setAttributeType(keySplit[0]);
		attributeCodePk.setAttributeCode(keySplit[1]);
		return attributeCodePk;
	}

	public String getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(String attributeType)
	{
		this.attributeType = attributeType;
	}

	public String getAttributeCode()
	{
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode)
	{
		this.attributeCode = attributeCode;
	}

}
