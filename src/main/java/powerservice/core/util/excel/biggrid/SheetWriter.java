package powerservice.core.util.excel.biggrid;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

/**
 * Writes spreadsheet data in a Writer.
 * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
 */
public class SheetWriter {
    private final Writer goWriter;
    private int gnCurrentRow;

    public SheetWriter(Writer poWriter){
        goWriter = poWriter;
    }

    public void beginSheet() throws IOException {
        goWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        goWriter.write("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
        goWriter.write("<sheetData>\n");
    }

    public void endSheet() throws IOException {
        goWriter.write("</sheetData>");
        goWriter.write("</worksheet>");
    }

    /**
     * Insert a new row
     *
     * @param rownum 0-based row number
     */
    public void insertRow(int pnCurrentRow) throws IOException {
        goWriter.write("<row r=\"" + (pnCurrentRow + 1) + "\">\n");
        gnCurrentRow = pnCurrentRow;
    }

    /**
     * Insert row end marker
     */
    public void endRow() throws IOException {
        goWriter.write("</row>\n");
    }

    public void createCell(int pnCurrentCol, String psCellVl, int pnStyleIdx) throws IOException {
        String oCellReference = new CellReference(gnCurrentRow, pnCurrentCol).formatAsString();
        goWriter.write("<c r=\"" + oCellReference + "\" t=\"inlineStr\"");
        if (pnStyleIdx != -1) {
            goWriter.write(" s=\"" + pnStyleIdx + "\"");
        }
        goWriter.write(">");
        goWriter.write("<is><t><![CDATA[" + psCellVl + "]]></t></is>");
        goWriter.write("</c>");
    }

    public void createCell(int pnCurrentCol, String psCellVl) throws IOException {
        createCell(pnCurrentCol, psCellVl, -1);
    }

    public void createCell(int pnCurrentCol, double pfCellVl, int pnStyleIdx) throws IOException {
        String oCellReference = new CellReference(gnCurrentRow, pnCurrentCol).formatAsString();
        goWriter.write("<c r=\"" + oCellReference + "\" t=\"n\"");
        if(pnStyleIdx != -1) {
            goWriter.write(" s=\"" + pnStyleIdx + "\"");
        }
        goWriter.write(">");
        goWriter.write("<v>" + pfCellVl + "</v>");
        goWriter.write("</c>");
    }

    public void createCell(int pnCurrentCol, double pfCellVl) throws IOException {
        createCell(pnCurrentCol, pfCellVl, -1);
    }

    public void createCell(int pnCurrentCol, Calendar pdCellVl, int pnStyleIdx) throws IOException {
        createCell(pnCurrentCol, DateUtil.getExcelDate(pdCellVl, false), pnStyleIdx);
    }
}