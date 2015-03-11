package com.davidbuhler.plaidsuit.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.List;

public interface IContactsView<T> {

    void setPresenter(Presenter<T> presenter);

    void setRowData(List<T> rowData);

    Widget asWidget();

    public interface Presenter<T> {
        void onAddButtonClicked();

        void onRefreshButtonClicked();

        void onDeleteButtonClicked();

    }
}
