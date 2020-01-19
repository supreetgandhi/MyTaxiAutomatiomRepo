package services;


import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mytaxi.model.Posts;

import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.http.ContentType;

import hook.Initializer;
import reports.ReportHandler;

public class PostsService {

	private Posts posts;
	ReportHandler reporthandler;
	
	public PostsService(ReportHandler reporthandler) {
		posts = new Posts();
		this.reporthandler=reporthandler;
	}

	public List<Posts> getPostsList(int userid) {
		List<Posts> postslist = null;
		try {
			postslist = Arrays.asList(
				given()
					.contentType(ContentType.JSON)
					.body(posts)
			   .when()
					.get("/posts")
		       .then()
					.statusCode(200)
			   .extract()
					.response()
					.getBody()
					.as(Posts[].class)
					);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return postslist;
	}
	
	public List<Integer> getPostsIDList(List<Posts> postslist, int userid) {
		List<Integer> postid = new ArrayList<Integer>();
		try {
			for(Posts post : postslist) {
				if(post.getUserId()==userid) {
					postid.add(post.getId());
				}
			}
			if(postid.size()==0) reporthandler.fail("No posts found in the response for userid: "+ userid);
			else {reporthandler.pass("List of posts made by userid : "+ userid +" have posts id's : "+ postid.toString());}
			}catch(Exception e) {
				e.printStackTrace();
			}
		return postid;
	}
	
	public List<String> getPostsByUser(List<Posts> postslist, int userid) {
		List<String> posttitle = new ArrayList<String>();
		try {
			for(Posts post : postslist) {
				if(post.getUserId()==userid) {
					posttitle.add(post.getTitle());
				}
			}
			if(posttitle.size()==0) reporthandler.fail("No posts found in the posts service response for userid: "+ userid);
			else {
				for(String title : posttitle) {
					reporthandler.info("Posts made by user have titles : "+ title.toString());
				}
			}
		}catch(Exception e) {
				e.printStackTrace();
			}
		return posttitle;
	}
}
