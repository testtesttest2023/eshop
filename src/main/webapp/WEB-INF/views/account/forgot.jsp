<%@ page pageEncoding="utf-8"%>

<form action="/account/forgot" method="post">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-danger pull-right">${message}</div>
			<h4 class="panel-title">FORGOT PASSWORD</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label>Username</label>
				<input name="username" value="${param.username}" class="form-control">
			</div>
			<div class="form-group">
				<label>Email Address</label>
				<input name="email" value="${param.email}" class="form-control">
			</div>
		</div>
		<div class="panel-footer">
			<button class="btn btn-warning">
				<span class="glyphicon glyphicon-user"></span> Retrieve
			</button>	
		</div>
	</div>
</form>