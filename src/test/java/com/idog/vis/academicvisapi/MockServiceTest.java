/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import com.idog.vis.academicvisapi.beans.AcademicApiAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class MockServiceTest {
    @Test
    public void testMock() {
        MockService mockService = new MockService();
        List<AcademicApiPaper> mockPapers = mockService.mockPapers();
        
        Assert.assertEquals(10, mockPapers.size());
        
        AcademicApiPaper paper = mockPapers.get(0);
        List<AcademicApiAuthor> authors = paper.getAuthors();
        Assert.assertEquals(10, authors.size());
        
        List<Long> refs = paper.getReferences();
        Assert.assertEquals(10, refs.size());
    }
}
