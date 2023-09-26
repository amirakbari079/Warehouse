app.service("CategoryService",['$resource',function ($resource){
    var customActions={
        search:{method:'Get',url:'api/category/search'}
    }
    return $resource('',{},customActions)
}])
