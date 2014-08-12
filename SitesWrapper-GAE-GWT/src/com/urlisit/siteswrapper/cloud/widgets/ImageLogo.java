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

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class ImageLogo implements AttachEvent.Handler {

  private View view;
  private final Image logo = new Image();
  private boolean loaded;
  private Timer timer;
  private BigDecimal leftPercent;
  private BigDecimal topPercent;
  private BigDecimal widthPercent;
  private BigDecimal oldWidth;
  private BigDecimal oldHeight;
  private BigDecimal newWidth;
  private BigDecimal newHeight;  
  
  /**
   * 
   */
  public ImageLogo(View view) {
    this.view = view;
    leftPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoLeftPercent()));
    topPercent  = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoTopPercent()));
    widthPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoWidthPercent()));
    //logo.setVisible(false);
    loaded = false;
    logo.getElement().getStyle().setOpacity(Double.valueOf(view.getLiterals().zero()));
    logo.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        //logo.setVisible(true);
        loaded = true;
        resize();
        fadeIn();
      }
    });
    view.getPanel().add(logo);
    logo.setUrl(UriUtils.fromString(view.getPage().getLogoImage()));
  }
  
  public void resize() {
    if (isLoaded()) {
      int left = leftPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientWidth()))).intValue();
      int top  = topPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientHeight()))).intValue();
      newWidth = widthPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientWidth())));
      oldWidth = BigDecimal.valueOf(Integer.valueOf(logo.getWidth()).doubleValue());
      oldHeight = BigDecimal.valueOf(Integer.valueOf(logo.getHeight()).doubleValue());
      newHeight = newWidth.multiply(oldWidth.divide(oldHeight, 4, BigDecimal.ROUND_HALF_EVEN));
      logo.setPixelSize(newWidth.intValue(), newHeight.intValue());
      view.getPanel().setWidgetPosition(logo, left, top);
    } else {
      logo.addLoadHandler(new LoadHandler() {
        @Override
        public void onLoad(LoadEvent event) {
          //logo.setVisible(true);
          loaded = true;
          resize();
        }
      }); 
    }
  }
  
  public void fadeIn() {
    int interval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(0.0, MathContext.DECIMAL32);
      BigDecimal increment = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() <= BigDecimal.ONE.doubleValue()) {
          logo.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.add(increment, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(interval);
  }
  
  public void fadeOut() {
    int stepInterval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(1.0, MathContext.DECIMAL32);
      BigDecimal decrement = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() >= BigDecimal.ZERO.doubleValue()) {
          logo.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.subtract(decrement, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(stepInterval);
  }

  public boolean isLoaded() {
    return loaded;
  }

  @Override
  public void onAttachOrDetach(AttachEvent event) {
    if (event.isAttached()) {
      fadeIn();
    } else {
      fadeOut();
    }
  }

}