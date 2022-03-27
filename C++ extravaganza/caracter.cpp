#include "caracter.h"

SDL_Texture* caracter::loadTexture(const char* buff, fereastra& c)
{


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
		//Color key image
		SDL_SetColorKey(loadedSurface, SDL_TRUE, SDL_MapRGB(loadedSurface->format, 0xFF, 0xFF, 0xFF));

		//Create texture from surface pixels
		newTexture = SDL_CreateTextureFromSurface(c.get_render(), loadedSurface);
		if (newTexture == NULL)
		{
			printf("Unable to create texture from %s! SDL Error: %s\n", buff, SDL_GetError());
		}

		//Get rid of old loaded surface
		SDL_FreeSurface(loadedSurface);
	}

	//Return success
	return newTexture;

}

SDL_Texture* caracter::get_surface(int i)
{
	return movement[i];
}

int caracter::get_viata()
{
	return viata;
}

int caracter::get_atac()
{
	return atac;
}

caracter::caracter(int viata, int atac)
{
	//this->player_h = -1;
	//this->player_w = -1;
	this->viata = viata;
	this->atac = atac;
}

void caracter::creeare_obiect(int** v, int val)
{
	for (int i = 2+ position.y/10; i >= position.y/10 - 2; i--)
		if(i<48&&i>=0)
		for (int j = 2 + position.x/10; j >= position.x/10 - 2; j--)
			if(j<64*3&&j>=0)
			v[i][j] = val;
}
void caracter::distrugere_obiect(int** v)
{
	for (int i = 2+ position.y/10; i >= position.y/10 - 2; i--)
		if(i<48&&i>=0)
		for (int j = 2 + position.x/10; j >= position.x/10 - 2; j--)
			if(j<64*3&&j>=0)
			v[i][j] = 0;
}

bool caracter::cross(int** v, int val)
{
	
	for (int i = 2 + position.y / 10; i >= position.y / 10 - 2; i--)
		if (i < 48 && i >= 0)
			for (int j = 2 + position.x / 10; j >= position.x / 10 - 2; j--)
				if (j < 64*3 && j >= 0)
				{
					if (j - 1 >= 0)
					{
						if (v[i][j - 1] == val)
							return true;
						if(i-1>=0)
						if (v[i - 1][j - 1] == val)
							return true;
						if(i+1<48)
						if (v[i + 1][j - 1] == val)
							return true;
					}
					if(i-1>=0)
					if (v[i - 1][j] == val)
						return true;
					if(i+1<48)
					if (v[i + 1][j] == val)
						return true;
					if (j + 1 < 64*3)
					{
						if(i-1>=0)
						if (v[i - 1][j + 1] == val)
							return true;
						if (v[i][j + 1] == val)
							return true;
						if(i+1<48)
						if (v[i + 1][j + 1] == val)
							return true;
					}
				}
	return false;
}	