'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:LoginCtrl
 * @description # LoginCtrl Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'OrderCtrl',
				[
						'$scope',
						'$state',
						'$http',
						'$interval',
						'alertService',
						'localStorageService',
						'$window',
						'$confirm',
						'T',
						'$q',
						'ngDialog',
						function($scope, $state, $http, $interval,
								alertService, localStorageService, $window,
								$confirm, T, $q, ngDialog) {

							// START ======================== AngularJS
							// Smart-Table select all rows directive
							// ===============
							$scope.vm = this;
							// $window.open('http://www.c-sharpcorner.com/1/302/angularjs.aspx',
							// 'C-Sharpcorner', 'width=500,height=400');
							// Declare the array for the selected items
							$scope.vm.selected = [];

							// Function to get data for all selected items
							$scope.vm.selectAll = function(collection) {

								// if there are no items in the 'selected'
								// array,
								// push all elements to 'selected'
								if ($scope.vm.selected.length === 0) {

									angular.forEach(collection, function(val) {

										$scope.vm.selected.push(val);

									});

									// if there are items in the 'selected'
									// array,
									// add only those that ar not
								} else if ($scope.vm.selected.length > 0
										&& $scope.vm.selected.length != $scope.vm.data.length) {

									angular.forEach(collection, function(val) {

										var found = $scope.vm.selected
												.indexOf(val);

										if (found == -1)
											$scope.vm.selected.push(val);

									});

									// Otherwise, remove all items
								} else {

									$scope.vm.selected = [];

								}

							};

							// Function to get data by selecting a single row
							$scope.vm.select = function(id) {

								var found = $scope.vm.selected.indexOf(id);

								if (found == -1)
									$scope.vm.selected.push(id);

								else
									$scope.vm.selected.splice(found, 1);

								// =====Sheldon=====
								var tableTr = document.getElementById('tr' + id.hybrisOrderId);

								var tableInput = tableTr.getElementsByTagName('input')[0];

								if (tableInput.checked == false && found == -1) {
									tableTr.className = 'ng-scope : st-selected';
									tableInput.checked = true;
								} else if (tableInput.checked == true && found != -1) {
									tableTr.className = 'ng-scope : null';
									tableInput.checked = false;
								}

							}

							$scope.vm.rowCollection = [];

							// for (id; id < 11; id++) {
							// $scope.vm.rowCollection.push(generateRandomItem(id));
							// }

							$scope.vm.data = [];// [].concat($scope.vm.rowCollection);
							// END
							// =====================================AngularJS
							// Smart-Table select all rows directive
							// =====================================

							// $scope.$watch('vm.data', function(row) {
							// // get selected row
							// row.filter(function(r) {
							// if (r.isSelected) {
							//         
							// }
							// })
							// }, true);

							// $scope.rowCollection = [];

							$scope.page = 10;
							$scope.visible = false;

							$scope.orderType = null;
							$scope.orderTypeList = null;

							$scope.invoice = null;
							$scope.invoiceList = null;

							$scope.returnRequest = 'N';
							$scope.returnRequestList = null;

							$scope.orerId = null;
							$scope.orderconsignmentID = null;

							$scope.orderstatus = null;
							$scope.orderstatusList = null;

							$scope.orderConsignmentStatus = null;
							$scope.orderConsignmentStatusList = null;

							$scope.orderCreateDateFr = null;
							$scope.orderCreateDateTo = null;
							$scope.shippedDateFr = null;
							$scope.shippedDateTo = null;
							$scope.deliveredDateFr = null;
							$scope.deliveredDateTo = null;

							$scope.supplier = null;
							$scope.supplierList = null;

							$scope.delay = 0;
							$scope.minDuration = 0;
							$scope.templateUrl = '';
							$scope.message = 'Please Wait...';
							$scope.backdrop = true;
							$scope.promise = null;

							$scope.customerName = null;
							

							$http.get('./supplier/listUserSupplier').success(
									function(data) {

										$scope.supplierList = data;
									});

							var userRoes = localStorageService.get("userRole");
							var supplierId = localStorageService
									.get("supplierId");

							var arr = new Array();
							if (userRoes != null
									&& (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
								$scope.visible = true;
								arr.push('NEW');
								arr.push('PICKED');
								arr.push('SHIPPED');
								$scope.orderstatus = arr;

								$scope.orderType = "S";
								

							} else {
								$scope.visible = false;

//								arr.push('NEW');
//								arr.push('PICKED');
//								arr.push('SHIPPED');

								$scope.orderstatus = "";

								$scope.orderType = "S";
								$scope.collectMethod = "";

							}

							$scope.supplierRole = false;
							if (userRoes != null && userRoes == 'SUPPLIER') {
								$scope.supplierRole = true;
							} else {
								$scope.supplierRole = false;
							}

							$scope.formats = [ 'yyyyMMdd','MM/dd/yyyy', 'dd-MMMM-yyyy','yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate' ];
							$scope.format = $scope.formats[0];
							$scope.altInputFormats = [ 'M!/d!/yyyy' ];

							$scope.open1 = function() {
								$scope.popup1.opened = true;
							};
							$scope.open2 = function() {
								$scope.popup2.opened = true;
							};
							$scope.open3 = function() {
								$scope.popup3.opened = true;
							};

							$scope.open4 = function() {
								$scope.popup4.opened = true;
							};

							$scope.open5 = function() {
								$scope.popup5.opened = true;
							};

							$scope.open6 = function() {
								$scope.popup6.opened = true;
							};

							$scope.popup1 = {
								opened : false
							};
							$scope.popup2 = {
								opened : false
							};
							$scope.popup3 = {
								opened : false
							};
							$scope.popup4 = {
								opened : false
							};
							$scope.popup5 = {
								opened : false
							};
							$scope.popup6 = {
								opened : false
							};

							function disabled(data) {
								var date = data.date, mode = data.mode;
								return mode === 'day'
										&& (date.getDay() === 0 || date
												.getDay() === 6);
							}

							$scope.dateOptions = {
								dateDisabled : false,
								formatYear : 'yy',
								maxDate : new Date(2020, 5, 22),
								// minDate: new Date(),
								startingDay : 1
							};

							$scope.orderstatusList = [ {
								name : '',
								label : 'All'
							}, {
								name : "NEW",
								label : "New "
							}, {
								name : "PICKED",
								label : "Picked"
							}, {
								name : "SHIPPED",
								label : "Shipped"
							}, {
								name : "CANCELLED",
								label : "Cancelled "
							}, {
								name : "COMPLETED",
								label : "Completed"
							},

							{
								name : "DELIVERY",
								label : "Delivered"
							},

							];
							
							$scope.collectMethodList = [ {
								name : '',
								label : 'All'
							}, {
								name : "C",
								label : "Collection Point"
							}, {
								name : "S",
								label : "Shop Collection"
							}, {
								name : "D",
								label : "Customer Address"
							}
							
							];

							$scope.orderTypeList = [ {
								name : '',
								label : ''
							}, {
								name : "S",
								label : "Supplier Direct Delivery"
							}, {
								name : "C",
								label : "Consignment"
							} ];

							$scope.invoiceList = [ {
								name : '',
								label : ''
							}, {
								name : "Y",
								label : "Yes"
							}, {
								name : "N",
								label : "No"
							} ];

							$scope.returnRequestList = [ {
								name : '',
								label : ''
							}, {
								name : "Y",
								label : "Yes"
							}, {
								name : "N",
								label : "No"
							} ];

							// $scope.orderConsignmentStatusList = [{name: '',
							// label: ''}, {
							// name: "CONFIRMED",
							// label: "Confirmed"
							// }, {name: "SHIPPED", label: "Shipped"},
							// {name: "CANCELED", label: "Canceled"}];
							// =====================================================================

							$scope.view = function() {
								if ($scope.vm.selected.length != 1) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger', T.T('select_records'));
									return;
								}

//								var orderNr = $scope.vm.selected[0]["id"];
								var orderNr = $scope.vm.selected[0]["hybrisOrderId"];

								alertService.cleanAlert();
								
								if($scope.supplierRole ){
									$state.go('main.mobile_orderDisplayDetails', {'orderNr' : orderNr});
								}else{
									$state.go('main.orderDisplayDetails', {'orderNr' : orderNr});
								}
							};

							// ================  batch Pick
							$scope.batchPick = function() {
								alertService.cleanAlert();
								var checkSelected = [];
								// checkSelected.clear();

								if ($scope.vm.selected.length <= 0) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger',T.T('Please_select_order_New_status'));
									return;
								}

								for (var i = 0; i < $scope.vm.selected.length; i++) {
									var model = $scope.vm.selected[i];
									if (model.status == 'NEW'
//											&& model.orderType == 'SUPPLIER_DIRECT_DELIVERY'
												) {
										checkSelected.push(model);
									}
								}

								if (checkSelected.length <= 0) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger',T.T('Please_select_order_New_status'));
									return;
								}

								var popupMsg = T.T('Confirm_the_following_orders_picked_today');
								var successMsg = T.T('Confirm_picked_the_following_orders');
								for (var i = 0; i < checkSelected.length; i++) {
									var model = checkSelected[i];
									var hybrisOrderId = model["hybrisOrderId"];
									if (i == (checkSelected.length - 1) || i == 0) {
										popupMsg = popupMsg + " "
												+ hybrisOrderId + "";
										successMsg = successMsg + " "
												+ hybrisOrderId + "";
									} else {
										popupMsg = popupMsg + " "
												+ hybrisOrderId + ",";
										successMsg = successMsg + " "
												+ hybrisOrderId + ",";
									}
								}

								popupMsg = popupMsg + "?";

								$confirm({
									text : popupMsg,
									title : T.T('Pick_confirm'),
									ok : T.T('Yes'),
									cancel : T.T('No')
								}).then(function() {

													var waitForUpdateStatus = 'PICKED';

													var data = {
														'waitForUpdateStatus' : waitForUpdateStatus,
														'orderVoList' : checkSelected
													};

													$http.post('./order/batchUpdateForSupplier',data)
															.success(
																	function(
																			data) {

																		if (data['errorType'] == "success") {
																			$scope
																					.searchOrder();
																			alertService
																					.add(
																							data["errorType"],
																							successMsg
																									+ " .");
																		} else {
																			$scope.closeAlert = alertService.closeAlert;
																			alertService
																					.add(
																							data["errorType"],
																							data["errorMessage"]);
																		}

																	});

												});
							};

							
							/**			shipconfirm start				 */
							
							
// ================ batch ship=============================================
							$scope.batchShip = function() {

								alertService.cleanAlert();
								var checkSelected = [];
								// checkSelected.clear();

								if ($scope.vm.selected.length !=1) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger',T.T('Please_select_order_of_Picked_status'));
									return;
								}

								for (var i = 0; i < $scope.vm.selected.length; i++) {
									var model = $scope.vm.selected[i];
									
									console.log(model.status);
									if (model.status == 'PICKED') {
										console.log($scope.visible);
										console.log(model.collectMethod);
										
										if($scope.visible &&  model.collectMethod=='Shop Collection'){
											alertService.add('danger','Collection Point cannot ship the order of Shop Collection');
											return;
										}
										
										checkSelected.push(model);
									}
								}

								if (checkSelected.length <= 0) {
									$scope.closeAlert = alertService.closeAlert;
									alertService
											.add('danger',T.T('Please_select_order_of_Picked_status'));
									return;
								}

								
								//设置提示字符串（提示信息 、 success信息）
								var popupMsg = T.T('Confirm_the_following_orders_are_fully_shipped_today');
								var successMsg = T.T('Confirm_shipped_the_following_orders');
								for (var i = 0; i < checkSelected.length; i++) {
									var model = checkSelected[i];
									var hybrisOrderId = model["hybrisOrderId"];
									if (i == (checkSelected.length - 1) || i == 0) {
										popupMsg = popupMsg + " " + hybrisOrderId + "";
										successMsg = successMsg + " " + hybrisOrderId + "";
									} else {
										popupMsg = popupMsg + " " + hybrisOrderId + ",";
										successMsg = successMsg + " " + hybrisOrderId + ",";
									}
								}

								popupMsg = popupMsg + "?";
								
				                var templateUrl = "views/pages/orderShipConfirm.html";
								
				            	  ngDialog.open({
					                	className: 'ngdialog-theme-default custom-width-400',
				            		    template: templateUrl,
				            		    scope: $scope,
				            		    controller:['$scope', '$state', '$http', '$interval', 
		                                  'alertService', 'localStorageService', '$stateParams','T','ngDialog',
		                                  function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog) {
				            		    	

				                            //promise
				                        	$scope.promise = $http({
				                                method: 'GET',
				                                params: {orderId: $scope.vm.selected[0].hybrisOrderId},
				                                url: "./order/orderShipDetails"
				                            }).success(function (data) {
				                                $scope.rowCollection = data;
				                                $scope.orderShipConfirmDisable=false;
				                                
				                                for(var rI = 0 ; rI < $scope.rowCollection.length ; rI ++){
				                                	if($scope.rowCollection[rI].flag != "Y"){
				                                		$scope.orderShipConfirmDisable=true;
				                                	}
				                                }
				                                
				                            });
				            		    	
					        		    	$scope.shipConfirm = function () {

												alertService.cleanAlert();
												var checkSelected = [];
												// checkSelected.clear();

												if ($scope.vm.selected.length <= 0) {
													$scope.closeAlert = alertService.closeAlert;
													alertService.add('danger',T.T('Please_select_order_of_Picked_status'));
						            		    	 $scope.closeThisDialog(0);
						            		    	 ngDialog.closePromise;
													return;
												}

												for (var i = 0; i < $scope.vm.selected.length; i++) {
													var model = $scope.vm.selected[i];
													if (model.status == 'PICKED'
															//&& model.orderType == 'SUPPLIER_DIRECT_DELIVERY'
																) {
														checkSelected.push(model);
													}
												}

												if (checkSelected.length <= 0) {
													$scope.closeAlert = alertService.closeAlert;
													alertService.add('danger',T.T('Please_select_order_of_Picked_status'));
						            		    	 $scope.closeThisDialog(0);
						            		    	 ngDialog.closePromise;
													return;
												}

												var popupMsg = T.T('Confirm_the_following_orders_are_fully_shipped_today');
												var successMsg = T.T('Confirm_shipped_the_following_orders');
												for (var i = 0; i < checkSelected.length; i++) {
													var model = checkSelected[i];
													var hybrisOrderId = model["hybrisOrderId"];
													if (i == (checkSelected.length - 1) || i == 0) {
														popupMsg = popupMsg + " " + hybrisOrderId + "";
														successMsg = successMsg + " " + hybrisOrderId + "";
													} else {
														popupMsg = popupMsg + " " + hybrisOrderId + ",";
														successMsg = successMsg + " " + hybrisOrderId + ",";
													}
												}

												popupMsg = popupMsg + "?";

//												$confirm({
//													text : popupMsg,
//													title : T.T('Ship_Confirm'),
//													ok : T.T('Yes'),
//													cancel : T.T('No')
//												}) .then( function() {

								var waitForUpdateStatus = 'SHIPPED';

								var data = {
									'waitForUpdateStatus' : waitForUpdateStatus,
									'orderVoList' : checkSelected
								};

								$http.post('./order/batchUpdateForSupplier',data)
									.success( function(data) {

													if (data['errorType'] == "success") {
														$scope.searchOrder();
														// alertService.cleanAlert();
														alertService.add(data["errorType"],successMsg+ " .");
													} else {
														$scope.closeAlert = alertService.closeAlert;
														alertService.add(
																		data["errorType"],
																		data["errorMessage"]);
													}

						            		    	 $scope.closeThisDialog(0);
						            		    	 ngDialog.closePromise;
													
												});

//																});

											}
					        		    	
					        		    	$scope.saveConfirm = function () {
					        	                $http.post('./order/orderUpdateShipDetails', $scope.rowCollection).
					        	                success(function (flag) {
					        	                	
					        	                	//console.log(flag);
					        	                	
					        	                	$scope.orderShipConfirmDisable=flag;
					        	                	
					        	                });

					        		    	}
					            		    $scope.cancelShipConfirm = function () {
					            		    	 $scope.closeThisDialog(0);
					            		    	 ngDialog.closePromise;
					            		    }
					            		    
					            		    $scope.changeOrderShipConfirm = function () {
				                                $scope.orderShipConfirmDisable=true;
					            		    }
					            		    
					                  }],
//					                  preCloseCallback: function(value) {
//				        		      alertService.cleanAlert();	   
//				        		    }
				        		});

							};
							
							
							/**			shipconfirm end				 */
						function toDateString(date) {
				                if (date == null || date == '')
				                    return null;

				                var d = date;
				                var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();
				                var mmm = (d.getMonth() + 1) < 10 ? "0" + (d.getMonth() + 1) : (d.getMonth() + 1);
				                var yyyy = d.getFullYear().toString();
				                return yyyy + mmm + dd;
				            };

				    //======================delivery-==============================		
							$scope.batchConfirmDelivery = function() {

								alertService.cleanAlert();
								var checkSelected = [];

								if ($scope.vm.selected.length !=1) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger',T.T('Please_select_order_of_Shipped_status'));
									return;
								}
								// Shop Collection = S  时，Admin 不可以　shippend/delivery ..
								for (var i = 0; i < $scope.vm.selected.length; i++) {
									var model = $scope.vm.selected[i];
									
									
									if (model.status == 'CANCELLED') {
										alertService.add('danger','Cannot delivery the order of cancelled status');
										return;
									}
																		
									if (model.status == 'SHIPPED') {
										//S	Shop Collection
										if($scope.visible &&  model.collectMethod=='Shop Collection'){
											alertService.add('danger','Collection Point cannot delivery the order of Shop Collection');
											return;
										}
										
										if(!$scope.visible &&  model.collectMethod=='Collection Point'){
											alertService.add('danger','Shop Collection cannot delivery the order of Collection Point');
											return;
										}
										
										checkSelected.push(model);
										
										delivery(checkSelected);
									}else{
										
										var code = model.hybrisOrderId;
										$scope.promise = $http({
			                                method: 'GET',
			                                params: {orderId: code},
			                                url: "./order/permissionExecut"
			                            }).success(function (data) {
			                            	
			                                console.log(data);
			                                
			                                if(data){
			                                	checkSelected.push(model);
			                                }
			                                	
			                                delivery(checkSelected);
			                                
			                            });
						        		
									}
								}
								
							};
							
							function delivery (checkSelected){
								
								

								if (checkSelected.length <= 0) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger',T.T('Please_select_order_of_Shipped_status'));
									return;
								}

								var popupMsg = T.T("Confirm_following_orders_fully_delivered_today");
								var successMsg = T.T("Confirm_delivered_the_following_orders");
								for (var i = 0; i < checkSelected.length; i++) {
									var model = checkSelected[i];
									var hybrisOrderId = model["hybrisOrderId"];
									if (i == (checkSelected.length - 1) || i == 0) {
										popupMsg = popupMsg + " "+ hybrisOrderId + "";
										successMsg = successMsg + " "+ hybrisOrderId + "";
									} else {
										successMsg = successMsg + ""	+ hybrisOrderId + ",";
									}
								}

								popupMsg = popupMsg + "?";

								$confirm({
									text : popupMsg,
									title : T.T('Delivery_Confirm'),
									ok : T.T('Yes'),
									cancel : T.T('No')
								}).then(function() {
										var waitForUpdateStatus = 'DELIVERY';
										var data = {
											'waitForUpdateStatus' : waitForUpdateStatus,
											'orderVoList' : checkSelected
										};

										$http.post('./order/batchUpdateForSupplier',data)
										.success(
												function(data) {

													if (data['errorType'] == "success") {
														$scope.searchOrder();
														// alertService.cleanAlert();
														alertService.add(data["errorType"],successMsg+ " .");
														$scope.vm.selected = [];
													} else {
														$scope.closeAlert = alertService.closeAlert;
														alertService.add(data["errorType"],data["errorMessage"]);
													}
												});

									});
							}
							
							

							$scope.supplierSelected = null;

							function checkData() {

								var error = false;

								var orderCreateDateFrCode = toDDMMMYYYY($scope.orderCreateDateFr);
								var orderCreateDateToCode = toDDMMMYYYY($scope.orderCreateDateTo);

								var jquerycrFr = jQuery("#crfm").val();
								var jquerycrTo = jQuery("#crto").val();

								if (jquerycrFr == '' || jquerycrFr == null) {

								} else if (orderCreateDateFrCode == null) {
									alertService.add('danger',T.T('check_order_create_date_format_fr'));
									error = true;
								}

								if (jquerycrTo == '' || jquerycrTo == null) {

								} else if (orderCreateDateToCode == null) {
									alertService.add('danger',T.T('check_order_create_date_format_to'));
									error = true;
								}

								var shippedDateFrCode = toDDMMMYYYY($scope.shippedDateFr);
								var shippedDateToCode = toDDMMMYYYY($scope.shippedDateTo);

								var jqueryconFr = jQuery("#confm").val();
								var jqueryconTo = jQuery("#conto").val();

								if (jqueryconFr == '' || jqueryconFr == null) {

								} else if (shippedDateFrCode == null) {
									alertService.add('danger',	T.T('check_Order_Consignment_Shipped_Date_format_fr'));
									error = true;
								}

								if (jqueryconTo == '' || jqueryconTo == null) {

								} else if (shippedDateToCode == null) {
									alertService
											.add('danger',T.T('check_Order_Consignment_Shipped_Date_format_to'));
									error = true;
								}

								var deliveredDateFrCode = toDDMMMYYYY($scope.deliveredDateFr);
								var deliveredDateToCode = toDDMMMYYYY($scope.deliveredDateTo);

								var jquerydelFr = jQuery("#delfm").val();
								var jquerydelTo = jQuery("#delto").val();

								if (jquerydelFr == '' || jquerydelFr == null) {

								} else if (deliveredDateFrCode == null) {
									alertService
											.add('danger',	T.T('check_Order_Consignment_Delivered_Date_format_fr'));
									error = true;
								}

								if (jquerydelTo == '' || jquerydelTo == null) {

								} else if (deliveredDateToCode == null) {
									alertService
											.add('danger',T.T('check_Order_Consignment_Delivered_Date_format_to'));
									error = true;
								}

								// console.log("error:" + error);

								return error;

							}

							// search button
							$scope.searchOrder = function() {
								alertService.cleanAlert();
								var deferred = $q.defer();
								/*angular.forEach(
												$scope.supplier,
												function(value, key) {

													if ($scope.supplierSelected == null) {
														$scope.supplierSelected = value.supplierId;
													} else {
														$scope.supplierSelected = $scope.supplierSelected
																+ ","
																+ value.supplierId;
													}
												});

								if (!$scope.visible) {

									$scope.supplierSelected = supplierId;
								}*/
								var orderCreateDateFrCode = toDDMMMYYYY($scope.orderCreateDateFr);
								var orderCreateDateToCode = toDDMMMYYYY($scope.orderCreateDateTo);

								if ($scope.orderCreateDateFr != null
										&& $scope.orderCreateDateFr != ''
										&& $scope.orderCreateDateTo != null
										&& $scope.orderCreateDateTo != '') {
									if ($scope.orderCreateDateFr > $scope.orderCreateDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add('danger',T.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var shippedDateFrCode = toDDMMMYYYY($scope.shippedDateFr);
								var shippedDateToCode = toDDMMMYYYY($scope.shippedDateTo);

								if ($scope.shippedDateFr != null
										&& $scope.shippedDateFr != ''
										&& $scope.shippedDateTo != null
										&& $scope.shippedDateTo != '') {
									if ($scope.shippedDateFr > $scope.shippedDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add('danger',T.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var deliveredDateFrCode = toDDMMMYYYY($scope.deliveredDateFr);
								var deliveredDateToCode = toDDMMMYYYY($scope.deliveredDateTo);
								if ($scope.deliveredDateFr != null
										&& $scope.deliveredDateFr != ''
										&& $scope.deliveredDateTo != null
										&& $scope.deliveredDateTo != '') {
									if ($scope.deliveredDateFr > $scope.deliveredDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService.add('danger',	T.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								if (checkData())
									return;
										
								
								var status = null;
								var collectMethodStatus = null;
								
								if($scope.orderstatus instanceof Array){
									
									status = $scope.orderstatus ;
								}else{
									status = [];
									status.push($scope.orderstatus);
								}
								
								if($scope.collectMethod instanceof Array){
									
									collectMethodStatus = $scope.collectMethod ;
								}else{
									collectMethodStatus = [];
									collectMethodStatus.push($scope.collectMethod);
								}
								
								//console.log(status);
								
								
								$scope.object = {};
								$scope.object.supplier = $scope.supplierSelected;
								$scope.object.orderId = $scope.orerId;
								$scope.object.pickOrderId = $scope.orderconsignmentID;
								$scope.object.orderStatus = status;
								$scope.object.collectMethod = collectMethodStatus;
								$scope.object.orderDateFr = orderCreateDateFrCode;
								$scope.object.orderDateTo = orderCreateDateToCode;
								$scope.object.shippedDateFr = shippedDateFrCode;
								$scope.object.shippedDateTo = shippedDateToCode;
								$scope.object.deliveryDateFr = deliveredDateFrCode;
								$scope.object.deliveryDateTo = deliveredDateToCode;
								$scope.object.orderType = $scope.orderType;
								$scope.object.invoiceReadyInd = $scope.invoice;
								$scope.object.returnRequest = $scope.returnRequest;
								$scope.object.customerName = $scope.customerName;

								// $scope.object. outstandingReturnRequest
								// =$scope.outstandingReturnRequest;

								$http.post('./order/listOrder', $scope.object)
										.success(function(data) {
											deferred.resolve(data);
											// START ========================
											// AngularJS Smart-Table select all
											// rows directive ===============
											$scope.vm.rowCollection = data;
											$scope.vm.data = [].concat(data);
											// END ========================
											// AngularJS Smart-Table select all
											// rows directive ===============
											$scope.supplierSelected = null;

											$scope.vm.selected = [];

										}).

										error(
												function(data, status, headers,
														config) {
													deferred.reject();
												});

								$scope.promise = deferred.promise;
							};

							// export Product
							$scope.report = function() {

								alertService.cleanAlert();

								var deferred = $q.defer();

								angular
										.forEach(
												$scope.supplier,
												function(value, key) {

													if ($scope.supplierSelected == null) {
														$scope.supplierSelected = value.supplierId;
													} else {
														$scope.supplierSelected = $scope.supplierSelected
																+ ","
																+ value.supplierId;
													}

												});

								if (!$scope.visible) {

									$scope.supplierSelected = supplierId;
								}
								var orderCreateDateFrCode = toDDMMMYYYY($scope.orderCreateDateFr);
								var orderCreateDateToCode = toDDMMMYYYY($scope.orderCreateDateTo);

								if ($scope.orderCreateDateFr != null
										&& $scope.orderCreateDateFr != ''
										&& $scope.orderCreateDateTo != null
										&& $scope.orderCreateDateTo != '') {
									if ($scope.orderCreateDateFr > $scope.orderCreateDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var shippedDateFrCode = toDDMMMYYYY($scope.shippedDateFr);
								var shippedDateToCode = toDDMMMYYYY($scope.shippedDateTo);

								if ($scope.shippedDateFr != null
										&& $scope.shippedDateFr != ''
										&& $scope.shippedDateTo != null
										&& $scope.shippedDateTo != '') {
									if ($scope.shippedDateFr > $scope.shippedDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var deliveredDateFrCode = toDDMMMYYYY($scope.deliveredDateFr);
								var deliveredDateToCode = toDDMMMYYYY($scope.deliveredDateTo);
								if ($scope.deliveredDateFr != null
										&& $scope.deliveredDateFr != ''
										&& $scope.deliveredDateTo != null
										&& $scope.deliveredDateTo != '') {
									if ($scope.deliveredDateFr > $scope.deliveredDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								$scope.object = {};
								$scope.object.supplier = $scope.supplierSelected;
								$scope.object.orderId = $scope.orerId;
								$scope.object.pickOrderId = $scope.orderconsignmentID;
								$scope.object.orderStatus = $scope.orderstatus;
								$scope.object.orderDateFr = orderCreateDateFrCode;
								$scope.object.orderDateTo = orderCreateDateToCode;
								$scope.object.shippedDateFr = shippedDateFrCode;
								$scope.object.shippedDateTo = shippedDateToCode;
								$scope.object.deliveryDateFr = deliveredDateFrCode;
								$scope.object.deliveryDateTo = deliveredDateToCode;
								$scope.object.orderType = $scope.orderType;
								$scope.object.invoiceReadyInd = $scope.invoice;
								$scope.object.returnRequest = $scope.returnRequest;

								$http
										.post('./order/exportOrder',
												$scope.object)
										.success(
												function(data) {

													deferred.resolve(data);
													var byteCharacters = atob(data.fileContent);

													var byteNumbers = new Array(
															byteCharacters.length);
													for (var i = 0; i < byteCharacters.length; i++) {
														byteNumbers[i] = byteCharacters
																.charCodeAt(i);
													}

													var byteArray = new Uint8Array(
															byteNumbers);
													// console.log(byteArray);
													var blob = new Blob(
															[ byteArray ],
															{
																type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
															});

													saveAs(
															blob,
															"order_sales_export_"
																	+ new Date()
																			.getTime()
																	+ ".xlsx");

												}).

										error(
												function(data, status, headers,
														config) {
													deferred.reject();
												});

								$scope.promise = deferred.promise;
							}

							// export Product
							$scope.reportReturn = function() {

								alertService.cleanAlert();

								var deferred = $q.defer();
								angular
										.forEach(
												$scope.supplier,
												function(value, key) {

													if ($scope.supplierSelected == null) {
														$scope.supplierSelected = value.supplierId;
													} else {
														$scope.supplierSelected = $scope.supplierSelected
																+ ","
																+ value.supplierId;
													}

												});

								if (!$scope.visible) {

									$scope.supplierSelected = supplierId;
								}
								var orderCreateDateFrCode = toDDMMMYYYY($scope.orderCreateDateFr);
								var orderCreateDateToCode = toDDMMMYYYY($scope.orderCreateDateTo);

								if ($scope.orderCreateDateFr != null
										&& $scope.orderCreateDateFr != ''
										&& $scope.orderCreateDateTo != null
										&& $scope.orderCreateDateTo != '') {
									if ($scope.orderCreateDateFr > $scope.orderCreateDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var shippedDateFrCode = toDDMMMYYYY($scope.shippedDateFr);
								var shippedDateToCode = toDDMMMYYYY($scope.shippedDateTo);

								if ($scope.shippedDateFr != null
										&& $scope.shippedDateFr != ''
										&& $scope.shippedDateTo != null
										&& $scope.shippedDateTo != '') {
									if ($scope.shippedDateFr > $scope.shippedDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								var deliveredDateFrCode = toDDMMMYYYY($scope.deliveredDateFr);
								var deliveredDateToCode = toDDMMMYYYY($scope.deliveredDateTo);
								if ($scope.deliveredDateFr != null
										&& $scope.deliveredDateFr != ''
										&& $scope.deliveredDateTo != null
										&& $scope.deliveredDateTo != '') {
									if ($scope.deliveredDateFr > $scope.deliveredDateTo) {

										$scope.closeAlert = alertService.closeAlert;
										alertService
												.add(
														'danger',
														T
																.T("From_date_can_not_larger_than_to_date"));
										return;
									}
								}

								$scope.object = {};
								$scope.object.supplier = $scope.supplierSelected;
								$scope.object.orderId = $scope.orerId;
								$scope.object.pickOrderId = $scope.orderconsignmentID;
								$scope.object.orderStatus = $scope.orderstatus;
								$scope.object.orderDateFr = orderCreateDateFrCode;
								$scope.object.orderDateTo = orderCreateDateToCode;
								$scope.object.shippedDateFr = shippedDateFrCode;
								$scope.object.shippedDateTo = shippedDateToCode;
								$scope.object.deliveryDateFr = deliveredDateFrCode;
								$scope.object.deliveryDateTo = deliveredDateToCode;
								$scope.object.orderType = $scope.orderType;
								$scope.object.invoiceReadyInd = $scope.invoice;
								$scope.object.returnRequest = $scope.returnRequest;

								$http
										.post('./order/exportOrderReturn',
												$scope.object)
										.success(
												function(data) {
													deferred.resolve(data);

													var byteCharacters = atob(data.fileContent);

													var byteNumbers = new Array(
															byteCharacters.length);
													for (var i = 0; i < byteCharacters.length; i++) {
														byteNumbers[i] = byteCharacters
																.charCodeAt(i);
													}

													var byteArray = new Uint8Array(
															byteNumbers);
													// console.log(byteArray);
													var blob = new Blob(
															[ byteArray ],
															{
																type : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
															});

													saveAs(
															blob,
															"order_return_export_"
																	+ new Date()
																			.getTime()
																	+ ".xlsx");

												}).

										error(
												function(data, status, headers,
														config) {
													deferred.reject();
												});

								$scope.promise = deferred.promise;

							}

							// 2016/3/26
							$scope.printPdf = function() {

								if ($scope.vm.selected.length < 1) {
									$scope.closeAlert = alertService.closeAlert;
									alertService.add('danger', T
											.T('select_records'));
									return;
								}
								var invoiceInds = "";
								var selectNumber = $scope.vm.selected.length;
								var number = 0;
								for (var i = 0; i < $scope.vm.selected.length; i++) {
									var invoiceFilename = $scope.vm.selected[i]["invoiceFilename"];
									var invoiceInd = $scope.vm.selected[i]["invoiceReadyInd"];
									if (invoiceInd == 'Y') {
										invoiceInds = invoiceInds
												+ $scope.vm.selected[i]["invoiceFilename"]
												+ ",";
									} else {
										number++;
									}
								}

								// alert(invoiceInds);
								if (selectNumber != number) {
									window.location.href = "/assp/uploadPdfFile?invoiceInds="
											+ invoiceInds;
								} else {
									$scope.closeAlert = alertService.closeAlert;
									alertService
											.add(
													'danger',
													T
															.T('Invoice_Delivery_Note_selected_order_not_readied'));
									return;
								}

							}

							$scope.singlePrintPdf = function(invoiceFilename) {
								window.location.href = "/pssp/uploadPdfFile?invoiceInds="
										+ invoiceFilename;
							};

							$scope.searchOrder();
							function toDDMMMYYYY(date) {
								if (date == null || date == '')
									return null;

								var mths = [ "JAN", "FEB", "MAR", "APR", "MAY",
										"JUN", "JUL", "AUG", "SEP", "OCT",
										"NOV", "DEC" ];
								var d = new Date(date.getTime());
								var dd = d.getDate() < 10 ? "0" + d.getDate()
										: d.getDate().toString();
								var mmm = d.getMonth() + 1;
								var yyyy = d.getFullYear().toString(); // 2011
								

								return yyyy + "-" + mmm + "-" + dd;
							}

						} ]).directive('stRatio', function() {
			return {
				link : function(scope, element, attr) {
					var ratio = +(attr.stRatio);

					element.css('width', ratio + '%');

				}
			};
		});
