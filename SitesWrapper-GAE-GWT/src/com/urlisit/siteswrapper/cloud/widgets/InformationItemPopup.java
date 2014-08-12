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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.urlisit.siteswrapper.cloud.factory.Literals;
import com.urlisit.siteswrapper.cloud.model.Item;
import java.math.BigDecimal;

/**
 * @author url
 *
 */
public class InformationItemPopup {
  
  DialogBox popup = new DialogBox();
  Video video;
  Item item;
  Literals literals;

  /**
   * 
   */
  public InformationItemPopup(Item item, Literals literals) {
    this.item = item;
    this.literals = literals;
    popup.setText(item.getName());
    popup.setGlassEnabled(true);
    popup.setAnimationEnabled(true);
    VerticalPanel contents = new VerticalPanel();
    contents.setSpacing(5);
    contents.setWidth(Double.valueOf(Window.getClientWidth() * .75).intValue() + "px");
    popup.setWidget(contents);
    HTML description = new HTML(item.getDescription());
    contents.add(description);
    //contents.setCellHorizontalAlignment(description, HasHorizontalAlignment.ALIGN_CENTER);
    contents.setCellHorizontalAlignment(description, HasHorizontalAlignment.ALIGN_JUSTIFY);
    HorizontalPanel hContents = new HorizontalPanel();
    String videoEmbedHtml = literals.youTubeOldEmbed420x315().replace("VIDEO_URL", item.getVideoUrl());
    final HTML video = new HTML(videoEmbedHtml);
    final Image image = new Image(item.getImageUrl());
    image.setVisible(false);
    image.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        BigDecimal newHeight = BigDecimal.valueOf(315.00);
        BigDecimal width = BigDecimal.valueOf(Integer.valueOf(image.getWidth()).doubleValue());
        BigDecimal height = BigDecimal.valueOf(Integer.valueOf(image.getHeight()).doubleValue());
        BigDecimal newWidth = newHeight.multiply(width.divide(height, 4, BigDecimal.ROUND_HALF_EVEN));
        image.setPixelSize(newWidth.intValue(), newHeight.intValue());
        image.setVisible(true);
      }
    });
    hContents.add(video);
    hContents.add(image);
    contents.add(hContents);
    contents.setCellHorizontalAlignment(hContents, HasHorizontalAlignment.ALIGN_CENTER);
    Button closeButton = new Button(
        "Close", new ClickHandler() {
          public void onClick(ClickEvent event) {
            popup.hide();
          }
        });
    contents.add(closeButton);
    contents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
  }
  
  public void show() {
    //Window.alert(Double.valueOf(Window.getClientWidth() * .75).intValue() + "");
    popup.setHeight(Double.valueOf(Window.getClientHeight() * .75).intValue() + "px");
    popup.setWidth(Double.valueOf(Window.getClientWidth() * .75).intValue() + "px");
    popup.center();
    popup.show();
  }

}
