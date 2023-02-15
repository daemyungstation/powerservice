package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwNewTypeMainConsDAO extends EgovAbstractMapper {
	/**
     * 대명라이프웨이 고객정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 고객정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/**
     * 고객 상세정보를 조회한다.(MEM_PROD_ACCNT)
     *
     * @param pmParam 고객정보
     * @return 고객상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustMemList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwCustMemList", pmParam);
    }
    
    /**
     * 고객 개인정보 활용동의 정보를 검색한다.
     *
     * @param psId 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getPersInfoPcusCnsnList", pmParam);
    }
    
    
    /**
     * 개인정보 활용동의 스크립트 상세정보를 검색한다.
     *
     * @param 없음
     * @return 스크립트 상세정보
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusSrctDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getPersInfoPcusSrctHstrList", pmParam);
    }
    
    /**
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam 고객조회로그 정보
     * @throws Exception
     */
    public int insertCustInqLog(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertCustInqLog", pmParam);
    }
    
    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getCustUnusMemoCnt", pmParam);
    }
    
    /**
     * 대명라이프웨이 OCB 전송 이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbTransHistCnt() throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getOcbTransHistCnt");
    }
    
    /**
     * 고객이력 정보를 등록한다.
     *
     * @param pmParam 고객이력 정보
     * @throws Exception
     */
    public int insertCustBasiHstr(Map<String, ?> pmParam) throws Exception {
    //    CommonUtils.printLog(pmParam);

        return insert("DlwNewTypeMainConsMap.insertCustBasiHstr", pmParam);
    }
    
    /**
     * OCB정보를 수정한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateMemberOCB(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.updateMemberOCB", pmParam);
    }
    
    /**
     * 고객 정보를 검색한다. (상담이력테이블 저장)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getDlwMemberDtpt(String pMemNo) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwMemberDtpt", pMemNo);
    }
    
    /**
     * 고객정보를 등록한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertMember(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertMember", pmParam);
    }
    
    /**
     * 고객 주소 정보를 등록한다.
     *
     * @param pmParam 고객 주소 정보
     * @throws Exception
     */
    public int mergeMemberAddrDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.mergeMemberAddrDtl", pmParam);
    }
    
    /**
     * 고객 주소 이력 정보를 등록한다.
     *
     * @param pmParam 고객 주소 이력 정보
     * @throws Exception
     */
    public int insertMemberAddrDtlHstr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertMemberAddrDtlHstr", pmParam);
    }
    
    /**
     * 고객정보를 수정한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateMember(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.updateMember", pmParam);
    }
    
    /**
     * 변경 고객 주소 정보의 존재 건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 변경 고객 주소 정보의 존재 건수
     * @throws Exception
     */
    public int getMemberAddrDtlExistCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getMemberAddrDtlExistCount", pmParam);
    }
    
    /******************************************************
     * 2017.09.20 김준호
     * 회원 고유번호로 해당 고객 등록 상품 갯수 확인
     * **************************************************/

    public List<Map<String, Object>> selectCountProd(Map<String, ?> pmParam) {
        return selectList("DlwNewTypeMainConsMap.selectCountProd", pmParam);
    }
    
    /**
     * 변경 삭제 내역 저장
     *
     * @param pmParam 변경 삭제 내역
     * @return
     * @throws Exception
     */
    public int insertReqUpdDelInf(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertReqUpdDelInf", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
	public int delFlagMemProdAccnt(Map<String, ?> pmParam) throws Exception {
		return update("DlwNewTypeMainConsMap.delFlagMemProdAccnt", pmParam);
	}
	   
	/**
	 *
	 *
	 * @param pmParam 검색 조건
	 * @return
	 * @throws Exception
	 */
	public int delFlagPayMng(Map<String, ?> pmParam) throws Exception {
	    return update("DlwNewTypeMainConsMap.delFlagPayMng", pmParam);
	}
	  
	/**
	*
	*
	* @param pmParam 검색 조건
	* @return
	* @throws Exception
	*/
	public int delFlagRescission(Map<String, ?> pmParam) throws Exception {
	    return update("DlwNewTypeMainConsMap.delFlagRescission", pmParam);
	}
	 
	/**
	 *
	 *
	 * @param pmParam 검색 조건
	 * @return
	 * @throws Exception
	 */
	public int delFlagCnclBrkdnMng(Map<String, ?> pmParam) throws Exception {
	    return update("DlwNewTypeMainConsMap.delFlagCnclBrkdnMng", pmParam);
	}
	 
	/**
	 *
	 *
	 * @param pmParam 검색 조건
	 * @return
	 * @throws Exception
	 */
	public int delFlagEvent(Map<String, ?> pmParam) throws Exception {
	    return update("DlwNewTypeMainConsMap.delFlagEvent", pmParam);
	}
	 
	/**
	 *
	 *
	 * @param pmParam 검색 조건
	 * @return
	 * @throws Exception
	 */
	public int delFlagTaxtProc(Map<String, ?> pmParam) throws Exception {
	    return update("DlwNewTypeMainConsMap.delFlagTaxtProc", pmParam);
	}
	
	/**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagCmsMemb(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.delFlagCmsMemb", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagMstrChgInf(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.delFlagMstrChgInf", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagGasuAmtReg(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.delFlagGasuAmtReg", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagDcAmtReg(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.delFlagDcAmtReg", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int delFlagCardMemb(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.delFlagCardMemb", pmParam);
    }
    
    /**
     * 대명라이프웨이 CMS 회원 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제여부
     * @throws Exception
     */
    public int deleteDlwCmsMmbr(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.deleteDlwCmsMmbr", pmParam);
    }
    
    /**
     * 입력 혹은 수정된 고객 정보 상담이력 및 변견 LOG 등록
     *
     * @param pmParam 고객정보
     * @throws Exception
      */
    public void insertMemLogSave(Map<String, ?> pmParam) throws Exception {
           update("DlwNewTypeMainConsMap.insertMemLogSave", pmParam);           
    }    
    
    /**
     * 웹디비고객정보를 회원명을 수정한다
     *
     * @param pmParam 고객정보
     * @throws Exception
      */
    public void updateMember_web(Map<String, ?> pmParam) throws Exception {
           update("DlwNewTypeMainConsMap.updateMember_web", pmParam);
    }
    
    /**
     * 고객 상세정보를 조회한다.(MEM_PROD_ACCNT)
     *
     * @param pmParam 고객정보
     * @return 고객상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustPrdtDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwCustPrdtList", pmParam);
    }
    
    /**
     * 캠페인발신이력 정보의 검색수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getIbDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getIbDpmsReslHstrCount", pmParam);
    }


    /**
     * 캠페인발신이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getIbDpmsReslHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getIbDpmsReslHstrList", pmParam);
    }
    
    /**
     * VOC 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 정보의 검색 수
     * @throws Exception
     */
    public int getVocDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getVocDtlCount", pmParam);
    }

    /**
     * VOC 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getVocDtlList", pmParam);
    }
    
    /**
     * 문자 전송 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자 전송 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getChatSndgHstrCount", pmParam);
    }

    /**
     * 문자 전송 이력 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getChatSndgHstrList", param);
    }
    
    /**
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwConsProdList", pmParam);
    }
    
    /**
     * 선택된 상품 정보를 가져온다
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdListDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwConsProdListDtl", pmParam);
    }    
    
    /**
     * 회원 상품정보를 검색한다. (메인화면에서 해약금 조회 2018.01.30 김찬영
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getProdInfoInqNew", pmParam);
    }
    
    /**
     * 현재 회차 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 회차
     * @throws Exception
     */
    public int getNowMonthCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getNowMonthCount", pmParam);
    }

    /**
     * 해약환급금 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약환급금
     * @throws Exception
     */
    public int getResnAmt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getResnAmt", pmParam);
    }
    
    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getResnGubn", pmParam);
    }
    
    /**
     * 전자서명 상태값 [00(인증값)_00(상품계약서 상태값)]
     *
     * @param pmParam 검색 조건
     * @return 해피콜 승인여부
     * @throws Exception
     */
    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getNiceAuthStat", pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception {
       return (Integer) selectOne("DlwNewTypeMainConsMap.getProdSrvcHistForMPAS", pmParam);
   }
   
   /**
    * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 부가서비스 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwNewTypeMainConsMap.getProdSrvcMstList", pmParam);
   }
   
   /**
    * 할인우대권을 체크한다.
    *
    * @param pmParam 검색 조건
    * @return 할인우대권 체크 결과
    * @throws Exception
    */
   public String validateDiscntPassNo(Map<String, ?> pmParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.validateDiscntPassNo", pmParam);
   }

   /**
    * 특별할인
    *
    * @param pmParam 검색 조건
    * @return 특별할인
    * @throws Exception
    */
   public String selectNewChanGunsuDPM(Map<String, ?> pmParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.selectNewChanGunsuDPM", pmParam);
   }
   
   /**
    * 상담-상품 모델분류 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 모델분류 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwNewTypeMainConsMap.getDlwModlMstInfo", pmParam);
   }

   /**
    * 상담-상품 모델명 정보를 검색한다.
    *
    * @return 모델명 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwNewTypeMainConsMap.getDlwModlDtlInfo", pmParam);
   }

   /**
    * 상담-상품 상품종류 정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 상품종류 정보 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwNewTypeMainConsMap.getDlwProdKindList", pmParam);
   }
   
   /**
    * 회원의 리조트 정보 조회
    *
    * @param pmParam 검색 조건
    * @return 회원의 리조트 정보
    * @throws Exception
    */
   public Map<String, Object> getResortInfo(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwNewTypeMainConsMap.getResortInfo", pmParam);
   }
   
   /**
    * 회원 상품 정보 수정 시 체크 해야 할 사항 (청구중 or 두구좌 제한 등등)
    *
    * @param pmParam 검색 조건
    * @return 회원의 리조트 정보
    * @throws Exception
    */
   public Map<String, Object> getAccntCheck(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwNewTypeMainConsMap.getAccntCheck", pmParam);
   }   
   
   /**
    * 2구좌 가입 제한 체크
    *
    * @param pmParam 검색 조건
    * @return Y/N
    * @throws Exception
    */
   public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getNoSaleAccnt", pmParam);
   }
   
   /**
    * 카드 코드 조회
    *
    * @param pmParam 검색 조건
    * @return 카드 코드
    * @throws Exception
    */
   public String getCardCode(String psParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getCardCode", psParam);
   }
   
   /**
    * 회원번호 생성
    *
    * @param pmParam 검색 조건
    * @return 회원번호
    * @throws Exception
    */
   public String createAccntNo(String psParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.createAccntNo", psParam);
   }
   
   /**
    * 회원_상품_계좌 등록
    *
    * @param pmParam 검색 조건
    * @return 회원번호
    * @throws Exception
    */
   public int insertMemProdAccnt(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertMemProdAccnt", pmParam);
   }
   
   /**
    * 해피콜 승인상태 체크
    *
    * @param pmParam 검색 조건
    * @return 해피콜 승인여부
    * @throws Exception
    */
   public String getHpclAckdStatChk(Map<String, ?> pmParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getHpclAckdStatChk", pmParam);
   }
   
   /**
    * 해피콜 승인 등록
    *
    * @param pmParam 해피콜 승인정보
    * @return
    * @throws Exception
    */
   public int insertHpclAckd(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertHpclAckd", pmParam);
   }

   /**
    * 해피콜 승인 수정
    *
    * @param pmParam 해피콜 승인정보
    * @return
    * @throws Exception
    */
   public int updateHpclAckd(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.updateHpclAckd", pmParam);
   }
   
   /**
    * 해피콜 이력 등록
    *
    * @param pmParam 해피콜 이력 정보
    * @return
    * @throws Exception
    */
   public int insertHpclHist(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertHpclHist", pmParam);
   }
   
   /**
    * 리조트 번호 조회
    *
    * @param psParam 회원번호
    * @return 리조트 번호
    * @throws Exception
    */
   public String getResortNo(String psParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getResortNo", psParam);
   }
   
   /**
    * 스마트라이프 상담 등록
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertSmartLifeCnslMng(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertSmartLifeCnslMng", pmParam);
   }
   
   /**
    * 부가서비스 등록
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertMemProdAccntSvc", pmParam);
   }
   
   /**
    * 두구좌 계정 정보저장
    *
    * @param pmParam 검색 조건
    * @return 회원번호
    * @throws Exception
    */
   public int insertMemTwinAccnt(Map<String, Object> pmParam) {
 	  insert("DlwNewTypeMainConsMap.insertMemTwinAccnt1", pmParam);
 	  insert("DlwNewTypeMainConsMap.insertMemTwinAccnt2", pmParam);
 	  return 2;
   }
   
   /**
    * 스마트라이프 상담 수정
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int updateMemProdAccnt(Map<String, ?> pmParam) throws Exception {
    //   CommonUtils.printLog(pmParam);
       return update("DlwNewTypeMainConsMap.updateMemProdAccnt", pmParam);

   }
   
   /**
    * 부가서비스 삭제
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int deleteMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
       return delete("DlwNewTypeMainConsMap.deleteMemProdAccntSvc", pmParam);
   }
   
   /**
    * 해피콜 정보의 검색 수를 반환한다.
    *
    * @param pmParam 검색 조건
    * @return 해피콜 정보의 검색 건수
    * @throws Exception
    */
   public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception {
       return (Integer) selectOne("DlwNewTypeMainConsMap.getdeliveryCnt", pmParam);
   }
   
   /**
    *  발주관리  설치장소 업데이트
    *
    * @param pmParam 검색 조건
    * @return 회원번호
    * @throws Exception
    * */
   public int updatedelivery(Map<String, ?> pmParam) throws Exception {
       return update("DlwNewTypeMainConsMap.updatedelivery", pmParam);
   }
   
   /**
    * 녹취 상품 정보를 등록한다.
    *
    * @param pmParam 녹취 상품 정보
    * @throws Exception
    */
   public int mergeRecProdDtl(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.mergeRecProdDtl", pmParam);
   }
   
   /**
    * CMS 출금이체 신청중인지 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public String getCmsWdrwReqChk(String psParam) throws Exception {
       return (String) selectOne("DlwConsProdMap.getCmsWdrwReqChk", psParam);
   }

   /**
    * 카드 출금이체 신청중인지 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public String getCardWdrwReqChk(String psParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getCardWdrwReqChk", psParam);
   }

   /**
    * 카드 출금이체 신청중인지 체크
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public String getPayChk(String psParam) throws Exception {
       return (String) selectOne("DlwNewTypeMainConsMap.getPayChk", psParam);
   }
   
   /**
    * 대명라이프웨이 부가정보 조회
    *
    * @param pmParam 검색 조건
    * @return  부가정보
    * @throws Exception
    */
   public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
       return selectList("DlwNewTypeMainConsMap.getBugaInfo", pmParam);
   }
   
   /**
    * 사원 상세정보를 검색한다.
    *
    * @param pmParam 검색 조건
    * @return 사원 상세정보
    * @throws Exception
    */
   public Map<String, Object> getDlwEmplDtpt(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwNewTypeMainConsMap.getDlwEmplList", pmParam);
   }
   
   /**
    * 납입정보(부가서비스)등록 회원을 체크한다.
    *
    * @param pmParam 고객정보
    * @return 회원정보
    * @throws Exception
    */
   public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception {
       return selectOne("DlwNewTypeMainConsMap.getBugaSrvcMemChk", pmParam);
   }
   
   /* ================================================================
    * 날짜 : 20180809
    * 이름 : 송준빈
    * 추가내용 : 최근 애큐온 인증 여부 Y/N
    * 대상 테이블 : TB_MEMBER_NICE_INFO
    * ================================================================
    * */
	public List<Map<String, Object>> getAcuonLatelyAuth(Map<String, Object> pmParam) {
		return selectList("DlwNewTypeMainConsMap.getAcuonLatelyAuth", pmParam);
	}
	
	/**
     * 저장되어 있는 회원번호별 부가서비스관리 이력 조회
     *
     * @param pmParam 검색 조건
     * @return map
     * @throws Exception
     */

    public List<Map<String, Object>> selectSvcAccntNoHist(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.selectSvcAccntNoHist", pmParam);
    }
    
    /**
     * 저장되어 있는 회원번호별 전체 부가서비스 이력 조회
     *
     * @param pmParam 검색 조건
     * @return map
     * @throws Exception
     */

    public List<Map<String, Object>> selectMemProdAccntSvcTot(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.selectMemProdAccntSvcTot", pmParam);
    }
    
    /**
     *  상담에서 변경되면 부가서비스 이력에도 수정하도록 처리하는 로직
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.updateCallCenterVatSvcHist", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서내용 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getCertfMng(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getCertfMng", pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 해약율과 해약금 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getResnAmt_01(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getResnAmt2", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public int insertMemberNiceInfoSecond(Map<String, ?> pmParam) {
		return insert("DlwNewTypeMainConsMap.insertMemberNiceInfoSecond", pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve2(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getMemberNiceRetrieve2", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no) {
        return selectOne("DlwNewTypeMainConsMap.getWdrwReqAccntConfirm", accnt_no);
    }
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) {
        return selectList("DlwNewTypeMainConsMap.getWdrwReqAccntEventConfirm", accnt_no);
    }
    
    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiExtConfirmList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getMemberMangiExtConfirmList", pmParam);
    }
    
    /**
     * 대명라이프웨이 Card 고객정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보 건수
     * @throws Exception
     */
    public int getDlwCardCsmmCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwCardCsmmCount", pmParam);
    }
    
    /**
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardCsmm(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwCardCsmm", pmParam);
    }
    
    /**
     * 대명라이프웨이 Cms 고객정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보 건수
     * @throws Exception
     */
    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwCmsCsmmCount", pmParam);
    }

    /**
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwCmsCsmm", pmParam);
    }
    
    /**
     * 발주정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 발주정보
     * @throws Exception
     */
    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getOdrgInfo", pmParam);
    }
    
    /**
     * 물류 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlvList", pmParam);
    }
    
    /**
     * 납입이력(상조부금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwPayMngList", pmParam);
    }

    /**
     * 납입이력(결합상품할부원금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품할부원금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwPayMngDtlList", pmParam);
    }

    /**
     * 납입이력(결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwPayMngDtl1List", pmParam);
    }
    
    /**
     * 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngAllList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwPayMngAllList", pmParam);
    }
    
    /**
     * 해약정보_상품정보 및 가입일자에 따른 SEQ, 적용일자를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약정보
     * @throws Exception
     */
    public Map<String, Object> getDlwResnAmtList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwResnAmtList", pmParam);
    }
    
    /**
     * 대명라이프웨이 해약환급금 D 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAmtDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getResnAmtDetailList", pmParam);
    }
    
    /**
     * 결제정보를 검색한다. (개별목록 신청내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 결제정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwPymnHstrList", pmParam);
    }
    
    /**
     * 청구내역을 검색한다. (개별목록 청구내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 청구내역 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwAskHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwAskHstrList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20181113
     * 이름 : 송준빈
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwLogList(Map<String, Object> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getMainWdrwLogList", pmParam);
    }
    
    /**
     * 고객 정보를 검색한다. (상담테이블 설정)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasiConsInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getCustBasiConsInfo", pmParam);
    }
    
    /**
     * 상담 정보를 등록한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int insertCons(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertCons", pmParam);
    }
    
    /**
     * 상담이력 정보를 등록한다.
     *
     * @param pmParam 상담이력 정보
     * @throws Exception
     */
    public int insertConsHstr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertConsHstr", pmParam);
    }
    
    /**
     * 상담 그룹 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsGroup(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getConsGroup", pmParam);
    }
    
    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getConsCount", pmParam);
    }
    
    /**
     * 엔컴 상담정보 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담정보 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwConsCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getDlwConsCount", pmParam);
    }
    
    /**
     * 엔컴 상담정보 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getDlwConsList", pmParam);
    }
    
    /**
     * 홈페이지 회원 변경 이력 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원 변경 이 검색 건수
     * @throws Exception
     */
    public int getDlwlifeweyCount(Map<String, ?> pmParam) {
        return (Integer) selectOne("DlwNewTypeMainConsMap.getDlwlifeweyCount", pmParam);
    }
    
    /**
     * 홈페이지 회원 변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원 변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) {
        return selectList("DlwNewTypeMainConsMap.getDlwlifeweyList", pmParam);
    }
    
	/**
     * 상품 검색한다. (NEWTYPE)
     *
     * @param pmParam 고객정보
     * @return 고객상세정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProductList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwProductList", pmParam);
    }
    
    /**
     * 고객정보 신용점수를 등록한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int insertMemberCredit(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertMemberCredit", pmParam);
    }
    
    /**
     * LMS 문자메세지를 전송한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertSmsSend(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertSmsSend", pmParam);
    }
    
    /**
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public int insertChatSndgHstr(Map<String, ?> pmParam) throws Exception {
       return insert("DlwNewTypeMainConsMap.insertChatSndgHstr", pmParam);
    }
    
	/**
     * 회원 정보 중 마케팅 정보 조회
     * TB_MEMBER_MKT_INFO
     */
    public Map<String, Object> getDlwMarketingInfoList(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwMarketingInfoList", pmParam);
    }
    
    /**
     * 마케팅정보 등록
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int updateMktInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.updateMktInfo", pmParam);
    }    
    
    /**
     * 마케팅정보 등록
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int insertMktInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwNewTypeMainConsMap.insertMktInfo", pmParam);
    }      
    
	/**
     * 회원 정보 중 마케팅 히스토리정보 조회
     * TB_MEMBER_MKT_INFO
     */
    public  List<Map<String, Object>>  getDlwMarketinghstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwNewTypeMainConsMap.getDlwMarketinghstrList", pmParam);
    }
    
    /**
     * 회원 정보 중 마케팅 정보 조회
     * TB_MEMBER_MKT_INFO
     */
    public Map<String, Object> getDlwMarketGroup(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwNewTypeMainConsMap.getDlwMarketGroup", pmParam);
    }
    
    /**
     * 마케팅정보 등록
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int mergeMktInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.mergeMktInfo", pmParam);
    }
    
    /**
     * 고객정보를 수정한다.
     *
     * @param pmParam 고객정보
     * @throws Exception
     */
    public int updateMemberMkt(Map<String, ?> pmParam) throws Exception {
        return update("DlwNewTypeMainConsMap.updateMemberMkt", pmParam);
    }
}
