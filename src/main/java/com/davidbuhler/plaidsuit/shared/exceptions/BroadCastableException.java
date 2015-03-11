package com.davidbuhler.plaidsuit.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BroadCastableException extends Exception implements IsSerializable
{
    private static final long	serialVersionUID	= 1L;
    private String				message;

    public BroadCastableException()
    {
        super();
    }

    public BroadCastableException(String value)
    {
        message = value;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}