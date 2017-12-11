package com.example.propertyanimation.opengl;

/**
 * 创建者     李文东
 * 创建时间   2017/12/11 9:39
 * 描述
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述
 */

public class OpenGlView {
  /**
   * OpenGl的世界里,我们只能画点,线,三角形,复杂的图形都是由三角形构成的
   *
   * 在 OpenGL 里有两个最基本的概念：Vertex 和 Fragment。一切图形都从 Vertix 开始，
   * Vertix 序列围成了一个图形。那什么是 Fragment 呢？为此我们需要了解一下光栅化（Rasterization）：
   * 光栅化是把点、线、三角形映射到屏幕上的像素点的过程（每个映射区域叫一个 Fragment），也就是生成 Fragment 的过程。
   * 通常一个 Fragment 对应于屏幕上的一个像素，但高分辨率的屏幕可能会用多个像素点映射到一个 Fragment，以减少 GPU 的工作。
   *
   * shader着色器程序,shader用来描述如何绘制(渲染),GLSL是OpenGl的编程语言,
   * OpenGL 渲染需要两种 Shader：Vertex Shader 和 Fragment Shader。
   *
   * opengl只会渲染坐标值范围在[-1.1]的内容,超出这个范围的内容就会被裁减掉,这个范围空间叫clip space
   */
}
