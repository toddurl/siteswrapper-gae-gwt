/**
 * 
 */
package com.urlisit.siteswrapper.cloud.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Holds the persistent description of each landing page in the site
 * 
 * @author Todd Url <toddurl @ urlisit.com>
 * @version  1.0
 * @since  02-28-2012
 *
 */
@PersistenceCapable
public class Item implements IsSerializable {

  /**
   * Ensures each persisted landingPageConfiguration object has a unique key
   * in the datastore.
   */
  @SuppressWarnings("unused")
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
  private String encodedKey;
  
  /**
   * One of either current, previous or SimpleDateFormat. Used to mark a
   * configuration before during and after an update of the configuration
   * object. Under normal conditions, revision has a value of either current
   * or a date (in SimpleDateFormat) when it was archived as the object of an
   * updateSiteConfiguration POST request. During the updateSiteConfiguration
   * POST request, the current configuration is marked as previous and the
   * new SiteConfiguration object is created and marked as current. Upon
   * successful creation of the new LandingPAgeConfiguration object (marked
   * as current) the old LandingPageConfiguration object (marked as previous)
   * is archived by setting the value of revision to the current Date/Time in
   * SimpleDateFormat. If the update(Site|Page|LandingPage)Configuration
   * operation is interrupted or fails for any reason, the configuration
   * is rolled back by deleting any objects marked as current and setting
   * the value of revision for all objects marked as previous to current.
   */
  @Persistent
  private String revision;
  
  /**
   * Used for the name of the landing page button, title etc..
   */
  @Persistent
  private String name;

  /**
   * Gets displayed as information about and in the landing page
   */
  @Persistent
  private List<String> description = new ArrayList<String>();

  /**
   * Points to a video which is displayed in the landing page
   */
  @Persistent
  private String videoUrl;

  /**
   * Points to an image which is displayed in the landing page
   */
  @Persistent
  private String imageUrl;
  
  /**
   * Displayed as the name of the source for the original information
   * displayed in the landing page.
   */
  @Persistent
  private String linkName;
  
  /**
   * The Url to the source for the original information displayed in the
   * landing page. Serves as the href in the anchor tag for the linkName
   */
  @Persistent
  private String linkUrl;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationOne;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueOne;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationTwo;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueTwo;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationThree;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueThree;
  
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationFour;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueFour;
  
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationFive;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueFive;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationSix;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueSix;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationSeven;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueSeven;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationEight;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueEight;
  
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationNine;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueNine;
  
  /**
   * Name portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String specificationTen;
  
  /**
   * Value portion of name=value pairs used to arbitrarily assign
   * arbitrary specifications and values to a landing page
   */
  @Persistent
  private String valueTen;
  
  /**
   * Reported to the google bot in the sitemap
   */
  @Persistent
  private String lastmod;
  
  /**
   * Method setRevision.
   * @param revision String
   */
  public void setRevision(String revision) {
    this.revision = revision;
  }
  
  /**
   * Method getRevision.
   * @return String
   */
  public String getRevision() {
    return this.revision;
  }
  
  /**
   * Method setName.
   * @param name String
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Method getName.
   * @return String
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Method getDescription.
   * @return String
   */
  public String getDescription() {
    StringBuilder result = new StringBuilder();
    for (Iterator<String> iterator = description.iterator(); iterator.hasNext();) {
     result.append(iterator.next());
    }
    return result.toString();
  }
  
  /**
   * Method setDescription.
   * @param description String
   */
  public void setDescription(String description) {
    while (description.length() > 500) {
      this.description.add(description.substring(0, 499));
      description = description.substring(500, description.length());
    }
    if (description.length() > 0) {
      this.description.add(description);
    }
  }
  
  /**
   * Method setVideoUrl.
   * @param videoUrl String
   */
  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }
  
  /**
   * Method getVideoUrl.
   * @return String
   */
  public String getVideoUrl() {
    return this.videoUrl;
  }
  
  /**
   * Method setImageUrl.
   * @param imageUrl String
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  
  /**
   * Method getImageUrl.
   * @return String
   */
  public String getImageUrl() {
    return this.imageUrl;
  }
  
  /**
   * Method setLinkName.
   * @param linkName String
   */
  public void setLinkName(String linkName) {
    this.linkName = linkName;
  }
  
  /**
   * Method getLinkName.
   * @return String
   */
  public String getLinkName() {
    return this.linkName;
  }

  /**
   * Method setLinkUrl.
   * @param linkUrl String
   */
  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }
  
  /**
   * Method getLinkUrl.
   * @return String
   */
  public String getLinkUrl() {
    return this.linkUrl;
  }
  
  /**
   * Method setSpecificationOne.
   * @param specificationOne String
   */
  public void setSpecificationOne(String specificationOne) {
    this.specificationOne = specificationOne;
  }
  
  /**
   * Method getSpecificationOne.
   * @return String
   */
  public String getSpecificationOne() {
    return this.specificationOne;
  }
  
  /**
   * Method setValueOne.
   * @param valueOne String
   */
  public void setValueOne(String valueOne) {
    this.valueOne = valueOne;
  }
  
  /**
   * Method getValueOne.
   * @return String
   */
  public String getValueOne() {
    return this.valueOne;
  }
  
  /**
   * Method setSpecificationTwo.
   * @param specificationTwo String
   */
  public void setSpecificationTwo(String specificationTwo) {
    this.specificationTwo = specificationTwo;
  }
  
  /**
   * Method getSpecificationTwo.
   * @return String
   */
  public String getSpecificationTwo() {
    return this.specificationTwo;
  }
  
  /**
   * Method setValueTwo.
   * @param valueTwo String
   */
  public void setValueTwo(String valueTwo) {
    this.valueTwo = valueTwo;
  }
  
  /**
   * Method getValueTwo.
   * @return String
   */
  public String getValueTwo() {
    return this.valueTwo;
  }
  
  /**
   * Method setSpecificationThree.
   * @param specificationThree String
   */
  public void setSpecificationThree(String specificationThree) {
    this.specificationThree = specificationThree;
  }
  
  /**
   * Method getSpecificationThree.
   * @return String
   */
  public String getSpecificationThree() {
    return this.specificationThree;
  }
  
  /**
   * Method setValueThree.
   * @param valueThree String
   */
  public void setValueThree(String valueThree) {
    this.valueThree = valueThree;
  }
  
  /**
   * Method getValueThree.
   * @return String
   */
  public String getValueThree() {
    return this.valueThree;
  }
  
  /**
   * Method setSpecificationFour.
   * @param specificationFour String
   */
  public void setSpecificationFour(String specificationFour) {
    this.specificationFour = specificationFour;
  }
  
  /**
   * Method getSpecificationFour.
   * @return String
   */
  public String getSpecificationFour() {
    return this.specificationFour;
  }
  
  /**
   * Method setValueFour.
   * @param valueFour String
   */
  public void setValueFour(String valueFour) {
    this.valueFour = valueFour;
  }
  
  /**
   * Method getValueFour.
   * @return String
   */
  public String getValueFour() {
    return this.valueFour;
  }
  
  /**
   * Method setSpecificationFive.
   * @param specificationFive String
   */
  public void setSpecificationFive(String specificationFive) {
    this.specificationFive = specificationFive;
  }
  
  /**
   * Method getSpecificationFive.
   * @return String
   */
  public String getSpecificationFive() {
    return this.specificationFive;
  }
  
  /**
   * Method setValueFive.
   * @param valueFive String
   */
  public void setValueFive(String valueFive) {
    this.valueFive = valueFive;
  }
  
  /**
   * Method getValueFive.
   * @return String
   */
  public String getValueFive() {
    return this.valueFive;
  }
  
  /**
   * Method setSpecificationSix.
   * @param specificationSix String
   */
  public void setSpecificationSix(String specificationSix) {
    this.specificationSix = specificationSix;
  }
  
  /**
   * Method getSpecificationSix.
   * @return String
   */
  public String getSpecificationSix() {
    return this.specificationSix;
  }
  
  /**
   * Method setValueSix.
   * @param valueSix String
   */
  public void setValueSix(String valueSix) {
    this.valueSix = valueSix;
  }
  
  /**
   * Method getValueSix.
   * @return String
   */
  public String getValueSix() {
    return this.valueSix;
  }
  
  /**
   * Method setSpecificationSeven.
   * @param specificationSeven String
   */
  public void setSpecificationSeven(String specificationSeven) {
    this.specificationSeven = specificationSeven;
  }
  
  /**
   * Method getSpecificationSeven.
   * @return String
   */
  public String getSpecificationSeven() {
    return this.specificationSeven;
  }
  
  /**
   * Method setValueSeven.
   * @param valueSeven String
   */
  public void setValueSeven(String valueSeven) {
    this.valueSeven = valueSeven;
  }
  
  /**
   * Method getValueSeven.
   * @return String
   */
  public String getValueSeven() {
    return this.valueSeven;
  }
  
  /**
   * Method setSpecificationEight.
   * @param specificationEight String
   */
  public void setSpecificationEight(String specificationEight) {
    this.specificationEight = specificationEight;
  }
  
  /**
   * Method getSpecificationEight.
   * @return String
   */
  public String getSpecificationEight() {
    return this.specificationEight;
  }
  
  /**
   * Method setValueEight.
   * @param valueEight String
   */
  public void setValueEight(String valueEight) {
    this.valueEight = valueEight;
  }
  
  /**
   * Method getValueEight.
   * @return String
   */
  public String getValueEight() {
    return this.valueEight;
  }
  
  /**
   * Method setSpecificationNine.
   * @param specificationNine String
   */
  public void setSpecificationNine(String specificationNine) {
    this.specificationNine = specificationNine;
  }
  
  /**
   * Method getSpecificationNine.
   * @return String
   */
  public String getSpecificationNine() {
    return this.specificationNine;
  }
  
  /**
   * Method setValueNine.
   * @param valueNine String
   */
  public void setValueNine(String valueNine) {
    this.valueNine = valueNine;
  }
  
  /**
   * Method getValueNine.
   * @return String
   */
  public String getValueNine() {
    return this.valueNine;
  }
  
  /**
   * Method setSpecificationTen.
   * @param specificationTen String
   */
  public void setSpecificationTen(String specificationTen) {
    this.specificationTen = specificationTen;
  }
  
  /**
   * Method getSpecificationTen.
   * @return String
   */
  public String getSpecificationTen() {
    return this.specificationTen;
  }
  
  /**
   * Method setValueTen.
   * @param valueTen String
   */
  public void setValueTen(String valueTen) {
    this.valueTen = valueTen;
  }
  
  /**
   * Method getValueTen.
   * @return String
   */
  public String getValueTen() {
    return this.valueTen;
  }
  
  /**
   * Method setLastmod.
   * @param lastmod String
   */
  public void setLastmod(String lastmod) {
    this.lastmod = lastmod;
  }
  
  /**
   * Method getLastmod.
   * @return String
   */
  public String getLastmod() {
    return this.lastmod;
  }

  public Object getEncodedKey() {
    return encodedKey;
  }
  
  /**
   * Intended only for debugging.
   * @return String
   */
  /*
  @Override
  public String toString() {
    final StringBuilder object = new StringBuilder(this.getClass().getName());
    final Field[] fields = this.getClass().getDeclaredFields();
    object.append(" Object {");
    object.append(EOL);
    for (Field field : fields) {
      object.append("  ");
      try {
        object.append(field.getName());
        object.append(": ");
        object.append(field.get(this));
      }
      catch (IllegalAccessException ex) {
        object.append("IllegalAccessException occured " + ex.getMessage());
      }
      object.append(EOL);
    }
    object.append('}');
    return object.toString();
  }
  */
}
