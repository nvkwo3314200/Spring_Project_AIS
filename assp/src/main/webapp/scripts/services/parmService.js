'use strict';

angular.module('psspAdminApp').factory('parmService', function($http,$q,$location) {
	
	var parmService = {};
	
	parmService.mallId = null;
	
	parmService.segment = null;
	
	parmService.mallVoList = null;
	
	return parmService;
});
