package me.manabu.Helpers;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import me.manabu.Controllers.LoginController;
import me.manabu.LoginActivity;
import me.manabu.R;

public class NavigationDrawerHelper {

    private static PrimaryDrawerItem main_page;
    private static SecondaryDrawerItem logout;

    public static void init(Activity activity, Toolbar toolbar) {

        main_page = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_euro_sign)
                .withName(R.string.drawer_item_home);
        logout = new SecondaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout);

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
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    Log.d("click", String.valueOf(position));
                    switch (position){
                        case 2:
                            LoginController.logOut(activity);
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                    }

                    return false;
                })
                .build();
    }

    private static AccountHeader configuteAccountHeader(Activity activity){
        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com")
                )
                .build();
    }
}