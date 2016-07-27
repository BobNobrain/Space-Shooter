package ru.spaceshooter.game.ui;

public class ConfirmationWindow extends MessageWindow
{	
	public ConfirmationWindow(String title, String text)
	{
		super();
		setTitle(title);
		setText(text);
		addButton(NO);
		addButton(YES);
	}
	
	public boolean getAsnwer() { return dialogResult==YES; }
}
