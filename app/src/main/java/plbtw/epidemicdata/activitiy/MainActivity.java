package plbtw.epidemicdata.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ViewFlipper;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.fragment.ExploreFragment;
import plbtw.epidemicdata.R;
import plbtw.epidemicdata.fragment.ProfileFragment;
import plbtw.epidemicdata.utils.FunctionUtil;


/**rftg
 * Created by DedeEko on 5/18/2016.
 */
public class MainActivity extends BaseActivity {

    public static int REQUEST_CODE_PILIH_LOKASI = 1;
    private static int RESULT_CODE_PILIH_LOKASI = 1;

    private int activeMenu;

    //=START - menu definitions
    public static final int MENU_EXPLORE = 0;
    public static final int MENU_PROFILE = 1;


    private ViewFlipper mainFlipper;
    private Drawer result = null;
    public BottomBar mBottomBar;

    public ExploreFragment exploreFragment;
    public ProfileFragment profileFragment;

    private Bundle savedInstanceState;

    public static View activityRoot;
    private Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private final String name = "loginUser";
    private static final int mode = Activity.MODE_PRIVATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLeftIcon(0);

        setNavigationBar();

        setBottomBar();
        activeMenu = MENU_EXPLORE;

        sharedPreferences = getSharedPreferences(name, mode);


    }

    @Override
    protected void onStart() {
        super.onStart();

        openMenu(activeMenu, true);
    }

    @Override
    public void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mainFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        activityRoot = findViewById(android.R.id.content);


        exploreFragment = new ExploreFragment();
        profileFragment = new ProfileFragment();


        replaceFragment(R.id.explore_container, exploreFragment, false);
        replaceFragment(R.id.profile_container, profileFragment, false);


    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {

            }

            @Override
            public void onRightIconClick() {
                Intent i = new Intent(MainActivity.this, TabMenuActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void updateUI() {
        setNavigationBar();
        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.


    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void setNavigationBar()
    {
        if(result!=null)
            result.removeAllItems();

        result = new DrawerBuilder()
                .withActivity(this)
                .withSliderBackgroundColor(getResources().getColor(R.color.actionbar_color))
                .withTranslucentStatusBar(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Profile").withIcon(FontAwesome.Icon.faw_user).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_gift).withIdentifier(3),
                        new SectionDrawerItem().withName("Others"),
                        new SecondaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(8)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                openMenu(MENU_EXPLORE);

                            }else if (drawerItem.getIdentifier() == 2) {

                                openMenu(MENU_PROFILE);
                            }
                            else if (drawerItem.getIdentifier() == 3) {

                                Intent i = new Intent(MainActivity.this, TabMenuActivity.class);
                                startActivity(i);
                            }
                            else if (drawerItem.getIdentifier() == 8) {

                                logout();
                            }
                            if (intent != null) {

                            }
                        }
                        return false;
                    }
                })
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void logout()
    {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email","");
        editor.putString("username","");
        editor.putString("id_user","");
        editor.putString("nama","");
        editor.putString("password","");
        editor.putString("poin","");
        editor.putString("role","");
        editor.putString("telp","");
        editor.apply();
        finish();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }


    public void setBottomBar(){
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.useFixedMode();
        mBottomBar.setDefaultTabPosition(0);


        mBottomBar.setItemsFromMenu(R.menu.menu_bottombar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user selected item number one.
                    openMenu(MENU_EXPLORE);
                } else if (menuItemId == R.id.bottomBarItemTwo) {
                    Intent i = new Intent(MainActivity.this, PostEdActivity.class);
                    startActivity(i);


//                    mBottomBar.selectTabAtPosition(0, true);

                } else if (menuItemId == R.id.bottomBarItemThree) {
                    openMenu(MENU_PROFILE);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomBar.selectTabAtPosition(0, false);
    }

    public void openMenu(int target) {
        openMenu(target, false);
    }

    public void openMenu(int target, boolean needToForce) {
        if(activeMenu == target && !needToForce)    // don't process if we're already on this menu
            return;

        // clear back button stack
//        deleteBackStack();

        // hide soft keyboard
        FunctionUtil.hideSoftKeyboard(this);

        // holds indicators
        boolean isExploreActive = target == MENU_EXPLORE;
        boolean isProfileActive = target == MENU_PROFILE;

        // run target fragment
        if(isExploreActive)
            exploreFragment.run();
        else if(isProfileActive)
            profileFragment.run();

        // flag the activated menu
        activeMenu = target;

        // flip the view
        mainFlipper.setDisplayedChild(activeMenu);
    }
}
