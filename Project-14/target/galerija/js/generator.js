

function generateButtons(data){
	var description = data.tags;
	var html = "";
	if(description.length == 0){
		html = "No results found..."
	} else {
		for(var i = 0; i < description.length; i++) {
			html += 
				"<button class='gridButton' value = '"+
				htmlEscape(description[i])+"' onclick = displayThumbnails(this.value)>"+
				htmlEscape(description[i]) + "</button>";
		}
	} 
	
	return html;
}


function generateImages(data){
	var pictureDesc = data;
	var html = "";
	if(pictureDesc.length == 0){
		html = "No results found..."
	} else {
		for(var i = 0; i < pictureDesc.length; i++) {
			html += 
				"<img onclick=displayLargeImage(this.alt) src='data:image/png;base64,"+
				pictureDesc[i].base64Encoding+"' alt='"+htmlEscape(pictureDesc[i].name)+"'>";
		}
	}
	return html;
}

function generateLargeImage(data){
	if(data.length == 0){
		html = "No results found..."
	} else {
		html = "<img src='data:image/png;base64,"+ data.base64+"' alt='"+htmlEscape(data.name)+"'>";
		html += "<h2>" + data.description +"</h2>";
		html += "<h3>" + data.tags +"</h3>";
	}
	return html;
}

function htmlEscape(str){
	return String(str)
		.replace(/&/g, '&amp;')
		.replace(/"/g, '&quot;')
		.replace(/'/g, '&#39')
		.replace(/</g, '&lt')
		.replace(/>/g, '&gt');
}