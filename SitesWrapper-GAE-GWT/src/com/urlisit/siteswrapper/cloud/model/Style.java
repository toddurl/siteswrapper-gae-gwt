/* Copyright 2012 URL IS/IT
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

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Extension;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Holds the persistent styling information of a webapp. Instances of Style are
 * created and persisted to the datastore as the result of HTTP POST calls from
 * Google Apps Scripts running in Google Docs to the Rest servlet in Google App
 * Engine.
 * 
 * @author Todd Url <toddurl @ yahoo.com>
 * @version   1.0
 * @since   12-05-2012
 * @author url
 */
@PersistenceCapable(detachable="true")
public final class Style implements IsSerializable {
  
  /**
   * Ensures each persisted object has a unique key in the datastore
   */
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String encodedKey;
  
  
  /**
   * One of either current, previous or a time/date stamp in SimpleDateFormat.
   * Used to mark a configuration before during and after an update of the
   * object. Under normal conditions, revision has a value of either current
   * or a date (in SimpleDateFormat) when it was archived as the result of an
   * updateStyleConfiguration POST request. During the updateStyleConfiguration
   * POST request, the current configuration is marked as previous and the
   * new Style configuration object is created and marked as current. Upon
   * successful creation of the new Style configuration object (marked as
   * current) the old Style configuration object (marked as previous) is
   * archived by setting the value of revision to the current Date/Time in
   * SimpleDateFormat. If the update(Site|Style|Page|Item)Configuration
   * operation is interrupted or fails for any reason, the configuration
   * is rolled back by deleting any objects marked as current and setting
   * the value of revision for all objects marked as previous to current.
   */
  @Persistent
  private String revision;
  
  /**
   * Used to establish a parent child relationship between the Site and Style
   * configuration objects
   */
  @Persistent
  private String lookAndFeel;
  
  /**
   * A one line description of the LookAndFeel associated with this Style object
   */
  @Persistent
  private String description;
  
  /**
   * HTML color code to be used for the primary color associated with the color
   * scheme of the LookAndFeel
   */
  @Persistent
  private String primaryColor;
  
  /**
   * HTML color code to be used for the primary accent color associated with the
   * color scheme of the LookAndFeel
   */
  @Persistent
  private String primaryAccentColor;
  
  /**
   * HTML color code to be used for the secondary accent color of the color
   * scheme to be used for LookAndFeel associated with this Style object
   */
  @Persistent
  private String secondaryAccentColor;
  
  /**
   * HTML color code to be used for the tertiary accent color of the color
   * scheme to be used for LookAndFeel associated with this Style object
   */
  @Persistent
  private String tertiaryAccentColor;
  
  /**
   * Google Web Fonts font name used for the mainMenu font face
   */
  @Persistent
  private String mainMenuFontFamily;
  
  /**
   * Google Web Fonts font size used for the mainMenu font face
   */
  @Persistent
  private String mainMenuFontSize;
  
  /**
   * HTML color code to be used for the main menu selection font color of the
   * color scheme used for LookAndFeel associated with this Style object
   */
  @Persistent
  private String mainMenuSelectionFontColor;
  
  /**
   * HTML color code to be used for the main menu hover font color of the
   * color scheme used for LookAndFeel associated with this Style object
   */
  @Persistent
  private String mainMenuHoverFontColor;
  
  /**
   * HTML color code to be used for the main menu selected font color of the
   * color scheme used for LookAndFeel associated with this Style object
   */
  @Persistent
  private String mainMenuSelectedFontColor;
  
  /**
   * Line separator appropriate for the current platform
   */
  @Persistent
  private static final String EOL = "\n"; // $codepro.audit.disable platformSpecificLineSeparator
  
  /**
   * Sets the revision field to one of either TimeDateStamp.toString()
   * or 'current'
   * @param revision
   */
  public void setRevision(String revision) {
    this.revision = revision;
  }
  
  /**
   * Gets the value of the revision field for the current Site configuration
   * object
   * @return String
   */
  public String getRevision() {
    return this.revision;
  }
  
  /**
   * Sets lookAndFeel to a valid LookAndFeel label
   * 
   * @param lookAndFeel
   */
  public void setLookAndFeel(String lookAndFeel) {
    this.lookAndFeel = lookAndFeel;
  }
  
  /**
   * Gets the value of lookAndFeel
   * 
   * @return String identifying the LookAndFeel selected in the Site configuration object
   */
  public String getLookAndFeel() {
    return lookAndFeel;
  }
  
  /**
   * Sets the description of the look and feel associated with this Style
   * object. Description should be taken from the description field of the
   * LookAndFeel enum associated with this Style object
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Gets the description of the LookAndFeel associated with this Style object
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Sets the html color code for the primary color to be used for the color
   * scheme of the LookAndFeel
   */
  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }
  
  /**
   * Gets the html color code for the primary color to be used for the color
   * scheme associated with the selected LookAndFeel
   */
  public String getPrimaryColor() {
    return primaryColor;
  }
  
  /**
   * Sets the html color code for the primary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public void setPrimaryAccentColor(String primaryAccentColor) {
    this.primaryAccentColor = primaryAccentColor;
  }
  
  /**
   * Gets the html color code for the primary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public String getPrimaryAccentColor() {
    return primaryAccentColor;
  }
  
  /**
   * Sets the html color code for the secondary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public void setSecondaryAccentColor(String secondaryAccentColor) {
    this.secondaryAccentColor = secondaryAccentColor;
  }
  
  /**
   * Gets the html color code for the secondary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public String getSecondaryAccentColor() {
    return secondaryAccentColor;
  }
  
  /**
   * Sets the html color code for the tertiary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public void setTertiaryAccentColor(String tertiaryAccentColor) {
    this.tertiaryAccentColor = tertiaryAccentColor;
  }
  
  /**
   * Gets the html color code for the tertiary accent color to be used for the
   * color scheme of the LookAndFeel associated with this Style object
   */
  public String getTertiaryAccentColor() {
    return tertiaryAccentColor;
  }
  
  /**
   * Sets the Google Web Font family name for the main menu
   */
  public void setMainMenuFontFamily(String mainMenuFontFamily) {
    this.mainMenuFontFamily = mainMenuFontFamily;
  }
  
  /**
   * Gets the Google Web Font family name for the main menu
   */
  public String getMainMenuFontFamily() {
    return mainMenuFontFamily;
  }
  
  /**
   * Sets the Google Web Font size for the main menu
   */
  public void setMainMenuFontSize(String mainMenuFontSize) {
    this.mainMenuFontSize = mainMenuFontSize;
  }
  
  /**
   * Gets the Google Web Font size for the main menu
   */
  public String getMainMenuFontSize() {
    return mainMenuFontSize;
  }

  /**
   * Sets the html color code for the main menu selection font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public void setMainMenuSelectionFontColor(String mainMenuSelectionFontColor) {
    this.mainMenuSelectionFontColor = mainMenuSelectionFontColor;
  }
  
  /**
   * Gets the html color code for the main menu selection font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public String getMainMenuSelectionFontColor() {
    return mainMenuSelectionFontColor;
  }

  /**
   * Sets the html color code for the main menu hover font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public void setMainMenuHoverFontColor(String mainMenuHoverFontColor) {
    this.mainMenuHoverFontColor = mainMenuHoverFontColor;
  }
  
  /**
   * Gets the html color code for the main menu hover font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public String getMainMenuHoverFontColor() {
    return mainMenuHoverFontColor;
  }

  /**
   * Sets the html color code for the main menu selected font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public void setMainMenuSelectedFontColor(String mainMenuSelectedFontColor) {
    this.mainMenuSelectedFontColor = mainMenuSelectedFontColor;
  }
  
  /**
   * Gets the html color code for the main menu hover font color to be used
   * for the color scheme of the LookAndFeel associated with this Style object
   */
  public String getMainMenuSelectedFontColor() {
    return mainMenuSelectedFontColor;
  }

  /**
   * Output object as a named JavaScript object to be used as an associated array of name/value
   * pairs to be bound to a GWT Dictionary
   * 
   * @see com.google.gwt.i18n.client.Dictionary
   */
  public String toDictionary() {
    StringBuilder dictionary = new StringBuilder("<script>").append(EOL);
    dictionary.append("var Style = {").append(EOL);
    dictionary.append("lookAndFeel: " + "\"" + lookAndFeel + "\",").append(EOL);
    dictionary.append("description: " + "\"" + description + "\",").append(EOL);
    dictionary.append("primaryColor: " + "\"" + primaryColor + "\",").append(EOL);
    dictionary.append("primaryAccentColor: " + "\"" + primaryAccentColor + "\",").append(EOL);
    dictionary.append("secondaryAccentColor: " + "\"" + secondaryAccentColor + "\",").append(EOL);
    dictionary.append("tertiaryAccentColor: " + "\"" + tertiaryAccentColor + "\",").append(EOL);
    dictionary.append("mainMenuFontFamily: " + "\"" + mainMenuFontFamily + "\",").append(EOL);
    dictionary.append("mainMenuFontSize: " + "\"" + mainMenuFontSize + "\",").append(EOL);
    dictionary.append("mainMenuSelectionFontColor: " + "\"" + mainMenuSelectionFontColor + "\",").append(EOL);
    dictionary.append("mainMenuHoverFontColor: " + "\"" + mainMenuHoverFontColor + "\",").append(EOL);
    dictionary.append("mainMenuSelectedFontColor: " + "\"" + mainMenuSelectedFontColor + "\"").append(EOL);
    dictionary.append("};").append(EOL);
    dictionary.append("</script>").append(EOL);
    return dictionary.toString();
  }
  
  public Style fromDictionary(Dictionary dictionary) {
    Style style = new Style();
    style.setLookAndFeel(dictionary.get("lookAndFeel"));
    style.setDescription(dictionary.get("description"));
    style.setPrimaryColor(dictionary.get("primaryColor"));
    style.setPrimaryAccentColor(dictionary.get("primaryAccentColor"));
    style.setSecondaryAccentColor(dictionary.get("secondaryAccentColor"));
    style.setTertiaryAccentColor(dictionary.get("tertiaryAccentColor"));
    style.setMainMenuFontFamily(dictionary.get("mainMenuFontFamily"));
    style.setMainMenuFontSize(dictionary.get("mainMenuFontSize"));
    style.setMainMenuSelectionFontColor(dictionary.get("mainMenuSelectionFontColor"));
    style.setMainMenuHoverFontColor(dictionary.get("mainMenuHoverFontColor"));
    style.setMainMenuSelectedFontColor(dictionary.get("mainMenuSelectedFontColor"));
    return style;
  }

public Object getEncodedKey() {
    return encodedKey;
}
  
}
