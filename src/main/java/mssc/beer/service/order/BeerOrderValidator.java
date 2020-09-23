package mssc.beer.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mssc.beer.repository.BeerRepository;
import mssc.model.event.BeerOrderDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator
{
    private final BeerRepository beerRepository;

    public Boolean validateBeerOrder(BeerOrderDto beerOrderDto)
    {
        AtomicInteger beersNotFound = new AtomicInteger();
        beerOrderDto.getBeerOrderLines().forEach(beer ->
        {
            if(beerRepository.findByUpc(beer.getUpc()) == null)
                beersNotFound.incrementAndGet();
        });

        return beersNotFound.get() == 0;
    }
}
