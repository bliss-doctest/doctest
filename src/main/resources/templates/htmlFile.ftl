<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"> 
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