<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="powerservice.business.web.web.WebNiceSenderController"%>

<!--
<script type="text/javascript">

</script>
-->
<html>
<head>
<title> NICE 전송 Test 화면</title>
</head>
<body>
    <%
    /**

    * @ NICE로 부터 받은 값 공통부
    *	data_type = 전문 식별 값
    *	corporation_code = 고객사 코드 값
    *	app_no = 고객 식별 값
    *	nice_no = NICE가 발행한 계약건 유니크 값
    *	nccs_stat = 계약건 상태 값
    *	1차 발송 시 - 01: 젒수 시	02: 통신사 인증 성공 시	04: 세이프키 및 CB정보 획득 후 완료 시
    *	2차 발송 시 - 01: 접수 시	02: 위탁처리 동의 시 	04: 계약서 작성 및 완료 시
    *	memo_no = 메모 시퀀스 값
    *	upp_tx_type = 전송 구분 값
    *	01: 1차 접수 시	02: 통신사 인증 성공 시	03: 세이프키 및 CB정보 획득 후 완료 시 04: 2차 접수 시 05: 위탁처리 동의 시 06: 계약서 작성 및 완료 시
    */

    String data_type			= (request.getParameter("data_type") == null) ? "": request.getParameter("data_type");
    String corporation_code		= (request.getParameter("corporation_code") == null) ? "": request.getParameter("corporation_code");
    String app_no				= (request.getParameter("app_no") == null) ? "": request.getParameter("app_no");
    String nice_no				= (request.getParameter("nice_no") == null) ? "": request.getParameter("nice_no");
    String nccs_stat			= (request.getParameter("nccs_stat") == null) ? "": request.getParameter("nccs_stat");
    String memo_no				= (request.getParameter("memo_no") == null) ? "": request.getParameter("memo_no");
    String upp_tx_type			= (request.getParameter("upp_tx_type") == null) ? "": request.getParameter("upp_tx_type");

    /**
    * @ NICE로 부터 받은 값 개별부. 필요없는 값일 경우 무시
    *	ca_name = 컨설턴트 성명
    *	ca_ph1 = 컨설턴트 연락처
    *	ca_ph2 = 컨설턴트 연락처
    *	ca_ph3 = 컨설턴트 연락처
    *	promise_date = 약속일자
    *	promise_time = 약속시간
    *	zip1 = 실징구지 우편번호(앞)
    *	zip2 = 실징구지 우편번호(뒤)
    *	addr1 = 실징구지 우편주소
    *	addr2 = 실징구지 상세주소
    *	g_cnt = 보증인 수
    *	g1_ca_name = 보증인 컨설턴트 성명
    *	g1_ca_ph1 = 보증인 컨설턴트 연락처
    *	g1_ca_ph2 = 보증인 컨설턴트 연락처
    *	g1_ca_ph3 = 보증인 컨설턴트 연락처
    *	g1_promise_date = 보증인 약속일자
    *	g1_promise_time = 보증인 약속시간
    *	g1_zip1 = 보증인실징구지우편번호(앞)
    *	g1_zip2 = 보증인실징구지우편번호(뒤)
    *	g1_addr1 = 보증인실징구지우편주소
    *	g1_addr2 = 보증인실징구지상세주소
    *	request_reason = 고객 인증 방법(NSIC - 본인인증 서비스 사용 시) 1차발송때 휴대폰1 카드2
    *	impossible_reason = 징구불가사유
    *	img_file = 이미지 파일('|' 파이프 구분자로 전송:img_file=파일명1|파일명2|파일명3)
    *	manager_name = 나신정 담당자 성명
    */

    //추가 값 CB정보 관련

    String recvMessage			= (request.getParameter("recvMessage") == null) ? "": request.getParameter("recvMessage"); // 신용정보
    String ci                   = (request.getParameter("ci") == null) ? "": request.getParameter("ci");                   // CI_VAL 값
    System.out.print(ci);
    String gender               = (request.getParameter("gender") == null) ? "": request.getParameter("gender");           // 성별
    String img_file				= (request.getParameter("img_file") == null) ? "": request.getParameter("img_file");      // ImgFile 명

    String ca_name				= (request.getParameter("ca_name") == null) ? "" : request.getParameter("ca_name");
    String ca_ph1				= (request.getParameter("ca_ph1") == null) ? "": request.getParameter("ca_ph1");
    String ca_ph2				= (request.getParameter("ca_ph2") == null) ? "": request.getParameter("ca_ph2");
    String ca_ph3				= (request.getParameter("ca_ph3") == null) ? "": request.getParameter("ca_ph3");
    String promise_date			= (request.getParameter("promise_date") == null) ? "": request.getParameter("promise_date");
    String promise_time			= (request.getParameter("promise_time") == null) ? "": request.getParameter("promise_time");
    String zip1					= (request.getParameter("zip1") == null) ? "": request.getParameter("zip1");
    String zip2					= (request.getParameter("zip2") == null) ? "": request.getParameter("zip2");
    String addr1				= (request.getParameter("addr1") == null) ? "": request.getParameter("addr1");
    String addr2				= (request.getParameter("addr2") == null) ? "": request.getParameter("addr2");
    String g_cnt				= (request.getParameter("g_cnt") == null) ? "": request.getParameter("g_cnt");
    String g1_ca_name			= (request.getParameter("g1_ca_name") == null) ? "" : request.getParameter("g1_ca_name");
    String g1_ca_ph1			= (request.getParameter("g1_ca_ph1") == null) ? "": request.getParameter("g1_ca_ph1");
    String g1_ca_ph2			= (request.getParameter("g1_ca_ph2") == null) ? "": request.getParameter("g1_ca_ph2");
    String g1_ca_ph3			= (request.getParameter("g1_ca_ph3") == null) ? "": request.getParameter("g1_ca_ph3");
    String g1_promise_date	= (request.getParameter("g1_promise_date") == null) ? "": request.getParameter("g1_promise_date");
    String g1_promise_time	= (request.getParameter("g1_promise_time") == null) ? "": request.getParameter("g1_promise_time");
    String g1_zip1				= (request.getParameter("g1_zip1") == null) ? "" : request.getParameter("g1_zip1");
    String g1_zip2				= (request.getParameter("g1_zip2") == null) ? "": request.getParameter("g1_zip2");
    String g1_addr1				= (request.getParameter("g1_addr1") == null) ? "": request.getParameter("g1_addr1");
    String g1_addr2				= (request.getParameter("g1_addr2") == null) ? "": request.getParameter("g1_addr2");
    String request_reason		= (request.getParameter("request_reason") == null) ? "": request.getParameter("request_reason");
    String impossible_reason	= (request.getParameter("impossible_reason") == null) ? "" : request.getParameter("impossible_reason");
    //String img_file				= (request.getParameter("img_file") == null) ? "": request..getParameter("img_file");
    String manager_name		= (request.getParameter("manager_name") == null) ? "": request.getParameter("manager_name");

    //응답 결과 세팅 파라미터

    String result_memo	= "";
    String result_code 	= "01";

    /*응답 공통부 */
    /*
    String data_type		 	 = "SICROG01";
    String corporation_code	 	 = "DMDLCC";
    String app_no			 	 = "TAR201802060610030";
    String app_type              = "01";
    String nice_no			 	 = "fdiapshpohrfeiwupq4032786y278r3y21038y4trfg3b19v79r83qgd9g9646w4fbwq380";
    String nccs_stat		 	 = "01";
    String memo_no			 	 = "";
    String upp_tx_type		 	 = "01";
    String result_code           = "01";
    String result_memo           = "";
    String gender                = ""; // "01";
    String ci                    = ""; // "ntfeAFnbuzgkJTGrzCoC0P+Klv/W9cz+5ibqQuzr9Jm+9vG0Aqc6LkuOvmumr3zcd1WVn7yy+mFCGKF1gEovVA==";
    String recvMessage			 = ""; // (request.getParameter("recvMessage") == null) ? "": request.getParameter("recvMessage");
    String img_file              = ""; // "TAR201802060610031.pdf";
    */

    /* 응답 개별부 */
    //String cust_nm               = "안용운";
    //String clph_tlno             = "01036795543";
    //String brth_mon_day          = "19870101";

    //NICE로부터 수신받은 데이터 필수 값 체크

        if("".equals(data_type))
        {
            result_code = "02"; //전문 식별 값 누락으로, 필수값 누락 처리
            result_memo = "data_type";
        }
        else if("UPPROG01".equals(data_type) || "UPPROG02".equals(data_type) || "SICROG01".equals(data_type))
        {
            if("".equals(corporation_code) || "".equals(app_no) || "".equals(nice_no) || "".equals(nccs_stat))
            {
                result_code = "02";
                result_memo = "필수값 누락 항목 기재";
            }
        }
        else
        {
            if("".equals(corporation_code) || "".equals(app_no) || "".equals(nice_no))
            {
                result_code = "02";
                result_memo = "필수값 누락 항목 기재";
            }
        }
    %>

    <jsp:forward page="/web/nice/updateNiceSendStatement">
        <jsp:param name="app_no"            	value='<%=app_no %>' />
        <jsp:param name="data_type"         	value='<%=data_type %>' />
        <jsp:param name="corporation_code"  	value='<%=corporation_code %>' />
        <jsp:param name="nice_no"           	value='<%=nice_no %>' />
        <jsp:param name="nccs_stat"         	value='<%=nccs_stat %>' />
        <jsp:param name="memo_no"           	value='<%=memo_no %>' />
        <jsp:param name="upp_tx_type"       	value='<%=upp_tx_type %>' />
        <jsp:param name="result_code"       	value='<%=result_code %>' />
        <jsp:param name="result_memo"       	value='<%=result_memo %>' />
        <jsp:param name="ci"           			value='<%=ci %>' />
        <jsp:param name="gender"                value='<%=gender %>' />
        <jsp:param name="recvMessage"           value='<%=recvMessage %>' />
        <jsp:param name="img_file"              value='<%=img_file %>' />
    </jsp:forward>

</body>
</html>
