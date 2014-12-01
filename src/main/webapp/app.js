define(	['jquery', 'backbone', 'marionette', 'handlebars', 'jquery_ui'], 
		function ($, Backbone, Marionette, Handlebars)
{
	var main = function () {		
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
			return	pad(date.getDate(), 2) + "." + pad(date.getMonth() + 1, 2) + "." + 
					date.getFullYear() + " " + 
					pad(date.getHours(), 2) + ":" + pad(date.getMinutes(), 2);
		});
		
		app.TaskModel = Backbone.Model.extend({			
			//url: app.endpointUrl,
			complete: function () {
				this.sync(null, this, { 
					url: this.url() + '/complete', 
					type: 'post',
					success: _.bind(this.set, this, {completed: true})
				});
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
						var dayPeriodMs = 24 * 60 * 60 * 1000;
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
			
			initialize: function () {
				this.listenTo(this.model, "change", this.render);
			},			
			
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
			childView: app.MainTaskItemView,
			initialize: function() {
		        this.listenTo(this.collection, 'sync', this.render);		        
			}			
		});
		
		app.HeaderView = Marionette.CompositeView.extend({
			template: '#header-template',
			childView: app.SearchTaskItemView,
			childViewContainer: ".task-list_block_search",
			
			initialize: function(options) {
				if (typeof options.mainCollection !== 'undefined') {
					this.mainCollection = options.mainCollection;
				}
			},

			onRender: function () {				
				this.$el.find('.task-create-inputs__date').datepicker({
					dateFormat: "dd.mm.yy 12:00"
				});
			},
			childViewOptions: function (model, index) {
				return { hasSeparator: index !== (this.collection.length - 1) };
			},
			
			ui: {
				entry: 'input.entry',
				search_popup: '.popup_task_search',
				add_task_button: '.create-task-icon',
				desc_input: '.task-create-inputs__desc',
				date_input: '.task-create-inputs__date',
				save_input: '.task-create-inputs__save'
			},
			
			events: {
				"keyup @ui.entry": function (e) {
					this.collection.fetch({
						url: this.collection.url + '?query=' + $(e.target).val()
					});					
				},
				"focus @ui.entry": function (e) {
					this.ui.search_popup.fadeIn();
				},
				"blur @ui.entry": function (e) {
					this.ui.search_popup.fadeOut();
				},
				"click @ui.add_task_button": function (e) {
					this.popup_toggle();
				},
				"focus @ui.desc_input, @ui.date_input": function (e) {
					this.ui.save_input.removeClass('btn-danger');					
				},
				"click @ui.completed_icon": function (e) {
					
				},				
				"click @ui.save_input": function (e) {					
					try {
						var dateVal = this.ui.save_input.val();
						
						var	dateAndTime = dateVal.split(' '),
							date = dateAndTime[0].split('.'), 
							time = dateAndTime[1].split(':');						
							day = parseInt(date[0], 10), 
							month = parseInt(date[1], 10) - 1, 
							year = parseInt(date[2], 10),
							hours = parseInt(time[0], 10), 
							minutes = parseInt(time[1], 10),
							deadline = new Date(year, month, day, hours, minutes, 0, 0);
						
						this.mainCollection.create({
							description: this.ui.desc_input.val(),
							deadline: deadline.getTime(),
							completed: false
						}, {
							wait: true, 
							error: _.bind(this.bad_create, this), 
							success: _.bind(this.popup_toggle, this)
						});																						
					} catch (exception) {
						this.bad_create();
					}
					
				}				
			},
			
			popup_toggle: function () {
				this.$el.find('.popup_task_create').toggle(500);
			},
			
			bad_create: function (model, response) {
				this.ui.save_input.addClass('btn-danger');
			}
		});
		
		app.on("start", function () {
			app.taskList = new app.TaskListModel();			
			app.taskList.fetch();
			
			app.searchTaskList = new app.TaskListModel();
			
			app.listView = new app.TaskListView({ collection: app.taskList });
			app.headerView = new app.HeaderView({ 
				collection: app.searchTaskList, 
				mainCollection: app.taskList
			});
			
			app.tasksRegion.show(app.listView);
			app.headerRegion.show(app.headerView);
		});
		
		app.start();
	};
	
	return main;
});