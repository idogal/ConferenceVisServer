/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import com.mongodb.MongoClient;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author idoga
 */
public class VisPersistenceServiceTest {

    private static VisPersistenceService service;

//    @BeforeClass
//    public static void init() {
//        ApiCache cache = new ApiCache();
//        MongoClient mongoClient = new MongoClient();
//        service = new VisPersistenceService(cache, mongoClient);
//
//    }
//
//    @Test
//    public void getMsApiResponseTest() {
//        String msApiResponse = service.getMsApiResponse("CHASE", "", 3);
//    }
//
//    @Test
//    public void storeMsApiResponseTest() {
//        String r = getMsApiJsonResponse();
//        
//        service.storeMsApiResponse("CHASE", "", 3, r);
//    }

    private String getMsApiJsonResponse() {
        String r
                = "{\"expr\":\"Composite(C.CN='chase')\",\"entities\":[{\"logprob\":-19.129,\"Id\":2125146799,\"Ti\":\"whyaresoftwareprojectsmovingfromcentralizedtodecentralizedversioncontrolsystems\",\"Y\":2009,\"E\":\"{\\\"DN\\\":\\\"Whyaresoftwareprojectsmovingfromcentralizedtodecentralizedversioncontrolsystems\\\",\\\"IA\\\":{\\\"IndexLength\\\":74,\\\"InvertedIndex\\\":{\\\"Version\\\":[0],\\\"control\\\":[1,35,42],\\\"systems\\\":[2],\\\"are\\\":[3,19],\\\"essential\\\":[4],\\\"for\\\":[5],\\\"co-ordinating\\\":[6],\\\"work\\\":[7],\\\"on\\\":[8],\\\"a\\\":[9,32,39,53,56],\\\"software\\\":[10],\\\"project.\\\":[11],\\\"A\\\":[12],\\\"number\\\":[13],\\\"of\\\":[14,61],\\\"open-\\\":[15],\\\"and\\\":[16,55,58,64],\\\"closed-source\\\":[17],\\\"projects\\\":[18,69],\\\"proposing\\\":[20],\\\"to\\\":[21,38,70],\\\"move,\\\":[22],\\\"or\\\":[23],\\\"have\\\":[24],\\\"already\\\":[25],\\\"moved,\\\":[26],\\\"their\\\":[27],\\\"source\\\":[28],\\\"code\\\":[29],\\\"repositories\\\":[30],\\\"from\\\":[31],\\\"centralized\\\":[33],\\\"version\\\":[34,41],\\\"system\\\":[36,43],\\\"(CVCS)\\\":[37],\\\"decentralized\\\":[40],\\\"(DVCS).\\\":[44],\\\"In\\\":[45],\\\"this\\\":[46],\\\"paper\\\":[47],\\\"we\\\":[48],\\\"summarize\\\":[49],\\\"the\\\":[50,62,72],\\\"differences\\\":[51],\\\"between\\\":[52],\\\"CVCS\\\":[54],\\\"DVCS,\\\":[57],\\\"describe\\\":[59],\\\"some\\\":[60],\\\"rationales\\\":[63],\\\"perceived\\\":[65],\\\"benefits\\\":[66],\\\"offered\\\":[67],\\\"by\\\":[68],\\\"justify\\\":[71],\\\"transition.\\\":[73]}},\\\"S\\\":[{\\\"Ty\\\":0,\\\"U\\\":\\\"http://dx.doi.org/10.1109/CHASE.2009.5071408\\\"},{\\\"Ty\\\":0,\\\"U\\\":\\\"http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=5071408&contentType=Conference+Publications\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dl.acm.org/citation.cfm?id=1572211\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://ieeexplore.ieee.org/xpls/abs_all.jsp?arnumber=5071408\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://yadda.icm.edu.pl/yadda/element/bwmeta1.element.ieee-000005071408\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dblp.uni-trier.de/db/conf/icse/chase2009.html#AlwisS09\\\"},{\\\"Ty\\\":0,\\\"U\\\":\\\"http://ieeexplore.ieee.org/xpl/abstractReferences.jsp?reload=true&arnumber=5071408&contentType=Conference+Publications&sortType%3Dasc_p_Sequence%26filter%3DAND%28p_IS_Number%3A5071388%29\\\"},{\\\"Ty\\\":0,\\\"U\\\":\\\"http://people.cs.ubc.ca/~bsd/papers/chase-2009-dealwis.pdf\\\"}],\\\"VFN\\\":\\\"CooperativeandHumanAspectsofSoftwareEngineering\\\",\\\"VSN\\\":\\\"CHASE\\\",\\\"FP\\\":36,\\\"LP\\\":39,\\\"DOI\\\":\\\"10.1109/CHASE.2009.5071408\\\",\\\"CC\\\":{\\\"2144072824\\\":[\\\"IndeedthiswastheoriginalmotivationforthefirstrecordedDVCS,ReliableSoftware’sCodeCo-op,introducedin1997[7].\\\\u001bReliableSoftwareneededtocatertoteamsdevelopingacrossdistributedlocations,wherethelatencytoaccessacentralrepositorywasprohibitive[7].\\\"],\\\"116022312\\\":[\\\"AnewgenerationofVCS,calleddecentralizedVCSs(DVCS),haveemergedthataddresssomeofthelimitationsofcurrentcentralizedVCSs(CVCS),suchasCVS[1,5]andSubversion[4],tobettersupportdecentralizedworkflows.\\\\u001bThemostcommonly-usedversioncontrolsystem(VCS)usedtodayarecentralizedVCSs,astypifiedbyCVS[1,5]andSubversion[4].\\\"]},\\\"PR\\\":[1990244497,2122230059,1995935241,2296021021,349439569,2030791412,2142999443,2147397672,1488556252,2117696999,2068902213,1612213247,2082989244,2070849536,2155971005,2044337252,2130225015,1819759706,2604006439,2155952880],\\\"ANF\\\":[{\\\"FN\\\":\\\"Briande\\\",\\\"LN\\\":\\\"Alwis\\\",\\\"S\\\":1},{\\\"FN\\\":\\\"Jonathan\\\",\\\"LN\\\":\\\"Sillito\\\",\\\"S\\\":2}],\\\"BV\\\":\\\"Proceedingsofthe2009ICSEWorkshoponCooperativeandHumanAspectsonSoftwareEngineering\\\",\\\"BT\\\":\\\"p\\\",\\\"PB\\\":\\\"IEEE\\\"}\"},{\"logprob\":-19.553,\"Id\":2122676969,\"Ti\":\"whydonewcomersabandonopensourcesoftwareprojects\",\"Y\":2013,\"E\":\"{\\\"DN\\\":\\\"Whydonewcomersabandonopensourcesoftwareprojects\\\",\\\"IA\\\":{\\\"IndexLength\\\":161,\\\"InvertedIndex\\\":{\\\"Open\\\":[0],\\\"source\\\":[1],\\\"software\\\":[2],\\\"projects,\\\":[3],\\\"are\\\":[4,115],\\\"based\\\":[5],\\\"on\\\":[6,45],\\\"volunteers\\\":[7],\\\"collaboration\\\":[8],\\\"and\\\":[9,22,62,64,80,96,99,133],\\\"require\\\":[10],\\\"a\\\":[11,30,46,149],\\\"continuous\\\":[12],\\\"influx\\\":[13],\\\"of\\\":[14,39,43,58,85,109,130,137,143],\\\"newcomers\\\":[15,44,95,110,119],\\\"for\\\":[16],\\\"their\\\":[17,26],\\\"continuity.\\\":[18],\\\"Newcomers\\\":[19],\\\"face\\\":[20],\\\"difficulties\\\":[21],\\\"obstacles\\\":[23],\\\"when\\\":[24],\\\"starting\\\":[25],\\\"contributions,\\\":[27],\\\"resulting\\\":[28],\\\"in\\\":[29],\\\"low\\\":[31],\\\"retention\\\":[32],\\\"rate.\\\":[33],\\\"This\\\":[34],\\\"paper\\\":[35],\\\"presents\\\":[36],\\\"an\\\":[37],\\\"analysis\\\":[38],\\\"the\\\":[40,50,65,75,86,103,118,123,128,131,135,141,159],\\\"first\\\":[41],\\\"interactions\\\":[42],\\\"project,\\\":[47],\\\"checking\\\":[48],\\\"if\\\":[49],\\\"dropout\\\":[51],\\\"may\\\":[52],\\\"have\\\":[53,69],\\\"been\\\":[54],\\\"influenced\\\":[55,126],\\\"by\\\":[56,127,134],\\\"lack\\\":[57,142],\\\"answer,\\\":[59],\\\"answers\\\":[60,100,132],\\\"politeness\\\":[61],\\\"helpfulness,\\\":[63],\\\"answer\\\":[66,138,144],\\\"author.\\\":[67],\\\"We\\\":[68,90],\\\"collected\\\":[70],\\\"five\\\":[71],\\\"years\\\":[72],\\\"data\\\":[73],\\\"from\\\":[74],\\\"developers'\\\":[76,92],\\\"mailing\\\":[77],\\\"list\\\":[78],\\\"communication\\\":[79],\\\"issue\\\":[81],\\\"manager\\\":[82],\\\"(Jira)\\\":[83],\\\"discussions\\\":[84],\\\"Hadoop\\\":[87],\\\"Common\\\":[88],\\\"project.\\\":[89,160],\\\"observed\\\":[91],\\\"communication,\\\":[93],\\\"identifying\\\":[94],\\\"classifying\\\":[97],\\\"questions\\\":[98],\\\"content.\\\":[101],\\\"In\\\":[102],\\\"analyzed\\\":[104],\\\"period,\\\":[105],\\\"less\\\":[106],\\\"than\\\":[107],\\\"20%\\\":[108],\\\"became\\\":[111],\\\"long-term\\\":[112],\\\"contributors.\\\":[113],\\\"There\\\":[114],\\\"evidences\\\":[116],\\\"that\\\":[117,151],\\\"decision\\\":[120,154],\\\"to\\\":[121,155],\\\"abandon\\\":[122,158],\\\"project\\\":[124],\\\"was\\\":[125,145],\\\"authors\\\":[129],\\\"type\\\":[136],\\\"received.\\\":[139],\\\"However,\\\":[140],\\\"not\\\":[146],\\\"evidenced\\\":[147],\\\"as\\\":[148],\\\"factor\\\":[150],\\\"influences\\\":[152],\\\"newcomers'\\\":[153],\\\"remain\\\":[156],\\\"or\\\":[157]}},\\\"S\\\":[{\\\"Ty\\\":0,\\\"U\\\":\\\"http://dx.doi.org/10.1109/CHASE.2013.6614728\\\"},{\\\"Ty\\\":3,\\\"U\\\":\\\"https://www.researchgate.net/profile/Igor_Steinmacher/publication/236671212_Why_Do_Newcomers_Abandon_Open_Source_Software_Projects/links/00b7d518d286bd22be000000.pdf?origin=publication_list\\\"},{\\\"Ty\\\":3,\\\"U\\\":\\\"http://doi.ieeecomputersociety.org/10.1109/CHASE.2013.6614728\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://ieeexplore.ieee.org/lpdocs/epic03/wrapper.htm?arnumber=6614728\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"https://www.computer.org/web/csdl/index/-/csdl/proceedings/chase/2013/6290/00/06614728-abs.html\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dblp.uni-trier.de/db/conf/icse/chase2013.html#SteinmacherWCG13\\\"},{\\\"Ty\\\":0,\\\"U\\\":\\\"http://www.computer.org/web/csdl/index/-/csdl/proceedings/chase/2013/6290/00/06614728-abs.html\\\"}],\\\"VFN\\\":\\\"CooperativeandHumanAspectsofSoftwareEngineering\\\",\\\"VSN\\\":\\\"CHASE\\\",\\\"FP\\\":25,\\\"LP\\\":32,\\\"DOI\\\":\\\"10.1109/CHASE.2013.6614728\\\",\\\"PR\\\":[2123834217,2125889680,2057245786,2098879844,2102322333,2122704901,2139888022,2028561093,2123945507,2136421959,2157071571,2017003061,2120244029,1658908529,1972052832,2018174787,2107146981,2166838101,2123204546,1975782063],\\\"ANF\\\":[{\\\"FN\\\":\\\"Igor\\\",\\\"LN\\\":\\\"Steinmacher\\\",\\\"S\\\":1},{\\\"FN\\\":\\\"IgorScaliante\\\",\\\"LN\\\":\\\"Wiese\\\",\\\"S\\\":2},{\\\"FN\\\":\\\"AnaPaula\\\",\\\"LN\\\":\\\"Chaves\\\",\\\"S\\\":3},{\\\"FN\\\":\\\"MarcoAurélio\\\",\\\"LN\\\":\\\"Gerosa\\\",\\\"S\\\":4}],\\\"BV\\\":\\\"20136thInternationalWorkshoponCooperativeandHumanAspectsofSoftwareEngineering(CHASE)\\\",\\\"BT\\\":\\\"p\\\",\\\"PB\\\":\\\"IEEE\\\"}\"},{\"logprob\":-19.607,\"Id\":2139159781,\"Ti\":\"sarosaneclipsepluginfordistributedpartyprogramming\",\"Y\":2010,\"E\":\"{\\\"DN\\\":\\\"Saros:aneclipseplug-infordistributedpartyprogramming\\\",\\\"IA\\\":{\\\"IndexLength\\\":105,\\\"InvertedIndex\\\":{\\\"This\\\":[0],\\\"paper\\\":[1,57,90],\\\"describes\\\":[2],\\\"the\\\":[3,37],\\\"social\\\":[4],\\\"practice\\\":[5],\\\"of\\\":[6,14,36,55,62,66,71,95],\\\"distributed\\\":[7,19,67,74],\\\"party\\\":[8,75],\\\"programming\\\":[9,16],\\\"as\\\":[10],\\\"a\\\":[11,18,41,59,93],\\\"natural\\\":[12],\\\"extension\\\":[13],\\\"pair\\\":[15],\\\"in\\\":[17,101],\\\"context\\\":[20],\\\"with\\\":[21,92],\\\"two\\\":[22],\\\"or\\\":[23],\\\"more\\\":[24],\\\"software\\\":[25,42],\\\"developers\\\":[26],\\\"working\\\":[27],\\\"together.\\\":[28],\\\"To\\\":[29],\\\"this\\\":[30,45,56],\\\"end\\\":[31],\\\"we\\\":[32],\\\"provide\\\":[33],\\\"an\\\":[34],\\\"overview\\\":[35],\\\"Eclipse\\\":[38],\\\"plug-in\\\":[39],\\\"Saros,\\\":[40],\\\"implementation\\\":[43],\\\"supporting\\\":[44],\\\"practice,\\\":[46],\\\"and\\\":[47],\\\"explain\\\":[48],\\\"its\\\":[49],\\\"technical\\\":[50],\\\"architecture.\\\":[51],\\\"The\\\":[52,89],\\\"central\\\":[53],\\\"contribution\\\":[54],\\\"is\\\":[58,73,85],\\\"detailed\\\":[60],\\\"description\\\":[61],\\\"four\\\":[63],\\\"concrete\\\":[64],\\\"scenarios\\\":[65],\\\"collaboration\\\":[68],\\\"where\\\":[69],\\\"one\\\":[70],\\\"them\\\":[72],\\\"programming.\\\":[76],\\\"Furthermore\\\":[77],\\\"it\\\":[78],\\\"will\\\":[79],\\\"be\\\":[80],\\\"shown\\\":[81],\\\"how\\\":[82],\\\"each\\\":[83],\\\"scenario\\\":[84],\\\"supported\\\":[86],\\\"by\\\":[87],\\\"Saros.\\\":[88],\\\"closes\\\":[91],\\\"discussion\\\":[94],\\\"preliminary\\\":[96],\\\"findings\\\":[97],\\\"about\\\":[98],\\\"establishing\\\":[99],\\\"Saros\\\":[100],\\\"Open\\\":[102],\\\"Source\\\":[103],\\\"projects.\\\":[104]}},\\\"S\\\":[{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dl.acm.org/citation.cfm?id=1833319\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://dblp.uni-trier.de/db/conf/icse/chase2010.html#SalingerOBS10\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"http://portal.acm.org/citation.cfm?doid=1833310.1833319\\\"},{\\\"Ty\\\":1,\\\"U\\\":\\\"https://doi.acm.org/10.1145/1833310.1833319\\\"}],\\\"VFN\\\":\\\"CooperativeandHumanAspectsofSoftwareEngineering\\\",\\\"VSN\\\":\\\"CHASE\\\",\\\"FP\\\":48,\\\"LP\\\":55,\\\"DOI\\\":\\\"10.1145/1833310.1833319\\\",\\\"CC\\\":{\\\"2004985500\\\":[\\\"CoxandGreenberg[10]identifiedseveralimportantdesignprinciplesforsupportingthecollaborationofdistributedteamsviasoftware:“(1)Provideacommon,visuallysimilarenvironmentforallparticipants;(2)Providetimelyfeedbackofallactionswithintheworkspace;(3)Supportgesturinganddeicticreferences(pointinggesturesaccompaniedbyverbalcuessuchas‘here’or‘thisone’);(4)Supportworkspaceawareness(‘the...\\\\u001bbecausethereexistmanydifferentscenariosfortheuseofSaros,suchasforreviewsorknowledgetransfer(seesection5).Thedescriptionsoftheawarenessfeatures(seesection4)andthetechnicalimplementation(seesection3)showthatSarosaddressesthedesignprinciplesforsupportingcollaborationidentifiedbyCoxandGreenberg[10]andshouldhelptoovercomesomeofthehurdlesofdevelopersbeingdistributed.\\\"],\\\"1546877146\\\":[\\\"ThelattercampconsistsprimarilyofresearchprototypesandnotablyincludeCOPPER[27],TUKAN[39],Moomba[33],Sangam[22,28],andXPairtise[40].\\\"],\\\"2014795666\\\":[\\\"[21,31]),betterdiffusionofknowledgeandrespectivelybetterlearning(e.g.[5]),higherworksatisfaction(e.g.\\\"],\\\"166645439\\\":[\\\"ThelattercampconsistsprimarilyofresearchprototypesandnotablyincludeCOPPER[27],TUKAN[39],Moomba[33],Sangam[22,28],andXPairtise[40].\\\"],\\\"2072790834\\\":[\\\"ThelattercampconsistsprimarilyofresearchprototypesandnotablyincludeCOPPER[27],TUKAN[39],Moomba[33],Sangam[22,28],andXPairtise[40].\\\"],\\\"1507897335\\\":[\\\"“Thismeansthatbothmustviewacopyofthesamescreen[respectivelyfiles],andatleastoneofthemshouldhavethecapabilitytochangethecontents”[1].\\\\u001bTheresultsindicatethatDPPiscomparablewith“co-locatedorvirtualteams(withoutpairprogramming)inproductivityandquality”[1].\\\"],\\\"2102069726\\\":[\\\"ThedatagatheringcomponentissimilartoHackystat[25]orElectroCodeoGram[38]andcaptures“actualprocess”datainmediumgranularity,suchasnumberofconcurrenteditingevents,rolechangesbetweenobserveranddriver,etc.\\\"],\\\"1989814541\\\":[\\\"(2)TheconcurrencycontrollayerbasedontheJupiteralgorithm[30]isinchargeofsynchronizingtheactivitiesofusersinaSarossession.\\\"],\\\"2050187629\\\":[\\\"HerebyitbecomeseasierforausertoidentifythesectionsinaprojectwhicharecurrentlybeingviewedoreditedandcouldpotentiallybeusedtoshowonlyapartialviewofinterestoftheprojectasinEclipseMylyn[26].\\\"],\\\"2139838568\\\":[\\\"Tothisend,SarosusestheXMPPExtensionProtocolJingle[36]toestablishapeer-to-peerconnectionbetweensessionparticipantsandtransferlargebinaryobjectsoutsidetheXMPPband.\\\"],\\\"59243194\\\":[\\\"Justasa“mousetrail”canbeusedtoraisetheawarenessofafastmovingmousecursor[2],theadditionalcoloredtrailmakesiteasiertotrackthecontributionsofindividualauthors.\\\"],\\\"1505931955\\\":[\\\"Nawrockietal.[29]comparedtheproductivityofSbStosoloprogrammingandPP.Theyfounda20%overheadcomparedtosoloprogramming,whilepairprogramminghadanoverheadof50%.\\\"],\\\"1996958808\\\":[\\\"[44])toalloweachparticipanttowriteinreal-timeonhisorherlocaldocumentwhileremotemodificationsofotherparticipantsaremergedintothedocumentconcurrently.\\\"],\\\"2049329210\\\":[\\\"[21,31])andleadstofasterprogress(e.g.\\\\u001b[21,31]),betterdiffusionofknowledgeandrespectivelybetterlearning(e.g.\\\\u001b[13,31])andgreaterconfidenceregardingtheirownwork(e.g.\\\\u001b[31]).ButitalsoseemsthatPPcanleadtohigherprogrammingcostsandeffort(e.g.\\\\u001b[21,31]).AtthemomentitisnotwellunderstoodinwhichsituationsaninvestmentinPPwillpayofflaterinthedevelopmentprocessorwhichfactorsdeterminethesuccessofPP.\\\"],\\\"2129576856\\\":[\\\"Theyratherfindthatthepairnormallymovesthroughdifferentabstractionlevelstogether[6,8,18].\\\"],\\\"2028555835\\\":[\\\"ThelattercampconsistsprimarilyofresearchprototypesandnotablyincludeCOPPER[27],TUKAN[39],Moomba[33],Sangam[22,28],andXPairtise[40].\\\"],\\\"2011380188\\\":[\\\"...collaborationofdistributedteamsviasoftware:“(1)Provideacommon,visuallysimilarenvironmentforallparticipants;(2)Providetimelyfeedbackofallactionswithintheworkspace;(3)Supportgesturinganddeicticreferences(pointinggesturesaccompaniedbyverbalcuessuchas‘here’or‘thisone’);(4)Supportworkspaceawareness(‘theup-tothe-momentunderstandingofanotherperson’sinteractionwiththesharedspace’[19]”[...\\\\u001bApossiblereasonforthisbehaviourcouldbe“thatthelackofanaudiochannelinterferedwiththepairs’abilitytoeffectivelycollaborate”[20].\\\\u001bSimilarto[7]and[20]wefoundthatanaudiochannelisessentialtocompensateforthelostface-to-faceinteractionandissuperiortotext-basedchat.\\\\u001bTheseapplicationsreplicatethedesktopofauserandpermitremoteuserstocollaboratebytakingcontrolofmouseandkeyboard[20].\\\"],\\\"2113208984\\\":[\\\"Theyratherfindthatthepairnormallymovesthroughdifferentabstractionlevelstogether[6,8,18].\\\"],\\\"2094783406\\\":[\\\"Theyratherfindthatthepairnormallymovesthroughdifferentabstractionlevelstogether[6,8,18].\\\"],\\\"1998204765\\\":[\\\"[13,31])andgreaterconfidenceregardingtheirownwork(e.g.\\\"],\\\"1509296764\\\":[\\\"Similarconclusionsaredrawnbytwocasestudies(DPPvs.traditionalvirtualteams)withstudentsdonebyStottsetal.[42],usingdifferentCOTSsolutions(e.g.\\\"],\\\"2106201538\\\":[\\\"ThelattercampconsistsprimarilyofresearchprototypesandnotablyincludeCOPPER[27],TUKAN[39],Moomba[33],Sangam[22,28],andXPairtise[40].\\\"],\\\"1485628575\\\":[\\\"Asecondscenarioinsoftwaredevelopmentisperformingacodereview,whichisparticularlycommoninOpenSourcedevelopmentforassuringquality[41].\\\"],\\\"2171623515\\\":[\\\"Dewanetal.[11]havedoneanexploratory,qualitativestudyondistributedside-by-sideprogramming(DSbS),providingaclassificationofworkmodes:concurrentuncoupledprogramming,concurrentcoupledprogramming,pairprogramming,concurrentprogramming/browsingandconcurrentbrowsing.\\\"],\\\"1994838062\\\":[\\\"TheinitialimplementationofJupiterusedinSaroswastakenfromtheeditorACE[4]andsubsequentlytested,correctedandexpandedfortheuseinEclipse[47]andtosupportconcurrentundofollowingthealgorithmfrom[43].\\\"],\\\"2148071752\\\":[\\\"Therolesofdriverandobserveraredeliberatelyswitchedbetweenthepairperiodically”[45].\\\"],\\\"2161232704\\\":[\\\"InanexperimentconductedbyCanforaetal.[7]studentsusedVNC“tosharethedesktopandInstantMessagingofNetMeetingtosupportcommunication”(noaudiochannel).\\\\u001bSimilarto[7]and[20]wefoundthatanaudiochannelisessentialtocompensateforthelostface-to-faceinteractionandissuperiortotext-basedchat.\\\"],\\\"158951875\\\":[\\\"Side-by-sideprogrammingwasfirstdescribedbyCockburn[9]andcanbeexplainedinthefollowingway:“Sideby-sideprogrammingislikesoloprogramminginthatthetwoprogrammersinvolvedeachusetheirowncomputerandnormallyworkaloneona(sub)task.\\\\u001b[9])aswellasobservingsessionsinrealworkcontexts.\\\"],\\\"1995571854\\\":[\\\"...thecollaborationofdistributedteamsviasoftware:“(1)Provideacommon,visuallysimilarenvironmentforallparticipants;(2)Providetimelyfeedbackofallactionswithintheworkspace;(3)Supportgesturinganddeicticreferences(pointinggesturesaccompaniedbyverbalcuessuchas‘here’or‘thisone’);(4)Supportworkspaceawareness(‘theup-tothe-momentunderstandingofanotherperson’sinteractionwiththesharedspace’[19]”...\\\"],\\\"2136705655\\\":[\\\"Theresultsofthesestudiesareoftencontradictory[37],butthereisevidencethatinsomesettingsPPimprovescodequality(e.g.\\\"],\\\"1979549501\\\":[\\\"[24]),andwillalsoaddressrequestsforthisfeaturefromtheuserfeedbackreceivedbySaros.\\\"],\\\"1493688518\\\":[\\\"SeveralideasbehindSarosarebasedonthepracticeofpairprogramming(PP),popularisedbytheagiledevelopmentmethodologyeXtremeProgramming(XP)[3].\\\\u001bRolechangescanthenbeperformedbythehosttosupportthefrequentchangesinroleassignmentsuggestedbyXP[3].\\\"],\\\"2032843080\\\":[\\\"Thetermawarenessisusedtodenotetheunderstandingonepersonhasaboutthesharedenvironmentandactivitiesofothers[14].\\\"]},\\\"PR\\\":[2028555835,2120089691,1493688518,2143602702,2049329210,2123802152,2148071752,1972098965,2104844656,1992025345,2000304087,2171798428,2172173129,2293128725,2032843080,2096800101,2123230287,2171623515,1996958808,2011380188],\\\"ANF\\\":[{\\\"FN\\\":\\\"Stephan\\\",\\\"LN\\\":\\\"Salinger\\\",\\\"S\\\":1},{\\\"FN\\\":\\\"Christopher\\\",\\\"LN\\\":\\\"Oezbek\\\",\\\"S\\\":2},{\\\"FN\\\":\\\"Karl\\\",\\\"LN\\\":\\\"Beecher\\\",\\\"S\\\":3},{\\\"FN\\\":\\\"Julia\\\",\\\"LN\\\":\\\"Schenk\\\",\\\"S\\\":4}],\\\"BV\\\":\\\"Proceedingsofthe2010ICSEWorkshoponCooperativeandHumanAspectsofSoftwareEngineering\\\",\\\"BT\\\":\\\"p\\\",\\\"PB\\\":\\\"ACM\\\"}\"}]}";
        
        return r;
    }
}
