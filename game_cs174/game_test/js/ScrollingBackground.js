function ScrollingBackground() {
	
	this.width = 0;
    this.height = 0;
    this.deltaScroll = 1;

    this.InitScrollingBackground = function(texture, x, y, z, width, height, deltaScroll) {
        
		this.InitDrawableObject(texture, x, y, z);
        this.width = width;
        this.height = height;
        this.deltaScroll = deltaScroll;
		
        return this;
    }
	
    this.DisposeScrollingBackground = function() {
        this.DisposeDrawableObject();
    }
	
	this.UpdateBackground = function(canvas, position, fillArea, deltaPosition) {
		
		var left; 
		var top;
		var width;
		var height;
		
        var xOffset = Math.abs(deltaPosition[0]) % this.texture.width;
        var yOffset = Math.abs(deltaPosition[1]) % this.texture.height;

		if(deltaPosition[0] < 0)
			left = this.texture.width - xOffset;
		else
			left = xOffset;
		
		if(deltaPosition[1] < 0)
			top = this.texture.height - yOffset;
		else
			top = yOffset;

		if(fillArea[0] < this.texture.width - left)
			width = fillArea[0];
		else
			width = this.texture.width - left;

		if(fillArea[1] < this.texture.height - top)
			height = fillArea[1];
		else
			height = this.texture.height - top;
        
        this.context.drawImage(this.texture, left, top, width, height, position[0], position[1], width, height);
        return [width, height];
    }

    this.Draw = function(deltaTime, canvas, deltaX, deltaY) {
        var background = [0, 0];
        
        for (var y = 0; y < this.height; y += background[1]) {
            for (var x = 0; x < this.width; x += background[0]) {
				var position = [this.x + x, this.y + y];
                var fillArea = [this.width - x, this.height - y];
                var deltaPosition = [0, 0];
				
                if (x == 0) 
					deltaPosition[0] = deltaX * this.deltaScroll;
				
                if (y == 0) 
					deltaPosition[1] = deltaY * this.deltaScroll;
				
                background = this.UpdateBackground(canvas, position, fillArea, deltaPosition);
            }
        }
    }
}

ScrollingBackground.prototype = new DrawableObject();