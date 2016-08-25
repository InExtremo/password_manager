var myApp = angular.module("myApp", []);
myApp.controller("RegisterCtrl", function ($scope) {
    $scope.cancel = function () {
        window.location = "./main.html";
    }
});
myApp.controller("LoginCtrl", function ($scope) {
    $scope.cancel = function () {
        window.location = "./main.html";
    }
});
myApp.controller("MainCtrl", function ($scope) {
    $scope.signup = function () {
        window.location = "./signup.html";
    }
    $scope.login = function () {
        window.location = "./login.html";
    }
});

// myApp.directive("validPasswordC", function() {
//   return {
//     require: 'ngModel',
//     scope: {

//       reference: '=validPasswordC'

//     },
//     link: function(scope, elm, attrs, ctrl) {
//       ctrl.$parsers.unshift(function(viewValue, $scope) {

//         var noMatch = viewValue != scope.reference
//         ctrl.$setValidity('noMatch', !noMatch);
//         return (noMatch)?noMatch:!noMatch;
//       });

//       scope.$watch("reference", function(value) {;
//         ctrl.$setValidity('noMatch', value === ctrl.$viewValue);

//       });
//     }
//   }
// });
myApp.directive('pwCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            var firstPassword = '#' + attrs.pwCheck;
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    var v = elem.val() === $(firstPassword).val();
                    ctrl.$setValidity('pwmatch', v);
                });
            });
        }
    }
}]);
 