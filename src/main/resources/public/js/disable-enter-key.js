$(document).ready(function () {
  $(window).keydown(function (e) {
    if (e.keyCode === 13) {
      e.preventDefault();
    }
  });
});
