package projects.karlosp3rez.androidpokedex.retrofit;

import projects.karlosp3rez.androidpokedex.utils.Common;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(Common.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
}
