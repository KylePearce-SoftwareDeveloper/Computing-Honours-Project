package com.base.game;

import java.util.ArrayList;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;



public class TestGame extends Game
{
	private ArrayList<MeshRenderer> meshRendererObjects = new ArrayList<MeshRenderer>();
	private ArrayList<FreeMove> freeMoveObjects = new ArrayList<FreeMove>();
	private ArrayList<SpotLight> spotLightObjects = new ArrayList<SpotLight>();
	
	String collectableFilePath = "powerup.wav";
	String backgroundFilePath = "Soft-piano-music.wav";
    
    public void init()
    {
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        
        //MATERIALS
        Material materialCube = new Material(); 
        materialCube.addTexture("diffuse", new Texture("bricks.jpg"));
        materialCube.addFloat("specularIntensity", 1);
        materialCube.addFloat("specularPower", 8);
        Material materialTest = new Material();
        materialTest.addTexture("diffuse", new Texture("white.jpg"));
        materialTest.addFloat("specularIntensity", 1);
        materialTest.addFloat("specularPower", 8);
        
        //MESHES
        //Mesh testMonkeyMesh = new Mesh("newMonkey.obj");
        
        //MESH_RENDERERS
        MeshRenderer wallRight = new MeshRenderer(null, materialCube, 0.25f, 3.0f, 10.0f);
        MeshRenderer wallLeft = new MeshRenderer(null, materialCube, 0.25f, 3.0f, 10.0f);
        MeshRenderer wallBottom = new MeshRenderer(null, materialCube, 10.0f, 0.25f, 10.0f);
        MeshRenderer wallTop = new MeshRenderer(null, materialCube, 10.0f, 0.25f, 10.0f);
        MeshRenderer wallFront = new MeshRenderer(null, materialCube, 10.0f, 3.0f, 0.25f);
        MeshRenderer wallBack = new MeshRenderer(null, materialCube, 10.0f, 3.0f, 0.25f);
        meshRendererObjects.add(wallRight);
        meshRendererObjects.add(wallLeft);
        meshRendererObjects.add(wallBottom);
        meshRendererObjects.add(wallTop);
        meshRendererObjects.add(wallFront);
        meshRendererObjects.add(wallBack);
         
        //GAME_OBJECTS
        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0,0,1), 0.4f);
        directionalLightObject.addComponent(directionalLight);
        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));
                  
        GameObject pointLightObject = new GameObject();
        pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 1.0f, new Attenuation(0,0,1)));
        pointLightObject.getTransform().getPos().set(0, 3, 0);
         
        GameObject CollisionBoxTest = new GameObject().addComponent(wallRight);
        CollisionBoxTest.getTransform().getPos().set(10, 3, 0);
         
        GameObject CollisionBoxTest2 = new GameObject().addComponent(wallLeft);
        CollisionBoxTest2.getTransform().getPos().set(-10, 3, 0);
         
        GameObject CollisionBoxTest3 = new GameObject().addComponent(wallBottom);
        CollisionBoxTest3.getTransform().getPos().set(0, 0, 0);
        
        GameObject CollisionBoxTest4 = new GameObject().addComponent(wallTop);
        CollisionBoxTest4.getTransform().getPos().set(0, 6, 0);
        
        GameObject CollisionBoxTest5 = new GameObject().addComponent(wallFront);
        CollisionBoxTest5.getTransform().getPos().set(0, 3, 10);
        
        GameObject CollisionBoxTest6 = new GameObject().addComponent(wallBack);
        CollisionBoxTest6.getTransform().getPos().set(0, 3, -10);
         
        //GameObject testMesh5 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(testMonkeyMesh, materialTest, 0.0f, 0.0f, 0.0f));// 11/7/19 test
        //testMesh5.getTransform().getPos().set(0, 3, 0);
        //testMesh5.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));
         
        GameObject player = new GameObject();
        FreeMove playerMovement = new FreeMove(10.0f);
        freeMoveObjects.add(playerMovement);
        player.getTransform().getPos().set(5, 3, 0);
        player.addComponent(new FreeLook(0.5f));
        player.addComponent(playerMovement);
        player.addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f));
        
        GameObject spotLightObject = new GameObject();
        SpotLight spotLight =  new SpotLight(new Vector3f(0,1,1), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
        spotLightObjects.add(spotLight);
        spotLightObject.addComponent(spotLight);
        spotLightObject.getTransform().getPos().set(5,5,0);//13/11/19
         
        addObject(directionalLightObject);
        addObject(pointLightObject);
        addObject(spotLightObject);
        addObject(CollisionBoxTest);
        addObject(CollisionBoxTest2);
        addObject(CollisionBoxTest3);
        addObject(CollisionBoxTest4);
        addObject(CollisionBoxTest5);
        addObject(CollisionBoxTest6);
        //addObject(testMesh5);
        addObject(player);
        
        playBackgroundMusic(backgroundFilePath, 0.5f);
    }
    
    
    public void checkCollision() {
    	for(MeshRenderer currentMeshRenderer: meshRendererObjects) {
    		Vector3f testMeshPos = currentMeshRenderer.getParent().getTransform().getPos();
    		Vector3f testMeshScale = currentMeshRenderer.getScaleAttrib();//new Vector3f(2.0f, 2.0f, 2.0f);
    		Vector3f testPlayerPos = freeMoveObjects.get(0).getParent().getTransform().getPos();
    		Vector3f testPlayerScale = new Vector3f(1.0f, 1.0f, 1.0f);
    	
    		Vector3f tmp = new Vector3f(testPlayerPos.getX(), testPlayerPos.getY() - 1.0f, testPlayerPos.getZ());
    		//check the X axis
    		if (Math.abs(tmp.getX() - testMeshPos.getX()) < testPlayerScale.getX() + (testMeshScale.getX()) / 1.0) {
    			//check the Y axis
    			if (Math.abs(tmp.getY() - testMeshPos.getY()) < testPlayerScale.getY() + (testMeshScale.getY()) / 1.0) {
    				//check the Z axis
    				if (Math.abs(tmp.getZ() - testMeshPos.getZ()) < testPlayerScale.getZ() + (testMeshScale.getZ()) / 1.0) {
    					freeMoveObjects.get(0).getParent().getTransform().setPos(freeMoveObjects.get(0).getOldPos());
    				}
    			}
    		}
    	}
	}
    
    public void updateSpotLight()//13/11/19
    {
    	spotLightObjects.get(0).getParent().getTransform().setPos(freeMoveObjects.get(0).getNewPos());
    	spotLightObjects.get(0).getParent().getTransform().setRot(freeMoveObjects.get(0).getTransform().getRot());
    }
    
    public static void playCollectableMusic(String filePath, float volume)
    {
    	try
    	{
    		InputStream in = TestGame.class.getResourceAsStream("/music/"+filePath);//the next 5 lines including this are 6/1/20 test
    		BufferedReader musicReader = new BufferedReader(new InputStreamReader(in));
    		//File musicPath = new File(filePath);
    		String line= musicReader.readLine();
    		if(line != null)
    		{
    			AudioInputStream audioinput = AudioSystem.getAudioInputStream(TestGame.class.getResource("/music/"+filePath));//musicPath);
    			Clip clip = AudioSystem.getClip();
    			clip.open(audioinput);
    			if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
    				// set volume
    				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    				float range = gainControl.getMaximum() - gainControl.getMinimum();
    				float gain = (range * volume) + gainControl.getMinimum();
    				gainControl.setValue(gain);
    			}
    			clip.start();
    		}
    		else
    		{
    			System.out.println("cant find path");
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public static void playBackgroundMusic(String filePath, float volume)
    {
    	try
    	{
    		InputStream in = TestGame.class.getResourceAsStream("/music/"+filePath);//the next 5 lines including this are 6/1/20 test
    		BufferedReader musicReader = new BufferedReader(new InputStreamReader(in));
    		//File musicPath = new File(filePath);
    		String line= musicReader.readLine();
    		if(line != null)
    		{
    			AudioInputStream audioinput = AudioSystem.getAudioInputStream(TestGame.class.getResource("/music/"+filePath));
    			Clip clip = AudioSystem.getClip();
    			clip.open(audioinput);
    			if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
    				// set volume
    				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    				float range = gainControl.getMaximum() - gainControl.getMinimum();
    				float gain = (range * volume) + gainControl.getMinimum();
    				gainControl.setValue(gain);
    			}
    			clip.start();
    			clip.loop(clip.LOOP_CONTINUOUSLY);
    		}
    		else
    		{
    			System.out.println("cant find path");
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
