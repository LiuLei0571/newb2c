package com.example.b2c.fragment;

import java.util.HashMap;

/**
 * 买家fragment的工厂
 */

public class BuyerFactoryFragment {
    private HashMap<Integer,BaseFragment>fragmentMap=new HashMap<Integer, BaseFragment>();
    public BaseFragment getFragment(int position){
        BaseFragment baseFragment = fragmentMap.get(position);
        if (baseFragment==null){
        switch (position){
            case 0:
                baseFragment=new HomeIndexFragmentNew();
                break;
            case 1:
                baseFragment=new HomeClassifyFragment();
                break;
            case 2:
                baseFragment=new HomeCartFragment();
                break;
            case 3:
                baseFragment=new HomeCollectionFragment();
                break;
            case 4:
                baseFragment=new HomeMineFragment();
                break;
        }
            fragmentMap.put(position,baseFragment);
        }
        return baseFragment;
    }
}
