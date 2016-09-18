package com.zmy.gradledemo.ala.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zmy on 16/9/18.
 */
public class PersonUserInfoData extends ServerCommonData {

    @SerializedName("data")
    private PersonUserData data;

    public void setData(PersonUserData data) {
        this.data = data;
    }

    public PersonUserData getData() {
        return data;
    }

}
