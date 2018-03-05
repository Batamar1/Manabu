package me.manabu.helpers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
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
import com.mikepenz.materialdrawer.model.interfaces.OnPostBindViewListener;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.fresco.processors.internal.FastBlur;
import me.manabu.R;

public class NavigationDrawerHelper {

    private static Drawable headerDrawable;
    private static int BLUR = 5;

    private static PrimaryDrawerItem main_page;
    private static PrimaryDrawerItem decks;
    private static SecondaryDrawerItem logout;
    private static ProfileDrawerItem profileDrawerItem;
    private static DividerDrawerItem divider;

    public static void init(Activity activity) {

        divider = new DividerDrawerItem();

        main_page = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_home)
                .withName(R.string.drawer_item_home);
        decks = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_list_alt)
                .withName(R.string.drawer_item_decks);
        logout = new SecondaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    AuthHelper.signOut(activity);
                    return true;
                });
        profileDrawerItem = new ProfileDrawerItem()
                .withName(AuthHelper.getAccount().getDisplayName())
                .withEmail(AuthHelper.getAccount().getEmail())
                .withIcon(AuthHelper.getAccount().getPhotoUrl());

        initImageLoader();
        loadHeadDrawable(activity);
    }

    public static Drawer getDrawer(Activity activity, Toolbar toolbar) {
        return new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(configureAccountHeader(activity))
                .addDrawerItems(
                        main_page,
                        decks
                )
                .withStickyFooterShadow(true)
                .withFooterDivider(true)
                .addStickyDrawerItems(
                        logout
                )
                .build();
    }

    private static AccountHeader configureAccountHeader(Activity activity) {
        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(headerDrawable)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .addProfiles(profileDrawerItem)
                .build();
    }

    private static void initImageLoader() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                tag = "User Avatar";
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }

    private static void loadHeadDrawable(Activity activity) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                headerDrawable = new BitmapDrawable(activity.getResources(), FastBlur.blur(bitmap, BLUR, false));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                headerDrawable = activity.getDrawable(R.drawable.header);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                headerDrawable = activity.getDrawable(R.drawable.header);
            }
        };

        Picasso.with(activity).load(AuthHelper.getAccount().getPhotoUrl()).into(target);
    }
}