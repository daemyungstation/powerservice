/*
 * (@)# DlvServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */
package powerservice.business.chn.service.impl;

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

import powerservice.business.chn.service.DlvService;
import powerservice.business.chn.web.DlvController;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 물류 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/28
 * @프로그램ID PS450400
 *
 */
@Service
public class DlvServiceImpl extends EgovAbstractServiceImpl implements DlvService {

    @Resource
    public DlvDAO dlvDAO;

    private final Logger log = LoggerFactory.getLogger(DlvController.class);

    /**
     * 물류/물류상세 정보를 등록/수정한다.
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



            int nResult = dlvDAO.insertDlv(pmParam);
            dlvDAO.insertDlvDtl(pmParam);

            if (nResult > 0) {
                sKey = (String) pmParam.get("dlv_id");
            }

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 333: " + sKey);

        } else {
            if (!sDlvStatCd.equals(sOldDlvStatCd)) {
                dlvDAO.insertDlvDtl(pmParam);
            } else {
                dlvDAO.updateDlvDtl(pmParam);
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>. 444: " + sKey);
        }

        return sKey;
    }


    /*증서여부 */
    /**
     * 회원번호의 증서가 있는지 알기위해 회원번호 개수
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    public int getDlvAccntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlvDAO.getDlvAccntCount(pmParam);
    }

    /*	물류 */
    /**
     * 물류 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    public int getDlvCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlvDAO.getDlvCount(pmParam);
    }

    /**
     * 물류 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception {
        return dlvDAO.getDlvList(pmParam);
    }

    /**
     * 물류 정보를 삭제한다.
     *
     * @param pmParam 물류 정보
     * @throws Exception
     */
    public int deleteDlv(Map<String, ?> pmParam) throws Exception {
        int nResult = dlvDAO.deleteDlv(pmParam);

        return nResult;
    }


    /*	물류상세 */
    /**
     * 물류상세 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return Dlv 정보의 검색 수
     * @throws Exception
     */
    /*
    public int getDlvDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlvDAO.getDlvDtlCount(pmParam);
    }
    */
    /**
     * 물류상세 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류상세 리스트
     * @throws Exception
     */
    /*
    public List<Map<String, Object>> getDlvDtlList(Map<String, ?> pmParam) throws Exception {
        return dlvDAO.getDlvDtlList(pmParam);
    }
    */

    /**
     * 물류상세 정보를 삭제한다.
     *
     * @param pmParam 물류상세 정보
     * @throws Exception
     */
    public int deleteDlvDtl(Map<String, ?> pmParam) throws Exception {
        int nResult = dlvDAO.deleteDlvDtl(pmParam);

        return nResult;
    }


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

                for (i=0;i<rows;i++){
                	xrow = sheet.getRow(i);
                	int cells = xrow.getPhysicalNumberOfCells();

                	if (i < 5) continue;

                	for(j=0;j<cells;j++){
                		xcell = xrow.getCell(j);
                		value = "";

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
            		//CommonUtils.printLog(hmParam);

                    //Map<String, Object> mMemAccntInfo = null;
                    //mMemAccntInfo = dlwProdService.getMemProdAccntWithDlv(row.get("accnt_no").toString());

                    if (hmParam.get("dlv_id").equals("")){
                        iAccntCnt = getDlvAccntCount(hmParam);
                        if (iAccntCnt > 0) {
                        	throw new EgovBizException("회원번호가 중복 되었습니다. > " + hmParam.get("accnt_no"));
                        }
                    }

            		saveDlv(hmParam);

                }

                /*

                Iterator<Row> rowIterator = sheet.iterator();


				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					j=0;

					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>.." + rowIterator.next().getRowNum());


					while (cellIterator.hasNext()) {
						cell = cellIterator.next();

						value = "";

						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							value = "" + cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							value = "" + cell.getNumericCellValue();
							break;
						case Cell.CELL_TYPE_STRING:
							value = "" + cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_BLANK:
							value = " ";
							break;
						default:
							break;
						}

						if (null != value) {
							value = value.trim();
						}

						switch (j) {
                        case 0:
                        	mRow.put("DLV_ID", value);
                        	break;
                        case 1:
                        	mRow.put("MEM_NM", value);
                        	break;
                        case 2:
                        	mRow.put("ACCNT_NO", value);
                        	break;
                        case 7:
                        	mRow.put("DLV_NO", value); // 송장번호
                        	break;
                        case 8:
                        	mRow.put("DLV_DSPS_DT", value); // 송장일
                        	break;
                        case 11:
                        	mRow.put("SNDG_KIND_CD", value); //배송(발송)종류 > 증서 멤버십 등등
                        	break;
                        case 12:
                        	mRow.put("DLV_TYPE_CD", value); // 배송(발송)유형 > 택배, 등기 듣등
                        	break;
                        case 13:
                        	mRow.put("DLV_STAT_CD", value); // 진행상태(배송상태) 발솔 반송
                        	break;
                        case 14:
                        	mRow.put("DSPS_RSN_CD", value); // 처리사유코드 > 이사 폐문 등등
                        	break;
                        case 15:
                        	mRow.put("DSPS_MTHD_CD", value); //처리방법코드> 해피콜 , 재발송 등 내부 처리방법
                        	break;
                        case 16:
                        	mRow.put("DLV_DSPS_RSN_CD", value); // 처리결과 코드 (폐기)> 성명오기재, 수령완료, 재발급 완료
                        	break;

                        default:
                        	break;
                        }

						System.out.println("Value: " + value);
						j++;
					}


                	lst.add(mRow);
                	i++;
				}
				*/

                upfile.delete();



                /*

                // 리스트 로우 카운트

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>.1" + lst.size());


                listMap.setRowMaps(lst);
                int test = listMap.getRowMaps().size();

                if (listMap != null && listMap.size() > 0) {
                	for(int ii=0; ii <= listMap.size(); ii++){
                		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>.2" + ii);
                    	mResuslt = listMap.get(ii);

                        CommonUtils.printLog(mResuslt);
                	}
                }
                */
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



}
