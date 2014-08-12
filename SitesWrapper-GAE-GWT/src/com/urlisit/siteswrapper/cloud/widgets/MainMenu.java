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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class MainMenu extends Composite {
  
  private final FlexTable menu = new FlexTable();
  private final List<Entry> entries = new ArrayList<Entry>();

  /**
   * 
   */
  public MainMenu() {
    initWidget(menu);
  }
  
  public void addEntry(View view) {
    String html;
    SafeHtml selection;
    SafeHtml selected;
    if (view.getPage().getMainMenuSelectionHtml().equals(view.getLiterals().undefined())) {
      html = view.getLookAndFeel().getMainMenuSelectionHtml();
      html = html.replace(view.getLiterals().FONT_FAMILY(), view.getStyle().getMainMenuFontFamily());
      html = html.replace(view.getLiterals().FONT_SIZE(), view.getStyle().getMainMenuFontSize());
      html = html.replace(view.getLiterals().FONT_COLOR(), view.getStyle().getMainMenuSelectionFontColor());
      html = html.replace(view.getLiterals().HOVER_COLOR(), view.getStyle().getMainMenuHoverFontColor());
      html = html.replace(view.getLiterals().PAGE_NAME(), view.getPage().getPageName());
    } else {
      html = view.getPage().getMainMenuSelectionHtml();
    }
    selection = new SafeHtmlBuilder().appendHtmlConstant(html).toSafeHtml();
    if (view.getPage().getMainMenuSelectedHtml().equals(view.getLiterals().undefined())) {
      html = view.getLookAndFeel().getMainMenuSelectedHtml();
      html = html.replace(view.getLiterals().FONT_FAMILY(), view.getStyle().getMainMenuFontFamily());
      html = html.replace(view.getLiterals().FONT_SIZE(), view.getStyle().getMainMenuFontSize());
      html = html.replace(view.getLiterals().FONT_COLOR(), view.getStyle().getMainMenuSelectedFontColor());
      html = html.replace(view.getLiterals().HOVER_COLOR(), view.getStyle().getMainMenuHoverFontColor());
      html = html.replace(view.getLiterals().PAGE_NAME(), view.getPage().getPageName());
    } else {
      html = view.getPage().getMainMenuSelectedHtml();
    }
    selected = new SafeHtmlBuilder().appendHtmlConstant(html).toSafeHtml();
    entries.add(view.getNumber(), new Entry(view, selection, selected, view.getTitle()));
    menu.setWidget(view.getLiterals().zero(), view.getNumber(), entries.get(view.getNumber()).getLink());
    entries.get(view.getNumber()).getView().getPanel().add(this);
  }
  
  public void onResize(int viewNumber) {
    String title = Window.getTitle();
    Literals literals = entries.get(viewNumber).getView().getLiterals();
    LookAndFeel lookAndFeel = entries.get(viewNumber).getView().getLookAndFeel();
    ViewPanel panel = entries.get(viewNumber).getView().getPanel();
    int numCells = menu.getCellCount(literals.rowZero());
    double menuWidth = Window.getClientWidth() - (Window.getClientWidth() * Double.valueOf(lookAndFeel.getMainMenuLeft()));
    int cellWidth = (int) Math.round(menuWidth / numCells);
    for (int x = 0; x < numCells; x++) {
      menu.getCellFormatter().setWidth(literals.rowZero(), x, cellWidth + literals.px());
      menu.getCellFormatter().setHorizontalAlignment(literals.zero(), x, HasHorizontalAlignment.ALIGN_LEFT);
    }
    int left = (int) Math.round(Window.getClientWidth() * Double.valueOf(lookAndFeel.getMainMenuLeft()));
    int top = (int) Math.round(Double.valueOf(lookAndFeel.getMainMenuTopPercent()) * Window.getClientHeight());
    panel.add(this, left, top);
    panel.onResize();
  }

  public int getCellCount(int row) {
    return menu.getCellCount(row);
  }
  
  public FlexTable.FlexCellFormatter getCellFormatter() {
    return menu.getFlexCellFormatter();
  }

  public void setCellPadding(int i) {
    menu.setCellPadding(i);
  }

  public void setCellSpacing(int i) {
    menu.setCellSpacing(i);
  }
  
  public void setEntrySelected(int viewNumber) {
    entries.get(viewNumber).getLink().setHTML(entries.get(viewNumber).getSelected());
  }
  
  public void setEntryNotSelected(int viewNumber) {
    entries.get(viewNumber).getLink().setHTML(entries.get(viewNumber).getSelection());
  }

}