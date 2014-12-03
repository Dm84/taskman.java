define(['jquery', 'js/views/task'], function ($, TaskItemView) {
	
	var MainTaskItemView = TaskItemView.extend({
		template: '#task-item-template',
		tagName: 'div class="task-item"',
		
		initialize: function () {
			this.listenTo(this.model, "change", this.render);
		},			
		
		onRender: function () {
			TaskItemView.prototype.onRender.call(this);
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

	return MainTaskItemView;
});