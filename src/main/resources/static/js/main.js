var kosarkaskiSavez = angular.module('kosarkaskiSavez', ['ngRoute']);

kosarkaskiSavez.config(function($routeProvider) {
    $routeProvider
        .when("/", {
          templateUrl : '/templates/clubs.html'
        })
        .when("/clubs", {
          templateUrl : '/templates/clubs.html'
        })
        .when("/club/:id", {
          templateUrl : '/templates/club.html'
        })
        .when("/player/:id", {
          templateUrl : '/templates/player.html'
        })
        .otherwise({
         redirectTo: '/'
       });
});

/*
        APLIKACIJA KOSARKASKI SAVEZ
*/

// CLUBS CONTROLLER
kosarkaskiSavez.controller('clubsCtrl', function($scope, $http, $location){

    // BROJAC STRANICE
    $scope.brojacStranice = 0;

    // INICIJALNO PRAZAN CLUB OBJEKAT
    $scope.club = {
        "name":"",
        "players":[],
    };

    var ucitajKlubove = function(){

        // CONFIG OBJEKAT MORA BITI UNUTAR FUNKCIJE
        var config = {'params':{'page':$scope.brojacStranice}};

        if($scope.findClub && $scope.findClub.name) {
            config.params.name = $scope.findClub.name;
        }

        $http.get('/api/clubs', config).then(function(resp){
            $scope.clubs = resp.data;
            $scope.totalPages = Number(resp.headers().totalpages);
            console.log($scope.totalPages);
        });

    };

    ucitajKlubove();

    $scope.changePage = function(i) {
        if($scope.brojacStranice >= 0) {
            $scope.brojacStranice += i;
        }
        ucitajKlubove();
    };

    $scope.createClub = function() {
        $http.post('/api/clubs', $scope.club).then(ucitajKlubove);
    };

    $scope.editClub = function(club) {
        $location.path('/club/'+club.clubID);
    };

    $scope.deleteClub = function(id) {
        $http.delete('/api/clubs/'+id).then(ucitajKlubove);
    };

    $scope.search = ucitajKlubove;

});

// CLUB CONTROLLER
kosarkaskiSavez.controller('clubCtrl', function($scope, $http, $location, $routeParams){

    $scope.player = {
        "firstname":"",
        "lastname":"",
        "club":{}
    };

    var getClub = function() {
        $http.get('/api/clubs/'+$routeParams.id).then(function(resp){
            $scope.club = resp.data;
            $scope.brojIgraca = $scope.club.players.length;
            console.log($scope.brojIgraca);
        });
    };

    getClub();

    $scope.editClub = function(){
        $http.put('/api/clubs/'+$scope.club.clubID, $scope.club).then(getClub);
    };

    $scope.editPlayer = function(playerID) {
        $location.path('/player/'+playerID);
    };

    $scope.addPlayer = function(clubID) {
        $scope.player.club.clubID = clubID;
        $http.post('/api/players', $scope.player).then(function(){
            $scope.player = {};
            getClub();
        });
    };

    $scope.deletePlayer = function(id) {
        $http.delete('/api/players/'+id).then(getClub);
    };

});

// PLAYER CONTROLLER

kosarkaskiSavez.controller('playerCtrl', function($scope, $http, $location, $routeParams){

    var getPlayer = function(){
        $http.get('/api/players/'+$routeParams.id).then(function(resp){
            $scope.player = resp.data;
        });
    };

    getPlayer();

    $scope.editPlayer = function(){
        $http.put('/api/players/'+$scope.player.playerID, $scope.player).then(function(){
            $location.path('/club/'+$scope.player.club.clubID);
        });
    };

    $scope.cancel = function (){
        $location.path('/club/'+$scope.player.club.clubID);
    };

});
