package com.example.mad_tute_05_data_handling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad_tute_05_data_handling.database.DBHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edit_username, edit_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_username = findViewById(R.id.edit_username);
        edit_pw = findViewById(R.id.edit_pw);

    }

    public  void saveUser(View view){
        String name = edit_username.getText().toString();
        String password = edit_pw.getText().toString();
        DBHandler dbHandler = new DBHandler(this);


        if (name.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Enter values",Toast.LENGTH_SHORT).show();
        }else{
            long inserted = dbHandler.addInfo(name,password);

            if (inserted > 0){
                Toast.makeText(this,"Data inserted successfully !",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void viewAll(View view){
        DBHandler dbHandler = new DBHandler(this);

        List info = dbHandler.readAll();

        String[] infoArray = (String[]) info.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Users Details");

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userName = infoArray[i].split(":")[0];
               // Toast.makeText(MainActivity.this,userName,Toast.LENGTH_SHORT).show();

                edit_username.setText(userName);
                edit_pw.setText("********");

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUser(View view){
        DBHandler dbHandler = new DBHandler(this);
        String userName = edit_username.getText().toString();

        if(userName.isEmpty()){
            Toast.makeText(this,"Select a user",Toast.LENGTH_SHORT).show();
        }else{
            dbHandler.deleteInfo(userName);
            Toast.makeText(this,userName + " user is deleted",Toast.LENGTH_SHORT).show();
            edit_username.setText("");
            edit_pw.setText("");
        }
    }

    public void updateUser(View view){
        DBHandler dbHandler = new DBHandler(this);

        String userName = edit_username.getText().toString();
        String password = edit_pw.getText().toString();

        if(userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Select or type user",Toast.LENGTH_SHORT).show();
        }else{
            dbHandler.updateInfo(view,userName,password);
            //Toast.makeText(this,userName + " user is updated",Toast.LENGTH_SHORT).show();
            edit_username.setText("");
            edit_pw.setText("");
        }
    }

}