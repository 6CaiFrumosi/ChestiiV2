#include <stdio.h>
#include <regex>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string>
#include <cstring>
struct mesg_buffer 
{   
    long mesg_type;
    char mesg_text[2000];
} message;

std::string* receive()
{
    std::string *aux;
    int msgid;

    if ( -1 == ( msgid = msgget( (key_t)1234, IPC_CREAT | 0666)))
	{
		perror( "msgget() failed");
		exit( 1);
	} 
    if(-1==msgrcv(msgid, &message, sizeof(message), 1, 0))
    {
        perror( "msgrcv() failed");
		exit( 1);    
    }  
    aux=new std::string(message.mesg_text);
    msgctl(msgid, IPC_RMID,NULL);
    return aux;
}

int is_html(std::string* s)
{

    int i=0, aux, stare;    
    std::string token;
    std::smatch m;
    std::regex reg("<(\"[^\"]*\"|'[^']*'|[^'\">])*>");   
    while(i<s->length())
    {
        
        token = s->substr(i, s->find("\n"));
        if (regex_search(token, m, reg))
        {   
            stare = 1;
        }
        else
        {
            stare++;
        }
        i+=s->find("\n")+1;
    }
    if (stare != 1)
    {
        return 0;
    }
    else
    {
        return 1;
    }
}

int main()
{
    FILE *fptr=NULL;

    std::string *s=receive();
    if (is_html(s))
    {
        printf("\nFisierul transmis este in format html\n");
        fptr = fopen("/home/sergiu/Desktop/my_html.txt", "w");
        if (fptr == NULL) {
            printf("Error!");
            exit(1);
            }
        fptr = fopen("/home/sergiu/Desktop/my_html.txt", "w");        
        fprintf(fptr, "%s", s->c_str());
        fclose(fptr);
    }
    else
    {
        printf("\nFisierul transmis nu este in format html\n");
    }
    delete s;
    return 0;
}
