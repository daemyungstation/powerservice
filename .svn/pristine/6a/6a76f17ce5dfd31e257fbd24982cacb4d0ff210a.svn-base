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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;

import powerservice.business.dlw.service.DlwAlowProportCalcService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class DlwAlowProportCalcServiceImpl extends EgovAbstractServiceImpl implements DlwAlowProportCalcService {

	@Resource
    public DlwAlowProportCalcDAO dlwAlowProportCalcDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwAlowProportCalcServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
    public int getAlowProportAccntCount(Map<String, ?> pmParam) throws Exception {
        return dlwAlowProportCalcDAO.getAlowProportAccntCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 회원별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.ALOW_DA_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportAccntList(Map<String, ?> pmParam) throws Exception {
        return dlwAlowProportCalcDAO.getAlowProportAccntList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회수
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public int getAlowProportProdCount(Map<String, ?> pmParam) throws Exception {
        return dlwAlowProportCalcDAO.getAlowProportProdCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 상품별수당안분 조회리스트
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getAlowProportProdList(Map<String, ?> pmParam) throws Exception {
        return dlwAlowProportCalcDAO.getAlowProportProdList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210603
     * 이름 : 송준빈
     * 추가내용 : 수당안분계산대상 고객업로드
     * 대상 테이블 : LF_DMUSER.TMP_PROPORT_CALC
     * ================================================================
     * */
    public void alowAccntExcelUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

        String tempDir = System.getProperty("java.io.tmpdir");
        //LOGGER.info("---uploadToSms 서비스 - 1");

        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
        Enumeration enm = multipartRequest.getFileNames();
        String sKey = "";

        int fileCnt	= 0;

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

        try 
        {
        	mParam = new HashMap<>();
        	
        	dlwAlowProportCalcDAO.deleteTmpProportCalc(hmParam);

            // 실제로는 단건만 처리함
            while (enm.hasMoreElements())
            {
                fileCnt++;
                sKey = (String)enm.nextElement();

                log.debug("sKey : " + sKey);

                File upfile = multipartRequest.getFile(sKey);
                String sUpFileName = upfile.getName();

                log.debug("upfile.length() : " +upfile.length());

                if (upfile.exists()) 
                {
                    log.debug("file exists");
                } 
                else 
                {
                    log.debug("file does not exists");
                }

                // 엑셀파일 워크북 객체 생성
                workbook = new XSSFWorkbook(new FileInputStream(upfile));

                sheet = workbook.getSheetAt(0);

                String value;
                int i=0, j;


                int rows = sheet.getPhysicalNumberOfRows();

                if (rows > 10005)
                {
                    throw new EgovBizException("업로드정보가 10000건이 넘습니다. 전산팀에 업로드 요청 부탁드립니다.!!");
                }

                log.debug("Excel Rows : " + rows);

                for (i=0; i < rows; i++)
                {
                    xrow = sheet.getRow(i);
                    int cells = xrow.getPhysicalNumberOfCells();

                    if (i < 1)
                    {
                        continue;
                    }

                    for(j = 0; j < cells; j++)
                    {
                        xcell = xrow.getCell(j);

                        value = "";

                        switch (xcell.getCellType())
                        {
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

                        if (null != value)
                        {
                            value = value.trim();
                        }
                        
                        switch (j)
                        {
                            case 0:
                                mRow.put("accnt_no", value);
                            break;

                            default:

                            break;
                        }
                        
                    }

                    lst.add(mRow);
                    listMap.setRowMaps(lst);
                    hmParam = listMap.get(0);
                    
                    dlwAlowProportCalcDAO.insertAlowAccntExcel(hmParam);
                    /*
                    int iRegYn = DlwPayDAO.existUplusPrepayHoldData(hmParam);
                    
                    if(iRegYn <= 0)
                    {
                    	DlwPayDAO.insertUplusPrepayHoldData(hmParam);
                    }
                    */
                }

                upfile.delete();
            }

            if (fileCnt < 1)
            {
                throw new EgovBizException("업로드된 파일이 없습니다.");
            }

        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        finally
        {

        }
    }
}