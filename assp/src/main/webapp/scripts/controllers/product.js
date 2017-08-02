'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductCtrl
 * @description # ProductCtrl Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'ProductCtrl',
				function($scope, $state, $http, localStorageService,
						alertService, uiUploader, $stateParams, $window, T,$q) {
					var loadflag = false;
					localStorageService.set("baseProductId", "");
					$scope.loadImages = function() {
						setImageDrog();
					}
					

					$scope.loadSwatchImages = function() {
						setSwatchImageDrog();
					}

					$scope.approvalButtonDisable = false;
					$scope.page = 10;
					$scope.productinfo = {};
					$scope.productinfo.imagesList = [];
					$scope.productinfo.swatchImagesList = [];
					$scope.supplierCode = null;
					$scope.delete_button = false;
					$scope.cancel_button = true;
					$scope.save_button = true;
					$scope.add_item_num_type_div = true;
					$scope.submit_for_approve_button = false;
					$scope.approve_button = false;
					$scope.reject_button = false;
					$scope.delete_item_num_type = true;
					$scope.supplierList = null;
					$scope.depts = null;
					$scope.productinfo.dept = null;
					$scope.productinfo.clazz = null;
					$scope.classes = null;
					$scope.productinfo.subClass = null;
					$scope.productinfo.brandCode = null;
					$scope.subClasses = null;
					$scope.estoreCategoryId = null;
					$scope.edit_product_id = null;
					$scope.rowCollection = [];
					$scope.product_list_div = false;
					$scope.add_product = false;
					$scope.subProductInfoList = [];
					$scope.productinfo.barcodeList = [];
					$scope.productinfo.barcodeList.push({});

					$scope.supplierlist_div = false;
					$scope.supplier_name_div = false;

					$scope.product_approval_lead_time = null;
					$scope.dailyInventoryReadonly=true;
					
					$scope.productCodeShow = false;
					$scope.confirmUpdateButton = false;
					$scope.ignoreUpdateButton = false;
					$scope.onlineUpdatedMsg = false;
					$scope.minReplenishmentLevelReadonly=true;
					$scope.maxReplenishmentLevelReadonly=true;

					// start used for update history
					$scope.productId_staging_for_history = null;
					$scope.productinfo.userId = null;
					$scope.productinfo.action = null;
					$scope.productinfo.actionTimeFr = null;
					$scope.productinfo.actionTimeTo = null;
					// end

					$scope.productinfo.consignmentCalBasis='N';
					$scope.productinfo.pnsOnlineDeliveryType='1';
					
					$scope.failedReason = null;
				      $scope.delay = 0;
			      		$scope.minDuration = 0;
			      		$scope.templateUrl = '';
			      		$scope.message = 'Please Wait...';
			      		$scope.backdrop = true;
			      		$scope.promise = null;
			      		
			      		
			      		$scope.isCollapsed = false;
			      		
			      		 
			      		$scope.collapsed = function(){
			      	
			      		  
			      		  if($scope.isCollapsed )
			      			$scope.isCollapsed  = false;
			      		  else if(!$scope.isCollapsed )
			      			$scope.isCollapsed  = true;
			      	
			      		}
			      		
					
					$scope.open1 = function() {
						$scope.popup1.opened = true;
					};
					$scope.popup1 = {
						opened : false
					};
					$scope.open2 = function() {
						$scope.popup2.opened = true;
					};
					$scope.popup2 = {
						opened : false
					};

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

					$scope.formats = [ 'MM/dd/yyyy', 'dd-MMMM-yyyy',
							'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate' ];
					$scope.format = $scope.formats[0];
					$scope.altInputFormats = [ 'M!/d!/yyyy' ];

					$http({
						method : 'GET',
						url : "./product/getProductApprovalLeadTime"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.product_approval_lead_time = parseInt(data.returnData) + 1;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}
									});

					function disabled(data) {
						var product_approval_lead_time = 1;
						if ($scope.product_approval_lead_time != null) {
							product_approval_lead_time = $scope.product_approval_lead_time;
						}

						var date = data.date, mode = data.mode;
						var today = new Date();
						var t = new Date(today);
						var tm = new Date(t.getFullYear(), t.getMonth(), t
								.getDate()
								+ product_approval_lead_time);
						var year = tm.getFullYear();
						var month = tm.getMonth() + 1; // 鐠佹澘绶辫ぐ鎾冲閺堝牊妲哥憰锟�+1閻拷
						var dt = tm.getDate();
						var new_date1 = new Date(year, month - 1, dt);

						var d = new Date(date.getTime());
						var dd = d.getDate() < 10 ? "0" + d.getDate() : d
								.getDate().toString();
						var mmm = d.getMonth() + 1;
						var yyyy = d.getFullYear().toString();
						var new_date2 = new Date(yyyy, mmm - 1, dd);
						if (new_date1 > new_date2) {
							return true;
						} else {
							return false;
						}
					}

					$scope.dateOptions = {
						dateDisabled : disabled,
						formatYear : 'yy',
						maxDate : new Date(2020, 5, 22),
						// minDate: new Date(),
						startingDay : 1
					};

					//=========Sheldon=========
					
			    	$http({method:'GET',url:"./deliveryDate/getDeliveryDate"}).
			        success(function(data) {
			        	$scope.deliveryDateModel = data.deliveryDate;
			        	$scope.minDeliverDateList = data.minDeliverDate;
			        	$scope.maxDeliverDateList = data.maxDeliverDate;
			        });
			    	
			    	
			    	$http({method:'GET',url:"./deliveryDate/getDeliveryDateDefault"}).
			    	success(function(data) {
			    		$scope.deliveryDateModel = data.deliveryDate;
			    		$scope.minDeliverDateDefault = data.minDeliverDateDefault;
			    		$scope.maxDeliverDateDefault = data.maxDeliverDateDefault;
			    	});
			    	
			    	
					$http.get('./supplier/listUserSupplier').success(
							function(data) {
								$scope.supplierList = data;
							});

//					var userRoes = localStorageService.get("userRole");
				
					var supplierId = localStorageService.get("supplierId");
					
					var user = localStorageService.get("user");

					var mallId  =user.mallId;
					var shopId =user.shopId;
					var userRose = user.roleCode;
					$scope.estoreCategorys = {};
					
					
					if(shopId!=null){
						userRose = "SUPPLIER";
					}
					
					var userRoes = userRose;//localStorageService.get("userRole");
					
					if (userRose == 'SYSTEMADMIN' ||userRoes == 'APPROVER'|| userRoes == 'MALL'||mallId!=null ) {
						$scope.supplierlist_div = true;
					
					}else{
						$scope.supplierlist_div = false;
						$scope.supplierCode = shopId;
						
						if(user.shop!=null){
							$scope.productinfo.supplierCode = user.shop.shopCode;
							$scope.productinfo.supplierName = user.shop.shopName;
						}
					}
					
//					if (userRoes != null && (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
//						$scope.supplierlist_div = true;
//						if ($stateParams.productId == null) {
//							$scope.consignmentCalBasisReadonly = false;
//							addProductReadonlyForAp();
//						}
//					} else {
//						$scope.supplierlist_div = false;
//						$scope.supplierCode = supplierId;
//						getBrands(supplierId);
//						getCategorys(supplierId);
//						getDepts(supplierId, null);
//						if ($stateParams.productId == null) {
//							$scope.consignmentCalBasisReadonly = false;
//							addProductReadonlyForSu();
//						}
//					}

					$scope.fClick = function(data) {
						$scope.supplierCode = data.id;
					
//						getBrands($scope.supplierCode);
					//	getCategorys($scope.supplierCode);
//						$scope.productinfo.supplierLeadTime = null;
//						$scope.productinfo.minDeliverDate = null;
//						$scope.productinfo.maxDeliverDate = null;
//						$scope.productinfo.deliveryMode = null;
//						getDepts($scope.supplierCode, null);
					}

					$scope.chooseCategory = function(data) {
						$scope.estoreCategoryId = data.lovValue;
					}

					if ($stateParams.productId != null) {
						$scope.edit_product_id = $stateParams.productId;
						$scope.productId_staging_for_history = $stateParams.productId;
						editProduct($stateParams.productId, 1);
					}

					$scope.get_staging_version_product = function() {
						$scope.loadflag = false;
						var product_id = $scope.productinfo.stagingProductId;
					//	console.log(product_id);
						if (!(typeof ($scope.productinfo.stagingProductId) == "undefined")
								&& $scope.productinfo.stagingProductId != null) {
							editProduct(product_id, 1);
						}
					}

					function editProduct(productId, type) {

						// set stating product id for update hostory
						$scope.productId_staging_for_history = productId;

						$scope.showOnline = false;
						$scope.productCodeShow = true;
						jQuery("#update_history").css("display", "block");
						$scope.depts = [];
						$scope.classes = [];
						$scope.subClasses = [];
						$scope.promise =	$http({
							method : 'GET',
							params : {
								productId : productId
							},
							url : "./product/initEditProductDetail"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
									
												$scope.productinfo = {};
												$scope.productinfo = data.returnData;
											//	console.log(data.returnData);
												$scope.supplierCode=$scope.productinfo.supplierCode;

												if (type == 1) {
													$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
													$scope.rowCollection = $scope.productinfo.subProductInfoList;
												}
												if (data.returnData.hasOnline) {
													$scope.showOnline = true;
													readonlyEdit();
												} else {
													$scope.showOnline = false;
													canEdit();
												}
												$scope.delete_product_id = data.returnData.id;
												
												if ($scope.productinfo.deliveryMode == "C") {
													$scope.minReplenishmentLevelReadonly=false;
													$scope.maxReplenishmentLevelReadonly=false;
													$scope.deliveryModeReadonly=false;
													$scope.dailyInventoryReadonly=true;
													$scope.onlineDateReadonly=false;
													//==========Sheldon==============
//													$scope.productinfo.minDeliverDate = $scope.productinfo.minDeliverDate.toString();
//													$scope.productinfo.maxDeliverDate = $scope.productinfo.maxDeliverDate.toString();
//													$scope.productinfo.minDeliverDate = $scope.minDeliverDateDefault;
//													$scope.productinfo.maxDeliverDate = $scope.maxDeliverDateDefault;
//													$scope.productinfo.minDeliverDate = "3";
//													$scope.productinfo.maxDeliverDate = 9;
													
													
												}else{
													$scope.minReplenishmentLevelReadonly=true;
													$scope.maxReplenishmentLevelReadonly=true;
													$scope.deliveryModeReadonly=true;
													$scope.dailyInventoryReadonly=false;
													$scope.onlineDateReadonly=true;
												}
												
												if ($scope.productinfo.smallExpensive == "Y") {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}

												if (data.returnData.productCode != ""
														&& data.returnData.onlineUpdatedInd == "Y") {
													$scope.confirmUpdateButton = true;
													$scope.ignoreUpdateButton = true;
													$scope.onlineUpdatedMsg = true;
												} else {
													$scope.confirmUpdateButton = false;
													$scope.ignoreUpdateButton = false;
													$scope.onlineUpdatedMsg = false;
												}
												

//												getBrands(data.returnData.supplierCode);
//												getCategorys(data.returnData.supplierCode);
//												
//												getDepts(
//														data.returnData.supplierCode,
//														data.returnData.dept);
												
												if (data.returnData.dept != null) {
												
													getClasses(
															data.returnData.supplierCode,
															data.returnData.dept);
												}
												if (data.returnData.clazz != null) {
													getSubClasses(
															$scope.productinfo.supplierCode,
															data.returnData.clazz);
												}
											

												$scope.product_list_div = true;
												$scope.add_product = true;
												$scope.supplierlist_div = false;

												$scope.supplier_name_div = true;
												$scope.add_item_num_type_div = true;
												$scope.delete_item_num_type = true;
												
											
												isDisplayButton(
														data.returnData.status,
														data.returnData.hasOnline);
												if (userRoes != null && (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
													setHighLightFileds($scope.productinfo.productUpFiledList,$scope.productinfo.productImagesUpFiledList);
													editProductReadonlyForAp($scope.productinfo.retekLastApprovedDate);
												}else{
													editProductReadonlyForSu($scope.productinfo.retekLastApprovedDate);
												}
												
												
												if ($scope.productinfo.baseProductId == null) {
													localStorageService.set("baseProductId",
															data.returnData.id);
												} else {
													$scope.brandReadonly=true;
													$scope.variantOnReadonly=true;
													localStorageService.set(
																	"baseProductId",
																	data.returnData.baseProductId);
												}
												
												if ($scope.productinfo.deliveryMode == "D") {
													$scope.minOrderQtyReadonly=true;
													
												}else if ($scope.productinfo.deliveryMode == "W") {
													$scope.minOrderQtyReadonly=true;
												}else{
													$scope.minOrderQtyReadonly=false;
												}
												
													alertService.cleanAlert();
													
													if(data.returnData.productStatus=='UNAPPROVED_IN_RETEK' ){
														var failed_reason=data.returnData.failedReason;
														$scope.failedReason = failed_reason;
													}
													

											} else {
													$scope.closeAlert = alertService.closeAlert;
													alertService.add(data["errorType"],data["errorMessage"]);
											}
										});
					}
					function setHighLightFileds(productUpFiledList,
							productImagesUpFiledList) {
						angular
								.forEach(
										productImagesUpFiledList,
										function(productImagesUpFieldModel) {
											if (productImagesUpFieldModel.imageType == "PRODUCT_IMAGE") {
												jQuery("#product_image")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productImagesUpFieldModel.imageType == "SWATCH_IMAGE") {
												jQuery("#swatchImage")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
										});
						angular
								.forEach(
										productUpFiledList,
										function(productUpFieldModel) {
											//console.log(productUpFieldModel);
											if (productUpFieldModel.columnName == "SHORT_DESC_EN") {
												jQuery("#shortDescEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "SHORT_DESC_TC") {
												jQuery("#shortDescTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "SHORT_DESC_SC") {
												jQuery("#shortDescSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "LONG_DESC_EN") {
												jQuery("#longDescEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "LONG_DESC_TC") {
												jQuery("#longDescTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "LONG_DESC_SC") {
												jQuery("#longDescSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_USAGE_EN") {
												jQuery("#productUsageEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_USAGE_TC") {
												jQuery("#productUsageTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_USAGE_SC") {
												jQuery("#productUsageSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_WARNINGS_EN") {
												jQuery("#productWarningsEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_WARNINGS_TC") {
												jQuery("#productWarningsTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_WARNINGS_SC") {
												jQuery("#productWarningsSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "STORAGE_CONDITION_EN") {
												jQuery("#storageConditionEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "STORAGE_CONDITION_TC") {
												jQuery("#storageConditionTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "STORAGE_CONDITION_SC") {
												jQuery("#storageConditionSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_INGREDIENTS_EN") {
												jQuery("#productIngredientsEn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_INGREDIENTS_SC") {
												jQuery("#productIngredientsSc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_INGREDIENTS_TC") {
												jQuery("#productIngredientsTc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "COLOR_GROUP") {
												jQuery("#colorGroup")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "COLOR_CODE") {
												jQuery("#colorCode")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "COLOR_DESC") {
												jQuery("#colorDesc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "COLOR_HEX_CODE") {
												jQuery("#colorHexCode")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "VARIANT_COLOR"
													|| productUpFieldModel.columnName == "VARIANT_SIZE") {
												jQuery("#variantOn")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "ONLINE_DATE") {
												jQuery("#onlineDate")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "OFFLINE_DATE") {
												jQuery("#offlineDate")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "UNIT_RETAIL") {
												jQuery("#unitRetail")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "STANDARD_UOM") {
												jQuery("#standardUom")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "ORIGIN_COUNTRY") {
												jQuery("#originCountry")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "SUPPLIER_LEAD_TIME") {
												jQuery("#supplierLeadTime")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "MIN_ORDER_QTY") {
												jQuery("#minOrderQty")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "MIN_DELIVER_DATE") {
												jQuery("#minDeliverDate")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "MAX_DELIVER_DATE") {
												jQuery("#maxDeliverDate")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "DAILY_INVENTORY") {
												jQuery("#dailyInventory")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "CONSIGNMENT_TYPE") {
												jQuery("#consignmentType")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "CONSIGNMENT_CAL_BASIS") {
												jQuery("#consignmentCalBasis")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "CONSIGNMENT_RATE") {
												jQuery("#consignmentRate")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "SIZE_DESC") {
												jQuery("#sizeDesc")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "CASE_PACK_INNER") {
												jQuery("#casePackInner")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "CASE_PACK_CASE") {
												jQuery("#casePackCase")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "DIM_UNIT") {
												jQuery("#dimUnit")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "WEIGHT_UNIT") {
												jQuery("#weightUnit")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "NUTRITION_LABEL") {
												jQuery("#nutritionLabel")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_DIM_HEIGHT") {
												jQuery("#productDimHeight")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_DIM_WIDTH") {
												jQuery("#productDimWidth")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_DIM_LENGTH") {
												jQuery("#productDimLength")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "SHIPPING_DIM_HEIGHT") {
												jQuery("#shippingDimHeight")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_DIM_WIDTH") {
												jQuery("#shippingDimWidth")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "SHIPPING_DIM_LENGTH") {
												jQuery("#shippingDimLength")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "CASE_DIM_HEIGHT") {
												jQuery("#caseDimHeight")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "CASE_DIM_WIDTH") {
												jQuery("#caseDimWidth")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "CASE_DIM_LENGTH") {
												jQuery("#caseDimLength")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "GROSS_WEIGHT") {
												jQuery("#grossWeight")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "SHIPPING_WEIGHT") {
												jQuery("#shippingWeight")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											if (productUpFieldModel.columnName == "PACKAGE") {
												jQuery("#packAge")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "PRODUCT_SHELF_LIFE") {
												jQuery("#productShelfLife")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "MIN_SHELF_LIFE") {
												jQuery("#minShelfLife")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

											if (productUpFieldModel.columnName == "MANUF_COUNTRY") {
												jQuery("#manufCountry")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											
											if (productUpFieldModel.columnName == "MAX_REPLENISHMENT_LEVEL") {
												jQuery("#maxReplenishmentLevel")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											
											if (productUpFieldModel.columnName == "MIN_REPLENISHMENT_LEVEL") {
												jQuery("#minReplenishmentLevel")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											
											if (productUpFieldModel.columnName == "SHIPPING_INFO") {
												jQuery("#shippingInfo")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}
											
											if (productUpFieldModel.columnName == "PNS_ONLINE_DELIVERY_TYPE") {
												jQuery("#pnsOnlineDeliveryType")
														.css(
																{
																	"border" : "1px solid #d8a256"
																});
											}

										});
					}

					function addProductReadonlyForSu() {
						$scope.brandReadonly = false;
						$scope.estoreCategoryReadonly = false;
						$scope.supplierProductCodeReadonly = false;
						$scope.productCodeReadonly = true;

						$scope.shortDescEnReadonly = false;
						$scope.shortDescTcReadonly = false;
						$scope.shortDescScReadonly = false;

						$scope.longDescEnReadonly = false;
						$scope.longDescTcReadonly = false;
						$scope.longDescScReadonly = false;

						$scope.productUsageEnReadonly = false;
						$scope.productUsageTcReadonly = false;
						$scope.productUsageScReadonly = false;

						$scope.productWarningsEnReadonly = false;
						$scope.productWarningsTcReadonly = false;
						$scope.productWarningsScReadonly = false;

						$scope.storageConditionEnReadonly = false;
						$scope.storageConditionTcReadonly = false;
						$scope.storageConditionScReadonly = false;

						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;

						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;

						$scope.colorGroupReadonly = false;
						$scope.colorCodeReadonly = false;
						$scope.colorHexCodeReadonly = false;
						$scope.colorDescReadonly = false;
						$scope.variantOnReadonly = false;

						$scope.unitRetailReadonly = false;
						$scope.smallExpensiveReadonly = false;
						$scope.standardUomReadonly = false;
						$scope.originCountryReadonly = false;

						$scope.onlineDateReadonly = false;
						$scope.offlineDateReadonly = false;
						$scope.deptReadonly = false;
						$scope.clazzReadonly = false;
						$scope.subClassReadonly = false;

						$scope.deliveryModeReadonly = false;
						$scope.supplierLeadTimeReadonly = true;
						$scope.minOrderQtyReadonly = false;

						$scope.minDeliverDateReadonly = true;
						$scope.maxDeliverDateReadonly = true;

						$scope.consignmentTypeReadonly = true;
						$scope.consignmentCalBasisReadonly = true;
						$scope.consignmentRateReadonly = false;

						$scope.sizeDescReadonly = false;
						$scope.casePackInnerReadonly = false;
						$scope.casePackCaseReadonly = false;

						$scope.productDimHeightReadonly = false;
						$scope.productDimWidthReadonly = false;
						$scope.productDimLengthReadonly = false;

						$scope.shippingDimHeightReadonly = false;
						$scope.shippingDimWidthReadonly = false;
						$scope.shippingDimLengthReadonly = false;

						$scope.caseDimHeightReadonly = false;
						$scope.caseDimWidthReadonly = false;
						$scope.caseDimLengthReadonly = false;

						$scope.grossWeightReadonly = false;
						$scope.shippingWeightReadonly = false;

						$scope.packAgeReadonly = false;
						$scope.productShelfLifeReadonly = false;
						$scope.minShelfLifeReadonly = false;
						$scope.barcodeNumReadonly = false;

						$scope.dimUnitReadonly = false;
						$scope.weightUnitReadonly = false;
						$scope.untritionLabelReadonly = false;
						$scope.manufCountryReadonly = false;

					}

					function addProductReadonlyForAp() {
						$scope.brandReadonly = false;
						$scope.estoreCategoryReadonly = false;
						$scope.supplierProductCodeReadonly = false;
						$scope.productCodeReadonly = true;

						$scope.shortDescEnReadonly = false;
						$scope.shortDescTcReadonly = false;
						$scope.shortDescScReadonly = false;

						$scope.longDescEnReadonly = false;
						$scope.longDescTcReadonly = false;
						$scope.longDescScReadonly = false;

						$scope.productUsageEnReadonly = false;
						$scope.productUsageTcReadonly = false;
						$scope.productUsageScReadonly = false;

						$scope.productWarningsEnReadonly = false;
						$scope.productWarningsTcReadonly = false;
						$scope.productWarningsScReadonly = false;

						$scope.storageConditionEnReadonly = false;
						$scope.storageConditionTcReadonly = false;
						$scope.storageConditionScReadonly = false;

						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;

						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;

						$scope.colorGroupReadonly = false;
						$scope.colorCodeReadonly = false;
						$scope.colorDescReadonly = false;
						$scope.variantOnReadonly = false;

						$scope.unitRetailReadonly = false;
						$scope.smallExpensiveReadonly = false;
						$scope.standardUomReadonly = false;
						$scope.colorHexCodeReadonly = false;
						$scope.originCountryReadonly = false;

						$scope.onlineDateReadonly = false;
						$scope.offlineDateReadonly = false;
						$scope.deptReadonly = false;
						$scope.clazzReadonly = false;
						$scope.subClassReadonly = false;

						$scope.deliveryModeReadonly = false;
						$scope.supplierLeadTimeReadonly = true;
						$scope.minOrderQtyReadonly = false;

						$scope.minDeliverDateReadonly = true;
						$scope.maxDeliverDateReadonly = true;

						$scope.consignmentTypeReadonly = true;
						$scope.consignmentCalBasisReadonly = false;
						$scope.consignmentRateReadonly = false;

						$scope.sizeDescReadonly = false;
						$scope.casePackInnerReadonly = false;
						$scope.casePackCaseReadonly = false;

						$scope.productDimHeightReadonly = false;
						$scope.productDimWidthReadonly = false;
						$scope.productDimLengthReadonly = false;

						$scope.shippingDimHeightReadonly = false;
						$scope.shippingDimWidthReadonly = false;
						$scope.shippingDimLengthReadonly = false;

						$scope.caseDimHeightReadonly = false;
						$scope.caseDimWidthReadonly = false;
						$scope.caseDimLengthReadonly = false;

						$scope.grossWeightReadonly = false;
						$scope.shippingWeightReadonly = false;
						$scope.packAgeReadonly = false;
						$scope.productShelfLifeReadonly = false;
						$scope.minShelfLifeReadonly = false;
						$scope.barcodeNumReadonly = false;

						$scope.dimUnitReadonly = false;
						$scope.weightUnitReadonly = false;
						$scope.untritionLabelReadonly = false;
						$scope.manufCountryReadonly = false;

					}

					function editProductReadonlyForSu(retekLastApprovedDate) {
					   if(null==retekLastApprovedDate){
							$scope.brandReadonly = false;
							$scope.supplierProductCodeReadonly = false;
							$scope.productCodeReadonly = true;
							$scope.colorGroupReadonly = false;
							$scope.colorCodeReadonly = false;
							$scope.colorHexCodeReadonly = false;
							$scope.colorDescReadonly = false;
							$scope.variantOnReadonly = false;
							$scope.unitRetailReadonly = false;
							$scope.originCountryReadonly = false;
							$scope.smallExpensiveReadonly = false;
							$scope.deptReadonly = false;
							$scope.clazzReadonly = false;
							$scope.subClassReadonly = false;
							$scope.deliveryModeReadonly = false;
							$scope.supplierLeadTimeReadonly = true;
							$scope.minOrderQtyReadonly = false;
							$scope.consignmentTypeReadonly = true;
							$scope.consignmentCalBasisReadonly = true;
							$scope.consignmentRateReadonly = false;
					   }else{
						   $scope.brandReadonly = true;
							$scope.supplierProductCodeReadonly = true;
							$scope.productCodeReadonly = true;
							$scope.colorGroupReadonly = true;
							$scope.colorCodeReadonly = true;
							$scope.colorHexCodeReadonly = true;
							$scope.colorDescReadonly = true;
							$scope.variantOnReadonly = true;
							$scope.unitRetailReadonly = true;
							$scope.originCountryReadonly = true;
							$scope.smallExpensiveReadonly = true;
							$scope.deptReadonly = true;
							$scope.clazzReadonly = true;
							$scope.subClassReadonly = true;
							$scope.deliveryModeReadonly = true;
							$scope.supplierLeadTimeReadonly = true;
							$scope.minOrderQtyReadonly = true;
							$scope.consignmentTypeReadonly = true;
							$scope.consignmentCalBasisReadonly = true;
							$scope.consignmentRateReadonly = true;
					   }
					   
						$scope.minDeliverDateReadonly = true;
						$scope.maxDeliverDateReadonly = true;
						$scope.estoreCategoryReadonly = false;
						$scope.shortDescEnReadonly = false;
						$scope.shortDescTcReadonly = false;
						$scope.shortDescScReadonly = false;
						$scope.longDescEnReadonly = false;
						$scope.longDescTcReadonly = false;
						$scope.longDescScReadonly = false;
						$scope.productUsageEnReadonly = false;
						$scope.productUsageTcReadonly = false;
						$scope.productUsageScReadonly = false;
						$scope.productWarningsEnReadonly = false;
						$scope.productWarningsTcReadonly = false;
						$scope.productWarningsScReadonly = false;
						$scope.storageConditionEnReadonly = false;
						$scope.storageConditionTcReadonly = false;
						$scope.storageConditionScReadonly = false;
						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;
						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;
						$scope.standardUomReadonly = false;
						$scope.offlineDateReadonly = false;
		
						$scope.sizeDescReadonly = false;
						$scope.casePackInnerReadonly = false;
						$scope.casePackCaseReadonly = false;
						$scope.productDimHeightReadonly = false;
						$scope.productDimWidthReadonly = false;
						$scope.productDimLengthReadonly = false;
						$scope.shippingDimHeightReadonly = false;
						$scope.shippingDimWidthReadonly = false;
						$scope.shippingDimLengthReadonly = false;
						$scope.caseDimHeightReadonly = false;
						$scope.caseDimWidthReadonly = false;
						$scope.caseDimLengthReadonly = false;
						$scope.grossWeightReadonly = false;
						$scope.shippingWeightReadonly = false;
						$scope.packAgeReadonly = false;
						$scope.productShelfLifeReadonly = false;
						$scope.minShelfLifeReadonly = false;
						$scope.barcodeNumReadonly = false;
						$scope.dimUnitReadonly = false;
						$scope.weightUnitReadonly = false;
						$scope.untritionLabelReadonly = false;
						$scope.manufCountryReadonly = false;

					}

					function editProductReadonlyForAp(retekLastApprovedDate) {
						if(null==retekLastApprovedDate){
							$scope.brandReadonly = false;
							$scope.supplierProductCodeReadonly = false;
							$scope.productCodeReadonly = true;
							$scope.colorGroupReadonly = false;
							$scope.colorCodeReadonly = false;
							$scope.colorHexCodeReadonly = false;
							$scope.colorDescReadonly = false;
							$scope.variantOnReadonly = false;
							$scope.unitRetailReadonly = false;
							$scope.originCountryReadonly = false;
							$scope.deptReadonly = false;
							$scope.clazzReadonly = false;
							$scope.subClassReadonly = false;
							$scope.deliveryModeReadonly = false;
							$scope.supplierLeadTimeReadonly = true;
							$scope.consignmentTypeReadonly = true;
							$scope.consignmentCalBasisReadonly = false;
							$scope.consignmentRateReadonly = false;
						}else{
							$scope.brandReadonly = true;
							$scope.supplierProductCodeReadonly = true;
							$scope.productCodeReadonly = true;
							$scope.colorGroupReadonly = true;
							$scope.colorCodeReadonly = true;
							$scope.colorHexCodeReadonly = true;
							$scope.colorDescReadonly = true;
							$scope.variantOnReadonly = true;
							$scope.unitRetailReadonly = true;
							$scope.originCountryReadonly = true;
							$scope.deptReadonly = true;
							$scope.clazzReadonly = true;
							$scope.subClassReadonly = true;
							$scope.deliveryModeReadonly = true;
							$scope.supplierLeadTimeReadonly = true;
							$scope.consignmentTypeReadonly = true;
							$scope.consignmentCalBasisReadonly = true;
							$scope.consignmentRateReadonly = true;
						}
						
						$scope.minDeliverDateReadonly = true;
						$scope.maxDeliverDateReadonly = true;
						$scope.estoreCategoryReadonly = false;
						$scope.shortDescEnReadonly = false;
						$scope.shortDescTcReadonly = false;
						$scope.shortDescScReadonly = false;
						$scope.longDescEnReadonly = false;
						$scope.longDescTcReadonly = false;
						$scope.longDescScReadonly = false;
						$scope.productUsageEnReadonly = false;
						$scope.productUsageTcReadonly = false;
						$scope.productUsageScReadonly = false;
						$scope.productWarningsEnReadonly = false;
						$scope.productWarningsTcReadonly = false;
						$scope.productWarningsScReadonly = false;
						$scope.storageConditionEnReadonly = false;
						$scope.storageConditionTcReadonly = false;
						$scope.storageConditionScReadonly = false;
						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;
						$scope.productIngredientsEnReadonly = false;
						$scope.productIngredientsTcReadonly = false;
						$scope.productIngredientsScReadonly = false;
						$scope.smallExpensiveReadonly = false;
						$scope.standardUomReadonly = false;
						$scope.offlineDateReadonly = false;
						$scope.minOrderQtyReadonly = false;
						$scope.sizeDescReadonly = false;
						$scope.casePackInnerReadonly = false;
						$scope.casePackCaseReadonly = false;
						$scope.productDimHeightReadonly = false;
						$scope.productDimWidthReadonly = false;
						$scope.productDimLengthReadonly = false;
						$scope.shippingDimHeightReadonly = false;
						$scope.shippingDimWidthReadonly = false;
						$scope.shippingDimLengthReadonly = false;
						$scope.caseDimHeightReadonly = false;
						$scope.caseDimWidthReadonly = false;
						$scope.caseDimLengthReadonly = false;
						$scope.grossWeightReadonly = false;
						$scope.shippingWeightReadonly = false;
						$scope.packAgeReadonly = false;
						$scope.productShelfLifeReadonly = false;
						$scope.minShelfLifeReadonly = false;
						$scope.barcodeNumReadonly = false;
						$scope.dimUnitReadonly = false;
						$scope.weightUnitReadonly = false;
						$scope.untritionLabelReadonly = false;
						$scope.manufCountryReadonly = false;

					}

					function hiddenButton() {
						$scope.delete_button = false;
						$scope.approve_button = false;
						$scope.reject_button = false;
						$scope.submit_for_approve_button = false;
					}

					function readonlyEdit() {
						$scope.supplierProductCodeReadonly = true;
						$scope.unitRetailReadonly = true;
						$scope.consignmentRateReadonly = true;
					}

					function canEdit() {
						$scope.supplierProductCodeReadonly = false;
						$scope.unitRetailReadonly = false;
						$scope.consignmentRateReadonly = false;
					}

					function getDepts(supplierId, dept) {
						$scope.depts = [];
						$scope.classes = [];
						$scope.subClasses = [];
						$http({
							method : 'GET',
							params : {
								supplierId : supplierId,
								deptId:dept
							},
							url : "./product/getDeptsBySupplierId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.depts = data.returnDataList;
												//console.log(data.returnDataList);
												if (null == $scope.depts
														|| "" == $scope.depts) {
													$scope.depts = [];
												}
												if (!(typeof (dept) == "undefined")
														&& $scope.depts.length >= 0
														&& dept != null) {
													var flag = true;
													for (var n = 0; n < $scope.depts.length; n++) {
														if (dept == $scope.depts[n].id) {
															flag = false;
														}
													}

													if (flag) {
														$http(
																{
																	method : 'GET',
																	params : {
																		deptId : dept
																	},
																	url : "./product/getDeptByDeptId"
																})
																.success(
																		function(
																				data) {
																			if (data != null
																					&& data != '')
																				$scope.depts = [];
																			$scope.depts
																					.push(data);
																			for (var i = 0; i < $scope.depts.length; i++) {
																				$scope.dept = new Array();
																				if (dept == $scope.depts[i].id) {
																					var ctObj = $scope.depts[i];
																					var obj = {
																						ticked : true,
																						descriptionCode : ctObj.descriptionCode,
																						dept : ctObj.id
																					};
																					var qqs = $scope.depts;
																					qqs
																							.splice(
																									i,
																									1,
																									obj);
																					$scope.depts = qqs;
																					$scope.dept
																							.push(obj);
																				}
																			}
																		});
													} else {
														for (var i = 0; i < $scope.depts.length; i++) {
															$scope.dept = new Array();
															if (dept == $scope.depts[i].id) {
																var ctObj = $scope.depts[i];
																var obj = {
																	ticked : true,
																	descriptionCode : ctObj.descriptionCode,
																	dept : ctObj.id
																};
																var qqs = $scope.depts;
																qqs.splice(i,
																		1, obj);
																$scope.depts = qqs;
																$scope.dept
																		.push(obj);
															}
														}
													}
												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					function getClasses(supplierId, deptId) {
						$http({
							method : 'GET',
							params : {
								supplierId : supplierId,
								deptId : deptId
							},
							url : "./product/getClassByDeptId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.classes = data.returnDataList;
												if (null == $scope.classes
														|| "" == $scope.classes) {
													$scope.classes = [];
												}
												if (!(typeof ($scope.productinfo.clazz) == "undefined")
														&& $scope.classes.length >= 0
														&& $scope.productinfo.clazz != null) {
													var flag = true;
													for (var n = 0; n < $scope.classes.length; n++) {
														if ($scope.productinfo.clazz == $scope.classes[n].id) {
															flag = false;
														}
													}
													if (flag) {
														$http(
																{
																	method : 'GET',
																	params : {
																		classId : $scope.productinfo.clazz
																	},
																	url : "./product/getClassByClassId"
																})
																.success(
																		function(
																				data) {
																			if (data != null
																					&& data != '')
																				$scope.classes
																						.push(data);
																			for (var i = 0; i < $scope.classes.length; i++) {
																				$scope.clazz = new Array();
																				if ($scope.productinfo.clazz == $scope.classes[i].id) {
																					var ctObj = $scope.classes[i];
																					var obj = {
																						ticked : true,
																						descriptionCode : ctObj.descriptionCode,
																						clazz : ctObj.id
																					};
																					var qqs = $scope.classes;
																					qqs
																							.splice(
																									i,
																									1,
																									obj);
																					$scope.classes = qqs;
																					$scope.clazz
																							.push(obj);
																				}
																			}
																		});
													} else {
														for (var i = 0; i < $scope.classes.length; i++) {
															$scope.clazz = new Array();
															if ($scope.productinfo.clazz == $scope.classes[i].id) {
																var ctObj = $scope.classes[i];
																var obj = {
																	ticked : true,
																	descriptionCode : ctObj.descriptionCode,
																	clazz : ctObj.id
																};
																var qqs = $scope.classes;
																qqs.splice(i,
																		1, obj);
																$scope.classes = qqs;
																$scope.clazz
																		.push(obj);
															}
														}
													}
												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					function getSubClasses(supplierId, classId) {
						$http({
							method : 'GET',
							params : {
								supplierId : supplierId,
								classId : classId
							},
							url : "./product/getSubClasssBySupplierClassId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.subClasses = data.returnDataList;
												if (null == $scope.subClasses
														|| "" == $scope.subClasses) {
													$scope.subClasses = [];
												}
												if (!(typeof ($scope.productinfo.subClass) == "undefined")
														&& $scope.subClasses.length >= 0
														&& $scope.productinfo.subClass != null) {
													var flag = true;
													for (var n = 0; n < $scope.subClasses.length; n++) {
														if ($scope.productinfo.subClass == $scope.subClasses[n].id) {
															flag = false;
														}
													}
													if (flag) {
														$http(
																{
																	method : 'GET',
																	params : {
																		subClassId : $scope.productinfo.subClass
																	},
																	url : "./product/getSubClassBySubClassId"
																})
																.success(
																		function(
																				data) {
																			if (data != null
																					&& data != '')
																				$scope.subClasses
																						.push(data);
																			for (var i = 0; i < $scope.subClasses.length; i++) {
																				$scope.subClass = new Array();
																				if ($scope.productinfo.subClass == $scope.subClasses[i].id) {
																					var ctObj = $scope.subClasses[i];
																					var obj = {
																						ticked : true,
																						descriptionCode : ctObj.descriptionCode,
																						subClass : ctObj.id
																					};
																					var qqs = $scope.subClasses;
																					qqs
																							.splice(
																									i,
																									1,
																									obj);
																					$scope.subClasses = qqs;
																					$scope.subClass
																							.push(obj);
																				}
																			}
																		});
													} else {
														for (var i = 0; i < $scope.subClasses.length; i++) {
															$scope.subClass = new Array();
															if ($scope.productinfo.subClass == $scope.subClasses[i].id) {
																var ctObj = $scope.subClasses[i];
																var obj = {
																	ticked : true,
																	descriptionCode : ctObj.descriptionCode,
																	subClass : ctObj.id
																};
																var qqs = $scope.subClasses;
																qqs.splice(i,
																		1, obj);
																$scope.subClasses = qqs;
																$scope.subClass
																		.push(obj);
															}
														}
													}
												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					function getBrands(supplierId) {
						$http({
							method : 'GET',
							params : {
								supplierId : supplierId
							},
							url : "./product/getBrandsBySupplierId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.brands = data.returnDataList;
												if (null == $scope.brands
														|| "" == $scope.brands) {
													$scope.brands = [];
												}
												if (!(typeof ($scope.productinfo.brandCode) == "undefined")
														&& $scope.brands.length >= 0
														&& $scope.productinfo.brandCode != null) {
													var flag = true;
													for (var n = 0; n < $scope.brands.length; n++) {
														if ($scope.productinfo.brandCode == $scope.brands[n].brandCode) {
															flag = false;
														}
													}
													if (flag) {
														$http(
																{
																	method : 'GET',
																	params : {
																		brandCode : $scope.productinfo.brandCode
																	},
																	url : "./product/getBrandByBrandCode"
																})
																.success(
																		function(
																				data) {
																			if (data != null
																					&& data != '')
																				$scope.brands
																						.push(data);
																			for (var i = 0; i < $scope.brands.length; i++) {

																				$scope.brandCode = new Array();
																				if ($scope.productinfo.brandCode == $scope.brands[i].brandCode) {
																					var ctObj = $scope.brands[i];
																					var obj = {
																						ticked : true,
																						descEn : ctObj.descEn,
																						brandCode : ctObj.brandCode
																					};
																					var qqs = $scope.brands;
																					qqs
																							.splice(
																									i,
																									1,
																									obj);
																					$scope.brands = qqs;
																					$scope.brandCode
																							.push(obj);
																				}
																			}
																		});
													} else {
														for (var i = 0; i < $scope.brands.length; i++) {

															$scope.brandCode = new Array();
															if ($scope.productinfo.brandCode == $scope.brands[i].brandCode) {
																var ctObj = $scope.brands[i];
																var obj = {
																	ticked : true,
																	descEn : ctObj.descEn,
																	brandCode : ctObj.brandCode
																};
																var qqs = $scope.brands;
																qqs.splice(i,
																		1, obj);
																$scope.brands = qqs;
																$scope.brandCode
																		.push(obj);
															}
														}
													}

												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					function getCategorys(supplierId) {
						$http({
							method : 'GET',
							params : {
								supplierId : supplierId
							},
							url : "./product/getCategorysBySupplierId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.estoreCategorys = data.returnDataList;
												if (null == $scope.estoreCategorys
														|| "" == $scope.estoreCategorys) {
													$scope.estoreCategorys = [];

												}
												if (!(typeof ($scope.productinfo.estoreCategory) == "undefined")
														&& $scope.estoreCategorys.length >= 0
														&& $scope.productinfo.estoreCategory != null) {
													var flag = true;
													for (var n = 0; n < $scope.estoreCategorys.length; n++) {
														if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[n].lovValue) {
															flag = false;
														}
													}
													if (flag) {
														$http(
																{
																	method : 'GET',
																	params : {
																		lovValue : $scope.productinfo.estoreCategory
																	},
																	url : "./product/getLovById"
																})
																.success(
																		function(
																				data) {
																			if (data != null
																					&& data != '')
																				$scope.estoreCategorys
																						.push(data);
																			for (var i = 0; i < $scope.estoreCategorys.length; i++) {
																				$scope.estoreCategory = new Array();
																				if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[i].lovValue) {

																					var ctObj = $scope.estoreCategorys[i];
																					var obj = {
																						ticked : true,
																						lovDesc : ctObj.lovDesc,
																						lovValue : ctObj.lovValue
																					};

																					var qqs = $scope.estoreCategorys;
																					qqs
																							.splice(
																									i,
																									1,
																									obj);
																					$scope.estoreCategorys = qqs;
																					$scope.estoreCategory
																							.push(obj);
																				}
																			}

																		});

													} else {
														for (var i = 0; i < $scope.estoreCategorys.length; i++) {
															$scope.estoreCategory = new Array();
															if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[i].lovValue) {

																var ctObj = $scope.estoreCategorys[i];
																var obj = {
																	ticked : true,
																	lovDesc : ctObj.lovDesc,
																	lovValue : ctObj.lovValue
																};

																var qqs = $scope.estoreCategorys;
																qqs.splice(i,
																		1, obj);
																$scope.estoreCategorys = qqs;
																$scope.estoreCategory
																		.push(obj);
															}
														}
													}

												}

												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					}

					// lwh start

					// Options for sortable code
					$scope.sortable_option = {
						// Only allow draggable when click on handle element
						handle : 'div.drowme',
						// Construct method before sortable code
						construct : function(model) {
							for (var i = 0; i < model.length; i++) {
								// model[i].letter +=" (constructed)";
							}
						},
						// Callback after item is dropped
						stop : function(imagesList, dropped_index) {
							// list[ dropped_index].letter += " Dropped";
						}
					};

					// Options for sortable code
					$scope.swatch_sortable_option = {
						// Only allow draggable when click on handle element
						handle : 'div.swatch_drowme',
						// Construct method before sortable code
						construct : function(model) {
							for (var i = 0; i < model.length; i++) {
								// model[i].letter +=" (constructed)";
							}
						},
						// Callback after item is dropped
						stop : function(swatchImagesList, dropped_index) {
							// list[ dropped_index].letter += " Dropped";
						}
					};

					function validateImagesFormat(files) {

						for (var i = 0; i < files.length; i++) {

							var format = files[i]["type"];
							if (!("image/jpeg" == format)) {
								alert(files[i]["name"]
										+ " Invalid  JPEG format.");
								return false;
								break;
							}

						}
						return true;

					}

					function setImageDrog() {

						var obj = $("#imgUpload");
						obj.on('dragenter', function(e) {
							e.stopPropagation();
							e.preventDefault();
							$(this).css('border', '3px solid #0B85A1');
						});
						obj.on('dragover', function(e) {
							e.stopPropagation();
							e.preventDefault();
						});
						obj.on('drop', function(e) {
							$(this).css('border', '3px dotted #0B85A1');
							e.preventDefault();

							var files = e.originalEvent.dataTransfer.files;
							var result = validateImagesFormat(files);
							if (!result) {
								return result;
							}

							// var files = e.target.files;
							if (files.length > 6) {
								alert(T.T('product_images_max_size'));
								return false;
							}

							var imagesArray = $("#imgUpload").find("img");
							if ((files.length + imagesArray.length) > 6) {
								alert(T.T('product_images_max_size'));
								return false;
							}

							uiUploader.addFiles(files);
							$scope.$apply();

							for (var i = 0; i < files.length; i++) {

								upload(files[i]);
							}

						});
						$(document).on('dragenter', function(e) {
							e.stopPropagation();
							e.preventDefault();
						});
						$(document).on('dragover', function(e) {
							e.stopPropagation();
							e.preventDefault();
							obj.css('border', '2px dotted #0B85A1');
						});
						$(document).on('drop', function(e) {

							e.stopPropagation();
							e.preventDefault();
						});
					}

					Array.prototype.baoremove = function(dx) {
						if (isNaN(dx) || dx > this.length) {
							return false;
						}
						this.splice(dx, 1);

					}

					$scope.deleteImageFile = function(delid) {
						$("#drowme_" + delid).remove();
						$("#handle_" + delid).remove();
						var imageObj = $scope.productinfo.imagesList[delid];
						if (imageObj["id"] != null && imageObj["id"] != '') {

							if ($scope.productinfo.deleteImagesList == null) {
								$scope.productinfo.deleteImagesList = [];
							}
							$scope.productinfo.deleteImagesList.push(imageObj);

						}
						$scope.productinfo.imagesList.baoremove(delid);

					}

					function setSwatchImageDrog() {
						var obj = $("#swatchImgUpload");
						obj.on('dragenter', function(e) {
							e.stopPropagation();
							e.preventDefault();
							$(this).css('border', '3px solid #0B85A1');
						});
						obj.on('dragover', function(e) {
							e.stopPropagation();
							e.preventDefault();
						});
						obj
								.on(
										'drop',
										function(e) {
											$(this).css('border',
													'3px dotted #0B85A1');
											e.preventDefault();

											var files = e.originalEvent.dataTransfer.files;
											var result = validateImagesFormat(files);
											if (!result) {
												return result;
											}

											// var files = e.target.files;
											if (files.length > 1) {
												alert(T
														.T('swatch_image_max_size'));
												return false;
											}

											var imagesArray = $(
													"#swatchImgUpload").find(
													"img");
											if ((files.length + imagesArray.length) > 1) {
												alert(T
														.T('swatch_image_max_size'));
												return false;
											}

											uiUploader.addFiles(files);
											$scope.$apply();

											for (var i = 0; i < files.length; i++) {

												uploadSwatch(files[i]);
											}

										});
						$(document).on('dragenter', function(e) {
							e.stopPropagation();
							e.preventDefault();
						});
						$(document).on('dragover', function(e) {
							e.stopPropagation();
							e.preventDefault();
							obj.css('border', '2px dotted #0B85A1');
						});
						$(document).on('drop', function(e) {

							e.stopPropagation();
							e.preventDefault();
						});
					}

					$scope.deleteSwatchImageFile = function(delid) {
						// $("#drowme_swatch_"+delid).remove();
						$("#handle_swatch_" + delid).remove();
						var imageObj = $scope.productinfo.swatchImagesList[delid];
						if (imageObj["id"] != null && imageObj["id"] != '') {

							if ($scope.productinfo.deleteSwatchImagesList == null) {
								$scope.productinfo.deleteSwatchImagesList = [];
							}
							$scope.productinfo.deleteSwatchImagesList
									.push(imageObj);
						}
						$scope.productinfo.swatchImagesList.baoremove(delid);

					}

					$scope.openTcWarnings = function() {
						window.open(
										'./views/pages/productWarningInfo.html',
										'newwindow',
										'height=600, width=900, top=0, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no') //

					}

					$scope.openEnWarnings = function() {
						window.open(
										'./views/pages/productWarningInfo.html',
										'newwindow',
										'height=600, width=900, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no') //

					}

					$scope.openScWarnings = function() {
						window.open(
										'./views/pages/productWarningInfo.html',
										'newwindow',
										'height=600, width=900, top=100, left=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no') //

					}
					function upload(file) {

						uiUploader.startUpload({
							url : './product/upload',
							concurrency : 2,
							onProgress : function(file) {
								$scope.$apply();
							},
							onCompleted : function(file, response) {

								var data = eval("(" + response + ")");

								var errorType = data["errorType"];
								var errorMessage = data["errorMessage"];
								if (errorType == 'danger') {
									alert(errorMessage);
									return;
								} else {
									var listdata = data["returnDataList"];
									for (var i = 0; i < listdata.length; i++) {
										$scope.productinfo.imagesList.push(listdata[i]);
									}
								}
							},
							onCompletedAll : function(file) {
								$scope.$apply();
							},
							onError : function(e) {
								$log.info('error occurs');
								$scope.disable = false;
								$scope.$apply();
							}
						});
					}// end

					function uploadSwatch(file) {

						uiUploader.startUpload({
							url : './product/uploadSwatch',
							concurrency : 2,
							onProgress : function(file) {
								$scope.$apply();
							},
							onCompleted : function(file, response) {

								var data = eval("(" + response + ")");

								var errorType = data["errorType"];
								var errorMessage = data["errorMessage"];
								if (errorType == 'danger') {
									alert(errorMessage);
									return;
								} else {

									var listdata = data["returnDataList"];
									for (var i = 0; i < listdata.length; i++) {
										$scope.productinfo.swatchImagesList
												.push(listdata[i]);
									}
								}

							},
							onCompletedAll : function(file) {
								$scope.$apply();
							},
							onError : function(e) {
								$log.info('error occurs');
								$scope.disable = false;
								$scope.$apply();
							}
						});
					}// end

					$scope.save = function() {
						console.log("save---");
						console.log($scope.supplierCode);
						console.log("save end---");
						 alertService.cleanAlert();
						 $scope.closeAlert = alertService.closeAlert;
						 
						if (!validateForm()) {
							myScroll();
							return false;
						}
						if (localStorageService.get("baseProductId") != ''
								&& localStorageService.get("baseProductId") != 'null') {

							if ($scope.productinfo.id == null) {
								$scope.productinfo.baseProductId = localStorageService
										.get("baseProductId");
							}
						}
						
						var deferred = $q.defer();

						$scope.productinfo.supplierCode = $scope.supplierCode;

						$scope.productinfo.estoreCategory = $scope.estoreCategoryId;

						if ($scope.productinfo.smallExpensive) {
							$scope.productinfo.smallExpensive = "Y";
						} else {
							$scope.productinfo.smallExpensive = "N";
						}

						$http
								.post('./product/save', $scope.productinfo)
								.success(
										function(data) {
											
											 $scope.closeAlert = alertService.closeAlert;
											deferred.resolve(data);
											if (data['errorType'] == "success") {
												
												
												$scope.productinfo = data.returnData;
												$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
												$scope.rowCollection = $scope.subProductInfoList;
												$scope.productCodeShow = true;
												$scope.product_list_div = true;
												$scope.add_product = true;
												$scope.supplierlist_div = false;
												$scope.supplier_name_div = true;
												$scope.productCodeReadonly = true;
												$scope.delete_product_id = data.returnData.id;
												
												//===========Sheldon============
//												$scope.productinfo.minDeliverDate = $scope.productinfo.minDeliverDate.toString();
//												$scope.productinfo.maxDeliverDate = $scope.productinfo.maxDeliverDate.toString();
												
												if ($scope.productinfo.smallExpensive == "Y") {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}

												$scope.productId_staging_for_history = data.returnData.id;

												if (!(typeof ($scope.productinfo.estoreCategory) == "undefined")
														&& $scope.estoreCategorys.length > 0
														&& $scope.productinfo.estoreCategory != null) {
													for (var i = 0; i < $scope.estoreCategorys.length; i++) {
														$scope.estoreCategory = new Array();
														if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[i].lovValue) {

															var ctObj = $scope.estoreCategorys[i];
															var obj = {
																ticked : true,
																lovDesc : ctObj.lovDesc,
																lovValue : ctObj.lovValue
															};

															var qqs = $scope.estoreCategorys;
															qqs.splice(i, 1,
																	obj);
															$scope.estoreCategorys = qqs;
															$scope.estoreCategory
																	.push(obj);

														}
													}
												}

												if (data.returnData.hasOnline) {
													readonlyEdit();
												} else {
													canEdit();
												}
												isDisplayButton(
														data.returnData.status,
														data.returnData.hasOnline);

												if (localStorageService
														.get("baseProductId") == null
														|| localStorageService
																.get("baseProductId") == '') {
													localStorageService
															.set(
																	"baseProductId",
																	$scope.productinfo.id);

												}
												alertService.cleanAlert();
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
												myScroll();

											} else if (data['errorType'] == "warning") {
												$scope.productinfo = data.returnData;

												$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
												$scope.rowCollection = $scope.subProductInfoList;
												$scope.product_list_div = true;
												$scope.add_product = true;
												$scope.supplierlist_div = false;
												$scope.supplier_name_div = true;
												$scope.productCodeShow = true;
												$scope.productCodeReadonly = true;
												$scope.delete_product_id = data.returnData.id;
												
												//===========Sheldon============
//												$scope.productinfo.minDeliverDate = $scope.productinfo.minDeliverDate.toString();
//												$scope.productinfo.maxDeliverDate = $scope.productinfo.maxDeliverDate.toString();
												
												if ($scope.productinfo.smallExpensive == "Y") {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}

												$scope.productId_staging_for_history = data.returnData.id;

												if (!(typeof ($scope.productinfo.estoreCategory) == "undefined")
														&& $scope.estoreCategorys.length > 0
														&& $scope.productinfo.estoreCategory != null) {
													for (var i = 0; i < $scope.estoreCategorys.length; i++) {
														$scope.estoreCategory = new Array();
														if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[i].lovValue) {

															var ctObj = $scope.estoreCategorys[i];
															var obj = {
																ticked : true,
																lovDesc : ctObj.lovDesc,
																lovValue : ctObj.lovValue
															};

															var qqs = $scope.estoreCategorys;
															qqs.splice(i, 1,
																	obj);
															$scope.estoreCategorys = qqs;
															$scope.estoreCategory
																	.push(obj);
														}
													}
												}

												if (data.returnData.hasOnline) {
													readonlyEdit();
												} else {
													canEdit();
												}
												isDisplayButton(
														data.returnData.status,
														data.returnData.hasOnline);

												if (userRoes != null
														&& (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
													setHighLightFileds(
															$scope.productinfo.productUpFiledList,
															$scope.productinfo.productImagesUpFiledList);
												}

												alertService.cleanAlert();
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
												myScroll();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
												myScroll();
											}

										}).
					                    
					                    error(function(data,status,headers,config){
					                   	 deferred.reject();     
					                    });

					                
					                $scope.promise = deferred.promise;
					}

					$scope.submit_for_approve = function() {
						 alertService.cleanAlert();
						var product_id = $scope.delete_product_id;
						if (product_id == "") {
							alert(T.T("product_id_should_be_not_empty"));
							return false;
						}

						$http({
							method : 'POST',
							params : {
								pIds : product_id,
								pStatus : "",
								pType : 'submitApprove'
							},
							url : "./product/updateProductStatus"
						})
								.success(
										function(data) {

											if (data != null && data != '') {
												if (data['errorType'] != "success") {

													$scope.closeAlert = alertService.closeAlert;
													alertService
															.add(
																	data["errorType"],
																	data["errorMessage"]);
													myScroll();
												} else {
													$scope.submit_for_approve_button = false;
													$scope.productinfo = data.returnData;
													$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
													$scope.rowCollection = $scope.subProductInfoList;
													$scope.closeAlert = alertService.closeAlert;
													alertService.add("success", T.T("product_submit_approve_successful"));
													myScroll();
												}

											}

										});
					}

					$scope.approve = function() {
						 alertService.cleanAlert();
						var flag=true;
						if ($scope.productinfo.id == "" || $scope.productinfo.id == null || $scope.productinfo.id=="undefined") {
							alert(T.T("product_id_should_be_not_empty"));
						}
//						
//						if($scope.productinfo.dept==null || $scope.productinfo.dept=="" || $scope.productinfo.dept=="undefined")
//						{
//							jQuery("#dept_err").html(T.T("dept_is_required"));
//							flag=false;
//						}else{
//							jQuery("#dept_err").html("");
//						}
						
//						if($scope.productinfo.clazz==null || $scope.productinfo.clazz=="" || $scope.productinfo.clazz=="undefined")
//						{
//							jQuery("#class_err").html(T.T("class_is_required"));
//							flag=false;
//						}else{
//							jQuery("#class_err").html("");
//						}
//						
//						if($scope.productinfo.subClass==null || $scope.productinfo.subClass==""  || $scope.productinfo.subClass=="undefined")
//						{
//							jQuery("#subclass_err").html(T.T("subclass_is_required"));
//							flag=false;
//						}else{
//							jQuery("#subclass_err").html("");
//						}
						
//						if('W'==$scope.productinfo.deliveryMode){
//							if($scope.productinfo.supplierLeadTime==null || $scope.productinfo.supplierLeadTime=="undefined")
//							{
//								jQuery("#supplierleadtime_err").html(T.T("supplierleadtime_is_required"));
//								flag=false;
//							}else{
//								jQuery("#supplierleadtime_err").html("");
//							}
//						}else{
//							jQuery("#supplierleadtime_err").html("");
//						}
						
						if($scope.productinfo.casePackInner==null || $scope.productinfo.casePackInner=="" || $scope.productinfo.casePackInner=="undefined")
						{
							jQuery("#casePackInner_err").html(T.T("casePackInner_is_required"));
							flag=false;
						}else{
							jQuery("#casePackInner_err").html("");
						}
						
						if($scope.productinfo.casePackCase==null || $scope.productinfo.casePackCase=="" || $scope.productinfo.casePackCase=="undefined")
						{
							jQuery("#casePackCase_err").html(T.T("casePackCase_is_required"));
							flag=false;
						}else{
							jQuery("#casePackCase_err").html("");
						}
				
						if(!flag){
							return false;
						}
						var ids = "";
						$scope.approvalButtonDisable = true;
						$http({
							method : 'POST',
							params : {
								pIds : $scope.productinfo.id
							},
							url : "./product/checkApproveProduct"
						}).success(function(data) {

	        	            	  $http({
	      							method : 'POST',
	      							params : {
	      								pIds : $scope.productinfo.id,
	      								pStatus : "",
	      								pType : 'approve'
	      							},
	      							url : "./product/updateProductStatus"
	      						})
	      								.success(
	      										function(data) {
													$scope.approvalButtonDisable = false;
	      											if (data != null && data != '') {
	      												if (data['errorType'] != "success") {
	      													$scope.closeAlert = alertService.closeAlert;
	      													alertService
	      															.add(
	      																	data["errorType"],
	      																	data["errorMessage"]);
	      													myScroll();
	      												} else {
	      													$scope.approve_button = false;
	      													$scope.reject_button = false;
	      													$scope.delete_button = false;
	      													$scope.productinfo = data.returnData;
	      													$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
	      													$scope.rowCollection = $scope.subProductInfoList;
	      													$scope.closeAlert = alertService.closeAlert;
	      													$scope.consignmentCalBasisReadonly = false;
	      													if (data.returnData.hasOnline) {
	      														jQuery("#online_version").css("display","block");
	      													}
	      													alertService.add("success", T.T("approve_successful"));
	      													myScroll();
	      												}

	      											}

	      										}).error(function(data){
	      											$scope.approvalButtonDisable = false;
	      										});

						});
					}

					$scope.reject_product = function() {
						var product_id = $scope.delete_product_id;
						if (product_id == "") {
							alert(T.T());
							return false;
						}

						$http({
							method : 'POST',
							params : {
								pIds : product_id,
								pStatus : "",
								pType : 'reject'
							},
							url : "./product/updateProductStatus"
						})
								.success(
										function(data) {
											// console.log(data);
											if (data != null && data != '') {
												if (data['errorType'] != "success") {

													$scope.closeAlert = alertService.closeAlert;
													alertService
															.add(
																	data["errorType"],
																	data["errorMessage"]);
													myScroll();
												} else {
													$scope.reject_button = false;
													$scope.approve_button = false;
													$scope.productinfo = data.returnData;
													$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
													$scope.rowCollection = $scope.subProductInfoList;
													$scope.closeAlert = alertService.closeAlert;
													alertService.add("success", T.T("product_reject_successful"));
													myScroll();
												}

											}

										});
					}
					$scope.$watch('subProductInfoList', function(row) {
						// get selected row

						if (row != null) {
							row.filter(function(r) {
								if (r.isSelected) {
									if (r.id != null) {
										$scope.delete_product_id = r.id;
										editProduct(r.id, 0);
									}
								}
							})
						}
					}, true);

					$scope.cancel = function() {
						if ($scope.edit_product_id == null) {
							$state.go("main.product", {}, {
								reload : true
							});
							myScroll();
						} else {
							$state.go('main.product_view');
							myScroll();
						}
					}

					$scope.confirm_update = function() {
						$scope.loadflag = true;
						var product_id = $scope.delete_product_id;
						if (product_id == "") {
							alert(T.T("product_id_should_be_not_empty"));
							return false;
						}
						$http({
							method : 'GET',
							params : {
								productId : product_id
							},
							url : "./product/confirmUpdate"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.confirmUpdateButton = false;
												$scope.ignoreUpdateButton = false;
												$scope.onlineUpdatedMsg = false;
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);

											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					}

					$scope.ignore_update = function() {
						$scope.loadflag = true;
						var product_id = $scope.delete_product_id;
						if (product_id == "") {
							alert(T.T("product_id_should_be_not_empty"));
							return false;
						}
						$http({
							method : 'GET',
							params : {
								productId : product_id
							},
							url : "./product/ignoreUpdate"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.confirmUpdateButton = false;
												$scope.ignoreUpdateButton = false;
												$scope.onlineUpdatedMsg = false;
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					}
					
					$scope.get_online_version_product = function() {
						$scope.loadflag = true;
						 $scope.promise = $http({
							method : 'GET',
							params : {
								productId : $scope.productinfo.onlineProductId
							},
							url : "./product/initEditProductDetail"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.productinfo = {};
												$scope.productinfo = data.returnData;
												//console.log(data.returnData);
												$scope.subProductInfoList = [];
												$scope.rowCollection = [];
												
												//==========Sheldon==============
//												$scope.productinfo.minDeliverDate = $scope.productinfo.minDeliverDate.toString();
//												$scope.productinfo.maxDeliverDate = $scope.productinfo.maxDeliverDate.toString();
												
												hiddenAllButton();

												$scope.add_product = false;
												$scope.product_list_div = false;
												$scope.delete_product_id = data.returnData.id;
//												getBrands(data.returnData.supplierCode);
//												getCategorys(data.returnData.supplierCode);
//												getDepts(
//														data.returnData.supplierCode,
//														data.returnData.dept);
												if (data.returnData.dept != null) {
													getClasses(
															data.returnData.supplierCode,
															data.returnData.dept);
												}
												if (data.returnData.clazz != null) {
													getSubClasses(
															$scope.productinfo.supplierCode,
															data.returnData.clazz);
												}

												if ($scope.productinfo.smallExpensive == "Y") {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}

												if (data.returnData.productCode != ""
														&& data.returnData.onlineUpdatedInd == "Y") {
													$scope.confirmUpdateButton = true;
													$scope.ignoreUpdateButton = true;
													$scope.onlineUpdatedMsg = true;
												} else {
													$scope.confirmUpdateButton = false;
													$scope.ignoreUpdateButton = false;
													$scope.onlineUpdatedMsg = false;
												}

												alertService.cleanAlert();
												$scope.supplier_name_div = true;
												if (data.returnData.hasOnline) {
													readonlyEdit();
												} else {
													canEdit();
												}
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					}

					$scope.delete_product = function() {
						var product_id = $scope.delete_product_id;
						if (product_id == "") {
							alert(T.T("product_id_should_be_not_empty"));
							return false;
						}
						$http({
							method : 'GET',
							params : {
								productId : product_id
							},
							url : "./product/deleteProductByProductId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												if (data.returnData == null) {
													$state.go("main.product",
															{}, {
																reload : true
															});
												} else {
													$scope.productinfo = data.returnData;
													$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
													$scope.rowCollection = $scope.productinfo.subProductInfoList;
													$scope.delete_product_id = $scope.productinfo.id;
												}
												$state.go("main.product_view",
														{}, {
															reload : true
														});
												
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
												myScroll();
											}

										});
					}

					function isDisplayButton(status, flag) {
						/* console.log("status :"+status);
						// console.log("flag :"+flag);
						// console.log("userRoes: "+userRoes);
						// console.log($scope.delete_button);*/
						if (status == "Draft" && userRoes != null && userRoes == 'SUPPLIER' && flag == false) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = true;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Draft" && userRoes != null && userRoes == 'SUPPLIER' && flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = true;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Pending for approval" && userRoes != null && userRoes == 'SUPPLIER'&& flag == false) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if ((status == "Approved in Retek" || status == "Pending for approval") && userRoes != null&& userRoes == 'SUPPLIER'&& flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if ((status == "Auto Approved" || status == "Approved") && userRoes != null&& userRoes == 'SUPPLIER'&& flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Rejected" && userRoes != null && userRoes == 'SUPPLIER' && flag == false) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Rejected" && userRoes != null && userRoes == 'SUPPLIER' && flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						}else if (status == "Draft" && userRoes != null && (userRoes == 'APPROVER')&& flag == false) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Draft"&& userRoes != null && (userRoes == 'APPROVER')&& flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Pending for approval" && userRoes != null&& (userRoes == 'APPROVER')&& flag == false) {
							$scope.approve_button = true;
							$scope.reject_button = true;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Pending for approval" && userRoes != null&& (userRoes == 'APPROVER')&& flag == true) {
							$scope.approve_button = true;
							$scope.reject_button = true;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if ((status = "Approved in Retek"|| status == "Approved")&& userRoes != null&& (userRoes == 'APPROVER')&& flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Rejected" && userRoes != null && (userRoes == 'APPROVER')&& flag == false) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = true;
							$scope.save_button = true;
							$scope.cancel_button = true;
						} else if (status == "Rejected" && userRoes != null && (userRoes == 'APPROVER')&& flag == true) {
							$scope.approve_button = false;
							$scope.reject_button = false;
							$scope.submit_for_approve_button = false;
							$scope.delete_button = false;
							$scope.save_button = true;
							$scope.cancel_button = true;
						}
					}

					function hiddenAllButton() {
						$scope.supplierlist_div = false;
						$scope.delete_button = false;
						$scope.approve_button = false;
						$scope.submit_for_approve_button = false;
						$scope.reject_button = false;
						$scope.cancel_button = false;
						$scope.save_button = false;
						$scope.add_item_num_type_div = false;
						$scope.delete_item_num_type = false;
					}
					$scope.productinfo.minDeliverDate = null;
					
					$scope.getSupplierBySupplierId = function() {

						var deliveryMode = jQuery("#deliveryMode").val();
						if (deliveryMode == "C") {
							
							
							$scope.productinfo.supplierLeadTime = null;
							$scope.productinfo.minDeliverDate = null;
							$scope.productinfo.maxDeliverDate = null;
							$scope.productinfo.dailyInventory = null;
							$scope.dailyInventoryReadonly=true;
							jQuery("#dailyInventory_err").html("");
							$scope.productinfo.minOrderQty="1";
							$scope.minOrderQtyReadonly=false;
							$scope.minReplenishmentLevelReadonly=false;
							$scope.maxReplenishmentLevelReadonly=false;
							$scope.productinfo.consignmentType="W";
							$scope.productinfo.pnsOnlineDeliveryType="1";
							$scope.pnsOnlineDeliveryTypeReadonly=false;
						} else {
							jQuery("#dailyInventory_err").html("");
							$scope.productinfo.maxReplenishmentLevel=null;
							$scope.productinfo.minReplenishmentLevel=null;
							jQuery("#maxReplenishmentLevel_err").html("");
							jQuery("#minReplenishmentLevel_err").html("");
						}
						
						if (deliveryMode == "W") {
							$scope.productinfo.minOrderQty="1";
							$scope.minOrderQtyReadonly=true;
							$scope.minReplenishmentLevelReadonly=true;
							$scope.maxReplenishmentLevelReadonly=true;
							$scope.productinfo.consignmentType="W";
							$scope.productinfo.pnsOnlineDeliveryType="0";
							$scope.pnsOnlineDeliveryTypeReadonly=false;
							$scope.dailyInventoryReadonly=false;
							$scope.productinfo.dailyInventory = "0";
							$scope.productinfo.pnsOnlineDeliveryType="1";
						}else if (deliveryMode == "D") {
							$scope.dailyInventoryReadonly=false;
							$scope.productinfo.dailyInventory = "0";
							$scope.productinfo.minOrderQty="";
							$scope.minOrderQtyReadonly=true;
							$scope.pnsOnlineDeliveryTypeReadonly=true;
							$scope.maxReplenishmentLevelReadonly=true;
							$scope.productinfo.consignmentType="C";
							$scope.productinfo.pnsOnlineDeliveryType="1";
							$scope.minReplenishmentLevelReadonly=true;
						}
						
						if(null==$scope.productinfo.supplierCode){
							getSupplierBySupplierId(supplierId, deliveryMode);
						}else{
							getSupplierBySupplierId($scope.productinfo.supplierCode, deliveryMode);
						}
						
						//===閺�鎯ф躬鏉╂瑤閲滄担宥囩枂====Sheldon=====
						
						if (deliveryMode == "C") {
							$scope.productinfo.minDeliverDateStr = $scope.minDeliverDateDefault;
							$scope.productinfo.maxDeliverDateStr = $scope.maxDeliverDateDefault;
						}
					}

					function getSupplierBySupplierId(supplierCode, deliveryMode) {
						if (supplierCode == null) {
							$scope.productinfo.minDeliverDate = null;
							$scope.productinfo.maxDeliverDate = null;
							$scope.productinfo.supplierLeadTime = null;
							
							return false;
						}

						$http({
							method : 'GET',
							params : {
								supplierId : supplierCode
							},
							url : "./product/getSupplierBySupplierId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												
												if (deliveryMode == "W") {
													$scope.productinfo.supplierLeadTime = data.returnData.warehouseDeliLeadTime;
													$scope.productinfo.minDeliverDate = null;
													$scope.productinfo.maxDeliverDate = null;
													$scope.productinfo.onlineDate=data.returnData.productDefaultOnlineDate;
													$scope.onlineDateReadonly=true;
												} else if (deliveryMode == "D") {
													$scope.productinfo.minDeliverDate = data.returnData.minDeliveryDay;
													$scope.productinfo.maxDeliverDate = data.returnData.maxDeliveryDay;
													$scope.productinfo.supplierLeadTime = null;
													$scope.productinfo.onlineDate=data.returnData.productDefaultOnlineDate;
													$scope.onlineDateReadonly=true;
												} else {
													$scope.productinfo.minDeliverDateStr = $scope.minDeliverDateDefault;
													$scope.productinfo.maxDeliverDateStr = $scope.maxDeliverDateDefault;
													$scope.productinfo.supplierLeadTime = null;
													$scope.onlineDateReadonly=false;
													$scope.productinfo.onlineDate=null;
												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					// $http({method:'GET',
					// url:"./product/getCategorysBySupplierId"}).
					// success(function(data) {
					// if (data['errorType'] == "success") {
					// $scope.estoreCategorys = data.returnDataList;
					// alertService.cleanAlert();
					// }else {
					// $scope.closeAlert = alertService.closeAlert;
					// alertService.add(data["errorType"],
					// data["errorMessage"]);
					// }
					//        	 
					// });

					$http({
						method : 'GET',
						url : "./product/getDailyInventory"
					})
							.success(
									function(data) {

										if (data['errorType'] == "success") {
											$scope.dailyInventorys = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});
					$http({
						method : 'GET',
						url : "./product/getStandardUom"
					})
							.success(
									function(data) {

										if (data['errorType'] == "success") {
											$scope.uoms = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});
					$http({
						method : 'GET',
						url : "./product/getUomCbm"
					})
							.success(
									function(data) {

										if (data['errorType'] == "success") {
											$scope.lovs = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});

					$http({
						method : 'GET',
						url : "./product/getUomWeight"
					})
							.success(
									function(data) {

										if (data['errorType'] == "success") {
											$scope.uomWeights = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});

					$http({
						method : 'GET',
						url : "./product/getAllItemNumType"
					})
							.success(
									function(data) {

										if (data['errorType'] == "success") {
											$scope.itemNumTypes = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}
									});

					$http({
						method : 'GET',
						url : "./product/getAllCountry"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.countrys = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});
					$http({
						method : 'GET',
						url : "./product/getPnsOnlineDeliveryType"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.pnsOnlineDeliveryTypes = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});
					
					$http({
						method : 'GET',
						url : "./product/getPackages"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.packages = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});
					$http({
						method : 'GET',
						url : "./product/getManufCountrys"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.manufCountrys = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}

									});

					$http({
						method : 'GET',
						url : "./product/getMinOrderQty"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.minOrderQtys = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}
									});

					$http({
						method : 'GET',
						url : "./product/getColorGroups"
					})
							.success(
									function(data) {
										if (data['errorType'] == "success") {
											$scope.colorGroups = data.returnDataList;
											alertService.cleanAlert();
										} else {
											$scope.closeAlert = alertService.closeAlert;
											alertService.add(data["errorType"],
													data["errorMessage"]);
										}
									});

					/*
					 * $http({method:'GET',params:{ udaId:785},
					 * url:"./product/getUdasByUdaId"}). success(function(data) {
					 * if (data['errorType'] == "success") {
					 * $scope.minDeliverDates = data.returnDataList;
					 * alertService.cleanAlert(); }else { $scope.closeAlert =
					 * alertService.closeAlert;
					 * alertService.add(data["errorType"],
					 * data["errorMessage"]); }
					 * 
					 * });
					 */

					/*
					 * $http({method:'GET',params:{ lovId:786},
					 * url:"./product/getLovsByLovId"}). success(function(data) {
					 * if (data['errorType'] == "success") {
					 * $scope.maxDeliverDates = data.returnDataList;
					 * alertService.cleanAlert(); }else { $scope.closeAlert =
					 * alertService.closeAlert;
					 * alertService.add(data["errorType"],
					 * data["errorMessage"]); } });
					 */

					$scope.showClass = function(data) {
						$scope.classes = [];
						$scope.subClasses = [];
						if(null!=data.id){
							$scope.productinfo.dept = data.id;
						}

						$http({
							method : 'GET',
							params : {
								supplierId : $scope.supplierCode,
								deptId : $scope.productinfo.dept
							},
							url : "./product/getClassByDeptId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.classes = data.returnDataList;
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					$scope.showSubClass = function(data) {
						$scope.subClasses = [];
						$scope.productinfo.dept = data.deptId;
						if(null!=data.id){
							$scope.productinfo.clazz = data.id;
						}
						$http({
							method : 'GET',
							params : {
								supplierId : $scope.supplierCode,
								dept : $scope.productinfo.dept,
								classId : $scope.productinfo.clazz
							},
							url : "./product/getSubClasssBySupplierClassId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.subClasses = data.returnDataList;
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					}

					$scope.initBrand = function(data) {
						$scope.productinfo.brandCode = data.brandCode;
					}

					$scope.initSubClass = function(data) {
						if(null!=data.id){
							$scope.productinfo.subClass = data.id;
						}
						$http({
							method : 'GET',
							params : {
								supplierId : $scope.supplierCode
							},
							url : "./product/getCategorysBySupplierId"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.estoreCategorys = data.returnDataList;
												if (null == $scope.estoreCategorys || "" == $scope.estoreCategorys) {
													$scope.estoreCategorys = [];

												}
												//($scope.estoreCategorys);
												$http({
													method : 'GET',
													params : {
														subClassId : $scope.productinfo.subClass
													},
													url : "./product/getDefaultCategoryBySubClassId"
												})
														.success(
																function(data) {
																	if (data['errorType'] == "success") {
                                                                      //  console.log(data);
																		if (data.returnData != null&& data.returnData != ''){
																				$scope.estoreCategorys.push(data);
																				for (var i = 0; i < $scope.estoreCategorys.length; i++) {
																				$scope.estoreCategory = new Array();
																				if (data.returnData.lovValue == $scope.estoreCategorys[i].lovValue) {
																					var ctObj = $scope.estoreCategorys[i];
																					var obj = {
																						ticked : true,
																						lovDesc : ctObj.lovDesc,
																						lovValue : ctObj.lovValue
																					};
																					var qqs = $scope.estoreCategorys;
																					qqs.splice(i,1,obj);
																					$scope.estoreCategorys = qqs;
																					$scope.estoreCategory.push(obj);
																				}
																			}	
																		}else{
																			for (var i = 0; i < $scope.estoreCategorys.length; i++) {
																				$scope.estoreCategory = new Array();
																				if ($scope.productinfo.estoreCategory == $scope.estoreCategorys[i].lovValue) {

																					var ctObj = $scope.estoreCategorys[i];
																					var obj = {
																						ticked : true,
																						lovDesc : ctObj.lovDesc,
																						lovValue : ctObj.lovValue
																					};

																					var qqs = $scope.estoreCategorys;
																					qqs.splice(i,
																							1, obj);
																					$scope.estoreCategorys = qqs;
																					$scope.estoreCategory
																							.push(obj);
																				}
																			}
																		}
																		alertService.cleanAlert();
																	} else {
																		$scope.closeAlert = alertService.closeAlert;
																		alertService.add(data["errorType"],
																				data["errorMessage"]);
																	}
																});
												

												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}

										});
					
					}

					$scope.add_item_num_type = function() {
						$scope.productinfo.barcodeList.push({});
					}

					$scope.setSmallExpensive = function() {
						//console.log($scope.productinfo.unitRetail);
						$http({
							method : 'GET',
							url : "./product/getSmallExpensive"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.small_expensive = parseInt(data.returnData);
												console
														.log($scope.small_expensive);
												if ($scope.productinfo.unitRetail > $scope.small_expensive) {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}
												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});

					}

					$scope.add_sub_product = function() {
						if (userRoes != null
								&& (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
							$scope.supplierlist_div = true;
							$scope.supplier_name_div = false;
							$scope.consignmentCalBasisReadonly = false;
							addProductReadonlyForAp();
						} else {
							addProductReadonlyForSu();
						}

						hiddenButton();
						var baseProductId = localStorageService
								.get("baseProductId");
						if (baseProductId == "" || baseProductId == "null") {
							alert(T.T("base_product_id_is_not_exist"));
							return false;
						}
						// console.log("baseProductId: "+baseProductId);
						 $scope.promise =	$http({
							method : 'GET',
							params : {
								productId : baseProductId
							},
							url : "./product/initNewProduct"
						})
								.success(
										function(data) {
											if (data['errorType'] == "success") {
												$scope.add_item_num_type_div = true;
												$scope.productinfo = data.returnData;
												$scope.subProductInfoList = $scope.productinfo.subProductInfoList;
												$scope.subProductInfoList
														.push({});
												$scope.productinfo.barcodeList
														.push({});
												$scope.rowCollection = $scope.subProductInfoList;
												$scope.productCodeShow = false;
												canEdit();
												

												if ($scope.productinfo.smallExpensive == "Y") {
													$scope.productinfo.smallExpensive = true;
												} else {
													$scope.productinfo.smallExpensive = false;
												}

												if ($scope.productinfo.deliveryMode == "C") {
													$scope.minReplenishmentLevelReadonly=false;
													$scope.maxReplenishmentLevelReadonly=false;
													$scope.dailyInventoryReadonly=true;
													$scope.minOrderQtyReadonly=false;
													$scope.onlineDateReadonly=false;
												}else{
													$scope.minOrderQtyReadonly=true;
													$scope.minReplenishmentLevelReadonly=true;
													$scope.maxReplenishmentLevelReadonly=true;
													$scope.dailyInventoryReadonly=false;
													$scope.onlineDateReadonly=true;
												}

												
												$scope.brandReadonly=true;
												$scope.variantOnReadonly=true;

												alertService.cleanAlert();
											} else {
												$scope.closeAlert = alertService.closeAlert;
												alertService.add(
														data["errorType"],
														data["errorMessage"]);
											}
										});
					}

					$scope.deleteitemnumtype = function(barcode) {
						angular.forEach($scope.productinfo.barcodeList,
								function(data, index) {
									if (data.$$hashKey == barcode.$$hashKey) {
										$scope.productinfo.barcodeList.splice(
												index, 1);
									}
								});

					}

					function validateForm() {
						jQuery("#dept_err").html("");
						jQuery("#class_err").html("");
						jQuery("#subclass_err").html("");
						
						var html = "";
						if (userRoes != null && (userRoes == 'SYSTEMADMIN' || userRoes == 'APPROVER')) {
//							angular.forEach($scope.supplierList, function(
//									supplier) {
//								if (supplier.ticked) {
//									$scope.supplierCode = supplier.supplierId;
//								}
//							});
//
//							if ($scope.supplierlist_div == true) {
//								if ($scope.supplierCode == null
//										|| $scope.supplierCode == "") {
//									jQuery("#supplierCode_err").html(
//											T.T('supplier_code_is_required'));
//									html += "1";
//								} else {
//									jQuery("#supplierCode_err").html("");
//
//								}
//							}
						}

						angular.forEach($scope.estoreCategorys, function(
								category) {
							if (category.ticked) {
								$scope.estoreCategoryId = category.lovValue;
							}
						});

						
//						if ($scope.estoreCategoryId == null
//								|| $scope.estoreCategoryId == "") {
//							jQuery("#estoreCategory_err").html(
//									T.T('estore_category_is_required'));
//							html += "1";
//						} else {
//							jQuery("#estoreCategory_err").html("");
//
//						}

						var supplierProductCode = jQuery("#supplierProductCode")
								.val();
						if (supplierProductCode == "") {
							jQuery("#supplierProductCode_err").html(
									T.T('supplier_product_code_is_required'));
							html += "1";
						} else {
							jQuery("#supplierProductCode_err").html("");

						}

						var shortDescEn = jQuery("#shortDescEn").val();
						if (shortDescEn == "") {
							jQuery("#shortDescEn_err").html(
									T.T('item_name(en)_is_required'));
							html += "1";
						}else{
//							var re = new RegExp("^[A-Z0-9 `~!@#$%^&*()_+-={}\\:;'<>?./]+$");
//			                var result = re.test(shortDescEn);
//					        if(!result){    
//					        	 jQuery("#shortDescEn_err").html(T.T('item_name_en_err')+"[0-9]|[A-Z]|[ `~!@#$%^&*()_+-={}\:;'<>?./]");
//					        	 html += "1";
//					         }else{
					        	 jQuery("#shortDescEn_err").html("");
					        // }    
						}  
						
						var shortDescTc = jQuery("#shortDescTc").val();
						if (shortDescTc == "") {
							jQuery("#shortDescTc_err").html(
									T.T('item_name(tc)_is_required'));
							html += "1";
						} else {
							jQuery("#shortDescTc_err").html("");

						}


//						if ($scope.productinfo.brandCode == null) {
//							jQuery("#brandCode_err").html(
//									T.T('brand_should_is_required'));
//							html += "1";
//						} else {
//							jQuery("#brandCode_err").html("");
//
//						}

						var deliveryMode = jQuery("#deliveryMode").val();

						if (deliveryMode == "? undefined:undefined ?") {
							jQuery("#deliveryMode_err").html(
									T.T('Delivery_mode_is_required'));
							html += "1";
						} else {
							jQuery("#deliveryMode_err").html("");
							if (deliveryMode == 'D' || deliveryMode == "W") {
								var dailyInventory = jQuery("#dailyInventory")
										.val();
								if (dailyInventory == "?"
										|| dailyInventory == "") {
									jQuery("#dailyInventory_err").html(
											T.T('daily_inventory_is_required'));
									html += "1";
								} else {
									jQuery("#dailyInventory_err").html("");
								}
						
							} else {
								jQuery("#dailyInventory_err").html("");
								var maxReplenishmentLevel = jQuery("#maxReplenishmentLevel").val();
								if(""==maxReplenishmentLevel){
									jQuery("#maxReplenishmentLevel_err").html(
											T.T("maxReplenishmentLevel_is_required"));
									html += "1";
									
								}else{
									if (maxReplenishmentLevel.length != 0 && !validateNumber(maxReplenishmentLevel)) {
										jQuery("#maxReplenishmentLevel_err").html(
												T.T("invalid_maxReplenishmentLevel_format"));
										html += "1";
									}else{
										jQuery("#maxReplenishmentLevel_err").html("");
									}

								}

								var minReplenishmentLevel = jQuery("#minReplenishmentLevel").val();
								if (minReplenishmentLevel.length != 0 && !validateNumber(minReplenishmentLevel)) {
									jQuery("#minReplenishmentLevel_err").html(
											T.T("invalid_minReplenishmentLevel_format"));
									html += "1";
								}else{
									jQuery("#minReplenishmentLevel_err").html("");
								}
								
								if (validateNumber(maxReplenishmentLevel)) {
									var minOrderQty = $scope.productinfo.minOrderQty;
									if(parseInt(minReplenishmentLevel)>parseInt(maxReplenishmentLevel)){
										jQuery("#minReplenishmentLevel_err").html(
												T.T("invalid_minReplenishmentLevel_value"));
										html += "1";
									}
									
									if(minOrderQty>maxReplenishmentLevel){
										jQuery("#maxReplenishmentLevel_err").html(
												T.T("invalid_maxReplenishmentLevel_value"));
										html += "1";
									}
								}
							
							}
						}
						
						var pnsOnlineDeliveryType=jQuery("#pnsOnlineDeliveryType").val();
						if("?"==pnsOnlineDeliveryType || ""==pnsOnlineDeliveryType)
						{
							jQuery("#pnsOnlineDeliveryType_err").html(
									T.T("pnsOnlineDeliveryType_is_required"));
							html += "1";
						}else{
							jQuery("#pnsOnlineDeliveryType_err").html("");
						}
						
						
						var originCountry = jQuery("#originCountry").val();

						if (originCountry == "?" || "" == originCountry) {
							jQuery("#originCountry_err").html(
									T.T("originCountry_is_required"));
							html += "1";
						} else {
							jQuery("#originCountry_err").html("");
						}
						
						var sizeDesc = jQuery("#sizeDesc").val();

						if (sizeDesc == "?" || "" == sizeDesc) {
							jQuery("#sizeDesc_err").html(
									T.T("sizeDesc_is_required"));
							html += "1";
						} else {
							jQuery("#sizeDesc_err").html("");
						}

						var standardUom = jQuery("#standardUom").val();

						if (standardUom == "?") {
							jQuery("#standardUom_err").html(
									T.T("standard_uom_is_required"));
							html += "1";
						} else {
							jQuery("#standardUom_err").html("");

						}

						var onlineDate = jQuery("#onlineDate").val();
						var offlineDate = jQuery("#offlineDate").val();

						if (onlineDate == "" && offlineDate != "") {
							jQuery("#onlineDate_err").html(
									T.T('online_date_is_required'));
							html += "1";
						} else {
							jQuery("#onlineDate_err").html("");
						}

						if (onlineDate != "" && offlineDate != "") {
							var onlineDateArr=onlineDate.split("/");
							var offlineDateArr=offlineDate.split("/");
							var onlineDateStr=onlineDateArr[2]+onlineDateArr[0]+onlineDateArr[1];
							var offlineDateStr=offlineDateArr[2]+offlineDateArr[0]+offlineDateArr[1];
							if (onlineDateStr > offlineDateStr) {
								jQuery("#offlineDate_err")
										.html(
												T
														.T('offline_date_should_be_bigger_than_online_date'));
								html += "1";
							} else {
								jQuery("#offlineDate_err").html("");
								jQuery("#onlineDate_err").html("");
							}
						}

						var unitRetail = jQuery("#unitRetail").val();
						if (unitRetail.length == 0) {
							jQuery("#unitRetail_err").html(
									T.T('unit_retail_weight_is_required'));
							html += "1";

						} else if (unitRetail.length != 0
								&& !validateNumber(unitRetail)) {
							jQuery("#unitRetail_err").html(
									T.T("invalid_unit_retail_weight_format"));
							html += "1";
						} else {
							jQuery("#unitRetail_err").html("");

						}

						var grossWeight = jQuery("#grossWeight").val();
						if (grossWeight.length != 0
								&& !validateNumber(grossWeight)) {
							jQuery("#grossWeight_err").html(
									T.T("invalid_gross_weight_format"));
							html += "1";
						} else {
							jQuery("#grossWeight_err").html("");

						}

						var shippingWeight = jQuery("#shippingWeight").val();
						if (shippingWeight.length != 0
								&& !validateNumber(shippingWeight)) {
							jQuery("#shippingWeight_err").html(
									T.T("invalid_shipping_weight_format"));
							html += "1";
						} else {
							jQuery("#shippingWeight_err").html("");

						}

						var caseDimLength = jQuery("#caseDimLength").val();
						if (caseDimLength.length != 0
								&& !validateNumber(caseDimLength)) {
							jQuery("#caseDimLength_err").html(
									T.T("invalid_case_dim_length_format"));
							html += "1";
						} else {
							jQuery("#caseDimLength_err").html("");

						}

						var caseDimWidth = jQuery("#caseDimWidth").val();
						if (caseDimWidth.length != 0
								&& !validateNumber(caseDimWidth)) {
							jQuery("#caseDimWidth_err").html(
									T.T("invalid_case_dim_width_format"));
							html += "1";
						} else {
							jQuery("#caseDimWidth_err").html("");

						}

						var caseDimHeight = jQuery("#caseDimHeight").val();
						if (caseDimHeight.length != 0
								&& !validateNumber(caseDimHeight)) {
							jQuery("#caseDimHeight_err").html(
									T.T("invalid_case_dim_height_format"));
							html += "1";
						} else {
							jQuery("#caseDimHeight_err").html("");

						}

						var shippingDimLength = jQuery("#shippingDimLength")
								.val();
						if (shippingDimLength.length != 0
								&& !validateNumber(shippingDimLength)) {
							jQuery("#shippingDimLength_err").html(
									T.T("invalid_shipping_dim_length_format"));
							html += "1";
						} else {
							jQuery("#shippingDimLength_err").html("");

						}

						var shippingDimHeight = jQuery("#shippingDimHeight")
								.val();
						if (shippingDimHeight.length != 0
								&& !validateNumber(shippingDimHeight)) {
							jQuery("#shippingDimHeight_err").html(
									T.T("invalid_shipping_dim_height_format"));
							html += "1";
						} else {
							jQuery("#shippingDimHeight_err").html("");
						}

						var shippingDimWidth = jQuery("#shippingDimWidth")
								.val();
						if (shippingDimWidth.length != 0
								&& !validateNumber(shippingDimWidth)) {
							jQuery("#shippingDimWidth_err").html(
									T.T("invalid_shipping_dim_width_format"));
							html += "1";
						} else {
							jQuery("#shippingDimWidth_err").html("");
						}

						var productDimLength = jQuery("#productDimLength")
								.val();
						if (productDimLength.length != 0
								&& !validateNumber(productDimLength)) {
							jQuery("#productDimLength_err").html(
									T.T("invalid_product_dim_length_format"));
							html += "1";
						} else {
							jQuery("#productDimLength_err").html("");

						}

						var productDimWidth = jQuery("#productDimWidth").val();
						if (productDimWidth.length != 0
								&& !validateNumber(productDimWidth)) {
							jQuery("#productDimWidth_err").html(
									T.T("invalid_product_dim_width_format"));
							html += "1";
						} else {
							jQuery("#productDimWidth_err").html("");

						}

						var productDimHeight = jQuery("#productDimHeight")
								.val();
						if (productDimHeight.length != 0
								&& !validateNumber(productDimHeight)) {
							jQuery("#productDimHeight_err").html(
									T.T("invalid_product_dim_height_format"));
							html += "1";
						} else {
							jQuery("#productDimHeight_err").html("");

						}

						var casePackCase = jQuery("#casePackCase").val();
						if (casePackCase.length != 0
								&& !validateInteger(casePackCase)) {
							jQuery("#casePackCase_err").html(
									T.T("invalid_case_pack_case_format"));
							html += "1";
						} else {
							jQuery("#casePackCase_err").html("");
						}

						var casePackInner = jQuery("#casePackInner").val();

						if (casePackInner.length != 0
								&& !validateInteger(casePackInner)) {
							jQuery("#casePackInner_err").html(
									T.T("ivalid_case_pack_inner_format"));
							html += "1";
						} else {
							jQuery("#casePackInner_err").html("");
						}

						var consignmentRate = jQuery("#consignmentRate").val();
						if (consignmentRate.length != 0
								&& !validateNumber(consignmentRate)) {
							jQuery("#consignmentRate_err").html(
									T.T("invalid_consignment_rate_format"));
							html += "1";
						} else if (consignmentRate.length != 0
								&& validateNumber(consignmentRate)
								&& consignmentRate > 100) {
							jQuery("#consignmentRate_err")
									.html(
											T
													.T("consignment_rate_should_not_be_bigger_than_100"));
							html += "1";
						} else {
							jQuery("#consignmentRate_err").html("");
						}

						var flag = true;
						if (html != "") {
							alertService.add("danger",T.T("please_correct_below_error"));
							flag = false;
						}
						return flag;

					}

					function validateNumber(text) {
						var flag = true;
						var re = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
						var result = re.test(text);
						if (!result) {
							flag = false;
						}
						return flag;
					}
					function validateInteger(text) {
						var flag = true;
						var re = new RegExp("^[1-9]\\d*$");
						var result = re.test(text);
						if (!result) {
							flag = false;
						}
						return flag;
					}

					function myScroll() {
						var x = document.body.scrollTop
								|| document.documentElement.scrollTop;
						var timer = setInterval(function() {
							x = x - 500;
							if (x < 100) {
								x = 0;
								window.scrollTo(x, x);
								clearInterval(timer);
							}
							window.scrollTo(x, x);
						}, "1");
					}

					// ===== START update history
					// =============================================================================

					// START ======================== AngularJS Smart-Table
					// select all rows directive ===============
					$scope.vm = this;
					// $window.open('http://www.c-sharpcorner.com/1/302/angularjs.aspx',
					// 'C-Sharpcorner', 'width=500,height=400');
					// Declare the array for the selected items
					$scope.vm.selected = [];

					// Function to get data for all selected items
					$scope.vm.selectAll = function(collection) {

						// if there are no items in the 'selected' array,
						// push all elements to 'selected'
						if ($scope.vm.selected.length === 0) {

							angular.forEach(collection, function(val) {

								$scope.vm.selected.push(val);

							});

							// if there are items in the 'selected' array,
							// add only those that ar not
						} else if ($scope.vm.selected.length > 0
								&& $scope.vm.selected.length != $scope.vm.data.length) {

							angular.forEach(collection, function(val) {

								var found = $scope.vm.selected.indexOf(val);

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

					}

					$scope.vm.rowCollection = [];

					// for (id; id < 11; id++) {
					// $scope.vm.rowCollection.push(generateRandomItem(id));
					// }

					$scope.vm.data = [];// [].concat($scope.vm.rowCollection);
					// END =====================================AngularJS
					// Smart-Table select all rows directive
					// =====================================

					$scope.dateOptions2 = {
						dateDibled : false,
						formatYear : 'yy',
						maxDate : new Date(2020, 5, 22),
						// minDate: new Date(),
						startingDay : 1
					};
					$scope.click_update_history = function() {

						$scope.search_update_history();
					}

					$scope.search_update_history = function() {
						if ($scope.productId_staging_for_history == null
								|| $scope.productId_staging_for_history == '')
							return;
						
						var fr = toDDMMMYYYY($scope.productinfo.actionTimeFr);
						var to = toDDMMMYYYY($scope.productinfo.actionTimeTo);

 var jqueryFr= jQuery("#fm").val();
 var jqueryTo = jQuery("#to").val();
   
 if(jqueryFr =='' || jqueryFr==null ){
 
 }else  if(fr==null ){
 	alertService.add('danger',T.T('check_date_format_fr'));
 	return;
 }


 if(jqueryTo =='' || jqueryTo==null){
 
 }else  if(to==null ){
 	alertService.add('danger',T.T('check_date_format_to'));
 	return;
 }
 
						if ($scope.productinfo.actionTimeFr != null
								&& $scope.productinfo.actionTimeFr != ''
								&& $scope.productinfo.actionTimeTo != null
								&& $scope.productinfo.actionTimeTo != '') {
							if ($scope.productinfo.actionTimeFr > $scope.productinfo.actionTimeTo) {

								$scope.closeAlert = alertService.closeAlert;
								alertService
										.add('danger',
												"From date can't be larger than to date");
								return;
							}
						}

						 $scope.promise = $http(
								{
									method : 'GET',
									params : {
										productId : $scope.productId_staging_for_history,
										userId : $scope.productinfo.userId,
										action : $scope.productinfo.action,
										actionTimeFr : fr,
										actionTimeTo : to
									},
									url : "./productUpdateHist/viewUpHist"
								}).success(function(data) {

							// START ======================== AngularJS
							// Smart-Table select all rows directive
							// ===============
								//	console.log(data);
							$scope.vm.rowCollection = data;
							$scope.vm.data = [].concat(data);
							// END ======================== AngularJS
							// Smart-Table select all rows directive
							// ===============
							alertService.cleanAlert();

						});
					};

					
					
					//閺傝纭跺ǎ璇插
					
					
					
					
					
					
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

					// $scope.search_update_history();

					// ===== END update history
					// ========================================================================

				})

		.directive('stRatio', function() {
			return {
				link : function(scope, element, attr) {
					var ratio = +(attr.stRatio);

					element.css('width', ratio + '%');

				}
			};
		});
