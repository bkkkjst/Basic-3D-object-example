package com.nwanvu.example.opengl3d.renderer;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.nwanvu.example.opengl3d.obj.Diamond;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nwanvu on 28/05/2016.
 * DiamondRenderer object
 */
public class DiamondRenderer implements GLSurfaceView.Renderer {
    private final Diamond diamond;

    private final float transZ;
    private float angle = 0f;
    private float rotateX = 0f;
    private float rotateY = 1f;
    private float rotateZ = 0f;

    public DiamondRenderer(float value) {
        diamond = new Diamond();
        this.transZ = value;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

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

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        if(angle > 360) {
            angle -= 360;
        }

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, transZ);
        gl.glRotatef(angle, rotateX, rotateY, rotateZ); // rotate about the axis (1,1,1)
        diamond.draw(gl);

        angle++;
    }

    public void setRotateX(float rotateX) {
        this.rotateX = rotateX;
    }

    public float getRotateX() {
        return rotateX;
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
    }

    public float getRotateY() {
        return rotateY;
    }

    public void setRotateZ(float rotateZ) {
        this.rotateZ = rotateZ;
    }

    public float getRotateZ() {
        return rotateZ;
    }
}
