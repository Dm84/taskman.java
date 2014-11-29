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
		
		app.endpointUrl = '/artifact/endpoint/task'; 
		app.addRegions({tasksRegion: "#task-list", headerRegion: '#header'});
				
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
			
			initialize: function (models, options) {				
				if (typeof options['url'] !== undefined) 
					this.url = options['url'];
			},
			
			model: app.TaskModel,			
			urlRoot: 'http://localhost:8080',
		});

		app.TaskItemView = Marionette.ItemView.extend({
			onRender: function () {
				var now = new Date(), nowMs = now.getTime(), 
					deadlineMs = this.model.get('deadline'), 
					deadlineDate = new Date(deadlineMs),
					overdue = nowMs - deadlineMs;					
				
				var $dateEl = $('.task-date', this.$el);
				
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
		
		app.MainTaskItemView = app.TaskItemView.extend({
			template: '#task-item-template',
			tagName: 'div class="task-item"',
		});
		app.SearchTaskItemView = app.TaskItemView.extend({
			template: '#search-task-template',
			tagName: 'div class="task-item task-item_block_search"',
		});

		
		app.TaskListView = Marionette.CollectionView.extend({
			childView: app.MainTaskItemView
		});
		
		app.HeaderView = Marionette.CompositeView.extend({
			template: '#header-template',
			childView: app.SearchTaskItemView,
			childViewContainer: ".task-list_block_search",
			childEvents: { 
				render: function () {
					//this.$el.find("div:last").append('<div class="task-item task-item_block_search"></div>');
					
				}
			},			
			events: {
				"keyup input.entry": function (e) {
					this.collection.fetch({
						url: this.collection.url + '?query=' + $(e.target).val()
					});					
				},
				"focus input.entry": function (e) {
					this.$el.find('.task-list-popup').fadeIn();
				},
				"blur input.entry": function (e) {
					this.$el.find('.task-list-popup').fadeOut();
				}
			}
		});
		
		app.on("start", function () {
			      
			var taskList = new app.TaskListModel([], {url: app.endpointUrl + '/list'});			
			taskList.fetch();
			
			var searchTaskList = new app.TaskListModel([], {url: app.endpointUrl + '/search'});
			
			var listView = new app.TaskListView({ collection: taskList });
			var headerView = new app.HeaderView({ collection: searchTaskList});
			
			app.tasksRegion.show(listView);
			app.headerRegion.show(headerView);
		});
		
		app.start();

	}
});