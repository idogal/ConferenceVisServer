/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

/**
 *
 * @author idoga
 */
public interface VisPersistence {
    //public void getChasePaper(ApiResourceRequest request);
    public String getMsApiResponse(String conference, String year, int count);
    public void storeMsApiResponse(String conference, String year, int count, String response);
    
    public String getMsApiResponse(String paperId);
    public void storeMsApiResponse(String paperId, String response);
}
