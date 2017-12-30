package com.enseirb.joinme;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jemai on 29/12/17.
 */

public class Contact implements Parcelable{

    private String name;
    private String phone_number;


    public Contact(){
        name=null;
        phone_number=null;
    }

    public Contact(String name,String phone_number) {
        this.name = name;
        this.phone_number=phone_number;
    }
    public Contact(Parcel source) {
       name=source.readString();
        phone_number=source.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>()
    {
        @Override
        public Contact createFromParcel(Parcel source)
        {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size)
        {
            return new Contact[size];
        }
    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone_number);
    }
}
