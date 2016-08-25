'use strict';

var myApp = angular.module('myApp', [
    'ngRoute',
    'ngMaterial'
]).config(['$locationProvider', '$routeProvider', '$mdIconProvider',
    function ($locationProvider, $routeProvider, $mdIconProvider) {

    }]);

myApp.controller('mainCtrl', [
    '$scope', '$http', '$location', '$window',
    function ($scope, $http, $location, $window) {
        $scope.user = {
            email: "",
            password: ""
        };
        $scope.Login = function sendData(value, $location) {

            $http({
                url: 'http://localhost:8080/pasman/' + "j_security_check?j_username=" + value.email + "&j_password=" + value.password,
                method: "POST"
            })
                .then(function (response) {
                        // success
                        $window.location.href = 'http://localhost:8080/pasman/api/getAll';
                        //$location.path($location.absUrl().split('?')[0]);
                    },
                    function (response) { // optional
                        // failed
                        $window.location.href = 'http://localhost:8080/pasman/error.html';
                    });
        }
    }
]);