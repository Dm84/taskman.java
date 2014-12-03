define(['jquery', 'marionette'], function ($, Marionette) {
	
	var TaskItemView = Marionette.ItemView.extend({
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
	
	return TaskItemView;	
})