package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwOverallStatusService;
import powerservice.common.util.CommonUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwOverallStatusServiceImpl extends EgovAbstractServiceImpl implements DlwOverallStatusService {

	@Resource
    public DlwOverallStatusDAO dlwOverallStatusDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwOverallStatusServiceImpl.class);

	/** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int getOverallStatusCount(Map<String, Object> pmParam) {
        return dlwOverallStatusDAO.getOverallStatusCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusList(Map<String, Object> pmParam) {
        return dlwOverallStatusDAO.getOverallStatusList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 업데이트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int updateOverallStatusDownLoadEmplFileList(Map<String, ?> pmParam)throws Exception {
        return dlwOverallStatusDAO.updateOverallStatusDownLoadEmplFileList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 이력정보 등록
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_DNDL_HIST
     * ================================================================
     * */
    public int insertOverallStatusDownLoadEmplHist(Map<String, ?> pmParam) throws Exception {
        return dlwOverallStatusDAO.insertOverallStatusDownLoadEmplHist(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return dlwOverallStatusDAO.getOverallStatusBatchDay(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 유효성 체크
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int validationOverallStatusFile(Map<String, ?> pmParam) throws Exception {
        return dlwOverallStatusDAO.validationOverallStatusFile(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int insertOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return dlwOverallStatusDAO.insertOverallStatusBatchDay(pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int deleteOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return dlwOverallStatusDAO.deleteOverallStatusBatchDay(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 생성 (배치처리)
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY, LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int batchOverallStatusFileMake() throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String sYYYYMMDD = sdf.format(d);
        String sYYYYMM   = sYYYYMMDD.substring(0, 6);

        Map<String, Object> hmParamTypeA = new HashMap<String, Object>();
        hmParamTypeA.put("bat_proc_dt", sYYYYMMDD);
        Map<String, Object> hmParam = new HashMap();

        List<Map<String, Object>> listBatchInfo = dlwOverallStatusDAO.getOverallStatusExcelBatchDay(hmParamTypeA);

        if (listBatchInfo.size() <= 0)
        {
            System.out.println("배치수행 일자가 아닙니다. 배치를 종료합니다.");
            return -1;
        }

        String sMbidFileParentFixPath;
        String sMbidFileFullPath = null;
        String osName = System.getProperty("os.name").toLowerCase();
        
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        
        FileOutputStream outputStream = null;
        Connection connectionTypeA = null;
        PreparedStatement preparedStatementTypeA = null ;
        ResultSet rsTypeA = null;
        
        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
        String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");
        
        
        double dTotStatus = dlwOverallStatusDAO.selectTotStatusCount(listBatchInfo.get(0));
        
        int iTotStatus = (int)Math.ceil(dTotStatus/800000);
        int iPage1 = 1;
        int iPage2 = 800001;
        Workbook wb = new SXSSFWorkbook(100);
        
        hmParam = listBatchInfo.get(0);
        
        try
        {
        	if (osName.indexOf("windows") >= 0)
            {
                sMbidFileParentFixPath = "C:\\media\\Status\\"; // 이건 예제임. 실제 PATH가 아님. (rootPath : C:/)
                sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "\\" + sYYYYMMDD + "_Status.xlsx";
            }
            else
            {
                sMbidFileParentFixPath = "/media/Status/"; // 이건 예제임. 실제 PATH가 아님. (rootPath : /app/)
                sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "/" + sYYYYMMDD + "_Status.xlsx";
            }

            File fileMakeFileDirectoryPath = new File(sMbidFileParentFixPath + sYYYYMM);
            
            if(fileMakeFileDirectoryPath.exists() == false)
            {
                fileMakeFileDirectoryPath.mkdirs();
            }
            
	        for(int i=0; i<iTotStatus; i++) {
	        	
	            
	            if(i > 0) {
	            	iPage1 = iPage1+800000;
	            	iPage2 = iPage2+800000;
	            }
	            
	         // A 타입 파일 작성 start
	            String sqlStatementTypeA =  "";
	            sqlStatementTypeA += "WITH /* AlowMap.selectTotStatus */                                                                                                                                     \n";
	            sqlStatementTypeA += "    W_MAIN AS (                                                                                                                                                        \n";
	            sqlStatementTypeA += "            SELECT * FROM (                                                                                                                                            \n";
	            sqlStatementTypeA += "                SELECT PAGE_LIST.*,                                                                                                                                    \n";
	            sqlStatementTypeA += "                       ROWNUM AS RNUM                                                                                                                                  \n";
	            sqlStatementTypeA += "                  FROM (                                                                                                                                               \n";
	            sqlStatementTypeA += "        SELECT /*+ INDEX(MEM_PROD_ACCNT PK__MEM_PROD_ACCNT__46E78A0C) */                                                                                               \n";
	            sqlStatementTypeA += "               MPA.ACCNT_NO                                                                                                                                            \n";
	            sqlStatementTypeA += "             , MPA.PROD_CD                                                                                                                                             \n";
	            sqlStatementTypeA += "             , MEM.MEM_NO                                                                                                                                              \n";
	            sqlStatementTypeA += "             , count(MPA.ACCNT_NO) over() TOT_ROW_CNT                                                                                                                  \n";
	            sqlStatementTypeA += "          FROM LF_DMUSER.MEMBER MEM INNER JOIN                                                                                                                         \n";
	            sqlStatementTypeA += "               LF_DMUSER.MEM_PROD_ACCNT MPA ON MEM.MEM_NO = MPA.MEM_NO AND MPA.DEL_FG = 'N' AND MPA.JOIN_DT  <=  '"+hmParam.get("JOIN_ED_DT")+"'  AND MPA.JOIN_DT  >=  '"+hmParam.get("JOIN_ST_DT")+"'\n";
	            sqlStatementTypeA += "                    INNER JOIN                                                                                                                                         \n";
	            sqlStatementTypeA += "               LF_DMUSER.PRODUCT PROD ON MPA.PROD_CD = PROD.PROD_CD                                                                                                    \n";
	            sqlStatementTypeA += "                    INNER JOIN                                                                                                                                         \n";
	            sqlStatementTypeA += "               LF_DMUSER.EMPLOYEE EMP ON EMP.EMPLE_NO = MPA.EMPLE_NO                                                                                                   \n";
	            sqlStatementTypeA += "        WHERE 1=1                                                                                                                                                      \n";
	            sqlStatementTypeA += "          AND MEM.DEL_FG = 'N'                                                                                                                                         \n";
	            sqlStatementTypeA += "                ) PAGE_LIST                                                                                                                                            \n";
	            sqlStatementTypeA += "             ) PAGE_LIST                                                                                                                                               \n";
	            sqlStatementTypeA += "             WHERE 1=1                                                                                                                                                 \n";
	            sqlStatementTypeA += "             AND RNUM >= "+iPage1+"                                                                                                                                    \n";
	            sqlStatementTypeA += "             AND RNUM < "+iPage2+"                                                                                                                                     \n";
	            sqlStatementTypeA += "    )                                                                                                                                                                  \n";
	            sqlStatementTypeA += "    SELECT ACCNT_NO                             /* 회원번호 */                                                                                                         \n";
	            sqlStatementTypeA += "         , MEM_NO                                                                                                                                                      \n";
	            sqlStatementTypeA += "         , MEM_NM                               /* 회원명 */                                                                                                           \n";
	            sqlStatementTypeA += "         , MODEL_CL_NM                          /* 모델분류명 */                                                                                                       \n";
	            sqlStatementTypeA += "         , JOIN_DT                              /* 가입일자(1회당) */                                                                                                  \n";
	            sqlStatementTypeA += "         , PAY_MTHD                             /* 납입방법 */                                                                                                         \n";
	            sqlStatementTypeA += "         , PAY_STAT                             /* 납입상태(월) */                                                                                                     \n";
	            sqlStatementTypeA += "         , JOIN_STAT                            /* 가입상태 */                                                                                                         \n";
	            sqlStatementTypeA += "         , RESN_PROC_YN_NM                      /* 해약처리 */                                                                                                         \n";
	            sqlStatementTypeA += "         , EVENT_REG_DAY                        /* 행사일자 */                                                                                                         \n";
	            sqlStatementTypeA += "         , A042                                 /* 실납입회차 */                                                                                                       \n";
	            sqlStatementTypeA += "         , TOT_PAY_CNT                          /* 총납입횟수 */                                                                                                       \n";
	            sqlStatementTypeA += "         , LST_PAY_DAY                          /* 최종납입날짜 */                                                                                                     \n";
	            sqlStatementTypeA += "         , TOT_PAY_AMT                          /* 총납입금 */                                                                                                         \n";
	            sqlStatementTypeA += "         , TOT_PKG_AMT                          /* 총결합상품금 */                                                                                                     \n";
	            sqlStatementTypeA += "         , TOT_ADD_AMT                          /* 총추가부담금 */                                                                                                     \n";
	            sqlStatementTypeA += "         , LST_PAY_AMT                          /* 최종납입금 */                                                                                                       \n";
	            sqlStatementTypeA += "         , PKG_AMT                              /* 결합상품금 */                                                                                                       \n";
	            sqlStatementTypeA += "         , ADD_PAY_AMT                          /* 추가부담금 */                                                                                                       \n";
	            sqlStatementTypeA += "         , LIFE_BU_RM_AMT                       /* 상조부금잔여액 */                                                                                                   \n";
	            sqlStatementTypeA += "         , PKG_RM_AMT                           /* 결합잔여금액 */                                                                                                     \n";
	            sqlStatementTypeA += "         , PROD_CD                              /* 상품코드 */                                                                                                         \n";
	            sqlStatementTypeA += "         , PROD_NM                                                                                                                                                     \n";
	            sqlStatementTypeA += "         , RM_1Y_REL + RM_1Y_ADD AS RM_1_YEAR   /* 1년결합잔여 */                                                                                                      \n";
	            sqlStatementTypeA += "         , TOT_ROW_CNT                                                                                                                                                 \n";
	            sqlStatementTypeA += "         , count(*) over () cnt                                                                                                                                        \n";
	            sqlStatementTypeA += "         ,	 CASE WHEN SECTION2 = '0001' THEN '비채권'                                                                                                               \n";
	            sqlStatementTypeA += "                   WHEN SECTION2 = '0002' THEN '채권'                                                                                                                  \n";
	            sqlStatementTypeA += "                   ELSE SECTION2                                                                                                                                       \n";
	            sqlStatementTypeA += "             END SECTION2                                                                                                                                              \n";
	            sqlStatementTypeA += "         , MAIN_CONTRACT_NM                       /* 계약표시 */                                                                                                       \n";
	            sqlStatementTypeA += "         , USE_AMT /* 레디캐시 */                                                                                                                                      \n";
	            sqlStatementTypeA += "         , TO_CHAR(REG_DM, 'YYYYMMDD') AS REG_DM                                                                                                                       \n";
	            sqlStatementTypeA += "         , END_NO                                                                                                                                                      \n";
	            sqlStatementTypeA += "         , A039                                                                                                                                                        \n";
	            sqlStatementTypeA += "         , A040                                                                                                                                                        \n";
	            sqlStatementTypeA += "         , MAN_DAY                                                                                                                                                     \n";
	            sqlStatementTypeA += "      FROM (                                                                                                                                                           \n";
	            sqlStatementTypeA += "    SELECT K.ACCNT_NO                                                                                                                                                  \n";
	            sqlStatementTypeA += "         , K.MEM_NO                                                                                                                                                    \n";
	            sqlStatementTypeA += "         , K.MEM_NM                                                                                                                                                    \n";
	            sqlStatementTypeA += "         , K.MODEL_CL_NM                                                                                                                                               \n";
	            sqlStatementTypeA += "         , K.JOIN_DT                                                                                                                                                   \n";
	            sqlStatementTypeA += "         , K.PAY_MTHD                                                                                                                                                  \n";
	            sqlStatementTypeA += "         , CASE WHEN K.YEN_CHE = 0 AND K.YEN = 1 THEN '만기'                                                                                                           \n";
	            sqlStatementTypeA += "                WHEN K.YEN_CHE = 0 AND K.YEN = 0 THEN '정상'                                                                                                           \n";
	            sqlStatementTypeA += "                WHEN K.YEN_CHE = 1 AND K.YEN = 0 THEN '당월미납'                                                                                                       \n";
	            sqlStatementTypeA += "                WHEN K.YEN_CHE  >  1 AND K.YEN = 0 THEN TO_CHAR(K.YEN_CHE - 1) || '회 연체'                                                                            \n";
	            sqlStatementTypeA += "                WHEN K.YEN_CHE  <  0 AND K.YEN = 0 THEN REPLACE(TO_CHAR(K.YEN_CHE), '-', '')|| '회 선납'                                                               \n";
	            sqlStatementTypeA += "                WHEN K.YEN_CHE  <  0 AND K.YEN = 1 THEN '만기'                                                                                                         \n";
	            sqlStatementTypeA += "                END PAY_STAT                                                                                                                                           \n";
	            sqlStatementTypeA += "         , K.JOIN_STAT                                                                                                                                                 \n";
	            sqlStatementTypeA += "         , K.RESN_PROC_YN_NM                                                                                                                                           \n";
	            sqlStatementTypeA += "         , K.EVENT_REG_DAY                                                                                                                                             \n";
	            sqlStatementTypeA += "         , K.A042                                                                                                                                                      \n";
	            sqlStatementTypeA += "         , K.TOT_PAY_CNT                                                                                                                                               \n";
	            sqlStatementTypeA += "         , K.LST_PAY_DAY                                                                                                                                               \n";
	            sqlStatementTypeA += "         , K.TOT_PAY_AMT                                                                                                                                               \n";
	            sqlStatementTypeA += "         , TOT_PKG_AMT                                                                                                                                                 \n";
	            sqlStatementTypeA += "         , TOT_ADD_AMT                                                                                                                                                 \n";
	            sqlStatementTypeA += "         , K.LST_PAY_AMT                                                                                                                                               \n";
	            sqlStatementTypeA += "         , K.PKG_AMT                                                                                                                                                   \n";
	            sqlStatementTypeA += "         , K.ADD_PAY_AMT                                                                                                                                               \n";
	            sqlStatementTypeA += "         , K.LIFE_BU_RM_AMT                                                                                                                                            \n";
	            sqlStatementTypeA += "         , K.PKG_RM_AMT                                                                                                                                                \n";
	            sqlStatementTypeA += "         , K.PROD_CD                                                                                                                                                   \n";
	            sqlStatementTypeA += "         , K.PROD_NM                                                                                                                                                   \n";
	            sqlStatementTypeA += "         , (SELECT sum(REL_AMT)                                                                                                                                        \n";
	            sqlStatementTypeA += "              FROM LF_DMUSER.PRODUCT_NO_DATA                                                                                                                           \n";
	            sqlStatementTypeA += "             WHERE PROD_CD = K.PROD_CD                                                                                                                                 \n";
	            sqlStatementTypeA += "               AND NO BETWEEN K.PMD_NO + 1 AND K.PMD_NO+12                                                                                                             \n";
	            sqlStatementTypeA += "             ) AS RM_1Y_REL                                                                                                                                            \n";
	            sqlStatementTypeA += "         , (SELECT sum(ADD_AMT)                                                                                                                                        \n";
	            sqlStatementTypeA += "              FROM LF_DMUSER.PRODUCT_NO_DATA                                                                                                                           \n";
	            sqlStatementTypeA += "             WHERE PROD_CD = K.PROD_CD                                                                                                                                 \n";
	            sqlStatementTypeA += "               AND NO BETWEEN K.PMD1_NO + 1 AND K.PMD1_NO+12                                                                                                           \n";
	            sqlStatementTypeA += "             ) AS RM_1Y_ADD                                                                                                                                            \n";
	            sqlStatementTypeA += "         , K.TOT_ROW_CNT                                                                                                                                               \n";
	            sqlStatementTypeA += "         , SECTION2                                                                                                                                                    \n";
	            sqlStatementTypeA += "         , K.MAIN_CONTRACT_NM                                                                                                                                          \n";
	            sqlStatementTypeA += "         , K.USE_AMT                                                                                                                                                   \n";
	            sqlStatementTypeA += "         , K.REG_DM                                                                                                                                                    \n";
	            sqlStatementTypeA += "         , K.END_NO                                                                                                                                                    \n";
	            sqlStatementTypeA += "         , A039                                                                                                                                                        \n";
	            sqlStatementTypeA += "         , A040                                                                                                                                                        \n";
	            sqlStatementTypeA += "         , MAN_DAY                                                                                                                                                     \n";
	            sqlStatementTypeA += "    FROM (SELECT M.ACCNT_NO                                                                                                                                            \n";
	            sqlStatementTypeA += "               , M.MEM_NO                                                                                                                                              \n";
	            sqlStatementTypeA += "               , MEM.MEM_NM                                                                                                                                            \n";
	            sqlStatementTypeA += "               , PMCC.MODEL_CL_NM                                                                                                                                      \n";
	            sqlStatementTypeA += "               , MPA.JOIN_DT                                                                                                                                           \n";
	            sqlStatementTypeA += "               , LF_DMUSER.FN_COM_NM('01', MPA.PAY_MTHD)                                                                                                               \n";
	            sqlStatementTypeA += "                      AS PAY_MTHD                                                                                                                                      \n";
	            sqlStatementTypeA += "               , CASE WHEN (P.EXPR_NO - MPA.NEW_CHAN_GUNSU)  >  NVL(TRUNC(MONTHS_BETWEEN(LAST_DAY('"+hmParam.get("JOIN_ED_DT")+"') ,MPA.JOIN_DT),0) + 1, 0)              \n";
	            sqlStatementTypeA += "                      THEN NVL(TRUNC(MONTHS_BETWEEN(LAST_DAY('"+hmParam.get("JOIN_ED_DT")+"') ,MPA.JOIN_DT),0) + 1, 0) - NVL(PM.NO, 0)                                   \n";
	            sqlStatementTypeA += "                      ELSE NVL((P.EXPR_NO - MPA.NEW_CHAN_GUNSU), 0) - NVL(PM.NO, 0)                                                                                    \n";
	            sqlStatementTypeA += "                  END YEN_CHE                                                                                                                                          \n";
	            sqlStatementTypeA += "               , CASE WHEN (P.EXPR_NO - MPA.NEW_CHAN_GUNSU) = PM.NO                                                                                                    \n";
	            sqlStatementTypeA += "                      THEN 1                                                                                                                                           \n";
	            sqlStatementTypeA += "                      ELSE 0                                                                                                                                           \n";
	            sqlStatementTypeA += "                  END YEN                                                                                                                                              \n";
	            sqlStatementTypeA += "               , CASE WHEN EVT.ACCNT_NO IS NOT NULL THEN '행사'                                                                                                        \n";
	            sqlStatementTypeA += "                      WHEN RESN.ACCNT_NO IS NOT NULL THEN LF_DMUSER.FN_COM_NM('64',RESN.RESN_CL)                                                                       \n";
	            sqlStatementTypeA += "                      ELSE '정상'                                                                                                                                      \n";
	            sqlStatementTypeA += "                  END JOIN_STAT                                                                                                                                        \n";
	            sqlStatementTypeA += "               , CASE WHEN NVL(RESN_CL,'X') = '02' THEN '미대상'                                                                                                       \n";
	            sqlStatementTypeA += "                             WHEN NVL(RESN_PROC_YN,'N') ='Y' AND NVL(CHK_SUBMIT,'N') ='N' THEN '처리'                                                                  \n";
	            sqlStatementTypeA += "                             ELSE '미처리'                                                                                                                             \n";
	            sqlStatementTypeA += "                         END RESN_PROC_YN_NM                                                                                                                           \n";
	            sqlStatementTypeA += "               , CASE WHEN EVT.ACCNT_NO IS NOT NULL THEN EVT.EVENT_COMP_DAY                                                                                            \n";
	            sqlStatementTypeA += "                      WHEN RESN.ACCNT_NO IS NOT NULL THEN RESN.RESN_PROC_DAY                                                                                           \n";
	            sqlStatementTypeA += "                        ELSE NULL                                                                                                                                      \n";
	            sqlStatementTypeA += "                      END EVENT_REG_DAY                                                                                                                                \n";
	            sqlStatementTypeA += "               , NVL(PM.NO, 0) A042                                                                                                                                    \n";
	            sqlStatementTypeA += "               , P.EXPR_NO - MPA.NEW_CHAN_GUNSU AS TOT_PAY_CNT                                                                                                         \n";
	            sqlStatementTypeA += "               , NVL(PM.PAY_DAY, '') LST_PAY_DAY                                                                                                                       \n";
	            sqlStatementTypeA += "               , CASE WHEN P.PROD_CL = '01' THEN                                                                                                                       \n";
	            sqlStatementTypeA += "                            P.PROD_AMT                                                                                                                                 \n";
	            sqlStatementTypeA += "                      WHEN P.PROD_CL = '03' THEN                                                                                                                       \n";
	            sqlStatementTypeA += "                            PND.AMT                                                                                                                                    \n";
	            sqlStatementTypeA += "                      ELSE                                                                                                                                             \n";
	            sqlStatementTypeA += "                            NULL                                                                                                                                       \n";
	            sqlStatementTypeA += "                      END TOT_PAY_AMT                                                                                                                                  \n";
	            sqlStatementTypeA += "               , PND.REL_AMT AS   TOT_PKG_AMT                                                                                                                          \n";
	            sqlStatementTypeA += "               , PND.ADD_AMT AS TOT_ADD_AMT                                                                                                                            \n";
	            sqlStatementTypeA += "               , NVL(PM.PAY_AMT, 0) LST_PAY_AMT                                                                                                                        \n";
	            sqlStatementTypeA += "               , NVL(PMD.PAY_AMT, 0) PKG_AMT                                                                                                                           \n";
	            sqlStatementTypeA += "               , NVL(PMD1.PAY_AMT, 0) ADD_PAY_AMT                                                                                                                      \n";
	            sqlStatementTypeA += "               , PND.AMT - NVL((P.MON_PAY_AMT * MPA.NEW_CHAN_GUNSU), 0) - NVL(PM.PAY_AMT, 0) LIFE_BU_RM_AMT                                                            \n";
	            sqlStatementTypeA += "               , (PND.REL_AMT + PND.ADD_AMT) - (NVL(PMD.PAY_AMT, 0) + NVL(PMD1.PAY_AMT, 0)) PKG_RM_AMT                                                                 \n";
	            sqlStatementTypeA += "               , MPA.PROD_CD                                                                                                                                           \n";
	            sqlStatementTypeA += "               , P.PROD_NM                                                                                                                                             \n";
	            sqlStatementTypeA += "               , NVL(PMD.NO,0) PMD_NO                                                                                                                                  \n";
	            sqlStatementTypeA += "               , NVL(PMD1.NO,0) PMD1_NO                                                                                                                                \n";
	            sqlStatementTypeA += "               , M.TOT_ROW_CNT                                                                                                                                         \n";
	            sqlStatementTypeA += "               , P.SECTION_T as SECTION2                                                                                                                               \n";
	            sqlStatementTypeA += "               , NVL(FN_COM_NM('0194', MPA.MAIN_CONTRACT), '상조') AS MAIN_CONTRACT_NM                                                                                 \n";
	            sqlStatementTypeA += "               , NVL((SELECT MD.USE_AMT FROM LF_DMUSER.TB_MEMBER_DLWMALL MD WHERE 1=1 AND MD.GOODS_ID = M.ACCNT_NO), 0) AS USE_AMT                                     \n";
	            sqlStatementTypeA += "               , MPA.REG_DM                                                                                                                                            \n";
	            sqlStatementTypeA += "               , (SELECT MAX(END_NO) FROM PRODUCT_DTL WHERE ALLT_AMT > 0 AND PROD_CD = MPA.PROD_CD) AS END_NO                                                          \n";
	            sqlStatementTypeA += "               , replace(NVL(MPA.NEW_CHAN_GUNSU * P.MON_PAY_AMT, 0), chr(0), '') A039                                                                                  \n";
	            sqlStatementTypeA += "               , replace(NVL(MPA.NEW_CHAN_GUNSU, 0), chr(0), '') A040                                                                                                  \n";
	            sqlStatementTypeA += "               , TO_CHAR(ADD_MONTHS(MPA.JOIN_DT, P.EXPR_NO - MPA.NEW_CHAN_GUNSU - 1 +                                                                                  \n";
	            sqlStatementTypeA += "		                           NVL((SELECT                                                                                                                           \n";
	            sqlStatementTypeA += "		                                    NVL(MME.EXT_PERIOD, 0)                                                                                                       \n";
	            sqlStatementTypeA += "		                                FROM LF_DMUSER.TB_MEMBER_MANGI_EXT MME                                                                                           \n";
	            sqlStatementTypeA += "		                                WHERE 1=1                                                                                                                        \n";
	            sqlStatementTypeA += "		                                AND DEL_FG = 'N'                                                                                                                 \n";
	            sqlStatementTypeA += "		                                AND MME.ACCNT_NO = MPA.ACCNT_NO), 0) * 12 ),'YYYYMMDD') AS MAN_DAY                                                               \n";
	            sqlStatementTypeA += "            FROM W_MAIN M INNER JOIN                                                                                                                                   \n";
	            sqlStatementTypeA += "                 LF_DMUSER.MEMBER MEM ON M.MEM_NO = MEM.MEM_NO                                                                                                         \n";
	            sqlStatementTypeA += "                      INNER JOIN                                                                                                                                       \n";
	            sqlStatementTypeA += "                 LF_DMUSER.MEM_PROD_ACCNT MPA ON M.ACCNT_NO = MPA.ACCNT_NO                                                                                             \n";
	            sqlStatementTypeA += "                      INNER JOIN                                                                                                                                       \n";
	            sqlStatementTypeA += "                 LF_DMUSER.PRODUCT P ON M.PROD_CD = P.PROD_CD                                                                                                          \n";
	            sqlStatementTypeA += "                      LEFT OUTER JOIN                                                                                                                                  \n";
	            sqlStatementTypeA += "                 LF_DMUSER.PRODUCT_MODEL_CL_CD PMCC ON MPA.PROD_MODEL_KIND = PMCC.MODEL_CL_CD                                                                          \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 (SELECT PMG.ACCNT_NO                                                                                                                                  \n";
	            sqlStatementTypeA += "                       , SUM(PMG.PAY_AMT) PAY_AMT                                                                                                                      \n";
	            sqlStatementTypeA += "                       , COUNT(PMG.NO) NO                                                                                                                              \n";
	            sqlStatementTypeA += "                       , MAX(PMG.PAY_DAY) PAY_DAY                                                                                                                      \n";
	            sqlStatementTypeA += "                    FROM LF_DMUSER.PAY_MNG PMG                                                                                                                         \n";
	            sqlStatementTypeA += "                       , W_MAIN TP                                                                                                                                     \n";
	            sqlStatementTypeA += "                   WHERE PMG.ACCNT_NO = TP.ACCNT_NO                                                                                                                    \n";
	            sqlStatementTypeA += "                     AND PMG.DEL_FG = 'N'                                                                                                                              \n";
	            sqlStatementTypeA += "                     AND PMG.PAY_DAY   <=   to_char(LAST_DAY('"+hmParam.get("LAST_DAY")+"') ,'YYYYMMDD')                                                               \n";
	            sqlStatementTypeA += "                   GROUP BY PMG.ACCNT_NO) PM ON PM.ACCNT_NO = MPA.ACCNT_NO                                                                                             \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 (SELECT PMD2.ACCNT_NO                                                                                                                                 \n";
	            sqlStatementTypeA += "                       , SUM(PMD2.PAY_AMT) PAY_AMT                                                                                                                     \n";
	            sqlStatementTypeA += "                       , COUNT(PMD2.NO) NO                                                                                                                             \n";
	            sqlStatementTypeA += "                       , MAX(PMD2.PAY_DAY) PAY_DAY                                                                                                                     \n";
	            sqlStatementTypeA += "                    FROM LF_DMUSER.PAY_MNG_DTL PMD2                                                                                                                    \n";
	            sqlStatementTypeA += "                       , W_MAIN TP                                                                                                                                     \n";
	            sqlStatementTypeA += "                   WHERE PMD2.ACCNT_NO = TP.ACCNT_NO                                                                                                                   \n";
	            sqlStatementTypeA += "                     AND PMD2.DEL_FG = 'N'                                                                                                                             \n";
	            sqlStatementTypeA += "                     AND PMD2.PAY_DAY   <=   to_char(LAST_DAY('"+hmParam.get("LAST_DAY")+"') ,'YYYYMMDD')                                                              \n";
	            sqlStatementTypeA += "                   GROUP BY PMD2.ACCNT_NO) PMD ON PMD.ACCNT_NO = MPA.ACCNT_NO                                                                                          \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 (SELECT PMD3.ACCNT_NO                                                                                                                                 \n";
	            sqlStatementTypeA += "                       , SUM(PMD3.PAY_AMT) PAY_AMT                                                                                                                     \n";
	            sqlStatementTypeA += "                       , COUNT(PMD3.NO) NO                                                                                                                             \n";
	            sqlStatementTypeA += "                       , MAX(PMD3.PAY_DAY) PAY_DAY                                                                                                                     \n";
	            sqlStatementTypeA += "                    FROM LF_DMUSER.PAY_MNG_DTL1 PMD3                                                                                                                   \n";
	            sqlStatementTypeA += "                       , W_MAIN TP                                                                                                                                     \n";
	            sqlStatementTypeA += "                   WHERE PMD3.ACCNT_NO = TP.ACCNT_NO                                                                                                                   \n";
	            sqlStatementTypeA += "                     AND PMD3.DEL_FG = 'N'                                                                                                                             \n";
	            sqlStatementTypeA += "                     AND PMD3.PAY_DAY   <=   to_char(LAST_DAY('"+hmParam.get("LAST_DAY")+"') ,'YYYYMMDD')                                                              \n";
	            sqlStatementTypeA += "                   GROUP BY PMD3.ACCNT_NO) PMD1 ON PMD1.ACCNT_NO = MPA.ACCNT_NO                                                                                        \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 LF_DMUSER.EVENT EVT ON EVT.ACCNT_NO = MPA.ACCNT_NO AND EVT.DEL_FG = 'N' AND EVT.EVENT_PROC_DAY  <=  to_char(LAST_DAY('"+hmParam.get("JOIN_ED_DT")+"'), 'YYYYMMDD')\n";
	            sqlStatementTypeA += "                 AND EVT.EVENT_PROC_DAY  >=  to_char(LAST_DAY('"+hmParam.get("JOIN_ST_DT")+"'), 'YYYYMMDD')                                                              \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 LF_DMUSER.RESCISSION RESN ON RESN.ACCNT_NO = MPA.ACCNT_NO AND RESN.DEL_FG = 'N' AND  RESN.RESN_PROC_DAY  <=  to_char(LAST_DAY('"+hmParam.get("JOIN_ED_DT")+"') ,'YYYYMMDD')\n";
	            sqlStatementTypeA += "                 AND  RESN.RESN_PROC_DAY  >=  to_char(LAST_DAY('"+hmParam.get("JOIN_ST_DT")+"') ,'YYYYMMDD')                                                             \n";
	            sqlStatementTypeA += "                       LEFT OUTER JOIN                                                                                                                                 \n";
	            sqlStatementTypeA += "                 (SELECT PROD_CD                                                                                                                                       \n";
	            sqlStatementTypeA += "                       , SUM(AMT)                   AMT                                                                                                                \n";
	            sqlStatementTypeA += "                       , SUM(REL_AMT)               REL_AMT                                                                                                            \n";
	            sqlStatementTypeA += "                       , SUM(ADD_AMT)               ADD_AMT                                                                                                            \n";
	            sqlStatementTypeA += "                    FROM LF_DMUSER.PRODUCT_NO_DATA                                                                                                                     \n";
	            sqlStatementTypeA += "                   GROUP BY PROD_CD) PND ON PND.PROD_CD = MPA.PROD_CD                                                                                                  \n";
	            sqlStatementTypeA += "        ) K                                                                                                                                                            \n";
	            sqlStatementTypeA += "    ) Q                                                                                                                                                                \n";
	
	            System.out.println(sqlStatementTypeA);
	
            
            	Class.forName(sAccessClassName);
                connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
                rsTypeA = preparedStatementTypeA.executeQuery();
	            
                
                // 대용량 엑셀 만들기
                
                Sheet sh = wb.createSheet(Integer.toString(i+1));
                
                // 선수금 sheet용
                String[] sArrTitle = {"고유번호","회원번호","회원명","상품명","모델분류명","등록일자(접수일자)","가입일자(1회당)","납입방법","납입상태(월)","가입상태"
                		             ,"해약처리","행사종료일자","특별할인금","특별할인회차","실납입회차","총납입횟수","결합회차","최종납입날짜","총납입금","총결합상품금"
                		             ,"총추가부담금","최종납입금","결합상품금","추가부담금","상조부금잔여금액","결합잔여금액","1년 결합잔여","색션2","계약표시","레디캐시금액","만기일자"};

                List <String> lstCol = new ArrayList<String>();
                lstCol.add("mem_no");
                lstCol.add("accnt_no");
                lstCol.add("mem_nm");
                lstCol.add("prod_nm");
                lstCol.add("model_cl_nm");
                lstCol.add("reg_dm");
                lstCol.add("join_dt");
                lstCol.add("pay_mthd");
                lstCol.add("pay_stat");
                lstCol.add("join_stat");
                lstCol.add("resn_proc_yn_nm");
                lstCol.add("event_reg_day");
                lstCol.add("a039");
                lstCol.add("a040");
                lstCol.add("a042");
                lstCol.add("tot_pay_cnt");
                lstCol.add("end_no");
                lstCol.add("lst_pay_day");
                lstCol.add("tot_pay_amt");
                lstCol.add("tot_pkg_amt");
                lstCol.add("tot_add_amt");
                lstCol.add("lst_pay_amt");
                lstCol.add("pkg_amt");
                lstCol.add("add_pay_amt");
                lstCol.add("life_bu_rm_amt");
                lstCol.add("pkg_rm_amt");
                lstCol.add("rm_1_year");
                lstCol.add("section2");
                lstCol.add("main_contract_nm");
                lstCol.add("use_amt");
                lstCol.add("man_day");
                
                int idx = 0;
                int jdx = 0;
                Row row = sh.createRow(idx++);
                
                for (String field : sArrTitle)
                {
                    Cell cell = row.createCell(jdx++);
                    cell.setCellValue((String) field);
                }
                
                // 해당 셀에 데이터를 입력한다.
                Map<String, Object> mRow = null;
                String val = "";
                String colId = "";

                for (idx = 0; idx < mList.size(); idx++) 
                {
                    mRow = mList.get(idx);
                    row = sh.createRow(idx+1);
                    
                    for (jdx = 0; jdx < lstCol.size(); ++jdx) 
                    {
                        colId = lstCol.get(jdx);
                        Cell cell = row.createCell(jdx);
                        val = CommonUtils.nvls(String.valueOf(mRow.get(colId)));
                        cell.setCellValue(val);
                    }
                }
                
                while(rsTypeA.next())
                {
                    row = sh.createRow(idx+1);
                    
                    for (jdx = 0; jdx < lstCol.size(); ++jdx) 
                    {
                        colId = lstCol.get(jdx);
                        Cell cell = row.createCell(jdx);
                        val = CommonUtils.nvls(String.valueOf(rsTypeA.getString(colId)));
                        cell.setCellValue(val);
                    }
                    idx++;
                }
	        }
	        outputStream = new FileOutputStream(sMbidFileFullPath);
            wb.write(outputStream);
	        
	        hmParamTypeA.put("ext_dt", sYYYYMMDD);
            hmParamTypeA.put("file_type", "0");
            hmParamTypeA.put("file_path", sMbidFileFullPath);
            hmParamTypeA.put("file_name", sYYYYMMDD + "_Status.xlsx");
            
            dlwOverallStatusDAO.insertOverallStatusExcelFileList(hmParamTypeA);
	        
        //System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," + "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);
            
            outputStream.close();
            rsTypeA.close();
            preparedStatementTypeA.close();
            connectionTypeA.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(outputStream != null)
            {
                outputStream.close();
            }
            if(rsTypeA != null)
            {
                rsTypeA.close();
            }
            if(preparedStatementTypeA != null)
            {
                preparedStatementTypeA.close();
            }
            if(connectionTypeA != null)
            {
                connectionTypeA.close();
            }
        }
    return 0;
    }
}
