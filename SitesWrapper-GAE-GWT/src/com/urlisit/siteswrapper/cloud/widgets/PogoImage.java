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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class PogoImage extends Composite implements Pogo {

  private View view;
  private boolean fadeEnabled = false;
  private int fadeRepeatingInterval = 15;
  private int fadeDelay = 0;
  private Timer timer;
  private final Image logo = new Image();
  private boolean isLoaded;
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
  public PogoImage(View view) {
    this.view = view;
    initWidget(logo);
    leftPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoLeftPercent()));
    topPercent  = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoTopPercent()));
    widthPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoWidthPercent()));
    logo.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        isLoaded = true;
        onResize();
      }
    });
    logo.getElement().getStyle().setOpacity(Double.valueOf(view.getLiterals().zero()));
    logo.setUrl(UriUtils.fromString(view.getPage().getLogoImage()));
    logo.addAttachHandler(this);
    view.getPanel().add(this.asWidget());
  }

  /** 
   * This method must be called whenever the logo's size has been modified. This is typically the result of the browser
   * window being resized and is typically handled by the owning ViewPanel, View or some other implementor of the
   * ProvidesResize interface. Because a resize involves getting and setting the size of the Image widget, the image
   * must already be loaded. Consequently, if isLoaded() returns false, a LoadHandler is created and onResize()
   * rescheduled to occur when Image.getWidth/getHeight will succeed.
   * 
   * @see com.google.gwt.user.client.ui.RequiresResize#onResize()
   */
  @Override
  public void onResize() {
    if (isLoaded()) {
      int left = leftPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientWidth()))).intValue();
      int top  = topPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientHeight()))).intValue();
      newWidth = widthPercent.multiply(BigDecimal.valueOf(Double.valueOf(Window.getClientWidth())));
      oldWidth = BigDecimal.valueOf(Integer.valueOf(logo.getWidth()).doubleValue());
      oldHeight = BigDecimal.valueOf(Integer.valueOf(logo.getHeight()).doubleValue());
      newHeight = newWidth.multiply(oldWidth.divide(oldHeight, 4, BigDecimal.ROUND_HALF_EVEN));
      logo.setPixelSize(newWidth.intValue(), newHeight.intValue());
      view.getPanel().setWidgetPosition(this, left, top);
    } else {
      logo.addLoadHandler(new LoadHandler() {
        @Override
        public void onLoad(LoadEvent event) {
          isLoaded = true;
          onResize();
        }
      }); 
    }
  }

  /**
   * @see com.google.gwt.event.logical.shared.AttachEvent.Handler#onAttachOrDetach(com.google.gwt.event.logical.shared.AttachEvent)
   */
  @Override
  public void onAttachOrDetach(AttachEvent event) {
    if (event.isAttached()) {
      if (isFadeEnabled()) {
        fadeIn();
      } else {
        logo.getElement().getStyle().setOpacity(1);
      }
    } else {
      if (isFadeEnabled()) {
        fadeOut();
      } else {
        logo.getElement().getStyle().setOpacity(0);
      }
    }
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#setFadeEnabled(boolean)
   */
  @Override
  public void setFadeEnabled(boolean enabled) {
    fadeEnabled = enabled;
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#isFadeEnabled()
   */
  @Override
  public boolean isFadeEnabled() {
    return fadeEnabled;
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#setFadeScheduleRepeatingInterval(int)
   */
  @Override
  public void setFadeScheduleRepeatingInterval(int milliseconds) {
    fadeRepeatingInterval = milliseconds;
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#setFadeScheduleDelay(int)
   */
  @Override
  public void setFadeScheduleDelay(int milliseconds) {
    fadeDelay = milliseconds;
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#fadeIn()
   */
  @Override
  public void fadeIn() {
    timer = new Timer() {
      @Override
      public void run() {
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
        timer.scheduleRepeating(fadeRepeatingInterval);
      }
    };
    timer.schedule(fadeDelay);
  }

  /**
   * @see com.urlisit.siteswrapper.cloud.widgets.Fadable#fadeOut()
   */
  @Override
  public void fadeOut() {
    timer = new Timer() {
      @Override
      public void run() {
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
        timer.scheduleRepeating(fadeRepeatingInterval);
      }
    };
    timer.schedule(fadeDelay);
  }

  @Override
  public boolean isLoaded() {
    return isLoaded;
  }
  
  @Override
  public void setTitle(String title) {
    logo.setTitle(title);
  }
  
  @Override
  public String getTitle() {
    return logo.getTitle();
  }

}