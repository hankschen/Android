package com.example.hanks.myretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yvtc on 2016/11/18.
 */
public interface GitHubService {
//    @GET("users/{user}/repos")
    @GET("PHP_Project_forXAMPP/11-14_projectForAll/{user}/readData.php")
    Call<List<Repo>> listRepos(@Path("user") String user);
//    Call<List<Repo>> listRepos(@Query("user") String user);
}
