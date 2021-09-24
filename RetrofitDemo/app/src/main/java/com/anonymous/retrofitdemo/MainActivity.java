package com.anonymous.retrofitdemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        jsonPlaceHolderApi = RetrofitClient.getRetrofitInstance().create(JsonPlaceHolderApi.class);

        getPosts();
//        getComments();

    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(4);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textView.setText(MessageFormat.format("Error Code: {0}", response.code()));
                    return;
                }

                List<Comment> comments = response.body();
                for (Comment c :
                        comments) {
                    String content = "";

                    content += "ID: " + c.getId() + "\n";
                    content += "Post ID: " + c.getPostId() + "\n";
                    content += "Name: " + c.getName() + "\n";
                    content += "E-mail: " + c.getEmail() + "\n";
                    content += "Text: " + c.getText() + "\n\n";


                    textView.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }

    private void getPosts() {
        Call<List<Posts>> call = jsonPlaceHolderApi.getPosts(2);

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(MessageFormat.format("Code: {0}", response.code()));
                    return;
                }

                List<Posts> posts = response.body();
                for (Posts posts1 :
                        posts) {
                    String content = "";

                    content += "ID: " + posts1.getId() + "\n";
                    content += "User ID: " + posts1.getUserId() + "\n";
                    content += "Title: " + posts1.getTitle() + "\n";
                    content += "Text: " + posts1.getText() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}