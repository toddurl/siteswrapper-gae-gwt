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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Query;
import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.urlisit.siteswrapper.cloud.client.Rpc;
import com.urlisit.siteswrapper.cloud.model.DTO;
import com.urlisit.siteswrapper.cloud.model.Item;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Page;

/**
 * The server side implementation of the Rpc.
 * @author Todd Url <toddurl @ urlisit.com>
 * @version 1.0
 * @since 4-10-2012
 */
@SuppressWarnings("serial")
public class RpcImpl extends RemoteServiceServlet implements Rpc {
  
 /**
  * Field log.
  */
  @SuppressWarnings("unused")
  private final Logger log = Logger.getLogger(RpcImpl.class.getName());
  
  @Override
  public Site getSite() {
    return DAO.getSite();
  }

  @Override
  public List<Page> getPages() {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Query query = pm.newQuery(Page.class);
    query.setFilter("revision == revisionParameter");
    query.declareParameters("String revisionParameter");
    /*
     * This is a runtime cast from Object to List<Page> which the runtime can
     * not check because information about Generics is erased as a result of
     * compilation and is not available at runtime (Thus the warning
     * 'unchecked'). However, since the Query will only return a List of Page
     * objects (Page.class), this cast will always be safe, even if the query
     * returns an empty List<Page>.
     */
    @SuppressWarnings("unchecked")
    List<Page> DAO = (List<Page>) query.execute("current");
    List<Page> DTO = new ArrayList<Page>();
    for (Iterator<Page> iterator = DAO.iterator(); iterator.hasNext();) {
      DTO.add(pm.detachCopy(iterator.next()));
    }
    pm.close();
    return DTO;
  }
  
  @Override
  public Page[] getPageArray() {
    List<Page> pageList = getPages();
    Page[] plist = new Page[pageList.size()];
    int x = 0;
    for (Page page: pageList) {
      plist[x++] = page;
    }
    return plist;
  }
  
  @Override
  public DTO getDTO() {
    DTO dto = new DTO();
    dto.setSite(DAO.getSite());
    dto.setPages(DAO.getPages());
    return dto;
  }

 /**
  * Intended only for debugging.
  *
  * <P>Here, the contents of every field are placed into the result, with one
  * field per line.
  * @return String
  */
  @Override public String toString() {
  final StringBuilder result = new StringBuilder(); // $codepro.audit.disable defineInitialCapacity
  final String eol = System.getProperty("line.separator");

  result.append(this.getClass().getName() + " Object {" + eol);
  result.append(" ServletInfo: " + this.getServletInfo() + eol);
  result.append(" ServletName: " + this.getServletName() + eol);
  result.append('}');
  return result.toString();
  }

@Override
public Item getItem(String name) {
  return DAO.getItem(name);
}

}