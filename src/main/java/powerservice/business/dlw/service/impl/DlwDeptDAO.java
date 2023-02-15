/*
 * (@)# DlwDeptDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
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
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.stereotype.Repository;

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 부서 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwDept
 */
@Repository
public class DlwDeptDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 부서정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 부서 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwDeptCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwDeptMap.getDlwDeptCount", pmParam);
    }

    /**
     * 부서 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwDeptList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwDeptList", pmParam);
    }

    /*자료권한 전체 미적용자*/
    public List<Map<String, Object>> getDlwDeptList001_mi(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwDeptList001_mi", pmParam);
    }


    /*권한미사용자리스트*/
    public List<Map<String, Object>> getDlwreportDeptList001_mi(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwreportDeptList001_mi", pmParam);
    }


    /*권한그룹별 조회 항목 설정*/
    public List<Map<String, Object>> getDlwreportDeptListdll(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwreportDeptListdll", pmParam);
    }

    /**/
    public List<Map<String, Object>> getDlwDeptgrpList003(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwDeptgrpList003", pmParam);
    }


    /*종합현황-권한별 사용자관리리스트*/
    public List<Map<String, Object>> getDlwreportDeptgrpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwreportDeptgrpList", pmParam);
    }



    /*자료권한 전체 적용자*/
    public List<Map<String, Object>> getDlwDeptList001(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwDeptList001", pmParam);
    }

    /*권한사용자리스트*/
    public List<Map<String, Object>> getDlwreportDeptList001(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwDeptMap.getDlwreportDeptList001", pmParam);
    }

    public int deptinsert(Map<String, ?> pmParam) throws Exception {

        return update("DlwDeptMap.deptinsert", pmParam);
    }

    public int reportdeptinsert(Map<String, ?> pmParam) throws Exception {

        return update("DlwDeptMap.reportdeptinsert", pmParam);
    }

    public int authgrpcdinsert(Map<String, ?> pmParam) throws Exception {

        return update("DlwDeptMap.authgrpcdinsert", pmParam);
    }





    public int authgrpcdcnt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwDeptMap.authgrpcdcnt", pmParam);
    }


    public int authgrpcddelete(Map<String, ?> pmParam) throws Exception {

        return update("DlwDeptMap.authgrpcddelete", pmParam);
    }




    public int dept_grp_insert(Map<String, ?> pmParam) throws Exception {
        CommonUtils.printLog(pmParam);
        if ("1".equals(pmParam.get("gubun"))) {
          return update("DlwDeptMap.dept_grp_insert", pmParam);                     /////신규
        } else {
          return update("DlwDeptMap.dept_grp_update", pmParam);                     //// 업데이트
        }
    }

    public int report_grp_insert(Map<String, ?> pmParam) throws Exception {
       CommonUtils.printLog(pmParam);
        if ("1".equals(pmParam.get("gubun"))) {
          return update("DlwDeptMap.report_grp_insert", pmParam);                     /////신규
        } else {
          return update("DlwDeptMap.report_grp_update", pmParam);                     //// 업데이트
        }
    }

}
