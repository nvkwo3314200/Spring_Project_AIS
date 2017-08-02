'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:MenuManagerCtrl
 * @description # MenuManagerCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp').controller('ParmCtrl',
	function($scope, $http,$stateParams,cacheService, parmService, $state, localStorageService, alertService, $location
			, ngDialog, T, $controller) {
		$controller("BaseCtrl", {$scope:$scope});
		$scope.tableData = [];
		$scope.selected = [];
		$scope.searchmodel = cacheService.getCache();
		cacheService.setCache($scope.searchmodel);
		$scope.pageModel = $scope.searchmodel;
		$scope.isCheck = false;
		
		$scope.init=function(){
			$scope.getPowers();
			alertService.showMsg();
		};
			  
		if(parmService.mallVoList) { 
			$scope.mallVoList = parmService.mallVoList;
		} else {
			$http.post('./mall/mallList',{}).success(
				function(data) {
					$scope.mallVoList = data["returnDataList"];
					parmService.mallVoList = $scope.mallVoList;
				}
			);
		}
		
		$scope.changeSegment = function() {
			var obj = {};
			obj.mallId = $scope.searchmodel.mallId;
			if(obj.mallId != null && obj.mallId != "" && obj.mallId != undefined) {
				$http.post('./parm/segmentList',obj).success(
					function(data) {
						$scope.segmentList = data["returnDataList"]; 
					}
				);
			} else {
				$scope.segmentList = {};
			}
		}
		
		$scope.selectAll = function(collection) {
			if ($scope.selected.length === 0) {
				angular.forEach(collection, function(val) {
					$scope.selected.push(val);
				});
			} else if ($scope.selected.length > 0 && $scope.selected.length != $scope.tableData.length) {
				angular.forEach(collection, function(val) {
					var found = $scope.selected.indexOf(val);
					if (found == -1)
						$scope.selected.push(val);
				});
			} else {
				$scope.selected = [];
			}
		};

		$scope.select = function(id) {
			var found = $scope.selected.indexOf(id);
			if (found == -1)
				$scope.selected.push(id);
			else
				$scope.selected.splice(found, 1);
			
			//=====Add Style=====
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
		};
		
		$scope.select1 = function(parm) {
			$state.go('main.sys_parm_add', {'id' : parm.id, 'state':true});
		};
		
		$scope.create = function() {
			$state.go('main.sys_parm_add');
		};
		
		$scope.search = function (flag){
			if(flag) $scope.isCheck = true;
			if($scope.searchmodel.mallId != null && $scope.searchmodel.segment != null) {
				$http.post('./parm/search', $scope.searchmodel).success(
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
			}
		};
		
		$scope.edit = function() {
			if ($scope.selected.length != 1) {
				
				alertService.add('danger', T.T('select_records'));
				return;
			}
			var id = $scope.selected[0]['id'];
			$state.go('main.sys_parm_add', {'id' : id});
		};
		
		$scope.view= function(){
			if ($scope.selected.length != 1) {
				
				alertService.add('danger', T.T('select_records'));
				return;
			}
			var id = $scope.selected[0]['id'];
			var state=true;
			$state.go('main.sys_parm_add', {'id' : id,'state':state});
		};
			
		$scope.resert= function () {
			$scope.searchmodel.mallId=undefined;
			$scope.searchmodel.segment=undefined;
			$scope.segmentList={};
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
			$http.post('./parm/delete', ids).success(
				function(data) {
					console.log(data);
					if (data['errorType'] == "success") {
						$scope.selected = [];
						$http.post('./parm/search', $scope.searchmodel).success(
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
		
		if(parmService.mallId) {
			$scope.searchmodel.mallId = parmService.mallId;
			$scope.changeSegment();
		}
		if(parmService.segment) {
			$scope.searchmodel.segment = parmService.segment;
		}
		
		$scope.$on('$stateChangeStart', function(event, toState, toStateParams, fromState, fromStateParams) {
			if(toState.templateUrl.indexOf("/parm") > -1) {
				parmService.segment = $scope.searchmodel.segment;
				parmService.mallId = $scope.searchmodel.mallId;
			}else {
				parmService.segment = null;
				parmService.mallId = null;
			}
		});
	}
);