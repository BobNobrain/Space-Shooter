package ru.spaceshooter.game;

import ru.spaceshooter.main.ResourceManager;
import ru.spaceshooter.phys.VectorF;

public class TestEnemy1 extends Enemy
{
	Sprite ship;
	
	public TestEnemy1()
	{
		ship=new Sprite(ResourceManager.getEnemy("testenemy1"));
		ship.moveTo(50, 50);
		currentWeapon=new LaserGun();
		currentWeapon.setOwner(this);
		ship.enablePhys(true);
		ship.setMaxVelocity(2F);
		ship.setFriction(0.99F);
		
		setMaxHp(50);
		setHp(50);
	}
	
	@Override
	public Sprite getSprite()
	{
		return ship;
	}
	
	@Override
	protected void behave()
	{
		if(getWrapper()==null) return;
		
		int dx=getWrapper().getPlayerX()-ship.getX();
		int dy=getWrapper().getPlayerY()-ship.getY();
		
		ship.setAccel(new VectorF(dx, dy, 1.5F));
		
		if(dx<10 && dx>-10 && dy>0) fire();
	}
}
