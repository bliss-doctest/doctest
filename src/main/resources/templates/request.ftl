<div class="box">
	<span class="headline">		
		<#if isAnUploadRequest>
			Upload-Request
		<#else>
			Request
		</#if>
	</span>
	<ul>
		<li>HTTP: ${http}</li>
		<li>URI: ${uri}</li>
		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<pre>${payload.expected}</pre>
		</#if>
		<#if isAnUploadRequest>
		<li>FileName: ${fileName}</li>
		<li>FileSize: ${fileSize} bytes</li>
		<li>Mimetype: ${mimeType}</li>
		<#if showFileBody>
		<li>FileBody: 
			<button type="button" onclick="toggle(${id})" class="moreOrLess">See/hide file content</button>
            <pre id=${id} style="display:none">
                ${htmlEscapedFileBody}
            </pre>
        </li>
        </#if>
		</#if>
    </ul>
</div>