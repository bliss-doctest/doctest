<div class="box">
	<span>		
		<#if isAnUploadRequest>
			Upload-Request
		<#else>
			Request
		</#if>
	</span>
	<ul>
		<li>HTTP:${http}</li>
		<li>URI:${uri}</li>
		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<pre>${payload.expected}</pre>
		</#if>
		<#if isAnUploadRequest>
		<li>FileName: ${fileName}</li>
		<li>FileBody: ${fileBody}</li>
		</#if>
    </ul>
</div>