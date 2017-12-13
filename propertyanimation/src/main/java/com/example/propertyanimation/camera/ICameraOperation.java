package com.example.propertyanimation.camera;


/**
 * 相机操作的接口
 * @author jerry
 * @date 2015-09-24
 */
public interface ICameraOperation {
    /**
     *   切换前置和后置相机
     */
    public  void  switchCamera();
    /**
     *  切换闪光灯模式
     * @param i
     */
    public void switchFlashMode(int i);
    /**
     *  拍照
     */
    public boolean takePicture();
    /**
     *  相机最大缩放级别
     *  @return
     */
    public int getMaxZoom();
    /**
     *  设置当前缩放级别
     *  @param zoom
     */
    public void setZoom(int zoom);
    /**
     *  获取当前缩放级别
     *  @return
     */
    public int getZoom();
    /**
     * 释放相机
     *
     */
    public void releaseCamera();
}
