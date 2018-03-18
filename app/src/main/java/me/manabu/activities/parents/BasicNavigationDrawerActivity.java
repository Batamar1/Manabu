package me.manabu.activities.parents;

import com.mikepenz.materialdrawer.Drawer;

import me.manabu.utils.NavigationDrawer;

abstract public class BasicNavigationDrawerActivity extends BasicToolbarActivity {

    protected Drawer drawer;

    protected void initDrawer() {
        drawer = new NavigationDrawer(this, toolbar).getDrawer();
    }

    public Drawer getDrawer() {
        return drawer;
    }
}