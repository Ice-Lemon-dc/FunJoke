package com.dc.funjoke;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dc.baselibrary.ioc.CheckNet;
import com.dc.baselibrary.ioc.OnClick;
import com.dc.baselibrary.ioc.ViewById;
import com.dc.framelibrary.BaseSkinActivity;
import com.dc.framelibrary.ExceptionCrashHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * @author 43497
 */
public class MainActivity extends BaseSkinActivity {

    @ViewById(R.id.text_tv)
    private TextView mTextView;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        mTextView.setText("Android");
    }

    @Override
    protected void initData() {


        //获取上次的崩溃信息上传到服务器
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
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
    }


    @OnClick(R.id.text_tv)
    @CheckNet
    public void login(View view) {
        Toast.makeText(this, "tv===", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.text_iv)
    private void textIvClick() {
        Toast.makeText(this, "iv+++", Toast.LENGTH_SHORT).show();
    }
}
