/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.util.List;

/**
 *
 * @author idoga
 */
public class ApiResourceRequest {

    private String conferenceName = "";
    private String year = "";
    private int count = 0;

    public ApiResourceRequest() {
    }

    public ApiResourceRequest(String year, int count) {
        this.year = year;
        this.count = count;
    }
    
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public String getConferenceName() {
        return conferenceName;
    }
    
    public int getCount() {
        return count;
    }

    public String getYear() {
        return year;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ApiResourceRequest)) {
            return false;
        }

        ApiResourceRequest req = (ApiResourceRequest) obj;

        return req.year.equals(this.year)
                && req.conferenceName.equals(this.conferenceName)
                && req.count == this.count;

    }

    @Override
    public int hashCode() {
        int result = 84;
        result = 31 * result * this.count;
        result = 31 * result * ((this.year == null) ? 0 : this.year.hashCode());
        result = 31 * result * ((this.conferenceName == null) ? 0 : this.conferenceName.hashCode());

        return result;
    }    
}
