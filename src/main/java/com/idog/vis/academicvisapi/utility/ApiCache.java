/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.idog.vis.academicvisapi.resources.ApiResourceRequest;
import com.idog.vis.academicvisapi.resources.ApiResourceResponse;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author idoga
 */
public class ApiCache {

    Cache<ApiResourceRequest, ApiResourceResponse> apiResponses;

    public ApiCache() {
        apiResponses
                = CacheBuilder.newBuilder()
                        .maximumSize(10)
                        //.weakKeys()
                        .expireAfterAccess(120, TimeUnit.SECONDS)
                        .build();
    }

    public Cache<ApiResourceRequest, ApiResourceResponse> getApiResponses() {
        return apiResponses;
    }

    public ApiResourceResponse getApiResponse(ApiResourceRequest key) {
        return apiResponses.getIfPresent(key);
    }

    public void putApiResponse(ApiResourceRequest key, ApiResourceResponse value) {
        apiResponses.put(key, value);
    }
}
