package hook;

import org.junit.Before;
import org.mytaxi.core.Context;
import com.jayway.restassured.RestAssured;
import reports.ReportHandler;

public abstract class Initializer{

	public static Context context=new Context();
	public static ReportHandler reporthandler;
    
	@Before
	public void init() {
		RestAssured.baseURI=context.getBaseURI();
	}
	
	public static ReportHandler reports() {
		reporthandler=new ReportHandler();
		return reporthandler;
	}
}
