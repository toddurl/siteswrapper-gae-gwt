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
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

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

  private static final long serialVersionUID = 1L;

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { 
    Properties props = new Properties(); 
    Session session = Session.getDefaultInstance(props, null); 
    MimeMessage message = null;
    String contentId = "";
    String contentMD5 = "";
    String contentType = "";
    String description = "";
    String disposition = "";
    String messageId = "";
    String subject = "";
    try {
      message = new MimeMessage(session, req.getInputStream());
      contentId = message.getContentID();
      contentMD5 = message.getContentMD5();
      contentType = message.getContentType();
      description = message.getDescription();
      disposition = message.getDisposition();
      messageId = message.getMessageID();
      subject = message.getSubject();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    log.warning("Received email!");
    log.warning("ContentID=" + contentId);
    log.warning("ContentMD5=" + contentMD5);
    log.warning("ContentType=" + contentType);
    log.warning("Description=" + description);
    log.warning("Diposition=" + disposition);
    log.warning("MessageID=" + messageId);
    log.warning("subject=" + subject);
  }
}