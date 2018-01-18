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
public interface NetworkProcessor {
    public void processPapersList(List<AcademicApiPaper> chasePapers);
    public List<VisAuthor> deriveAuthorsListFromPapersList(List<AcademicApiPaper> chasePapers);
}
