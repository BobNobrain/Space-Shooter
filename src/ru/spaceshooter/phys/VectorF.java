package ru.spaceshooter.phys;

public class VectorF
{	
	public static final float 	UP		=(float)Math.PI*3/2,
								DOWN	=(float)Math.PI/2,
								LEFT	=(float)Math.PI,
								RIGHT	=0F;
	
	public static final VectorF ex=new VectorF(1F, 0F),
								ey=new VectorF(0F, 1F),
								O=new VectorF(0F, 0F);
	
	public float x, y;
	
	//
	// CONSTRUCTORS
	//
	
	public VectorF(float x, float y)
	{ this.x=x; this.y=y; }
	
	public VectorF(VectorF direction, float magnitude)
	{
		VectorF v=direction.copy();
		v.setMagnitude(magnitude);
		this.x=v.x; this.y=v.y;
	}
	public VectorF(float dx, float dy, float mag)
	{
		x=dx; y=dy;
		if(!(dx==dy && dy==0)) setMagnitude(mag);
	}
	
	
	//
	// POLAR
	//
	
	public float getMagnitude()
	{
		return (float)Math.sqrt(x*x+y*y);
	}
	public float getDirection()
	{
		if(y==0 && x==0) return Float.NaN;
		if(y==0) return x>0? RIGHT : LEFT;
		if(x==0) return y>0? DOWN : UP;
		return (float)Math.atan2(y, x);
	}
	
	public void setMagnitude(float mag)
	{
		multipleBy(mag/getMagnitude());
	}
	public void rotate(float newAngle)
	{
		float mag=getMagnitude();
		x=(float)Math.cos(newAngle)*mag;
		y=(float)Math.sin(newAngle)*mag;
	}
	
	
	//
	// OPERATIONS
	//
	
	public void multipleBy(float coeff)
	{
		x*=coeff; y*=coeff;
	}
	public void divideBy(float coeff)
	{
		x/=coeff; y/=coeff;
	}
	public void add(VectorF v)
	{
		x+=v.x; y+=v.y;
	}
	public void sub(VectorF v)
	{
		x-=v.x; y-=v.y;
	}
	
	
	//
	// OTHER
	//
	
	public VectorF copy()
	{ return new VectorF(x, y); }
	
	public VectorF getUnitary()
	{
		VectorF v=copy();
		v.setMagnitude(1);
		/* or
		float a=v.getDirection();
		v.x=Math.cos(a);
		v.y=Math.sin(a);
		*/
		return v;
	}
}
