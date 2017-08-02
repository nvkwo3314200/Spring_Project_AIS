'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ForgetCtrl
 * @description
 * # ForgetCtrl
 * Controller of the psspAdminApp
 */
    angular.module('psspAdminApp')
    .controller('ForgetCtrl', function ($scope,$http, $state, alertService,$location) {
    	$scope.token='';
        $scope.send = function () {
        	$http({method:'POST', params:{  email:$scope.email,language:window.localStorage.lang}, url:"./forget/sendmail"}).
            success(function(data) {
                if (data['errorType'] == "success") {
                    alertService.cleanAlert();
                    alert(data["errorMessage"]);
                    $state.go('login');
                    
                } else {
                	$scope.closeAlert = alertService.closeAlert; 
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
            });
        	
            
        };
        
        $scope.login = function () {
        	$scope.closeAlert = alertService.cleanAlert();
        	$state.go('login');
        }
        

        //resetPassword
        if ($location.search().token) {  
            var token = $location.search().token; 
            }
            
        
        $scope.changePassword = function () {
      
            if ($location.search().token) {  
            	$scope.token= $location.search().token; 
                }
            
        	$http({method:'POST', params:{  token:$scope.token,newPassword:$scope.newPassword,repeatPassword:$scope.repeatPassword}, url:"./forget/changePassword"}).
            success(function(data) {
                if (data['errorType'] == "success") {
                    alertService.cleanAlert();
                    alert(data["errorMessage"]);
                    $state.go('login');
                    
                } else {
                	$scope.closeAlert = alertService.closeAlert; 
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
            });
        	
            
        };
        
       

    })

