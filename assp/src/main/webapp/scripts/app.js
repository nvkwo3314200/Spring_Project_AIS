'use strict';
/**
 * @ngdoc overview
 * @name psspAdminApp
 * @description # psspAdminApp
 * 
 * Main module of the application.
 */

angular.module('psspAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar',
    'LocalStorageModule',
    'pascalprecht.translate',
    'validation',
    'validation.rule',
    'isteven-multi-select',
    'smart-table',
    'html5.sortable',
    'ngDialog',
    'angular-confirm',
    'integralui',
    'cgBusy',
    'wt.responsive'
])
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$httpProvider', '$translateProvider', '$validationProvider','ngDialogProvider',
        function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider, $translateProvider, $validationProvider,ngDialogProvider) {
            $validationProvider.showSuccessMessage = false;
            ngDialogProvider.setForceHtmlReload(true);
            var lang = window.localStorage.lang || 'en';

           
            $translateProvider.preferredLanguage(lang);
            $translateProvider.useStaticFilesLoader({
                prefix: 'i18n/',
                suffix: '.json'
            });
            $translateProvider.useSanitizeValueStrategy(null);

            $ocLazyLoadProvider.config({
                debug: false,
                events: true,
            });

            $urlRouterProvider.otherwise('/main');

            $httpProvider.interceptors.push('requestInterceptor');
            $httpProvider.interceptors.push('responseInterceptor');

            if(!$httpProvider.defaults.headers.get){
                $httpProvider.defaults.headers.get = {};
            }
            $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
            $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

            $stateProvider
                .state('main', {
                    url: '/main',
                    templateUrl: 'views/main/main.html',
                    resolve: {
                        loadMyDirectives: function ($ocLazyLoad) {
                        	
                            return $ocLazyLoad.load(
                                    {
                                        name: 'toggle-switch',
                                        files: ["bower_components/angular-toggle-switch/angular-toggle-switch.min.js",
                                            "bower_components/angular-toggle-switch/angular-toggle-switch.css"
                                        ],
                                        cache: true
                                    }),
                                $ocLazyLoad.load(
                                    {
                                        name: 'ngAnimate',
                                        files: ['bower_components/angular-animate/angular-animate.js'],
                                        cache: true
                                    }),
                                $ocLazyLoad.load(
                                    {
                                        name: 'ngCookies',
                                        files: ['bower_components/angular-cookies/angular-cookies.js'],
                                        cache: true
                                    }),
                                $ocLazyLoad.load(
                                    {
                                        name: 'ngResource',
                                        files: ['bower_components/angular-resource/angular-resource.js'],
                                        cache: true
                                    }),
                                $ocLazyLoad.load(
                                    {
                                        name: 'ngSanitize',
                                        files: ['bower_components/angular-sanitize/angular-sanitize.js'],
                                        cache: true
                                    }),
                                $ocLazyLoad.load(
                                    {
                                        name: 'psspAdminApp',
                                        files: [
                                            'scripts/controllers/base.js',
                                            'scripts/directives/csSelect/csSelect.js',
                                            'scripts/directives/csSelect/pageSelect.js',
                                            'scripts/directives/csSelect/rowSelectAll.js',
                                            'scripts/directives/csSelect/rowSelect.js',
                                            'scripts/directives/csSelect/sysPagination.js',
                                            'scripts/services/cacheService.js',
                                            'scripts/services/parmService.js',
                                            'scripts/services/alertService.js',
                                            'views/pages/pagination_custom.html',
                                            'scripts/directives/header/header.js',
                                            'scripts/directives/footer/footer.js',
                                            'scripts/directives/sidebar/sidebar.js'
                                        ]
                                    })
// $ocLazyLoad.load(
// {
// name:'pascalprecht.translate',
// files:['bower_components/bower-angular-translate-2.9.0.1/angular-translate.js']
// })

                        }
                    }
                })
                .state('login', {
                    templateUrl: 'views/pages/login.html',
                    url: '/login',
                    controller: 'LoginCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: [
                                    'scripts/controllers/login.js',
                                    'scripts/services/alertService.js'
                                ]
                            })
                        }
                    }
                })
                
                 .state('send', {
                    templateUrl: 'views/pages/forget.html',
                    url: '/send',
                    controller: 'ForgetCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: [
                                    'scripts/controllers/forget.js',
                                    'scripts/services/alertService.js'
                                ]
                            })
                        }
                    }
                })
                
                   .state('forget', {
                    templateUrl: 'views/pages/resetPassword.html',
                    url: '/forget',
                    controller: 'ForgetCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: [
                                    'scripts/controllers/forget.js',
                                    'scripts/services/alertService.js'
                                ]
                            })
                        }
                    }
                })
                
                .state('main.password', {
                	templateUrl: 'views/pages/password.html',
                	url: '/changePassword',
                	controller: 'PasswordCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: [
                				        'scripts/controllers/password.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
   
                  .state('main.product_view', {
                    templateUrl: 'views/pages/productView.html',
                    url: '/productView',
                    controller: 'ProductViewCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/productView.js'
                                    , 'scripts/services/alertService.js']
                            })
                        }
                    }

                })
                
                 .state('main.product_import_log', {
                    templateUrl: 'views/pages/logView.html',
                    url: '/logView',
                    controller: 'LogViewCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/productImportLogView.js'
                                    , 'scripts/services/alertService.js']
                            })
                        }
                    }

                })
                
                 .state('main.user_view', {
                    templateUrl: 'views/pages/userView.html',
                    url: '/userView',
                    controller: 'UserViewCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/user.js'
                                    , 'scripts/services/alertService.js']
                            })
                        }
                    }

 				})
                

                   .state('main.user_add', {
                    templateUrl: 'views/pages/userAdd.html',
                    url: '/userAdd?:userId',
                    controller: 'UserAddCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/userAdd.js',
                                        'scripts/services/alertService.js'
                                        
                                    ]
                                })
                        }
                    }
                })
                
                               
                .state('main.view_order_history', {
                    templateUrl: 'views/pages/odrPage.html',
                    url: '/order',
                    controller: 'OrderCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/order.js' ,
                                    'scripts/services/alertService.js']
                            })
                        }
                    }
                })
                
                // ==============START================== MOBILE
				// ==============================
                .state('main.mobile_view_order_history', {
                    templateUrl: 'views/pages/mobileOdrPage.html',
                    url: '/mobileOrder',
                    controller: 'OrderCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/order.js' ,
                                    'scripts/services/alertService.js']
                            })
                        }
                    }
                })
                
                .state('main.mobile_orderDisplayDetails', {
                    templateUrl: 'views/pages/mobileOdrPageTab.html',
                    url: '/mobileOrderDetails?:orderNr',
                    controller: 'OrderDisplayDetailsCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/orderDetails.js' ,
                                        'scripts/services/alertService.js']
                                })
                        }
                    }


                })
                

// ==========================END====== MOBILE ==============================
                .state('main.orderDisplayDetails', {
                    templateUrl: 'views/pages/odrPageTab.html',
                    url: '/orderDetails?:orderNr',
                    controller: 'OrderDisplayDetailsCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/orderDetails.js' ,
                                        'scripts/services/alertService.js']
                                })
                        }
                    }


                })


                /*
				 * .state('main.product_view',{
				 * templateUrl:'views/pages/productView.html',
				 * url:'/viewProduct' })
				 */

                .state('main.product_batch_upload', {
                    templateUrl: 'views/pages/productBatchUpload.html',
                    url: '/productBatchUpload',
                    controller: 'ProductBatchUploadCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/productBatchUpload.js',
                                        'scripts/services/alertService.js'
                                        
                                    ]
                                })
                        }
                    }
                })

               
                .state('main.product', {
                    templateUrl: 'views/pages/product.html',
                    url: '/product?:productId',
                    controller: 'ProductCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/product.js',
                                        'scripts/services/alertService.js'
                                        
                                    ]
                                })
                        }
                    }
                })

               .state('main.productAdd', {
                    templateUrl: 'views/pages/product.html',
                    url: '/product?:productId',
                    controller: 'ProductCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/product.js',
                                        'scripts/services/alertService.js'
                                        
                                    ]
                                })
                        }
                    }
                })
                
               /*
				 * .state('main.product_import_log', { templateUrl:
				 * 'views/pages/productImportLog.html', url: '/productImportLog' })
				 */

                .state('main.system_user_account', {
                    templateUrl: 'views/pages/userAccount.html',
                    url: '/userAccount'
                })

                .state('main.system_brand', {
                    templateUrl: 'views/pages/brand.html',
                    url: '/brand',
                    controller: 'BrandCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                            $ocLazyLoad.load({
                                name: 'ui.grid.resizeColumns'
                            }),
                            $ocLazyLoad.load({
                                name: 'ui.grid.resizeColumns'
                            }),
                            $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),

                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/brand.js' ,
                                        'scripts/services/alertService.js']
                                })
                        }
                    }
                })
                
                
                .state('main.system_category', {
                    templateUrl: 'views/pages/category.html',
                    url: '/category'
                })
                     
               .state('main.myAccount_profile', {
            	templateUrl: 'views/pages/profile.html',
            	url: '/profile',
            	controller: 'ProfileCtrl',
            	resolve: {
            		loadMyFiles: function ($ocLazyLoad) {
            			return $ocLazyLoad.load({
            				name: 'psspAdminApp',
            				files: [
            				        'scripts/controllers/profile.js',
            				        'scripts/services/alertService.js'
            				        ]
            			})
            		}
            	}
            })
                
                .state('main.myAccount_settings', {
                    templateUrl: 'views/pages/settings.html',
                    url: '/settings',
                    controller: 'SettingsCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.uploader',
                                    files: ['pssp/js/uploader.js']
                                }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/setting.js' ,
                                        'scripts/services/alertService.js']
                                })
                        }
                    }
                })
                 
                .state('main.system_list_value', {
                    templateUrl: 'views/pages/listOfValue.html',
                    url: '/listOfValue',
                    controller: 'ListOfValueCtrl',
                    resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                    name: 'psspAdminApp',
                                    files: ['scripts/controllers/listOfValue.js',
                                        'scripts/services/alertService.js'
                                        
                                    ]
                                })
                        }
                    }
                })
                
                
                .state('main.system_dept', {
                	templateUrl: 'views/pages/dept.html',
                	url: '/dept',
                	controller: 'DeptCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'ui.uploader',
                				files: ['pssp/js/uploader.js']
                			}),
                			$ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/dept.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })

                .state('main.cronJob', {
                	templateUrl: 'views/pages/cronJob.html',
                	url: '/cronJob',
                	controller: 'CronJobCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/cronJob.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })
                
                .state('main.deliveryDate', {
                	templateUrl: 'views/pages/deliveryDate.html',
                	url: '/configuration',
                	controller: 'DeliveryDateCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/deliveryDate.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })
                
                // System shop
                .state('main.system_shop', {
                	templateUrl: 'views/pages/systemShop.html',
                	url: '/systemShop',
                	controller: 'SystemShopCtrl',
                	resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),

                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/systemShop.js'
                                    , 'scripts/services/alertService.js']
                            })
                        }
                    }
                })
                
                
                
                 // Eletter
                .state('main.system_eletter', {
                	templateUrl: 'views/pages/eletter.html',
                	url: '/eletterView',
                	controller: 'EletterCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            	}),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }), 
                                $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/eletter.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })
                
                // Product Cat
                .state('main.shop_product_cat', {
                	templateUrl: 'views/pages/productCat.html',
                	url: '/productCat',
                	controller:'ProductCatCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                                $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/productCat.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
                
                 // Product Cat add
                .state('main.shop_product_cat_add', {
                	templateUrl: 'views/pages/productCatAdd.html',
                	url: '/productCatAdd?:id',
                	controller: 'ProductCatAddCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                                $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/productCatAdd.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
                
                .state('main.system_eletter_add', {
                	templateUrl: 'views/pages/eletterAdd.html',
                	url: '/eletterAdd',
                	controller: 'EletterAddCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            	}),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }), 
                				$ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/eletterAdd.js',
                				        'scripts/services/alertService.js',
                				        'plugins/ckeditor/ckeditor.js',
                				        'scripts/directives/ckeditor/initckedit.js'
                				]
                			})
                		}
                	}
                })
                
                 // Product Cat
                .state('main.my_shop_setting', {
                	templateUrl: 'views/pages/deliveryDate.html',
                	url: '/configuration',
                	controller: 'DeliveryDateCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/deliveryDate.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
                
                 // Product Cat
                .state('main.my_shop_product_cat', {
                	templateUrl: 'views/pages/deliveryDate.html',
                	url: '/configuration',
                	controller: 'DeliveryDateCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/deliveryDate.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })
                
                // discount
                .state('main.my_shop_discount', {
                	templateUrl: 'views/pages/deliveryDate.html',
                	url: '/configuration',
                	controller: 'DeliveryDateCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/deliveryDate.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
                
                  // Role Function
                .state('main.role_function', {
                	templateUrl: 'views/pages/roleFunc.html',
                	url: '/roleFunc',
                	controller: 'RoleFuncCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/roleFunc.js',
                				        'scripts/services/alertService.js'
                				        
                				        ]
                			})
                		}
                	}
                })
                
                // Role Function Add / Edit
                .state('main.role_function_add', {
                	templateUrl: 'views/pages/roleFuncAdd.html',
                	url: '/roleFuncAdd',
                	controller: 'RoleFuncAddCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/roleFuncAdd.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                })
                .state('main.sys_function', {
                	templateUrl: 'views/pages/sysFunc.html',
                	url: '/sysFunc',
                	controller: 'SysFuncCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/sysFunc.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                }).state('main.shop_basic_info', {
                	templateUrl: 'views/pages/systemShopAdd.html',
                	url: '/systemShopAdd?:shopId?:shopName?:state',
                	controller: 'ShopBasicInfoCtrl',
                	resolve: {
                        loadMyFiles: function ($ocLazyLoad) {
                            return $ocLazyLoad.load({
                                name: 'ui.uploader',
                                files: ['pssp/js/uploader.js']
                            }),
                                $ocLazyLoad.load({
                                name: 'psspAdminApp',
                                files: ['scripts/controllers/shopBasicInfo.js'
                                    , 'scripts/services/alertService.js'
                                    ,'bootstrap/css/bootstrap.css']
                            })
                        }
                    }
                }).state('main.shop_ip', {
                	templateUrl: 'views/pages/systemShopAdd.html',
                	url: '/systemShopAdd?:shopId?:shopName',
                	controller: 'ShopIpCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopIp.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                }).state('main.shop_user', {
                	templateUrl: 'views/pages/systemShopAdd.html',
                	url: '/systemShopAdd?:shopId?:shopName',
                	controller: 'ShopUserCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopUser.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                }).state('main.shop_setup_user', {
                	templateUrl: 'views/pages/shopSetting.html',
                	url: '/shopSetup�?:my_type',
                	controller: 'ShopUserCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopUser.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                }).state('main.shop_category', {
                	templateUrl: 'views/pages/systemShopAdd.html',
                	url: '/systemShopAdd?:shopId?:shopName',
                	controller: 'ShopCategoryCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopCategory.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}
                }).state('main.shop_contract', {
                	templateUrl: 'views/pages/systemShopAdd.html',
                	url: '/systemShopAdd?:shopId?:shopName',
                	controller: 'ShopContractCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopContract.js',
                				        'scripts/services/alertService.js'
                				        ]
                			})
                		}
                	}

                }).state('main.shop_setting', {
                	templateUrl: 'views/pages/shopSetting.html',
                	url: '/shopSetting',
                	controller: 'ShopSettingCtrl',
                	resolve: {
                		loadMyFiles: function ($ocLazyLoad) {
                			return $ocLazyLoad.load({
                                name: 'ui.grid.selection',
                                files: [
                                    'bower_components/angular-ui-grid/ui-grid.min.css',
                                    'bower_components/angular-ui-grid/ui-grid.min.js'
                                ]
                            	}),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.grid.resizeColumns'
                                }),
                                $ocLazyLoad.load({
                                    name: 'ui.uploader',
                                    files: ['pssp/js/uploader.js']
                                }),
                				$ocLazyLoad.load({
                				name: 'psspAdminApp',
                				files: ['scripts/controllers/shopSetting.js',
                				        'scripts/services/alertService.js',
                				        'scripts/directives/ckeditor/ckeditor.js',
                				        'scripts/directives/ckeditor/initckedit.js'
                				]
                			})
                		}
                	}
                })
                .state('main.userManager', {
					templateUrl: 'views/pages/power/userManager.html',
					params : {'userInfo':null},
					url: '/userManager',
					controller: 'UserManagerCtrl',
					resolve: {
						loadMyFiles: function ($ocLazyLoad) {
							return $ocLazyLoad.load({
								name: 'psspAdminApp',
								files: [ 'scripts/controllers/power/userManager.js' ]
							});
						}
					}
				})
				            
				.state('main.userManager_add', {
					templateUrl: 'views/pages/power/userManagerAdd.html',
					url: '/userManagerAdd?:userId?:viewStatu',
					controller: 'UserManagerAddCtrl',
					resolve: {
						loadMyFiles: function ($ocLazyLoad) {
							return $ocLazyLoad.load({
								name: 'psspAdminApp',
								files: [ 'scripts/controllers/power/userManagerAdd.js' ]
							});
						}
					}
				})
                
                .state('main.menu_manager', {
            		templateUrl: 'views/pages/power/menuManager.html',
            		url: '/menuManager',
            		controller: 'MenuManagerCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: [ 'scripts/controllers/power/menuManager.js' ]
            				});
            			}
            		}
            	}).state('main.menu_add', {
            		templateUrl: 'views/pages/power/menuAdd.html',
            		url: '/menuAdd?:id?:state',
            		controller: 'menuAddCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: [ 'scripts/controllers/power/menuAdd.js' ]
            				});
            			}
            		}
            	}).state('main.roleManager', {
            		templateUrl: 'views/pages/power/roleManager.html',
            		url: '/roleManager',
            		controller: 'RoleManagerCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            						name: 'psspAdminApp',
            						files: [ 'scripts/controllers/power/roleManager.js' ]
            				});
            			}
            		}
            	}).state('main.roleManager_add', {
            		templateUrl: 'views/pages/power/roleManagerAdd.html',
            		url: '/roleManagerAdd?:roleId?:state',
            		controller: 'RoleManagerAddCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: [ 'scripts/controllers/power/roleManagerAdd.js' ]
            					});
            				}
            			}
            	}).state('main.mall', {
            		templateUrl: 'views/pages/mall/mall.html',
            		url: '/mall',
            		controller: 'MallCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: [ 'scripts/controllers/mall/mall.js' ]
            					});
            				}
            			}
            	}).state('main.mall_add', {
            		templateUrl: 'views/pages/mall/mallAdd.html',
            		url: '/mallAdd?:id?:state',
            		controller: 'MallAddCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: ['scripts/controllers/mall/mallAdd.js']
            				});
            			}
            		}
            	}).state('main.sys_parm', {
            		templateUrl:'views/pages/parm/parm.html',
            		url:'/parm?:mallId?:segment',
            		controller:'ParmCtrl',
            		resolve: {
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: ['scripts/controllers/parm/parm.js']
            				});
            			}
            		}
            	}).state('main.sys_parm_add', {
            		templateUrl:'views/pages/parm/parmAdd.html',
            		url:'/parmAdd?:id?state',
            		controller:'ParmAddCtrl',
            		resolve:{
            			loadMyFiles: function ($ocLazyLoad) {
            				return $ocLazyLoad.load({
            					name: 'psspAdminApp',
            					files: ['scripts/controllers/parm/parmAdd.js']
            				});
            			}
            		}
            	}) 
        }])
            .factory('T', ['$translate', function($translate) {
    var T = {
        T:function(key) {
            if(key){
                return $translate.instant(key);
            }
            return key;
        }
    };
    return T;
}])

    .run(function ($rootScope, $state, localStorageService, userService) {
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState, fromStateParams) {
            if (toState.name.indexOf("login") == -1 && toState.name.indexOf("send") == -1 && toState.name.indexOf("forget") == -1) {
            	userService.getCurrentUser().then(function(data) { 
            		 
            		if (data == null || data == '') {
                        event.preventDefault();
                        $state.go("login");
                    } else {
                    	console.log('======');
                        	localStorageService.set("id", data.id);
                            localStorageService.set("userId", data.userId);
                            localStorageService.set("supplierId", data.supplierId);
                            localStorageService.set("userName", data.userName);
                            localStorageService.set("userRole", data.userRole);
                            localStorageService.set("shopId", data.shopId);
                            localStorageService.set("mallId", data.mallId);
                            localStorageService.set("user", data);
                            
                            
                            /*
							 * localStorageService.set("supplierName",
							 * data.shop.shopName);
							 */
                    }
            	}, function(data) {
            		// cannot get user info, go to login page
// console.log('getCurrentUser error');
                	event.preventDefault();
                    $state.go("login");
                }); 
            }
            
        });
        
    });



	

    