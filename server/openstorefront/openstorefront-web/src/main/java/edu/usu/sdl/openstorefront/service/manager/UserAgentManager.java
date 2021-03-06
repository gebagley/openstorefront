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
package edu.usu.sdl.openstorefront.service.manager;

import net.sf.ehcache.Element;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 * Handles parsing of the user agent string
 *
 * @author dshurtleff
 */
public class UserAgentManager
		implements Initializable
{

	private static UserAgentStringParser parser;

	public static void init()
	{
		parser = UADetectorServiceFactory.getCachingAndUpdatingParser();
	}

	public static void cleanup()
	{
		parser.shutdown();
	}

	public static String getDataVersion()
	{
		return parser.getDataVersion();
	}

	public static ReadableUserAgent parse(String userAgentString)
	{
		ReadableUserAgent readableUserAgent;
		Element result = OSFCacheManager.getUserAgentCache().get(userAgentString);
		if (result == null) {

			readableUserAgent = parser.parse(userAgentString);
			Element element = new Element(userAgentString, readableUserAgent);
			OSFCacheManager.getUserAgentCache().put(element);
		} else {
			readableUserAgent = (ReadableUserAgent) result.getObjectValue();
		}
		return readableUserAgent;
	}

	@Override
	public void initialize()
	{
		UserAgentManager.init();
	}

	@Override
	public void shutdown()
	{
		UserAgentManager.cleanup();
	}

}
