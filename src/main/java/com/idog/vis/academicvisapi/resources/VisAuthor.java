/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.resources;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author idoga
 */
public class VisAuthor {
    private final String name;
    private List<Long> refs = new ArrayList<>();

    public VisAuthor(String name) {
        this.name = name;
    }

    public void setRefs(List<Long> refs) {
        this.refs = refs;
    }
    
    public void addRef(Long ref) {
        this.refs.add(ref);
    }

    public String getName() {
        return name;
    }

    public List<Long> getRefs() {
        return refs;
    }
}
