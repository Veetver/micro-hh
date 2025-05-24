package ru.practicum.android.microhh.core.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.practicum.android.microhh.BuildConfig
import ru.practicum.android.microhh.R
import ru.practicum.android.microhh.core.api.HhApiInstance
import ru.practicum.android.microhh.core.models.VacancyResponse

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        HhApiInstance.HHService.vacancies("", "Bearer $accessToken").enqueue(object : Callback<VacancyResponse> {
            override fun onResponse(
                call: Call<VacancyResponse>,
                response: Response<VacancyResponse>
            ) {
                if (response.isSuccessful) {
                    val vacancyResponse = response.body()
                    Log.d("API_RESPONSE", "Total Found: ${vacancyResponse?.found}")
                    Log.d("API_RESPONSE", "Current Page: ${vacancyResponse?.page}")
                    Log.d("API_RESPONSE", "Items Per Page: ${vacancyResponse?.perPage}")

                    vacancyResponse?.items?.forEach { vacancy ->
                        Log.d("API_RESPONSE", "Vacancy Name: ${vacancy.name}")
                        Log.d("API_RESPONSE", "Employer: ${vacancy.employer?.name}")
                    }
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<VacancyResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Request failed: ${t.message}")
            }
        })
    }

}
