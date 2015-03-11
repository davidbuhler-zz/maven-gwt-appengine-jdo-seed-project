package com.davidbuhler.plaidsuit.client.event;

import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.event.shared.GwtEvent;

public class ContactUpdatedEvent extends GwtEvent<ContactUpdatedEventHandler>
{

	public static Type<ContactUpdatedEventHandler> TYPE = new Type<ContactUpdatedEventHandler>();
	private final ContactDTO updatedContactDTO;

	public ContactUpdatedEvent(ContactDTO updatedContactDTO)
	{
		this.updatedContactDTO = updatedContactDTO;
	}

	public ContactDTO getUpdatedContact()
	{
		return updatedContactDTO;
	}

	@Override
	public Type<ContactUpdatedEventHandler> getAssociatedType()
	{
		return TYPE;
	}

	@Override
	protected void dispatch(ContactUpdatedEventHandler handler)
	{
		handler.onContactUpdated(this);
	}
}
