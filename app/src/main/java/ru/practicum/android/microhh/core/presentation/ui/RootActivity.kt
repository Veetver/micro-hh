package ru.practicum.android.microhh.core.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = requireNotNull(_binding) {
        "ViewBinding cannot be null"
    }
    private var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        setupUI()
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }


    private fun setupUI() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.lastOrNull()

            when (destination.id) {
                else -> {}
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
