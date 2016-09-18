package com.zmy.gradledemo.ala.data;

/**
 * Created by zmy on 16/9/17.
 */
public class RelationInfoData {
    private String user_id;
    private String to_user_id;
    private int follow_status;
    private int push_switch;
    private int like_degree;
    private int dislike_degree;
    private int intimacy_degree;
    private int mask_status;
    private String update_time;
    private String create_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
    }

    public int getPush_switch() {
        return push_switch;
    }

    public void setPush_switch(int push_switch) {
        this.push_switch = push_switch;
    }

    public int getLike_degree() {
        return like_degree;
    }

    public void setLike_degree(int like_degree) {
        this.like_degree = like_degree;
    }

    public int getDislike_degree() {
        return dislike_degree;
    }

    public void setDislike_degree(int dislike_degree) {
        this.dislike_degree = dislike_degree;
    }

    public int getIntimacy_degree() {
        return intimacy_degree;
    }

    public void setIntimacy_degree(int intimacy_degree) {
        this.intimacy_degree = intimacy_degree;
    }

    public int getMask_status() {
        return mask_status;
    }

    public void setMask_status(int mask_status) {
        this.mask_status = mask_status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
