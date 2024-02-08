#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include "Shape.cpp"
#include "Circle.cpp"
#include "Rectangle.cpp"
#include "Triangle.cpp"

using namespace std;

int countSpaces(const std::string& str)
{
    int spaceCount = 0;

    for(char ch : str)
    {
        if(ch == ' ')
        {
            spaceCount++;
        }
    }
    return spaceCount;
}
int main()
{
    int command = 0;
    while(true)
    {
        command = -1;
        cout << "Would you like to find the area of perimeter of these shapes?\n\nArea: Input 1\nPerimeter: Input 2\n" << endl;
        try
        {
            cin >> command;
            std::cin.clear(); 
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); //This and line above it handles errors from reading the cout lines
            if(command != 1 && command != 2)
            {
                throw std::invalid_argument("command is not 1 or 2"); //Throw exception because it wasn't a 1 or 2
            }
            break;
        }
        catch(...) //If didn't input a number, or if the number wasn't 1 or 2
        {
            cout << "Please make sure to input a number value that's either 1 or 2\n" << endl;
        }
    }

    string typeOfCommand = "";
    if(command == 1) //Depending on command input, either compute area or perimeter of shapes
        typeOfCommand = "AREA";
    else
        typeOfCommand = "PERIMETER";
    string line; //Variable that will get each line of input
    string s; //Variable that will get each word from the line

    ifstream in_myfile("../data/input.txt");
    ofstream out_myfile("../data/output.txt");

    if(in_myfile.is_open()) //Checks if the in-file opened properly
    {
        string line = "";
        while(getline(in_myfile,line)) //Sets 'line' to the next line of the file, this code will run until it's out of shapes
        {
            int spaceCount = countSpaces(line);
            istringstream iss(line); //Creates an istringstream, to parse through the 'line' variable
            string s;
            getline(iss,s,' '); //Sets 's' to the next word of 'line'
            
            float result;

            if(s.compare("CIRCLE") == 0) //This Shape is circle
            {
                if(spaceCount != 1) //Not a proper circle
                {
                    result = -1;
                }
                else //Proper circle
                {
                    string r;
                    getline(iss,r,' '); //Sets the variable 's1' to be the next word in 'line'
                                            //This code is error handled, as if there isn't enough parameters, it will throw an exception

                    Circle shape(stof(r));

                    if(typeOfCommand.compare("AREA") == 0)
                    {
                        result = shape.getArea();
                    }
                    else if(typeOfCommand.compare("PERIMETER") == 0)
                    {
                        result = shape.getPerimeter();
                    }
                }
            }
            else if(s.compare("RECTANGLE") == 0) //This Shape is rectangle
            {
                if(spaceCount != 2) //Not a proper rectangle
                {
                    result = -1;
                }
                else //Proper rectangle
                {
                    string s1;
                    getline(iss,s1,' '); //Sets the variable 's1' to be the next word in 'line'

                    string s2;
                    getline(iss,s2,' ');

                    Rectangle shape(stof(s1),stof(s2));

                    if(typeOfCommand.compare("AREA") == 0)
                    {
                        result = shape.getArea();
                    }
                    else if(typeOfCommand.compare("PERIMETER") == 0)
                    {
                        result = shape.getPerimeter();
                    }
                }
            }
            else if(s.compare("TRIANGLE") == 0) //This Shape is triangle
            {
                if(spaceCount != 1) //Not a proper triangle
                {
                    result = -1;
                }
                else //Proper triangle
                {
                    string s1;
                    getline(iss,s1,' '); //Sets the variable 's1' to be the next word in 'line'

                    string s2;
                    getline(iss,s2,' ');

                    string s3;
                    getline(iss,s3,' ');

                    Triangle shape(stof(s1),stof(s2),stof(s3));

                    if(typeOfCommand.compare("AREA") == 0)
                    {
                        result = shape.getArea();
                    }
                    else if(typeOfCommand.compare("PERIMETER") == 0)
                    {
                        result = shape.getPerimeter();
                    }
                }
            }
            else
            {
                //This is not a shape that is allowed
            }
            if(out_myfile.is_open())
            {
                if(result <= 0)
                {
                    out_myfile << s << " is invalid, please ensure shape is a valid shape, all capital letters, and has valid input numbers to have all sides connect" << endl;
                }
                else
                {
                    out_myfile << s << " " << typeOfCommand << ": " << result <<endl;
                }
            }
            else
            {
                cout << "Unable to open output file - " << "out_file_name" << endl;
            }
        }
    }
    else
    {
        cout << "Unable to open input file - " << "in_file_name" << endl;
        out_myfile << "Unable to open input file - " << "in_file_name" << endl;
    }
    in_myfile.close(); //Closing the files after use
    out_myfile.close();
    cout << "Check your output file :)" << endl;
}