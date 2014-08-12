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
package com.urlisit.siteswrapper.cloud.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.urlisit.siteswrapper.cloud.model.DTO;
import com.urlisit.siteswrapper.cloud.model.Item;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Page;

/**
 * The client side stub for the RPC service.
 * 
 * @author Todd Url
 */
@RemoteServiceRelativePath("rpc")
public interface Rpc extends RemoteService {
  
  /**
   * Returns a Data Transfer Object representing the current Site configuration
   * object in the datastore
   */
  Site getSite();
  
  /**
   * Returns a Data Transfer Object representing an ArrayList of the current
   * Page configuration objects in the GAE Data-store
   */
  List<Page> getPages();
  
  /**
   * Returns a Data Transfer Object representing an Array of the current
   * Page configuration objects in the GAE Data-store
   */
  Page[] getPageArray();
  
  /**
   * Returns a Data Transfer Object containing all the current configuration
   * objects in the GAE Data-store
   * @return DTO containing an object reference to an array of Page objects and an array of Landing
   * objects
   */
  DTO getDTO();
  
  /**
   * @param name
   * @return an information Item in the datastore whose name field matches name
   */
  Item getItem(String name);
}