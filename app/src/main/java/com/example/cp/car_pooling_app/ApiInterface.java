package com.example.cp.car_pooling_app;


import com.example.cp.car_pooling_app.Data.RespMsg;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiInterface {

     String Url = "http://demoproject.in/carpooling_service/Service1.svc/";

    //String Url="http://192.168.0.104/car_pooling/Service1.svc/";

    @GET("giveRating/{ride_id}/{user_id}/{driver_id}/{rating}")
    Call<RespMsg> giveRating(@Path("ride_id") String ride_id,@Path("user_id") String user_id,
                             @Path("driver_id") String driver_id,@Path("rating") String rating);

}
