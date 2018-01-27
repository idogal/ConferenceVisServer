/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import com.idog.vis.academicvisapi.resources.ApiResourceRequest;
import com.idog.vis.academicvisapi.resources.ApiResourceResponse;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author idoga
 */
public class ApiCache {

    private Cache<ApiResourceRequest, ApiResourceResponse> apiChasePapersResponses;
    private Cache<String, AcademicApiResponse> apiByIdResponses;

    public ApiCache() {
        apiChasePapersResponses
                = CacheBuilder.newBuilder()
                        .maximumSize(10)
                        //.weakKeys()
                        .expireAfterAccess(60, TimeUnit.MINUTES)
                        .build();
        
        apiByIdResponses
                = CacheBuilder.newBuilder()
                        .maximumSize(1000)
                        //.weakKeys()
                        .expireAfterAccess(60, TimeUnit.MINUTES)
                        .build();        
    }

    public Cache<ApiResourceRequest, ApiResourceResponse> getChasePapersResponses() {
        return apiChasePapersResponses;
    }

    public Cache<String, AcademicApiResponse> getByIdResponses() {
        return apiByIdResponses;
    }

    public ApiResourceResponse getChasePapersResponse(ApiResourceRequest key) {
        return apiChasePapersResponses.getIfPresent(key);
    }
    
    public AcademicApiResponse getByIdResponse(String id) {
        return apiByIdResponses.getIfPresent(id);
    }    

    public void putChasePapersResponse(ApiResourceRequest key, ApiResourceResponse value) {
        apiChasePapersResponses.put(key, value);
    }
    
    public void putByIdResponse(String id, AcademicApiResponse value) {
        apiByIdResponses.put(id, value);
    }    
}
