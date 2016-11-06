package smt.wanida.esymocro;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {
// Expllicit การประกาศตัวแปร  จะต้ะองอยู่นอก class ทั้งหมด
    private Button signInbutton, singUpbutton;
    private EditText userEditText, passEditText;
    private String userString, passwordString;
    private MyConstante myConstante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myConstante = new MyConstante();

        //bind Widget
        signInbutton = (Button) findViewById(R.id.button);
        singUpbutton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passEditText = (EditText) findViewById(R.id.editText5);

        //singUp controller
        singUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

        //signIn Create
        signInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value from edit text
                userString = userEditText.getText().toString().trim();
                passwordString = passEditText.getText().toString().trim();

                //check space
                if (userString.equals("") || passwordString.equals("")) {
                    //have space
                    MyAlert myAlert = new MyAlert(MainActivity.this,R.drawable.kon48,
                            getResources().getString(R.string.title_naveSpace)
                    ,getResources().getString(R.string.Message_haveSpace));

                    myAlert.myDailog();
                } else {
                    // no space
                    GetUser getUser = new GetUser(MainActivity.this);
                    getUser.execute(myConstante.getUrlJson());
                }


            } //onclick
        });

    } // Main Method

    private class GetUser extends AsyncTask<String, Void, String> {
        private Context context;

        public GetUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(params[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }//doIn

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("6NovV3", "Jsom ==>" + s);

        }//onpost
    } // calss


}  // Main Class นี่คือคลาสหลัก
