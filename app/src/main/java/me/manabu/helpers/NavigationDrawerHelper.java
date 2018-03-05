package me.manabu.helpers;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

import me.manabu.activities.LoginActivity;
import me.manabu.R;
import me.manabu.activities.MainActivity;

public class NavigationDrawerHelper {

    private static Drawable headerDrawable;

    private static PrimaryDrawerItem main_page;
    private static SecondaryDrawerItem logout;
    private static ProfileDrawerItem profileDrawerItem;

    public static void init(Activity activity) {

        main_page = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_euro_sign)
                .withName(R.string.drawer_item_home);
        logout = new SecondaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    AuthHelper.signOut(activity);
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    return true;
                });
        profileDrawerItem = new ProfileDrawerItem()
                .withName(AuthHelper.getAccount().getDisplayName())
                .withEmail(AuthHelper.getAccount().getEmail());
    }

    public static void initAccount(GoogleSignInAccount account) {
        //profileDrawerItem.withName(account.getDisplayName()).withEmail(account.getEmail());
    }

    public static void draw(Activity activity, Toolbar toolbar) {
        new DrawerBuilder()
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

    private static AccountHeader configuteAccountHeader(Activity activity) {
        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .addProfiles(profileDrawerItem)
                .build();
    }
}