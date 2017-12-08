package renderEngine;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;

public class Display {
	
	public long win;
	
	final int WIDTH = 640;
	final int HEIGHT = 480;
	
	public Display() {
		if(!GLFW.glfwInit()) {
			System.err.println("Failed to initialize GLFW!");
			System.exit(1);
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		win = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Game", 0, 0);
		
		if(win == 0) {
			throw new IllegalStateException("Failed to create a window!");
		}
	
		GLFW.glfwShowWindow(win);
		GLFW.glfwMakeContextCurrent(win);
		
		update();
	}
	
	
	public void update() {
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
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
	
		GL11.glClearColor(0, 0, 0, 0);
		
		Model model = new Model(vertices, indices, texture_coords);
		Shader shader = new Shader("shader");
		
		Texture tex = new Texture("./textures/test2.png");

		Matrix4f projection = new Matrix4f()
				.ortho2D(-WIDTH/2, WIDTH/2, -HEIGHT/2, HEIGHT/2).scale(10);

		while (!GLFW.glfwWindowShouldClose(win) ) {			//MAINLOOP
			GLFW.glfwPollEvents();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			
			shader.bind();
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", projection);
			model.render();
			tex.bind(0);
			
			GLFW.glfwSwapBuffers(win);
		}
		

		GLFW.glfwTerminate();								//END :(
	}
}
