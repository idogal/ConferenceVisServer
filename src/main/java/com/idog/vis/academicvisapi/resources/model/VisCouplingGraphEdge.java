/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.model;

/**
 *
 * @author idoga
 */
public class VisCouplingGraphEdge {
    
    private VisPaperAuthor authorA;
    private VisPaperAuthor authorB;
    private VisSimpleCoupling coupling;
    private boolean directional;

    public VisCouplingGraphEdge(VisPaperAuthor authorA, VisPaperAuthor authorB, VisSimpleCoupling coupling, boolean directional) {
        this.authorA = authorA;
        this.authorB = authorB;
        this.coupling = coupling;
        this.directional = directional;
    }

    public VisPaperAuthor getAuthorA() {
        return authorA;
    }

    public VisPaperAuthor getAuthorB() {
        return authorB;
    }

    public VisSimpleCoupling getCoupling() {
        return coupling;
    }

    public boolean isDirectional() {
        return directional;
    }

    @Override
    public String toString() {
        return "Edge: " + authorA.getName() + " -" + ((directional) ? "> " : " ") + authorB.getName() + " (" + coupling + ")";
    }
}
