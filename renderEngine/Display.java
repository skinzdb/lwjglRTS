package renderEngine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Display {
	
	private long win;
	
	private int width, height;
	private int x, y;
	
	private String title;
	
	private boolean fullscreen;
	
	// *** GETTERS ***
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(win);
	}
	
	public boolean fullscreen() {
		return fullscreen;
	}
	
	// *** SETTERS ***
	
	// SHOWS MORE DESCRIPTIVE ERRORS
	public static void setCallbacks() {
		GLFWErrorCallback.createPrint(System.err).set();
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void centre() {
		GLFWVidMode vid = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		setPosition((vid.width() - width) / 2,
				((vid.height() - height) / 2));
	}
	
	// SETS WINDOWS HINTS, "value" CAN BE 1 OR 0 WHICH IS TRUE AND FALSE RESPECTIVELY
	// HAS TO BE DONE BEFORE WINDOW IS CREATED!!
	public void setHint(int hint, int value) {
		GLFW.glfwWindowHint(hint, value);
	}
	
	// *** MAIN FUNCTIONALITY ***
	
	// CREATE DISPLAY OBJECT, DEFAULT SIZE: 640x480
	
	public Display() {
		if(!GLFW.glfwInit()) {
			System.err.println("Failed to initialize GLFW!");
			System.exit(1);
		}
		
		setSize(640, 480);
		setTitle("Game");
	}
	
	// DISPLAY WINDOW
	
	public void init() {
		win = GLFW.glfwCreateWindow(width, height, title, fullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		
		if(win == 0) {
			throw new IllegalStateException("Failed to create a window!");
		}
		
		if(!fullscreen) {
			GLFW.glfwSetWindowPos(win, x, y);
		}
		
		GLFW.glfwShowWindow(win);
		GLFW.glfwMakeContextCurrent(win);
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(win);
	}
}
