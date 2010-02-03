package com.stickycoding.Rokon.Backgrounds;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.GLHelper;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureBuffer;
import com.stickycoding.Rokon.VertexBuffer;
import com.stickycoding.Rokon.OpenGL.RokonRenderer;


/**
 * A very basic, static textured background image
 * 
 * @author Richard
 */
public class FixedBackground extends Background {
	
	public TextureBuffer _buffer;
	public VertexBuffer _vb;
	
	public FixedBackground(Texture texture) {
		_buffer = new TextureBuffer(texture, TextureBuffer.NORMAL);
		_vb = new VertexBuffer(0, 0, Rokon.fixedWidth, Rokon.fixedHeight);
		_buffer.update();
	}
	
	public void drawFrame(GL10 gl) {
		GLHelper.selectTexture(gl, _buffer.getTexture());
		GLHelper.glColor4f(gl, 1, 1, 1, 1);
		gl.glLoadIdentity();
		GLHelper.vertexPointer(gl, _vb.get());
		GLHelper.texCoordPointer(gl, _buffer.getByteBuffer());
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
