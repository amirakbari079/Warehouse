app.controller('CategoryListController', ['$scope', 'CategoryService', 'ngTableParams', '$location', '$timeout', 'dialogs',
    function ($scope, CategoryService, ngTableParams, $location, $timeout, dialogs) {
        $scope.edit = function (code) {
            dialogs.create('app/components/category/category.edit.html', 'CategoryEditController', {
                code: code
            }, {}, '').result.then(resolveCallback, rejectCallback);

            function resolveCallback() {
                setTimeout(function () {
                    $scope.tableParams.reload()
                    console.log("promise resolved,.3s")
                },300)
            }

            function rejectCallback() {
                setTimeout(function(){$scope.tableParams.reload()
                console.log("promise rejected,.3s")}
                    ,300)

            }
        }
        $scope.delete=function (code){
            CategoryService.deleteCategory({'code': code});
            setTimeout(function (){
                $scope.tableParams.reload();
            },300)

        }

        $scope.display=function (code){
            // $location.assign("category/display/"+code);

            $location.path("/category/display/"+code);
            $location.$$search="";
            $location.replace();
            $location.$$compose();

            console.log($location.$$absUrl);
            console.log($location.$$url);
            // $location.path("#");
            // $location.replace();
        }

        $scope.add=function (){
            dialogs.create('app/components/category/category.add.html', 'CategoryEditController', {
                subject: 'subject'
            }, {}, '').result.then(resolveCallback, rejectCallback);

            function resolveCallback() {
                setTimeout(function () {
                    $scope.tableParams.reload()
                    console.log("promise resolved,.3s")
                },300)
            }

            function rejectCallback() {
                setTimeout(function(){$scope.tableParams.reload()
                        console.log("promise rejected,.3s")}
                    ,300)

            }

        }

        $scope.searchParams = $location.search();

        $scope.searchParams.orderBy == null || $scope.searchParams.orderBy == "" ? $scope.searchParams.orderBy = "subject" : $scope.searchParams.orderBy = $location.search().orderBy;
        $scope.searchParams.sortDirection == null || $scope.searchParams.sortDirection == "" ? $scope.searchParams.sortDirection = "asc" : $scope.searchParams.sortDirection = $location.search().sortDirection;
        $scope.searchParams.pageNumber == null || $scope.searchParams.pageNumber == "" ? $scope.searchParams.pageNumber = "1" : $scope.searchParams.pageNumber = $location.search().pageNumber;
        // $scope.searchParams.pageSize==null || $scope.searchParams.pageSize==""?$scope.searchParams.pageSize="10":$scope.searchParams.pageSize=$location.search().pageSize;
        if ($scope.searchParams.pageSize == "10" || $scope.searchParams.pageSize == "25" || $scope.searchParams.pageSize == "50" || $scope.searchParams.pageSize == "100") {
            $scope.searchParams.pageSize = $location.search().pageSize;
        } else {
            $scope.searchParams.pageSize = "10";
        }


        $scope.search = function () {
            console.log($scope.searchParams);
            $scope.tableParams.reload();
        }

        var sortingKey = $scope.searchParams.orderBy;
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
                    $scope.searchParams.orderBy = Object.keys(params.$params.sorting)[0];
                    $scope.searchParams.sortDirection = Object.values(params.$params.sorting)[0];


                    // Update the URL with the current table parameters
                    $location.$$search = $scope.searchParams;
                    $location.$$compose();

                    //Use category service to fetch data from back-end
                    CategoryService.search($scope.searchParams).$promise.then(onFulfillment, onRejection);

                    function onFulfillment(searchResponse) {
                        //Update internal parameters of ng-table after arrival of new data from back-end, total count of data is used for rendering pagination
                        console.log(searchResponse)
                        params.total(searchResponse.totalCount);
                        var data = searchResponse.items.slice((searchResponse.pageSize - 1) * searchResponse.pageNumber, searchResponse.pageSize * searchResponse.pageNumber);
                        $defer.resolve(data);//Use defered API to provide category items to ng-table
                    }

                    function onRejection(reason) {
                        // Flash.create('danger', 'Problem occurred while searching categories')
                    }
                }//end of getData
            }
        );//end of ngTableParams


    }])

app.controller('categoryDisplayController', ['$scope', '$route', 'CategoryService', function ($scope, $route, CategoryService) {
    var code = $route.current.params.code;
    console.log(code);
    CategoryService.loadByCode({code: code}).$promise.then(onFulfillment, onRejection);

    // $scope.searchParams=$location.search();
    function onFulfillment(searchResponse) {
        //Update internal parameters of ng-table after arrival of new data from back-end, total count of data is used for rendering pagination
        $scope.subject = searchResponse.subject;
        $scope.code = searchResponse.code;
    }

    function onRejection(reason) {
        // Flash.create('danger', 'Problem occurred while searching categories')
        $scope.code = searchResponse.code;
        alert("nooooooo")

    }
}])


app.controller('CategoryEditController', ['$scope', 'data', '$modalInstance', 'CategoryService', function ($scope, data, $modalInstance, CategoryService) {
    $scope.code = data.code;
    $scope.ok = function () {
            CategoryService.editSubject({code: $scope.code}, {subject: $scope.subject}).$promise.then(onFulfillment, onRejection);
            function onFulfillment() {
                console.log("ok")
            }
            function onRejection() {
                alert("nana")
            }
        $modalInstance.close({someImportantData: $scope.subject});
    }

    $scope.cancel = function () {
        $modalInstance.dismiss();
    }
    // dialogs.create('app/components/category/category.edit.html','CategoryEditController',{data: topass,anotherVar: 'value'},{},'');

}])

app.controller('CategoryAddController', ['$scope', 'data', '$modalInstance', 'CategoryService', function ($scope, data, $modalInstance, CategoryService) {
    $scope.ok = function () {
        CategoryService.saveCategory(subject, {subject: $scope.subject}).$promise.then(onFulfillment, onRejection);
        function onFulfillment() {
            console.log("ok")
        }
        function onRejection() {
            alert("nana")
        }
        $modalInstance.close({someImportantData: $scope.subject});
    }

    $scope.cancel = function () {
        $modalInstance.dismiss();
    }
    // dialogs.create('app/components/category/category.edit.html','CategoryEditController',{data: topass,anotherVar: 'value'},{},'');

}])



