#include <iostream>
#include <cmath>
#include "Triangle.h"
#include "Shape.h"

Triangle::Triangle(float s1, float s2, float s3)
{
    this->side1 = s1;
    this->side2 = s2;
    this->side3 = s3;
    calculateArea();
    calculatePerimeter();
}
void Triangle::calculateArea()
{
    float s = (side1+side2+side3)/2;
    area = sqrt(s*(s-side1)*(s-side2)*(s-side3));
}
void Triangle::calculatePerimeter()
{
    perimeter = side1+side2+side3;
}