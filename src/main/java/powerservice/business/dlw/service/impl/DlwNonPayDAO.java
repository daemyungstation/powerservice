package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwNonPayDAO extends EgovAbstractMapper {

    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayTitList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayTitList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayCount(Map<String, ?> pmParam) {
        return selectOne("DlwNonPayMap.getNonPayCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayPopCount(Map<String, ?> pmParam) {
        return selectOne("DlwNonPayMap.getNonPayPopCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayPopList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayPopList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221101
     * 이름 : 김주용
     * 추가내용 : 미납대상 중복 체크
     * 대상 테이블 : TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int getNonPayChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNonPayMap.getNonPayChk", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납대상 TITLE생성
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MST
     * ================================================================
     * */
    public int insertNonPayMentMst(Map<String, Object> pmParam) {
        return insert("DlwNonPayMap.insertNonPayMentMst", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납대상 TITLE수정
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_YENCHE_MST
     * ================================================================
     * */
    public int updateNonPayMentMst(Map<String, Object> pmParam) {
        return update("DlwNonPayMap.updateNonPayMentMst", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221103
     * 이름 : 김주용
     * 추가내용 : 미납DTL 생성
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int insertNonPayMentDtl(Map<String, Object> pmParam) {
        return insert("DlwNonPayMap.insertNonPayMentDtl", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221102
     * 이름 : 임성수
     * 추가내용 : 미납대상결과(NEW) 현황 조회수
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG, TB_MEMBER_BASIC_INFO_DAY
     * ================================================================
     * */
    public int getNonPayResultListCount(Map<String, Object> pmParam) {
        return selectOne("DlwNonPayMap.getNonPayResultListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221102
     * 이름 : 임성수
     * 추가내용 : 미납대상결과(NEW) 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG, TB_MEMBER_BASIC_INFO_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getNonPayResultList(Map<String, Object> pmParam) {
        return selectList("DlwNonPayMap.getNonPayResultList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221110
     * 이름 : 김주용
     * 추가내용 : 미납대상월별정보 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int getNonPayMntTotCount(Map<String, ?> pmParam) {
        return selectOne("DlwNonPayMap.getNonPayMntTotCount", pmParam);
    }

    public List<Map<String, Object>> getNonPayMstMonList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayMstMonList", pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlBndList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayDtlBndList", pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlOverList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayDtlOverList", pmParam);
    }

    public List<Map<String, Object>> getNonPayDtlSectionList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.getNonPayDtlSectionList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납회원 대상수 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> selectNonPayPopList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.selectNonPayPopList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납대상자 전체 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public int selectNonPayPopDetailListCount(Map<String, ?> pmParam) {
        return selectOne("DlwNonPayMap.selectNonPayPopDetailListCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20221125
     * 이름 : 조용우
     * 추가내용 : 미납대상자 조회
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> selectNonPayPopDetailList(Map<String, ?> pmParam) {
        return selectList("DlwNonPayMap.selectNonPayPopDetailList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 조용우
     * 추가내용 : 미납월별정보 데이터 INSERT
     * 대상 테이블 : LF_DMUSER.TB_CUR_MON_PAY_DAY_AMT
     * ================================================================
     * */
    public void insertTbCurMonPayDayAmt(Map<String, ?> pmParam) throws Exception {
        insert("DlwNonPayMap.insertTbCurMonPayDayAmt", pmParam);
    }

    /** ================================================================
     * 날짜 : 20230113
     * 이름 : 정성안
     * 추가내용 : 미납관리(해피콜) 대상/서브대상목록ID 저장
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    public int updateNoPayMngInfo(Map<String, Object> pmParam) {
        return update("DlwNonPayMap.updateNoPayMngInfo", pmParam);
    }

    /** ================================================================
     * 날짜 : 20230119
     * 이름 : 조용우
     * 추가내용 : 미납정보 당월 미납 초기데이터 INSERT , 미납정보 당월납입실적 UPDATE
     * 대상 테이블 :   TB_CLOSE_MEMBER_MON , PRODUCT , PRODUCT_NO_DATA , TB_BANKRUPTCY_MNG , RESCISSION , TB_MEMBER_EXT_SPECIAL , TB_NONPAYMENT_MNG_TMP , TB_MEMBER_WDRW_REQ_DAMO
     * ================================================================
     * */
    public void insertTbNonpaymentMng(Map<String, ?> pmParam) throws Exception {
        insert("DlwNonPayMap.insertTbNonpaymentMng", pmParam);
    }

    /** ================================================================
     * 날짜 : 20230120
     * 이름 : 조용우
     * 추가내용 : 미납정보 당월 미납 초기데이터 INSERT , 미납정보 당월납입실적 UPDATE
     * 대상 테이블 :   TB_CLOSE_MEMBER_MON , PRODUCT , PRODUCT_NO_DATA , TB_BANKRUPTCY_MNG , RESCISSION , TB_MEMBER_EXT_SPECIAL , TB_NONPAYMENT_MNG_TMP , TB_MEMBER_WDRW_REQ_DAMO
     * ================================================================
     * */
    public void updateTbNonpaymentMng(Map<String, ?> pmParam) throws Exception {
        update("DlwNonPayMap.updateTbNonpaymentMng", pmParam);
    }

}
