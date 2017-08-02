'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
	.controller('ShopIpCtrl',
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
				function($scope, $state, $http, $interval, alertService, localStorageService,
						$stateParams,$confirm, ngDialog, $q, T) {
			
			//init show type
			$scope.showBasicInfo = false;
			$scope.showShopIp = true;
			$scope.showShopUser = false;
			$scope.showShopCategory = false;
			$scope.showShopContract = false;
			$scope.shopId = null;
			$scope.shopName = $stateParams.shopName;
			$scope.isEdit = true;
			$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
			
			$scope.loginTypes = [ {
				code : 'I',
				description : T.T('system_shop_allow')
			}, {
				code : "E",
				description : T.T('system_shop_deny')
			} ]
			
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
			
			$scope.vm = this;
			$scope.vm.rowCollection = [];
	        $scope.vm.data = [];
	        
	        $scope.openAddIp = function(type) {
				var isopen = true;
				if(type == "EDIT") {
					isopen = $scope.editShopIps();
				} else {
					$scope.loginIpRestEdit = {};
					$scope.loginIpRestEdit.shopId = $scope.shopId;
					$scope.loginIpRestEdit.exIncludeInd = "";
					$scope.loginIpRestEdit.ipStart1 = "";
					$scope.loginIpRestEdit.ipStart2 = "";
					$scope.loginIpRestEdit.ipStart3 = "";
					$scope.loginIpRestEdit.ipStart4 = "";
					$scope.loginIpRestEdit.ipEnd1 = "";
					$scope.loginIpRestEdit.ipEnd2 = "";
					$scope.loginIpRestEdit.ipEnd3 = "";
					$scope.loginIpRestEdit.ipEnd4 = "";
				}
				if(isopen) {
					$scope.dialogErrorMessage = null;
					$scope.hasError = false;
					$scope.ableAdd = false;
					alertService.cleanAlert();
					ngDialog.open({
						className : 'ngdialog-theme-default',
						plain : false,
						width : '70%',
						showClose : true,
						closeByDocument : false,
						closeByEscape : true,
						appendTo : false,
						template : 'views/pages/sysShopIpAdd.html',
						scope : $scope
					});
				}
			}
	        
	        $scope.initShopIpList = function() {
				//初始化ip信息
	        	var shopid = $stateParams.shopId;
				$scope.loginIpRestEdit.shopId =$scope.loginIpRest.shopId = $scope.shopId = shopid;
				$scope.promise = $http({
					method : 'GET',
					params : {shopId : shopid,
							  exIncludeInd : $scope.loginIpRest.exIncludeInd},
					url : "./shopIp/shopIpViewList"
				}).success(function(data) {
					$scope.vm.rowCollection = data;
	                $scope.vm.data = [].concat(data);
				});
			}
			
			
			$scope.saveEditIp = function (obj) {
				if($scope.checkIp()) {
					$http.post('./shopIp/saveShopIp', $scope.loginIpRestEdit).
			     		success(function (data) {
			     	if (data['errorType'] == "success") {
			           
			             alertService.add(data["errorType"],"save success");	 
						
					 }else{
		     	       	  $scope.ableAdd=false;  
		                       alertService.add(data["errorType"],data["errorMessage"]);
		                 
		     	          }
			     		  $scope.vm.selected = [];
			     	      $scope.initShopIpList();
			     	      obj.closeThisDialog(0);  
					}); 
				} else {
					$scope.hasError=true;
				}
			}
			
			$scope.deleteShopIps = function () {

        		 if( $scope.vm.selected.length < 1){
            		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
                 }
	    		 var shopIpIds = "";
	             for (var i = 0; i < $scope.vm.selected.length; i++) {
	            	 shopIpIds = shopIpIds+$scope.vm.selected[i]["id"] + ","; 	
	              }
	             var popupMsg = T.T('confirm_to_delete_selected_value');
                $confirm({text: popupMsg, title: T.T('button_delete'), ok: T.T('Yes'), cancel: T.T('No')})
                    .then(function () {
                        $http({
                            method: 'POST',
                            params: {
                            	shopIpIds:shopIpIds
                            },
                            url: "./shopIp/deleteShopIp"
              	          }).success(function (data) {
              	        	$scope.closeAlert = alertService.closeAlert;
              	        		//console.log(data);
              	              if (data != null && data != '') {
              	                  if (data['errorType'] != "success") {
              	                      alertService.add(data["errorType"], data["errorMessage"]);
              	                  } else {
              	                      alertService.add("success", "delete successful");
              	                	  $scope.initShopIpList();
              	                  }
              	              }   
              	    	})

   	          });
        	
			}
			
			$scope.editShopIps = function () {
				if( $scope.vm.selected.length != 1){
					$scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return false;
				}
				
				$scope.loginIpRestEdit = $scope.vm.selected[0];
				//console.log($scope.loginIpRestEdit);
				return true;
			}
			
			$scope.page = 100;
			// Declare the array for the selected items
			$scope.vm.selected = [];

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
			}
			
			$scope.initShopIpList();
			
			$scope.checkIp = function () {
				if($scope.loginIpRestEdit.ipStart1 == null || 
					$scope.loginIpRestEdit.ipStart2 == null || 
					$scope.loginIpRestEdit.ipStart3 == null || 
					$scope.loginIpRestEdit.ipStart4 == null || 
					$scope.loginIpRestEdit.ipEnd1 == null || 
					$scope.loginIpRestEdit.ipEnd2 == null || 
					$scope.loginIpRestEdit.ipEnd3 == null || 
					$scope.loginIpRestEdit.ipEnd4 == null ) {
					$scope.dialogErrorMessage = T.T('shop_ip_is_null');
					return false;
				} else if ($scope.loginIpRestEdit.ipStart1.toString() == "" ||
						$scope.loginIpRestEdit.ipStart2.toString() == "" ||
						$scope.loginIpRestEdit.ipStart3.toString() == "" ||
						$scope.loginIpRestEdit.ipStart4.toString() == "" ||
						$scope.loginIpRestEdit.ipEnd1.toString() == "" ||
						$scope.loginIpRestEdit.ipEnd2.toString() == "" ||
						$scope.loginIpRestEdit.ipEnd3.toString() == "" ||
						$scope.loginIpRestEdit.ipEnd4.toString() == "" ) {
					$scope.dialogErrorMessage = T.T('shop_ip_is_null');
					return false;
				}
				
				var startIp = $scope.addLength($scope.loginIpRestEdit.ipStart1)
							+$scope.addLength($scope.loginIpRestEdit.ipStart2)
							+$scope.addLength($scope.loginIpRestEdit.ipStart3)
							+$scope.addLength($scope.loginIpRestEdit.ipStart4);
				
				var endIp = $scope.addLength($scope.loginIpRestEdit.ipEnd1)
							+$scope.addLength($scope.loginIpRestEdit.ipEnd2)
							+$scope.addLength($scope.loginIpRestEdit.ipEnd3)
							+$scope.addLength($scope.loginIpRestEdit.ipEnd4);
				if(endIp - startIp >0) {
					return true;
				} else {
					$scope.dialogErrorMessage = T.T('end_ip_less_than_start_ip');
					return false;
				}
			}
			
			$scope.addLength = function (obj) {
				var ip = obj.toString();
				if(ip.length == 1) return "00"+ip;
				if(ip.length == 2) return "0"+ip;
				if(ip.length == 3) return ""+ip;
			}
		} ]);