/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.vismodel.VisPaperAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.resources.vismodel.VisCouplingGraphEdge;
import com.idog.vis.academicvisapi.resources.vismodel.VisCouplingGraphNode;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author idoga
 */
public interface NetworkProcessor {
    public void processAbcCoupling(List<AcademicApiPaper> chasePapers);
    public List<VisCouplingGraphEdge> processSimpleCoupling(List<AcademicApiPaper> chasePapers);
    public Map<VisPaperAuthor, VisPaperAuthor> deriveAuthorsToAuthorsMap(List<AcademicApiPaper> chasePapers);
    public Set<VisCouplingGraphNode> deriveAuthorsSet(List<AcademicApiPaper> chasePapers);
}
