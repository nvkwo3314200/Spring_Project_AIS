'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('LogViewCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService',
        function ($scope, $state, $http, $interval, alertService,localStorageService) {
            $scope.page = 10;
            $scope.formats = ['MM/dd/yyyy', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
            $scope.format = $scope.formats[0];
            $scope.altInputFormats = ['M!/d!/yyyy'];

            $scope.delay = 0;
      		$scope.minDuration = 0;
      		$scope.templateUrl = '';
      		$scope.message = 'Please Wait...';
      		$scope.backdrop = true;
      		$scope.promise = null;

            $scope.open1 = function () {
                $scope.popup1.opened = true;
            };
            $scope.open2 = function () {
                $scope.popup2.opened = true;
            };
            $scope.popup1 = {
                    opened: false
                };
                $scope.popup2 = {
                    opened: false
                };
                
                function disabled(data) {
                    var date = data.date,
                        mode = data.mode;
                    return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
                }

                $scope.dateOptions = {
                    dateDisabled: false,
                    formatYear: 'yy',
                    maxDate: new Date(2020, 5, 22),
                    //minDate: new Date(),
                    startingDay: 1
                };

                $scope.importTypes = [{
                    code: "",
                    description: ""
                },{
                    code: "LOG001",
                    description: "Product master export to Rete"
                }, {
                    code: "LOG002",
                    description: "Product barcode export to Retek"
                },
                {
                    code: "LOG003",
                    description: "Product image export to Retek"
                },
                {
                    code: "LOG004",
                    description: "Product master ack from Retek"
                },
                {
                    code: "LOG005",
                    description: "Product barcode ack from Retek"
                },
                {
                    code: "LOG006",
                    description: "Product image ack from Retek"
                }]
                
                var yugi = function(n){
                    var now = new Date;
                    now.setDate(now.getDate() - n);
                    return now;
                }
                
                var date2 = yugi(10);
                
                var importDateFrCode = toDDMMMYYYY(date2);
                var importDateToCode = toDDMMMYYYY(new Date());
                
                $scope.importDateFrStr = date2;
                $scope.importDateToStr =  new Date();
            
         $scope.promise =    $http({
                method: 'GET',
                params: {
                	importTypeArr:$scope.importTypeArr,
                	importDateFrStr:importDateFrCode,
                  	importDateToStr: importDateToCode
                    },
                url: "./product/productImportLogViewList"
            }).success(function(data) {
	        	// console.log(data);
	        	 $scope.vm.rowCollection = data;
	        	  $scope.vm.data = [].concat(data);
                 alertService.cleanAlert();
	        });
            
            //search button
            $scope.searchProductLog = function() {
            	  var importDateFrCode = toDDMMMYYYY($scope.importDateFrStr);
                  var importDateToCode = toDDMMMYYYY($scope.importDateToStr);



                  if ($scope.importDateFrStr != null && $scope.importDateFrStr != '' && $scope.importDateToStr != null && $scope.importDateToStr != '') {
                      if ($scope.importDateFrStr > $scope.importDateToStr) {

                          $scope.closeAlert = alertService.closeAlert;
                          alertService.add('danger', "From date can't be larger than to date");
                          return;
                      }
                  }

            	
            	
                  $scope.promise =    $http({
                	  method: 'GET',
                      params: {
                      	importTypeArr:$scope.importTypeArr,
                      	importDateFrStr:importDateFrCode,
                      	importDateToStr:importDateToCode
                          },
                      url: "./product/productImportLogViewList"
                }).success(function(data) {
                    // START ======================== AngularJS Smart-Table select all rows directive ===============
                    $scope.vm.rowCollection = data;
                    $scope.vm.data = [].concat(data);
                    // END ======================== AngularJS Smart-Table select all rows directive ===============
                    alertService.cleanAlert();

                });
            }

            function toDDMMMYYYY(date) {
                if (date == null || date == '')
                    return null;

                var mths = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
                var d = new Date(date.getTime());
                var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate().toString();
                var mmm = d.getMonth() + 1;
                var yyyy = d.getFullYear().toString(); //2011
                //var YY = YYYY.substr(2);   // 11

                return yyyy + "-" + mmm + "-" + dd;
            }
            
            $scope.downloadFile  = function(fileName,importType){
            	 $scope.promise =   $http({
                	  method: 'GET',
                      params: {
                    	  fileName:fileName,
                    	  importType:importType
                          },
                      url: "./product/checkFileExcit"
                }).success(function(data) {
                	 $scope.closeAlert = alertService.closeAlert; 
                   if(data){
                	   window.location.href="/pssp/downloadServletFile?fileName="+fileName+"&importType="+importType;
                   }else{
                	   alertService.add('danger', "file is not exist");
               		   return;
                   }
                  

                });
            	/*if(fileName != "" && importType !=""){
            	window.location.href="/pssp/downloadServletFile?fileName="+fileName+"&importType="+importType;
            	}else{
            		alert("file is not excit");
            		return;
            	}*/
            };
            
        }]);
