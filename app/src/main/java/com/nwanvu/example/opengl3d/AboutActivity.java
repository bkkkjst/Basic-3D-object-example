package com.nwanvu.example.opengl3d;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;


public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mVersionTextView = (TextView) findViewById(R.id.aboutVersionTextView);
        if (mVersionTextView != null)
            mVersionTextView.setText(String.format(getResources().getString(R.string.app_version), BuildConfig.VERSION_NAME));

        View aboutImageView = findViewById(R.id.aboutImageView);
        if (aboutImageView != null) {
            aboutImageView.setOnClickListener(this);
            AnimatorSet logoAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.ic_launcher_anim);
            logoAnimator.setTarget(aboutImageView);
            logoAnimator.start();
        }
        View licenses = findViewById(R.id.licenses);
        if (licenses != null)
            licenses.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.licenses:
                WebView webView = new WebView(this);
                webView.getSettings().setBuiltInZoomControls(false);
                webView.getSettings().setDisplayZoomControls(false);

                webView.loadUrl("file:///android_asset/license.html");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(webView);
                builder.setTitle(R.string.text_open_source_licenses)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }
    }
}
