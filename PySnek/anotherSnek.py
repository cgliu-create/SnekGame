import random
import pygame

class Point:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return isinstance(other, Point) and self.x == other.x and self.y == other.y


class Control:
    def __init__(self):
        self.movingLeft = self.movingRight = self.movingDown = self.movingUp = False

    def turnRight(self):
        self.movingUp = self.movingDown = self.movingLeft = False
        self.movingRight = True

    def turnLeft(self):
        self.movingUp = self.movingDown = self.movingRight = False
        self.movingLeft = True

    def turnUp(self):
        self.movingDown = self.movingLeft = self.movingRight = False
        self.movingUp = True

    def turnDown(self):
        self.movingUp = self.movingLeft = self.movingRight = False
        self.movingDown = True


class Game:
    def __init__(self, width=0, height=0, numX=0, numY=0):
        self.control = Control()
        self.snake = [Point(0, 0)]
        self.food = [Point(1, 1)]
        self.swidth = width//numX
        self.sheight = height//numY
        self.numX = numX
        self.numY = numY

    def generateFood(self):
        if len(self.food) == 0:
            y = random.randint(0, 19)
            x = random.randint(0, 19)
            self.food.append(Point(x, y))

    def checkFoodEaten(self):
        if len(self.food) > 0:
            if self.snake[len(self.snake) - 1] == self.food[0]:
                self.food.pop(0)
                return True
            return False
        self.generateFood()
        return False

    def grow(self, x=0, y=0):
        self.snake.append(Point(x, y))
        if not self.checkFoodEaten():
            self.snake.pop(0)

    def selfCollision(self):
        for s in self.snake:
            count = 0
            for c in self.snake:
                if c == s:
                    count += 1
            if count > 1:
                return True
        return False

    def outOfBounds(self, x=0, y=0):
        return y < 0 or x < 0 or y >= self.numY or x >= self.numX

    def moveOrDie(self):
        head = self.snake[len(self.snake) - 1]
        x = 0
        y = 0
        if self.control.movingDown:
            y = head.y + 1
            x = head.x
        if self.control.movingUp:
            y = head.y - 1
            x = head.x
        if self.control.movingRight:
            y = head.y
            x = head.x + 1
        if self.control.movingLeft:
            y = head.y
            x = head.x - 1
        if self.outOfBounds(x, y) or self.selfCollision():
            return True
        self.grow(x, y)
        return False


red = (255, 0, 0)
green = (0, 255, 0)
black = (0, 0, 0)


class gui:
    def __init__(self):
        pygame.init()
        width = 600
        height = 600
        game = Game(width, height, 20, 20)
        win = pygame.display.set_mode((width, height))
        running = True
        clock = pygame.time.Clock()
        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_LEFT:
                        game.control.turnLeft()
                    if event.key == pygame.K_RIGHT:
                        game.control.turnRight()
                    if event.key == pygame.K_UP:
                        game.control.turnUp()
                    if event.key == pygame.K_DOWN:
                        game.control.turnDown()
            win.fill(black)
            for p in game.snake:
                pygame.draw.rect(win, green, (p.x * game.swidth, p.y * game.swidth, game.swidth, game.sheight))
            for p in game.food:
                pygame.draw.rect(win, red, (p.x * game.swidth, p.y * game.swidth, game.swidth, game.sheight))
            if game.moveOrDie():
                running = False
            pygame.display.update()
            time = clock.tick(5)
        pygame.quit()


if __name__ == '__main__':
    run = gui()
