package com.fengyun.hanzhifengyun.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fengyun.hanzhifengyun.R;
import com.hanzhifengyun.rxbus.OnSubscribe;
import com.hanzhifengyun.rxbus.RxBus;
import com.hanzhifengyun.rxbus.ThreadType;

import java.util.ArrayList;
import java.util.List;


public class RxBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus);
        RxBus.getInstance().register(this);
    }


    public void test1005(View view){
        RxBus.getInstance().send(1005);
    }
    public void test1005String(View view){
        RxBus.getInstance().send(1005,"字符串数据");
    }
    public void test1005Int(View view){
        int a = 11;
        long b = 22;
        double c = 33.33;
        RxBus.getInstance().send(1005,a);
        RxBus.getInstance().send(1005,b);
        RxBus.getInstance().send(1005,c);
        RxBus.getInstance().send(1005,true);
    }
    public void test1005Object(View view){
        RxBus.getInstance().send(1005,getData());
    }

    private List<String> getData() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stringList.add("dkd" + i);
        }
        return stringList;
    }

    public void test1007(View view){
        RxBus.getInstance().send(1007);
    }
    public void test1007String(View view){
        RxBus.getInstance().send(1007,"字符串数据");
    }
    public void test1007Int(View view){
        int a = 11;
        long b = 22;
        double c = 33.33;
        boolean d = true;
        RxBus.getInstance().send(1007,a);
        RxBus.getInstance().send(1007,b);
        RxBus.getInstance().send(1007,c);
        RxBus.getInstance().send(1007,d);
    }
    public void test1007Object(View view){
        RxBus.getInstance().send(1007, getData());
    }

    public void register(View view) {
        RxBus.getInstance().register(this);
    }

    public void unRegister(View view) {
        RxBus.getInstance().unRegister(this);
    }

//    @OnSubscribe(code = 1005,
//            threadType = ThreadType.UI)
//    public void receive1005(){
//        Log.i("rxbus_log","code  1005 ");
//    }
//
//    @OnSubscribe(code = 1005,
//            threadType = ThreadType.CURRENT_THREAD)
//    public void receive1005(String msg){
//        Log.i("rxbus_log","code  1005  String:"+msg);
//    }
    @OnSubscribe(code = 1005)
    public void receive1005(int msg){
        Log.i("rxbus_log","code  1005  int:"+msg);
    }

    @OnSubscribe(code = 1005)
    public void receive1005Long(long msg){
        Log.i("rxbus_log","code  1005  long:"+msg);
    }

    @OnSubscribe(code = 1005, threadType = ThreadType.UI)
    public void receive1005Double(double msg){
        Log.i("rxbus_log","code  1005  double:"+msg);
    }

    @OnSubscribe(code = 1005)
    public void receive1005Boolean(boolean msg){
        Log.i("rxbus_log","code  1005  boolean:"+msg);
    }


//    @OnSubscribe(code = 1005)
//    public void receive1005(ArrayList msg){
//        Log.i("rxbus_log","code  1005  data:"+msg);
//    }
//
//    @OnSubscribe(code = 1007)
//    public void receive1007(){
//        Log.i("rxbus_log","code  1007 ");
//    }
//
//    @OnSubscribe(code = 1007)
//    public void receive1007(String msg){
//        Log.i("rxbus_log","code  1007  String:"+msg);
//    }
//    @OnSubscribe(code = 1007)
//    public void receive1007(Integer msg){
//        Log.i("rxbus_log","code  1007  int:"+msg);
//    }
//    @OnSubscribe(code = 1007)
//    public void receive1007(ArrayList msg){
//        Log.i("rxbus_log","code  1007  data:"+msg);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }
}
