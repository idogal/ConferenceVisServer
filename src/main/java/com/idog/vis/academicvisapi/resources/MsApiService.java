/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author idoga
 */
public interface MsApiService {
    public List<AcademicApiPaper> getChasePapersAsList(String year, boolean noCache) throws IOException;
}
