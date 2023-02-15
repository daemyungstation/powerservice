/*
 * (@)# UserService.java
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

package powerservice.business.usr.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.impl.DlwEmplDAO;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.impl.FileDAO;
import powerservice.business.usr.service.UserService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.crypto.SHA256;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID User
 */
@Service
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    public UserDAO userDAO;

    @Resource
    public DlwEmplDAO dlwEmplDAO;

    @Resource
    public FileDAO fileDAO;

    @Resource
    public BasVlService basVlService;

    /**
     * 사용자 정보를 입력한다.
     *
     * @param pmParam 사용자 정보
     * @return
     * @throws Exception
     */
    public String insertUser(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        String sUseYn = StringUtils.defaultString((String) pmParam.get("use_yn"));

        int nResult = userDAO.insertUser(pmParam);

        if (nResult > 0) {
            sKey = (pmParam.get("lgn_id") + "").toUpperCase();

            String sClphNo = StringUtils.defaultString((String) pmParam.get("clph_no"));
            if (!"".equals(sClphNo)) {
                pmParam.put("clph_no", CommonUtils.convertPhoneString(sClphNo));
            }

            pmParam.put("user_id", sKey);

            if ("".equals(sUseYn) || "N".equals(sUseYn)) {
                pmParam.put("del_fg", "Y");
            } else {
                pmParam.put("del_fg", "N");
            }
            dlwEmplDAO.mergeEmpl(pmParam);

            // 첨부파일 업데이트
            updateFile(pmParam);
        }
        return sKey;
    }

    /**
     * 사용자 정보를 수정한다.
     *
     * @param pmParam 사용자 정보
     * @throws Exception
     */
    public int updateUser(Map<String, Object> pmParam) throws Exception {
        String sUseYn = StringUtils.defaultString((String) pmParam.get("use_yn"));
        int nResult = userDAO.updateUser(pmParam);

        if (nResult > 0) {

            String sClphNo = StringUtils.defaultString((String) pmParam.get("clph_no"));
            if (!"".equals(sClphNo)) {
                pmParam.put("clph_no", CommonUtils.convertPhoneString(sClphNo));
            }

            if ("".equals(sUseYn) || "N".equals(sUseYn)) {
                pmParam.put("del_fg", "Y");
            } else {
                pmParam.put("del_fg", "N");
            }
            dlwEmplDAO.mergeEmpl(pmParam);

            // 첨부파일 업데이트
            //updateFile(pmParam);
        }
        return nResult;
    }

    /**
     * 첨부파일에 사용자 아이디를 업데이트 한다.
     *
     * @param pmParam 사용자 정보
     * @throws Exception
     */
    public void updateFile(Map<String, Object> pmParam) throws Exception {
        pmParam.put("rltn_item_id", pmParam.get("user_id"));

        // 파일수정시 fileid로 넘어옴.
        @SuppressWarnings("unchecked")
        List<String> sfileIdList = (ArrayList<String>) pmParam.get("fileIds");
        String sFileId = (String) pmParam.get("file_id");
        if (sFileId != null) {
            sfileIdList.add(sFileId);
        }

        // 파일 삭제
        fileDAO.deleteFile(pmParam);

        // 첨부파일 업데이트
        if (sfileIdList != null && sfileIdList.size() > 0) {
            fileDAO.updateFile(pmParam);
        }
    }

    /**
     * 비밀번호를 변경한다.
     *
     * @pmParam pmParam 비밀번호
     * @return
     * @throws Exception
     */
    public int updateUserPassword(Map<String, ?> pmParam) throws Exception {
        return userDAO.updateUserPassword(pmParam);
    }

    /**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) userDAO.getUserCount(pmParam);
    }

    /**
     * 사용자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception {
        return userDAO.getUserList(pmParam);
    }
    
    /**
     * 사용자 정보를 검색한다_newtype(20190729)
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeUserList(Map<String, ?> pmParam) throws Exception {
        return userDAO.getNewTypeUserList(pmParam);
    }

    /**
     * 사용자 정보를 검색한다. - 고객 선택 팝업
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList2(Map<String, ?> pmParam) throws Exception {
        return userDAO.getUserList2(pmParam);
    }

    /**
     * 사용자 상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserStatusList(Map<String, ?> pmParam) throws Exception {
        return userDAO.getUserStatusList(pmParam);
    }

    /**
     * 기준값 정보를 검색한다.
     *
     * @param id 기준값 ID
     * @return 기준값 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getUser(Map<String, ?> pmParam) throws Exception {
        return userDAO.getUser(pmParam);
    }

    /**
     * 사용자 상태 정보를 저장한다.
     *
     * @param 사용자 상태정보
     * @return
     * @throws Exception
     */
    public int mergeChnlStatInfo(Map<String, ?> pmParam) throws Exception {
        return userDAO.mergeChnlStatInfo(pmParam);
    }

    /**
     * 대명 사원정보 Merge
     *
     * @param 없음
     * @return 팀 리스트
     * @throws Exception
     */
    public void mergeDlwEmpl() throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        // 초기 비빌번호 기준값 암호화
        String sInitPassword = basVlService.getBasVlAsString("init_password");
        if (sInitPassword != null && !"".equals(sInitPassword)) {
            sInitPassword = SHA256.digest(sInitPassword);
        }
        mParam.put("pwd", sInitPassword);

        long nSttTm = System.currentTimeMillis();
        LOGGER.info("============ mergeDlwEmpl start ============");

        dlwEmplDAO.getSqlSession().select("DlwEmplMap.getScdlEmplList", mParam, new dlwEmplRowHandler(mParam));

        long nJobTm = System.currentTimeMillis() - nSttTm;
        LOGGER.info("============ mergeDlwEmpl end (" + (nJobTm < 1000 ? (nJobTm + "ms") : (((long) (nJobTm / 1000)) + "s")) + ") ============");
    }

    protected class dlwEmplRowHandler implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public dlwEmplRowHandler(Map<String, Object> mParam) {
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
            Map<String, Object> rslObj = null;
            Map<String, Object> rowData = null;
            try {
                rslObj = (Map<String, Object>)context.getResultObject();
                rowData = new HashMap<String, Object>();

                String sDelFg = StringUtils.defaultString((String) rslObj.get("del_fg"));
                String sCtiFg = StringUtils.defaultString((String) rslObj.get("cti_fg"));

                rowData.put("user_id",      	rslObj.get("emple_no"));
                rowData.put("user_nm",      	rslObj.get("emple_nm"));
                rowData.put("clph_no",      	rslObj.get("cell"));
                rowData.put("zpcd", 			rslObj.get("zip"));
                rowData.put("home_addr",		rslObj.get("addr"));

                rowData.put("home_dtpt_addr",   rslObj.get("addr2"));
                rowData.put("emil_addr",    	rslObj.get("email"));
                if ("N".equals(sDelFg)) {
                    rowData.put("use_yn",    	"Y");
                } else {
                    rowData.put("use_yn",    	"N");
                }

                if (sCtiFg == null || sCtiFg.equals("")) {
                    rowData.put("cti_use_yn",       "N");
                } else {
                    rowData.put("cti_use_yn",       rslObj.get("cti_fg"));
                }
                rowData.put("cti_id",    		rslObj.get("cti_id"));

                rowData.put("cti_pwd",    		rslObj.get("cti_pwd"));
                rowData.put("empl_no",    		rslObj.get("grp_emple_no"));
                rowData.put("enco_dt",    		rslObj.get("ent_day"));
                rowData.put("rtco_dt",    		rslObj.get("resin_day"));

                rowData.put("pwd",    			gmParam.get("pwd"));

                /*// insert
                rowData.put("cntr_cd",    		"CCA");
                rowData.put("pwd",    			gmParam.get("pwd"));
                rowData.put("athr_cd",    		"CC20");// 센터팀장
                rowData.put("user_typ_cd",    	"40");	// USR010 - 40(부서직원)
                rowData.put("user_stat_cd",    	"10");	// USR020 - 10(근무)
                rowData.put("duty_cd",    		"10");	// USR030 - 40(부서직원)
                rowData.put("rgsr_id",    		"ADMIN");

                // update
                rowData.put("amnd_id",    		"ADMIN");*/

                int nResult = userDAO.getSqlSession().insert("UserMap.mergeScdlUser", rowData);
                LOGGER.info("============ mergeDlwEmpl SUCCESS [" + nResult + "] ==> " + rowData);
                rowData = null;
                rslObj = null;
            } catch (Exception exception) {
                LOGGER.error(exception.getMessage() + "============ mergeDlwEmpl ERROR ==> " + rowData);
            }
        }
    }

    /**
     * 쪽지 수신대상자 목록 조회
     *
     * @param pmParam 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserListForMemo(Map<String, Object> pmParam) throws Exception {
        return userDAO.getUserListForMemo(pmParam);
    }

    /**
     * 직무관리사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getevalUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) userDAO.getevalUserCount(pmParam);
    }

    public int getsearchevalUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) userDAO.getevalUserCount(pmParam);
    }




    public List<Map<String, Object>> getevalUserList(Map<String, ?> pmParam) throws Exception {
        return userDAO.getevalUserList(pmParam);
    }

    public List<Map<String, Object>> getsearchevalUserList(Map<String, ?> pmParam) throws Exception {
        return userDAO.getsearchevalUserList(pmParam);
    }

    public void evalsave(Map<String, DataSetMap> mapInDs) throws Exception {

         Map<String, Object> hmParam = new HashMap<String, Object>();

         DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

         String msg = "";



         for (int i = 0; i < listInDs.size(); i++) {
             hmParam = listInDs.get(i);

             ParamUtils.addSaveParam(hmParam);
             hmParam.put("e_userid", hmParam.get("rgsr_id"));
             hmParam.put("reg_man", hmParam.get("rgsr_id"));
             hmParam.put("upd_man", hmParam.get("rgsr_id"));

           //  CommonUtils.printLog(hmParam);

             int dtlcnt = userDAO.getevalUserCount_dtl(hmParam);        ///// 해당월 데이터 있는 지 확인

             if (dtlcnt  < 1 )  {
                 userDAO.insert_eval_admin(hmParam);          ////  초기값이 없으면 인서트
             } else {
                 userDAO.update_eval_admin(hmParam);             //// 데이터가 있으면 업데이트 진행
             }
         }
    }


     public void evalmsave(Map<String, Object> pmParam) throws Exception {

          userDAO.update_ma_eval_admin(pmParam);

     }

     public void delevalmsave(Map<String, Object> pmParam) throws Exception {

         userDAO.update_ma_eval_admin(pmParam);

    }


     public void delevalsave(Map<String, DataSetMap> mapInDs) throws Exception {

         Map<String, Object> hmParam = new HashMap<String, Object>();

         DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

         String msg = "";



         for (int i = 0; i < listInDs.size(); i++) {
             hmParam = listInDs.get(i);

            // ParamUtils.addSaveParam(hmParam);
             //hmParam.put("e_userid", hmParam.get("rgsr_id"));
             //hmParam.put("reg_man", hmParam.get("rgsr_id"));
             //hmParam.put("upd_man", hmParam.get("rgsr_id"));

            // CommonUtils.printLog(hmParam);


                 userDAO.del_eval_admin(hmParam);          ////  delete

         }
    }

    /** ================================================================
     * 날짜 : 20200616
     * 이름 : 송준빈
     * 추가내용 : 마지막 로그인 시간 업데이트
     * 대상 테이블 : PS_WILLVI.TB_USER
     * ================================================================
     */
    public int updateLastLoginDttm(Map<String, ?> pmParam) throws Exception {
        return userDAO.updateLastLoginDttm(pmParam);
    }

	public List<Map<String, Object>> getInCallCount(Map<String, ?> mSearchParam) throws Exception{
//		return (Integer) userDAO.getevalUserCount(pmParam); 
		
		return userDAO.getInCallCount(mSearchParam);
	}

	public List<Map<String, Object>> getOutCallCount(Map<String, ?> mSearchParam) throws Exception{
		
		return userDAO.getOutCallCount(mSearchParam);
	}
	
	/**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserFormCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) userDAO.getUserFormCount(pmParam);
    }
}








