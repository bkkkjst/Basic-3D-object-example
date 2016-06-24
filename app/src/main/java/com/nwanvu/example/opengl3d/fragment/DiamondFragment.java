package com.nwanvu.example.opengl3d.fragment;


import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.nwanvu.example.opengl3d.R;
import com.nwanvu.example.opengl3d.renderer.DiamondRenderer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiamondFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    public static final String NAME = "Diamond";
    private GLSurfaceView glView;
    private DiamondRenderer diamondRenderer;

    public DiamondFragment() {
        // Required empty public constructor
    }

    public static DiamondFragment newInstance() {
        DiamondFragment fragment = new DiamondFragment();
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
        diamondRenderer = null;
    }

    private View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_diamond, container, false);
        glView = (GLSurfaceView) view.findViewById(R.id.glview);
        assert glView != null;
        glView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.diamond_trans_z, outValue, true);
        float value = outValue.getFloat();
        diamondRenderer = new DiamondRenderer(value);
        glView.setRenderer(diamondRenderer); // Use a custom renderer
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        glView.setZOrderOnTop(true);

        SeekBar seekX = (SeekBar) view.findViewById(R.id.seek_x);
        seekX.setProgress((int) diamondRenderer.getRotateX() * 100);
        seekX.setOnSeekBarChangeListener(this);
        SeekBar seekY = (SeekBar) view.findViewById(R.id.seek_y);
        seekY.setProgress((int) diamondRenderer.getRotateY() * 100);
        seekY.setOnSeekBarChangeListener(this);
        SeekBar seekZ = (SeekBar) view.findViewById(R.id.seek_z);
        seekZ.setProgress((int) diamondRenderer.getRotateZ() * 100);
        seekZ.setOnSeekBarChangeListener(this);
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (diamondRenderer == null) return;

        switch (seekBar.getId()) {
            case R.id.seek_x:
                diamondRenderer.setRotateX(progress / 100f);
                break;
            case R.id.seek_y:
                diamondRenderer.setRotateY(progress / 100f);
                break;
            case R.id.seek_z:
                diamondRenderer.setRotateZ(progress / 100f);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
