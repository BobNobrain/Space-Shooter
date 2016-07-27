package ru.spaceshooter.game;

import ru.spaceshooter.phys.VectorF;

public abstract class Spaceship extends GameObject
{
	protected Weapon currentWeapon;
	private Level wrapper;
	public void setWrapper(Level lvl) { wrapper=lvl; }
	public Level getWrapper() { return wrapper; }
	
	boolean friendly;
	public boolean isFriendly() { return friendly; }
	public void setFriendly(boolean value) { friendly=value; }
	
	// TODO: protected ShieldGen generator; protected Engine engine;
	/*int shield=0, maxShield=0;
	float shieldCoeff=0.0F;
	boolean shieldOn=false;
	protected void createShield(int max, float coeff)
	{ shield=maxShield=max; shieldCoeff=coeff; }*/
	
	@Override
	protected void decreaseHp(int dhp)
	{
		// *=generator.getCoeff()
		super.decreaseHp(dhp);
	}
	
	public void fire()
	{
		if(wrapper==null) return;
		if(currentWeapon==null) return;
		currentWeapon.shoot(wrapper, getSprite().getX()+getSprite().getWidth()/2,
							getSprite().getY()+getSprite().getHeight()/2, getBulletDirection());
	}
	protected float getBulletDirection()
	{
		return isFriendly()? VectorF.UP:VectorF.DOWN;
	}
	
	@Override
	public void update()
	{
		super.update();
		currentWeapon.update();
	}
}
