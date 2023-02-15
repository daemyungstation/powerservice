package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwAltrHistService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwAltrHistServiceImpl extends EgovAbstractServiceImpl implements DlwAltrHistService {

	@Resource
    public DlwAltrHistDAO dlwAltrHistDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwAltrHistServiceImpl.class);
    
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int insertMemAccntAltrHistList(Map<String, ?> pmParam) throws Exception {
        return dlwAltrHistDAO.insertMemAccntAltrHistList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistConsList(Map<String, ?> pmParam) throws Exception {
        return dlwAltrHistDAO.getMemAccntAltrHistConsList(pmParam);
    }
    
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int getMemAccntAltrHistCount(Map<String, ?> pmParam) {
        return dlwAltrHistDAO.getMemAccntAltrHistCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistList(Map<String, ?> pmParam) {
        return dlwAltrHistDAO.getMemAccntAltrHistList(pmParam);
    }
}
