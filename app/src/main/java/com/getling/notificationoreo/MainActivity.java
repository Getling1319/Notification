package com.getling.notificationoreo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                showNotification();
                break;
            default:
                break;
        }
    }

    private void createChannel() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationFactory.createNotificationChannelGroup(manager, Constants.CHANNEL_GROUP_ID_1, "渠道组1");
        NotificationFactory.createNotificationChannelGroup(manager, Constants.CHANNEL_GROUP_ID_2, "渠道组2");

        NotificationChannel channel1 = new NotificationChannel(Constants.CHANNEL_ID_1, "渠道1", NotificationManager.IMPORTANCE_HIGH);
        channel1.setGroup(Constants.CHANNEL_GROUP_ID_1);
        channel1.setDescription("第一个渠道的描述");
        channel1.enableLights(true);
        channel1.enableVibration(true);
        manager.createNotificationChannel(channel1);

        NotificationChannel channel2 = new NotificationChannel(Constants.CHANNEL_ID_2, "渠道2", NotificationManager.IMPORTANCE_HIGH);
        channel2.setGroup(Constants.CHANNEL_GROUP_ID_2);
        manager.createNotificationChannel(channel2);

    }

    private void showNotification() {
        manager.notify(random.nextInt(), NotificationFactory.showSimpleNotification(this, Constants.CHANNEL_ID_1));
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String groupId = "group1";
            NotificationChannelGroup group = new NotificationChannelGroup(groupId, "第一个渠道组");

            String groupId2 = "group2";
            NotificationChannelGroup group2 = new NotificationChannelGroup(groupId2, "第二个渠道组");

            notificationManager.createNotificationChannelGroup(group);
            notificationManager.createNotificationChannelGroup(group2);


            String channelId = "channel1";
            NotificationChannel channel = new NotificationChannel(channelId, "渠道名称", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("渠道描述");
            channel.setGroup(groupId);
//            channel.enableVibration(true);
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.setShowBadge(true);

            String channelId2 = "channel2";
            NotificationChannel channel2 = new NotificationChannel(channelId2, "第二个渠道", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("第二个渠道描述");
            channel2.setGroup(groupId2);

            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel2);

            Notification notification = new Notification.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("一条消息的标题")
                    .setContentText("消息的具体内容")
                    .setAutoCancel(true)
                    .setNumber(2)
                    .build();
            notificationManager.notify(1, notification);
        }
    }
}
