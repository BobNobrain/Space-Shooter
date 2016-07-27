package ru.spaceshooter.game;

import ru.spaceshooter.utils.Factory;

public class BulletFactory implements Factory<Bullet>
{
	@Override
	public Bullet getNewInstance()
	{
		return new Bullet(BulletType.NONE, false);
	}	
}
