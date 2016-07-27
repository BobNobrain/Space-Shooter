package ru.spaceshooter.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import ru.spaceshooter.phys.*;

public class Sprite extends PhysObject
{	
	protected int width, height; //, rotation?
	private int cx, cy, cw, ch; // collision rect
	
	public int getX() { return (int)r.x; }
	public int getY() { return (int)r.y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public void setX(int value) { r.x=value; }
	public void setY(int value) { r.y=value; }
	public void setWidth(int value) { if(width==cw) cw=value; width=value; }
	public void setHeight(int value) { if(height==ch) ch=value; height=value; }
	public void moveBy(int dx, int dy) { r.x+=dx; r.y+=dy; }
	public void moveTo(int nx, int ny) { r.x=nx; r.y=ny; }
	public void resize(int nw, int nh)
	{
		if(width==cw) cw=nw; //this scales collision rect if it fills the whole sprite
		if(height==ch) ch=nh;
		width=nw; height=nh;
	}
	
	public float getCenterX() { return r.x+width/2F; }
	public float getCenterY() { return r.y+height/2F; }
	
	public Rectangle getCollisionRectangle()
	{
		return new Rectangle(cx, cy ,cw, ch);
	}
	public Rectangle getCollisionBounds()
	{
		return new Rectangle(cx+getX(), cy+getY() ,cw, ch);
	}
	public void setCollisionRectangle(int x, int y, int w, int h)
	{
		cx=x; cy=y; cw=w; ch=h;
	}
	
	boolean visible;
	public boolean isVisible() { return visible; }
	public void setVisibility(boolean vis) { visible=vis; }
	
	boolean phys;
	public boolean physEnabled() { return phys; }
	public void enablePhys(boolean flag) { phys=flag; }
	
	String name;
	public String getName() { return name; }
	public void setName(String s) { name=s; }
	
	protected BufferedImage src;
	
	private int frame, framesCount;
	public int currentFrame() { return frame; }
	public int totalFrames() { return framesCount; }
	public void gotoFrame(int f)
	{
		if(f>=0 && f<framesCount)
		{
			frame=f;
			lastDrawnTime=System.currentTimeMillis();
		}
	}
	public void nextFrame()
	{
		frame++;
		lastDrawnTime=System.currentTimeMillis();
		if(frame>=framesCount)
		{
			frame=0;
		}
	}
	public void prevFrame()
	{
		frame--;
		lastDrawnTime=System.currentTimeMillis();
		if(frame<0)
		{
			frame=framesCount-1;
		}
	}
	
	private int delay;
	public int getFrameDelay() { return delay; }
	public void setFrameDelay(int millis) { delay=millis; }
	private long lastDrawnTime=-1;
	
	private boolean paused;
	public boolean isPaused() { return paused; }
	public void play() { paused=false; lastDrawnTime=System.currentTimeMillis(); }
	public void pause() { paused=true; }
	
	
	public Sprite(BufferedImage source)
	{
		src=source;
		if(source!=null)
		{
			width=source.getWidth(null);
			height=source.getHeight(null);
		}
		framesCount=1;
		frame=0;
		delay=33;
		paused=false; phys=false;
		
		cx=cy=0;
		cw=width;
		ch=height;
		visible=true;
	}
	public Sprite(BufferedImage source, int width, int height)
	{
		this(source);
		int w=this.width, h=this.height;
		this.width=width;
		this.height=height;
		
		cw=width;
		ch=height;
		
		// TODO: make with Math.floor if needed
		framesCount=Math.max(1, w*h/width/height);
	}
	
	
	public Rectangle getFrameBounds(int frame)
	{
		Rectangle result=new Rectangle();
		result.width=width; result.height=height;
		int xfc=src.getWidth(null)/width;
		result.x=width*(frame%xfc);
		result.y=height*(frame/xfc);
		
		return result;
	}
	
	public void update()
	{
		if(!paused)
		{
			int elapsed=delay;
			if(lastDrawnTime!=-1) elapsed=(int)(System.currentTimeMillis()-lastDrawnTime);
			if(elapsed>=delay) 
			{
				nextFrame();
				lastDrawnTime=System.currentTimeMillis();
			}
		}
		if(phys)
		{
			// TODO: rewrite and reorganize and make better the physics
			updatePhys();
		}
	}
	
	public void paint(Graphics g)
	{
		if(!visible) return;
		Rectangle bounds=getFrameBounds(frame);
		if(framesCount==1)
		{
			g.drawImage(src, getX(), getY(), null);
		}
		else
		{
			g.drawImage(src.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height), getX(), getY(), null);
		}
		//System.out.println("painted in: "+x+" "+y+" "+width+" "+height);
	}
	
	public void dispose()
	{
		src=null;
	}
	
	public boolean collidesWith(Rectangle r)
	{
		return r.intersects(this.getCollisionBounds());
	}
	public boolean collidesWith(Sprite s)
	{
		Rectangle scr=s.getCollisionBounds();
		//System.out.println(scr+" & "+getCollisionBounds());
		return collidesWith(scr);
	}
}
