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
package edu.usu.sdl.openstorefront.web.test.system;

import edu.usu.sdl.openstorefront.service.query.QueryByExample;
import edu.usu.sdl.openstorefront.storage.model.TestEntity;
import edu.usu.sdl.openstorefront.web.test.BaseTestCase;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author dshurtleff
 */
public class DBLikeTest
		extends BaseTestCase
{

	public DBLikeTest()
	{
		this.description = "Like Query";
	}

	@Override
	protected void runInternalTest()
	{
		Arrays.asList("A", "B", "Apple", "andy", "Test", "BaseTest", "Orange").forEach(item -> {
			TestEntity testEntity = new TestEntity();
			testEntity.setCode(item);
			testEntity.setDescription(item + " - Description");
			testEntity.setCreateUser(TEST_USER);
			testEntity.setUpdateUser(TEST_USER);

			service.getLookupService().saveLookupValue(testEntity);
		});
		results.append("Saved Test Data").append("<br>");

		TestEntity testEntityLikeExample = new TestEntity();
		testEntityLikeExample.setCode("A%");

		QueryByExample queryByExample = new QueryByExample(new TestEntity());
		queryByExample.setLikeExample(testEntityLikeExample);

		List<TestEntity> testEntities = service.getPersistenceService().queryByExample(TestEntity.class, queryByExample);
		results.append("Results Entities of ").append(testEntityLikeExample.getCode()).append(":<br>");
		testEntities.forEach(entity -> {
			results.append(entity.getCode()).append("<br>");
		});
		if (testEntities.size() > 2 || testEntities.size() < 2) {
			failureReason.append("Failed to find expected results");
		}

		testEntityLikeExample = new TestEntity();
		testEntityLikeExample.setCode("%ange");

		queryByExample = new QueryByExample(new TestEntity());
		queryByExample.setLikeExample(testEntityLikeExample);

		testEntities = service.getPersistenceService().queryByExample(TestEntity.class, queryByExample);
		results.append("Results Entities of ").append(testEntityLikeExample.getCode()).append(": ").append("<br>");
		testEntities.forEach(entity -> {
			results.append(entity.getCode()).append("<br>");
		});
		if (testEntities.size() > 1 || testEntities.size() < 1) {
			failureReason.append("Failed to find expected results");
		}

		testEntityLikeExample = new TestEntity();
		testEntityLikeExample.setCode("%Test%");

		queryByExample = new QueryByExample(new TestEntity());
		queryByExample.setLikeExample(testEntityLikeExample);

		testEntities = service.getPersistenceService().queryByExample(TestEntity.class, queryByExample);
		results.append("Results Entities of ").append(testEntityLikeExample.getCode()).append(": ").append("<br>");
		testEntities.forEach(entity -> {
			results.append(entity.getCode()).append("<br>");
		});
		if (testEntities.size() > 2 || testEntities.size() < 2) {
			failureReason.append("Failed to find expected results");
		}

		results.append("Clean up records").append("<br>");
		results.append(service.getPersistenceService().deleteByExample(new TestEntity())).append(" records removed.<br>");
	}

}
