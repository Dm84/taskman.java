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

	<script type="text/text-plain" id="task-item-template">
		
			{{description}}
			<div class="task-date">
				{{dateFormat deadline}}
			</div>
		
	</script>


	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">			
				<div id="content">

					<div class="header">
						<div class="search-entry">
							<input type="text" class="entry"/>							
						</div>

						<div class="task-list-popup">
							<div class="task-list task-list_block_search">
								<div class="task-item_block_search task-item_block_search task-item_state_active">
									Редактирование <span class="item-match"> задач</span> из списка и поиска
									<div class="task-date_block_search task-date_block_search">
										21.03.2014 17:00
									</div>						
								</div>

								<div class="item-separator item-separator_block_search">
								</div>
								<div class="task-item_block_search task-item_block_search">
									Редактирование <span class="item-match"> задач</span> из списка и поиска
									<div class="task-date_block_search task-date_block_search">
										21.03.2014 17:00
									</div>						
								</div>

								<div class="item-separator item-separator_block_search">
								</div>
								<div class="task-item_block_search task-item_block_search">
									Редактирование <span class="item-match"> задач</span> из списка и поиска
									<div class="task-date_block_search task-date_block_search">
										21.03.2014 17:00
									</div>						
								</div>

								<div class="item-separator item-separator_block_search">
								</div>
								<div class="task-item_block_search task-item_block_search">
									Редактирование <span class="item-match"> задач</span> из списка и поиска
									<div class="task-date_block_search task-date_block_search">
										21.03.2014 17:00
									</div>						
								</div>
							</div>
						</div>
					</div>
					<div id="task-list" class="task-list">
						<div class="task-item">
								Редактирование задач из списка и поиска
							<div class="task-date">
								21.03.2014 17:00
							</div>
						</div>
					</div>


				</div>		
			</div>
		</div>
	</div>


</body>
</html>