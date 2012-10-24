<div class="box">
	<span class="headline">Response</span>
	<ul>
		<li>ResponseCode: ${responseCode}</li>

		<#if headers?has_content>
			<li>Headers
				<ol>
				<#list headers?keys as header>
				    <li>
				        <label>${header} = </label>
				        <div>${headers[header]}</diuv>
				    </li>
				</#list>
				</ol>
			</li>
		</#if>

		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<li>
				<div>payload:</div>
				<pre>${payload.expected}</pre>
			</li>
		</#if>
    </ul>
</div>