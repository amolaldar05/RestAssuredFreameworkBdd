package cucumber.Options;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features/placeValidation.feature",
    glue = {"stepDefinations"}, // Corrected glue path
    plugin = {"pretty", "json:target/jsonReports/cucumber-report.json"},
    tags = "@DeletePlace",
    monochrome = true
)
public class GooglePlaceRunner {
    // Runner for scenarios tagged with @DeletePlace
}
