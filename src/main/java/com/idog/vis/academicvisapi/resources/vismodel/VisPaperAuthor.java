/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources.vismodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author idoga
 */
public class VisPaperAuthor {

    private final String name;
    private List<VisPaperReference> refs = new ArrayList<>();
    private Set<VisPaper> papers = new HashSet<>(); 
    
    public VisPaperAuthor(String name) {
        this.name = name;
    }

    public void setRefs(List<VisPaperReference> refs) {
        this.refs = refs;
    }

    public void addRefs(List<VisPaperReference> refs) {
        this.refs.addAll(refs);
    }    
    
    public void addRef(VisPaperReference ref) {
        this.refs.add(ref);
    }
    
    public void setPapers(Set<VisPaper> papers) {
        this.papers = papers;
    }

    public void addPapers(List<VisPaper> papers) {
        this.papers.addAll(papers);
    }    
    
    public void addPaper(VisPaper paper) {
        this.papers.add(paper);
    }    

    public String getName() {
        return name;
    }

    public List<VisPaperReference> getRefs() {
        return refs;
    } 

    public Set<VisPaper> getPapers() {
        return papers;
    }
    
    @Override
    public String toString() {
        return this.name + ", refs: " + this.refs.toString();
    }

    /**
     * Two authors are equal if they have the same <b>name</b>.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VisPaperAuthor)) {
            return false;
        }
        VisPaperAuthor author = (VisPaperAuthor) obj;
        return Objects.equals(author.name, this.name);
    }

    @Override
    public int hashCode() {
        int result = 84;
        result = 31 * result * name.hashCode();
        return result;
    }
}
