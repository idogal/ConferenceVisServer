/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.beans.AcademicApiAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author idoga
 */
public class VisNetworkProcessor implements NetworkProcessor {

    @Override
    public void processPapersList(List<AcademicApiPaper> chasePapers) {
        
    }

    @Override
    public List<VisAuthor> deriveAuthorsListFromPapersList(List<AcademicApiPaper> chasePapers) {
        List<VisAuthor> visAuthors = new ArrayList<>();
        
        for (AcademicApiPaper chasePaper : chasePapers) {
            List<AcademicApiAuthor> msAuthors = chasePaper.getAuthors();
            for (AcademicApiAuthor msAuthor : msAuthors) {
                VisAuthor visAuthor = new VisAuthor(msAuthor.getAuthorName());
                visAuthor.setRefs(chasePaper.getReferences());
                
                visAuthors.add(visAuthor);
            }
        }
        
        return visAuthors;
    }
    
    
    
}
