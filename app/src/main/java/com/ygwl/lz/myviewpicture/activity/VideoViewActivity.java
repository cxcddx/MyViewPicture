package com.ygwl.lz.myviewpicture.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.loader.VideoClickListener;
import com.ygwl.lz.myviewpicture.R;
import com.ygwl.lz.myviewpicture.adapter.MyBaseQuickAdapter;
import com.ygwl.lz.myviewpicture.bean.UserViewInfo;
import com.ygwl.lz.myviewpicture.fragment.UserFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoViewActivity extends Activity {
    private ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("*(******************");
        setContentView(R.layout.activity_videoview);
        init();
    }
    //初始化数据和控件
    private void init(){
        //准备数据
        //初始化视频列表数据
        List<File> videoPathList = listFileSortByModifyTime(Environment.getExternalStorageDirectory().getPath() + "/screenrecord/");
        if (videoPathList != null && videoPathList.size()>0) {
            for (int i = videoPathList.size()-1; i >= 0; i--) {
                UserViewInfo info = new UserViewInfo(videoPathList.get(i).getPath(), videoPathList.get(i).getPath());
                info.setUser(videoPathList.get(i).getName());
                mThumbViewInfoList.add(info);
            }
        }
        //初始化图片列表
        List<File> bmpPathList = listFileSortByModifyTime(Environment.getExternalStorageDirectory().getPath() + "/snapshot/");
        if (bmpPathList != null && bmpPathList.size()>0) {
            for (int i = bmpPathList.size()-1; i >= 0; i--) {
                UserViewInfo info = new UserViewInfo(bmpPathList.get(i).getPath());
                info.setUser(bmpPathList.get(i).getName());
                mThumbViewInfoList.add(info);
            }
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        MyBaseQuickAdapter adapter=new MyBaseQuickAdapter(this);
        adapter.addData(mThumbViewInfoList);
        mRecyclerView.setAdapter(adapter);
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
               GPreviewBuilder.from(VideoViewActivity.this)
                       .setData(mThumbViewInfoList)
                       .setUserFragment(UserFragment.class)
                       .setCurrentIndex(position)
                       .setSingleFling(true)
                       .setFullscreen(true)
                       .setOnVideoPlayerListener(new VideoClickListener(){

                           @Override
                           public void onPlayerVideo(String url) {

                               Intent intent = new Intent(VideoViewActivity.this, VideoPlayerDetailedActivity.class);
                               intent.putExtra("url", url);
                               startActivity(intent);

                           }
                       })
                       .setType(GPreviewBuilder.IndicatorType.Number)
                       .start();
           }
       });


    }
    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos;i < mThumbViewInfoList.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView =  itemView.findViewById(R.id.iv);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    /**
     * 获取文件夹下的所有子文件名称
     * @param path 文件夹路径
     * @return
     */
    public static List<String> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i].getAbsolutePath());
            files[i].lastModified();

        }
        return s;
    }



    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */ public static List<File> listFileSortByModifyTime(String path)
    {
        List<File> list = getFiles(path);
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>()
            {
                @Override
                public int compare(File file, File newFile)
                {
                    if (file.lastModified() < newFile.lastModified())
                    {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified())
                    {
                        return 0;
                    } else
                    {
                        return 1;
                    }
                }
            });
        }
//        List<String> pathList = new ArrayList<>();
//        for (File file : list) {
//            pathList.add(file.getPath());
//        }
        return list;
    }

    /**
     * 获取指定目录下的文件列表
     * @param path
     * @return
     */
    public static List<File> getFiles(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<File> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i]);

        }
        return s;
    }


}
