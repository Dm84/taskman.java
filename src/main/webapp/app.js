define(	['jquery', 'backbone', 'marionette', 'handlebars'], 
		function ($, Backbone, Marionette, Handlebars)
{

	return function () {

		Marionette.TemplateCache.prototype.compileTemplate = function (rawTemplate) {

		    // Mustache.parse will not return anything useful (returns an array)
		    // The render function from Marionette.Renderer.render expects a function
		    // so instead pass a partial of Mustache.render 
		    // with rawTemplate as the initial parameter.

		    // Additionally Mustache.compile no longer exists so we must use parse.		    
		    return Handlebars.compile(rawTemplate);
		};
		
		var app = new Backbone.Marionette.Application();
 
		app.addRegions({tasksRegion: "#task-list"});
		
		Handlebars.registerHelper('dateFormat', function(date) {
			
			function pad(n, width, z) {
				  z = z || '0';
				  n = n + '';
				  return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
			}
			
			var date = new Date(date);
			return	pad(date.getDay(), 2) + "." + pad(date.getMonth(), 2) + "." + 
					date.getFullYear() + " " + 
					pad(date.getHours(), 2) + ":" + pad(date.getMinutes(), 2);
		});
		
		app.TaskModel = Backbone.Model.extend({});
		
		app.TaskListModel = Backbone.Collection.extend({			
			model: app.TaskModel,
			url: '/artifact/endpoint/task/list',
			urlRoot: 'http://localhost:8080',
		});

		app.TaskItemView = Marionette.ItemView.extend({
			template: '#task-item-template',
			tagName: 'div class="task-item"',
			
			onRender: function () {
				var now = new Date(), nowMs = now.getTime(), 
					deadlineMs = this.model.get('deadline'), 
					deadlineDate = new Date(deadlineMs),
					overdue = nowMs - deadlineMs;					
				
				var $dateEl = $('.task-date', this.$el);
				console.log(this.$el.html());
				
				if (overdue > 0) {
					if (this.model.get('complete') === true) {
						$dateEl.addClass('task-date_status_completed_with_overdue');
					} else {						
						$dateEl.addClass('task-date_status_overdue');	
					}					
				} else
				{
					var dayPeriodMs = 24 * 60 * 1000;
					if (-overdue < dayPeriodMs) {
						$dateEl.addClass('task-date_status_todo');
					} else {
						$dateEl.addClass('task-date_status_future');
					}
				}
			}
		});
		
		app.TaskListView = Marionette.CollectionView.extend({
			childView: app.TaskItemView
		});		
		
		app.on("start", function () {
			      
			var taskList = new app.TaskListModel();			
			taskList.fetch();
			
			var listView = new app.TaskListView({ collection: taskList });
			app.tasksRegion.show(listView);
		});
		
		app.start();

	}
});