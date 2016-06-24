package com.nwanvu.example.opengl3d.renderer;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.nwanvu.example.opengl3d.obj.Cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * OpenGL Custom renderer used with GLSurfaceView
 */
public class CubesRenderer implements GLSurfaceView.Renderer {

	private Cube cube;
	private Cube cube1;
	private Cube cube2;

	private final float transZ;
	private float angleCube = 0f;
	private float speedCube = 1f;

	public CubesRenderer(float value) {
		cube = new Cube();
		cube1 = new Cube();
		cube2 = new Cube();
		this.transZ = value;
	}

	// Call back when the surface is first created or re-created
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	// Call back after onSurfaceCreated() or whenever the window's size changes
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0)
			height = 1; // To prevent divide by zero
		float aspect = (float) width / height;

		// Set the viewport (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix
		// Use perspective projection
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset

	}

	// Call back to draw the current frame.
	@Override
	public void onDrawFrame(GL10 gl) {
		// Clear color and depth buffers using clear-value set earlier
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		if(angleCube > 360){
			angleCube -= 360;
		}
		// ----- Render the Color Cube -----
		gl.glLoadIdentity(); // Reset the model-view matrix
		gl.glTranslatef(-2.5f, 0.0f, transZ); // Translate right and into the
												// screen
		gl.glRotatef(angleCube, 1.0f, 0f, 0f); // rotate about the axis (1,1,1)
												// (NEW)
		cube.draw(gl); // Draw the cube (NEW)
		// Update the rotational angle after each refresh (NEW)
		angleCube += speedCube; // (NEW)

		// / cube2
		gl.glLoadIdentity(); // Reset the model-view matrix
		gl.glTranslatef(0.0f, 0.0f, transZ);
		gl.glRotatef(angleCube * 2, 1.0f, 0f, 0f);
		cube1.draw(gl);

		// / cube3
		gl.glLoadIdentity(); // Reset the model-view matrix
		gl.glTranslatef(2.5f, 0.0f, transZ);
		gl.glRotatef(angleCube * 4, 1.0f, 0f, 0f);
		cube2.draw(gl);

	}

	public float getSpeedCube() {
		return speedCube;
	}

	public void setSpeed(float speed) {
		this.speedCube = speed;
	}
}
