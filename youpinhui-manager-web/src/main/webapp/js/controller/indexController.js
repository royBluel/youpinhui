app.controller('indexController',function($scope,loginService){
	//显示当前登录的用户名
	$scope.showLoginName=function(){
		loginService.loginName().success(
			function(response){
				$scope.loginName=response.loginName;
			}
	);
	}
});