package powerservice.common.util.excel;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 조회 결과를 엑셀 파일로 생성한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 조경태
 * @version 1.0
 * @date 2013/04/01
 */
public class ExcelResultHandler implements ResultHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcelResultHandler.class);

    private int gnFileRowCnt        = 10000;		// 파일 저장 건수 단위
    private int gnTotalCnt          = 0;			// 전체 건수
    private int gnCurrentRow        = 0;			// 현재 처리 행
    private long gnStartTime        = 0;			// 처리 시작 시간
    private String gsTempFileName   = "";			// 템플릿 엑셀 파일명
    private String gsTargetFileName = "";			// 생성 엑셀 파일명

    private XLSTransformer goTransformer = new XLSTransformer(); // JXLS 워크북 생성 객체
    private Map<String, Object> gmJxls = new HashMap<String, Object>(); // JXLS 전체 데이터
    private List<Map<String, Object>> gmJxlsList = new ArrayList<Map<String, Object>>(); // JXLS 리스트 데이터

    /**
     * 생성자
     *
     * @param param Map<String, String>
     */
    public ExcelResultHandler(Map<String, Object> param) {
        super();

        this.gnTotalCnt       = (Integer) param.get("totalCnt");
        this.gsTempFileName   = (String) param.get("tempFileName");
        this.gsTargetFileName = (String) param.get("targetFileName");

        gmJxls.put("totalCnt",   gnTotalCnt);   // 파일 저장 건수 단위
    }

    /**
     * 조회 결과를 파일 저장 건수 단위의 리스트 객체로 생성한다.
     *
     * @param context ResultContext
     */
    @SuppressWarnings("unchecked")
    @Override
    public void handleResult(ResultContext context) {
        gmJxlsList.add((Map<String, Object>)context.getResultObject());

        gnCurrentRow = context.getResultCount();
        if (gnCurrentRow % gnFileRowCnt == 0 || gnCurrentRow >= gnTotalCnt || gnTotalCnt == 0) {
            gmJxls.put("list", gmJxlsList);

            createExcelFile();

            gmJxls.remove("list");
            gmJxlsList.clear();
        }
    }

    /**
     * 데이터 객체를 엑셀 파일로 생성한다.
     */
    protected void createExcelFile() {
        BufferedOutputStream oOutputStream = null;

        try {
            int nStartRow = gnCurrentRow - gnFileRowCnt + 1;
            if (gnCurrentRow % gnFileRowCnt > 0) {
                nStartRow = gnCurrentRow - (gnCurrentRow % gnFileRowCnt) + 1;
            }

            gmJxls.put("startIndex", nStartRow);   // 파일 시작 행
            gmJxls.put("endIndex",   gnCurrentRow); // 파일 종료 행

            gnStartTime = System.currentTimeMillis();

            // 엑셀 행 높이 자동 설정 처리자 추가
            goTransformer.registerRowProcessor(new AutoHeightRowProcessor());

            Workbook oWorkbook = goTransformer.transformXLS(new FileInputStream(gsTempFileName), gmJxls);

            String sTempTargetFileName = gsTargetFileName + ".xlsx";
            if (gnTotalCnt > gnFileRowCnt) {
                sTempTargetFileName = gsTargetFileName + "-"  + nStartRow + ".xlsx";
            }
            oOutputStream = new BufferedOutputStream(new FileOutputStream(sTempTargetFileName));

            oWorkbook.write(oOutputStream);
            oOutputStream.flush();
            oOutputStream.close();

            LOGGER.info("[createExcelFile] " + nStartRow + " ~ " + gnCurrentRow + " (" + (System.currentTimeMillis() - gnStartTime) + "ms)");
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        } finally {
            if (oOutputStream != null) {
                try {
                    oOutputStream.close();
                } catch (Exception exception) {
                    LOGGER.error(exception.getMessage());
                }
            }
        }
    }
}