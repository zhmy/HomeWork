package com.zmy.gradledemo;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zmy.gradledemo.ala.PersonWrapperFragment;
import com.zmy.gradledemo.ala.PersonInfoActivity;
import com.zmy.gradledemo.alazan.PeriscopeLayout;
import com.zmy.gradledemo.annotation.AnnotationActivity;
import com.zmy.gradledemo.rn.RnMainActivity;
import com.zmy.gradledemo.rn.RnTestActivity;
import com.zmy.gradledemo.tab.TabTestActivity;
import com.zmy.gradledemo.text.TextViewActivity;
import com.zmy.gradledemo.vector.TestVectorActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView api_url;
    private ListView listview;
    String[] strs = new String[]{
            "RN test", "Annotation test", "Vector Drawable", "Text View", "Tab test"
    };

    private SwipeRefreshLayout swipeLayout;
    private LinearLayout outer;
    private View inner;
    private View zan_bg;
    private PeriscopeLayout zanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        zan_bg = findViewById(R.id.zan_bg);
        zanView = (PeriscopeLayout) findViewById(R.id.zanView);
        zan_bg.setOnClickListener(this);

        outer = (LinearLayout) findViewById(R.id.outer);
        inner = findViewById(R.id.inner);
        outer.setOnClickListener(this);

        api_url = (TextView) findViewById(R.id.api_url);
        api_url.setText(BuildConfig.API_URL);
        api_url.setOnClickListener(this);

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class clazz = null;
                switch (position) {
                    case 0:
                        clazz = RnMainActivity.class;
                        break;
                    case 1:
                        clazz = AnnotationActivity.class;
                        break;
                    case 2:
                        clazz = TestVectorActivity.class;
                        break;
                    case 3:
                        clazz = TextViewActivity.class;
                        break;
                    case 4:
                        clazz = TabTestActivity.class;
                        break;
                    default:
                        break;
                }
                if (clazz != null) {
                    startActivity(new Intent(MainActivity.this, clazz));
                }
            }
        });

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    boolean isShowClose;
    PersonWrapperFragment personFragment;

    private void startCloseAnim(final boolean isShow) {
        isShowClose = isShow;

//        if (isShow) {
//            inner.setVisibility(View.VISIBLE);
//        } else {
//            inner.setVisibility(View.GONE);
//        }
//        if (isShow) {
//            personFragment = new PersonWrapperFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.push_up_in_overshoot, R.anim.push_up_out);
//            transaction.add(R.id.container, personFragment).commit();
//        } else {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.push_up_in_overshoot, R.anim.push_up_out);
//            transaction.remove(personFragment).commit();
//        }

        final int startValue = isShow ? 0 : 200;
        final int endValue = isShow ? 200 : 0;

        ValueAnimator ballBouncer = ValueAnimator.ofInt(startValue, endValue);
        ballBouncer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator ballBouncer) {
                final int animatedValue = (Integer) ballBouncer
                        .getAnimatedValue();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) inner.getLayoutParams();
                lp.height = animatedValue;
                inner.setLayoutParams(lp);
            }
        });

        ballBouncer.setDuration(300);
        ballBouncer.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.api_url:
                if (BuildConfig.DEBUG) {
                    startActivity(new Intent(MainActivity.this, PersonInfoActivity.class));
                } else {
                    startCloseAnim(!isShowClose);
                }
                break;
            case R.id.zan_bg:
                zanView.addHeart();
                break;
            case R.id.outer:
                startCloseAnim(!isShowClose);
                break;
            default:
                break;
        }
    }
}
