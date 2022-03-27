/*This source code copyrighted by Lazy Foo' Productions (2004-2020)
and may not be redistributed without written permission.*/
#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif 
//Using SDL and standard IO
#include <iostream>
#include <time.h>
#include <ctime>
#include "Fumiko.h"
using namespace std;


int monster::nr_monsters{ 3 };
int main( int argc, char* args[] )
{	
	int ok3;
	//monster::set_NRmonsters(3);
	int ceva = 0;
	SDL_Rect init, init2;
	SDL_Rect screen;
	bool ok2 = false;
	int nr_monster = monster::get_NRmonsters();
	time_t curr_time;
	tm* tm_local;
	SDL_Texture* original_texture;
	bool ok;
	int high = 150;
	bool you_jumped=false;
	const Uint8* keystate;
	int frameCount=0, frame_rader=0, frameCount_2=0, fps_2=60;
	const int fps = 60; int frame_rader_2 = 0;
	Uint32 frameStart;
	fereastra* c=new fereastra;
	SDL_Event e; // Are grija de evenimente
	bool quit = false;
	SDL_Texture* another_texture = NULL;
	Fumiko* fumiko = new Fumiko(24, 10, 100);
	SDL_Texture* some_texture = NULL;
	//Apply the image stretched
	SDL_Rect stretchRect, fumiko_position, monster_pos;
	
	monster** m = new monster*[nr_monster];

	for (int i = 0; i < nr_monster; i++)
		m[i] = new monster(4);
	screen.h = c->SCREEN_HEIGHT;
	screen.w = c->SCREEN_WIDTH;
	screen.x = 0;
	screen.y = 0;
	//
	stretchRect.x = 0;
	stretchRect.y = 0;
	stretchRect.w = c->SCREEN_WIDTH;
	stretchRect.h = c->SCREEN_HEIGHT;	
	//pozitia lui fumiko
	fumiko_position.h = fumiko->get_player_h();
	fumiko_position.w = fumiko->get_player_w();
	fumiko_position.x = 100;
	fumiko_position.y = 350;
	//
	init.h = fumiko_position.h;
	init.w = fumiko_position.w;
	init.x = fumiko_position.x;
	init.y =fumiko_position.y;
	fumiko->set_position(fumiko_position);
	//pozitia monstrului	;
	monster_pos.y = 330;
	monster_pos.w = m[0]->get_player_w();
	monster_pos.h = m[0]->get_player_h();
	curr_time = time(NULL);
	tm_local = localtime(&curr_time);
	srand(tm_local->tm_sec);
	for (int i = 0; i < nr_monster; i++)
	{

		monster_pos.x = rand()% c->get_widht();
		m[i]->set_position(monster_pos);
		m[i]->set_viata(5000);
	}
	fumiko->set_viata(10000);
	int v[] = { 0,6,12,0,18};
	//Start up SDL and create window

	
	cout << "\nMonster x coordinate: " << monster_pos.x;
	
	if( !c->init() )
	{
		printf( "Failed to initialize!\n" );
	}
	else
	{
		//Load media
		if (!c->loadMedia("D:/Proiect-POO-Slasher/Texturi/forest.png"))
		{
			printf("Failed to load media!\n");
		}
		else
		{
			for(int i=0; i<nr_monster; i++)
				m[i]->loadMedia(*c);
			fumiko->set_nrTexturi(24);
			fumiko->loadMedia(*c);
			//While application is running
			//c->creeare_obiect(fumiko_position.x/10, fumiko_position.y/10);
			fumiko->creeare_obiect(c->get_mapa(), 1);
			//m->creeare_obiect(c->get_mapa(), 2);
			original_texture = fumiko->get_surface(0);
			//c->set_position(fumiko->get_position());
			while (!quit)
			{	
				ok3 = false;
				if (c->get_position().x<=-c->get_widht()||c->get_position().x>=c->get_widht())
				{
					c->set_position3(-c->get_widht());
					c->set_position2(c->get_widht());
					c->set_position(0);
					//fumiko->set_position(init);
				}
				else
					init2 = fumiko->get_position();
				
				
				frameStart = SDL_GetTicks();
				some_texture = original_texture;
				
				//Handle events on queue
				while (SDL_PollEvent(&e) != 0)
				{
					//some_texture = fumiko->get_surface(0);
					//User requests quit
					if (e.type == SDL_QUIT)
					{
						quit = true;
					}
			
					}
		
				keystate = SDL_GetKeyboardState(NULL);
				if (keystate[SDL_SCANCODE_SPACE])
					for(int i=0; i<nr_monster; i++)
						some_texture = fumiko->atack(v, m[i], c, frame_rader, ok2);
				else
					if (keystate[SDL_SCANCODE_RIGHT])
					{
						
						if (you_jumped == true)
						{

							fumiko_position = fumiko->get_position();
							fumiko_position.x += 4;
							fumiko->distrugere_obiect(c->get_mapa());
							if (!fumiko->cross(c->get_mapa(), 2))
							{
								fumiko->set_position(fumiko_position);
							}
						}
						some_texture = fumiko->walk_right(v, c, frame_rader);
						original_texture = fumiko->get_surface(0);
						ok2 = false;
						c->set_position(fumiko->get_position(), init2);
						//c->set_position2(fumiko->get_position(), init2);
						ceva = fumiko->get_position().x - init2.x;
						ok3 = false;
					}
					else
						if (keystate[SDL_SCANCODE_LEFT])
						{
							if (you_jumped == true)
							{

								fumiko_position = fumiko->get_position();
								fumiko_position.x -= 4;
								fumiko->distrugere_obiect(c->get_mapa());
								if (!fumiko->cross(c->get_mapa(), 2))
								{
									fumiko->set_position(fumiko_position);
								}
							}
							some_texture = fumiko->walk_left(v, c, frame_rader);
							original_texture = fumiko->get_surface(6);
							ok2 = true;
							c->set_position(fumiko->get_position(), init2);
							
							//c->set_position2(fumiko->get_position(), init2);
							//ceva = fumiko->get_position().x - init2.x;
						}
						else
							if(keystate[SDL_SCANCODE_DOWN])
									some_texture = fumiko->stay_down();
				if (keystate[SDL_SCANCODE_UP]&&you_jumped==false)
				{
					you_jumped = true;
					ok = false;
				}
				//Clear screen
					SDL_RenderClear(c->get_render());
					//Render texture to screen
					
					if (you_jumped)
					{
							if(ok==true&&high>=150)
								you_jumped = false;
							else
								some_texture = fumiko->jump(c, ok, high, ok2);
							cout << "\nok= " << ok << " " << "\nhigh= " << high;
						
					}
					if (fumiko->get_viata() > 0)
					{
						
						SDL_RenderCopy(c->get_render(), c->get_draw(), NULL, &c->get_position());
						SDL_RenderCopy(c->get_render(), c->get_draw(), NULL, &c->get_position2());
						SDL_RenderCopy(c->get_render(), c->get_draw(), NULL, &c->get_position3());
						if (you_jumped == true)
						{	
							init.y = fumiko->get_position().y;
						}
						SDL_RenderCopy(c->get_render(), some_texture, NULL, &init);
						for (int i = 0; i < nr_monster; i++)
						{
							if(ok3==true)
								another_texture = m[i]->walk(fumiko, c, v, frame_rader_2, ceva);
							else
								another_texture = m[i]->walk(fumiko, c, v, frame_rader_2, 0);
							if (another_texture != NULL)
								SDL_RenderCopy(c->get_render(), another_texture, NULL, &m[i]->get_position());
						}
					//Wait two seconds
					}
					else
						SDL_RenderCopy(c->get_render(), c->get_gameOver(), NULL, NULL);
					//Update screen
					SDL_RenderPresent(c->get_render());
				if ((1000/fps)>SDL_GetTicks()-frameStart)
				{
					SDL_Delay((1000/60)-(SDL_GetTicks()-frameStart)); 
				}

			}
		}
	}
	cout << endl;
	c->afisare_map();
	delete c;
	delete fumiko;
	//Free resources and close SDL with the help of the destructor


	return 0;
}