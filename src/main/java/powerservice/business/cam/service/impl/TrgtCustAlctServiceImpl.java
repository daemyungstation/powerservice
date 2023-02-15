/*
 * (@)# TrgtCustAlctServiceImpl.java
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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import powerservice.business.cam.service.ExtcMstTrgtService;
import powerservice.business.cam.service.TrgtCustAlctService;
import powerservice.business.onln.service.impl.ExtcTrgtDAO;
import powerservice.business.pds.service.pdsService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.usr.service.impl.UserDAO;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 캠페인대상고객할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID TrgtCustAlct
 */

@Service
public class TrgtCustAlctServiceImpl extends EgovAbstractServiceImpl implements TrgtCustAlctService {

    @Resource
    public TrgtCustAlctDAO trgtCustAlctDAO;

    @Resource
    public DpmsReslHstrDAO dpmsReslHstrDAO;

    @Resource
    public ExtcTrgtDAO extcTrgtDAO;

    @Resource
    public BasVlService basVlService;

    @Resource
    public UserDAO userDAO;

    @Resource
    public TrgtCustDAO trgtCustDAO;

    @Resource
    private ExtcMstTrgtService extcMstTrgtService;

    @Resource
    private CmpgAlctDAO cmpgAlctDAO;

    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;
    
    @Resource
    private pdsService pdsService;

    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNumSelectAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("sCmpgDivCd")); //상위
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("sCmpgTypCd")); //하위

        String sAlctNum = StringUtils.defaultString((String) pmParam.get("sAlctNum")); //하위
        Map<String, Object> mParam = new HashMap<String, Object>();


        int nAlctNum =  Integer.parseInt(sAlctNum); //할당수치
        int nEnd = 0;

        for(int i = 0; listInDs.size() > i; i++){
            if(nEnd == nAlctNum){
                break;
            }
            nEnd++;
            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));
            mParam.put("user_id", sCnsrId);

            String sSubTrgtListId = StringUtils.defaultString((String)mParam.get("sub_trgt_list_id")); //하위

            //hmParam.put("sub_trgt_list_id", sSubTrgtListId);
            String sTrgtListId =(String)mParam.get("trgt_list_id") ;
            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);
            hmParam.put("sub_trgt_list_id",sSubTrgtListId);
            ParamUtils.addSaveParam(hmParam);
            ParamUtils.addCenterParam(hmParam);
            getChkAlct(hmParam);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            //02.이미 할당건인지 아닌지 체크
            if("Y".equals(AlctYn)){
                mParam.put("save_fg","Y");
                int Cnt = trgtCustAlctDAO.updateChangeCnsr(mParam);
                if(Cnt == 0){ //처리완료건
                    -- nEnd;
                }else{
                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){// 웹디비
                        if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                        	mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                        	mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", mParam.get("prmv_id"));
                        mParam.put("call_stts", "진행중");
                        String userNm=userDAO.getUserNm(mParam);
                        mParam.put("user_nm", userNm);

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                        ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }
                }
            }else{
                mParam.put("cmpg_id",sCmpgId);
                int Cnt = trgtCustAlctDAO.insertTgtCustAssign(mParam);
                if(Cnt == 0){
                    --nEnd;
                }else{
                   //할당건이 아닌경우
                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){

                        if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){	
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                            mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", mParam.get("prmv_id"));
                        mParam.put("call_stts", "진행중");
                        String userNm=userDAO.getUserNm(mParam);
                        mParam.put("user_nm", userNm);

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }

                }
            }
        }
        return 0;

    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNewTypeNumSelectAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sAlctNum = StringUtils.defaultString((String) pmParam.get("sAlctNum")); //하위
        Map<String, Object> mParam = new HashMap<String, Object>();

        int nAlctNum =  Integer.parseInt(sAlctNum); //분배수
        int nEnd = 0;
        
		for(int i = 0; listInDs.size() > i; i++){
		    if(nEnd == nAlctNum){
		        break;
		    }
		    nEnd++;
		    mParam = listInDs.get(i);
		    
	        ParamUtils.addSaveParam(mParam);
	        ParamUtils.addCenterParam(mParam);	
	        
		    mParam.put("cntr_cd","CCA");
		    mParam.put("cmpg_id",StringUtils.defaultString((String)pmParam.get("sCmpgId")));
		    mParam.put("user_id",StringUtils.defaultString((String)pmParam.get("sCnsrId")));
		    
		    System.out.println(mParam);
		    nCnt = trgtCustAlctDAO.insertTgtCustAssign(mParam);		    		    		    
		}
        return nCnt;
    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertSelectAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("sCmpgDivCd")); //상위
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("sCmpgTypCd")); //하위

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));
            mParam.put("user_id", sCnsrId);

            String sSubTrgtListId = StringUtils.defaultString((String)mParam.get("sub_trgt_list_id")); //하위

            //hmParam.put("sub_trgt_list_id", sSubTrgtListId);
            String sTrgtListId =(String)mParam.get("trgt_list_id") ;
            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);
            hmParam.put("sub_trgt_list_id",sSubTrgtListId);
            ParamUtils.addSaveParam(hmParam);
            ParamUtils.addCenterParam(hmParam);
            getChkAlct(hmParam);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            //02.이미 할당건인지 아닌지 체크
            if("Y".equals(AlctYn)){
                mParam.put("save_fg","Y");
                int Cnt = trgtCustAlctDAO.updateChangeCnsr(mParam);
                if(Cnt == 0){ //처리완료건

                }else{
                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                    	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                            mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", mParam.get("prmv_id"));
                        mParam.put("call_stts", "진행중");
                        String userNm=userDAO.getUserNm(mParam);
                        mParam.put("user_nm", userNm);

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }
                }
            }else{
                mParam.put("cmpg_id",sCmpgId);
                int Cnt = trgtCustAlctDAO.insertTgtCustAssign(mParam);
                if(Cnt == 0){

                }else{
                   //할당건이 아닌경우
                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){

                    	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                            mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", mParam.get("prmv_id"));
                        mParam.put("call_stts", "진행중");
                        String userNm=userDAO.getUserNm(mParam);
                        mParam.put("user_nm", userNm);

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }
                }
            }
        }
        return nCnt;
    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNewTypeSelectAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));

            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            
            mParam.put("cntr_cd","CCA");
            mParam.put("cmpg_id",sCmpgId);
            mParam.put("user_id",sCnsrId);
                                 
            //이미 분배된 대상자는 update 신규는insert 처리
            if("Y".equals(AlctYn)){
            	mParam.put("save_fg","Y");
            	nCnt = trgtCustAlctDAO.updateChangeCnsr(mParam);
            } else {
            	mParam.put("cnsr_id",sCnsrId);
            	nCnt = trgtCustAlctDAO.insertTgtCustAssign(mParam);	
            } 
            
            //캠페인 할당 등록 (아웃바운드 캠페인 선택시 해당 캠페인에 등록된 상담원인지 체크하는 부분)
            if (nCnt > 0){            	
                getChkAlct(mParam);	            	
            }
            
        }        
        return nCnt;
    }

    /**
     * 캠페인 대상고객에 상담사를 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertTgtCustAssign(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        String[] sUserIds = StringUtils.defaultString((String)pmParam.get("selectcheck")).split(",");
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("cmpg_id"));
        String sTrgtListId = StringUtils.defaultString((String)pmParam.get("trgt_list_id"));
        String sSubTrgtListId = StringUtils.defaultString((String)pmParam.get("sub_trgt_list_id"));
        String sMode = StringUtils.defaultString((String)pmParam.get("mode"));
        String sCntrCd = StringUtils.defaultString((String)pmParam.get("cntr_cd"));
        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("cmpg_div_cd"));
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("cmpg_typ_cd"));
        String sRgsrId = StringUtils.defaultString((String)pmParam.get("rgsr_id"));
        String sAmndId = StringUtils.defaultString((String)pmParam.get("amnd_id"));
        int nListPoint = 0; // 대상리스트 포인트

        // To_Do : 1) 할당 대상 고객 조회하기. TB_TRGT_CUST_ALCT 테이블의 할당되지 않은 고객리스트정보
        List<Map<String, Object>> mTrgtList = trgtCustAlctDAO.getTgtCustNonAlctList(pmParam);

        // To_Do : 2) TB_TRGT_CUST_ALCT(대상고객할당) 테이블 등록
        for (Map<String, Object> trgt : mTrgtList) {
            String nTrgtCustDtptId = trgt.get("trgt_cust_dtpt_id").toString();
            String sPrmvId ="";


            if(trgt.get("prmv_id") != null){
                sPrmvId = trgt.get("prmv_id").toString();
            }

            Map<String, Object> mParam = new HashMap<String, Object>();


            mParam.put("cmpg_id", sCmpgId);
            mParam.put("trgt_list_id", sTrgtListId);
            mParam.put("sub_trgt_list_id", sSubTrgtListId);
            mParam.put("trgt_cust_dtpt_id", nTrgtCustDtptId);

            mParam.put("user_id", sUserIds[nListPoint%sUserIds.length]);		// listPoint%userids.length <- 선택된 상담사를 한명씩 번갈아 가면서 선택
            mParam.put("cntr_cd", sCntrCd);
            mParam.put("rgsr_id", sRgsrId);
            mParam.put("amnd_id", sAmndId);

            switch(sMode) {
                case "EQAS" :
                    nResult += trgtCustAlctDAO.insertTgtCustAssign(mParam);
                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                    	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                            mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", sPrmvId);
                        mParam.put("call_stts", "진행중");
                        String userNm=userDAO.getUserNm(mParam);
                        mParam.put("user_nm", userNm);

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("prmv_id", sPrmvId);
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            extcMstTrgtService.updateUnpy(mParam);
                        }
                    }
                break;
                case "RDAS" :
                    int nIntRdasnum = StringUtils.defaultString((String)pmParam.get("rdasnum")).equals("") ? 0 : Integer.parseInt(StringUtils.defaultString((String)pmParam.get("rdasnum")));

                    if (nIntRdasnum*sUserIds.length > nListPoint) {
                        nResult += trgtCustAlctDAO.insertTgtCustAssign(mParam);
                        if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                        		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                        	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                                mParam.put("b2b_stts", "SMART");
                            }else if("TAR99999999999998".equals(sCmpgId)){
                                mParam.put("b2b_stts", "LGB2B");
                            }else if("TAR99999999999997".equals(sCmpgId)){
                                mParam.put("b2b_stts", "DAEKYO");
                            }else if("TAR99999999999996".equals(sCmpgId)){
                                mParam.put("b2b_stts", "GSSUPER");

                            // 추가생성
                            }else if("TAR99999999999995".equals(sCmpgId)){
                                mParam.put("b2b_stts", "LGU");
                            }else if("TAR99999999999994".equals(sCmpgId)){
                                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999993".equals(sCmpgId)){
                                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999992".equals(sCmpgId)){
                                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999991".equals(sCmpgId)){
                                //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("CMP202102268151057".equals(sCmpgId)){
                                mParam.put("b2b_stts", "LGU");
                            }else if("CMP202105119262676".equals(sCmpgId)){
                                mParam.put("b2b_stts", "LGB2B");
                            }else if("CMP202207135923265".equals(sCmpgId)){
                                mParam.put("b2b_stts", "DLIVE");
                            }else if("CMP202207286104304".equals(sCmpgId)){
                                mParam.put("b2b_stts", "CUCKOO");
                            }

                            mParam.put("osc_cnsl_seq", sPrmvId);
                            mParam.put("call_stts", "진행중");
                            String userNm=userDAO.getUserNm(mParam);
                            mParam.put("user_nm", userNm);

                            // 온라인 상담사 등록 처리상태 변경
                            extcTrgtDAO.updateOnlineStatAlct(mParam);
                        }
                        /* 미납관리 신규로 인한 로직 삭제 (임동진_20190201)
                        if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                            if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                    ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                                mParam.put("prmv_id", sPrmvId);
                                mParam.put("updateOption", "alct");
                                mParam.put("alct_yn", "Y");
                                extcMstTrgtService.updateUnpy(mParam);
                            }
                        }
                        */
                    }
               break;
            }
            nListPoint++;
        }
        return nResult;
    }

    public int getChkAlct(Map<String, Object> mParam) throws Exception {
        int nCnt = 0;
        nCnt = cmpgAlctDAO.getChkAlct(mParam);

        if(nCnt == 0){
            cmpgAlctDAO.insertCmpgAlct(mParam);
        }
        return nCnt;
    }


    public int insertAlct(Map<String, Object> mParam) throws Exception {
        return trgtCustAlctDAO.insertTgtCustAssign(mParam);
    }

    public int insertTgtCustAlctFromSystem(Map<String, Object> mParam) throws Exception {
        return trgtCustAlctDAO.insertTgtCustAlctFromSystem(mParam);
    }


    /**
     * 캠페인 대상고객 할당을 회수한다. 사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int deleteTgtcustassign(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        String[] sUserIds = StringUtils.defaultString((String) pmParam.get("selectcheck")).split(",");// 수치 할당 대상 상담사정보

        String sMode = StringUtils.defaultString((String)pmParam.get("mode"));
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("cmpg_id"));
        String sTrgtListId = StringUtils.defaultString((String)pmParam.get("trgt_list_id"));
        String sSubTrgtListId = StringUtils.defaultString((String)pmParam.get("sub_trgt_list_id"));
        String sCntrCd = StringUtils.defaultString((String)pmParam.get("cntr_cd"));
        String sWithDrawNum = StringUtils.defaultString((String)pmParam.get("rdwdnum"));
        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("cmpg_div_cd"));
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("cmpg_typ_cd"));

        for (int idx = 0; idx < sUserIds.length; idx++) {
            String _userid = StringUtils.defaultString(String.valueOf(sUserIds[idx]));
            Map<String, Object> mParam = new HashMap<String, Object>();
            mParam.put("trgt_list_id", sTrgtListId);
            mParam.put("cmpg_id", sCmpgId);
            mParam.put("sub_trgt_list_id", sSubTrgtListId);
            mParam.put("user_id", _userid);
            mParam.put("cntr_cd", sCntrCd);

            switch(sMode) {
            case "EQWD" :
                ParamUtils.addSaveParam(mParam);
                if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                    mParam.put("cnsr_id", _userid);
                    List<Map<String, Object>> mOnlineIds = trgtCustDAO.onlineId(mParam);
                    for (Map<String, Object> trgt : mOnlineIds) {
                    	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                            mParam.put("b2b_stts", "SMART");
                        }else if("TAR99999999999998".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("TAR99999999999997".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DAEKYO");
                        }else if("TAR99999999999996".equals(sCmpgId)){
                            mParam.put("b2b_stts", "GSSUPER");

                        // 추가생성
                        }else if("TAR99999999999995".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("TAR99999999999994".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999993".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999992".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("TAR99999999999991".equals(sCmpgId)){
                            //mParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                        }else if("CMP202102268151057".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGU");
                        }else if("CMP202105119262676".equals(sCmpgId)){
                            mParam.put("b2b_stts", "LGB2B");
                        }else if("CMP202207135923265".equals(sCmpgId)){
                            mParam.put("b2b_stts", "DLIVE");
                        }else if("CMP202207286104304".equals(sCmpgId)){
                            mParam.put("b2b_stts", "CUCKOO");
                        }

                        mParam.put("osc_cnsl_seq", trgt.get("prmv_id"));
                        mParam.put("call_stts", "할당대기");

                        // 온라인 상담사 등록 처리상태 변경
                        extcTrgtDAO.updateOnlineStatAlct(mParam);
                    }
                }

                if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                    if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                            ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                        mParam.put("cnsr_id", _userid);
                        List<Map<String, Object>> mOnlineIds = trgtCustDAO.onlineId(mParam);
                        for (Map<String, Object> trgt : mOnlineIds) {
                            mParam.put("prmv_id",trgt.get("prmv_id"));
                            mParam.put("updateOption", "alct_d");
                            mParam.put("alct_yn", "N");
                            extcMstTrgtService.updateUnpy(mParam);
                        }
                    }
                }
                // To_Do : 1) TB_TRGT_CUST_ALCT 삭제
                nResult += trgtCustAlctDAO.deleteTgtcustassign(mParam);
                break;
            case "RDWD" :
                mParam.put("delcount", sWithDrawNum);
                List<Map<String, Object>> mDelList =  extcMstTrgtService.getDeleteTrgtCustAlct(mParam);

                for (Map<String, Object> deltrgt : mDelList) {
                    deltrgt.put("user_id", _userid);
                    deltrgt.put("delOption", "del");
                    ParamUtils.addSaveParam(deltrgt);

                    if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
                    		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                        deltrgt.put("cnsr_id", _userid);
                        List<Map<String, Object>> mOnlineIds = trgtCustDAO.onlineId(deltrgt);
                        for (Map<String, Object> trgt : mOnlineIds) {
                        	if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                                deltrgt.put("b2b_stts", "SMART");
                            }else if("TAR99999999999998".equals(sCmpgId)){
                                deltrgt.put("b2b_stts", "LGB2B");
                            }else if("TAR99999999999997".equals(sCmpgId)){
                                deltrgt.put("b2b_stts", "DAEKYO");
                            }else if("TAR99999999999996".equals(sCmpgId)){
                                deltrgt.put("b2b_stts", "GSSUPER");

                            // 추가생성
                            }else if("TAR99999999999995".equals(sCmpgId)){
                                deltrgt.put("b2b_stts", "LGU");
                            }else if("TAR99999999999994".equals(sCmpgId)){
                                //deltrgt.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999993".equals(sCmpgId)){
                                //deltrgt.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999992".equals(sCmpgId)){
                                //deltrgt.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("TAR99999999999991".equals(sCmpgId)){
                                //deltrgt.put("b2b_stts", "GSSUPER");  //변경 >>>
                            }else if("CMP202102268151057".equals(sCmpgId)){
                            	deltrgt.put("b2b_stts", "LGU");
                            }else if("CMP202105119262676".equals(sCmpgId)){
                            	deltrgt.put("b2b_stts", "LGB2B");
                            }else if("CMP202207286104304".equals(sCmpgId)){
                                mParam.put("b2b_stts", "CUCKOO");
                            }

                            deltrgt.put("osc_cnsl_seq", trgt.get("prmv_id"));
                            deltrgt.put("call_stts", "할당대기");

                            // 온라인 상담사 등록 처리상태 변경
                            extcTrgtDAO.updateOnlineStatAlct(deltrgt);
                        }
                    }

                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            deltrgt.put("cnsr_id", _userid);
                            List<Map<String, Object>> mOnlineIds = trgtCustDAO.onlineId(deltrgt);
                            for (Map<String, Object> trgt : mOnlineIds) {
                                deltrgt.put("prmv_id",trgt.get("prmv_id"));
                                deltrgt.put("updateOption", "alct_d");
                                deltrgt.put("alct_yn", "N");
                                extcMstTrgtService.updateUnpy(deltrgt);
                            }
                        }
                    }
                    // To_Do : 1) TB_TRGT_CUST_ALCT 삭제
                    nResult += trgtCustAlctDAO.deleteTgtcustassign(deltrgt);
                }
                break;
            }
        }
        return nResult;

    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getTrgtCustAlctCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctCount(pmParam);
    }

    /**
     * 캠페인 전체에 할당된 건수를 가져온다  사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getAlctCount(pmParam);
    }


    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getTrgtAlctCustCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtAlctCustCount(pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getTrgtCustAlctList(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctList(pmParam);
    }
    
    /**
     * 대상고객별 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtAlctCustList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getTrgtAlctCustList(pmParam);
    }


    /**
     * 대상고객별 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtAlctCustList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtAlctCustList(pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getTgtCustAlctCountByCust(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getTgtCustAlctCountByCust(pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctListByCust(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getTrgtCustAlctListByCust(pmParam);
    }

    /**
     * 캠페인 결과정보를 수정한다.(ajax)
     *
     * @param pmParam 캠페인 결과정보
     * @throws Exception
     */
    public int updateCmpgResult(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        //trgtCustAlctDAO.updateTrgtCustByCustId(pmParam);
        nResult = trgtCustAlctDAO.updateCmpgResult(pmParam);
        if(nResult > 0){
            dpmsReslHstrDAO.insertDpmsReslHstr(pmParam); // 히스토리
            String sCmpgId = (String)pmParam.get("cmpg_id");
            String sTrgtListId =(String)pmParam.get("trgt_list_id") ;
            if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)
            		|| "CMP202204063724405".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
                pmParam.put("osc_cnsl_seq", pmParam.get("prmv_id"));

                if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId) || "CMP202204063724405".equals(sCmpgId)){
                    pmParam.put("b2b_stts", "SMART");
                }else if("TAR99999999999998".equals(sCmpgId)){
                    pmParam.put("b2b_stts", "LGB2B");
                }else if("TAR99999999999997".equals(sCmpgId)){
                    pmParam.put("b2b_stts", "DAEKYO");
                }else if("TAR99999999999996".equals(sCmpgId)){
                    pmParam.put("b2b_stts", "GSSUPER");

                // 추가생성
                }else if("TAR99999999999995".equals(sCmpgId)){
                    pmParam.put("b2b_stts", "LGU");
                }else if("TAR99999999999994".equals(sCmpgId)){
                    //pmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                }else if("TAR99999999999993".equals(sCmpgId)){
                    //pmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                }else if("TAR99999999999992".equals(sCmpgId)){
                    //pmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                }else if("TAR99999999999991".equals(sCmpgId)){
                    //pmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
                }else if("CMP202102268151057".equals(sCmpgId)){
                	pmParam.put("b2b_stts", "LGU");
                }else if("CMP202105119262676".equals(sCmpgId)){
                	pmParam.put("b2b_stts", "LGB2B");
                }else if("CMP202207135923265".equals(sCmpgId)){
                	pmParam.put("b2b_stts", "DLIVE");
                }else if("CMP202207286104304".equals(sCmpgId)){
                	pmParam.put("b2b_stts", "CUCKOO");
                }

                String userNm=userDAO.getUserNm(pmParam);
                pmParam.put("user_nm", userNm);

                ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                String sIpAddr = oServletRequestAttribute.getRequest().getRemoteAddr();

                pmParam.put("ip_addr", sIpAddr);
                
                // 온라인 상담사 등록 처리상태 변경
                extcTrgtDAO.updateOnlineStatAlct(pmParam);
                // 온라인 상담 이력 등록
                extcTrgtDAO.insertOnlnCons(pmParam);
            }
            if("CMP202107289160790".equals(sCmpgId) || "CMP202108301483542".equals(sCmpgId) || "CMP202202223898890".equals(sCmpgId) || "CMP202202223898904".equals(sCmpgId)){
            	// 온라인 상담 이력 등록
            	pmParam.put("id_no", pmParam.get("user_defn5_cntn"));
            	
            	String userNm=userDAO.getUserNm(pmParam);
                pmParam.put("user_nm", userNm);

                ServletRequestAttributes oServletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                String sIpAddr = oServletRequestAttribute.getRequest().getRemoteAddr();

                pmParam.put("ip_addr", sIpAddr);
                
                extcTrgtDAO.insertOnlnUplusCons(pmParam);
            }
        }

        return nResult;
    }

    /**
     * 캠페인 결과정보를 조회한다.(ajax)
     *
     * @param pmParam 검색 조건
     * @return 캠페인 결과정보
     * @throws Exception
     */
    public Map<String, Object> getCmpgResult(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getCmpgResult(pmParam);
    }



    /**
     * 캠페인별 배정 검색 수를 반환한다. (사용안하는 듯)
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getTgtCustAssignCountByCampaign(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getTgtCustAssignCountByCampaign(pmParam);
    }

    /**
     * 캠페인별 배정 정보를 검색한다. (사용안하는 듯)
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTgtCustAssignListByCampaign(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getTgtCustAssignListByCampaign(pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 할당정보 Key (TRGT_LIST_ID,CNSR_ID,CMPG_ID,TRGT_CUST_DTPT_ID)
     * @return 할당정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrgtCustAlct(Map<String, Object> pmParam) throws Exception {
        return trgtCustAlctDAO.getTrgtCustAlct(pmParam);
    }

    /**
     * 캠페인 대상리스트 고객아이디를 수정한다.(ajax)
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateTrgtCustCustId(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.updateTrgtCustCustId(pmParam);
    }

    /**
     * CitId를 업데이트한다
     *
     * @param pmParam 캠페인 정보
     * @throws Exception
     */
    public String updateCtiId(Map<String, Object> pmParam) throws Exception {
           trgtCustAlctDAO.updateCtiId(pmParam);
           String sResultMsg = pmParam.get("cti_crnc_dtl_id").toString();
        return sResultMsg;
    }

    /**
     * 고객별 아웃바운드 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getCustHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getCustHstrCount(pmParam);
    }

    /**
     * 고객별 아웃바운드 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객별 아웃바운드 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCustHstrList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getCustHstrList(pmParam);
    }

    /**
     * 캠페인 대상고객을 할당 변경한다. (상담사 변경) !>>>>
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int updateChangeCnsr(Map<String, Object> pmParam) throws Exception {

        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("sCmpgDivCd"));
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("sCmpgTypCd"));

        String sCmpgId = StringUtils.defaultString((String) pmParam.get("sCmpgId"));
        String sTrgtListId =StringUtils.defaultString((String) pmParam.get("sTrgtListId"));
        Map<String, Object> hmParam = pmParam;
        hmParam.put("cnsr_id", StringUtils.defaultString((String)pmParam.get("cnsr_id")));
        hmParam.put("user_id", StringUtils.defaultString((String)pmParam.get("user_id")));

        if(sCmpgId.equals(sTrgtListId) || "CMP202102268151057".equals(sCmpgId) || "CMP202105119262676".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId)
        		|| "CMP202202223900650".equals(sCmpgId) || "CMP202207135923265".equals(sCmpgId) || "CMP202207286104304".equals(sCmpgId)){
            String userNm=userDAO.getUserNm(hmParam);
            hmParam.put("user_nm", userNm);

            if("TAR99999999999999".equals(sCmpgId) || "CMP202202223900655".equals(sCmpgId) || "CMP202202223900650".equals(sCmpgId)){
                hmParam.put("b2b_stts", "SMART");
            }else if("TAR99999999999998".equals(sCmpgId)){
                hmParam.put("b2b_stts", "LGB2B");
            }else if("TAR99999999999997".equals(sCmpgId)){
                hmParam.put("b2b_stts", "DAEKYO");
            }else if("TAR99999999999996".equals(sCmpgId)){
                hmParam.put("b2b_stts", "GSSUPER");

            // 추가생성
            }else if("TAR99999999999995".equals(sCmpgId)){
                hmParam.put("b2b_stts", "LGU");
            }else if("TAR99999999999994".equals(sCmpgId)){
                //hmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999993".equals(sCmpgId)){
                //hmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999992".equals(sCmpgId)){
                //hmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("TAR99999999999991".equals(sCmpgId)){
                //hmParam.put("b2b_stts", "GSSUPER");  //변경 >>>
            }else if("CMP202102268151057".equals(sCmpgId)){
                hmParam.put("b2b_stts", "LGU");
            }else if("CMP202105119262676".equals(sCmpgId)){
            	hmParam.put("b2b_stts", "LGB2B");
            }else if("CMP202207135923265".equals(sCmpgId)){
            	hmParam.put("b2b_stts", "DLIVE");
            }else if("CMP202207286104304".equals(sCmpgId)){
            	hmParam.put("b2b_stts", "CUCKOO");
            }

            String sPrmvId = StringUtils.defaultString((String) hmParam.get("prmv_id"));
            hmParam.put("osc_cnsl_seq", sPrmvId);
            hmParam.put("call_stts", "진행중");

            // 온라인 상담사 등록 처리상태 변경
            extcTrgtDAO.updateOnlineStatAlct(hmParam);
        }

        if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
            if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                    ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                hmParam.put("updateOption", "alct");
                hmParam.put("alct_yn", "Y");
                hmParam.put("cmpg_id",sCmpgId);
                extcMstTrgtService.updateUnpy(hmParam);
            }
        }

        return trgtCustAlctDAO.updateChangeCnsr(pmParam);
    }


    /**
     * 캠페인 처리현황(상담사별)의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 처리현황(상담사별)의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustAlctByCnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getTrgtCustAlctByCnsrCount(pmParam);
    }

    /**
     * 캠페인 처리현황(상담사별)을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustAlctByCnsrList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getTrgtCustAlctByCnsrList(pmParam);
    }

    /**
     * 캠페인 중복할당 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 중복할당  검색 건수
     * @throws Exception
     */
    public int getDupAlctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getDupAlctCount(pmParam);
    }

    /**
     * 캠페인 중복할당을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 중복할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDupAlctList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getDupAlctList(pmParam);
    }

    /**
     * 캠페인 중복할당 삭제한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 중복할당  삭제
     * @throws Exception
     */
    public int deleteDupAlct(Map<String, Object> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.deleteDupAlct(pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctDirectCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctDirectCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctDirectList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctDirectList(pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNewTypeSelectDirectAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));

            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            
            mParam.put("cntr_cd","CCA");
            mParam.put("cmpg_id",sCmpgId);
            mParam.put("user_id",sCnsrId);
                                 
            //이미 분배된 대상자는 update 신규는insert 처리
            if("Y".equals(AlctYn)){
            	mParam.put("save_fg","Y");
            	nCnt = trgtCustAlctDAO.updateChangeDirectCnsr(mParam);
            } else {
            	mParam.put("cnsr_id",sCnsrId);
            	nCnt = trgtCustAlctDAO.insertTgtCustDirectAlct(mParam);	
            } 
            
            //캠페인 할당 등록 (아웃바운드 캠페인 선택시 해당 캠페인에 등록된 상담원인지 체크하는 부분)
            if (nCnt > 0){            	
                getChkAlct(mParam);	            	
            }
            
        }        
        return nCnt;
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainDirectCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctMainDirectCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainDirectList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctMainDirectList(pmParam);
    }
    
    /**
     * 다이렉트 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.updateDirectSessionClose(pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctUplusCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctUplusCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctUplusList(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctUplusPopList(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList2(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctUplusPopList2(pmParam);
    }
    
    /**
     * 웹 DB링크 세션 종료
     *
     * @param pmParam 캠페인 대상리스트 고객아이디 정보
     * @throws Exception
     */
    public int updateUplusSessionClose(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.updateUplusSessionClose(pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNewTypeSelectUplusAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));

            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            
            mParam.put("cntr_cd","CCA");
            mParam.put("cmpg_id",sCmpgId);
            mParam.put("user_id",sCnsrId);
                                 
            //이미 분배된 대상자는 update 신규는insert 처리
            if("Y".equals(AlctYn)){
            	mParam.put("save_fg","Y");
            	nCnt = trgtCustAlctDAO.updateChangeUplusCnsr(mParam);
            } else {
            	mParam.put("cnsr_id",sCnsrId);
            	nCnt = trgtCustAlctDAO.insertTgtCustUplusAlct(mParam);	
            } 
            
            //캠페인 할당 등록 (아웃바운드 캠페인 선택시 해당 캠페인에 등록된 상담원인지 체크하는 부분)
            if (nCnt > 0){            	
                getChkAlct(mParam);	            	
            }
            
        }        
        return nCnt;
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainUplusCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctMainUplusCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainUplusList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctMainUplusList(pmParam);
    }
    
    /**
     * 캠페인 대상고객에 PDS를 연동한다
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertSelectPdsIntr(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        String sCmpgDivCd = StringUtils.defaultString((String)pmParam.get("sCmpgDivCd")); //상위
        String sCmpgTypCd = StringUtils.defaultString((String)pmParam.get("sCmpgTypCd")); //하위

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));
            mParam.put("user_id", sCnsrId);

            String sSubTrgtListId = StringUtils.defaultString((String)mParam.get("sub_trgt_list_id")); //하위

            //hmParam.put("sub_trgt_list_id", sSubTrgtListId);
            String sTrgtListId =(String)mParam.get("trgt_list_id") ;
            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);
            hmParam.put("sub_trgt_list_id",sSubTrgtListId);
            ParamUtils.addSaveParam(hmParam);
            ParamUtils.addCenterParam(hmParam);
            getChkAlct(hmParam);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            //02.이미 할당건인지 아닌지 체크
            if("Y".equals(AlctYn)){
                mParam.put("save_fg","Y");
                int Cnt = trgtCustAlctDAO.updateChangeCnsr(mParam);
                if(Cnt == 0){ //처리완료건

                }else{
                	//PDS테이블에 INSERT
                	hmParam.put("mem_no",mParam.get("mem_no"));
                	hmParam.put("accnt_no",mParam.get("accnt_no"));
                	hmParam.put("mem_nm",mParam.get("cust_nm"));
                	hmParam.put("tel",mParam.get("clph_tlno"));
                	hmParam.put("memo","캠페인PDS연동");
                	hmParam.put("memo3",mParam.get("trgt_cust_dtpt_id"));
                	hmParam.put("reg_man",sCnsrId);
                	
                	if("DF".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertDF(hmParam);
                	} else if ("CD".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertCD(hmParam);
                	} else if ("HC".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertHC(hmParam);
                	}
                	
                	if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }
                }
            }else{
                mParam.put("cmpg_id",sCmpgId);
                int Cnt = trgtCustAlctDAO.insertTgtCustAssign(mParam);
                if(Cnt == 0){

                }else{
                	//PDS테이블에 INSERT
                	hmParam.put("mem_no",mParam.get("mem_no"));
                	hmParam.put("accnt_no",mParam.get("accnt_no"));
                	hmParam.put("mem_nm",mParam.get("cust_nm"));
                	hmParam.put("tel",mParam.get("clph_tlno"));
                	hmParam.put("memo","캠페인PDS연동");
                	hmParam.put("memo3",mParam.get("trgt_cust_dtpt_id"));
                	hmParam.put("reg_man",sCnsrId);
                	
                	if("DF".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertDF(hmParam);
                	} else if ("CD".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertCD(hmParam);
                	} else if ("HC".equals(pmParam.get("pdsGubun"))) {
                		pdsService.insertHC(hmParam);
                	}
                	
                    if(sCmpgDivCd.equals("2000") || sCmpgDivCd.equals("3000")){ //미납
                        if(sCmpgTypCd.equals("2100")||sCmpgTypCd.equals("2200")||sCmpgTypCd.equals("2300")||sCmpgTypCd.equals("2400")
                                ||sCmpgTypCd.equals("3100")||sCmpgTypCd.equals("3200")||sCmpgTypCd.equals("3400")){
                            mParam.put("updateOption", "alct");
                            mParam.put("alct_yn", "Y");
                            mParam.put("cmpg_id",sCmpgId);
                            extcMstTrgtDAO.updateUnpy(mParam);
                        }
                    }
                }
            }
        }
        return nCnt;
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMangiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctMangiCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMangilist(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctMangilist(pmParam);
    }
    
    /**
     * 캠페인 대상고객에 상담사를 선택 할당한다.  사용
     *
     * @param pmParam 캠페인대상고객할당 정보
     * @throws Exception
     */
    public int insertNewTypeSelectMangiAlct(Map<String, Object> pmParam , DataSetMap listInDs) throws Exception {
        int nCnt = 0;
        String sCnsrId = StringUtils.defaultString((String)pmParam.get("sCnsrId")); //변경할 id
        String sCmpgId = StringUtils.defaultString((String)pmParam.get("sCmpgId")); //캠페인 id

        Map<String, Object> mParam = new HashMap<String, Object>();

        for(int i = 0; listInDs.size() > i; i++){

            mParam = listInDs.get(i);

            String AlctYn = StringUtils.defaultString((String)mParam.get("alct_yn"));

            // 01 . 있는지 없는지 체크 없으면 TB_CMPG_ALCT_DTL 에 등록
            Map<String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("cnsr_id", sCnsrId);
            hmParam.put("cmpg_id",sCmpgId);

            ParamUtils.addSaveParam(mParam);
            ParamUtils.addCenterParam(mParam);
            
            mParam.put("cntr_cd","CCA");
            mParam.put("cmpg_id",sCmpgId);
            mParam.put("user_id",sCnsrId);
                                 
            //이미 분배된 대상자는 update 신규는insert 처리
            if("Y".equals(AlctYn)){
            	mParam.put("save_fg","Y");
            	nCnt = trgtCustAlctDAO.updateChangeMangiCnsr(mParam);
            } else {
            	mParam.put("cnsr_id",sCnsrId);
            	nCnt = trgtCustAlctDAO.insertTgtCustMangiAlct(mParam);
            } 
            
            //캠페인 할당 등록 (아웃바운드 캠페인 선택시 해당 캠페인에 등록된 상담원인지 체크하는 부분)
            if (nCnt > 0){            	
                getChkAlct(mParam);	            	
            }
            
        }        
        return nCnt;
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다. 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustAlctMainMangiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) trgtCustAlctDAO.getNewTypeTrgtCustAlctMainMangiCount(pmParam);
    }
    
    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainMangiList(Map<String, ?> pmParam) throws Exception {
        return trgtCustAlctDAO.getNewTypeTrgtCustAlctMainMangiList(pmParam);
    }
}
