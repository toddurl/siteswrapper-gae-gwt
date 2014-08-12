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
package com.urlisit.siteswrapper.cloud.presenter;

import java.util.Map;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * Concrete implimentation of the Presenter interface.
 * 
 * @author Todd Url
 */
public final class URLePresenter implements Presenter {
  
  public URLePresenter() {
    Window.addResizeHandler(this);
    History.addValueChangeHandler(this);
  }
  
  @Override
  public void onValueChange(ValueChangeEvent<String> event) {
    /*
     * Handle events from a click in the MainMenu
     */
    for (String viewTitle : views.keySet()) {
      if (event.getValue().equals(viewTitle)) {
        getView(Window.getTitle()).getMenu().setEntryNotSelected(Window.getTitle());
        getView(viewTitle).getMenu().setEntrySelected(viewTitle);
        getView(Window.getTitle()).unLoad(this, getView(viewTitle));
      }
    }
  }

  @Override
  public void loadView(View view) {
    RootPanel.get().clear();
    RootPanel.get().add(view.asWidget());
  }

  @Override
  public int bindView(View view) {
    views.put(view.getTitle(), view);
    view.setNumber(views.size() - 1);
    return views.size() - 1;
  }

  @Override
  public View getView(String title) {
    return views.get(title);
  }

  @Override
  public Map<String, View> getViews() {
    return views;
  }

  @Override
  public void onResize(ResizeEvent event) {
    //getView(Window.getTitle()).getMenu().onResize(getView(Window.getTitle()).getNumber());
    getView(Window.getTitle()).onResize();
    //getView(Window.getTitle()).getMenu().onResize();
  }

}