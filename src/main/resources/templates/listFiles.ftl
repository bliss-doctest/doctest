<div class="menu">
	<ul>
		<#list files as file>
			<li>
				<a href="${file.href}">${file.name}</a>
			</li>
		</#list>
	</ul>
</div>