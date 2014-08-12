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

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RequiresResize;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public final class Selector extends Composite implements RequiresResize {

  private final Presenter presenter;
  private final FlexTable mainMenu = new FlexTable();
  private final Map<String, Entry> entries = new LinkedHashMap<String, Entry>();
  
  /**
   * 
   */
  public Selector(Presenter presenter) {
    this.presenter = presenter;
    initWidget(mainMenu);
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
    entries.put(view.getTitle(), new Entry(view, selection, selected, view.getTitle()));
    mainMenu.setWidget(view.getLiterals().zero(), view.getNumber(), entries.get(view.getTitle()).getLink());
    mainMenu.getElement().getStyle().setVisibility(Visibility.VISIBLE);
    mainMenu.getElement().getStyle().setOpacity(1);
    view.getPanel().add(this);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.RequiresResize#onResize()
   */
  @Override
  public void onResize() {
    Literals literals = presenter.getView(Window.getTitle()).getLiterals();
    LookAndFeel lookAndFeel = presenter.getView(Window.getTitle()).getLookAndFeel();
    ViewPanel panel = presenter.getView(Window.getTitle()).getPanel();
    int numCells = mainMenu.getCellCount(literals.rowZero());
    double width = Window.getClientWidth() - (Window.getClientWidth() * Double.valueOf(lookAndFeel.getMainMenuLeft()));
    int cellWidth = (int) Math.round(width / numCells);
    for (int x = 0; x < numCells; x++) {
      mainMenu.getCellFormatter().setWidth(literals.rowZero(), x, cellWidth + literals.px());
      mainMenu.getCellFormatter().setHorizontalAlignment(literals.zero(), x, HasHorizontalAlignment.ALIGN_LEFT);
    }
    int left = (int) Math.round(Window.getClientWidth() * Double.valueOf(lookAndFeel.getMainMenuLeft()));
    int top = (int) Math.round(Double.valueOf(lookAndFeel.getMainMenuTopPercent()) * Window.getClientHeight());
    panel.add(this, left, top);
  }
  
  public int getCellCount(int row) {
    return mainMenu.getCellCount(row);
  }
  
  public FlexTable.FlexCellFormatter getCellFormatter() {
    return mainMenu.getFlexCellFormatter();
  }

  public void setCellPadding(int i) {
    mainMenu.setCellPadding(i);
  }

  public void setCellSpacing(int i) {
    mainMenu.setCellSpacing(i);
  }
  
  public void setTitle(String title) {
    mainMenu.setTitle(title);
  }
  
  public String getTitle() {
    return mainMenu.getTitle();
  }
  
  public void setEntrySelected(String title) {
    entries.get(title).getLink().setHTML(entries.get(title).getSelected());
  }
  
  public void setEntryNotSelected(String title) {
    entries.get(title).getLink().setHTML(entries.get(title).getSelection());
  }

}
