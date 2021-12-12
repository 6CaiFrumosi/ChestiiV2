#pragma once
class Fumiko;
#include "Fumiko.h"
class monster:public caracter
{
private:
	const int nr_text;
	static int nr_monsters;
public:
	bool loadMedia(fereastra& c);
	monster(int nr);
	SDL_Texture* walk(Fumiko* fumiko, fereastra* c, int* state, int& frame_rader, int position2);
	static int get_NRmonsters() { return nr_monsters; }
	static void set_NRmonsters(int nr_monsters_2) { nr_monsters = nr_monsters_2; }
};