package ru.spaceshooter.game;

public interface EventBrokerListener
{	
	boolean onEvent(String eventId, Object params);
}
