/* Copyright 2011 URL IS/IT
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you  may  not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless  required  by  applicable  law  or  agreed  to  in  writing,  software
 * distributed under the License is distributed on an  "AS  IS"  BASIS,  WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either  express  or  implied.  See  the
 * License for the specific language governing permissions and limitations under
 * the License. */
package com.urlisit.siteswrapper.cloud.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * @author	Todd Url <toddurl @ yahoo.com>
 * @version	1.0
 * @since	10-31-2011
 */
public interface Resources extends ClientBundle {
	Resources resources = GWT.create(Resources.class);
	@Source("../resources/Css.css")
	public CssResource Css();
	
	@Source("../resources/searchButton.png")
	public ImageResource searchButton();
	
	@Source("../resources/mainMenuSprite.png")
	public ImageResource mainMenuSprite();
}