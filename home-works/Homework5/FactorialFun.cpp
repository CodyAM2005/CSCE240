#include <iostream>
#include <chrono>
#include <vector>

using namespace std; //Using namespace std instead of writing std::string

long factorial(long n);
long combination(long n, long r);

class Timer {
public:
    Timer() {
        startTimept = std::chrono::high_resolution_clock::now();
    }

    ~Timer() {
        Stop();
    }

    void Stop() {
        auto endTimept = std::chrono::high_resolution_clock::now();
        auto start = std::chrono::time_point_cast<std::chrono::microseconds>(startTimept).time_since_epoch().count();
        auto end = std::chrono::time_point_cast<std::chrono::microseconds>(endTimept).time_since_epoch().count();
        auto duration = end - start;
        double ms = duration * 0.001;
        std::cout << "Time taken: " << duration << " us (" << ms << " ms)\n";
    }

private:
    std::chrono::time_point<std::chrono::high_resolution_clock> startTimept;
};

int main()
{
    cout<<"> Factorial Fun ";

    string numbers;
    cin.clear();
    //cin >> numbers;
    getline(cin,numbers);

    cout<<endl;

    string space_delimiter = " ";
    vector<string> words{};
    numbers += " ";

    size_t pos = 0;
    while ((pos = numbers.find(space_delimiter)) != string::npos) {
        words.push_back(numbers.substr(0, pos));
        numbers.erase(0, pos + space_delimiter.length());
    }
    try
    {
        if(words.size() == 1 || words.size() == 3)
        {
            {
                Timer timer;
                cout<<factorial(stol(words[0]))<<endl;
            }
        }
        if(words.size() == 2 || words.size() == 3)
        {
            {
                Timer timer;
                cout<<combination(stol(words[0]),stol(words[1]))<<endl;
            }
        }
        if(words.size()<1 || words.size()>3)
        {
            cout<<"Please use 1-3 inputs for this."<<endl;
        }
    }
    catch(const std::exception& e)
    {
        cout<<"Error"<<endl;
    }
}
long factorial(long n) 
{
    try
    {
        if(n > 1)
            return n * factorial(n - 1);
        else
            return 1;
    }
    catch(const std::exception& e)
    {
        cout<<"Numbers too big"<<endl;
        return 0;
    }
}
long combination(long n, long r)
{
    try
    {
        if(r>n)
        {
            long temp = n;
            n = r;
            r = temp;
        }
        return factorial(n)/(factorial(r)*factorial(n-r));
    }
    catch(const std::exception& e)
    {
        cout<<"Numbers are too large"<<endl;
        return 0;
    }
}