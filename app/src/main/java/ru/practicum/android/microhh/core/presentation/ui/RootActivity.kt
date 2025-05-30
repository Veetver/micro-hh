package ru.practicum.android.microhh.core.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.api.HhApiInstance
import ru.practicum.android.microhh.core.models.VacancyResponse
import ru.practicum.android.microhh.core.utils.AppLog
import ru.practicum.android.microhh.core.utils.NetworkUtils
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

        setupUI()
        networkRequestExample()

        if (NetworkUtils.isNetworkAvailable(this)) {
            networkRequestExample()
        }
    }

    private fun networkRequestExample() {
        HhApiInstance.HHService.vacancies(
            "",
            "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
            ).enqueue(object : Callback<VacancyResponse> {

            override fun onResponse(
                call: Call<VacancyResponse>,
                response: Response<VacancyResponse>
            ) {
                if (response.isSuccessful) {
                    val vacancyResponse = response.body()

                    AppLog.d(AppLog.RETROFIT_API_RESPONSE, "Total Found: ${vacancyResponse?.found}")
                    AppLog.d(AppLog.RETROFIT_API_RESPONSE, "Current Page: ${vacancyResponse?.page}")
                    AppLog.d(AppLog.RETROFIT_API_RESPONSE, "Items Per Page: ${vacancyResponse?.perPage}")

                    vacancyResponse?.items?.forEach { vacancy ->
                        AppLog.d(AppLog.RETROFIT_API_RESPONSE, "Vacancy Name: ${vacancy.name}")
                        AppLog.d(AppLog.RETROFIT_API_RESPONSE, "Employer: ${vacancy.employer.name}")
                    }
                } else {
                    AppLog.e(AppLog.RETROFIT_API_RESPONSE, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<VacancyResponse>, t: Throwable) {
                AppLog.e(AppLog.RETROFIT_API_RESPONSE, "Request failed: ${t.message}")
            }
        })
    }

    private fun setupUI() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        navController?.let {
            binding.bottomNav.setupWithNavController(it)
            it.addOnDestinationChangedListener { _, destination, _ ->
                // val currentFragment = navHostFragment?.childFragmentManager?.fragments?.lastOrNull()

                when (destination.id) {
                    else -> {}
                }
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
