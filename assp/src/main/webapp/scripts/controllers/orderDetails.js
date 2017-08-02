'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('OrderDisplayDetailsCtrl', ['$scope', '$state', '$http', '$interval', 
                                             'alertService', 'localStorageService', '$stateParams','T','ngDialog',
        function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog) {
    		$scope.page = 10;
			$scope.open3 = function() {
				$scope.popup3.opened = true;
			};
			$scope.popup3 = {
				opened : false
			};
		
			$scope.open4 = function() {
				$scope.popup4.opened = true;
			};
		
			$scope.popup4 = {
				opened : false
			};


            $scope.formats = ['MM/dd/yyyy', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
            $scope.format = $scope.formats[0];
            $scope.altInputFormats = ['M!/d!/yyyy'];
            

            $scope.popup1 = {
                opened: false
            };

            $scope.open1 = function () {
                $scope.popup1.opened = true;
            };


            function disabled(data) {
                var date = data.date,
                    mode = data.mode;
                return false;//mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
            }

            var userRoes = localStorageService.get("userRole");
            var supplierId = localStorageService.get("supplierId");
            var userName= localStorageService.get("userName");
            
            $scope.visible = false;
            $scope.sheldonvisible = false;
            

            
            $scope.su = false;
            if (userRoes != null && userRoes == 'SUPPLIER') {
                $scope.su= true;
            } else {
                $scope.su = false;
            }
            

			$scope.supplierRole = false;
			if (userRoes != null && userRoes == 'SUPPLIER') {
				$scope.supplierRole = true;
			} else {
				$scope.supplierRole = false;
			}
			

         // start used for update history
			$scope.orderinfo = {};
			$scope.orderinfo.actionTimeFr = null;
			$scope.orderinfo.actionTimeTo = null;
			// end
			
            $scope.rowCollection = [];
            $scope.orderData = null;
            
            //order return request
            $scope.rowReturnEntryCollection = [];
            $scope.orderReturnEntryData = null;
            
            $scope.returnRowCollection = [];
            $scope.orderReturnData = null;
            
            $scope.orderCompRowCollection = [];
            $scope.orderCompData = null;

            $scope.status = null;
            $scope.pickedStatus = null;
            $scope.shippedStatus = null;

            $scope.deliveredStatus = null;
            $scope.waitFordeliveryStatus = false;
            $scope.deliverySuccessList = [
                {name: "Y", label: "Full delivered"},
                {name: "N", label: "Undelivered"}
            ];
            
            $scope.invoiceReadyInd = null;
            $scope.orderType = null;
            $scope.delay = 0;
      		$scope.minDuration = 0;
      		$scope.templateUrl = '';
      		$scope.message = 'Please Wait...';
      		$scope.backdrop = true;
      		$scope.promise = null;
      		
      		$scope.permissionExecut = false;
      		
      		$scope.serialCollection = [
      		  {name: "Y", label: "Full delivered"},
      		  {name: "N", label: "Undelivered"},
      		  {name: "N", label: "Undelivered"},
      		  {name: "N", label: "Undelivered"},
      		  {name: "N", label: "Undelivered"}
      		 ];
      		
      		
            $scope.initSearch = function () {
            	  
            	$scope.promise = $http({
                    method: 'GET',
                    params: {orderId: $stateParams.orderNr},
                    url: "./order/orderDetails"
                }).success(function (data) {
                    $scope.rowCollection = data["entryVoList"];
                    
                  //  console.log('-------------------------------- order entry list---------------');
                //    console.log($scope.rowCollection);
                    $scope.orderData = data
                    $scope.status = data["status"];
                    
//                    console.log($scope.orderData);

                    $scope.pickedStatus = false;
                    $scope.shippedStatus = false;
                    $scope.waitFordeliveryStatus = false;
                    $scope.invoiceReadyInd = false;
                    $scope.deliveredStatus = false;
                    
                    
                    $scope.orderType = data["orderType"]; 
                    
//                    if($scope.orderType=='SUPPLIER_DIRECT_DELIVERY' && userRoes != null && userRoes == 'SUPPLIER')
                    if(userRoes == 'SUPPLIER')
                    	$scope.visible = true;
                    else
                    	$scope.visible = false;
                    

                    if(data["invoiceReadyInd"] == 'Y'){
                    	 $scope.invoiceReadyInd = true;
                    }

                    if ($scope.status == 'NEW' || $scope.status == null || $scope.status == '') {
                        $scope.pickedStatus = true;
                    }

                    else if ($scope.status == 'PICKED') {
                    	if(userRoes == 'SUPPLIER'){
                    		$scope.shippedStatus = false;
                    	}else{
                    		$scope.shippedStatus = true;
                    	}
                    }

                    else if ($scope.status == 'SHIPPED') {
                        $scope.waitFordeliveryStatus = true;
                    }

                    else if ($scope.status == 'DELIVERY_FAILURE' || $scope.status == 'DELIVERY_CONFIRMED') {
                        $scope.deliveredStatus = true;
                    }
                    
                    else if ($scope.status == 'DELIVERY') {
                        $scope.deliveredStatus = true;
                       // console.log("---DLIVERY---");
                    }

                    //PICK
                    if ($scope.pickedStatus) {
                        // picked date default 'Today'
                        $scope.orderData.pickDate = new Date();
                    }

                    //PICK
                    if ($scope.shippedStatus) {
                        // shippedDate date default 'Today'
                        $scope.orderData.consignmentShippedDate = new Date();
                    }

                    //PICK
                    if ($scope.waitFordeliveryStatus) {
                        // deliverDate date default 'Today'
                    }
                    $scope.orderData.deliveryDate = new Date();

                    $scope.orderData['waitForUpdateStatus'] = null;

                    if ($scope.rowCollection != null) {
                        for (var i = 0; i < $scope.rowCollection.length; i++) {
                            var model = $scope.rowCollection[i];
                            if ($scope.status == 'NEW') {
                                model['pickedQty'] = model["qty"];
                            }
                            if ($scope.status == 'SHIPPED') {
                                model['deliveryQty'] = model["pickedQty"];
                            }

                            if($scope.rowCollection[i].returnTotal == null || $scope.rowCollection[i].returnTotal == ""){
	                            var totalNumber = 0 ;
	                            for(var ii=0 ; ii < model.orderEntrySerialList.length ; ii ++){
	                            	if(model.orderEntrySerialList[ii].returnInd == "Y"){
	                            		totalNumber+=1;
	                            	}
	                            }
	                            $scope.rowCollection[i].returnTotal=totalNumber;
 							}
                        }
                    }//end if
                }
                
                );
            	
            	//================================
            	
            	var code =$stateParams.orderNr;
            	  
				$http({
                    method: 'GET',
                    params: {orderId: code},
                    url: "./order/permissionExecut"
                }).success(function (data) {
                	 if(data && $scope.supplierRole ){
                		$scope.permissionExecut = true;
                	 }else if(!$scope.supplierRole && $scope.status == 'SHIPPED'){
                	 	$scope.permissionExecut = true;
                	 }else{
                		$scope.permissionExecut = false;
                	 }
                	 
                });
				
				
				
				
            };

            $scope.initSearch();

            $scope.dateOptions = {
                dateDisabled: disabled,
                formatYear: 'yy',
                maxDate: new Date(2020, 5, 22),
                // minDate: new Date(),
                startingDay: 1
            };

            
            $scope.linkOrigOrder = function (orderNr) {
              
                $state.go('main.orderDisplayDetails', {'orderNr': orderNr});
            };
            
            
            
            
            
//=====================================delivery /pick / ship /cancel  button =========================
//2.======================delivery-==============================		
			$scope.batchConfirmDelivery = function() {

				alertService.cleanAlert();
				var checkSelected = [];

				
				
				
					var model = $scope.orderData;
					
					
					//2.	New rule :If Order Status = 'Cancelled' not allow to 'Delivery Confirm'
					if (model.orderStatus == 'CANCELLED') {
						alertService.add('danger','Cannot delivery the order of cancelled status');
						return;
					}
					
					//Status = ‘Picked’, should NOT display the ‘Delivery Confirm’ button for Admin. 
					if(!$scope.supplierRole &&  model.status != 'SHIPPED'){
						alertService.add('danger',T.T('Please_select_order_of_Shipped_status'));
						return;
					}
					
					// Shop Collection = S  时，Admin 不可以　shippend/delivery ..
					if(!$scope.supplierRole &&  model.deliveryFlag=='S'){
						alertService.add('danger','You can not work on this order (Please check Delivery Status)');
						return;
					}
					
					if (model.status == 'SHIPPED') {
						if($scope.supplierRole &&  model.collectMethod=='Collection Point'){
							alertService.add('danger','You can not work on this order (Please check Delivery Status)');
							return;
						}
						
						checkSelected.push(model);
						
						delivery(checkSelected);
					}else{
						
						var code = model.hybrisOrderId;
//						console.log("code:"+code);
						$scope.promise = $http({
                            method: 'GET',
                            params: {orderId: code},
                            url: "./order/permissionExecut"
                        }).success(function (data) {
                        	
//                            console.log(data);
                            
                            if(data){
                            	checkSelected.push(model);
                            }
                            	
                            delivery(checkSelected);
                            
                        });
		        		
					}
				}
				
		
			
			
			
			//==== use for DELIVER Confirm html
			$scope.deliveryMessage='';
			
			$scope.showTrackId = false;
			$scope.popupOrder =null;
			
			function delivery (checkSelected){

				var popupMsg = T.T("Confirm_following_orders_fully_delivered_today");
				var successMsg = T.T("Confirm_delivered_the_following_orders");
				for (var i = 0; i < checkSelected.length; i++) {
					var model = checkSelected[i];
					
					$scope.popupOrder = model;
					if (model.status == 'SHIPPED' &&  model.collectMethod=='Customer Address'){
						$scope.showTrackId = true;
					}else{
						$scope.showTrackId = false;
					}
					
					var hybrisOrderId = model["hybrisOrderId"];
					if (i == (checkSelected.length - 1) || i == 0) {
						popupMsg = popupMsg + " "+ hybrisOrderId + "";
						successMsg = successMsg + " "+ hybrisOrderId + "";
					} else {
						successMsg = successMsg + ""	+ hybrisOrderId + ",";
					}
				}

				popupMsg = popupMsg + "?";

				
//				console.log("$scope.showTrackId :"+$scope.showTrackId );
				
				$scope.deliveryMessage = popupMsg;
				//=========start===================
				
				// $scope.vm.selected[0].hybrisOrderId
 var templateUrl = "views/pages/orderDeliveryConfirm.html";
	
  ngDialog.open({
    	className: 'ngdialog-theme-default custom-width-400',
	    template: templateUrl,
	    scope: $scope,
	    controller:['$scope', '$state', '$http', '$interval', 
          'alertService', 'localStorageService', '$stateParams','T','ngDialog',
          function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog) {
	    	 
	    	for (var i = 0; i < checkSelected.length; i++) {
				var model = checkSelected[i];
				
				$scope.popupOrder = model;
	    	}
            		    	
	    	$scope.deliveryConfirm = function () {

				alertService.cleanAlert();
				
				if($scope.showTrackId && ($scope.popupOrder.trackId2 == null || $scope.popupOrder.trackId2 == '') ){
					alertService.add('danger','Track ID is required');
					return;
				}

				var waitForUpdateStatus = 'DELIVERY';

				var data = {
					'waitForUpdateStatus' : waitForUpdateStatus,
					'trackId':$scope.popupOrder.trackId2,
					'orderVoList' : checkSelected
				};

				$http.post('./order/batchUpdateForSupplier',data)
					.success( function(data) {

					if (data['errorType'] == "success") {
						$scope.initSearch();
						alertService.add(data["errorType"],successMsg+ " .");
					} else {
						$scope.closeAlert = alertService.closeAlert;
						alertService.add(data["errorType"],data["errorMessage"]);
					}

    		    	 $scope.closeThisDialog(0);
    		    	 ngDialog.closePromise;
					
				});
			} //end save
	        		    	
		    $scope.cancelDeliveryConfirm = function () {
		    	 $scope.closeThisDialog(0);
		    	 ngDialog.closePromise;
		    }
	            		
          }],

        });

	}
			
//	END  delivery==================
            $scope.ship = function () {

                //Validation Ship Date
                //Ship date must be later than or equal to the order pick date and order create date
                var error = false;

                jQuery("#error_shippedDate").html("");
                if ($scope.rowCollection.length > 0) {
                    if ($scope.orderData == null)
                        return;
                    var pickDateLong = $scope.orderData["pickDateLong"];

                    if ($scope.orderData.consignmentShippedDate == null || $scope.orderData.consignmentShippedDate == '') {
                        jQuery("#error_shippedDate").html(T.T('Ship_date_cannot_be_empty'));
                        error = true;
                    }

                    if ($scope.orderData.consignmentShippedDate != null && $scope.orderData.consignmentShippedDate != '' &&
                        pickDateLong != null) {
                        if (toDateString($scope.orderData.consignmentShippedDate) < pickDateLong) {
                            jQuery("#error_shippedDate").html(T.T('validate_ship_date'));
                            error = true;
                        } else {
                            jQuery("#error_shippedDate").html("");
                        }
                    }
                }


                if (error) {
                    return;
                }
                $scope.orderData['waitForUpdateStatus'] = 'SHIPPED';
                var successMsg =  T.T('Confirm_shipped_the_following_orders')+ $scope.orderData["hybrisOrderId"];
                $http.post('./order/updateForSupplier', $scope.orderData).
                success(function (data) {

                    if (data['errorType'] == "success") {
                        $scope.initSearch();
                        alertService.cleanAlert();
                        alertService.add(data["errorType"], successMsg);
                    } else {
                        $scope.closeAlert = alertService.closeAlert;
                        alertService.add(data["errorType"], data["errorMessage"]);
                    }
                });

            };
            
        	$scope.rowResult = [];
        	$scope.dataResult = [];

            $scope.dateOptions2 = {
					dateDisabled : false,
					formatYear : 'yy',
					maxDate : new Date(2020, 5, 22),
					// minDate: new Date(),
					startingDay : 1
				};
            $scope.click_update_history = function() {
				//$scope.search_update_history();
			}

//			$scope.search_update_history = function() {
//
//				if ($stateParams.orderNr == null || $stateParams.orderNr == '')
//					return;
//
//
//				var fr = toDDMMMYYYY($scope.orderinfo.actionTimeFr);
//				var to = toDDMMMYYYY($scope.orderinfo.actionTimeTo);
//
//				if ($scope.orderinfo.actionTimeFr != null
//						&& $scope.orderinfo.actionTimeFr != ''
//						&& $scope.orderinfo.actionTimeTo != null
//						&& $scope.orderinfo.actionTimeTo != '') {
//					if ($scope.orderinfo.actionTimeFr > $scope.orderinfo.actionTimeTo) {
//
//						$scope.closeAlert = alertService.closeAlert;
//						alertService
//								.add('danger',
//										"From date can't be larger than to date");
//						return;
//					}
//				}
//				
//				
//
//				$scope.promise =	$http(
//						{
//							method : 'GET',
//							params : {
//								orderId : $stateParams.orderNr,
//								userId : $scope.orderinfo.userId,
//								action : $scope.orderinfo.action,
//								actionTimeFr : fr,
//								actionTimeTo : to
//							},
//							url : "./orderUpdateHist/viewUpHist"
//						}).success(function(data) {
//						   $scope.rowResult = data;
//						   $scope.dataResult = [].concat(data);
//						   alertService.cleanAlert();
//
//				});
//			};
			
			function toDDMMMYYYY(date) {
				if (date == null || date == '')
					return null;

				var mths = [ "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
						"JUL", "AUG", "SEP", "OCT", "NOV", "DEC" ];
				var d = new Date(date.getTime());
				var dd = d.getDate() < 10 ? "0" + d.getDate() : d
						.getDate().toString();
				var mmm = d.getMonth() + 1;
				var yyyy = d.getFullYear().toString(); // 2011
				// var YY = YYYY.substr(2); // 11

				return yyyy + "-" + mmm + "-" + dd;
			}

			
//======================pick ===================
            $scope.pick = function () {

                jQuery("#error_pickDate").html("");

                if ($scope.rowCollection.length > 0) {
                    if ($scope.orderData == null)
                        return;
                    var orderDatetimeLong = $scope.orderData["orderDatetimeLong"];
                    var error = false;

                    if ($scope.orderData.pickDate == null || $scope.orderData.pickDate == '') {
                        jQuery("#error_pickDate").html(T.T('Pick_date_cannot_be_empty'));
                        error = true;
                    }

                    if ($scope.orderData.pickDate != null && $scope.orderData.pickDate != '' &&  orderDatetimeLong != null) {
                        if (toDateString($scope.orderData.pickDate) < orderDatetimeLong) {
                            jQuery("#error_pickDate").html(T.T('validate_pick_date'));
                            error = true;
                        } else {
                            jQuery("#error_pickDate").html("");
                        }
                    }

                    for (var i = 0; i < $scope.rowCollection.length; i++) {
                        var model = $scope.rowCollection[i];
                        var orderQty = model["qty"];
                        var pickedQty = model["pickedQty"];
                        var id = model["id"];

                        if (!validateInteger(pickedQty) || pickedQty > orderQty) {
                            jQuery("#error_" + id).html(T.T('validate_pick_quantity'));
                            error = true;
                        } else {
                            jQuery("#error_" + id).html("");
                        }
                    }



                    if (error) {
                        return;
                    }

                    $scope.orderData['waitForUpdateStatus'] = 'PICKED';
                    var successMsg = T.T('Confirm_picked_the_following_orders') + $scope.orderData["hybrisOrderId"];
                    $http.post('./order/updateForSupplier', $scope.orderData).
                    success(function (data) {

                        if (data['errorType'] == "success") {
                            $scope.initSearch();
                            alertService.cleanAlert();
                            alertService.add(data["errorType"], successMsg);
                        } else {
                            $scope.closeAlert = alertService.closeAlert;
                            alertService.add(data["errorType"], data["errorMessage"]);
                        }
                    });
                }

            }
            
//======================return confirm ===================
            $scope.returnConfirm = function () {
            	
            	jQuery("#error_pickDate").html("");
            	
            	if ($scope.rowCollection.length > 0) {
            		if ($scope.orderData == null)
            			return;
            		var orderDatetimeLong = $scope.orderData["orderDatetimeLong"];
            		var error = false;
            		
            		if ($scope.orderData.pickDate == null || $scope.orderData.pickDate == '') {
            			jQuery("#error_pickDate").html(T.T('Pick_date_cannot_be_empty'));
            			error = true;
            		}
            		
            		
            		for (var i = 0; i < $scope.rowCollection.length; i++) {
            			var model = $scope.rowCollection[i];
            			var orderQty = model["qty"];
            			var pickedQty = model["pickedQty"];
            			var id = model["id"];
            			
            			if (!validateInteger(pickedQty) || pickedQty > orderQty) {
            				jQuery("#error_" + id).html(T.T('validate_pick_quantity'));
            				error = true;
            			} else {
            				jQuery("#error_" + id).html("");
            			}
            		}
            		
            		if (error) {
            			return;
            		}
            		
            		$scope.orderData['waitForUpdateStatus'] = 'RETURN_CONFIRMED';
            		var successMsg = T.T('Confirm_return_the_following_orders') + $scope.orderData["hybrisOrderId"];
            		$http.post('./order/updateForSupplier', $scope.orderData).
            		success(function (data) {
            			
            			if (data['errorType'] == "success") {
            				$scope.initSearch();
            				alertService.cleanAlert();
            				alertService.add(data["errorType"], successMsg);
            			} else {
            				$scope.closeAlert = alertService.closeAlert;
            				alertService.add(data["errorType"], data["errorMessage"]);
            			}
            		});
            	}
            	
            }

            $scope.singlePrintPdf  = function(invoiceFilename){
            	window.location.href="/pssp/uploadPdfFile?invoiceInds="+invoiceFilename;
            }
            	
            
            $scope.cancel = function () {

            	if($scope.su){
            		$state.go('main.mobile_view_order_history');
            	}else{
            		$state.go('main.view_order_history');
            	}

            }
            
            
            $scope.orderEntry= null;
            
            $scope.skuId= null;
            $scope.supplierName= null;
            $scope.brand= null;
            $scope.productName= null;
            $scope.flag= false;
            
            
            
          
            
            //Add a button to open a dialog box to enter 30 records in the below example.
            $scope.openSerial=function(row,flag){
//            	$scope.openSerial=function(skuId,supplierName,brand,productName,flag,orderEntrySerialList){
            	
            	$scope.orderEntry = row;
//            	$scope.skuId= row.skuId;
//                $scope.supplierName= row.supplierName;
//                $scope.brand= row.brand;
//                $scope.productName=row.productName;
//                $scope.orderEntrySerialList=row.orderEntrySerialList;
                $scope.flag= flag;
                
                changeSerialNos(row);
//                if(flag == 'pqty'){
//                }else if(flag == 'delivery'){
//                	changeReturnSerialNos(row);
//                }
                
                var templateUrl = "views/pages/orderSerialNo.html";
            	if($scope.su){
            		templateUrl = "views/pages/mobileOrderSerialNo.html";
            	}else{
            		templateUrl = "views/pages/orderSerialNo.html";
            	}
            	
            	
            	
            	if(flag == 'pqty'){
            		templateUrl = "views/pages/mobileOrderSerialNo.html";
            	}
            	if(flag == 'delivery'){
            		templateUrl = "views/pages/orderSerialNo.html";
            	}
            	
            	
            	
            	  ngDialog.open({
            		  className: 'ngdialog-theme-default ',
            		    template: templateUrl,
            		    scope: $scope,
            		    controller:['$scope', '$state', '$http', '$interval', 
                                  'alertService', 'localStorageService', '$stateParams','T','ngDialog',
                                  function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog) {
            		    	
        		    	var returnNumber = $scope.orderEntry.returnTotal;
            		    	
        		    	
        		    	
        		    	alertService.cleanAlert();
        		    	
//        		    	if ($scope.orderEntry.pickedQty >= $scope.orderEntry.returnTotal) {
//                    		for(var orReI = 0 ; orReI<$scope.orderEntry.pickedQty ; orReI ++){
//                    			if(orReI < $scope.orderEntry.returnTotal){
//                    				$scope.orderEntry.orderEntrySerialList[orReI].returnInd = 'Y';
//                    			}else{
//                    				$scope.orderEntry.orderEntrySerialList[orReI].returnInd = 'N';
//                    			}
//                    		}
//                    	} else {
//                    	}
            		    	
        		    	$scope.returnConfirmed = function () { }	    	
        		    	
        		    	$scope.addSerialNo = function () {
        		    		if($scope.orderEntry.orderEntrySerialList.length < $scope.orderEntry.qty)
        		    		$scope.orderEntry.orderEntrySerialList.push({});
        		    	}
        		    	
        		    	$scope.deleteSerialNo = function (row) {
        		    		
    						angular.forEach($scope.orderEntry.orderEntrySerialList,
    								function(data, index) {
    									if (data.$$hashKey == row.$$hashKey) {
    										$scope.orderEntry.orderEntrySerialList.splice(index, 1);
    									}
    								});
        		    	}
        		    	
        		    	$scope.saveSerialNo = function () {
        		    		
        			    	for(var serialIn = 0 ; serialIn <$scope.orderEntry.orderEntrySerialList.length ; serialIn++){
        			    		$scope.orderEntry.orderEntrySerialList[serialIn].serialNo =$("#serialNoId_"+serialIn)[0].value;
        			    	}
        			    	
        		    		
        		    		var orderEntrySerialsData={
        		    				orderId : $scope.orderData.hybrisOrderId, 
        		    				entryId : $scope.orderEntry.id, 
        		    				entrySerialNos : $scope.orderEntry.orderEntrySerialList
        		    				};
        		    		
        		    	var totalNumber = 0 ;
  	                   	  for(var i=0 ; i < $scope.orderEntry.orderEntrySerialList.length ; i ++){
  	                   		  if($scope.orderEntry.orderEntrySerialList[i].returnInd == "Y"){
  	                   			totalNumber+=1;
  	                   		  }
  	                   	  }
  		                  
  	                   	  
  	                   	  if($scope.orderEntry.returnTotal!= totalNumber){
  	                   		  
  	                   		 if($scope.status == 'DELIVERY'){
	  	                   		$scope.closeAlert = alertService.closeAlert;
		                        alertService.add("danger", "Return quantity not match!");
		                        return;
  	                   	  	  }
	                      
  	                   	  }
  	                   	  
        		    		
        		    		
        	                $http.post('./order/updateOrderEntrySerialList', orderEntrySerialsData).
        	                success(function (data) {

        	                    if (data['errorType'] == "success") {
//        	                        $scope.initSearch();
        	                        alertService.cleanAlert();
        	                        alertService.add(data["errorType"], "Update serial no successful");
        	                    } else {
        	                        $scope.closeAlert = alertService.closeAlert;
        	                        alertService.add(data["errorType"], data["errorMessage"]);
        	                    }
        	                });
        		    		

           		    	 $scope.closeThisDialog(0);
           		    	 ngDialog.closePromise;
           		    	 
           		    	 var totalNumber = 0 ;
	                   	  for(var i=0 ; i < $scope.orderEntry.orderEntrySerialList.length ; i ++){
	                   		  if($scope.orderEntry.orderEntrySerialList[i].returnInd == "Y"){
	                   			totalNumber+=1;
	                   		  }
	                   	  }
		                   	$scope.orderEntry.returnTotal=totalNumber;
	    		    	}
        		    	
            		    $scope.cancelSerialNo = function () {
            		    	 $scope.closeThisDialog(0);
            		    	 ngDialog.closePromise;
            		    }
            		    
                  }],
        		    
        		    preCloseCallback: function(value) {
        		      alertService.cleanAlert();	   
        		    }
        		});
            	
            }
            
            
            
            $scope.openAdd= function (id) {
            	var templateUrl = "views/pages/orderReturnDetail.html";
            	if($scope.su){
            		templateUrl = "views/pages/mobileOrderReturnDetail.html";
            	}else{
            		templateUrl = "views/pages/orderReturnDetail.html";
            	}
                       
                ngDialog.open({
                	className: 'ngdialog-theme-default custom-width-900',
          		    template:  templateUrl,
          		    scope: $scope,
          		    controller:['$scope', '$state', '$http', '$interval', 
                                'alertService', 'localStorageService', '$stateParams','T','ngDialog',
                                function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog) {
          		    	
          		    //order return request
          	            $scope.rowReturnEntryCollection = [];
          	            $scope.orderReturnEntryData = null;
          		    
          		     $http({
                          method: 'POST',
                          params: {id: id},
                          url: "./order/orderReturnDetail"
                      }).success(function (data) {

                          $scope.rowReturnEntryCollection = data["entryList"];
                          $scope.orderReturnEntryData = data;

                      });
          		    	
          		    	
          		      $scope.returnConfirmed = function () {
          		    	var error = false;
          		    	 //Validation Delivered quantity must be less than or equal to the picked quantity
                          for (var i = 0; i < $scope.rowReturnEntryCollection.length; i++) {
                              var model = $scope.rowReturnEntryCollection[i];
                             
                              var orderQty = model["orderQty"];
                              var expectedQty = model["expectedQty"];
                              var returnReqQty = model["returnReqQty"];
                              var writeOffQty = model["writeOffQty"];
                              var actualCollectedQty = model["actualCollectedQty"];
                              var skuCollectRmk = model["skuCollectRmk"];
                              
                              var id = model["id"];
                            
                              if(expectedQty >0){
                            	  
                            	 // console.log( !validateInteger(actualCollectedQty));
                            	 // console.log( actualCollectedQty);
                            	  if(actualCollectedQty < 0 || !validateInteger(actualCollectedQty)){
	                            		jQuery("#actural_" + id).html(T.T('order_return_actual_collected_Quantity_not_interger'));
	                            		error = true;
                            	  }else if(actualCollectedQty>expectedQty ){
                            		  jQuery("#actural_" + id).html(T.T('order_return_actual_collected_Quantity_invalid'));
	                            		error = true;                            		  
                            	  }
                            	 
                          	  }
                          }

                        
                          
                          if (error) {
                              return;
                          }
                          
                          var successMsg = T.T('Return_Confirmed_Successful') ;
                          $http.post('./order/confirmOrderReturn', $scope.orderReturnEntryData).
                          success(function (data) {
                        	  
                        	 

                              if (data['errorType'] == "success") {
                            	  
                                  $scope.initSearch();
                                  alertService.cleanAlert();
                                  alertService.add(data["errorType"], successMsg);
                                  
                                  $scope.closeThisDialog(0);
         			             ngDialog.closePromise;      
                                  
                              } else {
                                  $scope.closeAlert = alertService.closeAlert;
                                  alertService.add(data["errorType"], data["errorMessage"]);
                              }
                          });
          		      }
	    		
          		    $scope.cancelReturn = function () {
          		    	
          		    	 $scope.closeThisDialog(0);
			             ngDialog.closePromise;

                    }
          		    
                }],
      		    
      		    preCloseCallback: function(value) {
      		      alertService.cleanAlert();	   
      		    }
      		});
	    		
	    	
            };
            
            
            function changeSerialNos(row){
            	var qty = row.pickedQty;
            	if(row.orderEntrySerialList.length < qty){
            		var num = qty - row.orderEntrySerialList.length;
            		for( var a = 0 ; a < num ; a++){
            			row.orderEntrySerialList.push({});
            		}
            	}
            }
//            function changeReturnSerialNos(row){
//            	var qty = row.returnTotal;
//            	if(row.orderEntrySerialList.length < qty){
//            		var num = qty - row.orderEntrySerialList.length;
//            		for( var a = 0 ; a < num ; a++){
//            			row.orderEntrySerialList.push({});
//            		}
//            	}
//            }
            /**
             * ng_blur ng_change
             */
            
            $scope.blur_checkPqty = function(row){checkPqty(row);}
            $scope.blur_returnqty = function(row){checkReQty(row);}
            
            function checkPqty(row){
                if (row.qty < row.pickedQty) {
                    jQuery("#error_"+row.id).html(T.T('validate_pick_qty'));
                    row.pickedQty = null;
                    error = true;
                } else {
                    jQuery("#error_"+row.id).html("");
                }
            }
            
            function checkReQty(row){
            	if (row.pickedQty < row.returnTotal) {
            		jQuery("#errorReturn_"+row.id).html(T.T('validate_return_qty'));
            		row.returnTotal = null;
            		error = true;
            	} else {
            		jQuery("#errorReturn_"+row.id).html("");
            	}
            }
            
            
            
//==================================================================

            function toDateString(date) {
                if (date == null || date == '')
                    return null;

                var d = date;
                var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();
                var mmm = (d.getMonth() + 1) < 10 ? "0" + (d.getMonth() + 1) : (d.getMonth() + 1);
                var yyyy = d.getFullYear().toString();
                return yyyy + mmm + dd;
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

