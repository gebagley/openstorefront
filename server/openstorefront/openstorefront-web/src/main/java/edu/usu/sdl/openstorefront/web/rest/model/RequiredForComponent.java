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
import edu.usu.sdl.openstorefront.storage.model.AttributeType;
import edu.usu.sdl.openstorefront.storage.model.Component;
import edu.usu.sdl.openstorefront.storage.model.ComponentAttribute;
import edu.usu.sdl.openstorefront.validation.RuleResult;
import edu.usu.sdl.openstorefront.validation.ValidationResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jlaw
 */
public class RequiredForComponent
{

	@NotNull
	@ConsumeField
	private Component component;

	@NotNull
	@ConsumeField
	@DataType(ComponentAttribute.class)
	private List<ComponentAttribute> attributes = new ArrayList<>();

	public RequiredForComponent()
	{
	}

	public ValidationResult checkForComplete()
	{
		ValidationResult validationResult = new ValidationResult();

		ServiceProxy serviceProxy = new ServiceProxy();

		List<AttributeType> requireds = serviceProxy.getAttributeService().getRequiredAttributes();
		Set<String> requiredTypeSet = new HashSet<>();
		for (AttributeType attributeType : requireds) {
			requiredTypeSet.add(attributeType.getAttributeType());
		}

		List<String> matchedAttributes = new ArrayList<>();
		for (ComponentAttribute attribute : attributes) {
			String type = attribute.getComponentAttributePk().getAttributeType();
			if (requiredTypeSet.contains(type)) {
				if (matchedAttributes.add(type)) {
					matchedAttributes.add(type);
				}
			}
		}
		if (matchedAttributes.size() < requiredTypeSet.size()) {
			RuleResult ruleResult = new RuleResult();
			ruleResult.setMessage("Missing Required Attributes");
			ruleResult.setEntityClassName(ComponentAttribute.class.getSimpleName());
			ruleResult.setFieldName("componentAttributes");
			ruleResult.setValidationRule("Must has required attributes");
			validationResult.getRuleResults().add(ruleResult);
		}
		return validationResult;
	}

	public Component getComponent()
	{
		return component;
	}

	public void setComponent(Component component)
	{
		this.component = component;
	}

	public List<ComponentAttribute> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(List<ComponentAttribute> attributes)
	{
		this.attributes = attributes;
	}

}
