package test;

import utils.MailUtils;

import javax.mail.MessagingException;

/**
 * @Auther: 69013
 * @Date: 2018/11/15 14:57
 * @Description:
 */
public class TestMailer {
    public static void main(String[] args) {
        try {
            boolean send = new MailUtils("smtp.exmail.qq.com", "true", "domain\\demo", "liudatian@cvallis.com", "P3Ggt86Y6ttVYEbg").send(new String[]{"690139592@qq.com"}, null, null, "仅仅测试", "<h3>test</h3>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
