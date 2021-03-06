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
package edu.usu.sdl.openstorefront.web.rest.resource;

import edu.usu.sdl.openstorefront.doc.APIDescription;
import edu.usu.sdl.openstorefront.doc.DataType;
import edu.usu.sdl.openstorefront.doc.RequireAdmin;
import edu.usu.sdl.openstorefront.doc.RequiredParam;
import edu.usu.sdl.openstorefront.exception.OpenStorefrontRuntimeException;
import edu.usu.sdl.openstorefront.service.PersistenceService;
import edu.usu.sdl.openstorefront.service.manager.DBManager;
import edu.usu.sdl.openstorefront.storage.model.LookupEntity;
import edu.usu.sdl.openstorefront.storage.model.UserTypeCode;
import edu.usu.sdl.openstorefront.util.SecurityUtil;
import edu.usu.sdl.openstorefront.util.ServiceUtil;
import edu.usu.sdl.openstorefront.validation.ValidationModel;
import edu.usu.sdl.openstorefront.validation.ValidationResult;
import edu.usu.sdl.openstorefront.validation.ValidationUtil;
import edu.usu.sdl.openstorefront.web.rest.model.FilterQueryParams;
import edu.usu.sdl.openstorefront.web.rest.model.GenericLookupEntity;
import edu.usu.sdl.openstorefront.web.viewmodel.LookupModel;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang.StringUtils;

/**
 * Lookup Entities
 *
 * @author dshurtleff
 */
@Path("v1/resource/lookuptypes")
@APIDescription("A lookup entity provide a set of valid values for a given entity. For Example: this can be used to fill drop-downs with values.")
public class LookupTypeResource
		extends BaseResource
{

	private static final Logger log = Logger.getLogger(LookupTypeResource.class.getName());

	@GET
	@APIDescription("Get a list of available lookup entities")
	@Produces({MediaType.APPLICATION_JSON})
	@DataType(LookupModel.class)
	public List<LookupModel> listEntitiies()
	{
		List<LookupModel> lookupModels = new ArrayList<>();

		Collection<Class<?>> entityClasses = DBManager.getConnection().getEntityManager().getRegisteredEntities();
		for (Class entityClass : entityClasses) {
			if (ServiceUtil.LOOKUP_ENTITY.equals(entityClass.getSimpleName()) == false) {
				if (ServiceUtil.isSubLookupEntity(entityClass)) {
					LookupModel lookupModel = new LookupModel();

					lookupModel.setCode(entityClass.getSimpleName());
					APIDescription aPIDescription = (APIDescription) entityClass.getAnnotation(APIDescription.class);
					if (aPIDescription != null) {
						lookupModel.setDescription(aPIDescription.value());
					}
					lookupModels.add(lookupModel);
				}
			}
		}
		return lookupModels;
	}

	@GET
	@APIDescription("Get a entity type codes")
	@Produces({MediaType.APPLICATION_JSON})
	@DataType(value = GenericLookupEntity.class, actualClassName = "LookupEntity")
	@Path("/{entity}")
	public List<LookupEntity> getEntityValues(
			@PathParam("entity")
			@RequiredParam String entityName,
			@BeanParam FilterQueryParams filterQueryParams)
	{
		checkEntity(entityName);

		List<LookupEntity> lookups = new ArrayList<>();
		try {
			Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
			lookups = service.getLookupService().findLookup(lookupClass, filterQueryParams.getStatus());
		} catch (ClassNotFoundException e) {
			throw new OpenStorefrontRuntimeException(" (System Issue) Unable to find entity: " + entityName, "System error...contact support.", e);
		}
		lookups = filterQueryParams.filter(lookups);
		return lookups;
	}

	@GET
	@APIDescription("Get a entity type codes.  Lighter Model for dropdown boxes")
	@Produces({MediaType.APPLICATION_JSON})
	@DataType(value = GenericLookupEntity.class, actualClassName = "LookupEntity")
	@Path("/{entity}/view")
	public List<LookupModel> getEntityValuesView(
			@PathParam("entity")
			@RequiredParam String entityName,
			@BeanParam FilterQueryParams filterQueryParams)
	{
		checkEntity(entityName);

		List<LookupModel> lookupViews = new ArrayList<>();
		try {
			Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
			List<LookupEntity> lookups = service.getLookupService().findLookup(lookupClass, filterQueryParams.getStatus());
			for (LookupEntity lookupEntity : lookups) {
				LookupModel lookupModel = new LookupModel();
				lookupModel.setCode(lookupEntity.getCode());
				lookupModel.setDescription(lookupEntity.getDescription());
				lookupViews.add(lookupModel);
			}
		} catch (ClassNotFoundException e) {
			throw new OpenStorefrontRuntimeException(" (System Issue) Unable to find entity: " + entityName, "System error...contact support.", e);
		}
		lookupViews = filterQueryParams.filter(lookupViews);
		return lookupViews;
	}

	@POST
	@RequireAdmin
	@APIDescription("Adds a new code to a given entity")
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/{entity}")
	public Response postNewEntity(
			@PathParam("entity")
			@RequiredParam String entityName,
			GenericLookupEntity genericLookupEntity)
	{
		return handlePostPutCode(entityName, genericLookupEntity, true);
	}

	private Response handlePostPutCode(String entityName, LookupEntity lookupEntity, boolean post)
	{
		checkEntity(entityName);
		ValidationModel validationModel = new ValidationModel(lookupEntity);
		validationModel.setConsumeFieldsOnly(true);
		ValidationResult validationResult = ValidationUtil.validate(validationModel);
		if (validationResult.valid()) {
			try {
				Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
				LookupEntity newLookupEntity = (LookupEntity) lookupClass.newInstance();
				newLookupEntity.setCode(lookupEntity.getCode());
				newLookupEntity.setDescription(lookupEntity.getDescription());
				newLookupEntity.setDetailedDecription(lookupEntity.getDetailedDecription());

				newLookupEntity.setActiveStatus(LookupEntity.ACTIVE_STATUS);
				newLookupEntity.setCreateUser(SecurityUtil.getCurrentUserName());
				newLookupEntity.setUpdateUser(SecurityUtil.getCurrentUserName());
				service.getLookupService().saveLookupValue(newLookupEntity);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new OpenStorefrontRuntimeException(" (System Issue) Unable to process entity. " + entityName, "System error...contact support.", e);
			}
		} else {
			return Response.ok(validationResult.toRestError()).build();
		}
		if (post) {
			LookupEntity lookupEntityCreated = service.getLookupService().getLookupEnity(entityName, lookupEntity.getCode());
			return Response.created(URI.create("v1/resource/lookuptypes/" + entityName + "/" + lookupEntity.getCode())).entity(lookupEntityCreated).build();
		} else {
			return Response.ok().build();
		}
	}

	@GET
	@APIDescription("Get a entity type detail for the given id")
	@Produces({MediaType.APPLICATION_JSON})
	@DataType(value = UserTypeCode.class, actualClassName = "LookupEntity")
	@Path("/{entity}/{code}")
	public Response getEntityValues(
			@PathParam("entity")
			@RequiredParam String entityName,
			@PathParam("code")
			@RequiredParam String code)
	{
		if (StringUtils.isBlank(code)) {
			throw new OpenStorefrontRuntimeException("Id is a required field");
		}

		LookupEntity lookupEntity = null;
		checkEntity(entityName);
		PersistenceService persistenceService = service.getPersistenceService();
		try {
			Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
			Object value = persistenceService.findById(lookupClass, code);
			if (value != null) {
				lookupEntity = (LookupEntity) persistenceService.unwrapProxyObject(lookupClass, value);
			}
		} catch (ClassNotFoundException e) {
			throw new OpenStorefrontRuntimeException("(System Issue) Unable to find entity: " + entityName, "System error...contact support.", e);
		}
		Response response;
		if (lookupEntity == null) {
			response = Response.status(Response.Status.NOT_FOUND).entity(lookupEntity).build();
		} else {
			response = Response.ok(lookupEntity).build();
		}
		return response;
	}

	@PUT
	@RequireAdmin
	@APIDescription("Updates descriptions for a given entity and code.")
	@Consumes({MediaType.APPLICATION_JSON})
	@Path("/{entity}/{code}")
	public Response updateEntityValue(
			@PathParam("entity")
			@RequiredParam String entityName,
			@PathParam("code")
			@APIDescription("Passing the code in the body is optional")
			@RequiredParam String code,
			GenericLookupEntity genericLookupEntity)
	{

		LookupEntity lookupEntity = service.getLookupService().getLookupEnity(entityName, code.toUpperCase());
		if (lookupEntity == null) {
			throw new OpenStorefrontRuntimeException("Lookup code not found", "Check code passed in. (Case-InSensitive)");
		}
		genericLookupEntity.setCode(code.toUpperCase());
		return handlePostPutCode(entityName, genericLookupEntity, false);
	}

	@DELETE
	@RequireAdmin
	@APIDescription("Remove a code from the entity")
	@Path("/{entity}/{code}")
	public void deleteEntityValue(
			@PathParam("entity")
			@RequiredParam String entityName,
			@PathParam("code")
			@RequiredParam String code)
	{
		checkEntity(entityName);
		try {
			Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
			service.getLookupService().removeValue(lookupClass, code);
		} catch (ClassNotFoundException e) {
			throw new OpenStorefrontRuntimeException("(System Issue) Unable to find entity: " + entityName, "System error...contact support.", e);
		}
	}

	private void checkEntity(String entityName)
	{
		boolean valid = false;
		if (ServiceUtil.LOOKUP_ENTITY.equals(entityName) == false) {
			try {
				Class lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + entityName);
				valid = ServiceUtil.isSubLookupEntity(lookupClass);
			} catch (ClassNotFoundException e) {
				valid = false;
			}
		}
		if (!valid) {
			throw new OpenStorefrontRuntimeException("Lookup Type not found", "Check entity name passed in. (Case-Sensitive and should be Camel-Cased)");
		}
	}

}
