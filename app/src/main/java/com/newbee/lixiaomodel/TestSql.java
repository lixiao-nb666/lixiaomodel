package com.newbee.lixiaomodel;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.sqlite.BaseSqlServer;
import com.lixiao.build.mybase.sqlite.config.BaseSqliteConfig;
import com.lixiao.build.mybase.sqlite.event.SqlListenObserver;
import com.lixiao.build.mybase.sqlite.event.SqlListenSubscriptionSubject;

import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/18 0018 14:38
 */
public class TestSql extends BaseSqlServer {
    private final String tag=getClass().getSimpleName()+">>>>";
    private static TestSql testSql;
    private SqlListenObserver sqlListenObserver=new SqlListenObserver() {
        @Override
        public void add(Class cls, Object object, boolean isOk) {
            LG.i(tag,"add:"+(DemoBean)object);
        }

        @Override
        public void update(Class cls, Object object, boolean isOk) {
            LG.i(tag,"update:"+isOk+"---"+(DemoBean)object);
        }

        @Override
        public void err(String errStr) {
            LG.i(tag,"err:"+errStr);
        }

        @Override
        public void remove(Class cls, String key, String vue, boolean isOk) {
            LG.i(tag,"remove:"+isOk+"--"+key+"---"+vue);
        }

    };

    private TestSql(Object o) {
        super(o);
        setNeedSendEvent(true);
        SqlListenSubscriptionSubject.getInstence().attach(sqlListenObserver);
    }

    public static TestSql getInstance(){
        if(null==testSql){
            synchronized (TestSql.class){
                if(null==testSql){
                    testSql=new TestSql(new DemoBean());
                }
            }
        }
        return testSql;
    }

    @Override
    public void close() {
        super.close();
        SqlListenSubscriptionSubject.getInstence().detach(sqlListenObserver);
    }

    public void add(){
        for(int i=0;i<100;i++){
            DemoBean demoBean=new DemoBean();
            demoBean.setDemo(true);
            demoBean.setDemoBoolean(false);
            demoBean.setDemoStr1("111fdsfsafsaf111__"+i);
            demoBean.setDemoStr("2fsdfsaf2__"+i);
            demoBean.setDemoInt(i);
            demoBean.setDemoLong(i*i+i);
            DemoBean db= (DemoBean) add(demoBean);

        }
    }

    public void remove(){
        delectById(22);
        delect("id","3302");
        que11();
    }

    public void que11(){
//        LG.i(toString(),"que:222");
//        DemoBean demoBean=new DemoBean();
//        demoBean.setId(3301);
//        demoBean.setDemo(true);
//        demoBean.setDemoBoolean(false);
//        demoBean.setDemoStr1("lixiaosandaye11111111111");
//        demoBean.setDemoStr("lixiaodaye");
//        update(demoBean);
//        String keyS[]={"demoInt"};
//        String vueS[]={"45"};
//        String fuzzyKeyS[]={"id"};
//        String fuzzyVueS[]={"7"};
        String keyS[]={};
        String vueS[]={};
        String fuzzyKeyS[]={"demoStr"};
        String fuzzyVueS[]={"45"};
        List<DemoBean> list=queAndfuzzyQue(keyS,vueS,fuzzyKeyS,fuzzyVueS);
        if(null!=list){
            for(DemoBean dd:list){
                LG.i(tag,"queqqqqqqqqqqqqqq:"+dd);
            }
        }

    }

}
