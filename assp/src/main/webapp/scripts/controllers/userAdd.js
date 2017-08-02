'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('UserAddCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$stateParams','$q','T',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$stateParams,$q,T) {
    	 $scope.supplierList = null; 
    	 $scope.supplierId = null;
    	 $scope.readDate = false;
    	 $scope.readDateId = "";
    	 $scope.activate2 = "Y";
    	 $scope.brandList = null;
//    	 $scope.categoryList = null;
    	 $scope.deptClassList = null;
    	 $scope.subclassList = [];
    	
    	$scope.delay = 0;
 		$scope.minDuration = 0;
 		$scope.templateUrl = '';
 		$scope.message = 'Please Wait...';
 		$scope.backdrop = true;
 		$scope.promise = null;
 		
 		//START==================  SUPPLIER MESSAGE
 		

         $scope.minValueList = [];
         $scope.maxValueList = [];
         
         $scope.userVo = {}; 
    	 $scope.activate2 = "Y";
    	 
         $scope.userVo.supplierVo = {};
         $scope.userVo.supplierVo.deliveryFee = "";
         
         
         $scope.userVo.supplierVo.freeDeliveryThreshold = "";
         $scope.userVo.supplierVo.consignmentVia = "";

         $scope.userVo.supplierVo.warehouseDeliLeadTime = "";
         $scope.userVo.supplierVo.minDeliveryDay = "";
         $scope.userVo.supplierVo.maxDeliveryDay = "";
         
         $scope.userVo.supplierVo.disableDeliveryFee = false;
         $scope.userVo.supplierVo.failedReason = null;
         
       
 		//END 
         $scope.supplierId2 = null;
    	  
    	 var supplierCode=null;
    	 $http.get('./order/listSupplier').
         success(function (data) {
             $scope.supplierList = data;
         });
    	 
    	 angular.forEach($scope.supplierList, function (supplier) {
     		if(supplier.ticked){
     			supplierCode = supplier;
     		}
         });
    	 
    	 
    	 $scope.initValue = function () {
             $http({
                 method: 'POST',
                 params: {lovId: "785"},
                 url: "./listOfValue/getLovList"
             }).success(function (data) {
                 $scope.minValueList = data;
             });


             $http({
                 method: 'POST',
                 params: {lovId: "786"},
                 url: "./listOfValue/getLovList"
             }).success(function (data) {
                 $scope.maxValueList = data;
             });
             
    	 };

    	 
    	 
    	 $scope.userroles = [{
             code: "",
             description: ''
         },{
             code: "SYSTEMADMIN",
             description: 'SYSTEMADMIN'
         }, {
             code: "APPROVER",
             description: 'APPROVER'
         },
         {
             code: "SUPPLIER",
             description: 'SUPPLIER'
         }]   
    	 //brand start
    	 $scope.brandSelect = [];  
         $scope.brandshowtoHtml = [];  
         $scope.brandtoSelect = [];  
         $scope.brand_from = function() {  
             if ($scope.brandSelect != null) {  
                 for (var i = 0; i < $scope.brandSelect.length; i++) {                       
                     for (var j = 0; j < $scope.brandList.length; j++) {  
                         if ($scope.brandList[j].brandCode == $scope.brandSelect[i].brandCode) {  
                             $scope.brandList.splice(j, 1);  
                         }  
                     };  
                     $scope.brandshowtoHtml.push($scope.brandSelect[i]);  
                   //  $scope.brandshowtoHtml.sort(function(a,b){return a['brandCode']>b['brandCode']?1:-1});
                     
                 }  
             } else {  
                 //Notifier.notifyWarning("至少选中一项"); 
            	 alert("please choose one record.");
             }  
             $scope.brandSelect=[];  
         };  
   
         $scope.brand_to = function() {
//        	 console.log($scope.brandtoSelect);
             if ($scope.brandtoSelect != null) {  
                 for (var i = 0; i < $scope.brandtoSelect.length; i++) {    
                     for (var j = 0; j < $scope.brandshowtoHtml.length; j++) {  
                    	// console.log($scope.brandshowtoHtml[j].brandCode);  
                         if ($scope.brandshowtoHtml[j].brandCode == $scope.brandtoSelect[i].brandCode) {  
                             $scope.brandshowtoHtml.splice(j, 1);  
                         }  
                     };  
                     $scope.brandList.push($scope.brandtoSelect[i]); 
                   //  $scope.brandList.sort(function(a,b){return a['brandCode']>b['brandCode']?1:-1});
                 };  
             } else {  
                 //Notifier.notifyWarning("至少选择一项");
            	 alert("please choose one record.");
             }  
             $scope.typetoSelect=[];  
         }  
         	//brand end
      
         $scope.categorySelect = [];  
         $scope.categorytoHtml = [];   
         $scope.categorytoSelect = null;  
         $scope.category_from = function() {  
             if ($scope.categorySelect != null) {  
                 for (var i = 0; i < $scope.categorySelect.length; i++) {                       
                     for (var j = 0; j < $scope.categoryList.length; j++) {  
                         if ($scope.categoryList[j].lovCode == $scope.categorySelect[i].lovCode) {  
                             $scope.categoryList.splice(j, 1);  
                         }  
                     };  
                     $scope.categorytoHtml.push($scope.categorySelect[i]); 
//                     console.log($scope.categorytoHtml);
                     $scope.categorytoHtml.sort(function(a,b){return a['lovCode']>b['lovCode']?1:-1});
                 }  
             } else {  
                 //Notifier.notifyWarning("至少选中一项"); 
            	 alert("please choose one record.");
             }  
             $scope.categorySelect=[];  
         };  
   
         $scope.category_to = function() {  
             if ($scope.categorytoSelect != null) {  
                 for (var i = 0; i < $scope.categorytoSelect.length; i++) {    
                     for (var j = 0; j < $scope.categorytoHtml.length; j++) {  
                    	 //console.log($scope.categorytoHtml[j].brandCode);  
                         if ($scope.categorytoHtml[j].lovCode == $scope.categorytoSelect[i].lovCode) {  
                             $scope.categorytoHtml.splice(j, 1);  
                         }  
                     };  
                     $scope.categoryList.push($scope.categorytoSelect[i]);  
//                     console.log($scope.categoryList);
                     $scope.categoryList.sort(function(a,b){return a['lovCode']>b['lovCode']?1:-1});
                 };  
             } else {  
                 //Notifier.notifyWarning("至少选择一项");
            	 alert("please choose one record.");
             }  
             $scope.typetoSelect=[];  
         }  
         	//eCategory end
   
        /* function contains(str,arr){
			 for(var i=0;i<arr.length;i++){
				 if(arr[i] == str){
					 return true;
				 }
			 }
			 return false;
		 }*/
         
         //dept class subclass
         $scope.unDeptSelect = [];
         $scope.deptSelect = null;
         $scope.deptToHtml = [];
         
         $scope.unClassSelect = [];
         $scope.classSelect = null;
         $scope.classToHtml = [];
         
         $scope.unSubClassSelect = [];
         $scope.subClassSelect = null;
         $scope.subClassToHtml = [];
         
         $scope.deptClass_from = function() {
        	// console.log($scope.unDeptSelect);
        	 //dept form
        	 if ($scope.unDeptSelect != null) {
                 for (var i = 0; i < $scope.unDeptSelect.length; i++) {
//                     for (var j = 0; j < $scope.deptList.length; j++) {
//                         if ($scope.deptList[j].id == $scope.unDeptSelect[i].id) {
//                             $scope.deptList.splice(j, 1);
//                         }  
//                     };
                	 
                	 //======Sheldon=======
                	 var deptToHtmlFlag=true;
                     for (var k = 0; k < $scope.deptToHtml.length; k++) {
                         if ($scope.deptToHtml[k].id == $scope.unDeptSelect[i].id) {
                        	 deptToHtmlFlag=false;
                         }
                     };
                     
//                     if($scope.unClassSelect!=null && $scope.unSubClassSelect!=null){
//                    	 var deptHasClassFlag=false;
//                    	 for (var k = 0; k < $scope.unClassSelect.length; k++) {
//                    		 if ($scope.unClassSelect[k].deptIdReal == $scope.unDeptSelect[i].id) {
//                    			 for (var l = 0; l < $scope.unSubClassSelect.length; l++) {
//                    				 if ($scope.unSubClassSelect[l].deptIdReal == $scope.unDeptSelect[i].id) {
//                    					 deptHasClassFlag=true;
//                    				 }
//                    			 }
//                    		 }  
//                    	 };
                    	 
                    	 if(deptToHtmlFlag){
                    		 $scope.deptToHtml.push($scope.unDeptSelect[i]);
                    	 }
//                     }
                     
                     
//                     $scope.deptToHtml.push($scope.unDeptSelect[i]);
                     console.log($scope.deptToHtml);
                     $scope.deptToHtml.sort(function(a,b){return a['deptId']>b['deptId']?1:-1});
//                     if($scope.unDeptSelect.length > 0){
//                    	 $scope.classList = [];
//                    	 $scope.subclassList = [];
//                     }
                 }
             } else {  
            	// alert("please choose one record.");
             }
             
            //class from
             if ($scope.unClassSelect != null) {
                 for (var i = 0; i < $scope.unClassSelect.length; i++) {
//                     for (var j = 0; j < $scope.classList.length; j++) {
//                         if ($scope.classList[j].id == $scope.unClassSelect[i].id) {
//                             $scope.classList.splice(j, 1);
//                         }
//                     };
                	 
                	 //======Sheldon=======
                	 var classToHtmlFlag=true;
                     for (var k = 0; k < $scope.classToHtml.length; k++) {
                         if ($scope.classToHtml[k].id == $scope.unClassSelect[i].id) {
                        	 classToHtmlFlag=false;
                         }  
                     };
                     
//                     if($scope.unSubClassSelect!=null){
//                    	 var classHasSubclassFlag=false;
//                    	 for (var k = 0; k < $scope.unSubClassSelect.length; k++) {
//                    		 if ($scope.unSubClassSelect[k].classIdReal == $scope.unClassSelect[i].id) {
//                    			 classHasSubclassFlag=true;
//                    		 }  
//                    	 };
                     
                     
	                     if(classToHtmlFlag){
	                    	 $scope.classToHtml.push($scope.unClassSelect[i]); 
	                     }
//                     }
                	 
                	 
//                     $scope.classToHtml.push($scope.unClassSelect[i]); 
                     $scope.classToHtml.sort(function(a,b){return a['deptId']>b['deptId']?1:-1});
//                     if($scope.unClassSelect.length > 0){
//                    	 $scope.subclassList = [];
//                     }
                     
                     changeClassF();
	             }
                 } else {
            	 //alert("please choose one record.");
             }
             
             //subClass from
             if ($scope.unSubClassSelect != null) {
                 for (var i = 0; i < $scope.unSubClassSelect.length; i++) {
//                     for (var j = 0; j < $scope.subclassList.length; j++) {
//                         if ($scope.subclassList[j].id == $scope.unSubClassSelect[i].id) {
//                             $scope.subclassList.splice(j, 1);
//                         }  
//                     };  
                	 
                	 
                	 
                	 //======Sheldon=======
                	 var subClassToHtmlFlag=true;
                     for (var k = 0; k < $scope.subClassToHtml.length; k++) {
                         if ($scope.subClassToHtml[k].id == $scope.unSubClassSelect[i].id) {
                        	 subClassToHtmlFlag=false;
                         }  
                     };
                     if(subClassToHtmlFlag){
                    	 $scope.subClassToHtml.push($scope.unSubClassSelect[i]);
                     }
                     
//                     $scope.subClassToHtml.push($scope.unSubClassSelect[i]); 
                     $scope.subClassToHtml.sort(function(a,b){return a['deptId']>b['deptId']?1:-1});
                 }  
             } else {  
            	// alert("please choose one record.");
             }

//             $scope.unDeptSelect=[];
//             $scope.unClassSelect=[];
//             $scope.unSubClassSelect=[];
             
             $scope.deptSelect=[];
             $scope.classSelect=[];
             $scope.subClassSelect=[];
             
             
         };  
   
         $scope.deptClass_to = function() {
        	 //console.log($scope.deptSelect);
         	//dept to
         	 if ($scope.deptSelect != null) {
                  for (var i = 0; i < $scope.deptSelect.length; i++) {
                      for (var j = 0; j < $scope.deptToHtml.length; j++) {
                          if ($scope.deptToHtml[j].description == $scope.deptSelect[i].description) {
                         	 
                         	//=====Sheldon : subClassToHtml.splice=========
                              for (var k = $scope.subClassToHtml.length-1; k >=0 ; k--) {
                                  if ($scope.subClassToHtml[k].deptIdReal == $scope.deptToHtml[j].id) {
                                      $scope.subClassToHtml.splice(k, 1);
                                  }
                              }
                              
                              //=====Sheldon : ClassToHtml.splice=========
                              for (var k = $scope.classToHtml.length-1; k >=0 ; k--) {
                             	 if ($scope.classToHtml[k].deptIdReal == $scope.deptToHtml[j].id) {
                             		 $scope.classToHtml.splice(k, 1);
                             	 }
                              }
                              
                              $scope.deptToHtml.splice(j, 1);
                          }  
                      };  
                      if($scope.deptList == null){
                     	 $scope.deptList = [];
                      }
//                      $scope.deptList.push($scope.deptSelect[i]);  
                      //console.log($scope.deptList);
                      $scope.deptList.sort(function(a,b){return a['deptId']>b['deptId']?1:-1});
                  };  
              } else {  
             	 //alert("please choose one record.");
              }  
         	 $scope.typetoSelect=[];
         	 
         	  //class to
         	 if ($scope.classSelect != null) {
                  for (var i = 0; i < $scope.classSelect.length; i++) {
                      for (var j = 0; j < $scope.classToHtml.length; j++) {
                          if ($scope.classToHtml[j].id == $scope.classSelect[i].id) {
                         	 var deptIdReal=$scope.classToHtml[j].deptIdReal;
                          	//=====Sheldon : subClassToHtml.splice=========
                              for (var k = $scope.subClassToHtml.length-1 ; k >=0 ; k--) {
                                  if ($scope.subClassToHtml[k].classIdReal == $scope.classToHtml[j].id) {
                                 	 $scope.subClassToHtml.splice(k, 1);
                                  }
                              }
                              $scope.classToHtml.splice(j, 1);  
                         	 deptSplice(deptIdReal);
                          }  
                      };  
                      if($scope.classList == null){
                     	 $scope.classList = [];
                      }
//                      $scope.classList.push($scope.classSelect[i]);  
                      $scope.classList.sort(function(a,b){return a['id']>b['id']?1:-1});
                  };  
              } else {  
             	 //alert("please choose one record.");
              }  
         	 $scope.typetoSelect=[];
         	 
         	 //subclass to
         	 if ($scope.subClassSelect != null) {
                  for (var i = 0; i < $scope.subClassSelect.length; i++) {
                      for (var j = 0; j < $scope.subClassToHtml.length; j++) {
                    	  
                    	  //去掉一个subClassToHtml
                          if ($scope.subClassToHtml[j].id == $scope.subClassSelect[i].id) {
                         	 var classIdReal=$scope.subClassToHtml[j].classIdReal;
                              $scope.subClassToHtml.splice(j, 1);
                              classSplice(classIdReal);
                          }
                      };
                      if($scope.subclassList == null){
                     	 $scope.subclassList = [];
                      }
                      //console.log($scope.subclassList);
                      $scope.subclassList.sort(function(a,b){return a['id']>b['id']?1:-1});
                  };  
              } else {  
             	// alert("please choose one record.");
              }
         	 
         	 $scope.typetoSelect=[];
         	 
         	changeClassF();
         	 
          }
         //============Sheldon : classSplice==========
         function classSplice(classIdReal){
        	 var subClassInfoFlag=false;
             for (var j = 0; j < $scope.subClassToHtml.length; j++) {
                 if ($scope.subClassToHtml[j].classIdReal == classIdReal) {
                	 subClassInfoFlag=true;
                 }
        	 }
             if(!subClassInfoFlag){
            	 for (var i = $scope.classToHtml.length-1; i >=0; i--) {
            		 if($scope.classToHtml[i].id==classIdReal){
            			 var deptIdReal=$scope.classToHtml[i].deptIdReal;
            			 $scope.classToHtml.splice(i, 1);
            			 deptSplice(deptIdReal);
            		 }
            	 }
             }
         }
         
         //============Sheldon : deptSplice==========
         function deptSplice(deptIdReal){
        	 var classInfoFlag=false;
        	 for (var j = 0; j < $scope.classToHtml.length; j++) {
        		 if ($scope.classToHtml[j].deptIdReal == deptIdReal) {
        			 classInfoFlag=true;
        		 }
        	 }
        	 if(!classInfoFlag){
        		 for (var i = $scope.deptToHtml.length-1; i >=0; i--) {
        			 if($scope.deptToHtml[i].id==deptIdReal){
        				 $scope.deptToHtml.splice(i, 1);
        			 }
        		 }
        	 }
         }
         
            
         
         	//deptClass end
         
    	 
	    $scope.fClick = function( data ) {   
	    	$scope.userVo.supplierId=data.id;
	 	}   
    	 
    
    	//save user
    	 $scope.save= function(id) { 
     		
    		var deferred = $q.defer();
     		if($scope.userVo == ''){
     			$scope.userVo ={};
     			$scope.userVo.supplierId= "";
     		}
//     		console.log($scope.userVo);
     		
     		//=======================================
     		//check supplier 
     		if($scope.userVo.userRole=='SUPPLIER'){
     		 var error = false;
     		 if($scope.userVo.supplierVo.deliveryFee!=null && $scope.userVo.supplierVo.deliveryFee!='' ){
     		 if (!judgeOneDecimal($scope.userVo.supplierVo.deliveryFee)) {
                 jQuery("#error_deliveryFee").html(T.T('user_edit_update_setting_invalid_delivery_fee'));
                 error = true;
             } else {
                 jQuery("#error_deliveryFee").html("");
             }
     		 }
     		 
     		 if($scope.userVo.supplierVo.freeDeliveryThreshold!=null && $scope.userVo.supplierVo.freeDeliveryThreshold!=''){
             if (!judgeOneDecimal($scope.userVo.supplierVo.freeDeliveryThreshold)) {
                 jQuery("#error_freeDeliveryThreshold").html(T.T('user_edit_update_setting_invalid_free_delivery_threshold'));
                 
                 error = true;
             } else {
                 jQuery("#error_freeDeliveryThreshold").html("");
             }
     		 }


             if (!validateInteger($scope.userVo.supplierVo.warehouseDeliLeadTime)) {
                 jQuery("#error_warehouseDeliLeadTime").html(T.T('user_edit_update_setting_invalid_lead_time_value'));
                 error = true;
             } else {
                 jQuery("#error_warehouseDeliLeadTime").html("");
             }

             if ($scope.userVo.supplierVo.minDeliveryDay != null && $scope.userVo.supplierVo.minDeliveryDay != ''
                 && $scope.userVo.supplierVo.maxDeliveryDay != null && $scope.userVo.supplierVo.maxDeliveryDay != '') {
                 if (parseFloat($scope.userVo.supplierVo.minDeliveryDay )> parseFloat($scope.userVo.supplierVo.maxDeliveryDay)) {
                     jQuery("#error_deliveryDay").html(T.T('user_edit_update_setting_invalid_max_min_value'));
                     error = true;
                 } else {
                     jQuery("#error_deliveryDay").html("");
                 }
             } else {
                 jQuery("#error_deliveryDay").html("");
             }


             if (error) {
                 return;
             }
     		}
             //end check===========================================
     		
//     		if(id != null){	
//     			$scope.userVo.brandSelect = [];
//     			$scope.userVo.deptSelect = [];
//         		$scope.userVo.classSelect = [];
//         		$scope.userVo.subClassSelect = [];
//     			$scope.userVo.activate = $scope.activate2;
//     			if($scope.brandshowtoHtml != null){
//         			for (var i = 0; i < $scope.brandshowtoHtml.length; i++) {     
//         				 $scope.userVo.brandSelect.push($scope.brandshowtoHtml[i].brandCode); 
//         			}    
//         		}      
//     			if($scope.deptToHtml != null){
//         			for (var i = 0; i < $scope.deptToHtml.length; i++) {     
//         				 $scope.userVo.deptSelect.push($scope.deptToHtml[i].id); 
//         			}    
//         		}     
//         		if($scope.classToHtml != null){
//         			for (var i = 0; i < $scope.classToHtml.length; i++) {     
//         				 $scope.userVo.classSelect.push($scope.classToHtml[i].deptCLassId); 
//         			}    
//         		}     
//         		if($scope.subClassToHtml != null){
//         			for (var i = 0; i < $scope.subClassToHtml.length; i++) {     
//         				 $scope.userVo.subClassSelect.push($scope.subClassToHtml[i].deptClassSubclassId); 
//         			}    
//         		} 
//         		
//         		console.log($scope.categoryList);
         		//console.log($scope.categorytoHtml );
//         		
//     		}else{
     			$scope.userVo.brandSelect = [];
         		$scope.userVo.categorySelect = []; 
         		$scope.userVo.deptSelect = [];
         		$scope.userVo.classSelect = [];
         		$scope.userVo.subClassSelect = [];
     			$scope.userVo.activate = $scope.activate2;
     		
         		if($scope.brandshowtoHtml != null){
         			for (var i = 0; i < $scope.brandshowtoHtml.length; i++) {     
         				 $scope.userVo.brandSelect.push($scope.brandshowtoHtml[i].brandCode); 
         			}    
         		}      
         		if($scope.categorytoHtml != null){
         			for (var i = 0; i < $scope.categorytoHtml.length; i++) {     
         				 $scope.userVo.categorySelect.push($scope.categorytoHtml[i].lovCode); 
         			}    
         		}     
         		
         		if($scope.deptToHtml != null){
         			for (var i = 0; i < $scope.deptToHtml.length; i++) {     
         				 $scope.userVo.deptSelect.push($scope.deptToHtml[i].id); 
         			}    
         		}     
         		if($scope.classToHtml != null){
         			for (var i = 0; i < $scope.classToHtml.length; i++) {     
         				 $scope.userVo.classSelect.push($scope.classToHtml[i].deptCLassId); 
         			}    
         		}     
         		if($scope.subClassToHtml != null){
         			for (var i = 0; i < $scope.subClassToHtml.length; i++) {     
         				 $scope.userVo.subClassSelect.push($scope.subClassToHtml[i].deptClassSubclassId); 
         			}    
         		}     
     		//}
     		
     
     		 $http.post('./user/saveUser', $scope.userVo).
              	success(function(data) {
            	  deferred.resolve(data);
                   if (data['errorType'] == "success") {
                	   
                  	 $scope.userVo=data.returnData;
                  	 
                  	var returnData = data.returnData['supplierVo'];
                  		if (returnData.minDeliveryDay != null) {
							$scope.userVo.supplierVo.minDeliveryDay = returnData.minDeliveryDay.toString();
						}
						if (returnData.maxDeliveryDay != null) {
							$scope.userVo.supplierVo.maxDeliveryDay = returnData.maxDeliveryDay.toString();
						}
						
						$scope.userVo.supplierVo.disableDeliveryFee = returnData.disableDeliveryFee;
						
//						console.log(returnData.disableDeliveryFee);
                  	
						$scope.supplierId2 = $scope.userVo.supplierId;
						
                       alertService.cleanAlert();
                     	 if(id == null){
                             alertService.add(data["errorType"], "add successful");
                     	 }
                         else{
                      	   alertService.add(data["errorType"], "update successful");
                   }
                   }else {
                     	 $scope.closeAlert = alertService.closeAlert; 
                         alertService.add(data["errorType"], data["errorMessage"]);
                     }     

              }).
              
              error(function(data,status,headers,config){
             	 deferred.reject();     
              });
     		 	$scope.promise = deferred.promise;
            };
     	
			if ($stateParams.userId != null) {
				$scope.initValue();
								$scope.readDate = true;
								$scope.promise = $http({
									method : 'GET',
									params : {userId : $stateParams.userId},
									url : "./user/userDetail"
								}).success(function(data) {
								
									$scope.brandList = null;
									$scope.brandshowtoHtml = null;
									$scope.categoryList = null;
									$scope.categorytoHtml = null;

									$scope.deptList = null;
									$scope.deptToHtml = null;
									$scope.classList = null;
									$scope.classToHtml = null;
									$scope.subclassList = null;
									$scope.subClassToHtml = null;

									
									$scope.userVo = data;
									$scope.brandList = data['brandUnSelectModel'];
									$scope.brandshowtoHtml = data['brandselectModel'];
									$scope.categoryList = data['lovUnSelectVos'];
									
									$scope.categorytoSelect = null;
									$scope.categorytoHtml = data['lovSelectVos'];
									$scope.deptList = data['unDeptSelectModel'];
																				
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

									//var supplierId = data['supplierId'];
									var returnData = data['supplierVo'];

									

									if ( returnData != null) {

//										if (returnData.deliveryFee != null	&& returnData.deliveryFee != '') {
//											//$scope.supplierVo.deliveryFee = returnData.deliveryFee;
//										} else {
//											//$scope.userVo.supplierVo.deliveryFee = 0;
//										}
//
//										if (returnData.freeDeliveryThreshold != null && returnData.freeDeliveryThreshold != '') {
//											//$scope.supplierVo.freeDeliveryThreshold = returnData.freeDeliveryThreshold;
//										} else {
//											//$scope.userVo.supplierVo.freeDeliveryThreshold = 0;
//										}

										//$scope.supplierVo.warehouseDeliLeadTime = returnData.warehouseDeliLeadTime;

										if (returnData.minDeliveryDay != null) {
											$scope.userVo.supplierVo.minDeliveryDay = returnData.minDeliveryDay.toString();

										}
										if (returnData.maxDeliveryDay != null) {
											$scope.userVo.supplierVo.maxDeliveryDay = returnData.maxDeliveryDay.toString();
										}
									}
										
										$scope.supplierId2 = $scope.userVo.supplierId;
									//}
									});
							} else {
								$scope.activate2 = "Y";
								
								$scope.userVo.userRole = "";
							}
     		
	
	 $scope.cancel2 =  function() {
		 $state.go('main.user_view');
	 }

	 
	 $scope.blur = function () {


        		 if($scope.supplierId2==null)
        			 return;


     	   		if($scope.supplierId2 == $scope.userVo.supplierId)
     	   			return;

     	   		if ($scope.userVo.userRole == 'SUPPLIER') {


     	   		$scope.userVo.supplierVo.disableDeliveryFee = false;
     	   		$scope.userVo.supplierVo.failedReason = null;
     	   		
     	   			$scope.promise = $http.get('./user/brandList').
     	            success(function (data) {
     	                $scope.brandList = data;

     	                $scope.brandshowtoHtml =[];


     	            });

     	   		}

             }

//	 $scope.blur = function () {
//		 if($scope.supplierId2==null)
//			 return;
//
//		if($scope.supplierId2 == $scope.userVo.supplierId)
//			return;
//
//		if ($scope.userVo.userRole == 'SUPPLIER') {
////	     $scope.userVo.supplierId= null;
//	         $scope.userVo.supplierVo.disableDeliveryFee = false;
//	         $scope.userVo.supplierVo.failedReason = null;
//		}
//
//     }
	 
		 
$scope.init= function() {	
		 $scope.closeAlert = alertService.closeAlert;
			
			// brand start
			$scope.promise = $http.get(
					'./user/brandList').success(
					function(data) {
						$scope.brandList = data;
					
					});
			// brand end

			// eCategory start
			$scope.promise = $http.get(
					'./user/categoryList').success(
					function(data) {
						$scope.categoryList = data;
				
					});
			// eCategory end

			// dept
				$scope.promise = $http.get(
					'./dept/getAllDeptByDesc').success(
					function(data) {
						$scope.deptList = data;
					});

			$scope.initValue();

			
			 $scope.userVo.supplierVo = {};
	         $scope.userVo.supplierVo.deliveryFee = "";
	         
	         
	         $scope.userVo.supplierVo.freeDeliveryThreshold = "";
	         $scope.userVo.supplierVo.consignmentVia = "";

	         $scope.userVo.supplierVo.warehouseDeliLeadTime = "";
	         $scope.userVo.supplierVo.minDeliveryDay = "";
	         $scope.userVo.supplierVo.maxDeliveryDay = "";
	         
	         $scope.userVo.supplierVo.disableDeliveryFee = false;
	         $scope.userVo.supplierVo.failedReason = null;
	       //  $scope.userVo.supplierId= null;
	         
	         
	         if($stateParams.userId!=null){
	         $scope.promise = $http({
					method : 'GET',
					params : {userId : $stateParams.userId},
					url : "./user/userDetail"
				}).success(function(data) {

					$scope.brandList = null;
					$scope.brandshowtoHtml = null;
					$scope.categoryList = null;
					$scope.categorytoHtml = null;

					$scope.deptList = null;
					$scope.deptToHtml = null;
					$scope.classList = null;
					$scope.classToHtml = null;
					$scope.subclassList = null;
					$scope.subClassToHtml = null;

					$scope.brandList = data['brandUnSelectModel'];
					$scope.categoryList = data['lovUnSelectVos'];
					$scope.deptList = data['unDeptSelectModel'];
					
					
					$scope.brandshowtoHtml =[]; //data['brandselectModel'];
					$scope.categorytoHtml = [];//data['lovSelectVos'];

					$scope.deptToHtml =[];// data['deptSelectModel'];

					$scope.classToHtml = [];//data['classSelectModel'];

					$scope.subClassToHtml = [];//data['subClassSelectModel'];
					
				});
	         
	         }
		 
	 }
	 
	// =========================== select supplier  option.
							$scope.getShowData = function() {

								if ($scope.userVo.userRole == 'SUPPLIER') {

									$scope.init();
							         $scope.userVo.supplierId= null;
								}

							};
	 
	 $scope.changeDept= function (){
		 var dept = $scope.unDeptSelect;
		 var deptId ="";
		 if(dept.length > 0){
			 for(var i=0;i < dept.length;i++){
				 deptId +=dept[i].id+",";
			 }
		 }
		 $http({method:'GET',params:{ deptId:deptId},  url:"./dept/getAllClassByDesc"}).
         success(function(data) {
        	 $scope.classList = data;
         });

        }
	 
//	 $scope.changeClass2= function (){
//		 var classVo = $scope.unClassSelect;
//		 var classId = "";
//		 if(classVo.length > 0){
//			 for(var i=0;i < classVo.length;i++){
//				 classId +=classVo[i].id+",";
//			 }
//		 }
//		 $http({method:'GET',params:{ classId:classId},  url:"./dept/getAllSubClassByDesc"}).
//         success(function(data) {
//        	 $scope.subclassList = data;
//        	 
//        	//======Sheldon=======
//        	 for (var i = 0; i < $scope.subclassList.length; i++) {
//	        	 for (var k = 0; k < $scope.subClassToHtml.length; k++) {
//		        	 if ($scope.subClassToHtml[k].id == $scope.subclassList[i].id) {
//		        		 $scope.subclassList.splice(i, 1);
//		        	 }
//	        	 }
//        	 }
//        	 
////             for (var j = 0; j < $scope.subclassList.length; j++) {
////	  	           if ($scope.subclassList[j].id == $scope.unDeptSelect[i].id) {
////	  	           }  
////	  	       };
//        	 
//         });
//        };
        
	 $scope.changeClass= function (){
		 changeClassF()
        };
        
        function changeClassF(){
        	
        	var classVo = $scope.unClassSelect;
        	var classId = "";
        	if(classVo.length > 0){
        		for(var i=0;i < classVo.length;i++){
        			classId +=classVo[i].id+",";
        		}
        	}
        	$http({method:'GET',params:{ classId:classId},  url:"./dept/getAllSubClassByDesc"}).
        	success(function(data) {
        		$scope.subclassList = data;
        		
        		//======Sheldon=======
        		for (var k = 0; k < $scope.subClassToHtml.length; k++) {
        			for (var i = $scope.subclassList.length-1; i >=0; i--) {
        				if ($scope.subClassToHtml[k].id == $scope.subclassList[i].id) {
        					$scope.subclassList.splice(i, 1);
        				}
        			}
        		}
        	});
        };
        

	 
	 $scope.minDeliveryDayChange = function() {
         
        // $scope.userVo.supplierVo.minDeliveryDay2 = $scope.supplierVo.minDeliveryDay;
         jQuery("#error_deliveryDay").html("");
         $scope.closeAlert = alertService.closeAlert;
         alertService.add("warning",  T.T('update_all_the_Supplier_Direct_Deliver_products_info'));
     };

     $scope.maxDeliveryDayChange = function() {
        
        // $scope.supplierVo.maxDeliveryDay2 = $scope.supplierVo.maxDeliveryDay;
         jQuery("#error_deliveryDay").html("");
         $scope.closeAlert = alertService.closeAlert;
         alertService.add("warning", T.T('update_all_the_Supplier_Direct_Deliver_products_info') );
     };

     $scope.warehouseDeliLeadTimeChange = function() {
        // $scope.supplierVo.warehouseDeliLeadTime2 = $scope.supplierVo.warehouseDeliLeadTime;
         jQuery("#error_warehouseDeliLeadTime").html("");
         alertService.add("warning",T.T('update_all_the_Consignment_via_warehouse_products_info'));

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
         var re = new RegExp("^[+]?([0-9]+(.[0-9]{1})?)$");
         var result = re.test(text);
         if (!result) {
             flag = false;
         }
         return flag;
     }
	 	
		
		
	 
	 
}]);
