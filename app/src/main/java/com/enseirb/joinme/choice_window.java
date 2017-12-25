package com.enseirb.joinme;

import android.content.Intent;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class choice_window extends AppCompatActivity {
    private ListView choices;
    String [] list_choice={"Saisir un numéro","consulter la liste des contacts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_window);
        choices=(ListView) findViewById(R.id.listChoice);
        choices.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list_choice));
        choices.setItemChecked(0, true);
    }


    public void redirectChoice(View v){
       Intent intent=null;
        if(choices.getCheckedItemPosition()==0){
            intent=new Intent(getApplicationContext(),PhoneNumberSms.class);

        }
        else {
            Toast.makeText(getApplicationContext(),"list of contact",Toast.LENGTH_LONG).show();
        }

        startActivity(intent);
    }
}
