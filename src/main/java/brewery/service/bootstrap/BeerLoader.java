package brewery.service.bootstrap;

import brewery.service.domain.Beer;
import brewery.service.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner
{
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository)
    {
        this.beerRepository = beerRepository;
    }


    @Override
    public void run(String... args) throws Exception
    {
        loadBeerObjects();
    }

    private void loadBeerObjects()
    {
        if(beerRepository.count() == 0)
        {
            beerRepository.save(Beer.builder()
                    .beerName("Corona Extra")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(337010000001L)
                    .price(new BigDecimal("3.99"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Captain Jack")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(337010000002L)
                    .price(new BigDecimal("4.99"))
                    .build());
        }
    }
}
