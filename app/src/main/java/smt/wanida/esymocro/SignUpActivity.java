package smt.wanida.esymocro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, userString, passwordString;
    private Uri uri;


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


                } // if


            } // onclick
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode) == 0 && (resultCode) == RESULT_OK ) {
            //Resule Success
            Log.d("5novV1", "Result OK");

            //Setup choose image to ImageView
            uri = data.getData();
            try{
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e){
                e.printStackTrace();
            }

        } //if

    } // onActivityResult
} //  Main Class
