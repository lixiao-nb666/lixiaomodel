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
public abstract class BaseUserPrivateAgreemeet2Activity extends BaseCompatActivity {

    public abstract UserPrivateAgreemeetInfoBean getUserPrivateAgreemeetInfo();
    private ImageView backIV;
    private TextView userPrivateBodyTV;
    private String priveStr;

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
        titleTv.setText(R.string.user_private_policy);
        UserPrivateAgreemeetInfoBean userPrivateAgreemeetInfoBean=getUserPrivateAgreemeetInfo();
        if(null==userPrivateAgreemeetInfoBean){
            userPrivateAgreemeetInfoBean=new UserPrivateAgreemeetInfoBean();
        }
        String appName=userPrivateAgreemeetInfoBean.getAppName();
        String companyName=userPrivateAgreemeetInfoBean.getComPanyName();
        String time=userPrivateAgreemeetInfoBean.getTime();

        priveStr= appName+"尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，本软件会按照本隐私权政策的规定使用和披露您的个人信息。但本软件将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，本软件不会将这些信息对外披露或向第三方提供。本软件会不时更新本隐私权政策。 您在同意本软件服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于本软件服务使用协议不可分割的一部分。\n" +
                "\n" +
                "1. 适用范围\n" +
                "\n" +
                "a) 在您注册"+appName+"帐号时，您根据"+appName+"要求提供的个人注册信息；\n" +
                "\n" +
                "b) 在您使用"+appName+"网络服务，或访问"+appName+"网页时，"+appName+"自动接收并记录的您的浏览器和计算机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；\n" +
                "\n" +
                "c) "+appName+"通过合法途径从商业伙伴处取得的用户个人数据。\n" +
                "\n" +
                "您了解并同意，以下信息不适用本隐私权政策：\n" +
                "\n" +
                "a) 您在使用"+appName+"平台提供的搜索服务时输入的关键字信息；\n" +
                "\n" +
                "b) "+appName+"收集到的您在"+appName+"发布的有关信息数据，包括但不限于参与活动、成交信息及评价详情；\n" +
                "\n" +
                "c) 违反法律规定或违反"+appName+"规则行为及"+appName+"已对您采取的措施。\n" +
                "\n" +
                "2. 信息使用\n" +
                "\n" +
                "a) "+appName+"不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和"+appName+"（含"+appName+"关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。\n" +
                "\n" +
                "b) "+appName+"亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何"+appName+"平台用户如从事上述活动，一经发现，"+appName+"有权立即终止与该用户的服务协议。\n" +
                "\n" +
                "c) 为服务用户的目的，"+appName+"可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与"+appName+"合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。\n" +
                "\n" +
                "3. 信息披露\n" +
                "\n" +
                "在如下情况下，"+appName+"将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：\n" +
                "\n" +
                "a) 经您事先同意，向第三方披露；\n" +
                "\n" +
                "b) 为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；\n" +
                "\n" +
                "c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；\n" +
                "\n" +
                "d) 如您出现违反中国有关法律、法规或者"+appName+"服务协议或相关规则的情况，需要向第三方披露；\n" +
                "\n" +
                "e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；\n" +
                "\n" +
                "f) 在"+appName+"平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，"+appName+"有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。\n" +
                "\n" +
                "g) 其它"+appName+"根据法律、法规或者软件政策认为合适的披露。\n" +
                "\n" +
                "4. 信息存储和交换\n" +
                "\n" +
                appName+"收集的有关您的信息和资料将保存在"+appName+"及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或"+appName+"收集信息和资料所在地的境外并在境外被访问、存储和展示。\n" +
                "\n" +
                "5. Cookie的使用\n" +
                "\n" +
                "a) 在您未拒绝接受cookies的情况下，"+appName+"会在您的计算机上设定或取用"+appName+"\n" +
                "\n" +
                "，以便您能登录或使用依赖于cookies的"+appName+"平台服务或功能。"+appName+"使用cookies可为您提供更加周到的个性化服务，包括推广服务。  b) 您有权选择接受或拒绝接受cookies。您可以通过修改浏览器设置的方式拒绝接受cookies。但如果您选择拒绝接受cookies，则您可能无法登录或使用依赖于cookies的"+appName+"网络服务或功能。\n" +
                "\n" +
                "c) 通过"+appName+"所设cookies所取得的有关信息，将适用本政策。\n" +
                "\n" +
                "6. 信息安全\n" +
                "\n" +
                "a) "+appName+"帐号均有安全保护功能，请妥善保管您的用户名及密码信息。本软件将通过对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。\n" +
                "\n" +
                "b) 在使用"+appName+"网络服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是"+appName+"用户名及密码发生泄露，请您立即联络"+appName+"客服，以便"+appName+"采取相应措施。\n"   +
                "本保密协议的解释权归"+companyName+"公司所有。\n" +
                "\n" +
                companyName+"公司\n" +
                "\n" +
                time;;
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
