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
package com.urlisit.siteswrapper.cloud.widgets;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author url
 *
 */
public class ViewPanel extends AbsolutePanel implements RequiresResize, ProvidesResize {

  /**
   * 
   */
  public ViewPanel() {
  }

  /**
   * @param elem
   */
  public ViewPanel(Element elem) {
    super(elem);
  }
  
  @Override
  public void onResize() {
    getElement().getStyle().setPosition(Position.RELATIVE);
    setPixelSize(Window.getClientWidth(), Window.getClientHeight());
    for (Widget child: getChildren()) {
      if (child instanceof RequiresResize) {
        ((RequiresResize) child).onResize();
      }
    }
  }

  public void resize(Selector selector, int left, int top) {
    add(selector, left, top);
    onResize();
  }
  
  /*
  @Override
  public void add(Widget widget) {
    if (widget instanceof Selector) {
      Window.alert("adding Selector");
    }
    super.add(widget);
  }
  */
}
