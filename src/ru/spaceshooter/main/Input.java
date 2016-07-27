package ru.spaceshooter.main;

import java.awt.event.KeyEvent;

import ru.spaceshooter.game.EventBroker;

public class Input
{	
	Input(){}
	static Input i=new Input();
	
	public static int mouseX=-1, mouseY=-1;
	
	public static Key KEY_UP=i.new Key(KeyEvent.VK_W);
	public static Key KEY_DOWN=i.new Key(KeyEvent.VK_S);
	public static Key KEY_ENTER=i.new Key(KeyEvent.VK_ENTER);
	public static Key KEY_LEFT=i.new Key(KeyEvent.VK_A);
	public static Key KEY_RIGHT=i.new Key(KeyEvent.VK_D);
	public static Key KEY_ESCAPE=i.new Key(KeyEvent.VK_ESCAPE);
	public static Key KEY_FIRE=i.new Key(KeyEvent.VK_SPACE);
	//...
	
	
	public static void onKeyPressed(int keyCode)
	{
		if(keyCode==KEY_UP.code){ KEY_UP.press(); return; }
		if(keyCode==KEY_DOWN.code){ KEY_DOWN.press(); return; }
		if(keyCode==KEY_ENTER.code){ KEY_ENTER.press(); return; }
		if(keyCode==KEY_LEFT.code){ KEY_LEFT.press(); return; }
		if(keyCode==KEY_RIGHT.code){ KEY_RIGHT.press(); return; }
		if(keyCode==KEY_ESCAPE.code){ KEY_ESCAPE.press(); return; }
		if(keyCode==KEY_FIRE.code){ KEY_FIRE.press(); return; }
	}
	public static void onKeyReleased(int keyCode)
	{
		if(keyCode==KEY_UP.code){ KEY_UP.release(); return; }
		if(keyCode==KEY_DOWN.code){ KEY_DOWN.release(); return; }
		if(keyCode==KEY_ENTER.code){ KEY_ENTER.release(); return; }
		if(keyCode==KEY_LEFT.code){ KEY_LEFT.release(); return; }
		if(keyCode==KEY_RIGHT.code){ KEY_RIGHT.release(); return; }
		if(keyCode==KEY_ESCAPE.code){ KEY_ESCAPE.release(); return; }
		if(keyCode==KEY_FIRE.code){ KEY_FIRE.release(); return; }
	}
	static void onKeyTyped(int keyCode)
	{
		//System.out.println(KEY_UP);
		EventBroker.invoke("keyTyped", keyCode);
	}
	
	
	public class Key
	{
		int code;
		long lPressed;
		boolean pressed;
		
		Key(int code)
		{
			this.code=code;
			lPressed=-1;
			pressed=false;
		}
		
		void press()
		{
			pressed=true;
			if(lPressed>0)
			{
				int dur=(int)(System.currentTimeMillis()-lPressed);
				if(dur>=700) { lPressed=-2; onKeyTyped(code); }
			}
			else if(lPressed==-1) { lPressed=System.currentTimeMillis(); onKeyTyped(code); }
			else if(lPressed==-2) onKeyTyped(code);
			EventBroker.invoke("keyPressed", this);
		}
		void release()
		{
			pressed=false;
			lPressed=-1;
			EventBroker.invoke("keyReleased", this);
		}
		
		public int getCode() { return code; }
		public boolean isPressed() { return pressed; }
	}
}
