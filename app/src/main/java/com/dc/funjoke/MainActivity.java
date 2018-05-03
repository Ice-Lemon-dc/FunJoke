package com.dc.funjoke;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dc.baselibrary.fixbug.FixDexManager;
import com.dc.baselibrary.http.HttpUtils;
import com.dc.baselibrary.http.OkHttpEngine;
import com.dc.baselibrary.ioc.ViewById;
import com.dc.framelibrary.BaseSkinActivity;
import com.dc.framelibrary.HttpCallBack;
import com.dc.funjoke.mode.DiscoverListResult;

import java.io.File;
import java.io.IOException;


/**
 * @author 43497
 */
public class MainActivity extends BaseSkinActivity implements View.OnClickListener {

    @ViewById(R.id.text_tv)
    private Button mButton;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        viewById(R.id.text_tv).setOnClickListener(this);
    }

    @Override
    protected void initData() {


        //获取上次的崩溃信息上传到服务器
 /*       File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if(crashFile.exists()){
            //上传到服务器
            try {
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));

                char[] buffer = new char[1024];
                int len = 0;
                while ((len = fileReader.read(buffer)) != -1){
                    String str = new String(buffer,0,len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

        //每次启动时 去后台获取差分包 然后修复本地的Bug

        //aliFixBug();

        fixDexBug();

        HttpUtils.with(this).url("http://is.snssdk.com/2/essay/discovery/v3/?")
                .addParam("iid", "6152551759")
                .addParam("aid", "7")
                //切换引擎
                .exchangeEngine(new OkHttpEngine())
                .get()
                .execute(new HttpCallBack<DiscoverListResult>() {

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }


                    @Override
                    public void onSuccess(DiscoverListResult result) {
                        Log.e("TAG", result.toString());

                        //取消进度条
                    }

                    @Override
                    public void onPreExecute() {
                        //加载进度条
                    }
                });
    }

    /**
     * 自行修复
     */
    private void fixDexBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "测试" + 2 / 0, Toast.LENGTH_SHORT).show();
    }

    /**
     * 阿里修复
     */
    private void aliFixBug() {

        //测试 直接获取本地内存卡里面的 fix.apatch
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            //修复bug
            try {
                BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

/*
    @OnClick(R.id.text_tv)
    @CheckNet
    public void login(View view) {
        Toast.makeText(this, "tv===", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.text_iv)
    private void textIvClick() {
        Toast.makeText(this, "iv+++", Toast.LENGTH_SHORT).show();
    }*/


}
