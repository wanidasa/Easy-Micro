package smt.wanida.esymocro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, userString, passwordString, imageString,
            imagePathString, imageNameString;
    private Uri uri;
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);

        //Sign Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get value from Edit Text
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //check space
                if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
                    // Have Space
                    Log.d("5novV1", "Have space");
                    MyAlert myAlert = new MyAlert(SignUpActivity.this,
                            R.drawable.doremon48,
                            getResources().getString(R.string.title_naveSpace),
                            getResources().getString(R.string.Message_haveSpace));
                    myAlert.myDailog();

                } else if (aBoolean) {
                    //non choose image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this, R.drawable.nobita48,
                            getResources().getString(R.string.title_ImageChoose),
                            getResources().getString(R.string.massesge_ImageChoose));
                    myAlert.myDailog();

                } else {
                    //Upload to Server
                    upLoadImage();
                    upLoadString();

                }

                // if


            } // onclick



            private void upLoadString() {

                imageString = "http://swiftcodingthai.com/mic/Images" + imageNameString;

                MyConstante myConstante = new MyConstante();
                AddNewUser addNewUser = new AddNewUser(SignUpActivity.this);
                addNewUser.execute(myConstante.getUrlAdduser());

            }

            private void upLoadImage() {
                //Change Policy
                StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                        .Builder().permitAll().build();
                StrictMode.setThreadPolicy(threadPolicy);

                try {

                    MyConstante myConstante = new MyConstante();
                    SimpleFTP simpleFTP = new SimpleFTP();
                    simpleFTP.connect(myConstante.getHostString(), myConstante.getPortAnInt()
                            , myConstante.getUserString(), myConstante.getPasswordString());
                    simpleFTP.bin();
                    simpleFTP.cwd("Images");
                    simpleFTP.stor(new File(imagePathString));
                    simpleFTP.disconnect();

                    Toast.makeText(SignUpActivity.this, "Upload Finish",
                            Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } //upLoadImage
        });

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent to Other App
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูป"), 0);


            } // Onclick
        });

    } // Main Method

    private class AddNewUser extends AsyncTask<String, Void, String> {

        private Context context;

        public AddNewUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("Name", nameString)
                        .add("User", userString)
                        .add("Password", passwordString)
                        .add("Image", imageString)
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(params[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("6nov2", "e doInBack ==>" + e.toString());
                return null;

            }

        } // do in back

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("6NovV2", "Result ==>" + s);

        } //on pos
    } // add new user class

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode) == 0 && (resultCode) == RESULT_OK) {
            //Resule Success
            Log.d("5novV1", "Result OK");

            //Setup choose image to ImageView
            uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // check choosed
            aBoolean = false;

            //Find Path of Image Choose
            imagePathString = myFindPath(uri);
            Log.d("6Nov", "Path ==>" + imagePathString);

            //find Name of Image Choose
            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("6Nov", "Name ==>" + imageNameString);


        } //if

    } // onActivityResult

    private String myFindPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result = cursor.getString(index);
        } else {
            result = uri.getPath();
        } //if

        return result;
    }
} //  Main Class
