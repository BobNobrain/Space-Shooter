package ru.spaceshooter.game;

import java.awt.Image;

import ru.spaceshooter.main.ResourceManager;
import ru.spaceshooter.phys.VectorF;

public abstract class Weapon
{	
	Spaceship owner;
	public void setOwner(Spaceship ship)
	{
		owner=ship;
	}
	protected boolean isFriendly() { return owner.isFriendly(); }
	
	long lastShootTime=0, lastRestoreTime=0;
	
	//
	// ��������������
	//
	protected String name="Abstract weapon";
	public String getName() { return name; }
	
	protected int chargingTime;
	public String getChargingTime()
	{ return (chargingTime/1000)+"."+((chargingTime%1000)/100); } // "12.7"
	
	protected int ammo, maxAmmo;
	public float getLoadedPercent() { return (float)ammo/maxAmmo; }
	// ���������� �� ���-�� ���������� �������� ������ �������
	protected int ammoDecrement;
	// ���� �� -1, �� ��������������� ���-�� �������� ����� ������� ����������� �������� ���������� �������
	protected int ammoRestoreTime;
	// ���-�� ����������������� �������� �� ���
	protected int ammoIncrement;
	
	// ������ �������������� ��������: �������� (�� ���������� ����) ��� ���������
	public enum AmmoRestoreMode { ONCE, CONTINUOSLY, NEVER }
	protected AmmoRestoreMode mode;
	
	protected float bulletSpeed;
	
	// ������ ������������� ��������� ��� ���������� �� ��� ����
	protected VectorF[] placeCoords;
	
	protected BulletType type;
	
	//
	// ������
	//
	public void shoot(Level wrapper, int x, int y, float direction)
	{
		if(ammo<ammoDecrement) return;
		int time=(int)(System.currentTimeMillis()-lastShootTime);
		if(lastShootTime==0) time=chargingTime;
		if(time>=chargingTime)
		{
			placeBullets(wrapper, x, y, direction);
			lastShootTime=System.currentTimeMillis();
			ammo-=ammoDecrement;
			if(ammo<0) ammo=0;
			if(mode==AmmoRestoreMode.ONCE)
			{
				// � ������ �������� �������������� ���� ��������, ������ ��������
				// � ammoRestoreTime �� ����� ����, ��� ��� �����������: 
				if(ammo==0) lastRestoreTime=lastShootTime;
			}
		}
	}
	protected void placeBullets(Level wrapper, int x, int y, float direction)
	{
		for(int i=0; i<placeCoords.length; ++i)
		{
			Bullet b=wrapper.getBulletInstance();
			b.convert(type);
			b.moveTo(x-b.getWidth()/2+(int)placeCoords[i].x, y-b.getHeight()/2+(int)placeCoords[i].y);
			b.setFriendly(isFriendly());
			
			VectorF v=new VectorF(0, bulletSpeed);
			v.rotate(direction);
			b.setVelocity(v);
		}
	}
	
	public void update()
	{
		if(mode==AmmoRestoreMode.NEVER) return;
		
		int time=ammoRestoreTime;
		if(lastRestoreTime!=0) time=(int)(System.currentTimeMillis()-lastRestoreTime);
		if(time>=ammoRestoreTime)
		{
			switch(mode)
			{
			case ONCE:
				if(ammo==0) ammo=maxAmmo;
				break;
			case CONTINUOSLY:
				if(ammo<maxAmmo)
				{
					ammo+=ammoIncrement;
					if(ammo>maxAmmo) ammo=maxAmmo;
					lastRestoreTime=System.currentTimeMillis();
				}
				break;
			}
		}
	}
	
	public Image getWeaponImage() { return weaponPreview; }
	// icon on the bar and image in the shop
	protected Image weaponIcon=ResourceManager.getWpnIcon("w_abstr"), weaponPreview;
	public Image getWeaponIcon() { return weaponIcon; }
}
