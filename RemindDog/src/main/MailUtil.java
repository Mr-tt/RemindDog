package main;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Administrator
 */
public class MailUtil {

    private static final String MAIL_HOST = "smtp.163.com";
    private static final String MAIL_TRANSPORT_PROTOCOL = "smtp";
    private static final String USER = "609715176@163.com";
    // 这个秘密不是邮箱的密码，是要去163邮箱开通申请得到的
    private static final String PASSWORD = "yehaitao1994";

    private MailUtil() {
    }

    public static void main(String[] args) {
        try {
            MailUtil.testSendMail("忘记打卡啦！快去打卡！！！", "打卡打卡！！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testSendMail(String subject, String content) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", MAIL_HOST);
        prop.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);
        //开启debug模式，方便看程序发送Email的运行状态
        session.setDebug(true);
        Transport ts = session.getTransport();
        ts.connect(MAIL_HOST, USER, PASSWORD);
        Message message = simpleMail(session, subject, content);
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }


    /**
     * 一封简单的只包含文本的邮件
     */
    public static MimeMessage simpleMail(Session session, String subject, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("609715176@qq.com"));
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=UTF-8");
        return message;
    }
}
