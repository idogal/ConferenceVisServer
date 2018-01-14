/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.msapi;

import org.junit.Test;

/**
 *
 * @author idoga
 */
public class MsAcademinApiExprTest {
    
    
    @Test
    public void MsAcademinApiExpr() {
        com.idog.vis.academicvisapi.msapi.MsAcademinApiExpr.MsAcademinApiExprBuilder msAcademinApiExprBuilder = new MsAcademinApiExpr.MsAcademinApiExprBuilder();
        MsAcademicQueryParam cnParam = msAcademinApiExprBuilder.addParam("C.CN", "chase", MsAcademicQueryParam.ParamType.STRING_PARAM, true);
        MsAcademicQueryParam yParam = cnParam.addNextParam("Y", "2011", MsAcademicQueryParam.LogicalOperator.AND, MsAcademicQueryParam.ParamType.NONSTRING_PARAM);
        com.idog.vis.academicvisapi.msapi.MsAcademinApiExpr build = msAcademinApiExprBuilder.build();
        
        String generate = build.generate();
        org.junit.Assert.assertNotNull(generate);
    }
}
