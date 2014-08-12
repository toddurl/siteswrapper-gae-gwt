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
package com.urlisit.siteswrapper.cloud.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.urlisit.siteswrapper.cloud.factory.Factory;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.widgets.MainMenu;
import com.urlisit.siteswrapper.cloud.widgets.Selector;
import com.urlisit.siteswrapper.cloud.widgets.ViewPanel;

/**
 * @author url
 *
 */
public interface View extends IsWidget, RequiresResize {
  Widget asWidget();
  void onResize();
  View createView();
  String getTitle();
  int getNumber();
  void setNumber(int number);
  ViewPanel getPanel();
  Selector getMenu();
  Site getSite();
  Page getPage();
  Style getStyle();
  LookAndFeel getLookAndFeel();
  Literals getLiterals();
  Factory getFactory();
  void unLoad(Presenter presenter, View view);
}
