package com.davidbuhler.plaidsuit.client;

import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.davidbuhler.plaidsuit.shared.exceptions.BroadCastableException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("contactsService")
public interface ContactsService extends RemoteService
{
	ContactDTO saveContact(ContactDTO contactDTO) throws BroadCastableException;

	Boolean deleteContact(String id)throws BroadCastableException;

	ContactDTO getContact(String id) throws BroadCastableException;

	List<ContactDTO> listContacts() throws BroadCastableException;
}
