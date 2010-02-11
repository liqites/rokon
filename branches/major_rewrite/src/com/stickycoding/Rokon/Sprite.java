package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class Sprite extends Entity {
	
	private int _drawPriority;
	
	private boolean _hasTexture;
	private Texture _texture;
	
	private boolean _hasCustomTextureBuffer;
	private TextureBuffer _customTextureBuffer;
	
	private VBO _vertexVBO;
	private BufferObject _buffer;
	
	private BlendFunction _blendFunction;
	private boolean _hasCustomBlendFunction;
	
	private int _textureTile = 1;
	
	private float _red = 1, _green = 1, _blue = 1, _alpha = 1;
	
	public Sprite(int x, int y, int width, int height, Texture texture) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		_drawPriority = Rokon.getDrawPriority();
		if(texture != null)
			setTexture(texture);
		preparePointers();
	}
	
	public Sprite(int x, int y, int width, int height) {
		this(x, y, width, height, null);
	}
	
	public Sprite(int x, int y, Texture texture) {
		this(x, y, FP.fromInt(texture.getWidth()), FP.fromInt(texture.getHeight()));
	}
	
	private void preparePointers() {
		if(Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_NORMAL || Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_DRAWTEX_NORMAL) {
			_buffer = new BufferObject(true);
			_buffer.update(0, 0, getWidth(), getHeight());
		}
		if(Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_VBO || Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_DRAWTEX_VBO) {
			_buffer = new BufferObject(true);
			_buffer.update(0, 0, getWidth(), getHeight());
			_vertexVBO = new VBO(_buffer.getIntBuffer());
			VBOManager.add(_vertexVBO);
		}
	}
	
	public int getDrawPriority() {
		return _drawPriority;
	}
	
	public void setDrawPriority(int drawPriority) {
		_drawPriority = drawPriority;
	}
	
	public void setTexture(Texture texture) {
		_texture = texture;
		_hasTexture = (_texture != null);
	}
	
	public Texture getTexture() {
		return _texture;
	}
	
	public boolean hasTexture() {
		return _hasTexture;
	}
	
	protected void onDraw(GL10 gl) {
		if(Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_VBO) {
			onDrawVBO(gl);
			return;
		} 
		if(Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_DRAWTEX_VBO || Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_DRAWTEX_NORMAL) {
			if(getRotationAngle() != 0)
				if(Rokon.getDrawPriority() == Rokon.DRAW_PRIORITY_DRAWTEX_NORMAL)
					onDrawNormal(gl);
				else
					onDrawVBO(gl);
			else
				onDrawDrawTex(gl);
			return;
		}		
		onDrawNormal(gl);
		return;
	}
	
	private void onDrawVBO(GL10 gl) {
		GLHelper.color4f(_red, _green, _blue, _alpha);
		gl.glPushMatrix();		
		GLHelper.enableVertexArray();
		GLHelper.bindBuffer(_vertexVBO.getIndex());
		GLHelper.vertexPointer(GL10.GL_FIXED);
		gl.glTranslatex(getX(), getY(), 0);
		if(getRotationAngle() != 0) {
			gl.glPushMatrix();
			if(isRotateAboutCentre()) {
				gl.glTranslatex((int)(getWidth() / 2), (int)(getHeight() / 2), 0);
				gl.glRotatex(getRotationAngle(), 0, 0, 0x10000);
			} else {
				gl.glTranslatex(getRotationPivotX(), getRotationPivotY(), 0);
				gl.glRotatex(getRotationAngle(), 0, 0, 0x10000);
			}
			gl.glPopMatrix();
		}
		if(_hasTexture) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(_texture.getTextureAtlas().getTextureIndex());
			if(_hasCustomTextureBuffer)
				GLHelper.texCoordPointer(_customTextureBuffer.getBuffer(), GL10.GL_FLOAT);
			else
				GLHelper.texCoordPointer(_texture.getTextureBuffer().getBuffer(), GL10.GL_FLOAT);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}
		//gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		((GL11)gl).glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_FIXED, 0);
		gl.glPopMatrix();
	}
	
	private void onDrawDrawTex(GL10 gl) {
		
	}
	
	private void onDrawNormal(GL10 gl) {
		GLHelper.color4f(_red, _green, _blue, _alpha);
		if(_hasCustomBlendFunction)
			GLHelper.blendMode(_blendFunction);
		else
			GLHelper.blendMode(Rokon.getDefaultBlendFunction());
		gl.glPushMatrix();		
		GLHelper.enableVertexArray();
		GLHelper.vertexPointer(_buffer.get(), GL10.GL_FIXED);
		gl.glTranslatex(getX(), getY(), 0);
		if(getRotationAngle() != 0) {
			if(isRotateAboutCentre()) {
				if(isScaleFromCentre())
					gl.glTranslatex(FP.div(FP.mul(getWidth(), getScaleX()), FP.TWO) - FP.div(FP.mul(getWidth(), getScaleX() - FP.ONE), FP.TWO), FP.div(FP.mul(getHeight(), getScaleY()), FP.TWO) - FP.div(FP.mul(getHeight(), getScaleY() - FP.ONE), FP.TWO), 0);
				else
					gl.glTranslatex(FP.div(FP.mul(getWidth(), getScaleX()), FP.TWO), FP.div(FP.mul(getHeight(), getScaleY()), FP.TWO), 0);
				
				gl.glRotatex(getRotationAngle(), 0, 0, 0x10000);
				
				if(isScaleFromCentre())
					gl.glTranslatex(-FP.div(FP.mul(getWidth(), getScaleX()), FP.TWO) + FP.div(FP.mul(getWidth(), getScaleX() - FP.ONE), FP.TWO), -FP.div(FP.mul(getHeight(), getScaleY()), FP.TWO) + FP.div(FP.mul(getHeight(), getScaleY() - FP.ONE), FP.TWO), 0);
				else
					gl.glTranslatex(-FP.div(FP.mul(getWidth(), getScaleX()), FP.TWO), -FP.div(FP.mul(getHeight(), getScaleY()), FP.TWO), 0);
				
			} else 
			{
				if(isScaleFromCentre())
					gl.glTranslatex(getRotationPivotX() - FP.div(FP.mul(getWidth(), getScaleX() - FP.ONE), FP.TWO), getRotationPivotY() - FP.div(FP.mul(getHeight(), getScaleY() - FP.ONE), FP.TWO), 0);
				else
					gl.glTranslatex(getRotationPivotX(), getRotationPivotY(), 0);
				gl.glRotatex(getRotationAngle(), 0, 0, 0x10000);
				if(isScaleFromCentre())
					gl.glTranslatex(-getRotationPivotX() + FP.div(FP.mul(getWidth(), getScaleX() - FP.ONE), FP.TWO), -getRotationPivotY() + FP.div(FP.mul(getHeight(), getScaleY() - FP.ONE), FP.TWO), 0);
				else
					gl.glTranslatex(-getRotationPivotX(), -getRotationPivotY(), 0);
			}
		}
		if(getScaleX() != FP.ONE || getScaleY() != FP.ONE) {
			if(isScaleFromCentre())
				gl.glTranslatex(-FP.div(FP.mul(getWidth(), getScaleX() - FP.ONE), FP.TWO), -FP.div(FP.mul(getHeight(), getScaleY() - FP.ONE), FP.TWO), 0);
			gl.glScalex(getScaleX(), getScaleY(), 0);
		}
		if(_hasTexture) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(_texture.getTextureAtlas().getTextureIndex());
			if(_hasCustomTextureBuffer)
				GLHelper.texCoordPointer(_customTextureBuffer.getBuffer(), GL10.GL_FLOAT);
			else
				GLHelper.texCoordPointer(_texture.getTextureBuffer(_textureTile - 1).getBuffer(), GL10.GL_FLOAT);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
	}
	
	protected void onUpdate() {
		super.onUpdate();
		//TODO Handle animations
		
		if(requiresPositionUpdate()) {
			//TODO Update buffers
		}
	}

	public void setRed(float red) {
		_red = red;
	}

	public float getRed() {
		return _red;
	}

	public void setGreen(float green) {
		_green = green;
	}

	public float getGreen() {
		return _green;
	}

	public void setBlue(float blue) {
		_blue = blue;
	}

	public float getBlue() {
		return _blue;
	}

	public void setAlpha(float alpha) {
		_alpha = alpha;
	}

	public float getAlpha() {
		return _alpha;
	}
	
	public void setColor(float red, float green, float blue) {
		_red = red;
		_green = green;
		_blue = blue;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		_red = red;
		_green = green;
		_blue = blue;
		_alpha = alpha;
	}
	
	public void setCustomTextureBuffer(TextureBuffer textureBuffer) {
		_customTextureBuffer = textureBuffer;
		_hasCustomTextureBuffer = (textureBuffer != null);
	}
	
	public void removeCustomTextureBuffer() {
		_customTextureBuffer = null;
		_hasCustomTextureBuffer = false;
	}
	
	public TextureBuffer getTextureBuffer() {
		if(_hasCustomTextureBuffer)
			return _customTextureBuffer;
		return _texture.getTextureBuffer(_textureTile - 1);
	}
	
	public void setTile(int tileIndex) {
		_textureTile = tileIndex;
		if(_textureTile > _texture.getTileCount())
			_textureTile = _texture.getTileCount();
		if(_textureTile < 1)
			_textureTile = 1;
	}
	
	public int getTile() {
		return _textureTile;
	}

}
