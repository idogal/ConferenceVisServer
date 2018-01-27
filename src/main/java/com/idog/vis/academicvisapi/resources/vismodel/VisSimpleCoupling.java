/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.vismodel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author idoga
 */
public class VisSimpleCoupling {
    private List<Long> matchingPapers;
    private int couplingStrength = 0;

    public VisSimpleCoupling() {
        this.matchingPapers = new ArrayList<>();
    }

    public int getCouplingStrength() {
        return couplingStrength;
    }
    
    public void setMatchingPapers(List<Long> matchingPapers) {
        this.matchingPapers = matchingPapers;
        this.couplingStrength = matchingPapers.size();
    }

    public void addMatchingPapers(Long matchingPaper) {
        this.matchingPapers.add(matchingPaper);
        couplingStrength ++;
    }    
    
    public List<Long> getMatchingPapers() {
        return matchingPapers;
    }
    
    
}
