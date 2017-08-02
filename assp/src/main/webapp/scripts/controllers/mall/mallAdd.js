'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:menuAddCtrl
 * @description # menuAddCtrl Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'MallAddCtrl',
				[
						'$scope',
						'$state',
						'$http',
						'$interval',
						'alertService',
						'localStorageService',
						'$stateParams',
						function($scope, $state, $http, $interval,
								alertService, localStorageService, $stateParams) {
							$scope.state=$stateParams.state;
							$scope.mallVo = {};
							$scope.mallVoCk = {};
							$scope.isSaveCk = false;
							$scope.codeUn = false;
							
							if($stateParams.id != undefined) {
								$http.post('./mall/mallDetail',$stateParams.id).success(
										function(data) {
											$scope.mallVo = data["returnData"];
											$scope.mallVo.falg = true;
										}
								);
							}
							
							$scope.save = function() {
								$scope.checkCode();
								$scope.isSaveCk = true;
								if($scope.isSaveCk && !$scope.codeUn) {
									$http.post('./mall/save',$scope.mallVo).success(
									function(data) {
										if (data['errorType'] == "success") {
											alertService.saveSuccess();
							        		$scope.cancel();
										} else if(data['errorType']!=null){
											alertService.add(data["errorType"],data["errorMessage"]);
										}
									});
								}
							};

							$scope.cancel = function() {
								
								 $state.go('main.mall');
								
							};
							
							$scope.autoCheck = function(mallVo, key){
								mallVo[key] = mallVo[key].replace(/\D/g,'');
						       	};
						     
						    $scope.checkCode = function(code){
						    	if(code != undefined) {
						    		$scope.mallVoCk.code = code;
						    		$http.post('./mall/check',$scope.mallVoCk).success(
						    			function(data) {
						    				$scope.codeUn = data["errorType"] != 'success'? false:true;
						    			}
						    		);
						    	} else {
						    		$scope.isSaveCk = false;
						    	}
						    }   	
						       	
						} ]);
