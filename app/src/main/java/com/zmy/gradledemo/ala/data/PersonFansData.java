package com.zmy.gradledemo.ala.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zmy on 16/9/17.
 */
public class PersonFansData extends ServerCommonData {

    public static class Data {
        private List<PersonUserData> list;
        private PageInfoData page;

        public List<PersonUserData> getList() {
            return list;
        }

        public void setList(List<PersonUserData> list) {
            this.list = list;
        }

        public PageInfoData getPage() {
            return page;
        }

        public void setPage(PageInfoData page) {
            this.page = page;
        }
    }

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }
}
