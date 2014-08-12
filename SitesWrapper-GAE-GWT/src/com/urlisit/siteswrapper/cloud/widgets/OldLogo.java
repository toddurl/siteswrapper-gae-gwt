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
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.urlisit.siteswrapper.cloud.model.DisplayLogoAs;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * @author url
 *
 */
public class OldLogo {
  
  private View view;
  private final Image imageLogo = new Image();
  private HTML htmlLogo;
  private boolean loaded;
  private Timer timer;
  private DisplayLogoAs displayLogoAs;
  private BigDecimal windowWidth;
  private BigDecimal windowHeight;
  private BigDecimal leftPercent;
  private BigDecimal topPercent;
  private BigDecimal widthPercent;
  private BigDecimal heightPercent;
  
  public OldLogo(View view) {
    this.view = view;
    leftPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoLeftPercent()));
    topPercent  = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoTopPercent()));
    widthPercent = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoWidthPercent()));
    heightPercent  = BigDecimal.valueOf(Double.parseDouble(view.getLookAndFeel().getLogoHeightPercent()));
    displayLogoAs = view.getPage().getDisplayLogoAs();
    switch (displayLogoAs) {
      case HTML:
        SafeHtml html = new SafeHtmlBuilder().appendHtmlConstant(view.getPage().getLogoHtml()).toSafeHtml();
        htmlLogo = new HTML(html);
        view.getPanel().add(htmlLogo);
        break;
      case IMAGE:
        SafeUri url = UriUtils.fromString(view.getPage().getLogoImage());
        imageLogo.getElement().getStyle().setProperty("opacity", ".0");
        imageLogo.addLoadHandler(new LoadHandler() {
          @Override
          public void onLoad(LoadEvent event) {
            loaded = true;
            resize();
            fadeIn();
          }
        });
        imageLogo.setUrl(url);
        view.getPanel().add(imageLogo);
        break;
      case NONE:
        break;
    }
  }

  public void resize() {
    if (loaded) {
      windowWidth = BigDecimal.valueOf(Window.getClientWidth());
      windowHeight = BigDecimal.valueOf(Window.getClientHeight());
      int left = leftPercent.multiply(windowWidth).intValue();
      int top  = topPercent.multiply(windowHeight).intValue();
    
      BigDecimal newHeight = heightPercent.multiply(windowHeight);
      BigDecimal oldWidth = BigDecimal.valueOf(Integer.valueOf(imageLogo.getWidth()).doubleValue());
      BigDecimal oldHeight = BigDecimal.valueOf(Integer.valueOf(imageLogo.getHeight()).doubleValue());
      BigDecimal newWidth = newHeight.multiply(oldWidth.divide(oldHeight, 4, BigDecimal.ROUND_HALF_EVEN));
    
      switch (displayLogoAs) {
        case HTML:
          view.getPanel().setWidgetPosition(htmlLogo, left, top);
          break;
        case IMAGE:
          //imageLogo.setSize(width + "px", height + "px");
          imageLogo.setPixelSize(newWidth.intValue(), newHeight.intValue());
          view.getPanel().setWidgetPosition(imageLogo, left, top);
          break;
        case NONE:
          break;
      }
    } else {
      imageLogo.addLoadHandler(new LoadHandler() {
        @Override
        public void onLoad(LoadEvent event) {
          loaded = true;
          resize();
          fadeIn();
        }
      });
    }
  }
  
  public void fadeIn() {
    imageLogo.getElement().getStyle().setOpacity(0);
    imageLogo.setVisible(true);
    int interval = 15;
    timer = new Timer() {
      BigDecimal current = new BigDecimal(0.0, MathContext.DECIMAL32);
      BigDecimal increment = new BigDecimal(0.1, MathContext.DECIMAL32);
      @Override
      public void run() {
        if (current.doubleValue() <= BigDecimal.ONE.doubleValue()) {
          imageLogo.getElement().getStyle().setOpacity(current.doubleValue());
          current = current.add(increment, MathContext.DECIMAL32);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleRepeating(interval);
  }

}