/**
 * @(#)AccessLoader.java
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.core.access;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import powerservice.core.util.xml.ParseDOM;
import powerservice.core.util.xml.ParseDOMException;

/**
 * 접근정보 로딩 및 생성
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author (주)인우기술
 * @version 1.0
 * @date 2013/04/01
 */
public class AccessLoader extends ParseDOM {
    // -- constructor
    // ---------------------------------------------------------

    /**
     * Access XML 정보 생성/ 로딩을 위한 객체 초기화
     * @throws Exception
     */
    public AccessLoader() throws Exception {
        super();
    }

    // -- loading
    // -------------------------------------------------------------

    /**
     * Access XML 파일을 읽어서, Access 객체를 설정함
     *
     * @param szAccessFilename
     *            Access 파일위치(경로포함)
     * @throws Exception
     * @throws ParseDOMException
     */
    public Access load(String szAccessFilename) throws Exception {
        Access objAccess = null;
        List<String> actionList = new ArrayList<String>();
        List<String> ipList = new ArrayList<String>();
        String ipMode = null;

        // 접근정보 파일 로딩
        try {
            super.loading(szAccessFilename);
        } catch (ParseDOMException exception) {
            throw new Exception("'" + szAccessFilename + "' 접근정보 파일 로딩 실패");
        }

        // 메모리에 상주된 접근정보로 부터 Access 객체 생성
        try {
            // Action 리스트 정보 추출
            NodeList actionNodeList = super.getNodeList("config/actionlist/action");
            if (actionNodeList == null) {
                throw new Exception("Access Action 리스트 정보 없음");
            }
            for (int idx = 0; idx < actionNodeList.getLength(); idx++) {
                Node actionNode = actionNodeList.item(idx);

                NamedNodeMap actionAttributeList = actionNode.getAttributes();
                if (actionAttributeList.getLength() != 1) {
                    throw new Exception("Access Action 속성 정보 부족");
                }

                Node pathAttribute = actionAttributeList.getNamedItem("path");
                if (pathAttribute != null) {
                    actionList.add(getNodeValue(pathAttribute));
                } else {
                    throw new Exception("Access Action 속성 정보 없음");
                }
            }

            // IP 접근모드 정보 추출
            Node ipListNode = super.getNode("config/iplist");
            if (ipListNode == null) {
                throw new Exception("Access IP 접근모드 정보 없음");
            }
            NamedNodeMap ipListAttributeList = ipListNode.getAttributes();
            if (ipListAttributeList.getLength() != 1) {
                throw new Exception("Access IP 접근모드 속성 정보 부족");
            }
            Node modeAttribute = ipListAttributeList.getNamedItem("mode");
            if (modeAttribute != null) {
                ipMode = getNodeValue(modeAttribute);
            } else {
                throw new Exception("Access IP 접근모드 속성 정보 없음");
            }

            // IP 리스트 정보 추출
            NodeList ipNodeList = super.getNodeList("config/iplist/ip");
            if (ipNodeList == null) {
                throw new Exception("Access IP 리스트 정보 없음");
            }
            for (int idx = 0; idx < ipNodeList.getLength(); idx++) {
                Node ipNode = ipNodeList.item(idx);
                if (ipNode == null) {
                    throw new Exception("Access IP 정보 없음");
                }
                String ip = getNodeValue(ipNode);
                if (ip != null) {
                    int index = ip.indexOf('*');
                    if(index >= 0) {
                        ip = ip.substring(0, index);
                    }
                    ipList.add(ip);
                } else {
                    throw new Exception("Access IP 정보 없음");
                }
            }

            objAccess = new Access(actionList, ipList, ipMode);
        } catch (ParseDOMException exception) {
            throw new Exception("접근정보 파일 추출 실패");
        } catch (ParseException exception) {
            throw new Exception("접근정보 파일 형식 오류");
        } finally {
            super.destroy();
        }

        return objAccess;
    }
}
