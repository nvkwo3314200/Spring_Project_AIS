'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('psspAdminApp')
  .controller('ProductBatchUploadCtrl',function($scope, $state, $http, localStorageService,
			alertService, uiUploader, $stateParams, $window,$q,T) {
        $scope.showMsg = false;
        $scope.disable=true;
       
        
        $scope.delay = 0;
  		$scope.minDuration = 0;
  		$scope.templateUrl = '';
  		$scope.message = 'Please Wait...';
  		$scope.backdrop = true;
  		$scope.promise = null;
  		
        $scope.upload = function() {
            $scope.disable=true;
            var deferred  = $q.defer();;
            uiUploader.startUpload({
                url: './productBatchUpload/upload',
                concurrency: 2,
                onProgress: function(file) {
                	
                    $scope.$apply();
                    
                },
                onCompleted: function(file, response) {
                	 deferred.resolve(file);
                	
                	var data = eval("(" + response + ")");
                		
  	
                	var err_info="";
                	//var j=2;
                	var type="success";
                    for(var i=0;i<data.length;i++){
                    	var obj=data[i];
                    	
                    	if(obj["hook"]!=null)
                    		err_info+="Row  "+obj["hook"]+" : "+obj["errorMessage"]+"<br>";
                    	else
                    		err_info+= obj["errorMessage"]+"<br>";
                    	
                        if(obj["errorType"]=="danger"){
                        	type="danger";
                        }
                       // j++
                    }
                	alertService.cleanAlert();
                	$scope.closeAlert = alertService.closeAlert;
					alertService.add(type,err_info);
                    $scope.showMsg = true;
                    $scope.disable=false;
                    uiUploader.removeAll();
                    var element = document.getElementById('file1');
                    element.value = "";
                    $scope.$apply();
                },
                onCompletedAll:function(file) {
                    //$scope.disable=false;
                    $scope.$apply();
                   // deferred.resolve(file);
                   
                   
                },
                onError:function(e) {
                   // $log.info('error occurs');
                    $scope.disable=false;
                    $scope.$apply();
                    deferred.reject();
                   
                }
                
                
            });
            if(deferred!=null)
            	$scope.promise = deferred.promise;
        }
        
        // download template file
        $scope.downloadProductTemplate = function(){      
        	$http.get('./product/downloadProductTemplate').
            success(function (data) {
            	var byteCharacters = atob(data.fileContent);
            	
            	var byteNumbers = new Array(byteCharacters.length);
            	for (var i = 0; i < byteCharacters.length; i++) {
            	    byteNumbers[i] = byteCharacters.charCodeAt(i);
            	}
            	
            	var byteArray = new Uint8Array(byteNumbers);
//            	console.log(byteArray);
            	var blob = new Blob([byteArray], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                });

            	saveAs(blob, "product_upload_template_"+new Date().getTime()+".xlsx");
//            	var objectUrl = URL.createObjectURL(blob);
//                window.open(objectUrl);
            });
        }

        //$scope.files = [];
        var element = document.getElementById('file1');
        element.addEventListener('change', function(e) {
            var files = e.target.files;
            uiUploader.addFiles(files);
            $scope.disable=false;
            $scope.showMsg = false;
            $scope.$apply();
        });
  });