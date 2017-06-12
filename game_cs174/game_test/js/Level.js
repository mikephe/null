function Level()
{
    this.tiles = new Array();
	this.pickup = new Object;
	this.enemy = new Object;
	
    this.tileWidth = 0;
    this.tileHeight = 0;

    this.InitLevel = function(width, height)
    {
		this.tileWidth = tile.width;
		this.tileHeight = tile.height;
		
		for(var i = 0; i < 50; i++)
		{
			this.tiles[i] = 1;
		}

		for(var i = 0; i < 50; i += 2)
		{
			this.pickup[i] = 'Berry';
		}
		
		//this.enemy['10'] = 'Enemy';
		
        this.AddTile(width, height);
		this.AddPickup(width, height);
		//this.AddEnemy(width, height);
		for(var i = 0; i < 5; i++) {
			this.enemy[i] = 'Enemy';
		}
		this.AddEnemy(width, height);

        return this;
    }

    this.AddTile = function(width, height)
    {
        for (var x = 0; x < this.tiles.length; ++x)
        {
            for (var y = 0; y < this.tiles[x]; ++y)
            {
                new DrawableObject().InitDrawableObject(tile, x * this.tileWidth, height - (y + 1) * this.tileHeight, 4);
            }
        }
    }
	
	this.AddPickup = function(width, height)
	{
		for(var i = 0; i < this.tiles.length; ++i)
		{
			if(this.pickup[i])
			{
				var x = i * this.tileWidth + this.tileWidth / 2;
				var y = height - this.TerrainHeight(i);
				
				if(this.pickup[i] == 'Berry')
				{
					new Pickup().InitPickup(1, berry, x - berry.width / 2, y - berry.height, 6, 1, 1);
				}
			}
		}
	}
	
	this.AddEnemy = function(width, height)
	{
		for(var i = 0; i < this.tiles.length; ++i)
		{
			if(this.enemy[i])
			{
				var x = i * this.tileWidth + this.tileWidth / 2;
				var y = height - this.TerrainHeight(i);
				
				if(this.enemy[i] == 'Enemy')
				{
					new Enemy().InitEnemy(enemy_left, x+700+(300*i) - enemy_left.width / 2, y - enemy_left.height, 7, 4, 4);
				}
			}
		}
	}

    this.CurrentTile = function(x)
    {
        return parseInt( x / this.tileWidth);
    }

    this.TerrainHeight = function(index)
    {
        if (index < 0 || index > this.tiles.length) 
		{
			return 0;
		}

        return this.tiles[index] *  this.tileHeight;
    }
}