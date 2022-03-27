
#include <string.h>
#include <iostream>
#include <cstring>
#include "Fumiko.h"


using namespace std;



bool Fumiko::loadMedia(fereastra &c)
{
	//Loading success flag
	
	bool success = true;
	char buff1[100] = "D:/Proiect-POO-Slasher/Texturi/PNG-Fumiko/";
	char buff[10] = "c1.png", *buff2;
	buff2 = new char[strlen(buff) + 2];
	strcpy_s(buff2, strlen(buff) + 1, buff);
	char* buff3 = new char[strlen(buff1) + strlen(buff2) + 1];
	
	for (int i = 1; i <= nr_texturi; i++)
	{
		if (i < 10)
			buff2[1] = i + '0';
		else
		{
			strcpy_s(buff2+3, strlen(strstr(buff, "."))+1,strstr(buff, "."));
			buff2[1] = i / 10+'0';
			buff2[2] = i % 10+'0';
			cout << endl << buff2 << endl;
		}
		strcpy_s(buff3, strlen(buff1) + 1, buff1);

		strcat_s(buff3, strlen(buff1) + strlen(buff2) + 1,buff2);
		cout << endl << buff3 << endl;
		movement[i - 1] = loadTexture(buff3, c);
		if (movement[i - 1] == NULL)
		{
			cout << "\nEroare la incarcarea imaginii!";
			success = false;
		}

	}

	return success;
}


Fumiko::Fumiko(int nr_texturi, int atac, int viata):caracter(atac, viata)
{
	SDL_Surface* loadedSurface = IMG_Load("D:/Proiect-POO-Slasher/Texturi/PNG-Fumiko/c1.png");
	player_h = loadedSurface->h / 2;
	player_w = loadedSurface->w / 2;
	SDL_FreeSurface(loadedSurface);
	this->nr_texturi = nr_texturi;
	movement = new SDL_Texture * [nr_texturi];
	for (int i = 0; i < nr_texturi; ++i)
	{
		movement[i] = NULL;
	}

}
Fumiko::~Fumiko()
{
	//Deallocate surfaces
	for (int i = 0; i < this->nr_texturi; ++i)
	{
		SDL_DestroyTexture(movement[i]);
		movement[i] = NULL;
	}
}
SDL_Texture* Fumiko::walk_right(int state[], fereastra* c, int& frame_rader)
{
	
	SDL_Texture* some_texture = NULL;
	cout << "\nframe_rader= " << frame_rader;
	if (frame_rader > 3)
	{
		cout << "\nstate[0]= " << state[0];
		frame_rader = 0;
		state[0]++;
	}
	if (state[0] > 5)
		state[0] = 0;
	cout << "\nFrame_rader=" << frame_rader;
	frame_rader++;
	some_texture = get_surface(state[0]);
	state[1] = 6;
	state[2] = 12;

	distrugere_obiect(c->get_mapa());
	if (cross(c->get_mapa(), 2))
		cout << "\nAi luat dmg";
	else
		position.x += 2;
	creeare_obiect(c->get_mapa(),1);
	return some_texture;
}
SDL_Texture* Fumiko::walk_left(int* state, fereastra* c, int &frame_rader)
{
	
	SDL_Texture* some_texture = NULL;
	
	if (frame_rader > 3)
	{
		frame_rader = 0;
		state[1]++;
	}
	if (state[1] > 10)
		state[1] = 6;
	frame_rader++;
	some_texture = get_surface(state[1]);
	state[0] = 0;
	state[2] = 12;
	distrugere_obiect(c->get_mapa());
	position.x -= 2;
	creeare_obiect(c->get_mapa(), 1);
	return some_texture;
}

SDL_Texture* Fumiko::atack(int* state, monster* m, fereastra* c, int& frame_rader, bool& ok)
{
	SDL_Texture* some_texture= NULL;
	SDL_Texture* my_attack = NULL;
	if (ok == false)
	{
		if (frame_rader > 5 * monster::get_NRmonsters())
		{
			frame_rader = 0;
			state[2]++;
		}
	}
	else
		if (frame_rader > 5 * monster::get_NRmonsters())
		{
		frame_rader = 0;
		state[4]++;
		}
	frame_rader++;
	
	if (ok == false)
	{
		if (state[2] > 15)
			state[2] = 12;
	}
	else
		if (state[4] > 22)
			state[4] = 18;

	if (ok == false)
		my_attack = get_surface(state[2]);
	else
		my_attack = get_surface(state[4]);
	some_texture = my_attack;
	state[0] = 0;
	state[1] = 6;
	if (ok == false)
		state[4] = 18;
	else
		state[2] = 0;

	if (m->cross(c->get_mapa(), 1))
	{
		cout << "\nMonstrul a luat dmg";
		m->set_viata((m->get_viata() - get_atac()));
	

	}
	return some_texture;
}

SDL_Texture* Fumiko::stay_down()
{
	SDL_Texture* some_texture =get_surface(16);
	return some_texture;
}

SDL_Texture* Fumiko::just_stay()
{
	SDL_Texture* some_surface =get_surface(0);
	return some_surface;
}

SDL_Texture* Fumiko::jump(fereastra* c,bool& ok, int &high, bool &ok2)
{
	SDL_Texture* some_texture = NULL;
		if (ok2 == false)
			some_texture = get_surface(17);
		else
			some_texture = get_surface(23);
		cout << "\nOK2= " << ok2;
	distrugere_obiect(c->get_mapa());
	if (high !=0&&ok==false)
	{
		position.y -= 10;
		high -= 10;
	}
	else
		ok = true;
	if (ok == true)
	{
		if (high < 150)
		{
			position.y += 10;
			high += 10;
		}
	}
	creeare_obiect(c->get_mapa(), 1);
	return some_texture;
}