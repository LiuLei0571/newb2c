package com.example.b2c.temple;


import com.example.b2c.activity.IAct;
import com.example.b2c.temple.impl.BannerTemplate;
import com.example.b2c.temple.impl.CategoryInfoTemplate;
import com.example.b2c.temple.impl.FourFourTemplate;
import com.example.b2c.temple.impl.FourTemplate;
import com.example.b2c.temple.impl.LocalDriverTemplate;
import com.example.b2c.temple.impl.LocalEmptyTemplate;
import com.example.b2c.temple.impl.TwoTwoTemplate;

import java.lang.reflect.Constructor;

/**
 * Created by milk on 2016/10/26.
 */

public enum Templates {

    P_BN(FourTemplate.class),// 四快或者更多N
    P_BANNER(BannerTemplate.class),//轮播图
    P_BLOCK(CategoryInfoTemplate.class), // 分类区域
    P_T2B2(TwoTwoTemplate.class), //大牌推荐
    P_T4B4(FourFourTemplate.class),
    LOCAL_EMPTY(LocalEmptyTemplate.class, false),//空模板
    LOCAL_DRIVER(LocalDriverTemplate.class);// 分割线

    Templates(Class<? extends ITemplate> templateCls) {
        this.templateCls = templateCls;
    }

    Templates(Class<? extends ITemplate> templateCls, boolean inOneRow) {
        this.templateCls = templateCls;
        this.inOneRow = inOneRow;
    }

    /**
     * 布局跨所有的列
     */
    private boolean inOneRow = true;
    private Class<? extends ITemplate> templateCls;

    public int getLocalViewType() {
        return ordinal();
    }

    public boolean isInOneRow() {
        return inOneRow;
    }

    /**
     * @param remoteViewType
     * @return
     */
    public static Class<? extends ITemplate> findViewMaker(String remoteViewType) {
        Templates template = null;
        try {
            template = valueOf(remoteViewType);
        } catch (Throwable throwable) {
        }
        if (template == null) {
            template = LOCAL_EMPTY;
        }
        return template.templateCls;
    }

    public static Templates findTemplate(String remoteViewType) {
        Templates template = null;
        try {
            template = valueOf(remoteViewType);
        } catch (Throwable throwable) {
        }
        if (template == null) {
            template = LOCAL_EMPTY;
        } else {
        }
        return template;
    }


    public static Class<? extends ITemplate> findViewMaker(int localViewType) {
        Templates template = null;
        try {
            template = Templates.values()[localViewType];
        } catch (Throwable throwable) {
        }
        if (template == null) {
            template = LOCAL_EMPTY;
        }
        return template.templateCls;
    }

    /**
     * 服务端的type转化为本地的type
     *
     * @param remoteViewType
     * @return
     */
    public static int findLocalViewType(String remoteViewType) {
        Templates template = null;
        try {
            template = valueOf(remoteViewType);
        } catch (Throwable throwable) {
        }
        if (template == null) {
            template = LOCAL_EMPTY;
        }
        return template.ordinal();
    }

    public static ITemplate makeInstance(IAct act, int localViewType) {
        ITemplate instance = null;
        Class<? extends ITemplate> cls = findViewMaker(localViewType);
        if (cls == null) {
            cls = LOCAL_EMPTY.templateCls;
        }
        try {
            Constructor<? extends ITemplate> constructor = cls.getDeclaredConstructor(IAct.class);
            instance = constructor.newInstance(act);
        } catch (Throwable throwable) {
        }
        return instance;
    }

}
