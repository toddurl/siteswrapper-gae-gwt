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

/**
 * Enum used to indicate the type of logo
 * 
 * @author Todd Url
 */
public enum DisplayLogoAs {
    IMAGE ("Image"), HTML ("Html"), NONE ("None");
    
    /**
     * Human readable string representation of the constant, typically used to populate an input field using the getLabel method
     */
    private final String label;

    DisplayLogoAs(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
    
}
