import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"target/classes/features"},
        plugin = {"json:target/cucumber-results/output.json"},
        glue = {"org.restassured"},
        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunner.class);

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass(
            alwaysRun = true
    )
    @Override
    public void setUpClass(ITestContext context) {
        try {
            super.setUpClass(context);
        } catch (Throwable e) {
            LOGGER.error("Class setup failed with the following error\n", e);
            throw e;
        }
    }

    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        try {
            super.runScenario(pickleWrapper, featureWrapper);
        } catch (Throwable e) {
            LOGGER.error("Scenario failed with the following error\n", e);
            throw e;
        }
    }
}