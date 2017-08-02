'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:RoleManagerCtrl
 * @description
 * # RoleManagerCtrl
 * Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'UserManagerCtrl',
				function($scope, $state, $stateParams,cacheService, userService, $http, $interval,
						alertService, localStorageService, ngDialog, $location, T, $controller) {
					$controller("BaseCtrl", {$scope:$scope});
					
					$scope.UserInfoVo = {};
					
					$scope.userName = "";
					// $scope.userVo.activate = "Y";
					$scope.activate = "Y";
					/**
					 * id 
					 */
					$scope.page = 10;
					$scope.selected = [];
					$scope.tableData = [];
					$scope.searchUserInfoVo = cacheService.getCache();
					cacheService.setCache($scope.searchUserInfoVo);
					$scope.pageModel = $scope.searchUserInfoVo;
					$scope.shopVoList = [];
					//console.log($scope.pageModel);
					$scope.init = function() {
						$scope.getPowers();
						$scope.search();
						$scope.getAllShop();
						alertService.showMsg();
					};
					
					$scope.getAllShop = function () {
						$http.post("./shop/getAllShop").success(function (data) {
							$scope.shopVoList = data;
						});
					}

					$scope.search = function(flag) {
						$http({
							method : 'POST',
							data : $scope.searchUserInfoVo,
							url : "./userManager/search"
						}).success(function(data) {
							$scope.tableData = data;
							$scope.selected = [];
							if(flag||false) alertService.cleanAlert();
						});
					};

					$scope.addUser = function() {
						$state.go('main.userManager_add');
					};

					$scope.editUser = function() {
						if ($scope.selected.length != 1) {
							
							alertService.add('danger',
									T.T('select_records'));
							return;
						}
						var userId = $scope.selected[0]["id"];
						$state.go('main.userManager_add', {
							'userId' : userId
						});
					};

					$scope.edit2 = function(row) {
						var userId = row["id"];
						$state.go('main.userManager_add', {
							'userId' : userId
						});
					};

					// delete user
					$scope.deleteUser = function(isDelete) {
						if ($scope.selected.length < 1) {
							
							alertService.add('danger',
									T.T('select_records'));
							return;
						}
						if (!isDelete) {
							$('#confirmModal').modal({
								keyboard : false
							});
							return;
						}
						var userIds = "";
						for (var i = 0; i < $scope.selected.length; i++) {
							userIds = userIds + $scope.selected[i]["id"]
									+ ",";
						}
						$http({
							method : 'POST',
							params : {
								userIds : userIds
							},
							url : "./userManager/deleteUser"
						}).success(
							function(data) {
								if (data != null && data != '') {
									if (data['errorType'] != "success") {
										alertService.add(data["errorType"],data["errorMessage"]);
									} else {
										alertService.add("success",T.T('delete_success'));
										$scope.search();
									}
								}

							});
					};

					$scope.view = function() {
						var viewStatu = true;
						if ($scope.selected.length != 1) {
							
							alertService.add('danger',
									T.T('select_records'));
							return;
						}
						console.log($scope.selected[0]);
						var userId = $scope.selected[0]["id"];
						$state.go('main.userManager_add', {
							'userId' : userId,
							'viewStatu' : viewStatu
						});
					};

					$scope.reset = function() {
						$scope.searchUserInfoVo.userCode = null;
						$scope.searchUserInfoVo.userName = null;
						cacheService.clearCache();
						alertService.cleanAlert();
					};

				});
