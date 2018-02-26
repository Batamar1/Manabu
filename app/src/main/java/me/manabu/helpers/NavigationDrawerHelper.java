package me.manabu.helpers;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import me.manabu.controllers.LoginController;
import me.manabu.activities.LoginActivity;
import me.manabu.R;

public class NavigationDrawerHelper {

    private static Drawable headerDrawable;

    private static PrimaryDrawerItem main_page;
    private static SecondaryDrawerItem logout;

    public static void init(Activity activity, Toolbar toolbar) {

        main_page = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_euro_sign)
                .withName(R.string.drawer_item_home);
        logout = new SecondaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        LoginController.logOut(activity);
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                        return true;
                    }
                });

        //create the drawer and remember the `Drawer` result object
        Drawer drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(configuteAccountHeader(activity))
                .addDrawerItems(
                        main_page,
                        new DividerDrawerItem(),
                        logout
                )
                .build();
    }

    private static AccountHeader configuteAccountHeader(Activity activity){

        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com")
                                .withIcon(headerDrawable)
                                //.withIcon(Uri.parse("https://pp.userapi.com/c831508/v831508482/251cb/fY9HxFYErg4.jpg"))
                )
                .build();
    }
}