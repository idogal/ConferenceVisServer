/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import com.idog.vis.academicvisapi.beans.AcademicApiAuthor;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.*;

/**
 *
 * @author idoga
 */
public class MockService {
    
    private AcademicApiAuthor mockAuthor() {
        AcademicApiAuthor authorMock = mock(AcademicApiAuthor.class);
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
        int index = new Random().nextInt(names.size());
        String aName = names.get(index);        
        when(authorMock.getAuthorName()).thenReturn(aName);
        
        return authorMock;
    }
    
    private AcademicApiPaper mockPaper() {
        AcademicApiPaper msPaperMock = mock(AcademicApiPaper.class);
        
        List<AcademicApiAuthor> mockedAuthors = new ArrayList<>();        
        for (int i = 0; i < 10; i++) {
            mockedAuthors.add(mockAuthor());
        }        
        when(msPaperMock.getAuthors()).thenReturn(mockedAuthors);
        
        List<Long> refs = new ArrayList<>();        
        for (int i = 0; i < 10; i++) {
            refs.add(Long.valueOf(new Random().nextInt(20)));
        }
        when(msPaperMock.getReferences()).thenReturn(refs);        
        
        return msPaperMock;
    }
    
    public List<AcademicApiPaper> mockPapers() {
        List<AcademicApiPaper> mockedPapers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AcademicApiPaper p = mockPaper();
            mockedPapers.add(p);
        }
        return mockedPapers;
    }    
}
