package ru.spaceshooter.main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame extends Frame implements WindowListener
{	
	public final int WIDTH=800, HEIGHT=600;
	GameCanvas cv;
	
	
	public MainFrame()
	{
		super();
		
		cv=new GameCanvas(this);
		
		Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scr.width-WIDTH)/2, (scr.height-HEIGHT)/2);
		setSize(WIDTH, HEIGHT);
		setTitle("ru.spaceshooter.main.MainFrame");
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		
		addWindowListener(this);
		
		add(cv);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub wnd activated
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub wnd deactivated	
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}
}
