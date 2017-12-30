package com.enseirb.joinme;

import android.content.Context;
import android.hcherif.enseirb.com.joinmeapplication.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

/**
 * Created by jemai on 29/12/17.
 */

public class CustomListItem extends ArrayAdapter<Contact> {

        private int             resource;
        private LayoutInflater inflater;
        private Context context;
        public static List<Contact> selectedContacts=new ArrayList<Contact>();

        public CustomListItem ( Context ctx, int resourceId, List<Contact> objects) {
            super( ctx, resourceId, objects );
            resource = resourceId;
            inflater = LayoutInflater.from( ctx );
            context=ctx;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent ) {
            convertView = (LinearLayout ) inflater.inflate( resource, null );
            final Contact contact = getItem( position );
            TextView contactName = (TextView) convertView.findViewById(R.id.contact_name);
            contactName.setText(contact.getName());

            TextView contactPhone = (TextView) convertView.findViewById(R.id.phone_number);
            contactPhone.setText(contact.getPhone_number());

             final CheckBox checkbox = (CheckBox)convertView.findViewById(R.id.check);


                   checkbox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                         //   String element = (String) checkbox.getTag();
                            if(isChecked) {

                                selectedContacts.add(contact);
                            }
                            else{
                                selectedContacts.remove(contact);
                            }

                        //    Toast.makeText(context,selectedContacts.size()+"",Toast.LENGTH_LONG).show();
                          //  Toast.makeText(context,contact.getName(),Toast.LENGTH_LONG).show();


                        }
                    });

            return convertView;
        }

    public List<Contact> getSelectedContacts() {
        return selectedContacts;
    }

    public void setSelectedContacts(List<Contact> selectedContacts) {
        this.selectedContacts = selectedContacts;
    }
}
