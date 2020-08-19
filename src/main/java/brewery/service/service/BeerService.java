package brewery.service.service;

import brewery.service.web.model.BeerDto;

import java.util.UUID;

public interface BeerService
{
    BeerDto findBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
