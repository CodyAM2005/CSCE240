#include <iostream>
#include "Rectangle.h"
#include "Shape.h"

Rectangle::Rectangle(float side1, float side2)
{
    this->side1 = side1;
    this->side2 = side2;
    calculateArea();
    calculatePerimeter();
}
void Rectangle::calculateArea()
{
    area = side1*side2;
}
void Rectangle::calculatePerimeter()
{
    perimeter = (side1*2)+(side2*2);
}