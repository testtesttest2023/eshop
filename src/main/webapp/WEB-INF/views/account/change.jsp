<%@ page pageEncoding="utf-8"%>

<form action="/account/change" method="post">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-danger pull-right">${message}</div>
			<h4 class="panel-title">CHANGE PASSWORD</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label>Username</label>
				<input name="username" value="${param.username}" class="form-control">
			</div>
			<div class="form-group">
				<label>Current Password</label>
				<input type="password" name="password" value="${param.password}" class="form-control">
			</div>
			<div class="form-group">
				<label>New Password</label>
				<input type="password" name="newpwd" value="${param.newpwd}" class="form-control">
			</div>
			<div class="form-group">
				<label>Confirm New Password</label>
				<input type="password" name="confirm" value="${param.confirm}" class="form-control">
			</div>
		</div>
		<div class="panel-footer">
			<button class="btn btn-warning">
				<span class="glyphicon glyphicon-user"></span> Change
			</button>	
		</div>
	</div>
</form>