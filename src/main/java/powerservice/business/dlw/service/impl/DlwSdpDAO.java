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

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 OCB 상품 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
 * @프로그램ID DlwOcbProd
 */
@Repository
public class DlwSdpDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 삼디프 시책금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwSdpMap.getSdpCount", pmParam);
    }

    /**
     * 삼디프 시책금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpManyCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwSdpMap.getSdpManyCount", pmParam);
    }

    /**
     * 삼디프 시책금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpPromoCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwSdpMap.getSdpPromoCount", pmParam);
    }

    /**
     * 삼디프 충전금 db 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getSdpChargeCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwSdpMap.getSdpChargeCount", pmParam);
    }

    /**
     * 삼디프 시책금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwSdpMap.getSdpList", pmParam);
    }
    /**
     * 삼디프 시책금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public List<Map<String, Object>> getSdpManyList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwSdpMap.getSdpManyList", pmParam);
    }
    /**
     * 삼디프 시책금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public List<Map<String, Object>> getSdpPromoList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwSdpMap.getSdpPromoList", pmParam);
    }

    /**
     * 삼디프 충전금 db 대상목록 조회
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getSdpChargeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwSdpMap.getSdpChargeList", pmParam);
    }

    /**
     * 삼디프 충전금 db 산출
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int saveChargeList(Map<String, ?> pmParam) throws Exception {
        return update("DlwSdpMap.saveChargeList", pmParam);
    }

    public int insertSDPSendLog(Map<String, Object> hmParam) {
        return insert("DlwSdpMap.insertSDPSendLog", hmParam);
    }

    public int selectSDPFileCount(Map<String, ?> hmParam) {
           return selectOne("DlwSdpMap.selectSDPFileCount", hmParam);
    }

    public List<Map<String, Object>> getSendFileList(Map<String, Object> hmParam) {
        return selectList("DlwSdpMap.getSendFileList", hmParam);
    }

    public int resultDtlInsert(Map<String, Object> paramMap) {
        return insert("DlwSdpMap.resultDtlInsert", paramMap);
    }

    public List<Map<String, Object>> getAllChargeDtList(String hmParam) {
          return selectList("DlwSdpMap.getAllChargeDtList", hmParam);
    }

    public int fileCount(Map<String, Object> hmParam) {
        return selectOne("DlwSdpMap.fileCount", hmParam);
    }

    public int sendFileUpdate(Map<String, Object> hRow) {
          return update("DlwSdpMap.sendFileUpdate", hRow);
    }

    public int dtlFailFileSet(Map<String, Object> hmParam) {
           return update("DlwSdpMap.dtlFailFileSet", hmParam);
    }

    public int resultDtlUpdate(Map<String, Object> dRow) {
       return   update("DlwSdpMap.resultDtlUpdate", dRow);
    }

    public int sendresult(Map<String, Object> hmParam) {
        return selectOne("DlwSdpMap.sendresult", hmParam);
    }

    public int resultMstUpdate(Map<String, Object> hRow) {
        return update("DlwSdpMap.resultMstUpdate", hRow);
    }

    public void dtlNoResultFileChange(Map<String, Object> hRow) {
        update("DlwSdpMap.dtlNoResultFileChange", hRow);
    }

    public int updateInitSdpMesData(Map<String, Object> pmParam) {
		return update("DlwSdpMap.updateInitSdpMesData", pmParam);
	}
    
	public int insertSdpMesData(Map<String, Object> pmParam) {
		 return insert("DlwSdpMap.insertSdpMesData", pmParam);
	}

	public int getSdpMesDataListCount(Map<String, Object> pmParam) {
		return selectOne("DlwSdpMap.getSdpMesDataListCount", pmParam);
	}

	public List<Map<String, Object>> getSdpMesDataList(Map<String, Object> pmParam) {
		return selectList("DlwSdpMap.getSdpMesDataList", pmParam);
	}
	
	public List<Map<String, Object>> getSdpMesDataSummary(Map<String, Object> pmParam) {
		return selectList("DlwSdpMap.getSdpMesDataSummary", pmParam);
	}
	
	public int updateSdpMesDataList(Map<String, Object> pmParam) {
		return update("DlwSdpMap.updateSdpMesDataList", pmParam);
	}
	
	public int insertSdpMesDataMst(Map<String, Object> pmParam) {
		 return insert("DlwSdpMap.insertSdpMesDataMst", pmParam);
	}
	
	public int insertSdpMesDataDtl(Map<String, Object> pmParam) {
		 return insert("DlwSdpMap.insertSdpMesDataDtl", pmParam);
	}

	public List<Map<String, Object>> getSendFileMesMstList(Map<String, Object> pmParam) {
		return selectList("DlwSdpMap.getSendFileMesMstList", pmParam);
	}

	public int insertSdpMesRecvLog(Map<String, Object> pmParam) {
		return insert("DlwSdpMap.insertSdpMesRecvLog", pmParam);
	}

	public int sendSdpMesFileUpdate(Map<String, Object> pmParam) {
		return update("DlwSdpMap.sendSdpMesFileUpdate", pmParam);
	}

	public List<Map<String, Object>> getAllSdpMesList(Map<String, Object> pmParam) {
		return selectList("DlwSdpMap.getAllSdpMesList", pmParam);
	}

	public int resultSdpMesDtlUpdate(Map<String, Object> pmParam) {
		return update("DlwSdpMap.resultSdpMesDtlUpdate", pmParam);
	}

	public int resultSdpMesMstUpdate(Map<String, Object> pmParam) {
		return update("DlwSdpMap.resultSdpMesMstUpdate", pmParam);
	}

	public int noResultSdpMesFileChange(Map<String, Object> pmParam) {
		return update("DlwSdpMap.noResultSdpMesFileChange", pmParam);
	}

	public int deleteSdpList(Map<String, Object> pmParam) {
		return delete("DlwSdpMap.deleteSdpList", pmParam);
	}
}
