<div class="menu">
	List of doctest files:
	<ul>
		<#list files as file>
			<li>
				<a href="${file.href}">${file.name}</a>
			</li>
		</#list>
	</ul>
</div>