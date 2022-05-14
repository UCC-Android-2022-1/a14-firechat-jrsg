package com.example.firechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static final int ACCION_SELECCION_IMAGEN = 1;

    private RecyclerView rvMensajes;
    private ImageButton bEnviar, bEnviarImagen;
    private EditText etMensaje;

    private MensajeAdapter adapter;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMensajes      = findViewById(R.id.rvMensajes);
        bEnviar         = findViewById(R.id.bEnviar);
        bEnviarImagen   = findViewById(R.id.ibEnviar);
        etMensaje       = findViewById(R.id.etMensaje);

        database = FirebaseDatabase.getInstance("https://firechat-3b3e6-default-rtdb.firebaseio.com/");
        myRef = database.getReference("chat");

        storage = FirebaseStorage.getInstance();

        adapter = new MensajeAdapter(this);
        rvMensajes.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMensajes.setLayoutManager(linearLayoutManager);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensaje();
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
                adapter.add(mensaje); //envia a pantalla
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                rvMensajes.scrollToPosition( adapter.getItemCount() - 1);
            }
        });

        bEnviarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarImagen();
            }
        });
    }

    private void enviarImagen() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, ACCION_SELECCION_IMAGEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == ACCION_SELECCION_IMAGEN){
                Uri uri = data.getData();

                StorageReference storageRef = storage.getReference().child("images/" + uri.getLastPathSegment() + "_" + System.currentTimeMillis());

                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Mensaje mensaje = new Mensaje();
                                mensaje.setNombre( "rsanchez" );
                                mensaje.setFechaHora(System.currentTimeMillis());
                                mensaje.setImagen( uri.toString() );

                                myRef.push().setValue(mensaje);
                            }
                        });


                    }
                });
            }
        }
    }

    private void enviarMensaje() {
        String mensaje = etMensaje.getText().toString().trim();

        Mensaje m = new Mensaje();

        m.setCuerpo( mensaje );
        m.setNombre( "rsanchez" );
        m.setFechaHora( System.currentTimeMillis() );

        myRef.push().setValue(m);

        etMensaje.setText("");
    }
}