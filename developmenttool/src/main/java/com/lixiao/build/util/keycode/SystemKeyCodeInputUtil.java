package com.lixiao.build.util.keycode;


import android.app.Instrumentation;
import android.hardware.input.InputManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import com.lixiao.build.mybase.LG;
import com.lixiao.build.util.root.RootShellCmd;
import com.lixiao.build.util.root.RootUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/12 0012 17:35
 */
public class SystemKeyCodeInputUtil {

    public static void inputKeyCode(final int keyCode){

        new Thread(new Runnable() {
            @Override
            public void run() {
                LG.i("inputKeyCode","inputKeyCode:"+keyCode);
                try {
//                    RootShellCmd.getInstance().simulateKey(keyCode);
//                    RootUtil.doRootCmd("input keyevent "+keyCode);
                    inputEvent(keyCode);
//            Runtime runtime = Runtime.getRuntime();
//            runtime.exec("input keyevent " + keyCode);
//            long now=System.currentTimeMillis();
//            invokeInjectInputEvent(new KeyEvent(now,now,KeyEvent.ACTION_DOWN,keyCode,0));
//            invokeInjectInputEvent(new KeyEvent(now,now,KeyEvent.ACTION_UP,keyCode,0));
                }catch (Exception e){
                    LG.i("inputKeyCode","inputKeyCode:"+e.toString());
                }
//        try {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Instrumentation inst = new Instrumentation();
//                        // KeyEvent.KEYCODE_DPAD_DOWN 向下跳格
//                        inst.sendKeyDownUpSync(keyCode);
//                    }catch (Exception e){
//                        LG.i("inputKeyCode","inputKeyCode:1--"+e.toString());
//                    }
//                }
//            }).start();
//        } catch (Exception e) {
//            LG.i("inputKeyCode","inputKeyCode:"+e.toString());
//        }
            }
        }).start();

    }

    private static void inputEvent(int keyCode){
        Instrumentation inst = new Instrumentation();
        inst.sendKeyDownUpSync(keyCode);

    }


//    private static void invokeInjectInputEvent(KeyEvent inputEvent) {
//        Class cl = InputManager.class;
//        try {
//            Method method = cl.getMethod("getInstance");
//            Object result = method.invoke(cl);
//            InputManager im = (InputManager) result;
//            method = cl.getMethod("injectInputEvent", InputEvent.class, int.class);
//            method.invoke(im, inputEvent, 0);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }  catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
}
