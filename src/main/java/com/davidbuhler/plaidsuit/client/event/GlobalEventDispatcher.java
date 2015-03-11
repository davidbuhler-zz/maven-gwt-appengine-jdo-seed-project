package com.davidbuhler.plaidsuit.client.event;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * Created by David Buhler on 3/8/2015.
 */
public class GlobalEventDispatcher {
    private static GlobalEventDispatcher	INSTANCE;

    static public GlobalEventDispatcher getInstance()
    {
        if (GlobalEventDispatcher.INSTANCE == null)
        {
            GlobalEventDispatcher.INSTANCE = new GlobalEventDispatcher();
        }
        return GlobalEventDispatcher.INSTANCE;
    }

    public EventBus EVENT_BUS	= GWT.create(SimpleEventBus.class);
}
