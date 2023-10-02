
app.controller('CategoryListController', ['$scope', 'CategoryService', 'ngTableParams', '$location','$timeout', function ($scope, CategoryService, ngTableParams, $location,$timeout) {
    // queryParams = $location.search();

    $scope.searchParams=$location.search();

    $scope.search = function () {
        console.log($scope.searchParams);
        $scope.tableParams.reload();
    }

    var sortingKey =  $scope.searchParams.orderBy;
    var sortingValue = $scope.searchParams.sortDirection;
    var sortingObject = {};
    sortingObject[sortingKey] = sortingValue;

    // Ng Table ....
    $scope.tableParams = new ngTableParams(
        {
            page: $scope.searchParams.pageNumber,
            count: $scope.searchParams.pageSize,
            sorting: sortingObject
        },//Initial values show page 1 with pageSize equal to 10 on first load

        {
            getData: function ($defer, params) {
                // console.log(params.$params)
                //Extract searchParams used by back-end from internal parameters of ng-table
                $scope.searchParams.pageNumber = params.$params.page;
                $scope.searchParams.pageSize = params.$params.count;
                $scope.searchParams.orderBy=Object.keys(params.$params.sorting)[0];
                $scope.searchParams.sortDirection=Object.values(params.$params.sorting)[0];


                // Update the URL with the current table parameters
                $location.$$search=$scope.searchParams;
                $location.$$compose();

                //Use category service to fetch data from back-end
                CategoryService.search($scope.searchParams).$promise.then(onFulfillment, onRejection);

                function onFulfillment(searchResponse) {
                    //Update internal parameters of ng-table after arrival of new data from back-end, total count of data is used for rendering pagination
                    params.total(searchResponse.totalCount);

                    $defer.resolve(searchResponse.items);//Use defered API to provide category items to ng-table
                }

                function onRejection(reason) {
                    // Flash.create('danger', 'Problem occurred while searching categories')
                }
            }//end of getData
        }
    );//end of ngTableParams


}])

app.controller('categoryDisplayController',['$scope','$route','CategoryService',function ($scope,$route,CategoryService){
    var code = $route.current.params.code;
    console.log(code);
    CategoryService.loadByCode({ code: code }).$promise.then(onFulfillment, onRejection);

    // $scope.searchParams=$location.search();
    function onFulfillment(searchResponse) {
        //Update internal parameters of ng-table after arrival of new data from back-end, total count of data is used for rendering pagination
        $scope.subject=searchResponse.subject;
        $scope.code=searchResponse.code;
        alert("okkkkkkkkkk")
    }

    function onRejection(reason) {
        // Flash.create('danger', 'Problem occurred while searching categories')
        $scope.code=searchResponse.code;
        alert("nooooooo")

    }
}])


