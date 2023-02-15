/*
 * (@)# DlwProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 입금마감현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author BIJ
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwPayCloseDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상품정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /**
     * 입금마감 현황 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectPayCloseList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwPayCloseMap.selectPayCloseList", pmParam);
    }
//

    /**
     * 전체 마감 MST 데이타 수동생성
     *
     * @param pmParam
     * @throws Exception
     */
    public void execPayDlProcTotMst(Map<String, Object> pmParam) throws Exception {
//        pmParam.put("outsqlcd", "");
//        pmParam.put("outsqlmg", "");
        update("DlwPayCloseMap.execPayDlProcTotMst", pmParam);

/*
        if(!pmParam.get("outsqlcd").equals("00")){
            //Exception
            throw new Exception (String.valueOf(pmParam.get("outSqlcd")) +":"+ String.valueOf(pmParam.get("outSqlmg")));
        }
*/
    }



    /**
     * 전체 마감 DTL 데이타 수동생성
     *
     * @param pmParam
     * @throws Exception
     */
    public void execPayDlProcTotDtl(Map<String, Object> pmParam) throws Exception {
//        pmParam.put("outsqlcd", "");
//        pmParam.put("outsqlmg", "");
        update("DlwPayCloseMap.execPayDlProcTotDtl", pmParam);

//        System.out.println("pmParam.outsqlcd==>"+pmParam.get("outsqlcd"));
//        System.out.println("pmParam.outsqlmg==>"+pmParam.get("outsqlmg"));

/*
        if(!pmParam.get("outsqlcd").equals("00")){
            //Exception
            throw new Exception (String.valueOf(pmParam.get("outSqlcd")) +":"+ String.valueOf(pmParam.get("outSqlmg")));
        }
*/
    }


    /**
     * 전체 마감 해지 처리 MST
     *
     * @param pmParam
     * @throws Exception
     */
    public void execPayDlTotCancelMstProc(Map<String, Object> pmParam) throws Exception {
        delete("DlwPayCloseMap.execPayDlTotCancelMstProc", pmParam);
    }


    /**
     * 전체 마감 해지 처리 DTL
     *
     * @param pmParam
     * @throws Exception
     */
    public void execPayDlTotCancelDtlProc(Map<String, Object> pmParam) throws Exception {
        delete("DlwPayCloseMap.execPayDlTotCancelDtlProc", pmParam);
    }


    /**
     * 기준년월 중복 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectDupChk(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwPayCloseMap.selectDupChk", pmParam);
    }
}
