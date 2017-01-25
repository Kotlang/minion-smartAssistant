angular.module('APIFactory', []).factory("API", function($http, $q) {
    return {
        getTeam: function(chatId, userId) {
            var deferred = $q.defer();

            $http.get("/flockapi/members?chatId=" + chatId + "&userId=" + userId)
                .then(function(response) {
                    deferred.resolve(response.data);
                }, function(err) { deferred.reject(err); })

            return deferred.promise;
        }
    };
});