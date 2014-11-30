<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.css"/>		
		<link rel="stylesheet" type="text/css" media="screen" href="css/styles.css"/>
		<script data-main="main" src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.js"></script>
		<title>Taskman</title>
</head>
<body>

	<script type="text/x-handlerbars-template" id="task-item-template">
			<div class="task-completed-icon">
			</div>		
			{{description}}
			<div class="task-date">
				{{dateFormat deadline}}
			</div>		
	</script>
	
	<script type="text/x-handlerbars-template" id="header-template">
		<div class="create-task-icon"></div>
		<div class="search-entry">
			<input type="text" class="entry"/>							
		</div>
		<div class="popup popup-window_task_search" style="display: none">
			<div class="task-list task-list_block_search">
			</div>
		</div>
		<div class="popup popup_task_create" style="display: none">
			<textarea class="task-description-input"></textarea>			
			<input type="text" class="task-date"/>
		</div>
	</script>

	<script type="text/x-handlerbars-template" id="search-task-template">
			{{description}}
			<div class="task-date task-date_block_search">
				{{dateFormat deadline}}
			</div>	
	</script>
	
	<script type="text/x-handlebars-template" id="search-separator-template">
		<div class="task-item task-item_block_search">
		<div class="item-separator item-separator_block_search"></div>
	</script>

	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">			
				<div id="content">
					<div class="header" id="header">
					</div>					
					<div id="task-list" class="task-list">
					</div>
				</div>		
			</div>
		</div>
	</div>


</body>
</html>