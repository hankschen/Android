package com.example.hanks.myretrofit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText etName,etSex,etBirthday,etEmail,etPhone,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findviews();
        setData();
    }

    void findviews(){
        etName = (EditText)findViewById(R.id.editText);
        etSex = (EditText)findViewById(R.id.editText2);
        etBirthday = (EditText)findViewById(R.id.editText3);
        etEmail = (EditText)findViewById(R.id.editText4);
        etPhone = (EditText)findViewById(R.id.editText5);
        etAddress = (EditText)findViewById(R.id.editText6);
    }

    public void onUpdate(View view){
        Intent intent = new Intent();
        intent.putExtra("name", etName.getText().toString());
        intent.putExtra("sex", etSex.getText().toString());
        intent.putExtra("birthday", etBirthday.getText().toString());
        intent.putExtra("email", etEmail.getText().toString());
        intent.putExtra("phone", etPhone.getText().toString());
        intent.putExtra("address", etAddress.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onRewrite(View view){
        setData();
    }

    public void onCancel(View view){
        Intent intent = getIntent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    void setData(){
        Intent intent = getIntent();
        int position = (int) intent.getExtras().getSerializable("項目");
        MyApp myApp = (MyApp) getApplicationContext();
        Repo repo = myApp.result.get(position);
        etName.setText(repo.cName);
        etSex.setText(repo.cSex);
        etBirthday.setText(repo.cBirthday);
        etEmail.setText(repo.cEmail);
        etPhone.setText(repo.cPhone);
        etAddress.setText(repo.cAddr);
    }
}
