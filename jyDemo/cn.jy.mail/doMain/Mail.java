package doMain;

/**
 * @Auther: 发送邮件的请求实体类
 * @Date: 2018/11/16 11:43
 * @Description:
 */
public class Mail {
    //收件人
    private String recipients;
    //文本内容
    private String content;
    //文件标题
    private String tittle;

    public Mail(String recipients, String content, String tittle) {
        this.recipients = recipients;
        this.content = content;
        this.tittle = tittle;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
