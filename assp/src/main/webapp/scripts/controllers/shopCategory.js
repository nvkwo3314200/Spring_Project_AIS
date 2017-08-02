'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ShopCategoryCtrl',
        ['$scope',
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
            function ($scope, $state, $http, $interval, alertService, localStorageService,
                      $stateParams, $confirm, ngDialog, $q, T) {


                $scope.showBasicInfo = false;
                $scope.showShopIp = false;
                $scope.showShopUser = false;
                $scope.showShopCategory = true;
                $scope.showShopContract = false;
                $scope.isEdit = true;
                $scope.shopId = $stateParams.shopId;
                $scope.shopName = $stateParams.shopName;

                // page 4
                $scope.supplierList = null;
                $scope.supplierId = null;
                $scope.readDate = false;
                $scope.readDateId = "";
                $scope.activate2 = "Y";
                $scope.brandList = null;
                // $scope.categoryList = null;
                $scope.deptClassList = null;
                $scope.subclassList = [];

                $scope.delay = 0;
                $scope.minDuration = 0;
                $scope.templateUrl = '';
                $scope.message = 'Please Wait...';
                $scope.backdrop = true;
                $scope.promise = null;


                $scope.category = {};
                $scope.category.categorySelect = [];
                $scope.categorytoHtml = [];
                $scope.category.categorytoSelect = [];
                
                
                $scope.cancel = function () {
                	$state.go('main.system_shop');
                };
                $scope.save = function () {
                    var categorys = [];
                    for (var i = 0; i<$scope.categorytoHtml.length;i++){
                        categorys.push($scope.categorytoHtml[i].lovCode);
//                        console.log("test"+$scope.categorytoHtml[i].lovCode);
//                        console.log("test"+$scope.categorytoHtml[i].lovValue);
//                        console.log(categorys);
                    }
                    $http({method:'POST', params:{categorys:categorys,suplierId:$scope.shopId}, url:"./supplier/categorys"}).
                    success(function(data) {
                        if (data['errorType'] == "success") {
                            alertService.cleanAlert();
                            alertService.add(data["errorType"], data["errorMessage"]);

                        } else {
                            $scope.closeAlert = alertService.closeAlert;
                            alertService.add(data["errorType"], data["errorMessage"]);
                        }
                    });
//                    for (var i = 0; i<$scope.categorytoHtml.length;i++){
//                        console.log("test"+$scope.categorytoHtml[i].lovCode);
//                        console.log("test"+$scope.categorytoHtml[i].lovValue);
//                    }


                };
                $scope.category_from = function () {
                    if ($scope.category.categorySelect != null) {
                        for (var i = 0; i < $scope.category.categorySelect.length; i++) {
                            for (var j = 0; j < $scope.categoryList.length; j++) {
                                if ($scope.categoryList[j].lovCode == $scope.category.categorySelect[i].lovCode) {
                                    $scope.categoryList
                                        .splice(j, 1);
                                }
                            }
                            $scope.categorytoHtml
                                .push($scope.category.categorySelect[i]);
                            console.log("html"+$scope.categorytoHtml);
                            $scope.categorytoHtml
                                .sort(function (a, b) {
                                    return a['lovCode'] > b['lovCode'] ? 1 : -1
                                });
                        }
                    } else {
                        // Notifier.notifyWarning("至少选中一项");
                       // alert("please choose one record.");
                    }
                    $scope.category.categorySelect = [];
                };



                $scope.category_to = function () {
                    if ($scope.category.categorytoSelect != null) {
                        for (var i = 0; i < $scope.category.categorytoSelect.length; i++) {
                            for (var j = 0; j < $scope.categorytoHtml.length; j++) {
                                // console.log($scope.categorytoHtml[j].brandCode);
                                if ($scope.categorytoHtml[j].lovCode == $scope.category.categorytoSelect[i].lovCode) {
                                    $scope.categorytoHtml.splice(j, 1);
                                }
                            }
                            $scope.categoryList.push($scope.category.categorytoSelect[i]);
                            // console.log($scope.categoryList);
                            $scope.categoryList
                                .sort(function (a, b) {
                                    return a['lovCode'] > b['lovCode'] ? 1 : -1
                                });
                        }
                    } else {
                        // Notifier.notifyWarning("至少选择一项");
                      //  alert("please choose one record.");
                    }
                    $scope.typetoSelect = [];
                };
                // eCategory end

                /*
                 * function contains(str,arr){ for(var i=0;i<arr.length;i++){
                 * if(arr[i] == str){ return true; } } return false; }
                 */
                $stateParams.userId = localStorageService.get("id");
                if ($stateParams.userId != null) {
                    console.log("11");
                    $scope.readDate = true;
                    $scope.promise = $http({
                        method: 'GET',
                        params: {
                            userId: $stateParams.userId,
                            supplierId:$stateParams.shopId
                        },
                        url: "./user/userDetail"
                    }).success(
                        function (data) {
                            $scope.categoryList = null;
                            $scope.categorytoHtml = null;



                            $scope.userVo = data;
                            /*$scope.brandList = data['brandUnSelectModel'];
                             $scope.brandshowtoHtml = data['brandselectModel'];*/
                            $scope.categoryList = data['lovUnSelectVos'];
                            $scope.category.categorytoSelect = null;
                            $scope.categorytoHtml = data['lovSelectVos'];


                            if (typeof ($scope.userVo.activate) == "undefined") {
                                $scope.activate2 = "Y";
                            } else {
                                $scope.activate2 = $scope.userVo.activate;
                            }
                            // getSupplierId($scope.userVo.supplierId);
                            $scope.closeAlert = alertService.closeAlert;


                            var returnData = data['supplierVo'];

                            if (returnData != null) {


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
            }]);