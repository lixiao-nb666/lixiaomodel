/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lixiao.build.zxing.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.lixiao.build.zxing.camera.CameraManager;
import com.lixiao.build.zxing.decoding.CaptureActivityHandler;
import com.lixiao.build.zxing.decoding.InactivityTimer;
import com.lixiao.build.zxing.util.ScanUtil;
import com.lixiao.build.zxing.util.ZXingUtils;
import com.lixiao.build.zxing.view.ViewfinderView;
import com.lixiao.developmenttool.R;

import java.io.IOException;
import java.util.Vector;


/**
 * The barcode reader activity itself. This is loosely based on the
 * CameraPreview example included in the Android SDK.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public class CaptureActivity extends Activity implements SurfaceHolder.Callback,
        OnClickListener {
    private CaptureActivityHandler handler;// ????????????
    private ViewfinderView viewfinderView;// ??????????????????
    private boolean hasSurface;// ????????????????????????
    private Vector<BarcodeFormat> decodeFormats;// ???????????????????????????
    private String characterSet;// ?????????
    private InactivityTimer inactivityTimer;// ??????????????????timer
    private MediaPlayer mediaPlayer;// ?????????
    private boolean playBeep;// ????????????
    private static final float BEEP_VOLUME = 0.10f;// ????????????
    private boolean vibrate;// ????????????

    // ?????????
    private Button flash_btn;
    private boolean isTorchOn = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_capture_layout);
        CameraManager.init(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        // ??????
        Button mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);
        flash_btn = (Button) findViewById(R.id.flash_btn);
        flash_btn.setOnClickListener(this);
        // ????????????
        Button photo_btn = (Button) findViewById(R.id.photo_btn);
        photo_btn.setOnClickListener(this);
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ?????????????????????
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        // ??????
        playBeep = true;
        // ????????????????????????
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // ??????
        vibrate = true;

    }

    @Override
    protected void onPause() {

        // ???????????? ???????????????
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {

        // ????????????????????????timer
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * ??????????????????
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(this, "????????????!", Toast.LENGTH_SHORT)
                    .show();
        } else {
           /* Intent intent = new Intent(this, ShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("msg", resultString);
            intent.putExtras(bundle);
            startActivity(intent);*/

            ScanUtil.setScanResult(this, resultString);
        }
    }

    /**
     * ??????????????????
     */
    private void selectPhoto() {
/*
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }*/
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"

        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "?????????????????????");

        startActivityForResult(wrapperIntent,
                REQUEST_CODE);
    }

    /**
     * ???????????????
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * ????????????
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    /**
     * ??????????????????
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private static final int REQUEST_CODE = 234;// ????????????code
    private String photo_path;// ?????????????????????

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE:

                    String[] proj = {MediaStore.Images.Media.DATA};
                    // ???????????????????????????
                    Cursor cursor = getContentResolver().query(data.getData(),
                            proj, null, null, null);

                    if (cursor.moveToFirst()) {

                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(column_index);
                        if (photo_path == null) {
                            photo_path = ZXingUtils.getPath(getApplicationContext(),
                                    data.getData());
                        }

                    }

                    cursor.close();

                    releaseImgThread();

                    break;

            }

        }

    }

    /**
     * ????????????Thread
     */
    private void releaseImgThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                Result result = ZXingUtils.scanningImage(photo_path);
                if (result == null) {
                    Message message=new Message();
                    message.what=SHOW_TOAST_MSG;

                    msgHandler.sendMessageDelayed(message,1);

                } else {
                    // ????????????

                    String recode =ZXingUtils.recode(result.toString());

                    Intent data = new Intent();
                    data.putExtra("LOCAL_PHOTO_RESULT", recode);
                    setResult(300, data);
                    finish();

                }
            }
        }).start();
    }

    private static final int SHOW_TOAST_MSG = 0;
    Handler msgHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_MSG:

                    Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    /*
     * onClick
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.flash_btn){
            // ???????????????
            if (isTorchOn) {
                isTorchOn = false;
                flash_btn.setText("??????");
                CameraManager.start();
            } else {
                isTorchOn = true;
                flash_btn.setText("??????");
                CameraManager.stop();
            }
        }else if(v.getId()==R.id.photo_btn){
            // ????????????
            selectPhoto();
        }else if(v.getId()==R.id.button_back){
            // ??????
            finish();
        }else {

        }



    }

}