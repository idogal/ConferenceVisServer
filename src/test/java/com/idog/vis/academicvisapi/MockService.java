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
    
    public List<AcademicApiPaper> mockPapers() {
        List<AcademicApiPaper> mockedPapers = new ArrayList<>();
        
        AcademicApiPaper mockPaper1989973809 = mockPaper1989973809();
        mockedPapers.add(mockPaper1989973809);
        AcademicApiPaper mockPaper2100507340 = mockPaper2100507340(); // orit hazzan, meira levy
        mockedPapers.add(mockPaper2100507340);
        AcademicApiPaper mockPaper2085477894 = mockPaper2085477894(); // orit hazzan, yael dubinsky
        mockedPapers.add(mockPaper2085477894);
        
        return mockedPapers;
    }        
    
    private static AcademicApiPaper mockPaper1989973809() {
        AcademicApiPaper paperMock = mock(AcademicApiPaper.class);
        when(paperMock.getId()).thenReturn(1989973809L); //Knowledge management in practice: The case of agile software development
        
        //1989973809 REFERENCES
        List<Long> mockRefs = new ArrayList<>();      
        mockRefs.add(178070138L);
        mockRefs.add(1488144930L);
        mockRefs.add(1493688518L);
        mockRefs.add(1504346132L);
        mockRefs.add(1542272426L);
        mockRefs.add(1544210224L);
        mockRefs.add(1547579513L);
        mockRefs.add(1566009915L);
        mockRefs.add(1658908529L);
        mockRefs.add(1814702370L);
        mockRefs.add(1960682045L);
        mockRefs.add(1985933438L);
        mockRefs.add(1998957059L);
        mockRefs.add(2053454824L);
        mockRefs.add(2076483318L);
        mockRefs.add(2107685659L);
        mockRefs.add(2118298147L);
        mockRefs.add(2129559874L);
        mockRefs.add(2146594781L);
        mockRefs.add(2153998517L);        
        when(paperMock.getReferences()).thenReturn(mockRefs);  
        
        // Authors
        AcademicApiAuthor levyMock = mock(AcademicApiAuthor.class);
        when(levyMock.getAuthorName()).thenReturn("meira levy");        
        when(levyMock.getAuthorId()).thenReturn(2569093161L);   
        AcademicApiAuthor hadarMock = mock(AcademicApiAuthor.class);
        when(hadarMock.getAuthorName()).thenReturn("irit hadar");         
        when(hadarMock.getAuthorId()).thenReturn(1246307506L);
        AcademicApiAuthor segalravivMock = mock(AcademicApiAuthor.class);
        when(segalravivMock.getAuthorName()).thenReturn("anat segalraviv");         
        when(segalravivMock.getAuthorId()).thenReturn(2228818140L);
        
        when(paperMock.getAuthors()).thenReturn(Arrays.asList(levyMock, hadarMock, segalravivMock));
        
        return paperMock;
    }    
    
    private static AcademicApiPaper mockPaper2085477894() {
        AcademicApiPaper paperMock = mock(AcademicApiPaper.class);
        when(paperMock.getId()).thenReturn(2085477894L); //Ad-hoc leadership in agile software development environments
        
        //2085477894 REFERENCES
        List<Long> mockRefs = new ArrayList<>();      
        mockRefs.add(1511975670L);
        mockRefs.add(1520904419L);
        mockRefs.add(1559302762L);
        mockRefs.add(1569881411L);
        mockRefs.add(1572454441L);
        mockRefs.add(1588543639L);
        mockRefs.add(1589259604L);
        mockRefs.add(1598289784L);
        mockRefs.add(1804097652L);
        mockRefs.add(1914512926L);
        mockRefs.add(2061804864L);
        mockRefs.add(2118298147L);
        mockRefs.add(2129663874L);
        mockRefs.add(2139502068L);
        mockRefs.add(2149468448L);
        mockRefs.add(2165974531L);    
        mockRefs.add(2461291769L);    
        when(paperMock.getReferences()).thenReturn(mockRefs);             
        
        // Authors
        AcademicApiAuthor dMock = mock(AcademicApiAuthor.class);
        when(dMock.getAuthorName()).thenReturn("yael dubinsky");        
        when(dMock.getAuthorId()).thenReturn(2050650508L);   
        AcademicApiAuthor hazanMock = mock(AcademicApiAuthor.class);
        when(hazanMock.getAuthorName()).thenReturn("orit hazzan");         
        when(hazanMock.getAuthorId()).thenReturn(266418477L);
        
        when(paperMock.getAuthors()).thenReturn(Arrays.asList(dMock, hazanMock));
        
        return paperMock;
    }    
    
    private static AcademicApiPaper mockPaper2100507340() {
        AcademicApiPaper paperMock = mock(AcademicApiPaper.class);
        when(paperMock.getId()).thenReturn(2100507340L); //Knowledge management in practice: The case of agile software development
        
        //2100507340 REFERENCES
        List<Long> mockRefs = new ArrayList<>();      
        mockRefs.add(80322239L);
        mockRefs.add(136209745L);
        mockRefs.add(184101768L);
        mockRefs.add(1557437459L);
        mockRefs.add(1565790345L);
        mockRefs.add(1569881411L);
        mockRefs.add(1804097652L);
        mockRefs.add(1867533769L);
        mockRefs.add(2027814993L);
        mockRefs.add(2076833217L);
        mockRefs.add(2080987876L);
        mockRefs.add(2095659828L);
        mockRefs.add(2098118776L);
        mockRefs.add(2132454116L);
        mockRefs.add(2138173796L);
        mockRefs.add(2144575309L);
        mockRefs.add(2148071752L);
        mockRefs.add(2480819921L);
        mockRefs.add(2593932684L);    
        when(paperMock.getReferences()).thenReturn(mockRefs);     
        
        
        // Authors
        AcademicApiAuthor levyMock = mock(AcademicApiAuthor.class);
        when(levyMock.getAuthorName()).thenReturn("meira levy");        
        when(levyMock.getAuthorId()).thenReturn(2311641733L);   
        AcademicApiAuthor hazanMock = mock(AcademicApiAuthor.class);
        when(hazanMock.getAuthorName()).thenReturn("orit hazzan");         
        when(hazanMock.getAuthorId()).thenReturn(266418477L);
        
        when(paperMock.getAuthors()).thenReturn(Arrays.asList(levyMock, hazanMock));
        
        return paperMock;
    }
    
    private AcademicApiAuthor mockAuthorRandom() {
        AcademicApiAuthor authorMock = mock(AcademicApiAuthor.class);
        List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
        int index = new Random().nextInt(names.size());
        String aName = names.get(index);        
        when(authorMock.getAuthorName()).thenReturn(aName);
        
        return authorMock;
    }
    
    private AcademicApiPaper mockPaperRandom() {
        AcademicApiPaper msPaperMock = mock(AcademicApiPaper.class);
        when(msPaperMock.getId()).thenReturn(Long.valueOf(new Random().nextInt(20)));
        
        List<AcademicApiAuthor> mockedAuthors = new ArrayList<>();        
        for (int i = 0; i < 3; i++) {
            mockedAuthors.add(mockAuthorRandom());
        }
        when(msPaperMock.getAuthors()).thenReturn(mockedAuthors);
        
        List<Long> refs = new ArrayList<>();        
        for (int i = 0; i < 10; i++) {
            refs.add(Long.valueOf(new Random().nextInt(20)));
        }
        when(msPaperMock.getReferences()).thenReturn(refs);        
        
        return msPaperMock;
    }    
    
    public List<AcademicApiPaper> mockPapersRandom() {
        return mockPapersRandom(false);
    }
    
    public List<AcademicApiPaper> mockPapersRandom(boolean print) {
        List<AcademicApiPaper> mockedPapers = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            AcademicApiPaper p = mockPaperRandom();
            mockedPapers.add(p);
            
            if (print) {
                System.out.println(p.toString());
            }
        }
        
        return mockedPapers;
    }    
}
