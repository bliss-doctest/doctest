CHANGES
=======

develop
-------

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
