package ru.spaceshooter.game;

import java.util.Vector;

public class EventBroker
{	
	class EventList
	{
		String evId;
		Vector<EventBrokerListener> listeners;
		
		EventList(String id)
		{
			evId=id;
			listeners=new Vector<EventBrokerListener>();
		}
		
		void addListener(EventBrokerListener l)
		{
			listeners.add(l);
		}
		void removeListener(EventBrokerListener l)
		{
			listeners.remove(l);
		}
		void clear()
		{
			listeners.clear();
		}
		void invoke(Object param)
		{
			for(int i=0; i<listeners.size(); i++)
			{
				if(listeners.get(i).onEvent(evId, param)) return;
			}
		}
	}
	
	static Vector<EventList> events=new Vector<EventBroker.EventList>();
	
	public static void invoke(String event, Object param)
	{
		System.out.println("Event invoked: "+event+" "+param);
		for(int i=0; i<events.size(); i++)
		{
			if(events.get(i).evId==event)
			{
				events.get(i).invoke(param);
				return;
			}
		}
	}
	
	public static void subscribe(String event, EventBrokerListener obj)
	{
		for(int i=0; i<events.size(); i++)
		{
			if(events.get(i).evId==event)
			{
				events.get(i).addListener(obj);
				return;
			}
		}
		EventBroker b=new EventBroker();
		events.add(b.new EventList(event));
		events.get(events.size()-1).addListener(obj);
	}
	public static void unsubscribe(String event, EventBrokerListener obj)
	{
		for(int i=0; i<events.size(); i++)
		{
			if(events.get(i).evId==event)
			{
				events.get(i).removeListener(obj);
				if(events.get(i).listeners.size()==0)
					events.remove(i);
				return;
			}
		}
	}
	public static void unsubscribe(EventBrokerListener obj)
	{
		for(int i=0; i<events.size(); i++)
		{
			events.get(i).removeListener(obj);
			if(events.get(i).listeners.size()==0)
				events.remove(i);
		}
	}
}
