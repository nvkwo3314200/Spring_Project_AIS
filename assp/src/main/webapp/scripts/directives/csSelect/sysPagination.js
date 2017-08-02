'use strict';

angular.module('psspAdminApp').directive('sysPagination', function() {
	return {
		templateUrl : 'scripts/directives/csSelect/sys_pagination.html',
		restrict : 'E',
		replace : true,
		link: function ($scope) {
			
			$scope.initPageModel = function(){
				
				$scope.pageModel.row = $scope.pageModel.row || 10;
				$scope.pageModel.pages = $scope.pageModel.pages || 1;
				$scope.pageModel.current = $scope.pageModel.current || 1;
				
			};
			
			$scope.selectPage = function(current){
				
				if(current == '') return;
				
				current = Math.abs( parseInt(current) );
				
				$scope.pageModel.current = Math.min(current, $scope.pageModel.pages);
				
				$scope.pageModel.endRow = $scope.pageModel.current * $scope.pageModel.row;
				$scope.pageModel.startRow = $scope.pageModel.endRow - $scope.pageModel.row +1;
				
				$scope.search();
				
			};
			
			$scope.selectRow = function(row){
				
				row = Math.abs( parseInt(row) );
				
				$scope.pageModel.row = Math.min(row, $scope.pageModel.dataTotal || row);
				
				$scope.selectPage(1);
				
			};
			
			$scope.$watch('tableData', function(newVal, oldVal){
				
				if(newVal == oldVal) return;
				
				$scope.pageModel.current = $scope.pageModel.startRow==1 ? 1 : $scope.pageModel.current;
				
				$scope.pageModel.startRow = 1;
				$scope.pageModel.endRow = $scope.pageModel.row;
					
				$scope.pageModel.dataTotal = newVal.length && newVal[0].dataTotal;
				
				$scope.pageModel.pages = parseInt(Math.ceil($scope.pageModel.dataTotal / $scope.pageModel.row)) || 1;
				
				if($scope.pageModel.current != 1 && newVal.length == 0){
					
					$scope.selectPage(1);
					
				}
				
			});

			$scope.initPageModel();
			$scope.selectPage($scope.pageModel.current);
        }
	};
});