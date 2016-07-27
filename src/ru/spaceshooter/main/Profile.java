package ru.spaceshooter.main;

import ru.spaceshooter.game.PlayerSpaceship;
import ru.spaceshooter.game.TestSpaceship;

public class Profile
{	
	private int level;
	public int getCurrentLevel() { return level; }
	public void levelUp() { ++level; }
	
	private PlayerSpaceship ship;
	public PlayerSpaceship getShip() { return ship; }
	public void changeSpaceship(PlayerSpaceship newOne) { ship=newOne; }
	
	private String name;
	public String getName() { return name; }
	
	private static Profile cur;
	public static Profile current() { return cur; }
	public static void setCurrentProfile(String name)
	{
		// TODO: searching for profile with this name, then cur=item;
		// DEBUG: that's a demo version, with single profile
		cur=new Profile(name);
	}
	
	
	public Profile(String name)
	{
		level=0;
		ship=new TestSpaceship();
		this.name=name;
	}
	
	
	public static Profile load(String name)
	{
		// TODO: loading player progress
		return null;
	}
	
	public void save()
	{
		// TODO: saving player progress
	}
}
