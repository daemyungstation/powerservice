/**
 * @(#)LicenseLoader.java
 *
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 */
package powerservice.core.license;

import java.text.ParseException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import powerservice.core.util.xml.ParseDOM;
import powerservice.core.util.xml.ParseDOMException;

/**
 * 라이선스 정보 로딩 및 생성
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author (주)인우기술
 * @version 1.0
 * @date 2015/04/01
 */
public class LicenseLoader extends ParseDOM {
    // -- constructor
    // ---------------------------------------------------------

    /**
     * License XML 정보 생성/ 로딩을 위한 객체 초기화
     * @throws Exception
     */
    public LicenseLoader() throws Exception {
        super();
    }

    // -- loading
    // -------------------------------------------------------------

    /**
     * License XML 파일을 읽어서, License 객체를 설정함
     *
     * @param psLicenseFile
     *            License 파일위치(경로포함)
     * @throws Exception
     * @throws ParseDOMException
     */
    public License load(String psLicenseFile) throws Exception {
        License oLicense = null;

        // 라이선스 파일 로딩
        try {
            super.loading(psLicenseFile);
        } catch (ParseDOMException exception) {
            throw new LicenseViolationException("'" + psLicenseFile + "' 라이선스 파일 로딩 실패");
        }

        // 메모리에 상주된 License 정보로부터 License 객체 생성
        try {
            NodeList oLicenseNodeList = super.getNodeList("INWOO-LICENSE/PRODUCT");
            if (oLicenseNodeList == null || oLicenseNodeList.getLength() < 1) {
                throw new LicenseViolationException("라이선스 정보 없음");
            }

            Node oLicenseNode        = null;
            NamedNodeMap oAttributes = null; // 속성정보

            Node oProductNode       = null; // 제품
            Node oVersionNode       = null; // 버전
            Node oSiteNode          = null; // 설치장소
            Node oIssuanceNode      = null; // 제품발급일
            Node oExpirationNode    = null; // 제품만료일
            Node oCenterNode        = null; // 센터수
            Node oUserNode          = null; // 사용자수
            Node oStaffNode         = null; // 직원수
            Node oAdminNode         = null; // 관리자수
            Node oIbModuleYnNode    = null; // IB 모듈 여부
            Node oObModuleYnNode    = null; // OB 모듈 여부
            Node oKmsModuleYnNode   = null; // KMS 모듈 여부
            Node oStafModuleYnNode  = null; // 2차처리 모듈 여부
            Node oMoModuleYnNode    = null; // MO 모듈 여부
            Node oWalbModuleYnNode  = null; // BOARD 모듈 여부
            Node oIpAddrNode        = null; // 설치 IP
            Node oKeyNode           = null; // 바로위의 정보들을 암호화한 값

            // for (int idx = 0; idx < oLicenseNodeList.getLength(); idx++) {
            // ProeductLicense 객체정보 추출
            oLicenseNode = oLicenseNodeList.item(0);

            // 해당 Product에 대한 속성추출
            oAttributes = oLicenseNode.getAttributes();
            if (oAttributes.getLength() != License.KEY_COUNT) {
                throw new LicenseViolationException("라이선스 정보 불일치");
            }

            oProductNode       = oAttributes.getNamedItem("LICENSE");
            oVersionNode       = oAttributes.getNamedItem("VERSION");
            oSiteNode          = oAttributes.getNamedItem("SITE");
            oIssuanceNode      = oAttributes.getNamedItem("ISSUANCE");
            oExpirationNode    = oAttributes.getNamedItem("EXPIRATION");
            oCenterNode        = oAttributes.getNamedItem("CENTER");
            oUserNode          = oAttributes.getNamedItem("USER");
            oStaffNode         = oAttributes.getNamedItem("STAFF");
            oAdminNode         = oAttributes.getNamedItem("ADMIN");
            oIbModuleYnNode    = oAttributes.getNamedItem("IB-MODULE");
            oObModuleYnNode    = oAttributes.getNamedItem("OB-MODULE");
            oKmsModuleYnNode   = oAttributes.getNamedItem("KMS-MODULE");
            oStafModuleYnNode  = oAttributes.getNamedItem("STAF-MODULE");
            oMoModuleYnNode    = oAttributes.getNamedItem("MO-MODULE");
            oWalbModuleYnNode  = oAttributes.getNamedItem("WALB-MODULE");
            oIpAddrNode        = oAttributes.getNamedItem("IP");
            oKeyNode           = oAttributes.getNamedItem("KEY");

            if (oProductNode != null && oVersionNode != null && oSiteNode != null && oIssuanceNode != null && oExpirationNode != null
                    && oCenterNode != null && oUserNode != null && oStaffNode != null && oAdminNode != null && oIbModuleYnNode != null
                    && oObModuleYnNode != null && oKmsModuleYnNode != null && oStafModuleYnNode != null && oMoModuleYnNode != null && oWalbModuleYnNode != null
                    && oIpAddrNode != null && oKeyNode != null) {
                oLicense = new License(getNodeValue(oProductNode), getNodeValue(oVersionNode), getNodeValue(oSiteNode), getNodeValue(oIssuanceNode), getNodeValue(oExpirationNode),
                        getNodeValue(oCenterNode), getNodeValue(oUserNode), getNodeValue(oStaffNode), getNodeValue(oAdminNode), getNodeValue(oIbModuleYnNode),
                        getNodeValue(oObModuleYnNode), getNodeValue(oKmsModuleYnNode), getNodeValue(oStafModuleYnNode), getNodeValue(oMoModuleYnNode), getNodeValue(oWalbModuleYnNode),
                        getNodeValue(oIpAddrNode), getNodeValue(oKeyNode));
            } else {
                throw new LicenseViolationException("라이선스 속성 정보 부족");
            }
            // }
        } catch (ParseDOMException exception) {
            throw new LicenseViolationException("라이선스 정보 추출 실패");
        } catch (ParseException exception) {
            throw new LicenseViolationException("라이선스 정보 형식 오류");
        } finally {
            super.destroy();
        }

        return oLicense;
    }
}
