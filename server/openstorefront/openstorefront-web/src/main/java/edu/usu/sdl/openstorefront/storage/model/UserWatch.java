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
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jlaw
 */
public class UserWatch
		extends BaseEntity
{

	@PK
	@NotNull
	private String userWatchId;

	@NotNull
	@ConsumeField
	private Date lastViewDts;

	@NotNull
	@ConsumeField
	private String componentId;

	@NotNull
	@Size(min = 1, max = OpenStorefrontConstant.FIELD_SIZE_USERNAME)
	@ConsumeField
	private String username;

	@NotNull
	@ConsumeField
	private Boolean notifyFlg;

	public UserWatch()
	{
	}

	public String getUserWatchId()
	{
		return userWatchId;
	}

	public void setUserWatchId(String userWatchId)
	{
		this.userWatchId = userWatchId;
	}

	public Date getLastViewDts()
	{
		return lastViewDts;
	}

	public void setLastViewDts(Date lastViewDts)
	{
		this.lastViewDts = lastViewDts;
	}

	public String getComponentId()
	{
		return componentId;
	}

	public void setComponentId(String componentId)
	{
		this.componentId = componentId;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Boolean getNotifyFlg()
	{
		return notifyFlg;
	}

	public void setNotifyFlg(Boolean notifyFlg)
	{
		this.notifyFlg = notifyFlg;
	}

}
