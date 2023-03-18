package com.example.databytes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.databytes.R;
import com.example.databytes.databinding.FragmentCasefolderBinding;
import com.example.databytes.databinding.FragmentGeneBinding;
import com.example.databytes.databinding.FragmentSlideshowBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gene extends Fragment {
    private FragmentGeneBinding binding;
    ImageView iv;
    Button b,gq;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding  = FragmentGeneBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Activity activity = getActivity();
        iv = root.findViewById(R.id.ivp);
        b = root.findViewById(R.id.gene);
        gq = root.findViewById(R.id.gqr);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QR q = new QR();
                SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
                String s = preferences.getString("patient_name","");
                q.qrgen(iv,s);
            }
        });
        gq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QR q = new QR();
                SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
                String s = preferences.getString("patient_name","");
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node= db.getReference("/"+s+"/ph");
                node.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        q.qrgen(iv,value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        return root;
    }
}