package tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.mytaxi.interfaces.IDataHolder;
import org.mytaxi.model.Comments;
import org.mytaxi.model.Posts;
import org.mytaxi.model.Users;

import com.aventstack.extentreports.ExtentTest;

import hook.Initializer;
import reports.ReportHandler;
import services.CommentsService;
import services.PostsService;
import services.UserService;
import java.util.List;

public class CommentsTest extends Initializer implements IDataHolder{
	
	private static int userid;
	private String username="Samant";
	private PostsService postservice;
	private UserService userservice;
	private CommentsService commentsservice;
	private static List<Integer> postsid;
	private List<Integer> postid = null;
	private List<String> posttitle=null;
	private List<String> commentsemail=null;
	static ExtentTest test;
	ReportHandler reporthandler=Initializer.reports();
	
	@Rule public TestName name= new TestName();
	
	
	/*************************************************************************************************
	 * Description            :          Test to get the user ID from USERS rest services for username
	 *                                   'Samantha' and get the userID for the same. The userID extracted
	 *                                   would be used in following test case to look for posts by the
	 *                                   user.
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Test
	public void verifyUserIDFromListTest() {
		List<Users> userslist;
		try {
			reporthandler.createTest("MyTaxi automation run report 1");
			reporthandler.createNode(name.getMethodName());
			reporthandler.info("Starting test case : "+name.getMethodName());
			userservice=new UserService(reporthandler);
			userslist = userservice.getUsersList();
			setUserID(userservice.getUserID(userslist, username));
		}catch(Exception e) {
			Initializer.reports().fail(e.getMessage());
		}
		userslist=null;
		userservice=null;
	}

	/*************************************************************************************************
	 * Description            :          Overridden method to get the user id fetched from rest services
	 * 									 this is used to pass the data from one test case to another
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Override
	public Integer getUserID() {
		return userid;
	}
	
	/*************************************************************************************************
	 * Description            :          Overridden method to set the user id from rest services
	 * 									 this is used to pass the data from one test case to another
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	/*
	 * @Override public void setUserID(int userid) { this.userid=userid; }
	 */
	
	/*************************************************************************************************
	 * Description            :          Test to get the posts list and then fetch the post id and post title
	 * 									 which would be passed on to the next test case where each email 
	 * 									 would be captured based on this post id and validated.
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Test
	public void verifyTotalPostsTest() {
		List<Posts> postslist;
		try {
			reporthandler.createNode(name.getMethodName());
			reporthandler.info("Starting test case : "+name.getMethodName());
			postservice=new PostsService(reporthandler);
			postslist = postservice.getPostsList(getUserID());
			postid=postservice.getPostsIDList(postslist, getUserID());
			posttitle=postservice.getPostsByUser(postslist, getUserID());
			setPostId(postid);
		}catch(Exception e) {
			Initializer.reports().fail(e.getMessage());
		}
		postslist=null;
		postservice=null;
	}

	/*************************************************************************************************
	 * Description            :          Overridden method to get the post id fetched from rest services
	 * 									 this is used to pass the data from one test case to another
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Override
	public List<Integer> getPostid() {
		return postsid;
	}

	/*************************************************************************************************
	 * Description            :          Overridden method to set the post id fetched from rest services
	 * 									 this is used to pass the data from one test case to another
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Override
	public void setPostId(List<Integer> postsid) {
		this.postsid=postsid;
	}
	
	/*************************************************************************************************
	 * Description            :          Test to validate the email in the comments section for the posts
	 * 									 made by the users and the email will be validated for 
	 * 									 1) A-Z characters allowed
										 2) a-z characters allowed
										 3) 0-9 numbers allowed
										 4) Additionally email may contain only dot(.), dash(-) and underscore(_)
										 5) Rest all characters are not allowed
	 * Created By             :          Supreet Singh Gandhi
	 * Created Date           :          18th January 2020
	 *************************************************************************************************/
	@Test
	public void verifyEmailSyntaxTest() {
		List<Comments> commentslist;
		try {
			reporthandler.createNode(name.getMethodName());
			reporthandler.info("Starting test case : "+name.getMethodName());
			commentsservice=new CommentsService(reporthandler);
			commentslist = commentsservice.getCommentsList();
			for(int postid : getPostid()) {
				commentsservice.getCommentsEmail(postid);
			}
		}catch(Exception e) {
			reporthandler.fail(e.getMessage());
		}
		reporthandler.endTest();
		commentslist=null;
		commentsservice=null;
	}
}
