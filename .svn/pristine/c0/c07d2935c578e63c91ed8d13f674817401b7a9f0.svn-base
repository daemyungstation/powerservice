/*
 * (@)# CustBasiServiceImpl.java
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.CustBasiService;
import powerservice.business.dlw.service.impl.DlwConsProdDAO;
import powerservice.business.dlw.service.impl.DlwCustDAO;
import powerservice.core.util.SessionUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CustBasi
 */
@Service
public class CustBasiServiceImpl extends EgovAbstractServiceImpl implements CustBasiService {

    private final Logger LOGGER = LoggerFactory.getLogger(CustBasiServiceImpl.class);

    @Resource
    public CustBasiDAO custBasiDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    @Resource
    public MemberAddrDtlDAO memberAddrDtlDAO;

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    /**
     * 고객 정보를 등록한다.
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String insertCustBasi(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        String sHomeZip = StringUtils.defaultString((String)pmParam.get("home_zip")); // 우편번호

        int nResult = dlwCustDAO.insertMember(pmParam); // 대명 회원정보 등록

        //int nResult = custBasiDAO.mergeCustBasi(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("mem_no");

            if (!"".equals(sHomeZip)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    pmParam.put("bzpt_div", sTeamCd);
                } else {
                    pmParam.put("bzpt_div", "999999");
                }

                pmParam.put("rltn_cd", "10");	// 본인
                int nResultMemberAddrDtl = memberAddrDtlDAO.mergeMemberAddrDtl(pmParam);
                // 고객 주소 정보가 변경된 경우
                if (nResultMemberAddrDtl > 0) {
                    // 고객 주소 이력 저장
                    try {
                        pmParam.put("member_addr_dtl_merge_yn", "Y");
                        memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
                    } catch (Exception exception) {
                        LOGGER.error(exception.getMessage());
                    }
                }
            }
            //custBasiDAO.insertCustBasiHstr(pmParam);
            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt(sKey);
          //  CommonUtils.printLog(mDtpt);
            mDtpt.put("cntr_cd", (String)pmParam.get("cntr_cd"));
            custBasiDAO.insertCustBasiHstr(mDtpt);
        }

        return sKey;
    }

    /**
     * 고객 정보를 수정한다.
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int updateCustBasi(Map<String, Object> pmParam) throws Exception {
        int nResult = dlwCustDAO.updateMember(pmParam); // 대명 회원정보 수정
        String sHomeZip = StringUtils.defaultString((String)pmParam.get("home_zip")); // 우편번호
        String sMemNo = StringUtils.defaultString((String)pmParam.get("mem_no"));

        if (nResult > 0) {
            if (!"".equals(sHomeZip)) {
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                String sAthrCd = oLoginUser.getAuthoritycd();
                String sTeamCd = oLoginUser.getTeamcd();
                if (sAthrCd.contains("TM")) {
                    pmParam.put("bzpt_div", sTeamCd);
                } else {
                    pmParam.put("bzpt_div", "999999");
                }

                pmParam.put("rltn_cd", "10");	// 본인
                // 변경 고객 주소 정보 존재 건수 조회
                int nExistCount = 0;
                try {
                    nExistCount = memberAddrDtlDAO.getMemberAddrDtlExistCount(pmParam);
                } catch (Exception exception) {
                    LOGGER.error(exception.getMessage());
                }
                // 고객 주소 정보 저장
                int nResultMemberAddrDtl = memberAddrDtlDAO.mergeMemberAddrDtl(pmParam);
                // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
                if (nResultMemberAddrDtl > 0 && nExistCount == 0) {
                    try {
                        pmParam.put("member_addr_dtl_merge_yn", "Y");
                        memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            //custBasiDAO.insertCustBasiHstr(pmParam);
            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt(sMemNo);
            mDtpt.put("cntr_cd", (String)pmParam.get("cntr_cd"));
            custBasiDAO.insertCustBasiHstr(mDtpt);
        }
        return nResult;
    }

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getCustBasiCount(Map<String, ?> pmParam) throws Exception {
        //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
        return (Integer) dlwCustDAO.getCustBasiCount(pmParam);
    }

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 업체용 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getCompUseListCount(Map<String, ?> pmParam) throws Exception {
        //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
        return (Integer) dlwCustDAO.getCompUseListCount(pmParam);
    }

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 직권해지 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnAuthListCount(Map<String, ?> pmParam) throws Exception {
        //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
        return (Integer) dlwCustDAO.getResnAuthListCount(pmParam);
    }

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 채권추심 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCreditMainListCount(Map<String, ?> pmParam) throws Exception {
        //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
        return (Integer) dlwCustDAO.getResnCreditMainListCount(pmParam);
    }

    /**
     * 고객 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 채권추심 입금 조회 고객 정보의 검색 건수
     * @throws Exception
     */
    public int getResnCreditpayMainListCount(Map<String, ?> pmParam) throws Exception {
        //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
        return (Integer) dlwCustDAO.getResnCreditpayMainListCount(pmParam);
    }



    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCustBasiList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getCustBasiList(pmParam);
    }


    /**
     * 고객 정보를 검색한다. (관리업무 - 고객정보관리)
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCustBasiList2(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getCustBasiList2(pmParam);
    }


    /**
     * 대명라이프웨이 직권해지 대상자 조회 count
     *
     * @param pmParam 검색 조건
     * @return 출금이체 신청 전 데이터 건수
     * @throws Exception
     */
    public int getResnTargetListCount(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getResnTargetListCount(pmParam);
    }

    /**
     * 대명라이프웨이 직권해지 대상자 조회
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnTargetList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getResnTargetList(pmParam);
    }

    /**
     * 대명라이프웨이 채권추심 대상자 조회
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getResnCreditList(pmParam);
    }

    /**
     * 직권해지 대상자 등록
     *
     * @param pmParam 고객조회로그 정보
     * @throws Exception
     */
    public int insertTargetList(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.insertTargetList(pmParam);
    }

    /**
     * 채권추심 대상자 등록
     *
     * @param pmParam 고객조회로그 정보
     * @throws Exception
     */
    public int insertCreditList(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.insertCreditList(pmParam);
    }

    /**
    *
    * 직권해지 설정일 업데이트
    *
    * @param pmParam 검색 조건
    * @return 설정 정보
    * @throws Exception
    */
   public int updateResnSenddt(Map<String, ?> pmParam) throws Exception {
       return dlwCustDAO.updateResnSenddt(pmParam);
   }

   /**
   *
   * 채권추심 삭제
   *
   * @param pmParam 검색 조건
   * @return 설정 정보
   * @throws Exception
   */
  public int delResnCredit(Map<String, ?> pmParam) throws Exception {
      return dlwCustDAO.delResnCredit(pmParam);
  }

   /**
   *
   * 직권해지 설정 후 해피콜 상태값 변경
   *
   * @param pmParam 검색 조건
   * @return 설정 정보
   * @throws Exception
   */
  public int updateHpclAckd(Map<String, ?> pmParam) throws Exception {
      dlwConsProdDAO.updateHpclAckd(pmParam);
      dlwConsProdDAO.insertHpclHist(pmParam);
      return 0;

  }


    /**
     * 업체 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCompUseList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getCompUseList(pmParam);
    }

    /**
     * 직권해지 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAuthList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getResnAuthList(pmParam);
    }

    /**
     * 채권추심 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditMainList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getResnCreditMainList(pmParam);
    }


    /**
     * 채권추심 고객 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnCreditpayMainList(Map<String, ?> pmParam) throws Exception {
        //return custBasiDAO.getCustBasiList(pmParam);
        return dlwCustDAO.getResnCreditpayMainList(pmParam);
    }

    /**
     * 고객 정보를 검색한다.
     *
     * @param psId 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasi(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("mem_no", psId);
        mParam.put("view_flag", "Y");
        //return custBasiDAO.getCustBasi(mParam);
        return dlwCustDAO.getCustBasi(mParam);
    }

    /**
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam 고객조회로그 정보
     * @throws Exception
     */
    public String insertCustInqLog(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = custBasiDAO.insertCustInqLog(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cust_inq_log_id");
        }

        return sKey;
    }

    /**
     * 고객 정보를 수정한다.(대명고객정보로 업데이트)
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */

    public void updateCustBasi_web(Map<String, Object> pmParam) throws Exception {
         dlwCustDAO.updateMember_web(pmParam); // 대명홈페이지  회원명 수정(개명신청자)
    }

}
