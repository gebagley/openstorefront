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
package edu.usu.sdl.openstorefront.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * Useful method dealing with time. Help keep the time handling centralized.
 *
 * @author dshurtleff
 */
public class TimeUtil
{

	private static final String OMP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static Date fromString(String value)
	{
		Date newDate = null;
		if (StringUtils.isNotBlank(value)) {
			SimpleDateFormat sdf = new SimpleDateFormat(OMP_DATE_FORMAT);
			newDate = sdf.parse(value, new ParsePosition(0));
		}
		return newDate;
	}

	public static String dateToString(Date value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(OMP_DATE_FORMAT);
		return sdf.format(value);
	}

	public static Date currentDate()
	{
		return new Date(System.currentTimeMillis());
	}

}
