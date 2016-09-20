'use strict';

var myApp = angular.module('myApp', [
    'ngRoute',
    'ngMaterial',
    'ngMessages'
]).config(['$locationProvider', '$routeProvider', '$mdIconProvider',
    function ($locationProvider, $routeProvider, $mdIconProvider) {
        $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('/');
    }]);

myApp.controller('mainCtrl', [
    '$scope', '$http', '$location', '$window', '$mdToast',
    function ($scope, $http, $location, $window, $mdToast) {
        $scope.user = {
            j_username: "",
            j_password: ""
        };

        $scope.reguser = {
            username: "",
            name: "",
            password: "",
            password2: ""
        };

        $scope.sign = false;
        // $scope.Login = function sendData(value, $location) {
        //
        //     $http({
        //         url: 'http://localhost:8080/pasman/' + "j_security_check?j_username=" + value.j_username + "&j_password=" + value.j_password,
        //         method: "POST"
        //     })
        //         .then(function (response) {
        //                 // success
        //                 $window.location.href = 'http://localhost:8080/pasman/api/getAll';
        //                 //$location.path($location.absUrl().split('?')[0]);
        //             },
        //             function (response) { // optional
        //                 // failed
        //                 $window.location.href = 'http://localhost:8080/pasman/error.html';
        //             });
        // };

        $scope.Login2 = function sendData(value) {
            $http({
                method: 'POST',
                url: "j_security_check",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: value
            }).success(function (data) {
                //  console.log("data");
                if (data.match("Something wrong")) {
                    // alert("BAAD");
                    $mdToast.show({
                        hideDelay: 3000,
                        position: 'bottom right',
                        controller: 'ToastCtrl',
                        templateUrl: 'res/Toast-template.html'
                    });
                    //$window.location.href = $location.absUrl() + 'j_security_check';
                } else {
                    //alert("yeah");
                    $window.location.href = $location.absUrl();
                }
            });
        };
        $scope.SignUP = function () {
            $scope.sign = !$scope.sign;
        };
        $scope.clearData = function () {
            $scope.reguser = {
                username: "",
                name: "",
                password: "",
                password2: ""
            };
        };
        $scope.reg = function () {
            console.log($scope.reguser);
        };

        $scope.isCanReg = function () {
            // return $scope.reguser.name.length<3?true:
            //     $scope.reguser.username.length<5?true:
            //         $scope.reguser.password.length<4?true:false;

            // return $scope.reguser.password2===$scope.reguser.password?false:true;
            return false;
        }
    }
]);

myApp.controller('ToastCtrl', function ($scope, $mdToast, $mdDialog) {
    // this.isDlgOpen=true;
    $scope.closeToast = function () {
        //  if (this.isDlgOpen) return;
        $mdToast
            .hide()
            .then(function () {
                // this.isDlgOpen = false;
            });
    }
});