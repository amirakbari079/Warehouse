app.controller('CategoryListController', ['$scope', 'CategoryService', 'ngTableParams', function ($scope, CategoryService, ngTableParams) {

    $scope.search = function () {
        CategoryService.search($scope.searchParams)
            .$promise.then(onFulfillment, onRejection);

        function onFulfillment(responseBody) {

            $scope.data = responseBody.items;

            console.log("successful response received from back-end with body: ", responseBody);

        }

        function onRejection(httpResponse) {
            console.log("unsuccessful response recived from back-end with http response", httpResponse)
        }
    }

    // Ng Table ....
        $scope.tableParams = new ngTableParams(
            {page: 1, count: 10},//Initial values show page 1 with pageSize equal to 10 on first load
            {
                getData: function ($defer, params) {
                    //Extract searchParams used by back-end from internal parameters of ng-table
                    $scope.searchParams.pageSize = params.$params.count;
                    $scope.searchParams.pageNumber = params.$params.page;
                    $scope.searchParams.orderBy=Object.keys(params.$params.sorting)[0];
                    $scope.searchParams.sortDirection=Object.values(params.$params.sorting)[0]

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




