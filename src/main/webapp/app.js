define(	['jquery', 'backbone', 'marionette', 'mustache'], 
		function ($, Backbone, Marionette, Mustache)
{

	return function () {

//		Marionette.TemplateCache.prototype.loadTemplate = function (templateId) {
//		    // Semi-implement original function (removed error handling for berevity)
//		    var template = $(templateId).html();		    
//		    return template;
//		}
		
		Marionette.TemplateCache.prototype.compileTemplate = function (rawTemplate) {

		    // Mustache.parse will not return anything useful (returns an array)
		    // The render function from Marionette.Renderer.render expects a function
		    // so instead pass a partial of Mustache.render 
		    // with rawTemplate as the initial parameter.

		    // Additionally Mustache.compile no longer exists so we must use parse.
		    Mustache.parse(rawTemplate);
		    return _.partial(Mustache.render, rawTemplate);
		};
		
		var app = new Backbone.Marionette.Application();
 
		app.addRegions({taskListRegion: "#task-list"});
		
		
		app.TaskModel = Backbone.Model.extend({
			
			defaults: {
				dateFormatted: ''
			},
			
			id: '0',
			description: '',
			completed: false,
			deadline: 0,
			date_formatted: '',
			
			initialize: function () {
				console.log(this.date_formatted);
			},
			
			set: function (attributes, options) {
				
				if (attributes['deadline'] !== 'undefined') {
					this.formatDeadline(attributes['deadline']);
				}
				Backbone.Model.prototype.set.call(this, attributes, options);
			},			
			
			formatDeadline: function (deadline) {
				var date = new Date(deadline);				
				this.date_formatted = date.toString();
			}
		});
		
		app.TaskListModel = Backbone.Collection.extend({			
			model: app.TaskModel,
			url: '/artifact/endpoint/task/list',
			urlRoot: 'http://localhost:8080',
		});

		app.TaskItemView = Marionette.ItemView.extend({
			template: '#task-item-template'
		});
		
		app.TaskListView = Marionette.CollectionView.extend({
			childView: app.TaskItemView,			
		});		
		
		app.on("start", function () {
			      
			var taskList = new app.TaskListModel();
			
			taskList.fetch();
			
			var listView = new app.TaskListView({ collection: taskList });
			app.taskListRegion.show(listView);
		});
		
		app.start();

	}
});