'use strict';

angular.module('bookme')

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/restClient', {
    templateUrl: 'app/restClient/restClient.html',
    controller: 'restClientCtrl'
  });
}])

.controller('restClientCtrl', function($scope, $location, $http) {
    $scope.client = {};
    $scope.response = {};

    $scope.fireHttpRequest = function() {
        var parts = $scope.client.url.split("://");
        if (parts.length === 1) {
            $scope.client.url = "http://" + $scope.client.url
        }

        $http({
            method: $scope.client.method,
            url: $scope.client.url
        }).then(function(response) {
            $scope.response = response;
        }, function(err) {
            $scope.response = err;
        })
    };
});