package com.example.uasamub_sekuter_ti7jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaftarKursusActivity extends AppCompatActivity {

    EditText nama_lengkap_peserta, no_handphone_peserta,
            alamat_email_peserta, mata_kursus, jenis_kelamin_peserta;
    Button btn_daftar_kursus;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kursus);

        nama_lengkap_peserta = findViewById(R.id.nama_lengkap_peserta);
        no_handphone_peserta = findViewById(R.id.no_handphone_peserta);
        alamat_email_peserta = findViewById(R.id.alamat_email_peserta);
        mata_kursus = findViewById(R.id.mata_kursus);
        jenis_kelamin_peserta = findViewById(R.id.jenis_kelamin_peserta);
        btn_daftar_kursus = findViewById(R.id.btn_daftar_kursus);

        btn_daftar_kursus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //menyimpan data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, nama_lengkap_peserta.getText().toString());
                editor.apply();

                //simpan kepada database
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("PesertaKursus").child(nama_lengkap_peserta.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap_peserta").setValue(nama_lengkap_peserta.getText().toString());
                        dataSnapshot.getRef().child("no_handphone_peserta").setValue(no_handphone_peserta.getText().toString());
                        dataSnapshot.getRef().child("alamat_email_peserta").setValue(alamat_email_peserta.getText().toString());
                        dataSnapshot.getRef().child("mata_kursus").setValue(mata_kursus.getText().toString());
                        dataSnapshot.getRef().child("jenis_kelamin_peserta").setValue(jenis_kelamin_peserta.getText().toString());
                        dataSnapshot.getRef().child("biaya_kursus").setValue(200000);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //berpindah activity
                Intent gotonextregister = new Intent(DaftarKursusActivity.this, SuccessRegisterActivity.class);
                startActivity(gotonextregister);

            }
        });
    }
}