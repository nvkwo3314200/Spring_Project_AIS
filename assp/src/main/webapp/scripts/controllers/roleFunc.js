'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')

    .controller('RoleFuncCtrl', ['$scope', '$state', '$http',
                                 '$interval',  'alertService', 'localStorageService', '$window', '$confirm','T','$q',
                                 function ($scope, $state, $http, $interval,  alertService, localStorageService, $window, $confirm,T,$q) {
		 	$scope.vm = this;
    	 	$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
			
			
    	 /**
    	  * id 
    	  * 
    	  * 
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
         
         
         $scope.userroles = [{
             code: "SYSTEMADMIN",
             description: 'SYSTEMADMIN',
             descriptionTw: '\u5546\u5bb6',
         }, {
             code: "APPROVER",
             description: 'APPROVER',
             descriptionTw: '\u5e97\u5bb6-APPROVER',
         },
         {
             code: "SUPPLIER",
             description: 'SUPPLIER',
             descriptionTw: '\u5e97\u5bb6',
         }]
         
         $scope.roleVo = {};
    	
           $scope.vm.rowCollection = [];
           $scope.vm.data = [];
            
            $scope.roleVoList = [{
            	roleCode : 'M001',
            	roleName : 'Bossini \u5546\u5bb6',
            	roleType : '\u5546\u5bb6',
            	remark : '',
            	active : 'Y',
            	inactiveDate : '',
            	seq : '1',
            	id:'1'
	        }, {
	        	roleCode : 'S001',
            	roleName : 'Bossini \u5e97\u5bb6',
            	roleType : '\u5e97\u5bb6',
            	remark : '',
            	active : 'Y',
            	inactiveDate : '',
            	seq : '2',
            	id:'2'
	        }, {
	        	roleCode : 'S002',
            	roleName : 'Bossini \u5e97\u5bb6-APPROVER',
            	roleType : '\u5e97\u5bb6-APPROVER',
            	remark : '',
            	active : 'N',
            	inactiveDate : '',
            	seq : '3',
            	id:'3'
	        }]
            $scope.vm.rowCollection = $scope.roleVoList;
            $scope.vm.data = [].concat($scope.roleVoList);
            
            //add user
            
            $scope.add = function(){
                $state.go('main.role_function_add');
            }
            
           
        	//edit user
        	
        	$scope.edit = function(){
        		//console.log($scope.vm.selected.length);
        		 if($scope.vm.selected.length != 1){
	        		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
        		 
	         var pId=$scope.vm.selected[0]["id"];
	         $state.go('main.role_function_add',{'id': pId});
        	}
        	
        	
        	// delete user
        	$scope.delete2= function(){
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
	                    /* delete code */	

   	          });
        	}
        	
       
            $scope.searchProductCat = function(message) {
                
            }
            
            

        }]);
        
