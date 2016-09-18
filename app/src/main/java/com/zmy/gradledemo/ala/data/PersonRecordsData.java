package com.zmy.gradledemo.ala.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zmy on 16/9/17.
 */
public class PersonRecordsData extends ServerCommonData {

    public static class Data {
        private List<PersonUserData> feed_list;
        private PageInfoData page;

        public List<PersonUserData> getFeed_list() {
            return feed_list;
        }

        public void setFeed_list(List<PersonUserData> feed_list) {
            this.feed_list = feed_list;
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
