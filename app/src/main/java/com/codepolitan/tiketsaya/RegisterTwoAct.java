package com.codepolitan.tiketsaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue, btn_add_photo;
    ImageView pic_photo_register_user;
    EditText bio, nama_lengkap;

    //untuk keperluan photo menggunakan Uri
    Uri photo_location;
    //membatasi jumlah foto yang diupload
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    //membuat string baru
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        Toast.makeText(this, username_key_new, Toast.LENGTH_SHORT).show();

        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        pic_photo_register_user = findViewById(R.id.pic_photo_register_user);

        bio = findViewById(R.id.bio);
        nama_lengkap = findViewById(R.id.nama_lengkap);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {


                                            @Override
                                            public void onClick(View v) {
                                                //ubah state menjadi loading
                                                btn_continue.setEnabled(false);
                                                btn_continue.setText("Loading...");

                                                //menyimpan kepada firebase
                                                //menggunakan username baru
                                                //di register 1 disimpan di local
                                                //kemudian ambil untuk key
                                                reference = FirebaseDatabase.getInstance().getReference()
                                                        .child("Users").child(username_key_new);
                                                storage = FirebaseStorage.getInstance().getReference()
                                                        .child("Photousers").child(username_key_new);

                                                //validasi untuk file apakah ada ?
                                                if (photo_location != null) {
                                                    final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." +
                                                            getFileExtension(photo_location));
                                                    //ketika kondisi sukses
                                                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                                                            //setelah berhasil meletakkan file
                                                            //dapatkan sebuah url
                                                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    //melakukan upload
                                                                    //yang diupload
                                                                    //gambar dan url

                                                                    String uri_photo = uri.toString();
                                                                    //ambil reference
                                                                    //ketika yang di upload url, berdasarkan username
                                                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                                                    reference.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                                                                    reference.getRef().child("bio").setValue(bio.getText().toString());
                                                                }
                                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Uri> task) {
                                                                    Intent gotosuccess = new Intent(RegisterTwoAct.this, SuccessRegisterAct.class);
                                                                    startActivity(gotosuccess);
                                                                }
                                                            });
                                                        }

                                                    });
                                                }
                                            }
                                        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //mendapatkan alamat file
    //akan digunakan ketika menyimpan foto ke firebase
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    //fuction yang dibutuhkan jika ingin menambahkan foto
    public void findPhoto(){
        Intent pic = new Intent();
        //karena ingin mengupload gambar menggunakan /image
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //validasi photonya sudah ada atau belum
        //memastikan bahwa datanya tidak null
        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            //membutuhkan library picasso

            //Picasso mengisi sebuah url pada sebuah imageview secara realtime
             //load image dari photo location ke pic photo register user
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_register_user);
        }
    }

    //mendapatkan reference
    public void getUsernameLocal(){
        //hanya perlu mendapatkan username key
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}

