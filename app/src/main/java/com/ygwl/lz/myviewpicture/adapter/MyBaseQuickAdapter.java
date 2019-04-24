package com.ygwl.lz.myviewpicture.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ygwl.lz.myviewpicture.R;
import com.ygwl.lz.myviewpicture.bean.UserViewInfo;


/**
 * Created by yangc on 2017/8/29.
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated:
 */

public class MyBaseQuickAdapter extends BaseQuickAdapter<UserViewInfo,BaseViewHolder> {
    public static final String TAG = "MyBaseQuickAdapter";
  private Context context;
    public MyBaseQuickAdapter(Context context) {
        super(R.layout.item_image);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserViewInfo item) {
        final ImageView thumbView = helper.getView(R.id.iv);
        //判断是否需要显示播放图标，即是否为视频文件
       if (item.getVideoUrl()==null){
           helper.getView(R.id.btnVideo).setVisibility(View.GONE);
       }else {
           helper.getView(R.id.btnVideo).setVisibility(View.VISIBLE);
       }

        ((TextView)helper.getView(R.id.tv)).setText(item.getUser());
        Glide.with(context)
                .load(item.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_iamge_zhanwei)
                .into(thumbView);
        thumbView.setTag(R.id.iv,item.getUrl());
    }
}
