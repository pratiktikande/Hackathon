package com.example.databytes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.databytes.R;
import com.example.databytes.databinding.FragmentCasefolderBinding;
import com.example.databytes.databinding.FragmentGeneBinding;
import com.example.databytes.databinding.FragmentMapBinding;
import com.example.databytes.databinding.FragmentSlideshowBinding;
import com.example.databytes.databinding.FragmentViewpresBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class map extends Fragment {
    private FragmentMapBinding binding;
    Button b;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding  = FragmentMapBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Activity activity = getActivity();
        b = root.findViewById(R.id.map1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
                String un = preferences.getString("patient_name","");
                FirebaseDatabase db=FirebaseDatabase.getInstance();
//                /Jayesh/users/test
                DatabaseReference node= db.getReference("/"+un+"/users/test/china");
                node.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                        String value = snapshot.getValue(String.class);
                        int comma = value.indexOf(",");
                        String lat = value.substring(0,comma);
                        int len = value.length();
                        String lon = value.substring(comma+1, len);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+value+"?q="+value+"(Patient+Location)"));
                        startActivity(intent);}
                        else{
                            Toast.makeText(activity, "there is no Accident", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.07613608801284,72.99426826825649?q=19.07613608801284,72.99426826825649(Patient+Location)"));
//                startActivity(intent);
            }
        });

        return root;
    }
}