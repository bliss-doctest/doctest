# Changelog

develop
-------
 - Removed reference to CloudBees build status

Version 0.9.0 29.09.2014
------------------------
 - Doctest HTML output directory configurable with system properties

Version 0.8.0 15.09.2014
------------------------
- string payload in requests is treated as text/plain now by the custom request factories
- using apitester 0.7.1 doing the same in the default request factories

Version 0.7.0 14.01.2014
------------------------
- additionalHeaders and patch httpMethod added. Only compatible with apiTest >0.7.0
- removed copyright headers from files that would be generated for users of doctest.
  (script.js, htmlStyle.css).
- Moved templates, styling and js to subfolder to enable theming of doctest html output.
- fixed a bug in IntegrationTest#verifyThatTheReportsAreCreated (the wrong file object was verified)
- Made protected methods in LogicDocTest public / also fixed testcases to reflect that change.
  This allows us to use the DocTestLibrary not only in test scenarios where we can use "extends DocTest", but
  also in scenarios, where we are already exending another testlibrary. In such scenarios we can now
  use DocTest as simple class level utitily class. 
  ATTENTION: When using this version of doctest, you will have to change the visibility of those methods to "public" wherever you override them in your LogicDocTest subclasses!
- Bump to site plugin version 3.3 (compatible with Maven 3.1)
- Added description how to customize doctest with own layout 

Version 0.6.4 07.05.2013
------------------------
- introduce doctest configuration 

Version 0.6.3 09.01.2013
------------------------
- parse and display a html body
- improve the isJsonValid method

Version 0.6.2 24.10.2012
------------------------
- show headers and cookies if the user decides it
- Configure the encoding of the surefire JVM to UTF-8

Version 0.6.1 19.10.2012
------------------------
- make the integration better by using a dummy test object for the request payload
- prettify the json payload in the response

Version 0.6 19.10.2012
------------------------
- add links to "maven site" generated content
- add headers to Request and Response object and render the headers if user decides it

Version 0.5.3 25.09.2012
------------------------
- do not show the body of an application/octet-stream file
- bound the doctest report in the html documentation generated with the site mojo 
- removed the asserts methods without explanation texts.
- added getIntroduction() function which can be overridden in the test to write an introduction for the report
- added abstract getFileName function to let the user choose the name of the html file
- only show the path and the query of the uri, the hostname and port are not important
- fixed escaping problem by rendering the fileBody of an uploaded image

Version 0.5.2 23.08.2012
------------------------
- fixed utf8-encoding problem
- refactored PostFactory problem to be more failsafe

Version 0.5.1: 11.08.2012
-------------------------
- reset the factory of the apiTest after having used it

Version 0.5: 11.08.2012
-------------------------
- added bound jquery and toggle the content of the file in the upload request template
- added upload request and update the request template to show the infos corresponding to the uploaded file

Version 0.4.7: 01.08.2012
-------------------------
- refactored htmlrenderer and htmlitems to use the same method for getting the template for all docitems
- added HtmlIndexFileRendererUnitTest
- added pretty printing
- says gets a <p> now
- added say with a variable parameter list to enable highlighted text, object and json posting. also fixed json rendering in HTML.

Version 0.4.6: 26.07.2012
-------------------------
- use apitester 0.5 and add some functions to test the cookies
- using freemarker for templates now
- fixed bug which messed up the files itemlists and added tests
- added template and css loading from the jar instead of the host projects filesystem

Version 0.4.1: 18.07.2012
-------------------------
- move the generated documentation to target/site
- add site plugin to generate the documentation
- create a fileHelper class and corresponding unit tests

Version 0.4: 16.07.2012
-------------------------
- improve the style of the html report
- separate the generation logic from the rendering logic. A ReportRenderer gets a list of DocItems and render it the way he wants.

Version 0.3: 12.07.2012
-------------------------
- added sayObject and sayPreformattedCode for DocTests
- added new assertJson methods which compare the json representations of objects
- refactor the Response object

Version 0.2.1: 12.07.2012
-------------------------
- implement assertTrueAndSay() and assertFalseAndSay() with optional message
- assertEqualsAndSay(...) takes Object as parameters and has optional message
- removed "important" from assert...AndSay() output
- create Response object to avoid consumers of this library having a dependency on the ApiTester library.
- create GUICE bindings for the request factories (request without redirect)

Version 0.2.0: 09.07.2012
-------------------------
 - implement assertTrue and assertFalse methods

Version 0.1: 09.07.2012
-----------------------
 - create html file for each test class
 - create an index.html file containing links to generated classes.
