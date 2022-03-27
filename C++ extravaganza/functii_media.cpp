#include "functii_media.h"
#include <iostream>
using namespace std;


bool fereastra::init()
{
	//Initialization flag
	bool success = true;

	//Create window
	gWindow = SDL_CreateWindow("SDL Tutorial", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN);
	if (gWindow == NULL)
	{
		printf("Window could not be created! SDL Error: %s\n", SDL_GetError());
		success = false;
	}
	else
	{
		//SDL_SetWindowFullscreen(gWindow, SDL_WINDOW_FULLSCREEN);
		//Create renderer for window
		gRenderer = SDL_CreateRenderer(gWindow, -1, SDL_RENDERER_ACCELERATED);
		if (gRenderer == NULL)
		{
			printf("Renderer could not be created! SDL Error: %s\n", SDL_GetError());
			success = false;
		}
		else
		{
			//Initialize renderer color
			SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);

			//Initialize PNG loading
			int imgFlags = IMG_INIT_PNG;
			if (!(IMG_Init(imgFlags) & imgFlags))
			{
				printf("SDL_image could not initialize! SDL_image Error: %s\n", IMG_GetError());
				success = false;
			}
			else
			{
				//Get window surface
				gScreenSurface = SDL_GetWindowSurface(gWindow);
				
			}
		}
	}

	return success;
}
void fereastra::creeare_obiect(int x, int y)
{
	mapa[y][x] = 1;
}
void fereastra::distrugere_obiect(int x, int y)
{
	mapa[y][x] = 0;
}
void fereastra::afisare_map()
{
	for (int i = 0; i < SCREEN_HEIGHT/10; i++)
	{
		for (int j = 0; j < (SCREEN_WIDTH/10)*3; j++)
			cout << mapa[i][j] << " ";
		cout << endl;
	}
	}
bool fereastra::loadMedia(const char* buff)
{
	//Load splash image
	gHelloWorld = loadTexture(buff);
	if (gHelloWorld == NULL)
	{
		printf("\nUnable to load image PNG!");
		return false;
	}
	game_over = loadTexture("D:/Proiect-POO-Slasher/Texturi/game_over.png");
	if (game_over == NULL)
	{
		printf("\nUnable lo load game_over PNG!");
		return false;
	}
	return true;
}

fereastra::fereastra() :SCREEN_WIDTH(640), SCREEN_HEIGHT(480)
{	
	//
	this->position.h = SCREEN_HEIGHT;
	this->position.w = SCREEN_WIDTH;
	this->position.x = 0;
	this->position.y = 0;
	//
	this->position2.h = SCREEN_HEIGHT;
	this->position2.w = SCREEN_WIDTH;
	this->position2.x = SCREEN_WIDTH;
	this->position2.y = 0;
	//
	this->position3.h = SCREEN_HEIGHT;
	this->position3.w = SCREEN_WIDTH;
	this->position3.x = -SCREEN_WIDTH;
	this->position3.y = 0;
	game_over = NULL;
	gWindow = NULL;
	gScreenSurface = NULL;
	gHelloWorld = NULL;
	gRenderer = NULL;
	mapa = new int* [SCREEN_HEIGHT / 10];
	for (int i = 0; i < SCREEN_HEIGHT / 10; i++)
	{
		mapa[i] = new int[(SCREEN_WIDTH / 10)*3];
		for (int j = 0; j < (SCREEN_WIDTH / 10)*3; j++)
			mapa[i][j] = 0;
	}
}
fereastra::~fereastra()
{
	//Free loaded image
	SDL_DestroyTexture(gHelloWorld);
	gHelloWorld= NULL;
	//Destroy window    
	SDL_DestroyRenderer(gRenderer);
	SDL_DestroyWindow(gWindow);
	gWindow = NULL;
	gRenderer = NULL;
	cout << endl << "Screen_height/10=" << SCREEN_HEIGHT / 10 << endl << "Screen_width/10=" << SCREEN_WIDTH / 10;
	for (int i = 0; i < SCREEN_HEIGHT/10; i++)
		delete[] mapa[i];
	delete[] mapa;
	mapa = NULL;
	//Quit SDL subsystems
	IMG_Quit();
	SDL_Quit();

}



SDL_Texture* fereastra::loadTexture(const char* buff)
{
	//free();
	//The final texture
	SDL_Texture* newTexture = NULL;

	//Load image at specified path
	SDL_Surface* loadedSurface = IMG_Load(buff);
	if (loadedSurface == NULL)
	{
		printf("Unable to load image %s! SDL_image Error: %s\n", buff, IMG_GetError());
	}
	else
	{
		//Create texture from surface pixels
		newTexture = SDL_CreateTextureFromSurface(gRenderer, loadedSurface);
		if (newTexture == NULL)
		{
			printf("Unable to create texture from %s! SDL Error: %s\n", buff, SDL_GetError());
		}

		//Get rid of old loaded surface
		SDL_FreeSurface(loadedSurface);
	}

	return newTexture;
}



void fereastra::set_position(SDL_Rect fumiko, SDL_Rect position)
{
	this->position.x = this->position.x - (fumiko.x - position.x);
	this->position2.x = this->position2.x - (fumiko.x - position.x);
	this->position3.x = this->position3.x - (fumiko.x - position.x);
}