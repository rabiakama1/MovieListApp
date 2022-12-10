package com.example.mobilliumcase.api

import com.example.mobilliumcase.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null

    /**Retrofit nesnesi object tipinde bir class altında tanımlanır.
     * Constant sınıfı altındaki baseUrl değişkeni parametre olarak retrofit’e tanımlıyoruz
     * Converter için GSON converter kullanmak istediğimizi belirtiyoruz.
     */
    fun getClient(): Retrofit {
        if (retrofit == null)
            retrofit =
                Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        return retrofit as Retrofit
    }
}