package ar.com.portlander.sendmeal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class Home extends AppCompatActivity {

    private Toolbar tb_home;
    private NotificationChannel notificationChannel;
    private MenuItem menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createNotificationChannel();

        tb_home = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(tb_home);

        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle(null);
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_registrarme:
                Intent intent_MainActivity = new Intent(this, MainActivity.class);
                startActivity(intent_MainActivity);
                //METER ESTO EN UN METODO REGISTRAR
                return true;
            case R.id.item_crear_item:
                Intent intent_Alta_plato = new Intent(this, Alta_plato.class);
                startActivity(intent_Alta_plato);
                return true;
            case R.id.item_lista_items:
                Intent intent_Lista_plato = new Intent(this, Lista_platos.class);
                startActivity(intent_Lista_plato);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);

        return true;

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String channel_id = getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            notificationChannel = new NotificationChannel(channel_id, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void registrarme(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}