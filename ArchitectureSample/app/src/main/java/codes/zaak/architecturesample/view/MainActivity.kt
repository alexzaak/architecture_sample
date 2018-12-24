package codes.zaak.architecturesample.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import codes.zaak.architecturesample.R
import codes.zaak.architecturesample.view.saga.SagaFragment
import codes.zaak.architecturesample.view.webview.WebviewFragment
import com.google.android.material.navigation.NavigationView
import dagger.android.AndroidInjection


class MainActivity : BaseActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId) {
                R.id.nav_why -> this.makeToast(menuItem.title.toString())
                R.id.nav_saga -> addFragment(SagaFragment(), SagaFragment.TAG)
                R.id.nav_add_character -> this.makeToast(menuItem.title.toString())
                R.id.nav_more -> this.addFragment(WebviewFragment(), WebviewFragment.TAG)
            }
            true
        }

        this.drawerLayout.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    // Respond when the drawer's position changes
                }

                override fun onDrawerOpened(drawerView: View) {
                    // Respond when the drawer is opened
                }

                override fun onDrawerClosed(drawerView: View) {
                    // Respond when the drawer is closed
                }

                override fun onDrawerStateChanged(newState: Int) {
                    // Respond when the drawer motion state changes
                }
            }
        )

        if (savedInstanceState == null) {
            addFragment(SagaFragment(), SagaFragment.TAG)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        var fragmentTemp = supportFragmentManager.findFragmentByTag(tag)

        if (fragmentTemp == null) {
            fragmentTemp = fragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentTemp, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun makeToast(msg: String) {
        Toast.makeText(this, "$msg selected", Toast.LENGTH_LONG).show()
    }
}
