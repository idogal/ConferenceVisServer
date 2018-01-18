/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.MockService;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class VisNetworkProcessorTest {
    
    VisNetworkProcessor visProc = new VisNetworkProcessor();
    MockService mocker = new MockService();
    @Test
    public void processPapersList() {
        List<AcademicApiPaper> mockPapers = mocker.mockPapers();
        List<VisAuthor> visAuthors = visProc.deriveAuthorsListFromPapersList(mockPapers);
        
        org.junit.Assert.assertEquals(100, visAuthors.size());
        
        List<Long> refs = visAuthors.get(0).getRefs();
        org.junit.Assert.assertEquals(10, refs.size());
    }
}
