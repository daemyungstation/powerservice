package powerservice.business.acn.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface AcnTranService {


        public int getTranMonCount(Map<String, ?> pmParam) throws Exception;

        public int getTranMonerrCount(Map<String, ?> pmParam) throws Exception;

        public int getsuTranMonCount(Map<String, ?> pmParam) throws Exception;

        public int getsuCount(Map<String, ?> pmParam) throws Exception;



        public int updateconvertResult(Map<String, ?> pmParam) throws Exception;


        public int updatesuconvertResult(Map<String, ?> pmParam) throws Exception;



        //녹취조회
        public int getrecacoCount(Map<String, ?> pmParam) throws Exception;




        //녹취조회리스트

        public List<Map<String, Object>> getrecacoList(Map<String, ?> pmParam)  throws Exception;


        //reject회원

        public List<Map<String, Object>> getrejectList(Map<String, ?> pmParam)  throws Exception;




       //삭제
        public int deleteCount(Map<String, ?> pmParam) throws Exception;

        public int deletesunapCount(Map<String, ?> pmParam) throws Exception;


        public List<Map<String, Object>> getTranMonList(Map<String, ?> pmParam)  throws Exception;

        public List<Map<String, Object>> getTranMonerrList(Map<String, ?> pmParam)  throws Exception;

        public List<Map<String, Object>> gettranseletresultList(Map<String, ?> pmParam)  throws Exception;

        public List<Map<String, Object>> getsuList(Map<String, ?> pmParam)  throws Exception;



    // 검색 시 사용
    //public Map<String, Object> getTranMon(String psId) throws Exception;

        public  List<Map<String, Object>> insertAClist(Map<String, ?> pmParam)  throws Exception;


        //수납산출생성
        public  List<Map<String, Object>> suinsertAClist(Map<String, ?> pmParam)  throws Exception;


        public List<Map<String, Object>> getinsertAClist(Map<String, ?> pmParam) throws Exception;



    /* *
     * [[신규 탭]]에서 사용하는 조회 / 정보변경 / 리스트 출력 메소드
     *
     */
     //신규 조회 count
        public int getTranNewCount(Map<String, ?> pmParam) throws Exception;

        public int getTranacuontCount(Map<String, ?> pmParam) throws Exception;

        public int getTranacuonsilCount(Map<String, ?> pmParam) throws Exception;

    //신규 조회리스트
        public List<Map<String, Object>> getTranNewList(Map<String, ?> pmParam)  throws Exception;

        public List<Map<String, Object>> getTranYList(Map<String, ?> pmParam)  throws Exception;

    //연체
        public List<Map<String, Object>> getYHjList(Map<String, ?> pmParam)  throws Exception;

     //정보변경
        public List<Map<String, Object>> getUpdateInfo(Map<String, ?> pmParam)  throws Exception;
        //회원결과 업로드
     //   public List<Map<String, Object>> acuonupdate(Map<String, ?> pmParam)  throws Exception;
        //회원결과 업로드
        public int acuonupdate(Map<String, ?> pmParam) throws Exception;

        public int getselectresultCount(Map<String, ?> pmParam) throws Exception;


}
