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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.log.Log;

import com.urlisit.siteswrapper.cloud.model.Landing;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.LookAndFeel;
import com.urlisit.siteswrapper.cloud.model.Style;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Item;

/**
 * DAO, (Data Access Object) is a non-instantiable utility class which provides static methods for
 * accessing the JDOs (Java Data Objects) in the GAE (Google App Engine) Data-store.
 *
 * DAO provides a single point of access and is used to to perform <b>C</b>reate, <b>R</b>ead, 
 * <b>U</b>pdate and <b>D</b>elete operations against the Site, Style, Page and Information
 * configuration JDOs.
 * 
 * @author Todd Url <toddurl @ urlisit.com>
 * @version   1.0
 * @since   04-10-2012
 * @see com.urlisit.siteswrapper.cloud.model.Site
 * @see com.urlisit.siteswrapper.cloud.model.Style
 * @see com.urlisit.siteswrapper.cloud.model.LookAndFeel
 * @see com.urlisit.siteswrapper.cloud.model.Page
 */
public final class DAO {

  /**
   * Logger for class specific errors and exceptions
   */
  private static final Logger log = Logger.getLogger(DAO.class.getName());

  /**
   * Provides static access to the data-nucleus persistence manager
   */
  private static PersistenceManager pm = PMF.get().getPersistenceManager();

  /**
   * Allows applications to obtain persistent instances, values, and aggregate data from the GAE
   * Data-store.
   */
  private static Query query;

  /**
   * Regular expression which matches end of line. Used to read the entire contents of a file and
   * return it as a String via Scanner.
   */
  private static final String EOF = "\\Z";
  
  /**
   * Suppresses the default constructor to enforce non-instantiability
   */
  private DAO() {
    // Ensures non-instantiability from within
    throw new AssertionError(); // $codepro.audit.disable thrownExceptions
  }
  
  /**
   * Updates the Site configuration object based on the parameters associated with an
   * HttpServletRequest object.
   * 
   * Populates the fields of the current Site object in the GAE Data-store
   * by calling the getParameter methods of the HttpServletRequest parameter
   * as arguments to the corresponding setField methods of the Site object
   * having a vale of 'current' in it's revision field. At any moment in time
   * there is only one Site object having a revision value of 'current', and it
   * is that object which referred to as the current Site object and which is
   * updated by the updateSite method. If the enableRevisionHistory field of the
   * current Site object is true, updateSite sets the value of the revision
   * field of the current Site object to 'previous' and creates a new Site
   * object with a revision field value of 'current', thus creating a new
   * current Site configuration object. <b>Note</b> a subsequent call to
   * commitChange makes the new current Site configuration object permanent by
   * setting the value of the revision field of the previous Site object to a
   * string containing the current date and time. If the rollbackChange method
   * is called instead of commitChange, the new current Site object is deleted
   * and the value of the revision field in the previous Site object is set to
   * 'current', rolling back the original updateSite method call.
   * <p>
   * updateSite throws an IllegalStateException if any of the following
   * conditions are detected.
   * <ul>
   * <li>
   * The configurationDocId presented by the requesting client does not match
   * the value configured in the appengine-web.xml file.
   * <li>
   * The access scheme, in the production environment, is http as opposed to
   * https. This requirement does not apply in the development environment.
   * <li>
   * The http access method was anything other than an http post request.
   * <li>
   * The http post request originated outside the Google Docs domain.
   * <li>
   * The http post request was made from an agent other than a Google Apps
   * Script.
   * <li>
   * The request originated from a country other than the US.
   * </ul>
   * @see com.urlisit.siteswrapper.cloud.model.Site
   * 
   * @param req
   * @throws IllegalArgumentException
   * @throws IOException 
   */
  public static void updateSite(HttpServletRequest req, HttpServletResponse resp) throws
  IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    final Extent<Site> sites = pm.getExtent(Site.class, false);
    for (Site site : sites) {
       if (site.getRevision().equals("current")) {
         site.setRevision("previous");
         pm.makePersistent(site);
       }
    }
    final Extent<Style> styles = pm.getExtent(Style.class, false);
    for (Style style : styles) {
       if (style.getRevision().equals("current")) {
          style.setRevision("previous");
          pm.makePersistent(style);
       }
    }
    final Extent<Page> pages = pm.getExtent(Page.class, false);
    for (Page page : pages) {
       if (page.getRevision().equals("current")) {
          page.setRevision("previous");
          pm.makePersistent(page);
       }
    }
    final Extent<Landing> landings = pm.getExtent(Landing.class, false);
    for (Landing landing: landings) {
       if (landing.getRevision().equals("current")) {
          landing.setRevision("previous");
          pm.makePersistent(landing);
       }
    }
    final Extent<Item> items = pm.getExtent(Item.class, false);
    for (Item item : items) {
       if (item.getRevision().equals("current")) {
          item.setRevision("previous");
          pm.makePersistent(item);
       }
    }
    final Site site = new Site();
    site.setRevision("current");
    site.setGoogleAppEngineApplication(req.getParameter("googleAppEngineApplication"));
    site.setGoogleAppEngineVersion(req.getParameter("googleAppEngineVersion"));
    site.setSiteName(req.getParameter("siteName"));
    for (LookAndFeel lookAndFeel: LookAndFeel.values()) {
      if (lookAndFeel.getLabel().equals(req.getParameter("lookAndFeel"))) {
        site.setLookAndFeel(lookAndFeel);
      }
    }
    site.setTheme(req.getParameter("theme"));
    site.setGoogleWebFontsUrl(req.getParameter("googleWebFontsUrl"));
    site.setFaviconUrl(req.getParameter("faviconUrl"));
    site.setAppleTouchIconUrl(req.getParameter("appleTouchIconUrl"));
    site.setDefaultPage(req.getParameter("defaultPage"));
    site.setLogoImage(req.getParameter("logoImage"));
    site.setLogoHtml(req.getParameter("logoHtml"));
    site.setDisplayLogoAs(req.getParameter("displayLogoAs"));
    site.setSiteFooter(req.getParameter("siteFooter"));
    site.setGwtRpcErrorMessage(req.getParameter("gwtRpcErrorMessage"));
    site.setRevisionHistoryEnabled(req.getParameter("revisionHistoryEnabled"));
    site.setEmpty(false);
    pm.makePersistent(site);
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }
  
  /**
   * Creates a new Style object and sets the resulting objects fields to match
   * the values specified in the parameters of the HttpServletRequest object.
   * 
   * @param req
   * @param resp
   */
  public static void updateStyle(HttpServletRequest req, HttpServletResponse resp) throws
  IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    final Style style = new Style();
    style.setRevision("current");
    style.setLookAndFeel(req.getParameter("lookAndFeel"));
    style.setDescription(req.getParameter("description"));
    style.setPrimaryColor(req.getParameter("primaryColor"));
    style.setPrimaryAccentColor(req.getParameter("primaryAccentColor"));
    style.setSecondaryAccentColor(req.getParameter("secondaryAccentColor"));
    style.setTertiaryAccentColor(req.getParameter("tertiaryAccentColor"));
    style.setMainMenuFontFamily(req.getParameter("mainMenuFontFamily"));
    style.setMainMenuFontSize(req.getParameter("mainMenuFontSize"));
    style.setMainMenuSelectionFontColor(req.getParameter("mainMenuSelectionFontColor"));
    style.setMainMenuHoverFontColor(req.getParameter("mainMenuHoverFontColor"));
    style.setMainMenuSelectedFontColor(req.getParameter("mainMenuSelectedFontColor"));
    pm.makePersistent(style);
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }
  
  /**
   * Creates a new Page object and sets the resulting objects fields to match
   * the values specified in the parameters of the HttpServletRequest object.
   * 
   * @param req
   * @param resp
   */
  public static void updatePage(HttpServletRequest req, HttpServletResponse resp) throws
  IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    final Page page = new Page();
    final SimpleDateFormat lastmod = new SimpleDateFormat("yyyy-MM-dd");
    page.setRevision("current");
    page.setPageName(req.getParameter("pageName"));
    page.setShowPageTitle(req.getParameter("showPageTitle"));
    page.setLogoImage(req.getParameter("logoImage"));
    page.setLogoHtml(req.getParameter("logoHtml"));
    page.setDisplayLogoAs(req.getParameter("displayLogoAs"));
    page.setLanguageTranslationEnabled(req.getParameter("languageTranslationEnabled"));
    for (int i = 0; i < req.getParameterValues("translatedLanguages").length; i++) {
      if (!req.getParameterValues("translatedLanguages")[i].equals("undefined")) {
        page.setTranslatedLanguages(req.getParameterValues("translatedLanguages")[i]);
      }
    }
    page.setCustomSearchEnabled(req.getParameter("customSearchEnabled"));
    for (int i = 0; i < req.getParameterValues("customSearchUrls").length; i++) {
      if (!req.getParameterValues("customSearchUrls")[i].equals("undefined")) {
         page.setCustomSearchUrls(req.getParameterValues("customSearchUrls")[i]);
      }
    }
    page.setMainMenuType(req.getParameter("mainMenuType"));
    page.setMainMenuDirection(req.getParameter("mainMenuDirection"));
    page.setMainMenuSelectionHtml(req.getParameter("mainMenuSelectionHtml"));
    page.setMainMenuSelectedHtml(req.getParameter("mainMenuSelectedHtml"));
    try {
      for (int i = 0; i < req.getParameterValues("backgroundImageUrls").length; i++) {
        if (!req.getParameterValues("backgroundImageUrls")[i].equals("undefined")) {
          page.setBackgroundImageUrls(req.getParameterValues("backgroundImageUrls")[i]);
        }
      }
    } catch (NullPointerException e) {
      page.setBackgroundImageUrls("undefined");
    }
    for (int i = 0; i < req.getParameterValues("backgroundImageDurationSeconds").length; i++) {
      if (!req.getParameterValues("backgroundImageDurationSeconds")[i].equals("undefined")) {
        page.setBackgroundImageDurationSeconds(req.getParameterValues("backgroundImageDurationSeconds")[i]);
      }
    }
    if (req.getParameterValues("contentMenuItemName").length > 0) {
      String name;
      for (int i = 0; i < req.getParameterValues("contentMenuItemName").length; i++) {
        if (!req.getParameterValues("contentMenuItemName")[i].equals("undefined")) {
          name = "";
          try {
            name = URLEncoder.encode(req.getParameterValues("contentMenuItemName")[i], "UTF-8");
          } catch (UnsupportedEncodingException e) {
            log.warning(e.getMessage());
          }
            //page.setContentMenuItemName(req.getParameterValues("contentMenuItemName")[i]);
            page.setContentMenuItemName(name);
        }
      }
    }
    for (int i = 0; i < req.getParameterValues("contentMenuItemLink").length; i++) {
      if (!req.getParameterValues("contentMenuItemLink")[i].equals("undefined")) {
        page.setContentMenuItemLink(req.getParameterValues("contentMenuItemLink")[i]);
      }
    }
    page.setContentMenuStyleSheet(req.getParameter("contentMenuStyleSheet"));
    page.setContentLayout(req.getParameter("contentLayout"));
    page.setContentHeader(req.getParameter("contentHeader"));
    page.setContentColumnOne(req.getParameter("contentColumnOne"));
    page.setContentColumnTwo(req.getParameter("contentColumnTwo"));
    page.setContentColumnThree(req.getParameter("contentColumnThree"));
    page.setContentLeftSidebar(req.getParameter("contentLeftSidebar"));
    page.setContentRightSidebar(req.getParameter("contentRightSidebar"));
    page.setContentFooter(req.getParameter("contentFooter"));
    for (int i = 0; i < req.getParameterValues("messageHeaderText").length; i++) {
      if (!req.getParameterValues("messageHeaderText")[i].equals("undefined")) {
        page.setMessageHeaderText(req.getParameterValues("messageHeaderText")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messageBodyText").length; i++) {
      if (!req.getParameterValues("messageBodyText")[i].equals("undefined")) {
        page.setMessageBodyText(req.getParameterValues("messageBodyText")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messageInformationItem").length; i++) {
      if (!req.getParameterValues("messageInformationItem")[i].equals("undefined")) {
        page.setMessageInformationItem(req.getParameterValues("messageInformationItem")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messageHtmlColorCode").length; i++) {
      if (!req.getParameterValues("messageHtmlColorCode")[i].equals("undefined")) {
        page.setMessageHtmlColorCode(req.getParameterValues("messageHtmlColorCode")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messageWidthPercentOfPage").length; i++) {
      if (!req.getParameterValues("messageWidthPercentOfPage")[i].equals("undefined")) {
        page.setMessageWidthPercentOfPage(req.getParameterValues("messageWidthPercentOfPage")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messagePercentOfPageFromLeft").length; i++) {
      if (!req.getParameterValues("messagePercentOfPageFromLeft")[i].equals("undefined")) {
        page.setMessagePercentOfPageFromLeft(req.getParameterValues("messagePercentOfPageFromLeft")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messagePercentOfPageFromTop").length; i++) {
      if (!req.getParameterValues("messagePercentOfPageFromTop")[i].equals("undefined")) {
        page.setMessagePercentOfPageFromTop(req.getParameterValues("messagePercentOfPageFromTop")[i]);
      }
    }
    for (int i = 0; i < req.getParameterValues("messageDurationSeconds").length; i++) {
      if (!req.getParameterValues("messageDurationSeconds")[i].equals("undefined")) {
        page.setMessageDurationSeconds(req.getParameterValues("messageDurationSeconds")[i]);
      }
    }
    page.setLastmod(lastmod.format(new Date()));
    pm.makePersistent(page);
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }

  /**
   * Creates a new Landing object and sets it's field values to match the parameters specified in the
   * HttpServletRequest object.
   * 
   * @param req
   * @param resp
   */
  public static void updateLanding(HttpServletRequest req, HttpServletResponse resp)
      throws IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    final Landing landing = new Landing();
    final SimpleDateFormat lastmod = new SimpleDateFormat("yyyy-MM-dd");
    landing.setRevision("current");
    landing.setName(req.getParameter("name"));
    landing.setDescription(req.getParameter("description"));
    landing.setVideoUrl(req.getParameter("videoUrl"));
    landing.setImageUrl(req.getParameter("imageUrl"));
    landing.setLinkName(req.getParameter("linkName"));
    landing.setLinkUrl(req.getParameter("linkUrl"));
    landing.setSpecificationOne(req.getParameter("specificationOne"));
    landing.setValueOne(req.getParameter("valueOne"));
    landing.setSpecificationTwo(req.getParameter("specificationTwo"));
    landing.setValueTwo(req.getParameter("valueTwo"));
    landing.setSpecificationThree(req.getParameter("specificationThree"));
    landing.setValueThree(req.getParameter("valueThree"));
    landing.setSpecificationFour(req.getParameter("specificationFour"));
    landing.setValueFour(req.getParameter("valueFour"));
    landing.setSpecificationFive(req.getParameter("specificationFive"));
    landing.setValueFive(req.getParameter("valueFive"));
    landing.setSpecificationSix(req.getParameter("specificationSix"));
    landing.setValueSix(req.getParameter("valueSix"));
    landing.setSpecificationSeven(req.getParameter("specificationSeven"));
    landing.setValueSeven(req.getParameter("valueSeven"));
    landing.setSpecificationEight(req.getParameter("specificationEight"));
    landing.setValueEight(req.getParameter("valueEight"));
    landing.setSpecificationNine(req.getParameter("specificationNine"));
    landing.setValueNine(req.getParameter("valueNine"));
    landing.setSpecificationTen(req.getParameter("specificationTen"));
    landing.setValueTen(req.getParameter("valueTen"));
    landing.setLastmod(lastmod.format(new Date()));
    pm.makePersistent(landing);
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }

  /**
   * Creates a new Item object and sets it's field values to match the
   * parameters specified in the HttpServletRequest object.
   * 
   * @param req
   * @param resp
   */
  public static void updateItem(HttpServletRequest req, HttpServletResponse resp)
      throws IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    final Item item = new Item();
    final SimpleDateFormat lastmod = new SimpleDateFormat("yyyy-MM-dd");
    item.setRevision("current");
    item.setName(req.getParameter("name"));
    item.setDescription(req.getParameter("description"));
    item.setVideoUrl(req.getParameter("videoUrl"));
    item.setImageUrl(req.getParameter("imageUrl"));
    item.setLinkName(req.getParameter("linkName"));
    item.setLinkUrl(req.getParameter("linkUrl"));
    item.setSpecificationOne(req.getParameter("specificationOne"));
    item.setValueOne(req.getParameter("valueOne"));
    item.setSpecificationTwo(req.getParameter("specificationTwo"));
    item.setValueTwo(req.getParameter("valueTwo"));
    item.setSpecificationThree(req.getParameter("specificationThree"));
    item.setValueThree(req.getParameter("valueThree"));
    item.setSpecificationFour(req.getParameter("specificationFour"));
    item.setValueFour(req.getParameter("valueFour"));
    item.setSpecificationFive(req.getParameter("specificationFive"));
    item.setValueFive(req.getParameter("valueFive"));
    item.setSpecificationSix(req.getParameter("specificationSix"));
    item.setValueSix(req.getParameter("valueSix"));
    item.setSpecificationSeven(req.getParameter("specificationSeven"));
    item.setValueSeven(req.getParameter("valueSeven"));
    item.setSpecificationEight(req.getParameter("specificationEight"));
    item.setValueEight(req.getParameter("valueEight"));
    item.setSpecificationNine(req.getParameter("specificationNine"));
    item.setValueNine(req.getParameter("valueNine"));
    item.setSpecificationTen(req.getParameter("specificationTen"));
    item.setValueTen(req.getParameter("valueTen"));
    item.setLastmod(lastmod.format(new Date()));
    pm.makePersistent(item);
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }
  
  /**
   * Make updateSite, updatePage and updateItem changes permanent.
   * 
   * @param req
   * @param resp
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */
  public static void commitChange(HttpServletRequest req, HttpServletResponse resp) 
      throws IllegalArgumentException, IOException {
    //checkSignature(req, resp);
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    String revisionHistoryEnabled = "undefined";
    String revisionTimeDateStamp = "undefined";
    while (revisionHistoryEnabled.equals("undefined")) {
      Extent<Site> sites = pm.getExtent(Site.class, false);
      for (Site site : sites) {
        if (site.getRevision().equals("current")) {
          revisionHistoryEnabled = site.getRevisionHistoryEnabled();
        }
      }
    }
    final Extent<Site> sites = pm.getExtent(Site.class, false);
    for (Site site : sites) {
      if (site.getRevision().equals("previous")) {
        if (revisionHistoryEnabled.equals("Yes")) {
          //revisionTimeDateStamp = site.getKey().getName();
          revisionTimeDateStamp = new SimpleDateFormat("yy-MM-dd-HH:mm:ss").format(new Date());
          site.setRevision(revisionTimeDateStamp);
          pm.makePersistent(site);
        } else if (revisionHistoryEnabled.equals("No")) {
          pm.deletePersistent(site);
        } else {
          pm.close();
          throw new IllegalStateException("revisionHistoryEnabled was undefin" +
              "ed during commit of configuration update data");
        }
      }
    }
    final Extent<Style> styles = pm.getExtent(Style.class, false);
    for (Style style : styles) {
      if (style.getRevision().equals("previous")) {
        if (revisionHistoryEnabled.equals("Yes")) {
          style.setRevision(revisionTimeDateStamp);
          pm.makePersistent(style);
        } else if (revisionHistoryEnabled.equals("No")) {
          pm.deletePersistent(style);
        } else {
          pm.close();
          throw new IllegalStateException("revisionHistoryEnabled was undefin" +
              "ed during commit of configuration update data");
        }
      }
    }
    final Extent<Page> pages = pm.getExtent(Page.class, false);
    for (Page page : pages) {
      if (page.getRevision().equals("previous")) {
        if (revisionHistoryEnabled.equals("Yes")) {
          page.setRevision(revisionTimeDateStamp);
          pm.makePersistent(page);
        } else if (revisionHistoryEnabled.equals("No")) {
          pm.deletePersistent(page);
        } else {
          pm.close();
          throw new IllegalStateException("revisionHistoryEnabled was undefin" +
              "ed during commit of configuration update data");
        }
      }
    }
    final Extent<Landing> landings = pm.getExtent(Landing.class, false);
    for (Landing landing : landings) {
      if (landing.getRevision().equals("previous")) {
        if (revisionHistoryEnabled.equals("Yes")) {
          landing.setRevision(revisionTimeDateStamp);
          pm.makePersistent(landing);
        } else if (revisionHistoryEnabled.equals("No")) {
          pm.deletePersistent(landing);
        } else {
          pm.close();
          throw new IllegalStateException("revisionHistoryEnabled was undefined during commit of co" +
                "nfiguration update data");
        }
      }
    }
    final Extent<Item> items = pm.getExtent(Item.class, false);
    for (Item item : items) {
      if (item.getRevision().equals("previous")) {
        if (revisionHistoryEnabled.equals("Yes")) {
          item.setRevision(revisionTimeDateStamp);
          pm.makePersistent(item);
        } else if (revisionHistoryEnabled.equals("No")) {
          pm.deletePersistent(item);
        } else {
          pm.close();
          throw new IllegalStateException("revisionHistoryEnabled was undefined during commit of co" +
                "nfiguration update data");
        }
      }
    }
    pm.close();
    resp.getWriter().write(getId());
    resp.setStatus(200);
  }

  /**
   * Returns an object reflecting the current Site configuration.
   * 
   * The object returned reflects the content of the datastore and consequently
   * may not contain useful data. This is especially true in the case that there
   * isn't a valid Site configuration object having the value of 'current' in
   * it's revision property, such as the time between initial installation of
   * the webapp and the initial update of the datastore. See <b>Site</b> for
   * more information. In this case (the case of an invalid or empty Site
   * configuration object), getSite will return an empty Site object.
   * This condition can and should be tested for using the isEmpty()
   * method of the returned Site object as in the following example usage;
   * <pre>
   * {@code Site site = Dao.getSite();
   * if (site.isEmpty())
   *   doSomething(); // Log an error and call the configuration wizard
   * else
   *   doSomethingElse()}
   * </pre>
   *   
   * @see com.urlisit.siteswrapper.cloud.model.Site
   * @return A Site configuration object.
   */
  public static Site getSite() {
    final PersistenceManager pm = PMF.get().getPersistenceManager();
    query = pm.newQuery(Site.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    @SuppressWarnings("unchecked")
    final List<Site> sites = (List<Site>) query.execute("current");
    Site site, detached = null;
    if (sites.isEmpty()) {
      log.warning("sites was empty");
      site = new Site();
      site.setEmpty(true);
      return site;
    } else {
      site = sites.get(0);
    }
    detached = pm.detachCopy(site);
    final String style = detached.getLookAndFeel().getLabel();
    query = pm.newQuery(Style.class);
    query.setFilter("lookAndFeel == lookAndFeelParameter");
    query.declareParameters("String lookAndFeelParameter");
    @SuppressWarnings("unchecked")
    final List<Style> styles = (List<Style>) query.execute(style);
    detached.setStyle(styles.get(0));
    return detached;
  }

  /**
   * Returns a List of current Page objects from the datastore. A Page object is
   * considered current if the value of the revision field is set to 'current'.
   * In the case that there are no current Page objects in the datastore,
   * getPageList will return an empty List<Page>.
   * This condition can and should be tested for using the isEmpty()
   * method of the returned List<Page> object as in the following example usage;
   * <pre>
   * {@code List<Page> pages = Dao.getPageList();
   * if (pages.isEmpty())
   *   doSomething(); // Log an error and call the configuration wizard
   * else
   *   doSomethingElse()}
   * </pre>
   *   
   * @see com.urlisit.siteswrapper.cloud.model.Page
   * @return A List collection of Page objects
   */
  public static List<Page> getPageList() {
    query = pm.newQuery(Page.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    @SuppressWarnings("unchecked")
    final List<Page> pages = (List<Page>) query.execute("current");
    return pages;
  }
  
  public static Page[] getPageArray() {
    final List<Page> pageList = getPageList();
    final Page[] pageArray = new Page[pageList.size()];
    int x = 0;
    for (Page page: pageList) {
      pageArray[x++] = page;
    }
    return pageArray;
  }
  
  public static Page[] getPages() {
    query = pm.newQuery(Page.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    @SuppressWarnings("unchecked")
    final List<Page> pagesList = (List<Page>) query.execute("current");
    final Page[] pagesArray = new Page[pagesList.size()];
    int pageNumber = 0;
    for (Page page: pagesList) {
      pagesArray[pageNumber++] = pm.detachCopy(page);
    }
    return pagesArray;
  }
  
  public static Page getDefaultPage() {
    Page defaultPage = null;
    final Site site = getSite();
    if (site.isEmpty()) {
      log.warning("Site was empty while getting default page");
    }
    final List<Page> pages = getPageList();
    for (Page page: pages) {
      if (site.getDefaultPage().equals(page.getPageName())) {
        defaultPage = pm.detachCopy(page);
        break;
      }
    }
    return defaultPage;
  }
  
  public static List<Landing> getLandingList() {
    query = pm.newQuery(Landing.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    @SuppressWarnings("unchecked")
    final List<Landing> landings = (List<Landing>) query.execute("current");
    return landings;
  }
  
  /**
   * Returns a List of current Item objects from the datastore. An Item object
   * is considered current if the value of the revision field is set to
   * 'current'. In the case that there are no current Item objects in the
   * datastore, getItemList will return an empty List<Item>.
   * This condition can and should be tested for using the isEmpty()
   * method of the returned List<Item> object as in the following example usage;
   * <pre>
   * {@code List<Item> items = Dao.getItemList();
   * if (items.isEmpty())
   *   doSomething(); // Log an error and call the configuration wizard
   * else
   *   doSomethingElse()}
   * </pre>
   *   
   * @see com.urlisit.siteswrapper.cloud.model.Item
   * @return A List collection of Item objects
   */
  public static List<Item> getItemList() {
    query = pm.newQuery(Item.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    @SuppressWarnings("unchecked")
    final List<Item> items = (List<Item>) query.execute("current");
    return items;
  }
  
  public static Item getItem(String name) {
    Item requestedItem = null;
    final List<Item> items = getItemList();
    for (Item item: items) {
      if (item.getName().equals(name)) {
        requestedItem = pm.detachCopy(item);
      }
    }
    return requestedItem;
  }
  

  /**
   * Returns a new instance of an empty Landing
   * 
   * @return Object reference to a new Landing
   * @see com.urlisit.siteswrapper.cloud.model.Landing
   */
  public static Landing newLanding() {
    final Landing landing = new Landing();
    landing.setLinkName("");
    landing.setDescription("");
    landing.setImageUrl("");
    landing.setLastmod("");
    landing.setLinkUrl("");
    landing.setName("");
    landing.setRevision("");
    landing.setSpecificationOne("");
    landing.setValueOne("");
    landing.setSpecificationTwo("");
    landing.setValueTwo("");
    landing.setSpecificationThree("");
    landing.setValueThree("");
    landing.setSpecificationFour("");
    landing.setValueFour("");
    landing.setSpecificationFive("");
    landing.setValueFive("");
    landing.setSpecificationSix("");
    landing.setValueSix("");
    landing.setSpecificationSeven("");
    landing.setValueSeven("");
    landing.setSpecificationEight("");
    landing.setValueEight("");
    landing.setSpecificationNine("");
    landing.setValueNine("");
    landing.setSpecificationTen("");
    landing.setValueTen("");
    landing.setVideoUrl("");
    return landing;
  }

  /**
   * Returns a new instance of an empty Item
   * 
   * @return Object reference to a new Item
   * @see com.urlisit.siteswrapper.cloud.model.Item
   */
  public static Item newItem() {
    final Item item = new Item();
    item.setLinkName("");
    item.setDescription("");
    item.setImageUrl("");
    item.setLastmod("");
    item.setLinkUrl("");
    item.setName("");
    item.setRevision("");
    item.setSpecificationOne("");
    item.setValueOne("");
    item.setSpecificationTwo("");
    item.setValueTwo("");
    item.setSpecificationThree("");
    item.setValueThree("");
    item.setSpecificationFour("");
    item.setValueFour("");
    item.setSpecificationFive("");
    item.setValueFive("");
    item.setSpecificationSix("");
    item.setValueSix("");
    item.setSpecificationSeven("");
    item.setValueSeven("");
    item.setSpecificationEight("");
    item.setValueEight("");
    item.setSpecificationNine("");
    item.setValueNine("");
    item.setSpecificationTen("");
    item.setValueTen("");
    item.setVideoUrl("");
    return item;
  }

  /**
   * Verifies the state of HttpServletRequest and HttpServletResponse parameters
   * and that they originated as a result of an authorized Google Apps Script
   * HTTP POST request to update the contents of the configuration data objects.
   * <p>
   * Values of all properties are stored in appengine-web.xml in the war/WEB-IMF
   * directory and defined using the <property name="property" value="value"/>
   * tag in the <system-properties> section of the document, where the name of
   * the property is specified as class.property and the value is simply value.
   */
  private static void checkSignature(HttpServletRequest req, HttpServletResponse resp)
      throws IllegalArgumentException, IllegalStateException {
    try {
      URL url = new URL("http://whois.arin.net/rest/ip/" + req.getRemoteAddr());
      InputStreamReader input = new InputStreamReader(url.openStream());
      BufferedReader reader = new BufferedReader(input);
      String whois = reader.readLine();
      reader.close();
      int beginIndex = whois.indexOf("<name>") + "<name>".length();
      int endIndex = whois.indexOf("</name>");
      String name = whois.subSequence(beginIndex, endIndex).toString();
      if (!name.equals("GOOGLE")) {
        throw new IllegalStateException("invocation originated from " + name +
            " not GOOGLE");
      }
      final String userAgent = req.getHeader("user-agent");
      final String documentAgent = System.getProperty("documentAgent");
      if (!userAgent.equals(documentAgent)) {
        throw new IllegalStateException("invocation via unathorized agent " +
            req.getHeader("user-agent"));
      }
      if (!req.getMethod().equalsIgnoreCase("post")) {
        throw new IllegalStateException("invocation via " + req.getMethod() +
            " not supported");
      }
      final String environment = 
          System.getProperty("com.google.appengine.runtime.environment");
      if (environment.equals("Production")) {
        if (!req.getScheme().equals("https")) {
          resp.setStatus(410);
          throw new IllegalStateException("accessed via " + req.getScheme() +
              " not supported");
        }
        String xAppEngineCountry = req.getHeader("X-AppEngine-Country");
        final String documentCountry = System.getProperty("documentCountry");
        if (!xAppEngineCountry.equals(documentCountry)) {
          throw new IllegalStateException("accessed via unathorized country " +
              xAppEngineCountry);
        }
      }
    } catch (MalformedURLException e) {
      log.warning("MalformedURLException " + e.getMessage());
    } catch (IOException e) {
      log.warning("IOException " + e.getMessage());
    } catch (IllegalStateException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
  
  /**
   * Returns the Google Docs document ID of a spreadsheet which is authorized
   * to execute RESTful HTTP POST requests against this Data Access Object.
   */
  public static String getId() {
    return System.getProperty("documentId");
  }
  
  /**
   * Provides printable information for debugging purposes
   *
   * @return String representation of the runtime object */
  @Override
  public String toString() {
    final String eol = System.getProperty("line.separator");
    final StringBuilder obj = new StringBuilder(this.getClass().getName());
    final Field[] fields = this.getClass().getDeclaredFields();
    obj.append("Object {" + eol);
    for (Field field: fields) {
      obj.append("   ");
      try {
        obj.append(field.getName());
        obj.append(": ");
        obj.append(field.get(this));
      } catch (IllegalAccessException ex) {
        log.warning("IllegalAccessException " + ex.getMessage());
      }
      obj.append(eol);
    }
    obj.append("}" + eol);
    return obj.toString();
  }
}
