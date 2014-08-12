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
package com.urlisit.siteswrapper.cloud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.urlisit.siteswrapper.cloud.factory.Factory;
import com.urlisit.siteswrapper.cloud.model.DTO;
import com.urlisit.siteswrapper.cloud.model.Page;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SitesWrapper implements EntryPoint {

  private Factory factory = GWT.create(Factory.class);

  public void onModuleLoad() {
    AsyncCallback<DTO> configuration = new AsyncCallback<DTO>() {
      public void onFailure(Throwable caught) { Window.alert(factory.getLiterals().rpcErrorMessage()); }
      @Override
      public void onSuccess(DTO pages) {
        for (Page page: pages.getPages()) {
          factory.newView(factory.getPresenter(), factory.getSelector(), page, factory.getSite(), factory.getStyle(),
              factory.getLiterals());
        }
        if (History.getToken().length() == 0) {
          History.newItem(factory.getSite().getDefaultPage(), false);
        }
        Window.setMargin("0px");
        Window.setTitle(History.getToken());
        History.fireCurrentHistoryState();
      }
    };
    RootPanel.getBodyElement().getStyle().setBackgroundColor(factory.getStyle().getPrimaryColor());
    factory.getRpc().getDTO(configuration);
  }
}
