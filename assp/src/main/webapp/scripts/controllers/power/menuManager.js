'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:MenuManagerCtrl
 * @description # MenuManagerCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp').controller('MenuManagerCtrl',
	function($scope, $http,$stateParams,cacheService, $state, localStorageService, alertService, $location
			, ngDialog, T, $controller) {
		$controller("BaseCtrl",{$scope:$scope});
		$scope.tableData = [];
		$scope.selected = [];
		$scope.menuNames=[];
		$scope.funcNamelist=[];
		$scope.searchmodel = cacheService.getCache();
		cacheService.setCache($scope.searchmodel);
		$scope.pageModel = $scope.searchmodel;
		
		$scope.init=function(){
			$scope.getPowers();
			$scope.getMenuName();
			alertService.showMsg();
		};
		
			 $scope.getMenuName=function(){
				 $http.post('./menuManager/getMenuName').success(
						 function(data) {
							 if (data['errorType'] == "success") {	
								 $scope.menuNames = data.returnDataList;
							} else {
								 
								 alertService.add(data["errorType"],
										 data["errorMessage"]);
							 }
						 }
				 );
				 
			 };
			 
		$scope.select1 = function(menu) {
			$state.go('main.menu_add', {'id' : menu.id});
		};
		
		$scope.create = function() {
			$state.go('main.menu_add');
		};
		
				
		$scope.search = function (flag){
			$http.post('./menuManager/searchMenu', $scope.searchmodel).success(
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
			$state.go('main.menu_add', {'id' : id});
		};
		
		$scope.view= function(){
			if ($scope.selected.length != 1) {
				
				alertService.add('danger', T.T('select_records'));
				return;
			}
			var id = $scope.selected[0]['id'];
			var state=true;
			$state.go('main.menu_add', {'id' : id,'state':state});
		};
			
		$scope.resert= function () {
			$scope.searchmodel.lev1=undefined;
			$scope.searchmodel.lev2=undefined;
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
			$http.post('./menuManager/delete', $scope.selected).success(
				function(data) {
					if (data['errorType'] == "success") {
						$scope.selected = [];
						$scope.getMenuName();
						$scope.searchmodel.lev1=undefined;
						$scope.searchmodel.lev2=undefined;
						$http.post('./menuManager/searchMenu', $scope.searchmodel).success(
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
		
		$scope.funcNameChange = function(){	
			$scope.funcNamelist=[];
			if($scope.searchmodel.lev1!=null){
				$http.post('./menuManager/getfuncName', $scope.searchmodel).success(
						function(data) {
							if (data['errorType'] == "success") {	
								$scope.funcNamelist = data.returnDataList;
								alertService.cleanAlert();
							} else if(data['errorType'] != null){
								
								
							}
						}
				);												 				
			}
     	};
		
		$scope.auditTrail = function(){
	   		 if($scope.selected.length != 1){
	       		 
	       		 alertService.add('danger', T.T('select_records'));
	   			 return;
	            }
	   		 $scope.auditTrailData = $scope.selected[0];
	   		 ngDialog.open({
	   	  		    className: 'ngdialog-theme-default custom-width-800',
	   	  		    template: 'views/pages/power/auditTrailDialog.html',
		   	  		closeByEscape: false,
					closeByDocument: false,
	   	  		    scope: $scope,
	   	  		    controller: ['$scope', 
	   							function($scope) {
	       				$scope.cancel = function (){
	       					$scope.closeThisDialog(0);
	       				};
	   	  		    }],
	   	  		    preCloseCallback: function(value) {
	   	  		    	  alertService.cleanAlert();
	   	  		    }
	   	  		});
	        };
	}
);