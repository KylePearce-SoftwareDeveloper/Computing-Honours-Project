package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent
{
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	
	private Vector3f oldPos;//12/11/19 - collision detection test
	private Vector3f newPos = new Vector3f(0,0,0);//13/11/19 - spotlight stuck to my head functionality
	
	public FreeMove(float speed)
	{
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	
	@Override
	public void input(float delta) 
	{
		this.oldPos = getTransform().getPos();
		float movAmt = speed * delta;
		Vector3f movementVector = new Vector3f(0,0,0);//13/11/19
		
		if(Input.GetKey(forwardKey))
			movementVector = movementVector.add(getTransform().getRot().getForward());//move(getTransform().getRot().getForward(), movAmt);
		if(Input.GetKey(backKey))
			movementVector = movementVector.sub(getTransform().getRot().getForward());//move(getTransform().getRot().getForward(), -movAmt);
		if(Input.GetKey(leftKey))
			movementVector = movementVector.add(getTransform().getRot().getLeft());//move(getTransform().getRot().getLeft(), movAmt);
		if(Input.GetKey(rightKey))
			movementVector = movementVector.add(getTransform().getRot().getRight());//move(getTransform().getRot().getRight(), movAmt);
		
		movementVector.setY(0);
		if(movementVector.length() > 0)
			movementVector = movementVector.normalized();
		
		move(movementVector, movAmt);
	}
		
	public void move(Vector3f dir, float amt) 
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
		this.newPos = getTransform().getPos();//13/11/19
	}
	
	//12/11/19 - collision detection test
	@Override 
	public void update(float delta)
	{
		getParent().getEngine().getGame().checkCollision();
		getParent().getEngine().getGame().updateSpotLight();//13/11/19
	}
	
	//12/11/19 - collision detection test
	public Vector3f getOldPos()
	{
		return oldPos;
	}
	
	public Vector3f getNewPos()//13/11/19
	{
		return newPos;
	}
}
