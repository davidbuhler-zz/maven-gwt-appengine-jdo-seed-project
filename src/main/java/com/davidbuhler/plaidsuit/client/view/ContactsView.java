package com.davidbuhler.plaidsuit.client.view;

import com.davidbuhler.plaidsuit.client.ContactsService;
import com.davidbuhler.plaidsuit.client.ContactsServiceAsync;
import com.davidbuhler.plaidsuit.shared.constants.UserMessages;
import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.cell.client.FieldUpdater;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ProvidesKey;

import java.util.List;
import java.util.logging.Logger;

public class ContactsView<T> extends Composite implements IContactsView<T>
{
	public static final String CONTACT_NAME = "Name";
	public static final String EMPTY_STRING = "";
	public static final String REMOVE = "Remove";
	private static ContactsViewUiBinder uiBinder = GWT.create(ContactsViewUiBinder.class);
	private static final Logger LOG = Logger.getLogger(ContactsView.class.getName());

	@UiField
	VerticalPanel verticalPanel;

	@UiField
	Button addButton;

	@UiField
	Button refreshButton;

	@UiField
	Label notificationBar;

	private Presenter<T> _presenter;
	private List<T> _rowData;
	private CellTable _table;

	public ContactsView()
	{
		initWidget(uiBinder.createAndBindUi(this));
		buildTable();
	}

	public void setPresenter(Presenter<T> presenter)
	{
		this._presenter = presenter;
	}

	@Override
	public void setRowData(List<T> rowData)
	{
		_rowData = rowData;
		_table.setRowCount(_rowData.size(), true);
		_table.setRowData(0, _rowData);
	}

	@UiHandler("addButton")
	void onAddButtonClicked(ClickEvent event)
	{
		if (_presenter != null)
		{
			_presenter.onAddButtonClicked();
		}
	}

	private void buildTable()
	{
		_table = new CellTable();
		new ProvidesKey<ContactDTO>()
		{
			@Override
			public Object getKey(final ContactDTO item)
			{
				return (item == null) ? null : item.getId();
			}
		};
		_table.setAutoHeaderRefreshDisabled(true);
		_table.setAutoFooterRefreshDisabled(true);
		final TextColumn<ContactDTO> nameColumn = new TextColumn<ContactDTO>()
		{
			@Override
			public String getValue(final ContactDTO value)
			{
				return value.getFullName();
			}
		};
		Column<ContactDTO, SafeHtml> editColumn = new Column<ContactDTO, SafeHtml>(new ClickableSafeHtmlCell())
		{
			@Override
			public SafeHtml getValue(ContactDTO contactDTO)
			{
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<a>");
				sb.appendEscaped(REMOVE);
				sb.appendHtmlConstant("</a>");
				return sb.toSafeHtml();
			}
		};
		editColumn.setFieldUpdater(new FieldUpdater<ContactDTO, SafeHtml>()
		{
			@Override
			public void update(final int index, final ContactDTO contact, final SafeHtml value)
			{

				final ContactsServiceAsync rpcService = GWT.create(ContactsService.class);
				rpcService.deleteContact(contact.getId(), new AsyncCallback<Boolean>()
				{
					public void onSuccess(Boolean result)
					{
						_rowData.remove(contact);
						setRowData(_rowData);
						_table.redraw();
					}

					public void onFailure(Throwable caught)
					{
						LOG.severe(caught.toString());
						Window.alert(UserMessages.ERROR_DELETING_CONTACT);
					}
				});
			}

		});
		_table.addColumn(nameColumn, CONTACT_NAME);
		_table.addColumn(editColumn, EMPTY_STRING);
		_table.setColumnWidth(editColumn, 100.0, Style.Unit.PX);
		verticalPanel.add(_table);
	}

	@UiHandler("refreshButton")
	void onRefreshButtonClicked(ClickEvent event)
	{
		if (_presenter != null)
		{
			_presenter.onRefreshButtonClicked();
		}
	}

	public Widget asWidget()
	{
		return this;
	}

	@UiTemplate("ContactsView.ui.xml")
	interface ContactsViewUiBinder extends UiBinder<Widget, ContactsView>
	{
	}
}
