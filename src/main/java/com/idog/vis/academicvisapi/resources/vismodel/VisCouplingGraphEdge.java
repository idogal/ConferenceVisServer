/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.vismodel;

/**
 *
 * @author idoga
 */
public class VisCouplingGraphEdge {
    
    private VisPaperAuthor authorA;
    private VisPaperAuthor authorB;
    private int couplingStrength;
    private boolean directional;

    public VisCouplingGraphEdge(VisPaperAuthor authorA, VisPaperAuthor authorB, int couplingStrength, boolean directional) {
        this.authorA = authorA;
        this.authorB = authorB;
        this.couplingStrength = couplingStrength;
        this.directional = directional;
    }

    public VisPaperAuthor getAuthorA() {
        return authorA;
    }

    public VisPaperAuthor getAuthorB() {
        return authorB;
    }

    public int getCouplingStrength() {
        return couplingStrength;
    }

    public boolean isDirectional() {
        return directional;
    }

    @Override
    public String toString() {
        return "Edge: " + authorA.getName() + " -" + ((directional) ? "> " : " ") + authorB.getName() + " (" + couplingStrength + ")";
    }
}