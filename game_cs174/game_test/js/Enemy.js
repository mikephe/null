/**
	Enemy Object
*/
function Enemy(){
	this.velocity = 30;
	this.InitEnemy = function(texture, xOp, yOp, z){
	//this.InitEnemy = function(texture, x, y, z, frameCount, frameRate){
	//(texture, x, y, z
		this.InitDrawableObject(texture, xOp, yOp, z);
		//this.InitAnimationManager(texture, x, y, z, frameCount, frameRate);
		return this;
	};
	this.DisposeEnemy = function(){
		this.DisposeAnimationManager();
	};
	//this.Update = function(deltaTime, context, deltaX, deltaY){
	this.Update = function(deltaTime, context){
		this.x -= this.velocity * deltaTime;
		if(this.BoundingBox().Intersects(player.BoundingBox())){
			this.DisposeEnemy();
			health -= 15;//player takes damage.
		}
	};
}
//Enemy.prototype = new AnimationManager;
Enemy.prototype = new DrawableObject;