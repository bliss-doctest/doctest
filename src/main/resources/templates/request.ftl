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
		
		<#if headers?has_content>
			<li>Headers:
				<ol>
				<#list headers?keys as header>
				    <li>${header} = ${headers[header]}</li>
				</#list>
				</ol>
				<#if cookies?has_content>
					
					<ol>
					 <li><b>Cookies:</b></li>
						<#list cookies?keys as cookie>
						    <li>${cookie} = ${cookies[cookie]}</li>
						</#list>
					</ol>
				</#if>
			</li>
		</#if>
		
		<#if payload?? && payload.expected?has_content && payload.expected != "" && payload.expected != "null" && payload.expected != "{}" >
			<li>
				<div>payload:</div>
				<pre>${payload.expected}</pre>
			</li>
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
