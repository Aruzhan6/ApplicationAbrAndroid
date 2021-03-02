package com.example.applicationabrandroid.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applicationabrandroid.MainActivity;
import com.example.applicationabrandroid.R;
import com.example.applicationabrandroid.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {

    EditText edPhone, edSms;
    Button btnLogin;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view =
                inflater.inflate(R.layout.fragment_auth, container, false);

        edPhone = view.findViewById(R.id.edPhone);
        edSms = view.findViewById(R.id.edSms);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edPhone.getText().toString())) {

                    Toast.makeText(getActivity().getApplicationContext(), " Phone Required", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(edSms.getText().toString())) {
                    login  (edPhone.getText().toString(),null);
                }
                else {
                    login(edPhone.getText().toString(), edSms.getText().toString());
                }
            }

            private void login(String phone,String otp){
                LoginRequest loginRequest;
                if (otp==null){
                    loginRequest = new LoginRequest(phone);

                } else {
                    loginRequest = new LoginRequest(phone,otp);
                }


                Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
                loginResponseCall.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if(response.isSuccessful()){

                            if (otp==null) {
                                Toast.makeText(getActivity().getApplicationContext(), "SMS отправлен ", Toast.LENGTH_LONG).show();
                                LoginResponse loginResponse = response.body();
                            }
                            else{

                                HomeFragment homeFrag= new HomeFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frameContainer, homeFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                                Toast.makeText(getActivity().getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "An error Occured ", Toast.LENGTH_LONG).show();

                    }

                });

            }
        });





        return view;
    }
}