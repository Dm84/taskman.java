define(['backbone', 'js/models/task'], function (Backbone, TaskModel) {

	var Model = Backbone.Collection.extend({			
		model: TaskModel,			
		urlRoot: location.host,
		
		initialize: function (attributes, options) {
			
			console.log('ok', options);
			this.url = options.url;
		},		
		comparator: function (task) {
			return task.get('deadline');
		}
	});
	
	return Model;
});
