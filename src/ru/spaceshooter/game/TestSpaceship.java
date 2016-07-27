package ru.spaceshooter.game;

import ru.spaceshooter.main.ResourceManager;

public class TestSpaceship extends PlayerSpaceship
{
	Sprite ship;
	
	public TestSpaceship()
	{
		ship=new Sprite(ResourceManager.getShip("ship"));
		setMaxHp(100);
		setHp(100);
		currentWeapon=new LaserGun();
		currentWeapon.setOwner(this);
		
		ship.setMaxVelocity(10F);
		ship.setFriction(0.95F);
	}

	@Override
	public Sprite getSprite()
	{
		return ship;
	}	
	
}
