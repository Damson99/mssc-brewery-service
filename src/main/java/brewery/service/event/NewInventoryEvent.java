package brewery.service.event;

import brewery.service.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent
{
    NewInventoryEvent(BeerDto beerDto)
    {
        super(beerDto);
    }
}
