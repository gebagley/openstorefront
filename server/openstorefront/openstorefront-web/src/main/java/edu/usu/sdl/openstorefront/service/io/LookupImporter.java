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
package edu.usu.sdl.openstorefront.service.io;

import au.com.bytecode.opencsv.CSVReader;
import edu.usu.sdl.openstorefront.service.manager.DBManager;
import edu.usu.sdl.openstorefront.service.manager.FileSystemManager;
import edu.usu.sdl.openstorefront.service.manager.Initializable;
import edu.usu.sdl.openstorefront.storage.model.ApplicationProperty;
import edu.usu.sdl.openstorefront.storage.model.LookupEntity;
import edu.usu.sdl.openstorefront.util.Convert;
import edu.usu.sdl.openstorefront.util.ServiceUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dshurtleff
 */
public class LookupImporter
		extends BaseDirImporter
		implements Initializable
{

	private static final Logger log = Logger.getLogger(LookupImporter.class.getName());

	private static final int CODE = 0;
	private static final int DESCRIPTION = 1;
	private static final int DETAILED_DESCRIPTION = 2;
	private static final int SORT_ORDER = 3;

	@Override
	public void initialize()
	{
		String lastSyncDts = serviceProxy.getSystemService().getPropertyValue(ApplicationProperty.LOOKUP_IMPORTER_LAST_SYNC_DTS);
		if (lastSyncDts == null) {
			//get the files and process.

			List<File> lookupCodeFiles = new ArrayList<>();

			Collection<Class<?>> entityClasses = DBManager.getConnection().getEntityManager().getRegisteredEntities();
			for (Class entityClass : entityClasses) {
				if (ServiceUtil.LOOKUP_ENTITY.equals(entityClass.getSimpleName()) == false) {
					if (ServiceUtil.isSubLookupEntity(entityClass)) {
						File codeFile = FileSystemManager.getImportLookup(entityClass.getSimpleName() + ".csv");
						if (codeFile.exists()) {
							lookupCodeFiles.add(codeFile);
						}
					}
				}
			}

			filesUpdatedOrAdded((File[]) lookupCodeFiles.toArray(new File[0]));
		} else {
			//Put in defaults, if needed
			Collection<Class<?>> entityClasses = DBManager.getConnection().getEntityManager().getRegisteredEntities();
			for (Class entityClass : entityClasses) {
				if (ServiceUtil.LOOKUP_ENTITY.equals(entityClass.getSimpleName()) == false) {
					if (ServiceUtil.isSubLookupEntity(entityClass)) {
						FileSystemManager.getImportLookup(entityClass.getSimpleName() + ".csv");
					}
				}
			}
		}
	}

	@Override
	public void shutdown()
	{
	}

	@Override
	protected String getSyncProperty()
	{
		return ApplicationProperty.LOOKUP_IMPORTER_LAST_SYNC_DTS;
	}

	@Override
	protected void processFile(File file)
	{
		//log
		log.log(Level.INFO, MessageFormat.format("Syncing lookup: {0}", file));

		//parse
		List<LookupEntity> lookupEntities = new ArrayList<>();
		String className = file.getName().replace(".csv", "");
		Class lookupClass = null;
		try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file)))) {

			lookupClass = Class.forName(DBManager.ENTITY_MODEL_PACKAGE + "." + className);
			List<String[]> allData = reader.readAll();
			for (String data[] : allData) {
				if (data.length > DESCRIPTION) {

					LookupEntity lookupEntity = (LookupEntity) lookupClass.newInstance();
					lookupEntity.setCode(data[CODE].trim().toUpperCase());
					lookupEntity.setDescription(data[DESCRIPTION].trim());

					if (data.length > DETAILED_DESCRIPTION) {
						lookupEntity.setDetailedDecription(data[DETAILED_DESCRIPTION].trim());
					}
					if (data.length > SORT_ORDER) {
						lookupEntity.setSortOrder(Convert.toInteger(data[SORT_ORDER].trim()));
					}

					lookupEntities.add(lookupEntity);
				} else {
					log.log(Level.WARNING, MessageFormat.format("(Missing Required Fields:  Code, Description) Unable Process line: {0} in file: {1}", new Object[]{Arrays.toString(data), file}));
				}
			}

		} catch (IOException ex) {
			log.log(Level.SEVERE, "Unable to read file: " + file, ex);
		} catch (ClassNotFoundException ex) {
			log.log(Level.SEVERE, "Unable to find Lookup Class for file:  " + file, ex);
		} catch (InstantiationException ex) {
			log.log(Level.SEVERE, "System error on:  " + file, ex);
		} catch (IllegalAccessException ex) {
			log.log(Level.SEVERE, "System error on:   " + file, ex);
		}

		//call sync
		if (lookupClass != null) {
			serviceProxy.getLookupService().syncLookupImport(lookupClass, lookupEntities);
		}
	}

}
