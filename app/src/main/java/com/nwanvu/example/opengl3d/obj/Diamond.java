package com.nwanvu.example.opengl3d.obj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/*
 * A square drawn in 2 triangles (using TRIANGLE_STRIP). This square has one color.
 */
public class Diamond {
	private FloatBuffer vertexBuffer; // Buffer for vertex-array
	private float[] sliceBody = { // Vertices for the square
			0.0f, -1.0f, 0.0f, // 1. bottom
			-0.5f, 1.0f, 1.54f, // 2. left
			0.5f, 1.0f, 1.54f // 3. right
	};

	private float[][] colors = {  // Colors of the 9 faces
			{1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
			{1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
			{0.3f, 1.0f, 0.0f, 1.0f},  // 2. green
			{0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
			{1.0f, 0.0f, 0.2f, 1.0f},  // 4. red
			{1.0f, 1.0f, 0.0f, 1.0f},   // 5. yellow
			{0.5f, 0.25f, 0.25f, 1.0f},	// 6. brown
			{0.0f, 1.0f, 1.0f, 1.0f},	// 7. cyan
			{0.5f, 0.5f, 0.5f, 1.0f},	// 8. gray
			{1.0f, 0.0f, 0.2f, 1.0f},	// 9. pink
	};

	private float[] rotateAngles = {0f, 36f, 72f, 108f, 144f, 180f, 216f, 252f, 288f, 324f};

	// Constructor - Setup the vertex buffer
	public Diamond() {
		// Setup vertex array buffer. Vertices in float. A float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(sliceBody.length * 4);
		vbb.order(ByteOrder.nativeOrder()); // Use native byte order
		vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
		vertexBuffer.put(sliceBody); // Copy data into buffer
		vertexBuffer.position(0); // Rewind
	}

	// Render the shape
	public void draw(GL10 gl) {
		// Enable vertex-array and define its buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		for (int i = 0; i < rotateAngles.length; i++) {
			//draw slice body
			gl.glPushMatrix();
			gl.glRotatef(rotateAngles[i], 0.0f, 1.0f, 0.0f);
			gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, sliceBody.length / 3);
			gl.glPopMatrix();

			//draw slice head up
			int indexColor = rotateAngles.length - 1 - i;
			gl.glPushMatrix();
			gl.glRotatef(rotateAngles[i], 0.0f, 1.0f, 0.0f);
			gl.glRotatef(180f, 0.0f, 0.0f, 1f);
			gl.glTranslatef(0.0f, -1.2f, 0.77f);
			gl.glScalef(1f, 0.2f, 0.5f);
			gl.glColor4f(colors[indexColor][0], colors[indexColor][1], colors[indexColor][2], colors[indexColor][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, sliceBody.length / 3);
			gl.glPopMatrix();

			//draw slice head down
			gl.glPushMatrix();
			gl.glRotatef(rotateAngles[i], 0.0f, 1.0f, 0.0f);
			gl.glRotatef(180f, 1f, 0f, 0f);
			gl.glRotatef(180f, 0f, 0f, 1f);
			gl.glRotatef(90f, 0f, 0.5f, 0f);
			gl.glTranslatef(0.0f, 1.2f, -1.6f);
			gl.glScalef(0.48f, 0.2f, 0.56f);
			gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, sliceBody.length / 3);
			gl.glPopMatrix();

			//draw slice top
			gl.glPushMatrix();
			gl.glRotatef(rotateAngles[i], 0.0f, 1.0f, 0.0f);
			gl.glRotatef(180f, 1f, 0f, 0f);
			gl.glRotatef(90f, 0f, 0.5f, 0f);
			gl.glTranslatef(0.0f, -1.4f, 0f);
			gl.glScalef(0.49f, 0f, 0.49f);
			gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, sliceBody.length / 3);
			gl.glPopMatrix();
		}

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
