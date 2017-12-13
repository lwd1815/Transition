package com.example.propertyanimation.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import com.example.propertyanimation.BaseApplication;
import com.example.propertyanimation.R;
import com.example.propertyanimation.camera.util.BitmapUtils;
import com.example.propertyanimation.camera.util.DisplayUrils;
import com.example.propertyanimation.camera.util.FileUtil;
import com.example.propertyanimation.camera.util.SPConfigUtil;
import com.example.propertyanimation.camera.util.StringUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.reactivestreams.Publisher;

/**
 * 正方形的CamerContainer
 *
 * @author jerry
 * @date 2015-09-16
 */
public class SquareCameraContainer extends FrameLayout
    implements ICameraOperation, IActivityLifiCycle {
  public static final String TAG = "SquareCameraContainer";

  private Context mContext;

  /**
   * 相机绑定的SurfaceView
   */
  private CameraView mCameraView;

  /**
   * 触摸屏幕时显示的聚焦图案
   */
  private FocusImageView mFocusImageView;
  /**
   * 缩放控件
   */
  private SeekBar mZoomSeekBar;

  private SmartCameraActivity mActivity;

  private SoundPool mSoundPool;

  private boolean mFocusSoundPrepared;

  private int mFocusSoundId;

  private String mImagePath;

  private SensorControler mSensorControler;

  public static final int RESETMASK_DELY = 1000; //一段时间后遮罩层一定要隐藏
  private int focusNum;
  private int picFlag = 1;
  private Point centerPoint;
  private double cropOffset;

  public SquareCameraContainer(Context context) {
    super(context);
    mContext = context;
    init();
  }

  public SquareCameraContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    init();
  }

  void init() {
    inflate(mContext, R.layout.custom_camera_container, this);

    mCameraView = (CameraView) findViewById(R.id.cameraView);
    mFocusImageView = (FocusImageView) findViewById(R.id.focusImageView);
    mZoomSeekBar = (SeekBar) findViewById(R.id.zoomSeekBar);

    mSensorControler = SensorControler.getInstance();
    int screenWidth = BaseApplication.getWindowWidth();
    int screenHeight = BaseApplication.getWindowHeight();
    centerPoint = new Point(screenWidth / 2, screenHeight / 2- DisplayUrils.dip2px(mContext, 40));

    mSensorControler.setCameraFocusListener(new SensorControler.CameraFocusListener() {
      @Override public void onFocus() {
        onCameraFocus(centerPoint);
      }
    });
    mCameraView.setOnCameraPrepareListener(new CameraView.OnCameraPrepareListener() {
      @Override public void onPrepare(CameraManager.CameraDirection cameraDirection) {
        postDelayed(new Runnable() {
          @Override public void run() {

          }
        }, RESETMASK_DELY);
        //在这里相机已经准备好 可以获取maxZoom
        mZoomSeekBar.setMax(mCameraView.getMaxZoom());

        if (cameraDirection == CameraManager.CameraDirection.CAMERA_BACK) {
          //mHandler.postDelayed(new Runnable() {
          //  @Override public void run() {
          //    int screenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
          //    Point point = new Point(screenWidth / 2, screenWidth / 2);
          //    onCameraFocus(point);
          //  }
          //}, 800);
        }
      }
    });
    mCameraView.setSwitchCameraCallBack(new CameraView.SwitchCameraCallBack() {
      @Override public void switchCamera(boolean isSwitchFromFront) {
        if (isSwitchFromFront) {
          //mHandler.postDelayed(new Runnable() {
          //  @Override public void run() {
          //    int screenWidth = mActivity.getResources().getDisplayMetrics().widthPixels;
          //    Point point = new Point(screenWidth / 2, screenWidth / 2);
          //    onCameraFocus(point);
          //  }
          //}, 300);
        }
      }
    });
    mCameraView.setPictureCallback(pictureCallback);
    mZoomSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

    //        //音效初始化
    //        mSoundPool = getSoundPool();
  }

  private SoundPool getSoundPool() {
    if (mSoundPool == null) {
      mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
      mFocusSoundId = mSoundPool.load(mContext, R.raw.camera_focus, 1);
      mFocusSoundPrepared = false;
      mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
        @Override public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
          mFocusSoundPrepared = true;
        }
      });
    }
    return mSoundPool;
  }

  public void setImagePath(String mImagePath) {
    this.mImagePath = mImagePath;
  }

  public void bindActivity(SmartCameraActivity activity) {
    this.mActivity = activity;
    if (mCameraView != null) {
      mCameraView.bindActivity(activity);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //int len = SweetApplication.mScreenWidth;

    //保证View是正方形
    //setMeasuredDimension(mActivity.getResources().getDisplayMetrics().widthPixels, mActivity.getResources().getDisplayMetrics().heightPixels);
  }

  /**
   * 记录是拖拉照片模式还是放大缩小照片模式
   */

  private static final int MODE_INIT = 0;
  /**
   * 放大缩小照片模式
   */
  private static final int MODE_ZOOM = 1;
  private int mode = MODE_INIT;// 初始状态

  private float startDis;

  @Override public boolean onTouchEvent(MotionEvent event) {
    /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
      // 手指压下屏幕
      case MotionEvent.ACTION_DOWN:
        mode = MODE_INIT;
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        //如果mZoomSeekBar为null 表示该设备不支持缩放 直接跳过设置mode Move指令也无法执行
        if (mZoomSeekBar == null) return true;
        //移除token对象为mZoomSeekBar的延时任务
        mHandler.removeCallbacksAndMessages(mZoomSeekBar);
        //                mZoomSeekBar.setVisibility(View.VISIBLE);
        mZoomSeekBar.setVisibility(View.GONE);

        mode = MODE_ZOOM;
        /** 计算两个手指间的距离 */
        startDis = spacing(event);
        break;
      case MotionEvent.ACTION_MOVE:
        if (mode == MODE_ZOOM) {
          //只有同时触屏两个点的时候才执行
          if (event.getPointerCount() < 2) return true;
          float endDis = spacing(event);// 结束距离
          //每变化10f zoom变1
          int scale = (int) ((endDis - startDis) / 10f);
          if (scale >= 1 || scale <= -1) {
            int zoom = mCameraView.getZoom() + scale;
            //zoom不能超出范围
            if (zoom > mCameraView.getMaxZoom()) zoom = mCameraView.getMaxZoom();
            if (zoom < 0) zoom = 0;
            mCameraView.setZoom(zoom);
            mZoomSeekBar.setProgress(zoom);
            //将最后一次的距离设为当前距离
            startDis = endDis;
          }
        }
        break;
      // 手指离开屏幕
      case MotionEvent.ACTION_UP:
        if (mode != MODE_ZOOM) {
          //设置聚焦
          Point point = new Point((int) event.getX(), (int) event.getY());
          onCameraFocus(point);
        } else {
          //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
          mHandler.postAtTime(new Runnable() {

            @Override public void run() {

              mZoomSeekBar.setVisibility(View.GONE);
            }
          }, mZoomSeekBar, SystemClock.uptimeMillis() + 2000);
        }
        break;
    }
    return true;
  }

  /**
   * 两点的距离
   */
  private float spacing(MotionEvent event) {
    if (event == null) {
      return 0;
    }
    float x = event.getX(0) - event.getX(1);
    float y = event.getY(0) - event.getY(1);
    return (float) Math.sqrt(x * x + y * y);
  }

  /**
   * 相机对焦  默认不需要延时
   */
  private void onCameraFocus(final Point point) {
    onCameraFocus(point, false);
  }

  /**
   * 相机对焦
   *
   * @param needDelay 是否需要延时
   */
  public void onCameraFocus(final Point point, boolean needDelay) {
    long delayDuration = needDelay ? 300 : 0;

    mHandler.postDelayed(new Runnable() {
      @Override public void run() {
        if (!mSensorControler.isFocusLocked()) {
          if (mCameraView.focus(autoFocusCallback)) {
            mSensorControler.lockFocus();
            mFocusImageView.startFocus(point);

            //播放对焦音效
            //if (mFocusSoundPrepared) {
            //  mSoundPool.play(mFocusSoundId, 1.0f, 0.5f, 1, 0, 1.0f);
            //}
          }
        }
      }
    }, delayDuration);
  }

  @Override public void switchCamera() {
    mCameraView.switchCamera();
  }

  @Override public void switchFlashMode(int flashFlag) {
    mCameraView.switchFlashMode(flashFlag);
  }

  @Override public boolean takePicture() {
    setMaskOn();
    boolean flag = mCameraView.takePicture();
    if (!flag) {
      mSensorControler.unlockFocus();
    }
    setMaskOff();
    return flag;
  }

  @Override public int getMaxZoom() {
    return mCameraView.getMaxZoom();
  }

  @Override public void setZoom(int zoom) {
    mCameraView.setZoom(zoom);
  }

  @Override public int getZoom() {
    return mCameraView.getZoom();
  }

  @Override public void releaseCamera() {
    if (mCameraView != null) {
      mCameraView.releaseCamera();
    }
  }

  Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
    @Override public void onPreviewFrame(byte[] data, Camera camera) {
      //mActivity.rest();

      Log.i(TAG, "previewCallback");

      Message msg = handler.obtainMessage();
      msg.obj = saveToFuckSDCard(data, mContext);
      handler.sendMessage(msg);
    }
  };

  private Handler mHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  };

  public void startPreview() {
    mCameraView.startPreview();
  }

  public void stopPreview() {
    mCameraView.stopPreview();
  }

  private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

    @Override public void onAutoFocus(boolean success, Camera camera) {
      if (success) {
        if (picFlag == 1) {
          mCameraView.focus(null);
          //mCameraView.setOneShotPreview(previewCallback);
          mCameraView.takePicture();
          Toast.makeText(mActivity, "聚焦成功", Toast.LENGTH_SHORT).show();
          System.out.println("聚焦成功......");
        }
        mFocusImageView.onFocusSuccess();
      } else {
        mFocusImageView.onFocusFailed();
        Toast.makeText(mActivity, "聚焦失败", Toast.LENGTH_SHORT).show();
        System.out.println("聚焦失败......");
      }
      mHandler.postDelayed(new Runnable() {
        @Override public void run() {
          //一秒之后才能再次对焦
          mSensorControler.unlockFocus();
        }
      }, 1000);

      ////聚焦之后根据结果修改图片
      //if (success) {
      //  if (focusNum > -1) {
      //    mFocusImageView.onFocusSuccess();
      //    mActivity.focusSuccess();
      //  }else {
      //    mFocusImageView.onFocusFailed();
      //  }
      //  focusNum++;
      //} else {
      //  //聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
      //  mFocusImageView.onFocusFailed();
      //}
      //mHandler.postDelayed(new Runnable() {
      //  @Override public void run() {
      //    //一秒之后才能再次对焦
      //    mSensorControler.unlockFocus();
      //  }
      //}, 3000);
    }
  };

  private final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
    @Override public void onPictureTaken(final byte[] data, Camera camera) {
      //mActivity.rest();

      Log.i(TAG, "pictureCallback");

      //new SavePicTask(data, mCameraView.isBackCamera()).start();

      //Message msg = handler.obtainMessage();
      //msg.obj = saveToSDCard(data, mContext);
      //handler.sendMessage(msg);

      saveToSDCard(data, mContext);
    }
  };

  private Object saveToFuckSDCard(byte[] data, Context mContext) {
    ByteArrayOutputStream baos;
    byte[] rawImage;
    Bitmap bitmap;
    mCameraView.setOneShotPreview(null);
    //处理data
    Camera.Size previewSize = mCameraView.getPictureSize();
    BitmapFactory.Options newOpts = new BitmapFactory.Options();
    newOpts.inJustDecodeBounds = true;
    YuvImage yuvimage =
        new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
    baos = new ByteArrayOutputStream();
    yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100,
        baos);// 80--JPG图片的质量[0-100],100最高
    rawImage = baos.toByteArray();
    //将rawImage转换成bitmap
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

    if (bitmap != null) {
      //bitmap = rotateBitmap(bitmap,mCameraView.isBackCamera());
      bitmap = rotateFuckBitmap(bitmap, mCameraView.isBackCamera());
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
      String path = Environment.getExternalStorageDirectory() + "/DAYU/smartpic";
      String date_format = format.format(date);
      String filename = "IMG_" + date_format + ".jpg";
      compressBitmapToFile(bitmap, date_format, 512);
      compressBitmapToFile(bitmap, date_format, 720);
      compressBitmapToFile(bitmap, date_format, 960);
      File fileFolder = new File(path);
      if (!fileFolder.exists()) {
        fileFolder.mkdirs();
      }
      File jpgFile = new File(fileFolder, filename);
      mImagePath = jpgFile.getAbsolutePath();
      FileOutputStream outputStream = null; //
      try {
        outputStream = new FileOutputStream(jpgFile);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      //刷新相册
      MediaScannerConnection.scanFile(mContext, new String[] { jpgFile.getAbsolutePath() }, null,
          null);

      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
      try {
        outputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
      try {
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  private void compressBitmapToFile(final Bitmap bitmap, final String date_format) {
    //取512_100图片上传
    Flowable
        //.just(960,1080)
        .just(512)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .flatMap(new Function<Integer, Publisher<Integer[]>>() {
          @Override public Publisher<Integer[]> apply(final Integer px) throws Exception {
            return Flowable.create(new FlowableOnSubscribe<Integer[]>() {
              @Override public void subscribe(FlowableEmitter<Integer[]> emitter) throws Exception {
                //emitter.onNext(new Integer[] { px, 60 });
                //emitter.onNext(new Integer[] { px, 80 });
                emitter.onNext(new Integer[] { px, 90 });
                //emitter.onNext(new Integer[] { px, 95 });
                //emitter.onNext(new Integer[] { px, 100 });
                emitter.onComplete();
              }
            }, BackpressureStrategy.BUFFER);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .flatMap(new Function<Integer[], Publisher<String>>() {
          @Override public Publisher<String> apply(Integer[] params) throws Exception {
            String path = Environment.getExternalStorageDirectory() + "/DAYU/smartpic";
            String filename = "IMG_" + date_format + "_" + params[0] + "_" + params[1] + ".jpg";
            File fileFolder = new File(path);
            if (!fileFolder.exists()) {
              fileFolder.mkdirs();
            }
            final File file = new File(fileFolder, filename);

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            int wh = w > h ? h : w;
            int centerX = w / 2;
            int centerY = h / 2;

            Bitmap xBitmap =
                Bitmap.createBitmap(bitmap, centerX - wh / 2, centerY - wh / 2-(int)(cropOffset*getHeight()), wh, wh,
                    null, false);
            //bitmap.recycle();

            Bitmap result = Bitmap.createBitmap(params[0], params[0], Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(result);
            Rect rect = new Rect(0, 0, params[0], params[0]);
            canvas.drawBitmap(xBitmap, null, rect, null);

            FileOutputStream fos = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.JPEG, params[1], fos);
            //final int size = result.getByteCount();
            fos.flush();
            fos.close();
            return Flowable.create(new FlowableOnSubscribe<String>() {
              @Override public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                emitter.onNext(file.getAbsolutePath());
                emitter.onComplete();
              }
            }, BackpressureStrategy.BUFFER);
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            if (s.contains("512_90")) {
              mImagePath = s;
              //MediaScannerConnection.scanFile(mContext, new String[] { mImagePath }, null, null);
              Message msg = handler.obtainMessage();
              msg.obj = true;
              handler.sendMessage(msg);
            }
            System.out.println(s);
          }
        });
  }

  private void compressBitmapToFile(final Bitmap bmp, final String date_format, final int px) {
    Observable.create(new ObservableOnSubscribe<File>() {
      @Override public void subscribe(ObservableEmitter<File> emitter) throws Exception {
        String path = Environment.getExternalStorageDirectory() + "/DAYU/smartpic";
        String filename = "IMG_" + date_format + "_" + px + ".jpg";
        File fileFolder = new File(path);
        if (!fileFolder.exists()) {
          fileFolder.mkdirs();
        }
        File jpgFile = new File(fileFolder, filename);
        emitter.onNext(jpgFile);
        emitter.onComplete();
      }
    })
        .observeOn(io.reactivex.schedulers.Schedulers.io())
        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .flatMap(new Function<File, ObservableSource<Integer>>() {
          @Override public ObservableSource<Integer> apply(File file) throws Exception {
            Bitmap result = null;
            if (bmp.getHeight() >= bmp.getWidth()) {
              result = Bitmap.createBitmap(px, bmp.getHeight() * px / bmp.getWidth(),
                  Bitmap.Config.RGB_565);
              Canvas canvas = new Canvas(result);
              Rect rect = new Rect(0, 0, px, bmp.getHeight() * px / bmp.getWidth());
              canvas.drawBitmap(bmp, null, rect, null);
            } else {
              result = Bitmap.createBitmap(bmp.getWidth() * px / bmp.getHeight(), px,
                  Bitmap.Config.RGB_565);
              Canvas canvas = new Canvas(result);
              Rect rect = new Rect(0, 0, px, bmp.getHeight() * px / bmp.getWidth());
              canvas.drawBitmap(bmp, null, rect, null);
            }
            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //result.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
            //final int size = baos.size();
            //FileOutputStream fos = new FileOutputStream(file);
            //fos.write(baos.toByteArray());
            //fos.flush();
            //fos.close();
            //return Observable.create(new ObservableOnSubscribe<Integer>() {
            //  @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
            //    e.onNext(size);
            //    e.onComplete();
            //  }
            //});
            FileOutputStream fos = new FileOutputStream(file);
            result.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            final int size = result.getByteCount();
            fos.flush();
            fos.close();
            return Observable.create(new ObservableOnSubscribe<Integer>() {
              @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(size);
                e.onComplete();
              }
            });
          }
        })
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<Integer>() {
          @Override public void accept(Integer integer) throws Exception {
            System.out.println("pic_" + px + " : " + integer);
          }
        });
  }

  public void saveToSDCard(byte[] data, Context context) {
    Bitmap bitmap;
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = false;
    options.inPreferQualityOverSpeed = true;
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

    if (bitmap != null) {
      //bitmap = rotateBitmap(bitmap,mCameraView.isBackCamera());
      bitmap = rotateFuckBitmap(bitmap, mCameraView.isBackCamera());
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
      String path = Environment.getExternalStorageDirectory() + "/DAYU/picture";
      String date_format = format.format(date);
      String filename = "IMG_" + date_format + ".jpg";
      compressBitmapToFile(bitmap, date_format);
      //  File fileFolder = new File(path);
      //  if (!fileFolder.exists()) {
      //    fileFolder.mkdirs();
      //  }
      //  File jpgFile = new File(fileFolder, filename);
      //  mImagePath = jpgFile.getAbsolutePath();
      //  FileOutputStream outputStream = null; //
      //  try {
      //    outputStream = new FileOutputStream(jpgFile);
      //  } catch (FileNotFoundException e) {
      //    e.printStackTrace();
      //    return false;
      //  }
      //  //刷新相册
      //  MediaScannerConnection.scanFile(context, new String[] { jpgFile.getAbsolutePath() }, null,
      //      null);
      //
      //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
      //  try {
      //    outputStream.flush();
      //  } catch (IOException e) {
      //    e.printStackTrace();
      //    return false;
      //  }
      //  try {
      //    outputStream.close();
      //  } catch (IOException e) {
      //    e.printStackTrace();
      //    return false;
      //  }
      //  return true;
      //} else {
      //  return false;
    }
  }

  private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
      new SeekBar.OnSeekBarChangeListener() {

        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          mCameraView.setZoom(progress);
          mHandler.removeCallbacksAndMessages(mZoomSeekBar);
          //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
          mHandler.postAtTime(new Runnable() {

            @Override public void run() {
              mZoomSeekBar.setVisibility(View.GONE);
            }
          }, mZoomSeekBar, SystemClock.uptimeMillis() + 2000);
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override public void onStopTrackingTouch(SeekBar seekBar) {

        }
      };

  @Override public void onStart() {
    //第一次聚焦不处理
    focusNum = 0;
    mSensorControler.onStart();

    if (mCameraView != null) {
      mCameraView.onStart();
    }

    mSoundPool = getSoundPool();
  }

  @Override public void onStop() {
    focusNum = 0;
    mSensorControler.onStop();

    if (mCameraView != null) {
      mCameraView.onStop();
    }

    if (mSoundPool != null) {
      mSoundPool.release();
      mSoundPool = null;
    }
  }

  public void setMaskOn() {

  }

  public void setMaskOff() {

  }

  public int getPictureRotation() {
    return mCameraView.getPicRotation();
  }

  private Bitmap rotateFuckBitmap(Bitmap bitmap, boolean backCamera) {
    int degrees = mCameraView.getPicRotation();
    Matrix m = new Matrix();
    m.postRotate(degrees);
    try {
      bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    } catch (OutOfMemoryError localOutOfMemoryError) {
      System.gc();
      System.runFinalization();

      bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }

    return bitmap;
  }

  /**
   * 旋转bitmap
   * 对于前置摄像头和后置摄像头采用不同的旋转角度  前置摄像头还需要做镜像水平翻转
   */
  public Bitmap rotateBitmap(Bitmap bitmap, boolean isBackCamera) {
    System.gc();
    int degrees = isBackCamera ? 0 : 0;
    degrees = mCameraView.getPicRotation();
    if (null == bitmap) {
      return bitmap;
    }
    Matrix matrix = new Matrix();
    matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    if (!isBackCamera) {
      matrix.postScale(-1, 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);   //镜像水平翻转
    }
    //            Bitmap bmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,!isBackCamera);
    //不需要透明度 使用RGB_565
    Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
    Paint paint = new Paint();
    Canvas canvas = new Canvas(bmp);
    canvas.drawBitmap(bitmap, matrix, paint);

    if (null != bitmap) {
      bitmap.recycle();
    }

    return bmp;
  }

  /**
   * if true 获取以中心点为中心的正方形区域
   */
  private Rect getCropRect(byte[] data) {
    //获得图片大小
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(data, 0, data.length, options);

    int width = options.outWidth;
    int height = options.outHeight;
    int centerX = width / 2;
    int centerY = height / 2;

    int PHOTO_LEN = Math.min(width, height);
    return new Rect(centerX - PHOTO_LEN / 2, centerY - PHOTO_LEN / 2, centerX + PHOTO_LEN / 2,
        centerY + PHOTO_LEN / 2);
  }

  /**
   * 给出合适的sampleSize的建议
   */
  private int suggestSampleSize(byte[] data, int target) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    options.inPurgeable = true;
    BitmapFactory.decodeByteArray(data, 0, data.length, options);

    int w = options.outWidth;
    int h = options.outHeight;
    int candidateW = w / target;
    int candidateH = h / target;
    int candidate = Math.max(candidateW, candidateH);
    if (candidate == 0) return 1;
    if (candidate > 1) {
      if ((w > target) && (w / candidate) < target) candidate -= 1;
    }
    if (candidate > 1) {
      if ((h > target) && (h / candidate) < target) candidate -= 1;
    }
    //if (VERBOSE)
    Log.i(TAG,
        "for w/h " + w + "/" + h + " returning " + candidate + "(" + (w / candidate) + " / " + (h
            / candidate));
    return candidate;
  }

  public void fileScan(String filePath) {
    Uri data = Uri.parse("file://" + filePath);
    mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
  }

  long lastTime;

  public void setPicFlag(int position) {
    picFlag = position;
  }

  public void setCropOffset(double offset) {
    cropOffset = offset;
  }

  private class SavePicTask extends Thread {
    private byte[] data;
    private boolean isBackCamera;
    private boolean sampleSizeSuggested;
    private boolean ioExceptionRetried;     //寻找合适的bitmap发生io异常  允许一次重试

    SavePicTask(byte[] data, boolean isBackCamera) {
      sampleSizeSuggested = false;
      ioExceptionRetried = false;
      this.data = data;
      this.isBackCamera = isBackCamera;
    }

    @Override public void run() {
      super.run();

      long current = System.currentTimeMillis();

      Message msg = handler.obtainMessage();
      msg.obj = saveToSDCard(data);
      handler.sendMessage(msg);

      Log.i(TAG, "save photo:" + (System.currentTimeMillis() - current) + "ms");
    }

    /**
     * 将拍下来的照片存放在SD卡中
     *
     * @return imagePath 图片路径
     */
    public boolean saveToSDCard(byte[] data) {
      lastTime = System.currentTimeMillis();

      //ADD 生成保存图片的路径
      mImagePath = FileUtil.getCameraImgPath();
      Log.i(TAG, "ImagePath:" + mImagePath);

      //保存到SD卡
      if (StringUtils.isEmpty(mImagePath)) {
        Log.e(TAG, "要保存的图片路径为空");
        return false;
      }

      if (!FileUtil.checkSDcard()) {
        //Toast.makeText(mContext, R.string.tips_sdcard_notexist, Toast.LENGTH_LONG).show();

        return false;
      }

      Log.i(TAG,
          "saveToSDCard beforefindFitBitmap time:" + (System.currentTimeMillis() - lastTime));
      //从本地读取合适的sampleSize,默认为1
      int sampleSize = SPConfigUtil.loadInt("sampleSize", 1);
      Bitmap bitmap = findFitBitmap(data, getCropRect(data), sampleSize);

      if (bitmap == null) {
        return false;
      }

      Log.i(TAG, "saveToSDCard beforeSave time:" + (System.currentTimeMillis() - lastTime));
      BitmapUtils.saveBitmap(bitmap, mImagePath);
      //if (BitmapUtils.saveBitmap(bitmap, mImagePath)){
      //    mActivity.searchIntent(mImagePath);
      //}

      //            bitmap.recycle();

      Log.i(TAG, "saveToSDCard afterSave time:" + (System.currentTimeMillis() - lastTime));
      return true;
    }

    /**
     * 寻找合适的bitmap  剪切rect  并且做旋转  镜像处理
     */
    private Bitmap findFitBitmap(byte[] data, Rect rect, int sampleSize) {
      InputStream is = null;
      System.gc();
      try {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;

        is = new ByteArrayInputStream(data);

        Bitmap bitmap = BitmapUtils.decode(is, rect, options);

        //                BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
        //                bitmap = decoder.decodeRegion(rect, options);

        //未抛出异常，保存合适的sampleSize
        SPConfigUtil.save("sampleSize", sampleSize + "");

        Log.i(TAG, "sampleSize:" + sampleSize);
        Log.i(TAG, "saveToSDCard afterLoad Bitmap time:" + (System.currentTimeMillis() - lastTime));

        //                if(mCameraView.needRotateBitmap()) {
        bitmap = rotateBitmap(bitmap, isBackCamera);
        Log.i(TAG,
            "saveToSDCard afterRotate Bitmap time:" + (System.currentTimeMillis() - lastTime));
        //                }
        return bitmap;
      } catch (OutOfMemoryError e) {
        e.printStackTrace();
        System.gc();

                /* 是否对sampleSize做出过建议，没有就做一次建议，按照建议的尺寸做出缩放，做过就直接缩小图片**/
        if (sampleSizeSuggested) {
          return findFitBitmap(data, rect, sampleSize * 2);
        } else {
          return findFitBitmap(data, rect,
              suggestSampleSize(data, mActivity.getResources().getDisplayMetrics().widthPixels));
        }
      } catch (Exception e) {
        e.printStackTrace();
        //try again
        if (!ioExceptionRetried) {
          ioExceptionRetried = true;
          return findFitBitmap(data, rect, sampleSize);
        }
        return null;
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private Handler handler = new Handler() {

    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);

      boolean result = (Boolean) msg.obj;

      Log.i(TAG, "TASK onPostExecute:" + (System.currentTimeMillis() - lastTime));

      if (result) {
        //fileScan(mImagePath);
        //                releaseCamera();    //不要在这个地方释放相机资源   这里是浪费时间的最大元凶  约1500ms左右
       // mActivity.postTakePhoto(mImagePath);
        Log.i(TAG, "TASK:" + (System.currentTimeMillis() - lastTime));
      } else {
        Log.e(TAG, "photo save failed!");
      //  Toast.makeText(mContext, R.string.topic_camera_takephoto_failure, Toast.LENGTH_SHORT)
            //.show();

        ///mActivity.rest();

        mCameraView.startPreview();
      }
    }
  };
}
