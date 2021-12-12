#include <iostream>
#include <string.h>
#include "monsters.h"

bool monster::loadMedia(fereastra& c)
{
	//Loading success flag
	bool success = true;
	char buff1[] = "D:/Proiect-POO-Slasher/Texturi/Monsters/";
	char buff[] = "m1.png", * buff2;
	buff2 = new char[strlen(buff) + 2];
	strcpy_s(buff2, strlen(buff) + 1, buff);
	char* buff3 = new char[strlen(buff1) + strlen(buff2) + 1];

	for (int i = 1; i <= nr_text; i++)
	{
		if (i < 10)
			buff2[1] = i + '0';
		else
		{
			strcpy_s(buff2 + 3, strlen(strstr(buff, ".")) + 1, strstr(buff, "."));
			buff2[1] = i / 10 + '0';
			buff2[2] = i % 10 + '0';
		}
		strcpy_s(buff3, strlen(buff1) + 1, buff1);
		strcat_s(buff3, strlen(buff1) + strlen(buff2) + 1, buff2);
		movement[i - 1] = loadTexture(buff3, c);
		if (movement[i - 1] == NULL)
		{
			std::cout << "\nEroare la incarcarea imaginii!";
			success = false;
		}

	}

	return success;
}

monster::monster(int nr) :nr_text(nr)
{
	SDL_Surface* loadedSurface = IMG_Load("D:/Proiect-POO-Slasher/Texturi/Monsters/m1.png");
	player_h = loadedSurface->h / 2;
	player_w = loadedSurface->w / 2;
	SDL_FreeSurface(loadedSurface);
	movement= new SDL_Texture * [nr_text];
	for (int i = 0; i < nr_text; i++)
		movement[i] = NULL;
}

SDL_Texture* monster::walk(Fumiko* fumiko, fereastra* c, int *state, int &frame_rader,int position2)
{
	
	if (frame_rader >10)
	{
		state[3]++;
		frame_rader = 0;
	}
	frame_rader++;
	if (state[3]>= 4)
		state[3] = 0;
	distrugere_obiect(c->get_mapa());



	if (this->get_viata() > 0)
	{
		if (!cross(c->get_mapa(), 1))
		{
			this->position.x = this->position.x - 1;//position2-1;
			if (cross(c->get_mapa(), 1))
				this->position.x = this->position.x + 1; //+ position2 + 1;
		}
		else
		{	
			if(state[3]==3)
			fumiko->set_viata(fumiko->get_viata() - get_atac());
		}
		creeare_obiect(c->get_mapa(), 2);
		std::cout << "\nViata monstrului este: " << this->get_viata();
		std::cout << "\nViata lui Fumiko este: " << fumiko->get_viata();
	}
	else
		return NULL;
	if (fumiko->cross(c->get_mapa(), 2))
		std::cout << "\nAi luat dmg";
	return get_surface(state[3]);
}