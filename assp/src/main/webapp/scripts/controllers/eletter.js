'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')

    .controller('EletterCtrl', ['$scope', '$state', '$http',
                                 '$interval',  'alertService', 'localStorageService', '$window', '$confirm','T','$q',
                                 function ($scope, $state, $http, $interval,  alertService, localStorageService, $window, $confirm,T,$q) {
		 	$scope.vm = this;
    	 	$scope.delay = 0;
			$scope.minDuration = 0;
			$scope.templateUrl = '';
			$scope.message = 'Please Wait...';
			$scope.backdrop = true;
			$scope.promise = null;
			
			
    	 /**
    	  * 分页与查询操作 
    	  */
    	 $scope.page = 10;
         // Declare the array for the selected items
         $scope.vm.selected = [];

         // Function to get data for all selected items
         $scope.vm.selectAll = function (collection) {

             // if there are no items in the 'selected' array,
             // push all elements to 'selected'
             if ($scope.vm.selected.length === 0) {

                 angular.forEach(collection, function (val) {

                     $scope.vm.selected.push(val);

                 });

                 // if there are items in the 'selected' array,
                 // add only those that ar not
             } else if ($scope.vm.selected.length > 0 && $scope.vm.selected.length != $scope.vm.data.length) {

                 angular.forEach(collection, function (val) {

                     var found = $scope.vm.selected.indexOf(val);

                     if (found == -1) $scope.vm.selected.push(val);

                 });

                 // Otherwise, remove all items
             } else {

                 $scope.vm.selected = [];

             }

         };
         // Function to get data by selecting a single row
         $scope.vm.select = function (id) {

             var found = $scope.vm.selected.indexOf(id);

             if (found == -1) $scope.vm.selected.push(id);

             else $scope.vm.selected.splice(found, 1);
             
             //=====Sheldon=====
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
         }
         
    	
           $scope.vm.rowCollection = [];
           $scope.vm.data = [];
               
            $scope.userName = "";
            $scope.userRole = "";
           // $scope.userVo.activate = "Y";
            $scope.activate = "Y";
            
            $scope.eletterVo = {};
            $scope.eletterVo.shopId = "";
            $scope.eletterVo.desc = "";
            $scope.eletterVo.subject = "";
            $scope.eletterVo.content = "";
            $scope.eletterVo.file = "";
            $scope.eletterVo.publishInd = "";
            $scope.eletterVo.pubExpDat = "";
            $scope.eletterVo.pubActDat = "";
            $scope.eletterVo.actInd = "";
            $scope.eletterVo.inActDat = "";
            
            var eletterList = [6];
            for (var i = 0 ; i < 6 ; i++) {
            	var object = {id :i , shopId : "shop_"+(i+1) ,shopName : "haha" ,   principal :"Mr. Lu" , contact : "Mr. Lu", contactEmail : "123456@11.com", websiteName : "www.111.com", shopPhone : "123456"}
            	eletterList[i] = object;
            }
            $scope.vm.rowCollection = eletterList;
            $scope.vm.data = [].concat(eletterList);
            
            //add user
            
            $scope.addEletter = function(){
                $state.go('main.system_eletter_add');
            }
            
           
        	//edit user
        	
        	$scope.editEletter = function(){
        		//console.log($scope.vm.selected.length);
        		 if($scope.vm.selected.length != 1){
	        		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
	             }
        		 
	         var userId=$scope.vm.selected[0]["id"];
	         $state.go('main.system_eletter_add',{'userId': userId});
        	}
        	
        	
        	// delete user
        	$scope.deleteEletter = function(){
        		 if( $scope.vm.selected.length < 1){
            		 $scope.closeAlert = alertService.closeAlert;
	        		 alertService.add('danger', T.T('select_records'));
	    			 return;
                 }
	    		  var userIds = "";
	             for (var i = 0; i < $scope.vm.selected.length; i++) {
	            	 userIds = userIds+$scope.vm.selected[i]["id"] + ","; 	
	              }
	             
	             var popupMsg = T.T('confirm_to_delete_selected_value');
	          

	                $confirm({text: popupMsg, title: T.T('button_delete'), ok: T.T('Yes'), cancel: T.T('No')})
	                    .then(function () {
	                    	/* delete code */
   	          });
        	}
        	
       
            $scope.searchShop = function(message) {
                
            }

            /** data control **/
            $scope.formats = [ 'yyyyMMdd','MM/dd/yyyy', 'dd-MMMM-yyyy','yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate' ];
			$scope.format = $scope.formats[0];
			$scope.altInputFormats = [ 'M!/d!/yyyy' ];

			$scope.open1 = function() {
				$scope.popup1.opened = true;
			};
			$scope.open2 = function() {
				$scope.popup2.opened = true;
			};
			$scope.open3 = function() {
				$scope.popup3.opened = true;
			};

			$scope.open4 = function() {
				$scope.popup4.opened = true;
			};

			$scope.open5 = function() {
				$scope.popup5.opened = true;
			};

			$scope.open6 = function() {
				$scope.popup6.opened = true;
			};

			$scope.popup1 = {
				opened : false
			};
			$scope.popup2 = {
				opened : false
			};
			$scope.popup3 = {
				opened : false
			};
			$scope.popup4 = {
				opened : false
			};
			$scope.popup5 = {
				opened : false
			};
			$scope.popup6 = {
				opened : false
			};

			function disabled(data) {
				var date = data.date, mode = data.mode;
				return mode === 'day'
						&& (date.getDay() === 0 || date
								.getDay() === 6);
			}

			$scope.dateOptions = {
				dateDisabled : false,
				formatYear : 'yy',
				maxDate : new Date(2020, 5, 22),
				// minDate: new Date(),
				startingDay : 1
			};
            
            

        }]);
        
