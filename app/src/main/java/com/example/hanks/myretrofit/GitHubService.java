package com.example.hanks.myretrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yvtc on 2016/11/18.
 */
public interface GitHubService {
//    @GET("users/{user}/repos")
    //@GET("PHP_Project_forXAMPP/11-14_projectForAll/{user}/readData.php")
    @GET("api/readData.php")
    Call<List<Repo>> listRepos();
    //Call<List<Repo>> listRepos(@Path("user") String user);

    //Delete data
    @GET("api/api_delete.php")
    Call<ResponseBody> delete(@Query("cID") String cID);

    //    @POST("api/api_add_post.php")
//    Call<ResponseBody> add(@Body Repo repo);
//
//    @POST("api/api_add_post.php")
//    Call<ResponseBody> addByPlainText(@Body RequestBody body);

    //Add data
    @FormUrlEncoded
    @POST("api/api_add_post.php")
    Call<ResponseBody> addByFormPost(@Field("cName") String cName,
                                     @Field("cSex") String cSex,
                                     @Field("cBirthday") String cBirthday,
                                     @Field("cEmail") String cEmail,
                                     @Field("cPhone") String cPhone,
                                     @Field("cAddr") String cAddr);

    //Update data
    @FormUrlEncoded
    @POST("api/api_update_post.php")
    Call<ResponseBody> update(@Field("cName") String cName,
                                     @Field("cSex") String cSex,
                                     @Field("cBirthday") String cBirthday,
                                     @Field("cEmail") String cEmail,
                                     @Field("cPhone") String cPhone,
                                     @Field("cAddr") String cAddr);
}
