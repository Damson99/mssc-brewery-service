package brewery.service.event;

import brewery.service.web.model.BeerDto;


public class BrewBeerEvent extends BeerEvent
{
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
