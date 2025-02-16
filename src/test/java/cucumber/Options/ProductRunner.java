package cucumber.Options;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/java/features/productValidation.feature", glue = { "stepDefinations" }, plugin = {
		"pretty", "json:target/jsonReports/cucumber-report.json" }, monochrome = true)
public class ProductRunner {
//,dryRun = true:  It is used to check all step defination file are matching with feature file before actually run the code
}
