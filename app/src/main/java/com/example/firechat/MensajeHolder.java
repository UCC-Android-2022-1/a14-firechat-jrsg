package com.example.firechat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MensajeHolder extends RecyclerView.ViewHolder {
    private ImageView ivImagen;
    private ImageView ivMensaje;
    private TextView tvNombre, tvMensaje, tvFechaHora;

    public MensajeHolder(@NonNull View itemView) {
        super(itemView);

        ivImagen = itemView.findViewById(R.id.ivImagen);
        tvNombre = itemView.findViewById(R.id.tvNombre);
        tvMensaje = itemView.findViewById(R.id.tvMensaje);
        tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
        ivMensaje = itemView.findViewById(R.id.ivMensaje);
    }

    public ImageView getIvMensaje() {
        return ivMensaje;
    }

    public void setIvMensaje(ImageView ivMensaje) {
        this.ivMensaje = ivMensaje;
    }

    public ImageView getIvImagen() {
        return ivImagen;
    }

    public void setIvImagen(ImageView ivImagen) {
        this.ivImagen = ivImagen;
    }

    public TextView getTvNombre() {
        return tvNombre;
    }

    public void setTvNombre(TextView tvNombre) {
        this.tvNombre = tvNombre;
    }

    public TextView getTvMensaje() {
        return tvMensaje;
    }

    public void setTvMensaje(TextView tvMensaje) {
        this.tvMensaje = tvMensaje;
    }

    public TextView getTvFechaHora() {
        return tvFechaHora;
    }

    public void setTvFechaHora(TextView tvFechaHora) {
        this.tvFechaHora = tvFechaHora;
    }
}
