package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwSignListMainService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwSignListMainServiceImpl extends EgovAbstractServiceImpl implements DlwSignListMainService {

	@Resource
    public DlwSignListMainDAO dlwSignListMainDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwSignListMainServiceImpl.class);
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 조회수
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public int getSignListMainCount(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignListMainCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getSignListMainList(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignListMainList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명이력리스트 조회수
     * 대상 테이블 : SIGN.V_DOC_STATUS@DLCCSUB
     * ================================================================
     * */
    public int getSignHistMainCount(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignHistMainCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명이력리스트 조회리스트
     * 대상 테이블 : SIGN.V_DOC_STATUS@DLCCSUB
     * ================================================================
     * */
    public List<Map<String, Object>> getSignHistMainList(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignHistMainList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리 삭제 가능 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getDeleteSignYnConfirm(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getDeleteSignYnConfirm(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 전자서명거절
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
	public int updateSignMemberReject(Map<String, ?> pmParam) throws Exception {
		return dlwSignListMainDAO.updateSignMemberReject(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 대상 삭제
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public int deleteSignListMainList(Map<String, ?> pmParam) throws Exception {
		return dlwSignListMainDAO.deleteSignListMainList(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 대상 고객 조회 팝업
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getAccntList(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getAccntList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 대상 더블구좌 고객 조회 팝업 조회수
     * 대상 테이블 : LF_DMUSER.TB_ACCNT_DOUBLE, LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public int getDoubleAccntCount(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getDoubleAccntCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 대상 더블구좌 고객 조회 팝업 리스트
     * 대상 테이블 : LF_DMUSER.TB_ACCNT_DOUBLE, LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getDoubleAccntList(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getDoubleAccntList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 구분 코드 로드(전자서명구분1)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public List<Map<String, Object>> getCodeSignGbn1Full(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getCodeSignGbn1Full(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 구분 코드 로드(전자서명구분2)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getCodeSignGbn2Full(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getCodeSignGbn2Full(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 단일 회원번호, 다구좌로 등록된 전자서명인지 여부, 더블구좌번호로 등록된 전자서명인지 여부 
     *            (최초로 비대면계약서와 대면계약서를 작성했는지의 여부도 포함)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getAMccntNoRegSignConfirm(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getAMccntNoRegSignConfirm(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 등록회원 조회 (단구좌, 다구좌고객)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getSignAccnt2Info(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignAccnt2Info(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 등록회원 서명타입 조회 (단구좌, 다구좌고객)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getSignAccntSignTypeInfo(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignAccntSignTypeInfo(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 등록회원 조회 (더블구좌고객)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getSignDouble2Info(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignDouble2Info(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 등록회원 서명타입 조회 (더블구좌고객)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getSignDoubleSignTypeInfo(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignDoubleSignTypeInfo(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 더블구좌 회원 정보추출
     * 대상 테이블 : LF_DMUSER.TB_ACCNT_DOUBLE
     * ================================================================
     * */
    public List<Map<String, Object>> getDoubleInfo(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getDoubleInfo(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 더블구좌 회원 전자서명구분 기본코드 조회
     * 대상 테이블 : LF_DMUSER.TB_ACCNT_DOUBLE
     * ================================================================
     * */
    public List<Map<String, Object>> getDoubleBasicSignGbn(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getDoubleBasicSignGbn(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 더블구좌 회원 전자서명구분 기본코드 조회(비대면/대면계약서 이후)
     * 대상 테이블 : LF_DMUSER.TB_ACCNT_DOUBLE
     * ================================================================
     * */
    public List<Map<String, Object>> getRegistDoubleBasicSignGbn(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getRegistDoubleBasicSignGbn(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 이력 조회
     * 대상 테이블 : SIGN.V_DOC_STATUS@DLCCSUB
     * ================================================================
     * */
    public List<Map<String, Object>> getSignAccntInfoHist(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.getSignAccntInfoHist(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 구분 코드 초기화 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public List<Map<String, Object>> getCodeSignGbn1Init(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.getCodeSignGbn1Init(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분 코드2 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getCodeSignGbn2(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.getCodeSignGbn2(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 SIGN_NO 채번
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getCreateSignNo(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getCreateSignNo(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 정보등록
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public int insertSignMainList(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignMainList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분1 공통 코드 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public List<Map<String, Object>> getSignCodeMst(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignCodeMst(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분2 공통 코드 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getSignCodeDtl(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getSignCodeDtl(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분1 공통 코드 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public int insertSignCodeMstConfirm(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignCodeMstConfirm(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분1 공통 코드 입력 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public int insertSignCodeMst(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignCodeMst(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분2 공통 코드 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public int insertSignCodeDtlConfirm(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignCodeDtlConfirm(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분2 공통 코드 입력
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public int insertSignCodeDtl(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignCodeDtl(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분1 공통 코드 수정
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    public int updateSignCodeMst(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.updateSignCodeMst(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분2 공통 코드 수정
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    public int updateSignCodeDtl(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.updateSignCodeDtl(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20211230
     * 이름 : 임동진
     * 추가내용 : 전자서명 기초 회원 조회 (전자서명 상단 조회)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getAccntInfoList(Map<String, ?> pmParam) throws Exception {
        return dlwSignListMainDAO.getAccntInfoList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20211230
     * 이름 : 임동진
     * 추가내용 : 전자서명 신규 등록 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    public int insertSignNew(Map<String, ?> pmParam) throws Exception {
	    return dlwSignListMainDAO.insertSignNew(pmParam);
    }
}
