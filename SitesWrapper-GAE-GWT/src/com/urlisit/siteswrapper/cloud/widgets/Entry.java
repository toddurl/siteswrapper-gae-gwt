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

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Hyperlink;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public final class Entry {
  
  private final Hyperlink link = new Hyperlink();
  private final SafeHtml selection;
  private final SafeHtml selected;
  private final View view;
  
  /**
   * 
   */
  public Entry(View view, SafeHtml selection, SafeHtml selected, String historyToken) {
    this.view = view;
    this.selection = selection;
    this.selected = selected;
    link.setTargetHistoryToken(historyToken);
    link.setHTML(selection);
  }
  
  Hyperlink getLink() {
    return link;
  }

  SafeHtml getSelection() {
    return selection;
  }

  SafeHtml getSelected() {
    return selected;
  }
  
  /*
  AbsolutePanel getPanel() {
    return panel;
  }
  */
  
  View getView() {
    return view;
  }

}