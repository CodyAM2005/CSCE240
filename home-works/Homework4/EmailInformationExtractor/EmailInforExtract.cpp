#include <iostream>
#include <fstream> //This is used for strings and file parsing

using namespace std; //Using namespace std instead of writing std::string

class BaseEmailHeaderType //Attributes shared by both header types
{
    public:
        string received = "received";
        string contentType = "content-type";
        string contentTransferEncoding = "content-transfer-encoding";
        string from = "from";
        string to = "to";
        string cc = "cc";
        string subject = "subject";
        string date = "date";
        string messageID = "message-id";
        string replyTo = "reply-to";

};

class GmailHeaderType : BaseEmailHeaderType //Attributes only in the Gmail header
{
    public:
        string deliveredTo = "delivered-to";
        string xGoogleSmtpSource = "x-google-smtp-source";
        string mimeVersion = "mime-version";
};

class OutlookHeaderType : BaseEmailHeaderType //Attributes only in the Outlook header
{
    public:
        string authenticationResults = "authentication-results";
        string threadTopic = "thread-topic";
        string threadIndex = "thread-index";
        string references = "references";
        string acceptLanguage = "accept-language";
        string contentLanguage = "content-language";
        string xmsHasAttach = "x-ms-has-attach";
        string xmsExchangeOrganizationSCL = "x-ms-exchange-organization-scl";
};

int outOrGmail() //Method that will figure out if the provided header in the input file is outlook or gmail
{
    ifstream myFile;
    try
    {
        myFile.open("input.txt");
        if(myFile.is_open())
        {
            string line;
            while(getline(myFile,line)) //Parse through each line of the file, and set the line to the variable line
            {
                if(line.find("Authentication-Results")) //authentication-results is unique to outlook
                {
                    return 0; //This is outlook
                }
            }
            myFile.close();
        }
    }
    catch(...)
    {
        
    }
    return 1; //This is gmail
}
int main()
{
    string input; //This value will be set later with cin

    cout << "Welcome to the cool email thing! Input the info you want, or type 'quit' to quit" << endl;

    bool run = true; //Determines when to stop the loop
    while(run)
    {
        cout << "Please input the information you want now!\n" << endl;

        cin >> input; //input is set to the input from console

        int outlookOrGmail = outOrGmail(); // 0 for outlook, 1 for gmail

        if(input == "quit" || input == "Quit") //Quit function
        {
            run = false;
        }

        if(outlookOrGmail == 0) //This is outlook
        {
            ifstream myFile; //This is mostly the same code as in outOrGmail, except it's checking for a different value
            try
            {
                myFile.open("input.txt");
                if(myFile.is_open())
                {
                    string line;
                    while(getline(myFile,line))
                    {
                        if(line.find(input) == 0)
                        {
                            cout << line << endl;
                        }
                    }
                }
            }
            catch(...)
            {
                
            }
            myFile.close();
        }
        else //This is gmail
        {
            ifstream myFile;
            try
            {
                myFile.open("input.txt");
                if(myFile.is_open())
                {
                    string line;
                    while(getline(myFile,line))
                    {
                        if(line.find(input))
                        {
                            cout << line << endl;
                        }
                    }
                }
            }
            catch(...)
            {
                
            }
            myFile.close();
        }
    }
}