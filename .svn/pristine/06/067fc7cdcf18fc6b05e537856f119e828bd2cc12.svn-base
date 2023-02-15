package powerservice.business.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * * 나이스 평가정보사의 신용정보 조회 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/09/01
 * @프로그램ID NiceCredit
 */
@Repository
public class CommonDAO extends EgovAbstractMapper {

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
     * 지부 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBranchList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.getBranchList", pmParam);
    }

    /**
     * 공통콤보 행사관리 창고조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getWhCdList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.getWhCdList", pmParam);
    }

    /**
     * 거래처 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCustomerCdList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.getCustomerCdList", pmParam);
    }

    /**
     * 품목코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectGoodsComList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectGoodsComList", pmParam);
    }

    /**
     * CP 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectCpInfo(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectCpInfo", pmParam);
    }

    /**
     * CP 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOrgChartMap(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectOrgChartMap", pmParam);
    }

    /**
     * 영업형태 분류 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectStGrpCdList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectStGrpCdList", pmParam);
    }


    /**
     * 부가서비스 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectOptSvcList", pmParam);
    }

    /**
     * 엑셀다운로드 이력 생성
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertExcelDownHist(Map<String, Object> pmParam) throws Exception {
        return update("CommonMap.insertExcelDownHist", pmParam);
    }

    /**
     * 자료권한 그룹 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectDataAuthGrpList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectDataAuthGrpList", pmParam);
    }

    /**
     * 종합현황 조회 권한
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectReportAuthGrpList(Map<String, Object> pmParam) throws Exception {
        return selectList("CommonMap.selectReportAuthGrpList", pmParam);
    }

    /**
     * SMS발송
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertSMS(Map<String, Object> pmParam) throws Exception {
        return insert("CommonMap.insertSMS", pmParam);
    }

    /**
     * SMS발송
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertSMSReal(Map<String, Object> pmParam) throws Exception {
        return insert("CommonMap.insertSMSReal", pmParam);
    }

    /**
     * 공통코드 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectCboCommCd(String cd) throws Exception {
        return selectList("CommonMap.selectCboCommCd", cd);
    }



    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectMemNo(String accntNo) throws Exception {
        return selectOne("CommonMap.selectMemNo", accntNo);
    }

    /**
     * 파일다운로드 키 등록
     *
     * @param pmParam
     * @throws Exception
     */
    public int insertFileDown(Map<String, Object> pmParam) throws Exception {
        return insert("CommonMap.insertFileDown", pmParam);
    }

    /**
     * 파일다운로드 일시분초 변경
     *
     * @param pmParam
     * @throws Exception
     */
    public int updateFileDownDthms(Map<String, Object> pmParam) throws Exception {
        return insert("CommonMap.updateFileDownDthms", pmParam);
    }

    /**
    * 파일다운로드 키 조회
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> selectFileDown(Map<String, Object> pmParam) throws Exception {
       return selectList("CommonMap.selectFileDown", pmParam);
   }

   /**
    * 콤보 분류에 따른 영업형태
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> selectSaleTypeByStGrp(Map<String, Object> pmParam) throws Exception {
       return selectList("CommonMap.selectSaleTypeByStGrp", pmParam);
   }

   /**
    * 상품코드 조회
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> selectProdCdList(Map<String, Object> pmParam) throws Exception {
       return selectList("CommonMap.selectProdCdList", pmParam);
   }

   /**
    * 엑셀업로드 오류건 등록
    *
    * @param pmParam
    * @throws Exception
    */
   public int insertErrHist(Map<String, Object> pmParam) throws Exception {
       return update("CommonMap.insertErrHist", pmParam);
   }

   /**
    * B2B 코드 목록 조회
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> getB2bList(Map<String, Object> pmParam) throws Exception {
       return selectList("CommonMap.getB2bList", pmParam);
   }






   /**
    * 접속 로그 2017.11.30
    *
    * @param pmParam
    * @throws Exception
    */
   public void insertLog(Map<String, Object> pmParam) throws Exception {

       insert("CommonMap.insertLog", pmParam);

   }

   /**
    * 접속 로그카운트 검색 - 2017.12.08 정승철
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getLogCount(Map<String, ?> pmParam) throws Exception {
       return (Integer) selectOne("CommonMap.getLogCount", pmParam);
   }

   /**
    * 접속 로그 검색 - 2017.12.08 정승철
    *
    * @param pmParam 검색 조건
    * @return 프로그램 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getLogList(Map<String, ?> pmParam) throws Exception {
       return selectList("CommonMap.getLogList", pmParam);
   }
    
    /** ================================================================
     * 날짜 : 20200616
     * 이름 : 송준빈
     * 추가내용 : 엑셀다운로드 사유기입
     * 대상 테이블 : LF_DMUSER.MEM_MENU_LOG
     * ================================================================
     */
    public int insertExcelDownloadReason(Map<String, Object> pmParam) throws Exception {
        return insert("CommonMap.insertExcelDownloadReason", pmParam);
    }
}
