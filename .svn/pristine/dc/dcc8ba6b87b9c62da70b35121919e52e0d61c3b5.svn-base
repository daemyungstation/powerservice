package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwEvtTranService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwEvtTranServiceImpl extends EgovAbstractServiceImpl implements DlwEvtTranService {

	@Resource
    public DlwEvtTranDAO dlwEvtTranDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwEvtTranServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품,모델,패키지등록시 상품코드를 채번 (P:상품, M:상품모델, K:패키지)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT, LF_DMUSER.TB_EVT_TRAN_MODEL, LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
	public String getEvtTranCd(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.getEvtTranCd(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
	public int insertEvtTranExt(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.insertEvtTranExt(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 수정
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
	public int updateEvtTranExt(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.updateEvtTranExt(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
	public int deleteEvtTranExt(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.deleteEvtTranExt(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회수
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public int getEvtTranExtCount(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranExtCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getEvtTranExtList(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranExtList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    public int insertEvtTranModel(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.insertEvtTranModel(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 수정
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    public int updateEvtTranModel(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.updateEvtTranModel(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    public int deleteEvtTranModel(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.deleteEvtTranModel(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 조회수
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    public int getEvtTranModelCount(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranModelCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품모델 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_MODEL
     * ================================================================
     * */
    public List<Map<String, Object>> getEvtTranModelList(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranModelList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    public int insertEvtTranPackage(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.insertEvtTranPackage(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200617
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 수정
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    public int updateEvtTranPackage(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.updateEvtTranPackage(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    public int deleteEvtTranPackage(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.deleteEvtTranPackage(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 조회수
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    public int getEvtTranPackageCount(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranPackageCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환패키지 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_PKG
     * ================================================================
     * */
    public List<Map<String, Object>> getEvtTranPackageList(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranPackageList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 등록
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public int insertEvtTranList(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.insertEvtTranList(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 수정
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public int updateEvtTranList(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.updateEvtTranList(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 삭제
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public int deleteEvtTranList(Map<String, ?> pmParam) throws Exception {
		return dlwEvtTranDAO.deleteEvtTranList(pmParam);
	}
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환고객 조회(단일)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public List<Map<String, Object>> getEvtTranList(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환회원 조회수(종합)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public int getEvtTranMembersCount(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranMembersCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환회원 조회리스트(종합)
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN
     * ================================================================
     * */
    public List<Map<String, Object>> getEvtTranMembersList(Map<String, ?> pmParam) throws Exception {
        return dlwEvtTranDAO.getEvtTranMembersList(pmParam);
    }
}
