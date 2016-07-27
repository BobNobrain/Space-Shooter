package ru.spaceshooter.game;

import ru.spaceshooter.phys.VectorF;

public class RocketWeapon extends Weapon
{
	public RocketWeapon()
	{
		name="Rocket launcher";
		
		ammo=maxAmmo=15;
		ammoDecrement=1;
		//ammoIncrement=1;
		
		ammoRestoreTime=2500;
		chargingTime=500;
		mode=AmmoRestoreMode.ONCE;
		
		bulletSpeed=5;
		type=BulletType.ROCKET;
		
		placeCoords=new VectorF[] { new VectorF(-25F, 0), new VectorF(25F, 0) };
	}
}
