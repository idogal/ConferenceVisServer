/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idog.vis.academicvisapi.beans.AcademicApiPaper;
import com.idog.vis.academicvisapi.beans.AcademicApiPaperExtended;
import com.idog.vis.academicvisapi.beans.AcademicApiResponse;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class academicvisapiTest {
    
    @Test
    public void parseApiResponseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonA = getResponseJson();
        AcademicApiResponse apiResponse = mapper.readValue(jsonA, AcademicApiResponse.class);
        
        // Response
        System.out.println("expr = " + apiResponse.expr);
        org.junit.Assert.assertEquals("And(Composite(C.CN='icse'), Y=2015)", apiResponse.expr);
        
        System.out.println("entities # = " + apiResponse.entities.size());
        org.junit.Assert.assertEquals(1, apiResponse.entities.size());
        
        // Paper Entity
        AcademicApiPaper paper = apiResponse.entities.get(0);
        System.out.println("Title = " + paper.title);
        org.junit.Assert.assertEquals("iccta detecting inter component privacy leaks in android apps", paper.title);
        
        // Extended
        String paperExt = paper.extendedProperties;
        System.out.println("Ext = " + paper.extendedProperties);     
        AcademicApiPaperExtended ext = mapper.readValue(paperExt, AcademicApiPaperExtended.class);
        System.out.println("entities # = " + ext.venueFullName);
        org.junit.Assert.assertEquals("International Conference on Software Engineering", ext.venueFullName);
    }    
    
    private String getResponseJson() {
        return 
            "{\n" +
            "    \"expr\": \"And(Composite(C.CN='icse'), Y=2015)\",\n" +
            "    \"entities\": [\n" +
            "        {\n" +
            "            \"logprob\": -17.857,\n" +
            "            \"Id\": 2077202047,\n" +
            "            \"Ti\": \"iccta detecting inter component privacy leaks in android apps\",\n" +
            "            \"Y\": 2015,\n" +
            "            \"E\": \"{\\\"DN\\\":\\\"IccTA: detecting inter-component privacy leaks in Android apps\\\",\\\"IA\\\":{\\\"IndexLength\\\":194,\\\"InvertedIndex\\\":{\\\"Shake\\\":[0,64],\\\"Them\\\":[1,65],\\\"All\\\":[2,66],\\\"is\\\":[4,20,74],\\\"a\\\":[5,78,119,187],\\\"popular\\\":[6],\\\"\\\\\\\"Wallpaper\\\\\\\"\\\":[7],\\\"application\\\":[8,19,47],\\\"exceeding\\\":[9],\\\"millions\\\":[10],\\\"of\\\":[11,86,151,189],\\\"downloads\\\":[12],\\\"on\\\":[13,158],\\\"Google\\\":[14,191],\\\"Play.\\\":[15],\\\"At\\\":[16],\\\"installation,\\\":[17],\\\"this\\\":[18],\\\"given\\\":[21],\\\"permission\\\":[22],\\\"to\\\":[23,76,94,123],\\\"(1)\\\":[24],\\\"access\\\":[25],\\\"the\\\":[26,34,46,84,149,152],\\\"Internet\\\":[27],\\\"(for\\\":[28],\\\"updating\\\":[29],\\\"wallpapers)\\\":[30],\\\"and\\\":[31,53,165,179],\\\"(2)\\\":[32],\\\"use\\\":[33],\\\"device\\\":[35],\\\"microphone\\\":[36],\\\"(to\\\":[37],\\\"change\\\":[38],\\\"background\\\":[39],\\\"following\\\":[40],\\\"noise\\\":[41],\\\"changes).\\\":[42],\\\"With\\\":[43],\\\"these\\\":[44],\\\"permissions,\\\":[45],\\\"could\\\":[48],\\\"silently\\\":[49],\\\"record\\\":[50],\\\"user\\\":[51],\\\"conversations\\\":[52],\\\"upload\\\":[54],\\\"them\\\":[55],\\\"remotely.\\\":[56],\\\"To\\\":[57],\\\"give\\\":[58],\\\"more\\\":[59],\\\"confidence\\\":[60],\\\"about\\\":[61],\\\"how\\\":[62],\\\"actually\\\":[68],\\\"processes\\\":[69],\\\"what\\\":[70],\\\"it\\\":[71,73],\\\"records,\\\":[72],\\\"necessary\\\":[75],\\\"build\\\":[77],\\\"precise\\\":[79],\\\"analysis\\\":[80],\\\"tool\\\":[81],\\\"that\\\":[82],\\\"tracks\\\":[83],\\\"flow\\\":[85],\\\"any\\\":[87,95],\\\"sensitive\\\":[88],\\\"data\\\":[89,112],\\\"from\\\":[90,177],\\\"its\\\":[91],\\\"source\\\":[92],\\\"point\\\":[93],\\\"sink,\\\":[96],\\\"especially\\\":[97],\\\"if\\\":[98],\\\"those\\\":[99],\\\"are\\\":[100],\\\"in\\\":[101,129,174,183,186],\\\"different\\\":[102],\\\"components.\\\":[103],\\\"Since\\\":[106],\\\"Android\\\":[107,130],\\\"applications\\\":[108],\\\"may\\\":[109],\\\"leak\\\":[110],\\\"private\\\":[111],\\\"carelessly\\\":[113],\\\"or\\\":[114],\\\"maliciously,\\\":[115],\\\"we\\\":[116],\\\"propose\\\":[117],\\\"IccTA,\\\":[118],\\\"static\\\":[120],\\\"taint\\\":[121],\\\"analyzer\\\":[122],\\\"detect\\\":[124],\\\"privacy\\\":[125],\\\"leaks\\\":[126,173,182],\\\"among\\\":[127,145],\\\"components\\\":[128],\\\"applications.\\\":[131],\\\"IccTA\\\":[132,147,154],\\\"goes\\\":[133],\\\"beyond\\\":[134],\\\"state-of-the-art\\\":[135],\\\"approaches\\\":[136],\\\"by\\\":[137],\\\"supporting\\\":[138],\\\"inter-component\\\":[139],\\\"detection.\\\":[140],\\\"By\\\":[141],\\\"propagating\\\":[142],\\\"context\\\":[143],\\\"information\\\":[144],\\\"components,\\\":[146],\\\"improves\\\":[148],\\\"precision\\\":[150],\\\"analysis.\\\":[153],\\\"outperforms\\\":[155],\\\"existing\\\":[156],\\\"tools\\\":[157],\\\"two\\\":[159],\\\"benchmarks\\\":[160],\\\"for\\\":[161],\\\"ICC-leak\\\":[162],\\\"detectors:\\\":[163],\\\"DroidBench\\\":[164],\\\"ICC-Bench.\\\":[166],\\\"Moreover,\\\":[167],\\\"our\\\":[168],\\\"approach\\\":[169],\\\"detects\\\":[170],\\\"534\\\":[171],\\\"ICC\\\":[172,181],\\\"108\\\":[175],\\\"apps\\\":[176,185],\\\"MalGenome\\\":[178],\\\"2,395\\\":[180],\\\"337\\\":[184],\\\"set\\\":[188],\\\"15,000\\\":[190],\\\"Play\\\":[192],\\\"apps.\\\":[193]}},\\\"S\\\":[{\\\"Ty\\\":3,\\\"U\\\":\\\"http://orbilu.uni.lu/bitstream/10993/20058/1/li-iccta-preprint.pdf\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dl.acm.org/citation.cfm?id=2818791\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dblp.uni-trier.de/db/conf/icse/icse2015-1.html#0029BBKTARBOM15\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://orbilu.uni.lu/handle/10993/20058\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://ieeexplore.ieee.org/document/7194581/\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://publications.uni.lu/handle/10993/20058\\\"},{\\\"Ty\\\":0,\\\"U\\\":\\\"http://ieeexplore.ieee.org/xpl/abstractCitations.jsp?reload=true&arnumber=7194581&punumber%3D7174815\\\"}],\\\"VFN\\\":\\\"International Conference on Software Engineering\\\",\\\"VSN\\\":\\\"ICSE\\\",\\\"V\\\":1,\\\"FP\\\":280,\\\"LP\\\":291,\\\"DOI\\\":\\\"10.1109/ICSE.2015.48\\\",\\\"RP\\\":[{\\\"Id\\\":2166743230,\\\"CoC\\\":75},{\\\"Id\\\":1630356589,\\\"CoC\\\":53},{\\\"Id\\\":1988036170,\\\"CoC\\\":49},{\\\"Id\\\":2027538101,\\\"CoC\\\":48},{\\\"Id\\\":2125011234,\\\"CoC\\\":43},{\\\"Id\\\":1963971515,\\\"CoC\\\":36},{\\\"Id\\\":1986480799,\\\"CoC\\\":35},{\\\"Id\\\":1994588724,\\\"CoC\\\":33},{\\\"Id\\\":2140095007,\\\"CoC\\\":32},{\\\"Id\\\":2017025011,\\\"CoC\\\":30},{\\\"Id\\\":2113115074,\\\"CoC\\\":30},{\\\"Id\\\":124941384,\\\"CoC\\\":29},{\\\"Id\\\":2078197322,\\\"CoC\\\":29},{\\\"Id\\\":1912565424,\\\"CoC\\\":26},{\\\"Id\\\":2398484989,\\\"CoC\\\":26},{\\\"Id\\\":2122672392,\\\"CoC\\\":25},{\\\"Id\\\":2085577046,\\\"CoC\\\":24},{\\\"Id\\\":2114275288,\\\"CoC\\\":24},{\\\"Id\\\":2060692877,\\\"CoC\\\":22},{\\\"Id\\\":2127723417,\\\"CoC\\\":21}],\\\"ANF\\\":[{\\\"FN\\\":\\\"Yves Le\\\",\\\"LN\\\":\\\"Traon\\\",\\\"S\\\":5},{\\\"FN\\\":\\\"Damien\\\",\\\"LN\\\":\\\"Octeau\\\",\\\"S\\\":9},{\\\"FN\\\":\\\"Alexandre\\\",\\\"LN\\\":\\\"Bartel\\\",\\\"S\\\":2},{\\\"FN\\\":\\\"Siegfried\\\",\\\"LN\\\":\\\"Rasthofer\\\",\\\"S\\\":7},{\\\"FN\\\":\\\"Patrick\\\",\\\"LN\\\":\\\"McDaniel\\\",\\\"S\\\":10},{\\\"FN\\\":\\\"Tegawendé François D Assise\\\",\\\"LN\\\":\\\"Bissyande\\\",\\\"S\\\":3},{\\\"FN\\\":\\\"Li\\\",\\\"LN\\\":\\\"Li\\\",\\\"S\\\":1},{\\\"FN\\\":\\\"Jacques\\\",\\\"LN\\\":\\\"Klein\\\",\\\"S\\\":4},{\\\"FN\\\":\\\"Eric\\\",\\\"LN\\\":\\\"Bodden\\\",\\\"S\\\":8},{\\\"FN\\\":\\\"Steven\\\",\\\"LN\\\":\\\"Arzt\\\",\\\"S\\\":6}],\\\"BV\\\":\\\"2015 IEEE/ACM 37th IEEE International Conference on Software Engineering\\\",\\\"BT\\\":\\\"p\\\",\\\"PB\\\":\\\"IEEE\\\"}\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    }
}
