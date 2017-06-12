/**
    Handles objects throughout game by initializing and disposing them
*/

function Object()
{ 
    this.x = 0;
    this.y = 0;
    this.z = 0;

    this.InitObject = function(x, y, z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        
        objectManager.AddObject(this);
        
        return this;
    }

    this.DisposeObject = function()
    {
        objectManager.RemoveObject(this);
    }
}