#pragma once
#include "Shape.h"

class Circle : public Shape
{
    
    public:
        Circle(float r);
    private:
        float radius;
        void calculateArea() override;
        void calculatePerimeter() override;
};