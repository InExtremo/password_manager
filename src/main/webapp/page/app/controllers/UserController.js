// /**
//  * Created by Max on 06.08.2016.
//  */
// (function () {
//     angular.module('users').controller('UserController',UserController)
//
// function  UserController(userService,$mdSidenav,$mdBottom) {
//     var self = this;
//
//     self.selected = null;
//     self.users = [];
//     self.selectedUser = selectedUser;
//     self.togleList = toggleUserList;
//     self.share = share;
//
//     userService
//         .loadAllUsers().then(function (users) {
//         self.users = [].concat(users);
//         self.selected = users[0];
//     });
//
//
//     function toggleUserList() {
//         $mdSidenav('left').toggle();
//     }
//
//
//
//     }
// })();