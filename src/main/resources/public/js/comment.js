$(document).ready(function() {
    $("#addComment").prop("disabled", true);
});

$("#comment").on("keyup paste", function() {
    var n = $("#comment").val().trim().length;
    $("#commentHelp").html(n + " out of 1000 characters used");

    if (n == 0 || n > 1000) {
        $("#addComment").prop("disabled", true);
    } else {
        $("#addComment").prop("disabled", false);
    }

    if (n > 1000) {
        $("#comment").addClass("is-invalid");
        $("#commentHelp").addClass("invalid-feedback")
    } else {
        $("#comment").removeClass("is-invalid");
        $("#commentHelp").removeClass("invalid-feedback")
    }

});
