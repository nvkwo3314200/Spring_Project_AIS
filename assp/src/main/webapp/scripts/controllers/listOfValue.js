'use     strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('ListOfValueCtrl',function ($scope,$state, $http, localStorageService,alertService,uiUploader,$stateParams,$window,ngDialog,$confirm,T) {
    	$scope.ableAdd=false;  
    	$scope.showCategory=false;
    	
    	
    	$scope.delay = 0;
		$scope.minDuration = 0;
		$scope.templateUrl = '';
		$scope.message = 'Please Wait...';
		$scope.backdrop = true;
		$scope.promise = null;
		
        $http.get('./listOfValue/getEstoreCategory').
 		success(function (data) {
 			$scope.estoreCategory=data;
 			});
    	
    	function boolShow(lovId){
    		if($scope.estoreCategory==lovId){
    			$scope.showCategory=true;
    		}else{
    			$scope.showCategory=false;
    		}
    	}
        //Options for sortable code
        $scope.sortable_option = {
          //Only allow draggable when click on handle element
          handle:'td.nothandle',
          //Construct method before sortable code
          construct:function(model){
            for ( var i = 0; i < model.length; i++ ){
//              model[i].letter +=" (constructed)";
            }
          },
          //Callback after item is dropped
          stop:function(data, dropped_index){
//            list[ dropped_index].letter += " Dropped";
          }
        };
        
        $scope.supplierSelected = null;
        //search button
        $scope.getValueList = function () {
                        $scope.closeAlert = alertService.closeAlert;
        	if($scope.lovId!=null&&$scope.lovId!=''){
        		
        		
        		$scope.promise =   $http({
                       method: 'GET',
                       params: {
                      	 lovId: $scope.lovId
                       },
                       url: "./listOfValue/getLovList"
         	          }).
            		success(function (data) {
            			
            		$scope.showTable=true;
                    $scope.vm.rowCollection = data;
                    $scope.vm.data = [].concat(data);
                    $scope.supplierSelected = null;
                    $scope.vm.selected = [];
                    boolShow($scope.lovId);
                    

                });
            
        	}{
        		$scope.showTable=false;
        	}

        };
        
        $scope.updateValue=function (){
        	  alertService.cleanAlert();
        var obj={lovList:$scope.vm.data,lovId:$scope.lovId};
	         $http.post('./listOfValue/updateLov', obj).
		success(function (data) {
	          if (data['errorType'] == "success") {
            
        $scope.vm.rowCollection = data.returnDataList;
        $scope.vm.data = [].concat(data.returnDataList);
        $scope.vm.selected = [];
                    $scope.closeAlert = alertService.closeAlert;
        alertService.add(data["errorType"],data["errorMessage"]);
	          }else{
	        	  
	                          $scope.closeAlert = alertService.closeAlert;
                  alertService.add(data["errorType"],data["errorMessage"]);
                  var inputArray=$("td input[type='text']");
                  for(var i=0;i<inputArray.length;i++){
               		if($.trim($(inputArray[i]).val())==''){
               			$(inputArray[i]).addClass("input-invalid");
               		}else{
               			$(inputArray[i]).removeClass("input-invalid");
               		}
                  }
	          }
        

    });
        	
        }
        
    	$scope.openDelete=function (){
    		  alertService.cleanAlert();
            var checkSelected = [];
             if ($scope.vm.selected.length <= 0) {
                 $scope.closeAlert = alertService.closeAlert;
                 alertService.add('danger', T.T('select_records'));
                 return;
             }
             
             for (var i = 0; i < $scope.vm.selected.length; i++) {
                 var model = $scope.vm.selected[i];
                     checkSelected.push(model);
             }
             
//             console.log($scope.vm.selected.length);
             $confirm({text: T.T('confirm_to_delete_selected_value'), title: T.T('title_delete_list_of_value'), ok: 'Yes', cancel: 'No'})
             .then(function () {



                 var data = {'lovList' : checkSelected,'lovId':$scope.lovId};

                 $http.post('./listOfValue/batchDeleteLov', data).
                 success(function (data) {

                     if (data['errorType'] == "success") {
                         $scope.vm.rowCollection = data.returnDataList;
                         $scope.vm.data = [].concat(data.returnDataList);
                         $scope.vm.selected = [];
                         alertService.add(data["errorType"], data["errorMessage"]);
                     } else {
                         $scope.closeAlert = alertService.closeAlert;
                         alertService.add(data["errorType"], data["errorMessage"]);
                     }

                 });

             });
             
             
    		
    	}
    	$scope.openAdd=function (){
    		  $scope.ableAdd=false;  
    		  alertService.cleanAlert();
    		ngDialog.open({
    			
    			 className: 'ngdialog-theme-default',
                 plain: false,
                 showClose: true,
                 closeByDocument: true,
                 closeByEscape: true,
                 appendTo: false,
    		    template: 'views/pages/valueAdd.html',
    		    scope: $scope,
    		    controller: 'ListOfValueCtrl',
    		    preCloseCallback: function(value) {
    		    	  alertService.cleanAlert();
    	     		   $http({
    	                    method: 'POST',
    	                    params: {
    	                   	 lovId: $scope.lovId
    	                    },
    	                    url: "./listOfValue/getLovList"
    	      	          }).
    	         		success(function (data) {
    	         			$scope.showTable=true;
    	                 $scope.vm.rowCollection = data;
    	                 $scope.vm.data = [].concat(data);
    	                 $scope.supplierSelected = null;
    	                 $scope.vm.selected = [];
    	                 boolShow($scope.lovId);

    	             });
    	     		   
    		    }
    		});
   
    		
    	}
    	
    	$scope.addValue=function (){
    		  alertService.cleanAlert();
    		  $scope.ableAdd=true;  
            var obj={lovValue:$scope.lovValue,lovDesc:$scope.lovDesc,lovId:$scope.lovId};
 	         $http.post('./listOfValue/addValue', obj).
     		success(function (data) {
     	          if (data['errorType'] == "success") {
           
             alertService.add(data["errorType"],data["errorMessage"]);
             $scope.closeThisDialog(0);
             ngDialog.closePromise;
           
     	          }else{
     	       	  $scope.ableAdd=false;  
                       alertService.add(data["errorType"],data["errorMessage"]);
                 
     	          }
             

         });
 	         
    
    	}

     	
        $http.get('./listOfValue/getValuesList').
 		success(function (data) {
 			$scope.valueList=data;
 			});
      
     	$scope.showTable=false;
     	

// START ======================== AngularJS Smart-Table select all rows directive ===============
            $scope.vm = this;
            //  $window.open('http://www.c-sharpcorner.com/1/302/angularjs.aspx', 'C-Sharpcorner', 'width=500,height=400');
            // Declare the array for the selected items
            $scope.vm.selected = [];
            $scope.vm.data=[];
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

            }


            $scope.vm.rowCollection = [];

            //for (id; id < 11; id++) {
            //    $scope.vm.rowCollection.push(generateRandomItem(id));
            //}

            $scope.vm.data = [];// [].concat($scope.vm.rowCollection);
            //END =====================================AngularJS Smart-Table select all rows directive =====================================


            //$scope.$watch('vm.data', function(row) {
            //    // get selected row
            //    row.filter(function(r) {
            //        if (r.isSelected) {
            //            //console.log(r);
            //        }
            //    })
            //}, true);

            //  $scope.rowCollection = [];


        
      

     


        })
    .directive('stRatio', function () {
        return {
            link: function (scope, element, attr) {
                var ratio = +(attr.stRatio);

                element.css('width', ratio + '%');

            }
        };
    });
