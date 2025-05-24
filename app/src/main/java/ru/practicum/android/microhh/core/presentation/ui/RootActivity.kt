package ru.practicum.android.microhh.core.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        //setupUI()
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }


    private fun setupUI() {
        //navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentFragment = navHostFragment.childFragmentManager.fragments.lastOrNull()

            when (destination.id) {
                else -> {}
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
