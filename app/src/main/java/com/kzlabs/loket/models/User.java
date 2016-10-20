package com.kzlabs.loket.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by radsen on 10/18/16.
 */
public class User implements Parcelable {

    private String id;
    private String token;
    private String phoneNumber;

    protected User(Parcel in) {
        id = in.readString();
        token = in.readString();
        phoneNumber = in.readString();
    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(token);
        parcel.writeString(phoneNumber);
    }
}
