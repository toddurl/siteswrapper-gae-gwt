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

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.urlisit.siteswrapper.cloud.model.DTO;
import com.urlisit.siteswrapper.cloud.model.Item;
import com.urlisit.siteswrapper.cloud.model.Site;
import com.urlisit.siteswrapper.cloud.model.Page;

/**
 * The async counterpart of <code>Rpc</code>.
 * @author url
 * @version $Revision: 1.0 $
 */
public interface RpcAsync {
  
  void getSite(AsyncCallback<Site> callback);
  
  void getPages(AsyncCallback<List<Page>> callback);
  
  void getPageArray(AsyncCallback<Page[]> callback);

  void getDTO(AsyncCallback<DTO> callback);

  void getItem(String name, AsyncCallback<Item> callback);
}