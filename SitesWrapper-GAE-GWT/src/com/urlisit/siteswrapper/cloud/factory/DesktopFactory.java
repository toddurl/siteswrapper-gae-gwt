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

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.urlisit.siteswrapper.cloud.client.Rpc;
import com.urlisit.siteswrapper.cloud.client.RpcAsync;
import com.urlisit.siteswrapper.cloud.model.DisplayLogoAs;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.presenter.URLePresenter;
import com.urlisit.siteswrapper.cloud.view.Ghost;
import com.urlisit.siteswrapper.cloud.view.View;
import com.urlisit.siteswrapper.cloud.view.Koninklijke;
import com.urlisit.siteswrapper.cloud.view.UrlIsIt;
import com.urlisit.siteswrapper.cloud.widgets.Logo;
import com.urlisit.siteswrapper.cloud.widgets.LogoHtml;
import com.urlisit.siteswrapper.cloud.widgets.LogoImage;
import com.urlisit.siteswrapper.cloud.widgets.LogoNone;
import com.urlisit.siteswrapper.cloud.widgets.MainMenu;
import com.urlisit.siteswrapper.cloud.widgets.OldMenu;
import com.urlisit.siteswrapper.cloud.widgets.Selector;

/**
 * Implements the Factory interface
 * 
 * @author Todd Url
 */
public class DesktopFactory implements Factory {
  
  private final Factory factory = this;

  private final Literals literal = GWT.create(Literals.class);

  private final Site site = new Site().fromDictionary(Dictionary.getDictionary(literal.site()));
 
  private final Style style = new Style().fromDictionary(Dictionary.getDictionary(literal.style()));
 
  private final RpcAsync rpc = (RpcAsync) GWT.create(Rpc.class);

  private final Presenter presenter = new URLePresenter();

  private final Selector selector = new Selector(getPresenter());
  
  private final MainMenu menu = new MainMenu();
  
  @Override
  public Factory getFactory() {
    return factory;
  }

  @Override
  public Literals getLiteral() {
    return literal;
  }

  @Override
  public Literals getLiterals() {
    return literal;
  }

  @Override
  public Site getSite() {
    return site;
  }

  @Override
  public Style getStyle() {
    return style;
  }

  @Override
  public RpcAsync getRpc() {
    return rpc;
  }

  @Override
  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  public Selector getSelector() {
    return selector;
  }
 
  @Override
  public Logo newLogo(View view) {
    switch (view.getPage().getDisplayLogoAs()) {
      case IMAGE:
        return new LogoImage(view);
      case HTML:
        return new LogoHtml(view);
      case NONE:
        return LogoNone.newInstance(view);
      default:
        assert false;
        return null;
    }
  }
  
  @Override
  public View newView(Presenter presenter, Selector selector, Page page, Site site, Style style, Literals literals) {
    switch (site.getLookAndFeel()) {
      case URL_IS_IT:
        //return new UrlIsIt(page, site, style, presenter, menu, literals);
      case KONINKLIJKE:
        //return new Koninklijke(page, site, style, presenter, menu, literals);
      case GHOST:
        return new Ghost(presenter, selector, page, site, style, literals, factory);
      default:
        return null;
    }
  }

}