package com.dc.baselibrary.navigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 43497 on 2018/4/17.
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INvaigationBar {

    private P mParams;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    private void createAndBindView() {
        View navigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

        mParams.mParent.addView(navigationView,0);

        applyView();

    }



    public static abstract class Builder {



        public Builder(Context context, ViewGroup parent) {

        }


        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {

            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }
    }

}
