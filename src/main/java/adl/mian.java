package adl;

import java.io.File;
import java.sql.Timestamp;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class mian {

	public static String reportFolder = System.getProperty("user.dir") + "\\Repo\\Report";
	//// generate time stamp for unique path and name
	public static String reportPath = "";
	public static ExtentHtmlReporter htmlReporter = null;
	public static ExtentReports extentReport = null;

	public static ExtentTest parentNode;

	@BeforeSuite(alwaysRun = true)
	public void createReport(ITestContext context) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String folder = Long.toString(timestamp.getTime());
		//// get report path
		reportPath = reportFolder + "\\" + folder;
		File file = new File(reportPath);
		//// check if directory exist
		if (!file.exists()) {
			file.mkdir();
		}

		//// screenshot folder to be created in current runing directory
		//// generate time stamp for unique path and name
		htmlReporter = new ExtentHtmlReporter(reportPath + "\\" + "ADl.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		// Theme for report
		htmlReporter.config().setTheme(Theme.DARK);
		extentReport = new ExtentReports();
		extentReport.config();
		// Attach reporter to operate extent reports
		extentReport.attachReporter(htmlReporter);
		extentReport.flush();
	}

	@BeforeClass(alwaysRun = true)
	public void createParent(ITestContext context) {
		parentNode = extentReport
				.createTest(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1));
	}

	@AfterMethod(alwaysRun = true)
	public void appendNode(ITestResult result, ITestContext context) {
		String methodName = result.getName();
		ExtentTest node = parentNode.createNode(methodName);
		if (result.getStatus() == ITestResult.FAILURE) {
			node.log(Status.FAIL, "Test Data failed is ");
		}
		//// in case of skip
		else if (result.getStatus() == ITestResult.SKIP) {
			node.log(Status.SKIP, "Test Data Skip is ");

		} //// in case of pass
		else {
			node.log(Status.PASS, "Test Data Passed is ");
		}
		extentReport.flush();
	}

}
