$(document).ready(function() {
    $("#table-vouchers").DataTable( {
        "info" : false,
        "paging" : false,
        "searching" : false,
        columnDefs : [
            { targets : "no-sort", orderable : false }
        ]
    });

    jQuery.each(voucherIds, function(index, id) {
        $("#" + id).click();
    });
});

$("#table-vouchers").on("click", "tbody tr", function() {
    var id = $(this).data("href");
    $("#" + id).click();
});

$("input:checkbox").change(function() {
    var id = $(this).val();
    $("#tr-" + id).toggleClass("selected");
});

$("#selectAll").click(function() {
    $("#table-vouchers > tbody > tr").each(function() {
        if (!$("input", this).prop("checked")) {
            $(this).click();
        }
    });
});
