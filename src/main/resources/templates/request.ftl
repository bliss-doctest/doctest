<div class="box">
	<span>Request</span>
	<ul>
		<li>HTTP:${http}</li>
		<li>URI:${uri}</li>
		<#if payload?? && payload.expected?has_content && payload.expected != "null" && payload.expected != "{}" >
			<pre>${payload.expected}</pre>
		</#if>
    </ul>
</div>