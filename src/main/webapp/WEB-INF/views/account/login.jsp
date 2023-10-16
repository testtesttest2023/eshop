<%@ page pageEncoding="utf-8"%>

<form action="/account/login" method="post">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-danger pull-right">${message}${param.message}</div>
			<h4 class="panel-title">LOGIN</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label>Username</label>
				<input name="username" value="${username}" class="form-control">
			</div>
			<div class="form-group">
				<label>Password</label>
				<input type="password" name="password" value="${password}" class="form-control">
			</div>
			<div class="form-group">
				<label>
					<input name="remember" type="checkbox" value="true" ${empty username ? '' : 'checked'}>
					Remember me?
				</label>
			</div>	
		</div>
		<div class="panel-footer">
			<button class="btn btn-warning">
				<span class="glyphicon glyphicon-user"></span> Login
			</button>	
		</div>
	</div>
</form>