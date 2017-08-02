'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:RoleManagerCtrl
 * @description
 * # RoleManagerCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('RoleManagerCtrl', 
        function ($scope, $state, cacheService,$http, alertService, ngDialog, $location,$stateParams, T, $controller) {
    	$controller("BaseCtrl", {$scope:$scope});
    	
		 $scope.RoleVo = {};
		 $scope.tableData = [];
         $scope.userName = "";
         $scope.activate = "Y";
         $scope.searchModel = cacheService.getCache();
         cacheService.setCache($scope.searchModel);
         $scope.pageModel = $scope.searchModel;
         $scope.init = function(){
 			$scope.getPowers();
 			alertService.showMsg();
 			};
         
    	 /**
    	  * id 
    	  */
    	 
         $scope.selected = [];
         $scope.selectAll = function (collection) {
            if ($scope.selected.length === 0) {
                 angular.forEach(collection, function (val) {
                     $scope.selected.push(val);

                 });
            } else if ($scope.selected.length > 0 && $scope.selected.length != $scope.tableData.length) {
                 angular.forEach(collection, function (val) {
                     var found = $scope.selected.indexOf(val);
                     if (found == -1) $scope.selected.push(val);
                 });
            } else {
                 $scope.selected = [];

             }

         };
        $scope.select = function (id) {
             var found = $scope.selected.indexOf(id);
             if (found == -1) {
            	 $scope.selected.push(id);
             }else {
            	 $scope.selected.splice(found, 1);
            	 };
            	 
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
         
         $scope.select1 = function(role) {
        	 $state.go('main.roleManager_add',{'roleId': role.id});
 			
 		};
         
// 		$scope.search = function() {	  
//        $http.post('./roleManager/roleList', {})
//           .success(function(data) {
//	        	 $scope.rowCollection = data;
//                 $scope.data = [].concat(data);
//                
//                // alertService.cleanAlert();
//	        });
// 		};
            
   		$scope.search = function(flag) {	
   			$http.post('./roleManager/searchRole', $scope.searchModel).success(			
					function(data) {
					$scope.tableData = data;
                    $scope.selected = [];
                    if(flag||false) alertService.cleanAlert();
                }
			);
        };
           
            
        $scope.addRole = function(){
                $state.go('main.roleManager_add');
            };
            
    		
    	$scope.resert= function () {
    		$scope.searchModel.roleName=undefined;
    		cacheService.clearCache();
    	};
            
        $scope.editRole = function(){
        		if($scope.selected.length != 1){
	        		 
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
        		 var roleId=$scope.selected[0]["id"];
        		 $state.go('main.roleManager_add',{'roleId': roleId});
        	};
        	
        	
        $scope.view = function(){
        		 if($scope.selected.length != 1){
	        		 
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
        		 var roleId=$scope.selected[0]["id"];
        		 var state=true;
        		 $state.go('main.roleManager_add',{'roleId': roleId,'state':state});
        	};
        	
        $scope.deletes = function(isDelete){
        	if( $scope.selected.length < 1){
            		
	        		alertService.add('danger', T.T('select_records'));
	    			return;
                 }
        	if (!isDelete) {
			    $('#confirmModal').modal();
			    return;
			      }
	    	var roleIds = "";
	        for (var i = 0; i < $scope.selected.length; i++) {
	        	roleIds = roleIds+$scope.selected[i]["id"] + ","; 	
	        }
	         $http({
	              method: 'POST',
	              params: {
	                roleIds: roleIds
	              },
	              url: "./roleManager/deleteRole"
	   	         }).success(function (data) {
	   	             if (data['errorType'] != "success") {
	   	            	$scope.selected = [];
	   	            	
	   	                alertService.add(data["errorType"], data["errorMessage"]);
	   	             } else {
	   	                
	   	                alertService.add("success", T.T('delete_success'));
	   	                $scope.searchModel.roleName=undefined;
	   	                $scope.search();
	   	               }
	   	          });
        	};
        });
