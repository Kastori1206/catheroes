package com.web.blog.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public String send(String to) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			//이메일 인증
			int ran = new Random().nextInt(900000) + 100000;
			
			String authCode = String.valueOf(ran);
			
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
			helper.setSubject("회원가입 인증 코드 발급 안내 입니다.");
			helper.setText("귀하의 인증 코드는 " + authCode + "입니다.",true);
			helper.setFrom("ssafycatheroes@gmail.com");
			helper.setTo(to);
			
			javaMailSender.send(message);
			return authCode;
		}catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	 



}
