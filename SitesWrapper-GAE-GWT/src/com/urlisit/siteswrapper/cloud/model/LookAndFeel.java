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
package com.urlisit.siteswrapper.cloud.model;

/**
 * Dictates the look and feel of the client generated UI
 * 
 * @author  Todd Url <toddurl @ urlisit.com>
 * @version 1.0
 * @since 04-01-2012
 */
public enum LookAndFeel {
  KONINKLIJKE ("Koninklijke",
      "0",     // windowMargin
      ".14",   // logoHeightPercent
      ".25",  // logoWidthPercent
      "0",    // logoTopPercent
      ".1",   // logoLeftPercent
      "true",  // locLangSelectorEnabled
      ".025",  // locLangSelectorHeightPercent
      ".18",   // locLangSelectorWidthPercent
      ".03",   // locLangSelectorTopPercent
      ".68",   // locLangSelectorLeftPercent
      "true",  // searchFieldEnabled
      ".015",  // searchFieldHeightPercent
      ".14",   // searchFieldWidthPercent
      ".08",   // searchFieldTopPercent
      ".68",   // searchFieldLeftPercent
      ".032",  // searchButtonHeightPercent
      "1.666", // searchButtonWidthPercent
      ".08",   // searchButtonTopPercent
      ".835",  // searchButtonLeftPercent
      ".14",   // mainMenuTopPercent
      "0",     // mainMenuLeft
      "1.84",  // imagesHeightPercent
      "1",     // imagesWidthPercent
      ".17",   // imagesTopPercent
      "0",     // imagesLeftPercent
      "<span style=\"font-family:FONT_FAMILY,arial,verdana;cursor:pointer;fon" +
      "t-weight:bold;font-size:FONT_SIZE;\"><font color=FONT_COLOR>&rarr;&nbs" +
      "p;PAGE_NAME</font></span>", // mainMenuSelectionHtml
      "<span style=\"font-family:FONT_FAMILY,arial,verdana;cursor:pointer;fon" +
      "t-weight:bold;font-size:FONT_SIZE;\"><font color=FONT_COLOR>&darr;&nbs" +
      "p;&nbsp;PAGE_NAME</font></span>", // mainMenuSelectedHtml
      "Reminiscent of www.usa.lighting.philips.com"),
      GHOST ("Ghost",
          "0",     // windowMargin
          ".14",   // logoHeightPercent
          ".08",   // logoWidthPercent
          ".025",  // logoTopPercent
          ".1",    // logoLeftPercent
          "true",  // locLangSelectorEnabled
          ".025",  // locLangSelectorHeightPercent
          ".18",   // locLangSelectorWidthPercent
          ".03",   // locLangSelectorTopPercent
          ".68",   // locLangSelectorLeftPercent
          "true",  // searchFieldEnabled
          ".015",  // searchFieldHeightPercent
          ".14",   // searchFieldWidthPercent
          ".08",   // searchFieldTopPercent
          ".68",   // searchFieldLeftPercent
          ".032",  // searchButtonHeightPercent
          "1.666", // searchButtonWidthPercent
          ".08",   // searchButtonTopPercent
          ".835",  // searchButtonLeftPercent
          ".12",   // mainMenuTopPercent
          ".35",   // mainMenuLeft
          "1",     // imagesHeightPercent
          "1.0",   // imagesWidthPercent
          ".17",   // imagesTopPercent
          "0",     // imagesLeftPercent
          "<span style=\"font-family:FONT_FAMILY,arial,verdana;cursor:pointer;font-weight:400;" +
          "font-size:FONT_SIZE;border-bottom:3px solid FONT_COLOR\"><font color=FONT_COLOR>" +
          "PAGE_NAME&nbsp;&nbsp;&nbsp;</font></span>", // mainMenuSelectionHtml
          "<span style=\"font-family:FONT_FAMILY,arial,verdana;cursor:pointer;font-weight:400;" +
          "font-size:FONT_SIZE;border-bottom:3px solid FONT_COLOR\"><font color=FONT_COLOR>" +
          "PAGE_NAME&nbsp;&nbsp;&nbsp;</font></span>", // mainMenuSelectedHtml
          "Classy and minimalist back and white theme in the spirit of Ghosts in Gothenburg ghostgames.com"),
  URL_IS_IT ("URL IS/IT",
      "0",     // windowMargin
      ".10",   // logoHeightPercent
      "1",  // logoWidthPercent
      "0",    // logoTopPercent
      ".1",   // logoLeftPercent
      "true",  // locLangSelectorEnabled
      ".025",  // locLangSelectorHeightPercent
      ".18",   // locLangSelectorWidthPercent
      ".03",   // locLangSelectorTopPercent
      ".68",   // locLangSelectorLeftPercent
      "true",  // searchFieldEnabled
      ".015",  // searchFieldHeightPercent
      ".14",   // searchFieldWidthPercent
      ".08",   // searchFieldTopPercent
      ".68",   // searchFieldLeftPercent
      ".032",  // searchButtonHeightPercent
      "1.666", // searchButtonWidthPercent
      ".08",   // searchButtonTopPercent
      ".835",  // searchButtonLeftPercent
      ".14",   // mainMenuTopPercent
      "0",     // mainMenuLeft
      "1.84",  // imagesHeightPercent
      "1",     // imagesWidthPercent
      ".17",   // imagesTopPercent
      "0",     // imagesLeftPercent
      "<span><button style=\"font-family:FONT_FAMILY,arial,verdana;cursor:poi" +
      "nter;font-size:FONT_SIZE;background-color:#000000;color:FONT_COLOR;fon" +
      "t-weight:bold;height:100%;width:100%;\" onmouseover=\"this.style.color" +
      "='HOVER_COLOR'\" onmouseout=\"this.style.color='FONT_COLOR'\">PAGE_NAM" +
      "E</button></font></span>", // mainMenuSelectionHtml
      "<span><button style=\"font-family:FONT_FAMILY,arial,verdana;cursor:poi" +
      "nter;font-size:FONT_SIZE;background-color:#111111;color:FONT_COLOR;fon" +
      "t-weight:bold;height:100%;width:100%;\" onmouseover=\"this.style.color" +
      "='HOVER_COLOR'\" onmouseout=\"this.style.color='FONT_COLOR'\">PAGE_NAM" +
      "E</button></font></span>", // mainMenuSelectedHtml
      "Looks like toddurl.appspot.com");

  /**
   * Line separator
   */
  private static final String EOL = "\n"; // $codepro.audit.disable platformSpecificLineSeparator

  /**
   * A more human readable string representation version of the constant
   */
  private final String label;

  /**
   * Used to initially size, position and dynamically resize and position the
   * elements of a page in response to changes in the browsers window size.
   * The class field WindowMargin gets initialized from the value associated
   * with this enum via rpc.
   */
  private final String windowMargin;

  /**
   * The height of the logo specified as percent (in decimal) of the total (or
   * current) page height
   */
  private final String logoHeightPercent;

  /**
   * Width of the logo specified as a percent of logoHeight in decimal (i.e.
   * specify 150% as 1.5). Used to maintain the aspect ratio of the image in
   * Internet Explorer
   */
  private final String logoWidthPercent;

  /**
   * Position of Image logo from the top of the page specified as a percent of
   * the total page height in decimal (i.e. specify 10% as .1)
   */
  private final String logoTopPercent;
  
  /**
   * Position of Image logo from the left edge of the page specified as a
   * percent of the total page width in decimal (i.e. specify 10% as .1)
   */
  private final String logoLeftPercent;

  /**
   * Determines if a location/language (I18N localization) selector is made
   * available throughout the application.
   */
  private final String locLangSelectorEnabled;

  /**
   * If enabled, the locLangSelector height as a percent of the total page
   * height in decimal (i.e. specify 2.5% as .025)
   */
  private final String locLangSelectorHeightPercent;
  
  /**
   * If enabled, the locLangSelector width as a percent of the total page width
   * in decimal (i.e. specify 18% as .18)
   */
  private final String locLangSelectorWidthPercent;
  
  /**
   * If enabled, the locLangSelector distance from top of page as a percent of
   * the total page height in decimal (i.e. specify 3% as .03)
   */
  private final String locLangSelectorTopPercent;

  /**
   * If enabled, the locLangSelector distance from left edge of page as a
   * percent of the total page width in decimal (i.e. specify 68% as .68)
   */
  private final String locLangSelectorLeftPercent;

  /**
   * Determines if a search box is made available throughout the application.
   */
  private final String searchFieldEnabled;

  /**
   * If enabled, the searchField height expressed as a percent of the total page
   * height in decimal (i.e. specify 1.5% as .015)
   */
  private final String searchFieldHeightPercent;
  
  /**
   * IF enabled, the searchField width as a percent of the total page width in
   * decimal (i.e. specify 14.38% as .1438)
   */
  private final String searchFieldWidthPercent;
  
  /**
   * If enabled, the searchField position from top of page expressed as a
   * percent of the total page height in decimal (i.e. specify 8% as .08)
   */
  private final String searchFieldTopPercent;
  
  /**
   * If enabled, the searchField distance from left edge of page as a percent of
   * the total page width in decimal (i.e. specify 68% as .68)
   */
  private final String searchFieldLeftPercent;
  
  /**
   * If search enabled, the searchButton height as a percent of the total page
   * height in decimal (i.e. specify 1.5% as .015)
   */
  private final String searchButtonHeightPercent;
  
  /**
   * If searchFieldEnabled, the searchButton image width as percent of
   * searchButtonHeight (i.e. specify 16.66% as 1.666)
   */
  private final String searchButtonWidthPercent;
  
  /**
   * If searchFieldEnabled, the searchButton position from top of page as a
   * percent of the total page height in decimal (i.e. specify 8% as .08)
   */
  private final String searchButtonTopPercent;
  
  /**
   * If searchFieldEnabled, the searchButton distance from left edge of page as
   * a percent of the total page width in decimal (i.e. specify 83.5% as .835)
   */
  private final String searchButtonLeftPercent;
  
  /**
   * Position of FlexTable mainMenu from top of page expressed as a percent of
   * the total page height in decimal (i.e. specify 14% as .14)
   */
  private final String mainMenuTopPercent;
  
  /**
   * Used to position HTMLPanel mainMenu
   */
  private final String mainMenuLeft;
  
  /**
   * Height of images specified as a percent of imagesWidth in decimal (i.e.
   * specify 184% as 1.84). Used to maintain the aspect ratio of the image in
   * Internet Explorer
   */
  private final String imagesHeightPercent;
  
  /**
   * Width of images from left edge of page as a percent of the total page width
   * in decimal (i.e. specify 100% as 1)
   */
  private final String imagesWidthPercent;
  
  /**
   * Position of images from top of page as a percent of the total page height
   * in decimal (i.e. specify 8% as .08)
   */
  private final String imagesTopPercent;
  
  /**
   * Position of images from left edge of page as a percent of the total page
   * width in decimal (i.e. specify 0% as 0)
   */
  private final String imagesLeftPercent;
  
  /**
   * HTML assigned to the selection HyperLink in the MainMenu
   */
  private final String mainMenuSelectionHtml;

  /**
   * HTML assigned to the selected HyperLink in the MainMenu
   */
  private final String mainMenuSelectedHtml;

  /**
   * Description of the look and feel this constant renders as.
   */
  private final String description;

  LookAndFeel(String label, String windowMargin, String logoHeightPercent,
      String logoWidthPercent, String logoTopPercent, String logoLeftPercent,
      String locLangSelectorEnabled, String locLangSelectorHeightPercent,
      String locLangSelectorWidthPercent, String locLangSelectorTopPercent,
      String locLangSelectorLeftPercent, String searchFieldEnabled,
      String searchFieldHeightPercent, String searchFieldWidthPercent,
      String searchFieldTopPercent, String searchFieldLeftPercent,
      String searchButtonHeightPercent, String searchButtonWidthPercent,
      String searchButtonTopPercent, String searchButtonLeftPercent,
      String mainMenuTopPercent, String mainMenuLeft,
      String imagesHeightPercent, String imagesWidthPercent,
      String imagesTopPercent, String imagesLeftPercent,
      String mainMenuSelectionHtml, String mainMenuSelectedHtml,
      String description) {
    this.label = label;
    this.windowMargin = windowMargin;
    this.logoHeightPercent = logoHeightPercent;
    this.logoWidthPercent = logoWidthPercent;
    this.logoTopPercent = logoTopPercent;
    this.logoLeftPercent = logoLeftPercent;
    this.locLangSelectorEnabled = locLangSelectorEnabled;
    this.locLangSelectorHeightPercent = locLangSelectorHeightPercent;
    this.locLangSelectorWidthPercent = locLangSelectorWidthPercent;
    this.locLangSelectorTopPercent = locLangSelectorTopPercent;
    this.locLangSelectorLeftPercent = locLangSelectorLeftPercent;
    this.searchFieldEnabled = searchFieldEnabled;
    this.searchFieldHeightPercent = searchFieldHeightPercent;
    this.searchFieldWidthPercent = searchFieldWidthPercent;
    this.searchFieldTopPercent = searchFieldTopPercent;
    this.searchFieldLeftPercent = searchFieldLeftPercent;
    this.searchButtonHeightPercent = searchButtonHeightPercent;
    this.searchButtonWidthPercent = searchButtonWidthPercent;
    this.searchButtonTopPercent = searchButtonTopPercent;
    this.searchButtonLeftPercent = searchButtonLeftPercent;
    this.mainMenuTopPercent = mainMenuTopPercent;
    this.mainMenuLeft = mainMenuLeft;
    this.mainMenuSelectionHtml = mainMenuSelectionHtml;
    this.mainMenuSelectedHtml = mainMenuSelectedHtml;
    this.imagesHeightPercent = imagesHeightPercent;
    this.imagesWidthPercent = imagesWidthPercent;
    this.imagesTopPercent = imagesTopPercent;
    this.imagesLeftPercent = imagesLeftPercent;
    this.description = description;
  }

  public String getLabel() {
    return label;
  }

  public String getWindowMargin() {
    return windowMargin;
  }

  public String getLogoHeightPercent() {
    return logoHeightPercent;
  }

  public String getLogoWidthPercent() {
    return logoWidthPercent;
  }

  public String getLogoTopPercent() {
    return logoTopPercent;
  }

  public String getLogoLeftPercent() {
    return logoLeftPercent;
  }

  public String getLocLangSelectorEnabled() {
    return locLangSelectorEnabled;
  }

  public String getLocLangSelectorHeightPercent() {
    return locLangSelectorHeightPercent;
  }

  public String getLocLangSelectorWidthPercent() {
    return locLangSelectorWidthPercent;
  }

  public String getLocLangSelectorTopPercent() {
    return locLangSelectorTopPercent;
  }

  public String getLocLangSelectorLeftPercent() {
    return locLangSelectorLeftPercent;
  }

  public String getSearchFieldEnabled() {
    return searchFieldEnabled;
  }

  public String getSearchFieldHeightPercent() {
    return searchFieldHeightPercent;
  }

  public String getSearchFieldWidthPercent() {
    return searchFieldWidthPercent;
  }

  public String getSearchFieldTopPercent() {
    return searchFieldTopPercent;
  }

  public String getSearchFieldLeftPercent() {
    return searchFieldLeftPercent;
  }
  
  public String getSearchButtonHeightPercent() {
    return searchButtonHeightPercent;
  }
  
  public String getSearchButtonWidthPercent() {
    return searchButtonWidthPercent;
  }
  
  public String getSearchButtonTopPercent() {
    return searchButtonTopPercent;
  }
  
  public String getSearchButtonLeftPercent() {
    return searchButtonLeftPercent;
  }
  
  public String getMainMenuTopPercent() {
    return mainMenuTopPercent;
  }
  
  public String getMainMenuLeft() {
    return mainMenuLeft;
  }
  
  public String getImagesHeightPercent() {
    return imagesHeightPercent;
  }
  
  public String getImagesWidthPercent() {
    return imagesWidthPercent;
  }
  
  public String getImagesTopPercent() {
    return imagesTopPercent;
  }
  
  public String getImagesLeftPercent() {
    return imagesLeftPercent;
  }
  
  public String getMainMenuSelectionHtml() {
    return mainMenuSelectionHtml;
  }
  
  public String getMainMenuSelectedHtml() {
    return mainMenuSelectedHtml;
  }
  
  public String getDescription() {
    return description;
  }
  
  /**
   * Output object as a named JavaScript object to be used as an associated array of name/value
   * pairs to be bound to a GWT Dictionary
   * 
   * @see com.google.gwt.i18n.client.Dictionary
   */
  public String toDictionary() {
    StringBuilder dictionary = new StringBuilder("<script>").append(EOL);
    dictionary.append("var LookAndFeel = {").append(EOL);
    dictionary.append("label: " + "\"" + label + "\",").append(EOL);
    dictionary.append("windowMargin: " + "\"" + windowMargin + "\",").append(EOL);
    dictionary.append("logoHeightPercent: " + "\"" + logoHeightPercent + "\",").append(EOL);
    dictionary.append("logoWidthPercent: " + "\"" + logoWidthPercent + "\",").append(EOL);
    dictionary.append("logoTopPercent: " + "\"" + logoTopPercent + "\",").append(EOL);
    dictionary.append("logoLeftPercent: " + "\"" + logoLeftPercent + "\",").append(EOL);
    dictionary.append("locLangSelectorEnabled: " + "\"" + locLangSelectorEnabled + "\",").append(EOL);
    dictionary.append("locLangSelectorHeightPercent: " + "\"" + locLangSelectorHeightPercent + "\",").append(EOL);
    dictionary.append("locLangSelectorWidthPercent: " + "\"" + locLangSelectorWidthPercent + "\",").append(EOL);
    dictionary.append("locLangSelectorTopPercent: " + "\"" + locLangSelectorTopPercent + "\",").append(EOL);
    dictionary.append("locLangSelectorLeftPercent: " + "\"" + locLangSelectorLeftPercent + "\",").append(EOL);
    dictionary.append("searchFieldEnabled: " + "\"" + searchFieldEnabled + "\",").append(EOL);
    dictionary.append("searchFieldHeightPercent: " + "\"" + searchFieldHeightPercent + "\",").append(EOL);
    dictionary.append("searchFieldWidthPercent: " + "\"" + searchFieldWidthPercent + "\",").append(EOL);
    dictionary.append("searchFieldTopPercent: " + "\"" + searchFieldTopPercent + "\",").append(EOL);
    dictionary.append("searchFieldLeftPercent: " + "\"" + searchFieldLeftPercent + "\",").append(EOL);
    dictionary.append("searchButtonHeightPercent: " + "\"" + searchButtonHeightPercent + "\",").append(EOL);
    dictionary.append("searchButtonWidthPercent: " + "\"" + searchButtonWidthPercent + "\",").append(EOL);
    dictionary.append("searchButtonTopPercent: " + "\"" + searchButtonTopPercent + "\",").append(EOL);
    dictionary.append("searchButtonLeftPercent: " + "\"" + searchButtonLeftPercent + "\",").append(EOL);
    dictionary.append("mainMenuTopPercent: " + "\"" + mainMenuTopPercent + "\",").append(EOL);
    dictionary.append("mainMenuLeft: " + "\"" + mainMenuLeft + "\",").append(EOL);
    dictionary.append("imagesHeightPercent: " + "\"" + imagesHeightPercent + "\",").append(EOL);
    dictionary.append("imagesWidthPercent: " + "\"" + imagesWidthPercent + "\",").append(EOL);
    dictionary.append("imagesTopPercent: " + "\"" + imagesTopPercent + "\",").append(EOL);
    dictionary.append("imagesLeftPercent: " + "\"" + imagesLeftPercent + "\",").append(EOL);
    dictionary.append("mainMenuSelectionHtml: " + "\"" + mainMenuSelectionHtml.replace("\"", "\\\"") + "\",").append(EOL);
    dictionary.append("mainMenuSelectedHtml: " + "\"" + mainMenuSelectedHtml.replace("\"", "\\\"") + "\",").append(EOL);
    dictionary.append("description: " + "\"" + description + "\"").append(EOL);
    dictionary.append("};").append(EOL);
    dictionary.append("</script>").append(EOL);
    return dictionary.toString();
  }
}
