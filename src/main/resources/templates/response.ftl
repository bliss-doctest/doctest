<div class="box">
	<span>Response</span>
	<ul>
		<li>ResponseCode: ${responseCode}</li>
		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<li>
				<div>payload:</div>
				<pre>${payload.expected}</pre>
			</li>
		</#if>
    </ul>
</div>