package ar.com.portlander.sendmeal.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.portlander.sendmeal.R;
import ar.com.portlander.sendmeal.model.Plato;

public class PlatosRecyclerAdapterForNuevoPedido extends RecyclerView.Adapter<PlatosRecyclerAdapterForNuevoPedido.PlatoViewHolder> {
    private List<Plato> mDataset;
    private AppCompatActivity activity;
    // Provide a suitable constructor (depends on the kind of dataset)
    public PlatosRecyclerAdapterForNuevoPedido(List<Plato> myDataset, AppCompatActivity act) {
        mDataset = myDataset;
        activity = act;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class PlatoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView card;
        TextView titulo;
        TextView precio;
        ImageView imagen;
        Button boton;

        public PlatoViewHolder(View v){
            super(v);
            card = v.findViewById(R.id.cv);
            titulo = v.findViewById(R.id.titulo);
            precio = v.findViewById(R.id.precio);
            imagen = v.findViewById(R.id.foto);
            boton = v.findViewById(R.id.agregar_plato);
            boton.setVisibility(Button.VISIBLE);
            boton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String auxNombre = titulo.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("NOMBRE", auxNombre);
                    activity.setResult(1, intent);
                    activity.finish();
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlatosRecyclerAdapterForNuevoPedido.PlatoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                  int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_plato, parent, false);
        //...
        PlatoViewHolder  vh = new PlatoViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PlatoViewHolder platoHolder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // configurar cada view con que fila del arreglo de datos coincide.
        platoHolder.titulo.setTag(position);
        platoHolder.precio.setTag(position);
        platoHolder.imagen.setTag(position);
        Plato plato = mDataset.get(position);
        // ImageView rowGenero = vistaAux.findViewById(R.id.rowImgGenero);
        // TextView titulo = vistaAux.findViewById(R.id.rowTitulo);
        platoHolder.titulo.setText(plato.getTitulo());
        // ProgressBar pb = vistaAux.findViewById(R.id.rowRating);

        Float auxPrecio = plato.getPrecio()==null? 0.0F : plato.getPrecio().floatValue();
        //Log.d(PlatoAdapter.class.getName(), plato.getTitulo()+" ON plato.getCalificacion!!!!(): "+plato.getCalificacion()+ " - "+auxCalificacion);

        platoHolder.precio.setText(auxPrecio.toString());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
