/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.model;

import java.util.Objects;

/**
 *
 * @author idoga
 */
public class VisPaperReference extends VisPaper {
    
    private final VisPaper referencedFrom;

    public VisPaperReference(Long id, VisPaper referencedFrom) {
        super(id);
        this.referencedFrom = referencedFrom;
    }

    public VisPaper getReferencedFrom() {
        return referencedFrom;
    }

    @Override
    public int hashCode() {
        int result = 84;
        int paperId = (this.referencedFrom == null) ? 0 : this.referencedFrom.hashCode();
        
        result = super.hashCode();
        result = 31 * result * paperId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VisPaperReference)) {
            return false;
        }
        if (!(super.equals(obj))) {
            return false;
        }
        VisPaperReference ref = (VisPaperReference) obj;
        return Objects.equals(ref.referencedFrom, this.referencedFrom);
    }

    @Override
    public String toString() {
        return super.toString() + ", Refernced From: " + referencedFrom.toString();
    }
    
}
