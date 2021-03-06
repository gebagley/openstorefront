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
import edu.usu.sdl.openstorefront.storage.model.Component;
import edu.usu.sdl.openstorefront.storage.model.UserWatch;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dshurtleff
 */
public class UserWatchView
{
	@NotNull
	private String watchId;
	
	@NotNull
	private Date lastUpdateDts;
	
	@NotNull
	private Date lastViewDts;
	
	@NotNull	
	private Date createDts;
	
	@NotNull
	private String componentName;
	
	@NotNull
	@ConsumeField
	private String componentId;

	@NotNull
	@ConsumeField
	private Boolean notifyFlg;
	
	public UserWatchView()
	{
	}
	
	public static UserWatchView toView(UserWatch watch, Component component)
	{
		UserWatchView view = new UserWatchView();
		view.setComponentId(watch.getComponentId());
		view.setComponentName(component.getName());
		view.setCreateDts(watch.getCreateDts());
		view.setLastUpdateDts(component.getLastActivityDts());
		view.setLastViewDts(watch.getLastViewDts());
		view.setNotifyFlg(watch.getNotifyFlg());
		view.setWatchId(watch.getUserWatchId());
		return view;
	}

	public String getWatchId()
	{
		return watchId;
	}

	public void setWatchId(String watchId)
	{
		this.watchId = watchId;
	}

	public Date getLastUpdateDts()
	{
		return lastUpdateDts;
	}

	public void setLastUpdateDts(Date lastUpdateDts)
	{
		this.lastUpdateDts = lastUpdateDts;
	}

	public Date getLastViewDts()
	{
		return lastViewDts;
	}

	public void setLastViewDts(Date lastViewDts)
	{
		this.lastViewDts = lastViewDts;
	}

	public Date getCreateDts()
	{
		return createDts;
	}

	public void setCreateDts(Date createDts)
	{
		this.createDts = createDts;
	}

	public String getComponentName()
	{
		return componentName;
	}

	public void setComponentName(String componentName)
	{
		this.componentName = componentName;
	}

	public String getComponentId()
	{
		return componentId;
	}

	public void setComponentId(String componentId)
	{
		this.componentId = componentId;
	}

	/**
	 * @return the notifyFlg
	 */
	public Boolean getNotifyFlg()
	{
		return notifyFlg;
	}

	/**
	 * @param notifyFlg the notifyFlg to set
	 */
	public void setNotifyFlg(Boolean notifyFlg)
	{
		this.notifyFlg = notifyFlg;
	}
	
}
