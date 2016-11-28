package com.example.hanks.myretrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Add extends AppCompatActivity {
    EditText etName,etSex,etBirthday,etEmail,etPhone,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
    }

    void findViews(){
        etName = (EditText)findViewById(R.id.editText);
        etSex = (EditText)findViewById(R.id.editText2);
        etBirthday = (EditText)findViewById(R.id.editText3);
        etEmail = (EditText)findViewById(R.id.editText4);
        etPhone = (EditText)findViewById(R.id.editText5);
        etAddress = (EditText)findViewById(R.id.editText6);
    }

    public void onAdd(View view){
        Intent result = new Intent();
        result.putExtra("name", etName.getText().toString());
        result.putExtra("sex", etSex.getText().toString());
        result.putExtra("birthday", etBirthday.getText().toString());
        result.putExtra("email", etEmail.getText().toString());
        result.putExtra("phone", etPhone.getText().toString());
        result.putExtra("address", etAddress.getText().toString());
        setResult(Activity.RESULT_OK, result);
        finish();

    }

    public void onCancel(View view){
        Intent result = getIntent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();

    }
}
