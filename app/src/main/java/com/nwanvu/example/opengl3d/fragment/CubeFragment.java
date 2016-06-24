package com.nwanvu.example.opengl3d.fragment;


import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.nwanvu.example.opengl3d.R;
import com.nwanvu.example.opengl3d.renderer.CubesRenderer;

/**
 * A simple {@link Fragment} subclass.
 */
public class CubeFragment extends Fragment implements AppCompatSeekBar.OnSeekBarChangeListener {

    public static final String NAME = "Cube";
    private GLSurfaceView glView;
    private CubesRenderer cubesRenderer;

    public CubeFragment() {
        // Required empty public constructor
    }

    public static CubeFragment newInstance() {
        CubeFragment fragment = new CubeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initLayout(inflater, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        glView = null;
        cubesRenderer = null;
    }

    private View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_cube, container, false);
        AppCompatSeekBar seekSpeed = (AppCompatSeekBar) view.findViewById(R.id.seek_speed);
        seekSpeed.setOnSeekBarChangeListener(this);
        glView = (GLSurfaceView) view.findViewById(R.id.glview);
        assert glView != null;
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.cube_trans_z, outValue, true);
        float value = outValue.getFloat();
        cubesRenderer = new CubesRenderer(value);
        seekSpeed.setProgress((int) cubesRenderer.getSpeedCube() * 10);
        glView.setRenderer(cubesRenderer); // Use a custom renderer
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setZOrderOnTop(true);
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (cubesRenderer != null) {
            cubesRenderer.setSpeed(progress / 10f);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
