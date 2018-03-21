package me.manabu.modules


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView

import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

import jp.wasabeef.fresco.processors.internal.FastBlur
import me.manabu.R
import me.manabu.activities.DecksActivity
import me.manabu.activities.MainActivity

class NavigationDrawer(private val activity: Activity, private val toolbar: Toolbar) {

    lateinit var drawer: Drawer
        private set

    private lateinit var accountHeader: AccountHeader
    private lateinit var headerDrawable: Drawable
    private val headerBlur = 5

    private lateinit var mainPage: PrimaryDrawerItem
    private lateinit var decks: PrimaryDrawerItem
    private lateinit var settings: PrimaryDrawerItem
    private lateinit var logout: PrimaryDrawerItem
    private lateinit var profileDrawerItem: ProfileDrawerItem
    //private val divider = DividerDrawerItem()

    init {
        initImageLoader()
        initItems()
        initAndBuildDrawer()
        loadUserPictures(activity)
        setNowItem()
    }

    private fun initAndBuildDrawer() {
        drawer = DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        mainPage,
                        decks
                )
                .withCloseOnClick(true)
                .withHeaderPadding(true)
                .withStickyFooterShadow(false)
                .withStickyFooterDivider(true)
                .addStickyDrawerItems(
                        settings,
                        logout
                ).build()
    }

    private fun initImageLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                Picasso.with(imageView.context).load(uri).placeholder(placeholder).into(imageView)
            }

            override fun cancel(imageView: ImageView?) {
                Picasso.with(imageView!!.context).cancelRequest(imageView)
            }
        })
    }

    private fun initItems() {
        headerDrawable = activity.getDrawable(R.drawable.header)

        mainPage = PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_home)
                .withName(R.string.drawer_item_home)
                .withOnDrawerItemClickListener(getClassClicker(MainActivity::class.java))

        decks = PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_list_alt)
                .withName(R.string.drawer_item_decks)
                .withOnDrawerItemClickListener(getClassClicker(DecksActivity::class.java))

        settings = PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_cog)
                .withName(R.string.drawer_item_settings)

        logout = PrimaryDrawerItem()
                .withIcon(FontAwesome.Icon.faw_sign_out_alt)
                .withName(R.string.drawer_item_logout)
                .withOnDrawerItemClickListener { _, _, _ ->
                    Authentication.signOut(activity)
                    true
                }

        profileDrawerItem = ProfileDrawerItem()
                .withName(Authentication.account!!.displayName)
                .withEmail(Authentication.account!!.email)
                .withIcon(Authentication.account!!.photoUrl)

        accountHeader = AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(headerDrawable)
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .addProfiles(profileDrawerItem)
                .build()
    }

    private fun loadUserPictures(activity: Activity) {
        Picasso.with(activity).load(Authentication.account!!.photoUrl).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                //Once we get image - update header and variable
                headerDrawable = BitmapDrawable(activity.resources, FastBlur.blur(bitmap, headerBlur, false))
                accountHeader.setBackground(headerDrawable)
            }

            override fun onBitmapFailed(errorDrawable: Drawable) {
                Log.d("Navigation Drawer", "Background loading failed!")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
    }

    private fun setNowItem() {
        if (activity.javaClass == MainActivity::class.java) {
            setCurrentItem(mainPage)
        }
        if (activity.javaClass == DecksActivity::class.java) {
            setCurrentItem(decks)
        }
    }

    private fun setCurrentItem(iDrawerItem: PrimaryDrawerItem?) {
        iDrawerItem!!.withSetSelected(false)
                .withOnDrawerItemClickListener(null)
        drawer!!.setSelection(iDrawerItem, false)
    }

    private fun getClassClicker(className: Class<*>): Drawer.OnDrawerItemClickListener {
        return Drawer.OnDrawerItemClickListener { _, _, _ ->
            val intent = Intent(activity, className)
            activity.startActivity(intent)
            true
        }
    }
}