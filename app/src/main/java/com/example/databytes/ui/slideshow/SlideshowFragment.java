package com.example.databytes.ui.slideshow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.databytes.R;
import com.example.databytes.Scanner_page;
import com.example.databytes.databinding.FragmentSlideshowBinding;
import com.example.databytes.gaurdian_list_view;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SlideshowFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    EditText phone, otp1,gp;
    Button btngenOTP, btnverify,vg;
    ImageButton ib;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        phone = root.findViewById(R.id.phone);
        otp1 = root.findViewById(R.id.otp);
        ib = root.findViewById(R.id.imageButton);
        btngenOTP = root.findViewById(R.id.btngenerateOTP);
        btnverify =root.findViewById(R.id.btnverifyOTP);
        vg = root.findViewById(R.id.viewg);
        mAuth = FirebaseAuth.getInstance();
        bar = root.findViewById(R.id.bar);
        gp = root.findViewById(R.id.gaurdian_name_input);
        Activity activity = getActivity();
        vg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, gaurdian_list_view.class));
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                startActivity(new Intent(activity, Scanner_page.class));
            }
        });
        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(activity, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                }
                else {
//                    SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
//                    String gn = preferences.getString("gno","");
//                    String un = preferences.getString("patient_name","");
//                    FirebaseDatabase db=FirebaseDatabase.getInstance();
//                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("/"+un);
//                    Map<String, Object> updates = new HashMap<String,Object>();
//                    updates.put("guard"+gn, gp.getText().toString());
//
//                    int gn1 = Integer.parseInt(gn);
//                    gn1  = gn1+1;
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("gno", String.valueOf(gn1));
//                    updates.put("gno",String.valueOf(gn1));
//                    editor.apply();
//                    ref.updateChildren(updates);
                    String number = phone.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    sendverificationcode(number);
                }
            }
        });
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(otp1.getText().toString()))
                {
                    Toast.makeText(activity, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
                }
                else
                    verifycode(otp1.getText().toString());
            }
        });

        return root;
    }
    private void sendverificationcode(String phoneNumber)
    {   Activity activity = getActivity();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)  // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Activity activity = getActivity();
            Toast.makeText(activity, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            Activity activity = getActivity();
            Toast.makeText(activity, "Code sent", Toast.LENGTH_SHORT).show();
            btnverify.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }};
    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        Activity activity = getActivity();
                        if(task.isSuccessful())
                        {
                            SharedPreferences preferences = activity.getSharedPreferences("checkbox",activity.MODE_PRIVATE);
                            String gn = preferences.getString("gno","");
                            String un = preferences.getString("patient_name","");
                            FirebaseDatabase db=FirebaseDatabase.getInstance();
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("/"+un);
                            Map<String, Object> updates = new HashMap<String,Object>();
                            updates.put("guard"+gn, gp.getText().toString());

                            int gn1 = Integer.parseInt(gn);
                            gn1  = gn1+1;
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("gno", String.valueOf(gn1));
                            updates.put("gno",String.valueOf(gn1));
                            editor.apply();
                            ref.updateChildren(updates);
                            Toast.makeText(activity, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity ,SlideshowFragment.class));
                        }

                    }
                });}

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            Activity activity = getActivity();
            startActivity(new Intent(activity, SlideshowFragment.class));
            activity.finish();
        }}}