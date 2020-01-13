package com.base.engine.components;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.base.engine.core.Transform;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;
	
	private float scaleXAttrib;
	private float scaleYAttrib;
	private float scaleZAttrib;
	
	private static final float SIZE = 800;//12/1/20 - terraine test
	//private static final int VERTEX_COUNT = 128;//12/1/20 - terraine test
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	
	private float[][] heights;
	
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
	
	public MeshRenderer(Material material, String heightMap) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(MeshRenderer.class.getResourceAsStream("/textures/"+heightMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		
		Vertex[] verticesCube = new Vertex[count];
		
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;  //0
				
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				
				vertices[vertexPointer*3+1] = height;									    //1
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;//2
				normals[vertexPointer*3] = 0;											//0
				normals[vertexPointer*3+1] = 1;											//1
				normals[vertexPointer*3+2] = 0;											//2
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);    //0
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);  //1
				
				
				verticesCube[vertexPointer]   =   new Vertex( new Vector3f(vertices[vertexPointer*3], vertices[vertexPointer*3+1], vertices[vertexPointer*3+2]), new Vector2f(textureCoords[vertexPointer*2], textureCoords[vertexPointer*2+1]));
			    		
			    vertexPointer++;		
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		this.mesh = new Mesh(verticesCube, indices, true);
		this.material = material;
	}
	
	private float getHeight(int x, int y, BufferedImage image) {
		if(x<0 || x>=image.getHeight() || y<0 || y>=image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	public float getHeightOfTerraine(float worldX, float worldZ) {
		float terrainX = worldX - this.getParent().getTransform().getPos().getX();
		float terrainZ = worldZ - this.getParent().getTransform().getPos().getZ();
		float gridSquareSize = SIZE / ((float)heights.length -1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if(gridX >= heights.length - 1 || gridZ >= heights.length -1 || gridX < 0 || gridZ < 0) {
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
		float answer;
		if(xCoord <= (1 - zCoord)) {
			answer = barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), 
								  new Vector3f(1,heights[gridX + 1][gridZ], 0), 
								  new Vector3f(0,heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}else {
			answer = barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), 
								  new Vector3f(1,heights[gridX + 1][gridZ + 1], 1), 
								  new Vector3f(0,heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}
	
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.getZ() - p3.getZ()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getZ() - p3.getZ());
		float l1 = ((p2.getZ() - p3.getZ()) * (pos.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (pos.getY() - p3.getZ())) / det;
		float l2 = ((p3.getZ() - p1.getZ()) * (pos.getX() - p3.getX()) + (p1.getX() - p3.getX()) * (pos.getY() - p3.getZ())) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.getY() + l2 * p2.getY() + l3 * p3.getY();
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
