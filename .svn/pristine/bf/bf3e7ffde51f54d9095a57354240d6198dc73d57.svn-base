package powerservice.core.util.excel.biggrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BigGrid {

    public static String makeExcelFile(SqlSession poSqlSession, Map<String, Object> pmXlsParam, LinkedHashMap<String, BigGridColumn> pmColumnList) throws Exception {
        // 사용자 작업 폴더 생성
        String sTargetPath = (String) pmXlsParam.get("targetPath");
        File oTargePathDir = new File(sTargetPath);
        if (oTargePathDir.isDirectory() == false) {
            oTargePathDir.mkdir();
        }

        String sXlsId = (String) pmXlsParam.get("xls_id");
        String sXlsNm = (String) pmXlsParam.get("xls_nm");
        String sListQuryId = (String) pmXlsParam.get("list_qury_id");

        String sTargetFileName = sTargetPath + "/" + makeExcelFileName(sXlsId) + ".xlsx";
        String sTempFileName = sTargetFileName + ".temp.xlsx";

        // Step 1. Create a template file. Setup sheets and workbook-level objects such as
        // cell styles, number formats, etc.

        XSSFWorkbook oWorkbook = new XSSFWorkbook();
        XSSFSheet oSheet = oWorkbook.createSheet(sXlsNm);

        Map<String, XSSFCellStyle> mStyleList = createStyleList(oWorkbook);
        // name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
        String sSheetRef = oSheet.getPackagePart().getPartName().getName();

        // save the template
        FileOutputStream oTempOutputStream = new FileOutputStream(sTempFileName);
        try {
            oWorkbook.write(oTempOutputStream);
        } catch (Exception exception) {
            throw exception;
        } finally {
            oTempOutputStream.close();
        }

        // Step 2. Generate XML file.
        File oXmlFile = File.createTempFile("sheet", ".xml");
        OutputStreamWriter oOsWriter = new OutputStreamWriter(new FileOutputStream(oXmlFile), "UTF-8");
        try {
            SheetWriter oSheetWriter = new SheetWriter(oOsWriter);

            oSheetWriter.beginSheet();

            // 엑셀 데이터 조회
            BigGridRowHandler oRowHandler = new BigGridRowHandler(oWorkbook, mStyleList, pmColumnList, oSheetWriter);
            // oRowHandler.setExcelHeader(sXlsNm);
            poSqlSession.select(sListQuryId, pmXlsParam, oRowHandler);

            /*
            for (int i = 0; i < pmColumnList.size(); i++) {
                oSheet.autoSizeColumn(i);
            }
            */

            oSheetWriter.endSheet();
        } catch (Exception exception) {
            throw exception;
        } finally {
            oOsWriter.close();
        }

        // Step 3. Substitute the template entry with the generated data
        FileOutputStream oFsStream = new FileOutputStream(sTargetFileName);
        File oTemplateFile = new File(sTempFileName);
        try {
            substitute(oTemplateFile, oXmlFile, sSheetRef.substring(1), oFsStream);
        } catch (Exception exception) {
            throw exception;
        } finally {
            oFsStream.close();
        }
        oTemplateFile.delete();
        oXmlFile.delete();

        return sTargetFileName;
    }

    /**
     * Create a library of cell styles.
     */
    private static Map<String, XSSFCellStyle> createStyleList(XSSFWorkbook poWorkbook) {
        Map<String, XSSFCellStyle> mStyleList = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat oDataFormat = poWorkbook.createDataFormat();

        // 1. 일반
        XSSFCellStyle oStyle1 = poWorkbook.createCellStyle();
        XSSFFont oFont0 = poWorkbook.createFont();
        oFont0.setFontHeightInPoints((short)10);
        oStyle1.setFont(oFont0);
        oStyle1.setAlignment(XSSFCellStyle.ALIGN_GENERAL);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle1.setWrapText(false);
        mStyleList.put(BigGridColumn.STYLE_GENERAL, oStyle1);

        // 2. 타이틀
        XSSFCellStyle oStyle2 = poWorkbook.createCellStyle();
        XSSFFont oFont = poWorkbook.createFont();
        oFont.setBold(true);
        oFont.setFontHeightInPoints((short)10);
        oStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        oStyle2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        oStyle2.setFont(oFont);
        oStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        mStyleList.put(BigGridColumn.STYLE_HEADER, oStyle2);

        // 3. 숫자
        XSSFCellStyle oStyle3 = poWorkbook.createCellStyle();
        oStyle3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle3.setDataFormat(oDataFormat.getFormat("#,#"));
        mStyleList.put(BigGridColumn.STYLE_NUMBER, oStyle3);

        // 4. 비율
        XSSFCellStyle oStyle4 = poWorkbook.createCellStyle();
        oStyle4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle4.setDataFormat(oDataFormat.getFormat("0.0%"));
        mStyleList.put(BigGridColumn.STYLE_PERCENT, oStyle4);

        // 5. 일시
        XSSFCellStyle oStyle5 = poWorkbook.createCellStyle();
        oStyle5.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle5.setDataFormat(oDataFormat.getFormat("0000-00-00 00\":\"00\":\"00"));
        mStyleList.put(BigGridColumn.STYLE_DTTM, oStyle5);

        // 6. 일자
        XSSFCellStyle oStyle6 = poWorkbook.createCellStyle();
        oStyle6.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle6.setDataFormat(oDataFormat.getFormat("0000-00-00"));
        mStyleList.put(BigGridColumn.STYLE_DTTM, oStyle6);

        // 7. 시간
        XSSFCellStyle oStyle7 = poWorkbook.createCellStyle();
        oStyle7.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle7.setDataFormat(oDataFormat.getFormat("00\":\"00\":\"00"));
        mStyleList.put(BigGridColumn.STYLE_TIME, oStyle7);

        // 8. 우편번호
        XSSFCellStyle oStyle8 = poWorkbook.createCellStyle();
        oStyle8.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        oStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        oStyle8.setDataFormat(oDataFormat.getFormat("000\"-\"000"));
        mStyleList.put(BigGridColumn.STYLE_POST, oStyle8);

        return mStyleList;
    }

    /**
     *
     * @param zipfile the template file
     * @param tmpfile the XML file with the sheet data
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out the stream to write the result to
     */
    private static void substitute(File poZipFile, File oTempfile, String psEntry, OutputStream poOutputStream) throws IOException {
        ZipFile oZipFile = new ZipFile(poZipFile);

        ZipOutputStream oZipOutputStream = new ZipOutputStream(poOutputStream);

        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) oZipFile.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry oEntry = enumeration.nextElement();
            if (!oEntry.getName().equals(psEntry)){
                oZipOutputStream.putNextEntry(new ZipEntry(oEntry.getName()));
                InputStream oInputStream = oZipFile.getInputStream(oEntry);
                try {
                    copyStream(oInputStream, oZipOutputStream);
                } catch (Exception exception) {
                    throw exception;
                } finally {
                    oInputStream.close();
                }
            }
        }

        InputStream oInputStream = new FileInputStream(oTempfile);
        try {
            oZipOutputStream.putNextEntry(new ZipEntry(psEntry));
            copyStream(oInputStream, oZipOutputStream);
        } catch (Exception exception) {
            throw exception;
        } finally {
            oInputStream.close();

            oZipOutputStream.close();
            oZipFile.close();
        }
    }

    private static void copyStream(InputStream poInputStream, OutputStream poOutputStream) throws IOException {
        byte[] oByteArray = new byte[1024];
        int nReadSize = 0;
        while ((nReadSize = poInputStream.read(oByteArray)) >=0) {
            poOutputStream.write(oByteArray, 0, nReadSize);
        }
    }

    private static String makeExcelFileName(String psXlsId) {
        return psXlsId + "-" + Calendar.getInstance().getTimeInMillis();
    }
}
