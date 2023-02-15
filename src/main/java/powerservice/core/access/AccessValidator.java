/*
 * (@)# AccessValidator.java
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2013/01/01
 *
 * Copyright (c) 2013 by Inwoo Tech Inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of Inwoo Tech Inc.
 * You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Inwoo Tech Inc.
 *
 */
package powerservice.core.access;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import powerservice.business.sys.service.AcsService;

/**
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @AUTHOR : (주)인우기술
 * @VERSION : 1.0
 * @DATE : 2013/01/01
 * @PROGRAMID : AccessValidator
 * @DESCRIPTION : 접근 관리
 */

@Component
public class AccessValidator {

    private final static int REFRESH_TIME_PGM_ACS_IP_LIST = 3600000;

    private static AtomicLong updateTimeOfPgmAcsIpList = new AtomicLong(Calendar.getInstance().getTimeInMillis() - REFRESH_TIME_PGM_ACS_IP_LIST);

    private static List<Map<String, Object>> goPgmAcsIpList = null;

    private final Logger LOGGER = LoggerFactory.getLogger(AccessValidator.class);


    @Autowired
    private ServletContext ctx;

    @Resource
    private AcsService acsService;

    private static String gsXmlFilePath = "/WEB-INF/config/inwoo/access/access-config.xml";
    private static Access m_objAccessBean = null; // 접근정보


    /**
     * 접근정보 환경파일 로딩
     * @throws Exception
     */
    @PostConstruct
    private void loadAccess() throws Exception {
        try {
            if (m_objAccessBean == null) {
                m_objAccessBean = (new AccessLoader()).load(ctx.getRealPath("") + gsXmlFilePath);
                
                System.out.println(">>> gsXmlFilePath : " + gsXmlFilePath);
                List<String>lstTmp = m_objAccessBean.getActionList();
                for (String path : lstTmp) {
                	System.out.println("actionListPath : " + path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 접근 사용자 IP 체크
     */
    /*
    public boolean validateUserIp(String userip) {
        boolean access = true;
        List<String> ipList = m_objAccessBean.getIpList();
        if (ipList == null) {
            return access;
        }

        try {
            if (Access.IP_ACCESS_MODE.equals(m_objAccessBean.getIpAccessMode())) {
                access = false;
                for (int i = 0; i < ipList.size(); i++) {
                    if (userip.startsWith(ipList.get(i))) {
                        access = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < ipList.size(); i++) {
                    if (userip.startsWith(ipList.get(i))) {
                        access = false;
                        break;
                    }
                }
            }

            return access;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
    */

    /**
     * 접근 IP 리스트 조회
     */
    public void loadPgmAcsIpList(boolean bFrc) {
        try {
            long nSaveTm = updateTimeOfPgmAcsIpList.longValue();
            long nCurrTm = Calendar.getInstance().getTimeInMillis();
            if ((nSaveTm < nCurrTm - REFRESH_TIME_PGM_ACS_IP_LIST) || bFrc) {
                if (updateTimeOfPgmAcsIpList.compareAndSet(nSaveTm, nCurrTm)) {
                    goPgmAcsIpList = acsService.getAcsIpList();
                    LOGGER.info("=== loadPgmAcsIpList start ===");
                    if (goPgmAcsIpList != null) {
                        for (int i = 0; i < goPgmAcsIpList.size(); i++) {
                            LOGGER.info("=== goPgmAcsIpList " + (i + 1) + " " + goPgmAcsIpList.get(i));
                        }
                    }
                    LOGGER.info("=== loadPgmAcsIpList end ===");
                }
            }
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    /**
     * 접근 사용자 IP 체크
     */
    public boolean validateUserIp(String psUserIp) throws Exception {
        // 접근 IP 리스트 메모리 로드
        loadPgmAcsIpList(false);

        boolean bAcs = false;
        try {
            if (goPgmAcsIpList != null) {
                for (int i = 0; i < goPgmAcsIpList.size(); i++) {
                    if (goPgmAcsIpList.get(i) == null ||
                        goPgmAcsIpList.get(i).get("prms_ip_addr") == null) {
                        continue;
                    }
                    String sPrmsIpAddr = (String) goPgmAcsIpList.get(i).get("prms_ip_addr");
                    int nPrmsIpAddrIdx = sPrmsIpAddr.indexOf("*");
                    if (nPrmsIpAddrIdx < 0) {
                        if (psUserIp.equals(sPrmsIpAddr)) {
                            bAcs = true;
                            break;
                        }
                    } else if (nPrmsIpAddrIdx <= psUserIp.length()) {
                         String sUserIpSub = psUserIp.substring(0, nPrmsIpAddrIdx);
                         String sPrmsIpAddrSub = sPrmsIpAddr.substring(0, nPrmsIpAddrIdx);
                        if (sUserIpSub.equals(sPrmsIpAddrSub)) {
                            bAcs = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        }
        return bAcs;
    }

    /**
     * 접근 사용자 액션 체크
     */
    public boolean validateUserAction(String useraction) {
        boolean access = false;

        List<String> actionList = m_objAccessBean.getActionList();
        if (actionList == null) {
            return access;
        }

        try {
            for (int i = 0; i < actionList.size(); i++) {
                String action = actionList.get(i);
                if (action == null || "".equals(action)) {
                    continue;
                }

                if (useraction.trim().equals(action)) {
                    return true;
                } else if (action.endsWith("*")) {
                    action = action.replace("*", "");
                    if (useraction.startsWith(action)) {
                        return true;
                    }
                }
            }

            return access;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
