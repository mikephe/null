/**
    The player object
*/
function Player() {
    this.velocity = 1;//note that velocity in this case if used to move over 1 box
    this.up = false;
    this.down = false;
    this.left = false;
    this.right = false;
    this.space = false;
    this.boxMaxHor = 5;//horizontal and vertical components of the player's cage. given in boxes.
    this.boxMaxVer = 3;
    this.boxWidth = 50;
    this.boxHeight = 20;
    this.boxOriginX = 10;//top left corner of the player's cage
    this.boxOriginY = 100;
    this.boxMaxHorX = this.boxOriginX+(this.boxMaxHor*this.boxWidth);
    this.boxMaxVerY = this.boxOriginY+(this.boxMaxVer*this.boxWidth);
    this.isPlayerFlag = false;
    this.weaponTexture = new Image();
    this.weaponTexture.src = "sprites/fire.png";
    this.weapon = new DrawableObject;
    this.weaponRange = 200;
    this.spaceCount = 0;
    this.spaceCountMax= 10;
    this.easiness = 1.5; //higher is easier

    /**
        Initializes the player through DrawableObject
    */
    this.InitPlayer = function(texture, xOp, yOp, z, isPlayerFlag, player) {
        this.player = player;
        this.isPlayerFlag = isPlayerFlag;
        if(isPlayerFlag){       
            this.InitDrawableObject(texture, this.boxOriginX+xOp*this.boxWidth, yOp*this.boxWidth, z);//we've changed the x and y to now depend on the box.
            this.weapon.InitDrawableObject(this.weaponTexture, this.x+30, this.y+20, 0);    
        } else { //if enemy
            this.InitDrawableObject(texture, 700+xOp*this.boxWidth*this.easiness, yOp*this.boxWidth, z);
            this.velocity = 120+xOp*1.2;
        }
        return this;
   }

   /**
        Determines if user pressed arrow keys
   */
   this.keyDown = function(key) {
        if(this.isPlayerFlag){
            if (key.keyCode == 38 || key.keyCode == 87) {
                this.up = true;
            }
        
            if (key.keyCode == 40 || key.keyCode == 83){
                this.down = true;
            }
        
            if (key.keyCode == 37 || key.keyCode == 65) {
                this.left = true;
            }
        
            if (key.keyCode == 39 || key.keyCode == 68) {
                this.right = true;
            }
            if (key.keyCode === 32) {
                this.space = true;
            }
            else {
                this.space = false;
            }
        }
    }

    /**
        Determines if user is not pressing arrow keys
    */
    this.keyUp = function(key) {
        if(this.isPlayerFlag){      
            if (key.keyCode == 38 || key.keyCode == 87) {
                this.up = false;
            }
        
            if (key.keyCode == 40 || key.keyCode == 83) {
                this.down = false;
            }
    
            if (key.keyCode == 37 || key.keyCode == 65) {
                this.left = false;
            }

            if (key.keyCode == 39 || key.keyCode == 68) {
                this.right = false;
            }
            if (key.keyCode === 32) {
                this.space = false;
            }
        }
    }

    /**
        Updates Player's movement and actions
    */
    this.Update = function (deltaTime, context) {
        var tempDelta = 0;
        if(this.isPlayerFlag){
            //player fire
            if(this.space == true){
                this.weapon.x = this.x + 30;
                this.weapon.y = this.y - 10;
                this.spaceCount = this.spaceCountMax;
            } else {
                if(this.spaceCount > 0){
                    this.spaceCount--;
                } else {
                    this.weapon.x = -50;
                    this.weapon.y = -50;
                }
            }

            //player movement
            if(this.up) {
                tempDelta = this.y - (this.velocity * this.boxWidth);
                if(tempDelta >= this.boxOriginY){ //if we do not exceed the boundary
                    this.y = tempDelta;         //allow the change
                } 
            }
            if(this.down) {
                tempDelta = this.y + (this.velocity * this.boxWidth);
                if(tempDelta <= this.boxMaxVerY){
                    this.y = tempDelta;
                }
            }

            if(this.left) {
                tempDelta = this.x - (this.velocity * this.boxWidth);
                if(tempDelta >= this.boxOriginX){
                    this.x = tempDelta;
                }
            }

            if(this.right) {
                tempDelta = this.x + (this.velocity * this.boxWidth);
                if(tempDelta <= this.boxMaxHorX){
                    this.x = tempDelta;
                }
            }
        } else {
            this.x -= this.velocity * deltaTime;
            if(this.x < 0){
                this.DisposeEnemy();
                baseHealth -= 10;           
            }
            if(this.y === this.player.y && this.x < (this.player.x+40) && this.x > (this.player.x-40)){
                this.DisposeEnemy();
                health -= 15;//player takes damage.
            }
            if(this.y === this.player.y 
                && this.player.spaceCount > 0 
                && this.x < (this.player.x+30+this.player.weaponRange) 
                && this.x > this.player.x+30)
            {
                this.DisposeEnemy();
                score += 5;
            }               
        }
        //reset the buttons
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.space = false;
    }
    this.DisposeEnemy = function(){
        this.DisposeDrawableObject();
    }
}

Player.prototype = new DrawableObject;