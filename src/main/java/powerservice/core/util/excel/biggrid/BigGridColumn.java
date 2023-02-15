package powerservice.core.util.excel.biggrid;

public class BigGridColumn {
    public final static String STYLE_GENERAL = "general"; // 1. 일반
    public final static String STYLE_HEADER = "header"; // 2. 타이틀
    public final static String STYLE_NUMBER = "number"; // 3. 숫자
    public final static String STYLE_PERCENT = "percent"; // 4. 비율
    public final static String STYLE_DTTM = "dttm"; // 5. 일시
    public final static String STYLE_DATE = "date"; // 6. 일자
    public final static String STYLE_TIME = "time";// 7. 시간
    public final static String STYLE_POST = "post"; // 8. 우편번호

    private String gsColumnName;
    private String gsColumnStyle;
    private String gsColumnMergeFg;

    public BigGridColumn(String psColumnName, String psColumnStyle) {
        this.gsColumnName = psColumnName;
        this.gsColumnStyle = psColumnStyle;
        this.gsColumnMergeFg = "N";
    }

    public BigGridColumn(String psColumnName, String psColumnStyle, String psColumnMergeFg) {
        this.gsColumnName = psColumnName;
        this.gsColumnStyle = psColumnStyle;
        this.gsColumnMergeFg = psColumnMergeFg;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return gsColumnName;
    }
    /**
     * @return the style
     */
    public String getColumnStyle() {
        return gsColumnStyle;
    }
    /**
     * @return the mergefg
     */
    public String getColumnMergeFg() {
        return gsColumnMergeFg;
    }
    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String psColumnName) {
        this.gsColumnName = psColumnName;
    }
    /**
     * @param style the style to set
     */
    public void setColumnStyle(String psColumnStyle) {
        this.gsColumnStyle = psColumnStyle;
    }
    /**
     * @param mergefg the mergefg to set
     */
    public void setColumnMergeFg(String psColumnMergeFg) {
        this.gsColumnMergeFg = psColumnMergeFg;
    }
}
