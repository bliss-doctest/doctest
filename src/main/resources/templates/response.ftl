<div class="box">
	<span class="headline">Response</span>
	<ul>
		<li>ResponseCode: ${responseCode}</li>
		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<li>
				<div>payload:</div>
				<pre>${payload.expected}</pre>
			</li>
		</#if>
		
		<#if headers?has_content>
			<li>Headers</li>
			<#list headers?keys as header>
			    <li>${header} = ${headers[header]}</li>
			</#list>
		</#if>
    </ul>
</div>