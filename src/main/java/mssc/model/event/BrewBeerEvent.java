package mssc.model.event;

import mssc.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent
{
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
