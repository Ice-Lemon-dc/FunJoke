package com.dc.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dc.baselibrary.ioc.ViewUtils;

/**
 * Created by 43497 on 2018/4/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局
        setContentView();

        ViewUtils.inject(this);

        //初始化头部
        initTitle();

        //初始化界面
        initView();

        //初始化数据
        initData();
    }


    protected abstract void setContentView();


    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 启动Activity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected <T extends View> T viewById(int viewId){
        return (T)findViewById(viewId);
    }
}

