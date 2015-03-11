package com.davidbuhler.plaidsuit.client.view;

import com.davidbuhler.plaidsuit.client.IContactsServiceAsync;
import com.davidbuhler.plaidsuit.server.ContactsService;
import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import java.util.List;

public class ContactsView<T> extends Composite implements IContactsView<T> {

    private static ContactsViewUiBinder uiBinder = GWT.create(ContactsViewUiBinder.class);
    @UiField
    VerticalPanel verticalPanel;
    @UiField
    Button addButton;
    @UiField
    Button refreshButton;
    @UiField
    Label notificationBar;

    private Presenter<T> presenter;

    public ContactsView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setPresenter(Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setRowData(List<T> rowData) {
        buildTable();
        table.setRowCount(rowData.size(), true);
        table.setRowData(0, rowData);

    }

    @UiHandler("addButton")
    void onAddButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onAddButtonClicked();
        }
    }

    private CellTable table;

    private void buildTable() {
        table = new CellTable();
        new ProvidesKey<ContactDTO>() {
            @Override
            public Object getKey(final ContactDTO item) {
                return (item == null) ? null : item.getId();
            }
        };
        table.setAutoHeaderRefreshDisabled(true);
        table.setAutoFooterRefreshDisabled(true);
        final TextColumn<ContactDTO> nameColumn = new TextColumn<ContactDTO>() {
            @Override
            public String getValue(final ContactDTO value) {
                return value.getFullName();
            }
        };
        Column<ContactDTO, SafeHtml> editColumn = new Column<ContactDTO, SafeHtml>(new SafeHtmlCell()) {
            @Override
            public SafeHtml getValue(ContactDTO contactDTO) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                sb.appendHtmlConstant("<a>");
                sb.appendEscaped("Remove");
                sb.appendHtmlConstant("</a>");
                return sb.toSafeHtml();
            }
        };
        editColumn.setFieldUpdater(new FieldUpdater<ContactDTO, SafeHtml>() {
            @Override
            public void update(final int index, final ContactDTO contact, final SafeHtml value) {

                final IContactsServiceAsync rpcService = (IContactsServiceAsync) GWT.create(ContactsService.class);
                rpcService.deleteContact(contact.getId(), new AsyncCallback<Boolean>() {
                    public void onSuccess(Boolean result) {
                        table.redraw();
                    }

                    public void onFailure(Throwable caught) {

                    }
                });
            }

        });
        // addColumn(goalKeyColumn, Resource.ID);
        table.addColumn(nameColumn, "Name");
        table.addColumn(editColumn, "");
        table.setColumnWidth(editColumn, 100.0, Style.Unit.PX);

        verticalPanel.add(table);
    }

    private List<ContactDTO> _data;
    private ListDataProvider<ContactDTO> _dataProvider;


    @UiHandler("refreshButton")
    void onRefreshButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onRefreshButtonClicked();
        }
    }


    public Widget asWidget() {
        return this;
    }

    @UiTemplate("ContactsView.ui.xml")
    interface ContactsViewUiBinder extends UiBinder<Widget, ContactsView> {
    }
}
