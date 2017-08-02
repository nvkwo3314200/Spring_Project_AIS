/**
 * @license Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.language = "en";
	//config.filebrowserBrowseUrl= root_url+'/fileman/index.html';
    //config.filebrowserImageBrowseUrl = root_url+'/fileman/index.html?type=image';
    //config.removeDialogTabs = 'link:upload;image:upload';
    //config.extraPlugins = 'youtube';
    
    config.toolbar =
		[
			{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
			{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','Scayt' ] },
			{ name: 'styles', items : [ 'Styles','Format' ] },
			{ name: 'basicstyles', items : [ 'Bold','Italic','Strike','-','RemoveFormat','Format','Font','FontSize','TextColor','BGColor' ] },
			{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote' ] }
		]
		
	config.resize_enabled = false;
    config.allowedContent = true;//Disabling ACF, CKEditor will not filter entered content
};
