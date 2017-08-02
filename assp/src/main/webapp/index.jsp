<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-control" content="no-cache">
	<meta http-equiv="Cache" content="no-cache">
	<!-- <meta http-equiv="X-Frame-Options" content="SAMEORIGIN"> -->
    <title>B2B Supplier Portal</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- 
    <link rel="shortcut icon" href="pssp/images/favicon.png" />
     -->
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    
    <!-- Ionicons-->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
     
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
    <!--  Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <link rel="stylesheet" href="dist/css/skins/skin-blue.css">

	<link rel="stylesheet" href="bower_components/angular-loading-bar/loading-bar.min.css" media='all'>
    <!-- HTML5 Shim and IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- bower:js -->
    <script src="bower_components/angular/angular.min.js"></script>
    <script src="bower_components/jquery/dist/3.2.0/jquery-3.2.0.min.js"></script>
    <!-- 
    <script src="bower_components/jquery/dist/jquery.min.js"></script>
     -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script src="bower_components/json3/lib/json3.min.js"></script>
    <script src="bower_components/oclazyload/dist/ocLazyLoad.min.js"></script>
    <script src="bower_components/angular-loading-bar/loading-bar.min.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>
    <script src="bower_components/angular-local-storage/dist/angular-local-storage.js"></script>

    <script src="bower_components/smart-table/smart-table.min.js"></script>
    <script src="bower_components/bower-angular-translate-2.9.0.1/angular-translate.js"></script>
    <script src="bower_components/bower-angular-translate-loader-static-files-master/angular-translate-loader-static-files.min.js"></script>
    <script src="bower_components/filesaver/FileSaver.min.js"></script>
    
    <script src="bower_components/angular-multi-select-master/isteven-multi-select.js"></script>
	<script src="pssp/js/common.js"></script>

	
    <link rel="stylesheet" href="bower_components/angular-multi-select-master/isteven-multi-select.css">
    <script src="bower_components/angular-confirm-master/angular-confirm.js"></script>
          
	   
 
 
  <script src="bower_components/angular-busy-master/dist/angular-busy.js"></script>
  <link href="bower_components/angular-busy-master/dist/angular-busy.css" rel="stylesheet">
  <link href="dist/gh-fork-ribbon.css" rel="stylesheet">
    <!-- endbower -->    
    <!-- validation:js -->
       <script src="dist/angular-validation.js"></script>
       <script src="dist/angular-validation-rule.js"></script>
       <script src="dist/html5-sortable.js"></script>
       <!-- validation:css -->
           <link rel="stylesheet" href="dist/validation.css">
           <link rel="stylesheet" href="dist/gh-fork-ribbon.css">
           <!-- end validation -->
          
                      
           <link rel="stylesheet" href="pssp/css/common.css">
           <link rel="stylesheet" href="pssp/css/iconfont/iconfont.css">
           <link rel="stylesheet" href="pssp/css/smart_table.css">
           
           
                
  <!-- tree grid -->         
    <link rel="stylesheet" href="dist/tree/css/integralui.css" />
    <link rel="stylesheet" href="dist/tree/css/integralui.treegrid.css" />
    <link rel="stylesheet" href="dist/tree/css/integralui.checkbox.css" />
    <link rel="stylesheet" href="dist/tree/css/themes/theme-flat-blue.css" />
    <script type="text/javascript" src="dist/tree/js/angular.integralui.min.js"></script>
    <script type="text/javascript" src="dist/tree/js/angular.integralui.lists.min.js"></script>
    <script type="text/javascript" src="dist/tree/js/angular.integralui.treegrid.min.js"></script>
    <script type="text/javascript" src="dist/tree/js/angular.integralui.checkbox.min.js"></script>
  <!-- tree grid -->         
              
    <script src="scripts/app.js"></script>
    <script src="scripts/interceptors/responseInterceptor.js"></script>    
    <script src="scripts/interceptors/requestInterceptor.js"></script>   
    <script src="scripts/services/userService.js"></script>
    <script src="scripts/services/shopService.js"></script>
    <script src="scripts/services/alertService.js"></script>
    
    <!--  ngDialog -->
    <link rel="stylesheet" href="bower_components/ngDialog-master/css/ngDialog.css">
    <link rel="stylesheet" href="bower_components/ngDialog-master/css/ngDialog-theme-default.css">
    <link rel="stylesheet" href="bower_components/ngDialog-master/css/ngDialog-theme-plain.css">
    <link rel="stylesheet" href="bower_components/ngDialog-master/css/ngDialog-custom-width.css">
    <script src="bower_components/ngDialog-master/js/ngDialog.js"></script>
	
	<!-- +awerlang-angular-responsive-tables -->   
     <script src="bower_components/awerlang-angular-responsive-tables/release/angular-responsive-tables.js"></script>
     <link rel="stylesheet" href="bower_components/awerlang-angular-responsive-tables/release/angular-responsive-tables.css">
     
     <!-- tree guid -->
     <script src="bower_components/tree-table/tree-grid-directive.js"></script>
     <link rel="stylesheet" href="bower_components/tree-table/treeGrid.css">
     
 <!-- <meta name="x-frame-options" content="allowall" /> -->


        
  </head>

  <body class="hold-transition skin-blue sidebar-mini login-page">
      <div ng-app="psspAdminApp">
	  	<div ui-view></div>
	  </div>
	  


	 <script src="dist/js/app.min.js"></script>
  </body>
</html>
