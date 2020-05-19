import pygame
import random


class Point:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y = y

    def __eq__(self, other):
        return isinstance(other, Point) and self.x == other.x and self.y == other.y

    def __ne__(self, other):
        return not self == other


class ColorPoint(Point):
    def __init__(self, x=0, y=0, color=(0, 0, 0)):
        self.color = color
        Point.__init__(self, x, y)


class BlockGrid:
    def __init__(self, numX=1, numY=1, margin=1, swidth=1, sheight=1):
        self.swidth = swidth
        self.sheight = sheight
        self.numX = numX
        self.numY = numY
        self.width = numX * (margin + swidth) + margin
        self.height = numY * (margin + sheight) + margin
        self.data = [[ColorPoint for x in range(self.numX)] for y in range(self.numY)]
        for yp in range(self.numY):
            for xp in range(self.numX):
                xpos = margin + xp * (swidth + margin)
                ypos = margin + yp * (sheight + margin)
                self.data[yp][xp] = ColorPoint(xpos, ypos, (0, 0, 0))

    def resetGrid(self):
        for yp in range(self.numY):
            for xp in range(self.numX):
                self.data[yp][xp].color = (0, 0, 0)

    def markGrid(self, x=0, y=0, color=(0, 0, 0)):
        self.data[y][x].color = color

    def displayGrid(self, display):
        display.fill((255, 255, 255))
        for yp in range(self.numY):
            for xp in range(self.numX):
                p = self.data[yp][xp]
                pygame.draw.rect(display, p.color, (p.x, p.y, self.swidth, self.sheight))


class Snake:

    def __init__(self, x=0, y=0, grid=BlockGrid):
        self.body = [Point(x, y)]
        self.food = []
        self.grid = grid
        self.movingUp = False
        self.movingDown = False
        self.movingLeft = False
        self.movingRight = False

    def turnRight(self):
        self.movingUp = False
        self.movingDown = False
        self.movingLeft = False
        self.movingRight = True

    def turnLeft(self):
        self.movingUp = False
        self.movingDown = False
        self.movingLeft = True
        self.movingRight = False

    def turnUp(self):
        self.movingUp = True
        self.movingDown = False
        self.movingLeft = False
        self.movingRight = False

    def turnDown(self):
        self.movingUp = False
        self.movingDown = True
        self.movingLeft = False
        self.movingRight = False

    def generateFood(self):
        if len(self.food) == 0:
            y = random.randint(0, self.grid.numY - 1)
            x = random.randint(0, self.grid.numX - 1)
            self.food.append(Point(x, y))

    def checkFoodEaten(self):
        if len(self.food) > 0:
            if self.body[len(self.body) - 1] == self.food[0]:
                self.food.pop(0)
                return True
            return False
        self.generateFood()
        return False

    def grow(self, x=0, y=0):
        self.body.append(Point(x, y))
        if not self.checkFoodEaten():
            self.body.pop(0)

    def checkOutOfBounds(self, x=0, y=0):
        return y >= self.grid.numY or x >= self.grid.numX

    def checkCollision(self):
        for b in self.body:
            count = 0
            for c in self.body:
                if c == b:
                    count += 1
            if count == 2:
                return True
        return False

    def moveOrDie(self):
        head = self.body[len(self.body) - 1]
        x = 0
        y = 0
        if self.movingDown:
            y = head.y + 1
            x = head.x
        if self.movingUp:
            y = head.y - 1
            x = head.x
        if self.movingRight:
            y = head.y
            x = head.x + 1
        if self.movingLeft:
            y = head.y
            x = head.x - 1
        if self.checkCollision() or self.checkOutOfBounds(x, y):
            return True
        self.grow(x, y)
        return False

    def markSnake(self):
        self.grid.resetGrid()
        for b in self.body:
            self.grid.markGrid(b.x, b.y, color=(0, 255, 0))

    def markFood(self):
        for f in self.food:
            self.grid.markGrid(f.x, f.y, color=(255, 0, 0))


class gui:
    def __init__(self):
        pygame.init()
        data = BlockGrid(numX=20, numY=20, margin=5, swidth=25, sheight=25)
        snek = Snake(0, 0, data)
        win = pygame.display.set_mode((data.width, data.height))
        running = True
        clock = pygame.time.Clock()
        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_LEFT:
                        snek.turnLeft()
                    if event.key == pygame.K_RIGHT:
                        snek.turnRight()
                    if event.key == pygame.K_UP:
                        snek.turnUp()
                    if event.key == pygame.K_DOWN:
                        snek.turnDown()
            if snek.moveOrDie():
                running = False
            snek.markSnake()
            snek.markFood()
            data.displayGrid(win)
            pygame.display.update()
            time = clock.tick(5)
        pygame.quit()


if __name__ == '__main__':
    run = gui()
