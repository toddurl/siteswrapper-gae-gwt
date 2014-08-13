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
// $codepro.audit.disable largeNumberOfFields
// $codepro.audit.disable largeNumberOfMethods
// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity
// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
package com.urlisit.siteswrapper.cloud.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Extension;

import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Holds the persistent description of a web-app. Instances of Site are created
 * and persisted to the data-store as the result of HTTP POST calls from Google
 * Apps Scripts running in Google Docs to the Rest servlet in Google App Engine.
 * 
 * @author Todd Url <toddurl @ yahoo.com>
 * @version   1.0
 * @since   12-05-2012
 * @author url
 */
@PersistenceCapable(detachable="true")
public class Site implements IsSerializable {
  
  /**
   * Ensures each persisted object has a unique key in the datastore
   */
   @PrimaryKey
   @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
   @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
   private String encodedKey;

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
   * SimpleDateFormat. If the update(Site|Page|Landing|Information)Configuration
   * operation is interrupted or fails for any reason, the configuration
   * is rolled back by deleting any objects marked as current and setting
   * the value of revision for all objects marked as previous to current.
   */
   @Persistent
   private String revision;
   @Persistent
   private String tag;
   @Persistent
   private String name;
   @Persistent
   private String description;
   @Persistent
   private String applicationId;
   
   /**
    * googleAppEngineApplication is the identifier in GAE for this webapp
    */
   @Persistent
   private String googleAppEngineApplication;
   
   /**
    * googleAppEngineVersion is the version identifier in GAE for this webapp
    */
   @Persistent
   private String googleAppEngineVersion;

   /**
    * siteName is the name of the web (used in the title for example)
    */
   @Persistent
   private String siteName;

   /**
    * Interpret the data model with respect to a known overall site layout.
    * Currently one of;
    * Koninklijke - Philips Lighting (http://www.usa.lighting.philips.com/)
    */
   @Persistent
   private LookAndFeel lookAndFeel;
   
   /**
    * Apply rules defined in Style for specified LookAndFeel while rendering Pages and Landings
    */
   private Style style;

   /**
    * Interpret the data model with respect to a known Google Sites color theme.
    * Valid values for theme are found in Google Sites -> manage site -> Themes
    * and are simply the name of the Theme in lower case with spaces removed.
    * Concatenation and transformation to lower case is performed in the setter
    * setTheme, which takes as a parameter the theme name as it is specified in
    * Google Sites. So for example, the Google Sites theme 'Open Sky' which is 
    * specified as 'Open Sky' in the URLeSites Google Docs configuration
    * document Site Configuration section becomes theme = "opensky" when
    * specified as a parameter to setTheme as in Site.setTheme("Open Sky").
    * The concatenated lower case form is retained when theme is retrieved using
    * getTheme() which of course would return 'opensky' in this example. This
    * approach allows Google Sites themes to be specified in the same manner as
    * they are in Google Sites (i.e.; 'Open Sky') and used in the page source
    * as a css link in the same manner as they are in Google Sites (i.e. http://
    * gstatic.com/sites/p/6fecfc/system/app/themes/opensky/standard-css-opensky-
    * ltr-ltr.css).
    */
   @Persistent
   private String theme = "default";

   /**
    * The value for the href attribute of the Google Web Fonts link in the host
    * page. for example, to use font-family: 'Aldrich', sans-serif; as a style
    * specification, the html tag <link href='http://fonts.googleapis.com/css?fa
    * mily=Aldrich|Raleway:100|Open+Sans:300,400' rel=stylesheet type=text/css>
    * needs to appear in the head element of the host page. googleWebFontsUrl is
    * the url which would normally be used in this context. The value for
    * googleWebFontsUrl is specified in the GoogleDocsConfigurationDocument on
    * the SiteConfiguration page. For more information about Google Web Fonts
    * see the Google Web Fonts web site.
    */
   @Persistent
   private String googleWebFontsUrl;
   
   /**
    * faviconUrl
    */
   @Persistent
   private String faviconUrl;
   
   /**
    * appleTouchIconUrl
    */
   @Persistent
   private String appleTouchIconUrl;
   
  /**
   * Used to map an httpRequest for / to a default page
   */
   @Persistent
   private String defaultPage;

   /**
    * URL to be used for the logo when the LookAndFeel requires the same logo to be used for all pages
    */
   @Persistent
   private String logoImage;
   
   /**
    * Html to be used for the logo when the LookAndFeel requires the same logo to be used for all pages
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
    * Specifies the content (which can include HTML) which is placed in the
    * bottom of every page
    */
   @Persistent
   private List<String> siteFooter;
   
   /**
    * Gets displayed if Gwt Rpc Fails
    */
   @Persistent
   private String gwtRpcErrorMessage;
   
   /**
    * Determines if previous configurations are retained and/or restored by
    * time/date stamp
    */
   @Persistent
   private String revisionHistoryEnabled;
   
   /**
    * Line separator appropriate for the current platform
    */
   @Persistent
   private static final String EOL = "\n"; // $codepro.audit.disable platformSpecificLineSeparator
   
   /**
    * Indicates the data-store dosen't contain any objects having a revision
    * value of current. This value is set to false by default when an object
    * is loaded into the data-store. DAO sets this field to true if the
    * query for Site objects having a revision value of current fails to
    * retrieve any objects and returns an empty Site object. The client is
    * expected to test the returned Site object before using it by invoking the
    * isEmpty() method. True indicates that this object is empty and there are
    * no valid Site objects (those having a revision value of current) in the
    * data-store.
    */
   @Persistent
   private boolean empty;

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
    * Sets the value of the GAE application id for the web app. This value must
    * match the value used for the Application ID in GAE and the string
    * specified as the value for <application> in in appengine-web.xml
    * @param googleAppEngineApplication
    */
   public void setGoogleAppEngineApplication(
         String googleAppEngineApplication) {
      this.googleAppEngineApplication = googleAppEngineApplication;
   }

   /**
    * Gets the value of the GAE application Id
    * @return String
    */
   public String getGoogleAppEngineApplication() {
      return this.googleAppEngineApplication;
   }

   /**
    * Sets the value of the GAE application version for the web app. This value
    * must match the value used for the Application Version in GAE and the 
    * string specified as the value for <version> in in appengine-web.xml
    * @param googleAppEngineVersion
    */
   public void setGoogleAppEngineVersion(String googleAppEngineVersion) {
      this.googleAppEngineVersion = googleAppEngineVersion;
   }

   /**
    * Get the value of the GAE application version for this web app
    * @return String
    */
   public String getGoogleAppEngineVersion() {
      return this.googleAppEngineVersion;
   }

   /**
    * Sets the value used as the name of the web app
    * @param siteName
    */
   public void setSiteName(String siteName) {
      this.siteName = siteName;
   }

   /**
    * Gets the value used as the name for the web app
    * @return String
    */
   public String getSiteName() {
      return this.siteName;
   }

   /**
    * Sets the value of Theme for the web app. Must be one of the Themes
    * provided by Google Sites.  For example, Open Sky
    * @param theme
    */
   public void setTheme(String theme) {
      this.theme = theme.toLowerCase().replace(" ", "");
      if (this.theme.equals("launchdefault")) {
        this.theme = "default";
      } else if (this.theme.equals("blankslate")) {
        this.theme = "simplywhite";
      }
   }

   /**
    * Gets the value of Theme for the web app
    * @return String
    */
   public String getTheme() {
      return this.theme;
   }

   /**
    * Sets the value for the Google Web Fonts Url for the web app
    * @param googleWebFontsUrl
    */
   public void setGoogleWebFontsUrl(String googleWebFontsUrl) {
      this.googleWebFontsUrl = googleWebFontsUrl;
   }

   /**
    * Gets the value of the Google Web Fonts Url specified for the web app
    * @return String
    */
   public String getGoogleWebFontsUrl() {
      return this.googleWebFontsUrl;
   }

   /**
    * Method setFaviconUrl.
    * @param faviconUrl String
    */
   public void setFaviconUrl(String faviconUrl) {
      this.faviconUrl = faviconUrl;
   }

   /**
    * Method getFaviconUrl.
    * @return String
    */
   public String getFaviconUrl() {
      return this.faviconUrl;
   }

   /**
    * Method setAppleTouchIconUrl.
    * @param appleTouchIconUrl String
    */
   public void setAppleTouchIconUrl(String appleTouchIconUrl) {
      this.appleTouchIconUrl = appleTouchIconUrl;
   }

   /**
    * Method getAppleTouchIconUrl.
    * @return String
    */
   public String getAppleTouchIconUrl() {
      return this.appleTouchIconUrl;
   }

   /**
    * Method setDefaultPage.
    * @param defaultPage String
    */
   public void setDefaultPage(String defaultPage) {
      this.defaultPage = defaultPage;
   }

   /**
    * Method getDefaultPage
    * @return String
    */
   public String getDefaultPage() {
      return defaultPage;
   }

   /**
    * Method setLogoImage
    * @param url String
    */
   public void setLogoImage(String url) {
      this.logoImage = url;
   }

   /**
    * Method getLogoImage
    * @return String
    */
   public String getLogoImage() {
      return logoImage;
   }
   
   /**
    * Method setLogoHtml
    * @param html String
    */
   public void setLogoHtml(String html) {
      this.logoHtml = html;
   }

   /**
    * Method getLogoHtml
    * @return String
    */
   public String getLogoHtml() {
      return logoHtml;
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
    * Method getSiteFooter.
    * @return String
    */
   public String getSiteFooter() {
     StringBuilder result = new StringBuilder();
     for (Iterator<String> iterator = siteFooter.iterator(); iterator.hasNext();) {
       result.append(iterator.next());
     }
     return result.toString();
   }

   /**
    * Method setSiteFooter.
    * @param siteFooter String
    */
   public void setSiteFooter(String siteFooter) {
      //this.siteFooter = new Text(siteFooter);
     this.siteFooter = new ArrayList<String>();
     while (siteFooter.length() > 500) {
       this.siteFooter.add(siteFooter.substring(0, 499));
       siteFooter = siteFooter.substring(500, siteFooter.length());
     }
     if (siteFooter.length() > 0) {
       this.siteFooter.add(siteFooter);
     }
   }

   /**
    * Method setRevisionHistoryEnabled.
    * @param revisionHistoryEnabled String
    */
   public void setRevisionHistoryEnabled(String revisionHistoryEnabled) {
      this.revisionHistoryEnabled = revisionHistoryEnabled;
   }

   /**
    * Method getRevisionHistoryEnabled.
    * @return String
    */
   public String getRevisionHistoryEnabled() {
      return this.revisionHistoryEnabled;
   }

   /**
    * Setter for LookAndFeel
    * 
    * @param lookAndFeel
    */
   public void setLookAndFeel(LookAndFeel lookAndFeel) {
     this.lookAndFeel = lookAndFeel;
   }

   /**
    * Getter for LookAndFeel
    * 
    * @return The LookAndFeel enumeration for the current Site configuration object
    */
   public LookAndFeel getLookAndFeel() {
     return lookAndFeel;
   }

   /**
    * Setter for Style
    * @param style
    */
   public void setStyle(Style style) {
     this.style = style;
   }

   /**
    * Getter for Style
    * @return The Style object associated with the current Site configuration object
    */
   public Style getStyle() {
     return style;
   }
   
   /**
    * Set the value used for gwtRpcErrorMessage
    * 
    * @param message
    */
   public void setGwtRpcErrorMessage(String message) {
     this.gwtRpcErrorMessage = message;
   }
   
   /**
    * Gets the value used for gwtRpcErrorMessage
    * 
    * @return
    */
   public String getGwtRpcErrorMessage() {
     return gwtRpcErrorMessage;
   }

   /**
    * Indicates this instance of Site is not an accurate representation of
    * the current configuration of the Site. This can happen when a new
    * uncustomized version of the site is deployed, but the configuration has
    * yet to be completed. For example, the site is installed but the
    * configuration wizard has not been run. In this case (empty == true), the
    * site object cannot be used and the configuration wizard should be called.
    * 
    * @param empty
    */
   public void setEmpty(boolean empty) {
      this.empty = empty;
   }
   
   /**
    * Returns true if the Site object is empty, i.e., not an accurate reflection
    * of the current configuration of the site. This condition can and should
    * be tested for as in the following example usage;
    * <pre>
    * {@code Site site = Dao.getSite();
    * if (site.isEmpty())
    *   doSomething(); // Log an error and call the configuration wizard
    * else
    *   doSomethingElse()}
    * </pre>
    * @return the boolean value of the empty field
    */
   public boolean isEmpty() {
     return this.empty;
   }

   /**
    * Output object as a named JavaScript object to be used as an associated array of name/value
    * pairs to be bound to a GWT Dictionary
    * 
    * @see com.google.gwt.i18n.client.Dictionary
    */
   public String toDictionary() {
     StringBuilder dictionary = new StringBuilder("<script>").append(EOL);
     dictionary.append("var Site = {").append(EOL);
     dictionary.append("siteName: " + "\"" + siteName + "\",").append(EOL);
     dictionary.append("lookAndFeel: " + "\"" + lookAndFeel.toString() + "\",").append(EOL);
     dictionary.append("style: " + "\"" + style.getClass().getName() + "\",").append(EOL);
     dictionary.append("theme: " + "\"" + theme + "\",").append(EOL);
     dictionary.append("googleWebFontsUrl: " + "\"" + googleWebFontsUrl + "\",").append(EOL);
     dictionary.append("faviconUrl: " + "\"" + faviconUrl + "\",").append(EOL);
     dictionary.append("appleTouchIconUrl: " + "\"" + appleTouchIconUrl + "\",").append(EOL);
     dictionary.append("defaultPage: " + "\"" + defaultPage + "\",").append(EOL);
     dictionary.append("logoImage: " + "\"" + logoImage + "\",").append(EOL);
     dictionary.append("logoHtml: " + "\"" + logoHtml + "\",").append(EOL);
     dictionary.append("displayLogoAs: " + "\"" + displayLogoAs.toString() + "\",").append(EOL);
     dictionary.append("gwtRpcErrorMessage: " + "\"" + gwtRpcErrorMessage + "\"").append(EOL);
     dictionary.append("};").append(EOL);
     dictionary.append("</script>").append(EOL);
     return dictionary.toString();
   }
   
   public Site fromDictionary(Dictionary dictionary) {
     Site site = new Site();
     site.setSiteName(dictionary.get("siteName"));
     /*
     for (LookAndFeel lookAndFeel: LookAndFeel.values()) {
       if (lookAndFeel.toString().equals(dictionary.get("lookAndFeel"))) {
         site.setLookAndFeel(lookAndFeel);
       }
     }
     */
     site.setLookAndFeel(LookAndFeel.valueOf(dictionary.get("lookAndFeel")));
     site.setTheme(dictionary.get("theme"));
     site.setGoogleWebFontsUrl(dictionary.get("googleWebFontsUrl"));
     site.setFaviconUrl(dictionary.get("faviconUrl"));
     site.setAppleTouchIconUrl(dictionary.get("appleTouchIconUrl"));
     site.setDefaultPage(dictionary.get("defaultPage"));
     site.setLogoImage(dictionary.get("logoImage"));
     site.setLogoHtml(dictionary.get("logoHtml"));
     for (DisplayLogoAs displayLogoAs: DisplayLogoAs.values()) {
       if (displayLogoAs.getLabel().equals(dictionary.get("displayLogoAs"))) {
         site.setDisplayLogoAs(dictionary.get("displayLogoAs"));
       }
     }
     site.setGwtRpcErrorMessage(dictionary.get("gwtRpcErrorMessage"));
     return site;
   }

  public Object getEncodedKey() {
    return encodedKey;
  }
  
  public String getTag() {
      return tag;
  }

  public void setTag(String tag) {
      this.tag = tag;
  }
  
  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }
  
  public String getDescription() {
      return description;
  }

  public void setDescription(String description) {
      this.description = description;
  }
  
  public String getApplicationId() {
      return applicationId;
  }
  
  public void setApplicationId(String applicationId) {
      this.applicationId = applicationId;
  }
   
   /**
    * Provides for debugging purposes only
    * @return String
    */
   /*
    * FIX-REFLECTION NOT SUPPORTED IN GWT
   @Override
   public String toString() {
      final StringBuilder obj = new StringBuilder(this.getClass().getName());
      final Field[] fields = this.getClass().getDeclaredFields();
      obj.append("Object {" + EOL);
      for (Field field: fields) {
         obj.append("   ");
         try {
            obj.append(field.getName());
            obj.append(": ");
            obj.append(field.get(this));
         }
         catch (IllegalAccessException ex) {
            this.log.warning(ex.getMessage());
         }
         obj.append(EOL);
      } 
      obj.append("}" + EOL);
      return obj.toString();
   }
   */
}