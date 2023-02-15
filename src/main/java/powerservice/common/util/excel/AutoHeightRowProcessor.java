package powerservice.common.util.excel;

import java.util.Map;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;

/**
 * 엑셀 행 높이 자동 설정
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author (주)인우기술
 * @version 1.0
 * @date 2015/12/14
 */

public class AutoHeightRowProcessor implements RowProcessor {

    public AutoHeightRowProcessor() {
        super();
    }

    public void processRow(Row poRow, @SuppressWarnings("rawtypes") Map pmArg) {
        poRow.getPoiRow().setHeight((short) -1);
    }

}
