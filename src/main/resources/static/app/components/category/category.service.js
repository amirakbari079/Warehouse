app.service("CategoryService",['$resource',function ($resource){
    var customActions={
        search:{method:'Get',url:'api/category/search'},
        loadByCode:{method: 'Get',url:'api/category/:code'}
    }
    return $resource('',{},customActions)
}])
