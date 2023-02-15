package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwOthersService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;
import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class DlwOthersServiceImpl extends EgovAbstractServiceImpl implements DlwOthersService {

      @Resource
        DlwOthersDao dlwOthersDao;

      private final Logger log = LoggerFactory.getLogger(DlwOthersServiceImpl.class);

      //회사정보 조회
      public List<Map<String, Object>> getUseEntrList(Map<String, ?> pmParam) throws Exception{
          return dlwOthersDao.getUseEntrList(pmParam);
          }
    //회사정보 수정
      public void updateUseEntr(Map<String, Object> hmParam ) throws Exception {
          dlwOthersDao.updateUseEntr(hmParam);
        }


      //cms 기초환경설정 조회
      public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception{
          return dlwOthersDao.getDlwCmsComnInfo(pmParam);
        }
    //cms 기초환경설정 수정
      public void updateCMSComInfo(Map<String, Object> hmParam ) throws Exception {
          dlwOthersDao.updateCMSComInfo(hmParam);
        }


    //그룹코드  조회
      public List<Map<String, Object>> getGroupCdList(Map<String, ?> pmParam) throws Exception{
          return dlwOthersDao.getGroupCdList(pmParam);
          }
      // 그룹코드 등록
      public int insertComCdGrp(Map<String, ?> pmParam) throws Exception {
          return dlwOthersDao.insertComCdGrp(pmParam);
      }
      /* 그룹코드 수정 */
      public int updateComCdGrp(Map<String, Object> pmParam) throws Exception {
          ParamUtils.addSaveParam(pmParam);
          return  dlwOthersDao.updateComCdGrp(pmParam);
       }



     /* public String getMaxComCd(Map<String, ?> pmParam) throws Exception {
          return (String) dlwOthersDao.getMaxComCd(pmParam);

       }*/
            //공통코드 팝업 조회
      public List<Map<String, Object>> selectComCdGrpLis(Map<String, ?> pmParam) throws Exception{
          return dlwOthersDao.selectComCdGrpLis(pmParam);
          }
      // 공통코드 등록
      public int insertComCd(Map<String, ?> pmParam) throws Exception {
          return dlwOthersDao.insertComCd(pmParam);
      }
      /* 공통코드 수정 */
      public int updateComCd(Map<String, Object> pmParam) throws Exception {
          ParamUtils.addSaveParam(pmParam);
          return  dlwOthersDao.updateComCd(pmParam);
       }

      /* 공통코드 삭제 */
      public int deleteComcd(Map<String, ?> pmParam) throws Exception {
          return  dlwOthersDao.deleteComcd(pmParam);
       }


      //불능코드 팝업 조회
          public List<Map<String, Object>> getImpsCdList(Map<String, ?> pmParam) throws Exception{
              return dlwOthersDao.getImpsCdList(pmParam);
              }


          /****  불능코드 등록   ****/
          public String insertImpsCd(Map<String, Object> pmParam) throws Exception {

              int dupl_custmrCd = dlwOthersDao.getDuplImpsCd(pmParam); //불능코드 코드 중복 여부 가져옴

              if (dupl_custmrCd > 0) {
                                          return "중복";
                                      }
                  String rslt = "1";
                  ParamUtils.addSaveParam(pmParam);
                  dlwOthersDao.insertImpsCd(pmParam);

              return "1";
          }


          /* 불능코드 삭제 */
          public int deleteImpsCd(Map<String, ?> pmParam) throws Exception {
              return  dlwOthersDao.deleteImpsCd(pmParam);
           }






          //불능코드 팝업 조회
          public List<Map<String, Object>> selectMonTargetNoList(Map<String, ?> pmParam) throws Exception{
              return dlwOthersDao.selectMonTargetNoList(pmParam);
              }


          /**
           * 월목표건수 등록/수정/삭제
           *
           * @param XPlatformMapDTO xpDto
           * @return int
           * @throws Exception
           */
          public int saveMonTarget(XPlatformMapDTO xpDto) throws Exception {

              Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
              int iCudCnt = 0;
              int rowType = -1;
              Map hmParam = null;

              try {
                  DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                  if (listInDs != null && listInDs.size() > 0) {
                          for (int i=0; i<listInDs.size(); ++i) {
                              hmParam = listInDs.get(i);
                              ParamUtils.addSaveParam(hmParam);

                              rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                              if (rowType == DataSet.ROW_TYPE_INSERTED) {
                                  iCudCnt += dlwOthersDao.insertMonTargetNo(hmParam);
                              }
                          /*	else if (rowType == DataSet.ROW_TYPE_UPDATED) {  //수정부분
                                  iCudCnt += dlwOthersDao.updateCustmrMng(hmParam);
                              }	*/
                              else if (rowType == DataSet.ROW_TYPE_DELETED) {
                                  iCudCnt += dlwOthersDao.deleteMonTargetNo(hmParam);
                              }
                          }
                  }
              } catch (Exception e) {
                  throw e;
              }
              return iCudCnt;
          }

          /**
           * 반송 발송 관리 회원 리스트 count
           *
           * @param pmParam 검색 조건
           * @return 반송 발송 정보의 검색 건수
           * @throws Exception
           */
          public int getPostSendListCount(Map<String, ?> pmParam) throws Exception {
              //return (Integer) custBasiDAO.getCustBasiCount(pmParam);
              return (Integer) dlwOthersDao.getPostSendListCount(pmParam);
          }

          /**
           * 반송 발송 관리 회원 리스트
           *
           * @param pmParam 검색 조건
           * @return 고객 정보 목록
           * @throws Exception
           */
          public List<Map<String, Object>> getPostSendList(Map<String, ?> pmParam) throws Exception {
              //return custBasiDAO.getCustBasiList(pmParam);
              return dlwOthersDao.getPostSendList(pmParam);
          }

          /* 발송 반송 데이터 업로드 로직 */
          public void uploadToNas(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

              String tempDir = System.getProperty("java.io.tmpdir");
              log.info("---uploadToNas 서비스 - 1");

              MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
              Enumeration enm = multipartRequest.getFileNames();
              String sKey = "";

              int fileCnt			= 0;

              Map<String, Object> mParam = null;

              // 엑셀파일 워크북 객체 생성
              XSSFWorkbook workbook = null;

              // 시트 지정
              XSSFSheet sheet = null;

              // 로우
              XSSFRow xrow = null;

              // cell
              XSSFCell xcell = null;

              List<Map<String,Object>> lst = new ArrayList<>();
              Map<String,Object> mRow = new HashMap<>();

              DataSetMap listMap = new DataSetMap();
              Map<String, Object> hmParam = new HashMap<String, Object>();

              try {

            	  	mParam = new HashMap<>();

              	// 실제로는 단건만 처리함
                  while (enm.hasMoreElements())
                  {
                  	fileCnt++;
                      sKey = (String)enm.nextElement();
                      log.debug("sKey : " + sKey);
                      File upfile = multipartRequest.getFile(sKey);
                      log.debug("upfile.length() : " +upfile.length());
                      if (upfile.exists()) {
                      	log.debug("file exists");
                      } else {
                      	log.debug("file does not exists");
                      }

                      // 엑셀파일 워크북 객체 생성
                      workbook = new XSSFWorkbook(new FileInputStream(upfile));

                      sheet = workbook.getSheetAt(0);

                      Row row;
                      Cell cell;
                      String value;
                      int i=0, j;
                      boolean essential = false;
                      String str_essential = "";
                      int iAccntCnt = 0 ;  //회원번호 여부 체크용

                      int rows = sheet.getPhysicalNumberOfRows();

                      log.debug("Excel Rows : " + rows);

                      for (i=0;i<rows;i++){
                      	xrow = sheet.getRow(i);
                      	int cells = xrow.getPhysicalNumberOfCells();

                      	if (i < 5) continue;

                      	for(j=0;j<cells;j++){
                      		xcell = xrow.getCell(j);
                      		value = "";

                      		log.debug("Excel Cells : " + j);

                      		//System.out.println(xcell.getCellType());

                      		switch (xcell.getCellType()) {
      						case Cell.CELL_TYPE_BOOLEAN:
      							value = "" + xcell.getBooleanCellValue();
      							break;
      						case Cell.CELL_TYPE_NUMERIC:
      							value = "" + xcell.getNumericCellValue();
      							break;
      						case Cell.CELL_TYPE_STRING:
      							value = "" + xcell.getStringCellValue();
      							break;
      						case Cell.CELL_TYPE_BLANK:
      							value = " ";
      							break;
      						default:
      							value = "" + xcell.getStringCellValue();
      							break;
      						}

      						if (null != value) {
      							value = value.trim();
      						}

      						switch (j) {
                              case 0:
                              	mRow.put("dlv_id", value);
                              	break;
                              case 1:
                              	mRow.put("mem_nm", value);
                              	break;
                              case 2:
                              	mRow.put("accnt_no", value);
                              	break;
                              case 7:
                              	mRow.put("dlv_no", value); // 송장번호
                              	break;
                              case 8:
                              	mRow.put("dlv_dsps_dt", value); // 송장일
                              	break;
                              case 11:
                              	mRow.put("sndg_kind_cd", value); //배송(발송)종류 > 증서 멤버십 등등
                              	break;
                              case 12:
                              	mRow.put("dlv_type_cd", value); // 배송(발송)유형 > 택배, 등기 듣등
                              	break;
                              case 13:
                              	mRow.put("dlv_stat_cd", value); // 진행상태(배송상태) 발솔 반송
                              	break;
                              case 14:
                              	mRow.put("dsps_rsn_cd", value); // 처리사유코드 > 이사 폐문 등등
                              	break;
                              case 15:
                              	mRow.put("dsps_mthd_cd", value); //처리방법코드> 해피콜 , 재발송 등 내부 처리방법
                              	break;
                              case 16:
                              	mRow.put("dlv_dsps_rsn_cd", value); // 처리결과 코드 (폐기)> 성명오기재, 수령완료, 재발급 완료
                              	break;

                              default:
                              	break;
                              }
                      	}

                      	lst.add(mRow);
                  		listMap.setRowMaps(lst);
                  		hmParam = listMap.get(0);

                        ParamUtils.addSaveParam(hmParam);

                      if (hmParam.get("dlv_id").equals("")){
                          iAccntCnt = getDlvAccntCount(hmParam);
                          if (iAccntCnt > 0) {
                          	throw new EgovBizException("회원번호가 중복 되었습니다. > " + hmParam.get("accnt_no"));
                          }
                      }

                  	saveDlv(hmParam);

                   }

                   upfile.delete();

                  }

                  if (fileCnt < 1) {
                	  throw new EgovBizException("업로드된 파일이 없습니다.");
                  }

              } catch (FileNotFoundException ex) {

              	ex.printStackTrace();

                  throw ex;
              } catch (IOException ex) {

              	ex.printStackTrace();

                  throw ex;
              } finally {
              }

          }

          /* 회원번호 엑셀 업로드 로직 */
          public void uploadToAccnt(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

              String tempDir = System.getProperty("java.io.tmpdir");
              log.info("---uploadToNas 서비스 - 1");

              MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
              Enumeration enm = multipartRequest.getFileNames();
              String sKey = "";

              int fileCnt			= 0;

              Map<String, Object> mParam = null;

              // 엑셀파일 워크북 객체 생성
              XSSFWorkbook workbook = null;

              // 시트 지정
              XSSFSheet sheet = null;

              // 로우
              XSSFRow xrow = null;

              // cell
              XSSFCell xcell = null;

              List<Map<String,Object>> lst = new ArrayList<>();
              Map<String,Object> mRow = new HashMap<>();

              DataSetMap listMap = new DataSetMap();
              Map<String, Object> hmParam = new HashMap<String, Object>();

              try {

            	  	mParam = new HashMap<>();

              	// 실제로는 단건만 처리함
                  while (enm.hasMoreElements())
                  {
                  	fileCnt++;
                      sKey = (String)enm.nextElement();
                      log.debug("sKey : " + sKey);
                      File upfile = multipartRequest.getFile(sKey);
                      log.debug("upfile.length() : " +upfile.length());
                      if (upfile.exists()) {
                      	log.debug("file exists");
                      } else {
                      	log.debug("file does not exists");
                      }

                      // 엑셀파일 워크북 객체 생성
                      workbook = new XSSFWorkbook(new FileInputStream(upfile));

                      sheet = workbook.getSheetAt(0);

                      Row row;
                      Cell cell;
                      String value;
                      int i=0, j;
                      boolean essential = false;
                      String str_essential = "";
                      int iAccntCnt = 0 ;  //회원번호 여부 체크용

                      int rows = sheet.getPhysicalNumberOfRows();

                      if (rows > 1005){
                          throw new EgovBizException("업로드정보가 1000건이 넘습니다. 전산팀에 업로드 요청 부탁드립니다.!!");
                      }

                      log.debug("Excel Rows : " + rows);



                      for (i=0;i<rows;i++){
                      	xrow = sheet.getRow(i);
                      	int cells = xrow.getPhysicalNumberOfCells();

                      	if (i < 5) continue;

						xcell = xrow.getCell(0);
						value = "";

                  		switch (xcell.getCellType()) {
	  						case Cell.CELL_TYPE_BOOLEAN:
	  							value = "" + xcell.getBooleanCellValue();
	  							break;
	  						case Cell.CELL_TYPE_NUMERIC:
	  							value = "" + xcell.getNumericCellValue();
	  							break;
	  						case Cell.CELL_TYPE_STRING:
	  							value = "" + xcell.getStringCellValue();
	  							break;
	  						case Cell.CELL_TYPE_BLANK:
	  							value = " ";
	  							break;
	  						default:
	  							value = "" + xcell.getStringCellValue();
	  							break;
  						}

  						if (null != value) {
  							value = value.trim();
  						}

  						mRow.put("cell", value);

                      	lst.add(mRow);
                  		listMap.setRowMaps(lst);
                  		hmParam = listMap.get(0);

                        ParamUtils.addSaveParam(hmParam);

                        saveAccnt(hmParam);
                   }

                   upfile.delete();

                  }

                  if (fileCnt < 1) {
                	  throw new EgovBizException("업로드된 파일이 없습니다.");
                  }

              } catch (FileNotFoundException ex) {

              	ex.printStackTrace();

                  throw ex;
              } catch (IOException ex) {

              	ex.printStackTrace();

                  throw ex;
              } finally {
              }

          }

          /**
           * 회원번호의 증서가 있는지 알기위해 회원번호 개수
           *
           * @param pmParam 검색 조건
           * @return Dlv 정보의 검색 수
           * @throws Exception
           */
          public int getDlvAccntCount(Map<String, ?> pmParam) throws Exception {
              return (Integer) dlwOthersDao.getDlvAccntCount(pmParam);
          }

          /**
           * 발송 /반송 엑셀 업로드 데이터 저장부분
           *
           * @param pmParam 물류 정보
           * @throws Exception
           */
          public String saveDlv(Map<String, Object> pmParam) throws Exception {
              String sKey = StringUtils.defaultString((String) pmParam.get("dlv_id"));

              String sDlvStatCd = StringUtils.defaultString((String) pmParam.get("dlv_stat_cd"));
              String sOldDlvStatCd = StringUtils.defaultString((String) pmParam.get("old_dlv_stat_cd"));
              String sAccntno = StringUtils.defaultString((String) pmParam.get("accnt_no"));
              String dlv_dsps_dt = StringUtils.defaultString((String) pmParam.get("dlv_dsps_dt"));

              pmParam.put("dlv_dsps_dt", dlv_dsps_dt);

              CommonUtils.printLog(pmParam);

              System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 11111: " + sAccntno);

              // 반송일 경우 배송번호 제거, 이외에는 처리방법 및 처리결과 제거
              if (sDlvStatCd.equals("20")) {
                  pmParam.remove("dlv_no");
              } else {
                  pmParam.remove("dsps_mthd_cd");
                  pmParam.remove("dsps_rsn_cd");
              }

              System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 2222: " + sKey);

              if ("".equals(sKey)) {
                  int nResult = dlwOthersDao.insertDlv(pmParam);
                  dlwOthersDao.insertDlvDtl(pmParam);

                  if (nResult > 0) {
                      sKey = (String) pmParam.get("dlv_id");
                  }

                  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 333: " + sKey);

              } else {
                  if (!sDlvStatCd.equals(sOldDlvStatCd)) {
                	  dlwOthersDao.insertDlvDtl(pmParam);
                  } else {
                	  dlwOthersDao.updateDlvDtl(pmParam);
                  }
                  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 444: " + sKey);
              }

              return sKey;
          }

         /**
	     * 발송 / 반송 상세 정보의 검색 를 반환한다.
	     *
	     * @param pmParam 검색 조건
	     * @return 물류상세 리스트
	     * @throws Exception
	     */

	    public List<Map<String, Object>> getPostSendDtlList(Map<String, ?> pmParam) throws Exception {
	        return dlwOthersDao.getPostSendDtlList(pmParam);
	    }

        /**
         * 회원번호 엑셀 업로드 데이터 저장부분
         *
         * @param pmParam 물류 정보
         * @throws Exception
         */
        public String saveAccnt(Map<String, Object> pmParam) throws Exception {
            String sAccntno = StringUtils.defaultString((String) pmParam.get("accnt_no"));

            CommonUtils.printLog(pmParam);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 11111: " + sAccntno);

            dlwOthersDao.insertAccnt(pmParam);

            return sAccntno;
        }

        /**
	     * 전화번호 엘셀 업로드 데이터 회원조회결과
	     *
	     * @param pmParam 검색 조건
	     * @return 물류상세 리스트
	     * @throws Exception
	     */

	    public List<Map<String, Object>> getInfoofCellList(Map<String, ?> pmParam) throws Exception {
	        return dlwOthersDao.getInfoofCellList(pmParam);
	    }

        /* 회원정보 엑셀 업로드를 위한 회원 삭제 */
        public int deleteAccnt() throws Exception {
            return  dlwOthersDao.deleteAccnt();
         }




}
