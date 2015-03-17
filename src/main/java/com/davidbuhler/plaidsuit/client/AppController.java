package com.davidbuhler.plaidsuit.client;

import com.davidbuhler.plaidsuit.client.event.*;
import com.davidbuhler.plaidsuit.client.presenter.ContactsPresenter;
import com.davidbuhler.plaidsuit.client.presenter.EditContactPresenter;
import com.davidbuhler.plaidsuit.client.presenter.Presenter;
import com.davidbuhler.plaidsuit.client.view.ContactsView;
import com.davidbuhler.plaidsuit.client.view.EditContactView;
import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppController implements Presenter, ValueChangeHandler<String> {

    private final HandlerManager eventBus;
    private final ContactsServiceAsync rpcService;
    private HasWidgets container;
    private ContactsView<ContactDTO> contactsView = null;
    private EditContactView editContactView = null;
    private static final Logger LOG = Logger.getLogger(AppController.class.getName());

    public AppController(ContactsServiceAsync rpcService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddContactEvent.TYPE, new AddContactEventHandler() {
            public void onAddContact(AddContactEvent event) {
                doAddNewContact();
            }
        });

        eventBus.addHandler(EditContactEvent.TYPE, new EditContactEventHandler() {
            public void onEditContact(EditContactEvent event) {
                doEditContact(event.getId());
            }
        });

        eventBus.addHandler(EditContactCancelledEvent.TYPE, new EditContactCancelledEventHandler() {
            public void onEditContactCancelled(EditContactCancelledEvent event) {
                doEditContactCancelled();
            }
        });

        eventBus.addHandler(ContactUpdatedEvent.TYPE, new ContactUpdatedEventHandler() {
            public void onContactUpdated(ContactUpdatedEvent event) {
                doContactUpdated();
            }
        });
    }

    private void doAddNewContact() {
        History.newItem("add");
    }

    private void doEditContact(String id) {
        History.newItem("edit", false);
        Presenter presenter = new EditContactPresenter(rpcService, eventBus, new EditContactView(), id);
        presenter.go(container);
    }

    private void doEditContactCancelled() {
        History.newItem("list");
    }

    private void doContactUpdated() {
        History.newItem("list");
    }

    public void go(final HasWidgets container) {
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("list");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token != null) {
            if (token.equals("list")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                        LOG.log(Level.SEVERE, Level.SEVERE.getName(), caught);
                    }

                    public void onSuccess() {
                        contactsView = new ContactsView<ContactDTO>();
                        new ContactsPresenter(rpcService, eventBus, contactsView).go(container);
                    }
                });
            } else if (token.equals("add") || token.equals("edit")) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onFailure(Throwable caught) {
                        LOG.log(Level.SEVERE, Level.SEVERE.getName(), caught);
                    }

                    public void onSuccess() {
                        editContactView = new EditContactView();
                        new EditContactPresenter(rpcService, eventBus, editContactView).go(container);
                    }
                });
            }
        }
    }
}
