/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.engine.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

/**
 *
 * @author kylep
 */
public class CoreEngine 
{
    private boolean isRunning;
    private Game game;
    private RenderingEngine renderingEngine;
    private int width;
    private int height;
    private double frameTime;
    
    
    public CoreEngine(int width, int height, double framerate, Game game)
    {
    	this.isRunning = false;
        this.game = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0/framerate;    
        game.setEngine(this);
    }
    
    public void createWindow(String title)
    {
    	Window.createWindow(width, height, title);
    	this.renderingEngine = new RenderingEngine();
    }
    
    public void start()
    {
        if(isRunning)
            return;
        
        run();
    }
    
    public void stop()
    {
        if(!isRunning)
            return;
        
        isRunning = false;
    }
    
    private void run()
    {
        isRunning = true;
        
        int frames = 0;
        long frameCounter = 0;
        
        game.init();//16/6/19 - test. TestGame init() is hit once. 10/7/19 - "game" in this class is referring to the "new TestGame()" that was passed into this constructor
        
        double lastTime = Time.getTime();
        double unprocessedTime = 0;
        
        
        while(isRunning)
        {
            boolean render = false;
            
            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime=startTime;
            
            unprocessedTime += passedTime;
            frameCounter += passedTime;
            
            while(unprocessedTime > frameTime)
            {
                render=true;
                
                unprocessedTime -= frameTime;
                
                if(Window.isCloseRequested())
                    stop();
                
                
                game.input((float)frameTime);
                Input.Update();
                
                game.update((float)frameTime);
                
                if(frameCounter>= 1.0)
                {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if(render)
            {
            	game.render(renderingEngine);
            	Window.render();
                frames++;
            }
            else
            {
                try 
                {
                    Thread.sleep(1);
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(CoreEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        cleanUp();
    }
    
    
    private void cleanUp()
    {
        Window.dispose();
    }

	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	
	//12/11/19 - collision detection test
	public Game getGame()
	{
		return game;
	}
}
