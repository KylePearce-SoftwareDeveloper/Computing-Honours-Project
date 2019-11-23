package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public abstract class GameComponent 
{
	private GameObject parent;
	
	public void input(float delta) {} 
	public void update(float delta) {}
	public void render(Shader shader, RenderingEngine renderingEngine) {}
	
	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}
	
	public Transform getTransform()
	{
		return parent.getTransform();
	}
	
	public GameObject getParent()//12/11/19
	{
		return parent;
	}
	
	public void addToEngine(CoreEngine engine){}
	
}
