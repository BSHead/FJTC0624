package com.foorich.auscashier.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.foorich.auscashier.R;
import com.foorich.auscashier.bean.MerchantsTypeBean;

import java.util.List;

/**
 * author : YangDngHai
 * e-mail : 18201097621@163.com
 * date   : 2019-5-8 16:39
 * desc   : 商户类型
 * version: 1.0
 */
public class AllMerchantAdapter extends RecyclerView.Adapter<AllMerchantAdapter.ViewHolder> {

    public Context mContext;
    List<MerchantsTypeBean.DataBean> result;

    private OnItemClickListener mOnItemClickListener = null;

    public AllMerchantAdapter(List<MerchantsTypeBean.DataBean> list, Context mContext) {
        this.result = list;
        this.mContext = mContext;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchat, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MerchantsTypeBean.DataBean bean = result.get(position);
        holder.number.setText(bean.getService());


        final int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(finalPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }


    //内部类
    static class ViewHolder extends RecyclerView.ViewHolder {
        //行布局中的控件
        TextView number;//
//        ImageView img;//

        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            number = (TextView) itemView.findViewById(R.id.tv_type);
//            img = (ImageView) itemView.findViewById(R.id.img_bank);

        }
    }


    //设置一个Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
