package powerservice.common.util.excel;

import powerservice.common.util.DateUtil;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Excel 파일에 관련된 기능을 담당하는 클래스 기능의 확장이 필요할 경우 이클래스에 기능을 추가하여 사용함
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 조경태
 * @version 1.0
 * @date 2013/04/01
 */
public class ExcelManager {
    PJCellFormat cellFormat = new PJCellFormat();

    public void setPJCellFormat(PJCellFormat cellFormat) {
        this.cellFormat = cellFormat;
    }

    /**
     * 검색된 ArrayList 데이터를 엑셀파일로 저장한다.
     *
     * @param response
     *                HttpServletResponse
     * @param header
     *                ArrayList에서 뽑아낼 key값들의 모음(Header)
     * @param headerToPrint
     *                엑셀파일에 표시될 헤더이름
     * @param outputData
     *                엑셀파일로 저장하려는 ArrayList
     * @return boolean
     */
    public boolean excelTransformToClient(HttpServletResponse response,
        String[] header, String[] headerToPrint, ArrayList outputData, String[] mergefg, String querydbnm, String filenm, String[] querydbproptypecd) {
        boolean resultFlag = false;

        // JXL 객체들 선언
        WritableWorkbook workbook = null;
        WritableSheet sheet = null;
        try {
            String fileTitle = new String((filenm+"("+DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDD)+")"));
            fileTitle = URLEncoder.encode(fileTitle, "utf-8");


            //response.setHeader("Content-Disposition", "attachment; filename=myfile.xls");
            response.setHeader("Content-Disposition", "attachment; filename="+fileTitle+".xls");

            workbook = Workbook.createWorkbook(response.getOutputStream());

            if (outputData.size() > 65000) {
                int totalSheetCount = outputData.size() / 65000;
                totalSheetCount = (outputData.size() % 65000) == 0 ? totalSheetCount
                    : totalSheetCount + 1;

                for (int sheetIdx = 0; sheetIdx < totalSheetCount; sheetIdx++) {
                    workbook.createSheet(""+querydbnm + (sheetIdx + 1), sheetIdx);
                    sheet = workbook.getSheet(sheetIdx);
                    this.insertDataToSheet(sheet, header, headerToPrint,
                        outputData, sheetIdx, mergefg,querydbnm, querydbproptypecd);
                }
            } else {
                // sheet가 1개일 경우
                workbook.createSheet(""+querydbnm, 0);
                sheet = workbook.getSheet(0);
                insertDataToSheet(sheet, header, headerToPrint, outputData, 0, mergefg, querydbnm, querydbproptypecd);
                workbook.write();
            }

            workbook.write();
            workbook.close();
        } catch (Exception except) {
            except.printStackTrace();
        }

        return resultFlag;
    }

    private void insertDataToSheet(WritableSheet sheet, String[] header,
        String[] headerToPrint, ArrayList outputData, int currentSheet, String[] mergefg, String querydbnm, String[] querydbproptypecd) {
        Label label = null;
        Number number = null;
        DateTime datetime = null;
        Properties tempRow = new Properties(); // 한열의 데이터를 임시로 저장하기 위한 변수
        String tempColumn = "";
        String tagPattern = "[ |\t|s]*<[^>]*>";
        String noTagPattern = "[^<]*";

        int[] maxWidth = new int[header.length]; // 각 셀의 폭의 길이 조절을 위한 변수

        // 셀 폭 최소값을 헤더크기에 초기화
        for (int idx = 0; idx < headerToPrint.length; idx++) {
            maxWidth[idx] = headerToPrint[idx].length() * 2;
        }

        try {

            label = new Label(1, 0, querydbnm, cellFormat.getTitleFormat());
            sheet.addCell(label);
            sheet.mergeCells(1,0, headerToPrint.length ,0);


            // Header 데이터 입력
            for (int idx = 1; idx < headerToPrint.length + 1; idx++) {
                label = new Label(idx, 1, headerToPrint[idx - 1], cellFormat.getHeaderFormat());
                sheet.addCell(label);
            }

            String tmpCol[][] = new String[outputData.size()+2][header.length+1];
            int tmpRow[] = new int[outputData.size()+2];

            // 저장할 데이터 입력
            for (int rowIdx = 2; rowIdx < 65000; rowIdx++) {
                tempRow = new Properties();
                // 데이터 범위를 넘어서면 중단
                if (rowIdx - 2 + (currentSheet * 65000) >= outputData.size()) {
                    break;
                }

                if (currentSheet == 0) {
                    tempRow = (Properties) outputData.get(rowIdx - 2);
                } else {
                    tempRow = (Properties) outputData.get(rowIdx - 2
                        + (currentSheet * 65000));
                }

                // 엑셀 제목



                // 제일 앞 행에 번호 입력
                /*
                label = new Label(0, rowIdx, "" + rowIdx, cellFormat
                    .getDataFormat());
                sheet.addCell(label);
                 */
                for (int colIdx = 0; colIdx < header.length; colIdx++) {
                    // 태그를 포함하고 있다면 태그는 제거
                    tempColumn = tempRow.get(header[colIdx]) == null ? "" : tempRow.get(header[colIdx]).toString();
                    tempColumn = tempColumn.replaceAll("\r\n", "");
                    // &nbsp;는 space로 치환
                    tempColumn = tempColumn.replaceAll("&nbsp;", " ");
                    if (!tempColumn.matches(noTagPattern)) {
                        tempColumn = tempColumn.replaceAll(tagPattern, "");
                    }

                    if(mergefg[colIdx].equals("1")){	// 병합할 컬럼인지 체크
                        tmpCol[rowIdx][colIdx] = tempColumn;
                        if(rowIdx != 1){	// 첫 ROW가 아닐경우 병합 체크.
                            if(!tmpCol[rowIdx-1][colIdx].equals(tmpCol[rowIdx][colIdx])){	// 상위 ROW와 값이 다를 경우 병합.
                                if(colIdx == 0){	// 첫 COL은 그냥 병합.
                                    sheet.mergeCells(colIdx + 1, tmpRow[colIdx], colIdx + 1, rowIdx-1);
                                }else{
                                    if(tmpCol[rowIdx-1][colIdx-1].equals(tmpCol[rowIdx-2][colIdx-1])){	// parent 값이 같을 경우만 병합.
                                        sheet.mergeCells(colIdx + 1, tmpRow[colIdx], colIdx + 1, rowIdx-1);
                                    }
                                }
                                tmpRow[colIdx] = rowIdx;
                            }
                        }else{
                            tmpRow[colIdx] = 1;
                        }
                        if(outputData.size() == rowIdx){	// 마지막 row일 경우 병합 체크
                            if(colIdx == 0){
                                sheet.mergeCells(colIdx + 1, tmpRow[colIdx], colIdx + 1, rowIdx);
                            }else{
                                if(tmpCol[rowIdx][colIdx-1].equals(tmpCol[rowIdx-1][colIdx-1])){
                                    sheet.mergeCells(colIdx + 1, tmpRow[colIdx], colIdx + 1, rowIdx);
                                }else{
                                    sheet.mergeCells(colIdx + 1, rowIdx-1, colIdx + 1, rowIdx-1);
                                }
                            }
                        }
                    }

                    // 셀 넓이 설정
                    if (!tempColumn.matches("[^ㄱ-ㅎ]*")) {
                        // 한글패턴인 경우
                        if (maxWidth[colIdx] < tempColumn.length()) {
                            maxWidth[colIdx] = tempColumn.length();
                        }
                    } else {
                         if (maxWidth[colIdx] < tempColumn.length() * 2) {
                            maxWidth[colIdx] = tempColumn.length() * 2;
                        }
                    }

                    if (querydbproptypecd[colIdx].equals("30")) { // 날짜 타입인 경우

                        if (tempColumn.length() != 0) {
                            if (tempColumn.length() == 8) { // 8자리인 경우 년월일
                                DateFormat f = new SimpleDateFormat("yyyyMMdd");
                                Date date = f.parse(tempColumn);
                                datetime = new DateTime(colIdx + 1, rowIdx, date, cellFormat.getDateFormatYMD());
                                sheet.addCell(datetime);
                            } else if (tempColumn.length() == 6) { // 6자리는 시분초
                                DateFormat f = new SimpleDateFormat("HHmmss");
                                Date date = f.parse(tempColumn);
                                datetime = new DateTime(colIdx + 1, rowIdx, date, cellFormat.getDateFormatHMS());
                                sheet.addCell(datetime);
                            } else if (tempColumn.length() == 14){ // 그외는 년월일시분초
                                DateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
                                Date date = f.parse(tempColumn);
                                datetime = new DateTime(colIdx + 1, rowIdx, date, cellFormat.getDateFormatYMDHMS());
                                sheet.addCell(datetime);
                            } else {
                                label = new Label(colIdx + 1, rowIdx, tempColumn, cellFormat.getDataFormat());
                                sheet.addCell(label);
                            }
                        } else { // 데이터 없을 경우, 데이터타입으로 저장..
                            label = new Label(colIdx + 1, rowIdx, tempColumn, cellFormat.getDataFormat());
                            sheet.addCell(label);
                        }

                    } else if (querydbproptypecd[colIdx].equals("20")) { // 숫자 타입인 경우 (가운데정렬)
                        number = new Number(colIdx + 1, rowIdx, new Double(tempColumn), cellFormat.getNumberFormat());
                        sheet.addCell(number);

                    } else { // 그 외 텍스트 (왼쪽정렬)
                        //	if (!tempColumn.matches("^[0-9]*$") && !tempColumn.matches("^[0-9][0-9]*([.][0-9]+)?$") || "".equals(tempColumn) || tempColumn.startsWith("0")) {
                        //    label = new Label(colIdx + 1, rowIdx, tempColumn, cellFormat.getDataFormat());
                        //    sheet.addCㅇㅇㅇㅇell(label);
                        label = new Label(colIdx + 1, rowIdx, tempColumn, cellFormat.getDataFormat());
                        sheet.addCell(label);
                    }

                }
            }

            // sheet의 각 컬럼 폭을 설정해준다.
            for (int idx = 0; idx < maxWidth.length; idx++) {
                if (maxWidth[idx] > 60) {
                    sheet.setColumnView(idx + 1, 60);
                } else {
                    sheet.setColumnView(idx + 1, maxWidth[idx]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
