<div class="box">
	<span>Response</span>
	<ul>
		<li>ResponseCode: ${responseCode}</li>
		<#if payload??>
			<li>
				<div>payload:</div>
				<pre>${payload.expected}</pre>
			</li>
		</#if>
    </ul>
</div>