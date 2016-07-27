package ru.spaceshooter.game;

import java.awt.Graphics;
import java.util.Iterator;

import ru.spaceshooter.main.Input;

public class Player implements EventBrokerListener
{	
	PlayerSpaceship ship;
	Level wrapper;
	public void setWrapper(Level lvl) { wrapper=lvl; ship.setWrapper(lvl); }
	
	
	public Player(PlayerSpaceship ship)
	{
		this.ship=ship;
		this.ship.getSprite().enablePhys(true);
		restore();
	}
	
	@Override
	public boolean onEvent(String eventId, Object params)
	{
		return false;
	}	
	
	public void update()
	{	
		ship.moveLeft	(	Input.KEY_LEFT	.isPressed());
		ship.moveRight	(	Input.KEY_RIGHT	.isPressed());
		ship.moveUp		(	Input.KEY_UP	.isPressed());
		ship.moveDown	(	Input.KEY_DOWN	.isPressed());
		
		if(Input.KEY_FIRE.isPressed())
		{
			ship.fire();
		}
		
		ship.update();
	}
	
	public void paint(Graphics g)
	{
		ship.paint(g);
	}
	
	public int getX()
	{
		return (int)ship.getSprite().getCenterX();
	}
	public int getY()
	{
		return (int)ship.getSprite().getCenterY();
	}
	
	public float getHpRate()
	{
		return (float)ship.hp/ship.maxhp;
	}
	public float getShieldRate()
	{
		// TODO: create real call to ship.(...) after developing shield gens
		return 0.70F;
	}
	public boolean shieldRegen() { return false; }
	
	public Weapon[] getWeapons()
	{
		return new Weapon[] { ship.currentWeapon };
	}
	
	public void moveTo(int nx, int ny)
	{
		ship.getSprite().moveTo(nx, ny);
	}
	
	public void restore()
	{
		ship.heal();
		ship.recreateShield();
	}
}
