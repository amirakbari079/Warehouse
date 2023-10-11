categoryModule.service("CategoryService",['$resource',function ($resource){
    var customActions={
        search:{method:'Get',url:'api/category/search'},
        loadByCode:{method: 'Get',url:'api/category/:code'},
        editSubject:{method:'put',url:'api/category/update/:code'},
        deleteCategory:{method:'Delete',url:'api/category/:code'},
        saveCategory:{method:'Post',url:'api/category/save'}
    }
    return $resource('',{},customActions)
}])
