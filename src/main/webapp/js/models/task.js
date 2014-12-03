define(['underscore', 'backbone'], function (_, Backbone) {
	
	var Model = Backbone.Model.extend({			

		complete: function () {
			this.sync(null, this, { 
				url: this.url() + '/complete', 
				type: 'post',
				success: _.bind(this.set, this, {completed: true})
			});
		}
	});
	
	return Model;
});