'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp')
    .controller('DeptCtrl',['$scope', 
                            '$state', 
                            '$http',
						    'localStorageService',
						'alertService',
						'$stateParams',
						'ngDialog',
						'$window',
						'$confirm',
						"IntegralUITreeGridService",
						"$timeout",
						"T",
						function($scope, $state, $http, localStorageService,
								alertService,  $stateParams,ngDialog,
								$window,$confirm,$gridService,$timeout,T) {
    	
    	//init
    	$scope.gridName = "gridSample";
		$scope.editCell = null;
		$scope.rows=[];
		$scope.selectCellNum=null;
		$scope.selectCategory=null;
		$scope.selectedRow=null;
		var suppressChangeValueCallback = false;
		var suppressSelectCallback = false;
		
		$scope.delay = 0;
 		$scope.minDuration = 0;
 		$scope.templateUrl = '';
 		$scope.message = 'Please Wait...';
 		$scope.backdrop = true;
 		$scope.promise = null;
		
		var imageChecked = 'dist/tree/resources/checkbox/checkbox-checked-7.png';
		var imageUnchecked = 'dist/tree/resources/checkbox/checkbox-unchecked-7.png';
		
		
		$scope.onAfterCollapse = function(e){
			updateSelection();
		}
		
		$scope.onAfterExpand = function(e){
			updateSelection();
		}
		

		// A method that handles the checkbox value update 
		var updateSelection = function(){
			
			// Get a linear list of all rows and clear their checkbox status
			var fullList =  $gridService.getFlatList($scope.gridName, true);
			for (var i = 0; i < fullList.length; i++)
				fullList[i].cells[0].value = imageUnchecked;

			// Get a list of all selected rows and mark their checkboxes to true
//			var selList = $gridService.selectedRows($scope.gridName);
//			for (var i = 0; i < selList.length; i++)
//				selList[i].cells[0].value = imageChecked;
			
			$scope.$apply();
		}



		// Context menu data for Rows
//		$scope.rowContextMenu = [
//			{ 
//				key: 'REMOVE_ROW', 
//				text: "Remove Row", 
//				itemClick: function(e){
//					var list = $gridService.selectedRows($scope.gridName);
//					$gridService.suspendLayout($scope.gridName);
//
//					for (var i = 0; i < list.length; i++)
//						$gridService.removeRow($scope.gridName, list[i]);
//
//					$gridService.resumeLayout($scope.gridName);
//				}
//			} 
//		]

//		$timeout(function(){
//			var fullList =  $gridService.getFlatList($scope.gridName, true);
//			for (var i = 0; i < fullList.length; i++)
//				fullList[i].contextMenu = $scope.rowContextMenu;
//
//			$gridService.updateLayout($scope.gridName);
//		}, 1);
		
		$scope.columns = [
		          		{ 
							id: 9,
						 	editorType: 'image',
						 	editorSettings: {
						 		allowSelection: false
						 	},
						 	headerAlignment: "center", contentAlignment: "center",
						 	width: 30,
						 	fixedWidth: true
						},
	
			{ id: 1, headerText: T.T('label_department'), headerAlignment: "center", contentAlignment: "left", width: 160 },
			{ 
				id: 2, 
				headerText: T.T('label_id'),
				editorType: 'incell',
				headerAlignment: "center", contentAlignment: "left",
				editorTemplate: "'celleditor.html'",
				width: 250
			},
			{ 
				id: 3, 
				headerText: T.T('label_description'),
				editorType: 'incell',
				headerAlignment: "center", contentAlignment: "left",
				editorTemplate: "'celleditor.html'",
				width: 250
			},
			{ id: 4, headerText: T.T('estore_category'), headerAlignment: "center", contentAlignment: "left", width: 250 ,cellTemplateUrl: "'cell-button.html'"}
		];

		
		var initTimer = $timeout(function(){
			$timeout.cancel(initTimer);
		}, 1);

		$scope.editorFocus = { active: false }
		$scope.editBox = {
			text: ''
		}

		$scope.onBeforeEdit = function(e){
        	 $scope.closeAlert = alertService.closeAlert;
			if (e.cell){
				$scope.editCell = e.cell;
				$scope.editBox.text = e.cell.text;
				$scope.editorFocus.active = true;
			}
		}

		$scope.onAfterEdit = function(e){
			$scope.editCell = null;
			$scope.editorFocus.active = false;
		}

		$scope.onOk = function(cell){
			
			if(cell.cid==2){
				var idVal=$scope.editBox.text;
				
				if (isNaN(idVal)) { 
					alertService
					.add("danger",T.T('ID must be number.'));
					return ;
				}
				
				
				if (idVal!=null&&idVal.toString().length>4) { 
					alertService
					.add("danger",T.T('id_can_only_four_digits'));
					return ;
				}
			}
			confirmEdit(cell);
			updateData(cell.cid,cell.rid,cell.text)
			
		}
		
		var okcommit=function(cell){
			
			if(cell.cid==2){
				var idVal=$scope.editBox.text;
				
				if (isNaN(idVal)) { 
					alertService
					.add("danger",T.T('ID must be number.'));
					return ;
				}
				
				
				if (idVal!=null&&idVal.toString().length>4) { 
					alertService
					.add("danger",T.T('id_can_only_four_digits'));
					return ;
				}
			}
			confirmEdit(cell);
			updateData(cell.cid,cell.rid,cell.text)
			
		}


		$scope.onCancel = function(cell){
			cancelEdit(cell);
			 $scope.closeAlert = alertService.closeAlert;
		}


		var confirmEdit = function(cell){
			if (cell){
			
				cell.text = $scope.editBox.text;
				var selectRow=$gridService.selectedRow($scope.gridName);
				if(cell.cid==3){
				selectRow.cells[1].text=$scope.editBox.text;
				}
		}

			$gridService.closeEditor($scope.gridName, $scope.editCell);
		}
		


		var cancelEdit = function(){
			$gridService.closeEditor($scope.gridName, $scope.editCell);
		}

		$scope.onEditKeyDown = function(e,cell){
			switch (e.keyCode){
				case 13: // ENTER
					$gridService.focus($scope.gridName, $scope.modifyDescription);
					okcommit(cell);
					break;
					
				case 27: // ESCAPE
					cancelEdit($scope.modifyDescription);
					break;
			}

			e.stopPropagation();
		}
		
		 $scope.gridEvents = {
					afterCollapse: function(e){
						return $scope.onAfterCollapse(e);
					},
					afterExpand: function(e){
						return $scope.onAfterExpand(e);
					},
					afterBefore: function(e){
						return $scope.onBeforeSelect(e);
					},
					cellClick: function(e){
						return $scope.onCellClick(e);
					},
					rowClick: function(e){
						return $scope.onRowClick(e);
					}
			    }
		 
		//init end


		 
		$scope.onCellClick=function(e){
			 if(e.cell==null){
				 $scope.selectCellNum=null;
			 }
				
			if(e.cell)
				{	
				var cid=e.cell.cid;
				$scope.selectCellNum=cid;
				$scope.selectCategory=e.cell.eid;
				$scope.estoreCategoryId=e.cell.eid;
				}

			}
		 
			$scope.onRowClick = function(e){
				if (e.row){
					$scope.selectedRow=e.row;
					var list = $gridService.selectedRows($scope.gridName);
				
					e.row.cells[0].value = e.row.cells[0].value == imageChecked ? imageUnchecked : imageChecked;
					var checkImga=e.row.cells[0].value;
					
					$scope.$apply();
					updateSelection();
					e.row.cells[0].value=checkImga;
					var newList = [];
					newList.push(e.row);
					$gridService.selectedRows($scope.gridName, newList);
					$gridService.refresh($scope.gridName);
					
					var rowObj=e.row;
					var sid=rowObj.id;
					var flag=sid.substring(0,1);
					$scope.subclassId=sid.substring(1,sid.length);
					$scope.selectId=sid;
					$scope.parentId=rowObj.hid;
				if(flag=='S'){
					$scope.searchDeptId=e.row.cells[1].searchDeptId;
					$scope.searchClassId=e.row.cells[1].searchClassId;
					if($scope.selectCellNum==null||$scope.selectCellNum==4){
					openCategoryAdd();
					}
				}
				if(flag=='D'){
					$scope.searchDeptId=e.row.cells[1].searchDeptId;
				}
				
				if(flag=='C'){
					$scope.searchDeptId=e.row.cells[1].searchDeptId;
					$scope.searchClassId=e.row.cells[1].searchClassId;
					
				}
				$scope.$apply();
			}
			}
			
			function getSelectRow(){
				
//				var selectRow=$gridService.selectedRow($scope.gridName);
			}
			
			//delete 
			$scope.openDelete=function (){
				getSelectRow();
				var selList = $gridService.selectedRows($scope.gridName);
				if((selList.length==1&&selList[0].cells[0].value!=imageUnchecked)){
				var sid=$scope.selectId;
				var flag=sid.substring(0,1);
			
				$scope.subclassId=sid.substring(1,sid.length);
				if(flag=="D"){
					deptDelete();
					return;
				}
				if(flag=="C"){
					classDelete();
					return;
				}
				if(flag=="S"){
					subClassDelete();
					return;
				}
				}else{
	            	 $scope.closeAlert = alertService.closeAlert;
	                 alertService.add("danger", T.T('please_select_one_record'));
				}
				
			}
			
			//dept delete 
	    	function deptDelete(){
                $scope.closeAlert = alertService.closeAlert;
    $confirm({text: T.T('confirmed_delete_dept'), title: T.T('department_manager'), ok: 'Yes', cancel: 'No'})
    .then(function () {
		$http({
			method : 'POST',
			params : {
				id:$scope.subclassId
			},
			url : "./dept/deptDelete"
		})
        .success(function (data) {

            if (data['errorType'] == "success") {
            	 $scope.closeAlert = alertService.closeAlert;
                alertService.add(data["errorType"], data["errorMessage"]);
//                refereDeptTree(data.returnDataList);
        	 	$gridService.removeRow($scope.gridName, $scope.selectedRow);
				$gridService.resumeLayout($scope.gridName);
            } else {
                $scope.closeAlert = alertService.closeAlert;
                alertService.add(data["errorType"], data["errorMessage"]);
            }

        });

    });
}
	    	
	function classDelete(){
    $scope.closeAlert = alertService.closeAlert;
    $confirm({text: T.T('confirmed_delete_class'), title: T.T('class_manager'), ok: 'Yes', cancel: 'No'})
    .then(function () {

		$http({
			method : 'POST',
			params : {
				id:$scope.subclassId
			},
			url : "./dept/classDelete"
		})
        .success(function (data) {

            if (data['errorType'] == "success") {
            	 $scope.closeAlert = alertService.closeAlert;
                alertService.add(data["errorType"], data["errorMessage"]);
//                refereDeptTree(data.returnDataList);
        	 	$gridService.removeRow($scope.gridName, $scope.selectedRow);
				$gridService.resumeLayout($scope.gridName);
           } else {
                $scope.closeAlert = alertService.closeAlert;
                alertService.add(data["errorType"], data["errorMessage"]);
            }

        });

    });
}
	    	
	    	function subClassDelete(){
	    		$scope.closeAlert = alertService.closeAlert;
	    		$confirm({text: T.T('are_you_sure_to_delete'), title: T.T('sub_class_manager'), ok: 'Yes', cancel: 'No'})
	    		.then(function () {
	    			$http({
	    				method : 'POST',
	    				params : {
	    					id:$scope.subclassId
	    				},
	    				url : "./dept/subClassDelete"
	    			})
	    			.success(function (data) {
	    				
	    				if (data['errorType'] == "success") {
	    					$scope.closeAlert = alertService.closeAlert;
	    					alertService.add(data["errorType"], data["errorMessage"]);
//	    					  refereDeptTree(data.returnDataList);
	    	        	 	$gridService.removeRow($scope.gridName, $scope.selectedRow);
	    					$gridService.resumeLayout($scope.gridName);
	    				} else {
	    					$scope.closeAlert = alertService.closeAlert;
	    					alertService.add(data["errorType"], data["errorMessage"]);
	    				}
	    				
	    			});
	    			
	    		});
	    	}
			
			
			
			
			//add dept class subclass
			$scope.openAdd=function (){
				var selList = $gridService.selectedRows($scope.gridName);
				if(selList.length==0||(selList.length==1&&selList[0].cells[0].value==imageUnchecked)){
					openDeptAdd();
					return;
				}else{
				var sid=$scope.selectId;
				var flag=sid.substring(0,1);
				$scope.subclassId=sid.substring(1,sid.length);
				if(flag=="D"){
					openClassAdd();
					return;
				}
				if(flag=="C"){
					openSubClassAdd();
					return;
				}
				if(flag=="S"){
					openSubClassAdd();
					return;
				}
				
				}
				
			}
			
	    	function openDeptAdd(){
	    		//get data
	    		
	    		  alertService.cleanAlert();
	    		ngDialog.open({
	    			 className: 'ngdialog-theme-default',
	                 plain: false,
	                 showClose: true,
	                 closeByDocument: true,
	                 closeByEscape: true,
	                 appendTo: false,
	    		    template: 'views/pages/deptAdd.html',
	    		    scope: $scope,
	    		    controller: ['$scope', 
	                             '$state', 
	                             '$http',
	 						    'localStorageService',
	 						'alertService',
	 						'$stateParams',
	 						'ngDialog',
	 						'$window',
	 						'$confirm',
	 						"IntegralUITreeGridService",
	 						"$timeout",
	 						function($scope, $state, $http, localStorageService,
	 								alertService,  $stateParams,ngDialog,
	 								$window,$confirm,$gridService,$timeout) {
	    		    	
	    		    	
	    		        // controller logic
	    		    	
	    				$scope.addDept=function (){
	    					  alertService.cleanAlert();
	    					if (isNaN($scope.valId)) { 
	    						alertService
	    						.add("danger",T.T('id_must_be_number'));
	    						return ;
	    					}
	    					
	    					if ($scope.valId.length>4) { 
	    						alertService
	    						.add("danger",T.T('id_can_only_four_digits'));
	    						return ;
	    					}
	    	             
	    	                var obj={deptId:$scope.valId,description:$scope.valDesc};
	    	                $http.post('./dept/addDept', obj)
	    	        				.success(
	    	        						function(data) {
	    	        							if (data != null && data != '') {
	    	        								if (data['errorType'] != "success") {
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        						        
	    	        								} else {
	    	        								     $scope.closeThisDialog(0);
	    	        						             ngDialog.closePromise;
	    	        						             var deptObj=data.returnData;
	    	        						         	$gridService.addRow($scope.gridName, createDeptRow(deptObj));
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        								}

	    	        							}

	    	        						});
	    				}
	    				
	    		    }],
	    		    preCloseCallback: function(value) {
	    		    	  alertService.cleanAlert();
	    	     		   
	    		    }
	    		});
	    		
	    	}
	    	

	
	    	
	    	function openClassAdd(){
	    		  alertService.cleanAlert();
	    		$http({
	    			method : 'GET',
	    			params : {
	    				deptId : $scope.searchDeptId
	    			},
	    			url : "./dept/getDeptDetail"
	    		}).success(
	    						function(data) {
	    							$scope.deptId=data.deptId;
	    							$scope.deptDescripton=data.description;
	    						});
	    		
	    		
	    		
	    		ngDialog.open({
	    			 className: 'ngdialog-theme-default',
	                 plain: false,
	                 showClose: true,
	                 closeByDocument: true,
	                 closeByEscape: true,
	                 appendTo: false,
	                 
	                 template: 'views/pages/classAdd.html',
	    		    scope: $scope,
	    		    controller: ['$scope', 
	                             '$state', 
	                             '$http',
	 						    'localStorageService',
	 						'alertService',
	 						'$stateParams',
	 						'ngDialog',
	 						'$window',
	 						'$confirm',
	 						"IntegralUITreeGridService",
	 						"$timeout",
	 						function($scope, $state, $http, localStorageService,
	 								alertService,  $stateParams,ngDialog,
	 								$window,$confirm,$gridService,$timeout) {
	    		    	
	    		    	//logic
	    		    	$scope.addClass=function (){
	    		    		  alertService.cleanAlert();
	    						if (isNaN($scope.valId)) { 
	    							alertService
	    							.add("danger",T.T('id_must_be_number'));
	    							return ;
	    						}
		    					if ($scope.valId.length>4) { 
		    						alertService
		    						.add("danger",T.T('id_can_only_four_digits'));
		    						return ;
		    					}
	    	                var obj={classId:$scope.valId,description:$scope.valDesc,deptId:$scope.parentId};
	    	                $http.post('./dept/addClass', obj)
	    	        				.success(
	    	        						function(data) {
	    	        							if (data != null && data != '') {
	    	        								if (data['errorType'] != "success") {
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        						        
	    	        								} else {
	    	        								     $scope.closeThisDialog(0);
	    	        						             ngDialog.closePromise;
	    	        						             var deptObj=data.returnData;
	    	        						             if(deptObj!=null){
	    	        						            	 var classArray=deptObj.classList;
	    	        						            	 if(classArray!=null&&classArray.length>0){
//	    	        						            		 console.log("classArray[0]classArray[0]");
//	    	        						            		 console.log(classArray[0]);
	    	        						            		 $scope.selectedRow.rows.push(createClassRow(classArray[0],deptObj));
	    	        						            		 $gridService.resumeLayout($scope.gridName);
//	    	        						            	 	$gridService.insertRowAfter($scope.gridName, createClassRow(classArray[0],deptObj), $scope.selectedRow);
	    	        						            	 }
	    	        						             }
		    	        						         	
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        								}

	    	        							}

	    	        						});
	    				}
	    		    	
	    		    	//logic
	    		    	
	    		    }],
	    		    preCloseCallback: function(value) {
	    		    	  alertService.cleanAlert();
	    	     		   
	    		    }
	    		});
	    		
	    	}
	    	
	    	//$scope.selectedRow
	    	
		
	    	
	    	function openSubClassAdd(){
	    		$http({
	    			method : 'GET',
	    			params : {
	    				deptId : $scope.searchDeptId,
	    				classId:$scope.searchClassId
	    			},
	    			url : "./dept/getClassDetail"
	    		}).success(
	    						function(data) {
	    							$scope.deptId=data.deptId;
	    							$scope.deptDescripton=data.deptDesc;
	       							$scope.classId=data.classId;
	    							$scope.classDescripton=data.description;
	    						});
					
	    		  alertService.cleanAlert();
	    		ngDialog.open({
	    			 className: 'ngdialog-theme-default',
	                 plain: false,
	                 showClose: true,
	                 closeByDocument: true,
	                 closeByEscape: true,
	                 appendTo: false,
	    			template: 'views/pages/subClassAdd.html',
	    			scope: $scope,
	    			controller: ['$scope', 
	                             '$state', 
	                             '$http',
	 						    'localStorageService',
	 						'alertService',
	 						'$stateParams',
	 						'ngDialog',
	 						'$window',
	 						'$confirm',
	 						"IntegralUITreeGridService",
	 						"$timeout",
	 						function($scope, $state, $http, localStorageService,
	 								alertService,  $stateParams,ngDialog,
	 								$window,$confirm,$gridService,$timeout) {
	    				$scope.addSubClass=function (){
	    					  alertService.cleanAlert();
	    						if (isNaN($scope.valId)) { 
	    							alertService
	    							.add("danger",T.T('id_must_be_number'));
	    							return ;
	    						}
		    					if ($scope.valId.length>4) { 
		    						alertService
		    						.add("danger",T.T('id_can_only_four_digits'));
		    						return ;
		    					}
		    					
	    	                var obj={subClassId:$scope.valId,description:$scope.valDesc,classId:$scope.parentId};
	    	                $http.post('./dept/addSubClass', obj)
	    	        				.success(
	    	        						function(data) {
	    	        							if (data != null && data != '') {
	    	        								if (data['errorType'] != "success") {
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        						        
	    	        								} else {
	    	        								     $scope.closeThisDialog(0);
	    	        						             ngDialog.closePromise;
	    	        						             var deptObj=data.returnData;
	    	        						             if(deptObj!=null){
	    	        						            	 var classArray=deptObj.classList;
	    	        						            	 if(classArray!=null&&classArray.length>0){
	    	        						            		 var classObj=classArray[0];
	    	        						            		 var subclassArray=classObj.subClassList;
	    	        						            		 if(subclassArray!=null&&subclassArray.length>0){
	    	        						            			 var subclassObj=subclassArray[0];
	    	        						            				var sid=$scope.selectedRow.id;
	    	    	        						    				var flag=sid.substring(0,1);
	    	    	        						    				if(flag=='C'){
	    	    	        						    					$scope.selectedRow.rows.push(createSubClasRow(subclassObj,classObj,deptObj));
	    	    	        						    					 $gridService.resumeLayout($scope.gridName);
	    	    	        						    				}
	    	    	        						    				
	    	    	        						    				if(flag=='S'){
	    		    	        						            	 	$gridService.insertRowAfter($scope.gridName, createSubClasRow(subclassObj,classObj,deptObj), $scope.selectedRow);

//	    	    	        						    					$scope.selectedRow.rows.push(createSubClasRow(subclassObj,classObj,deptObj));
	    	    	        						    					$gridService.resumeLayout($scope.gridName);
	    	    	        						    				}
	    	    	        						    				 
	    	        						            		 }
	    	        						            	
	    	        						     	    	
	    	        						    				
	    	        						            		
	    	        						            	 }
	    	        						             }
	    	        						             
	    	        									alertService
	    	        											.add(
	    	        													data["errorType"],
	    	        													data["errorMessage"]);
	    	        								}

	    	        							}

	    	        						});
	    				}
	    				
	    				
	    			}],
	 								
	    			preCloseCallback: function(value) {
	    				  alertService.cleanAlert();
	    				
	    			}
	    		});
	    		
	    	}
	    	

	    	
			
		
			
    	function openCategoryAdd(){
    		
    		  alertService.cleanAlert();
  		
  		ngDialog.open({
  			className: 'ngdialog-theme-default custom-width-800',
             plain: false,
             showClose: true,
             closeByDocument: true,
             closeByEscape: true,
             appendTo: false,
  		    template: 'views/pages/eStoreCategoryAdd.html',
  		    scope: $scope,
  		    controller: ['$scope', 
                         '$state', 
                         '$http',
						    'localStorageService',
						'alertService',
						'$stateParams',
						'ngDialog',
						'$window',
						'$confirm',
						"IntegralUITreeGridService",
						"$timeout",
						function($scope, $state, $http, localStorageService,
								alertService,  $stateParams,ngDialog,
								$window,$confirm,$gridService,$timeout) {
  		    	//logic
  		  	$scope.chooseCategory = function(data) {
				$scope.estoreCategoryId = data.lovValue;
			}
			
			$scope.addOrUpdateCategory=function (){
				  alertService.cleanAlert();
               
        		$http({
        			method : 'POST',
        			params : {
        				ecategroyId : $scope.estoreCategoryId,
        				subClassId:$scope.subclassId
        			},
        			url : "./dept/saveEstoreCategory"
        		})
        				.success(
        						function(data) {
        							if (data != null && data != '') {
        								
        								if (data['errorType'] != "success") {
        					
        									alertService
        											.add(
        													data["errorType"],
        													data["errorMessage"]);
        						        
        								} else {
        								     $scope.closeThisDialog(0);
        						             ngDialog.closePromise;
        						             
        						             var deptObj=data.returnData;
        						             if(deptObj!=null){
        						            	 var classArray=deptObj.classList;
        						            	 if(classArray!=null&&classArray.length>0){
        						            		 var classObj=classArray[0];
        						            		 var subclassArray=classObj.subClassList;
        						            		 if(subclassArray!=null&&subclassArray.length>0){
        						            			 var subclassObj=subclassArray[0];
	    	        						            	 	$gridService.insertRowAfter($scope.gridName, createSubClasRow(subclassObj,classObj,deptObj), $scope.selectedRow);
	    	        						            	 	$gridService.removeRow($scope.gridName, $scope.selectedRow);
    	        						    					$gridService.resumeLayout($scope.gridName);
//    	        						    				}
    	        						    				 
        						            		 }
        						            	
        						     	    	
        						    				
        						            		
        						            	 }
        						             }
        						             
        									alertService
        											.add(
        													data["errorType"],
        													data["errorMessage"]);
        								}

        							}

        						});
			}
  		    	
  		    	//logic
  		    	
  		    }],
  		    
  		    preCloseCallback: function(value) {
  		      alertService.cleanAlert();
  	     		   
  		    }
  		});
  		
  		getCategorys();
 
  		
  	}
    	
    	function getCategorys() {
    		$scope.promise =$http({method : 'GET',url : "./dept/getCategorys"}).success(
							function(data) {
									//console.log(data);
									$scope.estoreCategorys = data;

									if ($scope.selectCategory!=null&&$scope.estoreCategorys!=null&& $scope.estoreCategorys.length > 0) {
										for (var i = 0; i < $scope.estoreCategorys.length; i++) {
											$scope.estoreCategory = new Array();
											if ($scope.selectCategory == $scope.estoreCategorys[i].lovValue) {

												var ctObj = $scope.estoreCategorys[i];
												var obj = {
													ticked : true,
													lovDesc : ctObj.lovDesc,
													lovValue : ctObj.lovValue
												};

												var qqs = $scope.estoreCategorys;
												qqs.splice(i, 1,
														obj);
												$scope.estoreCategorys = qqs;
												$scope.estoreCategory
														.push(obj);
											}
										}

									}

								
							

							});
		}
    	
    	  function updateData(pidVal,ridVal,valDataObj){
        	  alertService.cleanAlert();
        	  
        	  
        $http({
			method : 'POST',
			params : {
				pid : pidVal,
				rid : ridVal,
				valData : valDataObj
			},
			url : "./dept/updateData"
		})
				.success(
						function(data) {
							// console.log(data);
							if (data != null && data != '') {
								if (data['errorType'] != "success") {
									getDeptTree();
									$scope.closeAlert = alertService.closeAlert;
									alertService
											.add(
													data["errorType"],
													data["errorMessage"]);
								
								} else {
									$scope.closeAlert = alertService.closeAlert;
									alertService
											.add(
													data["errorType"],
													data["errorMessage"]);
								}

							}

						});
        }

		getDeptTree();
		
		
    function getDeptTree(){
	    alertService.cleanAlert();
	    $scope.promise = $http({ method: 'GET',url: "./dept/getDeptTree" }).
		success(function (data) {
			$gridService.suspendLayout($scope.gridName);
			$gridService.clearRows($scope.gridName);
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				$scope.rows.push(createDeptRow(data[i]));
			}
		}
	
		$gridService.resumeLayout($scope.gridName);
    });
      }
      
      function refereDeptTree(data){
          

   			$gridService.suspendLayout($scope.gridName);
   			$gridService.clearRows($scope.gridName);
   		if(data.length>0){
   			for(var i=0;i<data.length;i++){
   				$scope.rows.push(createDeptRow(data[i]));
   			}
   		}
   	
   		$gridService.resumeLayout($scope.gridName);
  
         }
     var createDeptRow=function (deptObj){

    	 var classList=deptObj.classList;
    	 var classRow=[];
    	 if(classList!=null&&classList.length>0){
    		 for(var n=0;n<deptObj.classList.length;n++){
    			 var classObj=classList[n];
    			 classRow.push(createClassRow(classObj,deptObj));
    		 }
    	 }
    	 
//    	 var row={id:'D'+deptObj["id"],text:deptObj.description,hid:deptObj.id,expanded :false,cells:createDeptCells(deptObj),rows:classRow};
    	 var row={id:'D'+deptObj["id"],text:deptObj.description,hid:deptObj.id,expanded :false,cells:createDeptCells(deptObj),rows:classRow};
    	 return row;
    	 
     }
     var createDeptCells=function (deptObj){
    	 var cells=[{ cid: 9, value: imageUnchecked }, { cid: 1, text: deptObj.description,searchDeptId:deptObj.id }, { cid: 2,text:deptObj.deptId}, { cid: 3,text:deptObj.description}];
    	 return cells;
     }
     
     var createClassRow=function(classObj,deptObj){
    	 var subClassList=classObj.subClassList;
    	 var subClassRow=[];

    	 if(subClassList!=null&&subClassList.length>0){
    		 for(var n=0;n<classObj.subClassList.length;n++){
    			 var subClassObj=classObj.subClassList[n];
    		
    			 subClassRow.push(createSubClasRow(subClassObj,classObj,deptObj));
    		 }
    	 }
    	 
    	 var row={id:'C'+classObj["id"],hid:classObj.id,expanded :false,text:classObj.description,cells:createClassCells(classObj,deptObj),rows:subClassRow};
    	 return row;
     }
     
     var createClassCells=function (classObj,deptObj){
    	 var cells=[{ cid: 9, value: imageUnchecked }, { cid: 1, text:classObj.description,searchDeptId:deptObj.id,searchClassId:classObj.id}, { cid: 2,text:classObj.classId,rid:'C'+classObj["id"]}, { cid: 3,text:classObj.description,rid:'C'+classObj["id"]}];
    	 return cells;
     }
     
     var createSubClasRow=function(subClassObj,classObj,deptObj){
    
    	 var row={id:'S'+subClassObj["id"],hid:subClassObj.classId,expanded :false,text:subClassObj.description,cells:createSubClasCells(subClassObj,classObj,deptObj)};
    	 return row;
     }
     
     var createSubClasCells=function (subClassObj,classObj,deptObj){
    	 var cells=[{ cid: 9, value: imageUnchecked }, { cid: 1, text: subClassObj.description,searchDeptId:deptObj.id,searchClassId:classObj.id}, { cid: 2,text:subClassObj.subClassId,rid:'S'+subClassObj["id"]}, { cid: 3,text:subClassObj.description,rid:'S'+subClassObj["id"]},{cid:4,text:subClassObj.lovDesc,templateObj: {indicator: true,text: subClassObj.lovDesc},eid:subClassObj.ecategroyId,rid:'S'+subClassObj["id"]}];
    	 return cells;
     }


        }])
    .directive('stRatio', function () {
        return {
            link: function (scope, element, attr) {
                var ratio = +(attr.stRatio);

                element.css('width', ratio + '%');

            }
        };
    })


