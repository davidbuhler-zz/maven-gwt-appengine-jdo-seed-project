package com.davidbuhler.plaidsuit.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ErrorEventHandler extends EventHandler
{
    void execute(ErrorEvent event);
}