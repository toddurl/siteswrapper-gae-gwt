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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.urlisit.siteswrapper.cloud.model.Landing;
import com.urlisit.siteswrapper.cloud.model.Page;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.server.DAO;

/**
 * @author Todd Url <toddurl @ urlisit.com>
 *
 * @version $Revision: 1.0 $
 */

public final class RESTful extends HttpServlet implements Cloneable {
  
  /**
   * Class specific logger for logging RESTful errors
   */
  private final Logger log = Logger.getLogger(this.getClass().getName());

  /**
   * Needed to satisfy the contract with Serializable
   * Value: {@value serialVersionUID}
   */
  private static final long serialVersionUID = 4898375979812952018L;
  
  /**
   * Determines default encoding and decoding to apply to POSTs and GETs
   */
  private static final String DEFAULT_CHARACTER_SET = "UTF-8";
  
  /**
   * Calls the Data Access Object in response to the decode of an HTTP POST request URI
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      if (req.getRequestURI().equals("/" + DAO.getId() + "/updateSite")) {
        try {
          DAO.updateSite(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
      if (req.getRequestURI().equals("/" + DAO.getId() + "/updateStyle")) {
        try {
          DAO.updateStyle(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
      if (req.getRequestURI().equals("/" + DAO.getId() + "/updateLanding")) {
        try {
          DAO.updateLanding(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
      if (req.getRequestURI().equals("/" + DAO.getId() + "/updatePage")) {
        try {
          DAO.updatePage(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
      if (req.getRequestURI().equals("/" + DAO.getId() + "/updateItem")) {
        try {
          DAO.updateItem(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
      if (req.getRequestURI().equals("/" + DAO.getId() + "/commitChange")) {
        try {
          DAO.commitChange(req, resp);
        } catch (IllegalStateException e) {
          log.warning("IllegalStateException " + e.getMessage());
        }
      }
    } catch (IOException e) {
      final StringBuilder msg = new StringBuilder("IOException in doPost");
      log.warning(msg.toString() + e.toString());
      resp.getWriter().write("FAILURE 0");
      return;
    }
  }
  
  /**
   * Gets the artifact associated with the request URI minus the context path
   * and the mime extension, and returns it via HTTP. In most cases the artifact
   * is a GWT HTML host page containing a <script> link reference to a GWT
   * module and HTML markup between the <noscript> </noscript> tags representing
   * the content of the requested artifact in the datastore. In this case
   * artifact is referring to an persistent object of type Page or Item. Other
   * artifacts supported include; sitemap and Google Webmaster Verification Code
   * where Google Webmaster Verification Code is the code provided by Google
   * Analytics, which must be served by the site in order to demonstrate
   * ownership.
   * 
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    /*
     * Decode the user-agent to one of mobile, tablet or desktop and pass it to HostPage for
     * inclusion in the Site Dictionary applied to the resulting HostPage
     */
    String agent = req.getHeader("user-agent");
    if (agent.contains("iPhone")) {
      agent = "mobile";
    } else if (agent.contains("iPad")) {
      agent = "tablet";
    } else if (agent.contains("Android")) {
      agent = "mobile";
    } else if (agent.contains("Googlebot-Mobile")) {
      agent = "mobile";
    } else if (agent.contains("Googlebot")) {
      agent = "desktop";
    } else {
      agent = "desktop";
    }
    /*
     * A GET request can only be one of ??
     * TODO DETAILED EXPLANATION
     */
    String request = req.getRequestURI();
    String parameter = req.getParameter("eSite");
    try {
      request = request.substring(1, request.indexOf("."));
    } catch (StringIndexOutOfBoundsException err) {
      request = DAO.getDefaultPage().getPageName();
    }
    try {
      request = URLDecoder.decode(request, DEFAULT_CHARACTER_SET);
    } catch (UnsupportedEncodingException err) {
      log.warning(this.getClass().getName() + err.getMessage());
      request = DAO.getDefaultPage().getPageName();
      parameter = null;
    }
    if (request.equals("sitemap")) {
      resp.getWriter().write("generating sitemap");
    } else if (request.equals(getServletConfig().getInitParameter("GoogleWebMasterToolsSiteVerifica" +
            "tionCode"))) {
      resp.getWriter().write("generateGoogleWebMasterToolsSiteVerificationCode");
    } else {
      HostPage page = new HostPage.Builder().hostPage(request).eSite(parameter).agent(agent).build();
      resp.getWriter().write(page.toMarkup());
    }
  }

  /**
   * Disable serialization by throwing an IOException on call of writeObject().
   * <p>
   * Serialization can be used to save objects when the JVM is "switched off".
   * Serialization flattens the object and saves it as a stream of bytes.
   * Serialization can allow an attacker to view the inner state of an object
   * and even see the status of the private attributes, so it's getting turned
   * off here.
   * 
   * @param out
   * @throws IOException
   */
  private final void writeObject(ObjectOutputStream out) throws IOException {
    throw new IOException("Object cannot be serialized");
  }
  
  /**
   * Disable deserialization by throwing an IOException on call of readObject().
   * <p>
   * Deserialization can be used to construct an object from a stream of bytes,
   * which may mimic a legitimate class. This could be used by an attacker to
   * instantiate an object�s state.
   * 
   * @param in
   * @throws IOException
   */
  private final void readObject(ObjectInputStream in) throws IOException {
    throw new IOException("Class cannot be deserialized");
  }

  /**
   * Makes this class unclonable by overriding clone and throwing a
   * CloneNotSupportedException.
   * <p>
   * Cloning allows an attacker to instantiate a class without running any of
   * the class constructors.
   *
   * @return Object
   * @throws java.lang.CloneNotSupportedException
   */
  @Override
  public final Object clone() throws java.lang.CloneNotSupportedException {
    throw new java.lang.CloneNotSupportedException("Cloning allows an attacke" +
      "r to instantiate a class without running any of the class constructors");
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
        }
        catch (IllegalAccessException ex) {
           log.warning(ex.getMessage());
        }
        obj.append(eol);
     } 
     obj.append("}" + eol);
     return obj.toString();
  }
}
