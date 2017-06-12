function Rectangle()
{
	this.left = 0;
	this.top = 0;
	this.width = 0;
	this.height = 0;
	
	this.InitRectangle = function(left, top, width, height)
	{
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	this.Intersects = function(rect)
	{
		if(this.left + this.width < rect.left)
		{
			return false;
		}
		
		if(this.top + this.height < rect.top)
		{
			return false;
		}
		
		if(this.left > rect.left + rect.width)
		{
			return false;
		}
		
		if(this.top > rect.top + rect.height)
		{
			return false;
		}
		
		return true;
	}
}