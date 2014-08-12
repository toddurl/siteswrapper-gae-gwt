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
package com.urlisit.siteswrapper.cloud.client;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.urlisit.siteswrapper.cloud.client.Resources;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * @author Todd Url <toddurl @ yahoo.com>
 * 
 * @version 1.0
 * @since 11-1-2011
 */
public class RenderedPage implements ResizeHandler, ValueChangeHandler<String> {

  /**
   * All widgets are attached to AbsolutePanel mainPanel via their respective
   * classes
   */
  public static AbsolutePanel mainPanel = new AbsolutePanel();
  
  /**
   * Not sure exactly how this is used
   */
  private static Map<String, String> pageMap = new HashMap<String, String>();
  
  /**
   * Not sure exactly how this is used
   */
  private static Map<String, RenderedPage> instanceMap = new HashMap<String, RenderedPage>();

  /**
   * Assigned as an argument to the constructor by the user of the class, pageTitle is unique to each
   * newly instantiated RenderedPage
   */
  private String pageTitle;
  
  /**
   * Assigned to each new RenderedPages and mapped to pageTitle in the pageMap by the constructor
   */
  private int pageNumber;
  
  /**
   * Used to assign the instance field pageNumber to each newly instantiated page. The pageNumber gets
   * mapped to the instance field pageTitle in the static collection pageMape in the constructor for
   * each newly instantiated page
   */
  private static int numberOfPages = 0;
  
  /**
   * Used to initially size, position and dynamically resize and position the elements of a page in response
   * to changes in the browsers window size
   */
  public static int windowMargin = 0, windowHeight = Window.getClientHeight(), windowWidth = Window.getClientWidth();
  
  /**
   * Unique to each page. Use the setLogo method of the instance to assign a logo to a newly created RenderedPage
   */
  private Image logo = new Image();
  
  /**
   * Image logo height specified as a percent of the total page height in decimal (i.e. specify 10% as .10)
   */
  private static double logoHeightPercent = .14;
  
  /**
   * logoHeightPercent converted to pixels
   */
  private static int logoHeight;
  
  /**
   * Image logo width specified as a percent of logoHeight in decimal (i.e. specify 150% as 1.5). Used to maintain the aspect
   * ratio of the image in Internet Explorer
   */
  private static double logoWidthPercent = 2.25;
  
  /**
   * logoWidthPercent converted to pixels
   */
  private static int logoWidth;

  /**
   * Position of Image logo from the left edge of the page specified as a percent of the total page width in decimal (i.e. specify 10% as .1)
   */
  private static double logoLeftPercent = .14;
  
  /**
   * logoLeftPercent converted to pixels
   */
  private static int logoLeft;

  /**
   * Position of Image logo from the top of the page specified as a percent of the total page height in decimal (i.e. specify 10% as .1)
   */
  private static double logoTopPercent = .1;
  
  /**
   * logoTopPercent converted to pixels
   */
  private static int logoTop;

  /**
   * I18N localization selector at the top of the page
   */
  public static ListBox locLangSelector = new ListBox();
  
  /**
   * Size ListBox locLangSelector width as a percent of the total page width in decimal (i.e. specify 18% as .18)
   */
  private static double locLangSelectorWidthPercent = .18;
  
  /**
   * locLangSelectorWidthPercent converted to pixels
   */
  private static int locLangSelectorWidth;
  
  /**
   * Size ListBox locLangSelector height as a percent of the total page height in decimal (i.e. specify 2.5% as .025)
   */
  private static double locLangSelectorHeightPercent = .025;
  
  /**
   * locLangSelectorHeightPercent converted to pixels
   */
  private static int locLangSelectorHeight;
  
  /**
   * Position ListBox locLangSelector distance from top of page as a percent of the total page height in decimal (i.e. specify 3% as .03)
   */
  private static double locLangSelectorTopPercent = .03;
  
  /**
   * locLangSelectorTopPercent converted to pixels
   */
  private static int locLangSelectorTop;
  
  /**
   * Position ListBox locLangSelector distance from left edge of page as a percent of the total page
   * width in decimal (i.e. specify 68% as .68)
   */
  private static double locLangSelectorLeftPercent = .68;
  
  /**
   * locLangSelectorLeftPercent converted to pixels
   */
  private static int locLangSelectorLeft;

  /**
   * Provides a data entry field for the search function
   */
  private static TextBox searchField = new TextBox();
  
  /**
   * Size of TextBox searchField width as a percent of the total page width in decimal (i.e. specify 14.38% as .1438)
   */
  private static double searchFieldWidthPercent = .14;
  
  /**
   * searchFieldWidthPercent converted to pixels
   */
  private static int searchFieldWidth;
  
  /**
   * Size of TextBox searchField height as a percent of the total page height in decimal (i.e. specify 1.5% as .015)
   */
  private static double searchFieldHeightPercent = .015;
  
  /**
   * searchFieldHeightPercent converted to pixels
   */
  private static int searchFieldHeight;

  /**
   * Position of TextBox searchField distance from left edge of page as a percent of the total page
   * width in decimal (i.e. specify 68% as .68)
   */
  private static double searchFieldLeftPercent = .68;
  
  /**
   * searchFieldLeftPercent converted to pixels
   */
  private static int searchFieldLeft;
  
  /**
   * Position of TextBox searchField from top of page as a percent of the total page height in decimal (i.e. specify 8% as .08)
   */
  private static double searchFieldTopPercent = .08;
  
  /**
   * searchFieldTopPercent converted to pixels
   */
  private static int searchFieldTop;
  
  /**
   * Sends the contents TextBox searchField to the server via GWT RPC
   */
  @SuppressWarnings("deprecation")
  private static Image searchButton = new Image(Resources.resources.searchButton().getURL());
  
  /**
   * Image searchButton height as a percent of the total page height in decimal (i.e. specify 1.5% as .015)
   */
  private static double searchButtonHeightPercent = .032;
  
  /**
   * searchButtonHeightPercent converted to pixels
   */
  private static int searchButtonHeight;
  
  /**
   * Image searchButton width as percent of searchButtonHeight (i.e. specify 16.66% as 1.666)
   */
  private static double searchButtonWidthPercent = 1.666;
  
  /**
   * searchButtonWidthPercent converted to pixels
   */
  private static int searchButtonWidth;
  
  /**
   * Position of Image searchButton distance from left edge of page as a percent of the total page
   * width in decimal (i.e. specify 83.5% as .835)
   */
  private static double searchButtonLeftPercent = .835;
  
  /**
   * searchButtonLeftPercent converted to pixels
   */
  private static int searchButtonLeft;
  
  /**
   * Position of Image searchButton from top of page as a percent of the total page height in decimal (i.e. specify 8% as .08)
   */
  private static double searchButtonTopPercent = .08;
  
  /**
   * searchButtonTopPercent converted to pixels
   */
  private static int searchButtonTop;
  
  /**
   * Panel containing the HyperLinks of the mainMenu in each cell
   */
  private static FlexTable mainMenu = new FlexTable();
  
  /**
   * Used to position HTMLPanel mainMenu
   */
  private static int mainMenuLeft = 0;
  
  /**
   * Position of FlexTable mainMenu from top of page as a percent of the total page height in decimal (i.e. specify 14% as .14)
   */
  private static double mainMenuTopPercent = .14;
  
  /**
   * HTML displayed as the mainMenu link. The default value uses the page title as specified in the RenderedPage constructor
   * (i.e. <span class=mainMenuLink><font color=#58AC5C>&rarr;&nbsp;TITLE_SPECIFIED_IN_CONTRUCTOR</font></span>)
   */
  private String mainMenuLink = null;
  
  /**
   * HTML displayed when the mainMenuLink is selected. The default value uses the page title as specified in the RenderedPage constructor
   * (i.e. <span class=mainMenuLink><font color=#58AC5C>&darr;&nbsp;&nbsp;TITLE_SPECIFIED_IN_CONTRUCTOR</font></span>)
   */
  private String mainMenuLinkSelected = null;

  /**
   * Used to position HTMLPanel mainMenu
   */
  private static int mainMenuTop;

  /**
   * Holds the HyperLinks for each RenderedPage object
   */
  private static ArrayList<Hyperlink> mainMenuLinks = new ArrayList<Hyperlink>();
  
  /**
   * History enabled Hyperlink for the RenderedPage object
   */
  private Hyperlink mainMenuHyperLink = new Hyperlink();
  
  /**
   * Holds the images associated with a RenderedPage object
   */
  private ArrayList<Image> images = new ArrayList<Image>();
  
  /**
   * Schedules the transition from RenderedPage object image to another. Called when a new RenderedPage object is materialized
   */
  private Timer imageTransitionTimer = null;
  private boolean imageTransitionTimerFlag = false;

  /**
   * Decreases the opacity of the current RenderedPage object image, removes it from AbsolutePanel mainPanel and inserts the next RenderedPage object image
   */
  private Timer decreaseImageOpacityTimer = null;
  private boolean decreaseImageOpacityTimerFlag = false;

  /**
   * Increases the opacity of the current RenderedPage object image
   */
  private Timer increaseImageOpacityTimer = null;
  private boolean increaseImageOpacityTimerFlag = false;
  
  /**
   * Decreases the opacity of the current RenderedPage object image, removes it from the mainPanel and cancels the imageTransitionTimer
   * in preparation for the transition to a new RenderedPage object 
   */
  private Timer cancelImageTransitionTimer = null;  
  
  /**
   * Width of images from left edge of page as a percent of the total page width in decimal (i.e. specify 100% as 1)
   */
  public static double imagesWidthPercent = 1;
  
  /**
   * imagesWidthPercent converted to pixels
   */
  private static int imagesWidth;
  
  /**
   * Height of images specified as a percent of imagesWidth in decimal (i.e. specify 184% as 1.84). Used to maintain the aspect
   * ratio of the image in Internet Explorer
   */
  public static double imagesHeightPercent = 1.84;
  
  /**
   * imagesHeightPercent converted to pixels
   */
  private static int imagesHeight;
  
  /**
   * Position of images from top of page as a percent of the total page height in decimal (i.e. specify 8% as .08)
   */
  public static double imagesTopPercent = .17;
  
  /**
   * imagesTopPercent converted to pixels
   */
  private static int imagesTop;
  
  /**
   * Position of images from left edge of page as a percent of the total page width in decimal (i.e. specify 0% as 0)
   */
  private static double imagesLeftPercent = 0;
  
  /**
   * imagesTopPercent converted to pixels
   */
  private static int imagesLeft;
  
  /**
   * Constants controlling the transition of one RenderedPage object image to another
   */
  private double imageOpacityMin = 0, imageOpacityMax = 1, imageOpacityStep = .1;
  
  /**
   * Interval in milliseconds bewteen each increase of decrease in the RenderedPage object image opacity
   */
  int imageOpacityStepInterval = 50;
  
  /**
   * Index of the current RenderedPage object image in the RenderedPage object images ArrayList
   */
  int imageCur = 0;
  
  /**
   * The current opacity of the currently displayed RenderedPage object image
   */
  private double imageOpacityCur = 0;

  /**
   * Interval in milliseconds between RenderedPage object image transitions
   */
  int imageTransitionInterval = 10000;
  
  private ArrayList<RenderedPage.Message> messages = new ArrayList<RenderedPage.Message>();
  
  /**
   * Schedules the transition from RenderedPage object message to another. Called when a new RenderedPage object is materialized
   */
  private Timer messageDelayTimer = null;
  private Timer messageTransitionTimer = null;

  /**
   * Decreases the opacity of the current RenderedPage object message, removes it from AbsolutePanel mainPanel and inserts the next RenderedPage object message
   */
  private Timer decreaseMessageOpacityTimer = null;

  /**
   * Increases the opacity of the current RenderedPage object message
   */
  private Timer increaseMessageOpacityTimer = null;
  
  /**
   * Each RenderedPage starts with a dimmed message which has it's
   * opacity initialy rased using a second opacity timer
   */
  private Timer initialIncreaseMessageOpacityTimer = null;
  
  /**
   * Decreases the opacity of the current RenderedPage object message, removes it from the mainPanel and cancels the messageTransitionTimer
   * in preparation for the transition to a new RenderedPage object 
   */
  private Timer cancelMessageTransitionTimer = null;
  
  /**
   * Constants controlling the transition of one RenderedPage object message to another
   */
  private double messageOpacityMin = 0, messageOpacityMax = 1, messageOpacityStep = .1;
  
  /**
   * Interval in milliseconds between each increase of decrease in the RenderedPage object message opacity
   */
  int messageOpacityStepInterval = 50;
  
  /**
   * Index of the current RenderedPage object message in the RenderedPage object messages ArrayList
   */
  int messageCur = 0;
  
  /**
   * The current opacity of the currently displayed RenderedPage object message
   */
  private double messageOpacityCur = 0;

  /**
   * Interval in milliseconds between RenderedPage object message transitions
   */
  int messageTransitionInterval = 10000;
  
  boolean messageDelayTimerFlag = false;
  boolean initialIncreaseMessageOpacityTimerFlag = false;
  boolean messageTransitionTimerFlag = false;
  boolean decreaseMessageOpacityTimerFlag = false;
  boolean increaseMessageOpacityTimerFlag = false;
  
  private MenuBar pageMenu = new MenuBar();
  private int pageMenuWidth = (int) Math.round(RenderedPage.windowWidth * .72);
  private int pageMenuLeft = (int) Math.round(RenderedPage.windowWidth * .14);
  private int pageMenuTop = (int) Math.round(.2 * RenderedPage.windowHeight);
  private DisclosurePanel pageMenuDisclosure = new DisclosurePanel();
  public String pageMenuItemDisclosed = "";
  
  private ArrayList<MenuItem> pageMenuItems = new ArrayList<MenuItem>();
  
  public RenderedPage(String title) {
    pageTitle = title;
    pageNumber = RenderedPage.numberOfPages++;
    sizePageElements();
    mainMenuLink = "<span class=mainMenuLink><font color=#58AC5C>&rarr;&nbsp;" + pageTitle + "</font></span>";
    mainMenuLinkSelected = "<span class=mainMenuLink><font color=#58AC5C>&darr;&nbsp;&nbsp;" + pageTitle + "</font></span>";
    mainMenuHyperLink.setHTML(mainMenuLink);
    mainMenuHyperLink.setTargetHistoryToken(pageTitle);   
    RenderedPage.instanceMap.put(pageTitle, this);
    RenderedPage.pageMap.put("page" + pageNumber, pageTitle);
    RenderedPage.pageMap.put(pageTitle, "page" + pageNumber);
    RenderedPage.mainMenuLinks.add(mainMenuHyperLink);
    Window.addResizeHandler(this);
    History.addValueChangeHandler(this);
  }
  
  @SuppressWarnings("deprecation")
  public static AbsolutePanel renderLayout() {
    Window.setMargin(RenderedPage.windowMargin + "px");
    RenderedPage.sizeLayoutElements();
    RenderedPage.locLangSelector.addItem("United States / English");
    RenderedPage.locLangSelector.addItem("Costa Rica / Spanish");
    RenderedPage.searchField.setText("Search");
    RenderedPage.mainMenu.setText(0, 0, "");
    RenderedPage.mainMenu.getElement().getStyle().setBackgroundImage("url('" + Resources.resources.mainMenuSprite().getURL() + "')");
    RenderedPage.mainMenu.getElement().getStyle().setProperty("BackgroundRepeat", "repeat-x");
    int x = 0;
    while(RenderedPage.pageMap.get("page" + x) != null) {
      RenderedPage.instanceMap.get(RenderedPage.pageMap.get("page" + x)).mainMenuHyperLink.setHTML(RenderedPage.instanceMap.get(RenderedPage.pageMap.get("page" + x)).getMainMenuLink());
      RenderedPage.mainMenu.setWidget(0, x + 1, mainMenuLinks.get(x));
      x++;
    }
    int cellWidth = (int) Math.round(RenderedPage.windowWidth / RenderedPage.mainMenu.getCellCount(0));
    int y = RenderedPage.mainMenu.getCellCount(0);
    for(x = 0; x <= y; x++) {
      RenderedPage.mainMenu.getCellFormatter().setWidth(0, x, cellWidth + "px");
      RenderedPage.mainMenu.getCellFormatter().setHorizontalAlignment(0, x, HasHorizontalAlignment.ALIGN_CENTER);
    }
    RenderedPage.mainPanel.add(RenderedPage.locLangSelector, RenderedPage.locLangSelectorLeft, RenderedPage.locLangSelectorTop);
    RenderedPage.mainPanel.add(RenderedPage.searchField, RenderedPage.searchFieldLeft, RenderedPage.searchFieldTop);
    RenderedPage.mainPanel.add(RenderedPage.searchButton, RenderedPage.searchButtonLeft, RenderedPage.searchButtonTop);
    RenderedPage.mainPanel.add(RenderedPage.mainMenu, RenderedPage.mainMenuLeft, RenderedPage.mainMenuTop);
    /*
     * If renderLayout is called with an empty instanceMap report it using an alert box and fail gracefully
     * by returning an AbsolutePanel without any page instances to render. This situation happens when the
     * RenderedPage.renderLayout() method is called before new instance objects are created or the configuration
     * in the datastore is empty.  
     */
    if (RenderedPage.instanceMap.isEmpty()) {
      Window.alert("RenderedPage instanceMap is empty. Check the configuration database.");
    } else {
      RenderedPage.instanceMap.get(Window.getTitle()).materialize();
    }
    return RenderedPage.mainPanel;
  }
  
  private void materialize() {
    for(int x = 0; x <= RenderedPage.mainMenuLinks.size() - 1; x++) {
      if(RenderedPage.mainMenuLinks.get(x).getTargetHistoryToken() == pageTitle) {
        RenderedPage.mainMenuLinks.get(x).setHTML(mainMenuLinkSelected);
        RenderedPage.mainPanel.add(logo, logoLeft, logoTop);
      }
    }
    if (images.size() > 0)
      scheduleImageTransition();
    if (messages.size() > 0)
      scheduleMessageTransition();
    if (pageMenuItems.size() > 0) {
      pageMenu.setWidth(pageMenuWidth + "px");
      for (int x = 0; x < pageMenuItems.size() - 1; x++) {
        pageMenuItems.get(x).setWidth(pageMenuWidth / pageMenuItems.size() + "px");
        pageMenu.addItem(pageMenuItems.get(x));
        pageMenu.addSeparator();
      }
      pageMenu.addItem(pageMenuItems.get(pageMenuItems.size() - 1));
      RenderedPage.mainPanel.add(pageMenu, pageMenuLeft, pageMenuTop);
      
      RenderedPage.mainPanel.add(pageMenuDisclosure, pageMenuLeft - 14, pageMenuTop + 30);
    }
  }
  
  private void dematerialize() {
    for(int x = 0; x <= RenderedPage.mainMenuLinks.size() - 1; x++) {
      if(RenderedPage.mainMenuLinks.get(x).getTargetHistoryToken() == pageTitle) {
        RenderedPage.mainMenuLinks.get(x).setHTML(mainMenuLink);
        RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(logo));
      }
    }
    if (images.size() > 0)
      cancelImageTransition();
    if (images.size() > 0)
      cancelMessageTransition();
    if (pageMenuItems.size() > 0) {
      RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(pageMenu));
      pageMenu.clearItems();
      RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(pageMenuDisclosure));
    }
  }

  private void sizePageElements() {
    logoHeight = (int) Math.round(logoHeightPercent * RenderedPage.windowHeight);
    logoWidth = (int) Math.round(logoHeight * logoWidthPercent);
    logoLeft = (int) Math.round(logoLeftPercent * RenderedPage.windowWidth);
    logoTop = (int) Math.round(logoTopPercent);
    logo.setPixelSize(logoWidth, logoHeight);
    imagesLeft = (int) Math.round(imagesLeftPercent * RenderedPage.windowWidth);
    imagesTop = (int) Math.round(imagesTopPercent * RenderedPage.windowHeight);
    imagesWidth = (int) Math.round(imagesWidthPercent * RenderedPage.windowWidth);
    imagesHeight = (int) Math.round(imagesWidth / imagesHeightPercent);
    if (images.size() > 0)
      images.get(imageCur).setPixelSize(imagesWidth, imagesHeight);
  }
  
  private void positionPageElements() {
    RenderedPage.mainPanel.setWidgetPosition(logo, logoLeft, logoTop);
    RenderedPage.mainPanel.setWidgetPosition(images.get(imageCur), imagesLeft, imagesTop);
  }

  private static void sizeLayoutElements() {
    RenderedPage.windowHeight = Window.getClientHeight();
    RenderedPage.windowWidth = Window.getClientWidth();
    RenderedPage.mainPanel.setHeight(RenderedPage.windowHeight + "px");
    RenderedPage.mainPanel.setWidth(RenderedPage.windowWidth + "px");
    RenderedPage.locLangSelectorWidth = (int) Math.round(RenderedPage.locLangSelectorWidthPercent * RenderedPage.windowWidth);
    RenderedPage.locLangSelectorHeight = (int) Math.round(RenderedPage.locLangSelectorHeightPercent * RenderedPage.windowHeight);
    RenderedPage.locLangSelectorTop = (int) Math.round(RenderedPage.locLangSelectorTopPercent * RenderedPage.windowHeight);
    RenderedPage.locLangSelectorLeft = (int) Math.round(RenderedPage.locLangSelectorLeftPercent * RenderedPage.windowWidth);
    RenderedPage.locLangSelector.setSize(RenderedPage.locLangSelectorWidth + "px", RenderedPage.locLangSelectorHeight + "px");
    RenderedPage.searchFieldWidth = (int) Math.round(RenderedPage.searchFieldWidthPercent * RenderedPage.windowWidth);;
    RenderedPage.searchFieldHeight = (int) Math.round(RenderedPage.searchFieldHeightPercent * RenderedPage.windowHeight);
    RenderedPage.searchFieldLeft = (int) Math.round(RenderedPage.searchFieldLeftPercent * RenderedPage.windowWidth);
    RenderedPage.searchFieldTop = (int) Math.round(RenderedPage.searchFieldTopPercent * RenderedPage.windowHeight);
    RenderedPage.searchField.setPixelSize(RenderedPage.searchFieldWidth, RenderedPage.searchFieldHeight);
    RenderedPage.searchButtonHeight = (int) Math.round(RenderedPage.searchButtonHeightPercent * RenderedPage.windowHeight);
    RenderedPage.searchButtonWidth = (int) Math.round(RenderedPage.searchButtonHeight * RenderedPage.searchButtonWidthPercent);
    RenderedPage.searchButtonLeft = (int) Math.round(RenderedPage.searchButtonLeftPercent * RenderedPage.windowWidth);
    RenderedPage.searchButtonTop = (int) Math.round(RenderedPage.searchButtonTopPercent * RenderedPage.windowHeight);
    RenderedPage.searchButton.setPixelSize(RenderedPage.searchButtonWidth, RenderedPage.searchButtonHeight);
    RenderedPage.mainMenuTop = (int) Math.round(RenderedPage.mainMenuTopPercent * RenderedPage.windowHeight);
    RenderedPage.mainMenu.getElement().getStyle().setWidth(RenderedPage.windowWidth, Style.Unit.PX);
  }
  
  private static void positionLayoutElements() {
    RenderedPage.mainPanel.setWidgetPosition(RenderedPage.locLangSelector, RenderedPage.locLangSelectorLeft, RenderedPage.locLangSelectorTop);
    RenderedPage.mainPanel.setWidgetPosition(RenderedPage.searchField, RenderedPage.searchFieldLeft, RenderedPage.searchFieldTop);
    RenderedPage.mainPanel.setWidgetPosition(RenderedPage.searchButton, RenderedPage.searchButtonLeft, RenderedPage.searchButtonTop);
    RenderedPage.mainPanel.setWidgetPosition(RenderedPage.mainMenu, RenderedPage.mainMenuLeft, RenderedPage.mainMenuTop);
  }
  
  public void setLogo(String imageUrl) {
    logo.setUrl(imageUrl);
  }
  
  public void setMainMenuLink(String html) {
    mainMenuLink = html;
  }
  
  public void setMainMenuLinkSelected(String html) {
    mainMenuLinkSelected = html;
  }
  
  private String getMainMenuLink() {
    return mainMenuLink;
  }
  
  public void addImage(String imageUrl) {
    images.add(new Image(imageUrl));
  }
  
  private void scheduleImageTransition() {
    increaseImageOpacityTimer = new Timer () {
      @Override
      public void run() {
        if (imageOpacityCur <= imageOpacityMax)
          images.get(imageCur).getElement().getStyle().setOpacity(imageOpacityCur = imageOpacityCur + imageOpacityStep);
        else {
          increaseImageOpacityTimerFlag = false;
          increaseImageOpacityTimer.cancel();
        }
      }
    };
    decreaseImageOpacityTimer = new Timer () {
      @Override
      public void run() {
        if (imageOpacityCur >= imageOpacityMin) {
          images.get(imageCur).getElement().getStyle().setOpacity(imageOpacityCur = imageOpacityCur - imageOpacityStep);
        } else if (imageOpacityCur < imageOpacityMin) {
          RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(images.get(imageCur)));
          imageCur++;
          if (imageCur == images.size())
            imageCur = 0;
          RenderedPage.mainPanel.insert(images.get(imageCur), imagesLeft, imagesTop, 0);
          images.get(imageCur).setPixelSize(imagesWidth, imagesHeight);
          increaseImageOpacityTimer.scheduleRepeating(imageOpacityStepInterval);
          increaseImageOpacityTimerFlag = true;
          decreaseImageOpacityTimerFlag = false;
          decreaseImageOpacityTimer.cancel();
        } 
      }
    };
    imageTransitionTimer = new Timer () {
      @Override
      public void run() {
        decreaseImageOpacityTimer.scheduleRepeating(imageOpacityStepInterval);
        decreaseImageOpacityTimerFlag = true;
      }
    };
    imageOpacityCur = 0;
    for (imageCur = 0; imageCur < images.size(); imageCur++) {
      images.get(imageCur).getElement().getStyle().setOpacity(imageOpacityCur);
      images.get(imageCur).setPixelSize(imagesWidth, imagesHeight);
    }
    imageCur = 0;
    RenderedPage.mainPanel.insert(images.get(imageCur), imagesLeft, imagesTop, 0);
    increaseImageOpacityTimer.scheduleRepeating(imageOpacityStepInterval);
    increaseImageOpacityTimerFlag = true;
    imageTransitionTimer.scheduleRepeating(imageTransitionInterval);
    imageTransitionTimerFlag = true;
  }

  private void cancelImageTransition() {
    cancelImageTransitionTimer = new Timer () {
      @Override
      public void run() {
        if (imageOpacityCur > imageOpacityMin) {
          images.get(imageCur).getElement().getStyle().setOpacity(imageOpacityCur = imageOpacityCur - imageOpacityStep);
        } else {
          RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(images.get(imageCur)));
          cancelImageTransitionTimer.cancel();
        }
      }
    };
    if (imageTransitionTimerFlag)
      imageTransitionTimer.cancel();
    if (increaseImageOpacityTimerFlag)
      increaseImageOpacityTimer.cancel();
    if (decreaseImageOpacityTimerFlag)
      decreaseImageOpacityTimer.cancel();
    cancelImageTransitionTimer.scheduleRepeating(imageOpacityStepInterval);
  }
  
  private void scheduleMessageTransition() {
    messageDelayTimer = new Timer() {
      @Override
      public void run() {
        RenderedPage.mainPanel.insert(messages.get(messageCur).getMessage(), messages.get(messageCur).messageLeft, messages.get(messageCur).messageTop, RenderedPage.mainPanel.getWidgetIndex(RenderedPage.mainMenu));
        initialIncreaseMessageOpacityTimer.scheduleRepeating(messageOpacityStepInterval);
        initialIncreaseMessageOpacityTimerFlag = true;
        messageDelayTimerFlag = false;
      }
    };
    initialIncreaseMessageOpacityTimer = new Timer () {
      @Override
      public void run() {
        if (messageOpacityCur <= messageOpacityMax)
          messages.get(messageCur).getMessage().getElement().getStyle().setOpacity(messageOpacityCur = messageOpacityCur + messageOpacityStep);
        else {
          messageTransitionTimer.scheduleRepeating(messageTransitionInterval);
          messageTransitionTimerFlag = true;
          initialIncreaseMessageOpacityTimerFlag = false;
          initialIncreaseMessageOpacityTimer.cancel();
        }
      }
    };
    messageTransitionTimer = new Timer () {
      @Override
      public void run() {
        decreaseMessageOpacityTimer.scheduleRepeating(messageOpacityStepInterval);
        decreaseMessageOpacityTimerFlag = true;
      }
    };
    decreaseMessageOpacityTimer = new Timer () {
      @Override
      public void run() {
        if (messageOpacityCur >= messageOpacityMin) {
          messages.get(messageCur).getMessage().getElement().getStyle().setOpacity(messageOpacityCur = messageOpacityCur - messageOpacityStep);
        } else if (messageOpacityCur < messageOpacityMin) {
          RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(messages.get(messageCur).getMessage()));
          messageCur++;
          if (messageCur == messages.size())
            messageCur = 0;
          RenderedPage.mainPanel.insert(messages.get(messageCur).getMessage(), messages.get(messageCur).getLeft(), messages.get(messageCur).getTop(), RenderedPage.mainPanel.getWidgetIndex(RenderedPage.mainMenu));
          increaseMessageOpacityTimer.scheduleRepeating(messageOpacityStepInterval);
          increaseMessageOpacityTimerFlag = true;
          decreaseMessageOpacityTimerFlag = false;
          decreaseMessageOpacityTimer.cancel();
        } 
      }
    };
    increaseMessageOpacityTimer = new Timer () {
      @Override
      public void run() {
        if (messageOpacityCur <= messageOpacityMax)
          messages.get(messageCur).getMessage().getElement().getStyle().setOpacity(messageOpacityCur = messageOpacityCur + messageOpacityStep);
        else {
          increaseMessageOpacityTimerFlag = false;
          increaseMessageOpacityTimer.cancel();
        }
      }
    };
    messageOpacityCur = 0;
    for (messageCur = 0; messageCur < messages.size(); messageCur++)
      messages.get(messageCur).getMessage().getElement().getStyle().setOpacity(messageOpacityCur);
    messageCur = 0;
    messageDelayTimer.schedule(2000);
    messageDelayTimerFlag = true;
  }

  private void cancelMessageTransition() {
    cancelMessageTransitionTimer = new Timer () {
      @Override
      public void run() {
        if (messageOpacityCur > messageOpacityMin) {
          messages.get(messageCur).getMessage().getElement().getStyle().setOpacity(messageOpacityCur = messageOpacityCur - messageOpacityStep);
        } else {
          RenderedPage.mainPanel.remove(RenderedPage.mainPanel.getWidgetIndex(messages.get(messageCur).getMessage()));
          //Window.alert("cancelMessageTransitionTimer canceled");
          cancelMessageTransitionTimer.cancel();
        }
      }
    };
    if (messageDelayTimerFlag)
      messageDelayTimer.cancel();
    if (initialIncreaseMessageOpacityTimerFlag)
      initialIncreaseMessageOpacityTimer.cancel();
    if (messageTransitionTimerFlag)
      messageTransitionTimer.cancel();
    if (decreaseMessageOpacityTimerFlag)
      decreaseMessageOpacityTimer.cancel();
    if (increaseMessageOpacityTimerFlag)
      increaseMessageOpacityTimer.cancel();
    cancelMessageTransitionTimer.scheduleRepeating(messageOpacityStepInterval);
  }
  
  @Override
  public void onResize(ResizeEvent event) {
    sizePageElements();
    if(pageTitle.equals(Window.getTitle())) {
      positionPageElements();
      RenderedPage.sizeLayoutElements();
      RenderedPage.positionLayoutElements();
    }
  }

  @Override
  public void onValueChange(ValueChangeEvent<String> event) {
    if(event.getValue().equals(pageTitle)) {
      RenderedPage.instanceMap.get(Window.getTitle()).dematerialize();
      RenderedPage.instanceMap.get(event.getValue()).materialize();
      Window.setTitle(event.getValue());
    }
  }

  public void addMessage(String headerText, String bodyText, String color, double widthPercent, double leftPercent, double topPercent) {
    messages.add(new Message(widthPercent, leftPercent, topPercent, color, headerText, bodyText));
  }
  
  private class Message {
    private HTMLPanel message;
    private int messageWidth;
    private int messageLeft;
    private int messageTop;
    private String messageTemplate = "<table id=messageId style='padding:5px;' width=messageWidthpx border=0 bgcolor=messageColor>" +
                     "<tr>" +
                     "  <td id=messageHeader style='font-family:Raleway,sans-serif;font-style:normal;font-weight:100;letter-spacing:0em;word-spacing:0em;line-height:1.4;font-size:38px;color:#ffffff;'>" +
                     "  messageHeader" +
                     "  </td>" +
                     "</tr>" +
                     "<tr>" +
                     "  <td id=messageContent style='font-family:Raleway,sans-serif;font-style:normal;font-weight:100;letter-spacing:0em;word-spacing:0em;line-height:1.4;font-size:18px;color:#ffffff;'>" +
                     "  messageContent" +
                     "  </td>" +
                     "</tr>" +
                     "</table>";
    
    public Message(double widthPercent, double leftPercent, double topPercent,  String color, String messageHeaderText, String messageContentText) {
      messageWidth = (int) Math.round(widthPercent * RenderedPage.windowWidth);
      messageLeft = (int) Math.round(leftPercent * RenderedPage.windowWidth);
      messageTop = (int) Math.round(topPercent * RenderedPage.windowHeight);
      if (color.equals("transparent")) {
        messageTemplate = messageTemplate.replace("bgcolor=messageColor", "");
      } else {
        messageTemplate = messageTemplate.replace("messageColor", color);
      }
      messageTemplate = messageTemplate.replace(" messageHeader", messageHeaderText);
      messageTemplate = messageTemplate.replace(" messageContent", messageContentText);
      messageTemplate = messageTemplate.replace("messageWidthpx", messageWidth + "px");
      message = new HTMLPanel(messageTemplate);
    }
    
    public HTMLPanel getMessage() {
      return message;
    }
    
    @SuppressWarnings("unused")
    public int getWidth() {
      return messageWidth;
    }
    
    public int getLeft() {
      return messageLeft;
    }
    
    public int getTop() {
      return messageTop;
    }
  }
  
  public void addPageMenuItem(String name, String html) {
    pageMenuItems.add(new MenuItem(SafeHtmlUtils.fromTrustedString("<center>" + name + "</center>"), new PageMenuCommand(name, html)));
  }

  private class PageMenuCommand implements Command {

    private Frame pageMenuPanel = new Frame();
    
    //private boolean thisMenuPanelDisclosed = false;
    private String  pageMenuItemName = null;
    //      String  pageMenuItemDisclosed = "";
    
    public PageMenuCommand(String name, String html) {
      pageMenuItemName = name;
      pageMenuPanel.setUrl(html);
      pageMenuPanel.getElement().getStyle().setHeight((Window.getClientHeight() - (pageMenuTop + pageMenu.getOffsetHeight())), Unit.PX);
      pageMenuPanel.getElement().getStyle().setWidth(pageMenuWidth, Unit.PX);
    }
    
    @Override
    public void execute() {
      pageMenuPanel.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
      pageMenuDisclosure.setAnimationEnabled(true);
      pageMenuDisclosure.setPixelSize(pageMenuWidth + 20, (RenderedPage.windowHeight - (pageMenuTop)));
      if (!pageMenuDisclosure.isOpen()) {
        //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
        pageMenuDisclosure.setContent(pageMenuPanel);
        pageMenuDisclosure.setOpen(true);
        pageMenuItemDisclosed = pageMenuItemName;
        //thisMenuPanelDisclosed = true;
        //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
      } else {
        if (pageMenuItemDisclosed.equals(pageMenuItemName)) {
          //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
          pageMenuDisclosure.setOpen(false);
          //thisMenuPanelDisclosed = false;
          pageMenuItemDisclosed = "";
          //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);      
        } else {
          //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
          pageMenuDisclosure.setOpen(false);
          pageMenuDisclosure.setContent(pageMenuPanel);
          pageMenuDisclosure.setOpen(true);
          //thisMenuPanelDisclosed = true;
          pageMenuItemDisclosed = pageMenuItemName;
          //Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);  
        }
      }
      /*
      if (!pageMenuDisclosure.isOpen()) {
        Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
        pageMenuDisclosure.setContent(pageMenuPanel);
        pageMenuDisclosure.setOpen(true);
        pageMenuItemDisclosed = pageMenuItemName;
        thisMenuPanelDisclosed = true;
        Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
      } else {
        if (thisMenuPanelDisclosed) {
          Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
          pageMenuDisclosure.setOpen(false);
          thisMenuPanelDisclosed = false;
          pageMenuItemDisclosed = "";
          Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
        } else {
          Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
          pageMenuDisclosure.setOpen(false);
          pageMenuDisclosure.setContent(pageMenuPanel);
          pageMenuDisclosure.setOpen(true);
          thisMenuPanelDisclosed = true;
          pageMenuItemDisclosed = pageMenuItemName;
          Window.alert("pageMenuDisclosure=" + pageMenuDisclosure.isOpen() + ", pageMenuItemDisclosed=" + pageMenuItemDisclosed + ", thisMenuPanelDisclosed=" + thisMenuPanelDisclosed);
        }
      }
      */
      /*
      if (pageMenuDisclosure.isOpen()) {
        Window.alert("pageMenuDisclosure=isOpen");
        if (thisMenuPanelDisclosed) {
          Window.alert("thisMenuPanelDisclosed=true");
          pageMenuDisclosure.setOpen(false);
          thisMenuPanelDisclosed = false;
        } else {
          Window.alert("thisMenuPanelDisclosed=false");
          pageMenuDisclosure.setOpen(false);
          pageMenuDisclosure.setContent(pageMenuPanel);
          pageMenuDisclosure.setOpen(true);
          thisMenuPanelDisclosed = true;
        }
      } else {
        Window.alert("pageMenuDisclosure=isClosed");
        pageMenuDisclosure.setContent(pageMenuPanel);
        pageMenuDisclosure.setOpen(true);
        thisMenuPanelDisclosed = true;
      }
      */
    }
  }
}
