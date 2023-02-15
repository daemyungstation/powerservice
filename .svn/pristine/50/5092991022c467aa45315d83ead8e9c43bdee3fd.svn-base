package powerservice.common.util.converter.excel;

import powerservice.common.bean.PJExcelConvertResult;
import powerservice.common.bean.PJHeaderKey;
import powerservice.common.util.IDGenerator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
* 엑셀파일에서 데이터를 추출하는데 관련된 기능을 제공하는 객체
*
* Copyright (c) 2013 Company Inwoo Tech Inc.
*
* @author 조경태
* @version 1.0
* @date 2008. 12. 17
* @프로그램ID 프로그램ID입력
 */
public class ExcelConvertUtil {

    private String PRIMARYKEY_NAME = "";
    private String PK_PREFIX = "";
    private PJHeaderKey[] importExcelHeaderKeyArray;

    /**
     * PK 필드이름과, 업무코드(KEY Prefix), 엑셀파일 헤더 내용을
     * 파라미터로 받아 생성하는 생성자
     * @see powerservice.seed.common.util.poi.PJHeaderKey
     *
     * @param primareyKeyName PK 필드이름
     * @param pkPrefix 업무코드
     * @param importExcelHeaderKeys 엑셀파일 헤더 keySet
     */
    public ExcelConvertUtil(String primareyKeyName, String pkPrefix, PJHeaderKey[] importExcelHeaderKeys) {
        PRIMARYKEY_NAME = primareyKeyName;
        PK_PREFIX = pkPrefix;
        importExcelHeaderKeyArray = importExcelHeaderKeys;
    }

    /**
     * PK 아이디를 생성할 필요가 없는 경우 엑셀파일 헤더 내용만 파라미터로
     * 받아 생성하는 생성자
     *
     * @param importExcelHeaderKeys 엑셀파일 헤더 keySet
     */
    public ExcelConvertUtil(PJHeaderKey[] importExcelHeaderKeys) {
        importExcelHeaderKeyArray = importExcelHeaderKeys;
    }

    /**
     * 기본 생성자로는 생성을 못하게 막음
     * @see ExcelConvertUtil(String primareyKeyName, String pkPrefix, String[] importExcelHeaderKeys)
     */
    private ExcelConvertUtil() { }


    
    private XSSFSheet getSheetFromInputStream2007(String filename) {
    	File file2 = new File(filename);
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        

        try {
            workbook = new XSSFWorkbook(new FileInputStream(file2));
            sheet = workbook.getSheetAt(0);
        } catch (Exception except) {
            except.printStackTrace();
        }
        return sheet;
    }
    
    private HSSFSheet getSheetFromInputStream(String filename) {
    	File file2 = new File(filename);
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;

        try {
            workbook = new HSSFWorkbook(new FileInputStream(file2));
            sheet = workbook.getSheetAt(0);
        } catch (Exception except) {
            except.printStackTrace();
        }
        return sheet;
    }

    /**
     *
     * 엑셀파일의 내용을 추출하여 반환한다.
     *
     * @see powerservice.seed.common.util.poi.PJExcelConvertResult
     * @param Excel file InputStream
     * @return PJExcelConvertResult
     */
    public PJExcelConvertResult extractFile(String inputStream) {
        ArrayList succeedList = new ArrayList(); // 성공 리스트
        ArrayList failList = new ArrayList(); // 실패 리스트
        PJExcelConvertResult convertResult = null;
        String failComment = ""; // 실패사유
        boolean endFlag = false;
        int nEndCnt = 0;
        String tempCellValue;
        HashMap currentRowMap;
        HSSFRow currentRow;
        HSSFSheet sheet = getSheetFromInputStream(inputStream);

        // 변환하고자 하는 sheet의 데이터가 없다면 null리턴
        if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
            return convertResult;
        }

        try {
            for (int idx = 1; idx < sheet.getPhysicalNumberOfRows(); idx++) {
                currentRowMap = new HashMap();
                currentRow = sheet.getRow(idx);
                nEndCnt = 0; // 초기화

                // importExcelHeaderKeyArray
                if(!PRIMARYKEY_NAME.equals("")) {
                    currentRowMap.put(PRIMARYKEY_NAME, PK_PREFIX + IDGenerator.generate()); // PK 입력
                }
                if (currentRow != null) {
                    HSSFCell cell;
                    for(int colIdx=0; colIdx < importExcelHeaderKeyArray.length; colIdx++) {
                        // DB에 입력할 필요가 없이 단지 양식용 헤더라면 무시하고 넘어간다.
                        if(importExcelHeaderKeyArray[colIdx].getPassFlag()) {
                            continue;
                        }

                        // 각 필드별 내용을 입력한다.
                        cell = currentRow.getCell((short) colIdx);
                        if(cell == null || getCellValue(cell).trim().equals("")) {
                            tempCellValue = "";
                            nEndCnt ++;
                            //endFlag = true;
                            //break;
                        }
                        else{
                            tempCellValue = getCellValue(cell).trim();
                        }

                        // 현재 데이터가 가용한 데이터인지 검사한다.
                        // 1. 제거해야할 문자가 있다면 제거해준다.
                        if(importExcelHeaderKeyArray[colIdx].getRemoveStrings() != null ) {
                            String[] removeStrings= importExcelHeaderKeyArray[colIdx].getRemoveStrings();
                            for(int currentIndex=0; currentIndex < removeStrings.length; currentIndex++) {
                                tempCellValue = tempCellValue.replaceAll(removeStrings[currentIndex], "");
                            }
                        } else if(importExcelHeaderKeyArray[colIdx].getRemoveString() != null) {
                            tempCellValue = tempCellValue.replaceAll(importExcelHeaderKeyArray[colIdx].getRemoveString(), "");
                        }

                        // 2. 숫자형 데이터에 문자가 섞여 있는지 확인한다.
                        if(importExcelHeaderKeyArray[colIdx].getNumericOnlyFlag()) {
                            if(!tempCellValue.matches("[0-9]*")) {
                                if(failComment.equals("")) {
                                    failComment = "[" + idx + "] " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 숫자형 데이터에 문자가 섞여 있습니다.";
                                } else {
                                    failComment += ", " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 숫자형 데이터에 문자가 섞여 있습니다.";
                                }
                            }
                        }

                        // 3. 최대 사이즈를 넘었는지 확인한다.
                        if(tempCellValue.getBytes().length > importExcelHeaderKeyArray[colIdx].getMaxSize()) {
                            if(failComment.equals("")) {
                                failComment = "[" + idx + "] " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 최대사이즈 " + importExcelHeaderKeyArray[colIdx].getMaxSize() +"를 초과하였습니다.";
                            } else {
                                failComment += ", " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 최대사이즈 " + importExcelHeaderKeyArray[colIdx].getMaxSize() +"를 초과하였습니다.";
                            }
                        }

                        // 실패사유가 없으면 데이터 추가
                        if(failComment.equals("")) {
                            currentRowMap.put(importExcelHeaderKeyArray[colIdx].getKeyName(), tempCellValue);
                        }
                    }

                    // 현재 Row에 해당하는 모든 로우의 값이 없는 경우
                    if(nEndCnt == importExcelHeaderKeyArray.length) {
                        endFlag = true;
                    }
                    
                }
                
                if(endFlag) {
                    break;
                }                

                // 실패사유가 있다면 실패리스트에 실패사유 입력 없으면 성공리스트에 데이터 입력
                if(failComment.equals("")) {
                    succeedList.add(currentRowMap);
                } else {
                    failList.add(failComment);
                    failComment = ""; // 실패사유 초기화
                }
            }
            // 엑셀파일 성공 리스트와 실패리스트를 입력
            convertResult = new PJExcelConvertResult(succeedList, failList);
        } catch (Exception except) {
            except.printStackTrace();
        }

        return convertResult;
    }

    /**
     *
     * cell의 내용을 반환한다.
     *
     * @param cell
     * @return
     */
    private String getCellValue(HSSFCell cell) {
        String value = "";
        int cellType = cell.getCellType();

        switch (cellType) {
        case HSSFCell.CELL_TYPE_FORMULA :
            value = cell.getCellFormula();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC :
            value = String.valueOf(new Double(cell.getNumericCellValue()).longValue());
            break;
        case HSSFCell.CELL_TYPE_STRING :
            value = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_BLANK :
            value = "";
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN :
            value = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_ERROR :
            value = String.valueOf(cell.getErrorCellValue());
            break;
        }
        return value;
    }
    
    /**
    *
    * 엑셀파일의 내용을 추출하여 반환한다.(2007버전이상)
    *
    * @see powerservice.seed.common.util.poi.PJExcelConvertResult
    * @param Excel file InputStream
    * @return PJExcelConvertResult
    */
   public PJExcelConvertResult extractFile2007(String inputStream) {
       ArrayList succeedList = new ArrayList(); // 성공 리스트
       ArrayList failList = new ArrayList(); // 실패 리스트
       PJExcelConvertResult convertResult = null;
       String failComment = ""; // 실패사유
       boolean endFlag = false;
       int nEndCnt = 0;
       String tempCellValue;
       HashMap currentRowMap;
       XSSFRow currentRow;
       XSSFSheet sheet = getSheetFromInputStream2007(inputStream);

       // 변환하고자 하는 sheet의 데이터가 없다면 null리턴
       if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
           return convertResult;
       }

       try {
           for (int idx = 0; idx < sheet.getPhysicalNumberOfRows(); idx++) {
               currentRowMap = new HashMap();
               currentRow = sheet.getRow(idx);
               nEndCnt = 0; // 초기화

               // importExcelHeaderKeyArray
               if(!PRIMARYKEY_NAME.equals("")) {
                   currentRowMap.put(PRIMARYKEY_NAME, PK_PREFIX + IDGenerator.generate()); // PK 입력
               }
               if (currentRow != null) {
                   XSSFCell cell;
                   for(int colIdx=0; colIdx < importExcelHeaderKeyArray.length; colIdx++) {
                       // DB에 입력할 필요가 없이 단지 양식용 헤더라면 무시하고 넘어간다.
                       if(importExcelHeaderKeyArray[colIdx].getPassFlag()) {
                           continue;
                       }

                       // 각 필드별 내용을 입력한다.
                       cell = currentRow.getCell((short) colIdx);
                       if(cell == null || getCellValue2007(cell).trim().equals("")) {
                           tempCellValue = "";
                           nEndCnt ++;
                           //endFlag = true;
                           //break;
                       }
                       else{
                           tempCellValue = getCellValue2007(cell).trim();
                       }

                       // 현재 데이터가 가용한 데이터인지 검사한다.
                       // 1. 제거해야할 문자가 있다면 제거해준다.
                       if(importExcelHeaderKeyArray[colIdx].getRemoveStrings() != null ) {
                           String[] removeStrings= importExcelHeaderKeyArray[colIdx].getRemoveStrings();
                           for(int currentIndex=0; currentIndex < removeStrings.length; currentIndex++) {
                               tempCellValue = tempCellValue.replaceAll(removeStrings[currentIndex], "");
                           }
                       } else if(importExcelHeaderKeyArray[colIdx].getRemoveString() != null) {
                           tempCellValue = tempCellValue.replaceAll(importExcelHeaderKeyArray[colIdx].getRemoveString(), "");
                       }

                       // 2. 숫자형 데이터에 문자가 섞여 있는지 확인한다.
                       if(importExcelHeaderKeyArray[colIdx].getNumericOnlyFlag()) {
                           if(!tempCellValue.matches("[0-9]*")) {
                               if(failComment.equals("")) {
                                   failComment = "[" + idx + "] " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 숫자형 데이터에 문자가 섞여 있습니다.";
                               } else {
                                   failComment += ", " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 숫자형 데이터에 문자가 섞여 있습니다.";
                               }
                           }
                       }

                       // 3. 최대 사이즈를 넘었는지 확인한다.
                       if(tempCellValue.getBytes().length > importExcelHeaderKeyArray[colIdx].getMaxSize()) {
                           if(failComment.equals("")) {
                               failComment = "[" + idx + "] " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 최대사이즈 " + importExcelHeaderKeyArray[colIdx].getMaxSize() +"를 초과하였습니다.";
                           } else {
                               failComment += ", " + importExcelHeaderKeyArray[colIdx].getKeyName() + ": 최대사이즈 " + importExcelHeaderKeyArray[colIdx].getMaxSize() +"를 초과하였습니다.";
                           }
                       }

                       // 실패사유가 없으면 데이터 추가
                       if(failComment.equals("")) {
                           currentRowMap.put(importExcelHeaderKeyArray[colIdx].getKeyName(), tempCellValue);
                       }
                   }
                   if(idx > 18){
                   // 현재 Row에 해당하는 모든 로우의 값이 없는 경우
                   if(nEndCnt == importExcelHeaderKeyArray.length) {
                       endFlag = true;
                   }
                   }
               }
               
               if(endFlag) {
                   break;
               }                

               // 실패사유가 있다면 실패리스트에 실패사유 입력 없으면 성공리스트에 데이터 입력
               if(failComment.equals("")) {
                   succeedList.add(currentRowMap);
               } else {
                   failList.add(failComment);
                   failComment = ""; // 실패사유 초기화
               }
           }
           // 엑셀파일 성공 리스트와 실패리스트를 입력
           convertResult = new PJExcelConvertResult(succeedList, failList);
       } catch (Exception except) {
           except.printStackTrace();
       }

       return convertResult;
   }
   
   
   /**
   *
   * cell의 내용을 반환한다.(2007버전이상)
   *
   * @param cell
   * @return
   */
  private String getCellValue2007(XSSFCell cell) {
      String value = "";
      int cellType = cell.getCellType();

      switch (cellType) {
      case XSSFCell.CELL_TYPE_FORMULA :
          value = cell.getCellFormula();
          break;
      case XSSFCell.CELL_TYPE_NUMERIC :
          value = String.valueOf(new Double(cell.getNumericCellValue()).longValue());
          break;
      case XSSFCell.CELL_TYPE_STRING :
          value = cell.getStringCellValue();
          break;
      case XSSFCell.CELL_TYPE_BLANK :
          value = "";
          break;
      case XSSFCell.CELL_TYPE_BOOLEAN :
          value = String.valueOf(cell.getBooleanCellValue());
          break;
      case XSSFCell.CELL_TYPE_ERROR :
          value = String.valueOf(cell.getErrorCellValue());
          break;
      }
      return value;
  }
}
