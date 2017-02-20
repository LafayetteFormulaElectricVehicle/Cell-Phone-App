package birrur.com.jsonparse;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.drawable.notification_icon).setContentTitle("My notification").setContentText("Hello World!");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void notificate(View view) {

        // Do something in response to button
        int mNotificationId = 001;
        NotificationManager nmg = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nmg.notify(mNotificationId, mBuilder.build());

    }
}
