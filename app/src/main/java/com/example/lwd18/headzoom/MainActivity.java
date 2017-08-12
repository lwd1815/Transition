package com.example.lwd18.headzoom;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Scene mScene1,mScene2,mScene3,scene1,scene2,scene3,scene4;
    private RelativeLayout mbaseLayout;
    private TransitionManager mCustomTransitionManager;
    private Button first;
    private Button second;
    private Button thrid;
    private Button four;
    private Button five;
    private Button six;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shapefour);
        mbaseLayout = (RelativeLayout) findViewById(R.id.base);
        mScene1 =  Scene.getSceneForLayout(mbaseLayout,R.layout.shapefirsts,this);
        mScene2 = Scene.getSceneForLayout(mbaseLayout,R.layout.shapefsecond,this);
        mScene3 = Scene.getSceneForLayout(mbaseLayout,R.layout.shapethrid,this);


        scene1 = Scene.getSceneForLayout(mbaseLayout, R.layout.activity_animations_scene1, this);
        scene2 = Scene.getSceneForLayout(mbaseLayout, R.layout.activity_animations_scene2, this);
        scene3 = Scene.getSceneForLayout(mbaseLayout, R.layout.activity_animations_scene3, this);
        scene4 = Scene.getSceneForLayout(mbaseLayout, R.layout.activity_animations_scene4, this);

        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(
            R.transition.slide_from_bottom));

        first = (Button) findViewById(R.id.first);
        second = (Button) findViewById(R.id.second);
        thrid = (Button) findViewById(R.id.thrid);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        thrid.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        switch (view.getId()){
            case R.id.first:
                TransitionManager.go(mScene1);
                break;
            case R.id.second:
                TransitionManager.go(scene1);
                break;
            case R.id.thrid:
                TransitionManager.go(scene2);
                break;
            case R.id.four:
                TransitionManager.go(scene3);
                break;
            case R.id.five:
                TransitionManager.go(scene4, TransitionInflater.from(MainActivity.this).
                    inflateTransition(R.transition.slide_and_changebounds_sequential));
                break;
            case R.id.six:
                TransitionManager.go(mScene3,TransitionInflater.from(MainActivity.this).
                    inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators));
                break;
        }
    }
}
