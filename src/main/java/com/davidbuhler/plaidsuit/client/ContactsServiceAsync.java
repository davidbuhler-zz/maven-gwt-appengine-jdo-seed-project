package com.davidbuhler.plaidsuit.client;

import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ContactsServiceAsync
{
	void saveContact(ContactDTO contactDTO, AsyncCallback<ContactDTO> callback);

	void deleteContact(String id, AsyncCallback<Boolean> callback);

    void getContact(String id,  AsyncCallback<ContactDTO> callback);

    void listContacts(AsyncCallback<List<ContactDTO>> async);
}

