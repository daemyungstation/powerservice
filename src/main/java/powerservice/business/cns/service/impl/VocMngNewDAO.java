/*
 * (@)# VocDtlDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class VocMngNewDAO extends EgovAbstractMapper {

    public int getVocMngNewCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("VocMngNewMap.getVocMngNewCount", pmParam);
    }

    public List<Map<String, Object>> getVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return selectList("VocMngNewMap.getVocMngNewList", pmParam);
    }
    
    public int existsVocMngNewListData(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("VocMngNewMap.existsVocMngNewListData", pmParam);
    }

    public int insertVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return insert("VocMngNewMap.insertVocMngNewList", pmParam);
    }
    
    public int deleteVocMngNewList(Map<String, ?> pmParam) throws Exception {
        return update("VocMngNewMap.deleteVocMngNewList", pmParam);
    }
    
    public int updateVocMngNew(Map<String, ?> pmParam) throws Exception {
        return update("VocMngNewMap.updateVocMngNew", pmParam);
    }
    
    public int insertVocMngNewHist(Map<String, ?> pmParam) throws Exception {
        return insert("VocMngNewMap.insertVocMngNewHist", pmParam);
    }
    
    public int sendVocMngList(Map<String, ?> pmParam) throws Exception {
        return insert("VocMngNewMap.sendVocMngList", pmParam);
    }
    
    public int getVocMngNewHistCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("VocMngNewMap.getVocMngNewHistCount", pmParam);
    }

    public List<Map<String, Object>> getVocMngNewHistList(Map<String, ?> pmParam) throws Exception {
        return selectList("VocMngNewMap.getVocMngNewHistList", pmParam);
    }
}
