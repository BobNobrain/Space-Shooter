package ru.spaceshooter.main;

public class UpdatingThread extends Thread
{	
	boolean started;
	long lastStart;
	final int interval=33;
	
	public UpdatingThread(String name)
	{
		super(name);
		started=false;
		lastStart=-1;
		setDaemon(true);
	}
	
	@Override
	public void start()
	{
		super.start();
		started=true;
	}
	
	@Override
	public void run()
	{
		while(started)
		{	
			long cur=System.currentTimeMillis();
			update();
			//System.out.print(System.currentTimeMillis()-cur+" ");
			
			try
			{
				int dur=(int)(System.currentTimeMillis()-cur);
				if(dur<interval)
				{
					sleep(interval-dur);
				}
				//System.out.println(dur+" nlastst:"+lastStart);
			}
			catch(InterruptedException ex)
			{
				System.out.print(this.getName()+" interrupted!");
				break;
			}
		}
	}
	
	protected void update(){}
}