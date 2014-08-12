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
package com.urlisit.siteswrapper.cloud.factory;

import com.google.gwt.i18n.client.Constants;

/**
 * Uses the GWT Constants interface to provide programmatic access to various types of literals.
 * 
 * @author Todd Url
 * @version 1.0
 */
public interface Literals extends Constants {
  
  /**
   * Youtube html embed code
   */
  @DefaultStringValue("<iframe width=420 height=315 src=\"VIDEO_URL\" frameborder=0 allowfullscreen></iframe>")
  String youTubeNewEmbed420x315();
  
  /**
   * Youtube old embed html
   */
  @DefaultStringValue("<object width=\"420\" height=\"315\"><param name=\"movie\" value=\"VIDEO_URL?version=3&amp;hl=en_US\">" +
   "</param><param name=\"allowFullScreen\" value=\"true\"></param><param name=\"allowscriptaccess\" value=\"always\">" +
   "</param><embed src=\"VIDEO_URL?version=3&amp;hl=en_US\" type=\"application/x-shockwave-flash\" width=\"420\" height=\"315\"" +
   "allowscriptaccess=\"always\" allowfullscreen=\"true\"></embed></object>")
  String youTubeOldEmbed420x315();
  
  /**
   * HTML literal masquerading as a close box
   */
  @DefaultStringValue("<span source=closeBox style=color:FONT_COLOR;>&nbsp;&nbsp;&nbsp;&#x292b;</span>")
  String closeBox();
  
  /**
   * Literal for string VIDEO_URL
   */
  @DefaultStringValue("VIDEO_URL")
  String VIDEO_URL();

  /**
   * Literal for string PAGE_NAME
   */
  @DefaultStringValue("PAGE_NAME")
  String PAGE_NAME();

  /**
   * Literal for string HOVER_COLOR
   */
  @DefaultStringValue("HOVER_COLOR")
  String HOVER_COLOR();

  /**
   * Literal for string FONT_COLOR
   */
  @DefaultStringValue("FONT_COLOR")
  String FONT_COLOR();

  /**
   * Literal for string FONT_SIZE
   */
  @DefaultStringValue("FONT_SIZE")
  String FONT_SIZE();

  /**
   * Literal for string FONT_FAMILY
   */
  @DefaultStringValue("FONT_FAMILY")
  String FONT_FAMILY();

  /**
   * Default value for undefined strings in the object model
   */
  @DefaultStringValue("undefined")
  String undefined();

  /**
   * Used to specify an empty string
   */
  @DefaultStringValue("")
  String emptyString();

  /**
   * Key for logoImage in the Site JavaScript dictionary
   */
  @DefaultStringValue("logoImage")
  String logoImage();

  /**
   * Just a zero, but used in reference to the FlexTable in Menu to clearly indicate row zero
   */
  @DefaultIntValue(0)
  int rowZero();

  /**
   * Just a zero
   */
  @DefaultIntValue(0)
  int zero();

  /**
   * String used for pixels specification in css and other style specifications
   * 
   * @return String px
   */
  @DefaultStringValue("px")
  String px();

  /**
   * Error message used when an Image fails to load
   * 
   * @return An error message indicating an image failed to load
   */
  @DefaultStringValue("An error occured while loading image ")
  String imageErrorMessage();

  /**
   * Error message used for the onFailure method of AsyncCallback
   * 
   * @return An error message indicating the network may be down
   */
  @DefaultStringValue("RPC Error - Check your network connection and try again!")
  String rpcErrorMessage();

  /**
   * The variable name of the JavaScript object defined in the host page which holds members of the Site configuration object.
   * 
   * @return Site The default value for the name of the Site configuration object which is simply "Site".
   */
  @DefaultStringValue("Site")
  String site();

  /**
   * The variable name of the JavaScript object defined in the host page which holds members of the Style configuration object.
   * 
   * @return Style The default value for the name of the Style configuration object which is simply "Style".
   */
  @DefaultStringValue("Style")
  String style();

  /**
   * The name of the key in the Site dictionary in the GWT host page which specifies the LookAndFeel associated with the Site
   */
  @DefaultStringValue("lookAndFeel")
  String lookAndFeel();

  /**
   * The name of the key in the Site dictionary in the GWT host page which specifies the DisplayLogoAs associated with the Site
   * or Page
   */
  @DefaultStringValue("displayLogoAs")
  String displayLogoAs();
  
  /**
   * Error message added to the InvalidStateException thrown when the default case of a switch statement against the enum
   * LookAndFeel is encountered. Since an enum is a constant, the switch statement will always be one of the enumerated values.
   */
  @DefaultStringValue("No value specified for LookAndFeel")
  String LookAndFeelIllegalState();
  
  /**
   * Left bracket
   */
  @DefaultStringValue("[")
  String leftBracket();
  
  /**
   * Right bracket
   */
  @DefaultStringValue("]")
  String rightBracket();

  /**
   * Left curly brace
   */
  @DefaultStringValue("{")
  String leftCurlyBrace();

  /**
   * Right curly brace
   */
  @DefaultStringValue("}")
  String rightCurlyBrace();

  /**
   * Pipe
   */
  @DefaultStringValue("|")
  String pipe();

  /**
   * Equals
   */
  @DefaultStringValue("=")
  String equals();

  /**
   * Site dictionary
   */
  @DefaultStringValue("Site dictionary:")
  String siteDictionary();

  /**
   * Style dictionary
   */
  @DefaultStringValue("Style dictionary:")
  String styleDictionary();
  
}
