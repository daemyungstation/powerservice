package powerservice.common.util;



/**
 * 문자열을 읽어들이는 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2016. 08. 29.
 */


public class ByteDataReader {
    int iPos;
    int iLen;
    String sOldCharset;
    String sNewCharset;
    boolean bUseTrim = false;
    byte[] data;

    public ByteDataReader(byte[] buff, int len, String oldCharset, String newCharset) {
        data = buff;
        iPos = 0;
        iLen = len;
        bUseTrim = false;
        sOldCharset = oldCharset;
        sNewCharset = newCharset;
    }

    public ByteDataReader(byte[] buff, int len) {
        data = buff;
        iPos = 0;
        iLen = len;
        bUseTrim = false;
        sOldCharset = "";
        sNewCharset = "";
    }
    public String getByEnc(int iCnt, String sCharsetName) {
        if (iPos >= iLen) {
            return "";
        }

        int iEnd = iPos + iCnt;

        if (iEnd >= iLen) {
            iEnd = iLen;
        }

        String sRet = null;
        byte[] buf = new byte[iCnt];

        try {
            sRet = new String(data, iPos, iCnt, sCharsetName);
            iPos = iPos + iCnt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (bUseTrim) {
            return sRet.trim();
        } else {
            return sRet;
        }
    }
    public String get(int iCnt) {
        if (iPos >= iLen) {
            return "";
        }

        int iEnd = iPos + iCnt;

        if (iEnd >= iLen) {
            iEnd = iLen;
        }

        // String sRet = data.substring(iPos, iEnd);
        String sRet = new String(data, iPos, iCnt);
        iPos = iPos + iCnt;

//		try {
//			sRet = new String(sRet.getBytes(sOldCharset), sNewCharset);
//			sRet = new String(sRet.getBytes(sOldCharset), sNewCharset);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			sRet = "";
//		}

        if (bUseTrim) {
            return sRet.trim();
        } else {
            return sRet;
        }
    }

    public void reset() {
        iPos = 0;
    }

    public void setTrim(boolean bTrim) {
        bUseTrim = bTrim;
    }
}


