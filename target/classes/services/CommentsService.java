package services;


import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mytaxi.model.Comments;
import org.mytaxi.model.Posts;

import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.http.ContentType;

import hook.Initializer;
import reports.ReportHandler;

public class CommentsService {

	private Comments comments;
	List<Comments> commentslist = null;
	private int counter=0;
	static ExtentTest test;
	ReportHandler reporthandler;
	
	public CommentsService(ReportHandler reporthandler) {
		comments = new Comments();
		this.reporthandler=reporthandler;
	}

	public List<Comments> getCommentsList() {
		
		try {
			commentslist = Arrays.asList(
				given()
					.contentType(ContentType.JSON)
					.body(comments)
			   .when()
					.get("/comments")
		       .then()
					.statusCode(200)
			   .extract()
					.response()
					.getBody()
					.as(Comments[].class)
					);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return commentslist;
	}
	
	public String getCommentsEmail(int postid) {
		String emailid = null;
		reporthandler.info("email will be validated for \r\n" + 
				"								 1)A-Z characters allowed					\r\n" + 
				"2) a-z characters allowed\r\n" + 
				"3) 0-9 numbers allowed\r\n" + 
				"4) Additionally email may contain only dot(.), dash(-) and underscore(_)\r\n" + 
				"5) Rest all characters are not allowed");
		try {
			for(Comments comment : commentslist) {
				if(comment.getPostId()==postid) 
				{
					counter = counter+1;
					emailid=comment.getEmail();
					reporthandler.info("Email id captured is : "+emailid);
				    verifyEmail(emailid);
				}else {
					
				}
			}
			reporthandler.info(counter+" is the count of emails found for the user's posts id under comments section.");
		}catch(Exception e) {
				e.printStackTrace();
			}
		return emailid;
	}
	
	public void verifyEmail(String emailid) {
		String regex="^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(emailid);
	    reporthandler.pass(emailid +" : "+ matcher.matches());
	}
}
