package com.foorich.auscashier.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foorich.auscashier.R;


/**
 * Created by SamZhao on 2017/5/2.
 */

public class ViewHolder  extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageViewCheckMark;

    public ViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageViewCheckMark = (ImageView) itemView.findViewById(R.id.imageViewCheckMark);
    }
}
