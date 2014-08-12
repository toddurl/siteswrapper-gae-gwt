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
package com.urlisit.siteswrapper.cloud.model;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Data Transfer Object used to return objects and collections of objects from 
 * the datastore.
 * 
 * @author Todd Url
 *
 */
public class DTO implements IsSerializable {
  
  private Site site;
  private Page[] pages;
  
  public void setSite(Site site) {
    this.site = site;
  }
  
  public Site getSite() {
    return site;
  }
  
  public void setPages(List<Page> pages) {
    int numberOfPages = pages.size();
    this.pages = new Page[numberOfPages];
    for (int page = 0; page < numberOfPages; page++) {
      this.pages[page] = pages.get(page);
    }
  }
  
  public void setPages(Page[] pages) {
    this.pages = pages;
  }
  
  public Page[] getPages() {
    return pages;
  }
}