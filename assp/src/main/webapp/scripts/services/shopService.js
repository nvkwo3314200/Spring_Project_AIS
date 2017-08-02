'use strict';

angular.module('psspAdminApp').factory('shopService', function($http,$q) {
	var shopService = {};

	shopService.getCurrentShopById = function(shopId) {
		if(shopId != undefined) {
			var deferred = $q.defer();
				$http({
					method : 'POST',
					params : {shopId : shopId},
					url : "./shop/shopDetail"
				}).success(function(data) {
					//console.log(data);
					deferred.resolve(data);
				});
			return deferred.promise;
		} else {
			return {};
		}
	};
	
	
	shopService.getShopInterfaceById = function(shopId) {
		if(shopId != undefined) {
			var deferred = $q.defer();
				$http({
					method : 'POST',
					params : {shopId : shopId},
					url : "./shop/shopInterface"
				}).success(function(data) {
					deferred.resolve(data);
				});
			return deferred.promise;
		} else {
			return {};
		}
	}

	return shopService;
});
