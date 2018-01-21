/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.vismodel.VisPaperReference;
import com.idog.vis.academicvisapi.resources.vismodel.VisPaper;
import com.idog.vis.academicvisapi.resources.vismodel.VisPaperAuthor;
import com.idog.vis.academicvisapi.MockService;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.resources.vismodel.VisCouplingGraphEdge;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class VisNetworkProcessorTest {

    VisNetworkProcessor visProc = new VisNetworkProcessor();
    MockService mocker = new MockService();

    @Test
    public void processPapersListTest() {
        List<AcademicApiPaper> mockPapers = mocker.mockPapersRandom();
        visProc.processAbcCoupling(mockPapers);
    }

    @Test
    public void deriveAuthorsListFromPapersListTest() {
        List<AcademicApiPaper> mockPapers = mocker.mockPapersRandom();
        Map<VisPaperAuthor, VisPaperAuthor> visAuthors = visProc.deriveAuthorsListFromPapersList(mockPapers);

        org.junit.Assert.assertTrue(visAuthors.size() < 12); // Max length of the author names list

        VisPaperAuthor a = (visAuthors.entrySet()).iterator().next().getValue();

        if (a == null) {
            throw new AssertionError("Could not get author from set");
        }

        List<VisPaperReference> refs = a.getRefs();
//        org.junit.Assert.assertEquals(visAuthors.size() * 10, refs.size());

        Set<VisPaper> papers = a.getPapers();
//        org.junit.Assert.assertEquals(1, papers.size());
    }

    @Test
    public void calculateSimpleCouplingOfAuthorsTest() {
        List<AcademicApiPaper> mockPapers = mocker.mockPapersRandom();
        List<VisCouplingGraphEdge> edges = visProc.processSimpleCoupling(mockPapers);
        
        /*
        for (VisCouplingGraphEdge edge : edges) {
            System.out.println(edge.toString());
        }*/
        
        
        //assert size
        org.junit.Assert.assertTrue(edges.size() > 0);
    }
}
