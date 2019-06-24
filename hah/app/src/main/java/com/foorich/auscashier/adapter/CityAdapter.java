package com.foorich.auscashier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.foorich.auscashier.R;
import com.foorich.auscashier.bean.AddressBean;

import java.util.List;

/**
 * Created by SamZhao on 2017/5/2.
 */

public class CityAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
//    private List<AddressModel.AllRegionBean.ChildCitysBean> mDataList;
    private List<AddressBean.DataBean.CityBean> mDataList;

    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private int layoutPosition;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data, int position, String regionName);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public CityAdapter(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    public void setData(List<AddressBean.DataBean.CityBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_area, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag("1");
        holder.textView.setText(mDataList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (String) holder.itemView.getTag(), layoutPosition, mDataList.get(position).getName());
            }
        });

        //更改状态
        if (position == layoutPosition) {
            holder.imageViewCheckMark.setVisibility(View.VISIBLE);
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.imageViewCheckMark.setVisibility(View.GONE);
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.black));
        }

    }
}
