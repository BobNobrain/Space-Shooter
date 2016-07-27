package ru.spaceshooter.utils;

import ru.spaceshooter.game.EventBroker;

public class Pool<T>
{	
	Object[] pool;
	boolean[] used;
	Factory<T> factory;
	int created;
	public int getSize() { return created; }
	public int getMaxSize() { return pool.length; }
	
	public Pool(Factory<T> f, int maxSize)
	{
		factory=f;
		pool=new Object[maxSize];
		used=new boolean[maxSize];
		created=0;
	}
	
	public T getNewObject()
	{
		for(int i=0; i<created; i++)
		{
			if(!used[i])
			{
				used[i]=true;
				//EventBroker.invoke("debugMsgChanged", "returned "+i);
				return (T)pool[i];
			}
		}
		if(created==pool.length)
		{
			//mark everything as unused, except the first that would be returned
			//that should make all first objects to disappear and to be converted to latest objs
			for(int i=1; i<pool.length; i++)
			{
				used[i]=false;
			}
			return (T)pool[0];
		}
		used[created]=true;
		pool[created]=factory.getNewInstance();
		
		//EventBroker.invoke("debugMsgChanged", "created "+created);
		
		return (T)pool[created++];
	}
	
	public void dispose(T obj)
	{
		for(int i=0; i<created; i++)
		{
			if((T)pool[i]==obj)
			{
				used[i]=false;
				return;
			}
		}
	}
	
	public void dispose()
	{
		for(int i=0; i<created; i++)
		{
			pool[i]=null;
		}
		pool=null;
		used=null;
	}
	
	public Object[] getArray()
	{
		return pool;
	}
}
