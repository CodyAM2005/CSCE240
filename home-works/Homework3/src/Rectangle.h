#pragma once
#include "Shape.h"

class Rectangle : public Shape
{
    public:
        Rectangle(float side1, float side2);
    private:
        float side1;
        float side2;
        void calculateArea() override;
        void calculatePerimeter() override;
};