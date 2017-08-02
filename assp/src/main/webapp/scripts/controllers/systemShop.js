'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')

    .controller('SystemShopCtrl', ['$scope', '$state', '$http',
                                 '$interval',  'alertService', 'localStorageService', '$window', '$confirm','T','$q','$location','userService','$controller','cacheService',
                                 function ($scope, $state, $http, $interval,  alertService, localStorageService, $window, $confirm,T,$q,$location,userService,$controller, cacheService) {
    		$controller("BaseCtrl", {$scope:$scope});
    		
    		$scope.tableData = [];
    		$scope.selected = [];
    		$scope.searchmodel = cacheService.getCache();
    		cacheService.setCache($scope.searchmodel);
    		$scope.pageModel = $scope.searchmodel;
    		
    		/*
    		$scope.shopCode = null;
    		$scope.shopName = null;
    		$scope.respPerson = null;
    		$scope.contactPerson = null;
    		$scope.contactEmail = null;
    		$scope.websiteName = null;
    		$scope.telNo = null;
    		$scope.shop = {};
			*/
			alertService.showMsg();
			
			
			/*userService.getCurrentUser().then(function (data) {
				alert(1);
				$scope.shop.shopId = data.shopId;
				$scope.shop.mallId = data.mallId;
			});*/
			
			 $scope.init = function () {
				 $scope.getPowers();
		 		 $scope.search();
			 }
			
            $scope.addShop = function(){
                $state.go('main.shop_basic_info');
            }
        	//edit user
        	
        	$scope.editShop = function(){
        		 if($scope.selected.length != 1){
	        		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
        		 
	         var shopId=$scope.selected[0]["id"];
	         var shopName = $scope.selected[0]["shopName"];
	         $state.go('main.shop_basic_info',{'shopId': shopId,'shopName':shopName});
        	}
        	
        	$scope.view= function(){
    			if ($scope.selected.length != 1) {
    				alertService.add('danger', T.T('select_records'));
    				return;
    			}
    			var id = $scope.selected[0]['id'];
    			var state=true;
    			$state.go('main.shop_basic_info', {'shopId' : id,'state':state});
    		};
        	
        	
        	// delete user
        	$scope.deleteShop = function(){
        		 if( $scope.selected.length < 1){
            		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
                 }
	    		  var shopIds = "";
	             for (var i = 0; i < $scope.selected.length; i++) {
	            	 shopIds = shopIds+$scope.selected[i]["id"] + ","; 	
	              }
	             
	             var popupMsg = T.T('confirm_to_delete_selected_value');
	          
                $confirm({text: popupMsg, title: T.T('save_button'), ok: T.T('Yes'), cancel: T.T('No')}).then(function () {
                    	//alert("delete code!");
                    $http({
                        method: 'POST',
                        params: {
                       	 shopIds: shopIds
                        },
                        url: "./shop/deleteShop"
          	          }).success(function (data) {
          	        	$scope.closeAlert = alertService.closeAlert;
          	        	
          	              if (data != null && data != '') {
          	                  if (data['errorType'] != "success") {
          	                     
          	                      alertService.add(data["errorType"], data["errorMessage"]);
          	                  } else {
          	                    
          	                      //alertService.add("success", "delete successful");
          	                	  $scope.search("delete successful");
          	                  }
          	
          	              }   

          	    	})
   	          });
        	}
        	
       
            $scope.search = function(flag) {
            	//alert("search functon write this");
        		$scope.searchmodel.id = localStorageService.get("shopId");
        		$scope.searchmodel.mallId = localStorageService.get("mallId");
        		
            	$scope.promise = $http.post('./shop/search', $scope.searchmodel).success(
    					function(data) {
    						if (data['errorType'] == "success") {
    							$scope.selected = [];
    							$scope.tableData = data.returnDataList;
    							if(flag||false) alertService.cleanAlert();
    						} else if(data['errorType'] !=null )  {
    							alertService.add(data["errorType"],
    									data["errorMessage"]);
    						}
    					}
    				);
            	/*
            	$scope.promise = $http({
    	            method: 'GET',
    	            params: {
    	            	shopId : localStorageService.get("shopId"),
    	            	mallId : localStorageService.get("mallId"),
    	            	shopCode:$scope.shopCode,
    	            	shopName:$scope.shopName,
    	            	respPerson:$scope.respPerson,
    	            	contactPerson:$scope.contactPerson,
    	            	contactEmail:$scope.contactEmail,
    	            	websiteName:$scope.websiteName,
    	            	telNo:$scope.telNo
    	                },
    	            url: "./shop/shopViewList"
    	        }).success(function(data) {
    	        	$scope.selected = [];
    				$scope.tableData = data;
    	        	if(flag||false) alertService.cleanAlert();
                });
                */
            }
            $scope.init();
 			
        }]);
        
