package reports;

import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import hook.Initializer;

public class ReportHandler {

	private final static Logger log=LoggerFactory.getLogger(ReportHandler.class);
	public ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentTest node;
    
    public ReportHandler() {
    }
    
    public ExtentTest createTest(String testName) {
    	extent = new ExtentReports();
    	htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/reports/testReport.html");
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setReportName("FreeNow rest assured test");
        return test=extent.createTest(testName);
	}
    
    public ExtentTest createNode(String nodeName) {
    	return node=test.createNode(nodeName);
    }
	public void isValidated(String message, Object actual, Object expected) {
		log.info(message +" : "+ actual);
		if(actual.equals(expected)) {
			node.log(Status.PASS, MarkupHelper.createLabel(" PASSED :"+message +" : "+ actual, ExtentColor.GREEN));
		}
		assertTrue(actual.equals(expected));
	}
	
	public void fail(String message) {
		log.error(message);
		node.log(Status.FAIL, MarkupHelper.createLabel(" FAILED :"+message, ExtentColor.RED));
		assertTrue(false);
	}

	public void pass(String message) {
		log.info(message);
		node.log(Status.PASS, MarkupHelper.createLabel(" PASSED :"+message, ExtentColor.GREEN));
		assertTrue(true);
	}
	
	public void info(String message) {
		log.info(message);
		node.log(Status.INFO, MarkupHelper.createLabel(" INFO :"+message, ExtentColor.GREEN));
	}
	
	public void endTest() {
		extent.flush();
		extent.close();
	}
}
