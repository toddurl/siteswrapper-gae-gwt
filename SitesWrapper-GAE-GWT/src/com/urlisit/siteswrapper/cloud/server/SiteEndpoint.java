package com.urlisit.siteswrapper.cloud.server;

import com.urlisit.siteswrapper.cloud.model.Site;
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

@Api(name = "siteendpoint", namespace = @ApiNamespace(ownerDomain = "urlisit.com", ownerName = "urlisit.com", packagePath = "siteswrapper.cloud.model"))
public class SiteEndpoint {

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @ApiMethod(name = "listSite")
    public CollectionResponse<Site> listSite(@Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

        PersistenceManager mgr = null;
        Cursor cursor = null;
        List<Site> execute = null;

        try {
            mgr = getPersistenceManager();
            Query query = mgr.newQuery(Site.class);
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                HashMap<String, Object> extensionMap = new HashMap<String, Object>();
                extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
                query.setExtensions(extensionMap);
            }

            if (limit != null) {
                query.setRange(0, limit);
            }

            execute = (List<Site>) query.execute();
            cursor = JDOCursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (Site obj : execute)
                ;
        } finally {
            mgr.close();
        }

        return CollectionResponse.<Site> builder().setItems(execute).setNextPageToken(cursorString).build();
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getSite")
    public Site getSite(@Named("id") String id) {
        PersistenceManager mgr = getPersistenceManager();
        Site site = null;
        try {
            site = mgr.getObjectById(Site.class, id);
        } finally {
            mgr.close();
        }
        return site;
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param site the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertSite")
    public Site insertSite(Site site) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            if (containsSite(site)) {
                throw new EntityExistsException("Object already exists");
            }
            mgr.makePersistent(site);
        } finally {
            mgr.close();
        }
        return site;
    }

    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param site the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateSite")
    public Site updateSite(Site site) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            if (!containsSite(site)) {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.makePersistent(site);
        } finally {
            mgr.close();
        }
        return site;
    }

    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeSite")
    public void removeSite(@Named("id") String id) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            Site site = mgr.getObjectById(Site.class, id);
            mgr.deletePersistent(site);
        } finally {
            mgr.close();
        }
    }

    private boolean containsSite(Site site) {
        PersistenceManager mgr = getPersistenceManager();
        boolean contains = true;
        try {
            mgr.getObjectById(Site.class, site.getEncodedKey());
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
