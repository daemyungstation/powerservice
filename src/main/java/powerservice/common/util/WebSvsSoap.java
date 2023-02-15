/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package powerservice.common.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WebSvsSoap
{

    public WebSvsSoap()
    {
        METHOD_NAME = "GetRoadAddress";
        NAMESPACE = "http://websvs.e-ncom.co.kr/common/";
        SOAP_ACTION = (new StringBuilder(String.valueOf(NAMESPACE))).append(METHOD_NAME).toString();
        URL = "http://websvs.e-ncom.co.kr/common/cmm.asmx";
    }

    public synchronized Map<String, Object> inqWebSvs(Map<String, Object> hmParam)
    {	//System.out.println("hmParam == : "+hmParam);
        List<Map<String, Object>> addrList = new ArrayList<Map<String,Object>>();
        String rtn = "";
        String result = "";
        String addr = "";
        String sido = (String)hmParam.get("sido");
        String gugun = (String)hmParam.get("gugun");
        String srch = (String)hmParam.get("srch");
        String pageNo = String.valueOf(hmParam.get("pageNo"));
        if(pageNo == null || pageNo.equals("")) {
            pageNo = "1";
        }
        hmParam.put("curPgNum", pageNo);
        if(sido != null && gugun != null && srch != null)
        {
            try
            {
                SoapObject sobj = new SoapObject(NAMESPACE, METHOD_NAME);
                sobj.addProperty("sido", sido);
                sobj.addProperty("gugun", gugun);
                sobj.addProperty("srch", srch);
                sobj.addProperty("pageNo", pageNo);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(110);
                envelope.dotNet = true;
                envelope.encodingStyle = "http://www.w3.org/2001/XMLSchema-instance";
                envelope.implicitTypes = true;
                envelope.setOutputSoapObject(sobj);
                HttpTransportSE hpHttpTransport = new HttpTransportSE(URL);
                hpHttpTransport.debug = true;
                hpHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"euc-kr\"?>");
                hpHttpTransport.call(SOAP_ACTION, envelope);
                rtn = envelope.getResponse().toString();
                //System.out.println("rtn : " + rtn);
                result = rtn.substring(0, rtn.indexOf("-"));
                //System.out.println("result : " + rtn);
                addr = rtn.substring(rtn.indexOf("-") + 1).replaceAll("\"", "'");
                //System.out.println("addr : " + addr);
                InputSource is = new InputSource(new StringReader(addr));
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                //System.out.println("document : " + document);
                XPath xpath = XPathFactory.newInstance().newXPath();
                Node totPgNum = (Node)xpath.compile("//road_juso/totalPage").evaluate(document, XPathConstants.NODE);
                //System.out.println("totPgNum : " + totPgNum.getTextContent()); <-- 주소가 없을때 NullPointerException
                if(totPgNum != null)
                {
                    hmParam.put("totPgNum", totPgNum.getTextContent());
                    hmParam.put("pageNo", pageNo);
                    String expression = "//*/list";
                    NodeList cols = (NodeList)xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
                    //System.out.println("cols.getLength() == : "+cols.getLength());
                    for(int idx = 0; idx < cols.getLength(); idx++)
                    {
                        Map<String, Object> row = new HashMap<String, Object>();
                        String id = cols.item(idx).getAttributes().item(0).getTextContent();
                        row.put("seq", id);
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/road_m").toString();
                        String road_m = xpath.compile(expression).evaluate(document);
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/road_d").toString();
                        String road_d = xpath.compile(expression).evaluate(document);
                        row.put("address", (new StringBuilder(String.valueOf(road_m))).append(road_d != null && !"".equals(road_d) ? (new StringBuilder(" ")).append(road_d).toString() : "").toString());
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/jibun_m").toString();
                        String jibun_m = xpath.compile(expression).evaluate(document);
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/jibun_d").toString();
                        String jibun_d = xpath.compile(expression).evaluate(document);
                        row.put("jibunAddr", (new StringBuilder(String.valueOf(jibun_m))).append(jibun_d != null && !"".equals(jibun_d) ? (new StringBuilder(" ")).append(jibun_d).toString() : "").toString());
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/post").toString();
                        String post = xpath.compile(expression).evaluate(document);
                        row.put("zipOldL6", post);
                        expression = (new StringBuilder("//*[@id=")).append(id).append("]/post_new").toString();
                        String postNew = xpath.compile(expression).evaluate(document);
                        row.put("zip", postNew);
                        addrList.add(row);
                    }

                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                result = "false";
                addr = "";
            }
        }
        //System.out.println("addrList == : "+addrList);
        hmParam.put("lists", addrList);
        hmParam.put("result", result);
        return hmParam;
    }

    String METHOD_NAME;
    String NAMESPACE;
    String SOAP_ACTION;
    String URL;
}
