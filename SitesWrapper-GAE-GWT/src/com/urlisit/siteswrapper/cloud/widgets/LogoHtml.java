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
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class LogoHtml implements Logo {
  
  private View view;
  private final HTML logo = new HTML();
  private boolean loaded;
  private Timer timer;
  private BigDecimal leftPercent;
  private BigDecimal topPercent;

  /**
   * 
   */
  public LogoHtml(View view) {
    this.view = view;
    leftPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoLeftPercent()));
    topPercent  = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoTopPercent()));
    logo.setHTML(view.getPage().getLogoHtml());
    logo.addAttachHandler(this);
    //logo.setVisible(true);
    view.getPanel().add(logo);
  }

  @Override
  public void onResize() {
    int left = leftPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientWidth()))).intValue();
    int top  = topPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientHeight()))).intValue();
    view.getPanel().setWidgetPosition(logo, left, top);
  }

  @Override
  public void onAttachOrDetach(AttachEvent event) {
    if (event.isAttached()) {
      fadeIn();
    } else {
      fadeOut();
    }
  }

  @Override
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

  @Override
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

  @Override
  public boolean isLoaded() {
    return loaded;
  }

}
