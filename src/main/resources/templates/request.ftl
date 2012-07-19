<div class="box">
	<span>Request</span>
	<ul>
		<li>HTTP:${http}</li>
		<li>URI:${uri}</li>
		<#if payload.expected != "{}">
			${payload.expected}
		</#if>
    </ul>
</div>