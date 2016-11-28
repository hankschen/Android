package com.example.hanks.myretrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
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
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        //Delete data
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("項目", position);
                startActivityForResult(intent, 0);
            }
        };
        listView.setOnItemClickListener(itemClickListener);

        //Update data
        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("項目", position);
                startActivityForResult(intent, 2);
                return true;
            }
        };
        listView.setOnItemLongClickListener(itemLongClickListener);

        //Set ListView & readData
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
                //.baseUrl("http://192.168.137.53:8081/PHP_Project_forXAMPP/11-14_projectForAll/")
                .baseUrl("http://127.0.0.1:80/PHP_Project_forXAMPP/11-14_projectForAll/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApp myApp = (MyApp) getApplicationContext();
        myApp.service = retrofit.create(GitHubService.class);

//        Call<List<Repo>> repos = service.listRepos("octocat");
//        Call<List<Repo>> repos = service.listRepos("api");
        myApp.repos = myApp.service.listRepos();

        //非同步呼叫
        myApp.repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();

                Iterator iterator = myApp.result.iterator();
                if(iterator != null) {
                    while (iterator.hasNext()) {
                        adapter.add(((Repo)iterator.next()).cName);
                    }
                }else{
                    Toast.makeText(MainActivity.this,"抱歉....沒有資料 !",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //Add data
    public void onAdd(View view){
        Intent intent = new Intent(MainActivity.this,Add.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: //Delete data
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "沒有刪除資料....", Toast.LENGTH_SHORT).show();
                }
                if (resultCode == Activity.RESULT_OK) {
                    final int position = (int) data.getExtras().getSerializable("項目");
                    MyApp myApp = (MyApp) getApplicationContext();
                    myApp.delete = myApp.service.delete(String.valueOf(myApp.result.get(position).cID));
                    myApp.delete.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(MainActivity.this, "資料刪除ok!", Toast.LENGTH_SHORT).show();
                            updateListView();
//                            MyApp myApp = (MyApp) getApplicationContext();
//                            myApp.result.remove(position); //刪掉local端的result資料
//                            //更新ListView
//                            adapter.remove(adapter.getItem(position));
//                            adapter.notifyDataSetChanged();
//                            Toast.makeText(MainActivity.this, "資料刪除ok!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "資料刪除失敗!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case 1: // Add data
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "沒有新增資料....", Toast.LENGTH_SHORT).show();
                }
                if (resultCode == Activity.RESULT_OK) {
                    MyApp myApp = (MyApp) getApplicationContext();
                    Repo repo = new Repo();
                    Intent intent = getIntent();
                    repo.cName = intent.getStringExtra("name");
                    repo.cSex = intent.getStringExtra("sex");
                    repo.cBirthday = intent.getStringExtra("birthday");
                    repo.cEmail = intent.getStringExtra("email");
                    repo.cPhone = intent.getStringExtra("phone");
                    repo.cAddr = intent.getStringExtra("address");
//                    repo.cName = "Hanks";
//                    repo.cName = intent.getStringExtra("name");
//                    repo.cSex = "M";
//                    repo.cBirthday = "1970-11-20";
//                    repo.cEmail = "chen@gmail.com";
//                    repo.cPhone = "0921999999";
//                    repo.cAddr = "taichung";
                    myApp.addByFormPost = myApp.service.addByFormPost(repo.cName,repo.cSex,repo.cBirthday,repo.cEmail,repo.cPhone,repo.cAddr);
                    myApp.addByFormPost.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(MainActivity.this, "資料新增ok!(add by form post ok)", Toast.LENGTH_SHORT).show();
                            updateListView();
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "資料新增失敗!", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Toast.makeText(MainActivity.this, "新增資料ok!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2: // Update data
                if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(MainActivity.this,"資料未修改..",Toast.LENGTH_SHORT).show();
                }
                if(resultCode == Activity.RESULT_OK){
                    MyApp myApp = (MyApp) getApplicationContext();
                    Repo repo = new Repo();
                    Intent intent = getIntent();
                    repo.cName = intent.getStringExtra("name");
                    repo.cSex = intent.getStringExtra("sex");
                    repo.cBirthday = intent.getStringExtra("birthday");
                    repo.cEmail = intent.getStringExtra("email");
                    repo.cPhone = intent.getStringExtra("phone");
                    repo.cAddr = intent.getStringExtra("address");
                    myApp.update = myApp.service.update(repo.cName,repo.cSex,repo.cBirthday,repo.cEmail,repo.cPhone,repo.cAddr);
                    myApp.update.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(MainActivity.this, "資料修改ok!", Toast.LENGTH_SHORT).show();
                            updateListView();
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "資料修改失敗!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
    public void updateListView() {
        MyApp myApp = (MyApp) getApplicationContext();
        Call<List<Repo>> reposClone = myApp.repos.clone();
        reposClone.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                MyApp myApp = (MyApp) getApplicationContext();
                myApp.result = response.body();

                Iterator it = myApp.result.iterator();
                adapter.clear();
                while(it.hasNext()) {
                    adapter.add(((Repo) it.next()).cName);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
