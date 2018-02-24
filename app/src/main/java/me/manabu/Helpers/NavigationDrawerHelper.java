package me.manabu.Helpers;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import me.manabu.Controllers.LoginController;
import me.manabu.LoginActivity;
import me.manabu.R;

public class NavigationDrawerHelper {

    public static void init(Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Exit");

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2
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
}