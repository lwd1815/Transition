package com.example.lwd18.headzoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    Button click = (Button) findViewById(R.id.click);

    click.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String url = "https://www.shineyie.com/shi?words="+"我爱你"+"&mode="+"1"+"&length="+"5"+"&yayun="+"1";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
            .url(url)
            .build();
        // TODO: 2017/12/20 如果依赖的包时okhttp2.0.4现在似乎不走回调了 
        Call call=mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
          @Override public void onFailure(Call call, IOException e) {
            e.printStackTrace();
          }

          @Override public void onResponse(Call call, Response response) throws IOException {
            System.out.println("res="+response);
          }
        });
      }
    });
  }
}
