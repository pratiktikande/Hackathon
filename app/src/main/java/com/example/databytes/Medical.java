package com.example.databytes;

import android.app.Activity;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import com.example.databytes.databinding.FragmentMedicalBinding;
import com.example.databytes.databinding.FragmentReminderBinding;
import com.example.databytes.databinding.FragmentSlideshowBinding;
import com.example.databytes.databinding.FragmentViewpresBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Medical extends Fragment {
    private FragmentMedicalBinding binding;
    Button b;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding  = FragmentMedicalBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Activity activity = getActivity();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        try {
            intent.setComponent(new ComponentName("health.care.com.healthcare", "health.care.com.healthcare.MainActivity"));
            startActivity(intent);
        }catch (Exception E){
            Toast.makeText(activity, "Please install med reminder", Toast.LENGTH_SHORT).show();
        }

        return root;
    }
}