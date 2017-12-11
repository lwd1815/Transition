package com.example.propertyanimation.opengl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者     李文东
 * 创建时间   2017/12/11 10:27
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

/**
 * GLSurfaceView.Renderer，它将实现我们的渲染逻辑
 */
class MyRenderer implements GLSurfaceView.Renderer {
  private static final String VERTEX_SHADER =
      "attribute vec4 vPosition;\n"
          + "void main() {\n"
          + " gl_Position = vPosition;\n"
          + "}";
  private static final String FRAGMENT_SHADER =
      "precision mediump float;\n"
          + "void main() {\n"
          + " gl_FragColor = vec4(0.5, 0, 0, 1);\n"
          + "}";
  private static final float[] VERTEX = {   // in counterclockwise order:
      0, 1, 0,  // top
      -0.5f, -1, 0,  // bottom left
      1, -1, 0,  // bottom right
  };

  @Override public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

  }

  @Override public void onSurfaceChanged(GL10 gl10, int i, int i1) {

  }

  @Override public void onDrawFrame(GL10 gl10) {

  }
}
