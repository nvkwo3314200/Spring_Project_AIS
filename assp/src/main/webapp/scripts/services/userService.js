'use strict';

angular.module('psspAdminApp').factory('userService', function($http,$q) {
	var userService = {};

	userService.getCurrentUser = function() {
        var deferred = $q.defer();
        
		$http({method:'POST', url:"./login/currentUser"}).
        success(function(data) {
        	//console.log(data);
        	deferred.resolve(data);
        });
        
		//console.log('return promise');
        return deferred.promise;
		
	};

	return userService;
});
