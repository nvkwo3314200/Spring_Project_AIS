'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
	.controller('SystemShopAddCtrl',
		[ 		'$scope',
				'$state',
				'$http',
				'$interval',
				'alertService',
				'localStorageService',
				'$stateParams',
				'$confirm',
				'ngDialog',
				'$q',
				'T',
				function($scope, $state, $http, $interval,
						alertService, localStorageService,
						$stateParams,$confirm, ngDialog, $q, T) {
					$scope.shopInfo = {};
					$scope.shopInfoOldStr;
					$scope.shopInfo.shopBasicInfo = {};
					$scope.shopInfo.shopBasicInfo.id = null;
					$scope.shopInfo.shopBasicInfo.shopName = null;
					$scope.shopInfo.shopBasicInfo.shopCode = null;
					$scope.shopInfo.shopBasicInfo.respPerson = null;
					$scope.shopInfo.shopBasicInfo.contactPerson = null;
					$scope.shopInfo.shopBasicInfo.contactEamil = null;
					$scope.shopInfo.shopBasicInfo.telNo = null;
					$scope.shopInfo.shopBasicInfo.fax = null;
					$scope.shopInfo.shopBasicInfo.address = null;
					$scope.shopInfo.shopBasicInfo.contractStartDate = null;
					$scope.shopInfo.shopBasicInfo.contractEndDate = null;
					$scope.shopInfo.shopBasicInfo.websiteName = null;
					$scope.shopInfo.shopBasicInfo.websiteDomain = null;
					$scope.shopInfo.shopBasicInfo.domainSuffix = null; //??
					$scope.shopInfo.shopBasicInfo.websiteEmailDomain = "asdfas@qq.com";
					$scope.shopInfo.shopBasicInfo.websiteContactEmail = "asdfas@qq.com";
					$scope.shopInfo.shopBasicInfo.useShopCartInd = "Y";
					$scope.shopInfo.shopBasicInfo.websiteStatus = 2;
					$scope.shopInfo.shopBasicInfo.websiteActiveInd = 'Y';
					$scope.shopInfo.shopBasicInfo.ownerWebSiteStatus = '';
					$scope.shopInfo.shopBasicInfo.ownerStatus = '';
					$scope.shopInfo.shopBasicInfo.websiteOnlineDate = "";
					$scope.shopInfo.shopBasicInfo.websiteInactiveDate = "";
					
					//Login Ip Restrict
					$scope.loginIpRest = {};
					$scope.loginIpRest.shopId = null;
					$scope.loginIpRest.exIncludeInd = null;
					$scope.loginIpRest.ipStart1 = null;
					$scope.loginIpRest.ipStart2 = null;
					$scope.loginIpRest.ipStart3 = null;
					$scope.loginIpRest.ipStart4 = null;
					$scope.loginIpRest.ipEnd1 = null;
					$scope.loginIpRest.ipEnd2 = null;
					$scope.loginIpRest.ipEnd3 = null;
					$scope.loginIpRest.ipEnd4 = null;
					
					//Login Ip Restrict
					$scope.loginIpRestEdit = {};
					
					//System User
					$scope.sysUser = {};
					$scope.sysUser.userCode = '';
					$scope.sysUser.userName = '';
					$scope.sysUser.password = '';
					$scope.sysUser.shopId = '';
					$scope.sysUser.activeInd = '';

					// function 变量
					$scope.checked;
					$scope.isEdit = false;

					// check page changed
					$scope.checkSave = function() {
						if (checkInfo()) {
							$scope.checked = true;
						} else {
							$scope.checked = false;
						}
					}
					var checkInfo = function() {
						if (JSON.stringify($scope.shopInfo.shopBasicInfo) == $scope.shopInfoOldStr) {
							return true;
						} else {
							if (confirm("save the information?")) {
								$scope.shopInfoOldStr = JSON
										.stringify($scope.shopInfo.shopBasicInfo);
								return true;
							} else {
								return false;
							}

						}
					}
					

					

					$scope.minDeliveryDayChange = function() {

						// $scope.userVo.supplierVo.minDeliveryDay2 =
						// $scope.supplierVo.minDeliveryDay;
						jQuery("#error_deliveryDay").html("");
						$scope.closeAlert = alertService.closeAlert;
						alertService.add("warning", T.T('update_all_the_Supplier_Direct_Deliver_products_info'));
					};

					$scope.maxDeliveryDayChange = function() {

						// $scope.supplierVo.maxDeliveryDay2 =
						// $scope.supplierVo.maxDeliveryDay;
						jQuery("#error_deliveryDay").html("");
						$scope.closeAlert = alertService.closeAlert;
						alertService
								.add("warning", T.T('update_all_the_Supplier_Direct_Deliver_products_info'));
					};

					$scope.warehouseDeliLeadTimeChange = function() {
						// $scope.supplierVo.warehouseDeliLeadTime2 =
						// $scope.supplierVo.warehouseDeliLeadTime;
						jQuery("#error_warehouseDeliLeadTime").html("");
						alertService.add("warning", T.T('update_all_the_Consignment_via_warehouse_products_info'));

					};

					function validateInteger(text) {
						var flag = true;
						var re = new RegExp("^[0-9]\\d*$");
						var result = re.test(text);
						if (!result) {
							flag = false;
						}
						return flag;
					}

					function judgeOneDecimal(text) {
						var flag = true;
						var re = new RegExp(
								"^[+]?([0-9]+(.[0-9]{1})?)$");
						var result = re.test(text);
						if (!result) {
							flag = false;
						}
						return flag;
					}

					$scope.category = {};
					$scope.category.categorySelect = [];
					$scope.categorytoHtml = [];
					$scope.category.categorytoSelect = null;
					$scope.category_from = function() {
						if ($scope.category.categorySelect != null) {
							for (var i = 0; i < $scope.category.categorySelect.length; i++) {
								for (var j = 0; j < $scope.categoryList.length; j++) {
									if ($scope.categoryList[j].lovCode == $scope.category.categorySelect[i].lovCode) {
										$scope.categoryList
												.splice(j, 1);
									}
								}
								;
								$scope.categorytoHtml
										.push($scope.category.categorySelect[i]);
								// console.log($scope.categorytoHtml);
								$scope.categorytoHtml
										.sort(function(a, b) {
											return a['lovCode'] > b['lovCode'] ? 1
													: -1
										});
							}
						} else {
							// Notifier.notifyWarning("至少选中一项");
							alert("please choose one record.");
						}
						$scope.category.categorySelect = [];
					};

					$scope.category_to = function() {
						if ($scope.category.categorytoSelect != null) {
							for (var i = 0; i < $scope.category.categorytoSelect.length; i++) {
								for (var j = 0; j < $scope.categorytoHtml.length; j++) {
									// console.log($scope.categorytoHtml[j].brandCode);
									if ($scope.categorytoHtml[j].lovCode == $scope.category.categorytoSelect[i].lovCode) {
										$scope.categorytoHtml.splice(j,
												1);
									}
								}
								;
								$scope.categoryList
										.push($scope.category.categorytoSelect[i]);
								// console.log($scope.categoryList);
								$scope.categoryList
										.sort(function(a, b) {
											return a['lovCode'] > b['lovCode'] ? 1
													: -1
										});
							}
							;
						} else {
							// Notifier.notifyWarning("至少选择一项");
							alert("please choose one record.");
						}
						$scope.typetoSelect = [];
					}
					// eCategory end

					/*
					 * function contains(str,arr){ for(var i=0;i<arr.length;i++){
					 * if(arr[i] == str){ return true; } } return false; }
					 */

					// dept class subclass
					$scope.department = {};
					$scope.department.unDeptSelect = [];
					$scope.department.deptSelect = null;
					$scope.deptToHtml = [];

					$scope.deptClass_from = function() {
						if ($scope.department.unDeptSelect != null) {
							for (var i = 0; i < $scope.department.unDeptSelect.length; i++) {
								for (var j = 0; j < $scope.department.deptList.length; j++) {
									if ($scope.department.deptList[j].description == $scope.department.unDeptSelect[i].description) {
										$scope.department.deptList
												.splice(j, 1);
									}
								}
								;
								$scope.deptToHtml
										.push($scope.department.unDeptSelect[i]);
								// console.log($scope.categorytoHtml);
								$scope.deptToHtml
										.sort(function(a, b) {
											return a['lovCode'] > b['lovCode'] ? 1
													: -1
										});
							}
						} else {
							// Notifier.notifyWarning("至少选中一项");
							alert("please choose one record.");
						}
						$scope.department.unDeptSelect = [];
					}

					$scope.deptClass_to = function() {
						if ($scope.department.deptSelect != null) {
							for (var i = 0; i < $scope.department.deptSelect.length; i++) {
								for (var j = 0; j < $scope.deptToHtml.length; j++) {
									if ($scope.deptToHtml[j].description == $scope.department.deptSelect[i].description) {
										$scope.deptToHtml.splice(j, 1);
									}
								}
								;
								$scope.department.deptList
										.push($scope.department.deptSelect[i]);
								// console.log($scope.categorytoHtml);
								$scope.department.deptList
										.sort(function(a, b) {
											return a['lovCode'] > b['lovCode'] ? 1
													: -1
										});
							}
						} else {
							// Notifier.notifyWarning("至少选中一项");
							alert("please choose one record.");
						}
						$scope.department.deptSelect = [];
					}

					$stateParams.userId = localStorageService.get("id");
					if ($stateParams.userId != null) {
						$scope.initValue();
						$scope.readDate = true;
						$scope.promise = $http({
							method : 'GET',
							params : {
								userId : $stateParams.userId
							},
							url : "./user/userDetail"
						}).success(
								function(data) {
									$scope.categoryList = null;
									$scope.categorytoHtml = null;

									$scope.department.deptList = null;
									$scope.deptToHtml = null;
									$scope.classList = null;
									$scope.classToHtml = null;
									$scope.subclassList = null;
									$scope.subClassToHtml = null;

									$scope.userVo = data;
									$scope.brandList = data['brandUnSelectModel'];
									$scope.brandshowtoHtml = data['brandselectModel'];
									$scope.categoryList = data['lovUnSelectVos'];

									$scope.category.categorytoSelect = null;
									$scope.categorytoHtml = data['lovSelectVos'];
									//console.log($scope.categorytoHtml);
									$scope.department.deptList = data['unDeptSelectModel'];

									$scope.deptToHtml = data['deptSelectModel'];

									$scope.classToHtml = data['classSelectModel'];

									$scope.subClassToHtml = data['subClassSelectModel'];

									if (typeof ($scope.userVo.activate) == "undefined") {
										$scope.activate2 = "Y";
									} else {
										$scope.activate2 = $scope.userVo.activate;
									}
									// getSupplierId($scope.userVo.supplierId);
									$scope.closeAlert = alertService.closeAlert;

									// var supplierId =
									// data['supplierId'];
									var returnData = data['supplierVo'];

									if (returnData != null) {

										// if
										// (returnData.deliveryFee
										// != null &&
										// returnData.deliveryFee
										// != '') {
										// //$scope.supplierVo.deliveryFee
										// =
										// returnData.deliveryFee;
										// } else {
										// //$scope.userVo.supplierVo.deliveryFee
										// = 0;
										// }
										//
										// if
										// (returnData.freeDeliveryThreshold
										// != null &&
										// returnData.freeDeliveryThreshold
										// != '') {
										// //$scope.supplierVo.freeDeliveryThreshold
										// =
										// returnData.freeDeliveryThreshold;
										// } else {
										// //$scope.userVo.supplierVo.freeDeliveryThreshold
										// = 0;
										// }

										// $scope.supplierVo.warehouseDeliLeadTime
										// =
										// returnData.warehouseDeliLeadTime;

										if (returnData.minDeliveryDay != null) {
											$scope.userVo.supplierVo.minDeliveryDay = returnData.minDeliveryDay
													.toString();

										}
										if (returnData.maxDeliveryDay != null) {
											$scope.userVo.supplierVo.maxDeliveryDay = returnData.maxDeliveryDay
													.toString();
										}
									}

									$scope.supplierId2 = $scope.userVo.supplierId;
									// }
								});
					} else {
						$scope.activate2 = "Y";

						$scope.userVo.userRole = "";
					}

					// Dialog

					
					$scope.openAddIp = function(type) {
						var isopen = true;
						if(type == "EDIT") {
							isopen = $scope.editShopIps();
						} else {
							$scope.loginIpRestEdit = {};
							$scope.loginIpRestEdit.shopId = $scope.shopInfo.shopBasicInfo.id;
							$scope.loginIpRestEdit.exIncludeInd = null;
							$scope.loginIpRestEdit.ipStart1 = null;
							$scope.loginIpRestEdit.ipStart2 = null;
							$scope.loginIpRestEdit.ipStart3 = null;
							$scope.loginIpRestEdit.ipStart4 = null;
							$scope.loginIpRestEdit.ipEnd1 = null;
							$scope.loginIpRestEdit.ipEnd2 = null;
							$scope.loginIpRestEdit.ipEnd3 = null;
							$scope.loginIpRestEdit.ipEnd4 = null;
						}
						if(isopen) {
							$scope.ableAdd = false;
							alertService.cleanAlert();
							ngDialog.open({
								className : 'ngdialog-theme-default',
								plain : false,
								width : '70%',
								showClose : true,
								closeByDocument : true,
								closeByEscape : true,
								appendTo : false,
								template : 'views/pages/sysShopIpAdd.html',
								scope : $scope
							});
						}
					}
					
					
					
// SHOP USER START
					
					
					
} ]);



