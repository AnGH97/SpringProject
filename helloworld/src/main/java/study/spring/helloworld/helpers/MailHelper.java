package study.spring.helloworld.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class MailHelper {
    // --> import org.springframework.mail.javamail.JavaMailSender;
    private final JavaMailSender javaMailSender;

    //"환경설정값을 주입받은 Beans"를 주입받기 위한 객체
    @Value("${mailhelper.sender.name}")
    private String senderName;

    @Value("${mailhelper.sender.email}")
    private String senderEmail;
    /**
     * 메일을 발송한다.
     * 
     * @param receiverName  - 수신자 이름
     * @param receiverEmail - 수신자 이메일 주소
     * @param subject   - 제목
     * @param content   - 내용
     * @throws MessagingException
     */
    public void sendMail(String receiverName, String receiverEmail, String subject, String content) throws Exception{
        log.debug("-------------------------------------------");
        log.debug(String.format("SenderName: %s", senderName));
        log.debug(String.format("SenderEmail: %s", senderEmail));
        log.debug(String.format("RecvEmail: %s", receiverEmail));
        log.debug(String.format("RecvName: %s", receiverName));
        log.debug(String.format("Subject: %s", subject));
        log.debug(String.format("Content: %s", content));
        log.debug("-------------------------------------------");

        /*//생성자를 통해 주입된 설정 객체를 원래 형태로 형변환
        //--> import org.springframework.mail.javamail.JavaMailSenderImpl;
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) this.javaMailSender;
        
        //설정파일에 설정된 사용자 이름을 가져온다.
        String sender = mailSenderImpl.getUsername();*/

        //--> import javax.mail.internet.MimeMessage;
        MimeMessage message = javaMailSender.createMimeMessage();
        //--> import org.springframework.mail.javamail.MimeMessageHelper;
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //제목, 내용, 수신자, 발신자 설정
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(new InternetAddress(senderEmail, "UTF-8"));
        helper.setTo(new InternetAddress(receiverEmail, receiverName, "UTF-8"));

        //메일 보내기
        javaMailSender.send(message);
    }

    /**
     * 메일을 발송한다.(첨부파일 없음)
     * 
     * @param receiverEmail - 수신자 이메일 주소
     * @param subject   - 제목
     * @param content   - 내용
     * @throws MessagingException
     */
    public void sendMail(String receiverEmail, String subject, String content) throws Exception{
        this.sendMail(null, receiverEmail, subject, content);
    }
}
