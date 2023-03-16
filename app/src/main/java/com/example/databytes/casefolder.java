package com.example.databytes;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.databytes.R;
import com.example.databytes.databinding.FragmentCasefolderBinding;
import com.example.databytes.databinding.FragmentSlideshowBinding;

public class casefolder extends Fragment {
    private FragmentCasefolderBinding binding;
    private ImageView iv1,iv2,iv3,iv4;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding  = FragmentCasefolderBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        iv1 = root.findViewById(R.id.Medicine);
        iv2 = root.findViewById(R.id.Cardiology);
        iv3 = root.findViewById(R.id.Skin);
        iv4 = root.findViewById(R.id.Neurology);
        Activity activity = getActivity();
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(activity , medicine.class));
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity , emergency.class));
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity , skin.class));
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity , neurology.class));
            }
        });

        return root;
    }
}