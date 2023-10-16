$(function(){
	if(location.hash){
		$(`a[href='${location.hash}']`).tab('show');
	}
	$(".datepicker").datepicker({dateFormat:'mm/dd/yy'});
})

$(function(){
	$(".btn-heart").click(function(){
		var id = $(this).parents("[data-pid]").attr("data-pid")
		$.ajax({
			url:"/product/favorite/" + id,
			success: function(resp){
				alert("Đã thêm vào danh sách sản phẩm yêu thích của bạn");
			}
		})
	});
	
	$(".btn-envelope").click(function(){
		var id = $(this).parents("[data-pid]").attr("data-pid");
		$("#send-dialog #id").val(id);
		$(".nn-sending").hide();
		$("#send-dialog").modal("show");
	});
	$(".btn-send").click(function(){
		var data = {
				sender: $("#send-dialog #sender").val(),
				receiver: $("#send-dialog #receiver").val(),
				subject: $("#send-dialog #subject").val(),
				body: $("#send-dialog #body").val(),
				id: $("#send-dialog #id").val()
		}
		$(".nn-sending").show();
		$.ajax({
			url:"/product/send",
			data: data,
			type:"POST",
			success: function(resp){
				$(".nn-sending").hide();
				alert("Đã gửi thông tin sản phẩm thành công!");
				$("#send-dialog").modal("hide");
			}
		})
	});

	$(".btn-cart-add").click(function(){
		var id = $(this).parents("[data-pid]").attr("data-pid");
		$.ajax({
			url:"/cart/add/" + id,
			success: function(resp) {
				updateCartInfo(resp);
			}
		});
		effectCart(id);
	});
	
	$(".btn-cart-remove").click(function(){
		var id = $(this).parents("[data-pid]").attr("data-pid");
		$.ajax({
			url:"/cart/remove/" + id,
			success: function(resp) {
				updateCartInfo(resp);
			}
		});
		$(this).parents("tr").hide(500);
	});
	
	$(".txt-cart-quantity").on("input", function(){
		var tr = $(this).parents("[data-pid]");
		var id = tr.attr("data-pid");
		var qty = $(this).val();
		$.ajax({
			url:"/cart/update/" + id + "/" + qty,
			success: function(resp) {
				updateCartInfo(resp);
			}
		});
		var price = tr.attr("data-price");
		var disc = tr.attr("data-discount");
		tr.find(".item-amt").html(Math.round(100*price*qty*(1-disc))/100.0);
	});
	
	$(".btn-cart-clear").click(function(){
		$.ajax({
			url:"/cart/clear",
			success: function(resp) {
				updateCartInfo(resp);
			}
		});
		$(this).parents(".panel").find("tbody>tr").hide(500);
	});
})

function updateCartInfo(info){
	$("#cart-cnt").html(info.count);
	$("#cart-amt").html(info.amount);
}
function effectCart(id){
	var img = $(`[data-pid=${id}]`).find(".panel-body img");
	$("style#cart-effect").html(`.cart-effect{
		background-image: url("${img.attr("src")}");
		background-size: 100% 100%;
	}`);
	img.effect("transfer", {to:"#cart-img", className:"cart-effect"}, 1000)
}