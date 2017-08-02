'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProfileCtrl
 * @description # ProfileCtrl Controller of the psspAdminApp
 */
angular.module('psspAdminApp').controller(
		'BrandCtrl',['$scope', '$state', '$http', '$interval', 
          'alertService', 'localStorageService', '$stateParams','T','ngDialog','uiUploader','$q',
       function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog,uiUploader,$q) {

			$scope.brand_code = "";
			$scope.desc_en = "";
			$scope.desc_tc = "";
			$scope.desc_sc = "";
			$scope.sys_ref = "";
			$scope.masterId = "";
			$scope.watsonsMallInd = "";
			$scope.supplierId = "";

			$scope.page = 10;

			$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
			  $scope.brandCurrentData = null;
			// START ======================== AngularJS Smart-Table select all
			// rows directive ===============
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

				if (found == -1)	$scope.vm.selected.push(id);

				else	$scope.vm.selected.splice(found, 1);

                //=====Sheldon=====
           	 	var tableTr = document.getElementById('tr'+id.brandCode);
           	 	
                var tableInput = tableTr.getElementsByTagName('input')[0];
              
              if(tableInput.checked==false && found == -1){
             	 tableTr.className='ng-scope : st-selected';
 	             tableInput.checked=true;
             	 }
              else if(tableInput.checked==true && found !=-1){
 	             tableTr.className='ng-scope : null';
 	             tableInput.checked=false;
             	 }

				
			}

			$scope.vm.rowCollection = [];

			// for (id; id < 11; id++) {
			// $scope.vm.rowCollection.push(generateRandomItem(id));
			// }

			$scope.vm.data = [];// [].concat($scope.vm.rowCollection);
			// END =====================================AngularJS Smart-Table
			// select all rows directive =====================================

			$scope.search = function() {

				
				var postData = {brandCode : $scope.brand_code,
						descEn : $scope.desc_en,
						descTc : $scope.desc_tc,
						descSc : $scope.desc_sc,
						sysRef : $scope.sys_ref,
						masterId : $scope.masterId,
						watsonsMallInd : $scope.watsonsMallInd,
						supplierId : $scope.supplierId};
				
				$scope.promise = $http({
      			  method:'POST', 
      			  data:$.param(postData), 
      			  url:"./brand/view",
      			headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
      			}).
//				$scope.promise = $http({
//					method : 'GET',
//					params : {
//						brandCode : $scope.brand_code,
//						descEn : $scope.desc_en,
//						descTc : $scope.desc_tc,
//						descSc : $scope.desc_sc,
//						sysRef : $scope.sys_ref,
//						masterId : $scope.masterId,
//						watsonsMallInd : $scope.watsonsMallInd,
//						supplierId : $scope.supplierId
//					},
//					url : "./brand/view"}).
				success(function(data) {

					// START ======================== AngularJS
					// Smart-Table select all rows directive
					// ===============
					$scope.vm.rowCollection = data;
					$scope.vm.data = [].concat(data);
					// END ======================== AngularJS
					// Smart-Table select all rows directive
					// ===============
				});
				alertService.cleanAlert();

//				if (data_return != null) {
//					if (data_return.errorMessage == null) {
//						alertService.add(data_return["errorType"], T
//								.T('update_successful'));
//					} else {
//						$scope.closeAlert = alertService.closeAlert;
//						alertService.add(data_return["errorType"],
//								data_return["errorMessage"]);
//					}
//				}
			};

			// 进页面刷新
			$scope.search();

			$scope.updateWatsonsMallInd = function() {
				update('Y');
			};

			$scope.cancelWatsonsMallInd = function() {
				update('N');
			};

			
			function update(watsonsMallInd) {
				
				alertService.cleanAlert();
				var checkSelected = [];

				if ($scope.vm.selected.length <= 0) {
					$scope.closeAlert = alertService.closeAlert;
					alertService.add('danger', T.T('select_records'));
					return;
				}

				var deferred = $q.defer();

				for (var i = 0; i < $scope.vm.selected.length; i++) {
					var model = $scope.vm.selected[i];
					checkSelected.push(model);
				}

				var data = {
					'flag' : watsonsMallInd,
					'brandList' : checkSelected
				};

				$http.post('./brand/updateWatsonsMallInd', data).success(
						function(data) {
							$scope.closeAlert = alertService.closeAlert;

							if (data['errorType'] == "success") {
								$scope.search();
								 alertService.add(data["errorType"], T.T('update_successful'));
								 deferred.resolve(data);
								$scope.vm.selected = [];
							} else {
								$scope.search();
								alertService.add(data["errorType"],data["errorMessage"]);

								 	deferred.resolve(data);
									$scope.vm.selected = [];
									//deferred.reject();
							}

						}).

				error(function(data, status, headers, config) {
					deferred.reject();
				});

				$scope.promise = deferred.promise;
			};
			
			
			
			//============================== edit popup==============
			
			$scope.openView= function (brandCode) {

	         	$scope.brandCurrentData = null;
	            ngDialog.open({
	                	className: 'ngdialog-theme-default custom-width-900',
	                	 plain: false,
		                 showClose: true,
		                 closeByDocument: true,
		                 closeByEscape: true,
		                 appendTo: false,
	          		    template: 'views/pages/settingBrandDetail.html',
	          		    scope: $scope,
	          		    controller:['$scope', '$state', '$http', '$interval', 
					'alertService', 'localStorageService', '$stateParams','T','ngDialog','uiUploader','$q',
				function ($scope, $state, $http, $interval, alertService, localStorageService, $stateParams,T,ngDialog,uiUploader,$q) {
	   
	          		    	
	            $scope.brandCurrentData = null;
	            $scope.upload_button = true;
	        	$scope.delete_button = false;
	        	$scope.image_file1=false	;
	        	$scope.image_file2=false;
	        	
	          		    	
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
	                                 $scope.search();

	                              } else {
	                            	  
	                            	  //console.log(data["errorType"]);
	                                  $scope.closeAlert = alertService.closeAlert;
	                                  alertService.add(data["errorType"], data["errorMessage"]);
	                              }
	                          });
	          		    	
	          		    }
		    		
	          		    $scope.cancelReturn = function () {
	          		    	
	          		    	 $scope.closeThisDialog(0);
				             ngDialog.closePromise;
				             $scope.search();
	                    }
	          		    
	                }],
	      		    
	      		    preCloseCallback: function(value) {
	      		     
	      		      alertService.cleanAlert();	  
	      		      $scope.search();       		      
	      		    }
	            
	      		});
	      		
	        };
			

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
