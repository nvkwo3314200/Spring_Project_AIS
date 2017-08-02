'use strict';

/**
 * @ngdoc directive
 * @name psspAdminApp.directive:sidebar
 * @description # sidebar
 * 
 * 
 * userRole:supplier ,approver,systemAdmin,
 */

angular.module('psspAdminApp').directive('sidebar', [ '$location', function() {
	return {
		templateUrl : 'scripts/directives/sidebar/sidebar.html',
		restrict : 'E',
		replace : true,
		controller : function($scope, localStorageService, userService) {
			userService.getCurrentUser().then(function(data) {
				$scope.userName = data.userName;
				if(data.shop != null) {
					$scope.shopName = data.shop.shopName;
				}
				if(data.mall != null) {
					$scope.mallName = data.mall.name;
				}
				document.getElementById('sidebarMenu').innerHTML = data.currentMenu;
			});
			
//			var userName = localStorageService.get("userName");
//			$scope.userName = userName;

			// Create Menu
//			var sidebarMenuCode = localStorageService.get("currentMenu");
//			document.getElementById('sidebarMenu').innerHTML = sidebarMenuCode;
//			if(userName!=null && sidebarMenuCode==null){
//				window.location.reload();
//			}
		}
	};
}]);
