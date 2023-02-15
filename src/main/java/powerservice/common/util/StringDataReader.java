package powerservice.common.util;


/**
 * 문자열을 읽어들이는 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2016. 08. 29.
 */

public class StringDataReader {
	int iPos;
	int iLen;
	boolean bUseTrim;
	String sData;
	
	public StringDataReader(String str) {		
		sData = str;
		iPos = 0;
		iLen = sData.length();
		bUseTrim = false;
	}
	
	public String get(int iCnt) {
		if (iPos >= iLen) {
			return "";
		}
		
		int iEnd = iPos + iCnt;
		
		if (iEnd >= iLen) {
			iEnd = iLen;
		}
		
		String sRet = sData.substring(iPos, iEnd);
		iPos = iPos + iCnt;
		
		if (bUseTrim) {
			return sRet.trim();
		} else {
			return sRet;
		}
	}
	
	public void reset() {
		iPos = 0;
	}
	
	public void setTrim(boolean bUseTrim) {
		bUseTrim = bUseTrim;
	}
}

