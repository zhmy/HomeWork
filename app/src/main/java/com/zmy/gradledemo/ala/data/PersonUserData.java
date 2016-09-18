package com.zmy.gradledemo.ala.data;

/**
 * Created by zmy on 16/9/17.
 */
public class PersonUserData {
    public static final int PERSON_RECORDS_TYPE = 0;
    public static final int PERSON_FANS_TYPE = 1;
    public static final int PERSON_FOLLOWS_TYPE = 2;

    private UserInfoData user_info;
    private LocationInfoData location_info;
    private RelationInfoData relation_info;
    private LiveInfoData live_info;
    private String admin_info;//TODO
    private String push_switch;//用户粉丝使用

    public String getPush_switch() {
        return push_switch;
    }

    public void setPush_switch(String push_switch) {
        this.push_switch = push_switch;
    }

    public UserInfoData getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoData user_info) {
        this.user_info = user_info;
    }

    public LocationInfoData getLocation_info() {
        return location_info;
    }

    public void setLocation_info(LocationInfoData location_info) {
        this.location_info = location_info;
    }

    public RelationInfoData getRelation_info() {
        return relation_info;
    }

    public void setRelation_info(RelationInfoData relation_info) {
        this.relation_info = relation_info;
    }

    public LiveInfoData getLive_info() {
        return live_info;
    }

    public void setLive_info(LiveInfoData live_info) {
        this.live_info = live_info;
    }

    public String getAdmin_info() {
        return admin_info;
    }

    public void setAdmin_info(String admin_info) {
        this.admin_info = admin_info;
    }
}
