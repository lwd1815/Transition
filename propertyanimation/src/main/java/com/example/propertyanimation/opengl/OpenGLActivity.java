package com.example.propertyanimation.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.propertyanimation.R;

public class OpenGLActivity extends AppCompatActivity {
  private GLSurfaceView mGlSurfaceView;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_gl);
    if (!Utils.supportGlEs20(this)){
      Toast.makeText(this, "GLES 2.0 not supported!", Toast.LENGTH_LONG).show();
      finish();
      return;
    }

    mGlSurfaceView= (GLSurfaceView) findViewById(R.id.surface);
    mGlSurfaceView.setEGLContextClientVersion(2);
    mGlSurfaceView.setEGLConfigChooser(8,8,8,8,16,0);
    mGlSurfaceView.setRenderer(new MyRenderer());
    mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
  }
}
