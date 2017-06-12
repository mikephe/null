/**
    Manages the animations
*/

function AnimationManager()
{
    this.currentFrame = 0;
    this.frameRate = 0;
    this.frameTime = 0;
    this.frameWidth = 0;
    this.frameHeight = 0;

    this.InitAnimationManager = function(texture, x, y, z, frameCount, framesPerSec)
    {
        this.InitDrawableObject(texture, x, y, z);
        
        this.currentFrame = 0;
        this.frameCount = frameCount;
        this.frameRate = 1 / framesPerSec;
        this.frameTime = this.frameRate;
        this.frameWidth = this.texture.width / this.frameCount;
        this.frameHeight = this.texture.height;

        return this;
    }
    
    this.BoundingBox = function()
    {
        return new Rectangle().InitRectangle(this.x, this.y, this.frameWidth, this.frameHeight);
    }
    
    this.DisposeAnimationManager = function()
    {
        this.DisposeDrawableObject();
    }

    this.Draw = function(deltaTime, context, deltaX, deltaY)
    {
        var sourceRect = this.frameWidth * this.currentFrame;
        
        context.drawImage(this.texture, sourceRect, 0, this.frameWidth, this.frameHeight, this.x - deltaX, this.y - deltaY, this.frameWidth, this.frameHeight);

        this.frameTime -= deltaTime;
        
        if (this.frameTime <= 0)
        {
           this.frameTime = this.frameRate;
           ++this.currentFrame;
           this.currentFrame %= this.frameCount;
        }
    }
}

AnimationManager.prototype = new DrawableObject;