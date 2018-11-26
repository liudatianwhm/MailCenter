package utils;

/**
 * @Auther: 69013
 * @Date: 2018/11/15 14:55
 * @Description:
 */
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
    private String host;
    private String auth;
    private String username;
    private String domainUser;
    private String password;

    public boolean send(String[] to, String[] cc, String[] bcc, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        Session s = Session.getInstance(props);
        //      s.setDebug(true);

        MimeMessage message = new MimeMessage(s);

        InternetAddress from = new InternetAddress(username);
        message.setFrom(from);
        InternetAddress[] Toaddress = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++)
            Toaddress[i] = new InternetAddress(to[i]);
        message.setRecipients(Message.RecipientType.TO, Toaddress);

        if (cc != null) {
            InternetAddress[] Ccaddress = new InternetAddress[cc.length];
            for (int i = 0; i < cc.length; i++)
                Ccaddress[i] = new InternetAddress(cc[i]);
            message.setRecipients(Message.RecipientType.CC, Ccaddress);
        }

        if (bcc != null) {
            InternetAddress[] Bccaddress = new InternetAddress[bcc.length];
            for (int i = 0; i < bcc.length; i++)
                Bccaddress[i] = new InternetAddress(bcc[i]);
            message.setRecipients(Message.RecipientType.BCC, Bccaddress);
        }
        message.setSubject(subject);
        message.setSentDate(new Date());

        BodyPart mdp = new MimeBodyPart();
        mdp.setContent(content, "text/html;charset=utf-8");
        Multipart mm = new MimeMultipart();
        mm.addBodyPart(mdp);
        message.setContent(mm);

        message.saveChanges();
        Transport transport = s.getTransport("smtp");
        transport.connect(host, (null == domainUser) ? username : domainUser, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return true;
    }

    public MailUtils(String host, String auth, String domainUser, String username, String password) {
        super();
        this.host = host;
        this.auth = auth;
        this.domainUser = domainUser;
        this.username = username;
        this.password = password;
    }
}
