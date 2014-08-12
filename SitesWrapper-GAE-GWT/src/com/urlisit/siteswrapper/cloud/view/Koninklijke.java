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

import java.math.BigDecimal;
import java.math.MathContext;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.urlisit.siteswrapper.cloud.event.ImageLoadEvent;
import com.urlisit.siteswrapper.cloud.event.ImageLoadHandler;
import com.urlisit.siteswrapper.cloud.factory.Factory;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.utilities.UrleLoader;
import com.urlisit.siteswrapper.cloud.widgets.MainMenu;
import com.urlisit.siteswrapper.cloud.widgets.OldBackgroundImage;
import com.urlisit.siteswrapper.cloud.widgets.OldMenu;
import com.urlisit.siteswrapper.cloud.widgets.Selector;
import com.urlisit.siteswrapper.cloud.widgets.ViewPanel;

/**
 * Implements a View reminiscent of Philips Consumer Electronics
 * 
 * @author Todd Url
 */
public class Koninklijke extends Composite implements View {
  
  private Page page;
  private Site site;
  private Style style;
  private Presenter presenter;
  private OldMenu mainMenu;
  private Literals literals;
  private LookAndFeel lookAndFeel;
  private int number;
  private AbsolutePanel viewPanel;
  private Image backgroundImage;
  private OldBackgroundImage bgImage;
  private boolean backgroundImageLoaded;
  private Timer timer;
  private int timerInterval = 25;
  
  public Koninklijke(Page page, Site site, Style style, Presenter presenter, OldMenu mainMenu, Literals literals) {
    this.page = page;
    this.site = site;
    this.style = style;
    this.presenter = presenter;
    this.mainMenu = mainMenu;
    this.literals = literals;
    this.lookAndFeel = site.getLookAndFeel();
    initWidget(new AbsolutePanel());
  }

  @Override
  protected void initWidget(Widget widget) {
    presenter.bindView(this);
    viewPanel = (AbsolutePanel) widget;
    viewPanel.setPixelSize(Window.getClientWidth(), Window.getClientHeight());
    viewPanel.getElement().getStyle().setPosition(Position.RELATIVE);
    super.initWidget(viewPanel);
  }

  @Override
  public void unLoad(final Presenter presenter, final View view) {
    if (isAttached()) {
      bgImage.fadeOut(presenter, view);
    } else {
      presenter.loadView(this);
    }
  }

  @Override
  public void onLoad() {
    Window.setTitle(getTitle());
    onResize();
    bgImage.fadeIn();
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
    this.number = number;
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
