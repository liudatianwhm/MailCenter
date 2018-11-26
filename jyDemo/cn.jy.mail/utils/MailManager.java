package utils;

/**
 * @Auther: 69013
 * @Date: 2018/11/15 15:23
 * TODO 网易邮件附件发送
 * @Description:
 */
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailManager implements Runnable{
    private CyclicBarrier cyclicBarrier;
    private int count;
    private  final static String host = "smtp.163.com";
    private  final static String from = "ldt182469@163.com"; // 发件人邮箱地址
    private  final static String user = "ldt182469@163.com"; // 发件人称号，同邮箱地址
    private  final static String password = "ldt1993"; // 发件人邮箱客户端授权码
    private  final static String port = "25";
    static Vector<String> file; // 多个附件
    private  final static String filename = "D:\\钉钉测试demo\\作业合同文档.DOCX"; // 附件的文件名
    private String recipients;
    private String content;
    private String tittle;

    public MailManager(CyclicBarrier cyclicBarrier, int count, String recipients, String content, String tittle) {
        this.cyclicBarrier = cyclicBarrier;
        this.count = count;
        this.recipients = recipients;
        this.content = content;
        this.tittle = tittle;
    }

    public MailManager(String recipients, String content, String tittle) {
        this.recipients = recipients;
        this.content = content;
        this.tittle = tittle;
    }
    public String toChinese(String text){
        try {
            text = MimeUtility.encodeText(new String(text.getBytes(), "GB2312"), "GB2312", "B");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
    /**
     * @param host
     * @param from
     * @param user
     * @param password
     * @param port
     * @param file
     * @param filename
     * @param recipients
     * @param content
     * @param tittle
     * TODO 发送邮件
     * author xiaofeixia
     * @Date: 2018/11/15 15:23
     *
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String host,String from,String user,String password,String port,Vector<String> file,String filename,String recipients,String content,String tittle) {
        System.out.println(host);
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.163.com"); // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", "smtp.163.com"); // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true"); // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props); // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true); // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session); // 加载发件人地址
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients)); // 加载收件人地址
            message.setSubject(tittle); // 加载标题
            Multipart multipart = new MimeMultipart(); // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart(); // 设置邮件的文本内容
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            //添加附件
//            if (file!=null){
//                Enumeration<String> efile = file.elements();
//                while (efile.hasMoreElements()){
//                    MimeBodyPart mbpFile = new MimeBodyPart();
//                    filename = efile.nextElement().toString();
//                    FileDataSource fds = new FileDataSource(filename);
//                    mbpFile.setDataHandler(new DataHandler(fds));
//                    mbpFile.setFileName(toChinese(fds.getName()));
//                }
//            }
            // 附件部分
            contentPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(filename);
            contentPart.setDataHandler(new DataHandler(source));

//            contentPart.setFileName("测试文件一");
            // 处理附件名称中文（附带文件路径）乱码问题
            try {
                contentPart.setFileName(MimeUtility.encodeText(filename));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);
            message.saveChanges(); // 保存变化
            Transport transport = session.getTransport("smtp"); // 连接服务器的邮箱
            transport.connect(host,Integer.parseInt(port),user, password); // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
//            transport.sendMessage(message, new Address[] { new InternetAddress("toEmail@qq.com") });
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public static void main(String[] args) { // 做测试用
////        List<User> list = new ArrayList<User>();
////        for(int i = 0 ;i<100;i++){
////            User user = new User(String.valueOf(i),i+"@163.com");
////            list.add(user);
////        }
////        MailUtilswy ds = new MailUtilswy();
////        MailManager.sendMail(host,from,user,password,port,file,filename,"690139592@qq.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");
//        MailManager mailManager = new MailManager("690139592@qq.com", "你好，这是一封测试邮件，无需回复。", "测试文件邮件");
//        Thread thread = new Thread(mailManager);
//        thread.start();
//    }

    @Override
    public void run() {
        sendMail(host,from,user,password,port,file,filename,recipients,content,tittle);
    }
}