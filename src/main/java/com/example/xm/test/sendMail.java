package com.example.xm.test;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class sendMail {

    public static void main(String[] args) {
        sendEmailWithAttachment("d:/pms_file", new Date());
    }

    public static void sendEmailWithAttachment(String path, Date date){
        SimpleDateFormat formatterForFileName = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 设置发件人、收件人、SMTP服务器等信息
        String from = "johnson@ddts.com.cn"; // 发件人的邮箱
        String password = "NYf59KnQBKmZY4ES";//发件人邮箱密码或授权码
        String to = "2656571256@qq.com"; // 收件人邮箱
        String host = "smtp.exmail.qq.com"; // 腾讯企业邮箱SMTP服务器地址
        String port = "465"; // SMTP端口，默认是465（SSL）
        String subject = "PMS"; // 邮件主题
        String messageText = sdf.format(date) + "       PMS-报表推送"; // 邮件正文

        // 创建Properties对象并设置SMTP服务器属性
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtsmtpp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // 启用SSL
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // 创建Session实例
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 创建MimeMessage实例
            MimeMessage message = new MimeMessage(session);

            // 设置发件人、收件人、主题和正文
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(messageText);

            // 创建Multipart用于存放文本和附件
            Multipart multipart = new MimeMultipart();

            // 添加文本部分
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(messageText);
            multipart.addBodyPart(textPart);

            // 添加附件部分
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File(path + "/PMS-" + "20240822112128" + ".xlsx"); // 附件文件路径
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());
            multipart.addBodyPart(attachmentPart);
            // 设置整个消息体
            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);
            System.out.println("pms邮件发送成功");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
