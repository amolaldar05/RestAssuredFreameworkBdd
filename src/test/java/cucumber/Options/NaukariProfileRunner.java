package cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

//@RunWith(Cucumber.class)
//@CucumberOptions(features = "src/test/java/features/naukariGulfProfileUpdate.feature", glue = "stepDefinations", plugin = {
//		"pretty", "html:target/cucumber-reports.html" }, monochrome = true)
//public class NaukariProfileRunner {
//
//}

@CucumberOptions(
	    features = {"src/test/java/features/naukariGulfProfileUpdate.feature"},
	    glue = {"stepDefinations"},
	    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"},
	    monochrome = true
	)
	public class NaukariProfileRunner extends AbstractTestNGCucumberTests {
	//dryRun=true
	// @DataProvider(parallel = true): When we want parallel execution of the scenarios

	    @Override
	    @DataProvider(parallel = true)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
	}
