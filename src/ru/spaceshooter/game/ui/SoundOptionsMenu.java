package ru.spaceshooter.game.ui;

public class SoundOptionsMenu extends MainScreenMenu
{	
	public SoundOptionsMenu()
	{
		super();
		x=300; yoffset=200;
		width=600; height=900;
		
		addAndPlace(new SoundVolumeMenuItem("Громкость: ", 0, 0, 0, 0, 0));
		
		addMenuItem("Назад");
		moveLastItemDown();
		
		setFontSize(36F);
	}
	
	@Override
	protected void onItemActivated(int pos)
	{
		switch(pos)
		{
		case 1:
			navigateBack(true);
			break;
		}
	}
}
