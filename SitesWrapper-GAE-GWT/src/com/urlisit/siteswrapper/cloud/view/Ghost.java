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

import java.util.Iterator;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.urlisit.siteswrapper.cloud.factory.Factory;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.DisplayLogoAs;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.widgets.Selector;
import com.urlisit.siteswrapper.cloud.widgets.OldBackgroundImage;
import com.urlisit.siteswrapper.cloud.widgets.BackgroundImage;
import com.urlisit.siteswrapper.cloud.widgets.ImageLogo;
import com.urlisit.siteswrapper.cloud.widgets.Logo;
import com.urlisit.siteswrapper.cloud.widgets.LogoHtml;
import com.urlisit.siteswrapper.cloud.widgets.LogoImage;
import com.urlisit.siteswrapper.cloud.widgets.OldMenu;
import com.urlisit.siteswrapper.cloud.widgets.MessageGhost;
import com.urlisit.siteswrapper.cloud.widgets.Pogo;
import com.urlisit.siteswrapper.cloud.widgets.PogoImage;
import com.urlisit.siteswrapper.cloud.widgets.ViewPanel;

/**
 * Composite view which implements the Ghost Look And Feel
 * 
 * @author Todd Url
 */
public class Ghost extends Composite implements View {
  
  private Presenter presenter;
  //private Menu menu;
  private Selector selector;
  private Page page;
  private Site site;
  private Style style;
  private Literals literals;
  private LookAndFeel lookAndFeel;
  private Factory factory;
  private int number;
  private ViewPanel panel;
  private BackgroundImage background;
  //private Logo logo;
  private Pogo logo;
  private MessageGhost message;
  
  public Ghost(Presenter presenter, Selector selector, Page page, Site site, Style style, Literals literals, Factory factory) {
    this.presenter = presenter;
    this.selector = selector;
    this.page = page;
    this.site = site;
    this.style = style;
    this.literals = literals;
    this.lookAndFeel = site.getLookAndFeel();
    this.factory = factory;
    initWidget(new ViewPanel());
  }

  @Override
  protected void initWidget(Widget widget) {
    panel = (ViewPanel) widget;
    number = presenter.bindView(this);
    background = new BackgroundImage(this);
    background.setTitle(page.getPageName());
    //logo = page.getDisplayLogoAs().equals(DisplayLogoAs.IMAGE) ? new LogoImage(this) : new LogoHtml(this); 
    logo = new PogoImage(this);
    logo.setFadeEnabled(true);
    logo.setFadeScheduleDelay(0);
    logo.setTitle(page.getPageName() + " logo");
    message = new MessageGhost(this);
    selector.addEntry(this);
    selector.setTitle("Selector");
    super.initWidget(panel);
  }

  @Override
  public void onResize() {
    panel.onResize();
    selector.onResize();
  }

  /*
  @Override
  public void resize() {
    panel.resize();
    background.onResize();
    menu.onResize(number);
    logo.onResize();
    message.resize();
  }
  */
  
  private void style() {
    panel.getElement().getStyle().setBorderWidth(literals.zero(), Unit.PX);
    panel.getElement().getStyle().setMargin(literals.zero(), Unit.PX);
    panel.getElement().getStyle().setPadding(literals.zero(), Unit.PX);
    selector.setCellPadding(literals.zero());
    selector.setCellSpacing(literals.zero());
  }

  @Override
  public void unLoad(final Presenter presenter, final View view) {
    if (isAttached()) {
      background.fadeOut(presenter, view);
    } else {
      presenter.loadView(this);
    }
  }

  @Override
  public void onLoad() {
    Window.setTitle(getTitle());
    style();
    onResize();
    background.fadeIn();
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
  public void setNumber(int number) {
    this.number = number;
  }

  @Override
  public Selector getMenu() {
    return selector;
  }

  @Override
  public ViewPanel getPanel() {
    return panel;
  }
 
  @Override
  public Site getSite() {
    return site;
  }

  @Override
  public Page getPage() {
    return page;
  }
 
  @Override
  public Style getStyle() {
    return style;
  }
 
  @Override
  public LookAndFeel getLookAndFeel() {
    return lookAndFeel;
  }
 
  @Override
  public Literals getLiterals() {
    return literals;
  }

  @Override
  public Factory getFactory() {
    return factory;
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public View createView() {
    // TODO Auto-generated method stub
    return null;
  }

}