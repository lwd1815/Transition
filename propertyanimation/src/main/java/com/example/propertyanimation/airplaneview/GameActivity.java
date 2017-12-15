package com.example.propertyanimation.airplaneview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.propertyanimation.R;
import com.example.propertyanimation.airplaneview.logic.GameView;

public class GameActivity extends AppCompatActivity {
  private GameView gameView;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    gameView= (GameView) findViewById(R.id.game_view);
    int[] bitmapIds = {
        R.drawable.plane,
        R.drawable.explosion,
        R.drawable.yellow_bullet,
        R.drawable.blue_bullet,
        R.drawable.small,
        R.drawable.middle,
        R.drawable.big,
        R.drawable.bomb_award,
        R.drawable.bullet_award,
        R.drawable.pause1,
        R.drawable.pause2,
        R.drawable.bomb
    };
    gameView.start(bitmapIds);
  }

  @Override protected void onPause() {
    super.onPause();
    if (gameView!=null){
      gameView.pause();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (gameView!=null){
      gameView.destory();
    }
    gameView=null;
  }
}
