package com.bashtovyi.artem.task.ui.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bashtovyi.artem.task.R;
import com.bashtovyi.artem.task.ui.MainActivity;
import com.bashtovyi.artem.task.ui.StringConstant;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * A placeholder fragment containing a simple view.
 */
public class NotificationFragment extends Fragment {
    private static final String TAG  = "NotificationFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_INT = "number";

    // counter for notifications
    private static int counter = 0;

    private ArrayList<Integer> notificationIds = new ArrayList<>();
    private Context context;

    private int uniqueId;
    private int number = 0;

    public NotificationFragment() {}

    public static NotificationFragment newInstance(int sectionNumber,int uniqueId) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ARG_INT,uniqueId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView imageView = rootView.findViewById(R.id.circle_image_view);
        number = getArguments().getInt(ARG_SECTION_NUMBER);
        uniqueId = getArguments().getInt(ARG_INT);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
            }
        });
        return rootView;
    }

    private void createNotification(){
        final Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(StringConstant.KEY_NOTIFICATION, number);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                uniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // FIXME : channel for API > 2
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.notification_logo))
               // .setColor(getResources().getColor(R.color.notification_color)
                .setContentTitle("Move to fragment")
                .setContentText("Notification " + number)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        counter++;
        Log.i(TAG, "createNotification: add id= " + counter);

        notificationIds.add(counter);
        notificationManager.notify(counter, mBuilder.build());
    }

    public void destroyAllNotifications() {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        for (Integer id : notificationIds) {
            notificationManager.cancel(id);
        }
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}