var warehouse=angular.module('warehouse', ['warehouse.category']);
warehouse.config(function ($routeProvider){
    $routeProvider.when("/category/list",{
        templateUrl:"app/components/category/category.list.html",
        controller:"CategoryListController",
        reloadOnSearch:false
    })

})