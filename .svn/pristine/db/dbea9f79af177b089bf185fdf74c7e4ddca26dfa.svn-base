/*
 * (@)# TargetcustServiceImpl.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cam.service.ExtcMstTrgtService;
import powerservice.business.cam.service.TrgtCustService;
import powerservice.business.dlw.service.impl.DlwTrgtCustDAO;
import powerservice.business.onln.service.impl.ExtcTrgtDAO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대상고객리스트 추출고객 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID  Targetcust
 */

@Service
public class TrgtCustServiceImpl extends EgovAbstractServiceImpl implements TrgtCustService {

    @Resource
    public TrgtCustDAO trgtCustDAO;

    @Resource
    public TrgtCustAlctDAO trgtCustAlctDAO;

    @Resource
    public TrgtListDAO trgtListDAO;

    @Resource
    public DlwTrgtCustDAO dlwTrgtCustDAO;

    @Resource
    public ExtcMstTrgtService extcMstTrgtService;

    @Resource
    public ExtcTrgtDAO extcTrgtDAO;

    /**
     * 대상고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)trgtCustDAO.getTrgtCustCount(pmParam);
    }

    /**
     * 대상고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustList(Map<String, ?> pmParam) throws Exception {
        return trgtCustDAO.getTrgtCustList(pmParam);
    }

    /**
     * 대상고객 정보를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return 대상고객리스트 추출고객 리스트
     * @throws Exception
     */
    public Map<String, Object> getTrgtCust(Map<String, ?> pmParam) throws Exception {
        return trgtCustDAO.getTrgtCust(pmParam);
    }

    /**
     * 대상리스트 정보를 삭제한다.(고객)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCust(Map<String, Object> pmParam) throws Exception {
        int nCnt = (Integer)trgtCustAlctDAO.getTrgtCustAlctCount(pmParam);
        if (nCnt == 0) {
            trgtCustDAO.deleteTrgtCust(pmParam); // 대상고객 삭제
            String sCmpgTypCd  = (String)pmParam.get("cmpg_typ_cd");
            if(sCmpgTypCd != null){
                if(sCmpgTypCd.equals("2000")||sCmpgTypCd.equals("3000")){ //미납일경우
                    pmParam.put("updateOption", "cust_del");
                    extcMstTrgtService.updateUnpy(pmParam);
                }
            }
        }
        return nCnt;
    }

    /**
     * 대상 정보를 저장한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
/*    public int insertTrgtCustByDB(Map<String, ?> pmParam) throws Exception {
        return trgtCustDAO.insertTrgtCustByDB(pmParam);
    }*/

    /**
     * 대상 정보를 조회한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    /*public void searchTrgtCustByDB(Map<String, ?> pmParam) throws Exception {
        dlwTrgtCustDAO.getSqlSession().select("DlwTrgtCustMap.searchTrgtCustByDB", pmParam, new trgtRowHandler(pmParam));
    }

    protected class trgtRowHandler implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public trgtRowHandler(Map<String, ?> pmParam) {
            super();
            this.gmParam = new HashMap<String, Object>();
            if (pmParam != null) {
                Set<String> oKeySet = pmParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, pmParam.get(sKey));
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();
            String mem_no = StringUtils.defaultString((String)rslObj.get("mem_no"));
            String mem_nm = StringUtils.defaultString((String)rslObj.get("mem_nm"));
            String home_tel = StringUtils.defaultString((String)rslObj.get("home_tel"));
            String cell = StringUtils.defaultString((String)rslObj.get("cell"));
            String home_zip = StringUtils.defaultString((String)rslObj.get("home_zip"));
            String home_addr = StringUtils.defaultString((String)rslObj.get("home_addr"));
            String home_addr2 = StringUtils.defaultString((String)rslObj.get(""));
            String email = StringUtils.defaultString((String)rslObj.get("email"));
            String trgt_list_id = StringUtils.defaultString((String)gmParam.get("trgt_list_id"));
            String rgsr_id =  StringUtils.defaultString((String)gmParam.get("rgsr_id"));
            String amnd_id =  StringUtils.defaultString((String)gmParam.get("amnd_id"));
            String trgt_list_typ_cd  = StringUtils.defaultString((String)gmParam.get("trgt_list_typ_cd"));
            String cntr_cd  = StringUtils.defaultString((String)gmParam.get("cntr_cd"));

            rowData.put("mem_no",           mem_no);
            rowData.put("mem_nm",           mem_nm);
            rowData.put("home_tel",         home_tel);
            rowData.put("cell",             cell);
            rowData.put("home_zip",         home_zip);
            rowData.put("home_addr",        home_addr);
            rowData.put("home_addr2",       home_addr2);
            rowData.put("email",            email);
            rowData.put("trgt_list_id",     trgt_list_id);
            rowData.put("rgsr_id",          rgsr_id);
            rowData.put("amnd_id",          amnd_id);
            rowData.put("cntr_cd",          cntr_cd);
            rowData.put("trgt_list_typ_cd",       trgt_list_typ_cd);

            trgtCustDAO.getSqlSession().insert("TrgtCustMap.insertTrgtCustByDB", rowData);
        }
    }*/

    /**
     * 대상리스트 정보를 삭제한다.(소분류리스트)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
   /* public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception {
        // int cnt = (Integer)selectOne("CampaignassignMap.getCampaignAssignCount", param); // 캠페인 할당 체크
        // if (cnt == 0) {
        trgtCustDAO.deleteTrgtCustByTrgtList(pmParam); // 대상고객 삭제
        // }
        // return cnt;

        return 0;
    }*/

    /**
     * 옵션에 따른 대상리스트 정보를 삭제한다.(엑셀 업로드 시 사용)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCustByExcel(Map<String, Object> pmParam) throws Exception {
        trgtCustDAO.deleteTrgtCustByExcel(pmParam); // 대상고객 삭제

        return 0;
    }


    /**
     * 대상고객 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 이력 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtCustHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)trgtCustDAO.getTrgtCustHstrCount(pmParam);
    }

    /**
     * 대상고객 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustHstrList(Map<String, ?> pmParam) throws Exception {
        return trgtCustDAO.getTrgtCustHstrList(pmParam);
    }

}
