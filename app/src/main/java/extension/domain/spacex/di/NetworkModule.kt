package extension.domain.spacex.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import extension.domain.spacex.BuildConfig
import extension.domain.spacex.data.network.Api
import extension.domain.spacex.data.network.DateDeserializer
import extension.domain.spacex.data.network.LaunchService
import extension.domain.spacex.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val networkModule = module {
    single { headerInterceptor() }
    single { loggingInterceptor() }
    single { dateDeserializer() }
    single { gsonBuilder(get()) }
    single { okhttpClient(get(), get()) }
    single { retrofit(get(), get()) }
    single { apiService(get()) }
    single { launchAppService(get()) }
}

fun launchAppService(
    api: Api
): LaunchService = LaunchService(api)

fun apiService(
    retrofit: Retrofit
): Api =
    retrofit.create(Api::class.java)

fun retrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

fun okhttpClient(
    headerInterceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

fun gsonBuilder(dateDeserializer: DateDeserializer): Gson =
    GsonBuilder().setDateFormat(Constants.DATE_FORMAT)
        .registerTypeAdapter(Date::class.java, dateDeserializer)
        .create()

fun dateDeserializer(): DateDeserializer =
    DateDeserializer()

fun headerInterceptor(): Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .build()

        val newRequest = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }

fun loggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
