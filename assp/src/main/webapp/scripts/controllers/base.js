'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:menuAddCtrl
 * @description # menuAddCtrl Controller of the psspAdminApp
 */
angular
		.module('psspAdminApp')
		.controller(
				'BaseCtrl',
				[
						'$scope',
						'$state',
						'$http',
						'$interval',
						'alertService',
						'localStorageService',
						'$stateParams',
						'ngDialog',
						'T',
						'$location',
						function($scope, $state, $http, $interval,alertService, localStorageService, $stateParams, ngDialog,T,$location) {
							
							$scope.getPowers = function() {
								$http.post('./login/getCurrentPowers', {
									url : $location.url().replace('/main/', '')
								}).success(function(data) {
									$scope.powers = data.returnData;
								});
							};
							
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
							
							$scope.auditTrail = function() {
								if ($scope.selected.length != 1) {
									alertService.add('danger',
											T.T('select_records'));
									return;
								}
								$scope.auditTrailData = $scope.selected[0];
								ngDialog.open({
									className : 'ngdialog-theme-default custom-width-800',
									template : 'views/pages/power/auditTrailDialog.html',
									closeByEscape: false,
									closeByDocument: false,
									scope : $scope,
									controller : [ '$scope', function($scope) {
										$scope.cancel = function() {
											$scope.closeThisDialog(0);
										};
									} ],
									preCloseCallback : function(value) {
										alertService.cleanAlert();
									}
								});
							};
							
						}]);