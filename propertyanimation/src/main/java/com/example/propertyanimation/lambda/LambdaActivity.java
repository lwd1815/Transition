package com.example.propertyanimation.lambda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.propertyanimation.R;

public class LambdaActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lambda);
    new Thread(()->System.out.println("   ")).start();
  }

}
