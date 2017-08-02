'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:DeliveryDateCtrl
 * @description
 * # DeliveryDateCtrl
 * Controller of the psspAdminApp
 */
    angular.module('psspAdminApp')
    .controller('DeliveryDateCtrl', function ($scope,$http, $state,localStorageService, alertService,$location) {

    	$scope.deliveryDateModel={};
    	$scope.minDeliverDate={};
    	$scope.maxDeliverDate={};
    	
    	$http({method:'GET',url:"./deliveryDate/getDeliveryDate"}).
        success(function(data) {
        	$scope.deliveryDateModel = data.deliveryDate;
        	$scope.minDeliverDate = data.minDeliverDate;
        	$scope.maxDeliverDate = data.maxDeliverDate;
        });
    	
    	$http({method:'GET',url:"./deliveryDate/getDeliveryDateDefault"}).
    	success(function(data) {
//    		$scope.deliveryDateModel = data.deliveryDate;
    		$scope.selectedMinDeliverDate = data.minDeliverDateDefault;
    		$scope.selectedMaxDeliverDate = data.maxDeliverDateDefault;
    	});
    	
        $scope.saveDeliveryDate = function () {
        	  alertService.cleanAlert();
        	$http({method:'POST', params:{
        		minDeliverDate:$scope.selectedMinDeliverDate,maxDeliverDate:$scope.selectedMaxDeliverDate}, url:"./deliveryDate/saveDeliveryDate"}).
            success(function(data) {

                if (data['errorType'] == "success") {
                	alertService.cleanAlert();
                	//console.log(data);
                	$scope.deliveryDateModel=data.returnData;
                    $scope.closeAlert = alertService.closeAlert;
                    alertService.add(data["errorType"], "Save successfully.");
                } else {
                	alertService.cleanAlert();
                	$scope.closeAlert = alertService.closeAlert; 
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
            });
        };
    })

