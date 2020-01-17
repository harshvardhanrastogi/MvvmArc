package com.kisan.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MessageService {
    @FormUrlEncoded
    @POST("AC75820374a20a718b3957c2d9ebaa9e22/Messages.json")
    Call<SmsResponse> sendSms(@Field("From") String from, @Field("To") String to, @Field("Body") String body);
}
