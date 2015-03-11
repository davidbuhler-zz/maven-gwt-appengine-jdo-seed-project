package com.davidbuhler.plaidsuit.client.service;

import java.util.logging.Logger;

public class ServiceDelegate {
    private static ServiceDelegate INSTANCE;
    private static final Logger LOG = Logger.getLogger(ServiceDelegate.class.getName());

    static public ServiceDelegate getInstance() {
        if (ServiceDelegate.INSTANCE == null) {
            ServiceDelegate.INSTANCE = new ServiceDelegate();
        }
        return ServiceDelegate.INSTANCE;
    }
}
