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

/**
 *
 * @author jlaw
 */
public class ComponentEvaluation {
    private String componentReviewId;
    private String componentId;
    private String reviewStatusCode;

    /**
     * @return the componentReviewId
     */
    public String getComponentReviewId() {
        return componentReviewId;
    }

    /**
     * @param componentReviewId the componentReviewId to set
     */
    public void setComponentReviewId(String componentReviewId) {
        this.componentReviewId = componentReviewId;
    }

    /**
     * @return the componentId
     */
    public String getComponentId() {
        return componentId;
    }

    /**
     * @param componentId the componentId to set
     */
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    /**
     * @return the reviewStatusCode
     */
    public String getReviewStatusCode() {
        return reviewStatusCode;
    }

    /**
     * @param reviewStatusCode the reviewStatusCode to set
     */
    public void setReviewStatusCode(String reviewStatusCode) {
        this.reviewStatusCode = reviewStatusCode;
    }
}