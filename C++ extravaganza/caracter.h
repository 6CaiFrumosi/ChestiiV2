#pragma once
#include "functii_media.h"
class caracter
{
protected:
	SDL_Texture** movement;
	int player_h, player_w;
	SDL_Rect position;
private:
	int viata;
	int atac;
public:
	int get_viata();
	int get_atac();
	caracter(int atac = 5, int viata = 100);
	virtual bool loadMedia(fereastra& c) = 0;
	SDL_Texture* get_surface(int i);
	SDL_Texture* loadTexture(const char* buff, fereastra& c);
	int get_player_h() { return player_h; }
	int get_player_w() { return player_w; }
	void creeare_obiect(int** v, int val);
	void distrugere_obiect(int** v);
	bool cross(int** v, int val);
	void set_viata(int viata) { this->viata = viata; }
	void set_atac(int atac) { this->atac = atac; }
	SDL_Rect get_position() { return position; }
	void set_position(SDL_Rect& position) { this->position = position; }

};
