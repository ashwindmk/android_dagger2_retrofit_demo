package com.ashwin.android.retrofitdagger2demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ashwin.android.retrofitdagger2demo.di.components.ApiComponent;
import com.ashwin.android.retrofitdagger2demo.di.components.DaggerApiComponent;
import com.ashwin.android.retrofitdagger2demo.di.modules.ApiModule;
import com.ashwin.android.retrofitdagger2demo.di.modules.AppModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Inject
    public Retrofit retrofit;

    private ApiComponent apiComponent;

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(getApplication()))
                .apiModule(new ApiModule("https://jsonplaceholder.typicode.com/"))
                .build();

        apiComponent.inject(this);

        responseTextView = (TextView) findViewById(R.id.response_textview);
    }

    public void load(View view) {
        RetrofitApi api = retrofit.create(RetrofitApi.class);
        Call<ResponseBody> call = api.getUser();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int responseCode = response.code();
                String text = "null";
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.i("dagger2-demo", "Response: " + response.raw());
                    JSONObject user = null;
                    try {
                        user = new JSONObject(response.body().string());
                        text = user.toString(2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    text = "Error response code: " + responseCode;
                }
                responseTextView.setText(text);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("dagger2-demo", "Response failure", t);
                responseTextView.setText("Response Failure :(");
            }
        });
    }
}
