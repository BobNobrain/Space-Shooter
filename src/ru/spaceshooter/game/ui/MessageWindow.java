package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;
import java.util.Vector;

import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.main.Input;
import ru.spaceshooter.main.ResourceManager;

public class MessageWindow extends Layer implements EventBrokerListener
{	
	public final int NONE=0, OK=1, CANCEL=2, YES=3, NO=4;
	int dialogResult;
	public int getResult() { return dialogResult; }
	protected void setResult(int result) { dialogResult=result; }
	
	int borderWidth, foneHeight, buttonsX, stripesHeight;
	boolean shown, closing;
	
	protected int borderHeight, height, showingSpeed, closingSpeed;
	
	protected Color borderColor=new Color(44, 85, 182, 255),
					foneColor=new Color(1F, 1F, 1F, 0.55F),
					foreColor=new Color(44, 85, 182, 255),
					stripesColor=new Color(0F, 0F, 0F, 0.65F);
	
	protected String title, message;
	public void setTitle(String text) { title=text; }
	public void setText(String text) { message=text; }
	public String getText() { return message; }
	public String getTitle() { return title; }
	
	Vector<Button> buttons;
	int selectedButton;
	protected int getSelectedIndex() { return selectedButton; }
	protected void selectButton(int index) { selectedButton=index; }
	
	protected Layer owner;
	public void setOwner(Layer owner) { this.owner=owner; }
	public boolean hasOwner() { return owner!=null; }
	
	
	public MessageWindow()
	{
		dialogResult=NONE;
		shown=closing=false;
		borderWidth=foneHeight=0;
		buttonsX=-GameCanvas.BW;
		height=200;
		borderHeight=3;
		
		showingSpeed=50;
		closingSpeed=60;
		
		buttons=new Vector<Button>();
		/*addButton(OK);
		addButton(CANCEL);
		addButton(NO);
		addButton(YES);
		title="Вы уверены, что уверены?";
		message="Здесь, между прочим, могла бы быть ваша реклама!";*/
		title="";
		message="";
		
		EventBroker.subscribe("keyTyped", this);
	}
	
	
	public void update()
	{
		if(shown) 
		{
			if(closing)
			{
				if(borderWidth>0) borderWidth-=closingSpeed;
				if(foneHeight>0) foneHeight-=closingSpeed;
				if(borderWidth<0) borderWidth=0;
				if(foneHeight<0) foneHeight=0;
				
				if(buttonsX>-GameCanvas.BW) buttonsX-=5*closingSpeed;
				if(buttonsX<-GameCanvas.BW) buttonsX=-GameCanvas.BW;
				if(stripesHeight>0) stripesHeight-=closingSpeed*2;
				if(stripesHeight<0) stripesHeight=0;
				
				if(foneHeight==0 && borderWidth==0) { shown=false; closing=false; }
			}
			else
			{
				if(borderWidth<GameCanvas.BW) borderWidth+=2*showingSpeed;
				if(borderWidth>=GameCanvas.BW/2 && foneHeight<=height)
				{
					foneHeight+=showingSpeed;
				}
				if(foneHeight>height) foneHeight=height;
				if(buttonsX<0) buttonsX+=2*showingSpeed;
				if(buttonsX>0) buttonsX=0;
				
				if(stripesHeight<GameCanvas.BH/2) stripesHeight+=showingSpeed;
				if(stripesHeight>GameCanvas.BH/2) stripesHeight=GameCanvas.BH/2;
			}
		}
		if(isActive())
		{
			for(int i=0; i<buttons.size(); i++)
			{
				buttons.get(i).update();
			}
		}
	}
	
	public void paint(Graphics g)
	{
		if(!shown) return;
		
		g.setColor(stripesColor);
		g.fillRect(0, 0, GameCanvas.BW, stripesHeight);
		g.fillRect(0, GameCanvas.BH-stripesHeight, GameCanvas.BW, stripesHeight);
		
		g.setColor(borderColor);
		g.fillRect(GameCanvas.BW-borderWidth, (GameCanvas.BH-foneHeight)/2-borderHeight, borderWidth, borderHeight);
		g.fillRect(0, (GameCanvas.BH+foneHeight)/2, borderWidth, borderHeight);
		
		g.setColor(foneColor);
		g.fillRect(0, (GameCanvas.BH-foneHeight)/2, GameCanvas.BW, foneHeight);
		
		bfm=g.getFontMetrics(buttonFont);
		if(foneHeight==height)
		{
			g.setFont(ResourceManager.defFont().deriveFont(40F));
			g.setColor(foreColor);
			FontMetrics fm=g.getFontMetrics();
			
			int ty=(GameCanvas.BH-foneHeight)/2+5+fm.getAscent();
			
			g.drawString(title, 15, ty);
			
			g.setFont(buttonFont);
			//g.drawString(message, 30, ty+fm.getHeight()+30);
			drawText(g, message, 30, ty+fm.getHeight()+30, GameCanvas.BW-60);
		}
		
		g.setFont(buttonFont);
		int xpen=30+buttonsX;
		int ypen=(GameCanvas.BH+foneHeight)/2+borderHeight+10;
		for(int i=0; i<buttons.size(); i++)
		{
			Button b=buttons.get(i);
			int bw=20+bfm.stringWidth(b.text);
			int bh=10+bfm.getAscent()+bfm.getDescent();
			
			g.setColor(b.selected?bFoneActiveColor:bFoneColor);
			g.fillRect(xpen, ypen+1, bw+1, bh+1);
			
			g.setColor(bBorderColor);
			g.drawRect(xpen-1, ypen, bw+2, bh+2);
			if(b.selected) g.fillRect(xpen+10, bh+ypen-bfm.getDescent()+2, bw-20, 3);
			
			
			g.setColor(b.selected?bForeActiveColor:bForeColor);
			g.drawString(b.text, xpen+10, ypen+6+bfm.getAscent());
			
			xpen+=bw+15;
		}
	}
	protected void drawText(Graphics g, String text, int x, int y, int maxW)
	{
		StringTokenizer st=new StringTokenizer(text);
		StringBuilder line=new StringBuilder(), temp=new StringBuilder();
		FontMetrics fm=g.getFontMetrics();
		while(st.hasMoreTokens())
		{
			temp.append(" ");
			String token=st.nextToken();
			temp.append(token);
			int w=fm.stringWidth(temp.toString());
			if(w>maxW)
			{
				g.drawString(line.toString(), x, y);
				y+=fm.getHeight();
				line.delete(0, line.length());
				temp.delete(0,  temp.length());
				line.append(token);
			}
			else
			{
				if(line.length()>0) line.append(" ");
				line.append(token);
			}
		}
		if(line.length()>0)
		{
			g.drawString(line.toString(), x, y);
		}
	}
	
	public void show() { shown=true; setActive(true); closing=false; dialogResult=NONE; }
	public void forceClose() { closing=true; onClosing(); }
	public void instantClose()
	{
		closing=false; shown=false;
		foneHeight=0;
		borderWidth=0;
		onClosing();
	}
	protected void onClosing()
	{
		setActive(false);
		if(hasOwner()) owner.setActive(true);
		EventBroker.invoke("msgWindowClose", this);
	}
	
	public boolean isShown() { return shown; }
	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(eventId=="keyTyped" && isActive())
		{
			int code=(int)params;
			if(code==Input.KEY_UP.getCode() || code==Input.KEY_LEFT.getCode())
			{
				moveLeft();
				return true;
			}
			if(code==Input.KEY_DOWN.getCode() || code==Input.KEY_RIGHT.getCode())
			{
				moveRight();
				return true;
			}
			if(code==Input.KEY_ENTER.getCode())
			{
				activateButton();
				//forceClose();
				return true;
			}
		}
		return false;
	}
	
	protected void addButton(int result)
	{
		buttons.add(this.new Button(result));
		if(buttons.size()==1) buttons.get(0).select();
	}
	protected void moveLeft()
	{
		if(buttons.size()==0) return;
		buttons.get(selectedButton).unselect();
		selectedButton--;
		if(selectedButton<0) selectedButton=buttons.size()-1;
		buttons.get(selectedButton).select();
	}
	protected void moveRight()
	{
		if(buttons.size()==0) return;
		buttons.get(selectedButton).unselect();
		selectedButton++;
		if(selectedButton>=buttons.size()) selectedButton=0;
		buttons.get(selectedButton).select();
	}
	protected void activateButton()
	{
		if(buttons.size()==0) return;
		dialogResult=buttons.get(selectedButton).result;
		if(dialogResult!=NONE) forceClose();
		onButtonActivated(selectedButton);
	}
	
	public boolean isFinished()
	{
		return dialogResult!=NONE;
	}
	
	protected void onButtonActivated(int index)
	{
		// ... to be overrided
	}
	
	public void dispose()
	{
		EventBroker.unsubscribe(this);
	}
	
	
	protected Font buttonFont=ResourceManager.defFont().deriveFont(30F);
	FontMetrics bfm;
	protected Color bFoneColor=new Color(1F, 1F, 1F, 0.25F);
	protected Color bFoneActiveColor=Color.white;
	protected Color bBorderColor=new Color(44, 85, 182, 255);
	protected Color bForeColor=Color.white;
	protected Color bForeActiveColor=new Color(44, 85, 182, 255);
	protected class Button
	{
		public String text;
		int result;
		boolean selected=false;
		int extraHeight=0;
		
		
		Button(int result)
		{
			this.result=result;
			// TODO: import text from resourses
			switch(result)
			{
			case OK:
				text="Окок";
				break;
			case CANCEL:
				text="Передумал";
				break;
			case NO:
				text="Неа";
				break;
			case YES:
				text="Дат";
				break;
			}
		}

		void select()
		{
			selected=true;
		}
		public void unselect()
		{
			selected=false;
		}
		void update()
		{
			if(selected)
			{
				if(extraHeight<10) extraHeight+=1;
			}
			else 
			{
				if(extraHeight>0) extraHeight-=2;
				if(extraHeight<0) extraHeight=0;
			}
		}
		
		int getResult() { return result; }
	}
}
