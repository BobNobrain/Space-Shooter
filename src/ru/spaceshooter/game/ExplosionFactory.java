package ru.spaceshooter.game;

import ru.spaceshooter.utils.Factory;

public class ExplosionFactory implements Factory<Explosion>
{
	
	@Override
	public Explosion getNewInstance()
	{
		return new Explosion();
	}
	
}
