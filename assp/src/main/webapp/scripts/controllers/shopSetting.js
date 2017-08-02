'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ShopSettingCtrl', ['$scope', '$state', '$http', '$interval','uiUploader', 'alertService','shopService','localStorageService','$stateParams','$q','T',
        function ($scope, $state, $http, $interval,uiUploader,alertService,shopService,localStorageService,$stateParams,$q,T) {
    
    $scope.showHomePage = true;
    $scope.showShopUser = false;
    $scope.shopId = localStorageService.get("shopId");	
    $scope.shopDescption = null;	
    $scope.shopInfo = null;	
	$scope.logoFile = null;	
	$scope.reader = new FileReader();   //创建一个FileReader接口
	$scope.form = {     //用于绑定提交内容，图片或其他数据
	    image:{},
	};
	$scope.thumb = {};      //用于存放图片的base64
	$scope.thumb_default = {    //用于循环默认的‘加号’添加图片的框
	    1111:{}
	};
	
	$scope.img_upload = function(files) {       //单次提交图片的函数

		if(files[0] != null && files[0].type != null && files[0].type.indexOf("image") > -1 ){
			alertService.cleanAlert();
			$scope.guid = 0;
		    //$scope.guid = (new Date()).valueOf();   //通过时间戳创建一个随机数，作为键名使用
			$scope.logoFile = files;
			$scope.disable=false;
		    $scope.reader.readAsDataURL(files[0]);  //FileReader的方法，把图片转成base64
		    $scope.reader.onload = function(ev) {
		        $scope.$apply(function(){
		            $scope.thumb[$scope.guid] = {
		                imgSrc : ev.target.result,  //接收base64
		            }
		        });
		    };
		} else {
			$scope.disable=true;
			var file = document.getElementById("one_input");   
			alertService.add("danger", T.T("select_picture"));
			$scope.$apply();
		}
	}
	
	$scope.upload = function() {
		uiUploader.addFiles($scope.logoFile);
        $scope.disable=true;
        var deferred  = $q.defer();
        uiUploader.startUpload({
            url: './shop/uploadLogo',
            concurrency: 2,
            data : {shopId : $scope.shopInfo.id, shopCode : $scope.shopInfo.shopCode},
            onProgress: function(file) {
                $scope.$apply();
            },
            onCompleted: function(file, response) {
            	 deferred.resolve(file);
            	var data = eval("(" + response + ")");
            	var errorType = data["errorType"];
				if(errorType != null || errorType != "") {
					alertService.cleanAlert();
					alertService.add(data["errorType"], data["errorMessage"]);
				}
				if(errorType == "success") {
					$scope.shopInfo.logoName = data["returnData"].logoName;
					console.log($scope.shopInfo.logoName);
				}
                uiUploader.removeAll();
                $scope.$apply();
            },
            onCompletedAll:function(file) {
                $scope.$apply();
            },
            onError:function(e) {
                $scope.$apply();
                deferred.reject();
            }
        });
        if(deferred!=null)
        	$scope.promise = deferred.promise;
    }
	
	
	
	$scope.init = function () {
		var shopId = localStorageService.get("shopId");
		if(shopId != undefined) {
			shopService.getShopInterfaceById(shopId).then(function(data){
				$scope.shopInfo =  data;
				$scope.thumb[0] = {
					imgSrc : $scope.shopInfo.logoPath
				}
			});
		}
	}
      /*
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
      */
	
	 $scope.save= function() {
		 var deferred = $q.defer();
		$scope.promise = $http.post('./shop/saveShop', $scope.shopInfo).success(function(data) {
			
			if (data['errorType'] == "success") {
				alertService.cleanAlert();
				alertService.add(data["errorType"], "update successful");
			} else if(data['errorType'] == "danger") {
				$scope.closeAlert = alertService.closeAlert;
				alertService.add(data["errorType"], data["errorMessage"]);
			}
		});
     };
     
	 $scope.cancel2 =  function() {
		 $state.go('main.system_eletter');
		 
	 }
	 
}]);
