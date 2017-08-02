var app = angular.module("psspAdminApp", [ 'tabsTemplate' ]).directive("myTabs",
		function() {
			return {
				restrict : "EA",
				transclude : true,
				scope : {},
				templateUrl : "myTabs.html",
				controller : [ "$scope", function($scope) {// 使用controller让最内层指令来继承外层指令，这样内层就可以通过scope的传导，来与外层指令进行数据之间的传递
					var panes = $scope.scopes = [];//
					 
					$scope.check = function () {
						if($scope.$parent.checkSave != undefined) {
							$scope.$parent.checkSave();
						}else{
							$scope.$parent.checked = true;
						} 
					}
					
					$scope.select = function(pane) {// 实现tabs功能
						if($scope.$parent.checked == undefined || $scope.$parent.checked) {
							angular.forEach(panes, function(scope) { // 遍历所有内存指令scope，统一隐藏内容。
								scope.selected = false;
							});
							pane.selected = true;// 通过ng-repeat只
						}
					};

					this.addScope = function(scope) {// 由内层指令来继承，把内层指令的scope，传到进外层指令进行控制
						if (panes.length === 0) {
							$scope.select(scope);
						}
						panes.push(scope);// 把内层指令数组，传入外层指令scope数组。
					}
					
				}]
			}
		}).directive("myPane", function() {
	return {
		restrict : 'EA',
		scope : {
			tittle : '@'
		},
		transclude : true,
		require : '^myTabs',// 继承外层指令
		templateUrl : "myPane.html",
		link : function(scope, elemenet, attrs, myTabsController) {
			myTabsController.addScope(scope);// 把内层指令的scope存入到外层指令中，让外层遍历。
		}
	}
});

angular.module("tabsTemplate", [])
		.run(["$templateCache", function($templateCache) {
					$templateCache.put(
						"myTabs.html",
						"<div class='table'>"
						+ "<ul class='nav nav-tabs'>"
						+ "<li ng-repeat='pane in scopes' ng-class='{active:pane.selected}' class='subTab'>"
						+ "<a ng-click='check();select(pane)'>{{pane.tittle}}<a/>"
						+ "</li>"
						+ "</ul>"
						+ "<div ng-transclude class='tab-content'></div>"
						+ "</div>")
				} ])
		.run([ "$templateCache", function($templateCache) {
					$templateCache.put("myPane.html",
						"<div class='table-pane' ng-show='selected' ng-transclude>"
						+ "</div>")
				} ])