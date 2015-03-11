package com.davidbuhler.plaidsuit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Contacts implements EntryPoint {
    private static final Logger LOG = Logger.getLogger(Contacts.class.getName());

    private void startApplication() {
        IContactsServiceAsync rpcService = GWT.create(IContactsService.class);
        HandlerManager eventBus = new HandlerManager(null);
        AppController appViewer = new AppController(rpcService, eventBus);
        appViewer.go(RootPanel.get());
    }

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                LOG.log(Level.SEVERE, Level.SEVERE.getName(), e);
            }
        });
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                startApplication();

            }
        });
    }
}
