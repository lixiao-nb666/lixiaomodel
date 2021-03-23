package com.lixiao.build.util;

import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.mybase.share.MyShare;



/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class AudioUtil {

    private AudioManager mAudioManager;
    private static AudioUtil mInstance;

    private AudioUtil() {
        try {
            mAudioManager = (AudioManager) BaseApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        }catch (Exception e){
            mAudioManager=null;
        }

    }

    public synchronized static AudioUtil getInstance() {
        if (mInstance == null) {
            mInstance = new AudioUtil();
        }
        return mInstance;
    }

    //获取多媒体最大音量
    public int getMediaMaxVolume() {
        try {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }catch (Exception e){
            return -1;
        }

    }

    //获取多媒体音量
    public int getMediaVolume() {
        try {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }catch (Exception e){
            return -1;
        }

    }

    //获取通话最大音量
    public int getCallMaxVolume() {
        try {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        }catch (Exception e){
            return -1;
        }

    }

    //获取系统音量最大值
    public int getSystemMaxVolume() {
        try {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        }catch (Exception e){
            return -1;
        }

    }

    //获取系统音量
    public int getSystemVolume() {
        try {
            int maxNumb = getSystemMaxVolume();
            int nowNumb = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
            int showNumb=nowNumb * 100 / maxNumb;
            String shareStr=MyShare.getInstance().getString(systemVolumeKey);
            if(!TextUtils.isEmpty(shareStr)){
                int share=Integer.valueOf(shareStr);
               int cha= Math.abs(share - showNumb);
               if(cha<17){
                   showNumb=share;
               }
            }

            return showNumb;
        }catch (Exception e){
            return -1;
        }

    }

    public int getVoiceNumb() {
        try {
            int maxNumb = getSystemMaxVolume();

            int nowNumb = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            return nowNumb * 100 / maxNumb;
        }catch (Exception e){
            return -1;
        }

    }

    //获取提示音量最大值
    public int getAlermMaxVolume() {
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }

    /**
     * 设置多媒体音量
     * 这里我只写了多媒体和通话的音量调节，其他的只是参数不同，大家可仿照
     */
    private final String systemVolumeKey="SystemVolume";
    public void setSystemVolume(int volume) {
        try {
            int needVolume = getSystemMaxVolume();
            if (volume <= 0) {
                needVolume = 0;
            } else if (volume > 0 && volume < 100) {
                needVolume = needVolume * volume / 100;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, //音量类型
                    needVolume,
                    AudioManager.FLAG_PLAY_SOUND
                            | AudioManager.FLAG_SHOW_UI);
            MyShare.getInstance().putString(systemVolumeKey,volume+"");
        } catch (Exception e) {
        }

    }

    /**
     * 设置多媒体音量
     * 这里我只写了多媒体和通话的音量调节，其他的只是参数不同，大家可仿照
     */
    public void setMediaVolume(int volume) {
        try {
            int needVolume = getMediaMaxVolume();
            if (volume <= 0) {
                needVolume = 0;
            } else if (volume > 0 && volume < 100) {

                needVolume = needVolume * volume / 100;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, //音量类型
                    needVolume,
                    AudioManager.FLAG_PLAY_SOUND
                            | AudioManager.FLAG_SHOW_UI);
        } catch (Exception e) {
        }
    }

    //设置通话音量
    public void setCallVolume(int volume) {
        try {
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                    volume,
                    AudioManager.STREAM_VOICE_CALL);
        }catch (Exception e){}

    }

    // 关闭/打开扬声器播放
    public void setSpeakerStatus(boolean on) {
        try {
            if (on) { //扬声器
                mAudioManager.setSpeakerphoneOn(true);
                mAudioManager.setMode(AudioManager.MODE_NORMAL);
            } else {
                // 设置最大音量
                int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
                mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, max, AudioManager.STREAM_VOICE_CALL);
                // 设置成听筒模式
                mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                mAudioManager.setSpeakerphoneOn(false);// 关闭扬声器
                mAudioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
            }
        }catch (Exception e){

        }

    }




}
