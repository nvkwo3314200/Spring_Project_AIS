'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProfileCtrl
 * @description
 * # ProfileCtrl
 * Controller of the psspAdminApp
 */
    angular.module('psspAdminApp')
    .controller('ProfileCtrl', function ($scope,$http, $state,localStorageService, alertService,$location) {

    	$scope.userId=true;
    	$scope.userName=true;
    	$scope.userModel={};
    	
    	$http({method:'GET',url:"./profile/getUser"}).
        success(function(data) {
         if (data['errorType'] == "success") {
        	 		$scope.userModel = data.returnData;
	                alertService.cleanAlert();
	      }else {
	           	  $scope.closeAlert = alertService.closeAlert; 
	              alertService.add(data["errorType"], data["errorMessage"]);
	      }
         
         });
    	
        $scope.editUser = function () {
        	//console.log($scope.userModel);
        	$http.post('./profile/editUser', $scope.userModel).
            success(function(data) {

                if (data['errorType'] == "success") {
                	//console.log(data);
                	$scope.userModel=data.returnData;
                    alertService.cleanAlert();
                    alertService.add(data["errorType"], "update successful");
                } else {
                	 
                	$scope.closeAlert = alertService.closeAlert; 
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
            });
        };
    })

