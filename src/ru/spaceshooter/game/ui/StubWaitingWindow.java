package ru.spaceshooter.game.ui;

public class StubWaitingWindow extends WaitingMessageWindow
{

	public StubWaitingWindow()
	{
		super("����������, ���������, ���� �", "��������� �����-�� ������ ��� �������� �����");
		setPercentage(1);
	}	
	
	void increment()
	{
		if(progress<1F) setPercentage(getPercentage()+1);
	}
	
	@Override
	public void update()
	{
		super.update();
		System.out.println(progress+" "+getPercentage());
		if(!isActive()) return;
		increment();
		if(progress==1F)
		{
			forceClose();
		}
	}
}
