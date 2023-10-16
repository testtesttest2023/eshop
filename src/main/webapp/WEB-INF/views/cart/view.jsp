<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f" %>

<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">SHOPPING CART</h4>
	</div>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Unit Price</th>
				<th>Discount</th>
				<th>Quantity</th>
				<th>Amount</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="p" items="${cart.items}">
			<tr data-pid="${p.id}" data-price="${p.unitPrice}" data-discount="${p.discount}">
				<td>${p.id}</td>
				<td>${p.name}</td>
				<td>
					$<f:formatNumber value="${p.unitPrice}" pattern="#,###.00"/>
				</td>
				<td>
					<f:formatNumber value="${p.discount * 100}" pattern="#,###.00"/>%
				</td>
				<td><input value="${p.quantity}" type="number" min="1" class="txt-cart-quantity" style="width:100px"></td>
				<td class="item-amt">
					$<f:formatNumber value="${p.unitPrice*p.quantity*(1-p.discount)}" pattern="#,###.00"/>
				</td>
				<td>
					<button class="btn btn-sm btn-danger btn-cart-remove">
						<span class="glyphicon glyphicon-trash"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="panel-footer">
		<a href="${backurl}" class="btn btn-primary">Add More</a>
		<button class="btn btn-danger btn-cart-clear">Clear Cart</button>
		<a href="/order/checkout" class="btn btn-success">Check Out</a>
	</div>
</div>