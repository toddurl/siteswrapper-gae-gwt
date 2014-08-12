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
package com.urlisit.siteswrapper.cloud.event;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Image;
import com.urlisit.siteswrapper.cloud.utilities.Dimensions;

public class ImageLoadEvent extends GwtEvent<ImageLoadHandler> {

  private static final Type<ImageLoadHandler> TYPE = new Type<ImageLoadHandler>();

  public ImageLoadEvent(ImageElement image, Dimensions dimensions) {
    this.image = image;
    this.dimensions = dimensions;
  }
  
  public ImageLoadEvent(String url, Dimensions dimensions) {
    this.url = url;
    this.dimensions = dimensions;
  }
  
  protected ImageElement image;
  protected String url;
  protected Dimensions dimensions;
  protected boolean imageTaken;
  
  public Dimensions getDimensions() {
    return dimensions;
  }
  
  public Image takeImage() {
    imageTaken = true;
    if (image == null) {
    return new Image(url);
    } else {
    Image ret = new ImageFromElement(image);
    image = null;
    return ret;
    }
  }
  
  public String getImageUrl() {
    if (url != null)
    return url;
    return image.getSrc();
  }
  
  public boolean isImageTaken() {
    return imageTaken;
  }
  
  public boolean isLoadFailed() {
    return dimensions == null;
  }

  @Override
  protected void dispatch(ImageLoadHandler handler) {
    handler.imageLoaded(this);
  }

  @Override
  public Type<ImageLoadHandler> getAssociatedType() {
    return TYPE;
  }

  public static Type<ImageLoadHandler> getType() {
    return TYPE;
  }
  
  private static class ImageFromElement extends Image {
    public ImageFromElement(ImageElement element) {
    super(element);
    }
  }

}