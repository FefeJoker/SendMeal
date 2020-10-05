package ar.com.portlander.sendmeal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.portlander.sendmeal.R;
import ar.com.portlander.sendmeal.model.Plato;

public class NuevoPedidoRecyclerAdapter extends RecyclerView.Adapter<NuevoPedidoRecyclerAdapter.PlatoViewHolder> {
    private List<Plato> mDataset;
    private AppCompatActivity activity;
    // Provide a suitable constructor (depends on the kind of dataset)
    public NuevoPedidoRecyclerAdapter(List<Plato> myDataset, AppCompatActivity act) {
        mDataset = myDataset;
        activity = act;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class PlatoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView card;
        TextView nombre;
        TextView precio;

        public PlatoViewHolder(View v){
            super(v);
            card = v.findViewById(R.id.cv);
            nombre = v.findViewById(R.id.nombre);
            precio = v.findViewById(R.id.precio);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public NuevoPedidoRecyclerAdapter.PlatoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                         int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_nuevo_pedido, parent, false);
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
        platoHolder.nombre.setTag(position);
        platoHolder.precio.setTag(position);
        Plato plato = mDataset.get(position);
        // ImageView rowGenero = vistaAux.findViewById(R.id.rowImgGenero);
        // TextView titulo = vistaAux.findViewById(R.id.rowTitulo);
        platoHolder.nombre.setText(plato.getTitulo());
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
