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
//		backbone: 'http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone',
		backbone: 'js/backbone',
		underscore: 'http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.7.0/underscore',
		bootstrap: 'js/bootstrap',
		marionette: 'js/backbone.marionette',
		mustache: 'js/mustache'

	}
});