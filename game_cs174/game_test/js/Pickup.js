function Pickup()
{
	this.InitPickup = function(score, texture, x, y, z, frameCount, frameRate)
	{
		this.InitAnimationManager(texture, x, y, z, frameCount, frameRate);
		this.score = score;
		
		return this;
	}
	
	this.DisposePickup = function()
	{
		this.DisposeAnimationManager();
	}
	
	this.Update = function(deltaTime, context, deltaX, deltaY)
	{
		if(this.BoundingBox().Intersects(player.BoundingBox()))
		{
			//effect.play();
			this.DisposePickup();
			score += 50;
		}
	}
}

Pickup.prototype = new AnimationManager;