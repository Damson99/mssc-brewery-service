package brewery.service.event;

import brewery.service.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent
{
    public NewInventoryEvent(BeerDto beerDto)
    {
        super(beerDto);
    }
}
