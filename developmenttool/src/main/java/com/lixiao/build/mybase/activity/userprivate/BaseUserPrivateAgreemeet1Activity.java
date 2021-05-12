package com.lixiao.build.mybase.activity.userprivate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.mybase.activity.userprivate.bean.UserPrivateAgreemeetInfoBean;
import com.lixiao.developmenttool.R;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/10/20 0020 18:09
 */
public  abstract class BaseUserPrivateAgreemeet1Activity extends BaseCompatActivity {

    public abstract UserPrivateAgreemeetInfoBean getUserPrivateAgreemeetInfo();

    private TextView userPrivateBodyTV;
    private ImageView backIV;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_user_private;
    }

    @Override
    public void initView() {
        userPrivateBodyTV=findViewById(R.id.tv_user_private_body);
        backIV=findViewById(R.id.iv_back);
        backIV.setImageResource(R.drawable.icon_back);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        TextView titleTv=findViewById(R.id.tv_private);
        titleTv.setText(R.string.user_private_agree);
        UserPrivateAgreemeetInfoBean userPrivateAgreemeetInfoBean=getUserPrivateAgreemeetInfo();
        if(null==userPrivateAgreemeetInfoBean){
            userPrivateAgreemeetInfoBean=new UserPrivateAgreemeetInfoBean();
        }
        String appName=userPrivateAgreemeetInfoBean.getAppName();
        String companyName=userPrivateAgreemeetInfoBean.getComPanyName();
        String time=userPrivateAgreemeetInfoBean.getTime();

        String priveStr ="用户个人隐私保密协议\n" +
                "尊敬的用户，欢迎阅读本协议：\n" +
                "\n" +
                appName+"依据本协议的规定提供服务，本协议具有合同效力。您必须完全同意以下所有条款并完成个人资料的填写，才能保证享受到更好的"+appName+"客服服务。您使用服务的行为将视为对本协议的接受，并同意接受本协议各项条款的约束。\n" +
                "\n" +
                "用户必须合法使用网络服务，不作非法用途，自觉维护本软件的声誉，遵守所有使用网络服务的网络协议、规定、程序和惯例。\n" +
                "\n" +
                "为更好的为用户服务，用户应向本软件提供真实、准确的个人资料，个人资料如有变更，应立即修正。如因用户提供的个人资料不实或不准确，给用户自身造成任何性质的损失，均由用户自行承担。\n" +
                "\n" +
                "尊重个人隐私是"+appName+"的责任，"+appName+"在未经用户授权时不得向第三方（"+companyName+"公司控股或关联、运营合作单位除外）公开、编辑或透露用户个人资料的内容，但由于政府要求、法律政策需要等原因除外。在用户发送信息的过程中和本软件收到信息后，本软件将遵守行业通用的标准来保护用户的私人信息。但是任何通过因特网发送的信息或电子版本的存储方式都无法确保100%的安全性。因此，本软件会尽力使用商业上可接受的方式来保护用户的个人信息，但不对用户信息的安全作任何担保。\n" +
                "\n" +
                "本软件有权在必要时修改服务条例，本软件的服务条例一旦发生变动，将会在本软件的重要页面上提示修改内容，用户如不同意新的修改内容，须立即停止使用本协议约定的服务，否则视为用户完全同意并接受新的修改内容。根据客观情况及经营方针的变化，本软件有中断或停止服务的权利，用户对此表示理解并完全认同。\n" +
                "\n" +
                "本保密协议的解释权归"+companyName+"公司所有。\n" +
                "\n" +
                companyName+"公司\n" +
                "\n" + time;
        userPrivateBodyTV.setText(priveStr);

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
