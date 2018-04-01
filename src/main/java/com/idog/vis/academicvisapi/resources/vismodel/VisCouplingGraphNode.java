/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.vismodel;

import java.util.Objects;

/**
 *
 * @author idoga
 */
public class VisCouplingGraphNode {

    private VisPaperAuthor author;

    public VisCouplingGraphNode(VisPaperAuthor author) {
        this.author = author;
    }

    public VisPaperAuthor getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Node: " + author.getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VisCouplingGraphNode)) {
            return false;
        }
        
        VisCouplingGraphNode node = (VisCouplingGraphNode) obj;
        return Objects.equals(node, obj);
    }

    @Override
    public int hashCode() {
        int result = 84;
        result = 31 * result * author.hashCode();
        return result;
    }    
}
