package com.example.b2c.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.b2c.R;
import com.example.b2c.helper.SPHelper;
import com.example.b2c.net.response.translate.MobileStaticTextCode;
import com.example.b2c.net.response.translate.OptionList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用途：
 * Created by milk on 16/9/1.
 * 邮箱：649444395@qq.com
 */
public abstract class BaseAdapter<T> extends BasePageAdapter<T> {
    protected MobileStaticTextCode mTranslatesString;
    protected OptionList optionList;

    private int viewId;

    protected Context context;

    private ArrayList<T> data;
    protected ImageLoader mImageLoader;
    protected DisplayImageOptions options;

    public BaseAdapter(Context context) {
        this.context = context;
        data = new ArrayList<T>();
        mImageLoader = ImageLoader.getInstance();
        mTranslatesString = SPHelper.getBean("translate", MobileStaticTextCode.class);
        optionList = SPHelper.getBean("options", OptionList.class);
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).showImageOnLoading(0)
                .showImageOnFail(R.drawable.ic_fail_image)
                .showImageForEmptyUri(R.drawable.ic_no_image)
                .displayer(new FadeInBitmapDisplayer(300, true, true, false))
                .build();
    }

    public BaseAdapter(Context context, int viewId) {
        this(context);
        this.viewId = viewId;
    }

    protected final Context getContext() {
        return context;
    }

    public final void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public final void addAll(Collection<? extends T> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public final void addAll(T[] items) {
        data.addAll(Arrays.asList(items));
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        T item = getItem(position);
        ViewHolderFactory viewHolderFactory = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            int viewId = getViewId(position);
            convertView = layoutInflater.inflate(viewId, parent, false);
            viewHolderFactory = new ViewHolderFactory(this, convertView);
            convertView.setTag(viewHolderFactory);
        } else {
            viewHolderFactory = (ViewHolderFactory) convertView.getTag();
        }
        renderView(viewHolderFactory, position);
        return convertView;
    }

    public abstract void renderView(ViewHolderFactory viewHolderFactory, int position);

    public int getViewId(int position) {
        return viewId;
    }

    public static class ViewHolderFactory {
        private View convertView;
        private BaseAdapter adapter;

        public ViewHolderFactory(BaseAdapter adapter, View convertView) {
            this.adapter = adapter;
            this.convertView = convertView;
            views = new SparseArray<>();
        }

        public SparseArray<View> views;

        public <T extends View> T findViewById(int viewId) {
            return findViewById(viewId, null);
        }

        public <T extends View> T findViewById(int viewId, InitViewListener initViewListener) {
            T view = (T) views.get(viewId);
            if (view == null) {
                view = (T) convertView.findViewById(viewId);
                views.put(viewId, view);
                if (initViewListener != null) {
                    initViewListener.firstInitView(view);
                }
            }
            return view;
        }

        public View getConvertView() {
            return convertView;
        }

        public Context getContext() {
            if (convertView != null) {
                return convertView.getContext();
            }
            return null;
        }

        public BaseAdapter getAdapter() {
            return adapter;
        }

        public ViewGroup.LayoutParams getLayoutParams() {
            return convertView.getLayoutParams();
        }

        public void setLayoutParams(ViewGroup.LayoutParams lp) {
            convertView.setLayoutParams(lp);
        }
    }

    public interface InitViewListener {
        void firstInitView(View view);
    }

    /**
     * 设置数据
     *
     * @param collection
     */
    public void setData(Collection<? extends T> collection) {
        clear();
        if (collection != null) {
            addAll(collection);
        }
    }

    public void setData(T... collection) {
        clear();
        if (collection != null) {
            addAll(collection);
        }
    }

    public boolean setItem(int position, T item) {
        if (data != null && position < data.size()) {
            data.set(position, item);
            return true;
        }
        return false;
    }

    /**
     * 设置数据
     *
     * @param collection
     */
    public void setData(List<? extends T> collection) {
        clear();
        if (collection != null) {
            addAll(collection);
        }
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < data.size()) {
            return data.get(position);
        } else {
            return null;
        }
    }

    public void removeAtPosition(int position) {
        if (data != null && position < data.size()) {
            data.remove(position);
        }
    }

    public void removeItem(T item) {
        if (data != null) {
            data.remove(item);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void add(T item) {
        data.add(item);
    }

    public void addAll(int location, Collection<T> items) {
        data.addAll(location, items);
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        return getItemViewType(item);
    }

    public int getItemViewType(T item) {
        return 0;
    }

    public List<T> getData() {
        return data;
    }

    /**
     * @param ：计算parentlistview item的高度。
     *                          无论innerlistview有多少个item，则只会显示一个item。
     **/
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
