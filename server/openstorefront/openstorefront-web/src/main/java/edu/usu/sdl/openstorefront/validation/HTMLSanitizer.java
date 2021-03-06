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
package edu.usu.sdl.openstorefront.validation;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * The sanitizes HTML to prevent XSS attacks This will allow structure....but no
 * scripting and it will set the no follow
 *
 * @author dshurtleff
 */
public class HTMLSanitizer
		extends Sanitizer
{

	@Override
	public Object santize(Object fieldData)
	{
		if (fieldData == null) {
			return fieldData;
		} else {
			String safe = Jsoup.clean(fieldData.toString(), new Whitelist().preserveRelativeLinks(true)
					.addTags(
							"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
							"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
							"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
							"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
							"ul")
					.addAttributes("a", "href", "title")
					.addAttributes("blockquote", "cite")
					.addAttributes("col", "span", "width")
					.addAttributes("colgroup", "span", "width")
					.addAttributes("img", "align", "alt", "height", "src", "title", "width")
					.addAttributes("ol", "start", "type")
					.addAttributes("q", "cite")
					.addAttributes("table", "summary", "width")
					.addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
					.addAttributes(
							"th", "abbr", "axis", "colspan", "rowspan", "scope",
							"width")
					.addAttributes("ul", "type")
					.addProtocols("a", "href", "ftp", "http", "https", "mailto")
					.addProtocols("blockquote", "cite", "http", "https")
					.addProtocols("cite", "cite", "http", "https")
					.addProtocols("q", "cite", "http", "https")
					.addTags("component-list")
					.addTags("span")
					.addAttributes("component-list", "hide-more", "click-callback", "class-list", "title", "data", "cols", "type", "filters", "set-filters", "code")
					.addAttributes("a", "ng-click", "id")
					.addAttributes(":all", "style")
					.addEnforcedAttribute("a", "rel", "nofollow")
			);
			return safe;
		}
	}

}
