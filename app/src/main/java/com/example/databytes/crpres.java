package com.example.databytes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class crpres extends AppCompatActivity {
    EditText Name,Date,Pre;
    Button B;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crpres);
        t  = findViewById(R.id.crpresdname);
        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String dn = preferences.getString("dname","");
        if(dn==""){
            startActivity(new Intent(crpres.this, Addhar_verfi_page.class));
            Toast.makeText(this, "Enter correct ID", Toast.LENGTH_SHORT).show();
        }
        t.setText("Welcome Dr."+dn);
        B = findViewById(R.id.crpresbut);
        Pre = findViewById(R.id.ptpre);

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,pre;
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                name = preferences.getString("pru","");
//                name =Name.getText().toString();
                pre = Pre.getText().toString();
                if(name.isEmpty()||pre.isEmpty()){
                    Toast.makeText(crpres.this, "Fill all!!", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    try {

                        String dname = preferences.getString("dname","");

//                        DatabaseReference node= db.getReference(name);
//                        DatabaseReference node1= db.getReference("/Doctors/"+k);
                        SimpleDateFormat date1 = new SimpleDateFormat("dd:MM:yyyy");
                        String date2 = date1.format(new Date());
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("/"+name);
                        Map<String, Object> updates = new HashMap<String,Object>();
                        updates.put(dname, pre);
                        ref.updateChildren(updates);
                        Toast.makeText(crpres.this, "Prescription Uploaded", Toast.LENGTH_SHORT).show();

                    }catch (Exception FirebaseError ){
                        Toast.makeText(crpres.this, "Enter Correct ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}