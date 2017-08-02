'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description # ProductViewCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
	.controller('ShopBasicInfoCtrl',
		[ 		'$scope',
				'$state',
				'$http',
				'$interval',
				'alertService',
				'localStorageService',
				'$stateParams',
				'$confirm',
				'ngDialog',
				'$q',
				'T',
				function($scope, $state, $http, $interval, alertService, localStorageService,
						$stateParams,$confirm, ngDialog, $q, T) {
			
			//init show type
			$scope.showBasicInfo = true;
			$scope.showShopIp = false;
			$scope.showShopUser = false;
			$scope.showShopCategory = false;
			$scope.showShopContract = false;
			
			$scope.isSaveCk = false;
			
			$scope.shopInfo = {};
			$scope.shopId = null;
			$scope.shopName = $stateParams.shopName;
			$scope.shopInfoOldStr;
			$scope.shopInfo.shopBasicInfo = {};
			$scope.shopInfo.shopBasicInfo.id = null;
			$scope.shopInfo.shopBasicInfo.shopName = null;
			$scope.shopInfo.shopBasicInfo.shopCode = null;
			$scope.shopInfo.shopBasicInfo.respPerson = null;
			$scope.shopInfo.shopBasicInfo.contactPerson = null;
			$scope.shopInfo.shopBasicInfo.contactEmail = null;
			$scope.shopInfo.shopBasicInfo.telNo = null;
			$scope.shopInfo.shopBasicInfo.fax = null;
			$scope.shopInfo.shopBasicInfo.address = null;
			$scope.shopInfo.shopBasicInfo.address2 = null;
			$scope.shopInfo.shopBasicInfo.address3 = null;
			$scope.shopInfo.shopBasicInfo.address4 = null;
			$scope.shopInfo.shopBasicInfo.contractStartDate = null;
			$scope.shopInfo.shopBasicInfo.contractEndDate = null;
			$scope.shopInfo.shopBasicInfo.websiteName = null;
			$scope.shopInfo.shopBasicInfo.websiteDomain = null;
			$scope.shopInfo.shopBasicInfo.domainSuffix = null; //??
			$scope.shopInfo.shopBasicInfo.websiteEmailDomain = null;
			$scope.shopInfo.shopBasicInfo.websiteContactEmail = new Date().valueOf();
			$scope.shopInfo.shopBasicInfo.useShopCartInd = "Y";
			$scope.shopInfo.shopBasicInfo.websiteStatus = 2;
			$scope.shopInfo.shopBasicInfo.websiteActiveInd = 'Y';
			$scope.shopInfo.shopBasicInfo.ownerWebSiteStatus = '';
			$scope.shopInfo.shopBasicInfo.ownerStatus = '';
			$scope.shopInfo.shopBasicInfo.websiteOnlineDate = "";
			$scope.shopInfo.shopBasicInfo.websiteInactiveDate = "";
			
			$scope.isEdit = false;
			$scope.state = $stateParams.state;
			
			$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
		
			$scope.ownerStatuses = [ {
				code : '',
				description : ''
			}, {
				code : "1",
				description : '\u5efa\u7f6e\u4e2d'
			}, {
				code : "2",
				description : '\u8fd0\u8425\u4e2d'
			}, {
				code : "3",
				description : '\u5df1\u95dc\u5e97'
			} ]
			
			$scope.websiteStatuses = [ {
				code : '',
				description : ''
			}, {
				code : "1",
				description : '\u5efa\u7f6e\u4e2d'
			}, {
				code : "2",
				description : '\u5df1\u4e0a\u7dda'
			}, {
				code : "3",
				description : '\u5df1\u4e0b\u7dda'
			} ]
			
			$scope.userroles = [ {
				code : "",
				description : ''
			}, {
				code : "SYSTEMADMIN",
				description : 'SYSTEMADMIN'
			}, {
				code : "APPROVER",
				description : 'APPROVER'
			}, {
				code : "SUPPLIER",
				description : 'SUPPLIER'
			} ]
			
			
			// 初始化基本信息
			if ($stateParams.shopId != null) {
				if(!$scope.state) {
					$scope.isEdit = true;
				}
				var shopid = $stateParams.shopId;
				$scope.shopId = shopid;
				$scope.promise = $http({
					method : 'GET',
					params : {shopId : shopid},
					url : "./shop/shopDetail"
				}).success(function(data) {
					console.log(data);
					$scope.shopInfo.shopBasicInfo = data; 
					$scope.shopInfoOldStr = JSON.stringify($scope.shopInfo.shopBasicInfo)
				});
			}
			
			$scope.save = function(id, flag) {
				$scope.isSaveCk = true;
				//console.log($scope.shopInfo.shopBasicInfo);
				if($scope.check()) {
					return;
				}
				var deferred = $q.defer();
				$scope.promise = $http.post('./shop/saveShop', $scope.shopInfo.shopBasicInfo).success(function(data) {
	            	  deferred.resolve(data);
	                   if (data['errorType'] == "success") {
	                	   $scope.shopName = $scope.shopInfo.shopBasicInfo.shopName;
	                	   $scope.shopInfo.shopBasicInfo=data.returnData;
	                       alertService.cleanAlert();
	                       if(id == null){
	                    	   alertService.add(data["errorType"], "add successful");
	                           $state.go('main.system_shop');
	                     	 }
	                         else{
	                      	   alertService.add(data["errorType"], "update successful");
		                   }
	                   }else {
	                	    alertService.cleanAlert();
	                	    alertService.msg = data["errorMessage"];
	                	   	if(data["returnData"].saveSuccess)  {
	                	   		if(!flag && !$scope.isEdit) {
	                	   			$state.go('main.system_shop');
	                	   		} else {
	                	   			alertService.showMsg();
	                	   		}
	                	   	} else {
	                	   		alertService.showMsg();
	                	   	}
	                   }     

		              }).error(function(data,status,headers,config){
		             	 deferred.reject();     
		              });
		     		 $scope.promise = deferred.promise;
		     		$scope.shopInfoOldStr = JSON.stringify($scope.shopInfo.shopBasicInfo);
		     		
			}
			
			$scope.checkUnq = function(type) {
				$scope.searchVo = {};
				$scope.searchVo.id = $scope.shopInfo.shopBasicInfo.id;
				if(type == "code") {
					if($scope.shopInfo.shopBasicInfo.shopCode == null) return;
					$scope.searchVo.shopCode = $scope.shopInfo.shopBasicInfo.shopCode;
				}else if (type="websiteEmailDomain") {
					if($scope.shopInfo.shopBasicInfo.websiteEmailDomain == null) return;
					$scope.searchVo.websiteEmailDomain = $scope.shopInfo.shopBasicInfo.websiteEmailDomain;
				}else if (type="websiteDomain") {
					if($scope.shopInfo.shopBasicInfo.websiteDomain == null) return;
					$scope.searchVo.websiteDomain = $scope.shopInfo.shopBasicInfo.websiteDomain;
				}
				console.log($scope.searchVo);
				$http.post('./shop/check', $scope.searchVo).success(function(data) {
					if(data["errorType"] == "danger"){
						if(type == "code") {
							$scope.codeError = true;
						}else if (type="websiteEmailDomain") {
							$scope.websiteEmailDomainError = true;
						}else if (type="websiteDomain") {
							$scope.websiteDomainError = true;
						}
					} else {
						if(type == "code") {
							$scope.codeError = false;
						}else if (type="websiteEmailDomain") {
							$scope.websiteEmailDomainError = false;
						}else if (type="websiteDomain") {
							$scope.websiteDomainError = false;
						}
					}
				});
			} 
			
			$scope.cancel2 = function() {
				$state.go('main.system_shop');
			}
			
			$scope.check = function() {
				if($scope.shopInfo.shopBasicInfo.shopCode == null) return true;
				if($scope.shopInfo.shopBasicInfo.shopName == null) return true;
				if($scope.shopInfo.shopBasicInfo.contactEmail == null) return true;
				if($scope.shopInfo.shopBasicInfo.contactPerson == null) return true;
				if($scope.shopInfo.shopBasicInfo.respPerson == null) return true;
				if($scope.shopInfo.shopBasicInfo.websiteName == null) return true;
				if($scope.shopInfo.shopBasicInfo.websiteEmailDomain == null) return true;
				return false;
			}
			
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
				var re = new RegExp(
						"^[+]?([0-9]+(.[0-9]{1})?)$");
				var result = re.test(text);
				if (!result) {
					flag = false;
				}
				return flag;
			}
			
			$scope.$on('$stateChangeStart', function(event, toState, toStateParams, fromState, fromStateParams) {
				if (JSON.stringify($scope.shopInfo.shopBasicInfo) != $scope.shopInfoOldStr) {
					if(toState.templateUrl === "views/pages/systemShopAdd.html") {
						var popupMsg = T.T('popup_msg_save_info');
						if(confirm(popupMsg)) {
							$scope.save($scope.shopId, true);
						} else {
							event.preventDefault(); //终止事件 
						}
					}
				}
			})
		} ]);

