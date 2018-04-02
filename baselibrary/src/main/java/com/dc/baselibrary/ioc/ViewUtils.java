package com.dc.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 43497 on 2018/4/2.
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * @param finder
     * @param object 反射需要执行的类
     */
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类里面所有的属性
        Class<?> clazz = object.getClass();
        //获取所有属性包括私有和共有
        Field[] fields = clazz.getDeclaredFields();

        //2.获取ViewById的里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的id值
                int viewId = viewById.value();
                //3.findViewById找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //能够注入所有修饰符
                    field.setAccessible(true);
                    //4.动态的注入找到的View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取OnClick的里面的value
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById找到View
                    View view = finder.findViewById(viewId);

                    //扩展功能 检查网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {
                        //4.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;


        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }


        @Override
        public void onClick(View v) {
            //是否需要检查网络
            if (mIsCheckNet) {
                if (!networkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),"您的网络不太给力",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            try {
                //所有方法都可以
                mMethod.setAccessible(true);
                //5.反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static boolean networkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
