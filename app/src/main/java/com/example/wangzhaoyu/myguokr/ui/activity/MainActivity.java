package com.example.wangzhaoyu.myguokr.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangzhaoyu.myguokr.R;
import com.example.wangzhaoyu.myguokr.model.response.User;
import com.example.wangzhaoyu.myguokr.server.ImageServer;
import com.example.wangzhaoyu.myguokr.server.UserServer;
import com.example.wangzhaoyu.myguokr.server.handler.DefaultServerHandler;
import com.example.wangzhaoyu.myguokr.ui.fragment.ArticlesListFragment;
import com.example.wangzhaoyu.myguokr.ui.fragment.GroupHotPostFragment;
import com.example.wangzhaoyu.myguokr.ui.fragment.SettingsFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int START_LOGIN_ACTIVITY_CODE = 5518;

    @InjectView(R.id.fragment_container)
    FrameLayout mFrameLayout;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.main_drawer)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.nv_main_navigation)
    NavigationView mNaviView;
    @InjectView(R.id.nav_header_avatar)
    ImageView mNavAvatarImage;
    @InjectView(R.id.nav_header_name)
    TextView mNavUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //set up navigation view
        mNaviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.menu_scientific:
                        replaceFragment(new ArticlesListFragment());
                        break;
                    case R.id.menu_group:
                        replaceFragment(new GroupHotPostFragment());
                        break;
                    case R.id.menu_ask:
                        break;
                    case R.id.menu_feed:
                        break;
                    case R.id.menu_likes:
                        break;
                    case R.id.menu_news:
                        break;
                    case R.id.menu_view_mode:
                        break;
                    case R.id.menu_settings:
                        replaceFragment(new SettingsFragment());
                        break;
                    case R.id.menu_about:
                        Intent intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //set up toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("果壳");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        updateUserDisplay();

        setUpMainFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        startIntroAnimation();
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.nav_header)
    public void OnNavHeaderClicked(View view) {
        if (TextUtils.isEmpty(UserServer.getInstance().getAccessToken())) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, START_LOGIN_ACTIVITY_CODE);
        } else {
            Intent intent = new Intent(this, UserInfoActivity.class);
            intent.putExtra(UserInfoActivity.ARG_UKEY, UserServer.getInstance().getUserUkey());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_LOGIN_ACTIVITY_CODE && resultCode == RESULT_OK) {
            Log.i(TAG, "access token: " + UserServer.getInstance().getAccessToken());
            Log.i(TAG, "ukey: " + UserServer.getInstance().getUserUkey());
            updateUserDisplay();
        }
    }

    /**
     * 启动时toolbar动画
     */
    private void startIntroAnimation() {
        int barSize = mToolbar.getHeight();
        mToolbar.setTranslationY(-barSize);
        mToolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300)
                .start();
    }

    private void setUpMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(R.id.fragment_container, new ArticlesListFragment())
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void updateUserDisplay() {
        UserServer.getInstance().getUserInfo(
                UserServer.getInstance().getUserUkey(),
                new DefaultServerHandler<User>(this) {
                    @Override
                    public void onRequestSuccess(User user) {
                        //set up navigation drawer
                        ImageLoader.getInstance().displayImage(
                                user.getResult().getAvatar().getLarge(),
                                mNavAvatarImage,
                                ImageServer.getAvatarDisplayOptions(
                                        getResources().getDimensionPixelSize(R.dimen.nav_avatar_size)));
                        mNavUserName.setText(user.getResult().getNickname());
                    }

                    @Override
                    public void onRequestError() {
                        //set up navigation drawer
                        ImageLoader.getInstance().displayImage(
                                "http://images.17173.com/2011/wow/2011/02/24/nanshengqi17.jpg",
                                mNavAvatarImage,
                                ImageServer.getAvatarDisplayOptions(getResources().getDimensionPixelSize(R.dimen.nav_avatar_size)));
                    }
                });
    }
}
