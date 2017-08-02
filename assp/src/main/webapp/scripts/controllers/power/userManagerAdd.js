'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('UserManagerAddCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$stateParams',
                                       'ngDialog','$window', '$confirm', "IntegralUITreeGridService", "$timeout",'T',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$stateParams
        			, ngDialog, $window, $confirm, $gridService, $timeout, T) {
    	 
    	$scope.isSaveCk=false;
    	//$scope.isShop=false;
		$scope.userInfoVo = {};
		//lijun
		$scope.falg=false;
		/*
		$scope.productline=[];
		$scope.ProdLnList=[];
		$scope.PlantList=[];
		$scope.brandSelect=[];
		$scope.brandList=[];
		$scope.prodSelect=[];
		$scope.prodList=[];
		$scope.prod=[];
		$scope.prodln=[];
		$scope.plant=[];
		$scope.plantcode=[];
		*/
		//LY new mothoed Pop--------------------------
	 	$scope.bufferRole = {};
	 	$scope.page = 10;
	    $scope.bufferRole.selected = [];
	    $scope.bufferRole.rowCollection = [];
	    $scope.bufferRole.data = [];
	    $scope.readDate = true;
		$scope.viewStatu =$stateParams.viewStatu;
		
		$scope.mallVoList = [];
		
		init();
		
		function init(){
	    	userDetail(); 
	    	$scope.falg=true;
 		};
	    
    	$scope.open1 = function () {
             $scope.popup1.opened = true;
         };
         $scope.popup1 = {
             opened: false
         };
        
         $scope.dateOptions = {
             dateDisabled: false,
             formatYear: 'yy',
             format:'yyyy/MM/dd',
//             maxDate: new Date(2020, 5, 22),
             //minDate: new Date(),
             startingDay: 1
         };
         
         function userDetail(){
        	if($stateParams.userId == null) return ;
			$http({method:'GET',params:{ userId:$stateParams.userId},  
				url:"./userManager/userDetail"}).
				success(function(data) {
					$scope.userInfoVo = data; 
					console.log(data);
					/*
					$scope.getPlantModels();
										
					if($scope.userInfoVo.plantCode!=null){
						$scope.plantcode=$scope.userInfoVo.plantCode.split(",");						
					}
					if($scope.userInfoVo.prodLn!=null){
						$scope.prodln=$scope.userInfoVo.prodLn.split(",");						
					}
					*/	
					$scope.bufferRole.rowCollection = $scope.userInfoVo.subRoleModelList;
					$scope.bufferRole.data = [].concat($scope.bufferRole.rowCollection);
					$scope.bufferRole.selected = [];
					console.log($scope.bufferRole.data);
					$scope.checkMallUser();
				}); 			
		};
		
		/*
		$scope.getPlantModels=function(){
			$http.post('./userManager/getPlantModels').
			success(function(data) { 								
					$scope.PlantList=data; 
					for(var j=0;j<$scope.plantcode.length;j++){
						for(var i=0;i<$scope.PlantList.length;i++){				
							if($scope.PlantList[i].plantCode==$scope.plantcode[j]){	
								$scope.brandList.push($scope.PlantList[i]);
								$scope.PlantList.splice(i,1);
							}			
						}
					}
					$scope.setProdLn();
			});		
		};
         */
         
		
    	//save user
     	$scope.save= function() { 
     		if($scope.userInfoVo == ''){
     			$scope.userInfoVo ={};
     			$scope.userInfoVo.id= "";
     		}
     	  $scope.isSaveCk=true;
     	  $scope.userInfoVo.subRoleModelList = $scope.bufferRole.rowCollection;
     	  $scope.userInfoVo.plantList=$scope.brandList;
     	  $scope.userInfoVo.prodLnList=$scope.prodList;
     	  if($scope.isMall) $scope.userInfoVo.mallId = localStorageService.get("mallId");
          $http.post('./userManager/saveUser', $scope.userInfoVo).
              success(function(data) {
                   if (data['errorType'] == "success") {
                	 // console.log(data.returnData);
//                  	 $scope.userInfoVo = data.returnData;
//                     alertService.cleanAlert();
//                 	 if(id == null){
//                         alertService.add(data["errorType"], "Saved successfully.");
//                         $scope.userInfoVo.userPwd = null;
//                 	 }else{
//                  	     alertService.add(data["errorType"], "Saved successfully.");
//                  	     $scope.userInfoVo.userPwd = null;
//                 	 }
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
   			$state.go('main.userManager',{'userInfo':$scope.userInfo});
   		});
	 };
	 
	 $scope.setdate=function(){
   	   $scope.userInfoVo.userActiveDate="";
	 };
	 
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

     $scope.roleManagerCodeList = [];
     $scope.roleModel = {};
	 $http.post('./roleManager/roleList',  $scope.roleModel).success(
		 function(data) {
			 for(var i=0 ; i< data.length ; i++) {
				 if(data[i].mallShopInd === "M") { //只选取有所有店家权限的角色
					 $scope.roleManagerCodeList.push(data[i]);
				 }
			 }
		 }
	 );
	 
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
         console.log($scope.userInfoVo.subRoleModelList);
      	 $http.post('./userManager/deleteRolePop', $scope.userInfoVo)
      		.success(function (data) {
 	              if (data != null && data != '') {
 	                  if (data['errorType'] != "success") {
 	                      
 	                      alertService.add(data["errorType"], data["errorMessage"]);
 	                  } else {
 	                	  $scope.bufferRole.rowCollection = data.returnData.subRoleModelList;
  				          $scope.bufferRole.data = [].concat($scope.bufferRole.rowCollection);
  		                  $scope.bufferRole.selected = [];
  		                  $scope.checkMallUser();
// 	                      alertService.add("success", T.T('delete_success'));
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
		 console.log( $scope.auditTrailData);
		 ngDialog.open({
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
  		ngDialog.open({
  			closeByDocument : false,
  		    className: 'ngdialog-theme-default custom-width-800',
  		    template: 'views/pages/power/userRoleDialog.html',
  		    scope: $scope,
  		    controller: ['$scope', '$http', 'alertService', '$stateParams','ngDialog',
						function($scope, $http, alertService,  $stateParams, ngDialog) {
  		    	
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
									$scope.checkMallUser();
								    $scope.closeThisDialog(0);
						            ngDialog.closePromise;
//						            alertService.add(data["errorType"],"Saved successfully.");
								}
							}
						});
				};
				
				$scope.cancelRolePop = function (){
					alertService.cleanAlert();
					$scope.closeThisDialog(0);
				};
  		    }],
  		    preCloseCallback: function(value) {
  		    	  alertService.cleanAlert();
  		    }
  		});
		return;
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
            		 $scope.userInfoVo.selectedRoleModel.mallShopInd =  $scope.selectedRole.mallShopInd;
            		 break;
            	 }
             }
		}
  	};
  	
  	$scope.checkMallUser = function () {
  		for(var i=0; i < $scope.bufferRole.data.length; i++) {
  			console.log($scope.bufferRole.data[i].mallShopInd);
  			if($scope.bufferRole.data[i].mallShopInd =='M') {
  				$scope.isMall=true;
  				return;
  			} else {
  				$scope.isMall=false;
  			}
  		}
  		$scope.isMall = false;
  	}
  	
	$http.post('./mall/mallList',{}).success(
		function(data) {
			$scope.mallVoList = data["returnDataList"];
		}
	);
  	/*
  	$scope.checkShop = function() {
  		for(var i=0; i < $scope.bufferRole.data.length; i++) {
  			if($scope.bufferRole.data[i].mallShopInd ==='S') {
  				$scope.isShop=true;
  				return;
  			}
  		}
  		$scope.isShop=false;
  	}
  	
  	$scope.getShopInfo = function() {
  		$http.post('./shop/shopViewList').success(
			 function(data) {
				 $scope.shopInfoList = data;
			 }
		 );
  	}
  	*/
}]);
