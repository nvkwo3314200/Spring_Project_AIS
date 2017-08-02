'use strict';
/**
 * @ngdoc function
 * @name psspAdminApp.controller:ProductViewCtrl
 * @description
 * # ProductViewCtrl
 * Controller of the psspAdminApp
 */
angular.module('psspAdminApp', ['treeGrid'])

	.controller('ProductCatCtrl', function ($scope, $state, $http, $interval,  alertService, localStorageService, $window, $confirm, T, $q, $timeout) {
        
    	var tree;
        
        var rawTreeData = [];
        $http.post("./shop/getCategoryList").success(function (data) {
    		if(data["errorType"] = "success") {
    			rawTreeData = data["returnDataList"];
    			$scope.tree_data = getTree(rawTreeData, 'code', 'parentId');;
    		}
    	});
        /*
        rawTreeData = [
        	{
        		code:'1',
        		parentId:null,
        		name:'parent'
        	},{
        		code:'2',
        		parentId:'1',
        		name:'child1'
        	},{
        		code:'3',
        		parentId:'1',
        		name:'child2'
        	},{
        		code:'4',
        		parentId:'2',
        		name:'child_c_1'
        	},{
        		code:'4',
        		parentId:'2',
        		name:'child_c_2'
        	}]
        	*/
        
        $scope.addProductCat = function() {
        	console.log($scope.tree_data_select);
        }
        
        var myTreeData = getTree(rawTreeData, 'code', 'parentId');
        $scope.tree_data = myTreeData;
        $scope.my_tree = tree = {};
        $scope.expand_on = {
            field: "name",
            displayName: T.T('shop_category'),
            sortable: true,
            filterable: true,
            cellTemplate: "<i>{{row.branch[expandingProperty.field]}}</i>"
        };

        $scope.col_defs = [
            {
                field: "Seq",
                sortable: true,
                displayName: T.T('product_cat_seq'),
                sortingType: "number"
            }
        ];
        $scope.my_tree_handler = function (branch) {
            console.log('you clicked on', branch)
        }

        function getTree(data, primaryIdName, parentIdName) {
            if (!data || data.length == 0 || !primaryIdName || !parentIdName)
                return [];

            var tree = [],
                rootIds = [],
                item = data[0],
                primaryKey = item[primaryIdName],
                treeObjs = {},
                parentId,
                parent,
                len = data.length,
                i = 0;

            while (i < len) {
                item = data[i++];
                primaryKey = item[primaryIdName];
                treeObjs[primaryKey] = item;
                parentId = item[parentIdName];
                if(parentId == "") parentId = null;
                if (parentId) {
                    parent = treeObjs[parentId];

                    if (parent.children) {
                        parent.children.push(item);
                    } else {
                        parent.children = [item];
                    }
                } else {
                    rootIds.push(primaryKey);
                }
            }

            for (var i = 0; i < rootIds.length; i++) {
                tree.push(treeObjs[rootIds[i]]);
            }
            ;
            return tree;
        }

    });


        
