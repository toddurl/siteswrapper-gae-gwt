/* Copyright 2014 URL IS/IT
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
package com.urlisit.siteswrapper.cloud.factory;

import com.urlisit.siteswrapper.cloud.client.RpcAsync;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.view.View;
import com.urlisit.siteswrapper.cloud.widgets.Logo;
import com.urlisit.siteswrapper.cloud.widgets.Selector;

/**
 * Provides a factory for dependency injection via GWT deferred binding
 * 
 * @author Todd Url
 * @version 1.0
 */
public interface Factory {
  Factory getFactory();
  Literals getLiteral();
  Literals getLiterals();
  Site getSite();
  Style getStyle();
  RpcAsync getRpc();
  Presenter getPresenter();
  Selector getSelector();
  Logo newLogo(View view);
  View newView(Presenter presenter, Selector selector, Page page, Site site, Style style, Literals literals);
}
