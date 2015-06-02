# SitesWrapper-GAE-GWT #
[![](https://c824ff113391b7c600d1069f19350d6607b580e1.googledrive.com/host/0BzPelJUA_7zUT3ZfQVdNcmwzbDg/SitesWrapperGAEGWTArchitecture.png)](https://www.linkedin.com/in/toddurl)
  1. **Server Service Wrapper** - Although technically in the same source tree and maintained as part of the same eclipse based project as the client wrapper, the server service wrapper is the server side Java component which provides MVC based HTML only web services as well service access to the datastore entity objects via endpoints. Additionally each MVC based HTML page view generated by the server in response to an HTTP GET is also a fully functional GWT host page. This dual nature is what enables the client wrapper to be accessed from any server service wrapper generated landing page.
  1. **Client Wrapper** - The client wrapper portion of the project is defined by source path entries of the GWT module SitesWrapper.gwt.xml which identify the sub packages containing GWT translatable source code. Although written in Java, the client wrapper is translated into JavaScript and executed by the client browser as part of the host/landing page when loaded by a browser which supports JavaScript.