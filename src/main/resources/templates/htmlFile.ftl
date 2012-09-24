<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">
<html>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
	<head>
		<style>${css}</style>
        <script type="text/javascript">${jsCode}</script>
        <title>DocTest for class ${name}</title>
    </head>
	<body>
		<div class="container">
			<div class="wrapper">			
            	<br/>
            	<a href="./index.html">back to index page</a><br/>
            	<div class="small">Doctest originally perfomed at: ${date}</div>
            	
            	<#if introduction?? && introduction != "">
            		<h3>Introduction:</h3>
            		<div>${introduction}</div>
            	</#if>
            	${items}
            </div>
        </div>
    <body>
</html>