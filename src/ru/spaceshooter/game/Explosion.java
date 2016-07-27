package ru.spaceshooter.game;

import ru.spaceshooter.main.ResourceManager;

public class Explosion extends GameObject
{	
	protected Sprite s;
	protected boolean active;
	private ExplosionType type;
	
	
	public Explosion()
	{
		s=null;
		active=false;
		type=ExplosionType.NONE;
	}

	@Override
	public Sprite getSprite()
	{
		return s;
	}
	
	@Override
	public void update()
	{
		super.update();
		s.update();
		if(!active)
		{
			if(s.currentFrame()==0)
			{
				s.pause();
				s.setVisibility(false);
			}
		}
		else
		{
			if(s.currentFrame()==s.totalFrames()-1)
			{
				active=false;
			}
		}
	}
	
	public void explodeAt(int x, int y)
	{
		active=true;
		s.moveTo(x, y);
		s.gotoFrame(0);
		s.setVisibility(true);
		s.play();
	}
	
	public void setType(ExplosionType t)
	{
		if(active) return;
		type=t;
		System.out.println(t);
		if(t==null) 
		{
			// Чёзанахер?!
			System.exit(0);
		}
		// TODO: danger! алярма! наверное, надо сделать специализированный пул взрывов
		// "мы же не хотим, чтобы сборщик мусора сошёл с ума", собирая new Sprite(...)
		switch(type)
		{
		case LASER_BLAST:
			s=new Sprite(ResourceManager.getExplosion("laser"), 16, 16);
			break;
		default:
			s=new Sprite(ResourceManager.getExplosion("regular"), 32, 32);
		}
	}
}
