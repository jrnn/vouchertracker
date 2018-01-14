var hack = $("script[src*=datatable-common]");
var tableId = hack.attr("data-tableId").split(";");
var hrefRoot = hack.attr("data-hrefRoot");

$(document).ready(function() {
    jQuery.each(tableId, function(index, id) {
        $("#" + id).DataTable( {
            "lengthChange" : false,
            "language" : {
                "search" : "Search"
            }
        });

        $("#" + id).on("click", "tbody tr", function() {
            if ($(this).data("href") != null) {
                window.location.href=hrefRoot + $(this).data("href");
            }
        });
    });
});
