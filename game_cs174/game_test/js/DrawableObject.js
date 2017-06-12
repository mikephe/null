/**
    Draw/Render objects on canvas
*/
function DrawableObject() {
    this.texture = null;

    /**
        Initializes object to draw
        @texture the texture to be drawn
        @x the x coordinate
        @y the y coordinate
        @z the z coordiante to determine order to be drawn
    */
    this.InitDrawableObject = function(texture, x, y, z) {
        this.InitObject(x, y, z);
        this.texture = texture;
        return this;
    }
    
    /**
        Draws image on canvas
        @param deltaTime
        @context the drawing context object
        @deltaX change in x
        @deltaY change in y
    */
    this.Draw = function(context, deltaX, deltaY) {
        context.drawImage(this.texture, this.x - deltaX, this.y - deltaY);
    }

    /**
        Disposes/removes object through Object.js
    */
    this.DisposeDrawableObject = function() {
        this.DisposeObject();
    }
}

DrawableObject.prototype = new Object;