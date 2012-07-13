package suite;

import integration.CompareObjectsIntegrationTest;
import integration.RequestsIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit to run all integration tests.
 * These are all tests which name ends with IntegrationTest
 * 
 * @author bmary
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({CompareObjectsIntegrationTest.class, RequestsIntegrationTest.class})
public class IntegrationTestSuite {

}
