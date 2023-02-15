/*
 * (@)# VocDtlService.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.cns.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.VocMngNewService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * VOC 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VocDtl
 */
@Service
public class VocMngNewServiceImpl extends EgovAbstractServiceImpl implements VocMngNewService {

    @Resource
    public VocMngNewDAO vocMngNewDAO;
    
    public int getVocMngNewCount(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.getVocMngNewCount(pmParam);
    }

    public List<Map<String, Object>> getVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.getVocMngNewList(pmParam);
    }
    
    public int existsVocMngNewListData(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.existsVocMngNewListData(pmParam);
    }

    public int insertVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.insertVocMngNewList(pmParam);
    }
    
    public int deleteVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.deleteVocMngNewList(pmParam);
    }
    
    public int updateVocMngNew(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.updateVocMngNew(pmParam);
    }
    
    public int insertVocMngNewHist(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.insertVocMngNewHist(pmParam);
    }
    
    public int sendVocMngList(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.sendVocMngList(pmParam);
    }
    
    public int getVocMngNewHistCount(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.getVocMngNewHistCount(pmParam);
    }

    public List<Map<String, Object>> getVocMngNewHistList(Map<String, ?> pmParam) throws Exception {
        return vocMngNewDAO.getVocMngNewHistList(pmParam);
    }
}
