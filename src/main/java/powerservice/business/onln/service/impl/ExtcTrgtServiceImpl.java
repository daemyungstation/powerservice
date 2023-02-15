/*
 * (@)# ExtcCndtService.java
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

package powerservice.business.onln.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
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

import powerservice.business.cam.service.impl.ExtcMstTrgtDAO;
import powerservice.business.cam.service.impl.ScrtDAO;
import powerservice.business.cam.service.impl.SubTrgtListDAO;
import powerservice.business.cam.service.impl.TrgtListDAO;
import powerservice.business.cam.service.impl.UnpyMngeDAO;
import powerservice.business.dlw.service.impl.DlwExtcTrgtDAO;
import powerservice.business.onln.service.ExtcTrgtService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.impl.BasVlDAO;
import powerservice.common.util.CommonUtils;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import sun.misc.BASE64Encoder;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대상고객추출조건을 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID ExtcCndt
 */

@Service
public class ExtcTrgtServiceImpl extends EgovAbstractServiceImpl implements ExtcTrgtService {


    @Resource
    public ScrtDAO scrtDAO;

    @Resource
    public SubTrgtListDAO subTrgtListDAO;

    @Resource
    public DlwExtcTrgtDAO dlwExtcTrgtDAO;

    @Resource
    public ExtcTrgtDAO extcTrgtDAO;

    @Resource
    public BasVlService basVlService;

    @Resource
    public TrgtListDAO trgtListDAO;

    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;

    @Resource
    public UnpyMngeDAO unpyMngeDAO;

    @Resource
    public BasVlDAO basVlDAO;

    /**
     * 온라인 추출 등록 처리상태 변경
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int updateOnlineStatAlct(Map<String, ?> pmParam) throws Exception {
        return extcTrgtDAO.updateOnlineStatAlct(pmParam) ;
    }


    /**
     * 온라인 상담등록을 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int insertOnlnCons(Map<String, ?> pmParam) throws Exception {
        int nCnt = 0;
        nCnt = extcTrgtDAO.insertOnlnCons(pmParam);
        if(nCnt>0){
            extcTrgtDAO.updateOnlineSeq(pmParam);
        }
        return  nCnt;
    }

    /**
     * 대상고객추출조건 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount(Map<String, ?> pmParam) throws Exception {
        String sCmpgTypCd = StringUtils.defaultString((String) pmParam.get("cmpg_typ_cd"));
        String sCmpgTypCd2 = StringUtils.defaultString((String) pmParam.get("cmpg_typ_cd2"));
        String sResnAdd = StringUtils.defaultString((String) pmParam.get("resn_add"));

        int nCnt = 0;
        if(sCmpgTypCd.equals("1000")){//해피콜
            if(sCmpgTypCd2.equals("1100")){//증서멤버쉽
                nCnt = (Integer) dlwExtcTrgtDAO.getExtcTrgtCount1100(pmParam);
            }else if(sCmpgTypCd2.equals("1200")){//가입승인
                nCnt = (Integer) dlwExtcTrgtDAO.getExtcTrgtCount1200(pmParam);
            }else if(sCmpgTypCd2.equals("1700")){//해약 미처리
            	if (sResnAdd.equals("Y")){
            		nCnt = (Integer) dlwExtcTrgtDAO.getExtcTrgtCount1700Add(pmParam); //해약 미처리건 중간 추가
            	}else{
            		nCnt = (Integer) dlwExtcTrgtDAO.getExtcTrgtCount1700(pmParam); //해약 미처리건 최초 추가
            	}
            }else{//반송
                nCnt = (Integer) extcMstTrgtDAO.getSnbcCount(pmParam);
            }
        }else if(sCmpgTypCd.equals("2000")){ //장기연체
            if(sCmpgTypCd2.equals("2100")){//업체관리
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("2200")){//해약방어
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("2300")){//미납분배
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("2400")){//채권분배
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }
        }else if(sCmpgTypCd.equals("3000")){ //단기연체
            if(sCmpgTypCd2.equals("3100")){//채권
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("3200")){//미납
                //nCnt = (Integer) unpyMngeDAO.getUnpyCount(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("3500")){//미납
                nCnt = (Integer) unpyMngeDAO.getMemYenche_cnt(pmParam);
            }else if(sCmpgTypCd2.equals("3300")){//당월미납
                nCnt = (Integer) unpyMngeDAO.getSmsUnpyCount(pmParam);
            }else if(sCmpgTypCd2.equals("3400")){//1회미납
                nCnt = (Integer) unpyMngeDAO.getSmsUnpyCount(pmParam);
            }

        }else if(sCmpgTypCd.equals("4000")){ //TM
            if(sCmpgTypCd2.equals("4100")){//1회미납
                nCnt = (Integer) extcTrgtDAO.getExtcTrgtCount4100(pmParam);
            }
        }

        return nCnt ;
    }

    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList(Map<String, ?> pmParam) throws Exception {
        String sCmpgTypCd = StringUtils.defaultString((String) pmParam.get("cmpg_typ_cd"));
        String sCmpgTypCd2 = StringUtils.defaultString((String) pmParam.get("cmpg_typ_cd2"));
        String sResnAdd = StringUtils.defaultString((String) pmParam.get("resn_add"));

        List<Map<String, Object>> getExtcTrgt = new ArrayList<Map<String,Object>>();

        if(sCmpgTypCd.equals("1000")){//해피콜
            if(sCmpgTypCd2.equals("1100")){//증서멤버쉽
                getExtcTrgt = dlwExtcTrgtDAO.getExtcTrgtList1100(pmParam);
            }else if(sCmpgTypCd2.equals("1200")){//가입승인
                getExtcTrgt = dlwExtcTrgtDAO.getExtcTrgtList1200(pmParam);
            }else if(sCmpgTypCd2.equals("1700")){//해약 미처리
              	if (sResnAdd.equals("Y")){
              		getExtcTrgt = dlwExtcTrgtDAO.getExtcTrgtList1700Add(pmParam); //해약 미처리건 중간 추가
            	}else{
            		getExtcTrgt = dlwExtcTrgtDAO.getExtcTrgtList1700(pmParam); //해약 미처리건 최초 추가
            	}

            }else { //반송
                getExtcTrgt = extcMstTrgtDAO.getSnbc(pmParam);
            }
        }else if(sCmpgTypCd.equals("2000")){ //장기연체
            if(sCmpgTypCd2.equals("2100")){//업체관리
            	//getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }else if(sCmpgTypCd2.equals("2200")){//해약방어
            	//getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }else if(sCmpgTypCd2.equals("2300")){//미납분배
            	//getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);;
            }else if(sCmpgTypCd2.equals("2400")){//채권분배
            	//getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }
        }else if(sCmpgTypCd.equals("3000")){ //단기연체
            if(sCmpgTypCd2.equals("3100")){//채권
            	//getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }else if(sCmpgTypCd2.equals("3200")){//미납
                //getExtcTrgt = unpyMngeDAO.getUnpy(pmParam);
            	/*신규 미납관리 회원 조회 20190103*/
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }else if(sCmpgTypCd2.equals("3500")){//일반미납
                getExtcTrgt = unpyMngeDAO.getMemYenche(pmParam);
            }else if(sCmpgTypCd2.equals("3300")){//당월미납
                getExtcTrgt = unpyMngeDAO.getSmsUnpy(pmParam);
            }else if(sCmpgTypCd2.equals("3400")){//1회미납
                getExtcTrgt = unpyMngeDAO.getSmsUnpy(pmParam);
            }
        }else if(sCmpgTypCd.equals("4000")){ //TM
            if(sCmpgTypCd2.equals("4100")){//가입
                getExtcTrgt = extcTrgtDAO.getExtcTrgtList4100(pmParam);
            }
        }

        return getExtcTrgt;
    }

    /**
     * 캠페인 TM, 가입 , B2Q 일때 매월 1일 SUBTRGTLIST 를 생성한다.
     *
     * @param
     * @throws Exception
     */
    public void insertSubTrgtList() throws Exception {
        String basVl = basVlService.getBasVlAsString("cmpg_tm_join_b2q");
        /*소스변경
        기준값 크기 에 따른 추가*/
        String basVl2 = basVlService.getBasVlAsString("cmpg_tm_join_b2q2");
        basVl2 += ","; //기준값
        basVl2 += basVl; //기준값
        String[] sCmpgTmB2Q = basVl2.split(",");

        Map<String, Object> hmParam = new HashMap<String, Object>();
        Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        for(int i = 0; sCmpgTmB2Q.length > i ; i++ ){
            String[] sCmpgTm = sCmpgTmB2Q[i].split("\\^");
            String sSubTrgtListNm = StringUtils.defaultString(( oCalendar.get(Calendar.YEAR)+"년 "+(oCalendar.get(Calendar.MONTH) + 1)+"월 "+sCmpgTm[1]));

            hmParam.put("trgt_list_id", sCmpgTm[0]);     // 기준값
            hmParam.put("sub_trgt_list_id_scheduling","STL"+sCmpgTm[1]+oCalendar.get(Calendar.YEAR)+(oCalendar.get(Calendar.MONTH) + 1));
            hmParam.put("sub_trgt_list_nm", sSubTrgtListNm);
            hmParam.put("invt_cnts_id", sCmpgTm[0]);     // 기준값
            if("TAR202102268150909".equals(sCmpgTm[0])) {
            	hmParam.put("cmpg_prfm_team_cd", "T20100");  // 기준값
            } else if("TAR202105119262669".equals(sCmpgTm[0])) {
            	hmParam.put("cmpg_prfm_team_cd", "T20600");  // 기준값
            } else {
            	hmParam.put("cmpg_prfm_team_cd", "T20300");  // 기준값
            }
            hmParam.put("trgt_list_extc_typ_cd", "20");  // DB
            hmParam.put("sub_trgt_list_expi", sSubTrgtListNm);
            hmParam.put("rgsr_id", "ADMIN");
            hmParam.put("amnd_id", "ADMIN");
            hmParam.put("cntr_cd", "CCA");

            String sKey = "";

            subTrgtListDAO.getSqlSession().insert("SubTrgtListMap.insertSubTrgtList", hmParam);

            //할당여부 업데이트
            updateCmpgAssigninfo(hmParam);
       }
    }

    /**
     * 실적건을 업데이트 한다.실적 (회수 , 회수회차) 조회 (납입)
     *
     * @param
     * @throws Exception
     */
    public void updateUnpyAcrs() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        Calendar oCalendar = Calendar.getInstance( );
        int today =  oCalendar.get(oCalendar.DATE);
        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 1일날은 해당월 말일실적을 저장한다. sFg : N 이면 말일
         * 수정일시 : 2016/8/11
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        if(today  == 1){
            mParam.put("sFg","N");
        }else{
            mParam.put("sFg","Y");
        }
        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getUnpyAcrs", mParam, new updateAcrs(mParam));
    }

    /**
     * 실적건을 업데이트 한다.실적 (회수 , 회수회차) 조회 (해약방어)
     *
     * @param
     * @throws Exception
     */
    public void updateCnctAcrs() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        Calendar oCalendar = Calendar.getInstance( );
        int today = oCalendar.get(oCalendar.DATE);
        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 1일날은 해당월 말일실적을 저장한다. sFg : N 이면 말일
         * 수정일시 : 2016/8/11
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        if(today  == 1){
            mParam.put("sFg","N");
        }else{
            mParam.put("sFg","Y");
        }
        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getCnctAcrs", mParam, new updateAcrs(mParam));
        unpyMngeDAO.getSqlSession().update("UnpyMngeHstrMap.insertUnpyMngeHstr");
    }

    protected class updateAcrs implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public updateAcrs(Map<String, Object> mParam) {
            super();

            this.gmParam = new HashMap<String, Object>();

            if (mParam != null) {
                Set<String> oKeySet = mParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, mParam.get(sKey));
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();

            Calendar oCalendar = Calendar.getInstance( );
            int today =  oCalendar.get(oCalendar.DATE);
            int endDay = oCalendar.getActualMaximum(oCalendar.DAY_OF_MONTH);
            if(gmParam.get("sFg") == "N"){//말일실적
                rowData.put("acrs_day_end",               rslObj.get("acrs_today"));
                rowData.put("acrs_enmn_unpy_rcvr_rntm",   rslObj.get("acrs_tday_unpy_rcvr_rntm"));
            }
            switch(today){
              case 6: //5일실적
                  rowData.put("acrs_day_5",               rslObj.get("acrs_today"));
                  rowData.put("acrs_5dy_unpy_rcvr_rntm",  rslObj.get("acrs_tday_unpy_rcvr_rntm"));
              break;

              case 11: //10일실적
                  rowData.put("acrs_day_10",              rslObj.get("acrs_today"));
                  rowData.put("acrs_10dy_unpy_rcvr_rntm", rslObj.get("acrs_tday_unpy_rcvr_rntm"));
              break;

              case 16: //15일실적
                  rowData.put("acrs_day_15",              rslObj.get("acrs_today"));
                  rowData.put("acrs_15dy_unpy_rcvr_rntm", rslObj.get("acrs_tday_unpy_rcvr_rntm"));
              break;

              case 21: //20일실적
                  rowData.put("acrs_day_20",              rslObj.get("acrs_today"));
                  rowData.put("acrs_20dy_unpy_rcvr_rntm", rslObj.get("acrs_tday_unpy_rcvr_rntm"));
              break;

              case 26: //25일실적
                  rowData.put("acrs_day_25",              rslObj.get("acrs_today"));
                  rowData.put("acrs_25dy_unpy_rcvr_rntm", rslObj.get("acrs_tday_unpy_rcvr_rntm"));
              break;
            }
            rowData.put("sFg",                      gmParam.get("sFg"));
            rowData.put("amnd_id",                  "ADMIN");
            // 실적
            rowData.put("accnt_no",                 rslObj.get("accnt_no"));
            rowData.put("acc_stat",                 rslObj.get("acc_stat"));
            rowData.put("acrs_today",               rslObj.get("acrs_today"));
            rowData.put("acrs_tday_unpy_rcvr_rntm", rslObj.get("acrs_tday_unpy_rcvr_rntm"));

            extcMstTrgtDAO.getSqlSession().update("ExtcMstTrgtMap.updateAcrs", rowData);
        }
    }

    /**
     * 장기 연체 업체관리 건을  ORACLE테이블로 저장한다.
     *
     * @param
     * @throws Exception
     */
    public void insertLntmBzptAdmn() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getExtcTrgtList2100", mParam, new insertUnpy(mParam));
        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 제외건 업데이트
         * 수정일시 : 2016/8/03
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        updatExcd();
      //***************************************************************
        //분배액 업데이트
        updateAltnAmt();
    }

    /**
     * 장기 연체 해약방어 건을  ORACLE테이블로 저장한다.
     *
     * @param
     * @throws Exception
     */
    public void insertLntmCnctPrtc() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getExtcTrgtList2200", mParam, new insertUnpy(mParam));
        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 제외건 업데이트
         * 수정일시 : 2016/8/03
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        updatExcd();
        //***************************************************************
        //분배액 업데이트
        updateAltnAmt();
    }

    /**
     *  연체  건을  ORACLE테이블로 저장한다.
     *
     * @param
     * @throws Exception
     */
    public void insertUnpy() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();

        mParam.put("sUnpy","Y");
        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getExtcTrgtListUnpy", mParam, new insertUnpy(mParam));

        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 제외건 업데이트
         * 수정일시 : 2016/8/03
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        updatExcd();
        //***************************************************************
        //분배액 업데이트
        updateAltnAmt();
    }

    /**
     * 당월미납 건 을  ORACLE테이블로 저장한다.
     *
     * @param
     * @throws Exception
     */
    public int insertMonthUnpy(Map<String, Object> pmParam) throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기
        mParam.put("ichae_dt", pmParam.get("ichae_dt"));
        mParam.put("sUnpy","M");
        pmParam.put("bas_vl_nm","month_unpy_mnge_click");

        Map<String, Object> hmParam = basVlDAO.getBasVl(pmParam);
        basVlDAO.updateMonthUnpy(hmParam); //기준값 업데이트

        dlwExtcTrgtDAO.getSqlSession().select("DlwExtcTrgtMap.getExtcTrgtList3300", mParam, new insertUnpy(mParam));

        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 제외건 업데이트
         * 수정일시 : 2016/8/03
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        updatExcd();
        //***************************************************************
        //분배액 업데이트
        updateAltnAmt();

        /*
         * 수정자 : 정훈연구원
         * 요청자 : 정성안팀장
         * 요청이유 : 당월미납은 스케줄링 시점을 찾을수 없어 수동으로 작동한다. /대상목록관리에
         *            초기화/초기화 를 누르면 버튼이 활성화 된다.
         * 수정일시 : 2016/8/10
         * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
         * */
        insertDlwYenche();//연체 MNG테이블에 인서트
        return 0;
    }

    protected class insertUnpy implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public insertUnpy(Map<String, Object> mParam) {
            super();

            this.gmParam = new HashMap<String, Object>();

            if (mParam != null) {
                Set<String> oKeySet = mParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, mParam.get(sKey));
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();
            String sPayStat  = String.valueOf(rslObj.get("pay_state"));
            rowData.put("accnt_no",              rslObj.get("accnt_no"));
            rowData.put("month_cnt",             rslObj.get("month_cnt"));
            rowData.put("true_cnt",              rslObj.get("true_cnt"));
            rowData.put("ichae_dt",              rslObj.get("ichae_dt"));
            rowData.put("acc_stat",              rslObj.get("acc_stat"));
            rowData.put("pay_mthd",              rslObj.get("pay_mthd"));
            rowData.put("pay_day",               rslObj.get("pay_day"));
            rowData.put("true_amt",              rslObj.get("true_amt"));
            rowData.put("relat_amt",             rslObj.get("relat_amt"));
            rowData.put("add_amt",               rslObj.get("add_amt"));
            rowData.put("section_t",                rslObj.get("section_t"));

            if(gmParam.get("sUnpy")=="Y"){ //장기 단기 미납 채권 1회차 미납
                rowData.put("pay_state",             CommonUtils.extractNumeral(sPayStat));
//                if("1회 연체".equals(sPayStat)|| "1".equals(sPayStat)){ //1회
//                    String sSection_T = (String)rslObj.get("section_t");
//                    if(rslObj.get("section_t") != null){
//                        if(sSection_T.equals("0002")){ //채권
//                            rowData.put("div_type",              "C");
//                        }else if(sSection_T.equals("0001")){ //미납
//                            rowData.put("div_type", "M");
//                        }
//                        rowData.put("camp_type", "3400");
//                    }else{
//                        rowData.put("div_type",  "I");
//                        rowData.put("camp_type", "9999");
//                    }
//                }else if("2".equals(sPayStat)||"3".equals(sPayStat)||"4".equals(sPayStat)||"5".equals(sPayStat)||"2회 연체".equals(sPayStat)||"3회 연체".equals(sPayStat)|| "4회 연체".equals(sPayStat)|| "5회 연체".equals(sPayStat)){ //단기
//                    if(rslObj.get("section_t") != null){
//                        String sSection_T = (String)rslObj.get("section_t");
//                        if(sSection_T.equals("0002")){ //채권
//                            rowData.put("div_type",  "C");
//                            rowData.put("camp_type", "3100");
//
//                        }else if(sSection_T.equals("0001")){ //미납
//                            rowData.put("div_type",  "M");
//                            rowData.put("camp_type", "3200");
//                        }
//                    }else{
//                        rowData.put("div_type",  "I");
//                        rowData.put("camp_type", "9999");
//                    }
//                }else{ //장기
//                    if(rslObj.get("section_t") != null){
//                        String sSection_T = (String)rslObj.get("section_t");
//                        if(sSection_T.equals("0002")){ //채권
//                            rowData.put("div_type",  "C");
//                            rowData.put("camp_type", "2400");
//                        }else if(sSection_T.equals("0001")){ //미납
//                            rowData.put("div_type",  "M");
//                            rowData.put("camp_type", "2300");
//                        }
//                    }else{
//                        rowData.put("div_type",  "I");
//                        rowData.put("camp_type", "9999");
//                    }
//                }

// 2018.02.01 부터 1회, 단기(2~5), 장기(6~) ===> 단기(1~3), 장기(4~)로 변경

                 if("1".equals(sPayStat)||"2".equals(sPayStat)||"3".equals(sPayStat)||"1회 연체".equals(sPayStat)||"2회 연체".equals(sPayStat)||"3회 연체".equals(sPayStat)){ //단기
                    if(rslObj.get("section_t") != null){
                        String sSection_T = (String)rslObj.get("section_t");
                        if(sSection_T.equals("0002")){ //채권
                            rowData.put("div_type",  "C");
                            rowData.put("camp_type", "3100");

                        }else if(sSection_T.equals("0001")){ //미납
                            rowData.put("div_type",  "M");
                            rowData.put("camp_type", "3200");
                        }
                    }else{
                        rowData.put("div_type",  "I");
                        rowData.put("camp_type", "9999");
                    }
                }else{ //장기
                    if(rslObj.get("section_t") != null){
                        String sSection_T = (String)rslObj.get("section_t");
                        if(sSection_T.equals("0002")){ //채권
                            rowData.put("div_type",  "C");
                            rowData.put("camp_type", "2400");
                        }else if(sSection_T.equals("0001")){ //미납
                            rowData.put("div_type",  "M");
                            rowData.put("camp_type", "2300");
                        }
                    }else{
                        rowData.put("div_type",  "I");
                        rowData.put("camp_type", "9999");
                    }
                }
            }else{
                if(gmParam.get("sUnpy")=="M"){ //당월미납
                    rowData.put("pay_state", "0");
                }else{
                    rowData.put("pay_state", CommonUtils.extractNumeral(sPayStat)); //업체관리 해약방어
                }
                rowData.put("div_type",  rslObj.get("div_type"));
                rowData.put("camp_type", rslObj.get("camp_type"));
            }
            /*
             * 수정자 : 정훈연구원
             * 요청자 : 정성안팀장
             * 요청이유 : 전화번호뒤에 공백 처리 및 문자 처리
             * 수정일시 : 2016/09/26
             */
            String sClphTlno = (String)rslObj.get("cell");
            if(sClphTlno !=null){
                rowData.put("cell", CommonUtils.extractNumeral(sClphTlno.trim()));
            }
            //rowData.put("cell",      rslObj.get("cell"));
            rowData.put("mem_nm",      rslObj.get("mem_nm"));
            rowData.put("prod_cd",     rslObj.get("prod_cd"));
            rowData.put("prod_nm",     rslObj.get("prod_nm"));
            rowData.put("join_dt",     rslObj.get("join_dt"));
            rowData.put("hpcall_stat", rslObj.get("hpcall_stat"));
            rowData.put("call_corp",   rslObj.get("call_corp"));
            rowData.put("mem_no",      rslObj.get("mem_no"));
            rowData.put("ovrd_cnt",    rslObj.get("ovrd_cnt"));
            rowData.put("cntr_cd",     "CCA");
            rowData.put("rgsr_id",     "ADMIN");
            rowData.put("amnd_id",     "ADMIN");
            /*
             * 수정자 : 정훈연구원
             * 요청자 : 정성안팀장
             * 요청이유 : 필요한 컬럼추가
             * 수정일시 : 2016/8/03
             * 하기 내용 문의 사항 있을시 요청자에게 문의하세요
             * */
            rowData.put("b2b_comp_nm", rslObj.get("b2b_comp_nm"));
            rowData.put("b2b_comp_cd", rslObj.get("b2b_comp_cd"));

            //***************************************************************
            if(gmParam.get("sUnpy")=="M"){ //당월미납
                int nCnt = 0;
                    nCnt = unpyMngeDAO.getSqlSession().selectOne("UnpyMngeMap.getMonthUnpyCount", rowData);
                if(nCnt == 0){
                    unpyMngeDAO.getSqlSession().insert("UnpyMngeMap.insertUnpy", rowData);
                }
            }else{
                unpyMngeDAO.getSqlSession().insert("UnpyMngeMap.insertUnpy", rowData);
            }
        }
    }

    /**
     * 캠페인 TM, 가입 , B2Q 일때 오전 6시부터 오후 22시까지 10분간격으로 타겟리스트를 온라인에서 가져온다.
     * @param
     * @throws Exception
     */
    public void insertB2QTmJoinCust() throws Exception{
        String basVl = basVlService.getBasVlAsString("cmpg_tm_join_b2q");
        /*소스변경
        기준값 크기 에 따른 추가*/
        String basVl2 = basVlService.getBasVlAsString("cmpg_tm_join_b2q2");
        basVl2 += ","; //기준값
        basVl2 += basVl; //기준값
        String[] sCmpgTmB2Q = basVl2.split(",");

        Map<String, Object> mParam = new HashMap<String, Object>();
        Calendar oCalendar = Calendar.getInstance( );  // 현재 날짜/시간 등의 각종 정보 얻기

        for(int i = 0; sCmpgTmB2Q.length > i ; i++ ){
            String[] sCmpgTm = sCmpgTmB2Q[i].split("\\^");
            if("TAR99999999999999".equals(sCmpgTm[0])){
                mParam.put("b2b_stts", "SMART");
            }else if("TAR99999999999998".equals(sCmpgTm[0]) || "TAR202105119262669".equals(sCmpgTm[0])){
                mParam.put("b2b_stts", "LGB2B");
            }else if("TAR99999999999997".equals(sCmpgTm[0])){
                mParam.put("b2b_stts", "DAEKYO");
            }else if("TAR99999999999996".equals(sCmpgTm[0])){
                mParam.put("b2b_stts", "GSSUPER");

            //추가생성
            }else if("TAR99999999999995".equals(sCmpgTm[0]) || "TAR202102268150909".equals(sCmpgTm[0])){
                 mParam.put("b2b_stts", "LGU");
            }else if("TAR99999999999994".equals(sCmpgTm[0])){
                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999993".equals(sCmpgTm[0])){
                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999992".equals(sCmpgTm[0])){
                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999991".equals(sCmpgTm[0])){
                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR202207135923241".equals(sCmpgTm[0])){ //개발 TAR202207123728204 운영 TAR202207135923241
                mParam.put("b2b_stts", "DLIVE");
            }else if("TAR202207286104206".equals(sCmpgTm[0])){ //개발 TAR202207253728462 운영 TAR202207286104206
                mParam.put("b2b_stts", "CUCKOO");
            }

            mParam.put("trgt_list_id", sCmpgTm[0]); //임시 기준값?
            mParam.put("sub_trgt_list_id","STL"+sCmpgTm[1]+oCalendar.get(Calendar.YEAR)+(oCalendar.get(Calendar.MONTH) + 1));
            mParam.put("rgsr_id", "ADMIN");
            mParam.put("amnd_id", "ADMIN");
            mParam.put("cntr_cd", "CCA");

            extcTrgtDAO.getSqlSession().select("ExtcTrgtMap.getExtcTrgtList4100", mParam, new insertB2QTmJoinRowHandler(mParam));

            mParam.put("call_stts","할당대기");

            // 추출갯수 및 오류갯수를 입력한다.
            mParam.put("updateOption", "extract");

            // 추출갯수 및 오류갯수를 입력한다.
            subTrgtListDAO.updateSubTrgtList(mParam);
            trgtListDAO.updateTrgtList(mParam);

            // 상태값 변경
            extcTrgtDAO.updateOnlineStat(mParam);
        }
    }

    protected class insertB2QTmJoinRowHandler implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public insertB2QTmJoinRowHandler(Map<String, Object> mParam) {
            super();

            this.gmParam = new HashMap<String, Object>();

            if (mParam != null) {
                Set<String> oKeySet = mParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, mParam.get(sKey));
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();
            String sClphTlno = (String)rslObj.get("clph_tlno");
            String sHomeTlno = (String)rslObj.get("home_tlno");

            rowData.put("mem_nm",    rslObj.get("mem_nm"));
            rowData.put("home_tlno", CommonUtils.extractNumeral(sHomeTlno));
            if(sClphTlno !=null){
                rowData.put("cell", CommonUtils.extractNumeral(sClphTlno.trim()));
            }
            rowData.put("prmv_id",           rslObj.get("prmv_id"));
            rowData.put("trgt_list_id",      gmParam.get("trgt_list_id"));
            rowData.put("sub_trgt_list_id",  gmParam.get("sub_trgt_list_id"));
            rowData.put("rgsr_id",           gmParam.get("rgsr_id"));
            rowData.put("amnd_id",           gmParam.get("amnd_id"));
            rowData.put("cntr_cd",           gmParam.get("cntr_cd"));

            rowData.put("user_defn1_cntn",   rslObj.get("user_defn1_cntn"));
            rowData.put("user_defn2_cntn",   rslObj.get("user_defn2_cntn"));
            rowData.put("user_defn3_cntn",   rslObj.get("user_defn3_cntn"));
            rowData.put("user_defn4_cntn",   rslObj.get("user_defn4_cntn"));
            rowData.put("user_defn5_cntn",   rslObj.get("user_defn5_cntn"));
            rowData.put("user_defn6_cntn",   rslObj.get("user_defn6_cntn"));
            rowData.put("user_defn7_cntn",   rslObj.get("user_defn7_cntn"));
            rowData.put("user_defn8_cntn",   rslObj.get("user_defn8_cntn"));
            rowData.put("user_defn9_cntn",   rslObj.get("user_defn9_cntn"));
            rowData.put("user_defn10_cntn",  rslObj.get("user_defn10_cntn"));

            subTrgtListDAO.getSqlSession().insert("SubTrgtListMap.insertTrgtCustByDB", rowData);
        }
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

    //제외조건을 업데이트 한다 .
    public void updatExcd() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        unpyMngeDAO.getSqlSession().update("UnpyMngeMap.updateExcdProd",mParam); //상품
        unpyMngeDAO.getSqlSession().update("UnpyMngeMap.updateExcdAccnt",mParam); //회원번호
        unpyMngeDAO.getSqlSession().update("UnpyMngeMap.updateExcdB2bComp",mParam); // 업체
        unpyMngeDAO.getSqlSession().update("UnpyMngeMap.updateExcdAccntAndExcdProd",mParam); // 위의 것들 중복
    }

    //해당월의 분배액을 업데이트 한다 .
    public void updateAltnAmt() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        unpyMngeDAO.getSqlSession().update("UnpyMngeMap.updateAltnAmt",mParam);
    }

    /**
     * 당월미납건(제외) MS_SQL 로 저장한다.
     *
     * @param
     * @throws Exception
     */
    public void insertDlwYenche() throws Exception{
        Map<String, Object> mParam = new HashMap<String, Object>();
        unpyMngeDAO.getSqlSession().select("UnpyMngeMap.getOrgUnpyMngeList", mParam, new insertDlwYenche(mParam));
    }


    protected class insertDlwYenche implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public insertDlwYenche(Map<String, Object> mParam) {
            super();

            this.gmParam = new HashMap<String, Object>();

            if (mParam != null) {
                Set<String> oKeySet = mParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, mParam.get(sKey));
                }
            }
        }

        @SuppressWarnings("unchecked")
        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();

            rowData.put("accnt_no",      rslObj.get("accnt_no"));
            rowData.put("moth_count",    rslObj.get("month_cnt"));
            rowData.put("true_count",    rslObj.get("true_cnt"));
            rowData.put("ichae_dt",      rslObj.get("ichae_dt"));
            rowData.put("acc_stat",      rslObj.get("acc_stat"));
            rowData.put("pay_mthd",      rslObj.get("pay_mthd"));
            rowData.put("pay_stat",      rslObj.get("pay_state"));
            rowData.put("pay_day",       rslObj.get("pay_day"));
            rowData.put("div_type",      rslObj.get("div_type"));
            rowData.put("true_sum",      rslObj.get("true_amt"));
            rowData.put("relat_sum",     rslObj.get("relat_amt"));
            rowData.put("add_sum",       rslObj.get("add_amt"));
            rowData.put("cnsl_emple_no", rslObj.get("cnsr_id"));

            int nCnt = 0;
            nCnt = dlwExtcTrgtDAO.getSqlSession().selectOne("DlwExtcTrgtMap.getYenche", rowData);
            if(nCnt == 0){
                dlwExtcTrgtDAO.getSqlSession().insert("DlwExtcTrgtMap.insertDlwYenche", rowData);
            }
        }
    }


    /**
     * 현재 미납상태를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyStat(Map<String, Object> pmParam) throws Exception {
        return dlwExtcTrgtDAO.getUnpyStat(pmParam);
    }

    /**
     * B2B업체코드를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return B2B업체코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> selectB2BList(Map<String, Object> pmParam) throws Exception {
        return dlwExtcTrgtDAO.selectB2BList(pmParam);
    }



    // 그룹코드 등록
 /*   public String insertB2BComp(Map<String, ?> pmParam) throws Exception {
        return dlwExtcTrgtDAO.insertB2BComp(pmParam);
    }
    */

    public String insertB2BComp(Map<String, Object> pmParam) throws Exception {

        //int dupl_custmrCd = dlwExtcTrgtDAO.getDuplB2BCd(pmParam); //거래처 코드 중복 여부 가져옴

        /*if (dupl_custmrCd > 0) {
                                    return "중복";
                                }
            String rslt = "";*/
            ParamUtils.addSaveParam(pmParam);
            dlwExtcTrgtDAO.insertB2BComp(pmParam);
        return "";
    }

    /* B2B업체코드 수정 */
    public int updateB2BComp(Map<String, Object> pmParam) throws Exception {
        ParamUtils.addSaveParam(pmParam);
        return  dlwExtcTrgtDAO.updateB2BComp(pmParam);
     }

    /* B2B업체코드 삭제 */
    public int deleteB2bcd(Map<String, ?> pmParam) throws Exception {
        return  dlwExtcTrgtDAO.deleteB2bcd(pmParam);
     }

    public int countCpHist(Map<String, Object> pmParam) throws Exception {
        return dlwExtcTrgtDAO.countCpHist(pmParam);
    }

    //레저 데이터 전송 작업(웹 redirecting)
    public Map<String, Object> sendDataDgns(Map<String, Object> pmParam) throws Exception {
        String rtnValue = "";

        if (pmParam.get("accnt_no") != null){
              String accntNo = nullStringProc((String)pmParam.get("accnt_no"), false); //회원번호(상품번호)
              String memNm = nullStringProc((String)pmParam.get("mem_nm"), false); //회원명
              String trMemNo = nullStringProc((String)pmParam.get("resort_no"), false); //우대번호(레전번호)
              String rMemNo = nullStringProc((String)pmParam.get("resort_mem_no"), false);
              String rMemGubun = nullStringProc((String)pmParam.get("pay_gubun"), false);
              String eventProcDay = nullStringProc((String)pmParam.get("event_proc_day"), false);
              String joinDt = nullStringProc((String)pmParam.get("join_dt"), false);
              String resnAcptDay = nullStringProc((String)pmParam.get("resn_acpt_day"), false);
              String cell = nullStringProc((String)pmParam.get("cell"), false);
              String idnNo = nullStringProc((String)pmParam.get("idn_no"), true);
              String payDay = nullStringProc((String)pmParam.get("pay_day"), false);
              String payGubun = nullStringProc((String)pmParam.get("resort_mem_gubun"), false);
              String yenchae_yn = "";
              //String ci_val = new String(Base64.encodeBase64(((String)data.get("ci")).getBytes()));
              String ci_val = nullStringProc((String)pmParam.get("ci_val"), false);
              String regMan = nullStringProc((String)pmParam.get("emple_no"),false);

              //행사 여부
              if ("Y".equals(isEvent(accntNo))){
                  yenchae_yn = nullStringProc("n", false);
              }else {
                  yenchae_yn = nullStringProc((String)pmParam.get("yenchae_yn"), false);
              }

              String param = "?lwMemNo=" + accntNo + "&lwMemName=" + URLEncoder.encode(memNm, "EUC-KR") +
                    "&trMemNo=" + trMemNo + "&rMemNo=" + rMemNo +
                    "&rMemGubun=" + rMemGubun + "&jDate=" + joinDt +
                    "&eDate=" + eventProcDay + "&cDate=" + resnAcptDay +
                    "&lwMemMobile=" + cell + "&lwMemId=" + idnNo +
                    "&oDate=" + payDay + "&payGubun=" + payGubun +
                    "&lwyunchae_yn=" + yenchae_yn + "&cntcInfo=" + URLEncoder.encode(ci_val, "EUC-KR")  ;

                System.out.println("=====[1]======>:"+ param);

                //System.out.println("=============param:"+param);
                String server = "https://mobile.daemyung.com/mem/inf/saveLifewayPkg.do" + param;
                //String server = "http://aaaa.daemyung.com/mem/inf/saveLifewayPkg.do" + param;
                String result = new String();
                String tmplsSndRslt = "";

                System.out.println("=====[2]======>:"+ server);

            try {
                URL url = new URL(server);
                URLConnection connection = url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                //connection.setConnectTimeout(8000);
                connection.setReadTimeout(20000);
                //connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String buf = null;
                result = br.readLine();
                
                System.out.println("=====[3]======>:"+ result);

                String[] temp = result.split("&");
                String[] success = temp[0].split("=");
                if (success[1].equals("success")) {
                    pmParam.put("exception", success[1]);
                    pmParam.put("success", "완료");
                    pmParam.put("ls_snd_rslt", "Y");
                    pmParam.put("emple_no", regMan);
                    System.out.println(success[1] + " : " + accntNo);
                } else {
                    pmParam.put("exception", success[1]);
                    pmParam.put("success", "오류");
                    pmParam.put("ls_snd_rslt", "F");
                    System.out.println(success[1] + " : " + accntNo);
                }

                //MPA에 전송정보 UPDATE
                //updateRsSndEnd(pmParam);

                System.out.println("=====[4]======>:"+ accntNo);
                }catch (MalformedURLException mue) {
                    pmParam.put("exception", "mue");
                    pmParam.put("ls_snd_rslt", "F");
                    pmParam.put("errMsg", "MalformedURLException error");
                } catch (IOException ioe) {
                    if (ioe.getMessage().equals("connect timed out")) {
                        pmParam.put("exception", "cto");
                        pmParam.put("ls_snd_rslt", "F");
                        pmParam.put("errMsg", "connect timed out");
                        //rtnValue = "connect timed out";
                        System.out.println("=====[5]======>:"+ rtnValue);
                }

                pmParam.put("exception", "rto");
                pmParam.put("ls_snd_rslt", "F");
                
                System.out.println("ioe.getMessage ========>"+ioe.getMessage());
                
                // return hmParam;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        //Map<String, Object> sendMap = pmParam;
        return pmParam;
    }

    public String nullStringProc(String param, boolean incoding)
    {
      if ((param == null) || (param.equals(""))) {
        param = "";
      } else if (incoding) {
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] b1 = param.getBytes();
        param = encoder.encode(b1);
      }

      return param;
    }

    //레저연동 조회
    public List<Map<String, Object>> getCpHist(Map<String, Object> pmParam) throws Exception {
        return dlwExtcTrgtDAO.getCpHist(pmParam);
    }


    public String isEvent(String paramString) throws Exception
       {
        String result = "";
             try
             {
               result = (String)dlwExtcTrgtDAO.isEvent(paramString);//this.sqlMapClient.queryForObject("life.isEvent", accntNo);

             } catch (Exception e) {
                 throw e;
            }
       return result;

      }

    //TMS 전송
    public int updateRsSndEnd(Map<String, ?> pmParam) throws Exception{
        int result = 0;
        try {
            result = dlwExtcTrgtDAO.updateRsSndEnd(pmParam);
        } catch (Exception  e) {
            throw new Exception("errors.updateRsSndEnd", e);
        }
        return result;
    }

  //레저 연체자 조회 건수
    public int countResortYenCheInfoList() throws Exception {
        return dlwExtcTrgtDAO.countResortYenCheInfoList();
    }
    
    //레저연체 리스트 페이지 조회용
    public int countResortYenCheInfoDataCnt(Map<String, Object> pmParam) throws Exception {
        return dlwExtcTrgtDAO.countResortYenCheInfoDataCnt(pmParam);
    }

    //레저 연체자 리스트
    public  List<Map<String, Object>>  selectResortYenCheInfoList(Map<String, Object> pmParam) throws Exception{
        return dlwExtcTrgtDAO.selectResortYenCheInfoList(pmParam);
    }

    //레저 연체자 TMS 결과
    public int updateRsYenCheSndEnd(Map<String, ?> pmParam) throws Exception
       {
            int result = 0;

            try {
                    result = dlwExtcTrgtDAO.updateRsYenCheSndEnd(pmParam);
                } catch (Exception  e) {
                      throw new Exception("errors.updateRsYenCheSndEnd", e);
                }
            return result;
        }

    public int insertResortMngHstr(Map<String, ?> pmParam) throws Exception{
        int result = 0;
        try {
            result = dlwExtcTrgtDAO.insertResortMngHstr(pmParam);
        } catch (Exception  e) {
            throw new Exception("errors.insertResortMngHstr", e);
        }
        return result;
    }

     public int yenCheInfoRefresh(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception
        {
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
             int iCudCnt = 0;
             int rowType = -1;
             String user_Id = "";
             Map hmParam = null;
             Map hmParam2 = null;

             int TOTinsert = 0;
             int update = 0;
             int TOTupdate = 0;
             int iResult = 0;

             try {
                    hmParam = new HashMap<String,Object>();

                    //입력자 정보 넣고
                    ParamUtils.addSaveParam(hmParam);

                    UserSessionCore oLoginUser = SessionUtils.getLoginUser();
                    user_Id = oLoginUser.getUserid();

                     //연체에서 정상으로 돌아온 회원 삭제
                     dlwExtcTrgtDAO.deleteYenCheInfo(hmParam);

                    //한번도 연체 대상자가 안된 회원 중 최초 연체 INSERT
                     TOTinsert = dlwExtcTrgtDAO.insertYenCheInfo(hmParam);

                    // 현재 납입상태와 리조트 연체 건수
                    TOTupdate = dlwExtcTrgtDAO.countResortYenCheInfo();

                    // 현재 납입상태와 리조트 연체 테이블의 납입상태를 확인
                    dlwExtcTrgtDAO.updateYenCheInfo(hmParam);



                       System.out.println(">>>>>>>>>>>>>");
                    System.out.println(">>>>>>>>>>>>>TOTinsert : " + TOTinsert );
                    System.out.println(">>>>>>>>>>>>>" );

                       System.out.println(">>>>>>>>>>>>>");
                    System.out.println(">>>>>>>>>>>>>TOTupdate : " + TOTupdate );
                    System.out.println(">>>>>>>>>>>>>" );

                    /*
                     // 현재 납입상태와 리조트 연체 테이블의 납입상태를 확인
                     List<Map<String,Object>> lst = dlwExtcTrgtDAO.select_before_updateYenCheInfo(hmParam);
                     if (lst == null || lst.size() == 0) {
                         System.out.println("select_before_updateYenCheInfo 결과가 없습니다");
                     }else{
                         for (int i=0; i<lst.size(); ++i) {
                             // 위의 내용상 틀린게 있으면 리조트 연체 상태값 변경
                             update = dlwExtcTrgtDAO.updateYenCheInfo(lst.get(i));
                             TOTupdate = TOTupdate + 1;
                         }
                     }
                     */
               } catch (Exception e) {
                   throw e;
               }

               mOut.put("insert", Integer.valueOf(TOTinsert ));
               mOut.put("update", Integer.valueOf(TOTupdate));

               return TOTinsert  + TOTupdate;

        }

     //레저연동 이력 20170810
     public int selectResortMngHstr_cnt(Map<String, Object> pmParam) throws Exception {
         return dlwExtcTrgtDAO.selectResortMngHstr_cnt(pmParam);
     }

     public  List<Map<String, Object>> selectResortMngHstr(Map<String, Object> pmParam) throws Exception{
         return dlwExtcTrgtDAO.selectResortMngHstr(pmParam);
     }

}
