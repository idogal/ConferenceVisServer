/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.vismodel.VisPaper;
import com.idog.vis.academicvisapi.resources.vismodel.VisPaperAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.resources.vismodel.VisCouplingGraphEdge;
import com.idog.vis.academicvisapi.resources.vismodel.VisPaperReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author idoga
 */
public class VisNetworkProcessor implements NetworkProcessor {
    private static final Logger LOGGER = LogManager.getLogger(VisNetworkProcessor.class);
    
    @Override
    public List<VisCouplingGraphEdge> processSimpleCoupling(List<AcademicApiPaper> chasePapers) {
        
        List<VisCouplingGraphEdge> edges = new ArrayList<>();
        
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = deriveAuthorsListFromPapersList(chasePapers);
        for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryA : visAuthors.entrySet()) {
            VisPaperAuthor visAuthorA = authorEntryA.getValue();

            for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryB : visAuthors.entrySet()) {
                VisPaperAuthor visAuthorB = authorEntryB.getValue();                
                int calculateSimpleCouplingOfAuthors = calculateSimpleCouplingOfAuthors(visAuthorA, visAuthorB);
                
                if (!visAuthorA.equals(visAuthorB)) {
                    VisCouplingGraphEdge e = new VisCouplingGraphEdge(visAuthorA, visAuthorB, calculateSimpleCouplingOfAuthors, true);
                    edges.add(e);
                }
            }
        }
        
        return edges;
    }

    /**
     * The coupling is <b>directional</b>, from A to B.
     *
     * @param authorA
     * @param authorB
     */
    private int calculateSimpleCouplingOfAuthors(VisPaperAuthor authorA, VisPaperAuthor authorB) {

        int coupling = 0;
        if (authorA.equals(authorB)) {
            return 0;
        }
        
        List<VisPaperReference> refsA = authorA.getRefs();
        Set<VisPaper> papersB = authorB.getPapers();

        for (VisPaper paperB : papersB) {
            for (VisPaperReference refA : refsA) {
                if (paperB.getId().equals(refA.getId())) {
                    coupling++;
                }
            }
        }
        
        return coupling;
    }

    @Override
    public void processAbcCoupling(List<AcademicApiPaper> chasePapers) {
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = deriveAuthorsListFromPapersList(chasePapers);
        for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryA : visAuthors.entrySet()) {
            VisPaperAuthor visAuthorA = authorEntryA.getValue();
            String authorNameA = visAuthorA.getName();
            HashSet<VisPaperReference> orginialAuthorRefsA = new HashSet<>(visAuthorA.getRefs());
            //System.out.println(authorNameA + " -> ");
            //System.out.print(orginialAuthorRefsA.toString());
            for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryB : visAuthors.entrySet()) {
                VisPaperAuthor visAuthorB = authorEntryB.getValue();
                String authorNameB = visAuthorB.getName();
                HashSet<VisPaperReference> intersectionResult = new HashSet<>(visAuthorA.getRefs());
                HashSet<VisPaperReference> authorRefsB = new HashSet<>(visAuthorB.getRefs());
                //System.out.println("    " + authorNameB + " -> ");
                //System.out.println("    " + authorRefsB.toString());
                if (authorNameA.equals(authorNameB)) {
                    continue;
                }

                boolean intersectionChange = intersectionResult.retainAll(authorRefsB);
                if (intersectionChange) {
                    int score = intersectionResult.size();
                    //System.out.print("    ****" + score);
                    //System.out.print(" - " + intersectionResult.toString());
                }
            }
        }
    }

    @Override
    public Map<VisPaperAuthor, VisPaperAuthor> deriveAuthorsListFromPapersList(List<AcademicApiPaper> chasePapers) {
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = new HashMap<>();
        HashMap<Long, VisPaper> allVisPapers = new HashMap<>();
        
        for (AcademicApiPaper chasePaper : chasePapers) {
            long academicApiPaperId = chasePaper.getId();
            LOGGER.debug("Processing paper {}", academicApiPaperId);
            VisPaper visPaper = allVisPapers.get(academicApiPaperId);
            if (visPaper == null) {
                visPaper = new VisPaper(chasePaper.getId());
            }
            
            // Build a list of refs for the current paper (it is not dependent on the author)
            List<VisPaperReference> visReferences = new ArrayList<>();
            for (Long reference : chasePaper.getReferences()) {
                VisPaperReference visPaperReference = new VisPaperReference(reference, visPaper);
                visReferences.add(visPaperReference);
            }
            LOGGER.debug("Added refs for paper {} - {}", chasePaper.getId(), visReferences.toString());
            
            List<AcademicApiAuthor> msAuthors = chasePaper.getAuthors();
            for (AcademicApiAuthor msAuthor : msAuthors) {

                VisPaperAuthor visAuthor = new VisPaperAuthor(msAuthor.getAuthorName());
                VisPaperAuthor currentAuthor = visAuthors.get(visAuthor);
                if (currentAuthor != null) {
                    currentAuthor.addRefs(visReferences);
                    currentAuthor.addPaper(new VisPaper(chasePaper.getId()));
                    LOGGER.debug("Existing author {} was added the paper and refs", currentAuthor);
                } else {
                    visAuthor.setRefs(visReferences);
                    visAuthor.addPaper(new VisPaper(chasePaper.getId()));
                    visAuthors.put(visAuthor, visAuthor);
                    LOGGER.debug("New author {} set with paper and refs", visAuthor);
                }
            }
        }

        return visAuthors;
    }
}
