#pragma once
#include "Shape.h"

class Triangle : public Shape
{
    public:
        Triangle(float side1, float side2, float side3);
    private:
        float side1;
        float side2;
        float side3;
        void calculateArea() override;
        void calculatePerimeter() override;
};