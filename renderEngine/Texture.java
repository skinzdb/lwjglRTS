package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename) {
		BufferedImage bI;
		try {
			bI = ImageIO.read(new File(filename));
			width = bI.getWidth();
			height = bI.getHeight();
			
			int[] rawPixels = new int[width * height * 4];
			rawPixels = bI.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					int pixel = rawPixels[i*width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF));	//RED
					pixels.put((byte)((pixel >> 8) & 0xFF));	//GREEN
					pixels.put((byte)(pixel & 0xFF));			//BLUE
					pixels.put((byte)((pixel >> 24) & 0xFF));	//ALPHA
				}
			}
			pixels.flip();
			
			id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bind(int sampler) {
		if(sampler >= 0 && sampler <= 31) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		}
	}
}
