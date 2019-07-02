/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbarGroups = [
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [  'spellchecker' ] },
		{ name: 'links' },
		{ name: 'alignment', groups : [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
		{ name: 'insert' },
		 
		
	 
		{ name: 'others' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'tools' },
		{ name: 'about' }
	];
config.extraPlugins = 'imageuploader';
 
//filebrowserBrowseUrl = st_url+'ckeditor/plugins/imageuploader/imgbrowser.php';
//config.imageBrowser_listUrl = st_url+"sitebrowse";
//config.allowedContent = true; 
var temp_sturl = st_url.replace("webadmin/index.php/", "") ;
config.contentsCss =  temp_sturl+ '/assetswti/css/style.css';


//CKEDITOR.instances['instanceName'].insertHtml('<img src="http://127.0.0.1/thc/webadmin/images/avatar.png">');


	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	 

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Simplify the dialog windows.
	 //config.removeDialogTabs = 'image:advanced;link:advanced';
};
