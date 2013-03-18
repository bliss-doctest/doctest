# Wanna see what it does?
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
	
![Example HTML output of the delete-profile endpoint.](example-delete-profile.png)

The whole idea for this project is based on the [python doctests](http://docs.python.org/2/library/doctest.html). The intention was to transfer this idea into the java world with full compatibility to the junit library.

# Wanna see more?
Have a look at the generated [doctest](Doctests.hmtl) files.