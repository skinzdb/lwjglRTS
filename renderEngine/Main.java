package renderEngine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {
	
	private static Display display;
	
	// USED FOR SYNC FUNCTION
	// private static long variableYieldTime, lastTime;
	
	public static void main(String[] args) {
		Display.setCallbacks();
		
		display = new Display();
		display.setSize(640, 480);
		display.centre();
		display.init();
		
		update();
	}
	
	public static void update() {									
		
		// *** INITIAL SETUP ***
		
		GL.createCapabilities();
		GL11.glClearColor(0, 0, 0, 0);
		
	    GLFW.glfwSwapInterval(0);	// Disable VSync = 0, Enable VSync = 1
		
		float vertices[] = {
				-0.5f,  0.5f, 1, 	// Vertex 1 (X, Y)	0
				0.5f,  0.5f, 1, 	// Vertex 2 (X, Y)	1
				0.5f,  -0.5f, 1, 	// Vertex 3 (X, Y)	2
				-0.5f,  -0.5f, 1, 	// Vertex 4 (X, Y)	3
		};
		float texture_coords[] = {
			    0,0,
			    1,0,
			    1,1,
			    0,1,
		};
		int indices[] = {
				0,1,2,
				2,3,0
		};
		
		Camera camera = new Camera(display.width(), display.height());
		Model model = new Model(vertices, indices, texture_coords);
		
		Shader shader = new Shader("shader");
		
		Texture tex = new Texture("./textures/test2.png");

	    Matrix4f scale = new Matrix4f().scale(new Vector3f(128,128,1));
	    Matrix4f target = new Matrix4f();
	    
	    camera.setPosition(new Vector3f(0,0,0));
	    
	    // FPS VARS
	    
	    double frame_cap = 1.0/60.0;	// 60 FPS CAP
	    
	    double frame_time = 0;		
	    double frames = 0;			    // Used for showing FPS
	    
	    double time = Timer.getTime();
	    double unprocessed = 0;
	    
	    // *** MAINLOOP ***
	    
	    while (!display.shouldClose()) {	
	    	
	    	// FPS LIMITER
	    	
	    	boolean can_render = false;	
	    	
	    	double time_2 = Timer.getTime();
	    	double passed = time_2 - time;
	    	unprocessed += passed;
	    	frame_time += passed;
	    	
	    	time = time_2;
	    	
	    	while(unprocessed >= frame_cap) {
	    		// ACCOUNT FOR UNPROCESSED TIME
	    		
	    		unprocessed -= frame_cap;
	    		can_render = true;
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
				
		    	// NON-RENDERING STUFF HERE
				
				target = scale;
				GLFW.glfwPollEvents();
	    	}
	    	
	    	if(!can_render) continue;
	    	
	    	// RENDERING
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			
			shader.bind();
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", camera.getProjection().mul(target));
			model.render();
			tex.bind(0);

			display.swapBuffers();
			
			frames++;
	    }
	    
	    // *** END ***
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

// Best way for capping frames... not using this yet
//
//	private static void sync(int fps) {
//        if (fps <= 0) return;
//        
//        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
//
//        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
//        long overSleep = 0; // time the sync goes over by
//          
//        try {
//            while (true) {
//                long t = System.nanoTime() - lastTime;
//                  
//                if (t < sleepTime - yieldTime) {
//                    Thread.sleep(1);
//                }else if (t < sleepTime) {
//                    // burn the last few CPU cycles to ensure accuracy
//                    Thread.yield();
//                }else {
//                    overSleep = t - sleepTime;
//                    break; // exit while loop
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally{
//            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
//             
//            // auto tune the time sync should yield
//            if (overSleep > variableYieldTime) {
//                // increase by 200 microseconds (1/5 a ms)
//                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
//            }
//            else if (overSleep < variableYieldTime - 200*1000) {
//                // decrease by 2 microseconds
//                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
//            }
//        }
//    }
}
