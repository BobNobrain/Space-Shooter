package ru.spaceshooter.phys;

public class PhysObject
{	
	protected VectorF r, v, a, F;
	protected float vmax;
	float m;
	float friction;
	boolean manualAcc;
	public boolean getManualAccelerationMode() { return manualAcc; }
	public void setManualAccelerationMode(boolean val) { manualAcc=val; }
	
	public VectorF getLocation() { return r.copy(); }
	public VectorF getVelocity() { return v.copy(); }
	public VectorF getAccel() { return a.copy(); }
	
	public void setLocation(VectorF val) { r=val; }
	public void setVelocity(VectorF val) { v=val; }
	public void setAccel(VectorF val) { a=val; }
	
	public VectorF location() { return r; }
	public VectorF velocity() { return v; }
	public VectorF accel() { return a; }
	
	public float getMaxVelocity() { return vmax; }
	public float getMass() { return m; }
	public float getFriction() { return friction; }
	
	public void setMaxVelocity(float val) { vmax=val; }
	public void setMass(float val) { m=val; }
	public void setFriction( float val) { friction=val; }
	
	
	public PhysObject()
	{
		r=VectorF.O.copy();
		v=VectorF.O.copy();
		vmax=-1;
		a=VectorF.O.copy();
		F=VectorF.O.copy();
		m=0F; friction=1F;
		manualAcc=true;
	}
	
	
	public VectorF getForce() { return F.copy(); }
	public void addForce(VectorF force) { F.add(force); }
	public void resetForce() { F=VectorF.O.copy(); }
	
	public void updatePhys()
	{
		if(!manualAcc && m!=0)
		{
			a=F.copy();
			a.divideBy(m);
		}
		
		r.add(v);
		v.add(a);
		
		if(friction!=1F) v.multipleBy(friction);
		
		if(vmax>=0)
		{
			if(v.getMagnitude()>vmax)
			{
				v.setMagnitude(vmax);;
			}
		}
		
		// HACK: increase this value to reduce effect of 'jumping' pix by pix (due to int draw coords) 
		if(v.getMagnitude()<0.5F) v=VectorF.O.copy();
	}
}
