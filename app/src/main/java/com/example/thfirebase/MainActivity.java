package com.example.thfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mydb;
    TextView Temperatura, Humedad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("FirebaseData","onCreate called");
        Temperatura=findViewById(R.id.Temp);
        Humedad=findViewById(R.id.Hum);
        mydb = FirebaseDatabase.getInstance().getReference().child("Sensor");
        try {
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapShot) {
                    Log.d("FirebaseData","onDataChange called");
                    if(Temperatura==null || Humedad==null){
                        Log.e("FirebaseData","TextViews not initialized");
                        return;
                    }
                    if (dataSnapShot.exists()){
                        if (dataSnapShot.child("temp").exists() && dataSnapShot.child("hum").exists()){
                            String tempdata = dataSnapShot.child("temp").getValue(String.class);
                            String humdata = dataSnapShot.child("hum").getValue(String.class);
                            if(tempdata!=null && humdata!=null){
                                Temperatura.setText(tempdata);
                                Humedad.setText(humdata);
                            }else{

                            }
                        }else{

                        }
                    }else {
                        Log.d("FirebaseData","DataSnapshot does not exist");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseData","onCancelled: "+ error.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("FirebaseData","Error: "+e.getMessage());
        }
    }
}