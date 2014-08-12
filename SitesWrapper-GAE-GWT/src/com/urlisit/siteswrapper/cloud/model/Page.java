/* Copyright 2011 URL IS/IT
 *
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
// $codepro.audit.disable largeNumberOfFields
// $codepro.audit.disable largeNumberOfMethods
// $codepro.audit.disable variableShouldBeFinal
package com.urlisit.siteswrapper.cloud.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Holds the persistent description of each page in the site stored as the
 * parameters used to recreate the page in the browser when instantiating and
 * calling the methods of client.URLeUi in the onModuleLoad method of the
 * EntryPoint class.
 * 
 * Instances of PageConfiguration are created and persisted to the datastore as
 * the result of HTTP POST calls from Google Apps Scipts in Google Docs to the
 * HttpEventHandler class in Google App Engine.
 * 
 * @author   Todd Url <toddurl @ yahoo.com>
 * @version  1.0
 * @since    11-01-2011
 */
@Entity
public class Page implements IsSerializable {
  /**
   * Ensures each persisted object has a unique key in the datastore
   */
   @Id
   private Long key;

   /**
    * One of either current, previous or SimpleDateFormat. Used to mark a
    * configuration before during and after an update of the configuration
    * object. Under normal conditions, revision has a value of either current
    * or a date (in SimpleDateFormat) when it was archived as the result of an
    * updateSiteConfiguration POST request. During the updateSiteConfiguration
    * POST request, the current configuration is marked as previous and the
    * new SiteConfiguration object is created and marked as current. Upon
    * successful creation of the new SiteConfiguration object (marked as
    * current) the old SiteConfiguration object (marked as previous) is
    * archived by setting the value of revision to the current Date/Time in
    * SimpleDateFormat. If the update(Site|Page|LandingPage)Configuration
    * operation is interrupted or fails for any reason, the configuration
    * is rolled back by deleting any objects marked as current and setting
    * the value of revision for all objects marked as previous to current.
    */
   @Persistent
   private String revision;

   /**
    * Used as a parameter to the client UI constructor, pageName becomes the
    * value of the client UI instance variable pageName
    */
   @Persistent
   private String pageName;

   /**
    * The parameter used to determine if the pageTitle should be shown in the
    * page 
    */
   @Persistent
   private Boolean showPageTitle;

   /**
    * The parameter passed to RenderedPage instance method setLogo, logoUrl
    * becomes the value of the RenderedPage instance variable logo
    */
   @Persistent
   private String logoImage;

   /**
    * The html counterpart of logoImage for use when the logo should be renderd as html instead of
    * as an image
    */
   @Persistent
   private String logoHtml;
   
   /**
    * Determines whether to display logo as an image or html. Any value specified other than image or
    * html will be converted to html
    */
   @Persistent
   private DisplayLogoAs displayLogoAs;

   /**
    * The parameter used to determine if the page should include a Google
    * Translate widget
    */
   @Persistent
   private Boolean languageTranslationEnabled;

   /**
    * The parameter passed to RenderedPage instance specifying what languages
    * are available for translation.
    */
   @Persistent
   private List<String> translatedLanguages = new ArrayList<String>();

   /**
    * The parameter used to determine if the page should include Google Custom
    * Search
    */
   @Persistent
   private Boolean customSearchEnabled;

   /**
    * The parameter passed to RenderedPage instance specifying what urls to
    * use for the page specific Google Custom Search domains
    */
   @Persistent
   private List<String> customSearchUrls = new ArrayList<String>();

   /**
    * The parameter passed to RenderedPage instance method setMainMenuLink.
    * mainMenuType must have a value of Link or Button.
    */
   @Persistent
   private String mainMenuType;

   /**
    * The parameter passed to RenderedPage instance method setMainMenuLink.
    * mainMenuDirection must have a value of Horizontal or Vertical.
    */
   @Persistent
   private String mainMenuDirection;

   /**
    * The parameter passed to RenderedPage instance method setMainMenuLink.
    * mainMenuSelectionHtml contains the value (which can include HTML) of
    * the page link in the main menu when the page is currently not rendered
    * or the cursor is not hovering over the link.
    */
   @Persistent
   private String mainMenuSelectionHtml;

   /**
    * The parameter passed to RenderedPage instance method
    * setMainMenuLinkSelected, mainMenuSelectedHtml contains the value (which
    * can include HTML) of the page link in the main menu when the page is
    * currently rendered
    */
   @Persistent
   private String mainMenuSelectedHtml;
   
   /**
    * The parameter passed to RenderedPage instance method addImage, imageUrl is
    * the URL of an image added to the image carousel of an instance of
    * RenderedPage
    */
   @Persistent
  private List<String> backgroundImageUrls = new ArrayList<String>();

   /**
    * The parameter passed to RenderedPage instance method addImage.
    * backgroundImageDurationSeconds is the number of seconds a background
    * image will display before proceeding to the next image.
    */
   @Persistent
   private List<String> backgroundImageDurationSeconds =
      new ArrayList<String>();

   /**
    * The parameter passed to RenderedPage instance method addContentMenuItem
    */
   @Persistent
   private List<String> contentMenuItemName = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addContentMenuItem
    */
   @Persistent
   private List<String> contentMenuItemLink = new ArrayList<String>();

   /**
    * The result of a GET against contentMenuItemLink
    */
   @Persistent
   private List<String> contentMenuItemCache = new ArrayList<String>();
   
   /**
    * Contains the css specifications for the Page Specific Content menu
    * MOVE TO SITE CONFIGURATION
    */
   @Persistent
   private String contentMenuStyleSheet;
   
   /**
    * Specifies the general layout of the page. Value corresponds to the
    * Google Sites basic layout templates of One column simple, Two column
    * simple, Three column simple, One column, Two column, Three column
    * Left sidebar, Right sidebar and Left right sidebar
    */
   @Persistent
   private String contentLayout;

   /**
    * Specifies the content (which can include HTML) which is placed in the
    * content header area of the page when the selected contentLayout includes
    * a header area
    */
   @Persistent
   private List<String> contentHeader;

   /**
    * Specifies the content (which can include HTML) which is placed in column
    * one area of the page when the selected contentLayout includes a column
    * one area
    */
   @Persistent
   private List<String> contentColumnOne;

   /**
    * Specifies the content (which can include HTML) which is placed in column
    * two area of the page when the selected contentLayout includes a column
    * two area
    */
   @Persistent
   private List<String> contentColumnTwo;
   
   /**
    * Specifies the content (which can include HTML) which is placed in column
    * three area of the page when the selected contentLayout includes a column
    * three area
    */
   @Persistent
   private List<String> contentColumnThree;
   
   /**
    * Specifies the content (which can include HTML) which is placed in the
    * Left sidebar area of the page when the selected contentLayout includes
    * Left sidebar
    */
   @Persistent
   private List<String> contentLeftSidebar;

   /**
    * Specifies the content (which can include HTML) which is placed in the
    * Right sidebar area of the page when the selected contentLayout
    * includes a Right sidebar
    */
   @Persistent
   private List<String> contentRightSidebar;

   /**
    * Specifies the content (which can include HTML) which is placed in the
    * Footer area of the page when the selected contentLayout
    * includes a Footer
    */
   @Persistent
   private List<String> contentFooter;

   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messageHeaderText = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messageBodyText = new ArrayList<String>();
   
   /**
    * Message rendering code refers to this field which maps to the name field in Item
    */
   @Persistent
   private List<String> messageInformationItem = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messageHtmlColorCode = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messageWidthPercentOfPage = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messagePercentOfPageFromLeft = new ArrayList<String>();
   
   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messagePercentOfPageFromTop = new ArrayList<String>();

   /**
    * The parameter passed to RenderedPage instance method addMessage
    */
   @Persistent
   private List<String> messageDurationSeconds = new ArrayList<String>();

   /**
    * Used to generate the sitemap entry for the page
    */
   @Persistent
   private String lastmod = new String();

   /**
    * Method getKey.
    * @return Key
    */
   public long getKey() {
      return key;
   }
   
   /**
    * Method setRevision.
    * @param revision String
    */
   public void setRevision(String revision) {
      this.revision = revision;
   }
   
   /**
    * Method getRevision.
    * @return String
    */
   public String getRevision() {
      return revision;
   }
   
   /**
    * Method getPageName
    * @return String
    */
   public String getPageName() {
      return pageName;
   }
   
   /**
    * Method setPageName
    * @param pageName String
    */
   public void setPageName(String pageName) {
      this.pageName = pageName;
   }
   
   /**
    * Method getShowPageTitle.
    * @return Boolean
    */
   public Boolean getShowPageTitle() {
      return showPageTitle;
   }
   
   /**
    * Method setShowPageTitle.
    * @param showPageTitle String
    */
   public void setShowPageTitle(String showPageTitle) {
      if (showPageTitle.equals("Yes")) {
         this.showPageTitle = true;
      } else {
         this.showPageTitle = false;
      }
   }
   
   /**
    * Get the URL specified for the image version of the logo
    * 
    * @return String
    */
   public String getLogoImage() {
      return logoImage;
   }
   
   /**
    * Set the URL to be used for the image version of the logo
    * 
    * @param logoImage String representation of the URL associated with the logo image
    */
   public void setLogoImage(String logoImage) {
      this.logoImage = logoImage;
   }

   /**
    * Get the Html specified for the markup version of the logo
    * 
    * @return String
    */
   public String getLogoHtml() {
      return logoHtml;
   }
   
   /**
    * Set the Html to be used for the markup version of the logo
    * 
    * @param logoHtml String
    */
   public void setLogoHtml(String logoHtml) {
      this.logoHtml = logoHtml;
   }

   /**
    * Returns a string with the value of either Image or Html
    * 
    * @return String
    */
   public DisplayLogoAs getDisplayLogoAs() {
      return displayLogoAs;
   }
   
   /**
    * Any specified value other than Image or Html will be converted to None
    * 
    * TODO Change String argument to enum
    * 
    * @param displayLogoAs String which must be one of Image or Html
    */
   public void setDisplayLogoAs(String displayLogoAs) {
     if (displayLogoAs.equals("Image")) {
       this.displayLogoAs = DisplayLogoAs.IMAGE;
     } else if (displayLogoAs.equals("Html")) {
       this.displayLogoAs = DisplayLogoAs.HTML;
     } else {
       this.displayLogoAs = DisplayLogoAs.NONE;
     }
   }

   /**
    * Method getLanguageTranslationEnabled.
    * @return Boolean
    */
   public Boolean getLanguageTranslationEnabled() {
      return languageTranslationEnabled;
   }
   
   /**
    * Method setLanguageTranslationEnabled.
    * @param languageTranslationEnabled String
    */
   public void setLanguageTranslationEnabled(String languageTranslationEnabled) {
      if (languageTranslationEnabled.equals("Yes")) {
         this.languageTranslationEnabled = true;
      } else {
         this.languageTranslationEnabled = false;
      }
   }

   /**
    * Method getTranslatedLanguages.
    * @param index int
    * @return String
    */
   public String getTranslatedLanguages(int index) {
      return translatedLanguages.get(index);
   }
   
   /**
    * Method setTranslatedLanguages.
    * @param translatedLanguages String
    */
   public void setTranslatedLanguages(String translatedLanguages) {
      this.translatedLanguages.add(translatedLanguages);
   }

   /**
    * Method getCustomSearchEnabled.
    * @return Boolean
    */
   public Boolean getCustomSearchEnabled() {
      return customSearchEnabled;
   }
   
   /**
    * Method setCustomSearchEnabled.
    * @param customSearchEnabled String
    */
   public void setCustomSearchEnabled(String customSearchEnabled) {
      if (customSearchEnabled.equals("Yes")) {
         this.customSearchEnabled = true;
      } else {
         this.customSearchEnabled = false;
      }
   }

   /**
    * Method getCustomSearchUrls.
    * @param index int
    * @return Text
    */
   public String getCustomSearchUrls(int index) {
      return customSearchUrls.get(index);
   }
   
   /**
    * Method setCustomSearchUrls.
    * @param customSearchUrls String
    */
   public void setCustomSearchUrls(String customSearchUrls) {
      //this.customSearchUrls.add(new Text(customSearchUrls));
      this.customSearchUrls.add(customSearchUrls);
   }

   /**
    * Method getMainMenuType.
    * @return String
    */
   public String getMainMenuType() {
      return mainMenuType;
   }
   
   /**
    * Method setMainMenuType.
    * @param mainMenuType String
    */
   public void setMainMenuType(String mainMenuType) {
      this.mainMenuType = mainMenuType;
   }

   /**
    * Method getMainMenuDirection.
    * @return String
    */
   public String getMainMenuDirection() {
      return mainMenuDirection;
   }
   
   /**
    * Method setMainMenuDirection.
    * @param mainMenuDirection String
    */
   public void setMainMenuDirection(String mainMenuDirection) {
      this.mainMenuDirection = mainMenuDirection;
   }

   /**
    * Method getMainMenuSelectionHtml.
    * @return String
    */
   public String getMainMenuSelectionHtml() {
      return mainMenuSelectionHtml;
   }
   
   /**
    * Method setMainMenuSelectionHtml.
    * @param mainMenuSelectionHtml String
    */
   public void setMainMenuSelectionHtml(String mainMenuSelectionHtml) {
      this.mainMenuSelectionHtml = mainMenuSelectionHtml;
   }

   /**
    * Method getMainMenuSelectedHtml.
    * @return String
    */
   public String getMainMenuSelectedHtml() {
      return mainMenuSelectedHtml;
   }
   
   /**
    * Method setMainMenuSelectedHtml.
    * @param mainMenuSelectedHtml String
    */
   public void setMainMenuSelectedHtml(String mainMenuSelectedHtml) {
      this.mainMenuSelectedHtml = mainMenuSelectedHtml;
   }
   
   /**
    * Method getBackgroundImageUrls.
    * @param index int
    * @return String
    */
   public String getBackgroundImageUrls(int index) {
      return backgroundImageUrls.get(index);
   }
   
   public List<String> getBackgroundImages() {
     return backgroundImageUrls;
   }
   
   /**
    * Method setBackgroundImageUrls.
    * @param backgroundImageUrls String
    */
   public void setBackgroundImageUrls(String backgroundImageUrls) {
      this.backgroundImageUrls.add(backgroundImageUrls);
   }
   
   /**
    * Returns the size of backgroundImageUrls
    */
   public int sizeOfBackgroundImageUrls() {
     return backgroundImageUrls.size();
   }

   /**
    * Method getBackgroundImageDurationSeconds.
    * @param index int
    * @return String
    */
   public String getBackgroundImageDurationSeconds(int index) {
      return backgroundImageDurationSeconds.get(index);
   }
   
   /**
    * Method setBackgroundImageDurationSeconds.
    * @param backgroundImageDurationSeconds String
    */
   public void setBackgroundImageDurationSeconds(
         String backgroundImageDurationSeconds) {
      this.backgroundImageDurationSeconds.add(backgroundImageDurationSeconds);
   }

   /**
    * Returns the size of backgroundImageUrls
    */
   public int sizeOfBackgroundImageDurationSeconds() {
     return backgroundImageDurationSeconds.size();
   }
   
   public List<String> getContentMenuItemNames() {
     return contentMenuItemName;
   }
   
   public List<String> getContentMenuItemLinks() {
     return contentMenuItemLink;
   }
   
   public Map<String, String> getContentMenu() {
     final Map<String, String> contentMenu = new HashMap<String, String>();
     for (int i = 0; i < contentMenuItemName.size(); i++) {
       contentMenu.put(contentMenuItemName.get(i), contentMenuItemLink.get(i));
     }
     return contentMenu;
   }
   
   /**
    * Method getContentMenuItemName.
    * @param index int
    * @return String
    */
   public String getContentMenuItemName(int index) {
      return contentMenuItemName.get(index);
   }
   
   /**
    * Method setContentMenuItemName.
    * @param contentMenuItemName String
    */
   public void setContentMenuItemName(String contentMenuItemName) {
      this.contentMenuItemName.add(contentMenuItemName);
   }

   /**
    * Returns the size of contentMenuItemName
    */
   public int sizeOfContentMenuItemName() {
     return contentMenuItemName.size();
   }
   
   /**
    * Method getContentMenuItemLink.
    * @param index int
    * @return String
    */
   public String getContentMenuItemLink(int index) {
      return contentMenuItemLink.get(index);
   }
   
   /**
    * Method setContentMenuItemLink.
    * @param contentMenuItemLink String
    */
   public void setContentMenuItemLink(String contentMenuItemLink) {
      this.contentMenuItemLink.add(contentMenuItemLink);
   }

   /**
    * Returns the size of contentMenuItemLink
    */
   public int sizeOfContentMenuItemLink() {
     return contentMenuItemLink.size();
   }
   
   /**
    * Method getContentMenuStyleSheet.
    * @return String
    */
   public String getContentMenuStyleSheet() {
      return contentMenuStyleSheet;
   }
   
   /**
    * Method setContentMenuStyleSheet.
    * @param contentMenuStyleSheet String
    */
   public void setContentMenuStyleSheet(String contentMenuStyleSheet) {
      this.contentMenuStyleSheet = contentMenuStyleSheet;
   }

   /**
    * Method getContentLayout.
    * @return String
    */
   public String getContentLayout() {
      return contentLayout;
   }
   
   /**
    * Method setContentLayout.
    * @param contentLayout String
    */
   public void setContentLayout(String contentLayout) {
      this.contentLayout = contentLayout;
   }

   /**
    * Method getContentHeader.
    * @return String
    */
   public String getContentHeader() {
      //return contentHeader.getValue();
      final StringBuilder result = new StringBuilder();
      for (String string : contentHeader) {
        result.append(string);
      }
      return result.toString();
   }
   
   /**
    * Method setContentHeader.
    * @param contentHeader String
    */
   public void setContentHeader(String contentHeader) {
      //this.contentHeader = new Text(contentHeader);
      this.contentHeader = new ArrayList<String>();
      while (contentHeader.length() > 500) {
        this.contentHeader.add(contentHeader.substring(0, 499));
        contentHeader = contentHeader.substring(500, contentHeader.length());
      }
      if (contentHeader.length() > 0) {
        this.contentHeader.add(contentHeader);
      }
   }
   
   /**
    * Method getContentColumnOne
    * @return String
    */
   public String getContentColumnOne() {
      final StringBuilder result = new StringBuilder();
      for (String string : contentColumnOne) {
        result.append(string);
      }
      return result.toString();
   }
   
   /**
    * Method setContentColumnOne
    * @param contentColumnOne String
    */
   public void setContentColumnOne(String contentColumnOne) {
      this.contentColumnOne = new ArrayList<String>();
      while (contentColumnOne.length() > 500) {
        this.contentColumnOne.add(contentColumnOne.substring(0, 499));
        contentColumnOne = contentColumnOne.substring(500, contentColumnOne.length());
      }
      if (contentColumnOne.length() > 0) {
        this.contentColumnOne.add(contentColumnOne);
      }
   }
   
   /**
    * Method getContentColumnTwo
    * @return String
    */
   public String getContentColumnTwo() {
      StringBuilder result = new StringBuilder();
      for (Iterator<String> iterator = contentColumnTwo.iterator(); iterator.hasNext();) {
        result.append(iterator.next());
      }
      return result.toString();
   }
   
   /**
    * Method setContentColumnTwo
    * @param contentColumnTwo String
    */
   public void setContentColumnTwo(String contentColumnTwo) {
      this.contentColumnTwo = new ArrayList<String>();
      while (contentColumnTwo.length() > 500) {
        this.contentColumnTwo.add(contentColumnTwo.substring(0, 499));
        contentColumnTwo = contentColumnTwo.substring(500, contentColumnTwo.length());
      }
      if (contentColumnTwo.length() > 0) {
        this.contentColumnTwo.add(contentColumnTwo);
      }
   }
   
   /**
    * Method getContentColumnThree
    * @return String
    */
   public String getContentColumnThree() {
      StringBuilder result = new StringBuilder();
      for (Iterator<String> iterator = contentColumnThree.iterator(); iterator.hasNext();) {
        result.append(iterator.next());
      }
      return result.toString();
   }
   
   /**
    * Method setContentColumnThree
    * @param contentColumnThree String
    */
   public void setContentColumnThree(String contentColumnThree) {
      this.contentColumnThree = new ArrayList<String>();
      while (contentColumnThree.length() > 500) {
        this.contentColumnThree.add(contentColumnThree.substring(0, 499));
        contentColumnThree = contentColumnThree.substring(500, contentColumnThree.length());
      }
      if (contentColumnThree.length() > 0) {
        this.contentColumnThree.add(contentColumnThree);
      }
   }
   
   /**
    * Method getContentLeftSidebar
    * @return String
    */
   public String getContentLeftSidebar() {
      StringBuilder result = new StringBuilder();
      for (Iterator<String> iterator = contentLeftSidebar.iterator(); iterator.hasNext();) {
        result.append(iterator.next());
      }
      return result.toString();
   }
   
   /**
    * Method setContentLeftSidebar
    * @param contentLeftSidebar String
    */
   public void setContentLeftSidebar(String contentLeftSidebar) {
      this.contentLeftSidebar = new ArrayList<String>();
      while (contentLeftSidebar.length() > 500) {
        this.contentLeftSidebar.add(contentLeftSidebar.substring(0, 499));
        contentLeftSidebar = contentLeftSidebar.substring(500, contentLeftSidebar.length());
      }
      if (contentLeftSidebar.length() > 0) {
        this.contentLeftSidebar.add(contentLeftSidebar);
      }
   }
   
   /**
    * Method getContentRightSidebar
    * @return String
    */
   public String getContentRightSidebar() {
      StringBuilder result = new StringBuilder();
      for (Iterator<String> iterator = contentRightSidebar.iterator(); iterator.hasNext();) {
        result.append(iterator.next());
      }
      return result.toString();
   }
   
   /**
    * Method setContentRightSidebar
    * @param contentRightSidebar String
    */
   public void setContentRightSidebar(String contentRightSidebar) {
      this.contentRightSidebar = new ArrayList<String>();
      while (contentRightSidebar.length() > 500) {
        this.contentRightSidebar.add(contentRightSidebar.substring(0, 499));
        contentRightSidebar = contentRightSidebar.substring(500, contentRightSidebar.length());
      }
      if (contentRightSidebar.length() > 0) {
        this.contentRightSidebar.add(contentRightSidebar);
      }
   }

   /**
    * Method getContentFooter
    * @return String
    */
   public String getContentFooter() {
      StringBuilder result = new StringBuilder();
      for (Iterator<String> iterator = contentFooter.iterator(); iterator.hasNext();) {
        result.append(iterator.next());
      }
      return result.toString();
   }
   
   /**
    * Method setContentFooter
    * @param contentFooter String
    */
   public void setContentFooter(String contentFooter) {
      this.contentFooter = new ArrayList<String>();
      while (contentFooter.length() > 500) {
        this.contentFooter.add(contentFooter.substring(0, 499));
        contentFooter = contentFooter.substring(500, contentFooter.length());
      }
      if (contentFooter.length() > 0) {
        this.contentFooter.add(contentFooter);
      }
   }

   /**
    * Method getMessageHeaderText.
    * @param index int
    * @return String
    */
   public String getMessageHeaderText(int index) {
      return messageHeaderText.get(index);
   }
   
   /**
    * Method setMessageHeaderText.
    * @param messageHeaderText String
    */
   public void setMessageHeaderText(String messageHeaderText) {
      this.messageHeaderText.add(messageHeaderText);
   }
   
   /**
    * Returns the size of messageHeaderText
    */
   public int sizeOfMessageHeaderText() {
     return messageHeaderText.size();
   }
   
   /**
    * Method getMessageBodyText.
    * @param index int
    * @return String
    */
   public String getMessageBodyText(int index) {
      return messageBodyText.get(index);
   }
   
   /**
    * Method setMessageBodyText.
    * @param messageBodyText String
    */
   public void setMessageBodyText(String messageBodyText) {
      this.messageBodyText.add(messageBodyText);
   }

   /**
    * Returns the size of messageBodyText
    */
   public int sizeOfMessageBodyText() {
     return messageBodyText.size();
   }
   
   /**
    * Method getMessageInformationItem
    * @param index int
    * @return String
    */
   public String getMessageInformationItem(int index) {
      return messageInformationItem.get(index);
   }
   
   /**
    * Method setMessageInformationItem
    * @param messageInformationItem String
    */
   public void setMessageInformationItem(String messageInformationItem) {
      this.messageInformationItem.add(messageInformationItem);
   }

   /**
    * Returns the size of messageInformationItem
    */
   public int sizeOfMessageInformationItem() {
     return messageInformationItem.size();
   }
   
   /**
    * Method getMessageHtmlColorCode.
    * @param index int
    * @return String
    */
   public String getMessageHtmlColorCode(int index) {
      return messageHtmlColorCode.get(index);
   }
   
   /**
    * Method setMessageHtmlColorCode.
    * @param messageHtmlColorCode String
    */
   public void setMessageHtmlColorCode(String messageHtmlColorCode) {
      this.messageHtmlColorCode.add(messageHtmlColorCode);
   }

   /**
    * Returns the size of messageHtmlColorCode
    */
   public int sizeOfMessageHtmlColorCode() {
     return messageHtmlColorCode.size();
   }
   
   /**
    * Method getMessageWidthPercentOfPage.
    * @param index int
    * @return String
    */
   public String getMessageWidthPercentOfPage(int index) {
      return messageWidthPercentOfPage.get(index);
   }
   
   /**
    * Method setMessageWidthPercentOfPage.
    * @param messageWidthPercentOfPage String
    */
   public void setMessageWidthPercentOfPage(String messageWidthPercentOfPage) {
      this.messageWidthPercentOfPage.add(messageWidthPercentOfPage);
   }

   /**
    * Returns the size of messageWidthPercentOfPage
    */
   public int sizeOfMessageWidthPercentOfPage() {
     return messageWidthPercentOfPage.size();
   }
   
   /**
    * Method getMessagePercentOfPageFromLeft.
    * @param index int
    * @return String
    */
   public String getMessagePercentOfPageFromLeft(int index) {
      return messagePercentOfPageFromLeft.get(index);
   }
   
   /**
    * Method setMessagePercentOfPageFromLeft.
    * @param messagePercentOfPageFromLeft String
    */
   public void setMessagePercentOfPageFromLeft(
         String messagePercentOfPageFromLeft) {
      this.messagePercentOfPageFromLeft.add(messagePercentOfPageFromLeft);
   }
   
   /**
   * Returns the size of messagePercentOfPageFromLeft
   */
  public int sizeOfMessagePercentOfPageFromLeft() {
    return messagePercentOfPageFromLeft.size();
  }
   
   /**
    * Method getMessagePercentOfPageFromTop.
    * @param index int
    * @return String
    */
   public String getMessagePercentOfPageFromTop(int index) {
      return messagePercentOfPageFromTop.get(index);
   }
   
   /**
    * Method setMessagePercentOfPageFromTop.
    * @param messagePercentOfPageFromTop String
    */
   public void setMessagePercentOfPageFromTop(String messagePercentOfPageFromTop) {
      this.messagePercentOfPageFromTop.add(messagePercentOfPageFromTop);
   }

   /**
    * Returns size of messagePercentOfPageFromTop
    */
   public int sizeOfMessagePercentOfPageFromTop() {
     return messagePercentOfPageFromTop.size();
   }
   
   /**
    * Method getMessageDurationSeconds.
    * @param index int
    * @return String
    */
   public String getMessageDurationSeconds(int index) {
      return messageDurationSeconds.get(index);
   }
   
   /**
    * Method setMessageDurationSeconds.
    * @param messageDurationSeconds String
    */
   public void setMessageDurationSeconds(String messageDurationSeconds) {
      this.messageDurationSeconds.add(messageDurationSeconds);
   }

   /**
    * Returns the size of messageDurationSeconds
    */
   public int sizeOfMessageDurationSeconds() {
     return messageDurationSeconds.size();
   }
   
   /**
    * Method setLastmod.
    * @param lastmod String
    */
   public void setLastmod(String lastmod) {
      this.lastmod = lastmod;
   }
   
   /**
    * Method getLastmod.
    * @return String
    */
   public String getLastmod() {
      return lastmod;
   }

}