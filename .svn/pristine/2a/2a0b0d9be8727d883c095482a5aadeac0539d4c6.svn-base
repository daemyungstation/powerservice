package powerservice.common.util.excel;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * @author Leonid Vysochyn
 */

public class AutoMergColumnRowProcessor implements RowProcessor {

    /**
     * 需要自动合并的列的index
     */
    private final MergeColumnStatus[] mergeColumnStatusArry;

    /**
     * 所有的动态列
     */
    private final Map<Integer, Row> allRows = new LinkedHashMap<Integer, Row>();

    /**
     * @param mergeColumnIndex the columns need to merge
     */
    public AutoMergColumnRowProcessor(short[] mergeColumnIndex) {
        super();

        mergeColumnStatusArry = new MergeColumnStatus[mergeColumnIndex.length];
        for (int i = 0; i < mergeColumnIndex.length; i++) {
            mergeColumnStatusArry[i] = new MergeColumnStatus();
            mergeColumnStatusArry[i].columnNum = mergeColumnIndex[i];
        }
    }

    /**
     * @see net.sf.jxls.processor.RowProcessor#processRow(net.sf.jxls.transformer.Row,
     *      java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public void processRow(Row row, Map pArg1) {
        allRows.put(row.getPoiRow().getRowNum(), row);
    }

    /**
     * 合并单元格
     */
    public void mergeCells() {
        int totalRows = allRows.size();
        int indx = 0;
        for (Row row : allRows.values()) {
            indx++;
            int rowNum = row.getPoiRow().getRowNum();
            boolean isLastRow = (totalRows == indx);
            for (MergeColumnStatus mergeStatus : mergeColumnStatusArry) {
                judgeMerge(row, rowNum, mergeStatus, isLastRow);
            }
        }

        allRows.clear();
    }

    private Object getCellVal(Cell hssfCell) {
        Object val = null;
        switch (hssfCell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC: {
            val = hssfCell.getNumericCellValue();
            break;
        }
        case HSSFCell.CELL_TYPE_BOOLEAN: {
            val = hssfCell.getBooleanCellValue();
            break;
        }
        case HSSFCell.CELL_TYPE_STRING: {
            val = hssfCell.getRichStringCellValue();
            break;
        }
        default: {
            // don't process other types
            return null;
        }
        }
        return val;
    }

    private void judgeMerge(Row row, int rowNum, MergeColumnStatus mergeStatus,
            boolean isLastRow) {
        Object val = null;
        Cell hssfCell = row.getPoiRow().getCell(mergeStatus.columnNum);
        val = getCellVal(hssfCell);
        if (val == null) {
            return;
        }

        if (val.equals(mergeStatus.prevColumnValue)) {
            hssfCell.setCellValue(new HSSFRichTextString(""));
        } else {
            if (mergeStatus.prevColumnValue == null) {
                mergeStatus.startRowNum = rowNum;
            } else {// merge cells
                int endPos = row.getPoiRow().getRowNum() - 1;
                int span = endPos - mergeStatus.startRowNum + 1;
                mergeCells(row, mergeStatus, endPos, span);
                mergeStatus.startRowNum = endPos + 1;
            }

            mergeStatus.prevColumnValue = val;
        }

        // handle last row
        if (isLastRow) {
            int endPos = row.getPoiRow().getRowNum();
            int span = endPos - mergeStatus.startRowNum + 1;
            mergeCells(row, mergeStatus, endPos, span);
        }
    }

    private void mergeCells(Row row, MergeColumnStatus mergeStatus, int endPos,int span) {
        if (span <= 1) {
            return;
        }

        // System.out.println("column " + mergeStatus.columnNum + " start "
        // + mergeStatus.startRowNum + " end " + endPos + " span " + span);

        /*
        row.getSheet().getPoiSheet().addMergedRegion(
                new Region(mergeStatus.startRowNum, mergeStatus.columnNum,
                        endPos, mergeStatus.columnNum));
        */

        row.getSheet().getPoiSheet().addMergedRegion(new CellRangeAddress(mergeStatus.startRowNum, endPos,
                mergeStatus.columnNum, mergeStatus.columnNum));
    }

}


class MergeColumnStatus {

    /**
     * column number
     */
    public short columnNum;

    /**
     * merge start
     */
    public int startRowNum;

    /**
     * prev cell data
     */
    public Object prevColumnValue;

}
