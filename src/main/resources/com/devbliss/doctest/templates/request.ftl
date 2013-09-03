<#-- 
  Copyright 2013, devbliss GmbH
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
  in compliance with the License. You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software distributed under the License
  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  or implied. See the License for the specific language governing permissions and limitations under
  the License.
 #-->
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
		
		<#if headers?has_content || cookies?has_content>
			<li>Headers:
				<#if headers?has_content>
					<ol>
						<#list headers?keys as header>
						    <li>
						        <label>${header} = </label>
						        <div>${headers[header]}</div>
						    </li>
						</#list>
					</ol>
				</#if>
				<#if cookies?has_content>
					
					<ol>
					 <li><b>Cookies:</b></li>
						<#list cookies?keys as cookie>
						    <li>
						        <label>${cookie} = </label>
						        <div>${cookies[cookie]}</div></li>
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
