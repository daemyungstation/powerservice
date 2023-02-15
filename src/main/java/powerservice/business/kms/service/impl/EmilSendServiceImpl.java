/*
 * (@)# EmilFxfrServiceImpl.java
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.kms.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.kms.service.EmilSendService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.sys.service.CdService;
import powerservice.business.sys.service.impl.FileDAO;
import powerservice.common.util.CommonUtils;

import com.sun.mail.smtp.SMTPTransport;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 이메일 템플릿 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID EmilFxfr
 */

@Service
public class EmilSendServiceImpl extends EgovAbstractServiceImpl implements EmilSendService {

    @Resource
    public EmilFxfrDAO emilFxfrDAO;

    @Resource
    public FileDAO fileDAO;

    @Resource
    private BasVlService basVlService;

    @Resource
    private CdService cdService;

    /**
     * 이메일을 발송한다.
     *
     * @param param HashMap<String, Object>
     * @throws Exception
     */
    public Map<String, String> sendEmail(Map<String, Object> param) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();

        SMTPTransport smtptransport = null;

        // System.out.println("###param : " + param);
        // System.out.println("### EMail sendEmail start.");

        try {
            // 메일서버정보
            String mailer         = "smtpsend";
            // E-Mail발송 서버 주소	"mail.inwoo.co.kr";
            String mailhosturl    = basVlService.getBasVlAsString("email_host_url");
            // E-Mail발송 서버 포트
            String mailhostport   = basVlService.getBasVlAsString("email_host_port");
            // E-Mail발송 서버 프로토콜
            String mailprotocol   = basVlService.getBasVlAsString("email_host_protocol");
            // E-Mail발송 서버 아이디
            String mailserverid	  = basVlService.getBasVlAsString("email_system_addr");
            // E-Mail 서버로그인암호
            String mailhostpwd	  = basVlService.getBasVlAsString("email_system_pw");

            // 기본 인증 해제
            String mailservercert = "true"; // E-Mail발송 서버 인증여부

            if ("".equals(mailhosturl) || "".equals(mailhostport) || "".equals(mailprotocol) || "".equals(mailserverid)) {
                resultMap.put("contents", CommonUtils.checkNull((String) param.get("emil_html_cntn")));
                resultMap.put("sendfg", "N");
                resultMap.put("errMsg", "ERROR : Check the mailhost-url and port");
            }

            // SMTP 메일 설정
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", mailprotocol);
            properties.setProperty("mail." + mailprotocol + ".starttls.enable", "true");
            properties.setProperty("mail." + mailprotocol + ".host", mailhosturl);
            properties.setProperty("mail." + mailprotocol + ".port", mailhostport);
            properties.setProperty("mail." + mailprotocol + ".auth", mailservercert);// SMTP 인증을 설정

            Authenticator authenticator = null;
            authenticator = new SMTPAuthenticator(mailserverid, mailhostpwd);

            if ("true".equals(mailservercert)) {
                properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }

            // 보내는 사람 이메일주소
            String fromaddr = mailserverid;
            // 받는 사람 이메일주소
            String toaddr   = CommonUtils.checkNull((String) param.get("emil_addr"));
            // 참조자 이메일주소
            String ccaddr   = CommonUtils.checkNull((String) param.get("ccemailaddr"));
            // 제목
            String title    = CommonUtils.checkNull((String) param.get("emil_titl"));
            // 내용
            String contents = CommonUtils.checkNull((String) param.get("emil_html_cntn"));

            // customTag 파싱
            String [] codeFilter = {"SYS050"};
            List<Map<String, Object>> codeList = cdService.getCdList(codeFilter);
            for (Map<String, Object> code : codeList) {
                String sValue ="";
                if (param.get(code.get("adtl_cd")) instanceof BigDecimal) {
                    sValue = String.valueOf(param.get(code.get("adtl_cd")));
                } else {
                    sValue = StringUtils.defaultString((String)param.get(code.get("adtl_cd")));
                }
                title = title.replace(code.get("cd_nm").toString(), sValue);
                contents = contents.replace(code.get("adtl_cd_nm").toString(), sValue);
            }

            Session session = Session.getInstance(properties, authenticator);

            Message message = new MimeMessage(session);

            message.setSubject(MimeUtility.encodeText(title, "UTF-8", "B")); // 제목
            message.setSentDate(new Date()); // 보내는 시간
            message.setHeader("X-Mailer", mailer);
            message.setFrom(new InternetAddress(fromaddr)); // 보내는 사람 이메일주소

            if (!"".equals(toaddr)) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toaddr, false)); // 받는 사람 이메일주소
            }
            if (!"".equals(ccaddr)) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccaddr, false)); // 참조자 이메일주소
            }

            Multipart multipart = new MimeMultipart();

            // 첨부파일
            String attachcnts = ""; // 첨부파일 내용
            String filename = null;
            byte[] filecontent = null;

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> fileList = (List<Map<String, Object>>) param.get("fileList");
            if (fileList != null) {
                for (Map<String, Object> fileMap : fileList) {
                    String fileid = (String) fileMap.get("file_id");
                    filename = (String) fileMap.get("file_nm");
                    filecontent = (byte[]) fileMap.get("file_cntn");

                    MimeBodyPart bodypart = new MimeBodyPart();
                    //ByteArrayDataSource 클래스 : byte 배열(첨부파일)을 데이터로 입력받음
                    bodypart.setDataHandler(new DataHandler(new ByteArrayDataSource(filecontent, "application/octet-stream")));
                    bodypart.setFileName(MimeUtility.encodeText(filename, "UTF-8", "B"));

                    // 에디터 이미지
                    if("30".equals(fileMap.get("atch_typ_cd"))){
                        bodypart.setHeader("Content-ID", fileid);	// Content-ID 생성
                    } else {
                        attachcnts += "\n" + filename;	// 컨텐츠에 파일이름 추가
                    }
                    multipart.addBodyPart(bodypart);

                    // 파일 메모리를 강제로 할당 해제
                    filecontent = null;
                    fileMap.remove("file_cntn");
                }
                fileList = null;
            }

            // 첨부파일 내용 추가
            if (!"".equals(attachcnts)) {
                contents += "\n\n\n[첨부파일]" + attachcnts + "\n\n* 첨부파일을 확인해주세요.";
            }

            contents = contents.replace("\n", "<br/>");
            resultMap.put("contents", contents);

            // 첨부이미지 경로 변경(cid:fileid 형태로 변경);
            Pattern pImgSrc = Pattern.compile("(?i)<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
            Pattern pFileid = Pattern.compile("\\/powerservice\\/file\\/download\\?file_id=(\\w+)");

            Matcher imgSrcMatcher = pImgSrc.matcher(contents);
            while (imgSrcMatcher.find()) {					// 이미지 태그 갯수만큼 반복
                String sImgSrc = imgSrcMatcher.group(1);	// SRC 속성

                Matcher fileidMatcher = pFileid.matcher(sImgSrc);

                if (fileidMatcher.find()) {
                    String srcTag = fileidMatcher.group(1);	// fileidc
                    contents = contents.replace(sImgSrc, "cid:"+srcTag);
                }
            }

            // 내용(HTML)
            MimeBodyPart bodypart = new MimeBodyPart();
            bodypart.setDataHandler(new DataHandler(new ByteArrayDataSource(contents, "text/html; charset=UTF-8")));
            multipart.addBodyPart(bodypart);
            // 내용(TEXT)
            bodypart = new MimeBodyPart();
            bodypart.setDataHandler(new DataHandler(new ByteArrayDataSource(CommonUtils.stripTag(contents), "text/plain; charset=UTF-8")));
            multipart.addBodyPart(bodypart);

            message.setContent(multipart);


            // 발송
            if (!"".equals(toaddr)) {
                try {
                    smtptransport = (SMTPTransport) session.getTransport(mailprotocol);
                    smtptransport.connect(mailhosturl, mailserverid, mailhostpwd); // 계정값 : no-reply 필요
                    smtptransport.sendMessage(message, message.getAllRecipients());	//계정값 : no-reply@worldvision.or.kr 필요
                    resultMap.put("sendfg", "Y");		//이메일 발송 성공 여부
                    resultMap.put("errMsg", "");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    smtptransport.close();
                }
            }
        } catch (MessagingException except) {
            except.printStackTrace();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            if (smtptransport != null) {
                smtptransport.close();
            }
        }

        // System.out.println("### EMail sendEmail end. (" + resultMap.get("sendfg") + ")");

        return resultMap;
    }


    class SMTPAuthenticator extends Authenticator {

        PasswordAuthentication passwordAuthentication;

        SMTPAuthenticator(String userName, String password) {
            passwordAuthentication = new PasswordAuthentication(userName, password);
        }
        //???
        public PasswordAuthentication getPasswordAuthentication() {
            return passwordAuthentication;
        }
    }
}
