'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:CronJobCtrl
 * @description
 * # CronJobCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('CronJobCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$stateParams',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$stateParams) {
    	 $scope.cronJobList = null; 
    	  
    	 $http.get('./cronJob/getCronJobList').
         success(function (data) {
             $scope.cronJobList = data;
            // console.log($scope.cronJobList);
         });
    	 
    	 
         $scope.run= function (conJobId) {
             alertService.cleanAlert();
        	 $http({
                 method: 'POST',
                 params: {
                	 cronJobId: conJobId
                 },
                 url: "./cronJob/runJob"
   	          }).success(function (data) {
   	             //console.log(data);
   	              if (data != null && data != '') {
   	            	  $scope.closeAlert = alertService.closeAlert;
   	                  alertService.add(data["errorType"], data["errorMessage"]);
   	              }   	          
   	            
   	          });
         }
}]);
