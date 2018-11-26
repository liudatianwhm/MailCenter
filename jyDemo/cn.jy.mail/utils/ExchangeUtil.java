package utils;

/**
 * @Auther: 69013
 * @Date: 2018/11/15 17:40
 * TODO exchange邮件发送工具类
 * @Description:
 */
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * 邮件发送工具实现类
 *
 * @author vino.dang
 * @create 2017/01/05
 */
public class ExchangeUtil {

    private static Logger logger = LoggerFactory.getLogger(ExchangeUtil.class);


    /**
     * 发送邮件
     * @param
     * @return
     */
    public static boolean sendEmail() {

        Boolean flag = false;
        try {
            ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1); //新建server版本
            ExchangeCredentials credentials = new WebCredentials("vino", "abcd123", "spacex"); //用户名，密码，域名
            service.setCredentials(credentials);
            service.setUrl(new URI("https://outlook.spacex.com/EWS/Exchange.asmx")); //outlook.spacex.com 改为自己的邮箱服务器地址
            EmailMessage msg = new EmailMessage(service);
            msg.setSubject("This is a test!"); //主题
            msg.setBody(MessageBody.getMessageBodyFromText("this is a test! pls ignore it!")); //内容
            msg.getToRecipients().add("126@126.com"); //收件人
//        msg.getCcRecipients().add("test2@test.com"); //抄送人
//        msg.getAttachments().addFileAttachment("D:\\Downloads\\EWSJavaAPI_1.2\\EWSJavaAPI_1.2\\Getting started with EWS Java API.RTF"); //附件
            msg.send(); //发送
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;

    }


    public static void main(String[] args) {

        sendEmail();

    }
}

