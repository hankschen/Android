package com.example.hanks.myretrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
//        String[] data = {"test1","Test2","Test3"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        adapter = new ArrayAdapter<String>(this,R.layout.myview);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
//                String itemContents = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(MainActivity.this,"你的選擇是: "+itemContents,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("項目",position);
                startActivityForResult(intent,0);
            }
        };

        listView.setOnItemClickListener(itemClickListener);

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
                .baseUrl("http://192.168.58.19:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

//        Call<List<Repo>> repos = service.listRepos("octocat");
        Call<List<Repo>> repos = service.listRepos("api");

        //非同步呼叫
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();

                Iterator iterator = myApp.result.iterator();
                if(iterator != null) {
                    while (iterator.hasNext()) {
                        adapter.add(((Repo)iterator.next()).cName);
//                        adapter.add(String.valueOf(((Repo)iterator.next()).cID));
                    }
                }else{
                    System.out.println("抱歉....沒有資料 !");
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_CANCELED){

        }

        if(resultCode == Activity.RESULT_OK){

        }

    }
}
