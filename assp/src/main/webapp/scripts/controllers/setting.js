'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProfileCtrl
 * @description
 * # ProfileCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('SettingsCtrl', ['$scope', '$state', '$http', '$interval', 
             'alertService', 'localStorageService', '$stateParams','T','ngDialog','uiUploader',
          function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog,uiUploader) {
		
        $scope.supplierVo = {};

        $scope.minValueList = [];
        $scope.maxValueList = [];

        $scope.supplierVo.deliveryFee = "";
        $scope.disableDeliveryFee = false;
        
        $scope.supplierVo.freeDeliveryThreshold = "";
        $scope.supplierVo.consignmentVia = "";

        $scope.supplierVo.warehouseDeliLeadTime = "";
        $scope.supplierVo.minDeliveryDay = "";
        $scope.supplierVo.maxDeliveryDay = "";


        
        
        $scope.returnRowCollection = [];
        $scope.brandCurrentData = null;
        $scope.failedReason = null;
        
        
           $scope.page = 10;
        // START ======================== AngularJS Smart-Table select all rows directive ===============
        
            $scope.vm = this;
            //  $window.open('http://www.c-sharpcorner.com/1/302/angularjs.aspx', 'C-Sharpcorner', 'width=500,height=400');
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

            }


            $scope.vm.rowCollection = [];

            //for (id; id < 11; id++) {
            //    $scope.vm.rowCollection.push(generateRandomItem(id));
            //}

            $scope.vm.data = [];// [].concat($scope.vm.rowCollection);
            //END =====================================AngularJS Smart-Table select all rows directive =====================================


        var userRoes = localStorageService.get("userRole");
        var supplierId = localStorageService.get("supplierId");
        
        //User Story 56 -	Switch off functions for soft launch
        ////$scope.softLaunch = true;

        //var switchOff= 'N';
       // if(switchOff == 'Y'){
       	// $scope.softLaunch= false;	
       // }
        $scope.delay = 0;
  		$scope.minDuration = 0;
  		$scope.templateUrl = '';
  		$scope.message = 'Please Wait...';
  		$scope.backdrop = true;
  		$scope.promise = null;
        
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
            
//            $http({
//                method: 'POST',
//                params: {lovId: "786"},
//                url: "./listOfValue/getLovList"
//            }).success(function (data) {
//                $scope.shopCategoryList = data;
//            });
            
            
            $scope.promise = $http({
                method: 'GET',
                params: {supplierId: supplierId},
                url: "./supplier/view"
            }).
            
        	//$http({method: 'GET', url: "./supplier/view"}).
            success(function (data) {
                if (data['errorType'] == "success") {
                    alertService.cleanAlert();
                    $scope.supplierVo = data.returnData;

                    if (data.returnData.deliveryFee != null && data.returnData.deliveryFee != '') {
                        $scope.supplierVo.deliveryFee = data.returnData.deliveryFee;
//                        $scope.supplierVo.deliveryFee2 = data.returnData.deliveryFee;
                    }else{
                       // $scope.supplierVo.deliveryFee = 0;
                      //  $scope.supplierVo.deliveryFee2 = 0;
                    }

                    if (data.returnData.freeDeliveryThreshold != null && data.returnData.freeDeliveryThreshold != '') {
                        $scope.supplierVo.freeDeliveryThreshold = data.returnData.freeDeliveryThreshold;
                       // $scope.supplierVo.freeDeliveryThreshold2 = data.returnData.freeDeliveryThreshold;
                    }else{
                       // $scope.supplierVo.freeDeliveryThreshold = 0;
                       // $scope.supplierVo.freeDeliveryThreshold2 = 0;
                    }

                    $scope.supplierVo.warehouseDeliLeadTime = data.returnData.warehouseDeliLeadTime;
                    //$scope.supplierVo.warehouseDeliLeadTime2 = data.returnData.warehouseDeliLeadTime;

                    if (data.returnData.minDeliveryDay != null) {
                        $scope.supplierVo.minDeliveryDay = data.returnData.minDeliveryDay.toString();
                      //  $scope.supplierVo.minDeliveryDay2 = data.returnData.minDeliveryDay.toString();
                    }
                    if (data.returnData.maxDeliveryDay != null) {
                        $scope.supplierVo.maxDeliveryDay = data.returnData.maxDeliveryDay.toString();
                       // $scope.supplierVo.maxDeliveryDay2 = data.returnData.maxDeliveryDay.toString();
                    }
			
			
                     $scope.failedReason =  data.returnData.failedReason;
					
                     //Disable DeliveryFee
                   	 $scope.disableDeliveryFee = data.returnData.disableDeliveryFee;
                   
                    
						 $scope.returnRowCollection = data.returnData.brandVo;
				   // START ======================== AngularJS Smart-Table select all rows directive ===============
                        $scope.vm.rowCollection = data.returnData.brandVo;
                        $scope.vm.data = [].concat(data.returnData.brandVo);
                        // END ======================== AngularJS Smart-Table select all rows directive ===============
                   

                } else {
                    $scope.closeAlert = alertService.closeAlert;
                    alertService.add(data["errorType"], data["errorMessage"]);
                }
               
            
            });
            
        };

        
        $scope.initValue();

     // open view
        $scope.openView= function (brandCode) {

         	$scope.brandCurrentData = null;
            ngDialog.open({
            	className: 'ngdialog-theme-default custom-width-900',
          		    template: 'views/pages/settingBrandDetail.html',
          		    scope: $scope,
          		    controller:['$scope', '$state', '$http', '$interval', 
				'alertService', 'localStorageService', '$stateParams','T','ngDialog','uiUploader',
			function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog,uiUploader) {
   
          		    	
            $scope.brandCurrentData = null;
            $scope.upload_button = true;
        	$scope.delete_button = false;
        	$scope.image_file1=false	;
//        	$scope.image_file2=false;
          		    	
          	$http({
                method: 'POST',
                params: {brandCode: brandCode},
                url: "./brand/getBrandBycode"
            }).success(function (data) {
            	
                $scope.brandCurrentData = data;
                
                var element = document.getElementById('file1');
              
                element.addEventListener('change', function(e) {
                    var files = e.target.files;
                    uiUploader.addFiles(files);
                    $scope.$apply();
                });
                
                if("undefined"==$scope.brandCurrentData.imageFileName || ""==$scope.brandCurrentData.imageFileName || null==$scope.brandCurrentData.imageFileName){
                	$scope.upload_button = true;
                	$scope.delete_button = false;
                	$scope.image_file1=true;
                	
                	
                }else{
                	$scope.upload_button = false;
                	$scope.delete_button = true;
                	$scope.image_file1=false;
                	
                }

            });
          		    	
          	
          	 $scope.upload = function() {
          		 
          		  
          		$scope.fileUploadObj ={"masterId":$scope.brandCurrentData.masterIdStr,"code":$scope.brandCurrentData.codeStr};
                 uiUploader.startUpload({
                     url: './supplier/upload',
                     concurrency: 2,
                     
                     data: $scope.fileUploadObj ,
                     onProgress: function(file) { $scope.$apply(); },
                     
                     onCompleted: function(file, response) {
                     
                     
                     	var data = eval("(" + response + ")");
                     
                     	
     		        	   if(data["errorType"]=='success'){
     			        		 $scope.supplierVo=data.returnData;
     			        		 $scope.upload_button = false;
     				        	 $scope.delete_button = true;
     				        	 $scope.image_file1=false;
     				        	
     				        	 $scope.brandCurrentData.imageFileName = data.returnData.imageFileName;
     				        	 $scope.$apply();
     	                    }else{
     	                    	
	     	                    $scope.upload_button = true;
			                	$scope.delete_button = false;
			                	$scope.image_file1=true;
			                	$scope.brandCurrentData.imageFileName = null;
     	                    }
     	                    
     		        	alertService.add(data["errorType"], data["errorMessage"]);
                         $scope.$apply();
                        
                     },
                     
                     onCompletedAll:function(file) {
                         $scope.$apply();
                     },
                     onError:function(e) {
                         $scope.$apply();
                      
                     }
                 });
               
             };
      
             $scope.deleteImage=function(){
            	 $scope.object= {};
                 $scope.object.masterId =$scope.brandCurrentData.masterIdStr;
                 $scope.object.code = $scope.brandCurrentData.codeStr;
                 
     	        $http.post('./supplier/delete',$scope.object).
     	        success(function (data) {
     	        
     	            if (data['errorType'] == "success") {
     	                alertService.cleanAlert();
     	                alertService.add(data["errorType"], data["errorMessage"]);
     	            	
        	 	 		$scope.upload_button = true;
	                	$scope.delete_button = false;
	                	$scope.image_file1=true;
	        	 		$scope.brandCurrentData.imageFileName =null;
				        	 
     	                $scope.closeAlert = alertService.closeAlert;
     	                //$state.go("main.myAccount_settings",{}, {reload : true});
     	                
     	                
     	               // $scope.$apply();
     	            } else {
     	                $scope.closeAlert = alertService.closeAlert;
     	                alertService.add(data["errorType"], data["errorMessage"]);
     	            }
     	            
     	            
     	           // alertService.add(data["errorType"], data["errorMessage"]);
                   //  $scope.$apply();
     	        });
             };
             
             
          		    	
          		    $scope.returnConfirmed = function () {
          		    
          		    
          		     $http.post('./supplier/updateBrand', $scope.brandCurrentData).
                          success(function (data) {


                              if (data['errorType'] == "success") {
                                 
                                 alertService.cleanAlert();
		        	             alertService.add(data["errorType"], data["errorMessage"]);
                                   
                                 $scope.closeThisDialog(0);
			             		 ngDialog.closePromise;      
                                 $scope.initValue();

                              } else {
                                  $scope.closeAlert = alertService.closeAlert;
                                  alertService.add(data["errorType"], data["errorMessage"]);
                              }
                          });
          		    	
          		    }
	    		
          		    $scope.cancelReturn = function () {
          		    	
          		    	 $scope.closeThisDialog(0);
			             ngDialog.closePromise;
			             $scope.initValue();
                    }
          		    
                }],
      		    
      		    preCloseCallback: function(value) {
      		     
      		      alertService.cleanAlert();	  
      		      $scope.initValue();       		      
      		    }
            
      		});
      		
        };
        

        $scope.minDeliveryDayChange = function() {
         
         //   $scope.supplierVo.minDeliveryDay2 = $scope.supplierVo.minDeliveryDay;
            jQuery("#error_deliveryDay").html("");
            $scope.closeAlert = alertService.closeAlert;
            alertService.add("warning",  T.T('update_all_the_Supplier_Direct_Deliver_products_info'));
        };

        $scope.maxDeliveryDayChange = function() {
           
          //  $scope.supplierVo.maxDeliveryDay2 = $scope.supplierVo.maxDeliveryDay;
            jQuery("#error_deliveryDay").html("");
            $scope.closeAlert = alertService.closeAlert;
            alertService.add("warning", T.T('update_all_the_Supplier_Direct_Deliver_products_info') );


        };

        $scope.warehouseDeliLeadTimeChange = function() {
            //$scope.supplierVo.warehouseDeliLeadTime2 = $scope.supplierVo.warehouseDeliLeadTime;
            jQuery("#error_warehouseDeliLeadTime").html("");
            alertService.add("warning",T.T('update_all_the_Consignment_via_warehouse_products_info'));

        };


        $scope.save = function () {
            alertService.cleanAlert();

            var error = false;

            $scope.closeAlert = alertService.closeAlert;

            if($scope.supplierVo.deliveryFee!=null && $scope.supplierVo.deliveryFee!='' ){
            if (!judgeOneDecimal($scope.supplierVo.deliveryFee)) {
                jQuery("#error_deliveryFee").html(T.T('user_edit_update_setting_invalid_delivery_fee'));
                
                error = true;
            } else {
                jQuery("#error_deliveryFee").html("");
            }
            }
            
            if($scope.supplierVo.freeDeliveryThreshold!=null && $scope.supplierVo.freeDeliveryThreshold!=''){
            if (!judgeOneDecimal($scope.supplierVo.freeDeliveryThreshold)) {
                jQuery("#error_freeDeliveryThreshold").html(T.T('user_edit_update_setting_invalid_free_delivery_threshold'));
                
                error = true;
            } else {
                jQuery("#error_freeDeliveryThreshold").html("");
            }
            }


            if (!validateInteger($scope.supplierVo.warehouseDeliLeadTime)) {
                jQuery("#error_warehouseDeliLeadTime").html(T.T('user_edit_update_setting_invalid_lead_time_value'));
                
                //alertService.add("danger", "Consignment via warehouse delivery lead time value must be larger than 0 and " +
                //    "length less than or equal to 4");
                error = true;
            } else {
                jQuery("#error_warehouseDeliLeadTime").html("");
            }

            if ($scope.supplierVo.minDeliveryDay != null && $scope.supplierVo.minDeliveryDay != ''
                && $scope.supplierVo.maxDeliveryDay != null && $scope.supplierVo.maxDeliveryDay != '') {
                if (parseFloat($scope.supplierVo.minDeliveryDay )> parseFloat($scope.supplierVo.maxDeliveryDay)) {

                    jQuery("#error_deliveryDay").html(T.T('user_edit_update_setting_invalid_max_min_value'));
                    //alertService.add("danger", "Min. value can't be larger than Max. value");
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
            
            $http.post('./supplier/editSetting', $scope.supplierVo).
                success(function (data) {
                    if (data['errorType'] == "success") {
                        alertService.cleanAlert();
                        alertService.add(data["errorType"], T.T('update_successful'));
                        $scope.closeAlert = alertService.closeAlert;
                        
                        $scope.initValue();

                    } else {

                        $scope.closeAlert = alertService.closeAlert;
                        alertService.add(data["errorType"], data["errorMessage"]);
                    }
                });
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
        
        
        
       
        
    }])
    .
    directive('stRatio', function () {
        return {
            link: function (scope, element, attr) {
                var ratio = +(attr.stRatio);

                element.css('width', ratio + '%');

            }
        };
    });
