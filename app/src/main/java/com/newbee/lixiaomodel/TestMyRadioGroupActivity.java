package com.newbee.lixiaomodel;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.view.radiogroup.MyRadioDataInfoBean;
import com.lixiao.view.radiogroup.MyRadioGroup;
import com.nrmyw.launcher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/24 0024 13:51
 */
public class TestMyRadioGroupActivity extends BaseCompatActivity {
    private MyRadioGroup myRadioGroup;
    private MyRadioGroup.ItemClick itemClick=new MyRadioGroup.ItemClick() {
        @Override
        public void initSelect(MyRadioDataInfoBean dataInfoBean) {
            LG.i(tag,"-----------initSelect:"+dataInfoBean);
        }

        @Override
        public void nowSelect(MyRadioDataInfoBean dataInfoBean) {
            LG.i(tag,"-----------kankannowdatainfoBean:"+dataInfoBean);
        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_test_radiogroup;
    }

    @Override
    public void initView() {
            myRadioGroup=findViewById(R.id.mrg);
    }

    @Override
    public void initData() {
        myRadioGroup.setListen(itemClick);
        List<MyRadioDataInfoBean> list=new ArrayList<>();
        for(int i=0;i<3;i++){
            MyRadioDataInfoBean dataInfoBean1=  new MyRadioDataInfoBean();
            switch (i%3){
                case 0:
                    dataInfoBean1.setBgRs(R.drawable.launcher_app_list);
                    break;
                case 1:
                    dataInfoBean1.setBgRs(R.drawable.launcher_app_list);
                    break;
                case 2:
                    dataInfoBean1.setBgRs(R.drawable.launcher_app_list);
                    break;
            }
            dataInfoBean1.setType(i+"");
            dataInfoBean1.setShowText(i+"");
            list.add(dataInfoBean1);
        }
        myRadioGroup.setList(list,"2");
    }

    @Override
    public void initControl() {
    }

    @Override
    public void closeActivity() {
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
