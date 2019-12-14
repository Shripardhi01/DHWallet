package com.dynast.dhwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dynast.dhwallet.api.APIService;
import com.dynast.dhwallet.api.APIUrl;
import com.dynast.dhwallet.model.User;
import com.dynast.dhwallet.model.Result;
import com.dynast.dhwallet.helper.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class RegisterActivity extends AppCompatActivity {

    private TextView lgn_link;
    private EditText name, email, mobile, pass1;
    private Button btn_reg;
    String email1 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lgn_link = findViewById(R.id.txt_login);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        mobile = findViewById(R.id.reg_mob);
        pass1 = findViewById(R.id.reg_pass1);
        btn_reg = findViewById(R.id.signup_btn);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().length() == 0 ||
                   email.getText().length()==0 ||
                   mobile.getText().length()==0 ||
                   pass1.getText().length()==0 ){
                    Toast.makeText(getApplicationContext(), "All fields are Required. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                        if(mobile.getText().length()!=10){
                            Toast.makeText(getApplicationContext(), "Invalid Mobile Number.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(pass1.getText().length()<6){
                                Toast.makeText(getApplicationContext(), "Password must be more than 6 character.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                            // coding for registration
                            userSignUp();
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Email.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        lgn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void userSignUp() {
        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String mobile1 = mobile.getText().toString().trim();
        String pass = pass1.getText().toString().trim();

        APIService service = APIUrl.getApiClient().create(APIService.class);

        User user = new User(name1, email1, pass, mobile1);

        //defining the call
        Call<Result> call = service.createUser(
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getMobile()
        );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                //if there is no error
                if (!response.body().getError()) {
                    //starting profile activity
                    finish();
//                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
