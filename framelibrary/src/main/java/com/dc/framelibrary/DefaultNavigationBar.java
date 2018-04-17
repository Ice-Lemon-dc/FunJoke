package com.dc.framelibrary;

import android.content.Context;
import android.view.ViewGroup;

import com.dc.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Created by 43497 on 2018/4/17.
 */

public class DefaultNavigationBar extends AbsNavigationBar{

    public DefaultNavigationBar(Builder.AbsNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {

    }

    public static class Builder extends AbsNavigationBar.Builder{

        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context,parent);
        }

        @Override
        public AbsNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        public static class DefaultNavigationParams extends AbsNavigationParams{



            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }

    }
}
