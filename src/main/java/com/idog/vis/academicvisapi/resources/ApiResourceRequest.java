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
                && req.count == this.count;

    }

    @Override
    public int hashCode() {
        int result = 84;
        result = 31 * result * this.count;
        result = 31 * result * ((this.year == null) ? 0 : this.year.hashCode());

        return result;
    }
    private String year;
    private int count;

    public ApiResourceRequest(String year, int count) {
        this.year = year;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getYear() {
        return year;
    }
}
