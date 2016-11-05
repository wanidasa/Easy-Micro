package smt.wanida.esymocro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
// Expllicit การประกาศตัวแปร  จะต้ะองอยู่นอก class ทั้งหมด
    private Button signInbutton, singUpbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind Widget
        signInbutton = (Button) findViewById(R.id.button);
        singUpbutton = (Button) findViewById(R.id.button2);

        //singUp controller
        singUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });

    } // Main Method
}  // Main Class นี่คือคลาสหลัก
