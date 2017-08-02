'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ForgetCtrl
 * @description
 * # ForgetCtrl
 * Controller of the psspAdminApp
 */
    angular.module('psspAdminApp')
    .controller('PasswordCtrl', function ($scope,$http, $state, alertService) {
        
        $scope.changePassword = function () {
        	$http({method:'POST', params:{newPassword:$scope.newPassword,repeatPassword:$scope.repeatPassword}, url:"./changePassword/change"}).
            success(function(data) {
                if (data['errorType'] == "success") {
                	$scope.newPassword='';
                	$scope.repeatPassword='';
                    alertService.cleanAlert();
                    alertService.add(data["errorType"], data["errorMessage"]);
                   
                } else {
                	$scope.closeAlert = alertService.closeAlert; 
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
            });
        	
            
        };
        
       

    })

