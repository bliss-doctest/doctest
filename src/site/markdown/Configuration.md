# How to configure the doctests
The *doctest* library can be configured if you have to. Currently the *Configuration* object only allows to change the output directory of the generated html files.

These two examples show how the usage of the configuration can look like:

<pre>
import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.Configuration;

abstract class MyAbstractDocTest extends DocTest {

	public MyAbstractDocTest() {
		Configuration configuration = new Configuration();
		configuration.setHtmlOutputDirectory = "/my/project/documentation";

		super(configuration);
	}

	...

}
</pre>

<pre>
import com.devbliss.doctest.DocTest;
import com.devbliss.doctest.Configuration;

class MyDocTest extends DocTest {

	@Before
	public setUp() {
		Configuration configuration = getConfiguration();
		configuration.setHtmlOutputDirectory = "/my/project/special/test/documentation";
		
        // set up my tests
    }

	@Test
    ...

}
</pre>

