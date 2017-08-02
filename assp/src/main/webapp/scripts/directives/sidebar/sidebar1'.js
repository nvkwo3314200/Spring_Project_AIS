'use strict';

/**
 * @ngdoc directive
 * @name psspAdminApp.directive:sidebar
 * @description # sidebar
 *
 *
 * userRole:supplier ,approver,systemAdmin,
 */

angular.module('psspAdminApp').directive('sidebar', [ '$location', function() {
	return {
        templateUrl: 'scripts/directives/sidebar/sidebar.html',
        restrict: 'E',
        replace: true,
            controller:function($scope,localStorageService){
            	$.AdminLTE.layout.activate();

                var userRoes= localStorageService.get("userRole");
                var userName= localStorageService.get("userName");
               // var switchOff= localStorageService.get("switchOff");
              
               
                var switchOff= 'N';
                
                $scope.su = false;
                
               // console.log('userName:'+userName);
                
                $scope.userName = userName;
                
                var systemAdminFlag = userRoes!=null&&  userRoes.length > 0 && userRoes.indexOf("SYSTEMADMIN") !== -1;
                systemAdminFlag = true;
                
                //Product Management
                $scope.productView = true;
                $scope.productAddEdit = false;
                $scope.productBatchUpload = false;
                $scope.productImportLog = false;
                
                //System Management
                $scope.systemManagement = false;
                $scope.systemUserAccount = false;
                $scope.systemListValue = false;
                $scope.systemDept = false;
                $scope.systemBrand= false;
                $scope.systemCategory= false;
                $scope.systemLov= false;
                $scope.systemConfiguration= false;

                
             
                
                //My Account
                $scope.myAccount= false;
                $scope.myAccountSettings = false;
                if (systemAdminFlag) {
                    $scope.productImportLog = true;
                    $scope.systemManagement = true;
                    $scope.systemUserAccount = true;
                    $scope.systemListValue = true;
                    $scope.systemDept = true;
                    $scope.systemBrand= true;
                    $scope.systemCategory= true;
                    $scope.systemLov= true;
                    $scope.systemConfiguration= true;
                }

                var approverFlag = userRoes!=null&& userRoes.length > 0 && userRoes.indexOf("APPROVER") !== -1;
                if (approverFlag) {
                	$scope.productImportLog = true;
                    $scope.productAddEdit = true;
                    $scope.productBatchUpload = true;

                } 

                var supplierFlag = userRoes!=null&& userRoes.length > 0 && userRoes.indexOf("SUPPLIER") !== -1;
                if (supplierFlag) {
                	$scope.productAddEdit = true;
                	$scope.productBatchUpload = true;
                    
                    $scope.myAccountSettings = true;
                    
                    $scope.su = true;
                }

        }


    }
} ]);
