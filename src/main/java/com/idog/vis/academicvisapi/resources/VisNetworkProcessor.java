/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.model.VisPaper;
import com.idog.vis.academicvisapi.resources.model.VisPaperAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.resources.model.VisCouplingGraphEdge;
import com.idog.vis.academicvisapi.resources.model.VisCouplingGraphNode;
import com.idog.vis.academicvisapi.resources.model.VisPaperReference;
import com.idog.vis.academicvisapi.resources.model.VisSimpleCoupling;
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

        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = deriveAuthorsToAuthorsMap(chasePapers);
        for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryA : visAuthors.entrySet()) {
            VisPaperAuthor visAuthorA = authorEntryA.getValue();

            for (Map.Entry<VisPaperAuthor, VisPaperAuthor> authorEntryB : visAuthors.entrySet()) {
                VisPaperAuthor visAuthorB = authorEntryB.getValue();
                VisSimpleCoupling couplingAtoB = calculateSimpleCouplingOfAuthors(visAuthorA, visAuthorB);
                VisSimpleCoupling couplingBtoA = calculateSimpleCouplingOfAuthors(visAuthorB, visAuthorA);

                if (!visAuthorA.equals(visAuthorB)) {
                    VisCouplingGraphEdge eAtoB = new VisCouplingGraphEdge(visAuthorA, visAuthorB, couplingAtoB, true);
                    VisCouplingGraphEdge eBtoA = new VisCouplingGraphEdge(visAuthorB, visAuthorA, couplingBtoA, true);

                    if (eAtoB.getCoupling().getCouplingStrength() > 0) {
                        edges.add(eAtoB);
                    }

                    if (eBtoA.getCoupling().getCouplingStrength() > 0) {
                        edges.add(eBtoA);
                    }
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
    private VisSimpleCoupling calculateSimpleCouplingOfAuthors(VisPaperAuthor authorA, VisPaperAuthor authorB) {

        VisSimpleCoupling coupling = new VisSimpleCoupling();
        if (authorA.equals(authorB)) {
            return coupling;
        }

        List<VisPaperReference> refsA = authorA.getRefs();
        Set<VisPaper> papersB = authorB.getPapers();

        for (VisPaper paperB : papersB) {
            for (VisPaperReference refA : refsA) {
                if (paperB.getId().equals(refA.getId())) {
                    coupling.addMatchingPapers(refA.getReferencedFrom().getId());
                }
            }
        }

        return coupling;
    }

    @Override
    public void processAbcCoupling(List<AcademicApiPaper> chasePapers) {
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = deriveAuthorsToAuthorsMap(chasePapers);
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
    public Map<VisPaperAuthor, VisPaperAuthor> deriveAuthorsToAuthorsMap(List<AcademicApiPaper> chasePapers) {
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = new HashMap<>();
        HashMap<Long, VisPaper> allVisPapers = new HashMap<>();

        // The source is each paper from the MS API - there it is the basic entity
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

            // Create an author for each author of the original paper
            // The authors are now the "main" entity
            List<AcademicApiAuthor> msAuthors = chasePaper.getAuthors();
            for (AcademicApiAuthor msAuthor : msAuthors) {

                VisPaperAuthor visAuthor = new VisPaperAuthor(msAuthor.getAuthorName());
                VisPaperAuthor currentAuthor = visAuthors.get(visAuthor);
                if (currentAuthor != null) {
                    currentAuthor.addRefs(visReferences);
                    currentAuthor.addPaper(new VisPaper(chasePaper.getId()));
                    LOGGER.debug("Existing author {} was added the paper and refs", currentAuthor);
                } else {
                    visAuthor.addRefs(visReferences);
                    visAuthor.addPaper(new VisPaper(chasePaper.getId()));
                    visAuthors.put(visAuthor, visAuthor);
                    LOGGER.debug("New author {} set with paper and refs", visAuthor);
                }
            }
        }

        return visAuthors;
    }

    @Override
    public Set<VisCouplingGraphNode> deriveAuthorsSet(List<AcademicApiPaper> chasePapers) {
        Set<VisCouplingGraphNode> authors = new HashSet<>();
        
        for (AcademicApiPaper chasePaper : chasePapers) {
            long academicApiPaperId = chasePaper.getId();
            LOGGER.debug("Processing paper {}", academicApiPaperId);

            // Create an author for each author of the original paper
            // The authors are now the "main" entity
            List<AcademicApiAuthor> msAuthors = chasePaper.getAuthors();
            for (AcademicApiAuthor msAuthor : msAuthors) {

                VisPaperAuthor visAuthor = new VisPaperAuthor(msAuthor.getAuthorName());
                VisCouplingGraphNode authorNode = new VisCouplingGraphNode(visAuthor);
                authors.add(authorNode);
            }
        }
        
        return authors;
    }
}
