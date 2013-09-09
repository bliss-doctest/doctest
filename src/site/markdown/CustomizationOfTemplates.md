# Bring your own layout - aka customization of templates

Doctests has a default design built into the html. Nice green validation boxes, black html protocols, green links and so on.
But what if you want to change that? Maybe you want to publish the results of the testacase with your own company 
logo and branding.

Well. You can do so easily.

The important folder here is <code>src/main/resources/com/devbliss/doctest</code> in the doctest project. The folder contains several files.
It contains styleHtml.css (the main styling), and a subfolder containing files used as raw templates for rendering. Check out https://github.com/devbliss/doctest/tree/master/src/main/resources/com/devbliss/doctest to get a feeling for the files.

Now let's suppose you want to change the color of links in your doctests. You can do so easily by copying htmlStyle.css into your
project (where you call "mvn test"). Always make sure you copy it into the resources directory with the correct path (what is 
<code>src/main/resources/com/devbliss/doctest/htmlStyle.css</code>.

Then open the file and change
<pre>
a {
	color:#70a629;
	font-weight:400;
	text-decoration:none;
}
</pre>

to

<pre>
a {
	color:#FF9900;
	font-weight:400;
	text-decoration:none;
}
</pre>


and voila a new "mvn test" will generate html files with new shiny orange links. That's only the start of course. That way
you can customize anything. From the way the html output looks like to the styling. Happy customizing!
