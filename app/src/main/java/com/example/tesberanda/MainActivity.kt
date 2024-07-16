package com.example.tesberanda

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tesberanda.databinding.ActivityMainBinding
import com.example.tesberanda.fragment.BerandaFragment
import com.example.tesberanda.fragment.GrafikFragment
import com.example.tesberanda.fragment.InfoFragment
import com.example.tesberanda.fragment.TableFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // Menyembunyikan status bar
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true // or false for dark icons


        bind.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    openFragment(BerandaFragment())
                    true
                }
                R.id.navigation_tabel-> {
                    openFragment(TableFragment())
                    true
                }
                R.id.navigation_grafik -> {
                    openFragment(GrafikFragment())
                    true
                }

                R.id.navigation_info -> {
                    openFragment(InfoFragment())
                    true
                }
                else -> false
            }
        }


        if (savedInstanceState == null) {
            bind.bottomNav.selectedItemId = R.id.navigation_home
        }
    }
    public fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}