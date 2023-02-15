/*
 * (@)# EmilRecpController.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/06/16
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

package powerservice.business.mta.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.DateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import powerservice.business.mta.service.EmilRecpService;
import powerservice.business.sys.service.FileService;
import powerservice.common.util.CommonUtils;

/**
 * 이메일을 수신 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/06/16
 * @프로그램ID EmilRecp
 */

@Controller
@RequestMapping(value = "/email")
public class EmilRecpController {

    @Resource
    private EmilRecpService emilRecpService;

    @Resource
    private FileService fileService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/recv")
    public Map<String, String> recvEmailList() throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();

        try {
            /*
            String mail_recv_url		= basicValueService.getBasicValueAsString("email_recv_url");		// E-Mail 수신 서버 URL
            String mail_recv_port		= basicValueService.getBasicValueAsString("email_recv_port");		// E-Mail 수신 서버 포트
            String mail_recv_protocol   = basicValueService.getBasicValueAsString("email_recv_protocol");	// E-Mail 수신 서버 프로토콜
            String mail_recv_id	 		= basicValueService.getBasicValueAsString("email_recv_id");			// E-Mail 수신 아이디
            String mail_recv_pwd		= basicValueService.getBasicValueAsString("email_recv_pwd");		// E-Mail 수신 패스워드
            */

            String mail_recv_url		= "mail.inwoo.co.kr";		// E-Mail 수신 서버 URL
            String mail_recv_port		= "110";					// E-Mail 수신 서버 포트
            String mail_recv_protocol   = "pop3";					// E-Mail 수신 서버 프로토콜
            String mail_recv_id	 		= "yjjeong@inwoo.co.kr";	// E-Mail 수신 아이디
            String mail_recv_pwd		= "";			// E-Mail 수신 패스워드


            // SMTP 메일 설정
            Properties properties = new Properties();
            properties.setProperty("mail.imap.host", mail_recv_url);
            properties.setProperty("mail.imap.port", mail_recv_port);
            properties.setProperty("mail.store.protocol", mail_recv_protocol);
            properties.setProperty("mail.imap.auth.plain.disable", "true");
            properties.setProperty("mail.imap.ssl.enable", "true");

            Session session = Session.getDefaultInstance(properties, null);
            session.setDebug(false);
            Store store     = session.getStore(mail_recv_protocol);
            store.connect(mail_recv_url, mail_recv_id, mail_recv_pwd);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            // Message[] messages = inbox.getMessages();

            // 날짜 검색 조건 셋팅 - 1일 이내에 발송된 이메일만 검색해서 가져온다.
            Calendar compareDate = Calendar.getInstance();
            compareDate.set(Calendar.HOUR_OF_DAY, 0);
            compareDate.set(Calendar.MINUTE, 0);
            compareDate.set(Calendar.SECOND, 0);
            compareDate.set(Calendar.MILLISECOND, 0);
            compareDate.add(Calendar.DATE, -1);
            // System.out.println("compare date:" + compareDate.getTime());

            SearchTerm srcTerm = new SentDateTerm(DateTerm.GE, compareDate.getTime());
            Message[] messages = inbox.search(srcTerm);

            // System.out.println("message Length:" + messages.length);


            for (Message msg : messages) {
                Map<String, Object> mMailInfo = mailInfo(msg);
                if (mMailInfo.get("from") != null) {
                    Map<String, Object> emlMap = new HashMap<String, Object>();

                    // 회신주소
                    String replyTo = "";
                    List<Map<String, Object>> tempList = (List<Map<String, Object>>)mMailInfo.get("reply-to");
                    if (tempList != null) {
                        for(int i=0; i< tempList.size(); i++) {
                            String tempAddr = ((Map<String, Object>)tempList.get(i)).get("addr").toString();
                            if(tempAddr == "")
                                continue;

                            if (replyTo != "") {
                                replyTo = replyTo + ", ";
                            }
                            replyTo = replyTo + tempAddr;
                        }
                    }
                    tempList = (List<Map<String, Object>>)mMailInfo.get("to");
                    if (tempList != null) {
                        for(int i=0; i< tempList.size(); i++) {
                            String tempAddr = ((Map<String, Object>)tempList.get(i)).get("addr").toString();
                            if(tempAddr == "" || tempAddr.equals(mail_recv_id))
                                continue;

                            if (replyTo != "") {
                                replyTo = replyTo + ", ";
                            }

                            replyTo = replyTo + tempAddr;
                        }
                    }
                    // System.out.println("##reply-to:" + replyTo);

                    // 참조/숨은참조 주소
                    String ccTo = "";
                    tempList = (List<Map<String, Object>>)mMailInfo.get("cc");
                    if (tempList != null) {
                        for(int i=0; i< tempList.size(); i++) {
                            String tempAddr = ((Map<String, Object>)tempList.get(i)).get("addr").toString();
                            if(tempAddr == "" || tempAddr.equals(mail_recv_id))
                                continue;

                            if (ccTo != "") {
                                ccTo = ccTo + ", ";
                            }

                            ccTo = ccTo + tempAddr;
                        }
                    }
                    tempList = (List<Map<String, Object>>)mMailInfo.get("bcc");
                    if (tempList != null) {
                        for(int i=0; i< tempList.size(); i++) {
                            String tempAddr = ((Map<String, Object>)tempList.get(i)).get("addr").toString();
                            if(tempAddr == "" || tempAddr.equals(mail_recv_id))
                                continue;

                            if (ccTo != "") {
                                ccTo = ccTo + ", ";
                            }

                            ccTo = ccTo + tempAddr;
                        }
                    }
                    // System.out.println("##cc-to:" + ccTo);

                    emlMap.put("emilmsg_id", mMailInfo.get("id"));
                    emlMap.put("emil_titl", mMailInfo.get("title"));
                    emlMap.put("emil_cntn", ((Map<String, Object>)mMailInfo.get("body")).get("text"));
                    emlMap.put("emil_html_cntn", ((Map<String, Object>)mMailInfo.get("body")).get("html"));
                    emlMap.put("recp_dttm", mMailInfo.get("recv_dttm"));
                    emlMap.put("dpms_dttm", mMailInfo.get("send_dttm"));
                    emlMap.put("dpms_emil_addr", ((Map<String, Object>)mMailInfo.get("from")).get("addr"));
                    emlMap.put("dpms_chpr_nm", ((Map<String, Object>)mMailInfo.get("from")).get("name"));
                    emlMap.put("reply_to", replyTo);
                    emlMap.put("cc_to", ccTo);

                    emlMap.put("fileList", ((Map<String, Object>)mMailInfo.get("body")).get("fileList"));

                    emlMap.put("cntr_cd", "CCA");
                    emlMap.put("rgsr_id", "SYSTEM");
                    emlMap.put("amnd_id", "SYSTEM");

                    String key = emilRecpService.insertEmilRecp(emlMap);

                    if (key.equals("")) {
                        System.out.println("## EMILMSG_ID : " + mMailInfo.get("id") + ". this e-mail is already exists in database. ");
                    }

                    // messages[i].setFlag(Flags.Flag.DELETED, true);
                    // System.out.println("===================================================================================================\r\n");
                }
            }

            inbox.close(true);
            store.close();

        } catch (MessagingException except) {
            except.printStackTrace();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {

        }

        // System.out.println("### EMail sendEmail end. (" + resultMap.get("sendfg") + ")");

        return resultMap;
    }

    /**
     * 이메일 메시지 정보를 파싱하여 리턴한다.
     * @param msg
     * @return
     * @throws Exception
     */
    private Map<String, Object> mailInfo(Message msg) throws Exception {
        Map<String, Object> mResult = new HashMap<String, Object>();

        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

        mResult.put("id", msg.getHeader("Message-ID")[0]);
        mResult.put("send_dttm", msg.getSentDate() == null ? null : DATE_FORMAT.format(msg.getSentDate()));
        mResult.put("recv_dttm", msg.getReceivedDate() == null ?  null : DATE_FORMAT.format(msg.getReceivedDate()));

        mResult.put("title", MimeUtility.decodeText(msg.getSubject()));

        mResult.put("from", msg.getFrom() == null ? "" : parseAddress(msg.getFrom()).get(0));		// 보낸사람
        mResult.put("to", parseAddress(msg.getRecipients(Message.RecipientType.TO)));				// 받는사람
        mResult.put("reply-to", parseAddress(msg.getReplyTo()));									// 회신받는 사람
        mResult.put("cc", parseAddress(msg.getRecipients(Message.RecipientType.CC)));				// 참조자
        mResult.put("bcc", parseAddress(msg.getRecipients(Message.RecipientType.BCC)));				// 숨은 참조자
        // CommonUtils.printLog(mResult);

        Object content = msg.getContent();
        Map<String, Object> bodyMap = null;
        if (content instanceof String) {
            bodyMap = new HashMap<String, Object>();
            bodyMap.put("html", content.toString());

        } else if (content instanceof Multipart) {
            bodyMap = parseMultipart((Multipart) content);
        }

        mResult.put("body", bodyMap);
        // CommonUtils.printLog(mResult);

        return mResult;
    }

    /**
     * ADDRESS 정보를 파싱해서 리턴한다.
     *
     * @param ADDRESS
     * @throws java.lang.Exception
     * @return Map<String, String> 보낸사람 풀네임(주소+성함), 이름, 이메일주소
     */
    private List<Map<String, String>> parseAddress(Address[] addArray) throws Exception {
        if (addArray == null)
            return null;

        List<Map<String, String>> mResultList = new ArrayList<Map<String, String>>();

        for (Address address : addArray) {
            Map<String, String> mFrom = new HashMap<String, String>();
            String sFullAddr = "", sName = "", sAddr ="";

            sFullAddr = MimeUtility.decodeText(address.toString()).replaceAll("\"", "").replaceAll("'",  "");

            Pattern p = Pattern.compile("<(.*.)>");
            Matcher m = p.matcher(sFullAddr);
            if (m.find()) {
                sAddr = m.group(1);
            }

            p = Pattern.compile("(.*.)<");
            m = p.matcher(sFullAddr);
            if (m.find()) {
                sName = m.group(1).trim();
            }

            mFrom.put("fullname", sFullAddr);
            mFrom.put("name", sName);
            mFrom.put("addr", sAddr.equals("") ? sFullAddr : sAddr);

            mResultList.add(mFrom);
        }

        return mResultList;
    }

    /**
     * 이메일의 MultiPart(바디)부분을 text/plain과 text/html, 파일로 파싱해서 리턴하여 준다.
     *
     * @param MultiPart 이메일의 Body
     * @throws java.lang.Exception
     * @return Map<String, String> 태그제거 내용, 태그포함 내용, 첨부파일리스트
     */
    private Map<String, Object> parseMultipart(Multipart mp) throws MessagingException, IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        resultMap.put("fileList", fileList);

        parseMultipart(mp, resultMap, 0);

        return resultMap;
    }
    @SuppressWarnings("unchecked")
    private  void parseMultipart(Multipart mp, Map<String, Object> resultMap, int lev) throws MessagingException, IOException {
        for(int i=0; i<mp.getCount(); i++) {
            BodyPart bp = mp.getBodyPart(i);

            if (bp.getContent() instanceof Multipart) {
                parseMultipart((Multipart)bp.getContent(), resultMap, lev+1);
            } else {
                // 첨부파일일 경우 처리
                if (Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition())) {
                    String atch_typ_cd = "10";
                    String content_id = ((MimeBodyPart)bp).getContentID() != null ? ((MimeBodyPart)bp).getContentID().replaceAll("<", "").replaceAll(">", "") : "";
                    String file_nm = bp.getFileName() == null ? "image" + new Date().getTime() + ".jpg" : MimeUtility.decodeText(bp.getFileName());

                    // 파일 추출 시작
                    InputStream is = bp.getInputStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while((bytesRead = is.read(buf))!=-1) {
                        buffer.write(buf, 0, bytesRead);
                    }
                    buffer.flush();
                    is.close();
                    // 파일 추출 종료

                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    fileMap.put("content_id", content_id);				// Content-ID
                    fileMap.put("atch_typ_cd", atch_typ_cd);			// 첨부유형코드
                    fileMap.put("file_nm", file_nm);					// 파일 명
                    fileMap.put("file_cntn", buffer.toByteArray());		// 파일내용
                    fileMap.put("file_size_vl", buffer.size());			// 파일크기
                    ((List<Map<String, Object>>)resultMap.get("fileList")).add(fileMap);

                    // System.out.println("[" + lev + "][" + i + "] : is AttachFile.");
                    // System.out.println("[" + lev + "][" + i + "] : is File. file_nm:" + file_nm + ", size:" + buffer.size());

                // inline 이미지일 경우 처리
                } else if (bp.isMimeType("image/jpeg")) {
                    String atch_typ_cd = "20";
                    String content_id = ((MimeBodyPart)bp).getContentID() != null ? ((MimeBodyPart)bp).getContentID().replaceAll("<", "").replaceAll(">", "") : "";
                    String file_nm = bp.getFileName() == null ? "image" + new Date().getTime() + ".jpg" : MimeUtility.decodeText(bp.getFileName());

                    // 파일 추출 시작
                    InputStream is = bp.getInputStream();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while((bytesRead = is.read(buf))!=-1) {
                        buffer.write(buf, 0, bytesRead);
                    }
                    buffer.flush();
                    is.close();
                    // 파일 추출 종료

                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    fileMap.put("content_id", content_id);				// Content-ID
                    fileMap.put("atch_typ_cd", atch_typ_cd);			// 첨부유형코드
                    fileMap.put("file_nm", file_nm);					// 파일 명
                    fileMap.put("file_cntn", buffer.toByteArray());		// 파일내용
                    fileMap.put("file_size_vl", buffer.size());			// 파일크기
                    ((List<Map<String, Object>>)resultMap.get("fileList")).add(fileMap);

                    // System.out.println("[" + lev + "][" + i + "] : is inline Image, content-ID : " + content_id);
                    // System.out.println("[" + lev + "][" + i + "] : is File. file_nm:" + file_nm + ", size:" + buffer.size());

                // BASE64 이미지일 경우 처리
                } else if (bp.getContentType().contains("image/")) {
                    String atch_typ_cd = "20";
                    String content_id = ((MimeBodyPart)bp).getContentID() != null ? ((MimeBodyPart)bp).getContentID().replaceAll("<", "").replaceAll(">", "") : "";
                    String file_nm = "image" + new Date().getTime() + ".jpg";

                    // 파일 추출 시작
                    com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) bp.getContent();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                    byte[] buf = new byte[4096];
                    int bytesRead;
                    while((bytesRead = test.read(buf))!=-1) {
                        buffer.write(buf, 0, bytesRead);
                    }
                    buffer.flush();
                    // 파일 추출 종료

                    Map<String, Object> fileMap = new HashMap<String, Object>();
                    fileMap.put("content_id", content_id);				// Content-ID
                    fileMap.put("atch_typ_cd", atch_typ_cd);			// 첨부유형코드
                    fileMap.put("file_nm", file_nm);					// 파일 명
                    fileMap.put("file_cntn", buffer.toByteArray());		// 파일내용
                    fileMap.put("file_size_vl", buffer.size());			// 파일크기
                    ((List<Map<String, Object>>)resultMap.get("fileList")).add(fileMap);

                    // System.out.println("[" + lev + "][" + i + "] : is inline Image, content-ID : " + content_id);
                    // System.out.println("[" + lev + "][" + i + "] : is Base64 File. file_nm:" + file_nm + ", size:" + buffer.size());

                // 태그 제거 내용일 경우 처리
                } else if (bp.getContentType().contains("text/html")) {
                    resultMap.put("html", bp.getContent());
                    // System.out.println("[" + lev + "][" + i + "] : " + bp.getContent());

                // 태그 포함 내용일 경우 처리
                } else if (bp.getContentType().contains("text/plain")) {
                    resultMap.put("text", bp.getContent());
                    // System.out.println("[" + lev + "][" + i + "] : " + bp.getContent());

                } else {
                    // else TO-DO
                    System.out.println("[" + lev + "][" + i + "] Exception Type.");
                    System.out.println("[" + lev + "][" + i + "] : " + bp.getDisposition());
                    System.out.println("[" + lev + "][" + i + "] : " + bp.getContentType());
                    System.out.println("[" + lev + "][" + i + "] : " + bp.getContent());
                }
            }
        }
    }

}