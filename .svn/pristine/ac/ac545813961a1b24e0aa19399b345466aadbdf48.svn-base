/*
 * (@)# DlwSdpProdController.java
 * 삼성디지털 프라자 현황물
 * @author 임동진
 * @version 1.0
 * @date 2016/09/19
 *
 */

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwSdpService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 삼디프 상품 정보를 관리한다
 *
 *
 * @author 임동진
 * @version 1.0
 * @date 2016/09/19
 * @프로그램ID DlwSdp
 */

@Service
public class DlwSdpServiceImpl extends EgovAbstractServiceImpl implements DlwSdpService {

    @Resource
    public DlwSdpDAO DlwSdpDAO;

    /**
     * 삼디프 시책금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpCount(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpCount(pmParam);
    }

    /**
     * 삼디프 다구좌 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpManyCount(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpManyCount(pmParam);
    }

    /**
     * 삼디프 프로모션 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpPromoCount(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpPromoCount(pmParam);
    }

    /**
     * 삼디프 충전금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpChargeCount(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpChargeCount(pmParam);
    }

    /**
     * 삼디프 시책금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpList(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpList(pmParam);
    }

    /**
     * 삼디프 다구좌 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpManyList(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpManyList(pmParam);
    }

    /**
     * 삼디프 프로모션 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpPromoList(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpPromoList(pmParam);
    }

    /**
     * 삼디프 충전금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpChargeList(Map<String, ?> pmParam) throws Exception {
        return DlwSdpDAO.getSdpChargeList(pmParam);
    }

    /** 삼디프 충전금 산출*/
    public int saveChargeList(Map<String, Object> pmParam) throws Exception {
        return DlwSdpDAO.saveChargeList(pmParam);
    }

    /*******************************
     * 삼디프 전송월 모든 파일 리스트
     ******************************/
    public List<Map<String, Object>> getAllChargeDtList(String hmParam) throws Exception {
         return DlwSdpDAO.getAllChargeDtList(hmParam);
    }


    public void dtlFailFileSet(Map<String, Object> hmParam) throws Exception {
        DlwSdpDAO.dtlFailFileSet(hmParam);
    }

    /*******************************
     * 삼디프 해당 전송월 파일 보낸 갯수 +1 체크
     ******************************/
    public int selectSDPFileCount(Map<String, Object> hmParam) throws Exception {
           return DlwSdpDAO.selectSDPFileCount(hmParam);
    }

    /*******************************
     * 삼디프 파일 전송, 송신 로그 입력
     ******************************/
    public int insertSDPSendLog(Map<String, Object> hmParam) throws Exception {
         return DlwSdpDAO.insertSDPSendLog(hmParam);
    }

    /*******************************
     * 삼디프 결과파일 내용 입력
     ******************************/
    public int resultDtlInsert(Map<String, Object> paramMap) throws Exception {
       return  DlwSdpDAO.resultDtlInsert(paramMap);
    }

    /*******************************
     * 삼디프 해당 전송월 보낸 파일 리스트
     ******************************/
    public List<Map<String, Object>> getSendFileList(Map<String, Object> hmParam) throws Exception {
        return DlwSdpDAO.getSendFileList(hmParam);
    }

    public int resultDtlUpdate(Map<String, Object> dRow) throws Exception {
         return  DlwSdpDAO.resultDtlUpdate(dRow);
    }

    public int sendFileUpdate(Map<String, Object> hRow) throws Exception {
         return  DlwSdpDAO.sendFileUpdate(hRow);
    }

    public int sendresult(Map<String, Object> hmParam) throws Exception {
        return  DlwSdpDAO.sendresult(hmParam);
    }

    public int resultMstUpdate(Map<String, Object> hRow) {
       return  DlwSdpDAO.resultMstUpdate(hRow);
    }

    public void dtlNoResultFileChange(Map<String, Object> hRow) {
           DlwSdpDAO.dtlNoResultFileChange(hRow);

    }
    
    public int updateInitSdpMesData(Map<String, Object> pmParam) {
		return DlwSdpDAO.updateInitSdpMesData(pmParam);
	}

	public int insertSdpMesData(Map<String, Object> pmParam) throws Exception {
		return DlwSdpDAO.insertSdpMesData(pmParam);
	}

	public int getSdpMesDataListCount(Map<String, Object> pmParam) {
		return DlwSdpDAO.getSdpMesDataListCount(pmParam);
	}

	public List<Map<String, Object>> getSdpMesDataList(Map<String, Object> pmParam) {
		return DlwSdpDAO.getSdpMesDataList(pmParam);
	}
	
	public List<Map<String, Object>> getSdpMesDataSummary(Map<String, Object> pmParam) {
		return DlwSdpDAO.getSdpMesDataSummary(pmParam);
	}
	
	public int updateSdpMesDataList(Map<String, Object> pmParam) {
		return DlwSdpDAO.updateSdpMesDataList(pmParam);
	}
	
	public int insertSdpMesDataMst(Map<String, Object> pmParam) throws Exception {
		return DlwSdpDAO.insertSdpMesDataMst(pmParam);
	}
	
	public int insertSdpMesDataDtl(Map<String, Object> pmParam) throws Exception {
		return DlwSdpDAO.insertSdpMesDataDtl(pmParam);
	}

	public List<Map<String, Object>> getSendFileMesMstList(Map<String, Object> pmParam) {
		return DlwSdpDAO.getSendFileMesMstList(pmParam);
	}

	public int insertSdpMesRecvLog(Map<String, Object> pmParam)throws Exception {
		return DlwSdpDAO.insertSdpMesRecvLog(pmParam);
	}

	public int sendSdpMesFileUpdate(Map<String, Object> pmParam) throws Exception {
		return DlwSdpDAO.sendSdpMesFileUpdate(pmParam);
	}

	public List<Map<String, Object>> getAllSdpMesList(Map<String, Object> pmParam) {
		return DlwSdpDAO.getAllSdpMesList(pmParam);
	}

	public int resultSdpMesDtlUpdate(Map<String, Object> pmParam) {
		return DlwSdpDAO.resultSdpMesDtlUpdate(pmParam);
	}

	public int resultSdpMesMstUpdate(Map<String, Object> pmParam) {
		return DlwSdpDAO.resultSdpMesMstUpdate(pmParam);
	}

	public int noResultSdpMesFileChange(Map<String, Object> pmParam) {
		return DlwSdpDAO.noResultSdpMesFileChange(pmParam);
	}

	public int deleteSdpList(Map<String, Object> pmParam) {
		return DlwSdpDAO.deleteSdpList(pmParam);
	}
}
