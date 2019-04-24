package com.ygwl.lz.myviewpicture.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.ygwl.lz.myviewpicture.R;

/**
 * @author cx
 * @class describe
 * @time 2019/4/23 14:42
 */
public class VideoPlayerDetailedActivity extends Activity {

    private VideoView videoView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_test);

        String videoUrl1 = getIntent().getStringExtra("url");

        //网络视频
//        String videoUrl2 = Utils.videoUrl ;

        Uri uri = Uri.parse( videoUrl1 );

        videoView = (VideoView)this.findViewById(R.id.videoView );

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoPlayerDetailedActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        //退出前需要将屏幕变为竖屏，否则会报错
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        finish();
    }

}
