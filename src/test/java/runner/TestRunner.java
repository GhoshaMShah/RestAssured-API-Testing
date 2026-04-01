package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    // ── Feature files
    features = "src/test/resources/features",

    // ── Step definitions
    glue = "stepdefs",

    // ── Reports
    plugin = {
        "pretty",
        "html:target/cucumber-reports/report.html",
        "json:target/cucumber.json"
        //"org.testng.reporters.XMLReporter"
    },

    // ── Clean console output
    monochrome = true,

    // ── Tag filter (override from CLI or testng.xml)
    tags = "not @ignore"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
