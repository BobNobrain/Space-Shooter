package ru.spaceshooter.main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.FeatureDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class ResourceManager
{	
	class Resource
	{
		public Resource(String key, Object obj)
		{
			name=key;
			data=obj;
		}
		String name;
		Object data;
	}
	
	private static Vector<Resource> bullets, decos, enemies, explosions, fones, ships, shop, weapons;
	
	private static Font def;
	private static Image splash;
	public static Image getSplash() { return splash; }
	
	static boolean done=false;
	public static boolean isReady()
	{
		return done;
	}
	
	private ResourceManager() {}
	
	static String path;
	public static void createResourses()
	{
		path="/ru/spaceshooter/res/";
		
		def=loadFont("Jura.ttf");
		
		splash=BI(path+"images/splash.png");
		
		bullets		=new Vector<Resource>();
		decos		=new Vector<Resource>();
		enemies		=new Vector<Resource>();
		explosions	=new Vector<Resource>();
		fones		=new Vector<Resource>();
		ships		=new Vector<Resource>();
		shop		=new Vector<Resource>();
		weapons		=new Vector<Resource>();
		
		String[] bullets={ "b_laser", "b_rocket" };
		String[] decos={};
		String[] enemies={ "testenemy1" };
		String[] explosions={ "regular", "laser" };
		String[] fones={ "hangar", "lvl0" };
		String[] ships={ "ship" };
		String[] shopItems={ "test" };
		String[] wpnIcons={ "w_abstr", "w_lasergun" };
		
		addBullets(bullets);
		addDecos(decos);
		addEnemies(enemies);
		addExplosions(explosions);
		addFones(fones);
		addShips(ships);
		addShopItems(shopItems);
		addWpnIcons(wpnIcons);
		
		done=true;
	}
	
	static void addFones(String[] names)
	{
		/* C:\Users\ user\Documents\Eclipse\SpaceShooter\src\ru\spaceshooter\res\images\fones\lvl0.png*/
		for(String name: names)
		{
			fones.add(new ResourceManager().new Resource(name, BI(path+"images/fones/"+name+".png")));
		}
	}
	
	static void addBullets(String[] names)
	{
		for(String name: names)
		{
			bullets.add(new ResourceManager().new Resource(name, BI(path+"images/bullets/"+name+".png")));
		}
	}
	
	static void addDecos(String[] names)
	{
		for(String name: names)
		{
			decos.add(new ResourceManager().new Resource(name, BI(path+"images/decorations/"+name+".png")));
		}
	}
	
	static void addExplosions(String[] names)
	{
		for(String name: names)
		{
			explosions.add(new ResourceManager().new Resource(name, BI(path+"images/explosions/"+name+".png")));
		}
	}
	
	static void addEnemies(String[] names)
	{
		for(String name: names)
		{
			enemies.add(new ResourceManager().new Resource(name, BI(path+"images/enemies/"+name+".png")));
		}
	}
	
	static void addShips(String[] names)
	{
		for(String name: names)
		{
			ships.add(new ResourceManager().new Resource(name, BI(path+"images/ships/"+name+".png")));
		}
	}
	
	static void addWpnIcons(String[] names)
	{
		for(String name: names)
		{
			weapons.add(new ResourceManager().new Resource(name, I(path+"images/weapons/"+name+".png")));
		}
	}
	
	static void addShopItems(String[] names)
	{
		for(String name: names)
		{
			shop.add(new ResourceManager().new Resource(name, I(path+"images/shop/"+name+".png")));
		}
	}
	
	static Font loadFont(String name)
	{
		File f;
		try
		{
			f=new File(new ResourceManager().getClass().getResource(path+"fonts/"+name).toURI());
			return Font.createFont(Font.TRUETYPE_FONT, f);
		}
		catch(URISyntaxException e)
		{
			err("Something is wrong with font loading...");
		}
		catch(FontFormatException e)
		{
			err("Wrong font format found!");
		}
		catch(IOException e)
		{
			err("General IO error...");
		}
		return null;
	}
	
	static void err(String msg)
	{
		System.out.println("ResourceManager error! "+msg);
	}
	
	
	static Toolkit getT()
	{
		return Toolkit.getDefaultToolkit();
	}
	static BufferedImage BI(String resName)
	{
		try
		{
			return ImageIO.read(new ResourceManager().getClass().getResource(resName));
		}
		catch(IOException e)
		{
			err("While loading "+resName);
			return null;
		}
	}
	static Image I(String resName)
	{
		Image i=getT().createImage(new ResourceManager().getClass().getResource(resName));
		return i;
	}
	
	
	public static Font defFont()
	{
		return def;
	}
	
	public static Image getFone(String name)
	{
		for(Resource r: fones)
		{
			if(r.name==name)
				return (Image)r.data;
		}
		return null;
	}
	public static Image getWpnIcon(String name)
	{
		for(Resource r: weapons)
		{
			if(r.name==name)
				return (Image)r.data;
		}
		return null;
	}
	public static Image getShopItem(String name)
	{
		for(Resource r: shop)
		{
			if(r.name==name)
				return (Image)r.data;
		}
		return null;
	}
	
	public static BufferedImage getBullet(String name)
	{
		for(Resource r: bullets)
		{
			if(r.name==name)
				return (BufferedImage)r.data;
		}
		return null;
	}
	public static BufferedImage getExplosion(String name)
	{
		for(Resource r: explosions)
		{
			if(r.name==name)
				return (BufferedImage)r.data;
		}
		return null;
	}
	public static BufferedImage getEnemy(String name)
	{
		for(Resource r: enemies)
		{
			if(r.name==name)
				return (BufferedImage)r.data;
		}
		return null;
	}
	public static BufferedImage getShip(String name)
	{
		for(Resource r: ships)
		{
			if(r.name==name)
				return (BufferedImage)r.data;
		}
		return null;
	}
	public static BufferedImage getDecoration(String name)
	{
		for(Resource r: decos)
		{
			if(r.name==name)
				return (BufferedImage)r.data;
		}
		return null;
	}
}
