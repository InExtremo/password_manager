'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
    'ngRoute',
    'ngMaterial'
]).config(['$locationProvider', '$routeProvider', '$mdIconProvider',
    function ($locationProvider, $routeProvider, $mdIconProvider) {
        /* $locationProvider.hashPrefix('!');

         $routeProvider.otherwise({redirectTo: '/view1'});*/
        // $routeProvider
        //     // .when('/ff', {
        //     //     templateUrl: 'index.html',
        //     //     controller: 'mainCtrl',
        //     //     controllerAs: 'book'
        //     // })
        //     .when('/container', {
        //     templateUrl: 'templ/container.html',
        //     controller: 'ChapterCtrl',
        //     controllerAs: 'chapter'
        // });

        // $locationProvider.html5Mode(true);
        $mdIconProvider
            .defaultIconSet("assets/svg/avatars.svg", 128)
            .icon("menu", "./assets/svg/menu.svg", 24)
            .icon("share", "./assets/svg/share.svg", 24)
            .icon("google_plus", "./assets/svg/google_plus.svg", 512)
            .icon("hangouts", "./assets/svg/hangouts.svg", 512)
            .icon("twitter", "./assets/svg/twitter.svg", 512)
            .icon("phone", "./assets/svg/phone.svg", 512);
    }]
);

myApp.service('accvalues', function () {
    this.dataFromrest = [];
});
myApp.controller('bookmarksCtrl', [
    '$scope', '$http', '$location',
    function ($scope, $http, $location) {

    }
]);
myApp.controller('notesCtrl', [
    '$scope', '$http', '$location',
    function ($scope, $http, $location) {

    }
]);
myApp.controller('accountCtrl', [
    '$scope', '$http', '$location', '$mdBottomSheet', '$log', 'accvalues',
    function ($scope, $http, $location, $mdBottomSheet, $log, accvalues) {
        // $scope.values = [
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     },
        //     {
        //         name: "google",
        //         link: "google.com",
        //         login: "login2",
        //         pass: "pass2"
        //     },
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     },
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     },
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     },
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     },
        //     {
        //         name: "somename",
        //         link: "vk.com",
        //         login: "login",
        //         pass: "pass"
        //     }
        // ]

        // function openButtonSheet() {
        //     $mdBottomSheet.show({
        //         contorller:SheetCtrl,
        //         templateUrl:'templ/botsh.html',
        //         parent:angular.element(document.querySelector('#content'))
        //     });
        //
        //     function SheetCtrl() {
        //         this.items=[
        //             {name:'phone',                icon: 'phone',                icon_url: 'svg/phone.svg'},
        //             {name:'phone2',               icon: 'phone2',               icon_url: 'svg/phone2.svg'}
        //         ];
        //         this.performAction = function (action) {
        //             $mdBottomSheet.hide();
        //         };
        //     }
        // }$scope.openButtonSheet = openButtonSheet;
        $scope.newitem = {
            //id: 0,
            description: "",
            link: "",
            login: "",
            name: "",
            password: ""
        };


        function resetItem() {
            $scope.newitem = {

                description: "",
                link: "",
                login: "",
                name: "",
                password: ""
            };
            // return false;
        }

        $scope.editval = false;

        function togleitem(item) {
            return item ? false : true;
            // $log(item + ' change')
        }

        function addVal(someval) {
            $http({
                url: 'http://localhost:8080/pasman/api/',
                method: "POST",
                data: someval,
                headers: {'Content-Type': 'application/json'}
            })
                .then(function (response) {
                        // success
                        $scope.datafromrest.push(response.data);
                        resetItem();
                        $scope.isAdd = false;
                    },
                    function (response) { // optional
                        // failed
                        alert("Problem with add data");
                    });
        }

        function deleteThis(item, values) {
            values.splice(values.indexOf(item), 1);
        }

        //add menu
        $scope.isAdd = false;

        $scope.init = function () {
            $http.get('http://localhost:8080/pasman/api/getAll').success(function (data) {
                $scope.datafromrest = data;
            }).error(function (data) {
                alert("Error");
            });

        };



        $scope.addVal = addVal;
        $scope.togleitem = togleitem;
        $scope.$log = $log;
        $scope.deleteThis = deleteThis;
        $scope.resetItem = resetItem;
    }


]).config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('dark-grey').backgroundPalette('grey').dark();
    $mdThemingProvider.theme('dark-orange').backgroundPalette('orange').dark();
    $mdThemingProvider.theme('dark-purple').backgroundPalette('deep-purple').dark();
    $mdThemingProvider.theme('dark-blue').backgroundPalette('blue').dark();

    // Configure a dark theme with primary foreground yellow
    $mdThemingProvider.theme('docs-dark', 'default')
        .primaryPalette('yellow')
        .dark();

});
myApp.controller('cardCtrl', [
    '$scope', '$http', '$location',
    function ($scope, $http, $location) {

    }
]);
myApp.config([
    '$routeProvider', '$locationProvider',
    function ($routeProvide, $locationProvider) {
        $routeProvide
            .when('/bookmarks', {
                templateUrl: 'pasman/page/app/templ/bookmarks.html',
                controller: 'bookmarksCtrl'
            }).when('/notes', {
            templateUrl: 'pasman/page/app/templ/notes.html',
            controller: 'notesCtrl'
        }).when('/account', {
            templateUrl: 'pasman/page/app/templ/accounts.html',
            controller: 'accountCtrl'
        }).when('/card', {
            templateUrl: 'pasman/page/app/templ/card.html',
            controller: 'cardCtrl'
        }).when('/login.html', {
            templateUrl: 'pasman/page/app/templ/login.html',
            controller: 'login'
        })
            .otherwise({
                redirectTo: '/pasman/page/app/'
            });

        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }
]);
myApp.controller('mainCtrl', ['$scope', '$mdSidenav', '$log', '$timeout', 'accvalues', '$http',
    function ($scope, $mdSidenav, $log, $timeout, accvalues, $http) {


        $scope.types = [
            {
                name: 'account',
                avatar: "assets/type/login.svg",
                link: 'account'
            },
            {
                name: 'card',
                avatar: "assets/type/card.svg",
                link: 'card'
            },
            {
                name: 'notes',
                avatar: "assets/type/notes.svg",
                link: 'notes'
            },
            {
                name: 'bookmarks',
                avatar: "assets/type/bookmarks.svg",
                link: 'bookmarks'
            }
        ];

        $scope.toggleLeft = buildDelayedToggler('left');

        function debounce(func, wait, context) {
            var timer;
            return function debounced() {
                var context = $scope,
                    args = Array.prototype.slice.call(arguments);
                $timeout.cancel(timer);
                timer = $timeout(function () {
                    timer = undefined;
                    func.apply(context, args);
                }, wait || 10);
            };
        }

        /**
         * Build handler to open/close a SideNav; when animation finishes
         * report completion in console
         */
        function buildDelayedToggler(navID) {
            return debounce(function () {
                // Component lookup should always be available since we are not using `ng-if`
                $mdSidenav(navID)
                    .toggle()
                    .then(function () {
                        $log.debug("toggle " + navID + " is done");
                    });
            }, 200);
        }

        $scope.update = function () {
            $http.get('http://localhost:8080/pasman/api/getAll').success(function (data) {
                $scope.datafromrest = data;
            }).error(function (data) {
                alert("Error");
            });
        };

    }]
);