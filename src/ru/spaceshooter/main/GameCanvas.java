package ru.spaceshooter.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.game.ui.ConfirmationWindow;
import ru.spaceshooter.game.ui.IDrawable;
import ru.spaceshooter.game.ui.IUpdatable;
import ru.spaceshooter.game.ui.Screen;

public class GameCanvas extends Canvas implements KeyListener, EventBrokerListener, IDrawable, IUpdatable
{	
	MainFrame mframe;

	public Image buffer;
	public static final int BW=1200, BH=900;
	
	Screen currentScreen;
	ConfirmationWindow exConfr;
	
	String debugMessage;
	
	public GameCanvas(MainFrame ptr)
	{
		mframe=ptr;
		ptr.addKeyListener(this);
		buffer=ptr.createImage(BW, BH);
		setBounds(0, 0, ptr.WIDTH, ptr.HEIGHT);
		//manager=new PaintManager(this);
		
		ResourceManager.createResourses();
		
		currentScreen=Screen.getLoadingScreen();
		currentScreen.setActive(true);
		
		exConfr=new ConfirmationWindow("Вы действительно хотите выйти?", "Весь несохранённый прогресс будет утерян!");
		exConfr.setActive(false);
		
		new UIThread(this).start();
		new LogicThread(this).start();
		
		setBackground(Color.black);
		
		debugMessage="";
		
		EventBroker.subscribe("exiting", this);
		EventBroker.subscribe("debugMsgChanged", this);
		EventBroker.subscribe("screenChange", this);
	}
	
	@Override
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void update()
	{
		exConfr.update();
		if(exConfr.isFinished())
		{
			if(exConfr.getAsnwer())
			{
				EventBroker.invoke("exit", null);
				System.exit(0);
			}
			else
			{
				//exConfr.forceClose();
				//exConfr.setActive(false);
				//currentScreen.setActive(true);
			}
		}
		if(!exConfr.isActive())
		{
			currentScreen.update();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(buffer==null)
		{
			buffer=mframe.createImage(BW, BH);
			System.out.println("buffer is null, "+this.isDisplayable());
			return;
		}
		Graphics gb=buffer.getGraphics();
		//super.paint(gb);
		Graphics2D gb2=(Graphics2D)gb;
		gb2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.setFont(ResourceManager.defFont().deriveFont(30F));
		currentScreen.paint(gb);
		exConfr.paint(gb);
		
		int w=getWidth(), h=getHeight();
		//Calculating scale factor...
		float sfw=((float)w)/BW, sfh=((float)h)/BH;
		float scale_factor=Math.min(sfw, sfh);
		//Start point for drawing buffer
		int xoff=0, yoff=0;
		//New image dimensions
		int nw=(int)(BW*scale_factor), nh=(int)(BH*scale_factor);
		
		//Stripes
		g.setColor(Color.black);
		if(sfw>sfh)
		{
			xoff=(w-nw)/2;
			g.fillRect(0, 0, xoff, h);
			g.fillRect(nw+xoff, 0, xoff, h);
		}
		if(sfw<sfh)
		{
			yoff=(h-nh)/2;
			g.fillRect(0, 0, w, yoff);
			g.fillRect(0, nh+yoff, w, yoff);
		}
		// Drawing buffer, ~22-23 millis version with scaling, up to 50 millis probably
		// NOTICE: to reduce this time, change PaintManager.paint code to use BW and BH and
		// calculate the dynamically, see int nw, nh
		
		//long time=System.currentTimeMillis();
		
		g.translate(xoff, yoff);
		
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		//g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.scale(scale_factor, scale_factor);
		g.drawImage(buffer, 0, 0, this);
		
		g.setColor(Color.white);
		g.setFont(ResourceManager.defFont().deriveFont(36F));
		g.drawString(debugMessage, 5, 35);
		
		//System.out.print(System.currentTimeMillis()-time+" ");
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		Input.onKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Input.onKeyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		//EventBroker.invoke("keyTyped", e.getKeyCode());
	}

	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(eventId=="exiting")
		{
			//System.exit(0);
			//currentScreen.setActive(false);
			exConfr.setActive(true);
			
			exConfr.show();
		}
		if(eventId=="debugMsgChanged")
		{
			debugMessage=(String)params;
			return true;
		}
		if(eventId=="screenChange")
		{
			currentScreen.dispose();
			currentScreen=(Screen)params;
			currentScreen.initialize();
			return false;
		}
		return false;
	}
}
