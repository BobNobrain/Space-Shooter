package ru.spaceshooter.game.ui;

import ru.spaceshooter.game.EventBroker;

public class SoundVolumeMenuItem extends TrackbarMenuItem
{
	public SoundVolumeMenuItem(String txt, int position, int x, int y, int w, int h)
	{
		super(txt, position, x, y, w, h);
	}	
	
	@Override
	public void setValue(int val)
	{
		int old=value;
		super.setValue(val);
		if(old!=val)
		{
			EventBroker.invoke("volumeChange", val);
		}
	}
}
