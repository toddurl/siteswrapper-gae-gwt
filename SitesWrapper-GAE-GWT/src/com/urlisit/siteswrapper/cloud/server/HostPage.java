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
package com.urlisit.siteswrapper.cloud.server;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Landing;
import com.urlisit.siteswrapper.cloud.model.Item;

/**
 * Generates a GWT host page based on the parameters passed to the constructor and initializer
 * methods of the inner class HostPage.Builder and the state of the Site, Style, Page and Landing
 * objects in the GAE Data-store.
 * 
 * The resulting HTML page includes a reference to a GWT {@code <module>.nocache.js} in a {@code
 * <script>} HTML tag. This page may also contain HTML elements in its body, some of which your GWT
 * module may reference or modify.
 * 
 * @author  Todd Url <toddurl @ urlisit.com>
 * @version 1.0
 * @since   05-31-2012
 */
public class HostPage {

  /**
   * Class specific logger
   */
  private final Logger log = Logger.getLogger(this.getClass().getName());
  
  /**
   * Determines default encoding and decoding to apply to hostPage request names
   */
  private static final String CHARACTER_SET = "UTF-8";

  /**
   * Required parameter - Typically the name specified in the {@code <module rename-to=''>} tag of
   * the module XML file of the GWT project.
   */
  private final String gwtModule;
  
  /**
   * The page name portion of the URI without the .html extension, hostPage is used to lookup
   * the the correct page to be displayed by matching it to fields of in Page or Landing
   */
  private final String hostPage;

  /**
   * Specifies which contentMenuItem should be displayed in an iframe in the noscript portion of the
   * resulting hostPage
   */
  private final String eSite;

  /**
   * Indicates the resulting host page should be generated for the desktop or mobile device
   */
  private final String agent;

  /**
   * An instance of Site
   */
  private final Site site;

  /**
   * An instance of Page
   */
  private final Page page;

  /**
   * An instance of Landing
   */
  @SuppressWarnings("unused")
  private final Landing landing;

  /**
   * Collection of Page objects
   */
  private final List<Page> pages;

  /**
   * Collection of Landing objects
   */
  private final List<Landing> landings;
  
  /**
   * Relative path to the package containing resource files
   */
  private static final String PATH = "../resources/";

  /**
   * Regular expression which matches end of line. Used to read the entire contents of a file and
   * return it as a String via Scanner.
   */
  private static final String EOF = "\\Z";

  /**
   * Line separator appropriate for the current platform
   */
  private static final String EOL = System.getProperty("line.separator");

  /**
   * Provides a builder object to be used as a parameter to the HostPage constructor
   * 
   * @author  Todd Url <toddurl @ urlisit.com>
   * @version 1.0
   * @since   05-31-2012
   */
  public static class Builder {

    /**
     * Class specific logger
     */
    private Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * Required parameter - Typically the name specified in the {@code <module rename-to=''>} tag of
     * the module XML file of the GWT project.
     */
    
    private String  gwtModule = new Scanner(this.getClass().getResourceAsStream("../URL_IS_IT.gwt.x" +
        "ml")).useDelimiter(EOF).next();

    /**
     * Optional parameter - The page name portion of the URI without the .html extension, hostPage
     * is used to lookup the the correct page to be displayed by matching it to fields of in Page or
     * Landing
     */
    private String hostPage = DAO.getDefaultPage().getPageName();

    /**
     * Specifies the contentMenuItemName which should be displayed as part of the hostPage
     */
    private String eSite = "";

    /**
     * Indicates the resulting host page should be generated for the desktop or mobile device
     */
    private String agent = "desktop";

    /**
     * An instance of Site
     */
    private Site site = DAO.getSite();

    /**
     * An instance of Page
     */
    private Page page = DAO.getDefaultPage();

    /**
     * An empty instance of Landing
     */
    private Landing landing = DAO.newLanding();

    /**
     * Collection of Page objects
     */
    private List<Page> pages = DAO.getPageList();

    /**
     * Collection of Landing objects
     */
    private List<Landing> landings = DAO.getLandingList();

    /**
     * Returns a Builder which ensures proper initialization of HostPage optional parameters
     */
    public Builder() {
      /*
       * Extract the GWT module name from the rename-to attribute of the module tag in the GWT
       * module.xml file, the contents of which by default are assigned to gwtModule.
       */
       final int beginDex = gwtModule.indexOf("<module rename-to='") + "<module rename-to='".length();
       final int endIndex = gwtModule.indexOf("'>", beginDex);
       gwtModule = gwtModule.substring(beginDex, endIndex);
    }
    
    /**
     * Overrides the default value for gwtModule
     * 
     * By default gwtModule is the string specified for the rename-to attribute of the module tag in
     * the gwt.xml configuration file for the GWT module. This value appears in the host page as the
     * src attribute of the {@code <script>} tag which references your GWT module. For example,
     * {@code <script type="text/javascript" src="gwtModule/gwtModule.nocache.js"></script>}
     * 
     * @param value Name of the GWT Module to be loaded by the host page
     * @return Builder instance having value for gwtModule
     */
    public Builder gwtModule(String value) {
      gwtModule = value;
      return this;
    }
    
    /**
     * Overrides the default value for hostPage which is an DAO.getDefaultPage().getPageName()
     * 
     * Value must be a valid Page or Landing name as defined in the GAE data-store for Page and
     * Landing objects having a revision value of 'current' or and empty string. In the case of value
     * specified as an empty string, or a string which is not a valid name of an existing Page or
     * Landing object, the default value for hostPage is used, i.e., the value specified for
     * defaultPage in the Site configuration object, which is returned by the call
     * DAO.getDefaultPage.getPageName().
     * 
     * @param value
     * @return Builder instance having a modified value for hostPage
     */
    public Builder hostPage(String value) {
      boolean validRequest = false;
      if (!value.isEmpty() && value != null) {
        for (Page page: pages) {
          if (page.getPageName().equals(value)) {
            validRequest = true;
            hostPage = page.getPageName();
          }
        }
        for (Landing landing: landings) {
          if (landing.getName().equals(value)) {
            validRequest = true;
            hostPage = landing.getName();
          }
        }
      }
      if (!validRequest) {
        hostPage = DAO.getDefaultPage().getPageName();
      }
      return this;
    }

    /**
     * Overrides the default value for eSite which is null
     * 
     * @param value
     * @return Builder instance having a modified value for eSite
     */
    public Builder eSite(String value) {
      if (value != null) {
        eSite = value;
      }
      return this;
    }

    /**
     * Overrides the default value for agent which is 'desktop'
     * 
     * @param value
     * @return Builder instance having a modified value for agent
     */
    public Builder agent(String value) {
      agent = value;
      return this;
    }

    /**
     * Overrides the default value for site which is DAO.getSite();
     * 
     * @param value
     * @return Builder instance having a modified value for site
     */
    public Builder site(Site value) {
      site = value;
      return this;
    }

    /**
     * Overrides the default value for page which is DAO.getDefaultPage()
     * 
     * @param value
     * @return Builder instance having a modified value for page
     */
    public Builder page(Page value) {
      page = value;
      return this;
    }

    /**
     * Overrides the default value for landing which is DAO.getNewLanding()
     * 
     * @param value
     * @return Builder instance having a modified value for landing
     */
    public Builder landing(Landing value) {
      landing = value;
      return this;
    }

    /**
     * Overrides the default value for pages which is null
     * 
     * @param value
     * @return Builder instance having a modified value for pages
     */
    public Builder pages(List<Page> value) {
      pages = value;
      return this;
    }

    /**
     * Overrides the default value for landings which is DAO.getLandingsList()
     * 
     * @param value List of Landing objects
     * @return Builder instance having a modified value for landings
     */
    public Builder landings(List<Landing> value) {
      landings = value;
      return this;
    }

    /**
     * Returns a new HostPage based on the state of the Builder
     * 
     * @return new HostPage
     */
    public HostPage build() {
      return new HostPage(this);
    }

    /**
     * Provides printable information for debugging purposes
     *
     * @return String representation of the runtime object
     */
    @Override
    public String toString() {
      final String eol = System.getProperty("line.separator"); // $codepro.audit.disable stringLiterals
      final StringBuilder obj = new StringBuilder(this.getClass().getName());
      final Field[] fields = this.getClass().getDeclaredFields();
      obj.append('{');
      obj.append(eol);
      for (Field field: fields) {
        try {
          obj.append(field.getName());
          obj.append(':');
          obj.append(field.get(this));
        } catch (IllegalAccessException ex) {
          log.warning(ex.getMessage());
        }
        obj.append(eol);
      }
      obj.append('}');
      obj.append(eol);
      return obj.toString();
    }
  }

  /**
   * Returns a new instance of HostPage using builder
   * 
   * @param builder
   */
  private HostPage(Builder builder) { // $codepro.audit.disable unusedMethod
    gwtModule = builder.gwtModule;
    hostPage = builder.hostPage;
    eSite = builder.eSite;
    agent = builder.agent;
    site = builder.site;
    page = builder.page;
    landing = builder.landing;
    pages = builder.pages;
    landings = builder.landings;
  }

  private String getResource(String file) {
    String res = file;
    try {
      res = new Scanner(this.getClass().getResourceAsStream(PATH + res)).useDelimiter(EOF).next();
    } catch (NullPointerException e) {
      log.warning("Not found or " + e.getMessage() + " resource file " + res);
      res = e.getMessage() + " resource file " + res + " not found";
    }
    return res;
  }

  public String toMarkup() {
    final StringBuilder markup = new StringBuilder();
    markup.append(getHeader());
    markup.append(getBody());
    return markup.toString();
  }

  private String getHeader() {
    String header = getResource("HEADER.html");
    header = header.replace("TITLE", getResource("TITLE.html").replace("TITLE", hostPage));
    header = header.replace("CSS_STYLE", getResource("CSS_STYLE.html").replace("CSS_STYLE", ""));
    header = header.replace("GOOGLE_WEB_FONTS_LINK", getResource("GOOGLE_WEB_FONTS_LINK.html").replace("URL", site.getGoogleWebFontsUrl()));
    header = header.replace("GOOGLE_SITES_THEME_LINK", getResource("GOOGLE_SITES_THEME_LINK.html").replace("THEME", site.getTheme()));
    header = header.replace("FAVICON_LINK", getResource("FAVICON_LINK.html").replace("URL", site.getFaviconUrl()));
    header = header.replace("APPLE_TOUCH_ICON_LINK", getResource("APPLE_TOUCH_ICON_LINK.html").replace("URL", site.getAppleTouchIconUrl()));
    header = header.replace("SITE_DICTIONARY", site.toDictionary());
    header = header.replace("STYLE_DICTIONARY", site.getStyle().toDictionary());
    header = header.replace("LOOK_AND_FEEL_DICTIONARY", site.getLookAndFeel().toDictionary());
    header = header.replace("GWT_MODULE_LINK", getResource("GWT_MODULE_LINK.html").replace("GWT_MODULE", gwtModule));
    return header;
  }
  
  private String getBody() {
    String body = getResource("BODY.html");
    body = body.replace("HOST_PAGE", hostPage);
    body = body.replace("NOSCRIPT", getHostPageNoscript());
    body = body.replace("BODY", "");
    return body;
  }
  
  private RequestType getRequestType() {
    RequestType type = null;
    for (Page page: pages) {
      if (page.getPageName().equals(hostPage)) {
        type = RequestType.PAGE;
      }
    }
    for (Landing landing: landings) {
      if (landing.getName().equals(hostPage)) {
        type =  RequestType.LANDING;
      }
    }
    /*
    for (Item item: DAO.getItemList()) {
      if (item.getName().equals(hostPage)) {
        type = RequestType.INFORMATION;
      }
    }
    */
    return type;
  }
  
  private String getNoscript() {
    String noscript;
    switch (getRequestType()) {
      case PAGE:
        noscript = "Type of page is Page";
        break;
      case LANDING:
        noscript = "Type of page is Landing";
        break;
      case INFORMATION:
        noscript = "Type of page is Information";
        break;
      default:
        noscript = "Type was DEFAULT";
        break;
    }
    return noscript;
  }
  
  private String getHostPageNoscript() {
    String noscript = getResource("NOSCRIPT.html");
    /*
     * Determine if the requested hostPage is a Page or Landing and assemble the noscript section
     * appropriately
     */
    for (Page page: pages) {
      if (page.getPageName().equals(hostPage)) {
        /*
         * Set the logo as either an image or html
         */
        if (page.getDisplayLogoAs().equals("Html")) {
          noscript = noscript.replace("LOGO_HTML<img src=\"LOGO_IMAGE\">", page.getLogoHtml());
        } else {
          noscript = noscript.replace("LOGO_HTML", "");
          noscript = noscript.replace("LOGO_IMAGE", page.getLogoImage());
        }
        /*
         * Set custom search
         */
        if (page.getCustomSearchEnabled()) {
          noscript = noscript.replace("CUSTOM_SEARCH_HTML", page.getCustomSearchUrls(0));
        } else {
          noscript = noscript.replace("CUSTOM_SEARCH_HTML", "");
        }
        /*
         * Set the page title
         */
        if (page.getShowPageTitle()) {
          noscript = noscript.replace("PAGE_TITLE", page.getPageName());
        }
        /*
         * Determine if the hostPage has a contentMenu by testing if the size of contentMenuItemName
         */
        StringBuilder contentMenu = new StringBuilder(getResource("CONTENT_MENU.html"));
        if (page.sizeOfContentMenuItemName() > 0) {
          StringBuilder contentMenuItems = new StringBuilder();
          String decodedName = "";
          for (String name: page.getContentMenuItemNames()) {
            /*
             * contentMenuItemNames are stored URL encoded so need to be decoded when used as readable
             * elements. Encoding and decoding allows arbitrary same arbitrary html to be used for
             * both the anchor link name as well as the value for the eSite parameter. Unfortunately
             * the servlet automatically decodes the parameter value so the contentMemuItemName needs
             * to be decoded as well in order to perform the comparison and used for the visual
             * element.
             */
            try {
              decodedName = URLDecoder.decode(name, CHARACTER_SET);
            } catch (UnsupportedEncodingException e) {
              log.warning(e.getMessage());
            }
            if (decodedName.equals(eSite)) {
              contentMenuItems.append(getResource("CONTENT_MENU_ITEM_SELECTED.html")
                  .replace("HOST_PAGE", hostPage)
                  .replace("DECODED_NAME", decodedName)
                  .replace("NAME", name));
            } else {
              contentMenuItems.append(getResource("CONTENT_MENU_ITEM_UNSELECTED.html")
                  .replace("HOST_PAGE", hostPage)
                  .replace("DECODED_NAME", decodedName)
                  .replace("NAME", name));
            }
          }
          contentMenu.replace(contentMenu.indexOf("CONTENT_MENU_ITEMS"),
              contentMenu.indexOf("CONTENT_MENU_ITEMS") + "CONTENT_MENU_ITEMS".length(),
              contentMenuItems.toString());
          for (String name: page.getContentMenuItemNames()) {
            try {
              decodedName = URLDecoder.decode(name, CHARACTER_SET);
            } catch (UnsupportedEncodingException e) {
              log.warning(e.getMessage());
            }
            if (decodedName.equals(eSite)) {
              contentMenu.append(getResource("CONTENT_MENU_IFRAME.html").replace("URL",
                  page.getContentMenu().get(name)));
            }
          }
        }
        /*
         * Set main content based on contentLayout
         * TODO Add contentLayouts in addition to Left sidebar as available in Google Sites
         * * One column (simple)
         * * Two column (simple)
         * * Three column (simple)
         * * One Column
         * * Two Column
         * * Three Column
         * * Right sidebar
         * * Left and right sidebar
         */
        if (page.getContentLayout().equals("Left sidebar")) {
          String mainContent = getResource("MAIN_CONTENT_LEFT_SIDEBAR.html");
          mainContent = mainContent.replace("CONTENT_HEADER", page.getContentHeader());
          mainContent = mainContent.replace("CONTENT_LEFT_SIDEBAR", page.getContentLeftSidebar());
          mainContent = mainContent.replace("CONTENT_COLUMN_ONE", contentMenu.toString() + page.getContentColumnOne());
          mainContent = mainContent.replace("CONTENT_FOOTER", page.getContentFooter());
          noscript = noscript.replace("MAIN_CONTENT", mainContent);
        }
      }
    }
    for (Landing landing: landings) {
      if (landing.getName().equals(hostPage)) {
        noscript = noscript.replace("LOGO_HTML<img src=\"LOGO_IMAGE\">", landing.getName());
        noscript = noscript.replace("CUSTOM_SEARCH_HTML", "");
        noscript = noscript.replace("PAGE_TITLE", "");
        //StringBuilder rainContent = new StringBuilder(getResource("LANDING_MAIN_CONTENT.html"));
        //rainContent.replace(rainContent.indexOf("DESCRIPTION"),
        //    rainContent.indexOf("DESCRIPTION") + "DESCRIPTION".length(), landing.getDescription());
        String mainContent = getResource("LANDING_MAIN_CONTENT.html");
        mainContent = mainContent.replace("DESCRIPTION", landing.getDescription());
        mainContent = mainContent.replace("IMAGE_HTML", getResource("IMG.html").replace("URL", landing.getImageUrl()));
        mainContent = mainContent.replace("VIDEO_HTML", getResource("VIDEO_EMBED.html").replace("VIDEO_URL", landing.getVideoUrl()));
        mainContent = mainContent.replace("LINK_HTML", getResource("ANCHOR.html").replace("URL", landing.getLinkUrl()).replace("LINK", hostPage));
        mainContent = mainContent.replace("LANDING_SPECIFICATIONS", getLandingSpecifications());
        noscript = noscript.replace("MAIN_CONTENT", mainContent);
      }
    }
    String navBar = getResource("HORIZONTAL_NAV_BAR.html");
    StringBuilder elements = new StringBuilder();
    String direction = "";
    for (Page page: pages) {
      direction = page.getMainMenuDirection();
      if (direction.equals("Horizontal") || direction.equals("Both")) {
        if (page.getPageName().equals(hostPage)) {
          try {
            elements.append(getResource("HORIZONTAL_NAV_BAR_ELEMENT_SELECTED.html")
                .replace("ENCODED_PAGE_NAME", URLEncoder.encode(page.getPageName(), CHARACTER_SET))
                .replace("PAGE_NAME", page.getPageName()));
          } catch (UnsupportedEncodingException e) {
            log.warning(e.getMessage());
          }
        } else {
          try {
            elements.append(getResource("HORIZONTAL_NAV_BAR_ELEMENT_UNSELECTED.html")
                .replace("ENCODED_PAGE_NAME", URLEncoder.encode(page.getPageName(), CHARACTER_SET))
                .replace("PAGE_NAME", page.getPageName()));
          } catch (UnsupportedEncodingException e) {
            log.warning(e.getMessage());
          }
        }
      }
    }
    navBar = navBar.replace("HORIZONTAL_ELEMENTS", elements.toString());
    noscript = noscript.replace("HORIZONTAL_NAV_BAR", navBar);
    /*
     * Vertical navigation bar
     */
    navBar = getResource("VERTICAL_NAV_BAR.html");
    elements.delete(0, elements.length());
    direction = "";
    for (Page page: pages) {
      direction = page.getMainMenuDirection();
      if (direction.equals("Vertical") || direction.equals("Both")) {
        if (page.getPageName().equals(hostPage)) {
          try {
            elements.append(getResource("VERTICAL_NAV_BAR_ELEMENT_SELECTED.html")
                .replace("ENCODED_PAGE_NAME", URLEncoder.encode(page.getPageName(), CHARACTER_SET))
                .replace("PAGE_NAME", page.getPageName()));
          } catch (UnsupportedEncodingException e) {
            log.warning(e.getMessage());
          }
        } else {
          try {
            elements.append(getResource("VERTICAL_NAV_BAR_ELEMENT_UNSELECTED.html")
                .replace("ENCODED_PAGE_NAME", URLEncoder.encode(page.getPageName(), CHARACTER_SET))
                .replace("PAGE_NAME", page.getPageName()));
          } catch (UnsupportedEncodingException e) {
            log.warning(e.getMessage());
          }
        }
      }
    }
    navBar = navBar.replace("VERTICAL_ELEMENTS", elements.toString());
    noscript = noscript.replace("VERTICAL_NAV_BAR", navBar);
    /*
     * Landing navigation bar
     */
    navBar = getResource("LANDING_NAV_BAR.html");
    elements.delete(0, elements.length());
    for (Landing landing: landings) {
      if (landing.getName().equals(hostPage)) {
        try {
          elements.append(getResource("LANDING_NAV_BAR_ELEMENT_SELECTED.html")
              .replace("ENCODED_NAME", URLEncoder.encode(landing.getName(), CHARACTER_SET))
              .replace("NAME", landing.getLinkName()));
        } catch (UnsupportedEncodingException e) {
          log.warning(e.getMessage());
        }
      } else {
        try {
          elements.append(getResource("LANDING_NAV_BAR_ELEMENT_UNSELECTED.html")
              .replace("ENCODED_NAME", URLEncoder.encode(landing.getName(), CHARACTER_SET))
              .replace("NAME", landing.getLinkName()));
        } catch (UnsupportedEncodingException e) {
          log.warning(e.getMessage());
        }
      }
    }
    navBar = navBar.replace("LANDINGS", elements.toString());
    noscript = noscript.replace("LANDING_NAV_BAR", navBar);
    noscript = noscript.replace("SITE_FOOTER", site.getSiteFooter());
    return noscript;
  }
  
  private String getLandingSpecifications() {
    String specifications = getResource("LANDING_SPECIFICATIONS.html");
    for (Landing landing: landings) {
      if (landing.getName().equals(hostPage)) {
        specifications = specifications.replace("SPECIFICATION_ONE", landing.getSpecificationOne());
        specifications = specifications.replace("VALUE_ONE", landing.getValueOne());
        specifications = specifications.replace("SPECIFICATION_TWO", landing.getSpecificationTwo());
        specifications = specifications.replace("VALUE_TWO", landing.getValueTwo());
        specifications = specifications.replace("SPECIFICATION_THREE", landing.getSpecificationThree());
        specifications = specifications.replace("VALUE_THREE", landing.getValueThree());
        specifications = specifications.replace("SPECIFICATION_FOUR", landing.getSpecificationFour());
        specifications = specifications.replace("VALUE_FOUR", landing.getValueFour());
        specifications = specifications.replace("SPECIFICATION_FIVE", landing.getSpecificationFive());
        specifications = specifications.replace("VALUE_FIVE", landing.getValueFive());
        specifications = specifications.replace("SPECIFICATION_SIX", landing.getSpecificationSix());
        specifications = specifications.replace("VALUE_SIX", landing.getValueSix());
        specifications = specifications.replace("SPECIFICATION_SEVEN", landing.getSpecificationSeven());
        specifications = specifications.replace("VALUE_SEVEN", landing.getValueSeven());
        specifications = specifications.replace("SPECIFICATION_EIGHT", landing.getSpecificationEight());
        specifications = specifications.replace("VALUE_EIGHT", landing.getValueEight());
        specifications = specifications.replace("SPECIFICATION_NINE", landing.getSpecificationNine());
        specifications = specifications.replace("VALUE_NINE", landing.getValueNine());
        specifications = specifications.replace("SPECIFICATION_TEN", landing.getSpecificationTen());
        specifications = specifications.replace("VALUE_TEN", landing.getValueTen());
      }
    }
    return specifications;
  }

  /**
   * Provides printable information for debugging purposes
   *
   * @return String representation of the runtime object */
  @Override
  public String toString() {
    final String eol = System.getProperty("line.separator"); // $codepro.audit.disable stringLiterals
    final StringBuilder obj = new StringBuilder(this.getClass().getName());
    final Field[] fields = this.getClass().getDeclaredFields();
    obj.append('{');
    obj.append(eol);
    for (Field field: fields) {
      try {
        obj.append(field.getName());
        obj.append(':');
        obj.append(field.get(this));
      } catch (IllegalAccessException ex) {
        log.warning(ex.getMessage());
      }
      obj.append(eol);
    }
    obj.append('}');
    obj.append(eol);
    return obj.toString();
  }
  
  /**
   * Specifies a page type for operations which format markup
   * 
   * Must be one of PAGE, LANDING or INFORMATION
   */
  private static enum RequestType { PAGE, LANDING, INFORMATION };
  
}