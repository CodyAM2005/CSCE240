#pragma once
#include <string>

class Shape
{
    public:
        float getArea();
        float getPerimeter();
    protected:
        float area;
        float perimeter;
        virtual void calculateArea() = 0;
        virtual void calculatePerimeter() = 0;
};