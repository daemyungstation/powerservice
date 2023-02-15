package powerservice.core.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;


public class ExcelImporter {
    private ApplicationContext _applicationContext = null;
    private String _sqlSessionFactoryId = null;
    private int _skipRow = 0;
    private int _insertRowCount = 0;
    private List<String> _colList = null;
    private String _mapId = null;
    private ExcelImportRowHandler _excelRowHandler = null;
    private List<Map<String, Object>> _resultList = null;

    private String _errMsg = "";
    /*
     * 0 : DB에 업로드값저장
     * 1 : 전달된 List에 업로드값저장
     */
    private int _saveOption = 0;


    public ExcelImporter( List<String> colList, ApplicationContext applicationContext, String sqlSessionFactoryId, String insertMapId) {
        this(colList, applicationContext, sqlSessionFactoryId, insertMapId, 1);
    }

    public ExcelImporter( List<String> colList, List<Map<String, Object>> resultList) {
        this(colList, resultList, 1);
    }

    public ExcelImporter(List<String> colList, List<Map<String, Object>> resultList, int skipRow) {
        _colList = colList;
        _resultList = resultList;
        _skipRow = skipRow;
        _saveOption = 1;
    }

    public ExcelImporter(List<String> colList, ApplicationContext applicationContext, String sqlSessionFactoryId, String insertMapId, int skipRow) {
        _colList = colList;
        _applicationContext = applicationContext;
        _sqlSessionFactoryId = sqlSessionFactoryId;
        _mapId = insertMapId;
        _skipRow = skipRow;
        _saveOption = 0;
    }

    public void setHanlder(ExcelImportRowHandler handler) {
        _excelRowHandler = handler;
    }

    public int process(String filePath) throws IOException, InvalidFormatException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        try {
            Workbook workbook = WorkbookFactory.create(fis);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            doInSheet(workbook.getSheetAt(0), evaluator);
        } catch (Exception exception) {
            throw exception;
        } finally {
            fis.close();
        }
        return _insertRowCount;
    }

    public int process(InputStream is) throws IOException, InvalidFormatException {
        try {
            Workbook workbook = WorkbookFactory.create(is);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            doInSheet(workbook.getSheetAt(0), evaluator);
        } catch (Exception exception) {
            throw exception;
        } finally {
            is.close();
        }
        return _insertRowCount;
    }

    private void doInSheet(Sheet sheet, FormulaEvaluator evaluator) {
        DefaultSqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSessionTemplate = null;
        switch(_saveOption){
        case 0:
            sqlSessionFactory = (DefaultSqlSessionFactory) _applicationContext.getBean(_sqlSessionFactoryId);
            sqlSessionTemplate = sqlSessionFactory.openSession(ExecutorType.BATCH);
            break;
        }

        DecimalFormat df = new DecimalFormat();

        for (Row row : sheet) {
            if (row.getRowNum() >= _skipRow) {
                Map<String, Object> rowMap = new HashMap<String, Object>();
                boolean isSave = true;
                int cellType = 0;
                float tempFloat = 0;
                int tempInt = 0;
                String tempString = "";

                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellType = cell.getCellType();
                    int idx = cell.getColumnIndex();

                    if (_colList.size() > idx && !_colList.get(idx).equals("")) {
                        if (cell != null && !cell.toString().equals("")) {
                            switch (cellType) {
                            case Cell.CELL_TYPE_NUMERIC :
                                // cell의 값이 numeric일 경우, 날짜와 숫자 구분.
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
                                    tempString = formatter.format(cell.getDateCellValue());
                                } else {
                                    // 형변환에 대한 함수 변경(수정)
                                    //tempFloat = (float)cell.getNumericCellValue();
                                    //tempInt = (int)cell.getNumericCellValue();
                                    BigDecimal bdCell = new BigDecimal(cell.getNumericCellValue());
                                    tempFloat = bdCell.floatValue();
                                    tempInt = Math.round(bdCell.floatValue());
                                    if (tempFloat == tempInt) {
                                        tempString = tempInt + "";
                                    } else {
                                        tempString = tempFloat + "";
                                    }
                                }
                                rowMap.put(_colList.get(idx), tempString);
                                break;

                            case Cell.CELL_TYPE_STRING :
                                rowMap.put(_colList.get(idx), cell.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_BLANK :
                                rowMap.put(_colList.get(idx), "");
                                break;
                            case Cell.CELL_TYPE_BOOLEAN :
                                rowMap.put(_colList.get(idx), cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_ERROR :
                                rowMap.put(_colList.get(idx), cell.getErrorCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA :
                                if (evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_NUMERIC) {
                                    double fddata = cell.getNumericCellValue();
                                    tempString = df.format(fddata);
                                } else if (evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_STRING) {
                                    tempString = cell.getStringCellValue();
                                } else if (evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_BOOLEAN) {
                                    boolean fbdata = cell.getBooleanCellValue();
                                    tempString = String.valueOf(fbdata);
                                }
                                rowMap.put(_colList.get(idx), tempString);
                                break;
                            default :
                                rowMap.put(_colList.get(idx), "");
                                break;
                            }
                        }
                    }

                }
                if (_excelRowHandler != null) {
                    isSave = _excelRowHandler.handleRow(rowMap, row.getRowNum());
                }
                if (isSave) {
                    switch(_saveOption){
                    case 0:
                        sqlSessionTemplate.insert(_mapId, rowMap);
                        break;
                    case 1:
                        _resultList.add(rowMap);
                        break;
                    }

                    _insertRowCount++;
                } else {
                    String errMsg = StringUtils.defaultString((String) rowMap.get("errMsg"));
                    _errMsg = _errMsg + (_errMsg != "" && errMsg != ""? "\\r\\n" : "") + errMsg;
                }
            }
        }

        switch(_saveOption){
        case 0:
            List<BatchResult> results = sqlSessionTemplate.flushStatements();
            results.clear();

            sqlSessionTemplate.commit();
            sqlSessionTemplate.close();
            break;
        }
    }

    public String getErrMsg() {
        return _errMsg;
    }
}