function goBack() {
    if (voucherId == "new") {
        window.location.href="/vouchers";
    } else {
        window.location.href="/vouchers/" + voucherId;
    }
};

$(document).ready(function() {
    if ($("#customerId option:selected").val() == "new") {
        $("#customer-form input").prop("readonly", false);
    }
});

$("#customerId").change(function() {
    if ($(this).val() == "new") {
        $("#customer-form input").prop("readonly", false).val("");
    } else {
        $("#customer-form input").prop("readonly", true);

        var i = $("#customerId option:selected").index() - 1;

        $("#passport").val(customers[i].passport);
        $("#firstName").val(customers[i].firstName);
        $("#lastName").val(customers[i].lastName);
    }
});
