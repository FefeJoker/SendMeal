package ar.com.portlander.sendmeal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Intent;
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
    private MenuItem menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //METER ESTO EN UN METODO REGISTRAR
                return true;
            case R.id.item_crear_item:
                //
                return true;
            case R.id.item_lista_items:
                //
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
    public void registrarme(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}