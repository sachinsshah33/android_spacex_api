package extension.domain.spacex.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import extension.domain.spacex.data.network.LaunchService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import extension.domain.spacex.BuildConfig
import extension.domain.spacex.data.network.Api
import extension.domain.spacex.data.network.DateDeserializer
import extension.domain.spacex.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*



@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url.newBuilder()
                .build()

            val newRequest = request.newBuilder()
                .addHeader("Content-Type", Constants.APPLICATION_JSON)
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }


    @Singleton
    @Provides
    fun provideDateDeserializer(): DateDeserializer = DateDeserializer()


    @Singleton
    @Provides
    fun provideGsonBuilder(dateDeserializer: DateDeserializer): Gson =
        GsonBuilder().setDateFormat(Constants.DATE_TIME_FORMAT)
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .create()


    @Singleton
    @Provides
    fun provideOkhttpClient(
        headerInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()



    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()



    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)



    @Singleton
    @Provides
    fun provideLaunchAppService(api: Api): LaunchService = LaunchService(api)

}