package com.example.b2c.adapter.base;


import android.content.Context;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by yh on 2016/8/5.
 */
public abstract class BaseRecyclerAdapter<M, VH extends BaseHolder> extends AbsAdapter<M, VH>
        implements IPageAdapter<M> {
    private List<M> dataList;

    public BaseRecyclerAdapter(Context context) {
        super(context);
        this.dataList = new ArrayList<>();
    }

    public BaseRecyclerAdapter(Context context, Collection<M> list) {
        super(context);
        this.dataList = new ArrayList<>();
        if (list != null) {
            this.dataList.addAll(list);
        }
    }

    /**
     * 填充数据,此操作会清除原来的数据
     *
     * @param list 要填充的数据
     * @return true:填充成功并调用刷新数据
     */
    public boolean setDataWithNotify(Collection<M> list) {
        boolean result = false;
        dataList.clear();
        if (list != null) {
            result = dataList.addAll(list);
        }
        notifyDataSetChanged();
        return result;
    }

    /**
     * 追加一条数据
     *
     * @param datas 要追加的数据
     * @return true:追加成功并刷新界面
     */
    public boolean addAllWithNotify(M... datas) {
        if (datas != null) {
            List<M> list = Arrays.asList(datas);
            int start = dataList.size() - getHeaderExtraViewCount();
            if (dataList.addAll(list)) {
                notifyItemRangeInserted(start, datas.length);
                return true;
            }
        }
        return false;
    }

    /**
     * 追加集合数据
     *
     * @param list 要追加的集合数据
     * @return 追加成功并刷新
     */
    public boolean addAllWithNotify(Collection<? extends M> list) {
        if (list != null) {
            int start = dataList.size() - getHeaderExtraViewCount();
            if (dataList.addAll(list)) {
                notifyItemRangeInserted(start, list.size());
                return true;
            }
        }
        return false;
    }

    /**
     * 在最顶部前置数据
     *
     * @param datas 要前置的数据
     */
    public boolean proposeItemsWithNotify(M... datas) {
        if (datas != null) {
            List<M> list = Arrays.asList(datas);
            int start = getHeaderExtraViewCount();
            if (dataList.addAll(start, list)) {
                notifyItemRangeInserted(start, datas.length);
                return true;
            }
        }
        return false;
    }

    /**
     * 在顶部前置数据集合
     *
     * @param list 要前置的数据集合
     */
    public boolean proposeListWithNotify(Collection<M> list) {
        if (list != null) {
            int start = getHeaderExtraViewCount();
            if (dataList.addAll(start, list)) {
                notifyItemRangeInserted(start, list.size());
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return dataList.size() + getExtraViewCount();
    }

    public int getCustomerCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    /**
     * 根据位置获取一条数据
     *
     * @param position View的位置
     * @return 数据
     */
    public M getItem(int position) {
        int headerCount = getHeaderExtraViewCount();
        int realIndex = position - headerCount;//位置纠正
        if (headerCount != 0 && position == 0 || realIndex >= dataList.size()) {//头部,尾部
            return null;
        }
        return dataList.get(realIndex);
    }

    /**
     * 根据ViewHolder获取数据
     *
     * @param holder ImgHolder
     * @return 数据
     */
    public M getItem(VH holder) {
        return getItem(holder.getAdapterPosition());
    }

    /**
     * 更新数据状态
     *
     * @param datas
     * @return
     */
    public boolean updateItemsWithNotify(M... datas) {
        boolean result = false;
        if (datas != null) {
            for (M data : datas) {
                int index = dataList.indexOf(data);
                if (index != -1) {
                    dataList.set(index, data);
                    notifyItemChanged(index + getHeaderExtraViewCount());
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 移除一条数据
     *
     * @param position 位置
     */
    public boolean removeItemWithNotify(int position) {
        if (position < dataList.size() && position >= 0) {
            removeIndexWithNotify(position);
            return true;
        }
        return false;
    }

    public boolean removeItem(int position) {
        if (position < dataList.size() && position >= 0) {
            dataList.remove(position);
            return true;
        }
        return false;
    }

    public boolean removeItemFromIndex(int position) {
        int count = dataList.size();
        if (position < count) {
            dataList = dataList.subList(0, position);
            notifyItemRangeRemoved(position, count - position);
        }
        return true;
    }

    private void removeIndexWithNotify(int index) {
        dataList.remove(index);
        if (getHeaderExtraViewCount() == 0) {
            notifyItemRemoved(index);
        } else {
            notifyItemRemoved(index + 1);
        }
    }

    /**
     * 移除一条数据
     *
     * @param datas 要移除的数据
     */
    public boolean removeItemsWithNotify(M... datas) {
        boolean result = false;
        if (datas != null) {
            for (M data : datas) {
                for (int index = dataList.indexOf(data); index != -1; index = dataList.indexOf(data)) {//找到元素
                    removeIndexWithNotify(index);
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        if (dataList != null) {
            dataList.clear();
        }
    }

    public int getCount() {
        return getItemCount();
    }

    @Override
    public int getDataCount() {
        return getCustomerCount();
    }

    @Override
    public boolean isDataEmpty() {
        if (getCustomerCount() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addAll(Collection<? extends M> list) {
        if (list != null) {
            dataList.addAll(list);
        }
    }
}
