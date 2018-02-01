function track() {
  trackingNo = trackingNo.replace(/\s/g,"")
  var url = "http://wwwapps.ups.com/WebTracking/track?loc=en_US&track.x=Track&trackNums=" + trackingNo
  var w = window.open(url, "_blank")
  w.focus()
}
