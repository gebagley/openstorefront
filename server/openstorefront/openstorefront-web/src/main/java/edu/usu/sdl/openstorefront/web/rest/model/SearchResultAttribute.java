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

import edu.usu.sdl.openstorefront.storage.model.ComponentAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dshurtleff
 */
public class SearchResultAttribute
{

	private String type;
	private String code;

	public SearchResultAttribute()
	{
	}

	public static SearchResultAttribute toView(ComponentAttribute attribute)
	{
		SearchResultAttribute view = new SearchResultAttribute();
		view.setCode(attribute.getComponentAttributePk().getAttributeCode());
		view.setType(attribute.getComponentAttributePk().getAttributeType());
		return view;
	}

	public static List<SearchResultAttribute> toViewList(List<ComponentAttribute> attributes)
	{
		List<SearchResultAttribute> list = new ArrayList<>();
		attributes.stream().forEach((attribute) -> {
			list.add(SearchResultAttribute.toView(attribute));
		});
		return list;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

}
