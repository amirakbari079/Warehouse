categoryModule.controller('CategoryListController', ['$scope', 'CategoryService', 'ngTableParams', '$location', '$timeout', 'dialogs', 'Flash',
    function ($scope, CategoryService, ngTableParams, $location, $timeout, dialogs, Flash) {
        console.log($location)
        $scope.$on('$routeUpdate', function () {
            // Update the variable here
            console.log("O-------------OO")
            debugger
            $scope.tableParams.reload();

        });

        $scope.flashAlert = function (type, message) {
            Flash.create(type, message, 2500, {class: 'custom-class', id: 'custom-id'}, true);
        }
        document.addEventListener("keydown", function (event) {
            if (event.key === "Enter")
                $scope.tableParams.reload()
        });
        $scope.edit = function (code) {
            var isEdit = true;
            dialogs.create('app/components/category/category.edit.html', 'CategoryEditController', {
                code: code,
                isEditMode: isEdit
            }, {}, '').result.then(resolveCallback, rejectCallback);

            function resolveCallback() {
                setTimeout(function () {
                    $scope.tableParams.reload()
                    console.log("promise resolved,.3s")
                }, 300)
            }

            function rejectCallback() {
                setTimeout(function () {
                        $scope.tableParams.reload()
                        console.log("promise rejected,.3s")
                    }
                    , 300)

            }
        }
        $scope.delete = function (code) {
            CategoryService.deleteCategory({'code': code}).$promise.then(onFulfillment, onRejection);

            function onFulfillment() {
                setTimeout(function () {
                    $scope.flashAlert('success', "Category with code: '" + code + "' removed successfully");
                    $scope.tableParams.reload();
                }, 300)
            }

            function onRejection() {
                $scope.flashAlert('danger', "There is no category with " + code + ".");
            }


        }

        $scope.display = function (code) {
            $location.path("/category/display/" + code);
            $location.$$search = "";
            $location.replace();
            $location.$$compose();
        }

        $scope.add = function () {
            isEdit = false;
            dialogs.create('app/components/category/category.edit.html', 'CategoryEditController', {
                isEditMode: isEdit
            }, {}, '').result.then(resolveCallback, rejectCallback);

            function resolveCallback() {
                setTimeout(function () {
                    $scope.tableParams.reload()
                    $scope.flashAlert('success', "Category successfully added")
                }, 300)
            }

            function rejectCallback() {
                setTimeout(function () {
                        $scope.tableParams.reload()
                        $scope.flashAlert('danger', "Field to add category")
                    }
                    , 300)

            }

        }

        $scope.searchParams = $location.search();
        $scope.searchParams.orderBy == null || $scope.searchParams.orderBy == "" ? $scope.searchParams.orderBy = "subject" : $scope.searchParams.orderBy = $location.search().orderBy;
        $scope.searchParams.sortDirection == null || $scope.searchParams.sortDirection == "" ? $scope.searchParams.sortDirection = "asc" : $scope.searchParams.sortDirection = $location.search().sortDirection;
        $scope.searchParams.pageNumber == null || $scope.searchParams.pageNumber == "" ? $scope.searchParams.pageNumber = "1" : $scope.searchParams.pageNumber = $location.search().pageNumber;

        if (Object.hasOwn($scope.searchParams, 'pageSize') || $scope.searchParams.pageSize == "10" || $scope.searchParams.pageSize == "25" || $scope.searchParams.pageSize == "50" || $scope.searchParams.pageSize == "100") {
            $scope.searchParams.pageSize = $location.search().pageSize;
        } else {
            $scope.searchParams.pageSize = "10";
        }


        $scope.search = function () {
            $scope.tableParams.reload().then(function (data) {
                if ($scope.searchParams.subject == "" || $scope.searchParams.subject == null || $scope.searchParams.subject == undefined) {
                    delete $scope.searchParams.subject;
                    console.log("value after: " + $scope.searchParams.subject);
                }
                if ($scope.searchParams.code == "" || $scope.searchParams.code == null || $scope.searchParams.code == "") {
                    delete $scope.searchParams.code;
                }
                console.log($scope.searchParams);
                $location.$$compose();
            }, function (error) {
                $scope.flashAlert('danger', "Problem occurred while searching categories");
            });
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

                    //Extract searchParams used by back-end from internal parameters of ng-table

                    // params.$params.page = $location.$$search.pageNumber;
                    // params.$params.count = $location.$$search.pageSize;
                    // var myKey = $location.$$search.orderBy;
                    // var myValue = $location.$$search.sortDirection;
                    // var myobj={myKey:myValue}
                    // params.$params.sorting.myKey=$location.$$search.orderBy
                    // Object.values(params.$params.sorting)[0]=$location.$$search.sortDirection;
                    // params.$params.sorting.value=$location.$$search.orderBy
                    debugger
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
                        $scope.flashAlert('danger', "Problem occurred while searching categories")

                    }
                }//end of getData
            }
        );//end of ngTableParams


    }])

categoryModule.controller('categoryDisplayController', ['$scope', '$route', 'CategoryService', function ($scope, $route, CategoryService) {
    var code = $route.current.params.code;
    console.log(code);
    CategoryService.loadByCode({code: code}).$promise.then(onFulfillment, onRejection);

    function onFulfillment(searchResponse) {
        //Update internal parameters of ng-table after arrival of new data from back-end, total count of data is used for rendering pagination
        $scope.subject = searchResponse.subject;
        $scope.code = searchResponse.code;
    }

    function onRejection(reason) {
        // Flash.create('danger', 'Problem occurred while searching categories')
        $scope.code = searchResponse.code;
        $scope.flashAlert('danger', "Problem occurred while searching categories")
    }
}])


categoryModule.controller('CategoryEditController', ['$scope', 'data', '$modalInstance', 'CategoryService', function ($scope, data, $modalInstance, CategoryService) {
    $scope.code = data.code;
    $scope.isEditMode = data.isEditMode
    $scope.disableBtn=false;
    $scope.ok = function () {
        $scope.disableBtn=true;
        if ($scope.isEditMode) {
            CategoryService.editSubject({code: $scope.code}, {subject: $scope.subject}).$promise.then(onFulfillment, onRejection);
        } else {
            CategoryService.saveCategory({subject: $scope.subject}).$promise.then(onFulfillment, onRejection);
        }

        function onFulfillment() {
            console.log("ok")
            $modalInstance.close({someImportantData: $scope.subject});
        }

        function onRejection() {
            console.log("sda")
        }


    }

    $scope.cancel = function () {
        $modalInstance.dismiss();
    }

}])



