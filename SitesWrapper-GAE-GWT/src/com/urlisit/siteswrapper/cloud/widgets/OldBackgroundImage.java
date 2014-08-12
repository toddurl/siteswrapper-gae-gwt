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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.urlisit.siteswrapper.cloud.presenter.Presenter;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class OldBackgroundImage implements EventListener, RequiresResize {
  
  private View view;
  private String url;
  private Image image;
  private ImageElement img;
  private Element div;
  private Timer timer;
  private boolean isLoaded;
  private BigDecimal windowWidth;
  private BigDecimal windowHeight;
  private BigDecimal imageWidth;
  private BigDecimal imageHeight;
  private BigDecimal widthRatio;
  private BigDecimal heightRatio;
  private BigDecimal scaledWidth;
  private BigDecimal scaledHeight;
  
  public OldBackgroundImage(View view) {
    this.view = view;
    div = DOM.createDiv();
    div.getStyle().setProperty("visibility", "hidden");
    div.getStyle().setProperty("position", "absolute");
    div.getStyle().setProperty("width", "1px");
    div.getStyle().setProperty("height", "1px");
    div.getStyle().setProperty("overflow", "hidden");
    Document.get().getBody().appendChild(div);
    img  = DOM.createImg().cast();
    div.appendChild(img);
    Event.sinkEvents(img, Event.ONLOAD | Event.ONERROR);
    Event.setEventListener(div, this);
    img.setSrc(view.getPage().getBackgroundImageUrls(view.getLiterals().zero()));
    image = new Image();
    image.setVisible(false);
    view.getPanel().insert(image, 0, 0, 0);
  }

  @Override
  public void onBrowserEvent(Event event) {
    if (event.getTypeInt() == Event.ONLOAD){
      isLoaded = true;
      img.removeFromParent();
      div.removeFromParent();
      image.setUrl(view.getPage().getBackgroundImageUrls(view.getLiterals().zero()));
      resize();
    } else if (event.getTypeInt() == Event.ONERROR) {
      Window.alert(view.getLiterals().imageErrorMessage() + url);
    }
  }

  @Override
  public void onResize() {
    if (isLoaded) {
      windowWidth = BigDecimal.valueOf(Window.getClientWidth());
      windowHeight = BigDecimal.valueOf(Window.getClientHeight());
      imageWidth = BigDecimal.valueOf(image.getWidth());
      imageHeight = BigDecimal.valueOf(image.getHeight());
      widthRatio = windowWidth.divide(imageWidth, 4, BigDecimal.ROUND_HALF_EVEN);
      heightRatio = windowHeight.divide(imageHeight, 4, BigDecimal.ROUND_HALF_EVEN);
      int left = 0;
      int top = 0;
      if (widthRatio.compareTo(heightRatio) >= 0) {
        scaledWidth = widthRatio.multiply(imageWidth);
        scaledHeight = widthRatio.multiply(imageHeight);
        top = scaledHeight.subtract(windowHeight).divide(new BigDecimal(2)).intValue();
        image.setWidth(scaledWidth.intValue() + "px");
        image.setHeight(scaledHeight.intValue() + "px");
        view.getPanel().setWidgetPosition(image, left, -top);
      } else {
        scaledWidth = heightRatio.multiply(imageWidth);
        scaledHeight = heightRatio.multiply(imageHeight);
        left = scaledWidth.subtract(windowWidth).divide(new BigDecimal(2)).intValue();
        image.setWidth(scaledWidth.intValue() + "px");
        image.setHeight(scaledHeight.intValue() + "px");
        view.getPanel().setWidgetPosition(image, -left, top);
      }
    }
  }

  public void resize() {
    if (isLoaded) {
      windowWidth = BigDecimal.valueOf(Window.getClientWidth());
      windowHeight = BigDecimal.valueOf(Window.getClientHeight());
      imageWidth = BigDecimal.valueOf(image.getWidth());
      imageHeight = BigDecimal.valueOf(image.getHeight());
      widthRatio = windowWidth.divide(imageWidth, 4, BigDecimal.ROUND_HALF_EVEN);
      heightRatio = windowHeight.divide(imageHeight, 4, BigDecimal.ROUND_HALF_EVEN);
      int left = 0;
      int top = 0;
      if (widthRatio.compareTo(heightRatio) >= 0) {
        scaledWidth = widthRatio.multiply(imageWidth);
        scaledHeight = widthRatio.multiply(imageHeight);
        top = scaledHeight.subtract(windowHeight).divide(new BigDecimal(2)).intValue();
        image.setWidth(scaledWidth.intValue() + "px");
        image.setHeight(scaledHeight.intValue() + "px");
        view.getPanel().setWidgetPosition(image, left, -top);
      } else {
        scaledWidth = heightRatio.multiply(imageWidth);
        scaledHeight = heightRatio.multiply(imageHeight);
        left = scaledWidth.subtract(windowWidth).divide(new BigDecimal(2)).intValue();
        image.setWidth(scaledWidth.intValue() + "px");
        image.setHeight(scaledHeight.intValue() + "px");
        view.getPanel().setWidgetPosition(image, -left, top);
      }
    }
  }
  
  public void fadeIn() {
    resize();
    image.getElement().getStyle().setOpacity(0);
    image.setVisible(true);
    int interval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(0.0, MathContext.DECIMAL32);
      BigDecimal increment = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() <= BigDecimal.ONE.doubleValue()) {
          image.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.add(increment, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(interval);
  }
  
  public void fadeOut(final Presenter presenter, final View view) {
    int stepInterval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(1.0, MathContext.DECIMAL32);
      BigDecimal decrement = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() >= BigDecimal.ZERO.doubleValue()) {
          image.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.subtract(decrement, MathContext.DECIMAL32);
        } else {
          timer.cancel();
          presenter.loadView(view);
        }
      }
    };
    timer.scheduleRepeating(stepInterval);
  }

  public void fadeOut() {
    int stepInterval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(1.0, MathContext.DECIMAL32);
      BigDecimal decrement = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() >= BigDecimal.ZERO.doubleValue()) {
          image.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.subtract(decrement, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(stepInterval);
  }
  
  public void setTitle(String title) {
    image.setTitle(title);
  }

}