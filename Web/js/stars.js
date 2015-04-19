var layers = 6;
setupBubbles = function () {
	for (var i = 1; i <= layers; i++) {
		var div = '<li class="layer" data-depth="' + (.5 / i) + '">'
		div += '<div class = "bubble-background" id = "bubble-background-' + i + '"></div>';
		div += '</li>'
		
		$('#home-scene').append(div);
		
		for (var j = 0; j < Math.random() * (layers - i) * 20 * screen.width / 1200 + 10; j++) {
			var diam = Math.round((Math.random() * (layers - i) * 2)) + "px";
			var oX = Math.round((Math.random() * $(window).width() * 1.2)) + "px";
			var oY = Math.round((Math.random() * $(window).height() * 1.2)) + "px";
			var opacity = Math.random() * .5 + (layers - i) * .2;
			var bubble = '<div class = "bubble" style = "width:' + diam + ';height:' + diam + ';';
			bubble += 'top:' + oY + ';left:' + oX + ';opacity:' + opacity + '"></div>';
			$('#bubble-background-' + i).append(bubble);
		}
		
	}
}

setupBubbles();
