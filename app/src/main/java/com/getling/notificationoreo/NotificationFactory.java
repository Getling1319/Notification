package com.getling.notificationoreo;

import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * @author getling
 * @email jialin.zhang@ucarinc.com
 * @date 2019/01/11
 * @description
 */
public class NotificationFactory {

    public static void createNotificationChannelGroup(NotificationManager manager, String groupId, String groupName) {
        NotificationChannelGroup group = new NotificationChannelGroup(groupId, groupName);
        manager.createNotificationChannelGroup(group);
    }

    public static Notification showSimpleNotification(Context context, String channelId) {
        return new Notification.Builder(context, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("消息的标题")
                .setContentText("内容内容内容")
                .build();
    }
}
