/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import com.idog.vis.academicvisapi.resources.model.VisPaper;
import com.idog.vis.academicvisapi.resources.model.VisPaperReference;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class VisPaperReferenceTest {
    @Test
    public void testEquals() {
        VisPaper p = new VisPaper(1111L);
        VisPaperReference refA = new VisPaperReference(21324L, p);
        VisPaperReference refB = new VisPaperReference(21555L, p);
        VisPaperReference refC = new VisPaperReference(21555L, p);
        
        boolean aNotEqualsB = !(refA.equals(refB));
        boolean bEqualsC = (refB.equals(refC));
        
        Assert.assertTrue(aNotEqualsB);
        Assert.assertTrue(bEqualsC);
    }
}
