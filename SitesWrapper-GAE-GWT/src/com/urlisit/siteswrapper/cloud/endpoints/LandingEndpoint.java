package com.urlisit.siteswrapper.cloud.endpoints;

import com.urlisit.siteswrapper.cloud.model.Landing;
import com.urlisit.siteswrapper.cloud.server.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "landingendpoint", namespace = @ApiNamespace(ownerDomain = "urlisit.com", ownerName = "urlisit.com", packagePath = "siteswrapper.cloud.model"))
public class LandingEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({ "unchecked", "unused" })
  @ApiMethod(name = "listLanding")
  public CollectionResponse<Landing> listLanding(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

    PersistenceManager mgr = null;
    Cursor cursor = null;
    List<Landing> execute = null;

    try {
      mgr = getPersistenceManager();
      Query query = mgr.newQuery(Landing.class);
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        HashMap<String, Object> extensionMap = new HashMap<String, Object>();
        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
        query.setExtensions(extensionMap);
      }

      if (limit != null) {
        query.setRange(0, limit);
      }

      execute = (List<Landing>) query.execute();
      cursor = JDOCursorHelper.getCursor(execute);
      if (cursor != null)
        cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (Landing obj : execute)
        ;
    } finally {
      mgr.close();
    }

    return CollectionResponse.<Landing> builder().setItems(execute).setNextPageToken(cursorString).build();
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return The entity with primary key id.
   */
  @ApiMethod(name = "getLanding")
  public Landing getLanding(@Named("id") String id) {
    PersistenceManager mgr = getPersistenceManager();
    Landing landing = null;
    try {
      landing = mgr.getObjectById(Landing.class, id);
    } finally {
      mgr.close();
    }
    return landing;
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param landing the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertLanding")
  public Landing insertLanding(Landing landing) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      if (containsLanding(landing)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.makePersistent(landing);
    } finally {
      mgr.close();
    }
    return landing;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param landing the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateLanding")
  public Landing updateLanding(Landing landing) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      if (!containsLanding(landing)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.makePersistent(landing);
    } finally {
      mgr.close();
    }
    return landing;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeLanding")
  public void removeLanding(@Named("id") String id) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      Landing landing = mgr.getObjectById(Landing.class, id);
      mgr.deletePersistent(landing);
    } finally {
      mgr.close();
    }
  }

  private boolean containsLanding(Landing landing) {
    PersistenceManager mgr = getPersistenceManager();
    boolean contains = true;
    try {
      mgr.getObjectById(Landing.class, landing.getEncodedKey());
    } catch (javax.jdo.JDOObjectNotFoundException ex) {
      contains = false;
    } finally {
      mgr.close();
    }
    return contains;
  }

  private static PersistenceManager getPersistenceManager() {
    return PMF.get().getPersistenceManager();
  }

}
