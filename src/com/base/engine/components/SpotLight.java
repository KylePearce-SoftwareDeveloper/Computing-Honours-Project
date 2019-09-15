package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class SpotLight extends PointLight
{
	private float cutOff;
	
	
	public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutOff) 
	{
		super(color, intensity, attenuation);
		this.cutOff = cutOff;
		
		setShader(new Shader("forward-spot"));
	}

	public Vector3f getDirection() {
		return getTransform().getTransformedRot().getForward();
	}
	
	public float getCutOff() {
		return cutOff;
	}
	
	public void setCutOff(float cutOff) {
		this.cutOff = cutOff;
	}
}
