package powerservice.common.util.excel;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

/**
 * Excel 파일의 출력 포멧에 관한 정보를 담고있는 POJO 좀더 세밀한 포멧관리가 필요시 확장 및 수정
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 조경태
 * @version 1.0
 * @date 2013/04/01
 */
public class PJCellFormat {

    private WritableFont titleFont;

    private WritableCellFormat titleFormat;

    private WritableFont headerFont;

    private WritableCellFormat headerFormat;

    private WritableFont dataFont;

    private WritableCellFormat dataFormat;

    private WritableCellFormat numberFormat;

    private WritableCellFormat dateFormatYMD;

    private WritableCellFormat dateFormatHMS;

    private WritableCellFormat dateFormatYMDHMS;

    public PJCellFormat() {
    // 인스턴스 생성시 디폴트 포멧 지정
    try {
        titleFont = new WritableFont(WritableFont.TAHOMA, 16,
                WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                Colour.BLACK);
        titleFormat = new WritableCellFormat(titleFont);
        titleFormat.setBackground(Colour.WHITE);
        titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        titleFormat.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
        titleFormat.setAlignment(Alignment.CENTRE);

        headerFont = new WritableFont(WritableFont.TAHOMA, 10,
            WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
            Colour.WHITE);
        headerFormat = new WritableCellFormat(headerFont);
        headerFormat.setBackground(Colour.OCEAN_BLUE);
        headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        headerFormat.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM);
        headerFormat.setAlignment(Alignment.CENTRE);

        dataFont = new WritableFont(WritableFont.TAHOMA, 10);
        dataFormat = new WritableCellFormat(dataFont);
        dataFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);   // 셀 수직 가운데 정렬
        // dataFormat.setWrap(true);

        numberFormat = new WritableCellFormat(NumberFormats.DEFAULT );
        numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        numberFormat.setAlignment(Alignment.CENTRE);

        DateFormat dateformatYMD = new DateFormat("yyyy-MM-dd");
        dateFormatYMD = new WritableCellFormat(dateformatYMD);
        dateFormatYMD.setBorder(Border.ALL, BorderLineStyle.THIN);
        dateFormatYMD.setAlignment(Alignment.CENTRE);

        DateFormat dateformatHMS = new DateFormat("hh:mm:ss");
        dateFormatHMS = new WritableCellFormat(dateformatHMS);
        dateFormatHMS.setBorder(Border.ALL, BorderLineStyle.THIN);
        dateFormatHMS.setAlignment(Alignment.CENTRE);

        DateFormat dateformatYMDHMS = new DateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormatYMDHMS = new WritableCellFormat(dateformatYMDHMS);
        dateFormatYMDHMS.setBorder(Border.ALL, BorderLineStyle.THIN);
        dateFormatYMDHMS.setAlignment(Alignment.CENTRE);

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

	public WritableFont getTitleFont() {
    return titleFont;
    }

    public void setTitleFont(WritableFont titleFont) {
    this.titleFont = titleFont;
    titleFormat.setFont(this.titleFont);
    }

    public WritableFont getHeaderFont() {
    return headerFont;
    }

    public void setHeaderFont(WritableFont headerFont) {
    this.headerFont = headerFont;
    headerFormat.setFont(this.headerFont);
    }

    public WritableFont getDataFont() {
    return dataFont;
    }

    public void setDataFont(WritableFont dataFont) {
    this.dataFont = dataFont;
    dataFormat.setFont(this.dataFont);
    }

    public WritableCellFormat getTitleFormat() {
        return titleFormat;
    }

    public void setTitleFormat(WritableCellFormat titleFormat) {
        this.titleFormat = titleFormat;
    }

    public WritableCellFormat getHeaderFormat() {
    return headerFormat;
    }

    public void setHeaderFormat(WritableCellFormat headerFormat) {
    this.headerFormat = headerFormat;
    }

    public WritableCellFormat getDataFormat() {
    return dataFormat;
    }

    public void setDataFormat(WritableCellFormat dataFormat) {
    this.dataFormat = dataFormat;
    }

    public WritableCellFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(WritableCellFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public WritableCellFormat getDateFormatYMDHMS() {
		return dateFormatYMDHMS;
	}

	public void setDateFormatYMDHMS(WritableCellFormat dateFormatYMDHMS) {
		this.dateFormatYMDHMS = dateFormatYMDHMS;
	}

	public WritableCellFormat getDateFormatYMD() {
		return dateFormatYMD;
	}

	public void setDateFormatYMD(WritableCellFormat dateFormatYMD) {
		this.dateFormatYMD = dateFormatYMD;
	}

	public WritableCellFormat getDateFormatHMS() {
		return dateFormatHMS;
	}

	public void setDateFormatHMS(WritableCellFormat dateFormatHMS) {
		this.dateFormatHMS = dateFormatHMS;
	}
}
