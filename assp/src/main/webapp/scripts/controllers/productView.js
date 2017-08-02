'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ProductViewCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$q',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$q) {
    	
    	 $scope.vm = this;

         // Declare the array for the selected items
         $scope.vm.selected = [];

         // Function to get data for all selected items
         $scope.vm.selectAll = function (collection) {

             // if there are no items in the 'selected' array,
             // push all elements to 'selected'
             if ($scope.vm.selected.length == 0) {

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

//        	 element.parent().addClass('st-selected');
        	 
        	 var tableTr = document.getElementById('tr'+id.id);
        	 
             var found = $scope.vm.selected.indexOf(id);

             if (found == -1) $scope.vm.selected.push(id);

             else $scope.vm.selected.splice(found, 1);
             var tableInput = tableTr.getElementsByTagName('input')[0];
//             tableTr.getElementsByTagName('input')[0].checked='true';
//             tableTr.className='ng-scope : st-selected';
             
             if(tableInput.checked==false && found == -1){
            	 tableTr.className='ng-scope : st-selected';
	             tableInput.checked=true;
            	 }
             else if(tableInput.checked==true && found !=-1){
	             tableTr.className='ng-scope : null';
	             tableInput.checked=false;
            	 }
             

         };
         // Function to get data by selecting a single row
         $scope.vm.select2 = function (id) {
             // if there are no items in the 'selected' array,
             // push all elements to 'selected'
             if ($scope.vm.selected.length == 0) {
                     $scope.vm.selected.push(id);
                 // if there are items in the 'selected' array,
                 // add only those that ar not
             } else if ($scope.vm.selected.length > 0 && $scope.vm.selected.length != $scope.vm.data.length) {
                     var found = $scope.vm.selected.indexOf(id);
                     if (found == 0) $scope.vm.selected.push(id);
                 // Otherwise, remove all items
             } else {
                 $scope.vm.selected = [];
             }

         };
    	
           $scope.vm.rowCollection = [];
           $scope.vm.data = [];
           
           
           $scope.approvalButtonDisable = false;
            $scope.page = 10;
            $scope.visible = false;
            $scope.visible1 = false;
            $scope.visible2 = false;
            $scope.visible3 = false;
            $scope.supplier = null;
            $scope.supplierList = null;    
            
            $scope.delay = 0;
      		$scope.minDuration = 0;
      		$scope.templateUrl = '';
      		$scope.message = 'Please Wait...';
      		$scope.backdrop = true;
      		$scope.promise = null;
      		
       
            $http.get('./supplier/listUserSupplier').
                success(function (data) {
                    $scope.supplierList = data;
                });

            var userRoes= localStorageService.get("userRole");
            var supplierId= localStorageService.get("supplierId");
            
            
            var user = localStorageService.get("user");

			var mallId  =user.mallId;
			var shopId =user.shopId;
			var userRose = user.roleCode;
		
			if(shopId!=null){
				userRose = "SUPPLIER";
				$scope.supplierCode = shopId;
			}
			
			var userRoes = userRose;
			var supplierId=  shopId;
            
			if (userRose == 'SYSTEMADMIN' ||userRoes == 'APPROVER'|| userRoes == 'MALL'||mallId!=null ) {
          //  if(userRoes!=null && ( userRoes=='SYSTEMADMIN' || userRoes=='APPROVER'  ||mallId!=null)){
                $scope.visible = true;
            }else{
                $scope.visible = false;
            }
            
            if(userRoes!=null && userRoes=='SYSTEMADMIN'){
                $scope.visible1 = true;
                $scope.visible2 = false;
                $scope.visible3 = false;
            }else if(userRoes!=null && userRoes=='APPROVER'){
            	  $scope.visible1 = false;
                  $scope.visible2 = true;
                  $scope.visible3 = false;
            }else{
            	 $scope.visible1 = false;
                 $scope.visible2 = false;
                 $scope.visible3 = true;
            }
          

            $scope.pstatus = [{
                code: "",
                description: ''
            },{
                code: "DRAFT",
                description: 'Draft'
            }, {
                code: "SUBMIT_FOR_APPROVAL",
                description: 'Pending for approval'
            },
            {
                code: "APPROVED",
                description: 'Approved'
            },
            {
                code: "AUTO_APPROVED",
                description: 'Auto Approved'
            },
            
            {
                code: "UPLOADED",
                description: 'Uploaded'
            },
            {
                code: "REJECTED",
                description: 'Rejected'
            }
//            ,
//            {
//                code: "APPROVED_IN_RETEK",
//                description: 'Approved in Retek'
//            },
//            {
//                code: "UNAPPROVED_IN_RETEK",
//                description: 'Unapproved in Retek'
//            }
            
            ]
            ;
       	 
       	 $scope.pdeliver = [{
             code: "",
             description: ''
         },{
                code: "D",
                description: 'Supplier direct delivery'
            }, {
                code: "N",
                description: 'Consignment via warehouse'
            },{
            code: "S",
            description: 'Consignment'
        }];
            var arr = new Array(); 
            
           if(userRoes!=null && ( userRoes=='SYSTEMADMIN' || userRoes=='APPROVER')){	
        	    arr.push('SUBMIT_FOR_APPROVAL');
        	    arr.push('UNAPPROVED_IN_RETEK');
            	$scope.status = arr;
            }else{
            	 arr.push('DRAFT');
            	 arr.push('REJECTED');
            	 arr.push('UNAPPROVED_IN_RETEK');
            	 $scope.status = arr;
            	
            }
            
           

//           $scope.promise =     $http({
//	            method: 'GET',
//	            params: {
//	            	supplierCode:$scope.supplierCode,
//	            	supplierProductCode:$scope.supplierProductCode,
//	            	productCode:$scope.productCode,
//	            	status:$scope.status,
//	            	shortDescEn:$scope.shortDescEn,
//	            	deliveryMode:$scope.deliveryMode,
//	            	dailyInventory:$scope.dailyInventory
//	                },
//	            url: "./product/productViewList"
//	        }).success(function(data) {
//	        	
//	        	 $scope.vm.rowCollection = data;
//                 $scope.vm.data = [].concat(data);
//              
//                 $scope.supplierSelected = null;
//                 alertService.cleanAlert();
//	        });
            
       
            //add product
            $scope.addProduct = function(){
                $state.go('main.product');
            };
            
          //edit product
            $scope.editProduct = function(){
	        	 if($scope.vm.selected.length != 1){
	        		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
	             var productId=$scope.vm.selected[0]["id"];
	             $state.go('main.product',{'productId': productId});
            }
            
            //upload product
            $scope.productUpload=function(){
	           	 if( $scope.vm.selected.length < 1){
	        		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
	           	 
	           	 var pIds = "";
	           	 var pStatus = "";
	           	 
		         for (var i = 0; i < $scope.vm.selected.length; i++) {
		        	  pIds = pIds+$scope.vm.selected[i]["id"] + ",";
		        	  var psta = $scope.vm.selected[i]["status"];
		        	  
		        	  pStatus = pStatus+$scope.vm.selected[0]["status"];
		        	  
		        	  
		        	  if(psta != 'Approved' && psta != 'Auto Approved'){
		        		 $scope.closeAlert = alertService.closeAlert;
			        		 alertService.add('danger', 'Please select product of Approved status.');
			    			 return;
		        	  }else{
		        		 
		        	  }
		          }
		         var deferred = $q.defer();
		         $http({
		             method: 'POST',
		             params: {
		            	 pIds: pIds
		             },
		             url: "./product/uploadProduct"
			          }).success(function (data) {
			              if (data != null && data != '') {
			            	  
			            	  deferred.resolve(data);
			                  if (data['errorType'] != "success") {
			                      $scope.closeAlert = alertService.closeAlert;
			                      alertService.add(data["errorType"], data["errorMessage"]);
			                  } else {
			                      alertService.add("success", "upload successful");
			                      $scope.searchProduct();
			                  }
			
			              } 
			            
			          }).error(
								function(data, status, headers,
										config) {
									deferred.reject();
								});

				$scope.promise = deferred.promise;;
        }
            
            
           //delete product $scope.vm.selected
            $scope.deleteProduct = function(){
            	 if( $scope.vm.selected.length < 1){
            		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
                 }
    		  var pIds = "";
    		  var pStatus = "";
             for (var i = 0; i < $scope.vm.selected.length; i++) {
            	  pIds = pIds+$scope.vm.selected[i]["id"] + ",";
            	  var psta = $scope.vm.selected[i]["status"];
            	  pStatus = pStatus+$scope.vm.selected[0]["status"];
            	  if(psta != 'Draft'){
            		 $scope.closeAlert = alertService.closeAlert;
 	        		 alertService.add('danger', T.T('select_records'));
 	    			 return;
            	  }else{
            		 
            	  }
              }
             $http({
                 method: 'POST',
                 params: {
                	 pIds: pIds
                 },
                 url: "./product/deleteProduct"
   	          }).success(function (data) {
//   	             
   	              if (data != null && data != '') {
   	                  if (data['errorType'] != "success") {
   	                      $scope.closeAlert = alertService.closeAlert;
   	                      alertService.add(data["errorType"], data["errorMessage"]);
   	                  } else {
   	                     
   	                      alertService.add("success", "update successful");
   	                      $scope.searchProduct();
   	                  }
   	
   	              }   	          
   	            
   	          });
            };
            
            //submit for approve
        	$scope.approveSubmitProduct = function(){
        		
        		 if($scope.vm.selected.length < 1){
        			 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', 'Please select product of draft status.');
	    			 return;
                 }
    		  var pIds = "";
    		   var pStatus = "";
    		  var selectNumber = $scope.vm.selected.length;
    		  var number = 0;
             for (var i = 0; i < $scope.vm.selected.length; i++) {
            	 //var pStatus =$scope.vm.selected[i]["status"];
            	 var psta = $scope.vm.selected[i]["status"];
            	 if(psta == 'Draft'){
            	  pIds = pIds+$scope.vm.selected[i]["id"] + ",";
             	  }else{
             		 number++; 
             	  }          	
              }

          
             if(selectNumber != number){
            	    $http({
                        method: 'POST',
                        params: {
                       	 pIds: pIds,
                            pStatus:pStatus,
                            pType:'submitApprove'
                        },
                        url: "./product/updateProductStatus"
          	          }).success(function (data) {
//          	             
          	              if (data != null && data != '') {
          	                  if (data['errorType'] != "success") {
          	
          	                      $scope.closeAlert = alertService.closeAlert;
          	                      alertService.add(data["errorType"], data["errorMessage"]);
          	                  } else {
          	                      $scope.searchProduct();
//          	                      $scope.closeAlert = alertService.closeAlert;
          	                      //alertService.add("success", "update successful");
          	                  }
          	
          	              }   	          
          	            
          	          });
             }else{
            	 $scope.closeAlert = alertService.closeAlert;
        		 alertService.add('danger', 'Please select product of draft status.');
    			 return;
             }
             
            };
        	
	        //approve
        	$scope.approveProduct = function(){
        		 if($scope.vm.selected.length < 1){
        			 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', 'Please select product of Pending for approval status.');
	    			 return;
                 }
        		 $scope.approvalButtonDisable = true;
    		  var pIds = "";
    		  var pStatus = "";
    		  var selectNumber=$scope.vm.selected.length;
    		  var number = 0;

             for (var i = 0; i < $scope.vm.selected.length; i++) {
            	             	  var psta =  $scope.vm.selected[i]["status"];
            	  if(psta == 'Pending for approval'){
            		  pIds = pIds+$scope.vm.selected[i]["id"] + ",";
            	  }else{
            		  number++; 
            	  }
              }
             
            checkApprove (pIds,selectNumber,number,pStatus);

            }
        	;
        	 //reject
        	$scope.rejectProduct = function(){
        		 if($scope.vm.selected.length < 1){
        			 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', 'Please select product of Pending for approval status.');
	    			 return;
                 }
    		  var pIds = "";
    		  var pStatus = "";
    		  var selectNumber = $scope.vm.selected.length;
    		  var number = 0;
             for (var i = 0; i < $scope.vm.selected.length; i++) {
            	 var psta =  $scope.vm.selected[i]["status"];
            	 if(psta == 'Pending for approval'){
            	  pIds = pIds+$scope.vm.selected[i]["id"] + ",";
            	 }else{
            		 number++; 
            	 }
              }
             if(selectNumber != number){
            	 $http({
                     method: 'POST',
                     params: {
                    	 pIds: pIds,
                         pStatus:pStatus,
                         pType:'reject'
                     },
                     url: "./product/updateProductStatus"
       	          }).success(function (data) {
//       	             
       	              if (data != null && data != '') {
       	                  if (data['errorType'] != "success") {
       	
       	                      $scope.closeAlert = alertService.closeAlert;
       	                      alertService.add(data["errorType"], data["errorMessage"]);
       	                  } else {
       	                      $scope.searchProduct();
       	                     // $scope.closeAlert = alertService.closeAlert;
       	                     // alertService.add("success", "update successful");
       	                  }
       	
       	              }   	          
       	            
       	          });
             }else{
            	 $scope.closeAlert = alertService.closeAlert;
        		 alertService.add('danger', 'Please select product of Pending for approval status.');
    			 return;
             }
            
            };
        	
        	//export Product
        	$scope.exportProduct = function(){
            
        		angular.forEach($scope.supplier, function (value, key) {

                    if ($scope.supplierSelected == null) {
                        $scope.supplierSelected = value.id;
                    } else {
                        $scope.supplierSelected = $scope.supplierSelected + "," + value.id;
                    }

                });


                if (!$scope.visible) {
                    $scope.supplierSelected = supplierId;
                }
                 
                
                
                $scope.promise =    $http({
                    method: 'GET',
                    params: {
                    	onlineUpdatedInd:$scope.onlineUpdatedInd,
                    	supplier:$scope.supplierSelected,
                    	supplierProductCode:$scope.supplierProductCode,
                    	productCode:$scope.productCode,
                    	status:$scope.status,
                    	shortDescEn:$scope.shortDescEn,
                    	deliveryMode:$scope.deliveryMode,
                    	dailyInventory:$scope.dailyInventory
                        },
                    url: "./product/export"
                }).success(function(data) {
                	var byteCharacters = atob(data.fileContent);
                	
                	var byteNumbers = new Array(byteCharacters.length);
                	for (var i = 0; i < byteCharacters.length; i++) {
                	    byteNumbers[i] = byteCharacters.charCodeAt(i);
                	}
                	
                	var byteArray = new Uint8Array(byteNumbers);
//                	
                	var blob = new Blob([byteArray], {
                        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    });
               
                	saveAs(blob, "product_export_"+new Date().getTime()+".xlsx");

//                	var objectUrl = URL.createObjectURL(blob);
//                    window.open(objectUrl);

                });
            }
 ;
            $scope.supplierSelected = null;

            
            
            $scope.searchProduct = function() {
            	
                angular.forEach($scope.supplier, function (value, key) {
                    if ($scope.supplierSelected == null) {
                        $scope.supplierSelected = value.id;
                    } else {
                        $scope.supplierSelected = $scope.supplierSelected + "," + value.id;
                    }

                });


                if (!$scope.visible) {

                    $scope.supplierSelected = supplierId;
                }
                
                
            	
                
                $scope.promise =  $http({
                    method: 'GET',
                    params: {
                    	onlineUpdatedInd:$scope.onlineUpdatedInd,
                    	supplier:$scope.supplierSelected,
                    	supplierProductCode:$scope.supplierProductCode,
                    	productCode:$scope.productCode,
                    	status:$scope.status,
                    	shortDescEn:$scope.shortDescEn,
                    	deliveryMode:$scope.deliveryMode,
                    	dailyInventory:$scope.dailyInventory
                        },
                    url: "./product/productViewList"
                }).success(function(data) {
                	$scope.approvalButtonDisable = false;
                    // START ======================== AngularJS Smart-Table select all rows directive ===============
                    $scope.vm.rowCollection = data;
                    $scope.vm.data = [].concat(data);
                    $scope.vm.selected = [];
                    // END ======================== AngularJS Smart-Table select all rows directive ===============
                    $scope.supplierSelected = null;
                    alertService.cleanAlert();

                }).error(function(data){
					$scope.approvalButtonDisable = false;
				}); 
            }
            
            $scope.searchProduct();

            function checkApprove (pIds,selectNumber,number,pStatus){
            	  var ids = "";
                  $http({
                      method: 'POST',
                      params: {
                     	 pIds: pIds
                      },
                      url: "./product/checkApproveProduct"
        	          }).success(function (data) {
        	        	 if(data.length > 0){
        	        		 for(var i=0 ;i<data.length;i++){
        	        			ids +=  data[i] +",";   	        		 
        	        		 }
        	        	 }	  
//        	              if(ids != ""){
//        	            	  $scope.closeAlert = alertService.closeAlert;
//        	        		  alertService.add('danger', 'The information is not complete for product VPN:'+ids+"not allow to approve.");
//        	        		  $scope.approvalButtonDisable = false;
//        	    			  return; 
//        	              }
        	              
        	              if(selectNumber != number){
        	            	  //alert(123);
        	            	  $http({
        	                      method: 'POST',
        	                      params: {
        	                     	 pIds: pIds,
        	                          pStatus:pStatus,
        	                          pType:'approve'
        	                      },
        	                      url: "./product/updateProductStatus"
        	        	          }).success(function (data) {
//        	        	             
        	        	              if (data != null && data != '') {
        	        	                  if (data['errorType'] != "success") {
        	        	
        	        	                      $scope.closeAlert = alertService.closeAlert;
        	        	                      alertService.add(data["errorType"], data["errorMessage"]);
        	        	                  } else {
        	        	                      $scope.searchProduct();
        	        	                     // $scope.closeAlert = alertService.closeAlert;
        	        	                     // alertService.add("success", "update successful");
        	        	                  }
        	        	
        	        	              }   	          
        	        	            
        	        	          }).error(function(data){
										$scope.approvalButtonDisable = false;
								  });  
        	            	  
        	              }else{
        	            	  $scope.closeAlert = alertService.closeAlert;
        		        		 alertService.add('danger', 'Please select product of Pending for approval status.');
        		        		 $scope.approvalButtonDisable = false;
        		    			 return; 
        	              } 
        	          });
            }
          
            
        }]);
