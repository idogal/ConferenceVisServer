/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author idoga
 */
public class AcademicApiPaper {
    @JsonProperty("Id")
    public int id;
    
    @JsonProperty("Ti")
    public String title;   
    
    @JsonProperty("E")
    public String extendedProperties;
}
