package com.example.propertyanimation.airplaneview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.propertyanimation.R;

public class AirPlaneActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_air_plane);
    Button start = (Button) findViewById(R.id.air_button);
    start.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(AirPlaneActivity.this,GameActivity.class));
      }
    });
  }
}
