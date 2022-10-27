package com.nrmyw.launcher;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.adapter.SystemAppsListAdapter;
import com.lixiao.build.util.systemapp.bean.ResultSystemAppInfoBean;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.build.util.systemapp.observer.PackageManagerObserver;
import com.lixiao.build.util.systemapp.observer.PackageManagerSubscriptionSubject;
import com.lixiao.build.util.systemapp.observer.PackageManagerType;
import com.nrmyw.launcher.adapter.MainLauncherIconAdapter;
import com.nrmyw.launcher.util.LiXiaoBaiDuBoardCastUtil;
import com.nrmyw.launcher.util.UrlToFilePathUtil;
import com.nrmyw.launcher.view.CardScaleHelper;

public class MainLauncherActivity extends BaseCompatActivity {

    private final int init_list = 1;
    private final int set_tv = 2;


    private PackageManagerObserver packageManagerObserver=new PackageManagerObserver() {
        @Override
        public void update(PackageManagerType eventBs, Object object) {
            switch (eventBs){
                case GET_SYSTEM_APPS:
                    Message message=new Message();
                    message.what=init_list;
                    message.obj=object;
                    viewHandler.sendMessageDelayed(message,0);
                    break;
                case ERR:
                    break;
            }
        }
    };

    private Runnable setIndexRunnable=new Runnable() {
        @Override
        public void run() {
//            appLV.scrollToPosition(0);
            mCardScaleHelper.onScrolledChangedCallback();
        }
    };

    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case init_list:
                    ResultSystemAppInfoBean resultSystemAppInfoBean= (ResultSystemAppInfoBean) msg.obj;
                    adapter.setData(resultSystemAppInfoBean.getAppList());
                    viewHandler.removeCallbacks(setIndexRunnable);
                    viewHandler.postDelayed(setIndexRunnable,100);
                    break;
                case set_tv:
                   /* SystemAppInfoBean appInfoBean= (SystemAppInfoBean) msg.obj;
                    titleTV.setText("nrmyw("+appInfoBean.getName()+")");*/
                    break;
            }
        }
    };
    private RecyclerView appLV;
    private CardScaleHelper mCardScaleHelper;
    private MainLauncherIconAdapter adapter;
    private MainLauncherIconAdapter.ItemClick itemChick=new MainLauncherIconAdapter.ItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean appInfoBean) {
            if(null!=appInfoBean){
                Message message=new Message();
                message.what=set_tv;
                message.obj=appInfoBean;
                viewHandler.sendMessageDelayed(message,0);
                LiXiaoBaiDuBoardCastUtil.sendNewBeeBaiDuTtsStr(context,"how are you ");

            }
        }
    };
    private int w= 1200 ;
    private TextView titleTV;
    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_launcher;
    }

    @Override
    public void initView() {
        appLV=findViewById(R.id.lv);
        titleTV=findViewById(R.id.tv_title);
//        appLV.post(new Runnable() {
//            @Override
//            public void run() {
//               w=  appLV.getMeasuredWidth();
//            }
//        });
    }



    @Override
    public void initData() {
        adapter=new MainLauncherIconAdapter(context,itemChick);
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        appLV.setLayoutManager(linearLayoutManager);
        appLV.setAdapter(adapter);
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(1);
        mCardScaleHelper.attachToRecyclerView(appLV);
//        appLV.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState== RecyclerView.SCROLL_STATE_IDLE ){
//                    if (recyclerView != null && recyclerView.getChildCount() > 0) {
////                        try {
////                            int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
////                            int needW=w/6;
////                            if(x>0){
////                                if(x>=needW){
////
////
////                                    currentPosition++;
////                                    if(currentPosition>=adapter.getItemCount()){
////                                        currentPosition=adapter.getItemCount()-1;
////                                    }
////                                }
////                            }else {
////                                if(x<=-needW){
////
////                                }else {
////                                    if(currentPosition!=0){
////                                        currentPosition++;
////                                    }
////                                }
////                            }
////                            appLV.scrollToPosition(currentPosition);
////                            x=0;
////                            Log.e("=====currentPosition", "" + currentPosition);
////                            adapter.getItemData(currentPosition);
////                        } catch (Exception e) {
////                        }
//                    }
//
//                }
//            }
//
//            int x=0;
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                x+=dx;
//                Log.e("=====onScrolled", "" + x);
//            }
//        });

    }


    @Override
    public void initControl() {
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        appLV.setLayoutManager(linearLayoutManager);
//        appLV.setAdapter(adapter);
        initRecyclerView();
        PackageManagerSubscriptionSubject.getInstance().addObserver(packageManagerObserver);
        PackageManagerUtil.getInstance().toGetSystemApps();
    }

    @Override
    public void closeActivity() {
        viewHandler.removeCallbacksAndMessages(null);
        PackageManagerSubscriptionSubject.getInstance().removeObserver(packageManagerObserver);
    }



    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
}
