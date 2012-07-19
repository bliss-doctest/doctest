#doctest


idea based on python doctests, transfered into java world
unittests / integration tests / regressions tests


goals:
- run java tests
 * test rest api
- generate html/pdf/... documentation if tests succeeds
 * searchable documentation
 * write tutorials



based on junit, html client, ...

Test your rest-API and create an always-uptodate documentation for it

## Getting started

### Importing the doctest library
If you are using maven for your project, you just have to the add a new dependency section to your pom.xml

	<dependencies>
	
		...
	
		<dependency>
		
            <groupId>com.devbliss.doctest</groupId>
            
            <artifactId>doctest</artifactId>
            
            <version>LAST_VERSION</version>
            
            <scope>test</scope>
            
        </dependency>
        
	</dependencies>

If you are not using maven, you can get the last version jar file [here](http://search.maven.org/#search|ga|1|g%3A%22com.devbliss.doctest%22).

### Checking out the project
The doctest project is a maven project hosted on github.com/devbliss. You can easily start him by executing the following commands:

	git clone https://github.com/devbliss/doctest.git
	
	cd doctest
	
	mvn install 


## Documentation

The doctest project uses the mvn site plugin. To generate the documentation of the project, just call: 
	
	mvn site:site  
	
The documentation to generate is defined in src/site/site.xml.

## Use case

