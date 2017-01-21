'use strict';

angular.module('bookme')

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/config', {
    templateUrl: 'app/config/config.html',
    controller: 'configCtrl'
  });
}])

.controller('configCtrl', function($scope, $location) {
});