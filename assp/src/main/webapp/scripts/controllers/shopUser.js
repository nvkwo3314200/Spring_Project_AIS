'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
	.controller('ShopUserCtrl',
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
			$scope.showShopIp = false;
			$scope.showShopUser = true;
			$scope.showShopCategory = false;
			$scope.showShopContract = false;
			$scope.shopId = $stateParams.shopId;
			$scope.shopName = $stateParams.shopName;
			$scope.isEdit = true;
			$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
			
			$scope.my_type = $stateParams.my_type;
			if($scope.my_type == "SHOPSETUP") {
				$scope.shopId = localStorageService.get("shopId");
			};
			
			//System User
			$scope.sysUserEdit = {};
			
			$scope.sysUser = {};
			$scope.sysUser.userCode = null;
			$scope.sysUser.userName = null;
			$scope.sysUser.password = null;
			$scope.sysUser.shopId = null;
			$scope.sysUser.roleType = null;
			$scope.sysUser.activeInd = 'N';
			
			$scope.vm = this;
			$scope.vm.rowCollection = [];
	        $scope.vm.data = [];
	        //
	        $scope.bufferRole = {};
		 	$scope.page = 10;
		    $scope.bufferRole.selected = [];
		    $scope.bufferRole.rowCollection = [];
		    $scope.bufferRole.data = [];
	        
	        $scope.openAddUser = function(type) {
	        	var isopen = true;
				if(type == "EDIT") {
					isopen = $scope.editUsers();
				} else {
					$scope.userInfoVo = {};
					$scope.userInfoVo.userActiveInd = "Y",
					$scope.userInfoVo.shopId = $scope.shopId;
					$scope.bufferRole.rowCollection = [];
					$scope.bufferRole.data = [];
					$scope.bufferRole.selected = [];
				}
	        	
				if(!isopen) {
					return;
				}
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
					template : 'views/pages/power/userManagerAdd.html',
					scope : $scope,
					controller : function ($scope) {
						$scope.bufferRole.selectAll = function (collection) {
					         if ($scope.bufferRole.selected.length === 0) {
					             angular.forEach(collection, function (val) {
					                 $scope.bufferRole.selected.push(val);

					             });
					         } else if ($scope.bufferRole.selected.length > 0 && $scope.bufferRole.selected.length != $scope.bufferRole.data.length) {
					             angular.forEach(collection, function (val) {
					                 var found = $scope.bufferRole.selected.indexOf(val);
					                 if (found == -1) $scope.bufferRole.selected.push(val);
					             });
					         } else {
					             $scope.bufferRole.selected = [];
					         }
					     };
					     
					     $scope.bufferRole.select = function (id) {
					    	 console.log(id);
					         var found = $scope.bufferRole.selected.indexOf(id);
					         if (found == -1) $scope.bufferRole.selected.push(id);
					         else $scope.bufferRole.selected.splice(found, 1);
					         
					         //=====Add Style=====
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
						
						$scope.addUserRole = function() {
						   	  $scope.userInfoVo.bufferRoleModel = {};
						   	  $scope.userInfoVo.selectedRoleModel = {};
						   	  $scope.userInfoVo.selectedRoleModel.roleActive = "Y";
						   	  $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
						   	  $scope.userRoleDialog();
					      };
					      
					     $scope.editUserRole = function(){
					     	 $scope.userInfoVo.selectedRoleModel = {};
					     	 $scope.userInfoVo.bufferRoleModel = {};
					     	 $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
					    		 if($scope.bufferRole.selected.length != 1){
					        		 
					        		 alertService.add('danger', T.T('select_records'));
					    			 return;
					          }
							 $scope.userInfoVo.bufferRoleModel = angular.copy($scope.bufferRole.selected[0]);
							 $scope.userInfoVo.selectedRoleModel = angular.copy($scope.bufferRole.selected[0]);
							 $scope.userRoleDialog();
					     };
					     
					     $scope.editUserRole2 = function(row){
					     	 $scope.userInfoVo.selectedRoleModel = {};
					     	 $scope.userInfoVo.bufferRoleModel = {};
					     	 $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
					    		
							 $scope.userInfoVo.bufferRoleModel = angular.copy(row);
							 $scope.userInfoVo.selectedRoleModel = angular.copy(row);
							 $scope.userRoleDialog();
					     };
					     

					 	$scope.deleteUserRole = function(isDelete){
					 		 $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
					 		 if( $scope.bufferRole.selected.length < 1){
					     		 
					     		 alertService.add('danger', T.T('select_records'));
					 			 return;
					          }
					 		 if (!isDelete) {
							    $('#confirmModal').modal({keyboard: false});
							    return;
							 }
					 		 var roleCodes = "";
					         for (var i = 0; i < $scope.bufferRole.selected.length; i++) {
					        	 roleCodes = roleCodes + $scope.bufferRole.selected[i]["roleCode"] + ",";
					         }
					         $scope.userInfoVo.bufferRoleCodes = roleCodes;
					        // console.log($scope.userInfoVo.subRoleModelList);
					      	 $http.post('./userManager/deleteRolePop', $scope.userInfoVo)
					      		.success(function (data) {
					 	              if (data != null && data != '') {
					 	                  if (data['errorType'] != "success") {
					 	                      
					 	                      alertService.add(data["errorType"], data["errorMessage"]);
					 	                  } else {
					 	                	  $scope.bufferRole.rowCollection = data.returnData.subRoleModelList;
					  				          $scope.bufferRole.data = [].concat($scope.bufferRole.rowCollection);
					  		                  $scope.bufferRole.selected = [];
					  		                  
//					 	                      alertService.add("success", T.T('delete_success'));
					 	                  }
					 	              }   
					 	          });
					 	};
					 	
					 	$scope.userRoleAuditTrail = function(){
							 if($scope.bufferRole.selected.length != 1){
					    		 
					    		 alertService.add('danger', T.T('select_records'));
								 return;
					         }
							 $scope.auditTrailData = $scope.bufferRole.selected[0];
							 //console.log( $scope.auditTrailData);
							 ngDialog.open({
								 	closeByDocument : false,
						  		    className: 'ngdialog-theme-default custom-width-800',
						  		    template: 'views/pages/power/auditTrailDialog.html',
						  		    scope: $scope,
						  		    controller: ['$scope', 
												function($scope) {
					    				$scope.cancel = function (){
					    					$scope.closeThisDialog(0);
					    				};
						  		    }],
						  		    preCloseCallback: function(value) {
						  		    	  alertService.cleanAlert();
						  		    }
						  		});
						};
					     
					     
					     
					 	$scope.userRoleDialog = function (){
					  		alertService.cleanAlert();
					  		
					  		$scope.showRolePop = true;
					  		/*
					  		ngDialog.open({
					  		    className: 'ngdialog-theme-default custom-width-800',
					  		    template: 'views/pages/power/userRoleDialog.html',
					  		    scope: $scope,
					  		    controller: ['$scope', '$http', 'alertService', '$stateParams','ngDialog',
											function($scope, $http, alertService,  $stateParams, ngDialog) {
					  		    	
							    	
					  		    }],
					  		    preCloseCallback: function(value) {
					  		    	  alertService.cleanAlert();
					  		    }
					  		});
							return;
							*/
						};
						
						$scope.saveRolePop = function (){
							alertService.cleanAlert();
							$http.post('./userManager/saveAddRole', $scope.userInfoVo)
			      				.success(function(data) {
									if (data != null && data != '') {
										if (data['errorType'] != "success") {
											alertService.add(data["errorType"],data["errorMessage"]);
										} else {
											$scope.bufferRole.rowCollection = data.returnData.subRoleModelList;
											$scope.bufferRole.data = [].concat($scope.bufferRole.rowCollection);
											$scope.bufferRole.selected = [];
											$scope.showRolePop = false;
//								            alertService.add(data["errorType"],"Saved successfully.");
										}
									}
								});
						};
						
						$scope.cancelRolePop = function (){
							$scope.showRolePop = false;
						};
						
						 $scope.openPop = function () {
					         $scope.popupPop.opened = true;
					     }; 
					     $scope.popupPop = {
					         opened: false
					     };
					     $scope.dateOptionsPop = {
					         dateDisabled: false,
					         formatYear: 'yy',
					         startingDay: 1
					     };
					     
					     $scope.setPopActiveDate = function(){
					    	 $scope.userInfoVo.selectedRoleModel.inactiveDate = "";
						 };
						 
						 $scope.roleValueChange = function(){
							if ($scope.roleManagerCodeList != null && $scope.roleManagerCodeList != '') {
					             for(var i=0; i< $scope.roleManagerCodeList.length; i++) {
					            	 if($scope.userInfoVo.selectedRoleModel.roleCode  ==  $scope.roleManagerCodeList[i].roleCode) {
					            		 $scope.selectedRole = $scope.roleManagerCodeList[i];
					            		 console.log("---------------------value-change---------------------");
					            		 console.log( $scope.selectedRole);
					            		 $scope.userInfoVo.selectedRoleModel.roleName = $scope.selectedRole.roleName;
					            		 $scope.userInfoVo.selectedRoleModel.roleId =  $scope.selectedRole.id;
					            		 break;
					            	 }
					             }
							}
					  	};
					  	
					  	$scope.save= function() { 
				     		if($scope.userInfoVo == ''){
				     			$scope.userInfoVo ={};
				     			$scope.userInfoVo.id= "";
				     		}
				     	  $scope.isSaveCk=true;
				     	  $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
				     	  $scope.userInfoVo.plantList=$scope.brandList;
				     	  $scope.userInfoVo.prodLnList=$scope.prodList;
				     	  console.log($scope.userInfoVo);
				          $http.post('./userManager/saveUser', $scope.userInfoVo).
				              success(function(data) {
				                   if (data['errorType'] == "success") {
				                	 // console.log(data.returnData);
//				                  	 $scope.userInfoVo = data.returnData;
//				                     alertService.cleanAlert();
//				                 	 if(id == null){
//				                         alertService.add(data["errorType"], "Saved successfully.");
//				                         $scope.userInfoVo.userPwd = null;
//				                 	 }else{
//				                  	     alertService.add(data["errorType"], "Saved successfully.");
//				                  	     $scope.userInfoVo.userPwd = null;
//				                 	 }
				                	   alertService.saveSuccess();
				                	   $scope.cancel();
				                   }else if(data['errorType']!=null){
				                 	  
				                     alertService.add(data["errorType"], data["errorMessage"]);
				                  }     
				  	        });
				       };
				       
				       	$scope.cancel =  function() {
					  		 $http.post('./userManager/cancel')
					     		.success(function (data) {
					     			$scope.userInfo=data;
					     			$scope.closeThisDialog(0);
						            ngDialog.closePromise;
						            alertService.cleanAlert();
						            $scope.initViewList();
						            alertService.showMsg();
					     		});
				  	 	};
					}
					
				});
			}
	        
	        
	        $scope.searchUserInfoVo = {};
	        $scope.initViewList = function() {
		        $scope.searchUserInfoVo.shopId = $scope.shopId;
				$http({
					method : 'POST',
					data : $scope.searchUserInfoVo,
					url : "./userManager/userList"
				}).success(function(data) {
					//console.log(data);
					$scope.vm.rowCollection = data;
	                $scope.vm.data = [].concat(data);
				});
			};
	        /*
	        $scope.initViewList = function() {
				//初始化ip信息
	        	var shopid = $stateParams.shopId;
	        	$scope.shopId = $scope.sysUser.shopId = shopid;
				$scope.promise = $http({
					method : 'GET',
					params : {shopId : shopid,
						userCode : $scope.sysUser.userCode,
						userName : $scope.sysUser.userName
					},
					url : "./userManager/userList"
				}).success(function(data) {
					
				});
			}
			*/
			
			$scope.saveEditIp = function (obj) {
				//console.log($scope.sysUserEdit);
				$http.post('./sysUser/saveSysUser', $scope.sysUserEdit).
		     		success(function (data) {
		     	if (data['errorType'] == "success") {
		           
		             alertService.add(data["errorType"],"save success");	 
					
				 }else{
	     	       	  $scope.ableAdd=false;  
	                       alertService.add(data["errorType"],data["errorMessage"]);
	                 
	     	          }
		     		  $scope.vm.selected = [];
		     		  $scope.initViewList();
		     	      obj.closeThisDialog(0);  
				});
			}
			
			$scope.deleteUsers = function () {

        		 if( $scope.vm.selected.length < 1){
            		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
                 }
	    		 var userIds = "";
	             for (var i = 0; i < $scope.vm.selected.length; i++) {
	            	 userIds = userIds+$scope.vm.selected[i]["id"] + ","; 	
	              }
	             var popupMsg = T.T('confirm_to_delete_selected_value');
                $confirm({text: popupMsg, title: T.T('button_delete'), ok: T.T('Yes'), cancel: T.T('No')})
                    .then(function () {
                        $http({
                            method: 'POST',
                            params: {
                            	userIds:userIds
                            },
                            url: "./sysUser/deleteSysUser"
              	          }).success(function (data) {
              	        	$scope.closeAlert = alertService.closeAlert;
              	              if (data != null && data != '') {
              	                  if (data['errorType'] != "success") {
              	                      alertService.add(data["errorType"], data["errorMessage"]);
              	                  } else {
              	                      alertService.add("success", "delete successful");
              	                	  $scope.initViewList();
              	                  }
              	              }   
              	    	})

   	          });
        	
			}
			
			
			$scope.editUsers = function (){
				console.log($scope.vm.selected);
				if( $scope.vm.selected.length != 1){
					$scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return false;
				}
				var userId = $scope.vm.selected[0].id;
				$http({method:'GET',params:{ userId:userId},  
					url:"./userManager/userDetail"}).
					success(function(data) {
						$scope.userInfoVo = data;   
						$scope.bufferRole.rowCollection = $scope.userInfoVo.subRoleModelList;
						$scope.bufferRole.data = [].concat($scope.bufferRole.rowCollection);
						$scope.bufferRole.selected = [];
						$scope.vm.selected = [];
				}); 
				return true;
			};
			
			/*
			$scope.editUsers = function () {
				
				$scope.promise = $http({
					method : 'POST',
					params : {
						userId : userId
					},
					url:"./userManager/"
				}).success(function (data) {
					console.log(data);
					$scope.sysUserEdit = data;
				})
				return true;
			}
			*/
			
			$scope.page = 10;
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
			
			$scope.initViewList();
			
			$scope.roleManagerCodeList = [];
		    $scope.roleModel = {};
			$http.post('./roleManager/roleList',  $scope.roleModel).success(
				 function(data) {
					// $scope.roleManagerCodeList = data;
					 for(var i=0 ; i< data.length ; i++) {
						 if(data[i].mallShopInd === "S") { //只选取有所有店家权限的角色
							 $scope.roleManagerCodeList.push(data[i]);
						 }
					 }
				 }
			 );
			
			
		} ]);