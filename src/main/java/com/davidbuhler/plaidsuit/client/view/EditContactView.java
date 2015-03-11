package com.davidbuhler.plaidsuit.client.view;

import com.davidbuhler.plaidsuit.client.presenter.EditContactPresenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;

public class EditContactView extends Composite implements EditContactPresenter.Display {
    public static final String SAVE = "Save";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME = "First Name";
    public static final String LAST_NAME = "Last Name";
    private final TextBox firstNameInputT;
    private final TextBox lastNameInput;
    private final FlexTable detailsTable;
    private final Button saveButton;
    private final Button cancelButton;
    private final Label notificationBar;

    public EditContactView() {
        SimplePanel contentDetailsDecorator = new SimplePanel();
        contentDetailsDecorator.setWidth("350px");
        initWidget(contentDetailsDecorator);

        VerticalPanel contentDetailsPanel = new VerticalPanel();
        contentDetailsPanel.setWidth("350px");

        notificationBar = new Label();
        notificationBar.setWidth("300px");
        contentDetailsPanel.add(notificationBar);

        contentDetailsPanel.setWidth("350px");
        // Create the contacts list
        //
        detailsTable = new FlexTable();
        detailsTable.setCellSpacing(0);
        detailsTable.setWidth("350px");
        firstNameInputT = new TextBox();
        lastNameInput = new TextBox();
        initDetailsTable();
        contentDetailsPanel.add(detailsTable);

        HorizontalPanel menuPanel = new HorizontalPanel();
        saveButton = new Button(SAVE);
        cancelButton = new Button(CANCEL);
        menuPanel.add(saveButton);
        menuPanel.add(cancelButton);
        contentDetailsPanel.add(menuPanel);
        contentDetailsDecorator.add(contentDetailsPanel);
    }

    private void initDetailsTable() {
        detailsTable.setWidget(0, 0, new Label(FIRST_NAME));
        detailsTable.setWidget(0, 1, firstNameInputT);
        detailsTable.setWidget(1, 0, new Label(LAST_NAME));
        detailsTable.setWidget(1, 1, lastNameInput);
        firstNameInputT.setFocus(true);
    }

    public HasClickHandlers getSaveButton() {
        return saveButton;
    }

    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public HasValue<String> getFirstNameInputT() {
        return firstNameInputT;
    }

    public HasValue<String> getLastNameInput() {
        return lastNameInput;
    }

    public Label getNotificationBar() {
        return notificationBar;
    }

    public Widget asWidget() {
        return this;
    }
}
