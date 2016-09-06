package com.zmy.library;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;

/**
 * 获取主进程通用shared preferences值的provider
 * 
 */
public class MainSharedPrefProvider extends ContentProvider {

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		String key = uri.getLastPathSegment();
		if (key != null && key.length() > 0) {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				return sp.getString(key, null);
			}
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (values != null && values.size() > 0) {
			String key = uri.getLastPathSegment();
			String value = values.getAsString(key);
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor editor = sp.edit();
				editor.putString(key, value);
				editor.commit();
				if (needBroadcast(key)) {
					sendBroadcast(key, value);
				}
			}
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String key = uri.getLastPathSegment();
		if (key != null && key.length() > 0) {
			SharedPreferences sp = getSharedPreferences();
			if (sp != null) {
				Editor editor = sp.edit();
				editor.remove(key);
				editor.commit();
				if (needBroadcast(key)) {
					sendBroadcast(key, null);
				}
			}
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		return 0;
	}

	private void sendBroadcast(String key, String value) {
		Intent intent = new Intent();
		intent.setAction(BaseApplication.BROADCAST_CHANGE_SHARED_PREF);
		intent.putExtra(SharedPrefConfig.INTENT_KEY, key);
		intent.putExtra(SharedPrefConfig.INTENT_VALUE, value);
		BaseApplication.getInstance().getApp().sendBroadcast(intent);
	}

	private boolean needBroadcast(String key) {
		if (key == null || key.length() == 0) {
			return false;
		}
		int len = SharedPrefConfig.BROADCAST_KEYS.length;
		for (int i = 0; i < len; i++) {
			if (SharedPrefConfig.BROADCAST_KEYS[i].equals(key)) {
				return true;
			}
		}
		return false;
	}

	private SharedPreferences getSharedPreferences() {
		String file = SharedPrefConfig.COMMON_SETTINGS_NAME;
		try {
			if (BaseApplication.getInstance().getApp() != null) {
				return BaseApplication.getInstance().getApp().getSharedPreferences(file,
						Context.MODE_PRIVATE);
			}
		} catch (Exception ex) {
			return null;
		}
		return null;
	}
}
