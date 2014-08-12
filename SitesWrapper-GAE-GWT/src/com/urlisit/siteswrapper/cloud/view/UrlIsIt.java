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

import com.google.gwt.user.client.ui.Composite;
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
 * @author Todd Url
 */
public class UrlIsIt extends Composite implements View {
  
  private Presenter presenter;
  private MainMenu menu;
  private Page page;
  private Site site;
  private Style style;
  private LookAndFeel lookAndFeel;
  private Literals literals;
  private ViewPanel panel;
  private int number;
  
  public UrlIsIt(Presenter presenter, MainMenu menu, Page page, Site site, Style style, Literals literals) {
    this.presenter = presenter;
    this.menu = menu;
    this.page = page;
    this.site = site;
    this.style = style;
    this.literals = literals;
    initWidget(new ViewPanel());
  }
  
  @Override
  protected void initWidget(Widget widget) {
    panel = (ViewPanel) widget;
    number = presenter.bindView(this);
    super.initWidget(widget);
  }
  
  @Override
  public void unLoad(final Presenter presenter, final View view) {
  }

  @Override
  public void onLoad() {
  }

  @Override
  public String getTitle() {
    return page.getPageName();
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public void setNumber(int number) {
    number = number;
  }

  @Override
  public Selector getMenu() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ViewPanel getPanel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Site getSite() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Page getPage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Style getStyle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public LookAndFeel getLookAndFeel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Literals getLiterals() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Factory getFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void onResize() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public View createView() {
    // TODO Auto-generated method stub
    return null;
  }

}