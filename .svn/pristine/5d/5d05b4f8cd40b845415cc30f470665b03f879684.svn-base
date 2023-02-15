/*
 * (@)# DlwDeptServiceImpl.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwDeptService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwDept
 */
@Service
public class DlwDeptServiceImpl extends EgovAbstractServiceImpl implements DlwDeptService {

    @Resource
    public DlwDeptDAO dlwDeptDAO;

    /**
     * 부서 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwDeptCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwDeptDAO.getDlwDeptCount(pmParam);
    }

    /**
     * 부서 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwDeptList(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwDeptList(pmParam);
    }

    /*자료권한  전체 권한 미적용 */
    public List<Map<String, Object>> getDlwDeptList001_mi(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwDeptList001_mi(pmParam);
    }

    /*권한미사용자리스트*/
    public List<Map<String, Object>> getDlwreportDeptList001_mi(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwreportDeptList001_mi(pmParam);
    }

    /*권한그룹별 조회 항목 설정*/
    public List<Map<String, Object>> getDlwreportDeptListdll(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwreportDeptListdll(pmParam);
    }



    /*자료권한그룹조회 */
    public List<Map<String, Object>> getDlwDeptgrpList003(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwDeptgrpList003(pmParam);
    }

    /*종합현황-권한별 사용자관리 */
    public List<Map<String, Object>> getDlwreportDeptgrpList(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwreportDeptgrpList(pmParam);
    }


    /*자료권한  전체 권한 적용 */
    public List<Map<String, Object>> getDlwDeptList001(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwDeptList001(pmParam);
    }

    /*권한  전체 권한 적용 */
    public List<Map<String, Object>> getDlwreportDeptList001(Map<String, ?> pmParam) throws Exception {
        return dlwDeptDAO.getDlwreportDeptList001(pmParam);
    }


    public void deptinsert(DataSetMap srchInDs,String data_auth, String grp_data_auth) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();

         if (srchInDs != null && srchInDs.size() > 0) {


             for (int i=0; i<srchInDs.size(); ++i) {
                  //세션

                 hmParam =(Map<String, Object>) srchInDs.get(i);

                 ParamUtils.addSaveParam(hmParam);

                 hmParam.put("upd_man", hmParam.get("rgsr_id"));
                 hmParam.put("data_auth", data_auth);
                 hmParam.put("grp_data_auth", grp_data_auth);
               //  CommonUtils.printLog(hmParam);
                 dlwDeptDAO.deptinsert(hmParam);
             }

         }

    }

    public void reportdeptinsert(DataSetMap srchInDs,String reportgrpcd) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();

         if (srchInDs != null && srchInDs.size() > 0) {


             for (int i=0; i<srchInDs.size(); ++i) {
                  //세션

                 hmParam =(Map<String, Object>) srchInDs.get(i);

                 ParamUtils.addSaveParam(hmParam);

                 hmParam.put("upd_man", hmParam.get("rgsr_id"));
                 hmParam.put("reportgrpcd", reportgrpcd);
                 dlwDeptDAO.reportdeptinsert(hmParam);
             }

         }

    }


    public void authgrpcdinsert(DataSetMap srchInDs,String authgrpcd) throws Exception {

        Map<String, Object> hmParam = new HashMap<String, Object>();

         if (srchInDs != null && srchInDs.size() > 0) {


             for (int i=0; i<srchInDs.size(); ++i) {
                  //세션

                 hmParam =(Map<String, Object>) srchInDs.get(i);

                 ParamUtils.addSaveParam(hmParam);
                 hmParam.put("reg_man", hmParam.get("rgsr_id"));
                 hmParam.put("authgrpcd", authgrpcd);
                 CommonUtils.printLog(hmParam);

                 int invCnt = dlwDeptDAO.authgrpcdcnt(hmParam);//  해당 데이터 있는지 확인

                 if ("1".equals(hmParam.get("chk"))) {
                         if  (invCnt < 1) {
                             dlwDeptDAO.authgrpcdinsert(hmParam);
                             //dlwDeptDAO.authgrpcddelete(hmParam);//  삭제 후 인선트
                         }
                   } else {
                       if  (invCnt > 0) {
                           dlwDeptDAO.authgrpcddelete(hmParam);//  삭제
                       }
                   }


             }

         }

    }


    public void dept_grp_insert(Map<String, ?> pmParam) throws Exception {
                 dlwDeptDAO.dept_grp_insert(pmParam);
        }


    public void report_grp_insert(Map<String, ?> pmParam) throws Exception {
        dlwDeptDAO.report_grp_insert(pmParam);
    }


}
