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
package edu.usu.sdl.openstorefront.web.action.test;

import edu.usu.sdl.openstorefront.service.query.QueryByExample;
import edu.usu.sdl.openstorefront.storage.model.ComponentEvaluationSection;
import edu.usu.sdl.openstorefront.storage.model.ComponentEvaluationSectionPk;
import edu.usu.sdl.openstorefront.storage.model.TestEntity;
import static edu.usu.sdl.openstorefront.web.action.test.BaseTestAction.TEST_USER;
import edu.usu.sdl.openstorefront.web.test.BaseTestCase;
import edu.usu.sdl.openstorefront.web.test.TestSuiteModel;
import java.util.Arrays;
import java.util.List;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;

/**
 * Lookup Service Test
 *
 * @author dshurtleff
 */
public class LookupServiceTest
		extends BaseTestAction
{

	@HandlesEvent("Find")
	public Resolution findTest()
	{
		TestSuiteModel testSuiteModel = new TestSuiteModel();
		testSuiteModel.setName("Lookup  Service Tests");

		//save some records - active, inactive
		testSuiteModel.getTests().add(new BaseTestCase(testServiceProxy())
		{

			@Override
			public String description()
			{
				return "Save Test";
			}

			@Override
			protected void runInternalTest()
			{
				Arrays.asList("A", "B").forEach(item -> {
					TestEntity testEntity = new TestEntity();
					testEntity.setCode(item);
					testEntity.setDescription(item + " - Description");
					testEntity.setActiveStatus(TestEntity.ACTIVE_STATUS);
					testEntity.setCreateUser(TEST_USER);
					testEntity.setUpdateUser(TEST_USER);

					service.getLookupService().saveLookupValue(testEntity);
				});
				results.append("Saved A, B").append("<br>");

				Arrays.asList("C", "D").forEach(item -> {
					TestEntity testEntity = new TestEntity();
					testEntity.setCode(item);
					testEntity.setDescription(item + " - Description");
					testEntity.setActiveStatus(TestEntity.INACTIVE_STATUS);
					testEntity.setCreateUser(TEST_USER);
					testEntity.setUpdateUser(TEST_USER);

					service.getLookupService().saveLookupValue(testEntity);
				});
				results.append("Saved C, D").append("<br>");

			}
		});

		//save
		testSuiteModel.getTests().add(new BaseTestCase(testServiceProxy())
		{
			@Override
			public String description()
			{
				return "Find (Query) Test";
			}

			@Override
			protected void runInternalTest()
			{
				results.append("Active").append("<br>");
				List<TestEntity> testActiveRecords = testServiceProxy().getLookupService().findLookup(TestEntity.class);
				testActiveRecords.stream().forEach(record -> {
					results.append(String.join("-", record.getCode(), record.getDescription())).append("<br>");
				});
				results.append("Check All").append("<br>");
				List<TestEntity> testInActiveRecords = testServiceProxy().getLookupService().findLookup(TestEntity.class, null);
				if (testInActiveRecords.size() == testActiveRecords.size()) {
					failureReason.append("All return the same count and active.");
				} else {
					results.append("Pass").append("<br>");
					success = true;
				}
			}
		});

		//clean up
		testSuiteModel.runAllTests();
		return sendReport(testSuiteModel);
	}

	@HandlesEvent("FindPK")
	public Resolution findTestPk()
	{
		TestSuiteModel testSuiteModel = new TestSuiteModel();
		testSuiteModel.setName("PK Tests");

		//save some records - active, inactive
		testSuiteModel.getTests().add(new BaseTestCase(testServiceProxy())
		{

			@Override
			public String description()
			{
				return "Checking PK";
			}

			@Override
			protected void runInternalTest()
			{
				ComponentEvaluationSectionPk componentEvaluationSectionPk = new ComponentEvaluationSectionPk();
				componentEvaluationSectionPk.setEvaulationSection("TEST");
				componentEvaluationSectionPk.setComponentId("883045");

				ComponentEvaluationSection section = service.getPersistenceService().findById(ComponentEvaluationSection.class, componentEvaluationSectionPk);

				ComponentEvaluationSection sectionExample = new ComponentEvaluationSection();
				sectionExample.setComponentEvaluationSectionPk(componentEvaluationSectionPk);
				service.getPersistenceService().queryByExample(ComponentEvaluationSection.class, new QueryByExample(sectionExample));

				results.append("Pass").append("<br>");
				success = true;
			}

		});

		//clean up
		testSuiteModel.runAllTests();
		return sendReport(testSuiteModel);
	}

}
