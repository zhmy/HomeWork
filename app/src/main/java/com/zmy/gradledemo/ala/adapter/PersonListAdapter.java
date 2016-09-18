package com.zmy.gradledemo.ala.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmy.gradledemo.R;
import com.zmy.gradledemo.ala.data.LiveInfoData;
import com.zmy.gradledemo.ala.data.PersonUserData;
import com.zmy.gradledemo.ala.data.UserInfoData;

import java.util.List;

/**
 * Created by zmy on 16/9/17.
 */
public class PersonListAdapter extends BaseAdapter {
    private Context context;
    private List<PersonUserData> list;
    private int type;

    public PersonListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PersonUserData> list, int type) {
        this.list = list;
        this.type = type;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonRecordViewHolder recordViewHolder = null;
        PersonFansViewHolder fansViewHolder = null;
        if (convertView == null) {
            if (type == PersonUserData.PERSON_RECORDS_TYPE) {
                convertView = LayoutInflater.from(context).inflate(R.layout.person_record_item, null);
                recordViewHolder = new PersonRecordViewHolder();
                recordViewHolder.record_image = (ImageView) convertView.findViewById(R.id.record_image);
                recordViewHolder.record_location = (TextView) convertView.findViewById(R.id.record_location);
                recordViewHolder.record_time_ago = (TextView) convertView.findViewById(R.id.record_time_ago);
                recordViewHolder.record_title = (TextView) convertView.findViewById(R.id.record_title);
                recordViewHolder.record_see_user = (TextView) convertView.findViewById(R.id.record_see_user);
                convertView.setTag(recordViewHolder);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.person_info_item, null);
                fansViewHolder = new PersonFansViewHolder();
                fansViewHolder.attention_operation = (ImageView) convertView.findViewById(R.id.attention_operation);
                fansViewHolder.user_icon = (ImageView) convertView.findViewById(R.id.user_icon);
                fansViewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
                convertView.setTag(fansViewHolder);
            }
        } else {
            if (type == PersonUserData.PERSON_RECORDS_TYPE) {
                recordViewHolder = (PersonRecordViewHolder) convertView.getTag();
                LiveInfoData data = list.get(position).getLive_info();
                if (data != null) {
//                    recordViewHolder.record_image.setImageURI(data.getCover());
                    recordViewHolder.record_title.setText(data.getDescription());
                    recordViewHolder.record_see_user.setText(String.valueOf(data.getJoin_count()));
                    if (!TextUtils.isEmpty(data.getLocation())) {
                        recordViewHolder.record_location.setVisibility(View.VISIBLE);
                        recordViewHolder.record_location.setText(data.getLocation());
                    } else {
                        recordViewHolder.record_location.setVisibility(View.GONE);
                    }
                    recordViewHolder.record_time_ago.setText("几天前");//start_time
                }

            } else {
                fansViewHolder = (PersonFansViewHolder) convertView.getTag();
                UserInfoData data = list.get(position).getUser_info();
                if (data != null) {
//                    fansViewHolder.user_icon.setImageURI(data.getPortrait());
                    fansViewHolder.user_name.setText(data.getUser_name());
                    if (type == PersonUserData.PERSON_FANS_TYPE) {
//                        fansViewHolder.attention_operation.setImageURI();//不一定关注
                    } else {
//                        fansViewHolder.attention_operation.setImageURI();//已关注
                    }
                }
            }
        }
        return convertView;
    }

    class PersonRecordViewHolder {
        public ImageView record_image;
        public TextView record_time_ago, record_title, record_location, record_see_user;
    }

    class PersonFansViewHolder {
        public ImageView user_icon, attention_operation;
        public TextView user_name;
    }
}
