package ru.spaceshooter.game;

import ru.spaceshooter.main.ResourceManager;
import ru.spaceshooter.phys.VectorF;

public class LaserGun extends Weapon
{
	public LaserGun()
	{
		name="Laser gun";
		
		ammo=maxAmmo=500;
		ammoDecrement=35;
		ammoIncrement=1;
		
		ammoRestoreTime=40;
		chargingTime=350;
		mode=AmmoRestoreMode.CONTINUOSLY;
		
		bulletSpeed=15;
		type=BulletType.LASER;
		
		placeCoords=new VectorF[] { VectorF.O };
		
		weaponIcon=ResourceManager.getWpnIcon("w_lasergun");
	}
}
