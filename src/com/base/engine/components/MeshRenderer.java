package com.base.engine.components;

import com.base.engine.core.Transform;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.Vertex;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;
	
	private float scaleXAttrib;
	private float scaleYAttrib;
	private float scaleZAttrib;
	
	public MeshRenderer(Mesh mesh, Material material, float scaleX, float scaleY, float scaleZ)
	{
		if(mesh == null)
		{
		//System.out.println("Mesh is null");	
		Vertex[] verticesCube = new Vertex[] { 	new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),
												new Vertex( new Vector3f( 1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),
												new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 1.0f)),

												new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),//cube
												new Vertex( new Vector3f(-1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 1.0f)),

												new Vertex( new Vector3f(1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),
												new Vertex( new Vector3f( 1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 1.0f)),

												new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),
												new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),//cube
												new Vertex( new Vector3f(-1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),
												new Vertex( new Vector3f(-1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 1.0f)),

												new Vertex( new Vector3f(1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),
												new Vertex( new Vector3f(-1.0f * scaleX, 1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),
												new Vertex( new Vector3f(-1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 1.0f)),

												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(0.0f, 0.0f)),//cube
												new Vertex( new Vector3f(-1.0f * scaleX, -1.0f * scaleY, -1.0f * scaleZ), new Vector2f(1.0f, 0.0f)),
												new Vertex( new Vector3f( -1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(1.0f, 1.0f)),
												new Vertex( new Vector3f(1.0f * scaleX, -1.0f * scaleY, 1.0f * scaleZ), new Vector2f(0.0f, 1.0f))};//cube

		int indicesCube[] = { 0, 1, 2, 0, 2, 3, //front
							  4, 5, 6, 4, 6, 7, //right
							  8, 9, 10, 8, 10, 11, //back
							  12, 13, 14, 12, 14, 15, //left
							  16, 17, 18, 16, 18, 19, //upper
							  20, 22, 21, 20, 23, 22}; //bottom
		
		this.scaleXAttrib = scaleX;
		this.scaleYAttrib = scaleY;
		this.scaleZAttrib = scaleZ;
		//Mesh meshCube = new Mesh(verticesCube, indicesCube, true);
		this.mesh = new Mesh(verticesCube, indicesCube, true);
		}
		else 
		{
		this.mesh =mesh;
		}
		
		this.material = material;
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) 
	{
		shader.bind();
    	shader.updateUniforms(getTransform(), material, renderingEngine);
        mesh.draw();
	}
	
	public Vector3f getScaleAttrib()
	{
		return new Vector3f(this.scaleXAttrib, this.scaleYAttrib, this.scaleZAttrib);
	}
}
