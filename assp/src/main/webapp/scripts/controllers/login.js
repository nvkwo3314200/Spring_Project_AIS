'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the psspAdminApp
 */
    angular.module('psspAdminApp')
    .controller('LoginCtrl', function ($scope, AuthService, $state, localStorageService, alertService,$translate,$http) {
        $scope.showMsg = false;
        
        
        //User Story 56 -	Switch off functions for soft launch
        $scope.switchOff = 'N';
        $scope.lan =[];
        if($scope.switchOff == 'N'){
	        $scope.lan =[{
	            code: "en",
	            description: 'English'
	        },{
	            code: "zh_TW",
	            description: "\u7e41\u9ad4\u4e2d\u6587"
	        },{
	            code: "zh_CN",
	            description: "\u7B80\u4F53\u4E2D\u6587"
	        }]
    	}else{
    		$scope.lan =[{
	            code: "en",
	            description: 'English'
	        }]	
    	}
        
        $scope.language = "en";
        $scope.rememberMe = true;
        
        function updateLoginMsg(language){
    		$http({
    			method : 'POST',
    			params : {
    				lastLanguage:language
    			},
    			url : "./login/updateLoginMsg"
    		});
        }
        
        $scope.enter = function(evt){
        	if(evt.keyCode == 13) $scope.login();
        };
        
        $scope.login = function () {
        	$http({method:'POST',
            	params:{
            		username: $scope.userName,
            		password: $scope.password,
            		language: $scope.language,
            		rememberMe: $scope.rememberMe
            	},
            	url:"./j_spring_security_check"
            }).success(function(data) {
            	if (data['errorType'] == "success") {
	            	alertService.cleanAlert();
	            	//localStorageService.set("userRole", "SYSTEMADMIN");
	            	console.log(localStorageService);
	                updateLoginMsg($scope.language);
	                $state.go('main');
            	} else {
            		alertService.add(data["errorType"], data["errorMessage"]);
            	}
            });
        }
        
        /*
        function updateLoginMsg(language){
        	
        	var postData = {lastLanguage:language};
        	
    		$http({
    			method : 'POST',
    			/*
    			params : {
    				lastLanguage:language
    			},
    			
    			data:$.param(postData), 
  			  	headers:{ 'Content-Type': 'application/x-www-form-urlencoded' } ,  			
    			url : "./login/updateLoginMsg"
    		})
            .success(function (data) {

            });
    		
        }
        $scope.login = function () {
            AuthService.login($scope.userId,$scope.password,$scope.language,$scope.rememberMe).then(
                function (res) {
                    var data = res.data;
                    //console.log(data);
                    //alert(window.localStorage.lang);
                    if (data['errorType'] == "success") {
                    	localStorageService.set("id", data['returnData']['id']);
                        localStorageService.set("userId", data['returnData']['userId']);
                        localStorageService.set("supplierId", data['returnData']['supplierId']);
                        localStorageService.set("userName", data['returnData']['userName']);
                        localStorageService.set("authToken", data['returnData']['authToken']);
                        localStorageService.set("userRole", data['returnData']['userRole']);
                        localStorageService.set("email", data['returnData']['email']);
                        localStorageService.set("contactNo", data['returnData']['contactNo']);
                        window.localStorage.lang = $scope.language;
                        alertService.cleanAlert();
                        updateLoginMsg($scope.language);
                        
                        //User Story 56 -	Switch off functions for soft launch
                        //localStorageService.set("switchOff", $scope.switchOff);
                        $state.go('main');
                    } else {
                    	$scope.closeAlert = alertService.closeAlert; 
                        alertService.add(data["errorType"], data["errorMessage"]);
                    }
                });
        };
        */
        
        
        
        $scope.send = function () {
        	alertService.cleanAlert();
        	$state.go('send');
        }
       
        if(typeof(window.localStorage.lang)!='undefined'){
        $scope.language=window.localStorage.lang;
       }
        $scope.switching = function(lang){
            $translate.use(lang.language);
            window.localStorage.lang = lang.language;
        };
        
      
        
    })
    .factory('AuthService', function ($http) {
        var authService = {};
        authService.login = function (userId,password,language,rememberMe) {
        	var postData = {username:userId, password:password,language:language,rememberMe:rememberMe};
            return $http({
            			  method:'POST', 
            			  data:$.param(postData), 
            			  url:"./j_spring_security_check",
            			  headers:{ 'Content-Type': 'application/x-www-form-urlencoded' } 
            			}).
                success(function(data) {
                    return data;
                });
        };
        return authService;
    });
