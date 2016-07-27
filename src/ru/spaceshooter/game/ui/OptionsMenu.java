package ru.spaceshooter.game.ui;

public class OptionsMenu extends MainScreenMenu
{	
	boolean closing;
	
	public OptionsMenu()
	{
		super();
		
		addMenuItem("Профиль");
		addMenuItem("Звук");
		addMenuItem("Изображение");
		
		addMenuItem("Назад");
		//items.get(3).y+=100;
		moveLastItemDown();
		
		setFontSize(36F);
		
		//setActive(0);
	}
	
	@Override
	protected void onItemActivated(int pos)
	{
		switch(pos)
		{
		case 1:
			SoundOptionsMenu som=new SoundOptionsMenu();
			som.setOwner(this.owner);
			som.setParentMenu(this);
			owner.setMenu(som);
			break;
		case 3:
			navigateBack(true);
			break;
		}
	}
}
