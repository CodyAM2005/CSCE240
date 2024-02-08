#include <iostream>
#include "Circle.h"
#include "Shape.h"

Circle::Circle(float r)
{
    this->radius = r;
    calculateArea();
    calculatePerimeter();
}
void Circle::calculateArea()
{
    area = radius*radius*3.14159265;
}
void Circle::calculatePerimeter()
{
    perimeter = radius * 2 * 3.14159265;
}