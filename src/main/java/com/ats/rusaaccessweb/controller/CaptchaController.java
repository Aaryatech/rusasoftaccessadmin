package com.ats.rusaaccessweb.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("captcha")
@Scope("session")
public class CaptchaController {

	/*
	 * <!-- https://mvnrepository.com/artifact/com.liferay/nl.captcha.simplecaptcha
	 * --> <dependency> <groupId>com.liferay</groupId>
	 * <artifactId>nl.captcha.simplecaptcha</artifactId> <version>1.1.1</version>
	 * </dependency>
	 */

	@RequestMapping(method = RequestMethod.GET)
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.err.println("In captcha controller");
		HttpSession session = request.getSession();

		response.setContentType("image/jpg");

		try {

			int iTotalChars = 6;
			int iHeight = 40;
			int iWidth = 150;
			Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
			Random randChars = new Random();
			String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, iTotalChars);
			BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();
			int iCircle = 15;
			for (int i = 0; i < iCircle; i++) {
				g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
			}
			Color myWhite = new Color(255, 255, 255);
			g2dImage.setFont(fntStyle1);
			for (int i = 0; i < iTotalChars; i++) {
				// g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255),
				// randChars.nextInt(255)));
				g2dImage.setColor(myWhite);
				if (i % 2 == 0) {
					g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
				} else {
					g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
				}
			}
			OutputStream osImage = response.getOutputStream();
			ImageIO.write(biImage, "jpeg", osImage);
			g2dImage.dispose();
			System.err.println("sImageCode= " +sImageCode);
			session.setAttribute("captcha_security", sImageCode);

			/*
			 * List<java.awt.Font> textFonts = Arrays.asList( new Font("Arial", Font.BOLD,
			 * 40), new Font("Courier", Font.BOLD, 40)); Captcha captcha = new
			 * Captcha.Builder(250, 90).addText() .addBackground(new
			 * FlatColorBackgroundProducer(Color.WHITE)) .addText(new
			 * DefaultTextProducer(2), new DefaultWordRenderer(Color.BLACK, textFonts))
			 * .build();
			 * 
			 * 
			 * HttpSession session = request.getSession();
			 * CaptchaServletUtil.writeImage(response, captcha.getImage());
			 * session.setAttribute("captcha_security", captcha);
			 */
			System.err.println("All Done");
		} catch (Exception e) {
			System.err.println("In captcha " + e.getMessage());
			e.printStackTrace();
		}
	}

}
