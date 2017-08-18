package sg.edu.rp.webservices.p13taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

/**
 * Created by 15017452 on 18/8/2017.
 */

public class ScheduledNotificationReciever extends BroadcastReceiver {

    int notificationId = 001;

    @Override
    public void onReceive(Context context, Intent i) {
        int id = i.getIntExtra("id", -1);

        String name = i.getStringExtra("name");
        String desc = i.getStringExtra("desc");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);


        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(name);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);

        Notification n = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // You may put an ID for the first parameter if you wish
        // to locate this notification to cancel
        notificationManager.notify(notificationId, n);


        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0,
                        intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher, context.getString(R.string.notification_title),
                pendingIntent).build();
        Intent intentreply = new Intent(context,
                ReplyActivity.class);
        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentreply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);

        Notification notification = new
                NotificationCompat.Builder(context)
                .setContentText(name)
                .setContentTitle(desc)
                .setSmallIcon(R.drawable.trihard)
                // When Wear notification is clicked, it performs
                // the action we defined in line below
                .extend(extender)
                .build();

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notificationId, notification);

    }

}

