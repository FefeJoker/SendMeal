package ar.com.portlander.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ar.com.portlander.sendmeal.model.Plato;
import ar.com.portlander.sendmeal.persistance.AppRepository;
import ar.com.portlander.sendmeal.services.PlatoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Alta_plato extends AppCompatActivity implements AppRepository.OnResultCallback<Plato>{
    private String titulo, descripcion;
    private Double precio;
    private Integer calorias;
    private ImageView img;
    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;
    private Plato plato;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_plato);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        RelativeLayout  imgHolder = (RelativeLayout) findViewById(R.id.image_view_holder);

        img = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height/2);
        img.setLayoutParams(params);
        imgHolder.addView(img);

    }
    public void crearPlato(View view){

        EditText ettitulo, etdescripcion, etprecio, etcalorias;
        ettitulo = (EditText) findViewById(R.id.titulo);
        etdescripcion = (EditText) findViewById(R.id.descripcion);
        etprecio = (EditText) findViewById(R.id.precio);
        etcalorias = (EditText) findViewById(R.id.calorias);
        String msg = "";

        if(ettitulo.getText().toString().isEmpty()) msg += "Completar título.\n";
        if(etdescripcion.getText().toString().isEmpty()) msg += "Completar descripción.\n";
        if(etprecio.getText().toString().isEmpty()) msg += "Completar precio.\n";
        if(etcalorias.getText().toString().isEmpty()) msg += "Completar calorías.\n";
        if(img.getDrawable() == null)  msg += "Agregar foto.\n";

        if(msg.isEmpty()){
            titulo = ettitulo.getText().toString();
            descripcion = etdescripcion.getText().toString();
            precio = Double.parseDouble(etprecio.getText().toString());
            calorias = Integer.parseInt(etcalorias.getText().toString());


            Plato plato = new Plato(titulo,descripcion,precio,calorias);

            this.plato = plato;
            repository = new AppRepository(this.getApplication(), this);
            repository.buscarTodosPlato();
            ////////////////////////////////
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3001/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            PlatoService platoService = retrofit.create(PlatoService.class);

            platoService.createPlato(plato);

            ////////////////////////////////
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void lanzarCamara(View view) {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    public void abrirGaleria(View view) {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_REQUEST || requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            img.setImageBitmap(imageBitmap);
            img.invalidate();
            Log.d("Foto", "Ok");

        }
    }

    @Override
    public void onResult(Plato result) {

    }

    @Override
    public void onInsert(){

    }

    @Override
    public void onResult(List<Plato> result) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        Integer id = result.size()+1;
        StorageReference platosImagesRef = storageRef.child("images/plato_" + id.toString() + ".jpg");

        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imagenBytes = baos.toByteArray(); // Imagen en arreglo de bytes

        UploadTask uploadTask = platosImagesRef.putBytes(imagenBytes);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // URL de descarga del archivo

                    Uri downloadUri = task.getResult();
                    plato.setUri(downloadUri);
                    repository.insertar(plato);
                } else {
                    // Fallo
                }
            }
        });
    }
}