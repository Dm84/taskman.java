define(['marionette', 'js/views/task_main'], function (Marionette, MainTaskItemView) {

	var TaskListView = Marionette.CollectionView.extend({
			childView: MainTaskItemView,
			initialize: function() {
		        this.listenTo(this.collection, 'sync', this.render);		        
		}			
	});
	
	return TaskListView;
});