define(	['jquery', 'backbone', 'marionette', 'handlebars', 'jquery_ui'], 
		function ($, Backbone, Marionette, Handlebars)
{
	return function () {		
		Marionette.TemplateCache.prototype.compileTemplate = function (rawTemplate) {
		    return Handlebars.compile(rawTemplate);
		};
		
		var app = new Backbone.Marionette.Application();
		
		app.endpointUrl = '/artifact/endpoint/tasks'; 
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
		
		app.TaskModel = Backbone.Model.extend({
			
			complete: function () {
				this.sync(null, this, { url: this.url() + '/complete', type: 'post' });
			}
		});		
		
		app.TaskListModel = Backbone.Collection.extend({			
			url: app.endpointUrl,
//			initialize: function (models, options) {				
//				if (typeof options['url'] !== undefined) 
//					this.url = options['url'];
//			},
			
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

				if (this.model.get('completed') === true) {
					$dateEl.addClass('task-date_status_completed');
				} else {
					if (overdue > 0) {
						$dateEl.addClass('task-date_status_overdue');										
					} else {
						var dayPeriodMs = 24 * 60 * 1000;
						if (-overdue < dayPeriodMs) {
							$dateEl.addClass('task-date_status_todo');
						} else {
							$dateEl.addClass('task-date_status_future');
						}
					}
				}
			}
		});
		
		app.MainTaskItemView = app.TaskItemView.extend({
			template: '#task-item-template',
			tagName: 'div class="task-item"',
			onRender: function () {
				app.TaskItemView.prototype.onRender.call(this);
				this.$el.attr('id', 'task-' + this.model.id);				
				
				if (this.model.get('completed')) {
					 this.$el.find('.task-completed-icon').addClass(
							 'task-completed-icon_status_true');
				}
			},
			
			events: {
				'click .task-completed-icon': function () {
					this.model.complete();					
				}
			}
		});
		
		app.SearchTaskItemView = app.TaskItemView.extend({
			template: '#search-task-template',
			tagName: 'div class="task-item task-item_block_search"',
			hasSeparator: false,
			
			initialize: function (options) {
				this.hasSeparator = options.hasSeparator;				
			},			
			events: {
				"click": function () {
					$('html, body').animate({
				        scrollTop: $("#task-" + this.model.id).offset().top
				    });
				}
			},
			
			onRender: function () {
				app.TaskItemView.prototype.onRender.call(this);
				
				if (this.hasSeparator) {					
					this.$el.addClass('task-item_separator_true');
				}				
			}
		
		});

		
		app.TaskListView = Marionette.CollectionView.extend({
			childView: app.MainTaskItemView
		});
		
		app.HeaderView = Marionette.CompositeView.extend({
			template: '#header-template',
			childView: app.SearchTaskItemView,
			childViewContainer: ".task-list_block_search",

			initialize: function () {
				this.$el.find('.task-date').datepicker();
			},
			childViewOptions: function (model, index) {
				return { hasSeparator: index !== (this.collection.length - 1) };
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
				},
				"click .create-task-icon": function (e) {
					
				}
			}
		});
		
		app.on("start", function () {
			      
			var taskList = new app.TaskListModel();			
			taskList.fetch();
			
			var searchTaskList = new app.TaskListModel();
			
			var listView = new app.TaskListView({ collection: taskList });
			var headerView = new app.HeaderView({ collection: searchTaskList});
			
			app.tasksRegion.show(listView);
			app.headerRegion.show(headerView);
		});
		
		app.start();

	}
});