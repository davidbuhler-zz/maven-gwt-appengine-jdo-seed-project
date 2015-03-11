package com.davidbuhler.plaidsuit.client.event;
import com.google.gwt.event.shared.GwtEvent;

public class ErrorEvent extends GwtEvent<ErrorEventHandler>
{
    public static Type<ErrorEventHandler>	TYPE			= new Type<ErrorEventHandler>();
    private String							_error			= null;
    private boolean							_isDismissable	= false;
    private String							_message		= null;

    public ErrorEvent(final String message, final boolean isDismissable, final String error)
    {
        super();
        _isDismissable = isDismissable;
        _message = message;
        _error = error;
    }

    @Override
    protected void dispatch(final ErrorEventHandler handler)
    {
        handler.execute(this);
    }

    @Override
    public Type<ErrorEventHandler> getAssociatedType()
    {
        return ErrorEvent.TYPE;
    }

    public String getError()
    {
        return _error;
    }

    public String getMessage()
    {
        return _message;
    }

    public boolean isDismissable()
    {
        return _isDismissable;
    }
}