'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:MenuManagerCtrl
 * @description # MenuManagerCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp').controller('MallCtrl',
	function($scope, $http,$stateParams,cacheService, $state, localStorageService, alertService, $location
			, ngDialog, T, $controller) {
		$controller("BaseCtrl", {$scope:$scope});
		$scope.tableData = [];
		$scope.selected = [];
		$scope.searchmodel = cacheService.getCache();
		cacheService.setCache($scope.searchmodel);
		$scope.pageModel = $scope.searchmodel;
		
		$scope.init=function(){
			$scope.getPowers();
			alertService.showMsg();
		};
		
		$scope.select1 = function(mall) {
			$state.go('main.mall_add', {'id' : mall.id, 'state':true});
		};
		
		$scope.create = function() {
			$state.go('main.mall_add');
		};
		
				
		$scope.search = function (flag){
			$http.post('./mall/search', $scope.searchmodel).success(
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
		};
		
		$scope.edit = function() {
			if ($scope.selected.length != 1) {
				
				alertService.add('danger', T.T('select_records'));
				return;
			}
			var id = $scope.selected[0]['id'];
			$state.go('main.mall_add', {'id' : id});
		};
		
		$scope.view= function(){
			if ($scope.selected.length != 1) {
				
				alertService.add('danger', T.T('select_records'));
				return;
			}
			var id = $scope.selected[0]['id'];
			var state=true;
			$state.go('main.mall_add', {'id' : id,'state':state});
		};
			
		$scope.resert= function () {
			$scope.searchmodel.code=undefined;
			$scope.searchmodel.name=undefined;
			cacheService.clearCache();
		};
		
		$scope.deletes = function(isDelete) {
			if ($scope.selected.length < 1) {
				alertService.add('danger', T.T('select_records'));
				return;
			}
			 if (!isDelete) {
				    $('#confirmModal').modal({keyboard: false});
				    return;
				      }
			var ids = "";
			for(var i=0; i<$scope.selected.length; i++) {
				ids += $scope.selected[i].id + ";";
			}
			$http.post('./mall/delete', ids).success(
				function(data) {
					if (data['errorType'] == "success") {
						$scope.selected = [];
						$http.post('./mall/search', $scope.searchmodel).success(
								function(data) {
									if (data['errorType'] == "success") {
										$scope.tableData = data.returnDataList;
									}
						});
						alertService.add("success",T.T('delete_success'));
					}else {
						
						alertService.add(data["errorType"],data["errorMessage"]);
					}
				}
			);
		};
	}
);