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
import java.util.Properties; 
import java.util.Scanner;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

import com.google.apphosting.api.ApiProxy;

/**
 * Handles in-bound email messages
 * 
 * @author Todd Url
 */
public class Email extends HttpServlet { 

  /*
   * logger Email errors
   */
  private final Logger log = Logger.getLogger(this.getClass().getName());

  /*
   * Provided for serialization
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Regular expression which matches end of line. Used to read the entire contents of a file and
   * return it as a String via Scanner.
   */
  public static final String EOF = "\\Z";

  /*
   * SitesWrapper-Apps-Script uses this String as the user name portion of the
   * destination address during initialization.
   */
  private static final String APPLICATION_NAME = "siteswrapper-gae-gwt";

  /*
   * Key used to retrieve the GAE application id during initialization.
   */
  private static final String APPLICATION_ID_KEY = "com.google.appengine.application.id";

  /*
   * Key used to retrieve the Google account name@domain for the SitesWrapper
   * administrator. This is the same user name as the App Engine administrator.
   */
  private static final String ADMINISTRATOR_KEY = "siteswrapper.administrator";
  
  /*
   * The expected Google Api authentication domain for SitesWrapper-Apps-Script
   */
  private static final String ADMINISTRATOR_DOMAIN = "gmail.com";

  /**
   * Handles post of emails delivered to the corresponding URI as defined in
   * the emailServlet definition of the web.xml file in WEB-INF.
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
    Properties properties = new Properties(); 
    Session session = Session.getDefaultInstance(properties, null); 
    MimeMessage message = null;
    String source = null;
    String destination = null;
    String subject = null;
    String body = null;
    String administrator = null;
    String application = null;
    String domain = null;
    try {
      message = new MimeMessage(session, req.getInputStream());
      source = message.getFrom()[0].toString();
      subject = message.getSubject();
      body = new Scanner(message.getInputStream()).useDelimiter(EOF).next();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    administrator = System.getProperty(ADMINISTRATOR_KEY);
    application = System.getProperty(APPLICATION_ID_KEY);
    destination = req.getRequestURI().split("@")[0].split("/")[3];
    domain = ApiProxy.getCurrentEnvironment().getAuthDomain();
    if (source.equals(administrator) &&
        subject.equals(application)  &&
        destination.equals(APPLICATION_NAME) &&
        domain.equals(ADMINISTRATOR_DOMAIN)) {
      DAO.setDocumentId(body);
    } else {
      log.warning("INITIALIZATION ATTEMPT FAILED");
    }
  }
}