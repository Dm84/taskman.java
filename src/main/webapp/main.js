requirejs(["app"], function(app) {	

	requirejs(["bootstrap"], function () {

		$(function () {
			app();	
		});


	});
		
});


requirejs.config({
	urlArgs: "bust=v2",
	paths: { 

		jquery: 'http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery',
		jquery_ui: 'js/jquery-ui',
//		backbone: 'http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone',
		handlebars: 'http://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars',
		backbone: 'js/backbone',
		underscore: 'http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.7.0/underscore',
		bootstrap: 'js/bootstrap',
		marionette: 'js/backbone.marionette',
		mustache: 'js/mustache'

	}
});