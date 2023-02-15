/*
 * Created on 2005. 8. 30.
 *
 */
package powerservice.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DateUtil {
    public static final String SZ_NULL_STRING = "";

    public static final SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");

    public static final SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat(
        "yyyyMM");

    public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat(
        "yyyyMMdd");

    public static final SimpleDateFormat SDF_YYYYMM_DASH = new SimpleDateFormat(
        "yyyy-MM");

    public static final SimpleDateFormat SDF_YYYYMM_SLASH = new SimpleDateFormat(
        "yyyy/MM");

    public static final SimpleDateFormat SDF_YYYYMM_DOT = new SimpleDateFormat(
        "yyyy.MM");

    public static final SimpleDateFormat SDF_YYYYMMDD_DASH = new SimpleDateFormat(
        "yyyy-MM-dd");

    public static final SimpleDateFormat SDF_YYYYMMDD_SLASH = new SimpleDateFormat(
        "yyyy/MM/dd");

    public static final SimpleDateFormat SDF_YYYYMMDD_DOT = new SimpleDateFormat(
        "yyyy.MM.dd");

    public static final SimpleDateFormat SDF_YYYYMMDD_HAN = new SimpleDateFormat(
        "yyyy년 MM월 dd일");

    public static final SimpleDateFormat SDF_YYYYMMDDEEE_HAN = new SimpleDateFormat(
        "yyyy년 MM월 dd일 (EEE)");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMM = new SimpleDateFormat(
        "yyyyMMddHHmm");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat(
        "yyyyMMddHHmmss");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMM_DASH_DOT = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMM_DASH_DOT = new SimpleDateFormat(
        "yyyy-MM-dd hh:mm a");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSS_NO_MARK = new SimpleDateFormat(
        "yyyyMMddHHmmss");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSSM_DASH_DOT = new SimpleDateFormat(
        "yyyy-MM-dd hh:mm:ss a");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSSM_DASH = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SDF_YYYYMMDDHHMMSSMSSS_DASH = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss SSS");

    public static final SimpleDateFormat SDF_HHMM = new SimpleDateFormat(
        "HHmm");

    public static final SimpleDateFormat SDF_HHMMSS = new SimpleDateFormat(
        "HHmmss");

    public static final SimpleDateFormat SDF_HHMM_DASH = new SimpleDateFormat(
        "HH:mm");

    public static final SimpleDateFormat SDF_HHMMSS_DASH = new SimpleDateFormat(
        "HH:mm:ss");

    public static final SimpleDateFormat SDF_HHMMM_DASH = new SimpleDateFormat(
        "hh:mm a");

    public static final SimpleDateFormat SDF_HHMMSSM_DASH = new SimpleDateFormat(
        "hh:mm:ss a");

    /**
     * srcSDF Type으로 되어있는 날짜 String을 dstSDF Type의 날짜 String으로 변환한다.
     *
     * @return String이 타당하면 변환된 Date를 아니면 null을 리턴한다.
     */
    public static String convertString(String szDate, SimpleDateFormat srcSDF,
        SimpleDateFormat dstSDF) {
    if (szDate == null || szDate.length() <= 0) {
        return SZ_NULL_STRING;
    }

    try {
        Date dt = srcSDF.parse(szDate);
        return dstSDF.format(dt);
    } catch (Exception e) {
        return SZ_NULL_STRING;
    }
    }

    /**
     * 주어진 Date를 sdf Type으로 되어있는 날짜 String으로 변환한다.
     *
     * @return Date가 타당하면 변환된 Date를 아니면 null을 리턴한다.
     */
    public static String convertDate(Date dt, SimpleDateFormat sdf) {
    if (dt == null) {
        return SZ_NULL_STRING;
    }

    try {
        return sdf.format(dt);
    } catch (Exception e) {
        return SZ_NULL_STRING;
    }
    }

    /**
     * 주어진 Timestamp를 sdf Type으로 되어있는 날짜 String으로 변환한다.
     *
     * @return Date가 타당하면 변환된 Date를 아니면 null을 리턴한다.
     */
    public static String convertDate(Timestamp ts, SimpleDateFormat sdf) {
    if (ts == null) {
        return SZ_NULL_STRING;
    }

    return convertDate(new Date(ts.getTime()), sdf);
    }

    /**
     * sdf형식으로 되어있는 날짜String을 Date Type으로 변경한다.
     */
    public static Date convertString(String szString, SimpleDateFormat sdf) {
    if (szString == null || szString.length() <= 0) {
        return null;
    }

    try {
        return sdf.parse(szString);
    } catch (Exception e) {
        // DebugUtil.printMSG_1( e);
        return null;
    }
    }

    // added by sjkim 2000.0620
    /**
     * 과거의 시간에 대한 시점을 얻는 Method.
     *
     * @parameter nDay : 현재로부터 몇일전
     * @parameter sdf : Date Format
     * @return : 지난 nDay전의 Date String
     */
    public static String lastDayToString(int nDay, SimpleDateFormat sdf) {
    Calendar rightNow = Calendar.getInstance();

    rightNow.add(Calendar.DATE, -nDay);

    try {
        return sdf.format(rightNow.getTime());
    } catch (Exception e) {
        return SZ_NULL_STRING;
    }
    }

    // added by sjkim 2000.0620
    /**
     * 과거의 시간에 대한 시점을 얻는 Method.
     *
     * @parameter nMonth : 현재로 부터 몇달전
     * @parameter sdf : Date Format
     * @return : 지난 nMonth전의 Date String
     */
    public static String lastMonthToString(int nMonth, SimpleDateFormat sdf) {
    Calendar rightNow = Calendar.getInstance();

    rightNow.add(Calendar.MONTH, -nMonth);

    try {
        return sdf.format(rightNow.getTime());
    } catch (Exception e) {
        return SZ_NULL_STRING;
    }
    }

    // added by khchoi 2000.0620

    /**
     * 과거의 시간에 대한 시점을 얻는 Method.
     *
     * @parameter sdf : Date Format
     * @return : 현재 Time의 Date String
     */

    public static String currentTimeToString(SimpleDateFormat sdf) {
    Calendar rightNow = Calendar.getInstance();

    try {
        return sdf.format(rightNow.getTime());
    } catch (Exception e) {
        return SZ_NULL_STRING;
    }
    }

    /**
     * @param :
     *                Source Tmestamp, Target Timestamp
     * @return : Source가 Target보다 선이면 '1', 후이면 '-1', 같으면 '0'
     */
    public static int compareTimestamp(java.sql.Timestamp tsSource,
        java.sql.Timestamp tsTarget) {
    if (tsSource == null) {
        return (tsTarget == null) ? 0 : -1;
    }

    if (tsTarget == null) {
        return 1;
    }

    return tsSource.equals(tsTarget) ? 0 : tsSource.before(tsTarget) ? 1
        : -1;
    }

    public static int calaWeekday(int nYear, int nMonth, int nDay) {
    if (nMonth >= 3) {
        nMonth -= 2;
    } else {
        nMonth += 10;
    }

    if ((nMonth == 11) || (nMonth == 12)) {
        nYear--;
    }

    int nCentNum = (int) (nYear / 100);
    int nDYearNum = nYear % 100;

    int g = (int) (2.6 * nMonth - 0.2);
    g += (int) (nDay + nDYearNum);
    g += (int) (nDYearNum / 4);
    g += (int) (nCentNum / 4);
    g -= (int) (2 * nCentNum);
    g %= 7;
    if (g < 0) {
        g += 7;
    }

    return g;
    }

    public static int getLastDay(int nYear, int nMonth) {
    int[] nMonthLen = { 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    if (nMonth == 2) {
        if (((nYear % 4 == 0) && (nYear % 100 != 0)) || (nYear % 400 == 0)) {
        return 29;
        } else {
        return 28;
        }
    } else {
        if (nMonth < 1 || nMonth > 12) {
        return 31;
        } else {
        return nMonthLen[nMonth - 1];
        }
    }
    }

    /**
     * 두 날자 사이의 일자를 리턴 한다. 공휴일 체크 하는 부분에 맞게 'YYYYMMDD'형태의 문자열로된 날자를 리턴한다. 주의 -
     * 시간을 제외한 순수 날자만을 체크함
     *
     * 두 날자의 차이가 음수 일경우 널를 반환 하도록 함
     *
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static String[] getDateYYYYMMDD(Date dateOne, Date dateTwo) {
    String szDateOne = DateUtil.convertDate(dateOne, SDF_YYYYMMDD);
    String szDateTwo = DateUtil.convertDate(dateTwo, SDF_YYYYMMDD);

    Date convertDateOne = DateUtil.convertString(szDateOne,
        DateUtil.SDF_YYYYMMDD);
    Date convertDateTwo = DateUtil.convertString(szDateTwo,
        DateUtil.SDF_YYYYMMDD);

    long lDayCount = (convertDateTwo.getTime() - convertDateOne.getTime())
        / (1000 * 60 * 60 * 24);
    // System.out.println("Day of Week=====================" + lDayCount);
    if (lDayCount < 1) {
        return null;
    }

    // 데이터형 크기가 틀려서 좀... 날자차가 너무 크면 그것도 좀..
    int nDay = (int) lDayCount;

    // 1월10일 , 1월 14일 차가 4일, 시작날자도 포함시켜서 리턴해야 하므로
    String[] szArrDate = new String[nDay + 1];
    szArrDate[0] = szDateOne;
    for (int i = 1; i < nDay + 1; i++) {
        long lTime = convertDateOne.getTime() + (1000 * 60 * 60 * 24);
        convertDateOne.setTime(lTime);
        szArrDate[i] = DateUtil.convertDate(convertDateOne,
            DateUtil.SDF_YYYYMMDD);
    }

    return szArrDate;
    }

    /**
     * Date를 입력받아 요일을 구함. 일요일~토요일이 1~7까지 맵핑됨
     *
     * @param date
     * @return 요일에 해당하는 숫자상수( Calendar.DAY_OF_WEEK)
     */
    public static int getDayOfWeek(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 시간(Hour)을 구함 , 0시~23시로 표현 됨
     *
     * @param date
     * @return
     */
    public static int getHourOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 분(Minute)을 구함 , 0~59분으로 표현 됨
     *
     * @param date
     * @return
     */
    public static int getMinuteOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.MINUTE);
    }

    /**
     * 초(Second)를 구함 , 0 ~59
     *
     * @param date
     * @return
     */
    public static int getSecondOfDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.SECOND);
    }

    /**
     * 해당월의 마직막일자를 반환 1월은 31, 2월 28일 또는 29일
     *
     * @param date
     * @return
     */
    public static int getLastDay(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.getActualMaximum(Calendar.SECOND);
    }

    public static Date getNextDay(Date date, int nDay) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_WEEK, nDay);

    return calendar.getTime();
    }

    public static Date addMinuteToDate(Date date, int nMinute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, nMinute);

    return calendar.getTime();
    }

    public static class Test {
    public static void main(String[] args) {
        // 일 ~ 토요일 , 1~7과 맵핑됨
        System.out.println("Day of Week=====================");
        //Date date1 = DateUtil.convertString("2006-01-10 14:21", DateUtil.SDF_YYYYMMDDHHMM_DASH_DOT);
        //Date date2 = new Date(System.currentTimeMillis());
        Date date1 = DateUtil.convertString("201109160200", DateUtil.SDF_YYYYMMDDHHMM);
        Date date2 = new Date(System.currentTimeMillis());

        System.out.println(DateUtil.addMinuteToDate(date1, -180));
        System.out.println(DateUtil.getHourOfDay(date1));
        System.out.println(date2);

        //System.out.println(DateUtil.addMinuteToDate(date1, 180));
        //System.out.println(DateUtil.getHourOfDay(date1));
        // String[] szArrDate = DateUtil.getDateYYYYMMDD( date1, date2);

        // for ( int i=0; i < szArrDate.length; i++)
        // {
        // System.out.println("" + szArrDate[i]);
        // }
        // Date date1 = DateUtil.convertString("2006-02-08 00:23:12",
        // DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
        // Date date2 = DateUtil.convertString("2006-01-09 13:23:12",
        // DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);
        // Date date3 = DateUtil.convertString("2006-01-14 09:23:12",
        // DateUtil.SDF_YYYYMMDDHHMMSSM_DASH);

        // System.out.println("Result:" + DateUtil.calaWeekday(2005, 9,
        // 25)
        // );

        // Date szDate = new Date( System.currentTimeMillis());
        // String szTDate = DateUtil.convertDate( szDate,
        // DateUtil.SDF_YYYYMMDD);
        // System.out.println("Result:" + szTDate.substring(0, 4) );
        // System.out.println("Result:" + szTDate.substring(4, 6) );
        // System.out.println("Result:" + szTDate.substring(6, 8) );
        // System.out.println("Result:" + Integer.parseInt( "01") );
    }
    }

    public static String getCurrDthms() {
    	Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	return sdf.format(cal.getTime());
	}
}