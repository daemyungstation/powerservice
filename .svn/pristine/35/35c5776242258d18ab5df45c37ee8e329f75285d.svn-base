package powerservice.core.util.excel.biggrid;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BigGridRowHandler implements ResultHandler {

    private int gnCurrentRow = 0;

    private XSSFWorkbook goWorkbook;
    private SheetWriter goSheetWriter;

    private Map<String, BigGridColumn> gmColumnList;
    private Map<String, XSSFCellStyle> gmStyleList;

    /**
     * 생성자
     */
    public BigGridRowHandler(XSSFWorkbook poWorkbook, Map<String, XSSFCellStyle> pmStyleList, LinkedHashMap<String, BigGridColumn> pmColumnList, SheetWriter poSheetWriter) {
        this.goWorkbook = poWorkbook;
        this.gmStyleList = pmStyleList;

        if (pmColumnList != null) {
            this.gmColumnList = pmColumnList;
        } else {
            this.gmColumnList = new LinkedHashMap<String, BigGridColumn>();
        }

        this.goSheetWriter = poSheetWriter;
    }

    public XSSFWorkbook getWorkbook() {
        return this.goWorkbook;
    }

    protected Map<String, BigGridColumn> getColumnList() {
        return this.gmColumnList;
    }

    protected void setColumnList(LinkedHashMap<String, BigGridColumn> pmColumnList) {
        this.gmColumnList = pmColumnList;
    }

    /**
     * 조회 데이터 행 처리
     */
    @SuppressWarnings("unchecked")
    @Override
    public void handleResult(ResultContext poResultContext) {
        Map<String, Object> mRowData = (Map<String, Object>) poResultContext.getResultObject();

        // 타이틀 행 작성
        if (this.gnCurrentRow == 0) {
            addHeaderRow();
        }

        addRow(mRowData);
    }

    /**
     * 엑셀 타이틀 작성
     */
    protected void setExcelHeader(String psExcelTitle) {
        try {
            this.goSheetWriter.insertRow(this.gnCurrentRow);
            this.goSheetWriter.createCell(2, psExcelTitle, gmStyleList.get(BigGridColumn.STYLE_HEADER).getIndex());
            this.goSheetWriter.endRow();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        this.gnCurrentRow = 2;

        // 타이틀 행 생성
        addHeaderRow();
    }

    /**
     * 엑셀 타이틀 행 작성
     */
    @SuppressWarnings("rawtypes")
    private void addHeaderRow() {
        Iterator oColumnIterator = gmColumnList.keySet().iterator();
        int nCurrentCol = 0;

        try {
            this.goSheetWriter.insertRow(this.gnCurrentRow);

            while (oColumnIterator.hasNext()) {
                String sColCd = (String) oColumnIterator.next();
                String sColNm = ((BigGridColumn) gmColumnList.get(sColCd)).getColumnName();

                this.goSheetWriter.createCell(nCurrentCol, sColNm, gmStyleList.get(BigGridColumn.STYLE_HEADER).getIndex());

                nCurrentCol++;
            }

            this.goSheetWriter.endRow();
        } catch (Exception exception) {
            throw new RuntimeException (exception);
        }

        this.gnCurrentRow++;
    }

    /**
     * 엑셀 데이터 작성
     */
    @SuppressWarnings("rawtypes")
    private void addRow(Map pmRowData) throws RuntimeException {
        Iterator oColumnIterator = gmColumnList.keySet().iterator();
        int nCurrentCol = 0;

        try {
            this.goSheetWriter.insertRow(this.gnCurrentRow);

            while (oColumnIterator.hasNext()) {
                String sColCd = (String) oColumnIterator.next();
                Object oColVl = pmRowData.get(sColCd);
                String sColVl = "";
                if (oColVl != null) {
                    // 문자열 변환
                    sColVl = String.valueOf(oColVl);
                }
                Short nStyleIdx = gmStyleList.get(((BigGridColumn) gmColumnList.get(sColCd)).getColumnStyle()).getIndex();
                // System.out.println("### [" + this.gnCurrentRow + "][" + nCurrentCol + "]=[" + sColVl + "] => " + nStyleIdx);

                switch (nStyleIdx) {
                case 3: // 숫자
                case 4: // 비율
                case 5: // 일시
                case 6: // 일자
                case 7: // 시간
                case 8: // 우편번호
                    this.goSheetWriter.createCell(nCurrentCol, "".equals(sColVl) ? 0 : Double.parseDouble(sColVl), nStyleIdx);
                    break;
                default: // 일반
                    this.goSheetWriter.createCell(nCurrentCol, sColVl, nStyleIdx);
                    break;
                }

                nCurrentCol++;
            }

            this.goSheetWriter.endRow();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        this.gnCurrentRow++;
    }
}
