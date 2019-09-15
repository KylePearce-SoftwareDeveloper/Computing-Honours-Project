package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;


public class TestGame extends Game
{
    
    public void init()
    {
    	System.out.println("Init() in TestGame called");//16/6/19 - test
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        
        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};//big square

        int indices[] = { 0, 1, 2,
        				  2, 1, 3};//big square
        
        Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, -fieldDepth/ 10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, fieldDepth/ 10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, -fieldDepth/ 10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, fieldDepth/ 10 * 3), new Vector2f(1.0f, 1.0f))};//little square

        int indices2[] = { 0, 1, 2,
        				  2, 1, 3};//little square
        
         
         Mesh mesh = new Mesh(vertices, indices, true);//***---___CREATING MESH - big plane___---***  
         Mesh mesh2 = new Mesh(vertices2, indices2, true);//***---___CREATING MESH - two smaller planes___---***
         Mesh tempMesh = new Mesh("monkey3.obj");//***---___CREATING MESH - Monkey Model___---***
         
         Mesh testBoxMesh = new Mesh("newBox.obj");// 13/7/19 test
         Mesh testMonkeyMesh = new Mesh("newMonkey.obj");// 13/7/19 test
         
         Material materialTest = new Material();// 13/7/19 test 
         materialTest.addTexture("diffuse", new Texture("white.jpg"));
         materialTest.addFloat("specularIntensity", 1);
         materialTest.addFloat("specularPower", 8);
         
         Material material = new Material();//***---___CREATING MATERIAL - bricks___---*** 
         material.addTexture("diffuse", new Texture("bricks.jpg"));
         material.addFloat("specularIntensity", 1);
         material.addFloat("specularPower", 8);
         
         Material material2 = new Material();//***---___CREATING MATERIAL - checkard___---*** 
         material2.addTexture("diffuse", new Texture("test.png"));
         material2.addFloat("specularIntensity", 1);
         material2.addFloat("specularPower", 8);
         
         
         
         MeshRenderer meshRenderer = new MeshRenderer(mesh, material);//***---___CREATING MESH RENDERER OBJECT - big plane mesh and bricks material___---***
         
         GameObject planeObject = new GameObject();//***---___CREATING GAME OBJECT - big plane___---***
         planeObject.addComponent(meshRenderer);//***---___rendering planeObject with meshRenderer___---***
         planeObject.getTransform().getPos().set(0, -1, 5);//***---___positioning big plane___---***
         
         GameObject directionalLightObject = new GameObject();//***---___CREATING GAME OBJECT - directional light___---***
         DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0,0,1), 0.4f);//***---___CREATING Directional light___---***
         directionalLightObject.addComponent(directionalLight);//***---___rendering directionalLightObject with directionalLight___---***
                  
         GameObject pointLightObject = new GameObject();//***---___CREATING GAME OBJECT - point light___---***
         pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Attenuation(0,0,1)));
         
         
         GameObject spotLightObject = new GameObject();//***---___CREATING GAME OBJECT - spot light___---***
         SpotLight spotLight =  new SpotLight(new Vector3f(0,1,1), 0.4f, 
          		new Attenuation(0,0,0.1f), 0.7f);
         spotLightObject.addComponent(spotLight);
         
         spotLightObject.getTransform().getPos().set(5, 0, 5);//setting position of spot light
         spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(90.0f)));//setting rotation of spot light
         
         addObject(planeObject);// add big plane to scene
         addObject(directionalLightObject);// add directional light to scene
         addObject(pointLightObject);// add point light to scene
         addObject(spotLightObject);// add spot light to scene
         
         //Up to here, mesh for Monkey (tempMesh) has been loaded but nothing else
         
         GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));//game object smaller plane 1
         GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));//game object smaller plane 2
         GameObject testMesh3 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(tempMesh, material));//game object monkey 1
         
         //-----------------------------------------------------------------------------------------------------------------------------------------
         GameObject testMesh4 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(testBoxMesh, material));// 11/7/19 test
         testMesh4.getTransform().getPos().set(0, 5, 0);// 11/7/19 test
         testMesh4.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));// 11/7/19 test
         addObject(testMesh4);// 11/7/19 test
         //-----------------------------------------------------------------------------------------------------------------------------------------
         
         //-----------------------------------------------------------------------------------------------------------------------------------------
         GameObject testMesh5 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(testMonkeyMesh, materialTest));// 11/7/19 test
         testMesh5.getTransform().getPos().set(5, 5, 10);// 11/7/19 test
         testMesh5.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));// 11/7/19 test
         addObject(testMesh5);// 11/7/19 test
         //-----------------------------------------------------------------------------------------------------------------------------------------
         
         testMesh1.getTransform().getPos().set(0, 2, 0);//set position of smaller plane 1
         testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));//set rotation of smaller plane 1
         
         testMesh2.getTransform().getPos().set(0, 0, 5);//set position of smaller plane 2

         testMesh1.addChild(testMesh2);//smaller plane 2 is now child of smaller plane 1
         testMesh2.addChild(new GameObject().addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10.0f)).addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));//the camera is now child of smaller plane 2        
         
         testMesh3.getTransform().getPos().set(5,5,5);//moving this code that positions at rotates monkey 1 above where it is added to scene
         testMesh3.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(-70.0f)));
         System.out.println("testMesh3 position touched in TestGame");//16/6/19 - test
         
         addObject(testMesh1);//add smaller plane 1 to scene, that also has smaller plane 2 as its child, which has camera as its child
         addObject(testMesh3);//add monkey 1 to scene
         
         addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));//add monkey 2 to scene
         
         directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));
    }
}
