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
package com.urlisit.siteswrapper.cloud.presenter;

import java.util.Map;
import java.util.LinkedHashMap;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.urlisit.siteswrapper.cloud.view.View;

/**
 * Generic Presenter Interface
 * 
 * @author TOdd Url
 */
public interface Presenter extends ValueChangeHandler<String>, ResizeHandler {
  Map<String, View> views = new LinkedHashMap<String, View>();
  @Override
  void onValueChange(ValueChangeEvent<String> event);
  @Override
  void onResize(ResizeEvent event);
  int bindView(View view);
  void loadView(View view);
  View getView(String title);
  Map<String, View> getViews();
}