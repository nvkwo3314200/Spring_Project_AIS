'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ShopContractCtrl',
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
                $scope.showShopCategory = false;
                $scope.showShopContract = true;
                $scope.showEditContract = false;
                $scope.isEdit = true;
                $scope.showShopContractedit = false;
                $scope.shopId = $stateParams.shopId;
                $scope.shopName = $stateParams.shopName;

                $scope.model = {};
                $scope.model.cashFlow = "";
                $scope.model.materialFlow = "";

                $scope.dialogErrorMessage ;

                $scope.payMethods = {};
                $scope.contractPayMethods = {};
                $scope.deliverMethods = {};
                $scope.contractDeliverMethods = {};
                $scope.isAllChecked = false;

                $scope.payToSelect = [];
                $scope.deliverToSelect = [];

                $scope.vm = this;
                $scope.vm.rowCollection = [];
                $scope.vm.data = [];
                $scope.contract;

                $scope.page = 10;
                // Declare the array for the selected items
                $scope.vm.selected = [];
                $scope.vm.paySelected = [];
                $scope.vm.deliverSelected = [];

                // Function to get data for all selected items
                $scope.vm.selectAll = function (collection) {
                    // if there are no items in the 'selected' array,
                    // push all elements to 'selected'
                    if ($scope.vm.selected.length === 0) {
                        angular.forEach(collection, function (val) {
                            $scope.vm.selected.push(val);
                        });
                        // if there are items in the 'selected' array,
                        // add only those that ar not
                    } else if ($scope.vm.selected.length > 0 && $scope.vm.selected.length != $scope.vm.data.length) {
                        angular.forEach(collection, function (val) {
                            var found = $scope.vm.selected.indexOf(val);
                            if (found == -1) $scope.vm.selected.push(val);
                        });
                        // Otherwise, remove all items
                    } else {
                        $scope.vm.selected = [];
                    }

                };

                // Function to get data by selecting a single row
                $scope.vm.select = function (id) {
                    var found = $scope.vm.selected.indexOf(id);
                    if (found == -1) $scope.vm.selected.push(id);
                    else $scope.vm.selected.splice(found, 1);
                    //=====Sheldon=====
                    var tableTr = document.getElementById('tr'+id.id);
                    var tableInput = tableTr.getElementsByTagName('input')[0];
                    if(tableInput.checked==false &&  found == -1){
                        tableTr.className='ng-scope : st-selected';
                        tableInput.checked=true;
                    }
                    else if(tableInput.checked==true && found != -1){
                        tableTr.className='ng-scope : null';
                        tableInput.checked=false;
                    }
                };

                //金流选择
                $scope.vm.selectPayAll = function (collection) {
                    // if there are no items in the 'selected' array,
                    // push all elements to 'selected'
                    if ($scope.vm.paySelected.length === 0) {
                        angular.forEach(collection, function (val) {
                            $scope.vm.paySelected.push(val);
                        });
                        // if there are items in the 'selected' array,
                        // add only those that ar not
                    } else if ($scope.vm.paySelected.length > 0 && $scope.vm.paySelected.length != $scope.payMethods.length) {
                        angular.forEach(collection, function (val) {
                            var found = $scope.vm.paySelected.indexOf(val);
                            if (found == -1) $scope.vm.paySelected.push(val);
                        });
                        // Otherwise, remove all items
                    } else {
                        $scope.vm.paySelected = [];
                    }
                };
                // Function to get data by selecting a single row
                $scope.vm.selectPay = function (id) {
                    var found = $scope.vm.paySelected.indexOf(id);
                    if (found == -1) $scope.vm.paySelected.push(id);
                    else $scope.vm.paySelected.splice(found, 1);
                    //=====Sheldon=====
                    var tableTr = document.getElementById('payTr'+id.id);
                    var tableInput = tableTr.getElementsByTagName('input')[0];
                    if(tableInput.checked==false &&  found == -1){
                        tableTr.className='ng-scope : st-selected';
                        tableInput.checked=true;
                    }
                    else if(tableInput.checked==true && found != -1){
                        tableTr.className='ng-scope : null';
                        tableInput.checked=false;
                    }
                };

                // End of 金流选择
                //物流选择
                $scope.vm.selectDeliverAll = function (collection) {
                    // if there are no items in the 'selected' array,
                    // push all elements to 'selected'
                    if ( $scope.vm.deliverSelected.length === 0) {
                        angular.forEach(collection, function (val) {
                             $scope.vm.deliverSelected.push(val);
                        });
                        // if there are items in the 'selected' array,
                        // add only those that ar not
                    } else if ( $scope.vm.deliverSelected.length > 0 &&  $scope.vm.deliverSelected.length != $scope.deliverMethods.length) {
                        angular.forEach(collection, function (val) {
                            var found =  $scope.vm.deliverSelected.indexOf(val);
                            if (found == -1)  $scope.vm.deliverSelected.push(val);
                        });
                        // Otherwise, remove all items
                    } else {
                         $scope.vm.deliverSelected = [];
                    }

                };
                // Function to get data by selecting a single row
                $scope.vm.selectDeliver = function (id) {
                    var found =  $scope.vm.deliverSelected.indexOf(id);
                    if (found == -1)  $scope.vm.deliverSelected.push(id);
                    else  $scope.vm.deliverSelected.splice(found, 1);
                    //=====Sheldon=====
                    var tableTr = document.getElementById('deliverTr'+id.id);
                    var tableInput = tableTr.getElementsByTagName('input')[0];
                    if(tableInput.checked==false &&  found == -1){
                        tableTr.className='ng-scope : st-selected';
                        tableInput.checked=true;
                    }
                    else if(tableInput.checked==true && found != -1){
                        tableTr.className='ng-scope : null';
                        tableInput.checked=false;
                    }
                };

                // End of 物流选择
                $scope.openEditContract = function (type) {
                    alertService.cleanAlert();
                    $scope.hasError=false;
                    var selectRight = true;
                    if(type=="EDIT"){
                        $scope.isEditContract=true;
                        selectRight= $scope.checkSelectNum(true);
                        if(selectRight){
                            $http({
                                method: "GET",
                                params: {
                                    id: $scope.vm.selected[0].id
                                },
                                url: "./contract/toEdit"
                            }).then(function successCallback(response) {
                                $scope.contract = response.data["contract"];
                                $scope.contractPayMethods = response.data['contractPayMethods'];
                                $scope.contractDeliverMethods = response.data['contractDeliverMethods'];
                                angular.forEach($scope.contractPayMethods,function (item) {
                                    angular.forEach( $scope.payMethods,function (pay) {
                                        if(pay.id==item.payMethodId){
                                            $scope.vm.selectPay(pay);
                                        }
                                    })
                                });
                                angular.forEach($scope.contractDeliverMethods,function (item) {
                                    angular.forEach( $scope.deliverMethods,function (deliver) {
                                        if(deliver.id==item.deliverMethodId){
                                           $scope.vm.selectDeliver(deliver);
                                        }
                                    })
                                });
                            }, function errorCallback(response) {
                                $scope.initContractList();
                            });
                        }
                    } else {
                        $scope.isEditContract=false;
                        $scope.contract = {};
                        $scope.contract.ref = "";
                        $scope.contract.startDate = "";
                        $scope.contract.endDate = "";
                        $scope.contract.status = "A";
                        $http({
                            method: "GET",
                            params: {
                                id: -1
                            },
                            url: "./contract/toEdit"
                        }).then(function successCallback(response) {
                            $scope.payMethods = response.data['payMethods'];
                            $scope.deliverMethods = response.data['deliverMethods'];
                            angular.forEach($scope.contractPayMethods,function (item) {
                                angular.forEach( $scope.payMethods,function (pay) {
                                    if(pay.id==item.payMethodId){
                                        $scope.vm.paySelected.push(pay);
                                    }
                                })
                            });
                            angular.forEach($scope.contractDeliverMethods,function (item) {
                                angular.forEach( $scope.deliverMethods,function (deliver) {
                                    if(deliver.id==item.deliverMethodId){
                                        $scope.vm.deliverSelected.push(deliver);
                                    }
                                })
                            });



                        }, function errorCallback(response) {
                            $scope.initContractList();
                        });
                    }
                    if(selectRight){
                        $scope.showEditContract=true;
                    }
                };
                $scope.open1 = function () {
                    $scope.popup1.opened = true;
                };
                $scope.popup1 = {
                    opened: false
                };
                $scope.open2 = function () {
                    $scope.popup2.opened = true;
                };
                $scope.popup2 = {
                    opened: false
                };
                $scope.saveContract = function (obj) {
                    alertService.cleanAlert();
                    $scope.contract.supplierId = $scope.shopId;
                    $scope.contract.payList=[];
                    $scope.contract.deliverList=[];
                    angular.forEach($scope.vm.paySelected,function (item) {
                        var pay ={};
                        pay.payMethodId=item.id;
                        pay.contractId=$scope.contract.id;
                        $scope.contract.payList.push(pay);
                    });
                    angular.forEach($scope.vm.deliverSelected,function (item) {
                        var deliver ={};
                        deliver.deliverMethodId=item.id;
                        deliver.contractId=$scope.contract.id;
                        $scope.contract.deliverList.push(deliver);
                    });
                    $http.post(
                        "./contract/save",
                        $scope.contract
                    ).then(function successCallback(response) {
                        if(response.data.errorType=="success"&&response.data.errorMessage!="ref_equal"){
                            alertService.add("success", T.T('contract_save_success'));
                        }else if(response.data.errorMessage=="ref_equal"){
                            $scope.hasError=true;
                            $scope.dialogErrorMessage = T.T('contract_ref_exists');
                        }else if(response.data.errorMessage=="start_date_null"){
                            $scope.hasError=true;
                            $scope.dialogErrorMessage = T.T('contract_start_date_cannot_null');
                        }else if(response.data.errorMessage=="ref_null"){
                            $scope.hasError=true;
                            $scope.dialogErrorMessage = T.T('contract_ref_cannot_null');
                        }else if(response.data.errorMessage=="end_date_before_start_date"){
                            $scope.hasError=true;
                            $scope.dialogErrorMessage = T.T('contract_end_date_before_start_date');
                        }
                    }, function errorCallback(response) {
                        alertService.add("error", T.T('contract_save_fail'));
                    });
                };
                $scope.backToContract=function () {
                    alertService.cleanAlert();
                    $scope.showEditContract=false;
                    $scope.initContractList();
                };
                $scope.deleteContract=function () {
                    alertService.cleanAlert();
                    var selectRight = $scope.checkSelectNum(false);
                    var valueCount = 0;
                    if(selectRight){
                        var contracts = $scope.vm.selected;
                        angular.forEach(contracts,function (contract) {
                            valueCount += (contract.payList.length>0||contract.deliverList.length>0)?1:0;
                        });
                        if(valueCount==0){
                            var popupMsg = T.T('confirm_to_delete_selected_value');
                            $confirm({text: popupMsg, title: T.T('button_delete'), ok: T.T('Yes'), cancel: T.T('No')})
                                .then(function () {
                                    var ids = [];
                                    angular.forEach(contracts,function (contract) {
                                        ids.push(contract.id);
                                    });
                                    $http.post(
                                        "./contract/deleteContract",
                                        ids
                                    ).then(function successCallback(resopnse) {
                                            alertService.add("success", T.T('contract_delete_success'));
                                            $scope.initContractList();
                                        },
                                        function errorCallback(resopnse) {
                                            alertService.add(resopnse.data["errorType"], T.T('contract_delete_fail'));
                                            $scope.initContractList();
                                        });
                                });
                        }else {
                            alertService.add("error", T.T('contract_delete_has_value'));
                        }
                    }
                };
                $scope.showContract=function () {
                    alertService.cleanAlert();
                    console.log("show Contract")
                };
                $scope.initContractList = function () {
                    alertService.cleanAlert();
                    $http({
                        method: 'GET',
                        params: {
                            supplierId: $scope.shopId
                        },
                        url: "./contract/contractList"
                    }).success(function (data) {
                        $scope.vm.rowCollection = data;
                        $scope.vm.data = [].concat(data);
                        $scope.vm.selected = [];
                    });
                    $http({
                        method: "GET",
                        params: {
                            id: -1
                        },
                        url: "./contract/toEdit"
                    }).then(function successCallback(response) {
                        $scope.vm.paySelected=[];
                        $scope.vm.deliverSelected=[];
                        $scope.payMethods = response.data['payMethods'];
                        $scope.deliverMethods = response.data['deliverMethods'];
                        angular.forEach($scope.contractPayMethods,function (item) {
                            angular.forEach( $scope.payMethods,function (pay) {
                                if(pay.id==item.payMethodId){
                                    $scope.vm.paySelected.push(pay);
                                }
                            })
                        });
                        angular.forEach($scope.contractDeliverMethods,function (item) {
                            angular.forEach( $scope.deliverMethods,function (deliver) {
                                if(deliver.id==item.deliverMethodId){
                                    $scope.vm.deliverSelected.push(deliver);
                                }
                            })
                        });
                    }, function errorCallback(response) {
                        $scope.initContractList();
                    });
                };
                $scope.initContractList();
                $scope.checkSelectNum=function (ischeckOne) {
                    if(ischeckOne){
                        if( $scope.vm.selected.length < 1){
                            $scope.closeAlert = alertService.closeAlert;
                            alertService.add('danger', T.T('contract_list_select_none'));
                            return false;
                        } else if ( $scope.vm.selected.length > 1){
                            $scope.closeAlert = alertService.closeAlert;
                            alertService.add('danger', T.T('contract_list_select_multi'));
                            return false;
                        }else {
                            return true;
                        }
                    }else{
                        if( $scope.vm.selected.length < 1){
                            $scope.closeAlert = alertService.closeAlert;
                            alertService.add('danger', T.T('contract_list_select_none'));
                            return false;
                        }else {
                            return true;
                        }
                    }
                }
            }]);