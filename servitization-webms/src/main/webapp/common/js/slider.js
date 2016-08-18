$(function() {
	var myInput = $("#threshold");
	var initValue = myInput.attr("value");  // myInput.val()也可

	var slider = $("#slider-range-min").slider({
		min : 0,
		max : 100,
		range : "min",
		value : initValue,
		slide : function(event, ui) {
			myInput.val(ui.value);
		}
	});

	myInput.change(function() {
		slider.slider("value", this.value);
	});
});

$(function() {
	var myInput = $("#threshold2");
	var initValue = myInput.attr("value");  // myInput.val()也可

	var slider = $("#slider-range-min2").slider({
		min : 0,
		max : 100,
		range : "min",
		value : initValue,
		slide : function(event, ui) {
			myInput.val(ui.value);
		}
	});

	myInput.change(function() {
		slider.slider("value", this.value);
	});
});