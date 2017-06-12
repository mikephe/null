/**
    Manages objects by loading and rendering them
*/

var bg = new Image();
bg.src = "images/BG.png";
var endGame = false;

function ObjectManager() {
    this.objects = new Array();
    
    this.lastFrame = new Date().getTime();
    
    this.deltaX = 0;
    this.deltaY = 0;
    
    this.main = null;
    
    this.canvas = null;
    this.context = null;
    
    /**
        Initializes objects
    */
    this.InitObjectManager = function() {
        objectManager = this;

        // initialize keyboard input (key up/down)
        document.onkeydown = function(event) {
            event.preventDefault();
            objectManager.keyDown(event);
        }

        document.onkeyup = function(event) {
            objectManager.keyUp(event);
        }

        this.canvas = document.getElementById('canvas');
        this.context = this.canvas.getContext('2d');

        this.main = new Main().Initialise();
        

        // Intervals to refresh the canvas to update game as it changes
        setInterval(function() { 
            objectManager.Draw(); 
        }, frameTime);
        return this;        
    }
    
    /**
        Add objects to array
    */
    this.AddObject = function(Object) {
        this.objects.push(Object);
        this.objects.sort(function(a,b) {
            return a.z - b.z;
        })
    };

    /**
        Remove objects when finished using them
    */
    this.RemoveObject = function(Object) {
        this.objects.removeObject(Object);
    }

    /**

    */
    this.keyDown = function(event) {
        for (obj in this.objects) {
            if (this.objects[obj].keyDown) {
                this.objects[obj].keyDown(event);
            }
        }
    }

    /**
    */
    this.keyUp = function(event) {
        for (obj in this.objects) {
            if (this.objects[obj].keyUp) {
                this.objects[obj].keyUp(event);
            }
        }
    }

    /**
        Draw objects to canvas
    */
    this.Draw = function () {
        var frame = new Date().getTime();
        var deltaTime = (frame - this.lastFrame) / 1000;
        this.lastFrame = frame;

        // Clears canvas
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);

        //score and health display
        this.context.font = "18px Arial";
        this.context.fillText("Score: "+score, 700, 20);
        this.context.fillText("Player HP: "+health, 350, 20);
        this.context.fillText("BASE HP: "+baseHealth, 20, 20);
        
        if(baseHealth <= 0){
            this.context.font = "50px Arial";
            this.context.fillText("YOUR BASE HAS FALLEN", 175, 100);
            this.context.fillText("GAME OVER", 280, 200);
            if (endGame === false)
                gameEnd();
            endGame = true;
        }else if (health <= 0){
            if(health < 0){ health = 0;}
            this.context.font = "50px Arial";
            this.context.fillText("YOUR WERE DEFEATED", 175, 100);
            this.context.fillText("GAME OVER", 280, 200);
            if (endGame === false)
                gameEnd();
            endGame = true;
        } else {
            for (obj in this.objects) {
                if (this.objects[obj].Update) {
                    this.objects[obj].Update(deltaTime, this.context);
                }

                if (this.objects[obj].Draw) {
                    this.objects[obj].Draw(this.context, this.deltaX, this.deltaY);
                }

            }
        }
        /* BACKGROUND CODE TODO */
        this.context.globalCompositeOperation='destination-over';
        this.context.drawImage(bg, 0, 0);
    };
}

function getTheEnd()
{
    return endGame;
}

function gameEnd(){
    var cookie = document.cookie.substring(9);
    ajax.post('addScore.php', {'gamertag': cookie, 'score': score}, 
        function(response){
            response = response.replace(/\r\n|\n/, '');
            if (response === 'true') {
                window.location.href = 'highscores.php';
            }
            else {
                window.location.href = 'game_screen.php';
            }
        }, true);
}

Array.prototype.remove = function (a) {
    var rest = this.slice(a + 1 || this.length);
    
    if(a >= 0)
        this.length = a;

    return this.push.apply(this, rest);
};

Array.prototype.removeObject = function (object) {
    for (var i = 0; i < this.length; ++i)
    {
        if (this[i] === object)
        {
            this.remove(i);
            break;
        }
    }
}