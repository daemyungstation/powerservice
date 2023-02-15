package powerservice.business.dlw.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwNonPayService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwNonPayServiceImpl extends EgovAbstractServiceImpl implements DlwNonPayService {

    @Resource
    public DlwNonPayDAO dlwNonPayDAO;

    private final Logger log = LoggerFactory.getLogger(DlwNonPayServiceImpl.class);

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayTitList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayTitList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayCount(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayPopCount(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayPopCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayPopList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayPopList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221101
     * 이름 : 김주용
     * 추가내용 : 미납대상 중복 체크
     * 대상 테이블 : TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayChk(Map<String, ?> pmParam) throws Exception {
        return dlwNonPayDAO.getNonPayChk(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납대상 TITLE생성
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MST
     * ================================================================
     * */
    public int insertNonPayMentMst(Map<String, Object> pmParam) {
        return dlwNonPayDAO.insertNonPayMentMst(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납대상 TITLE생성
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MST
     * ================================================================
     * */
    public int updateNonPayMentMst(Map<String, Object> pmParam) {
        return dlwNonPayDAO.updateNonPayMentMst(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납DTL 생성
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int insertNonPayMentDtl(Map<String, Object> pmParam) {
        int nResult = 0;
        dlwNonPayDAO.insertNonPayMentDtl(pmParam);

        String sNpGubn = CommonUtils.nvls((String)pmParam.get("np_gbn")); // 해피콜
        if ("002".equals(sNpGubn)) {
            dlwNonPayDAO.updateNoPayMngInfo(pmParam);
        }
        return nResult;
    }

    /** ================================================================
     * 날짜 : 20221102
     * 이름 : 임성수
     * 추가내용 : 미납대상결과(NEW) 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG, TB_MEMBER_BASIC_INFO_DAY
     * ================================================================
     * */
    public int getNonPayResultListCount(Map<String, Object> pmParam) {
        return dlwNonPayDAO.getNonPayResultListCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221102
     * 이름 : 임성수
     * 추가내용 : 미납대상결과(NEW) 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG, TB_MEMBER_BASIC_INFO_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayResultList(Map<String, Object> pmParam) {
        return dlwNonPayDAO.getNonPayResultList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221110
     * 이름 : 김주용
     * 추가내용 : 미납대상월별정보 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int getNonPayMntTotCount(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayMntTotCount(pmParam);
    }

    public List<Map<String, Object>> getNonPayMstMonList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayMstMonList(pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlBndList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayDtlBndList(pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlOverList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayDtlOverList(pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlSectionList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.getNonPayDtlSectionList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납회원 대상수 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> selectNonPayPopList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.selectNonPayPopList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납대상자 전체 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int selectNonPayPopDetailListCount(Map<String, ?> pmParam) {
        return dlwNonPayDAO.selectNonPayPopDetailListCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납대상자 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> selectNonPayPopDetailList(Map<String, ?> pmParam) {
        return dlwNonPayDAO.selectNonPayPopDetailList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 조용우
     * 추가내용 : 미납월별정보 데이터 INSERT
     * 대상 테이블 : LF_DMUSER.TB_CUR_MON_PAY_DAY_AMT
     * ================================================================
     * */
    public void insertTbCurMonPayDayAmt() throws Exception {
        Map<String, ?> pmParam = null;
        dlwNonPayDAO.insertTbCurMonPayDayAmt(pmParam);
    }

    /** ================================================================
     * 날짜 : 20230119
     * 이름 : 조용우
     * 추가내용 : 미납정보 당월 미납 초기데이터 INSERT , 미납정보 당월납입실적 UPDATE
     * 대상 테이블 : TB_CLOSE_MEMBER_MON , PRODUCT , PRODUCT_NO_DATA , TB_BANKRUPTCY_MNG , RESCISSION , TB_MEMBER_EXT_SPECIAL , TB_NONPAYMENT_MNG , TB_MEMBER_WDRW_REQ_DAMO
     * ================================================================
     * */
    public void insertTbNonpaymentMng() throws Exception {
        // 날짜형식가져오기            ​
        SimpleDateFormat nowDD = new SimpleDateFormat("dd");

        Calendar rightNow = Calendar.getInstance();
        String nowDate = nowDD.format(rightNow.getTime());

        if("01".equals(nowDate)){
//            Map<String, ?> pmParam = null;
//            dlwNonPayDAO.insertTbNonpaymentMng(pmParam);
        }else{
            Map<String, ?> pmParam = null;
            dlwNonPayDAO.updateTbNonpaymentMng(pmParam);
        }
    }
}
