package com.dynast.dhwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dynast.dhwallet.api.APIService;
import com.dynast.dhwallet.api.APIUrl;
import com.dynast.dhwallet.helper.SharedPrefManager;
import com.dynast.dhwallet.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    TextView reg_link;
    Button btn_login;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg_link = findViewById(R.id.txt_register);
        btn_login = findViewById(R.id.lgn_btn);
        username = findViewById(R.id.lgn_email);
        password = findViewById(R.id.lgn_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(username.getText().length() == 0 || password.getText().length() == 0){
                Toast.makeText(getApplicationContext(),"All fields are required.", Toast.LENGTH_SHORT).show();
            }else {
                userLogin();
            }
//                Intent i = new Intent(v.getContext(), DashboardActivity.class);
//                startActivity(i);
            }
        });

        reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    void userLogin(){
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        APIService service = APIUrl.getApiClient().create(APIService.class);

        //defining the call
        Call<Result> call = service.userLogin(user, pass);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (!response.body().getError()) {
                    finish();
//                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("error",t.getMessage());
            }
        });
    }
}
