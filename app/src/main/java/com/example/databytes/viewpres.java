package com.example.databytes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.databytes.R;
import com.example.databytes.databinding.FragmentCasefolderBinding;
import com.example.databytes.databinding.FragmentGeneBinding;
import com.example.databytes.databinding.FragmentSlideshowBinding;
import com.example.databytes.databinding.FragmentViewpresBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewpres extends Fragment {
    private FragmentViewpresBinding binding;
    EditText ed;
    TextView t1,t2;
    Button b;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding  = FragmentViewpresBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Activity activity = getActivity();
        ed = root.findViewById(R.id.vprname);
        b = root.findViewById(R.id.vprbut);
        t1 = root.findViewById(R.id.vprt1);
        t2 = root.findViewById(R.id.vprt2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed.getText().toString();
                Activity activity = getActivity();
                if(name.isEmpty()){
                    Toast.makeText(activity, "Enter Valid Doctor name", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    try {

                        SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
                        String uname = preferences.getString("patient_name","");
                        DatabaseReference node= db.getReference("/"+uname+"/"+name);

                        node.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String value = snapshot.getValue(String.class);
                                t1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.VISIBLE);
                                t2.setText(value);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(activity, "Turn On Internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception FirebaseError ){
                        Toast.makeText(activity, "Enter Correct ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }
}