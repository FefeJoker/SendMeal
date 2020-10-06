package ar.com.portlander.sendmeal;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1");
        builder.setSmallIcon(R.drawable.plato); //Buscar un iconito lindo.
        builder.setContentTitle("SendMeal");
        builder.setContentText(intent.getStringExtra("MENSAJE"));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
        //Se deberia manejar mejor el tema de los ids de las notificaciones.
    }
}
