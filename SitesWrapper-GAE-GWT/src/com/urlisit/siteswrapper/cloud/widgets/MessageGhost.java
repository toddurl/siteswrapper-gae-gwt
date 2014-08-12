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

import java.math.BigDecimal;
import java.math.MathContext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.urlisit.siteswrapper.cloud.client.Rpc;
import com.urlisit.siteswrapper.cloud.client.RpcAsync;
import com.urlisit.siteswrapper.cloud.model.DTO;
import com.urlisit.siteswrapper.cloud.model.Item;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class MessageGhost implements Message, ClickHandler, AttachEvent.Handler {
  
  private View view;
  private FlexTable message = new FlexTable();
  private FlexTable header = new FlexTable();
  private HTML headerText = new HTML();
  private FlexTable body = new FlexTable();
  private HTML bodyText = new HTML();
  private HTML closeBox = new HTML();
  private Button button = new Button();
  private BigDecimal leftPercent;
  private BigDecimal topPercent;
  private Timer timer;
  private RpcAsync rpc = (RpcAsync) GWT.create(Rpc.class);
  private boolean debug = false;
  
  /**
   * 
   */
  public MessageGhost(View view) {
    this.view = view;
    String primaryColor = view.getStyle().getPrimaryAccentColor();
    String secondaryColor = view.getStyle().getSecondaryAccentColor();
    String tertiaryColor = view.getStyle().getTertiaryAccentColor();
    String fontFamily = view.getStyle().getMainMenuFontFamily();
    leftPercent = BigDecimal.valueOf(Double.parseDouble(view.getPage().getMessagePercentOfPageFromLeft(0)));
    topPercent = BigDecimal.valueOf(Double.parseDouble(view.getPage().getMessagePercentOfPageFromTop(0)));
    headerText.setHTML(new SafeHtmlBuilder().appendHtmlConstant(view.getPage().getMessageHeaderText(0)).toSafeHtml());
    headerText.getElement().getStyle().setMargin(10, Unit.PX);
    closeBox.setHTML(view.getLiterals().closeBox().replace(view.getLiterals().FONT_COLOR(), secondaryColor));
    closeBox.getElement().getStyle().setColor(secondaryColor);
    closeBox.getElement().getStyle().setCursor(Style.Cursor.POINTER);
    closeBox.addClickHandler(this);
    header.setCellPadding(0);
    header.setCellSpacing(0);
    header.setWidget(0, 0, headerText);
    header.setWidget(0, 1, closeBox);
    header.getCellFormatter().getElement(0, 0).getStyle().setProperty("color", primaryColor);
    header.getCellFormatter().getElement(0, 0).getStyle().setProperty("fontFamily", fontFamily);
    header.getCellFormatter().getElement(0, 0).getStyle().setProperty("verticalAlign", "bottom");
    header.getCellFormatter().getElement(0, 0).getStyle().setProperty("backgroundColor", tertiaryColor);
    header.getCellFormatter().getElement(0, 1).getStyle().setProperty("lineHeight", "0%");
    header.getCellFormatter().getElement(0, 1).getStyle().setProperty("borderBottom", "55px solid transparent");
    header.getCellFormatter().getElement(0, 1).getStyle().setProperty("borderLeft", "55px solid " + tertiaryColor);
    header.getCellFormatter().getElement(0, 1).getStyle().setProperty("textAlign", "right");
    message.setWidget(0, 0, header);
    message.setHTML(1, 0, view.getLiterals().emptyString());
    message.getCellFormatter().getElement(1, 0).getStyle().setProperty("height", "10px");
    bodyText.setHTML(new SafeHtmlBuilder().appendHtmlConstant(view.getPage().getMessageBodyText(0)).toSafeHtml());
    bodyText.getElement().getStyle().setProperty("color", primaryColor);
    bodyText.getElement().getStyle().setProperty("opacity", "1");
    bodyText.getElement().getStyle().setProperty("fontFamily", fontFamily);
    bodyText.getElement().getStyle().setProperty("textAlign", "justify");
    body.setWidget(0, 0, bodyText);
    message.setWidget(2, 0, body);
    if (!view.getPage().getMessageInformationItem(0).equals("none")) {
      button.setText("more");
      button.getElement().setAttribute("item", view.getPage().getMessageInformationItem(0));
      button.setTitle(view.getPage().getMessageInformationItem(0));
      button.getElement().getStyle().setColor(secondaryColor);
      button.getElement().getStyle().setProperty("background", tertiaryColor);
      button.addClickHandler(this);
      message.setWidget(3, 0, button);
    }
    message.getElement().getStyle().setOpacity(0);
    view.getPanel().addAttachHandler(this);
    view.getPanel().add(message);
  }

  @Override
  public void resize() {
    int left = BigDecimal.valueOf(Window.getClientWidth()).multiply(leftPercent).intValue();
    int top = BigDecimal.valueOf(Window.getClientHeight()).multiply(topPercent).intValue();
    int width = header.getCellFormatter().getElement(0, 0).getClientWidth();
    width += header.getCellFormatter().getElement(0, 1).getClientWidth();
    message.getElement().getStyle().setProperty("width", width + "px");
    view.getPanel().setWidgetPosition(message, left, top);
  }

  @Override
  public void onClick(ClickEvent event) {
    String source = event.getSource().toString();
    if (source.contains("closeBox")) {
      event.stopPropagation();
      fadeOut();
    } else if (source.contains("item=")) {
      AsyncCallback<Item> asyncCallback = new AsyncCallback<Item>() {
        public void onFailure(Throwable caught) { Window.alert(view.getLiterals().rpcErrorMessage()); }
        @Override
        public void onSuccess(Item item) {
          InformationItemPopup informationItem = new InformationItemPopup(item, view.getLiterals());
          informationItem.show();
        }
      };
      rpc.getItem(button.getElement().getAttribute("item"), asyncCallback);
    }
  }

  @Override
  public void onAttachOrDetach(AttachEvent event) {
    if (event.isAttached()) {
      timer = new Timer() {
        @Override
        public void run() {
          fadeIn();
        }
      };
      timer.schedule(1000);
    } else {
      timer = new Timer() {
        @Override
        public void run() {
          fadeOut();
        }
      };
      timer.schedule(1000);
    }
  }

  public void fadeOut() {
    int stepInterval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(1.0, MathContext.DECIMAL32);
      BigDecimal decrement = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() >= BigDecimal.ZERO.doubleValue()) {
          message.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.subtract(decrement, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(stepInterval);
  }

  public void fadeIn() {
    resize();
    message.getElement().getStyle().setOpacity(0);
    message.setVisible(true);
    int interval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(0.0, MathContext.DECIMAL32);
      BigDecimal increment = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() <= BigDecimal.ONE.doubleValue()) {
          message.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.add(increment, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(interval); 
  }
  
}