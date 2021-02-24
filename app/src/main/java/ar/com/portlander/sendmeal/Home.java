package ar.com.portlander.sendmeal;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;
import ar.com.portlander.sendmeal.services.PlatoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
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

        //FirebaseApp.initializeApp(this.getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        signInAnonymously();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            // Error
                            return;
                        }

                        // FCM token
                        String token = task.getResult();

                        // Imprimirlo en un toast y en logs
                        Log.d("-------------------", token);
                        Toast.makeText(Home.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_registrarme:
                Intent intent_MainActivity = new Intent(this, MainActivity.class);
                aber();
                //startActivity(intent_MainActivity);
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

    public void aber(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PlatoService platoService = retrofit.create(PlatoService.class);

        Call<List<Plato>> callPlatos = platoService.getPlatoList();

        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                            Log.d("PLATO", response.body().get(0).getTitulo());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", t.getMessage());
                    }
                }
        );

    }
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Exito
                            Log.d("TAG", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Error
                            Log.w("TAG", "signInAnonymously:failure", task.getException());
                            //Toast.makeText(AnonymousAuthActivity.this, "Authentication failed.",
                            // Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}