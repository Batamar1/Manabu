package me.manabu.utils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.fresco.processors.internal.FastBlur;
import me.manabu.R;
import me.manabu.activities.DecksActivity;
import me.manabu.activities.MainActivity;

public class NavigationDrawer {

    private Activity activity;
    private Toolbar toolbar;

    private Drawer drawer;

    private AccountHeader accountHeader;
    private Drawable headerDrawable;
    private int BLUR = 5;

    private PrimaryDrawerItem main_page;
    private PrimaryDrawerItem decks;
    private PrimaryDrawerItem logout;
    private ProfileDrawerItem profileDrawerItem;
    private static final DividerDrawerItem divider = new DividerDrawerItem();

    private static final int PROFILE_ID = 0;

    public NavigationDrawer(Activity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;

        initImageLoader();
        initItems();
        loadUserPictures(activity);

        drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        main_page,
                        decks
                )
                .withStickyFooterShadow(false)
                .withStickyFooterDivider(true)
                .addStickyDrawerItems(
                        logout
                ).build();

        setNowItem(); //TODO: FIX!!!
    }

    public Drawer getDrawer() {
        return drawer;
    }

    private void initImageLoader() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }

    private void initItems() {
        headerDrawable = activity.getDrawable(R.drawable.header);

        main_page = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_home)
                .withName(R.string.drawer_item_home)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    return true;
                });

        decks = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_list_alt)
                .withName(R.string.drawer_item_decks)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    Intent intent = new Intent(activity, DecksActivity.class);
                    activity.startActivity(intent);
                    return true;
                });

        logout = new PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    AuthUtils.signOut(activity);
                    return true;
                });

        profileDrawerItem = new ProfileDrawerItem()
                .withName(AuthUtils.getAccount().getDisplayName())
                .withEmail(AuthUtils.getAccount().getEmail())
                .withIcon(AuthUtils.getAccount().getPhotoUrl());

        accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(headerDrawable)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .addProfiles(profileDrawerItem)
                .build();
    }

    private void loadUserPictures(Activity activity) {
        Picasso.with(activity).load(AuthUtils.getAccount().getPhotoUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                //Once we get image - update header and variable
                headerDrawable = new BitmapDrawable(activity.getResources(), FastBlur.blur(bitmap, BLUR, false));
                accountHeader.setBackground(headerDrawable);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("Navigation Drawer", "Background loading failed!");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    private void setNowItem() {
        if (activity.getClass() == MainActivity.class) {
            setSelection(main_page);
        }
        if (activity.getClass() == DecksActivity.class) {
            setSelection(decks);
        }
    }

    private void setSelection(PrimaryDrawerItem iDrawerItem){
        iDrawerItem.withSetSelected(false).withEnabled(false);
        drawer.setSelection(iDrawerItem, false);
    }
}