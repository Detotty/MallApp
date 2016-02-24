package com.mallapp.Model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Sharjeel on 24/02/2016.
 */
public class ContactModel {
    @DatabaseField(id = true)
    String profile_id="";
    @DatabaseField
    String profile_pic_url="";
    @DatabaseField
    String name="";
    @DatabaseField
    String phone_number="";

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
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

    public void setPhone_number(String phone_numbers) {
        this.phone_number = phone_numbers;
    }
}

