package services;


import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mytaxi.model.Posts;
import org.mytaxi.model.Users;

import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.http.ContentType;

import hook.Initializer;
import reports.ReportHandler;

public class UserService {

	private Users users;
	private int counter=0;
	private int userid=0;
	ReportHandler reporthandler;
	
	public UserService(ReportHandler reporthandler) {
		 users = new Users();
		 this.reporthandler=reporthandler;
	}

	public List<Users> getUsersList() {
		List<Users> userslist = null;
		reporthandler.info("Fetching the list of users from response");
		try {
			userslist = Arrays.asList(
				given()
					.contentType(ContentType.JSON)
					.body(users)
			   .when()
					.get("/users")
		       .then()
					.statusCode(200)
			   .extract()
					.response()
					.getBody()
					.as(Users[].class)
					);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userslist;
	}
	
	public Integer getUserID(List<Users> userslist, String username) {
		try {
			for(Users user : userslist) {
				counter=counter+1;
				if(user.getUsername().equals(username)) {
					reporthandler.isValidated("User ID for username "+username+" is", user.getId(), 3);
					userid=user.getId();
					break;
				}
				else {}	//do nothing
			}
			if(userslist.size()==counter) reporthandler.fail("No username found in the response : "+ username);
			else {} //do nothing
			}catch(Exception e) {
				e.printStackTrace();
			}
		return userid;
	}
}
