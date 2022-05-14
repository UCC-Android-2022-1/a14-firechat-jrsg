package com.example.firechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeHolder> {
    private ArrayList<Mensaje> data;
    private Context context;

    public MensajeAdapter(Context context) {
        this.context = context;

        data = new ArrayList<>();
    }

    public void add(Mensaje mensaje){
        data.add( mensaje );
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mensaje_fila, parent, false);
        return new MensajeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int position) {
        Mensaje mensaje = data.get(position);

        holder.getTvNombre().setText( mensaje.getNombre() );

        long fechaHora = mensaje.getFechaHora();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, hh:mm a", new Locale( "ES"));

        holder.getTvFechaHora().setText( simpleDateFormat.format( new Timestamp(fechaHora))  );

        holder.getTvMensaje().setText( mensaje.getCuerpo() );

        if(mensaje.getImagen() != null){
            Picasso.get().load( mensaje.getImagen() ).into( holder.getIvMensaje() );
            holder.getIvMensaje().setVisibility( View.VISIBLE );
        }else{
            holder.getIvMensaje().setImageResource(0);
            holder.getIvMensaje().setVisibility( View.GONE );
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
