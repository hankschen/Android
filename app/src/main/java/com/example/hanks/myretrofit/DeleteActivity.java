package com.example.hanks.myretrofit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Iterator;

public class DeleteActivity extends AppCompatActivity {
    Button btnCancel,btnDel;
    EditText etName,etSex,etBirthday,etEmail,etPhone,etAddress;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        findviews();
        Intent intent = getIntent();
        int position = (int) intent.getExtras().getSerializable("項目");
        MyApp myApp = (MyApp) getApplicationContext();
        Iterator iterator = myApp.result.iterator();
        int count = 0;
        while (iterator.hasNext()){ //一次抓一筆資料
            Repo repo = (Repo)iterator.next(); //取資料
            if(count == position){ //比對傳過來資料的比數index與這邊取到資料的筆數index
                etName.setText(repo.cName);
                etSex.setText(repo.cSex);
                etBirthday.setText(repo.cBirthday);
                etEmail.setText(repo.cEmail);
                etPhone.setText(repo.cPhone);
                etAddress.setText(repo.cAddr);
                break; //抓完一筆資料就跳出迴圈,這樣就不會一直在會圈裡跑到全部資料讀完
            }
            ++count;
        }

    }

    void findviews(){
        btnCancel = (Button)findViewById(R.id.button2);
        btnDel = (Button)findViewById(R.id.button);
        etName = (EditText)findViewById(R.id.editText);
        etSex = (EditText)findViewById(R.id.editText2);
        etBirthday = (EditText)findViewById(R.id.editText3);
        etEmail = (EditText)findViewById(R.id.editText4);
        etPhone = (EditText)findViewById(R.id.editText5);
        etAddress = (EditText)findViewById(R.id.editText6);
    }

    public void onCancel(View view){
        Intent result = getIntent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();
    }

    public void onDelete(View view){
        Intent result = getIntent();
        setResult(Activity.RESULT_OK, result);
        finish();
    }


}
