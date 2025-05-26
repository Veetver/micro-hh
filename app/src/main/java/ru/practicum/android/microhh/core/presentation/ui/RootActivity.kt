package ru.practicum.android.microhh.core.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.api.HhApiInstance
import ru.practicum.android.microhh.core.models.VacancyResponse
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
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        setupUI()

        if (NetworkUtils.isNetworkAvailable(this)) {
            networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        }
    }

    private fun networkRequestExample(accessToken: String) {
        HhApiInstance.HHService.vacancies("", "Bearer $accessToken").enqueue(object : Callback<VacancyResponse> {
            override fun onResponse(
                call: Call<VacancyResponse>,
                response: Response<VacancyResponse>
            ) {
                if (response.isSuccessful) {
                    val vacancyResponse = response.body()
                    Log.d(Constants.API_RESPONSE_TAG, "Total Found: ${vacancyResponse?.found}")
                    Log.d(Constants.API_RESPONSE_TAG, "Current Page: ${vacancyResponse?.page}")
                    Log.d(Constants.API_RESPONSE_TAG, "Items Per Page: ${vacancyResponse?.perPage}")

                    vacancyResponse?.items?.forEach { vacancy ->
                        Log.d(Constants.API_RESPONSE_TAG, "Vacancy Name: ${vacancy.name}")
                        Log.d(Constants.API_RESPONSE_TAG, "Employer: ${vacancy.employer?.name}")
                    }
                } else {
                    Log.e(Constants.API_RESPONSE_TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<VacancyResponse>, t: Throwable) {
                Log.e(Constants.API_RESPONSE_TAG, "Request failed: ${t.message}")
            }
        })
    }

    object Constants {
        const val API_RESPONSE_TAG = "API_RESPONSE"
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
