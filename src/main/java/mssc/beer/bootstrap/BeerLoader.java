package mssc.beer.bootstrap;

import mssc.beer.domain.Beer;
import mssc.beer.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner
{
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;


    @Override
    public void run(String... args) throws Exception
    {
        if(beerRepository.count() == 0)
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
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("3.99"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Captain Jack")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("4.99"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Desperados")
                    .beerStyle("PALE ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("4.99"))
                    .build());
        }
    }
}
