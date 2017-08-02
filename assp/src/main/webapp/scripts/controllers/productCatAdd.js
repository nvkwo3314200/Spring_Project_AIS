'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ProductCatAddCtrl', ['$scope', '$state', '$http', '$interval', 'alertService','localStorageService','$stateParams','$q','T',
        function ($scope, $state, $http, $interval, alertService,localStorageService,$stateParams,$q,T) {

	 $scope.cancel2 =  function() {
		 $state.go('main.shop_product_cat');
	 }
	 
	 $scope.save =  function() {
		 $state.go('main.shop_product_cat');
	 }
	 
	 $scope.init= function() {	
		/* init data  */
		 
	 }
	 
	 $scope.parentCate = "";
     $scope.cateOptions = [
    	 {
    		desc:'' 
    	 },{
    		 desc:'\u79fb\u52a8\u8bbe\u5907'
    	 },{
    		 desc:'\u624b\u673a'
    	 },{
    		 desc:'\u4e09\u661f'
    	 },{
    		 desc:'\u82f9\u679c'
    	 }
     ]
     
     $scope.data={
     		top1:'\u79fb\u52a8\u8bbe\u5907',
     		node1:'\u624b\u673a',
     		child1:'\u4e09\u661f',
     		child2:'\u82f9\u679c',
     		child3:'SONY',
     		child4:'HTC',
     		node2:'\u5e73\u677f',
     		child5:'\u4e09\u661f',
     		child6:'\u82f9\u679c',
     		child7:'\u5fae\u8f6f',
     		child8:'\u534e\u4e3a',
     		top2:'\u5bb6\u7535',
     		node21:'\u7535\u89c6',
     		child211:'Skyworth',
     		child212:'Hisense',
     		child213:'Philips',
     		child214:'Tcl',
     		node22:'\u51b0\u7bb1',
     		child221:'\u897f\u95e8\u5b50',
     		child222:'\u6d77\u5c14',
     		child223:'\u4e09\u661f',
     		child224:'\u7f8e\u7684',
     		top3:'\u670d\u9970',
     		node31:'\u7537\u88c5',
     		child311:'\u5916\u5957',
     		child312:'\u886c\u886b',
     		child313:'\u5939\u514b',
     		child314:'\u76ae\u8863',
     		node32:'\u5973\u88c5',
     		child321:'\u521d\u6625\u65b0\u54c1',
     		child322:'\u8fde\u8863\u88d9 ',
     		child323:'\u88e4\u5b50',
     		child324:'\u6bdb\u5462\u5916\u5957',
     		top4:'\u978b\u9774 / \u7bb1\u5305 ',
     		node41:'\u978b\u9774',
     		child411:'\u65b0\u6b3e\u77ed\u9774',
     		child412:'\u96ea\u5730\u9774',
     		child413:'\u4e2d\u957f\u9774',
     		child414:'\u7c97\u8ddf\u5355\u978b',
     		node42:'\u7bb1\u5305',
     		child421:'\u79cb\u51ac\u65b0\u54c1',
     		child422:'\u5973\u5305',
     		child423:'\u7537\u5305',
     		child424:'\u53cc\u80a9\u5305',
     		
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
