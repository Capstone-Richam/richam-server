package com.Nunbody.domain.Mail.service;


import com.Nunbody.domain.Mail.domain.MailBody;
import com.Nunbody.domain.Mail.domain.MailHeader;
import com.Nunbody.domain.Mail.domain.MailList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MailService {
    private final MongoTemplate mongoTemplate;
    private final Pattern pattern = Pattern.compile("<(.*?)>");
    private Matcher matcher;
    public MailBody Test(){
        MailBody mailBody;
        mailBody = MailBody.builder()
                .mailId("1")
                .content("안녕하세요 최호연이라고 합니다. 이렇게 연락드리게 되어서 대단히 죄송합니다")
                .build();

        return mongoTemplate.insert(mailBody);
    }
    public MailList getMail(String host){
        MailList naverMail = MailList.builder()
                .host(host)
                .build();

        /** naver mail */
        final String naverHost = "imap.naver.com";
        final String naverId = "qkrwlstjr0131";
        final String naverPassword = "beakgugong1!";
//        naverMail.setHost(host);



        try {
            Properties prop = new Properties();
            prop.put("mail.imap.host", naverHost);
            prop.put("mail.imap.port", 993);
            prop.put("mail.imap.ssl.enable", "true");
            prop.put("mail.imap.ssl.protocols", "TLSv1.2");
            prop.put("mail.store.protocol", "imap");


            // Session 클래스 인스턴스 생성
            Session session = Session.getInstance(prop);

            // Store 클래스
            Store store = session.getStore("imap");
            store.connect(naverHost, naverId, naverPassword);

            // 받은 편지함
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();
            MailHeader mailHeaderData;

            getMailBody(messages);
            for(int i=0;i<100;i++){
                matcher = pattern.matcher(messages[i].getFrom()[0].toString());
                if(matcher.find()) {
                    String fromPerson = matcher.group(1);
                    mailHeaderData = MailHeader.builder()
                            .title(messages[i].getSubject())
                            .fromPerson(fromPerson)
                            .date(String.valueOf(messages[i].getSentDate()))
                            .build();
                }
                else {
                    mailHeaderData = MailHeader.builder()
                            .title(messages[i].getSubject())
                            .fromPerson(messages[i].getFrom()[0].toString())
                            .build();
                }
                naverMail.addData(mailHeaderData);
            }

            // 폴더와 스토어 닫기
            folder.close(false);
            store.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return naverMail;
    }
    public void getMailBody(Message[] messages) throws MessagingException, IOException {
        List<MailBody> mailBodies = new ArrayList<>();
        Object content;
        for (int i=0; i<messages.length/2; i++) {
            content = messages[i].getContent();
            MailBody mailBody;
            if (content instanceof String) {
                // 텍스트 형식의 콘텐츠인 경우
                String text = content.toString();
                mailBody = MailBody.builder()
                        .content(text)
                        .build();
            } else if (content instanceof Multipart) {
                // 멀티파트인 경우 (예: HTML 메일)
                Multipart multipart = (Multipart) content;
                mailBody = MailBody.builder()
                        .content(multipart.toString())
                        .build();
            }
            else mailBody = null;
            mailBodies.add(mailBody);
        }
        mongoTemplate.insertAll(mailBodies);
    }

}
