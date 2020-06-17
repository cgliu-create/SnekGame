var movingLeft = false;
var movingRight = true;
var movingDown = false;
var movingUp = false;

function turnLeft(){
    movingLeft = true; movingRight = movingUp = movingDown = false;
}
function turnRight(){
    movingRight = true; movingLeft = movingUp = movingDown = false;
}
function turnUp(){
    movingUp = true; movingLeft = movingRight = movingDown = false;
}
function turnDown(){
    movingDown = true; movingLeft = movingRight = movingUp = false;
}

var speed = 1;

function coordinate(x, y){		
    this.x = x;		
    this.y = y;		
}		
var snake = [new coordinate(0, 0)];
var food = [new coordinate(5, 5)];

function generatefood(){
     let y = Math.round(Math.random() * 10);
     let x = Math.round(Math.random() * 10);
     food.push(new coordinate(x, y));
}

function growSnake(){
     let head = snake[snake.length-1];
     let y = head.y;
     let x = head.x;
     if (movingLeft){ x -= speed;}  
     if (movingRight){ x += speed;}  
     if (movingDown){ y += speed;}  
     if (movingUp){ y -= speed;}
     snake.push(new coordinate(x, y));
}

function checkBounds(){
     let head = snake[snake.length-1];
     return (head.y > 10 || head.y < 0 || head.x > 10 || head.x < 0);
}

function checkCollision(){
    let head = snake[snake.length-1];
    let body = snake.slice(0, snake.length-1);
    for (const coord of body) {
        if(head.x == coord.x && head.y == coord.y){ return true;}
    }
    return false;
}

const letternum = {
    0: "a",1: "b",2: "c",3: "d",4: "e",5: "f",6: "g",7: "h",8: "i",9: "j",10: "k"
};

function drawSnake(){
    for (const coord of snake) {
        let id = `#${letternum[coord.y]}${letternum[coord.x]}`;
        document.querySelector(id).style.backgroundColor = "green";
    }
}
function drawFood(){
    for (const coord of food) {
        let id = `#${letternum[coord.y]}${letternum[coord.x]}`;
        document.querySelector(id).style.backgroundColor = "red";
    }
}
function clear(){
    document.querySelectorAll("td").forEach(function(td){
        td.style.backgroundColor = "gray";
    });
}

var alive = true;

function animate(){
    if (alive){
        if (food.length == 0){ generatefood();}
        let head = snake[snake.length-1];
        let fooditem = food[0];
        if (head.x == fooditem.x && head.y == fooditem.y){ 
            growSnake();
            food.pop();
        } else {
            growSnake();
            snake.shift();
        }
        if (checkBounds() || checkCollision()){
            alive = false;
            alert("dead");
        } else {
            clear();
            drawFood();
            drawSnake();
        }
    }
}
    
document.addEventListener("DOMContentLoaded", () => {
    setInterval(animate, 500);
    document.querySelectorAll(".control").forEach(function(button){
        button.onclick = function() {
            let dir = button.dataset.dir
            if (dir == 'left'){ turnLeft();}
            if (dir == 'right'){ turnRight();}
            if (dir == 'down'){ turnDown();}
            if (dir == 'up'){ turnUp();}
        };
    });
});