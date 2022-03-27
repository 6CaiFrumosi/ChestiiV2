#pragma once
#include <stdio.h>
#include <SDL.h>
#include<SDL_image.h>
#include <stdlib.h>


//Screen dimension constants
class fereastra {
public:
	const int SCREEN_WIDTH;
	const int SCREEN_HEIGHT;
private:
	//The window we'll be rendering to
	SDL_Window* gWindow;

	//The surface contained by the window
	SDL_Surface* gScreenSurface;

	//The image we will load and show on the screen
	SDL_Texture* gHelloWorld;

	//The window renderer
	SDL_Renderer* gRenderer;

	SDL_Texture* game_over;
	SDL_Rect position;
	SDL_Rect position2;
	SDL_Rect position3;
	int** mapa;
public:
	//Starts up SDL and creates window
	fereastra();
	virtual ~fereastra();
	bool init();

	//Loads media
	bool loadMedia(const char* buff);
	SDL_Window* get_window() { return gWindow; }
	SDL_Surface* get_surface() { return gScreenSurface; }
	SDL_Texture* get_draw() { return gHelloWorld; }
	SDL_Renderer* get_render() { return gRenderer; }
	SDL_Texture* loadTexture(const char* buff);
	SDL_Texture* get_gameOver() { return game_over; }
	void creeare_obiect(int x, int y);
	void distrugere_obiect(int x, int y);
	void afisare_map();
	int** get_mapa() { return mapa; };
	int get_widht() { return SCREEN_WIDTH; }
	int get_height() { return SCREEN_HEIGHT; }
	void set_position(SDL_Rect fumiko, SDL_Rect position);
	void set_position(SDL_Rect pos) { this->position.x = pos.x; }
	virtual SDL_Rect get_position() { return this->position; }
	//void set_position2(SDL_Rect fumiko, SDL_Rect position);
	SDL_Rect get_position2() { return this->position2; }
	void set_position2(int position) { position2.x = position; }
	virtual void set_position(int position) { this->position.x = position;  }
	void set_position3(int pos) { this->position3.x = pos; }
	SDL_Rect get_position3() { return this->position3; }
};


