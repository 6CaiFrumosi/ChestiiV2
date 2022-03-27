#pragma once
class monster;
#include "caracter.h"
#include "monsters.h"
#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif



class Fumiko : public caracter
{
private:
	int nr_texturi;
public:
	~Fumiko();
	Fumiko(int nr_texturi, int atac, int viata);
	bool loadMedia(fereastra& c);
	SDL_Texture* walk_right(int state[], fereastra* c, int &frame_rader);
	SDL_Texture* walk_left(int* state, fereastra* c, int &frame_rader);
	SDL_Texture* atack(int* state, monster* m, fereastra* c, int &frame_rader,bool& ok);
	SDL_Texture* stay_down();
	SDL_Texture* just_stay();
	SDL_Texture* jump(fereastra* c, bool& ok, int& high, bool& ok2);
	void set_nrTexturi(int nrTexturi) { nr_texturi = nrTexturi; }
	int get_nrTexturi() { return nr_texturi; }
};
