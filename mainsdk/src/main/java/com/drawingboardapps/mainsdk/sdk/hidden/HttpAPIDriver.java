package com.drawingboardapps.mainsdk.sdk.hidden;


import com.drawingboardapps.mainsdk.sdk.external.models.DataTransfer;
import com.drawingboardapps.mainsdk.sdk.external.models.LEDData;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.GET_API;
import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.Status.ERROR;
import static com.drawingboardapps.mainsdk.sdk.hidden.Constants.EventBus.Status.OK;

/**
 * Created by Zach on 4/10/2017.
 */

public class HttpAPIDriver {

    private static final String TAG = HttpAPIDriver.class.getSimpleName();
    private final EventBus eventBusInstance;
    private Retrofit retrofit;

    public HttpAPIDriver(EventBus eventBusInstance) {
        this.eventBusInstance = eventBusInstance;
        init();
    }

    private void init() {
        Interceptor interceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                HttpUrl url = request.url()
                        .newBuilder()
                        .build();

                request = request
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .url(url)
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(Constants.Http.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.Http.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Http.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();

    }

    public void makeRequest(String query) {
        HttpApi api = retrofit.create(HttpApi.class);
        Call<ResponseBody> call = api.makeCall(query);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    parseCallResponse(response);
                } catch (IOException e) {
                    EventBus.getDefault().post(new DataTransfer(null, ERROR, "IOException"));
                    if (Constants.DEBUG) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    EventBus.getDefault().post(new DataTransfer(null, ERROR, "JSONException"));
                    if (Constants.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                EventBus.getDefault().post(new DataTransfer(null, ERROR, "HTTP Call Failed"));
            }

            private void parseCallResponse(Response<ResponseBody> response) throws IOException, JSONException {
                String str = response.body().string();

                JSONObject jobject = new JSONObject(str);
                JSONArray array = jobject.getJSONObject("meta").getJSONObject("view").getJSONArray("columns");
                LEDData data = new LEDData();
                for (int i = 0; i < array.length(); i++) {
                    jobject = array.getJSONObject(i);
                    String fieldName = jobject.getString("fieldName");

                    if (fieldName.equals("total_units")) {
                        jobject.getJSONObject("cachedContents").getString("sum");
                        continue;
                    }
                    if (fieldName.equals("energy_savings")) {
                        jobject.getJSONObject("cachedContents").getString("average");
                        continue;
                    }
                    if (fieldName.equals("annual_energy_savings")) {
                        data.setAvgSavingsDollars(jobject.getJSONObject("cachedContents").getString("average"));
                        data.setTotalSavingsDollars(jobject.getJSONObject("cachedContents").getString("sum"));
                    }
                }
                EventBus.getDefault().post(new DataTransfer(data, OK, GET_API));
            }
        });
    }

}
