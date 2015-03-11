package com.davidbuhler.plaidsuit.client.presenter;

import com.davidbuhler.plaidsuit.client.IContactsServiceAsync;
import com.davidbuhler.plaidsuit.client.event.*;
import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditContactPresenter implements Presenter {
    private static final Logger LOG = Logger.getLogger(EditContactPresenter.class.getName());
    private final IContactsServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;
    private ContactDTO contactDTO;

    public EditContactPresenter(IContactsServiceAsync rpcService, HandlerManager eventBus, Display display) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.contactDTO = new ContactDTO();
        this.display = display;
        bind();
    }

    public void bind() {
        this.display.getSaveButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doSave();
            }
        });

        this.display.getCancelButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new EditContactCancelledEvent());
            }
        });
        GlobalEventDispatcher.getInstance().EVENT_BUS.addHandler(ErrorEvent.TYPE, new ErrorEventHandler() {
            @Override
            public void execute(final ErrorEvent event) {
                EditContactPresenter.this.display.getNotificationBar().setText(event.getMessage());
            }
        });
    }

    private void doSave() {
        contactDTO.setFirstName(display.getFirstNameInputT().getValue());
        contactDTO.setLastName(display.getLastNameInput().getValue());
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<ContactDTO>> violations = validator.validate(contactDTO, Default.class);
        if (!violations.isEmpty()) {
            GlobalEventDispatcher.getInstance().EVENT_BUS.fireEvent(new ErrorEvent(violations.iterator().next().getMessage(), true, ""));
            return;
        }
        rpcService.saveContact(contactDTO, new AsyncCallback<ContactDTO>() {
            public void onSuccess(ContactDTO result) {
                eventBus.fireEvent(new ContactUpdatedEvent(result));
            }

            public void onFailure(Throwable caught) {
                LOG.log(Level.SEVERE, Level.SEVERE.getName(), caught);
            }
        });
    }

    public EditContactPresenter(IContactsServiceAsync rpcService, HandlerManager eventBus, Display display, String id) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = display;
        bind();
        rpcService.getContact(id, new AsyncCallback<ContactDTO>() {
            public void onFailure(Throwable caught) {
                LOG.log(Level.SEVERE, Level.SEVERE.getName(), caught);
            }

            public void onSuccess(ContactDTO result) {
                contactDTO = result;
                EditContactPresenter.this.display.getFirstNameInputT().setValue(contactDTO.getFirstName());
                EditContactPresenter.this.display.getLastNameInput().setValue(contactDTO.getLastName());
            }
        });
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }

    public interface Display {
        HasClickHandlers getSaveButton();

        HasClickHandlers getCancelButton();

        HasValue<String> getFirstNameInputT();

        HasValue<String> getLastNameInput();

        HasText getNotificationBar();

        Widget asWidget();
    }
}
