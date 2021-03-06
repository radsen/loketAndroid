package com.kzlabs.loket.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by radsen on 10/18/16.
 */

public class Pocket implements Parcelable{

    private String id;
    private float value;
    private User origin;
    private User destination;
    private String description;
    private int status;

    protected Pocket(Parcel in) {
        id = in.readString();
        value = in.readFloat();
        origin = in.readParcelable(User.class.getClassLoader());
        destination = in.readParcelable(User.class.getClassLoader());
        description = in.readString();
        status = in.readInt();
    }

    public static final Creator<Pocket> CREATOR = new Creator<Pocket>() {
        @Override
        public Pocket createFromParcel(Parcel in) {
            return new Pocket(in);
        }

        @Override
        public Pocket[] newArray(int size) {
            return new Pocket[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public User getDestination() {
        return destination;
    }

    public void setDestination(User destination) {
        this.destination = destination;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeFloat(value);
        parcel.writeParcelable(origin, i);
        parcel.writeParcelable(destination, i);
        parcel.writeString(description);
        parcel.writeInt(status);
    }
}
