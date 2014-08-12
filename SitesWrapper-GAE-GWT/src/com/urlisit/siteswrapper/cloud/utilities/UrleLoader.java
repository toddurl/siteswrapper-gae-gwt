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
package com.urlisit.siteswrapper.cloud.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;
import com.urlisit.siteswrapper.cloud.event.ImageLoadEvent;
import com.urlisit.siteswrapper.cloud.event.ImageLoadHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

/**
 * @author url
 *
 */
public class UrleLoader {
  private static Map<String, Dimensions> dimensionCache = new HashMap<String, Dimensions>();
  
  private static List<ImageLoader> activeLoaders = new ArrayList<ImageLoader>();
  
  private static Element loadingArea;
  
  private static class ImageLoader {
    
    ImageElement image = DOM.createImg().cast();
    List<ImageLoadHandler> handlers;
    String url;
    
    public ImageLoader() {
      Event.sinkEvents(image, Event.ONLOAD | Event.ONERROR);
      loadingArea.appendChild(image);
    }
    
    @SuppressWarnings("unused")
    public void clearHandlers() {
      if (handlers != null) {
        handlers.clear();
      }
    }
    
    public void addHandler(ImageLoadHandler handler) {
      if (handler != null) {
        if (handlers == null) {
          handlers = new ArrayList<ImageLoadHandler>(1);
        }
        handlers.add(handler);
      }
    }
    
    public void fireHandlers(ImageLoadEvent event) {
      if (handlers != null) {
        for (ImageLoadHandler handler : handlers) {
          handler.imageLoaded(event);
        }
      }
    }
    
    public void start(String url) {
      this.url = url;
      image.setSrc(url);
    }
    
    public boolean imageEquals(ImageElement image) {
      return this.image == image;
    }
    
    public boolean urlEquals(String url) {
      return this.url.equals(url);
    }
    
  }

  /**
   * Main method
   */
  public static void load(String url, ImageLoadHandler loadHandler) {
    if (url == null) {
      if (loadHandler != null) {
        loadHandler.imageLoaded(new ImageLoadEvent(url, null));
      }
      return;
    }
    if (dimensionCache.containsKey(url)) {
      if (loadHandler != null) {
        Dimensions cachedDimensions = dimensionCache.get(url);
        if (cachedDimensions.getWidth() == -1) {
          loadHandler.imageLoaded(new ImageLoadEvent(url, null));
        } else {
          loadHandler.imageLoaded(new ImageLoadEvent(url, cachedDimensions));
        }
      }
      return;
    } else {
      int index = findUrlInPool(url);
      if (index != -1) {
        activeLoaders.get(index).addHandler(loadHandler);
        return;
      }
    }
    init();
    ImageLoader loader = new ImageLoader();
    activeLoaders.add(loader);
    loader.addHandler(loadHandler);
    loader.start(url);
  }

  private static void init() {
    if (loadingArea == null) {
      loadingArea = DOM.createDiv();
      loadingArea.getStyle().setProperty("visibility", "hidden");
      loadingArea.getStyle().setProperty("position", "absolute");
      loadingArea.getStyle().setProperty("width", "1px");
      loadingArea.getStyle().setProperty("height", "1px");
      loadingArea.getStyle().setProperty("overflow", "hidden");
      Document.get().getBody().appendChild(loadingArea);
      Event.setEventListener(loadingArea, new EventListener() {
        @Override
        public void onBrowserEvent(Event event) {
          boolean success;
          if (Event.ONLOAD == event.getTypeInt()) {
            success = true;
          } else if (Event.ONERROR == event.getTypeInt()) {
            success = false;
          } else {
            return;
          }
          if (!ImageElement.is(event.getCurrentEventTarget())) {
            return;
          }
          ImageElement image = ImageElement.as(Element.as(event.getCurrentEventTarget()));
          int index = findImageInPool(image);
          ImageLoader loader = activeLoaders.get(index);
          Dimensions dim = null;
          if (success) {
            dim = new Dimensions(image.getWidth(), image.getHeight());
            dimensionCache.put(loader.url, dim);
          } else {
            dimensionCache.put(loader.url,  new Dimensions(-1, -1));
          }
          loadingArea.removeChild(image);
          activeLoaders.remove(index);
          ImageLoadEvent evt = new ImageLoadEvent(image, dim);
          loader.fireHandlers(evt);
        }

      });
    }
  }

  private static int findImageInPool(ImageElement image) {
    for (int index = 0; index < activeLoaders.size(); index++) {
      if (activeLoaders.get(index).imageEquals(image)) {
        return index;
      }
    }
    return -1;
  }
  
  private static int findUrlInPool(String url) {
    for (int index = 0; index < activeLoaders.size(); index++) {
      if (activeLoaders.get(index).urlEquals(url)) {
        return index;
      }
    }
    return -1;
  }
  
  @SuppressWarnings("unused")
  private static Dimensions getCachedDimensions(String url) {
    return dimensionCache.get(url);
  }

}