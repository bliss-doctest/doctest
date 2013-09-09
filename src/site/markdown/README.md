# Test and document your java application REST Api in one single file
Writing tests and documentation is one of the most important aspects of modern software development. At the same time this is the part with the fewest amount of time to work on during a project. This *doctest* library is the best way to merge the world of documenting and testing your REST Api of your java application powered by the [junit](http://junit.org/) library. 

## Why?
 * It saves your time, because happens in one test file.
 * It saves your time, because changes in your code appear immediatly in your documentation.
 * You can review your documentation at the same time you review your code, because all happens in your repository and not in a dedicated software instance like a wiki.
 * Your documentation proves its correctness by itself, because the new documentation will only be generated if all tests passed.
 * The output of your documentation can be what you like, just add a renderer that fits your needs. Currently it will render an HTML output.

## Want to see an example?
The following example shows the integration test of the delete-profile REST endpoint. The test method is seperated into 4 blocks introduction / setup, show the existing data by using the get-profile endpoint, call the delete-profile endpoint and finally show that the profile is gone by calling again the get-profile endpoint.

<pre>
class ProfileDocTest extends com.devbliss.doctest.DocTest {
	
	...
	
	/**
 	 * Write a test annotated method like you would do for a common junit test
 	 */
	@Test
	public void testDeleteProfile() {
        /*
         * Introduce the delete-profile endpoint to the reader of your documentation.
         * Create a default profile to setup your test. This will be invisible for the reader of your documentation.
         */
        sayNextSection("Delete a profile");
        say("A user with admin rights can delete a profile.");
        createDefaultProfile(); // internal convinience method to handle test profiles

        /*
         * Show the profile of interest to the reader of your documentation.  
         */
        say("We want to delete the profile with the id \"" + profile.id + "\".");
        final ApiResponse response1 = makeGetRequest(buildUri("api/v1/profile/" + profile.id));
        assertEquals(HttpStatus.SC_OK, response1.httpStatus);

        /*
         * Do the request of your interest and test everything you think is important.
         */
        say("A request to the delete-profile endpoint with the correct rigths and "
            + "an existing profile id will end up in a response with the status code " + HttpStatus.SC_NO_CONTENT);
        final ApiResponse response2 = makeDeleteRequest(buildUri("api/v1/profile/" + profile.id));
        assertEquals(HttpStatus.SC_NO_CONTENT, response2.httpStatus);

        /*
         * Show how the response of the GET endpoint changed after the DELETE request was successful.
         */
        say("After the deleting of a profile was successful the request to get the profile with the id \"" 
            + profile.id + "\" will end up in a response with the status code " + HttpStatus.SC_NOT_FOUND);
        final ApiResponse response3 = makeGetRequest(buildUri("api/v1/profile/" + profile.id));
        assertEqualsAndSay(HttpStatus.SC_NOT_FOUND, response3.httpStatus, "The profile is not found since it has been deleted.");
    }

    ...
}
</pre>

From the single test case above you'll get a result HTML that will look like this:
	
![Example HTML output of the delete-profile endpoint.](https://raw.github.com/devbliss/doctest/master/src/site/resources/example-delete-profile.png)

The whole idea for this project is based on the [python doctests](http://docs.python.org/2/library/doctest.html). The intention was to transfer this idea into the java world with full compatibility to the junit library.

## Getting started
Go to the latest version at [maven central](http://search.maven.org/#search|ga|1|g%3A%22com.devbliss.doctest%22) choose the build tool of you choice and add doctest as a dependency to your project or download the jar and include it manually. E.g if you are using maven just add the following dependency to your project pom.xml:

    <dependency>
        <groupId>com.devbliss.doctest</groupId>
        <artifactId>doctest</artifactId>
        <version>0.6.3</version>
    </dependency>

Once the doctest library is included to your project you can start writing *doctests* by extending *com.devbliss.doctest.DocTest*

Please make sure to read the changelog src/site/markdown/CHANGES.md to get information about new features and possibly incompatibilities to older versions.
