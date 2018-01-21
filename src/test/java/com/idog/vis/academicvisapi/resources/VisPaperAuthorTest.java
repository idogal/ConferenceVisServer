/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.vismodel.VisPaperAuthor;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author idoga
 */
public class VisPaperAuthorTest {
    
    @Test
    public void testEquals() {
        VisPaperAuthor authorA = new VisPaperAuthor("A");
        VisPaperAuthor authorB = new VisPaperAuthor("B");
        VisPaperAuthor authorC = new VisPaperAuthor("A");
        
        boolean isAnotequalsToB = !(authorA.equals(authorB));
        boolean isAequalsToC = authorA.equals(authorC);
        
        Assert.assertTrue(isAnotequalsToB);
        Assert.assertTrue(isAequalsToC);        
    }
}
