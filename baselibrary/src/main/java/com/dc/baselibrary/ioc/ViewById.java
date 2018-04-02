package com.dc.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 43497 on 2018/4/2.
 */

//代表Annotation的位置 FIELD放在属性上 METHOD放在方法上 TYPE类上 CONSTRUCTOR构造函数上
@Target(ElementType.FIELD)
//什么时候生效 CLASS编译时 RUNTIME运行时 SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {

    int value();
}
