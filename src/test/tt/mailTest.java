import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2019/10/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class mailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testMail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //发件人
        helper.setFrom("15068610616@163.com");
        //收件人
        helper.setTo("15068610616@163.com");
        //标题
        helper.setSubject("subject");
        //文本
        helper.setText("message text");
        //附件
        mailSender.send(mimeMessage);
    }
}
