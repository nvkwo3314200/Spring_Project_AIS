'use strict';

angular.module('psspAdminApp').factory('alertService', function($rootScope, T) {
	var alertService = {};

	$rootScope.alerts = [];
	
	alertService.msg;
	
	alertService.isSaveSuccess = false;
	
	alertService.isUpdateSuccess =  false;
	
	alertService.isDeleteSuccess =  false;
	
	alertService.add = function(type, msg) {
		$rootScope.alerts = [];
		$rootScope.alerts.push({
			'type' : type,
			'msg' : msg
		});
	};

	alertService.closeAlert = function(index) {
		$rootScope.alerts.splice(index, 1);
	};
	
	alertService.cleanAlert = function(index) {
		$rootScope.alerts = [];
	};
	
	alertService.showMsg = function(){
		
		if(alertService.isSaveSuccess){
			alertService.add('success', T.T('save_success'));
			alertService.isSaveSuccess = false;
		}
		
		if(alertService.isUpdateSuccess){
			alertService.add('success', T.T('update_success'));
			alertService.isUpdateSuccess = false;
		}
		
		if(alertService.isDeleteSuccess){
			alertService.add('success', T.T('delete_success'));
			alertService.isDeleteSuccess = false;
		}
		
		if(alertService.msg != undefined) {
			alertService.add('danger', alertService.msg);
			alertService.msg = undefined;
		}
	};
	
	alertService.saveSuccess = function(){
		alertService.isSaveSuccess = true;
	};
	
	alertService.updateSuccess = function(){
		alertService.isUpdateSuccess = true;
	};
	
	alertService.deleteSuccess = function(){
		alertService.isDeleteSuccess = true;
	};
	
	return alertService;
});
