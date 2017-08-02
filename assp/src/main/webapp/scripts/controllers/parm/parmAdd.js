'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:menuAddCtrl
 * @description # menuAddCtrl Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'ParmAddCtrl',
				[
						'$scope',
						'$state',
						'$http',
						'$interval',
						'alertService',
						'localStorageService',
						'$stateParams',
						'parmService',
						function($scope, $state, $http, $interval,
								alertService, localStorageService, $stateParams, parmService) {
							$scope.state=$stateParams.state;
							$scope.parmVo = {};
							$scope.isSaveCk = false;
							
							if($stateParams.id != undefined) {
								$scope.isEdit = true;
								$http.post('./parm/detail',$stateParams.id).success(
										function(data) {
											$scope.parmVo = data["returnData"];
											$scope.parmVo.falg = true;
										}
								);
							}
							
							$scope.save = function() {
								//var flag = $scope.checkCode();
								$scope.isSaveCk = true;
								var flag = $scope.checkEmpty();
								if(flag) {
									$http.post('./parm/save',$scope.parmVo).success(
									function(data) {
										if (data['errorType'] == "success") {
											alertService.saveSuccess();
											$scope.setParmService();
							        		$scope.cancel();
										} else if(data['errorType']!=null){
											alertService.add(data["errorType"],data["errorMessage"]);
										}
									});
								}
							};
							
							$http.post('./mall/mallList',{}).success(
								function(data) {
									$scope.mallVoList = data["returnDataList"];
								}
							);

							$scope.cancel = function() {
								 $state.go('main.sys_parm');
							};
							
							$scope.setParmService = function () {
								parmService.segment = $scope.parmVo.segment;
								parmService.mallId = $scope.parmVo.mallId;
							}
							
							$scope.autoCheck = function(parmVo, key){
								if(parmVo[key] != undefined) {
									parmVo[key] = parmVo[key].replace(/\D/g,'');
								}
						    };
						     
						    $scope.checkEmpty = function() {
						    	if($scope.parmVo.segment == null) return false;
						    	if($scope.parmVo.code == null) return false;
						    	if($scope.parmVo.shortDesc == null)return false;
						    	if($scope.parmVo.mallId == null) return false;
						    	if($scope.parmVo.dispSeq == null) return false;
						    	return true;
						    }
						       	
						} ]);
