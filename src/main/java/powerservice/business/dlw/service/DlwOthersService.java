package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Service
public interface DlwOthersService {

     public List<Map<String, Object>> getUseEntrList(Map<String, ?> pmParam) throws Exception;  //회사정보 조회

     public void updateUseEntr(Map<String, Object> srchInDs ) throws Exception; //회사정보 수정

     public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception; //CMS 기초환경설정 조회

     public void updateCMSComInfo(Map<String, Object> srchInDs ) throws Exception; //CMS 기초환경설정 수정


     public List<Map<String, Object>> getGroupCdList(Map<String, ?> pmParam) throws Exception; //그룹 코드 조회

     public int  insertComCdGrp(Map<String, ?> pmParam) throws Exception; //그룹 코드 등록

     public int updateComCdGrp(Map<String, Object> pmParam)  throws Exception;  //그룹 코드 수정



     public int deleteComcd(Map<String, ?> pmParam) throws Exception;	 //공통 코드 삭제

     public List<Map<String, Object>> selectComCdGrpLis(Map<String, ?> pmParam) throws Exception; //공통 코드 조회

     public int  insertComCd(Map<String, ?> pmParam) throws Exception; //공통 코드 등록

     public int updateComCd(Map<String, Object> pmParam)  throws Exception;  //공통 코드 수정


     public List<Map<String, Object>> getImpsCdList(Map<String, ?> pmParam) throws Exception; //불능 코드 조회

     public String  insertImpsCd(Map<String, Object> pmParam) throws Exception; //불능 코드 등록

     public int  deleteImpsCd(Map<String, ?> pmParam) throws Exception; //불능 코드 등록


     public int saveMonTarget(XPlatformMapDTO xpDto)  throws Exception; //월별목표건수 등록, 삭제

     public List<Map<String, Object>> selectMonTargetNoList(Map<String, ?> pmParam) throws Exception; //월별목표건수 리스트

     public int getPostSendListCount(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getPostSendList(Map<String, ?> pmParam) throws Exception;

     public void uploadToNas(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;

     public void uploadToAccnt(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;

     public String saveDlv(Map<String, Object> pmParam) throws Exception;

     public int getDlvAccntCount(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getPostSendDtlList(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getInfoofCellList(Map<String, ?> pmParam) throws Exception;

     public int  deleteAccnt() throws Exception; //회원정보 엑셀 업로드를 위한 회원삭제
}
