'use strict';

angular.module('bookme')

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/ooo', {
    templateUrl: 'app/ooo/ooo.html',
    controller: 'oooCtrl'
  });
}])

.controller('oooCtrl', function($scope, API, $location) {
    var flockValidationToken = $location.search().flockValidationToken;
    $scope.team = [];

    API.getTeam(flockValidationToken).then(function(members) { $scope.team = members; });
});