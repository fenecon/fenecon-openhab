package org.openhab.ui.femsui.internal;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FemsUIApp {
    public static final String WEBAPP_ALIAS = "/fems";
    private static final Logger logger = LoggerFactory.getLogger(FemsUIApp.class);

    protected HttpService httpService;

    protected void activate(ComponentContext componentContext) {
        try {
            httpService.registerResources(WEBAPP_ALIAS, "web", null);
            logger.info("Started FEMS UI at " + WEBAPP_ALIAS);
        } catch (NamespaceException e) {
            logger.error("Error during servlet startup", e);
        }
    }

    protected void deactivate(ComponentContext componentContext) {
        httpService.unregister(WEBAPP_ALIAS);
        logger.info("Stopped FEMS UI");
    }

    protected void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

    protected void unsetHttpService(HttpService httpService) {
        this.httpService = null;
    }
}
