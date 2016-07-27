package ru.spaceshooter.utils;

import ru.spaceshooter.game.TestEnemy1;

public class TestEnemy1Factory implements Factory<TestEnemy1>
{
	public TestEnemy1Factory(){}
	
	@Override
	public TestEnemy1 getNewInstance()
	{
		return new TestEnemy1();
	}
}
