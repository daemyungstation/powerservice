/*
 * (@)# EmilSndgHstrServiceImpl.java
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;

import powerservice.business.cam.service.SubTrgtListService;
import powerservice.business.dlw.service.impl.DlwTrgtCustDAO;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.excel.ExcelValidator;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 서브타겟 리스트 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/05/10
 * @프로그램ID SubTrgtList
 */

@Service
public class SubTrgtListServiceImpl extends EgovAbstractServiceImpl implements SubTrgtListService {

    @Resource
    public SubTrgtListDAO subTrgtListDAO;

    @Resource
    public DlwTrgtCustDAO dlwTrgtCustDAO;

    @Resource
    public DfctDataDAO dfctDataDAO;

    @Resource
    public ScrtDAO scrtDAO;

    @Resource
    public TrgtListDAO trgtListDAO;

    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;



    /**
     * 서브타겟 리스트를 검색한다.
     *
     * @pmParam paramHash 검색 조건
     * @return subTrgt 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getSubTrgtList(Map<String, ?> pmParam) throws Exception {
        return subTrgtListDAO.getSubTrgtList(pmParam);
    }

    /**
     * 서브타겟 리스트 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트 검색 수
     * @throws Exception
     */
    public int getSubTrgtListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)subTrgtListDAO.getSubTrgtListCount(pmParam);
    }

    /**
     * 신규가 아닌 추가 서브타겟 리스트를 등록한다
     * 임동진 20190107
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public String insertSubTrgtList_Add(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception {
    	String result = "";
    	int nCnt = 0;
    	if(listInDataSet.size() != 0){
    		for(int i = 0 ; listInDataSet.size() > i ; i++){
                Map hmParam = listInDataSet.get(i);

                hmParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
                hmParam.put("sub_trgt_list_id", pmParam.get("sub_trgt_list_id"));

                ParamUtils.addCenterParam(hmParam);
                ParamUtils.addSaveParam(hmParam);

                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                System.out.println(hmParam);
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

                //서브 대상 회원 등록
                nCnt = subTrgtListDAO.insertTrgtCustByDB(hmParam);

    		}
    	}

        //추출 갯수  및 오류 갯수를 입력한다.
        pmParam.put("updateOption", "extract");
        subTrgtListDAO.updateSubTrgtList(pmParam);

        //추출갯수 입력한다
        trgtListDAO.updateTrgtList(pmParam);

    	return result;
    }

    /**
     * 서브타겟 리스트를 등록한다.(ajax)
     *
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public String insertSubTrgtList(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception {
        String sKey = "";

        int nResult = subTrgtListDAO.insertSubTrgtList(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("sub_trgt_list_id");
            pmParam.put("sub_trgt_list_id",sKey);
            if (pmParam.get("trgt_list_extc_typ_cd").toString().equals("20")){ // DB추출
            	/*미납데이터 대상자 여부 확인*/
                if(listInDataSet.size() != 0){
                	/*미납데이터 대상자 loof*/
                    for(int i = 0 ; listInDataSet.size() > i ; i++){
                        Map hmParam = listInDataSet.get(i);

                        ParamUtils.addCenterParam(hmParam);
                        ParamUtils.addSaveParam(hmParam);

                        hmParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
                        hmParam.put("sub_trgt_list_id", sKey);
                        int nCnt = 0;

                        //서브 대상 회원 등록
                        nCnt = subTrgtListDAO.insertTrgtCustByDB(hmParam);

                        if(nCnt > 0){
                            String sCmpgTyp = StringUtils.defaultString((String) pmParam.get("cmpg_typ1"));
                            if(sCmpgTyp.equals("2000")||sCmpgTyp.equals("3000")){ //미납
                            	//미납의 경우 신규미납관리 생성으로 인한 로직 변경 20190104
                                //hmParam.put("extc_yn","Y");

                                //extcMstTrgtDAO.updateUnpy(hmParam);
                            	hmParam.put("updateOption","extc");
                            	extcMstTrgtDAO.updateUnpy_new(hmParam);
                            }
                        }
                    }
                }
            }else{//file
                if(listInDataSet.size() != 0){
                    for(int i = 0 ; listInDataSet.size() > i ; i++){
                        Map hmParam = listInDataSet.get(i);
                        ParamUtils.addCenterParam(hmParam);
                        ParamUtils.addSaveParam(hmParam);
                        hmParam.put("trgt_list_id", pmParam.get("trgt_list_id"));
                        hmParam.put("sub_trgt_list_id", sKey);
                        String resultMsg = "";

                        String sCmpgChnlCd = StringUtils.defaultString((String) pmParam.get("cmpg_pran_chnl_cd"));

                        hmParam.put("clph_tlno", (CommonUtils.checkNull((String)hmParam.get("clph_tlno"))).trim());
                        hmParam.put("home_tlno", (CommonUtils.checkNull((String)hmParam.get("home_tlno"))).trim());
                        hmParam.put("comp_tlno", (CommonUtils.checkNull((String)hmParam.get("comp_tlno"))).trim());
                        hmParam.put("etc_tlno", (CommonUtils.checkNull((String)hmParam.get("etc_tlno"))).trim());

                        resultMsg += ExcelValidator.validate(hmParam, "mem_no", ExcelValidator.FILTER_REQUIRE | ExcelValidator.FILTER_LENGTH, 24);
                        resultMsg += ExcelValidator.validate(hmParam, "cust_nm", ExcelValidator.FILTER_REQUIRE );
                        resultMsg += ExcelValidator.validate(hmParam, "clph_tlno", ExcelValidator.FILTER_NUMBER | ExcelValidator.FILTER_PHONE | ExcelValidator.FILTER_LENGTH, 12);
                        resultMsg += ExcelValidator.validate(hmParam, "home_tlno", ExcelValidator.FILTER_NUMBER | ExcelValidator.FILTER_PHONE | ExcelValidator.FILTER_LENGTH, 12);
                        resultMsg += ExcelValidator.validate(hmParam, "comp_tlno", ExcelValidator.FILTER_NUMBER | ExcelValidator.FILTER_PHONE | ExcelValidator.FILTER_LENGTH, 12);
                        resultMsg += ExcelValidator.validate(hmParam, "etc_tlno", ExcelValidator.FILTER_NUMBER | ExcelValidator.FILTER_PHONE | ExcelValidator.FILTER_LENGTH, 12);

                        switch(sCmpgChnlCd){
                            case "10": //전화
                                if (hmParam.get("home_tlno")==null && hmParam.get("comp_tlno")==null && hmParam.get("clph_tlno")==null && hmParam.get("etc_tlno")==null) {
                                    resultMsg += "home_tlno:'선택입력',";
                                    resultMsg += "comp_tlno:'선택입력',";
                                    resultMsg += "clph_tlno:'선택입력',";
                                    resultMsg += "etc_tlno:'선택입력',";
                                }
                            break;

                            case "20": //문자
                                if (hmParam.get("clph_tlno")==null) {
                                    resultMsg += "clph_tlno:'필수입력',";
                                }
                            break;
                        }

                        if (!resultMsg.equals("")) {
                            resultMsg +=  "rownum:" + (i+6);
                            hmParam.put("dfct_rsn", resultMsg);
                            try {
                                //불량데이터 저장
                                dfctDataDAO.insertDfctDataByExcel(hmParam);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            subTrgtListDAO.insertTrgtCustByExcel(hmParam);
                        }
                    }
                }
            }

            // 할당여부 업데이트
            updateCmpgAssigninfo(pmParam);

            //추출 갯수  및 오류 갯수를 입력한다.
            pmParam.put("updateOption", "extract");
            subTrgtListDAO.updateSubTrgtList(pmParam);

            //추출갯수 입력한다
            trgtListDAO.updateTrgtList(pmParam);
        }
        return sKey;
    }


    /**
     * 서브타겟 리스트를 수정한다.(ajax)
     *
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public int updateSubTrgtList(Map<String, Object> pmParam) throws Exception {
        int nCnt = subTrgtListDAO.updateSubTrgtList(pmParam);
        if(nCnt>0){
            String sCmpgTyp = StringUtils.defaultString((String) pmParam.get("cmpg_typ1"));
            if(sCmpgTyp.equals("2000")||sCmpgTyp.equals("3000")){ //미납
                pmParam.put("updateOption", "sub_trgt_update");
                extcMstTrgtDAO.updateUnpy(pmParam);
            }
        }
        return  nCnt;
    }

    /**
     * 서브타겟 리스트를 삭제한다.
     *
     * @param pmParam 설문답변 정보
     * @throws Exception
     */
    public int deleteSubTrgtList(Map<String, Object> pmParam) throws Exception {

        subTrgtListDAO.deleteSubTrgtList(pmParam); //서브타겟리스트 삭제
        dfctDataDAO.deleteDfctDataByTrgtList(pmParam); // ERRORDATA 삭제

        String sCmpgTyp = StringUtils.defaultString((String) pmParam.get("cmpg_typ1"));
        String sCmpgTyp2 = StringUtils.defaultString((String) pmParam.get("cmpg_typ2"));
        if(sCmpgTyp.equals("2000") || sCmpgTyp.equals("3000")){ //미납
            if(sCmpgTyp2.equals("2100")||sCmpgTyp2.equals("2200")||sCmpgTyp2.equals("2300")||sCmpgTyp2.equals("2400")
                    ||sCmpgTyp2.equals("3100")||sCmpgTyp2.equals("3200")||sCmpgTyp2.equals("3400")||sCmpgTyp2.equals("3500")){

               	//미납의 경우 신규미납관리 생성으로 인한 로직 변경 20190104
                //extcMstTrgtDAO.updateUnpy(hmParam);

            	pmParam.put("updateOption", "sub_del");
                extcMstTrgtDAO.updateUnpy_new(pmParam);
            }
        }

        subTrgtListDAO.deleteTrgtCustByTrgtList(pmParam);//대상고객삭제



        // 할당여부 업데이트
        updateCmpgAssigninfo(pmParam);

        // 추출갯수 및 오류갯수를 입력한다.
        pmParam.put("updateOption", "extract");
        subTrgtListDAO.updateSubTrgtList(pmParam);

        //추출갯수 입력한다
        trgtListDAO.updateTrgtList(pmParam);

        return subTrgtListDAO.deleteSubTrgtList(pmParam);
    }

    /**
     * 대상 정보를 삭제한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception {
        subTrgtListDAO.deleteTrgtCustByTrgtList(pmParam); // 대상고객 삭제
        return 0;
    }

    public int insertSubTrgtCustByDB(Map<String, Object> pmParam) throws Exception {
        int nCnt = 0;
        nCnt = subTrgtListDAO.insertTrgtCustByDB(pmParam);

        String sCmpgTyp = StringUtils.defaultString((String) pmParam.get("cmpg_typ1"));
        if(sCmpgTyp.equals("2000")||sCmpgTyp.equals("3000")){ //미납
            pmParam.put("extc_yn","Y");
            pmParam.put("updateOption","extc");
            extcMstTrgtDAO.updateUnpy(pmParam);
        }

        return nCnt;
    }

    //DB 저장
    public int insertTrgtCustByDB(Map<String, Object> pmParam) throws Exception {
        return subTrgtListDAO.insertTrgtCustByDB(pmParam);
    }

    //>>단일건저장
    public int insertOneTrgtCust(Map<String, Object> pmParam) throws Exception {
        int nCnt = 0 ;

        nCnt =subTrgtListDAO.insertOneTrgtCust(pmParam);
        if(nCnt > 0){
            // 추출갯수 및 오류갯수를 입력한다.
            pmParam.put("updateOption", "extract");
            subTrgtListDAO.updateSubTrgtList(pmParam);

            //추출갯수 입력한다
            trgtListDAO.updateTrgtList(pmParam);
        }
        return nCnt;
    }

    public int insertTrgtCustByExcel(Map<String, ?> pmParam) throws Exception {
        return subTrgtListDAO.insertTrgtCustByExcel(pmParam);
    }

    /**
     * 대상 정보를 조회한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    public void searchTrgtCustByDB(Map<String, ?> pmParam) throws Exception {
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
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();
            String mem_no = StringUtils.defaultString((String)rslObj.get("mem_no"));
            String cust_nm = StringUtils.defaultString((String)rslObj.get("mem_nm"));
            String home_tlno = StringUtils.defaultString((String)rslObj.get("home_tel"));
            String clph_tlno = StringUtils.defaultString((String)rslObj.get("cell"));
            String comp_tlno = StringUtils.defaultString((String)rslObj.get("wrpl_tel"));
            String trgt_list_id = StringUtils.defaultString((String)gmParam.get("trgt_list_id"));
            String rgsr_id =  StringUtils.defaultString((String)gmParam.get("rgsr_id"));
            String amnd_id =  StringUtils.defaultString((String)gmParam.get("amnd_id"));
            String cntr_cd  = StringUtils.defaultString((String)gmParam.get("cntr_cd"));
            String sub_trgt_list_id = StringUtils.defaultString((String)gmParam.get("sub_trgt_list_id"));

            rowData.put("mem_no",           mem_no); //고객번호
            rowData.put("cust_nm",          cust_nm); //고객명
            rowData.put("home_tlno",        home_tlno);//집 전화번호
            rowData.put("clph_tlno",        clph_tlno); //핸드폰
            rowData.put("comp_tlno",        comp_tlno); //회사전화번호
            rowData.put("trgt_list_id",     trgt_list_id);
            rowData.put("rgsr_id",          rgsr_id);
            rowData.put("amnd_id",          amnd_id);
            rowData.put("cntr_cd",          cntr_cd);
            rowData.put("sub_trgt_list_id",       sub_trgt_list_id);

            subTrgtListDAO.getSqlSession().insert("SubTrgtListMap.insertTrgtCustByDB", rowData);
        }
    }

    /**
     * 캠페인의 서브타겟별 대상 수와 배정건수를 조회한다. --!
     *
     * @param String 서브타겟 ID
     * @return 캠페인 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getAssigNum(String psId) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("sub_trgt_list_id", psId);
        return subTrgtListDAO.getAssigNum(mParam);
    }

    /**
     * 캠페인의 설문지/스크립트/대상리스트 할당정보를 수정한다.(ajax)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int updateCmpgAssigninfo(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        Map<String, Object> mParam = new HashMap<String, Object>();

        mParam.put("updateOption", "assign");
        mParam.put("cntr_cd", pmParam.get("cntr_cd"));
        mParam.put("amnd_id", pmParam.get("amnd_id"));


        String sCmpgInvtMthdCd = (String)pmParam.get("cmpg_invt_mthd_cd");

        mParam.put("scrt_id", pmParam.get("invt_cnts_id"));
        scrtDAO.updateScrt(mParam); // 스크립트 할당여부 수정

        return nResult;
    }

    /**
     * 서브타겟 리스트의 검색 수를 반환한다.(캠페인별 대상목록 현황)
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트 검색 수
     * @throws Exception
     */
    public int getSubTrgtStatByCmpgCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)subTrgtListDAO.getSubTrgtStatByCmpgCount(pmParam);
    }

    /**
     * 서브타겟 리스트 정보를 검색한다.(캠페인별 대상목록 현황)
     *
     * @pmParam paramHash 검색 조건
     * @return subTrgt 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getSubTrgtStatByCmpgList(Map<String, ?> pmParam) throws Exception {
        return subTrgtListDAO.getSubTrgtStatByCmpgList(pmParam);
    }
}
