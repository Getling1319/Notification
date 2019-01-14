package com.getling.notificationoreo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager manager;

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createChannel();

        random = new Random();

        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                showNotification(Constants.CHANNEL_ID_1);
                break;
            case R.id.btn_show:
                NotificationChannel channel = manager.getNotificationChannel(Constants.CHANNEL_ID_2);
                if (channel.getImportance() != NotificationManager.IMPORTANCE_HIGH) {
                    gotoNotificationSetting(channel.getId());
                } else {
                    showNotification(Constants.CHANNEL_ID_2);
                }
                break;
            case R.id.btn_delete:
                deleteChannel();
                break;
            default:
                break;
        }
    }

    private void createChannel() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //创建渠道组
        NotificationFactory.createNotificationChannelGroup(manager, Constants.CHANNEL_GROUP_ID_1, "渠道组1");
        NotificationFactory.createNotificationChannelGroup(manager, Constants.CHANNEL_GROUP_ID_2, "渠道组2");

        //创建渠道1
        NotificationChannel channel1 = new NotificationChannel(Constants.CHANNEL_ID_1, "渠道1", NotificationManager.IMPORTANCE_HIGH);
        channel1.setGroup(Constants.CHANNEL_GROUP_ID_1);
        channel1.setDescription("第一个渠道的描述");
        //是否使用呼吸灯
        channel1.enableLights(true);
        //是否允许震动
        channel1.enableVibration(true);
        //是否显示角标
        channel1.setShowBadge(true);
        manager.createNotificationChannel(channel1);

        //创建渠道2
        NotificationChannel channel2 = new NotificationChannel(Constants.CHANNEL_ID_2, "渠道2", NotificationManager.IMPORTANCE_HIGH);
        channel2.setDescription("第二个渠道的描述");
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel2.setGroup(Constants.CHANNEL_GROUP_ID_2);
        //是否使用呼吸灯
        channel2.enableLights(false);
        //是否允许震动
        channel2.enableVibration(false);
        //是否显示角标
        channel2.setShowBadge(false);
        manager.createNotificationChannel(channel2);

        //创建渠道3
        NotificationChannel channel3 = new NotificationChannel(Constants.CHANNEL_ID_3, "渠道3", NotificationManager.IMPORTANCE_HIGH);
        channel3.setDescription("第三个渠道的描述");
        channel3.setGroup(Constants.CHANNEL_GROUP_ID_2);
        manager.createNotificationChannel(channel3);

    }

    private void showNotification(String channelId) {
        manager.notify(random.nextInt(), NotificationFactory.showSimpleNotification(this, channelId));
    }

    private void gotoNotificationSetting(String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        startActivity(intent);
    }

    private void deleteChannel() {
        manager.deleteNotificationChannel(Constants.CHANNEL_ID_3);
    }
}