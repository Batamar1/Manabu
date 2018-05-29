package me.manabu.modules

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import co.zsmb.materialdrawerkt.builders.*
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import co.zsmb.materialdrawerkt.imageloader.drawerImageLoader
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.squareup.picasso.Picasso
import jp.wasabeef.fresco.processors.internal.FastBlur
import me.manabu.R
import me.manabu.activities.MainActivity
import me.manabu.activities.fragments.DecksFragment
import me.manabu.activities.fragments.MainFragment
import me.manabu.activities.fragments.SettingsFragment
import org.jetbrains.anko.toast

object NavigationDrawer {

    private const val headerBlur = 5

    private lateinit var accountHeader: AccountHeader
    private var headerDrawable: Drawable? = null

    private lateinit var mainPage: PrimaryDrawerItem
    lateinit var decksPage: PrimaryDrawerItem
    private lateinit var settings: PrimaryDrawerItem
    private lateinit var logout: PrimaryDrawerItem
    private lateinit var userProfile: ProfileDrawerItem


    fun build(context: MainActivity): Drawer {
        if (!CurrentUser.isSignedIn()) {
            throw ExceptionInInitializerError("Account still not initialized \nWhy are you actually trying to build a drawer, when we're still not signed in?")
        }

        initImageLoader()

        return context.drawer {
            toolbar = context.toolbar

            closeOnClick = true
            headerPadding = true
            stickyFooterShadow = false
            stickyFooterDivider = true

            accountHeader = accountHeader {
                profileImagesClickable = false
                selectionListEnabled = false
                backgroundDrawable = getBg(context)

                userProfile = profile(CurrentUser.getAccount().displayName!!, CurrentUser.getAccount().email!!) {
                    iconUrl = CurrentUser.getAccount().photoUrl.toString()
                }
            }


            //Elements
            mainPage = primaryItem(R.string.drawer_item_home) {
                iicon = FontAwesome.Icon.faw_home
                onClick { _ ->
                    context.changeFragment(MainFragment())
                    false
                }
            }
            decksPage = primaryItem(R.string.drawer_item_decks) {
                iicon = FontAwesome.Icon.faw_list_alt
                onClick { _ ->
                    context.changeFragment(DecksFragment())
                    false
                }
            }
            footer {
                settings = primaryItem(R.string.drawer_item_settings) {
                    iicon = FontAwesome.Icon.faw_cog
                    onClick { _ ->
                        context.changeFragment(SettingsFragment())
                        false
                    }
                }
                logout = primaryItem(R.string.drawer_item_logout) {
                    iicon = FontAwesome.Icon.faw_sign_out_alt
                    onClick { _ ->
                        CurrentUser.signOut(context)
                        false
                    }
                }

            }
        }
    }

    private fun getBg(activity: Activity): Drawable {
        if (headerDrawable != null)
            return headerDrawable as Drawable

        return activity.getDrawable(R.drawable.header)
    }

    private fun initImageLoader() {
        drawerImageLoader {
            placeholder { ctx, _ ->
                DrawerUIUtils.getPlaceHolder(ctx)
            }
            set { imageView, uri, placeholder, _ ->
                Picasso.with(imageView.context)
                        .load(uri)
                        .placeholder(placeholder)
                        .into(imageView)
            }
            cancel { imageView ->
                Picasso.with(imageView.context)
                        .cancelRequest(imageView)
            }
        }
    }

    fun loadUserBackground(activity: Activity) {
        Picasso.with(activity).load(CurrentUser.getAccount().photoUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                //Once we getAccount image - update header and variable
                headerDrawable = BitmapDrawable(activity.resources, FastBlur.blur(bitmap, headerBlur, false))
                accountHeader.setBackground(headerDrawable)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(errorDrawable: Drawable?) {
                activity.toast("Navigation Drawer: Can't load user background")
            }
        })
    }
}