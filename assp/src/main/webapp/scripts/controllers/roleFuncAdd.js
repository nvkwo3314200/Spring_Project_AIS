'use strict';

angular.module('psspAdminApp')
    .controller('RoleFuncAddCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$stateParams','$q','T',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$stateParams,$q,T) {
     
	 $scope.cancel2 =  function() {
		 $state.go('main.role_function');
	 }
	 
	 $scope.save =  function() {
		 $state.go('main.role_function');
	 }
	 
	 $scope.init= function() {	
		/* init data  */
		 
	 }
	 
	 $scope.state = false;
	 $scope.rowCollection = [];
	 $scope.page = 10;
	 
	 $scope.userroles = [{
         code: "SYSTEMADMIN",
         description: 'SYSTEMADMIN',
         descriptionTw: '\u5546\u5bb6',
     }, {
         code: "APPROVER",
         description: 'APPROVER',
         descriptionTw: '\u5e97\u5bb6-APPROVER',
     },
     {
         code: "SUPPLIER",
         description: 'SUPPLIER',
         descriptionTw: '\u5e97\u5bb6',
     }]
	 
	 $scope.roleVoList = [
		 {
	         code: "2",
	         funcNameTW: T.T('product_management'),
	         active : 'Y',
	         type : "menu"
	     }, {
	         code: "2",
	         funcNameTW: T.T('import_log'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "3",
	         funcNameTW: T.T('add_edit_product'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "4",
	         funcNameTW: T.T('batch_upload'),
	         active : 'Y',
	         type : "func"
	     }, {
	    	 code: "5",
	         funcNameTW: T.T('order_management'),
	         active : 'Y',
	         type : "menu"
	     }, {
	         code: "5",
	         funcNameTW: T.T('view_products'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "7",
	         funcNameTW: T.T('batch_upload'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "8",
	         funcNameTW: T.T('order_view'),
	         active : 'Y',
	         type : "func"
	     }, {
	    	 code: '1',
	         funcNameTW: T.T('system_management'),
	         active : 'Y',
	         type : "menu"
	     }, {
	    	 code: '1',
	         funcNameTW: T.T('user_account'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "2",
	         funcNameTW: T.T('role_function'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "3",
	         funcNameTW: T.T('list_of_value'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "4",
	         funcNameTW: T.T('General_Configuration'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "5",
	         funcNameTW: T.T('system_shop'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "7",
	         funcNameTW: T.T('system_eLetter'),
	         active : 'Y',
	         type : "func"
	     }, {
	    	 code: "7",
	         funcNameTW: T.T('my_shop'),
	         active : 'Y',
	         type : "menu"
	     }, {
	         code: "7",
	         funcNameTW: T.T('my_shop_setting'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "8",
	         funcNameTW: T.T('my_shop_product_cat'),
	         active : 'Y',
	         type : "func"
	     }, {
	         code: "7",
	         funcNameTW: T.T('my_shop_discount'),
	         active : 'Y',
	         type : "func"
	 }]
	 
	 $scope.rowCollection =$scope.roleVoList;
	 $scope.data=[].concat($scope.roleVoList);
	 
	 $scope.allCheck = "Y";
	 
	 $scope.checkAllActive = function () {
		 for(var i=0;i<$scope.roleVoList.length;i++) {
			 $scope.roleVoList[i].active = $scope.allCheck;
		 }
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
         var re = new RegExp("^[+]?([0-9]+(.[0-9]{1})?)$");
         var result = re.test(text);
         if (!result) {
             flag = false;
         }
         return flag;
     }
	 
}]);
